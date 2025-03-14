package com.agridata.cdzhdj.activity.farm.pigbreedinginputs.feed;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.pigbreed.feed.FeedStorageListData;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-05-05 08:56.
 * @Description :描述
 */
public class FeedStorageAdapter extends BaseRecyclerViewAdapter<FeedStorageListData.Result, BaseRecyclerViewHolder> {

    private Context mContext;


    public FeedStorageAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, FeedStorageListData.Result data, int position) {
        TextView nameTitleTv = viewHolder.getView(R.id.feed_manufacture_tv);
        TextView feedBusinessTv = viewHolder.getView(R.id.feed_business_tv);
        TextView feedBrandTv = viewHolder.getView(R.id.feed_brand_tv);
        TextView feedLotNumberTv = viewHolder.getView(R.id.feed_lot_number_tv);
        TextView dateOfProductionTv = viewHolder.getView(R.id.date_of_production_tv);
        TextView validityPeriodTv = viewHolder.getView(R.id.validity_period_tv);
        TextView storageQuantityTv = viewHolder.getView(R.id.storage_quantity_tv);
        TextView warehousingTimeTv = viewHolder.getView(R.id.warehousing_time_tv);

        TextView xdrNameTv = viewHolder.getView(R.id.xdr_name_tv);
        TextView regionNameTv = viewHolder.getView(R.id.region_name_tv);

        nameTitleTv.setText(data.manufacturer);
        feedBusinessTv.setText(data.enterprise);
        feedBrandTv.setText(data.brand);
        feedLotNumberTv.setText(data.batch);
        dateOfProductionTv.setText(data.beBornDate);
        validityPeriodTv.setText(data.validity);
        storageQuantityTv.setText(data.goodsNumber+"");
        warehousingTimeTv.setText(data.warehousingTime);

        xdrNameTv.setText(data.farm.name+"");
        regionNameTv.setText(data.region.regionFullName);

        Button detailsBtn = viewHolder.getView(R.id.details_btn);

        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedStorageDetailsActivity.start(mContext,data.mid);
            }
        });
    }

}
