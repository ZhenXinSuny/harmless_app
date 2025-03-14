package com.agridata.cdzhdj.activity.adapter.xdr;

import android.content.Context;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.data.XdrBean;
import com.agridata.cdzhdj.data.XdrCollectListBean;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.utils.LogUtil;
import com.agridata.util.DateTimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:04.
 */
public class XdrCollectListAdapter extends BaseRecyclerViewAdapter<XdrCollectListBean.Data, BaseRecyclerViewHolder> {

    private Context mContext;



    public XdrCollectListAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;

    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, XdrCollectListBean.Data data, final int position)  {


        TextView username_tv = viewHolder.getView(R.id.username_tv);

        String workerString  = data.worker.value.replace("\\", "");
        LogUtil.d("lzx----》"," workerString" + workerString);

       TextView phone_tv = viewHolder.getView(R.id.phone_tv);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(workerString);
            String Name = jsonObject.getString("Name");
            String Mobile = jsonObject.getString("Mobile");
            username_tv.setText(Name);
            phone_tv.setText(Mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }

       TextView address_tv = viewHolder.getView(R.id.address_tv);
        address_tv.setText(data.collectNo);

       TextView animal_tv = viewHolder.getView(R.id.animal_tv);
        JSONObject jsonObjectAnimal = null;
        try {
            jsonObjectAnimal = new JSONObject(data.animal.value.replace("\\", ""));
            String AnimalName = jsonObjectAnimal.getString("AnimalName");
            animal_tv.setText(AnimalName);

        } catch (JSONException e) {
            e.printStackTrace();
        }




        TextView animal_num_tv = viewHolder.getView(R.id.animal_num_tv);
        JSONObject jsonObjectUnit = null;
        try {
            jsonObjectUnit = new JSONObject(data.animalUnit.value.replace("\\", ""));
            String UnitName = jsonObjectUnit.getString("UnitName");
            animal_num_tv.setText(data.dieAmount + UnitName);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        TextView animal_weight_tv = viewHolder.getView(R.id.animal_weight_tv);

        animal_weight_tv.setText(data.dieWeight+"KG");

    }
}
