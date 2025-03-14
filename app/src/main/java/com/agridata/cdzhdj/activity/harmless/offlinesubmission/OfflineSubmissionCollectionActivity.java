package com.agridata.cdzhdj.activity.harmless.offlinesubmission;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.activity.harmless.collection.onCollectionListener;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.BatchImgBean;
import com.agridata.cdzhdj.data.CollectInfoData;
import com.agridata.cdzhdj.data.NewSendSmsBean;
import com.agridata.cdzhdj.data.SendStatusBean;
import com.agridata.cdzhdj.data.StatusMeBean;
import com.agridata.cdzhdj.data.XianChangRenBean;
import com.agridata.cdzhdj.data.db.bean.CollectionDBModel;
import com.agridata.cdzhdj.data.db.dao.AppDatabase;
import com.agridata.cdzhdj.data.db.mapper.CollectionModelMapper;
import com.agridata.cdzhdj.data.db.repository.ApplyBeanRepository;
import com.agridata.cdzhdj.data.db.repository.CollectionDBModelRepository;
import com.agridata.cdzhdj.data.db.repository.CustomDisposable;
import com.agridata.cdzhdj.databinding.ActivityOffineSubCollectionBinding;
import com.agridata.cdzhdj.utils.WaterMaskUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.lzx.utils.RxToast;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.reactivex.Flowable;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-05-06 11:06.
 * @Description :描述
 */
public class OfflineSubmissionCollectionActivity extends BaseActivity<ActivityOffineSubCollectionBinding> implements onCollectionListener {

    private CustomLoadingDialog mLoadingDialog;
    private OfflineSubmissionCollectionAdapter offlineSubmissionCollectionAdapter;

    private CollectionModelMapper collectionModelMapper;
    private CollectInfoData collectInfoData;

