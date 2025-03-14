package com.agridata.cdzhdj.activity.farm.pigbreedinginputs.veterinarydrug;

import android.content.Context;
import android.content.Intent;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.pigbreed.feed.FeedStorageDetails;
import com.agridata.cdzhdj.databinding.ActivityVeterinaryDrugDetailsBinding;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-05-05 15:02.
 * @Description :描述
 */
public class VeterinaryDrugDetailsActivity extends BaseActivity <ActivityVeterinaryDrugDetailsBinding> {

    private String mid;
    private CustomLoadingDialog mLoadingDialog;

    public static void start(Context context, String mid) {
        Intent intent = new Intent(context, VeterinaryDrugDetailsActivity.class);
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
    protected ActivityVeterinaryDrugDetailsBinding getBinding() {
        return ActivityVeterinaryDrugDetailsBinding.inflate(getLayoutInflater());
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
        HttpRequest.getFeedStorageDetails(VeterinaryDrugDetailsActivity.this, mid, new CallBackLis<FeedStorageDetails>() {
            @Override
            public void onSuccess(String method, FeedStorageDetails content) {
                hideLoading();
                setDetailsInfo(content.result);
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }

    public void  setDetailsInfo(FeedStorageDetails.Result result ){
        binding.feedManufactureTv.setText(result.manufacturer);
        binding.feedBusinessTv.setText(result.enterprise);
        binding.feedBrandTv.setText(result.brand);
        binding.feedLotNumberTv.setText(result.batch);
        binding.dateOfProductionTv.setText(result.beBornDate);
        binding.validityPeriodTv.setText(result.validity);
        binding.storageQuantityTv.setText(result.goodsNumber+"");
        binding.warehousingTimeTv.setText(result.warehousingTime);
        binding.depositorTv.setText(result.depositorUser.name);
        binding.feedNameTv.setText(result.goodsName);
    }
    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(VeterinaryDrugDetailsActivity.this);
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
