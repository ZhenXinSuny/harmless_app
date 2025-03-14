package com.agridata.cdzhdj.activity.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.MenuData;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * @auther 56454
 * @date 2022/6/21
 * @time 17:01.
 */
public class MainMenuAdapter extends BaseRecyclerViewAdapter<MenuData, BaseRecyclerViewHolder> {
    private List<MenuData> mDates;
    private Context mContext;
    private GridLayoutManager mGridLayoutManager;

    public MainMenuAdapter(int layoutResId, List<MenuData> dataList, Context context, GridLayoutManager gridLayoutManager) {
        super(layoutResId, dataList);
        mDates = dataList;
        mContext = context;
        mGridLayoutManager = gridLayoutManager;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, MenuData data, final int position) {
        TextView title = viewHolder.getView(R.id.tv_record);
        ImageView imageView = viewHolder.getView(R.id.iv_record);
        title.setText(data.name);
        Drawable mDrawable =   ContextCompat.getDrawable(mContext, data.img);
        Glide.with(mContext).load(mDrawable).into(imageView);
        if (position==0 && data.count>0){
            Badge badge;
            badge = new QBadgeView(mContext).bindTarget(viewHolder.getView(R.id.rl));
            badge.setBadgeGravity( Gravity.END |  Gravity.TOP);
            badge.setBadgeTextSize(10, true);
            badge.setGravityOffset(0,8,true);
            badge.setBadgePadding(8, true);
            badge.setShowShadow(true);
            badge.setBadgeNumber(data.count);
        }

    }
}
