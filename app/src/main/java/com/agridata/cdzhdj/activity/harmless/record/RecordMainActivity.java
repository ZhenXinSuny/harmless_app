package com.agridata.cdzhdj.activity.harmless.record;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.agridata.cdzhdj.activity.harmless.collection.ApplyActivity;
import com.agridata.cdzhdj.activity.harmless.collection.ApplyAdapter;
import com.agridata.cdzhdj.activity.harmless.collection.BoApplyFragment;
import com.agridata.cdzhdj.activity.harmless.collection.DaiApplyFragment;
import com.agridata.cdzhdj.activity.harmless.collection.YiApplyFragment;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.databinding.ActivityRecordBinding;
import com.agridata.cdzhdj.utils.TimeDialogUtils;
import com.agridata.network.utils.LogUtil;
import com.agridata.util.DateTimeUtils;

import java.util.ArrayList;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-05-06 18:02.
 * @Description :描述
 */
public class RecordMainActivity extends BaseActivity<ActivityRecordBinding> {
    private final static String TAG = "RecordMainActivity------》";

    private ArrayList<Fragment> fragments;

    public static void start(Activity context) {
        Intent intent = new Intent(context, RecordMainActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityRecordBinding getBinding() {
        return ActivityRecordBinding.inflate(getLayoutInflater());
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
            TimeDialogUtils.initTimePicker2(this,  binding.endTimeTv);
            TimeDialogUtils.pvTime1.show();
        });


        binding.queryBtn.setOnClickListener(v -> {
            int position = binding.fragmentViewPager.getCurrentItem();
            LogUtil.d("lzx------------》", "position" + position);
            Fragment selectedFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + binding.fragmentViewPager.getId() + ":" + position);
            LogUtil.d("lzx------------》", "selectedFragment" + selectedFragment);
            if (selectedFragment instanceof AllRecordFragment) {
                String startTime = binding.startTimeTv.getText().toString();
                String endTime = binding.endTimeTv.getText().toString();
                ((AllRecordFragment) selectedFragment).performNetworkRequest(startTime, endTime);
            } else if (selectedFragment instanceof YiRecordFragment) {
                String startTime = binding.startTimeTv.getText().toString();
                String endTime = binding.endTimeTv.getText().toString();
                ((YiRecordFragment) selectedFragment).performNetworkRequest(startTime, endTime);
            }else if (selectedFragment instanceof BoRecordFragment) {
                String startTime = binding.startTimeTv.getText().toString();
                String endTime = binding.endTimeTv.getText().toString();
                ((BoRecordFragment) selectedFragment).performNetworkRequest(startTime, endTime);
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
        String startTime = DateTimeUtils.getSevenDaysAgoTime();
        String endTimeTv = DateTimeUtils.getCurrentTime();
        fragments.add(AllRecordFragment.newInstance(startTime,endTimeTv));
        fragments.add(YiRecordFragment.newInstance(startTime,endTimeTv));
        fragments.add(BoRecordFragment.newInstance(startTime,endTimeTv));
        RecordMainAdapter adapter = new RecordMainAdapter(fragments, getSupportFragmentManager(), this);
        binding.fragmentViewPager.setAdapter(adapter);
        binding.fragmentViewPager.setCurrentItem(page);
        binding.fragmentViewPager.setOffscreenPageLimit(0);
        binding.groupTaskTabLayout.setupWithViewPager(binding.fragmentViewPager);
    }


}
