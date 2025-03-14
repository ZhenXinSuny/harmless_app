package com.agridata.cdzhdj.activity.adapter.ble;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.ScanBleBean;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-01 09:11.
 * @Description :描述
 */
public class ScanBleTypeAdapter extends BaseRecyclerViewAdapter<ScanBleBean, BaseRecyclerViewHolder> {

    private Context mContext;

    public ScanBleTypeAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder,ScanBleBean data, final int position) {


        TextView tv_scanname = viewHolder.getView(R.id.tv_scanname);

        tv_scanname.setText(data.Name);

    }

}
