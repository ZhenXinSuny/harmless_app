package com.agridata.cdzhdj.activity.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class RkMainPagerAdapter  extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private Context mContext;

    public RkMainPagerAdapter(List<Fragment> fragments, FragmentManager fm, Context context) {
        super(fm);
        this.mFragments = fragments;
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position) {
            case 0:
                title ="入库详情";
                break;
            case 1:
                title ="入库明细";
                break;
            default:
                title ="入库详情";
                break;
        }
        return title;
    }
}
