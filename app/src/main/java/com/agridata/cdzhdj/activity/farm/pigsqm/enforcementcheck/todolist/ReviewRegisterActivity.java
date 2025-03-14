package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.details.SpotCheckImgAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.law.EnforcementDetailBean;
import com.agridata.cdzhdj.data.law.SpotCheckBean;
import com.agridata.cdzhdj.databinding.ActivityReviewRegisterBinding;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.FullyGridLayoutManager;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.lzx.utils.RxToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-20 14:22.
 * @Description :描述抽查结果
 */
public class ReviewRegisterActivity extends BaseActivity<ActivityReviewRegisterBinding> {

    private String mid;
    private CustomLoadingDialog mLoadingDialog;
    private BottomSheetBehavior mBottomSheetBehavior;

    private SpotCheckBean spotCheckBean = new SpotCheckBean();


    private List<Integer> integerList;
    private List<Integer> integerListOne;

    public static void start(Context context, String Mid) {
        Intent intent = new Intent(context, ReviewRegisterActivity.class);
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
    protected ActivityReviewRegisterBinding getBinding() {
        return ActivityReviewRegisterBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        getArguments();
        initLoadingView();
        binding.titlebarLeft.setOnClickListener(v -> finish());
        spotCheckBean.TestResult = new SpotCheckBean.TestResultBean();
        spotCheckBean.TestResult.result = new ArrayList<>();
        mBottomSheetBehavior = BottomSheetBehavior.from(binding.llBottomSheet);
        ViewGroup.LayoutParams para1;
        para1 = binding.llBottomSheet.getLayoutParams();
        int height2 = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);
        para1.height=height2;
        binding.llBottomSheet.setLayoutParams(para1);


        binding.detailsBtn.setOnClickListener(v -> {
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                return;
            }
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                return;
            }
        });

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < integerListOne.size(); i++) {
                    spotCheckBean.TestResult.result.add(String.valueOf(integerListOne.get(i)));
                }
                spotCheckBean.TestResult.description = binding.otherProEt.getText().toString();
                spotCheckBean.SpotCheckStatus =1;
                spotCheckBean.Mid = mid;
                mCommitInfo(spotCheckBean);

            }
        });
        getEnforcementDetails();

    }

    @Override
    protected void initDatas() {

    }


    private void mCommitInfo(SpotCheckBean spotCheckBean){
        showLoading("数据提交中...");
        HttpRequest.updataChouCha(ReviewRegisterActivity.this, spotCheckBean , new CallBackLis<>() {
            @Override
            public void onSuccess(String method, StatusData status) {
                hideLoading();
                if (status.ErrorCode==0){
                    Objects.requireNonNull(RxToast.success(ReviewRegisterActivity.this,"提交成功")) ;
                    finish();
                }
            }
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(ReviewRegisterActivity.this,error));
            }
        });
    }
    //添加ViewItem
    private void addViewItemDoctor(int pos) {
       //如果一个都没有，就添加一个
            View hotelEvaluateView = View.inflate(this, R.layout.item_add_result, null);
           TextView  title_tv =  hotelEvaluateView.findViewById(R.id.title_tv);
            title_tv.setText("检查结果" + pos);
            binding.addResultLl.setTag("add");
            binding.addResultLl.addView(hotelEvaluateView);

        RadioButton radioButton = hotelEvaluateView.findViewById(R.id.qualified_ok);
        RadioButton radioButton1 = hotelEvaluateView.findViewById(R.id.disqualified_no);
        RadioGroup qualifiedRg = hotelEvaluateView.findViewById(R.id.qualified_rg);


        qualifiedRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == radioButton.getId()) {
                    for (int i = 0; i < integerList.size(); i++) {
                        if (i==pos-1){
                            integerListOne.set(i,1);
                        }
                    }
                    LogUtil.d("lzx----------------》true",integerListOne.toString());
                }  if (checkedId == radioButton1.getId()) {
                    for (int i = 0; i < integerList.size(); i++) {
                        if (i==pos-1){
                            integerListOne.set(i,0);
                        }
                    }
                    LogUtil.d("lzx----------------》false",integerListOne.toString());
                }
            }
        });

    }



    private void getEnforcementDetails() {
        HttpRequest.getEnforcementDetails(this, mid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, EnforcementDetailBean content) {
                if (content.status==0){

                    EnforcementDetailBean.Result.SamplePhotos samplePhotos = content.result.samplePhotos;
                    int size = samplePhotos.imageList.size();
                    integerList = new ArrayList<>();
                    integerListOne = new ArrayList<>();
                    for(int i=1;i<=size;i++){
                        addViewItemDoctor(i);
                        integerList.add(-1);
                        integerListOne.add(-1);
                    }
                    mSetBaseInfo(content);
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(ReviewRegisterActivity.this,error));
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
        SpotCheckImgAdapter spotCheckImgAdapter =new SpotCheckImgAdapter(R.layout.gv_filter_image,this);
        binding.recyclerSpotImg.setAdapter(spotCheckImgAdapter);

        List<String> imageList = result.samplePhotos.imageList;
        spotCheckImgAdapter.refreshDataList(imageList);





        Glide.with(ReviewRegisterActivity.this)
                .load(UrlUtils.pic_url +result.signatureEnforcementOfficer)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.zbryIv);

        Glide.with(ReviewRegisterActivity.this)
                .load(UrlUtils.pic_url +result.unitUnderInspectionSignature)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.bjcdwfzrIv);

        Glide.with(ReviewRegisterActivity.this)
                .load(UrlUtils.pic_url +result.eyewitnessSignature)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.jzrIv);
    }
    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(ReviewRegisterActivity.this);
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
