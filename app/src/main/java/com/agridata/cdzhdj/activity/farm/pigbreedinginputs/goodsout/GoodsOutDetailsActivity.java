package com.agridata.cdzhdj.activity.farm.pigbreedinginputs.goodsout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.pigbreed.goods.GoodsOutDetailsBean;
import com.agridata.cdzhdj.databinding.ActivityGoodsOutDetailsBinding;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-05-05 09:33.
 * @Description :描述
 */
public class GoodsOutDetailsActivity extends BaseActivity<ActivityGoodsOutDetailsBinding> {

    private String mid;
    private     CustomLoadingDialog mLoadingDialog;

    public static void start(Context context,String mid) {
        Intent intent = new Intent(context, GoodsOutDetailsActivity.class);
        intent.putExtra("mid", mid);
        context.startActivity(intent);
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        this.mid = this.getIntent().getStringExtra("mid");
    }
    @Override
    protected ActivityGoodsOutDetailsBinding getBinding() {
        return ActivityGoodsOutDetailsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        getArguments();
        binding.titlebarLeft.setOnClickListener(v -> finish());
    }

    @Override
    protected void initDatas() {
        getFeedStorageDetails();
    }
    private void getFeedStorageDetails() {
        showLoading("正在加载中...");
        HttpRequest.getGoodsOutDetails(GoodsOutDetailsActivity.this, mid, new CallBackLis<GoodsOutDetailsBean>() {
            @Override
            public void onSuccess(String method, GoodsOutDetailsBean content) {
                    hideLoading();
                setDetailsInfo(content.result);
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void  setDetailsInfo(GoodsOutDetailsBean.Result result ){
        binding.brandTv.setText(result.goodsName);
        binding.lotNumberTv.setText(result.batch +"");
        binding.singleHeadUsageTv.setText(result.singleUseNumber+"");
        binding.numberAnimalsUsedTv.setText(result.amount +"");
        binding.totalUsageTv.setText(result.totalUsage +"");
        binding.farmerNameTv.setText(result.farm.name);
        binding.regionNameTv.setText(result.region.regionFullName);

    }
    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(GoodsOutDetailsActivity.this);
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
