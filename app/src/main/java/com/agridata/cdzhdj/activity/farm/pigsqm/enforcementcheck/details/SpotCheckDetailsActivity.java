package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.details;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.law.EnforcementDetailBean;
import com.agridata.cdzhdj.databinding.ActivitySpotCheckDetailsBinding;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.FullyGridLayoutManager;
import com.agridata.network.listener.CallBackLis;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.lzx.utils.RxToast;

import java.util.List;
import java.util.Objects;


/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-18 13:44.
 * @Description :描述
 */
public class SpotCheckDetailsActivity extends BaseActivity<ActivitySpotCheckDetailsBinding> {

    private String mid;
    private CustomLoadingDialog mLoadingDialog;

    public static void start(Context context, String Mid) {
        Intent intent = new Intent(context, SpotCheckDetailsActivity.class);
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
    protected ActivitySpotCheckDetailsBinding getBinding() {
        return ActivitySpotCheckDetailsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        getArguments();
        initLoadingView();
        getEnforcementDetails();

        binding.titlebarLeft.setOnClickListener(v -> finish());

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
                Objects.requireNonNull(RxToast.error(SpotCheckDetailsActivity.this,error));
            }
        });
    }


    private void mSetBaseInfo(EnforcementDetailBean content){
        EnforcementDetailBean.Result result = content.result;


        binding.zfzbdwTv.setText(result.sponsorEnforcementUnit.name);
        binding.zbryTv.setText(result.organizer.name);


        binding.dwmcTv.setText(result.companyName);
        binding.fzrTv.setText(result.legalRepresentative);
        binding.telTv.setText(result.tel);
        binding.quhuTv.setText(result.region.regionFullName);
        binding.xiangxiTv.setText(result.detailedAddress);

        binding.ccnrTv.setText(result.contentOfRandomCheck !=null? result.contentOfRandomCheck :"暂无");

        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        binding.recyclerSpotImg.setLayoutManager(fullyGridLayoutManager);
        binding.recyclerSpotImg.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));
        SpotCheckImgAdapter   spotCheckImgAdapter =new SpotCheckImgAdapter(R.layout.gv_filter_image,this);
        binding.recyclerSpotImg.setAdapter(spotCheckImgAdapter);

        List<String> imageList = result.samplePhotos.imageList;
        spotCheckImgAdapter.refreshDataList(imageList);





        Glide.with(SpotCheckDetailsActivity.this)
                .load(UrlUtils.pic_url +result.signatureEnforcementOfficer)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.zbryIv);

        Glide.with(SpotCheckDetailsActivity.this)
                .load(UrlUtils.pic_url +result.unitUnderInspectionSignature)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.bjcdwfzrIv);

        Glide.with(SpotCheckDetailsActivity.this)
                .load(UrlUtils.pic_url +result.eyewitnessSignature)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.jzrIv);

        if (result.SpotCheckStatus==1){
            binding.chouchadengjiLl.setVisibility(View.VISIBLE);

            for (int i = 1; i <= result.testResult.result.size(); i++) {
                addViewItemDoctor(i,result.testResult.result.get(i-1));
            }


            binding.descriptionTv.setText(result.testResult.description);

        }
    }

    //添加ViewItem
    private void addViewItemDoctor(int pos,String result) {
        //如果一个都没有，就添加一个
        View hotelEvaluateView = View.inflate(this, R.layout.item_register_info, null);
        TextView title_tv =  hotelEvaluateView.findViewById(R.id.title_tips_tv);
        title_tv.setText("检查结果" + pos + "：");
        binding.addResultLl.setTag("add");
        binding.addResultLl.addView(hotelEvaluateView);

        TextView result_tv =  hotelEvaluateView.findViewById(R.id.result_tv);

        if (result.equals("1")){
            result_tv.setText("合格");
        }else {
            result_tv.setText("不合格");
        }


    }
    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(SpotCheckDetailsActivity.this);
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
