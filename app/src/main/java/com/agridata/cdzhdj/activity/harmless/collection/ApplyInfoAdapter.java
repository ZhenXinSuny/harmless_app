package com.agridata.cdzhdj.activity.harmless.collection;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.harmless.CollectFillInActivity;
import com.agridata.cdzhdj.data.harmless.ApplyBean;
import com.agridata.cdzhdj.utils.GlideUtils;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.agridata.cdzhdj.utils.NetworkUtils;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.lzx.utils.RxToast;

import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-28 13:41.
 * @Description :描述
 */
public class ApplyInfoAdapter extends BaseRecyclerViewAdapter<ApplyBean.ResultBean, BaseRecyclerViewHolder> {

    private Context mContext;

    private onCollectionListener collectionListener;

    public ApplyInfoAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, ApplyBean.ResultBean data, final int position) {
        TextView apply_no_tv = viewHolder.getView(R.id.apply_no_tv);
        apply_no_tv.setText(data.applyNo);
        TextView xdr_name_tv = viewHolder.getView(R.id.xdr_name_tv);
        xdr_name_tv.setText(data.userName);
        TextView iphone_num_tv = viewHolder.getView(R.id.iphone_num_tv);
        iphone_num_tv.setText(data.mobile);
        TextView region_name_tv = viewHolder.getView(R.id.region_name_tv);
        region_name_tv.setText(data.region.regionFullName);
        TextView address_name_tv = viewHolder.getView(R.id.address_name_tv);
        address_name_tv.setText(data.applyAddress);
        TextView apply_time_tv = viewHolder.getView(R.id.apply_time_tv);
        apply_time_tv.setText(data.applyTime);


        LinearLayout status_ll = viewHolder.getView(R.id.status_ll);
        TextView status_tv = viewHolder.getView(R.id.status_tv);

        LinearLayout btn_ll = viewHolder.getView(R.id.btn_ll);

        if (data.checkStatus == 0) {
            status_ll.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.dai_apply_bg));
            status_tv.setText("待收集");
            btn_ll.setVisibility(View.VISIBLE);
        } else if (data.checkStatus == 1) {
            status_ll.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.yi_apply_bg));
            status_tv.setText("已收集");
            btn_ll.setVisibility(View.GONE);
        } else {
            status_ll.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.bo_apply_bg));
            status_tv.setText("已驳回");
            btn_ll.setVisibility(View.GONE);
        }

        Button reject_btn = viewHolder.getView(R.id.reject_btn);
        reject_btn.setOnClickListener(view -> {
            if (!NetworkUtils.isNetAvailable(mContext)) {
                Objects.requireNonNull( RxToast.warning(mContext,"当前暂无网络，无法驳回"));
            }else {
                collectionListener.collection(position);
            }
        });

        Button check_btn = viewHolder.getView(R.id.check_btn);
        check_btn.setOnClickListener(view -> {
            CollectFillInActivity.start((Activity) mContext, GsonUtil.toJson(data));
        });

        ImageView idcard_iv = viewHolder.getView(R.id.idcard_iv);
        ImageView bank_iv = viewHolder.getView(R.id.bank_iv);

        if (NetworkUtils.isNetAvailable(mContext)) {
            if (!TextUtils.isEmpty(data.imgFiles.idCardPic)) {
                GlideUtils.createGlideEngine().loadRounderImage(mContext, UrlUtils.pic_url + data.imgFiles.idCardPic, idcard_iv, 16);
            }
            if (!StrUtil.isEmpty(data.imgFiles.bankPic)) {
                GlideUtils.createGlideEngine().loadRounderImage(mContext, UrlUtils.pic_url + data.imgFiles.bankPic, bank_iv, 16);
            }
        } else {
            if (!TextUtils.isEmpty(data.imgFiles.idCardPic)) {
                GlideUtils.createGlideEngine().loadRounderImage(mContext, "file://" + data.imgFiles.idCardPic, idcard_iv, 16);
            }
            if (!StrUtil.isEmpty(data.imgFiles.bankPic)) {
                GlideUtils.createGlideEngine().loadRounderImage(mContext, "file://" + data.imgFiles.bankPic, bank_iv, 16);
            }
        }

    }

    public void setCollectionListener(onCollectionListener linstener) {
        this.collectionListener = linstener;
    }
}
