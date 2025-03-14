package com.agridata.cdzhdj.activity.harmless.collection;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-28 11:05.
 * @Description :描述
 */
public class ApplyAdapter extends  FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private Context mContext;

    public ApplyAdapter(List<Fragment> fragments, FragmentManager fm, Context context) {
        super(fm);
        this.mFragments = fragments;
        this.mContext = context;
    }

    @NonNull
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
                title = "待收集";
                break;
            case 1:
                title = "已收集";
                break;
            default:
                title = "已驳回";
                break;
        }
        return title;
    }

}
