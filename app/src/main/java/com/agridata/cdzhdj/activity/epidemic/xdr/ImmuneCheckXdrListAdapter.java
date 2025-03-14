package com.agridata.cdzhdj.activity.epidemic.xdr;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.ImmuneXdrBean;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-05-09 14:00.
 * @Description :描述
 */
public class ImmuneCheckXdrListAdapter extends BaseRecyclerViewAdapter<ImmuneXdrBean.Result.PageItems, BaseRecyclerViewHolder> {

    private Context mContext;

    private OnXdrCheckListener onXdrCheckListener;


    public ImmuneCheckXdrListAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;

    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, ImmuneXdrBean.Result.PageItems data, final int position) {
        TextView name_tv = viewHolder.getView(R.id.name_tv);
        name_tv.setText(data.displayName);
        TextView username_tv = viewHolder.getView(R.id.username_tv);
        username_tv.setText(data.displayName);
        TextView phone_tv = viewHolder.getView(R.id.phone_tv);
        phone_tv.setText(StrUtil.hiddenMobile(data.mobile));
        TextView address_tv = viewHolder.getView(R.id.address_tv);
        address_tv.setText(data.addresss);
        TextView animal_type_tv = viewHolder.getView(R.id.animal_type_tv);
        TextView check_type_tv = viewHolder.getView(R.id.check_type_tv);

        TextView regionNameTv = viewHolder.getView(R.id.region_name_tv);
        regionNameTv.setText(data.region.regionFullName);

        Button checkBtn = viewHolder.getView(R.id.check_btn);
        if (data.animalVariety!=null && !data.animalVariety.isEmpty()){
            StringBuilder animalName = new StringBuilder();
            for (int i = 0; i < data.animalVariety.size(); i++) {
                animalName.append(data.animalVariety.get(i).name+
                        (i == data.animalVariety.size() - 1 ? "" : ","));
            }
            animal_type_tv.setText(animalName);
        }else {
            animal_type_tv.setText("");
        }

        Button rejectBtn = viewHolder.getView(R.id.reject_btn);



    if (data.examineStatus== 940 ){
        rejectBtn.setVisibility(View.GONE);
        checkBtn.setText("已审核");
        checkBtn.setEnabled(false);
    }else if (data.examineStatus== 942 ){
        checkBtn.setVisibility(View.GONE);
        rejectBtn.setText("已驳回");
        rejectBtn.setEnabled(false);
    }else {
        checkBtn.setOnClickListener(v -> onXdrCheckListener.check(data.mid,position));
        rejectBtn.setOnClickListener(v-> onXdrCheckListener.reject(data.mid,position));
    }

        Button editBtn = viewHolder.getView(R.id.edit_btn);
        editBtn.setOnClickListener(view -> XdrEditWebViewActivity.start(mContext,data.mid));
    }

    public void setOnXdrCheckListener(OnXdrCheckListener onXdrCheckListener) {
        this.onXdrCheckListener = onXdrCheckListener;
    }
}
