package com.agridata.cdzhdj.activity.adapter.eartag;

import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.epidemic.eartag.EarTagActivity;
import com.agridata.cdzhdj.data.ImmuneEarTagBean;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-11-28 16:55.
 * @Description :描述
 */
public class EarTagListAdapter extends BaseRecyclerViewAdapter<ImmuneEarTagBean, BaseRecyclerViewHolder> {

    private EarTagActivity mContext;
    private List<ImmuneEarTagBean> mImmuneEarTagBeanList;

    private SparseBooleanArray mBooleanArray = new SparseBooleanArray();
    private CheckBox eartag_check;


    public EarTagListAdapter(int layoutResId, EarTagActivity mContext, List<ImmuneEarTagBean> list) {
        super(layoutResId, null);
        this.mContext = mContext;
        this.mImmuneEarTagBeanList = list;

        LogUtil.d("lzx------>","每次走这里");

    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, ImmuneEarTagBean data, final int position) {
        TextView earTag_tv = viewHolder.getView(R.id.earTag_tv);
        earTag_tv.setText(data.EarTag);
        eartag_check = viewHolder.getView(R.id.eartag_check);
        eartag_check.setChecked(isItemChecked(position));
        eartag_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(position)) {
                    setItemChecked(position, false);
                } else {
                    setItemChecked(position, true);
                }
                mContext.setChooseEarTagLl();
            }
        });

        RelativeLayout eartag_ll = viewHolder.getView(R.id.eartag_ll);
        eartag_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(position)) {
                    setItemChecked(position, false);
                } else {
                    setItemChecked(position, true);
                }
                mContext.setChooseEarTagLl();
                notifyItemChanged(position);
            }
        });
    }

    /**
     * 全部选中
     */
    public void checkAllItem() {
        if (getDataList().size()==0){
            return;
        }
        for (int i = 0; i < getDataList().size(); i++) {
            setItemChecked(i, true);
        }

    }

    /**
     * 取消所有选中
     */
    public void clearAllSelected() {
        LogUtil.d("lzx----》", "全不选" + getDataList().toString());
        for (int i = 0; i < getDataList().size(); i++) {
            if (isItemChecked(i)) {
                setItemChecked(i, false);
            }
        }
    }

    /**
     * 删除选中
     */

    public void deleteSelected() {
        LogUtil.d("lzx----》","删除的数据原来" + mBooleanArray.toString());
        for (int i = getDataList().size() - 1; i >= 0; i--) {
            if (isItemChecked(i)) {
                getDataList().remove(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    mBooleanArray.delete(i);
                }
                LogUtil.d("lzx----》","删除的数据原来" + mBooleanArray.toString());
                notifyItemRemoved(i);

            }
        }
        notifyDataSetChanged();

    }

    /**
     * 获得选中条目的结果
     *
     * @return
     */
    public List<ImmuneEarTagBean> getSelectedItem() {
        List<ImmuneEarTagBean> immuneEarTagBeans = new ArrayList<>();
        for (int i = 0; i <   getDataList().size(); i++) {
            if (isItemChecked(i)) {
                immuneEarTagBeans.add( getDataList().get(i));
            }
        }
        return immuneEarTagBeans;
    }

    /**
     * 设置给定位置条目的选择状态
     *
     * @param position
     * @param isChecked
     */
    private void setItemChecked(int position, boolean isChecked) {
        mBooleanArray.put(position, isChecked);
    }

    /**
     * 根据位置判断条目是否选中
     *
     * @param position
     * @return
     */
    private boolean isItemChecked(int position) {
        return mBooleanArray.get(position);
    }


    //    响应itemClick
    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    private OnRecyclerViewItemClick onRecyclerViewItemClick; //itemClick

    interface OnRecyclerViewItemClick {
        void OnItemClick(int position, List<ImmuneEarTagBean> itemProperties);
    }
}
