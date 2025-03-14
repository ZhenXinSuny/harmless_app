package com.agridata.cdzhdj.view.recyclerview.listener;

import android.view.View;

import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;


/**
 * 简单的点击事件
 *
 * @author PengZhenjin
 * @date 2016-12-27
 */
public class SimpleClickListener implements BaseRecyclerViewAdapter.OnItemClickListener {

    @Override
    public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {

    }

    @Override
    public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
        return false;
    }
}


