package com.agridata.cdzhdj.activity.epidemic.immune.query;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.data.QueryImmuneBean;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-23 13:34.
 * @Description :描述
 */
public class QueryImmuneAdapter extends BaseRecyclerViewAdapter<QueryImmuneBean.Result.PageItems, BaseRecyclerViewHolder> {

    private Context mContext;


    public QueryImmuneAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;

    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, QueryImmuneBean.Result.PageItems data, final int position) {
        TextView xdr_tv = viewHolder.getView(R.id.xdr_tv);
        TextView fyy_tv = viewHolder.getView(R.id.fyy_tv);
        TextView animal_tv = viewHolder.getView(R.id.animal_tv);
        TextView immune_count_tv = viewHolder.getView(R.id.immune_count_tv);
        TextView times_tv = viewHolder.getView(R.id.times_tv);

        TextView region_tv = viewHolder.getView(R.id.region_tv);


        TextView mylx_tv = viewHolder.getView(R.id.mylx_tv);

        if (!TextUtils.isEmpty(data.xDRCoreInfo.name)){
            xdr_tv.setText(data.xDRCoreInfo.name);
        }

        if (data.isSelfWrite==1010){
            fyy_tv.setText(data.aHIUser.name);
        }else {
            fyy_tv.setText(UserDataSP.getInstance().getUserInfo().Result.name);
        }
        animal_tv.setText(data.animal.name);
        immune_count_tv.setText(data.immuneCount +"");
        times_tv.setText(data.immuned);
        region_tv.setText(data.region.regionFullName);

        if (data.immuneType==1007){
            mylx_tv.setText("首次免疫");
            mylx_tv.setBackground(mContext.getDrawable(R.drawable.bg_immune_patcb_text));
        }else {
            mylx_tv.setText("再次免疫");
            mylx_tv.setBackground(mContext.getDrawable(R.drawable.bg_immune_patcb_text_again));
        }

    }
}
