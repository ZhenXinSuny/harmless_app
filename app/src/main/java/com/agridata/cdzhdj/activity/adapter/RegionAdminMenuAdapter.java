package com.agridata.cdzhdj.activity.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.MenuData;
import com.agridata.cdzhdj.data.RegionAdminBean;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * @auther 56454
 * @date 2022/6/21
 * @time 17:01.
 */
public class RegionAdminMenuAdapter extends BaseRecyclerViewAdapter<RegionAdminBean, BaseRecyclerViewHolder> {
    private List<RegionAdminBean> mDates;
    private Context mContext;
    private GridLayoutManager mGridLayoutManager;

    public RegionAdminMenuAdapter(int layoutResId, List<RegionAdminBean> dataList, Context context, GridLayoutManager gridLayoutManager) {
        super(layoutResId, dataList);
        mDates = dataList;
        mContext = context;
        mGridLayoutManager = gridLayoutManager;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, RegionAdminBean data, final int position) {
        TextView title = viewHolder.getView(R.id.tv_region_admin);
        ImageView imageView = viewHolder.getView(R.id.iv_region_admin);
        TextView titleNum = viewHolder.getView(R.id.tv_num);
        TextView titleUnit = viewHolder.getView(R.id.tv_unit);
        title.setText(data.name);
        titleNum.setText(data.num+"");
        titleUnit.setText(data.unitName);
        View convertView = viewHolder.getConvertView();
//        ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
//        layoutParams.height = mGridLayoutManager.getWidth()/mGridLayoutManager.getSpanCount();
        Drawable mDrawable =   ContextCompat.getDrawable(mContext, data.img);
        Glide.with(mContext).load(mDrawable).into(imageView);


    }
}
