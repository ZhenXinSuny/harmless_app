package com.agridata.cdzhdj.activity.harmless.regionAdmin.inventoryrecord;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.region.SelectAreaActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.ruku.RuKuDetailActivity;
import com.agridata.cdzhdj.activity.adapter.RuKuAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.dao.TRegionDao;
import com.agridata.cdzhdj.data.RuKuBean;
import com.agridata.cdzhdj.data.TRegion;
import com.agridata.cdzhdj.databinding.ActivityInventoryRecordsBinding;
import com.agridata.cdzhdj.dbutils.DaoManager;
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

/**
 * 入库记录
 */
public class InventoryRecordsActivity extends BaseActivity<ActivityInventoryRecordsBinding> implements View.OnClickListener {
    private final static String TAG = "RuKuActivity------》";

    private int regionID;
    private TRegion tRegion;
    private RuKuAdapter ruKuAdapter;

    private int page = 0;
    private int size = 10;
    private List<RuKuBean.Result.PageItems> pageItemsList;
    private View mDrawer;

    private int regionLevel;
    public static void start(Context context) {
        Intent intent = new Intent(context, InventoryRecordsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ActivityInventoryRecordsBinding getBinding() {
        return ActivityInventoryRecordsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setOnClickListener(v -> finish());
        regionID = UserDataSP.getInstance().getUserInfo().Result.dependency.Dep_AgencyMID.Region.id;
        LogUtil.d(TAG, "获取本地区化" + regionID);
        tRegion = DaoManager.queryRegionDao().queryBuilder().where(TRegionDao.Properties.Region_id.eq(regionID)).list().get(0);
        LogUtil.d(TAG, "获取本地区化" + tRegion.toString());

        binding.navdrawer.regionTv.setText(tRegion.getRegion_shortname());
        regionLevel = (int) tRegion.getRegion_level();

        pageItemsList = new ArrayList<>();
        mDrawer = findViewById(R.id.navdrawer);
        binding.queryTv.setOnClickListener(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ruKuAdapter = new RuKuAdapter(R.layout.item_ruku, this);
        binding.recyclerView.setAdapter(ruKuAdapter);
        binding.refreshLayout.autoRefresh();
        binding.refreshLayout.setEnableOverScrollDrag(true);
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
        ruKuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                RuKuBean.Result.PageItems data = ruKuAdapter.getData(position);
                RuKuDetailActivity.start(InventoryRecordsActivity.this,data.mid);
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
            binding.refreshLayout.setEnableRefresh(true);
            binding.refreshLayout.autoRefresh(500);
            binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
            smoothMoveToPosition(binding.recyclerView,0);
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

        binding.navdrawer.regionTv.setOnClickListener(this);//区划
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


    private void getInfo() {//
        HttpRequest.getRuKu(InventoryRecordsActivity.this,   binding.navdrawer.startTimeTv.getText().toString()+" "+"00:00:00", binding.navdrawer.endTimeTv.getText().toString() +" "+"23:59:59", regionID, regionLevel, page, size,
               2 ,binding.navdrawer.rkPersonTv.getText().toString(),new CallBackLis<RuKuBean>() {
            @Override
            public void onSuccess(String method, RuKuBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    if (content.result.pageItems.size() > 0) {
                        binding.titlebarMiddle.setText("入库记录数" + "(" + content.result.totalCount + "条" +")" );
                        if (page == 0) {
                            pageItemsList = content.result.pageItems;
                            ruKuAdapter.refreshDataList(content.result.pageItems);
                            if (ruKuAdapter.getDataList().size() == content.result.totalCount) {
                                binding.refreshLayout.finishRefreshWithNoMoreData();//将不会再次触发加载更多事件
                            } else if (ruKuAdapter.getDataList().size() < content.result.totalCount) {
                                binding.refreshLayout.finishRefresh();
                            }
                        } else {
                            if (ruKuAdapter.getDataList().size() == content.result.totalCount) {
                                ruKuAdapter.addDataList(content.result.pageItems);
                                ruKuAdapter.notifyDataSetChanged();
                                binding.refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                            } else {
                                ruKuAdapter.addDataList(content.result.pageItems);
                                ruKuAdapter.notifyDataSetChanged();
                                if (ruKuAdapter.getDataList().size() == content.result.totalCount){
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
                        Objects.requireNonNull(RxToast.info(InventoryRecordsActivity.this,"当前暂无数据"));
                        binding.titlebarMiddle.setText("入库记录数" + "(" + 0 + "单" +")" );
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
    protected void OnEventMainThread() {
        super.OnEventMainThread();
        mRxManager.on("CHOOSE_REGION", o -> {
            int RegionID = (int) o;
            LogUtil.d(TAG, "回传回来的区划数据" + RegionID);
            TRegion tr = DaoManager.queryRegionDao().queryBuilder()
                    .where(TRegionDao.Properties.Region_id.eq(RegionID))
                    .list().get(0);
            LogUtil.d(TAG, "回传回来的区划数据" + tr.toString());

            regionLevel = (int) tr.getRegion_level();
            regionID =RegionID;
            binding.navdrawer.regionTv.setText(tr.getRegion_shortname());
        });
    }


    @Override
    public void onClick(View v) {
        if (v==binding.queryTv){
//            if (pageItemsList.size()>0){
            showDrawer();
//            }else {
//                ToastUtil.showToast(this,"当前暂无数据，无法使用查询功能");
//            }

        }else if (v==binding.navdrawer.regionTv){
            SelectAreaActivity.start(InventoryRecordsActivity.this);
        }
    }
}
