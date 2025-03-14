package com.agridata.cdzhdj.activity.entrycheck.check;

import android.content.Context;
import android.widget.ImageView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.utils.GlideEngine;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @ProjectName : AdmissionCheck
 * @Author :
 * @Time : 2021/10/15 17:22
 * @Description :
 */
public class EarTagImgAdapter extends BaseRecyclerViewAdapter<String, BaseRecyclerViewHolder> {

    private Context mContext;


    public EarTagImgAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder,String data, int position) {
        ImageView fiv = viewHolder.getView(R.id.fiv);

        GlideEngine.createGlideEngine().loadImage(mContext, UrlUtils.pic_url + data,fiv);


    }
}
