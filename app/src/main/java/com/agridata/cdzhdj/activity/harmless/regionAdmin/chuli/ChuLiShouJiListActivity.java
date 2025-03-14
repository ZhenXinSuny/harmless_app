package com.agridata.cdzhdj.activity.harmless.regionAdmin.chuli;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.collected.CollectedDetailActivity;
import com.agridata.cdzhdj.activity.adapter.ChuLiSJAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.RuKuDetailBean;
import com.agridata.cdzhdj.data.whh.ShouJiListForMidBean;
import com.agridata.cdzhdj.databinding.ActivityChuLiShoujiBinding;
import com.agridata.cdzhdj.utils.ToastUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ChuLiShouJiListActivity extends BaseActivity<ActivityChuLiShoujiBinding> {
    private final static String TAG = "ChuLiShouJiListActivity------》";
    private String  Mid;
    private CustomLoadingDialog mLoadingDialog;
    private ChuLiSJAdapter chuLiSJAdapter;


    public static void start(Activity context, String Mid) {
        Intent intent = new Intent(context, ChuLiShouJiListActivity.class);
        intent.putExtra("Mid", Mid);
        context.startActivity(intent);
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        Mid = this.getIntent().getStringExtra("Mid");
        LogUtil.d(TAG, "MID" + Mid);
    }

    @Override
    protected ActivityChuLiShoujiBinding getBinding() {
        return ActivityChuLiShoujiBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        getArguments();
        initLoadingView();
        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chuLiSJAdapter = new ChuLiSJAdapter(R.layout.item_cl_sj, this);
        binding.recyclerView.setAdapter(chuLiSJAdapter);


        showLoading("加载中...");
        HttpRequest.getRuKuDetail(this, Mid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, RuKuDetailBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    hideLoading();
                    //chuLiSJAdapter.refreshDataList(content.result.itemDatas);
                    List<String> midList = new ArrayList<>();
                    for (RuKuDetailBean.Result.ItemDatas itemData : content.result.itemDatas) {
                        midList.add(itemData.mid);
                    }
                    getShouJiForMidList(midList);
                }
            }
            @Override
            public void onFailure(String method, String error) {
                ToastUtil.showToast(ChuLiShouJiListActivity.this,error);
                hideLoading();
            }
        });
        chuLiSJAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                CollectedDetailActivity.start(ChuLiShouJiListActivity.this,chuLiSJAdapter.getData(position).mid,"2");
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
    }
    private void  getShouJiForMidList(List<String> midList){
        HttpRequest.getShouJiForMidList(ChuLiShouJiListActivity.this, midList, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, ShouJiListForMidBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    hideLoading();
                    if (content.result.size()>0){
                        chuLiSJAdapter.refreshDataList(content.result);
                    }
                }
            }
            @Override
            public void onFailure(String method, String error) {
                ToastUtil.showToast(ChuLiShouJiListActivity.this,error);
                hideLoading();
            }
        });
    }
    @Override
    protected void initDatas() {

    }

    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(this);
        this.mLoadingDialog.setCanceledOnTouchOutside(false);
    }


    /**
     * 显示加载框
     */
    public void showLoading(String tips) {
        if (this.mLoadingDialog != null && !this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.show();
            this.mLoadingDialog.setTitleVisibility(0);
            this.mLoadingDialog.setTitle(tips);
        }
    }

    /**
     * 隐藏 加载框
     */
    public void hideLoading() {
        if (this.mLoadingDialog != null && this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.hide();
        }
    }

}
