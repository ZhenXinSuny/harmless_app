package com.agridata.cdzhdj.activity.adapter.immuneXdr;

import android.content.Context;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.ImmuneXdrBean;
import com.agridata.cdzhdj.data.XdrBean;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.util.DateTimeUtils;

import java.text.ParseException;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-11-28 16:55.
 * @Description :描述
 */
public class ImmuneXdrListAdapter extends BaseRecyclerViewAdapter<ImmuneXdrBean.Result.PageItems, BaseRecyclerViewHolder> {


    public ImmuneXdrListAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, ImmuneXdrBean.Result.PageItems data, final int position) {
        TextView nameTv = viewHolder.getView(R.id.name_tv);
        nameTv.setText(data.displayName);
        TextView usernameTv = viewHolder.getView(R.id.username_tv);
        usernameTv.setText(data.displayName);
        TextView phoneTv = viewHolder.getView(R.id.phone_tv);
        String phone = data.legalPersonTel != null ? data.legalPersonTel : data.mobile;
        phoneTv.setText(phone);
        TextView addressTv = viewHolder.getView(R.id.address_tv);
        addressTv.setText(data.addresss);
        TextView animalTypeTv = viewHolder.getView(R.id.animal_type_tv);
        TextView regionNameTv = viewHolder.getView(R.id.region_name_tv);
        regionNameTv.setText(data.region.regionFullName);
        if (data.animalVariety!=null && !data.animalVariety.isEmpty()){
            StringBuilder animalName = new StringBuilder();
            for (int i = 0; i < data.animalVariety.size(); i++) {
                animalName.append(data.animalVariety.get(i).name).append(i == data.animalVariety.size() - 1 ? "" : ",");
            }
            animalTypeTv.setText(animalName);
        }else {
            animalTypeTv.setText("");
        }
    }
}
