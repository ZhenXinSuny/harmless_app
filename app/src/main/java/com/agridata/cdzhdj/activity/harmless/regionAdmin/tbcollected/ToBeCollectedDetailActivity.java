package com.agridata.cdzhdj.activity.harmless.regionAdmin.tbcollected;

import android.app.Activity;
import android.content.Intent;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.ToBeCollectedDetailBean;
import com.agridata.cdzhdj.databinding.ActivityToBeCollectedDetailBinding;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class ToBeCollectedDetailActivity extends BaseActivity<ActivityToBeCollectedDetailBinding> {

    private final static String TAG = "ToBeCollectedDetailActivity------》";
    private String Mid;
    private CustomLoadingDialog mLoadingDialog;

    private ToBeCollectedDetailBean toBeCollectedDetailBean;

    public static void start(Activity context, String Mid) {
        Intent intent = new Intent(context, ToBeCollectedDetailActivity.class);
        intent.putExtra("Mid", Mid);
        context.startActivity(intent);
    }

    @Override
    protected ActivityToBeCollectedDetailBinding getBinding() {
        return ActivityToBeCollectedDetailBinding.inflate(getLayoutInflater());
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        Mid = this.getIntent().getStringExtra("Mid");
        LogUtil.d(TAG, "MID" + Mid);
    }

    @Override
    protected void initView() {
        getArguments();
        initLoadingView();

        binding.titlebarLeft.setOnClickListener(v -> finish());
        showLoading("加载中...");
        HttpRequest.getToBeCollectedDetail(ToBeCollectedDetailActivity.this, Mid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, ToBeCollectedDetailBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    hideLoading();
                    toBeCollectedDetailBean = content;
                    setInfo();
                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }

    private  void  setInfo(){
        if (toBeCollectedDetailBean!=null){
            ToBeCollectedDetailBean.Result result = toBeCollectedDetailBean.result;
            binding.sqdhTv.setText(result.applyNo);
            binding.yzchTv.setText(result.userName);
            binding.sjhTv.setText(result.mobile);
            binding.qhTv.setText(result.region.regionFullName);
            binding.xxdzTv.setText(result.applyAddress);
            binding.sbdwTv.setText(result.animal.name);
            binding.sbslTv.setText(result.dieAmount);
            binding.sfzhTv.setText(result.iDCard);
            binding.yhkhTv.setText(result.bankCardNo);
            binding.sbsjTv.setText(result.applyTime);
            if (result.imgFiles!=null){
                Glide.with(ToBeCollectedDetailActivity.this)
                        .load(UrlUtils.pic_url + result.imgFiles.idCardPic)
                        .error(R.drawable.ic_default_iv)
                        .placeholder( R.drawable.ic_default_iv)
                        .fallback( R.drawable.ic_default_iv)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                        .into(binding.idcardIv);

                Glide.with(ToBeCollectedDetailActivity.this)
                        .load(UrlUtils.pic_url + result.imgFiles.bankPic)
                        .error(R.drawable.ic_default_iv)
                        .placeholder( R.drawable.ic_default_iv)
                        .fallback( R.drawable.ic_default_iv)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                        .into(binding.bankIv);
            }

        }
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
