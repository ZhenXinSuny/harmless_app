package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.todolist;

import static com.agridata.cdzhdj.base.MyApplication.getContext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.adapter.ImgeViewProblemAdapter;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.details.RealmDetailsActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.BatchImgBean;
import com.agridata.cdzhdj.data.ImageViewData;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.law.EnforcementDetailBean;
import com.agridata.cdzhdj.data.law.ReexaminationFillingBean;
import com.agridata.cdzhdj.databinding.ActivityReviewInfoBinding;
import com.agridata.cdzhdj.utils.PictureSelectorUtils;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.utils.WaterMaskUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.WaterMaskViewCheck;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.PictureFileUtils;
import com.lzx.utils.RxToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-20 10:39.
 * @Description :描述 复查
 */
public class ReviewInfoActivity extends BaseActivity<ActivityReviewInfoBinding> implements View.OnClickListener {


    private final static String TAG = "lzx------》";

    private CustomLoadingDialog mLoadingDialog;



    private BottomSheetBehavior mBottomSheetBehavior;
    private String inspectionFieldName;
    private final ReexaminationFillingBean reexaminationFillingBean = new ReexaminationFillingBean();
    private String mMid;

    private final List<ImageViewData> imageViewDataListProblem = new ArrayList<>();
    private ImgeViewProblemAdapter imgeViewProblemAdapter;

