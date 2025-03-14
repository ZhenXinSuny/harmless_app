package com.agridata.cdzhdj.activity.harmless.record;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.harmless.edit.CollectEditFillInActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.collected.CollectedDetailActivity;
import com.agridata.cdzhdj.data.CollectedBean;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-05-07 09:07.
 * @Description :描述
 */
public class RecordInfoAdapter extends BaseRecyclerViewAdapter<CollectedBean.Result.PageItems, BaseRecyclerViewHolder> {



    private Context mContext;


    public RecordInfoAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, CollectedBean.Result.PageItems data, final int position) {


        TextView collectionNoTv = viewHolder.getView(R.id.collection_no_tv);
        collectionNoTv.setText(data.collectNo);
        TextView xdrNameTv = viewHolder.getView(R.id.xdr_name_tv);
        xdrNameTv.setText(data.dep_ApplyGUID.userName);
        TextView xdrIphoneNumTv = viewHolder.getView(R.id.xdr_iphone_num_tv);
        xdrIphoneNumTv.setText(data.dep_ApplyGUID.mobile);
        TextView applyRegionNameTv = viewHolder.getView(R.id.apply_region_name_tv);
        applyRegionNameTv.setText(data.dep_ApplyGUID.applyAddress);
        TextView collectionFactroyTv = viewHolder.getView(R.id.collection_factroy_tv);
        collectionFactroyTv.setText(data.dep_ApplyGUID.applyPoint.name);
        TextView carNumTv = viewHolder.getView(R.id.car_num_tv);
        carNumTv.setText(data.carInfo.name);
        TextView collectionTimesTv = viewHolder.getView(R.id.collection_times_tv);
        collectionTimesTv.setText(data.createAtStr.substring(0,19) +"\u3000" + "收集");



        LinearLayout statusLl = viewHolder.getView(R.id.status_ll);
        TextView statusTv = viewHolder.getView(R.id.status_tv);

        Button detailBtn = viewHolder.getView(R.id.detail_btn);
        Button checkBtn = viewHolder.getView(R.id.check_btn);

        detailBtn.setOnClickListener(view -> {
            CollectedDetailActivity.start((Activity) mContext,data.mid,"1");
        });

        checkBtn.setOnClickListener(view -> {
            CollectEditFillInActivity.start((Activity) mContext,data.mid);
        });

        if (data.checkStatus == 0) {
            statusLl.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.dai_apply_bg));
            statusTv.setText("待审核");
            detailBtn.setVisibility(View.VISIBLE);
            checkBtn.setVisibility(View.GONE);
        } else if (data.checkStatus == 1) {
            statusLl.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.yi_apply_bg));
            statusTv.setText("已审核");
            detailBtn.setVisibility(View.VISIBLE);
            checkBtn.setVisibility(View.GONE);
        } else {
            statusLl.setBackground(AppCompatResources.getDrawable(mContext, R.drawable.bo_apply_bg));
            statusTv.setText("已驳回");
        }


        TextView animalCountTv = viewHolder.getView(R.id.animal_tv);
        String unitName = StrUtil.setAnimalUnitCollected(Integer.parseInt(data.animal.key));

        if (Integer.parseInt(data.animal.key)==1 || Integer.parseInt(data.animal.key)==2 || Integer.parseInt(data.animal.key)==3){
            String unitCountName = data.animal.animalName + data.dieAmount + unitName;
            animalCountTv.setText(unitCountName);
        }else {
            String unitCountName = data.animal.animalName + data.dieWeight + unitName;
            animalCountTv.setText(unitCountName);
        }

    }


}
