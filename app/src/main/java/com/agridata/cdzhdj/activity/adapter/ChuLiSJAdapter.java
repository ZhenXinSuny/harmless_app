package com.agridata.cdzhdj.activity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.RuKuDetailBean;
import com.agridata.cdzhdj.data.RuMxBean;
import com.agridata.cdzhdj.data.whh.ShouJiListForMidBean;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:04.
 */
public class ChuLiSJAdapter extends BaseRecyclerViewAdapter<ShouJiListForMidBean.Result, BaseRecyclerViewHolder> {

    private Context mContext;

    public ChuLiSJAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, ShouJiListForMidBean.Result data, final int position) {


        TextView sj_dh_tv = viewHolder.getView(R.id.sj_dh_tv);
        TextView sjr_tv = viewHolder.getView(R.id.sjr_tv);
        TextView sj_times = viewHolder.getView(R.id.sj_times);
        TextView sjcp_tv = viewHolder.getView(R.id.sjcp_tv);
        TextView sj_shuliang_tv = viewHolder.getView(R.id.sj_shuliang_tv);

        sj_dh_tv.setText(data.collectNo);
        sjr_tv.setText(data.worker.name);
        sj_times.setText(data.createAtStr.substring(0,19));
        sjcp_tv.setText(data.carInfo.name);
        sj_shuliang_tv.setText(data.animal.animalName+ data.dieAmount+ data.animalUnit.unitName);
    }
}
