package com.agridata.cdzhdj.activity.epidemic.eartag.lowble;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-22 16:50.
 * @Description :描述
 */
public class LowScanEarTagInfoAdapter extends BaseRecyclerViewAdapter<String, BaseRecyclerViewHolder> {

    private Context mContext;
    private onEarTagDelListener mOnEarTagDelListener;

    public LowScanEarTagInfoAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, String data, int position) {
        TextView name_tv = viewHolder.getView(R.id.name_tv);
        ImageView imageView   = viewHolder.getView(R.id.del);
        name_tv.setText(data);
        imageView.setOnClickListener(v -> mOnEarTagDelListener.onDel( position));
    }

    public void setmOnEarTagDelListener(onEarTagDelListener mOnEarTagDelListener) {
        this.mOnEarTagDelListener = mOnEarTagDelListener;
    }


    public interface onEarTagDelListener {

        void  onDel(int pos);
    }

}
