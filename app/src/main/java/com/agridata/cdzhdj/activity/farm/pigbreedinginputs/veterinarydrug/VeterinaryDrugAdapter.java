package com.agridata.cdzhdj.activity.farm.pigbreedinginputs.veterinarydrug;

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
public class VeterinaryDrugAdapter extends BaseRecyclerViewAdapter<FeedStorageListData.Result, BaseRecyclerViewHolder> {

    private Context mContext;


    public VeterinaryDrugAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, FeedStorageListData.Result data, int position) {
        TextView veterinaryManufactureTv = viewHolder.getView(R.id.veterinary_manufacture_tv);
        TextView veterinaryBusinessTv = viewHolder.getView(R.id.veterinary_business_tv);
        TextView veterinaryBrandTv = viewHolder.getView(R.id.veterinary_brand_tv);
        TextView veterinaryLotNumberTv = viewHolder.getView(R.id.veterinary_lot_number_tv);
        TextView dateOfProductionTv = viewHolder.getView(R.id.date_of_production_tv);
        TextView validityPeriodTv = viewHolder.getView(R.id.validity_period_tv);
        TextView storageQuantityTv = viewHolder.getView(R.id.storage_quantity_tv);
        TextView warehousingTimeTv = viewHolder.getView(R.id.warehousing_time_tv);
        veterinaryManufactureTv.setText(data.manufacturer);
        veterinaryBusinessTv.setText(data.enterprise);
        veterinaryBrandTv.setText(data.brand);
        veterinaryLotNumberTv.setText(data.batch);
        dateOfProductionTv.setText(data.beBornDate);
        validityPeriodTv.setText(data.validity);
        storageQuantityTv.setText(data.goodsNumber+"");
        warehousingTimeTv.setText(data.warehousingTime);

        TextView veterinary_name_tv = viewHolder.getView(R.id.veterinary_name_tv);
        veterinary_name_tv.setText(data.goodsName);
        Button detailsBtn = viewHolder.getView(R.id.details_btn);

        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VeterinaryDrugDetailsActivity.start(mContext,data.mid);
            }
        });
    }

}
