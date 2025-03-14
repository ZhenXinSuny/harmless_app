package com.agridata.cdzhdj.activity.entrycheck.history;

import android.content.Context;
import android.widget.ImageView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.utils.GlideEngine;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-11-21 10:29.
 * @Description :描述
 */
public class EarTagImgAdapter extends BaseRecyclerViewAdapter<String, BaseRecyclerViewHolder> {

    private Context mContext;


    public EarTagImgAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, String data, int position) {
        ImageView fiv = viewHolder.getView(R.id.fiv);


        Glide.with(mContext)
                .load(UrlUtils.pic_url +data)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(fiv);

       // GlideEngine.createGlideEngine().loadImage(mContext, UrlUtils.pic_url + data, fiv);
    }
}
