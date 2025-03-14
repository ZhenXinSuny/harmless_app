package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.reviewassignment;

import android.content.Context;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.law.EnforcementHomeData;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-17 15:07.
 * @Description :描述
 */
public class ReviewAssignmentMainAdapter extends BaseRecyclerViewAdapter<EnforcementHomeData.Result, BaseRecyclerViewHolder> {

    private Context mContext;
    private onAssignmentPersonListener assignmentPersonListener;

    public ReviewAssignmentMainAdapter(int layoutResId, Context mContext) {
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
        TextView lingyuTv = viewHolder.getView(R.id.lingyu_tv);

        Button assignBtn = viewHolder.getView(R.id.assign_btn);
        RelativeLayout fuLl = viewHolder.getView(R.id.fu_ll);


        if (data.reviewStatus==0){
            fuLl.setBackground(AppCompatResources.getDrawable(mContext,R.drawable.bg_no_sj));
        }else {
            fuLl.setBackground(AppCompatResources.getDrawable(mContext,R.drawable.bg_ok_sj));
        }

        if (data.assignmentStatus==1){
            assignBtn.setText("已指派");
            assignBtn.setClickable(false);
            assignBtn.setBackground(AppCompatResources.getDrawable(mContext,R.drawable.bg_assignment_no));

        }else {
            assignBtn.setBackground(AppCompatResources.getDrawable(mContext,R.drawable.login_btn_bg));
            assignBtn.setText("指派人员");
            assignBtn.setClickable(true);
        }

        xiebanrenTv.setText(data.coOrganizer.name);
        lingyuTv.setText(data.inspectionField.name);

        indexTv.setText(position +1 +"");
        zhubanrenTv.setText(data.organizer.name);

        unitNameTv.setText(data.companyName);
        telTv.setText(data.tel);
        commitTimeTv.setText(data.createAtStr);


        assignBtn.setOnClickListener(v-> assignmentPersonListener.assignment(data.mid,position,data.assignmentStatus));
    }

    public void setAssignmentPersonListener(onAssignmentPersonListener linstener) {
        this.assignmentPersonListener = linstener;
    }
}
