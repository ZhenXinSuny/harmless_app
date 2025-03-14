package com.agridata.cdzhdj.activity.harmless.regionAdmin.collect_record;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.collected.CollectedDetailActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.region.SelectAreaActivity;
import com.agridata.cdzhdj.activity.adapter.CollectedAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.dao.TRegionDao;
import com.agridata.cdzhdj.data.AnimalBean;
import com.agridata.cdzhdj.data.CollectedBean;
import com.agridata.cdzhdj.data.HarmlessAnimalBean;
import com.agridata.cdzhdj.data.StatusBean;
import com.agridata.cdzhdj.data.TRegion;
import com.agridata.cdzhdj.databinding.ActivityCollectRecordBinding;
import com.agridata.cdzhdj.dbutils.DaoManager;
import com.agridata.cdzhdj.utils.TimeDialogUtils;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
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

public class CollectRecordActivity extends BaseActivity <ActivityCollectRecordBinding> implements View.OnClickListener {
    private final static String TAG = "ToBeCollectedActivity------》";

    private int regionID;
    private int regionLevel;
    private TRegion tRegion;
    private CollectedAdapter collectedAdapter;

    private int page = 0;
    private int size = 10;
    private List<CollectedBean.Result.PageItems> pageItemsList;
    private View mDrawer;
    private boolean flag;

    private List<AnimalBean> animalBeanList;
    private int  animalID=-1;
    private int  statusId = 1;

    private OptionsPickerView pvCustomOptions, StatuspvCustomOptions;
    private List<StatusBean> statusBeanList;

