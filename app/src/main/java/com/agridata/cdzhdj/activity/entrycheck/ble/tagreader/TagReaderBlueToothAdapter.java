package com.agridata.cdzhdj.activity.entrycheck.ble.tagreader;

import android.content.Context;
import android.widget.TextView;


import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.org.smartbluekit.BlueDevice;

/**
 * @auther 56454
 * @date 2022/1/10
 * @time 9:05.
 */
public class TagReaderBlueToothAdapter extends BaseRecyclerViewAdapter<BlueDevice, BaseRecyclerViewHolder> {

    private Context mContext;

    public TagReaderBlueToothAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, BlueDevice data, final int position) {
        TextView tv_scanname = viewHolder.getView(R.id.tv_scanname);
        tv_scanname.setText(data.getDeviceType() + "["  + data.getDeviceMAC() +"]");

    }
}