    public OfflineSubmissionCollectionActivity() {
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, OfflineSubmissionCollectionActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityOffineSubCollectionBinding getBinding() {
        return ActivityOffineSubCollectionBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        offlineSubmissionCollectionAdapter = new OfflineSubmissionCollectionAdapter(R.layout.item_offine_collection_info, this);
        offlineSubmissionCollectionAdapter.setCollectionListener(this);
        binding.recyclerView.setAdapter(offlineSubmissionCollectionAdapter);
        //binding.refreshLayout.autoRefresh();
        binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);

        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getLocalInfo();
            }
        });
    }

    @Override
    protected void initDatas() {
        collectionModelMapper = new CollectionModelMapper();
        getLocalInfo();
    }

    /**
     * 按钮监听
     *
     * @param position
     */
    @Override
    public void collection(int position) {


        showLoading("数据上传中，请等待...");

        CollectionDBModel data = offlineSubmissionCollectionAdapter.getData(position);
        collectInfoData = collectionModelMapper.convertToInfoData(data);

        List<String> filePaths = new ArrayList<>();
        filePaths.add(collectInfoData.imgFiles.IdCardPic);
        upLoadImgBatch(filePaths, 1);


    }

    private void getLocalInfo() {
        showLoading("数据获取中...");
        Flowable<List<CollectionDBModel>> listFlowable = AppDatabase.getInstance(this).collectionDao().queryAllAsList();
        CustomDisposable.addDisposable(listFlowable, collectionDbModelList -> {
            hideLoading();
            binding.refreshLayout.finishRefresh();
            if (collectionDbModelList.isEmpty()) {
                binding.refreshLayout.setVisibility(View.GONE);
                binding.notDataRl.setVisibility(View.VISIBLE);
                return;
            }
            offlineSubmissionCollectionAdapter.refreshDataList(collectionDbModelList);

            LogUtil.d("lzx----->", "最终数据" + collectionDbModelList.size());
        });

    }


    private void upLoadImgBatch(List<String> filePath, int type) {

        HttpRequest.upLoadBatchImg(OfflineSubmissionCollectionActivity.this, filePath, new CallBackLis<BatchImgBean>() {
            @Override
            public void onSuccess(String method, BatchImgBean content) {
                LogUtil.d("lzx------->", "上传图片批量" + content.toString());
                if (content.status == 0) {
                    File myFolder = new File(WaterMaskUtil.SavaPhotoFile);
                    WaterMaskUtil.deleteDirectory(myFolder);
                    if (type == 1) {
                        collectInfoData.imgFiles.IdCardPic = content.result.get(0);
                        Objects.requireNonNull(RxToast.success(OfflineSubmissionCollectionActivity.this, "身份证上传成功"));
                        List<String> filePaths = new ArrayList<>();
                        filePaths.add(collectInfoData.imgFiles.BankPic);
                        //上传银行卡
                        upLoadImgBatch(filePaths, 2);
                    } else if (type == 2) {
                        collectInfoData.imgFiles.BankPic = content.result.get(0);
                        Objects.requireNonNull(RxToast.success(OfflineSubmissionCollectionActivity.this, "银行卡上传成功"));
                        List<String> filePaths = new ArrayList<>();
                        //上传纸质单据
                        filePaths.add(collectInfoData.imgFiles.CollectCertPic);
                        upLoadImgBatch(filePaths, 3);
                    } else if (type == 3) {
                        collectInfoData.imgFiles.CollectCertPic = content.result.get(0);
                        //上传收运人签名
                        Objects.requireNonNull(RxToast.success(OfflineSubmissionCollectionActivity.this, "纸质单据上传成功"));
                        List<String> filePaths = new ArrayList<>();
                        filePaths.add(collectInfoData.imgFiles.ShouYunYuanPic);
                        upLoadImgBatch(filePaths, 4);
                    } else if (type == 4) {
                        collectInfoData.imgFiles.ShouYunYuanPic = content.result.get(0);
                        //上传收运人签名
                        Objects.requireNonNull(RxToast.success(OfflineSubmissionCollectionActivity.this, "收运人签名上传成功"));
                        List<String> filePaths = new ArrayList<>();
                        filePaths.add(collectInfoData.imgFiles.YangZhiChangHuPic);
                        upLoadImgBatch(filePaths, 5);

                    } else if (type == 5) {
                        collectInfoData.imgFiles.YangZhiChangHuPic = content.result.get(0);
                        //上传收运人签名
                        Objects.requireNonNull(RxToast.success(OfflineSubmissionCollectionActivity.this, "养殖场户签名上传成功"));
                        List<String> sichuList = new ArrayList<>();
                        for (CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean : collectInfoData.imgFiles.SiChuPicList) {
                            sichuList.add(imgMidBean.Mid);
                        }
                        upLoadImgBatch(sichuList, 6);

                    } else if (type == 6) {
                        collectInfoData.imgFiles.SiChuPicList.clear();
                        for (int i = 0; i < content.result.size(); i++) {
                            CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
                            imgMidBean.Mid = content.result.get(i);
                            collectInfoData.imgFiles.SiChuPicList.add(imgMidBean);
                        }

                        //尸体
                        List<String> shiTiList = new ArrayList<>();
                        for (CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean : collectInfoData.imgFiles.ShiTiPicList) {
                            shiTiList.add(imgMidBean.Mid);
                        }
                        Objects.requireNonNull(RxToast.success(OfflineSubmissionCollectionActivity.this, "死畜照片上传成功"));
                        upLoadImgBatch(shiTiList, 7);


                    } else if (type == 7) {
                        collectInfoData.imgFiles.ShiTiPicList.clear();
                        for (int i = 0; i < content.result.size(); i++) {
                            CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
                            imgMidBean.Mid = content.result.get(i);
                            collectInfoData.imgFiles.ShiTiPicList.add(imgMidBean);
                        }

                        List<String> zcList = new ArrayList<>();
                        for (CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean : collectInfoData.imgFiles.ZhuangChePicList) {
                            zcList.add(imgMidBean.Mid);
                        }
                        Objects.requireNonNull(RxToast.success(OfflineSubmissionCollectionActivity.this, "尸体照片上传成功"));
                        upLoadImgBatch(zcList, 8);


                    } else if (type == 8) {
                        collectInfoData.imgFiles.ZhuangChePicList.clear();
                        for (int i = 0; i < content.result.size(); i++) {
                            CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
                            imgMidBean.Mid = content.result.get(i);
                            collectInfoData.imgFiles.ZhuangChePicList.add(imgMidBean);
                        }
                        Objects.requireNonNull(RxToast.success(OfflineSubmissionCollectionActivity.this, "装车照片上传成功"));
                        uploadInfo();

                    }

                } else {

                    Objects.requireNonNull(RxToast.error(OfflineSubmissionCollectionActivity.this, "上传失败~"));
                }

            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }


    private void uploadInfo() {
        HttpRequest.getCollect(OfflineSubmissionCollectionActivity.this, collectInfoData, new CallBackLis<StatusMeBean>() {
            @Override
            public void onSuccess(String method, StatusMeBean content) {
                hideLoading();
                if (content.code == 200) {
                    Objects.requireNonNull(RxToast.success(OfflineSubmissionCollectionActivity.this, "收集数据上传成功"));
                    CollectionDBModelRepository.getInstance().deleteById(OfflineSubmissionCollectionActivity.this, collectInfoData.collectNo);
                    ApplyBeanRepository.getInstance().deleteById(OfflineSubmissionCollectionActivity.this, collectInfoData.applyGUID);
                    getXianChangJianDuYuanInfo();
                    new Handler().postDelayed(() -> {
                        finish();
                    }, 2000);
                } else {
                    Objects.requireNonNull(RxToast.error(OfflineSubmissionCollectionActivity.this, content.msg));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }

    private void getXianChangJianDuYuanInfo() {
        HttpRequest.getXianChangJianDuYuan(OfflineSubmissionCollectionActivity.this, String.valueOf(collectInfoData.region.id), new CallBackLis<XianChangRenBean>() {
            @Override
            public void onSuccess(String method, XianChangRenBean content) {
                if (content.code == 200) {
                    if (!TextUtils.isEmpty(content.data.mobiles)) {
                        //sendSms(content.data.mobiles);

                    }

                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }


    private void sendSms(String phone) {

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());
        int year = nowCalendar.get(Calendar.YEAR);
        int month = nowCalendar.get(Calendar.MONTH) + 1;
        int DAY_OF_MONTH = nowCalendar.get(Calendar.DAY_OF_MONTH);
        int HOUR_OF_DAY = nowCalendar.get(Calendar.HOUR_OF_DAY);
        int MINUTE = nowCalendar.get(Calendar.MINUTE);
        int SECOND = nowCalendar.get(Calendar.SECOND);
        String times = year + "年" + month + "月" + DAY_OF_MONTH + "日";
        NewSendSmsBean sendSmsBean = new NewSendSmsBean();
        sendSmsBean.phone = phone;
        sendSmsBean.template = new NewSendSmsBean.TemplateBean();
        sendSmsBean.template.farm = collectInfoData.xdrName;
        sendSmsBean.template.userName = RoleSP.getInstance().getRoleInfo().harmlessUser.name;
        sendSmsBean.template.datetime = times;
        sendSmsBean.template.animalAmount = collectInfoData.animal.AnimalName + collectInfoData.dieAmount + "头";

        HttpRequest.sendNewSms(OfflineSubmissionCollectionActivity.this, sendSmsBean, new CallBackLis<SendStatusBean>() {
            @Override
            public void onSuccess(String method, SendStatusBean content) {
                if (content.Status == 0) {

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
