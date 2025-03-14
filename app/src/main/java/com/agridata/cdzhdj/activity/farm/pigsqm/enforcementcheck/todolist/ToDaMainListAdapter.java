package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.todolist;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.law.EnforcementHomeData;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.lzx.utils.RxToast;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-17 15:07.
 * @Description :描述
 */
public class ToDaMainListAdapter extends BaseRecyclerViewAdapter<EnforcementHomeData.Result, BaseRecyclerViewHolder> {

    private Context mContext;


    private onFuChaListener fuChaListener;

    private onQualifiedListener qualifiedListener;



    public ToDaMainListAdapter(int layoutResId, Context mContext) {
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


        TextView typeTv = viewHolder.getView(R.id.type_tv);
        Button assignBtn = viewHolder.getView(R.id.assign_btn);
        RelativeLayout fuLl = viewHolder.getView(R.id.fu_ll);
        LinearLayout xiebanrenLl = viewHolder.getView(R.id.xiebanren_ll);


        Button okBtn = viewHolder.getView(R.id.ok_btn);


        if (data.checkType==1){
            typeTv.setText("填报");
            xiebanrenTv.setText(data.coOrganizer.name);
            if (data.reviewStatus==0){
                fuLl.setBackground(AppCompatResources.getDrawable(mContext,R.drawable.bg_no_sj));
            }else {
                fuLl.setBackground(AppCompatResources.getDrawable(mContext,R.drawable.bg_ok_sj));
            }
        }else {
            typeTv.setText("抽查");
            assignBtn.setVisibility(View.GONE);
            fuLl.setVisibility(View.INVISIBLE);
            xiebanrenLl.setVisibility(View.GONE);
            okBtn.setVisibility(View.VISIBLE);
        }


        if (data.reviewStatus==1){
            assignBtn.setText("已复查");
            assignBtn.setClickable(true);
            assignBtn.setBackground(AppCompatResources.getDrawable(mContext,R.drawable.bg_assignment_no));
            assignBtn.setOnClickListener(v -> RxToast.warning(mContext,"该条信息已复查"));

        }else {
            assignBtn.setBackground(AppCompatResources.getDrawable(mContext,R.drawable.login_btn_bg));
            assignBtn.setText("复查");
            assignBtn.setClickable(true);
            assignBtn.setOnClickListener(v -> fuChaListener.fuCha(data.mid,position));
        }

        indexTv.setText(position +1 +"");
        zhubanrenTv.setText(data.organizer.name);

        unitNameTv.setText(data.companyName);
        telTv.setText(data.tel);
        commitTimeTv.setText(data.createAtStr.substring(0,19));

        if (data.spotCheckStatus==1){
            okBtn.setBackground(AppCompatResources.getDrawable(mContext,R.drawable.bg_assignment_no));
            okBtn.setOnClickListener(v -> RxToast.warning(mContext,"该条信息已进行抽查结果登记"));
        }else {
            okBtn.setOnClickListener(v -> qualifiedListener.qualified(data.mid,position));
        }
    }
    public void setFuChaListener(onFuChaListener linstener) {
        this.fuChaListener = linstener;
    }

    public void setQualifiedListener(onQualifiedListener linstener) {
        this.qualifiedListener = linstener;
    }


}
