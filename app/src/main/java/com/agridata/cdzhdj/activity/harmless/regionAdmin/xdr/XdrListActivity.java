package com.agridata.cdzhdj.activity.harmless.regionAdmin.xdr;

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
import com.agridata.cdzhdj.activity.harmless.regionAdmin.xdr.xdrcollect.XdrInfoActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.region.SelectAreaActivity;
import com.agridata.cdzhdj.activity.adapter.xdr.XdrListAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.dao.TRegionDao;
import com.agridata.cdzhdj.data.TRegion;
import com.agridata.cdzhdj.data.XdrBean;
import com.agridata.cdzhdj.databinding.ActivityXdrListBinding;
import com.agridata.cdzhdj.dbutils.DaoManager;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lzx.utils.RxToast;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class XdrListActivity extends BaseActivity<ActivityXdrListBinding> implements View.OnClickListener {
    private final static String TAG = "ToBeCollectedActivity------》";

    private int regionID;
    private TRegion tRegion;
    private XdrListAdapter xdrListAdapter;
    private int regionLevel;

    private int page = 1;
    private int size = 10;
    private List<XdrBean.Data> pageItemsList;
    private View mDrawer;


    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context, int RegionID, int RegionLevel) {
        Intent intent = new Intent(context, XdrListActivity.class);
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
        regionID = bundle.getInt("RegionID");
        regionLevel = bundle.getInt("RegionLevel");
    }
    @Override
    protected ActivityXdrListBinding getBinding() {
        return ActivityXdrListBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        getArguments();
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setOnClickListener(v -> finish());
        pageItemsList = new ArrayList<>();

        mDrawer = findViewById(R.id.navdrawer);

        binding.queryTv.setOnClickListener(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        xdrListAdapter = new XdrListAdapter(R.layout.item_xdr_info, this);
        binding.recyclerView.setAdapter(xdrListAdapter);
        binding.refreshLayout.autoRefresh();
        binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);

        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
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
        xdrListAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                XdrBean.Data data = xdrListAdapter.getData(position);
                XdrInfoActivity.start(XdrListActivity.this,data.mid,data.displayName);
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });



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
        binding.navdrawer.regionTv.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

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
    private void getInfo() {
        HttpRequest.getXdrList(XdrListActivity.this,  regionID, regionLevel,binding.navdrawer.xdrNameTv.getText().toString(),binding.navdrawer.phoneTv.getText().toString(), page, new CallBackLis<XdrBean>() {
            @Override
            public void onSuccess(String method, XdrBean content) {
                LogUtil.d("lzx---------》", content.toString());
                LogUtil.d(TAG,"执行查询1111");
                if (content.code == 200) {
                    if (!content.data.isEmpty()) {
                        if (page == 1) {
                            pageItemsList = content.data;
                            xdrListAdapter.refreshDataList(content.data);
                            binding.refreshLayout.finishRefresh();
                        } else {
                                xdrListAdapter.addDataList(content.data);
                                xdrListAdapter.notifyDataSetChanged();
                                binding.refreshLayout.finishLoadMore();
                        }
                    }else {
                        binding.refreshLayout.setEnableRefresh(false);
                        binding.refreshLayout.finishRefresh();
                        binding.refreshLayout.setVisibility(View.GONE);
                        binding.notDataRl.setVisibility(View.VISIBLE);
                        Objects.requireNonNull(RxToast.info(XdrListActivity.this,"当前暂无数据"));
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
            showDrawer();
        }else  if (v==binding.navdrawer.regionTv){
            SelectAreaActivity.start(XdrListActivity.this);
        }
    }


}
