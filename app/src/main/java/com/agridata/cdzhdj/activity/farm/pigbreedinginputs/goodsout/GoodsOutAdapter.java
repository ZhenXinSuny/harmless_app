package com.agridata.cdzhdj.activity.farm.pigbreedinginputs.goodsout;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.pigbreed.goods.GoodsOutListBean;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-05-05 08:56.
 * @Description :描述
 */
public class GoodsOutAdapter extends BaseRecyclerViewAdapter<GoodsOutListBean.Result, BaseRecyclerViewHolder> {

    private Context mContext;


    public GoodsOutAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, GoodsOutListBean.Result data, int position) {
        TextView brand_tv = viewHolder.getView(R.id.brand_tv);
        TextView lot_number_tv = viewHolder.getView(R.id.lot_number_tv);
        TextView single_head_usage_tv = viewHolder.getView(R.id.single_head_usage_tv);
        TextView number_animals_used_tv = viewHolder.getView(R.id.number_animals_used_tv);
        TextView total_usage_tv = viewHolder.getView(R.id.total_usage_tv);
        TextView farmer_name_tv = viewHolder.getView(R.id.farmer_name_tv);
        TextView region_name_tv = viewHolder.getView(R.id.region_name_tv);

        brand_tv.setText(data.goodsName);
        lot_number_tv.setText(data.batch+"");
        single_head_usage_tv.setText(data.singleUseNumber +"");
        number_animals_used_tv.setText(data.amount +"");
        total_usage_tv.setText(data.totalUsage +"");
        farmer_name_tv.setText(data.farm.name);
        region_name_tv.setText(data.region.regionFullName);
        Button detailsBtn = viewHolder.getView(R.id.details_btn);

        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsOutDetailsActivity.start(mContext,data.mid);
            }
        });
    }

}