    @Override
    protected ActivityReviewInfoBinding getBinding() {
        return ActivityReviewInfoBinding.inflate(getLayoutInflater());
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        this.mMid = this.getIntent().getStringExtra("Mid");
    }
    public static void start(Context context, String Mid) {
        Intent intent = new Intent(context, ReviewInfoActivity.class);
        intent.putExtra("Mid", Mid);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        getArguments();
        initLoadingView();
        binding.titlebarLeft.setOnClickListener(v -> finish());


        binding.submitBtn.setOnClickListener(this);//提交
        reexaminationFillingBean.ReviewProblemShooting = new ReexaminationFillingBean.ReviewProblemShootingBean();
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



        binding.lingyuDetailsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmDetailsActivity.start(ReviewInfoActivity.this,inspectionFieldName,mMid);
            }
        });

        binding.examinerSignatureIv.setOnClickListener(this);
        binding.checkSignatureIv.setOnClickListener(this);
        binding.otherProblemPicLl.setOnClickListener(this);

        reexaminationFillingBean.ReviewResult =-1;

        binding.recyclerviewProblem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        imgeViewProblemAdapter = new ImgeViewProblemAdapter(R.layout.item_be_inspected_pic, this);
        binding.recyclerviewProblem.setAdapter(imgeViewProblemAdapter);

    }

    @Override
    protected void initDatas() {
        getEnforcementDetails();
    }

   public void onRadioButtonClicked(View view){
       RadioButton button = (RadioButton) view;
       boolean isChecked = button.isChecked();
       if (view == binding.correctedOk) {
           if (isChecked) {
               reexaminationFillingBean.ReviewResult = 1;
           }
       }else if (view == binding.correctedNo){
           if (isChecked) {
               reexaminationFillingBean.ReviewResult = 2;
           }
       }
   }
    @Override
    protected void OnEventMainThread() {
        super.OnEventMainThread();
        mRxManager.on("ReviewCheckZFRY", o -> {
            String ZhiFaPerson = (String) o;
            LogUtil.d(TAG,"ReviewCheckZFRY" +ZhiFaPerson);


            Bitmap sourBitmap = BitmapFactory.decodeFile(ZhiFaPerson);
            Bitmap WaterBitmap = saveWaterMask(ReviewInfoActivity.this, 4, sourBitmap);
            String fileName = "IMG_" + new Date().getTime() + "SpotCheckJZRQM" + ".png";
            ZhiFaPerson = WaterMaskUtil.saveQNext(WaterBitmap, ReviewInfoActivity.this, fileName, 60);

            upLoadImg(ZhiFaPerson,2);
        });
        mRxManager.on("ReviewCheckDWFZRQM", o -> {
            String SpotCheckDWFZRQM = (String) o;
            LogUtil.d(TAG,"ReviewCheckDWFZRQM" +SpotCheckDWFZRQM);


            Bitmap sourBitmap = BitmapFactory.decodeFile(SpotCheckDWFZRQM);
            Bitmap WaterBitmap = saveWaterMask(ReviewInfoActivity.this, 4, sourBitmap);
            String fileName = "IMG_" + new Date().getTime() + "SpotCheckJZRQM" + ".png";
            SpotCheckDWFZRQM = WaterMaskUtil.saveQNext(WaterBitmap, ReviewInfoActivity.this, fileName, 60);

            upLoadImg(SpotCheckDWFZRQM,3);
        });
      
    }
    @Override
    public void onClick(View v) {
        if (v==binding.submitBtn){
            SubmitData();
        }else  if (v==binding.examinerSignatureIv){
            SignReviewActivity.start(ReviewInfoActivity.this,"复查人员签名");
        }else  if (v==binding.checkSignatureIv){
            SignReviewActivity.start(ReviewInfoActivity.this,"被检查单位负责人签名");
        }else  if (v==binding.otherProblemPicLl){//发现问题拍照
            getCamera(888);
        }
    }

    /**
     * 提交数据
     */
    private void SubmitData(){
        if (checkInfo()) {
            LogUtil.d(TAG,"最终的提交数据"+reexaminationFillingBean.toString());
            reexaminationFillingBean.ReviewStatus = 1;
            reexaminationFillingBean.Mid = mMid;
            //指定日期格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            //日历对象
            Calendar calendar = Calendar.getInstance();
            //获取当前日期
            calendar.setTime(new Date());
            System.out.println("当前时间：" + simpleDateFormat.format(calendar.getTime()));
            reexaminationFillingBean.ReviewTime = simpleDateFormat.format(calendar.getTime());
            reexaminationFillingBean.ReviewTheSituation = binding.otherProEt.getText().toString();
            mCommitInfo(reexaminationFillingBean);
        }
    }


    private void mCommitInfo(ReexaminationFillingBean reexaminationFillingBean){
        showLoading("数据提交中...");
        HttpRequest.updataReviewCheck(ReviewInfoActivity.this, reexaminationFillingBean , new CallBackLis<>() {
            @Override
            public void onSuccess(String method, StatusData status) {
                hideLoading();
                if (status.ErrorCode==0){
                    Objects.requireNonNull(RxToast.success(ReviewInfoActivity.this,"提交成功")) ;
                    finish();
                }
            }
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(ReviewInfoActivity.this,error));
            }
        });
    }


    private boolean checkInfo() {
        if (reexaminationFillingBean.ReviewResult==-1){
            Objects.requireNonNull( RxToast.warning(ReviewInfoActivity.this,"请选择复查情况结果"))   ;
            return false;
        }
        if (TextUtils.isEmpty(reexaminationFillingBean.SignatureOfRechecker)) {
            Objects.requireNonNull(RxToast.warning(ReviewInfoActivity.this, "请上传复查人员签字"));
            return false;
        }
        if (TextUtils.isEmpty(reexaminationFillingBean.SignatureOfThePersonReviewed)) {
            Objects.requireNonNull(RxToast.warning(ReviewInfoActivity.this, "请上传被检查单位负责人签名"));
            return false;
        }
        return  true;
    }


    /**
     * filePath 上传图片
     *
     * @param filePath
     */
    private void upLoadImg(String filePath,int PicBtnType) {
        showLoading("正在上传中...");
        HttpRequest.upLoadImg(ReviewInfoActivity.this, filePath, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d(TAG, "上传图片" + content.toString());
                if (content.Status == 0) {
                    Objects.requireNonNull(RxToast.success(ReviewInfoActivity.this, "上传成功"));
                    if (PicBtnType == 2) {
                        reexaminationFillingBean.SignatureOfRechecker  = content.Result;//复查人员签字
                        Glide.with(ReviewInfoActivity.this)
                                .load(UrlUtils.pic_url + content.Result)
                                .centerCrop()
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(binding.examinerSignatureIv);
                    } else if (PicBtnType == 3) {
                        reexaminationFillingBean.SignatureOfThePersonReviewed  = content.Result;//被检查单位负责人签名
                        Glide.with(ReviewInfoActivity.this)
                                .load(UrlUtils.pic_url + content.Result)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .into(binding.checkSignatureIv);
                    }
                    hideLoading();
                } else {
                    Objects.requireNonNull(RxToast.error(ReviewInfoActivity.this, content.Result));
                }

            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }


    private void getEnforcementDetails() {
        HttpRequest.getEnforcementDetails(this, mMid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, EnforcementDetailBean content) {
                if (content.status==0){
                    mSetBaseInfo(content);
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(ReviewInfoActivity.this,error));
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

        Glide.with(ReviewInfoActivity.this)
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
        Glide.with(ReviewInfoActivity.this)
                .load(UrlUtils.pic_url +result.signatureOfOrganizer)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.zbryIv);

        Glide.with(ReviewInfoActivity.this)
                .load(UrlUtils.pic_url +result.unitUnderInspectionSignature)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.bjcdwfzrIv);

        Glide.with(ReviewInfoActivity.this)
                .load(UrlUtils.pic_url +result.eyewitnessSignature)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.jzrIv);
    }





    private void getCamera(int type){
        PictureSelector.create(this)
                .openCamera(SelectMimeType.ofImage())
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        for (LocalMedia media : result) {
                            if (media.getWidth() == 0 || media.getHeight() == 0) {
                                if (PictureMimeType.isHasImage(media.getMimeType())) {
                                    MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(getContext(), media.getPath());
                                    media.setWidth(imageExtraInfo.getWidth());
                                    media.setHeight(imageExtraInfo.getHeight());
                                }
                            }
                            LogUtil.i("lzx---》", "文件名: " + media.getFileName());
                            LogUtil.i(TAG, "是否压缩:" + media.isCompressed());
                            LogUtil.i(TAG, "压缩:" + media.getCompressPath());
                            LogUtil.i(TAG, "初始路径:" + media.getPath());
                            LogUtil.i(TAG, "绝对路径:" + media.getRealPath());
                            LogUtil.i(TAG, "是否裁剪:" + media.isCut());
                            LogUtil.i(TAG, "裁剪路径:" + media.getCutPath());
                            LogUtil.i(TAG, "是否开启原图:" + media.isOriginal());
                            LogUtil.i(TAG, "原图路径:" + media.getOriginalPath());
                            LogUtil.i(TAG, "沙盒路径:" + media.getSandboxPath());
                            LogUtil.i(TAG, "水印路径:" + media.getWatermarkPath());
                            LogUtil.i(TAG, "视频缩略图:" + media.getVideoThumbnailPath());
                            LogUtil.i(TAG, "原始宽高: " + media.getWidth() + "x" + media.getHeight());
                            LogUtil.i(TAG, "裁剪宽高: " + media.getCropImageWidth() + "x" + media.getCropImageHeight());
                            LogUtil.i(TAG, "文件大小: " + PictureFileUtils.formatAccurateUnitFileSize(media.getSize()));
                            LogUtil.i(TAG, "可用路径: " + media.getAvailablePath());

                            switch (type){
                                case 888://多张
                                    ImageViewData imageViewDataPro = new ImageViewData();
                                    imageViewDataPro.mImageSize = PictureFileUtils.formatAccurateUnitFileSize(media.getSize());
                                    imageViewDataPro.mImageViewUrl = media.getCompressPath();
                                    imageViewDataPro.mPathName = media.getFileName();
                                    imageViewDataListProblem.add(imageViewDataPro);
                                    break;
                                default:
                                    break;
                            }


                        }
                        switch (type){
                            case 888://多张
                                binding.recyclerviewProblem.setVisibility(View.VISIBLE);
                                imgeViewProblemAdapter.refreshDataList(imageViewDataListProblem);
                                List<String> proPicList = new ArrayList<>();
                                for (LocalMedia localMedia : result) {
                                    proPicList.add(localMedia.getCompressPath());
                                }
                                upLoadImgBatch(proPicList);
                                break;
                            default:
                                break;
                        }

                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }


    private void upLoadImgBatch(List<String> filePath) {
        showLoading("正在上传中...");
        HttpRequest.upLoadBatchImg(ReviewInfoActivity.this, filePath, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, BatchImgBean content) {
                LogUtil.d(TAG, "上传图片批量" + content.toString());
                if (content.status == 0) {
                    Objects.requireNonNull(RxToast.success(ReviewInfoActivity.this, "上传成功"));
                    hideLoading();

                    for (String path : content.result) {
                        reexaminationFillingBean.ReviewProblemShooting.imageList.add(path);
                    }
                    LogUtil.d(TAG, "上传图片批量" + reexaminationFillingBean.ReviewProblemShooting.imageList.toString());
                } else {
                    Objects.requireNonNull(RxToast.error(ReviewInfoActivity.this, "上传失败~"));
                }

            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }



    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(ReviewInfoActivity.this);
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

    private static Bitmap saveWaterMask(Context context, int position, Bitmap sourBitmap) {
        WaterMaskViewCheck waterMaskView = new WaterMaskViewCheck(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        waterMaskView.setLayoutParams(params);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08")); //设置时区
        Date curDate = new Date(System.currentTimeMillis()); //获取当前时间
        String createDate = formatter.format(curDate);   //格式转换

        waterMaskView.setInfoDate(createDate);
        waterMaskView.setInfoZuoBiao("104.06519942516327，30.5780102618698");
        Bitmap waterBitmap = WaterMaskUtil.convertViewToBitmap(waterMaskView);
        //根据原图处理要生成的水印的宽高
        float width = sourBitmap.getWidth();
        float height = sourBitmap.getHeight();
        float be = width / height;

        if ((float) 16 / 9 >= be && be >= (float) 4 / 3) {
            //在图片比例区间内16;9~4：3内，将生成的水印bitmap设置为原图宽高各自的1/5
            waterBitmap = WaterMaskUtil.zoomBitmap(waterBitmap, (int) width / 5, (int) height / 5);
        } else if (be > (float) 16 / 9) {
            //生成4：3的水印
            waterBitmap = WaterMaskUtil.zoomBitmap(waterBitmap, (int) width / 5, (int) width * 3 / 20);
        } else if (be < (float) 4 / 3) {
            //生成4：3的水印
            waterBitmap = WaterMaskUtil.zoomBitmap(waterBitmap, (int) height * 4 / 15, (int) height / 5);
        }
        Bitmap watermarkBitmap = null;
        switch (position) {
            case 0:
                watermarkBitmap = WaterMaskUtil.createWaterMaskLeftTop(context, sourBitmap, waterBitmap, 0, 0);
                break;
            case 1:
                watermarkBitmap = WaterMaskUtil.createWaterMaskRightTop(context, sourBitmap, waterBitmap, 0, 0);
                break;
            case 2:
                watermarkBitmap = WaterMaskUtil.createWaterMaskRightBottom(context, sourBitmap, waterBitmap, 0, 0);
                break;
            case 3:
                watermarkBitmap = WaterMaskUtil.createWaterMaskLeftBottom(context, sourBitmap, waterBitmap, 0, 0);
                break;
            case 4:
                watermarkBitmap = WaterMaskUtil.createWaterMaskCenter(sourBitmap, waterBitmap);
                break;
        }
        return watermarkBitmap;

    }
}
