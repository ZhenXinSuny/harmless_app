package com.agridata.cdzhdj.activity.harmless.regionAdmin.chuli;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.agridata.cdzhdj.activity.adapter.ClMainPagerAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.databinding.ActivityChuLiDetailBinding;
import com.agridata.cdzhdj.activity.harmless.chulifragment.CLDetailFragment;
import com.agridata.cdzhdj.activity.harmless.chulifragment.CLRkFragment;
import com.agridata.network.utils.LogUtil;

import java.util.ArrayList;

public class ChuLiDetailActivity extends BaseActivity<ActivityChuLiDetailBinding> {
    private final static String TAG = "RuKuDetailActivity------》";




    private String Mid;
    private ArrayList<Fragment> fragments;






    public static void start(Activity context, String Mid) {
        Intent intent = new Intent(context, ChuLiDetailActivity.class);
        intent.putExtra("Mid", Mid);
        context.startActivity(intent);
    }


    @Override
    protected ActivityChuLiDetailBinding getBinding() {
        return ActivityChuLiDetailBinding.inflate(getLayoutInflater());
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        Mid = this.getIntent().getStringExtra("Mid");
        LogUtil.d(TAG, "MID" + Mid);
    }
    @Override
    protected void initView() {
        getArguments();
        addFragments();
        binding.titlebarLeft.setOnClickListener(v -> finish());
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
        fragments.add(CLDetailFragment.newInstance(Mid));
        fragments.add(CLRkFragment.newInstance(Mid));
        ClMainPagerAdapter adapter = new ClMainPagerAdapter(fragments, getSupportFragmentManager(), this);
        binding.fragmentViewPager.setAdapter(adapter);
        binding.groupTaskTabLayout.setupWithViewPager(binding.fragmentViewPager);
        binding.fragmentViewPager.setCurrentItem(page);
        binding.fragmentViewPager.setOffscreenPageLimit(3);


//        LinearLayout linearLayout = (LinearLayout) binding.groupTaskTabLayout.getChildAt(0);
//        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//        linearLayout.setDividerPadding(38);//设置分隔器两端的填充
//        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
//                R.drawable.tablayout_divider_vetical));

    }

}
