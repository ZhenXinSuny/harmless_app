package com.agridata.cdzhdj.activity.harmless.collection;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.agridata.cdzhdj.activity.harmless.shouYun.ShouYunWebViewActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.databinding.ActivityApplyBinding;
import com.agridata.cdzhdj.utils.NetworkUtils;
import com.agridata.cdzhdj.utils.TimeDialogUtils;
import com.agridata.network.utils.LogUtil;
import com.agridata.util.DateTimeUtils;
import com.lzx.utils.RxToast;


import java.util.ArrayList;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-26 13:30.
 * @Description :描述
 */
public class ApplyActivity extends BaseActivity<ActivityApplyBinding> {
    private final static String TAG = "ApplyActivity------》";
    private ArrayList<Fragment> fragments;

    public static void start(Activity context) {
        Intent intent = new Intent(context, ApplyActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityApplyBinding getBinding() {
        return ActivityApplyBinding.inflate(getLayoutInflater());
    }

    /**
     * 获取参数
     */
    private void getArguments() {

    }

    @Override
    protected void initView() {
        getArguments();
        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.startTimeTv.setText(DateTimeUtils.getSevenDaysAgoTime());//当前时间-7
        binding.endTimeTv.setText(DateTimeUtils.getCurrentTime());//当前时间
        binding.startTimeTv.setOnClickListener(v -> {
            TimeDialogUtils.initTimePicker1(this, binding.startTimeTv);
            TimeDialogUtils.pvTime.show();
        });
        binding.endTimeTv.setOnClickListener(v -> {
            TimeDialogUtils.initTimePicker2(this, binding.endTimeTv);
            TimeDialogUtils.pvTime1.show();
        });

        binding.queryBtn.setOnClickListener(v -> {
            int position = binding.fragmentViewPager.getCurrentItem();
            LogUtil.d("lzx------------》", "position" + position);
            Fragment selectedFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + binding.fragmentViewPager.getId() + ":" + position);
            LogUtil.d("lzx------------》", "selectedFragment" + selectedFragment);
            if (selectedFragment instanceof DaiApplyFragment) {
                String startTime = binding.startTimeTv.getText().toString();
                String endTime = binding.endTimeTv.getText().toString();
                ((DaiApplyFragment) selectedFragment).performNetworkRequest(startTime, endTime);
            } else if (selectedFragment instanceof YiApplyFragment) {
                String startTime = binding.startTimeTv.getText().toString();
                String endTime = binding.endTimeTv.getText().toString();
                ((YiApplyFragment) selectedFragment).performNetworkRequest(startTime, endTime);
            }else if (selectedFragment instanceof BoApplyFragment) {
                String startTime = binding.startTimeTv.getText().toString();
                String endTime = binding.endTimeTv.getText().toString();
                ((BoApplyFragment) selectedFragment).performNetworkRequest(startTime, endTime);
            }
        });
        binding.dailiApplyTv.setOnClickListener(v -> {
            if (NetworkUtils.isNetAvailable(ApplyActivity.this)) {
                ShouYunWebViewActivity.start(ApplyActivity.this);
            } else {
                Objects.requireNonNull(RxToast.warning(ApplyActivity.this, "当前无法连接网络，请检查网络设置是否正常"));
            }
        });
        addFragments();
    }

    @Override
    protected void initDatas() {

    }

    /**
     * 添加Fragment
     */
    private void addFragments() {
        int page = binding.fragmentViewPager.getCurrentItem();
        fragments = new ArrayList<>();
        fragments.add(DaiApplyFragment.newInstance(binding.startTimeTv.getText().toString(), binding.endTimeTv.getText().toString()));
        fragments.add(YiApplyFragment.newInstance(binding.startTimeTv.getText().toString(), binding.endTimeTv.getText().toString()));
        fragments.add(BoApplyFragment.newInstance(binding.startTimeTv.getText().toString(), binding.endTimeTv.getText().toString()));
        ApplyAdapter adapter = new ApplyAdapter(fragments, getSupportFragmentManager(), this);
        binding.fragmentViewPager.setAdapter(adapter);
        binding.groupTaskTabLayout.setupWithViewPager(binding.fragmentViewPager);
        binding.fragmentViewPager.setCurrentItem(page);
        binding.fragmentViewPager.setOffscreenPageLimit(0);
    }


}
