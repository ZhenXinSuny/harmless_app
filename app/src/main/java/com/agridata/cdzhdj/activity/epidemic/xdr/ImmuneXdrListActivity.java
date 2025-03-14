package com.agridata.cdzhdj.activity.epidemic.xdr;

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
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.region.SelectAreaActivity;
import com.agridata.cdzhdj.activity.adapter.immuneXdr.ImmuneXdrListAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.dao.TRegionDao;
import com.agridata.cdzhdj.data.ImmuneXdrBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.TRegion;
import com.agridata.cdzhdj.databinding.ActivityImmuneXdrListBinding;
import com.agridata.cdzhdj.dbutils.DaoManager;
import com.agridata.cdzhdj.utils.AppConstants;
import com.agridata.cdzhdj.utils.RxBus;
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

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-11-28 15:42.
 * @Description :免疫畜主界面
 */
public class ImmuneXdrListActivity extends BaseActivity<ActivityImmuneXdrListBinding> implements View.OnClickListener {
    private final static String TAG = "ImmuneXdrListActivity------》";

    private int regionID;
    private TRegion tRegion;
    private ImmuneXdrListAdapter immuneXdrListAdapter;

    private int page = 0;
    private int size = 10;
    private List<ImmuneXdrBean.Result.PageItems> pageItemsList;
    private View mDrawer;
    private boolean flag;
    private int RegionID, RegionLevel;

    private int IsSelfWrite;
    private String Mobile;

