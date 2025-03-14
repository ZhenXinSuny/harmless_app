package com.agridata.cdzhdj.activity.farm.pigbreedinginputs.veterinarydrug;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.pigbreed.feed.FeedStorageListData;
import com.agridata.cdzhdj.databinding.ActivityVeterinaryDrugBinding;
import com.agridata.cdzhdj.utils.TimeDialogUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.lzx.utils.RxToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-05-05 11:11.
 * @Description :描述
 */
public class VeterinaryDrugActivity extends BaseActivity<ActivityVeterinaryDrugBinding> implements View.OnClickListener{

    private final static String TAG = "VeterinaryDrugActivity------》";
    private CustomLoadingDialog mLoadingDialog;


    private String userMid;
    private View mDrawer;
    private String day;
    private String currentTime;
    private String userId;
    private VeterinaryDrugAdapter veterinaryDrugAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, VeterinaryDrugActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityVeterinaryDrugBinding getBinding() {
        return ActivityVeterinaryDrugBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        mDrawer = findViewById(R.id.navdrawer);
        binding.titlebarLeft.setOnClickListener(v -> finish());
        userId = UserDataSP.getInstance().getUserInfo().Result.userId;
        userMid = UserDataSP.getInstance().getUserInfo().Result.dependency.Mid;
        binding.queryTv.setOnClickListener(v -> {
            showDrawer();
        });



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        Date nowDate = calendar.getTime();
        currentTime = sdf.format(nowDate);



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        Calendar calendarStrat = Calendar.getInstance();
        calendarStrat.setTime(new Date());
        calendarStrat.add(Calendar.DAY_OF_MONTH,-7);
        day = simpleDateFormat.format(calendarStrat.getTime());



        binding.navdrawer.startTimeTv.setText(day);//当前时间
        binding.navdrawer.endTimeTv.setText(currentTime);//当前时间的前30天


        binding.navdrawer.sureBtn.setOnClickListener(v -> {
            LogUtil.d(TAG,"加载数据");
            binding.drawerLayout.closeDrawer(mDrawer);
            getFeedStorageListData();
        });

        binding.navdrawer.noBtn.setOnClickListener(v -> binding.drawerLayout.closeDrawer(mDrawer));

        binding.navdrawer.startTimeTv.setOnClickListener(v -> {
            TimeDialogUtils.initTimePicker3(this, binding.navdrawer.startTimeTv);
            TimeDialogUtils.pvTime3.show();
        });
        binding.navdrawer.endTimeTv.setOnClickListener(v -> {
            TimeDialogUtils.initTimePicker2(this, binding.navdrawer.endTimeTv);
            TimeDialogUtils.pvTime1.show();
        });
        binding.addFab.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(mDrawer);
            AddVeterinaryDrugActivity.start(VeterinaryDrugActivity.this);
        });

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

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(VeterinaryDrugActivity.this, LinearLayoutManager.VERTICAL, false));
        veterinaryDrugAdapter = new VeterinaryDrugAdapter(R.layout.item_veterinary_drug_list, VeterinaryDrugActivity.this);
        binding.recyclerview.setAdapter(veterinaryDrugAdapter);

        veterinaryDrugAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                VeterinaryDrugDetailsActivity.start(VeterinaryDrugActivity.this,veterinaryDrugAdapter.getData(position).mid);
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });

    }

    @Override
    protected void initDatas() {

    }
    @Override
    protected void OnEventMainThread() {

    }
    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getFeedStorageListData();
    }

    private void  getFeedStorageListData(){
        showLoading("正在加载中...");
        HttpRequest.getFeedStorageList(VeterinaryDrugActivity.this, userId,2, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, FeedStorageListData content) {
                hideLoading();
                if (content.errorCode==0){
                    if (content.result.size()>0){
                        binding.notDataRl.setVisibility(View.GONE);
                        veterinaryDrugAdapter.refreshDataList(content.result);
                    }
                }else {
                    Objects.requireNonNull( RxToast.error(VeterinaryDrugActivity.this,content.message));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull( RxToast.error(VeterinaryDrugActivity.this,error));
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

    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(VeterinaryDrugActivity.this);
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
