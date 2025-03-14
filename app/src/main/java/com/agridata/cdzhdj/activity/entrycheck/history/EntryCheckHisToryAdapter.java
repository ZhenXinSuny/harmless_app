package com.agridata.cdzhdj.activity.entrycheck.history;


import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckHistoryData;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-11-20 14:49.
 * @Description :描述
 */
public class EntryCheckHisToryAdapter extends BaseRecyclerViewAdapter<EntryCheckHistoryData.ResultDTO.PageItemsDTO, BaseRecyclerViewHolder> {

    private Context mContext;

    private mOnEntryCheckListener mOnEntryCheckListener;

    public void setOnEntryCheckListener(mOnEntryCheckListener listener) {
        this.mOnEntryCheckListener = listener;
    }
    public EntryCheckHisToryAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, EntryCheckHistoryData.ResultDTO.PageItemsDTO data, final int position) {
        TextView owner_tv = viewHolder.getView(R.id.owner_tv);
        TextView slaughterhouse_tv = viewHolder.getView(R.id.slaughterhouse_tv);
        TextView region_name_tv = viewHolder.getView(R.id.region_name_tv);
        TextView animal_tv = viewHolder.getView(R.id.animal_tv);
        TextView car_tv = viewHolder.getView(R.id.car_tv);
        TextView count_tv = viewHolder.getView(R.id.count_tv);
        TextView inspection_status_tv = viewHolder.getView(R.id.inspection_status_tv);
        TextView check_time_tv = viewHolder.getView(R.id.check_time_tv);
        TextView cert_num_tv = viewHolder.getView(R.id.cert_num_tv);


        Button mLogBtn = viewHolder.getView(R.id.log_btn);
        Button mReviseBtn = viewHolder.getView(R.id.revise_btn);
        mLogBtn.setOnClickListener(view -> mOnEntryCheckListener.mOnViewLog(position));
        mReviseBtn.setOnClickListener(view -> mOnEntryCheckListener.mOnViewModify(position));

        owner_tv.setText(data.owner);
        slaughterhouse_tv.setText(data.slaughterHouseName);
        region_name_tv.setText(data.region.regionFullName);
        animal_tv.setText(data.animalName);
        car_tv.setText(data.carNumber);
        count_tv.setText(data.counts +"");
        check_time_tv.setText(data.checkTime);
        cert_num_tv.setText(data.certNo);
        if (data.checkResult==1){
            inspection_status_tv.setText("合格");
            inspection_status_tv.setTextColor(ContextCompat.getColor(mContext,R.color.J5));
        }else {
            inspection_status_tv.setText("不合格");
            inspection_status_tv.setTextColor(ContextCompat.getColor(mContext,R.color.Red));
        }

    }
}
