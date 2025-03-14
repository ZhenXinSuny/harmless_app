package com.agridata.cdzhdj.activity.harmless.xdrbind.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.XdrBindListData;
import com.agridata.cdzhdj.data.whh.FarmXdrInfoListByUserIdAndMobileBean;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

import java.util.HashMap;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-02-27 14:34.
 * @Description :描述
 */
public class XdrBindListQueryAdapter extends BaseRecyclerViewAdapter<FarmXdrInfoListByUserIdAndMobileBean.Data.FarmList, BaseRecyclerViewHolder> {

    private Context mContext;
    /**
     * 單選
     */
    private HashMap<Integer, XdrBindListData.DataBean.FarmListBean> hashMap = new HashMap<>();

    public XdrBindListQueryAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;

    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, FarmXdrInfoListByUserIdAndMobileBean.Data.FarmList data, final int position) {
        TextView name_tv = viewHolder.getView(R.id.name_tv);
        name_tv.setText(data.displayName);
        TextView username_tv = viewHolder.getView(R.id.username_tv);
        username_tv.setText(data.displayName);
        TextView phone_tv = viewHolder.getView(R.id.phone_tv);
        phone_tv.setText(StrUtil.hiddenMobile(data.mobile));
        TextView address_tv = viewHolder.getView(R.id.address_tv);
        address_tv.setText(data.region.regionFullName);
        TextView animal_type_tv = viewHolder.getView(R.id.animal_type_tv);
        TextView region_name_tv = viewHolder.getView(R.id.region_name_tv);
        region_name_tv.setText(data.region.regionFullName);
        if (data.animalVariety != null && data.animalVariety.size() > 0) {
            StringBuilder animalName = new StringBuilder();
            for (int i = 0; i < data.animalVariety.size(); i++) {
                animalName.append(data.animalVariety.get(i).name +
                        (i == data.animalVariety.size() - 1 ? "" : ","));
            }
            animal_type_tv.setText(animalName);
        }else {
            animal_type_tv.setText("");
        }
        LinearLayout status_Ll = viewHolder.getView(R.id.status_Ll);
        TextView bind_type_tv = viewHolder.getView(R.id.bind_type_tv);

        status_Ll.setVisibility(View.VISIBLE);

        if (data.isBind){
            bind_type_tv.setText("已关联");
            bind_type_tv.setTextColor(mContext.getResources().getColor(R.color.J5));
        }else {
            bind_type_tv.setText("未关联");
            bind_type_tv.setTextColor(mContext.getResources().getColor(R.color.Red));
        }
//        LinearLayout perfect_Ll = viewHolder.getView(R.id.perfect_Ll);
//        TextView perfect_tv = viewHolder.getView(R.id.perfect_tv);
//        perfect_Ll.setVisibility(View.VISIBLE);
//        if (data.isNaturalLegal){
//            perfect_tv.setText("已完善法人/自然人信息");
//            perfect_tv.setTextColor(mContext.getResources().getColor(R.color.J5));
//        }else {
//            perfect_tv.setText("未完善法人/自然人信息");
//            perfect_tv.setTextColor(mContext.getResources().getColor(R.color.Red));
//        }

    }

}