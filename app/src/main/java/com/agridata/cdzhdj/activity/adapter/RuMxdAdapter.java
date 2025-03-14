package com.agridata.cdzhdj.activity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.CollectedBean;
import com.agridata.cdzhdj.data.RuMxBean;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:04.
 */
public class RuMxdAdapter extends BaseRecyclerViewAdapter<RuMxBean, BaseRecyclerViewHolder> {

    private Context mContext;

    public RuMxdAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, RuMxBean data, final int position) {


        TextView sj_dh_tv = viewHolder.getView(R.id.sj_dh_tv);
        TextView sj_ren_tv = viewHolder.getView(R.id.sj_ren_tv);
        TextView sj_shijian_tv = viewHolder.getView(R.id.sj_shijian_tv);
        TextView sj_shul_tv = viewHolder.getView(R.id.sj_shul_tv);

        if (!TextUtils.isEmpty(data.SJNum)){
            sj_dh_tv.setText("SJ-" +  data.SJNum.substring(data.SJNum.length()-4));
        }
        sj_ren_tv.setText(data.SJPerson);
        sj_shijian_tv.setText(data.SJTimes);
        sj_shul_tv.setText(data.SJAnimalNN);

    }
}
