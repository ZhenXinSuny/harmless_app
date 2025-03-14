package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.ImageViewData;
import com.agridata.cdzhdj.data.law.EnforcementHomeData;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-31 15:31.
 * @Description :描述
 */
public class EnforcementMianAdapter extends BaseRecyclerViewAdapter<EnforcementHomeData.Result, BaseRecyclerViewHolder> {

    private Context mContext;


    public EnforcementMianAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, EnforcementHomeData.Result data, int position) {
        TextView zhubanrenTv = viewHolder.getView(R.id.zhubanren_tv);
        TextView xiebanrenTv = viewHolder.getView(R.id.xiebanren_tv);
        TextView unitNameTv = viewHolder.getView(R.id.unit_name_tv);
        TextView telTv = viewHolder.getView(R.id.tel_tv);
        TextView commitTimeTv = viewHolder.getView(R.id.commit_time_tv);
        TextView indexTv = viewHolder.getView(R.id.index_tv);
        LinearLayout fuLl = viewHolder.getView(R.id.fu_ll);
        LinearLayout xiebanrenLl = viewHolder.getView(R.id.xiebanren_ll);

        if (data.checkType == 1) {
            fuLl.setVisibility(View.GONE);
            xiebanrenLl.setVisibility(View.VISIBLE);
            xiebanrenTv.setText(data.coOrganizer.name);
        } else  {
            fuLl.setVisibility(View.GONE);
            xiebanrenLl.setVisibility(View.GONE);
        }
        indexTv.setText(position +1 +"");
        zhubanrenTv.setText(data.organizer.name);

        unitNameTv.setText(data.companyName);
        telTv.setText(data.tel);
        commitTimeTv.setText(data.createAtStr);
    }

}
