package com.agridata.cdzhdj.activity.harmless;

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
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-28 09:07.
 * @Description :描述
 */
public class CollectionMainMenuAdapter extends BaseRecyclerViewAdapter<MenuData, BaseRecyclerViewHolder> {
    private List<MenuData> mDates;
    private Context mContext;
    private GridLayoutManager mGridLayoutManager;

    public CollectionMainMenuAdapter(int layoutResId, List<MenuData> dataList, Context context, GridLayoutManager gridLayoutManager) {
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
        View convertView = viewHolder.getConvertView();
        ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
        layoutParams.height = mGridLayoutManager.getWidth()/3;
        Drawable mDrawable =   ContextCompat.getDrawable(mContext, data.img);
        Glide.with(mContext).load(mDrawable).into(imageView);


    }
}
