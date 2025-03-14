package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-31 10:12.
 * @Description :描述
 */
public class LawListMainPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private Context mContext;

    public LawListMainPagerAdapter(List<Fragment> fragments, FragmentManager fm, Context context) {
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
    public int getItemPosition(Object object) {
        return LawListMainPagerAdapter.POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        if (fragment == mFragments.get(position)) {
            return fragment;
        } else {
            destroyItem(container, position, fragment);
            return super.instantiateItem(container, position);
        }
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
                title = "主办";
                break;
            case 1:
                title = "协办";
                break;
            default:
                title = "主办";
                break;
        }
        return title;
    }

}
