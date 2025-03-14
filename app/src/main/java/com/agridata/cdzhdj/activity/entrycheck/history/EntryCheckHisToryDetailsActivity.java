package com.agridata.cdzhdj.activity.entrycheck.history;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.entrycheck.DaiZaiHuDetailsBean;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckHistoryDetailBean;
import com.agridata.cdzhdj.databinding.ActivityEntryCheckHistoryDetailsBinding;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.FullyGridLayoutManager;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzx.utils.RxToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-11-20 17:56.
 * @Description :描述
 */
public class EntryCheckHisToryDetailsActivity extends BaseActivity<ActivityEntryCheckHistoryDetailsBinding> {

    private String mMid;
    private CustomLoadingDialog mLoadingDialog;
    private List<String> ImgViewList;
    private EarTagImgAdapter adapter;

    private LocalMedia localMedia;
    private List<LocalMedia> localMediaList;

    public static void start(Context context, String Mid) {
        Intent intent = new Intent(context, EntryCheckHisToryDetailsActivity.class);
        intent.putExtra("Mid", Mid);
        context.startActivity(intent);
    }

    @Override
    protected ActivityEntryCheckHistoryDetailsBinding getBinding() {
        return ActivityEntryCheckHistoryDetailsBinding.inflate(getLayoutInflater());
    }


    private void getArguments() {
        this.mMid = this.getIntent().getStringExtra("Mid");

    }

    @Override
    protected void initView() {
        binding.titlebarLeft.setOnClickListener(view -> finish());
        this.getArguments();
        this.initLoadingView();
    }

    @Override
    protected void initDatas() {
        getHistoryEntryCheckDetails();
    }


    private void getHistoryEntryCheckDetails() {
        showLoading("正在加载中...");
        HttpRequest.getHistoryEntryCheckDetails(EntryCheckHisToryDetailsActivity.this, mMid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, EntryCheckHistoryDetailBean content) {
                hideLoading();
                if (content.status == 0) {
                    setUi(content.result);
                } else {
                    Objects.requireNonNull(RxToast.error(EntryCheckHisToryDetailsActivity.this, "获取数据失败，请重试~"));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }

    private void setUi(EntryCheckHistoryDetailBean.Result result) {
        getDaiZaiHuDetails();
        if ("2".equals(result.certType)) {
            binding.certTypeTv.setText("动物B证");
        } else {
            binding.certTypeTv.setText("动物A证");
        }

        binding.certNumTv.setText(result.certNo);
        binding.animalNumTv.setText(result.counts + "头/只/羽");

        if (result.earTags != null && !result.earTags.isEmpty()) {
            binding.qualifiedEarLabelTv.setText(String.join(",", result.earTags));
        } else {
            binding.qualifiedEarLabelTv.setText("暂无数据");
        }
        if (!TextUtils.isEmpty(result.errorEarTags)) {
            binding.failedEarLabelTv.setText(result.errorEarTags);
        } else {
            binding.failedEarLabelTv.setText("暂无数据");
        }

        binding.testResultTv.setText(result.checkResult == 1 ? "合格" : "不合格");
        binding.timeQualifiedTv.setText(result.timeIsPass == 1 ? "合格" : "不合格");
        binding.addressQualifiedTv.setText(result.addressIsPass == 1 ? "合格" : "不合格");
        binding.carQualifiedTv.setText(result.carIsPass == 1 ? "合格" : "不合格");
        binding.channelQualifiedTv.setText(result.roadIsPass == 1 ? "合格" : "不合格");
        binding.animalQualifiedTv.setText(result.numberIsPass == 1 ? "合格" : "不合格");
        binding.eartagNumTv.setText(result.earTagIsPass == 1 ? "合格" : result.earTagIsPass == 2 ? "不抽查" : "不合格");
        binding.inspectionTimeTv.setText(result.checkTime);
        Glide.with(EntryCheckHisToryDetailsActivity.this)
                .load(UrlUtils.pic_url + result.carHead)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(binding.carIv);

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        binding.imgRecyclerview.setLayoutManager(manager);
        binding.imgRecyclerview.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 6), false));
        adapter = new EarTagImgAdapter(R.layout.item_eartag_img, this);
        binding.imgRecyclerview.setAdapter(adapter);


        if (result.imgs.size() > 0) {
            ImgViewList = new ArrayList<>();
            ImgViewList.addAll(result.imgs);
            adapter.refreshDataList(ImgViewList);
            localMediaList = new ArrayList<>();
            for (int i = 0; i < ImgViewList.size(); i++) {
                localMedia = new LocalMedia();
                localMedia.setPath(UrlUtils.pic_url + ImgViewList.get(i));
                localMediaList.add(localMedia);
            }
            LogUtil.d("lzx---》", "查看大图" + localMediaList.size() + localMediaList.toString());
        } else {
            binding.checkImageLl.setVisibility(View.GONE);
        }
    }

    private void getDaiZaiHuDetails() {
        HttpRequest.getDZHDetails(EntryCheckHisToryDetailsActivity.this, mMid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, DaiZaiHuDetailsBean content) {
                hideLoading();
                if (content.status == 0) {
                    if (content.result != null && !content.result.isEmpty()) {
                        binding.dzhLl.setVisibility(View.VISIBLE);
                        StringBuilder string = new StringBuilder();
                        for (int i = 0; i < content.result.size(); i++) {
                            string.append(content.result.get(i).name).append("[").append(content.result.get(i).amount).append("]").append("头/只/羽")
                                    .append(content.result.size() > 1 ? "," : "");
                        }
                        binding.dzhTv.setText(string.substring(0, string.length() - 1));
                    } else {
                        binding.dzhLl.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                RxToast.error(error);
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
