package com.agridata.cdzhdj.activity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.ChuLiDetailBean;
import com.agridata.cdzhdj.data.RuMxBean;
import com.agridata.cdzhdj.data.whh.RuKuListForMidBean;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:04.
 */
public class ChuLiMxdAdapter extends BaseRecyclerViewAdapter<RuKuListForMidBean.Result, BaseRecyclerViewHolder> {

    private Context mContext;

    public ChuLiMxdAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, RuKuListForMidBean.Result data, final int position) {


        TextView ruku_dh_tv = viewHolder.getView(R.id.ruku_dh_tv);
        TextView rkr_tv = viewHolder.getView(R.id.rkr_tv);
        TextView rk_times = viewHolder.getView(R.id.rk_times);
        TextView rukusl_tv = viewHolder.getView(R.id.rukusl_tv);
        ruku_dh_tv.setText(data.stockNo);//单号
        rkr_tv.setText(data.collectStockUser.name);//入库人
        rk_times.setText(data.createAtStr.substring(0,19));//时间
        rukusl_tv.setText(data.disinfect);//数量


    }
}
