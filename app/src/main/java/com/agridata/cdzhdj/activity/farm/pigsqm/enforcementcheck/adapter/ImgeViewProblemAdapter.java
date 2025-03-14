package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.ImageViewData;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-10 09:40.
 * @Description :描述
 */
public class ImgeViewProblemAdapter extends BaseRecyclerViewAdapter<ImageViewData, BaseRecyclerViewHolder> {

    private Context mContext;


    public ImgeViewProblemAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, ImageViewData data, int position) {
        ImageView file_img_iv = viewHolder.getView(R.id.file_img_iv);
        TextView file_name_tv = viewHolder.getView(R.id.file_name_tv);
        TextView file_size_tv = viewHolder.getView(R.id.file_size_tv);
        file_name_tv.setText(data.mPathName);
        file_size_tv.setText(data.mImageSize);
        Glide.with(mContext)
                .load(data.mImageViewUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(file_img_iv);
    }
}