    public static void start(Context context) {
        Intent intent = new Intent(context, CollectRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ActivityCollectRecordBinding getBinding() {
        return ActivityCollectRecordBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setOnClickListener(v -> finish());
        regionID = UserDataSP.getInstance().getUserInfo().Result.dependency.Dep_AgencyMID.Region.id;
        LogUtil.d(TAG, "获取本地区化" + regionID);
        tRegion = DaoManager.queryRegionDao().queryBuilder().where(TRegionDao.Properties.Region_id.eq(regionID)).list().get(0);
        LogUtil.d(TAG, "获取本地区化" + tRegion.toString());

        binding.navdrawerRk.regionTv.setText(tRegion.getRegion_shortname());
        regionLevel = (int) tRegion.getRegion_level();

        pageItemsList = new ArrayList<>();
        mDrawer = findViewById(R.id.navdrawer_rk);
        binding.queryTv.setOnClickListener(this);





        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        collectedAdapter = new CollectedAdapter(R.layout.item_collected, this,666);
        binding.recyclerView.setAdapter(collectedAdapter);
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
        collectedAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                CollectedBean.Result.PageItems data = collectedAdapter.getData(position);
                CollectedDetailActivity.start(CollectRecordActivity.this,data.mid,"1");
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



        binding.navdrawerRk.startTimeTv.setText(currentTime);//当前时间
        binding.navdrawerRk.endTimeTv.setText(currentTime);//当前时间的前30天

        binding.navdrawerRk.sureBtn.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(mDrawer);
            binding.refreshLayout.setVisibility(View.VISIBLE);
            binding.notDataRl.setVisibility(View.GONE);
            binding.refreshLayout.setEnableRefresh(true);
            binding.refreshLayout.autoRefresh(500);
            binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
            smoothMoveToPosition(binding.recyclerView,0);

        });

        binding.navdrawerRk.noBtn.setOnClickListener(v -> binding.drawerLayout.closeDrawer(mDrawer));

        binding.navdrawerRk.startTimeTv.setOnClickListener(v -> {
            TimeDialogUtils.initTimePicker1(this, binding.navdrawerRk.startTimeTv);
            TimeDialogUtils.pvTime.show();
        });
        binding.navdrawerRk.endTimeTv.setOnClickListener(v -> {
            TimeDialogUtils.initTimePicker2(this, binding.navdrawerRk.endTimeTv);
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
        statusBeanList = new ArrayList<>();
        binding.navdrawerRk.animalTypeEt.setOnClickListener(this);//动物种类
        binding.navdrawerRk.collectStatusTv.setOnClickListener(this);//审核状态
        binding.navdrawerRk.regionTv.setOnClickListener(this);//区划
        binding.navdrawerRk.animalTypeEt.setText("全部");
        binding.navdrawerRk.collectStatusTv.setText("已审核");

        getHarAnimalInfo();//获取动物
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
        HttpRequest.getCollected(CollectRecordActivity.this,  binding.navdrawerRk.startTimeTv.getText().toString() +" "+"00:00:00", binding.navdrawerRk.endTimeTv.getText().toString() +" "+"23:59:59", regionID, regionLevel, page, size,
                2,statusId, String.valueOf(animalID),binding.navdrawerRk.collectNumTv.getText().toString(),binding.navdrawerRk.collectPersonTv.getText().toString(),new CallBackLis<CollectedBean>() {
            @Override
            public void onSuccess(String method, CollectedBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    if (content.result.pageItems.size() > 0) {
                        binding.refreshLayout.setVisibility(View.VISIBLE);
                        binding.notDataRl.setVisibility(View.GONE);
                        binding.titlebarMiddle.setText("收集记录" + "(" + content.result.totalCount + "单" +")" );
                        if (page == 0) {
                            pageItemsList = content.result.pageItems;
                            collectedAdapter.refreshDataList(content.result.pageItems);
                            if (collectedAdapter.getDataList().size() == content.result.totalCount) {
                                binding.refreshLayout.finishRefreshWithNoMoreData();//将不会再次触发加载更多事件
                            } else if (collectedAdapter.getDataList().size() < content.result.totalCount) {
                                binding.refreshLayout.finishRefresh();
                            }
                        } else {
                            if (collectedAdapter.getDataList().size() == content.result.totalCount) {
                                collectedAdapter.addDataList(content.result.pageItems);
                                collectedAdapter.notifyDataSetChanged();
                                binding.refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                                LogUtil.d(TAG,"====数据停止");
                            } else {
                                collectedAdapter.addDataList(content.result.pageItems);
                                collectedAdapter.notifyDataSetChanged();
                                if (collectedAdapter.getDataList().size() == content.result.totalCount){
                                    binding.refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                                    return;
                                }
                                binding.refreshLayout.finishLoadMore();
                                LogUtil.d(TAG,"====数据加载");
                            }
                        }
                    }else {
                        binding.refreshLayout.setEnableRefresh(false);
                        binding.refreshLayout.finishRefresh();
                        binding.refreshLayout.setVisibility(View.GONE);
                        binding.notDataRl.setVisibility(View.VISIBLE);

                        Objects.requireNonNull(RxToast.info(CollectRecordActivity.this,"当前暂无数据"));
                        binding.titlebarMiddle.setText("收集记录" + "(" + 0 + "单" +")" );
                    }

                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
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
            binding.navdrawerRk.regionTv.setText(tr.getRegion_shortname());
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
            showDrawer();
        }else  if (v==binding.navdrawerRk.animalTypeEt){
            showAnimalDialog();
        }else  if (v==binding.navdrawerRk.collectStatusTv){
            showStatusDialog();
        }else if (v==binding.navdrawerRk.regionTv){
              SelectAreaActivity.start(CollectRecordActivity.this);
        }
    }

    private void showAnimalDialog() {
        List<String> listString = new ArrayList<>();
        for (AnimalBean animalBean : animalBeanList) {
            listString.add(animalBean.AnimalName);
        }
        pvCustomOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.navdrawerRk.animalTypeEt.setText(animalBeanList.get(options1).AnimalName);
            animalID = Integer.parseInt(animalBeanList.get(options1).ID);
        })
                .setLayoutRes(R.layout.custom_dongwu_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    tvSubmit.setOnClickListener(v1 -> {
                        pvCustomOptions.returnData();
                        pvCustomOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> pvCustomOptions.dismiss());

                })
                .isDialog(false)
                .setSelectOptions(animalID)
                .setContentTextSize(18)//滚轮文字大小
                .setLineSpacingMultiplier(2.8f)
                .build();
        pvCustomOptions.setPicker(listString);//添加数据
        pvCustomOptions.show();
    }

    private void showStatusDialog() {
        statusBeanList.clear();
        StatusBean  statusBean1 = new StatusBean(0,"待审核");
        StatusBean  statusBean2 = new StatusBean(1,"已审核");
        StatusBean  statusBean3 = new StatusBean(2,"驳回");
        statusBeanList.add(statusBean1);
        statusBeanList.add(statusBean2);
        statusBeanList.add(statusBean3);

        List<String> listString = new ArrayList<>();
        for (StatusBean statusBeanItem : statusBeanList) {
            listString.add(statusBeanItem.StatusName);
        }
        StatuspvCustomOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.navdrawerRk.collectStatusTv.setText(statusBeanList.get(options1).StatusName);
            statusId = statusBeanList.get(options1).Status;
            if (statusId==0){
                binding.navdrawerRk.timesLl.setVisibility(View.VISIBLE);
                binding.navdrawerRk.timesTipsTv.setText("收集时间:");
            }else if (statusId==1){
                binding.navdrawerRk.timesLl.setVisibility(View.VISIBLE);
                binding.navdrawerRk.timesTipsTv.setText("审核时间:");
            }else {
                binding.navdrawerRk.timesLl.setVisibility(View.VISIBLE);
                binding.navdrawerRk.timesTipsTv.setText("驳回时间:");
            }
        })
                .setLayoutRes(R.layout.custom_status_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    tvSubmit.setOnClickListener(v1 -> {
                        StatuspvCustomOptions.returnData();
                        StatuspvCustomOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> StatuspvCustomOptions.dismiss());

                })
                .isDialog(false)
                .setSelectOptions(statusId)
                .setLineSpacingMultiplier(2.8f)
                .setContentTextSize(18)//滚轮文字大小
                .build();
        StatuspvCustomOptions.setPicker(listString);//添加数据
        StatuspvCustomOptions.show();
    }
    /**
     * 获取动物
     */
    private void getHarAnimalInfo() {
        HttpRequest.getHarAnimal(CollectRecordActivity.this, new CallBackLis<HarmlessAnimalBean>() {
            @Override
            public void onSuccess(String method, HarmlessAnimalBean content) {
                LogUtil.d(TAG, "获取动物" + content.toString());
                animalBeanList = new ArrayList<>();
                for (HarmlessAnimalBean.ResultAnimalBean resultBean : content.Result) {
                    animalBeanList.add(new AnimalBean(String.valueOf(resultBean.AnimalID), resultBean.AnimalName));
                }

                AnimalBean  animalBean = new AnimalBean("-1","全部");
                animalBeanList.add(0,animalBean);
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }
}
