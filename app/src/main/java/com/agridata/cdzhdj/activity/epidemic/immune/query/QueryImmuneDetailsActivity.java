package com.agridata.cdzhdj.activity.epidemic.immune.query;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.QueryImmuneBean;
import com.agridata.cdzhdj.data.QueryImmuneDetailsBean;
import com.agridata.cdzhdj.databinding.ActivityQueryImmuneDetailsBinding;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-23 14:37.
 * @Description :描述 详细
 */
public class QueryImmuneDetailsActivity extends BaseActivity<ActivityQueryImmuneDetailsBinding> {


    private QueryImmuneBean.Result.PageItems detailsInfo;
    private CustomLoadingDialog mLoadingDialog;

    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context, QueryImmuneBean.Result.PageItems DetailsInfo) {
        Intent intent = new Intent(context, QueryImmuneDetailsActivity.class);

        Bundle data = new Bundle();
        data.putSerializable("DetailsInfo", DetailsInfo);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    /**
     * 获取上一级所传过来的值  去判断加载哪一个Url
     */
    private void getArguments() {
        Bundle bundle = getIntent().getBundleExtra("data");
        detailsInfo = (QueryImmuneBean.Result.PageItems) bundle.getSerializable("DetailsInfo");
        showLoading("数据获取中...");
        getInfo(detailsInfo.detailID);
    }

    @Override
    protected ActivityQueryImmuneDetailsBinding getBinding() {
        return ActivityQueryImmuneDetailsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        binding.titlebarLeft.setOnClickListener(v -> finish());
        getArguments();
    }

    @Override
    protected void initDatas() {

        if (detailsInfo != null) {
            binding.xdrTv.setText(detailsInfo.xDRCoreInfo.name);
            if (detailsInfo.isSelfWrite == 1010) {
                binding.fyyTv.setText(detailsInfo.aHIUser.name);
            } else {
                binding.fyyTv.setText(UserDataSP.getInstance().getUserInfo().Result.name);
            }

            binding.dwzlTv.setText(detailsInfo.animal.name);
            binding.myslTv.setText(detailsInfo.immuneCount + "");
            binding.rlTv.setText(detailsInfo.currentAge + "");
            binding.fysjTv.setText(detailsInfo.immuned);
            binding.ztTv.setText(detailsInfo.immuneType == 1007 ? "首次免疫" : "再次免疫");
            binding.qhdzTv.setText(detailsInfo.region.regionFullName);
            binding.ebhTv.setText(detailsInfo.earTags);

            if (detailsInfo.replenishEarTagCode!=null){
                binding.budaiTv.setText(detailsInfo.replenishEarTagCode);
            }else {
                binding.budaiLl.setVisibility(View.GONE);
            }
        }
    }


    private void getInfo(String Mid) {
        HttpRequest.getHistoryImmuneDetails(QueryImmuneDetailsActivity.this, Mid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, QueryImmuneDetailsBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    hideLoading();
                    binding.ybTv.setText(content.result.diseaseID.name);
                    binding.ymTv.setText(content.result.vaccineId.name);
                    binding.cjTv.setText(content.result.vaccineFactory);
                    binding.jldwTv.setText(content.result.capacity + content.result.unit);
                    binding.qzmyTv.setText(content.result.iIST == 1023 ? "是" : "否");
                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
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
