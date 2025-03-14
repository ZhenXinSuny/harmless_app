package com.agridata.cdzhdj.activity.harmless.offlinesubmission;

import android.content.Context;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.CollectInfoData;
import com.agridata.cdzhdj.data.CollectedDetailBean;
import com.agridata.cdzhdj.data.db.bean.CollectionDBModel;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.utils.LogUtil;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-05-06 14:48.
 * @Description :描述
 */
public class OfflineAnimalAdapter extends BaseRecyclerViewAdapter<CollectInfoData.DataItemBean, BaseRecyclerViewHolder> {

    private Context mContext;

    public OfflineAnimalAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, CollectInfoData.DataItemBean data, int position) {
        TextView earTag_tv = viewHolder.getView(R.id.earTag_tv);
        TextView zhongliang_tv = viewHolder.getView(R.id.zhongliang_tv);
        TextView dongwu_tv = viewHolder.getView(R.id.dongwu_tv);
        earTag_tv.setText(data.EarTagNo);
        zhongliang_tv.setText(data.BodyWeight + "kg");
        dongwu_tv.setText(data.Name);

    }

}
