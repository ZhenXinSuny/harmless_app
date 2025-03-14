package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.report;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.fragment.CoSponsorFragment;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.adapter.LawListMainPagerAdapter;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.fragment.HostFragment;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.databinding.ActivityLawenforcementCheckListBinding;
import com.agridata.cdzhdj.utils.RxBus;
import com.agridata.cdzhdj.utils.TimeDialogUtils;
import com.agridata.network.utils.LogUtil;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lzx.utils.RxToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-28 13:51.
 * @Description :描述 执法检查列表
 */
public class LawEnforcementCheckListActivity extends BaseActivity<ActivityLawenforcementCheckListBinding> {
    private static final String TAG = "LawEnforcementCheckListActivity---->";

    private ArrayList<Fragment> fragments;

    private  TimePickerView pvTime;
    private View mDrawer;
    private String day;
    private String currentTime;

    @Override
    protected ActivityLawenforcementCheckListBinding getBinding() {
        return ActivityLawenforcementCheckListBinding.inflate(getLayoutInflater());
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, LawEnforcementCheckListActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initView() {
        mDrawer = findViewById(R.id.navdrawer);
        binding.titlebarLeft.setOnClickListener(v -> finish());

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

            RxBus.getInstance().post("startTime", binding.navdrawer.startTimeTv.getText().toString() +" "+ "00:00:00");
            RxBus.getInstance().post("sendTime", binding.navdrawer.endTimeTv.getText().toString() +" "+ "23:59:59");
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

            getPermissions();

        });



        addFragments();
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
    protected void initDatas() {

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



    private void getPermissions() {
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.MANAGE_EXTERNAL_STORAGE).request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean all) {
                        if (!permissions.contains(Permission.MANAGE_EXTERNAL_STORAGE)) {
                            Objects.requireNonNull(RxToast.warning(LawEnforcementCheckListActivity.this, "以便于您更好的使用执法检查，请打开文件管理权限"));
                            return;
                        }
                        LogUtil.d("lzx---->", permissions.toString());
                        if (all) {
                            binding.drawerLayout.closeDrawer(mDrawer);
                            LawReportActivity.start(LawEnforcementCheckListActivity.this);
                        } else {
                            Objects.requireNonNull(RxToast.warning(LawEnforcementCheckListActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(LawEnforcementCheckListActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(LawEnforcementCheckListActivity.this, "获取权限失败"));
                        }
                    }
                });
    }

    /**
     * 添加Fragment
     */
    private void addFragments() {
        int page = binding.pager.getCurrentItem();
        fragments = new ArrayList<>();
        fragments.add(HostFragment.newInstance(day +" "+ "00:00:00", currentTime+" "+"23:59:59",1));
        fragments.add(CoSponsorFragment.newInstance(day +" "+"00:00:00", currentTime+" "+"23:59:59",2));
        LawListMainPagerAdapter adapter = new LawListMainPagerAdapter(fragments, getSupportFragmentManager(), this);
        binding.pager.setAdapter(adapter);
        binding.lawTabLayout.setupWithViewPager(binding.pager);
        binding.pager.setCurrentItem(page);

        binding.pager.setOffscreenPageLimit(0);
    }


}
