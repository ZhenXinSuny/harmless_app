package com.agridata.cdzhdj.activity.harmless.xdrbind.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.ImmuneXdrBean;
import com.agridata.cdzhdj.data.XdrBindListData;
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
public class XdrBindListAdapter extends BaseRecyclerViewAdapter<XdrBindListData.DataBean.FarmListBean, BaseRecyclerViewHolder> {

    private Context mContext;
    /**
     * 單選
     */
    private HashMap<Integer, XdrBindListData.DataBean.FarmListBean> hashMap = new HashMap<>();

    public XdrBindListAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;

    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, XdrBindListData.DataBean.FarmListBean data, final int position) {
        TextView name_tv = viewHolder.getView(R.id.name_tv);
        name_tv.setText(data.displayName);
        TextView username_tv = viewHolder.getView(R.id.username_tv);
        username_tv.setText(data.displayName);
        TextView phone_tv = viewHolder.getView(R.id.phone_tv);
        if (data.xDRType==2){
            phone_tv.setText(StrUtil.hiddenMobile(data.legalPersonTel));
        }else {
            phone_tv.setText(StrUtil.hiddenMobile(data.mobile));
        }

        TextView address_tv = viewHolder.getView(R.id.address_tv);
        address_tv.setText(data.region.regionFullName);
        TextView animal_type_tv = viewHolder.getView(R.id.animal_type_tv);


        int layoutPosition = viewHolder.getLayoutPosition();
        ImageView imageView = viewHolder.getView(R.id.xdr_check_iv);
        XdrBindListData.DataBean.FarmListBean bean = hashMap.get(layoutPosition);
        imageView.setImageResource(null != bean ? R.drawable.ic_check_eartag : R.drawable.ic_check_eartag_unchecked);


        if (data.animalVariety != null && !data.animalVariety.isEmpty()) {
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
        status_Ll.setVisibility(View.GONE);

        bind_type_tv.setVisibility(View.GONE);
//        if (data.isBind){
//            bind_type_tv.setText("已关联");
//            bind_type_tv.setTextColor(mContext.getResources().getColor(R.color.J5));
//        }else {
//            bind_type_tv.setText("未关联");
//            bind_type_tv.setTextColor(mContext.getResources().getColor(R.color.Red));
//        }
    }


    //单选 复选删除 再次选择
    public void singleChoice(int position, XdrBindListData.DataBean.FarmListBean goodsBean) {
        hashMap.clear();
        hashMap.put(position, goodsBean);
        notifyDataSetChanged();
    }

    public HashMap<Integer, XdrBindListData.DataBean.FarmListBean> getHashMap() {
        return hashMap;
    }
}