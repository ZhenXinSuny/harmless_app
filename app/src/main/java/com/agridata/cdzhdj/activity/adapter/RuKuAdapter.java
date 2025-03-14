package com.agridata.cdzhdj.activity.adapter;

import android.content.Context;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.CollectedBean;
import com.agridata.cdzhdj.data.RuKuBean;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:04.
 */
public class RuKuAdapter extends BaseRecyclerViewAdapter<RuKuBean.Result.PageItems, BaseRecyclerViewHolder> {

    private Context mContext;

    public RuKuAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, RuKuBean.Result.PageItems data, final int position) {
        TextView whhName = viewHolder.getView(R.id.clc_name_tv);
        whhName.setText(data.dep_FactoryGUID.disposeName);

        TextView ru_cp_tv = viewHolder.getView(R.id.ru_cp_tv);
        ru_cp_tv.setText(data.carInfo==null?"":data.carInfo.name);

        TextView ru_person_tv = viewHolder.getView(R.id.ru_person_tv);
        ru_person_tv.setText(data.collectStockUser.name);

        TextView ru_num_tv = viewHolder.getView(R.id.ru_num_tv);
        ru_num_tv.setText("入" + data.itemDatas.size()+"单");

        TextView ru_times_tv = viewHolder.getView(R.id.ru_times_tv);
        ru_times_tv.setText(data.createAtStr.substring(0,19));

        TextView sj_zt_tv = viewHolder.getView(R.id.sj_zt_tv);
        if (data.checkStatus.equals("0")) {
            sj_zt_tv.setBackground(mContext.getDrawable(R.drawable.textview_border_no_status));
            sj_zt_tv.setText("未审核");
        } else if (data.checkStatus.equals("1")){
            sj_zt_tv.setBackground(mContext.getDrawable(R.drawable.textview_border_ruku));
            sj_zt_tv.setText("已审核");
        }

        TextView animalCountTv = viewHolder.getView(R.id.animal_count_tv);


    }
}
