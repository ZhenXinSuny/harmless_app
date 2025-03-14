package com.agridata.cdzhdj.activity.harmless.offlinesubmission;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.CollectInfoData;
import com.agridata.cdzhdj.data.db.bean.CollectionDBModel;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-05-06 14:53.
 * @Description :描述
 */
public class LiXianPicAdapter extends BaseRecyclerViewAdapter<CollectInfoData.ImgFilesBean.ImgMidBean, BaseRecyclerViewHolder> {

    private Context mContext;

    public LiXianPicAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, CollectInfoData.ImgFilesBean.ImgMidBean data, final int position) {
        ImageView fiv = viewHolder.getView(R.id.fiv);
        ImageView iv_del = viewHolder.getView(R.id.iv_del);
        iv_del.setVisibility(View.GONE);

        Glide.with(mContext)
                .load("file://" + data.Mid)
                .error(R.drawable.ic_default_iv)
                .placeholder( R.drawable.ic_default_iv)
                .fallback( R.drawable.ic_default_iv)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(22)))
                .into(fiv);
    }
}