    private int mType;

    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ImmuneXdrListActivity.class);
        Bundle data = new Bundle();
        data.putInt("type", type);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }


    /**
     * 获取上一级所传过来的值  去判断加载哪一个Url
     */
    private void getArguments() {
        Bundle bundle = getIntent().getBundleExtra("data");
        mType = bundle.getInt("type");
    }


    @Override
    protected ActivityImmuneXdrListBinding getBinding() {
        return ActivityImmuneXdrListBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        getArguments();
        List<String> roleList = new ArrayList<>();
        for (LoginData.ResultBean.RolesBean role : UserDataSP.getInstance().getUserInfo().Result.roles) {
            roleList.add(role.id);
        }
        if (roleList.contains(AppConstants.FangYiYuanID) || roleList.contains(AppConstants.XZFYMaster)
                || roleList.contains(AppConstants.XianMaster)||roleList.contains(AppConstants.ShiMaster)) {
            binding.queryTv.setVisibility(View.VISIBLE);
            LogUtil.d(TAG, "防疫员");
            if (UserDataSP.getInstance().getUserInfo().Result.dependency.Dep_AgencyMID!=null){
                int regionID = UserDataSP.getInstance().getUserInfo().Result.dependency.Dep_AgencyMID.Region.id;
                TRegion tRegion = DaoManager.queryRegionDao().queryBuilder().where(TRegionDao.Properties.Region_id.eq(regionID)).list().get(0);
                RegionID = regionID;
                RegionLevel = (int) tRegion.getRegion_level();
                IsSelfWrite = AppConstants.IsSelfWrite.FYYMY;
                binding.navdrawerImmuneXdr.regionTv.setText(tRegion.getRegion_shortname());
                binding.addXdrTv.setVisibility(View.VISIBLE);
            }
        } else {
            binding.addXdrTv.setVisibility(View.GONE);
            LogUtil.d(TAG, "不是防疫员");
            IsSelfWrite = AppConstants.IsSelfWrite.ZZMY;
            //TODO:这个判断资助面议
            binding.queryTv.setVisibility(View.GONE);
            Mobile = UserDataSP.getInstance().getUserInfo().Result.mobile;
        }

       //沉浸式状态栏
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();
        binding.titlebarLeft.setOnClickListener(v -> finish());

        pageItemsList = new ArrayList<>();

        mDrawer = findViewById(R.id.navdrawer_immune_xdr);

        binding.queryTv.setOnClickListener(this);

        binding.addXdrTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddXdrWebActivity.start(ImmuneXdrListActivity.this);
            }
        });


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        immuneXdrListAdapter = new ImmuneXdrListAdapter(R.layout.item_immune_xdr, this);
        binding.recyclerView.setAdapter(immuneXdrListAdapter);
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
        immuneXdrListAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                if (mType == 1) {
                    RxBus.getInstance().post("IMMUNE_XDR", immuneXdrListAdapter.getDataList().get(position));
                    finish();
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });

        binding.navdrawerImmuneXdr.regionTv.setOnClickListener(v -> SelectAreaActivity.start(ImmuneXdrListActivity.this));

        binding.navdrawerImmuneXdr.sureBtn.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(mDrawer);
            binding.refreshLayout.setVisibility(View.VISIBLE);
            binding.notDataRl.setVisibility(View.GONE);
            binding.refreshLayout.setEnableRefresh(true);
            binding.refreshLayout.autoRefresh(500);
            binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
            smoothMoveToPosition(binding.recyclerView, 0);

        });

        binding.navdrawerImmuneXdr.noBtn.setOnClickListener(v -> binding.drawerLayout.closeDrawer(mDrawer));


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


    }


    @Override
    protected void OnEventMainThread() {
        super.OnEventMainThread();
        mRxManager.on("CHOOSE_REGION", o -> {
            int RegionIDInfo = (int) o;
            LogUtil.d(TAG, "回传回来的区划数据" + RegionIDInfo);
            TRegion tr = DaoManager.queryRegionDao().queryBuilder()
                    .where(TRegionDao.Properties.Region_id.eq(RegionIDInfo))
                    .list().get(0);
            LogUtil.d(TAG, "回传回来的区划数据" + tr.toString());
            binding.navdrawerImmuneXdr.regionTv.setText(tr.getRegion_shortname());
            RegionID = (int) tr.getRegion_id();
            RegionLevel = (int) tr.getRegion_level();
        });
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


    private void getInfo() {
        HttpRequest.getImmuneXdr(ImmuneXdrListActivity.this, RegionID, RegionLevel, page, size, IsSelfWrite, binding.navdrawerImmuneXdr.xdrPersonTv.getText().toString(), IsSelfWrite != 1010 ? Mobile : binding.navdrawerImmuneXdr.xdrPhoneTv.getText().toString(), false,new CallBackLis<ImmuneXdrBean>() {
            @Override
            public void onSuccess(String method, ImmuneXdrBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    if (content.result.pageItems.size() > 0) {
                        binding.refreshLayout.setVisibility(View.VISIBLE);
                        binding.notDataRl.setVisibility(View.GONE);
                        binding.titlebarMiddle.setText("畜主" + "(" + content.result.totalCount + "位" + ")");
                        if (page == 0) {
                            pageItemsList = content.result.pageItems;
                            immuneXdrListAdapter.refreshDataList(content.result.pageItems);
                            if (immuneXdrListAdapter.getDataList().size() == content.result.totalCount) {
                                binding.refreshLayout.finishRefreshWithNoMoreData();//将不会再次触发加载更多事件
                            } else if (immuneXdrListAdapter.getDataList().size() < content.result.totalCount) {
                                binding.refreshLayout.finishRefresh();
                            }
                        } else {
                            if (immuneXdrListAdapter.getDataList().size() == content.result.totalCount) {
                                immuneXdrListAdapter.addDataList(content.result.pageItems);
                                immuneXdrListAdapter.notifyDataSetChanged();
                                binding.refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                                LogUtil.d(TAG, "====数据停止");
                            } else {
                                immuneXdrListAdapter.addDataList(content.result.pageItems);
                                immuneXdrListAdapter.notifyDataSetChanged();
                                if (immuneXdrListAdapter.getDataList().size() == content.result.totalCount) {
                                    binding.refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                                    return;
                                }
                                binding.refreshLayout.finishLoadMore();
                                LogUtil.d(TAG, "====数据加载");
                            }
                        }
                    } else {
                        binding.refreshLayout.setEnableRefresh(false);
                        binding.refreshLayout.finishRefresh();
                        binding.refreshLayout.setVisibility(View.GONE);
                        binding.notDataRl.setVisibility(View.VISIBLE);
                        Objects.requireNonNull(RxToast.info(ImmuneXdrListActivity.this, "当前暂无数据"));
                        binding.titlebarMiddle.setText("畜主" + "(" + 0 + "位" + ")");
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
        if (v == binding.queryTv) {
            binding.navdrawerImmuneXdr.xdrPersonTv.setText("");
            binding.navdrawerImmuneXdr.xdrPhoneTv.setText("");
            showDrawer();

        }
    }
}
