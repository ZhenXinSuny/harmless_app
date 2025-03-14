package com.agridata.cdzhdj.activity.epidemic.eartag.lowble;

import android.content.Context;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.LowBleData;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-22 17:17.
 * @Description :描述
 */
public class ConnBlueToothAdapter  extends BaseRecyclerViewAdapter<LowBleData, BaseRecyclerViewHolder> {

    private Context mContext;

    public ConnBlueToothAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, LowBleData data, final int position) {

        TextView tv_scanname = viewHolder.getView(R.id.tv_scanname);
        tv_scanname.setText(data.getAddress());

    }
}
