package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.details.EnforcementDetailsActivity;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.details.SpotCheckDetailsActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.law.EnforcementHomeData;
import com.agridata.cdzhdj.databinding.ActivityToDoListMainBinding;
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
 * @Date : on 2023-04-19 17:10.
 * @Description :描述
 */
public class ToDoListMainActivity extends BaseActivity<ActivityToDoListMainBinding> implements onFuChaListener,onQualifiedListener {


    private final static String TAG = "lzx------》";


    private CustomLoadingDialog mLoadingDialog;

    private View mDrawer;
    private String day;
    private String currentTime;
    private String userId;
    private String PersonMid;
    private ToDaMainListAdapter toDaMainListAdapter;

    @Override
    protected ActivityToDoListMainBinding getBinding() {
        return ActivityToDoListMainBinding.inflate(getLayoutInflater());
    }
    public static void start(Context context) {
        Intent intent = new Intent(context, ToDoListMainActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void initView() {
        initLoadingView();


         PersonMid = UserDataSP.getInstance().getUserInfo().Result.dependency.Mid;
         userId = UserDataSP.getInstance().getUserInfo().Result.dependency.Mid;
        mDrawer = findViewById(R.id.navdrawer);
        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(ToDoListMainActivity.this, LinearLayoutManager.VERTICAL, false));
        toDaMainListAdapter = new ToDaMainListAdapter(R.layout.item_to_to_main_list, ToDoListMainActivity.this);
        toDaMainListAdapter.setFuChaListener(this);
        toDaMainListAdapter.setQualifiedListener(this);
        binding.recyclerview.setAdapter(toDaMainListAdapter);


        toDaMainListAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                if (toDaMainListAdapter.getData(position).checkType==2){
                    SpotCheckDetailsActivity.start(ToDoListMainActivity.this,toDaMainListAdapter.getData(position).mid);
                }else {
                    EnforcementDetailsActivity.start(ToDoListMainActivity.this,toDaMainListAdapter.getData(position).mid);
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
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
            getDoMainList();
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
    protected void initDatas() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDoMainList();
    }



    private void  getDoMainList(){
        showLoading("数据加载中...");
        HttpRequest.getToDoMianList(ToDoListMainActivity.this, binding.navdrawer.startTimeTv.getText().toString()+" "+ "00:00:00",binding.navdrawer.endTimeTv.getText().toString()+" "+ "23:59:59", userId, PersonMid,4, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, EnforcementHomeData enforcementHomeData) {
                hideLoading();
                if (enforcementHomeData.status==0){
                    if (enforcementHomeData.result.size()>0){
                        binding.notDataRl.setVisibility(View.GONE);
                        binding.recyclerview.setVisibility(View.VISIBLE);
                        toDaMainListAdapter.refreshDataList(enforcementHomeData.result);

                    }else {
                        binding.recyclerview.setVisibility(View.GONE);
                        binding.notDataRl.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(ToDoListMainActivity.this,error));
            }
        });
    }

    @Override
    public void fuCha(String mid, int position) {
        ReviewInfoActivity.start(ToDoListMainActivity.this,mid);
    }
    @Override
    public void qualified(String mid, int position) {
        ReviewRegisterActivity.start(ToDoListMainActivity.this,mid);
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
        this.mLoadingDialog = new CustomLoadingDialog(ToDoListMainActivity.this);
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
