package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.details;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.law.EnforcementDetailBean;
import com.agridata.cdzhdj.databinding.ActivityEnforcementDetailsBinding;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lzx.utils.RxToast;

import java.util.Objects;


/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-18 13:44.
 * @Description :描述
 */
public class EnforcementDetailsActivity extends BaseActivity<ActivityEnforcementDetailsBinding> {

    private String mid;
    private CustomLoadingDialog mLoadingDialog;
    private String inspectionFieldName;

    public static void start(Context context, String Mid) {
        Intent intent = new Intent(context, EnforcementDetailsActivity.class);
        intent.putExtra("Mid", Mid);
        context.startActivity(intent);
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        this.mid = this.getIntent().getStringExtra("Mid");
    }

    @Override
    protected ActivityEnforcementDetailsBinding getBinding() {
        return ActivityEnforcementDetailsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        getArguments();
        initLoadingView();
        getEnforcementDetails();

        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.lingyuDetailsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmDetailsActivity.start(EnforcementDetailsActivity.this,inspectionFieldName,mid);
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    private void getEnforcementDetails() {
        showLoading("正在加载中...");
        HttpRequest.getEnforcementDetails(this, mid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, EnforcementDetailBean content) {
                hideLoading();
            if (content.status==0){
                mSetBaseInfo(content);
              }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(EnforcementDetailsActivity.this,error));
            }
        });
    }


    private void mSetBaseInfo(EnforcementDetailBean content){
        EnforcementDetailBean.Result result = content.result;
        inspectionFieldName = result.inspectionField.name;

        binding.zfzbdwTv.setText(result.sponsorEnforcementUnit.name);
        binding.zbryTv.setText(result.organizer.name);
        binding.xbdwTv.setText(result.coOrganizeEnforcementUnits.name);
        binding.xbryTv.setText(result.coOrganizer.name);
        binding.jclyTv.setText(result.inspectionField.name);
        binding.dwmcTv.setText(result.companyName);
        binding.fzrTv.setText(result.legalRepresentative);
        binding.telTv.setText(result.tel);
        binding.quhuTv.setText(result.region.regionFullName);
        binding.jcjgTv.setText(result.inspectionResult.name);
        binding.xiangxiTv.setText(result.detailedAddress);
        if (!TextUtils.isEmpty(result.otherProblems)){
            binding.qtwtTv.setText(result.otherProblems	);
        }else {
            binding.qtwtTv.setText("暂无");
        }

        Glide.with(EnforcementDetailsActivity.this)
                .load(UrlUtils.pic_url +result.frontViewOfTheInspectedUnit)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.bjcdwzmIv);

        if (result.inspectionResult!=null){
            if ("4".equals(result.inspectionResult.id)){
                binding.fuchaLl.setVisibility(View.VISIBLE);
                if (result.reviewStatus==0){
                    binding.fuchazhuangtaiTv.setText("未复查");
                    binding.fuchazhuangtaiTv.setBackground(getDrawable(R.drawable.textview_border_no_status));
                   binding.fuchajigouLl.setVisibility(View.GONE);
                    binding.fucharyLl.setVisibility(View.GONE);
                    if (result.assignmentStatus==1){
                        binding.fuchajigouLl.setVisibility(View.VISIBLE);
                        binding.fucharyLl.setVisibility(View.VISIBLE);
                        binding.fczfjgTv.setText(result.assigningAgency.Name);
                        binding.fczfryTv.setText(result.assignPerson.Name);
                    }
                }else {
                    binding.fuchazhuangtaiTv.setText("已复查");
                    binding.fuchazhuangtaiTv.setBackground(getDrawable(R.drawable.textview_border));
                    binding.fuchajigouLl.setVisibility(View.VISIBLE);
                    binding.fucharyLl.setVisibility(View.VISIBLE);
                    binding.fczfjgTv.setText(result.assigningAgency.Name);
                    binding.fczfryTv.setText(result.assignPerson.Name);
                }
                binding.zhenggaitimeTv.setText(result.deadlineForRectification);
            }else {
                binding.fuchaLl.setVisibility(View.GONE);
            }
        }
        Glide.with(EnforcementDetailsActivity.this)
                .load(UrlUtils.pic_url +result.signatureOfOrganizer)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.zbryIv);

        Glide.with(EnforcementDetailsActivity.this)
                .load(UrlUtils.pic_url +result.unitUnderInspectionSignature)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.bjcdwfzrIv);

        Glide.with(EnforcementDetailsActivity.this)
                .load(UrlUtils.pic_url +result.eyewitnessSignature)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.jzrIv);
    }
    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(EnforcementDetailsActivity.this);
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
