package com.agridata.cdzhdj.activity.harmless.offlinesubmission;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.harmless.collection.onCollectionListener;
import com.agridata.cdzhdj.data.CollectInfoData;
import com.agridata.cdzhdj.data.CollectedBean;
import com.agridata.cdzhdj.data.db.bean.CollectionDBModel;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-05-06 11:20.
 * @Description :描述
 */
public class OfflineSubmissionCollectionAdapter extends BaseRecyclerViewAdapter<CollectionDBModel, BaseRecyclerViewHolder> {

    private Context mContext;

    private onCollectionListener collectionListener;

    public OfflineSubmissionCollectionAdapter(int layoutResId, Context mContext) {
        super(layoutResId, null);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, CollectionDBModel data, final int position) {
        TextView collectionNoTv = viewHolder.getView(R.id.collection_no_tv);
        collectionNoTv.setText(data.collectNo);
        TextView xdrNameTv = viewHolder.getView(R.id.xdr_name_tv);
        xdrNameTv.setText(data.xdrName);
        TextView xdrIphoneNumTv = viewHolder.getView(R.id.xdr_iphone_num_tv);
        xdrIphoneNumTv.setText(data.xdrMobile);
        TextView applyRegionNameTv = viewHolder.getView(R.id.apply_region_name_tv);
        applyRegionNameTv.setText(data.xdrAddress);
        TextView carNumTv = viewHolder.getView(R.id.car_num_tv);
        carNumTv.setText(data.carInfo.Name);
        TextView collectionTimesTv = viewHolder.getView(R.id.collection_times_tv);
        collectionTimesTv.setText(data.collectionTime);


        TextView animalTv = viewHolder.getView(R.id.animal_tv);
        String unitName = StrUtil.setAnimalUnitCollected(Integer.parseInt(data.animal.key));
        if (Integer.parseInt(data.animal.key) == 1 || Integer.parseInt(data.animal.key) == 2 || Integer.parseInt(data.animal.key) == 3) {
            String unitCountName = data.animal.AnimalName + data.dieAmount + unitName;
            animalTv.setText(unitCountName);
        } else {
            String unitCountName = data.animal.AnimalName + data.dieWeight + unitName;
            animalTv.setText(unitCountName);
        }
        Button checkBtn = viewHolder.getView(R.id.check_btn);
        checkBtn.setOnClickListener(view -> {
            collectionListener.collection(position);
        });

        Button detailBtn = viewHolder.getView(R.id.detail_btn);
        detailBtn.setOnClickListener(view -> OfflineSubmissionCollectionDetailsActivity.start(mContext, data.collectNo, data.applyGUID));

    }

    public void setCollectionListener(onCollectionListener linstener) {
        this.collectionListener = linstener;
    }

}
