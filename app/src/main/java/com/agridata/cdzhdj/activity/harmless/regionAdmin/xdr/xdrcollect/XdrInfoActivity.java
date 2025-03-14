package com.agridata.cdzhdj.activity.harmless.regionAdmin.xdr.xdrcollect;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.agridata.cdzhdj.activity.adapter.xdr.XdrMainPagerAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.databinding.ActivityXdrInfoBinding;
import com.agridata.cdzhdj.activity.harmless.xdrinfo.XdrInfoListFragment;
import com.agridata.cdzhdj.activity.harmless.xdrinfo.XdrTjFragment;
import com.agridata.network.utils.LogUtil;

import java.util.ArrayList;

public class XdrInfoActivity extends BaseActivity<ActivityXdrInfoBinding> implements View.OnClickListener {
    private final static String TAG = "RuKuDetailActivity------》";




    private String Mid;
    private String Name;
    private ArrayList<Fragment> fragments;






    public static void start(Activity context, String Mid,String name) {
        Intent intent = new Intent(context, XdrInfoActivity.class);
        intent.putExtra("Mid", Mid);
        intent.putExtra("Name",name);
        context.startActivity(intent);
    }


    @Override
    protected ActivityXdrInfoBinding getBinding() {
        return ActivityXdrInfoBinding.inflate(getLayoutInflater());
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        Mid = this.getIntent().getStringExtra("Mid");
        Name = this.getIntent().getStringExtra("Name");
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
        fragments.add(XdrInfoListFragment.newInstance(Mid));
        fragments.add(XdrTjFragment.newInstance(Mid));
        XdrMainPagerAdapter adapter = new XdrMainPagerAdapter(fragments, getSupportFragmentManager(), this);
        binding.fragmentViewPager.setAdapter(adapter);
        binding.groupTaskTabLayout.setupWithViewPager(binding.fragmentViewPager);
        binding.fragmentViewPager.setCurrentItem(page);
        binding.fragmentViewPager.setOffscreenPageLimit(0);

        binding.titlebarMiddle.setText(Name);

//        LinearLayout linearLayout = (LinearLayout) binding.groupTaskTabLayout.getChildAt(0);
//        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//        linearLayout.setDividerPadding(38);//设置分隔器两端的填充
//        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
//                R.drawable.tablayout_divider_vetical));

    }


    @Override
    public void onClick(View v) {

    }
}
