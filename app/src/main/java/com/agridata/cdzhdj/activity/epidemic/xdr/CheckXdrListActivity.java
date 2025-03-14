package com.agridata.cdzhdj.activity.epidemic.xdr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.region.SelectAreaActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.dao.TRegionDao;
import com.agridata.cdzhdj.data.CheckXdrBean;
import com.agridata.cdzhdj.data.ImmuneXdrBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.TRegion;
import com.agridata.cdzhdj.databinding.ActivityImmuneXdrListBinding;
import com.agridata.cdzhdj.dbutils.DaoManager;
import com.agridata.cdzhdj.utils.AppConstants;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
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
public class CheckXdrListActivity extends BaseActivity<ActivityImmuneXdrListBinding> implements View.OnClickListener, OnXdrCheckListener {
    private final static String TAG = "CheckXdrListActivity------》";
    private int regionID;
    private TRegion tRegion;
    private ImmuneCheckXdrListAdapter immuneXdrListAdapter;
    private int page = 0;
    private int size = 10;
    private List<ImmuneXdrBean.Result.PageItems> pageItemsList;
    private View mDrawer;
    private boolean flag;
    private int RegionID, RegionLevel;
    private int IsSelfWrite;
    private String Mobile;
    private CheckXdrBean checkXdrBean;
    private CustomLoadingDialog mLoadingDialog;
    private String mType;

    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context, String type) {
        Intent intent = new Intent(context, CheckXdrListActivity.class);
        Bundle data = new Bundle();
        data.putSerializable("type", type);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }



    private void getArguments() {
        Bundle bundle = getIntent().getBundleExtra("data");
        mType = bundle.getString("type");

    }

    @Override
    protected ActivityImmuneXdrListBinding getBinding() {
        return ActivityImmuneXdrListBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        getArguments();
        List<String> roleList = new ArrayList<>();
        for (LoginData.ResultBean.RolesBean role : UserDataSP.getInstance().getUserInfo().Result.roles) {
            roleList.add(role.id);
        }
        if (roleList.contains(AppConstants.FangYiYuanID) || roleList.contains(AppConstants.XZFYMaster) || roleList.contains(AppConstants.XianMaster) || roleList.contains(AppConstants.ShiMaster) || roleList.contains(AppConstants.WHHXCJDY)) {
            binding.queryTv.setVisibility(View.VISIBLE);
            LogUtil.d(TAG, "防疫员");
            int regionID = UserDataSP.getInstance().getUserInfo().Result.dependency.Dep_AgencyMID.Region.id;
            TRegion tRegion = DaoManager.queryRegionDao().queryBuilder().where(TRegionDao.Properties.Region_id.eq(regionID)).list().get(0);
            RegionID = regionID;
            RegionLevel = (int) tRegion.getRegion_level();
            IsSelfWrite = AppConstants.IsSelfWrite.FYYMY;
            binding.navdrawerImmuneXdr.regionTv.setText(tRegion.getRegion_shortname());
            if ("whh".equals(mType)){
                binding.addXdrTv.setVisibility(View.GONE);
            }else {
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
        binding.addXdrTv.setOnClickListener(v -> AddXdrWebActivity.start(CheckXdrListActivity.this));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        immuneXdrListAdapter = new ImmuneCheckXdrListAdapter(R.layout.item_immune_xdr_check, this);
        immuneXdrListAdapter.setOnXdrCheckListener(this);
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

        binding.navdrawerImmuneXdr.regionTv.setOnClickListener(v -> SelectAreaActivity.start(CheckXdrListActivity.this));

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
            TRegion tr = DaoManager.queryRegionDao().queryBuilder().where(TRegionDao.Properties.Region_id.eq(RegionIDInfo)).list().get(0);
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
        HttpRequest.getImmuneXdr(CheckXdrListActivity.this, RegionID, RegionLevel, page, size, IsSelfWrite, binding.navdrawerImmuneXdr.xdrPersonTv.getText().toString(), IsSelfWrite != 1010 ? Mobile : binding.navdrawerImmuneXdr.xdrPhoneTv.getText().toString(), true, new CallBackLis<ImmuneXdrBean>() {
            @Override
            public void onSuccess(String method, ImmuneXdrBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    if (!content.result.pageItems.isEmpty()) {
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
                        Objects.requireNonNull(RxToast.info(CheckXdrListActivity.this, "当前暂无数据"));
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

    /**
     * 进行审核操作
     *
     * @param mid
     * @param pos
     */
    @Override
    public void check(String mid, int pos) {
        showCheckXdrDialog(mid, 1);
    }

    @Override
    public void reject(String mid, int pos) {
        showCheckXdrDialog(mid, 2);
    }

    private void showCheckXdrDialog(String mid, int Type) {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        AlertDialog exitDialogLoginOut = new AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView title_tv = view.findViewById(R.id.title_tv);
        title_tv.setText("审核");
        if (Type == 1) {
            contentTv.setText("确认该条备案通过审核吗？");
        } else {
            contentTv.setText("确认该条备案进行驳回吗？");
        }


        cancelTv.setOnClickListener(view12 -> exitDialogLoginOut.dismiss());
        confirmTv.setOnClickListener(v -> {
            exitDialogLoginOut.dismiss();
            checkXdrBean = new CheckXdrBean();
            checkXdrBean.Mid = mid;
            if (Type == 1) {
                checkXdrBean.ExamineStatus = "940";
            } else {
                checkXdrBean.ExamineStatus = "942";
            }
            checkXdrStatus();
        });
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }


    private void checkXdrStatus() {
        showLoading("正在审核中...");
        HttpRequest.checkXdrStatus(CheckXdrListActivity.this, checkXdrBean, new CallBackLis<StatusData>() {
            @Override
            public void onSuccess(String method, StatusData content) {
                hideLoading();
                if (content.ErrorCode == 0) {
                    binding.refreshLayout.autoRefresh(500);
                    Objects.requireNonNull(RxToast.success(CheckXdrListActivity.this, "审核成功"));
                } else {
                    Objects.requireNonNull(RxToast.error(CheckXdrListActivity.this, content.Message));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull(RxToast.error(CheckXdrListActivity.this, error));
            }
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
