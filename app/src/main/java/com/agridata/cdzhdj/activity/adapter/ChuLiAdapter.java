package com.agridata.cdzhdj.activity.adapter;

import android.content.Context;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.ChuLiBean;
import com.agridata.cdzhdj.data.RuKuBean;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:04.
 */
public class ChuLiAdapter extends BaseRecyclerViewAdapter<ChuLiBean.Result.PageItems, BaseRecyclerViewHolder> {

    private Context mContext;

    public ChuLiAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, ChuLiBean.Result.PageItems data, final int position) {
        TextView chuliren_name_tv = viewHolder.getView(R.id.chuliren_name_tv);
        chuliren_name_tv.setText(data.handleUser.name);

        TextView cldn_tv = viewHolder.getView(R.id.cldn_tv);
        cldn_tv.setText(data.harmlessNo);

        TextView cl_type_tv = viewHolder.getView(R.id.cl_type_tv);//画质方式
        cl_type_tv.setText(data.handleCategory.name);

        TextView cl_times_tv = viewHolder.getView(R.id.cl_times_tv);
        cl_times_tv.setText(data.createAtStr.substring(0,19));

        TextView zt_tv = viewHolder.getView(R.id.zt_tv);
        if (data.checkStatus.equals("0")) {
            zt_tv.setBackground(mContext.getDrawable(R.drawable.textview_border_no_status));
            zt_tv.setText("未审核");
        } else if (data.checkStatus.equals("1")){
            zt_tv.setBackground(mContext.getDrawable(R.drawable.textview_border_chuli));
            zt_tv.setText("已审核");
        }

    }
}
