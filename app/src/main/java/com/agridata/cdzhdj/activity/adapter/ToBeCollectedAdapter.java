package com.agridata.cdzhdj.activity.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.RegionAdminBean;
import com.agridata.cdzhdj.data.ToBeCollectedBean;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.org.smartbluekit.BlueDevice;

import java.util.List;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:04.
 */
public class ToBeCollectedAdapter extends BaseRecyclerViewAdapter<ToBeCollectedBean.Result.PageItems, BaseRecyclerViewHolder> {

    private Context mContext;

    public ToBeCollectedAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, ToBeCollectedBean.Result.PageItems data, final int position) {
        TextView name_tv = viewHolder.getView(R.id.name_tv);
        name_tv.setText(data.userName);
        TextView username_tv = viewHolder.getView(R.id.username_tv);
        username_tv.setText(data.userName);
        TextView phone_tv = viewHolder.getView(R.id.phone_tv);
        phone_tv.setText(StrUtil.hiddenMobile(data.mobile));
        TextView address_tv = viewHolder.getView(R.id.address_tv);
        address_tv.setText(data.region.regionFullName);
        TextView times_tv = viewHolder.getView(R.id.times_tv);
        times_tv.setText(data.applyTime + "\u3000\u3000" + "申报");
        TextView source_type_tv = viewHolder.getView(R.id.source_type_tv);
        if (data.sourceType == 0) {
            source_type_tv.setText("App");
        } else {
            source_type_tv.setText("呼叫");
        }

        TextView animalCountTv = viewHolder.getView(R.id.animal_count_tv);
        String unitName = StrUtil.setAnimalUnit(data.animal.iD);
        String unitCountName = data.animal.name + data.dieAmount + unitName;
        animalCountTv.setText(unitCountName);
    }
}
