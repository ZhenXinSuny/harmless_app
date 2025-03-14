package com.agridata.cdzhdj.activity.adapter.xdr;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.CollectedBean;
import com.agridata.cdzhdj.data.XdrBean;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.util.DateTimeUtils;

import java.text.ParseException;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:04.
 */
public class XdrListAdapter extends BaseRecyclerViewAdapter<XdrBean.Data, BaseRecyclerViewHolder> {

    private Context mContext;



    public XdrListAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;

    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, XdrBean.Data data, final int position)  {
        TextView name_tv = viewHolder.getView(R.id.name_tv);
        name_tv.setText(data.displayName);
        TextView username_tv = viewHolder.getView(R.id.username_tv);
        username_tv.setText(data.displayName);
        TextView phone_tv = viewHolder.getView(R.id.phone_tv);

        String phone = data.legalPersonTel != null ? data.legalPersonTel : data.mobile;
        phone_tv.setText(phone);
        TextView address_tv = viewHolder.getView(R.id.address_tv);
        address_tv.setText(data.addresss);
        TextView whhcount_tv = viewHolder.getView(R.id.whhcount_tv);
        whhcount_tv.setText(data.whhcount + "");


        TextView times_tv = viewHolder.getView(R.id.times_tv);
        try {
            times_tv.setText(DateTimeUtils.dealDateFormat(data.create_at));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
