package com.agridata.cdzhdj.activity.harmless.regionAdmin.tbcollected;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.adapter.ToBeCollectedAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.TRegion;
import com.agridata.cdzhdj.data.ToBeCollectedBean;
import com.agridata.cdzhdj.databinding.ActivityTBCollectedBinding;
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
import java.util.Objects;

public class ToBeCollectedActivity extends BaseActivity<ActivityTBCollectedBinding> implements View.OnClickListener {
    private final static String TAG = "ToBeCollectedActivity------》";

    private int regionID;
    private TRegion tRegion;
    private ToBeCollectedAdapter toBeCollectedAdapter;
    private int RegionID,RegionLevel;

    private int page = 0;
    private int size = 10;
    private List<ToBeCollectedBean.Result.PageItems> pageItemsList;
    private View mDrawer;


    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context, int RegionID,int RegionLevel) {
        Intent intent = new Intent(context, ToBeCollectedActivity.class);
        Bundle data = new Bundle();
        data.putInt("RegionID", RegionID);
        data.putInt("RegionLevel", RegionLevel);
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
    }
    @Override
    protected ActivityTBCollectedBinding getBinding() {
        return ActivityTBCollectedBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        getArguments();
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setOnClickListener(v -> finish());
//        regionID = UserDataSP.getInstance().getUserInfo().Result.dependency.Dep_AgencyMID.Region.id;
//        LogUtil.d(TAG, "获取本地区化" + regionID);
//        tRegion = DaoManager.queryRegionDao().queryBuilder().where(TRegionDao.Properties.Region_id.eq(regionID)).list().get(0);
//        LogUtil.d(TAG, "获取本地区化" + tRegion.toString());
        pageItemsList = new ArrayList<>();

        mDrawer = findViewById(R.id.navdrawer);

        binding.queryTv.setOnClickListener(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        toBeCollectedAdapter = new ToBeCollectedAdapter(R.layout.item_to_be_collected, this);
        binding.recyclerView.setAdapter(toBeCollectedAdapter);
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
        toBeCollectedAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                ToBeCollectedBean.Result.PageItems data = toBeCollectedAdapter.getData(position);
                ToBeCollectedDetailActivity.start(ToBeCollectedActivity.this,data.mid);
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date nowDate = calendar.getTime();
        String currentTime = sdf.format(nowDate);
        binding.navdrawer.startTimeTv.setText(currentTime);//当前时间
        binding.navdrawer.endTimeTv.setText(currentTime);//当前时间的前30天

        binding.navdrawer.sureBtn.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(mDrawer);
            binding.refreshLayout.setVisibility(View.VISIBLE);
            binding.notDataRl.setVisibility(View.GONE);
            LogUtil.d(TAG,"执行查询1231231231");
            binding.refreshLayout.setEnableRefresh(true);
            binding.refreshLayout.autoRefresh(500);
            binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);

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
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                drawerView.setClickable(true);
            }
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

    }

    @Override
    protected void initDatas() {

    }


    private void getInfo() {
        HttpRequest.getToBeCollected(ToBeCollectedActivity.this,  binding.navdrawer.startTimeTv.getText().toString() +" "+"00:00:00", binding.navdrawer.endTimeTv.getText().toString() +" "+"23:59:59", RegionID, RegionLevel, page, size, new CallBackLis<ToBeCollectedBean>() {
            @Override
            public void onSuccess(String method, ToBeCollectedBean content) {
                LogUtil.d("lzx---------》", content.toString());
                LogUtil.d(TAG,"执行查询1111");
                if (content.status == 0) {
                    if (!content.result.pageItems.isEmpty()) {
                        binding.titlebarMiddle.setText("待收集" + "(" + content.result.totalCount + "单" +")" );
                        if (page == 0) {
                            pageItemsList = content.result.pageItems;
                            toBeCollectedAdapter.refreshDataList(content.result.pageItems);
                            if (toBeCollectedAdapter.getDataList().size() == content.result.totalCount) {
                                binding.refreshLayout.finishRefreshWithNoMoreData();//将不会再次触发加载更多事件
                            } else if (toBeCollectedAdapter.getDataList().size() < content.result.totalCount) {
                                binding.refreshLayout.finishRefresh();
                            }
                        } else {
                            if (toBeCollectedAdapter.getDataList().size() == content.result.totalCount) {
                                toBeCollectedAdapter.addDataList(content.result.pageItems);
                                toBeCollectedAdapter.notifyDataSetChanged();
                                binding.refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                            } else {
                                toBeCollectedAdapter.addDataList(content.result.pageItems);
                                toBeCollectedAdapter.notifyDataSetChanged();
                                if (toBeCollectedAdapter.getDataList().size() == content.result.totalCount){
                                    binding.refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                                    return;
                                }
                                binding.refreshLayout.finishLoadMore();
                            }
                        }
                    }else {
                        binding.refreshLayout.setEnableRefresh(false);
                        binding.refreshLayout.finishRefresh();
                        binding.refreshLayout.setVisibility(View.GONE);
                        binding.notDataRl.setVisibility(View.VISIBLE);
                        Objects.requireNonNull(RxToast.info(ToBeCollectedActivity.this,"当前暂无数据"));
                        binding.titlebarMiddle.setText("待收集" + "(" + 0 + "单" +")" );
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
        LogUtil.d(TAG,"打开搜索框");
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
//                showDrawer();
//            }else {
//                ToastUtil.showToast(this,"当前暂无数据，无法使用查询功能");
//            }
            showDrawer();
        }
    }
}
