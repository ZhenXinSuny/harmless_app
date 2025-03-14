package com.agridata.cdzhdj.activity.entrycheck.ble.tagreader;

import android.content.Context;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-28 10:08.
 * @Description :描述
 */
public class LowScanEarTagBleInfoAdapter extends BaseRecyclerViewAdapter<String, BaseRecyclerViewHolder> {

    private Context mContext;


    public LowScanEarTagBleInfoAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, String data, int position) {
        TextView name_tv = viewHolder.getView(R.id.name_tv);

        name_tv.setText(data);

    }
}
