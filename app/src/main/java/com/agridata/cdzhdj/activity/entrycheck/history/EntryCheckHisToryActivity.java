package com.agridata.cdzhdj.activity.entrycheck.history;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.entrycheck.loglist.LogEntryCheckActivity;
import com.agridata.cdzhdj.activity.entrycheck.revise.ReviseEntryCheckActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckHistoryData;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckLogData;
import com.agridata.cdzhdj.data.entrycheck.QuarantineDeclareData;
import com.agridata.cdzhdj.databinding.ActivityEntryCheckHistoryBinding;
import com.agridata.cdzhdj.utils.TimeDialogUtils;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.lzx.utils.RxToast;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-11-20 10:22.
 * @Description :入场查验历史记录
 */
public class EntryCheckHisToryActivity extends BaseActivity<ActivityEntryCheckHistoryBinding> implements View.OnClickListener, mOnEntryCheckListener {
    private final static String TAG = "EntryCheckHisToryActivity------》";

    private EntryCheckHisToryAdapter entryCheckHisToryAdapter;

    private int page = 0;
    private int size = 10;
    private List<EntryCheckHistoryData.ResultDTO.PageItemsDTO> pageItemsList;
    private View mDrawer;

    private int mCheckResult = -1;
    private String SlaughterID;
    private String SlaughterName;

    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context, String SlaughterID, String SlaughterName) {
        Intent intent = new Intent(context, EntryCheckHisToryActivity.class);
        Bundle data = new Bundle();
        data.putSerializable("SlaughterID", SlaughterID);
        data.putSerializable("SlaughterName", SlaughterName);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    private void getArguments() {
        Bundle bundle = getIntent().getBundleExtra("data");
        SlaughterID = bundle.getString("SlaughterID");
        SlaughterName = bundle.getString("SlaughterName");
    }

    @Override
    protected ActivityEntryCheckHistoryBinding getBinding() {
        return ActivityEntryCheckHistoryBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        getArguments();
        binding.titlebarLeft.setOnClickListener(v -> finish());
        pageItemsList = new ArrayList<>();
        mDrawer = findViewById(R.id.navdrawer_entry_check);
        binding.queryTv.setOnClickListener(this);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        entryCheckHisToryAdapter = new EntryCheckHisToryAdapter(R.layout.item_entry_check_history, this);
        entryCheckHisToryAdapter.setOnEntryCheckListener(this);
        binding.recyclerView.setAdapter(entryCheckHisToryAdapter);
        binding.refreshLayout.autoRefresh();
        binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);

        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            getInfo();
        });

        //加载的监听事件
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getInfo();
        });
        entryCheckHisToryAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                EntryCheckHisToryDetailsActivity.start(EntryCheckHisToryActivity.this, entryCheckHisToryAdapter.getDataList().get(position).mid);
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });


        binding.navdrawerEntryCheck.sureBtn.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(mDrawer);
            binding.refreshLayout.setVisibility(View.VISIBLE);
            binding.notDataRl.setVisibility(View.GONE);
            binding.refreshLayout.setEnableRefresh(true);
            binding.refreshLayout.autoRefresh(500);
            binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
            smoothMoveToPosition(binding.recyclerView, 0);

        });

        binding.navdrawerEntryCheck.noBtn.setOnClickListener(v -> binding.drawerLayout.closeDrawer(mDrawer));


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


        binding.navdrawerEntryCheck.startTimeTv.setOnClickListener(v -> {
            TimeDialogUtils.initTimePicker1(this, binding.navdrawerEntryCheck.startTimeTv);
            TimeDialogUtils.pvTime.show();
        });
        binding.navdrawerEntryCheck.endTimeTv.setOnClickListener(v -> {
            TimeDialogUtils.initTimePicker2(this, binding.navdrawerEntryCheck.endTimeTv);
            TimeDialogUtils.pvTime1.show();
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

    public void onRadioButtonClicked(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.navdrawerEntryCheck.qualifiedYesRb) {
            if (isChecked) {
                mCheckResult = 1;
            }
        } else if (view == binding.navdrawerEntryCheck.qualifiedNoRb) {
            if (isChecked) {
                mCheckResult = 2;
            }
        }
    }

    private void getInfo() {
        HttpRequest.getHistoryEntryCheck(EntryCheckHisToryActivity.this, page, size, String.valueOf(mCheckResult), binding.navdrawerEntryCheck.startTimeTv.getText().toString(), binding.navdrawerEntryCheck.endTimeTv.getText().toString(), binding.navdrawerEntryCheck.searchQuarantineEt.getText().toString(), SlaughterID, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, EntryCheckHistoryData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    if (!content.result.pageItems.isEmpty()) {
                        binding.refreshLayout.setVisibility(View.VISIBLE);
                        binding.notDataRl.setVisibility(View.GONE);
                        if (page == 0) {
                            pageItemsList = content.result.pageItems;
                            entryCheckHisToryAdapter.refreshDataList(content.result.pageItems);
                            if (entryCheckHisToryAdapter.getDataList().size() == content.result.totalCount) {
                                binding.refreshLayout.finishRefreshWithNoMoreData();//将不会再次触发加载更多事件
                            } else if (entryCheckHisToryAdapter.getDataList().size() < content.result.totalCount) {
                                binding.refreshLayout.finishRefresh();
                            }
                        } else {
                            if (entryCheckHisToryAdapter.getDataList().size() == content.result.totalCount) {
                                entryCheckHisToryAdapter.addDataList(content.result.pageItems);
                                entryCheckHisToryAdapter.notifyDataSetChanged();
                                binding.refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                                LogUtil.d(TAG, "====数据停止");
                            } else {
                                entryCheckHisToryAdapter.addDataList(content.result.pageItems);
                                entryCheckHisToryAdapter.notifyDataSetChanged();
                                if (entryCheckHisToryAdapter.getDataList().size() == content.result.totalCount) {
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
                        Objects.requireNonNull(RxToast.info(EntryCheckHisToryActivity.this, "当前暂无数据"));
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
            binding.navdrawerEntryCheck.searchQuarantineEt.setText("");
            binding.navdrawerEntryCheck.startTimeTv.setText("");
            binding.navdrawerEntryCheck.endTimeTv.setText("");
            showDrawer();
        }
    }

    /**
     * 查看日志
     *
     * @param position
     */
    @Override
    public void mOnViewLog(int position) {
        EntryCheckHistoryData.ResultDTO.PageItemsDTO data = entryCheckHisToryAdapter.getData(position);
        HttpRequest.getEntryCheckLog(EntryCheckHisToryActivity.this, data.mid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, EntryCheckLogData entryCheckLogData) {
                if (entryCheckLogData.errorCode == 0) {
                    if (!entryCheckLogData.result.isEmpty()) {
                        LogEntryCheckActivity.Companion.start(EntryCheckHisToryActivity.this, data.mid);
                    } else {
                        Objects.requireNonNull(RxToast.warning(EntryCheckHisToryActivity.this, getString(R.string.the_current_data_is_not_modified)));
                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(EntryCheckHisToryActivity.this, error));

            }
        });


    }

    /**
     * 修改
     *
     * @param position
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void mOnViewModify(int position) {
        EntryCheckHistoryData.ResultDTO.PageItemsDTO data = entryCheckHisToryAdapter.getData(position);
        String checkTime = data.checkTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        LocalDateTime givenTime = LocalDateTime.parse(checkTime, formatter);
        LocalDateTime timePlusOneHour = givenTime.plusHours(1);
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.systemDefault());
        boolean isCurrentTimeAfter = currentTime.isAfter(timePlusOneHour);
        if (isCurrentTimeAfter) {
            Objects.requireNonNull(RxToast.warning(EntryCheckHisToryActivity.this, getString(R.string.time_has_exceeded_cannot_be_modified)));
            return;
        }
        HttpRequest.getQuarantineDeclare(EntryCheckHisToryActivity.this, data.slaughterHouseID, checkTime, timePlusOneHour.format(formatter), new CallBackLis<>() {
            @Override
            public void onSuccess(String method, QuarantineDeclareData quarantineDeclareData) {
                if (quarantineDeclareData.code == 0) {
                    if (quarantineDeclareData.data.count > 0) {
                        Objects.requireNonNull(RxToast.warning(EntryCheckHisToryActivity.this, getString(R.string.the_slaughterhouse_has_carried_out)));
                    } else {
                        ReviseEntryCheckActivity.start(EntryCheckHisToryActivity.this, data.mid);
                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(EntryCheckHisToryActivity.this, error));
            }
        });
    }
}
