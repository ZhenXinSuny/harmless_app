package com.agridata.cdzhdj.activity.adapter;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.AnimalMenuBean;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.utils.LogUtil;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 15:31.
 */
public class AnimalMenuAdapter extends BaseRecyclerViewAdapter<AnimalMenuBean, BaseRecyclerViewHolder> {

    private Context mContext;
    private onDelListener mOnDelListener;

    public AnimalMenuAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    public void setDel(onDelListener linstener) {
        this.mOnDelListener = linstener;
    }
    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, AnimalMenuBean data, int position) {
        LogUtil.d("lzx----》",position +"") ;
        TextView earTag_tv = viewHolder.getView(R.id.earTag_tv);
        TextView zhongliang_tv = viewHolder.getView(R.id.zhongliang_tv);
        Button  del = viewHolder.getView(R.id.del_btn);
        TextView dongwu_tv = viewHolder.getView(R.id.dongwu_tv);
        earTag_tv.setText(data.EarTag);
        zhongliang_tv.setText(data.Weight+"kg");
        dongwu_tv.setText(data.AnimalName);
        del.setOnClickListener(v -> mOnDelListener.delete(position));
    }
}
