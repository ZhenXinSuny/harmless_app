package com.agridata.cdzhdj.activity.harmless.regionAdmin;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.bsduzs.BsdwZsWebViewActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.bzt.BZTActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.chuli.ChuLiActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.collect_record.CollectRecordActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.collected.CollectedActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.historical_collection.HistoricalCollectionActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.inventoryrecord.InventoryRecordsActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.ruku.RuKuActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.tbcollected.ToBeCollectedActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.xdr.XdrListActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.region.SelectAreaActivity;
import com.agridata.cdzhdj.activity.adapter.RegionAdminMenuAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.dao.TRegionDao;
import com.agridata.cdzhdj.data.AdminRegionHomeBean;
import com.agridata.cdzhdj.data.RegionAdminBean;
import com.agridata.cdzhdj.data.TRegion;
import com.agridata.cdzhdj.databinding.ActivityRegionAdminBinding;
import com.agridata.cdzhdj.dbutils.DaoManager;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lzx.utils.RxToast;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * 新版区划辖区管理员列表
 */
public class RegionAdminActivity extends BaseActivity<ActivityRegionAdminBinding> {

    private List<RegionAdminBean> mMenuDataList;
    private RegionAdminMenuAdapter regionAdminMenuAdapter;
    private CustomLoadingDialog mLoadingDialog;
    private final static String TAG = "RegionAdminActivity------》";

    private String startTime, endTime;
    private int regionLevel, regionIds;

    public static void start(Context context) {
        Intent intent = new Intent(context, RegionAdminActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ActivityRegionAdminBinding getBinding() {
        return ActivityRegionAdminBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {

        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setImageResource(R.drawable.title_back);
        binding.titlebarLeft.setOnClickListener(v -> finish());
        initLoadingView();


        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        binding.gridRecyclerview.setLayoutManager(layoutManager);
        regionAdminMenuAdapter = new RegionAdminMenuAdapter(R.layout.item_region_menu, mMenuDataList, this, layoutManager);
        binding.gridRecyclerview.setAdapter(regionAdminMenuAdapter);



        binding.regionTv.setOnClickListener(v -> SelectAreaActivity.start(RegionAdminActivity.this));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        Date nowDate = calendar.getTime();
        String currentTime = sdf.format(nowDate);
        LogUtil.d(TAG, "当前时间" + currentTime); 
        startTime = currentTime + " " + "00:00:00";
         endTime  = currentTime + " " + "23:59:59";
        int regionID = UserDataSP.getInstance().getUserInfo().Result.dependency.Dep_AgencyMID.Region.id;
        TRegion tRegion = DaoManager.queryRegionDao().queryBuilder().where(TRegionDao.Properties.Region_id.eq(regionID)).list().get(0);
        regionIds = regionID;
        regionLevel = (int) tRegion.getRegion_level();
        getNetInfo();

        binding.sjLl.setOnClickListener(v -> CollectRecordActivity.start(RegionAdminActivity.this));
        binding.rukuJlLl.setOnClickListener(v -> InventoryRecordsActivity.start(RegionAdminActivity.this));

        binding.sjHistoryLl.setOnClickListener(v -> HistoricalCollectionActivity.start(RegionAdminActivity.this));
        binding.sjBztLl.setOnClickListener(v -> BZTActivity.start(RegionAdminActivity.this));

        binding.bsdutjLl.setOnClickListener(v -> BsdwZsWebViewActivity.start(RegionAdminActivity.this));

        binding.yzhLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XdrListActivity.start(RegionAdminActivity.this,regionIds,regionLevel);
            }
        });
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getNetInfo();
            }
        });
    }

    @Override
    protected void initDatas() {
        regionAdminMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                RegionAdminBean data = regionAdminMenuAdapter.getData(position);
                switch (data.id) {
                    case 1://待收集
                        ToBeCollectedActivity.start(RegionAdminActivity.this,regionIds,regionLevel);
                        break;
                    case 2://已收集
                        CollectedActivity.start(RegionAdminActivity.this,regionIds,regionLevel);
                        break;
                    case 3://入库单
                        RuKuActivity.start(RegionAdminActivity.this,regionIds,regionLevel);
                        break;
                    case 4://处理单
                        ChuLiActivity.start(RegionAdminActivity.this,regionIds,regionLevel, String.valueOf(data.num));
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
    }


    private void getNetInfo() {
       // showLoading("加载中...");
        HttpRequest.getAdminRegionHome(RegionAdminActivity.this, startTime, endTime, regionIds, regionLevel, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, AdminRegionHomeBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.code == 200) {
                  //  hideLoading();
                    binding.refreshLayout.finishRefresh();
                    setMenuData(content);
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull(RxToast.error(RegionAdminActivity.this,error));
            }
        });
    }

    private void setMenuData(AdminRegionHomeBean content) {
        if (content.data != null) {
            mMenuDataList = new ArrayList<>();
            RegionAdminBean menuData = new RegionAdminBean("待收集", R.drawable.icon_dsj, 1, 1, content.data.daishouji, "单");
            RegionAdminBean menuData1 = new RegionAdminBean("已收集", R.drawable.icon_sj, 2, 1, content.data.yishouji, "单");
            RegionAdminBean menuData2 = new RegionAdminBean("入库数", R.drawable.icon_rk, 3, 1, content.data.yiruku, "头");
            RegionAdminBean menuData3 = new RegionAdminBean("处理数", R.drawable.icon_cl, 4, 1, content.data.yishuli, "头");
            mMenuDataList.add(menuData);
            mMenuDataList.add(menuData1);
            mMenuDataList.add(menuData2);
            mMenuDataList.add(menuData3);
        }else {
            mMenuDataList = new ArrayList<>();
            RegionAdminBean menuData = new RegionAdminBean("待收集", R.drawable.icon_dsj, 1, 1, 0, "单");
            RegionAdminBean menuData1 = new RegionAdminBean("已收集", R.drawable.icon_sj, 2, 1, 0, "单");
            RegionAdminBean menuData2 = new RegionAdminBean("入库数", R.drawable.icon_rk, 3, 1, 0, "头");
            RegionAdminBean menuData3 = new RegionAdminBean("处理数", R.drawable.icon_cl, 4, 1, 0, "头");
            mMenuDataList.add(menuData);
            mMenuDataList.add(menuData1);
            mMenuDataList.add(menuData2);
            mMenuDataList.add(menuData3);
        }
        regionAdminMenuAdapter.refreshDataList(mMenuDataList);
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
            binding.regionTv.setText(tr.getRegion_name());

            regionIds = (int) tr.getRegion_id();
            regionLevel = (int) tr.getRegion_level();

            getNetInfo();
        });
    }

    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(this);
        this.mLoadingDialog.setCanceledOnTouchOutside(false);
    }


    /**
     * 显示加载框
     */
    public void showLoading(String tips) {
        if (this.mLoadingDialog != null && !this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.show();
            this.mLoadingDialog.setTitleVisibility(0);
            this.mLoadingDialog.setTitle(tips);
        }
    }

    /**
     * 隐藏 加载框
     */
    public void hideLoading() {
        if (this.mLoadingDialog != null && this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.hide();
        }
    }
}
