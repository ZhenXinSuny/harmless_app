package com.agridata.cdzhdj.activity.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.CollectedBean;
import com.agridata.cdzhdj.data.ToBeCollectedBean;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:04.
 */
public class CollectedAdapter extends BaseRecyclerViewAdapter<CollectedBean.Result.PageItems, BaseRecyclerViewHolder> {

    private Context mContext;

    private int mType;

    public CollectedAdapter(int layoutResId, Context mContext,int Type) {
        super(layoutResId, null);
        this.mContext = mContext;
        this.mType = Type;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, CollectedBean.Result.PageItems data, final int position) {
        TextView name_tv = viewHolder.getView(R.id.name_tv);
        name_tv.setText(data.dep_ApplyGUID.userName);
        TextView username_tv = viewHolder.getView(R.id.username_tv);
        username_tv.setText(data.dep_ApplyGUID.userName);
        TextView phone_tv = viewHolder.getView(R.id.phone_tv);
        phone_tv.setText(StrUtil.hiddenMobile(data.dep_ApplyGUID.mobile));
        TextView address_tv = viewHolder.getView(R.id.address_tv);
        address_tv.setText(data.region.regionFullName);
        TextView times_tv = viewHolder.getView(R.id.times_tv);
        times_tv.setText(data.dep_ApplyGUID.applyTime+"\u3000" + "申报");


        TextView sj_person_tv = viewHolder.getView(R.id.sj_person_tv);
        sj_person_tv.setText(data.worker.name);

        TextView sj_car_num_tv = viewHolder.getView(R.id.sj_car_num_tv);
        sj_car_num_tv.setText(data.carInfo.name);

        TextView sj_times_tv = viewHolder.getView(R.id.sj_times_tv);
        sj_times_tv.setText(data.createAtStr.substring(0,19) +"\u3000" + "收集");

        TextView check_times_tv = viewHolder.getView(R.id.check_times_tv);


        TextView sj_zt_tv = viewHolder.getView(R.id.sj_zt_tv);
        if (data.checkStatus == 0) {
            sj_zt_tv.setBackground(mContext.getDrawable(R.drawable.textview_border_no_status));
            sj_zt_tv.setText("未审核");
            check_times_tv.setVisibility(View.GONE);
        } else if (data.checkStatus == 1) {
            sj_zt_tv.setBackground(mContext.getDrawable(R.drawable.textview_border));
            sj_zt_tv.setText("已审核");
            if (mType==666){
                check_times_tv.setVisibility(View.VISIBLE);
                check_times_tv.setText(data.checkTime +"\u3000" + "审核");
            }else {
                check_times_tv.setVisibility(View.GONE);
            }
        }else {
            sj_zt_tv.setBackground(mContext.getDrawable(R.drawable.textview_border_no_status));
            sj_zt_tv.setText("已驳回");
            check_times_tv.setVisibility(View.GONE);
        }

        TextView animalCountTv = viewHolder.getView(R.id.animal_count_tv);
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
