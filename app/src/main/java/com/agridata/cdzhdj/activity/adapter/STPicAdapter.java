package com.agridata.cdzhdj.activity.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.CollectedDetailBean;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class STPicAdapter extends BaseRecyclerViewAdapter<CollectedDetailBean.Result.ImgFiles.ShiTiPicList, BaseRecyclerViewHolder> {

    private Context mContext;

    public STPicAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, CollectedDetailBean.Result.ImgFiles.ShiTiPicList data, final int position) {
        ImageView fiv = viewHolder.getView(R.id.fiv);
        ImageView iv_del = viewHolder.getView(R.id.iv_del);
        iv_del.setVisibility(View.GONE);


        Glide.with(mContext)
                .load(UrlUtils.pic_url + data.mid)
                .error(R.drawable.ic_default_iv)
                .placeholder( R.drawable.ic_default_iv)
                .fallback( R.drawable.ic_default_iv)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(fiv);
    }
}
