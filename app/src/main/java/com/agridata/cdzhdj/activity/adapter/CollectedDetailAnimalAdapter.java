package com.agridata.cdzhdj.activity.adapter;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.AnimalMenuBean;
import com.agridata.cdzhdj.data.CollectedBean;
import com.agridata.cdzhdj.data.CollectedDetailBean;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.utils.LogUtil;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:04.
 */
public class CollectedDetailAnimalAdapter  extends BaseRecyclerViewAdapter<CollectedDetailBean.Result.ItemDatas, BaseRecyclerViewHolder> {

    private Context mContext;
    public CollectedDetailAnimalAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, CollectedDetailBean.Result.ItemDatas data, int position) {
        LogUtil.d("lzx----》",position +"") ;
        TextView earTag_tv = viewHolder.getView(R.id.earTag_tv);
        TextView zhongliang_tv = viewHolder.getView(R.id.zhongliang_tv);
        TextView dongwu_tv = viewHolder.getView(R.id.dongwu_tv);
        earTag_tv.setText(data.earTagNo);
        zhongliang_tv.setText(data.bodyWeight+"kg");
        dongwu_tv.setText(data.name);

    }
}
