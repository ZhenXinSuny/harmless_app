package com.agridata.cdzhdj.activity.harmless.regionAdmin.chuli;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.adapter.ChuLiAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.AdminRegionHomeBean;
import com.agridata.cdzhdj.data.ChuLiBean;
import com.agridata.cdzhdj.data.TRegion;
import com.agridata.cdzhdj.databinding.ActivityChuliBinding;
import com.agridata.cdzhdj.utils.TimeDialogUtils;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lzx.utils.RxToast;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ChuLiActivity extends BaseActivity<ActivityChuliBinding> implements View.OnClickListener {
    private final static String TAG = "ChuLiActivity------》";

    private int regionID;
    private TRegion tRegion;
    private ChuLiAdapter  chuLiAdapter;

    private int page = 0;
    private int size = 10;
    private List<ChuLiBean.Result.PageItems> pageItemsList;
    private View mDrawer;

    private int RegionID,RegionLevel;
    private String Num;
    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context, int RegionID,int RegionLevel,String num) {
        Intent intent = new Intent(context, ChuLiActivity.class);
        Bundle data = new Bundle();
        data.putInt("RegionID", RegionID);
        data.putInt("RegionLevel", RegionLevel);
        data.putString("Num", num);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }


    /**
     * 获取上一级所传过来的值  去判断加载哪一个Url
     */
    private void getArguments() {
        Bundle bundle = getIntent().getBundleExtra("data");
        RegionID = bundle.getInt("RegionID");
        RegionLevel = bundle.getInt("RegionLevel");
        Num = bundle.getString("Num");
    }


    @Override
    protected ActivityChuliBinding getBinding() {
        return ActivityChuliBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        getArguments();
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.chuliNumTv.setText( "(" + Num +")" );
        pageItemsList = new ArrayList<>();
        mDrawer = findViewById(R.id.navdrawer);

        binding.queryTv.setOnClickListener(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chuLiAdapter = new ChuLiAdapter(R.layout.item_chuli, this);
        binding.recyclerView.setAdapter(chuLiAdapter);
        binding.refreshLayout.autoRefresh();
        binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getInfo();
            }
        });

        //加载的监听事件
        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getInfo();
            }
        });
        chuLiAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                ChuLiBean.Result.PageItems data = chuLiAdapter.getData(position);
                ChuLiDetailActivity.start(ChuLiActivity.this,data.mid);
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        Date nowDate = calendar.getTime();
        String currentTime = sdf.format(nowDate);


        Date timeOneMonth = TimeDialogUtils.getTimeOneMonth();
        binding.navdrawer.startTimeTv.setText(sdf.format(timeOneMonth));//当前时间
        binding.navdrawer.endTimeTv.setText(currentTime);//当前时间的前30天

        binding.navdrawer.sureBtn.setOnClickListener(v -> {
            LogUtil.d(TAG,"加载数据");
            binding.drawerLayout.closeDrawer(mDrawer);
            binding.notDataRl.setVisibility(View.GONE);
            binding.refreshLayout.setEnableRefresh(true);
            binding.refreshLayout.autoRefresh(500);
            binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
            smoothMoveToPosition(binding.recyclerView,0);
            getNetInfo();
        });

        binding.navdrawer.noBtn.setOnClickListener(v -> binding.drawerLayout.closeDrawer(mDrawer));

        binding.navdrawer.startTimeTv.setOnClickListener(v -> {
            TimeDialogUtils.initTimePicker1(this, binding.navdrawer.startTimeTv);
            TimeDialogUtils.pvTime.show();
        });
        binding.navdrawer.endTimeTv.setOnClickListener(v -> {
            TimeDialogUtils.initTimePicker2(this, binding.navdrawer.endTimeTv);
            TimeDialogUtils.pvTime1.show();
        });

        //这里设置clickable(true)  必须动态设置  静态设置没有效果
        //解决问题   侧滑菜单出来的时候 点击菜单上的区域会有点击穿透问题

        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.setClickable(true);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
            }
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        getNetInfo();
    }
    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;
    //目标项是否在最后一个可见项之后 private boolean mShouldScroll; //记录目标项位置 private int mToPosition;
    //滑动到指定位置
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) { // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }
    @Override
    protected void initDatas() {

    }

    private void getNetInfo() {
        // showLoading("加载中...");
        HttpRequest.getAdminRegionHome(ChuLiActivity.this, binding.navdrawer.startTimeTv.getText().toString()+" "+"00:00:00", binding.navdrawer.endTimeTv.getText().toString() +" "+"23:59:59", RegionID, RegionLevel, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, AdminRegionHomeBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.code == 200) {

                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(ChuLiActivity.this,error));
            }
        });
    }
    private void getInfo() {//binding.navdrawer.startTimeTv.getText().toString()
        HttpRequest.getChuLi(ChuLiActivity.this,   binding.navdrawer.startTimeTv.getText().toString()+" "+"00:00:00", binding.navdrawer.endTimeTv.getText().toString() +" "+"23:59:59", RegionID, RegionLevel,page, size, new CallBackLis<ChuLiBean>() {
            @Override
            public void onSuccess(String method, ChuLiBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    if (content.result.pageItems.size() > 0) {
                        if (page == 0) {
                            binding.refreshLayout.setVisibility(View.VISIBLE);
                            binding.notDataRl.setVisibility(View.GONE);
                            pageItemsList = content.result.pageItems;
                            chuLiAdapter.refreshDataList(content.result.pageItems);
                            if (chuLiAdapter.getDataList().size() == content.result.totalCount) {
                                binding.refreshLayout.finishRefreshWithNoMoreData();//将不会再次触发加载更多事件
                            } else if (chuLiAdapter.getDataList().size() < content.result.totalCount) {
                                binding.refreshLayout.finishRefresh();
                            }
                        } else {
                            if (chuLiAdapter.getDataList().size() == content.result.totalCount) {
                                chuLiAdapter.addDataList(content.result.pageItems);
                                chuLiAdapter.notifyDataSetChanged();
                                binding.refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                            } else {
                                chuLiAdapter.addDataList(content.result.pageItems);
                                chuLiAdapter.notifyDataSetChanged();
                                if (chuLiAdapter.getDataList().size() == content.result.totalCount){
                                    binding.refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                                    return;
                                }
                                binding.refreshLayout.finishLoadMore();
                            }
                        }
                    }else {
                        binding.refreshLayout.setEnableRefresh(false);
                        binding.refreshLayout.finishRefresh();
                        binding.notDataRl.setVisibility(View.VISIBLE);
                        Objects.requireNonNull(RxToast.info(ChuLiActivity.this,"当前暂无数据"));
                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        showDrawer();
        return false;
    }

    private void showDrawer() {
        if (binding.drawerLayout.isDrawerOpen(mDrawer)) {
            binding.drawerLayout.closeDrawer(mDrawer);
        } else {
            binding.drawerLayout.openDrawer(mDrawer);
        }
    }



    @Override
    public void onClick(View v) {
        if (v==binding.queryTv){
//            if (pageItemsList.size()>0){
                showDrawer();
//            }else {
//                ToastUtil.showToast(this,"当前暂无数据，无法使用查询功能");
//            }

        }
    }
}
