package com.agridata.cdzhdj.activity.epidemic.immune.query;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.region.SelectAreaActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.dao.TRegionDao;
import com.agridata.cdzhdj.data.ImmuneXdrBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.QueryImmuneBean;
import com.agridata.cdzhdj.data.TRegion;
import com.agridata.cdzhdj.databinding.ActivityQueryImmuneBinding;
import com.agridata.cdzhdj.dbutils.DaoManager;
import com.agridata.cdzhdj.utils.AppConstants;
import com.agridata.cdzhdj.utils.MoveToPosUtils;
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
 * @Date : on 2022-12-23 10:57.
 * @Description :描述 防疫信息查询
 */
public class QueryImmuneActivity extends BaseActivity<ActivityQueryImmuneBinding> implements View.OnClickListener {

    private int IsSelfWrite;
    private View mDrawer;

    private static String TAG = "QueryImmuneActivity----->";


    private TRegion tRegion;
    private QueryImmuneAdapter queryImmuneAdapter;

    private int page = 0;
    private int size = 10;
    private List<QueryImmuneBean.Result.PageItems> pageItemsList;
    private int RegionID, RegionLevel;
    private String Mobile;
    private String XdrMid;
    private String AnimalID;

    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, QueryImmuneActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityQueryImmuneBinding getBinding() {
        return ActivityQueryImmuneBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        List<String> roleList = new ArrayList<>();

        for (LoginData.ResultBean.RolesBean role : UserDataSP.getInstance().getUserInfo().Result.roles) {
            roleList.add(role.id);
        }
        if (roleList.contains(AppConstants.FangYiYuanID) || roleList.contains(AppConstants.XZFYMaster)
                || roleList.contains(AppConstants.XianMaster)||roleList.contains(AppConstants.ShiMaster)) {
            binding.queryTv.setVisibility(View.VISIBLE);
            int regionID = UserDataSP.getInstance().getUserInfo().Result.dependency.Dep_AgencyMID.Region.id;
            TRegion tRegion = DaoManager.queryRegionDao().queryBuilder().where(TRegionDao.Properties.Region_id.eq(regionID)).list().get(0);
            IsSelfWrite = AppConstants.IsSelfWrite.FYYMY;
            RegionID = regionID;
            RegionLevel = (int) tRegion.getRegion_level();
            getInfo();
        } else {
            IsSelfWrite = AppConstants.IsSelfWrite.ZZMY;
            Mobile = UserDataSP.getInstance().getUserInfo().Result.mobile;
            //TODO:这个判断资助面议
            binding.queryTv.setVisibility(View.GONE);
            getXdrForMe();
        }


        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setOnClickListener(v -> finish());

        pageItemsList = new ArrayList<>();

        mDrawer = findViewById(R.id.navdrawer_immune_xdr);

        binding.queryTv.setOnClickListener(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        queryImmuneAdapter = new QueryImmuneAdapter(R.layout.item_history_immune, this);
        binding.recyclerView.setAdapter(queryImmuneAdapter);
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
        queryImmuneAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                QueryImmuneDetailsActivity.start(QueryImmuneActivity.this, queryImmuneAdapter.getData(position));
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });

        binding.navdrawerImmuneXdr.regionTv.setOnClickListener(v -> SelectAreaActivity.start(QueryImmuneActivity.this));
        binding.navdrawerImmuneXdr.sureBtn.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(mDrawer);
            binding.refreshLayout.setVisibility(View.VISIBLE);
            binding.notDataRl.setVisibility(View.GONE);
            binding.refreshLayout.setEnableRefresh(true);
            binding.refreshLayout.autoRefresh(500);
            binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
            MoveToPosUtils.smoothMoveToPosition(binding.recyclerView, 0);

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
    @Override
    protected void initDatas() {

    }

    private void getInfo() {
        HttpRequest.getHistoryImmune(QueryImmuneActivity.this, page, size, XdrMid, AnimalID,binding.navdrawerImmuneXdr.xdrPersonTv.getText().toString(), RegionLevel, RegionID, IsSelfWrite, new CallBackLis<QueryImmuneBean>() {
            @Override
            public void onSuccess(String method, QueryImmuneBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    if (content.result.pageItems.size() > 0) {
                        binding.refreshLayout.setVisibility(View.VISIBLE);
                        binding.notDataRl.setVisibility(View.GONE);
                        if (page == 0) {
                            pageItemsList = content.result.pageItems;
                            queryImmuneAdapter.refreshDataList(content.result.pageItems);
                            if (queryImmuneAdapter.getDataList().size() == content.result.totalCount) {
                                binding.refreshLayout.finishRefreshWithNoMoreData();//将不会再次触发加载更多事件
                            } else if (queryImmuneAdapter.getDataList().size() < content.result.totalCount) {
                                binding.refreshLayout.finishRefresh();
                            }
                        } else {
                            if (queryImmuneAdapter.getDataList().size() == content.result.totalCount) {
                                queryImmuneAdapter.addDataList(content.result.pageItems);
                                queryImmuneAdapter.notifyDataSetChanged();
                                binding.refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                                LogUtil.d("lzx------>", "====数据停止");
                            } else {
                                queryImmuneAdapter.addDataList(content.result.pageItems);
                                queryImmuneAdapter.notifyDataSetChanged();
                                if (queryImmuneAdapter.getDataList().size() == content.result.totalCount) {
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
                        Objects.requireNonNull(RxToast.info(QueryImmuneActivity.this, "当前暂无数据"));
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
            showDrawer();
        }
    }


    private void getXdrForMe() {
        HttpRequest.getImmuneXdr(QueryImmuneActivity.this, RegionID, RegionLevel, page, 100, IsSelfWrite, binding.navdrawerImmuneXdr.xdrPersonTv.getText().toString(), IsSelfWrite != 1010 ? Mobile : binding.navdrawerImmuneXdr.xdrPhoneTv.getText().toString(), false,new CallBackLis<ImmuneXdrBean>() {
            @Override
            public void onSuccess(String method, ImmuneXdrBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    if (content.result.pageItems.size() > 0) {
                        ImmuneXdrBean.Result.PageItems pageItems = content.result.pageItems.get(0);
                        XdrMid = pageItems.mid;
                        AnimalID = pageItems.animalVariety.get(0).id;
                        getInfo();

                    } else {
                        binding.notDataRl.setVisibility(View.VISIBLE);
                        RxToast.warning(QueryImmuneActivity.this, "您当前暂无备案...");
                    }

                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }
}
