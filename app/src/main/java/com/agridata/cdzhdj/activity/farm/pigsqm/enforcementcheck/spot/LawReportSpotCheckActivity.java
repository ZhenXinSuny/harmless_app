package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.spot;

import static com.agridata.cdzhdj.base.MyApplication.getContext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.mine.pic.PicActivity;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.adapter.ImgeViewProblemAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.BatchImgBean;
import com.agridata.cdzhdj.data.ImageViewData;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.ProvinceData;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.law.EnforcementData;
import com.agridata.cdzhdj.databinding.ActivityLawReportSpotCheckBinding;
import com.agridata.cdzhdj.utils.PictureSelectorUtils;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.utils.WaterMaskUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.WaterMaskViewCheck;
import com.agridata.cdzhdj.view.address.AddressDialogUtil;
import com.agridata.cdzhdj.view.address.AddressPickerDialog;
import com.agridata.cdzhdj.view.address.OnAddressChangeListener;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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
 * @Date : on 2023-04-14 18:04.
 * @Description :描述  执法抽查
 */
public class LawReportSpotCheckActivity extends BaseActivity<ActivityLawReportSpotCheckBinding>  implements View.OnClickListener, OnAddressChangeListener {
    private final static String TAG = "LawReportSpotCheckActivity------》";
    private CustomLoadingDialog mLoadingDialog;
    private EnforcementData enforcementData;
    private LoginData.ResultBean userData;




    private ImgeViewProblemAdapter imgeViewProblemAdapter;
    private final List<ImageViewData> imageViewDataListProblem = new ArrayList<>();


    private AddressPickerDialog dialog;
    private int mAddressId;

    public static void start(Context context) {
        Intent intent = new Intent(context, LawReportSpotCheckActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityLawReportSpotCheckBinding getBinding() {
        return ActivityLawReportSpotCheckBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        binding.titlebarLeft.setOnClickListener(v -> finish());
        getAddressInfo();
        binding.addressEt.setOnClickListener(this);//区划


        enforcementData = new EnforcementData();//提交的原数据
       //主办执法单位
        enforcementData.SponsorEnforcementUnit = new EnforcementData.SponsorEnforcementUnitBean();
        //主办人员
        enforcementData.Organizer = new EnforcementData.OrganizerBean();
        enforcementData.SamplePhotos = new EnforcementData.SamplePhotosBean();
        enforcementData.SamplePhotos.imageList = new ArrayList<>();


        userData = UserDataSP.getInstance().getUserInfo().Result;
        binding.hostUnitTv.setText(userData.dependency.Dep_AgencyMID.Name);
        binding.hostPersonTv.setText(userData.mobile);




        binding.recyclerviewSamplePhotos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        imgeViewProblemAdapter = new ImgeViewProblemAdapter(R.layout.item_be_inspected_pic, this);
        binding.recyclerviewSamplePhotos.setAdapter(imgeViewProblemAdapter);



        imgeViewProblemAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                PicActivity.start(LawReportSpotCheckActivity.this, imgeViewProblemAdapter.getData(position).mImageViewUrl);
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });

        binding.addressEt.setOnClickListener(this);
        binding.samplePhotosLl.setOnClickListener(this);
        binding.zfryIv.setOnClickListener(this);//执法人员
        binding.bjcfzrIv.setOnClickListener(this);//单位负责人
        binding.jzrIv.setOnClickListener(this);//见证人
        binding.submitBtn.setOnClickListener(this);//提交

        enforcementData.SponsorEnforcementUnit.Name = userData.dependency.Dep_AgencyMID.Name;
        enforcementData.SponsorEnforcementUnit.Mid = userData.dependency.Dep_AgencyMID.Mid;
        enforcementData.Organizer.Name = userData.name;
        enforcementData.Organizer.UserID = userData.dependency.Mid;
    }

    @Override
    protected void initDatas() {

    }
    @Override
    protected void OnEventMainThread() {
        super.OnEventMainThread();
        mRxManager.on("SpotCheckZFRY", o -> {
            String ZhiFaPerson = (String) o;
            LogUtil.d(TAG,"ZhiFaPerson" +ZhiFaPerson);

            Bitmap sourBitmap = BitmapFactory.decodeFile(ZhiFaPerson);
            Bitmap WaterBitmap = saveWaterMask(LawReportSpotCheckActivity.this, 4, sourBitmap);
            String fileName = "IMG_" + new Date().getTime() + "DWFZRQMPic" + ".png";
            ZhiFaPerson = WaterMaskUtil.saveQNext(WaterBitmap, LawReportSpotCheckActivity.this, fileName, 60);

            upLoadImg(ZhiFaPerson,2);
        });
        mRxManager.on("SpotCheckDWFZRQM", o -> {
            String SpotCheckDWFZRQM = (String) o;
            LogUtil.d(TAG,"SpotCheckDWFZRQM" +SpotCheckDWFZRQM);

            Bitmap sourBitmap = BitmapFactory.decodeFile(SpotCheckDWFZRQM);
            Bitmap WaterBitmap = saveWaterMask(LawReportSpotCheckActivity.this, 4, sourBitmap);
            String fileName = "IMG_" + new Date().getTime() + "SpotCheckDWFZRQM" + ".png";
            SpotCheckDWFZRQM = WaterMaskUtil.saveQNext(WaterBitmap, LawReportSpotCheckActivity.this, fileName, 60);


            upLoadImg(SpotCheckDWFZRQM,3);
        });
        mRxManager.on("SpotCheckJZRQM", o -> {
            String SpotCheckJZRQM = (String) o;
            LogUtil.d(TAG,"SpotCheckJZRQM" +SpotCheckJZRQM);

            Bitmap sourBitmap = BitmapFactory.decodeFile(SpotCheckJZRQM);
            Bitmap WaterBitmap = saveWaterMask(LawReportSpotCheckActivity.this, 4, sourBitmap);
            String fileName = "IMG_" + new Date().getTime() + "SpotCheckJZRQM" + ".png";
            SpotCheckJZRQM = WaterMaskUtil.saveQNext(WaterBitmap, LawReportSpotCheckActivity.this, fileName, 60);


            upLoadImg(SpotCheckJZRQM,4);
        });
    }
    @Override
    public void onClick(View v) {
        if (v==binding.addressEt){//区划
            showAddressDialog();
        }else  if (v==binding.samplePhotosLl){//发现问题拍照
            getCamera(888);
        }else  if (v==binding.zfryIv){//执法人员签名
            SignSpotCheckActivity.start(LawReportSpotCheckActivity.this,"执法人员签名");
        }else  if (v==binding.bjcfzrIv){//单位负责人签名
            SignSpotCheckActivity.start(LawReportSpotCheckActivity.this,"被检查单位负责人签名");
        }else if (v==binding.jzrIv) {//见证人签名
            SignSpotCheckActivity.start(LawReportSpotCheckActivity.this, "见证人签名");
        }else  if (v== binding.submitBtn){
            SubmitData();
        }
    }
    private void  getAddressInfo(){
        HttpRequest.getRegionInfo(LawReportSpotCheckActivity.this, 814883,4, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, ProvinceData provinceData) {
                LogUtil.d("lzx---------》", provinceData.toString());
                AddressDialogUtil.setAddressInfo(provinceData.data.children);
            }
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(LawReportSpotCheckActivity.this,error));
            }
        });
    }
    private void showAddressDialog() {
        dialog = new AddressPickerDialog(this);
        dialog.setOnAddressChangeListener(this);
        dialog.show();
        dialog.setData(AddressDialogUtil.data1, AddressDialogUtil.map2, AddressDialogUtil.map3);
    }

    @Override
    public void onAddressChange(String address, int id, int type,EnforcementData.RegionBean regionBean) {
        Log.d(TAG, address);
        Log.d(TAG, "id:" + id);
        Log.d(TAG, "regionBean:" + regionBean);
        binding.addressEt.setText(address);
        this.mAddressId = id;
        dialog.dismiss();

        enforcementData.Region  =  regionBean ;
    }

    @Override
    public void onDismiss(int type) {
        dialog.dismiss();
    }


    /**
     * 提交数据
     */
    private void SubmitData(){
        enforcementData.CompanyName = binding.nameUnitEt.getText().toString();//单位名称（个人姓名）
        enforcementData.LegalRepresentative = binding.legalRepresentativeEt.getText().toString();//法定代表人（负责人）
        enforcementData.Tel = binding.telEt.getText().toString();//联系电话
        enforcementData.DetailedAddress = binding.detailsAddressEt.getText().toString();//详细地址
        enforcementData.ContentOfRandomCheck = binding.contentOfRandomCheckEt.getText().toString();//抽查内容

        enforcementData. CheckType = 2;//1为填报 2抽查
        enforcementData.AssignmentStatus =3;
        //指定日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        //日历对象
        Calendar calendar = Calendar.getInstance();
        //获取当前日期
        calendar.setTime(new Date());
        System.out.println("当前时间：" + simpleDateFormat.format(calendar.getTime()));
        enforcementData.CommitTime = simpleDateFormat.format(calendar.getTime());
        if (checkInfo()) {
            LogUtil.d(TAG,"最终的提交数据"+enforcementData.toString());
            mCommitInfo(enforcementData);
        }
    }


    private void mCommitInfo(EnforcementData enforcementData){
        showLoading("数据提交中...");
        HttpRequest.addDataEnforcementData(LawReportSpotCheckActivity.this, enforcementData , new CallBackLis<>() {
            @Override
            public void onSuccess(String method, StatusData status) {
                hideLoading();
                if (status.ErrorCode==0){
                    Objects.requireNonNull(RxToast.success(LawReportSpotCheckActivity.this,"提交成功")) ;
                    finish();
                }
            }
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(LawReportSpotCheckActivity.this,error));
            }
        });
    }
    private boolean checkInfo() {



        if (TextUtils.isEmpty(binding.nameUnitEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(LawReportSpotCheckActivity.this, "请输入单位名称（个人姓名）"));
            return false;
        }

        if (TextUtils.isEmpty(binding.legalRepresentativeEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(LawReportSpotCheckActivity.this, "请输入法定代表人"));
            return false;
        }
        if (TextUtils.isEmpty(binding.telEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(LawReportSpotCheckActivity.this, "请输入联系电话"));
            return false;
        }
        if (TextUtils.isEmpty(enforcementData.Region.RegionName)) {
            Objects.requireNonNull(RxToast.warning(LawReportSpotCheckActivity.this, "请选择区划"));
            return false;
        }
        if (TextUtils.isEmpty(enforcementData.DetailedAddress)) {
            Objects.requireNonNull(RxToast.warning(LawReportSpotCheckActivity.this, "请输入详细地址"));
            return false;
        }
        if (TextUtils.isEmpty(enforcementData.EyewitnessSignature)) {
            Objects.requireNonNull(RxToast.warning(LawReportSpotCheckActivity.this, "请上传见证人签名"));
            return false;
        }
        if (TextUtils.isEmpty(enforcementData.UnitUnderInspectionSignature)) {
            Objects.requireNonNull(RxToast.warning(LawReportSpotCheckActivity.this, "请上传被检查单位负责人签名"));
            return false;
        }
        if (TextUtils.isEmpty(enforcementData.SignatureEnforcementOfficer)) {
            Objects.requireNonNull(RxToast.warning(LawReportSpotCheckActivity.this, "请上传执法人员签名"));
            return false;
        }
        if (enforcementData.SamplePhotos.imageList.size() == 0 ) {
            Objects.requireNonNull(RxToast.warning(LawReportSpotCheckActivity.this, "请上传抽查样品照片"));
            return false;
        }
        return  true;
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
                                    Bitmap sourBitmap1 = BitmapFactory.decodeFile(media.getAvailablePath());
                                    Bitmap WaterBitmap1 = saveWaterMask(LawReportSpotCheckActivity.this, 4, sourBitmap1);
                                    String fileName1 = "IMG_" + new Date().getTime() + "check" + ".png";
                                    if (Build.VERSION.SDK_INT < 29) {
                                        String sichuListPic = WaterMaskUtil.saveQNext(sourBitmap1, LawReportSpotCheckActivity.this, fileName1, 60);
                                        media.setCompressPath(sichuListPic);
                                    } else {
                                        String checkPic = WaterMaskUtil.saveQNext(WaterBitmap1, LawReportSpotCheckActivity.this, fileName1, 60);
                                        LogUtil.d("lzx----------->保存", checkPic);
                                        media.setCompressPath(checkPic);
                                    }


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
                                binding.recyclerviewSamplePhotos.setVisibility(View.VISIBLE);
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

    /**
     * filePath 上传图片
     *
     * @param filePath
     */
    private void upLoadImg(String filePath,int PicBtnType) {
        showLoading("正在上传中...");
        HttpRequest.upLoadImg(LawReportSpotCheckActivity.this, filePath, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d(TAG, "上传图片" + content.toString());
                if (content.Status == 0) {
                    Objects.requireNonNull(RxToast.success(LawReportSpotCheckActivity.this, "上传成功"));
                   if (PicBtnType == 2) {
                        enforcementData.SignatureEnforcementOfficer  = content.Result;//主办人员签名
                        Glide.with(LawReportSpotCheckActivity.this)
                                .load(UrlUtils.pic_url + content.Result)
                                .centerCrop()
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(binding.zfryIv);
                    } else if (PicBtnType == 3) {
                        enforcementData.UnitUnderInspectionSignature  = content.Result;//被检查单位负责人签名
                        Glide.with(LawReportSpotCheckActivity.this)
                                .load(UrlUtils.pic_url + content.Result)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .into(binding.bjcfzrIv);
                    } else if (PicBtnType == 4) {
                        enforcementData.EyewitnessSignature  = content.Result;//见证人签名
                        Glide.with(LawReportSpotCheckActivity.this)
                                .load(UrlUtils.pic_url + content.Result)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .into(binding.jzrIv);
                    }
                    hideLoading();
                } else {
                    Objects.requireNonNull(RxToast.error(LawReportSpotCheckActivity.this, content.Result));
                }

            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }


    private void upLoadImgBatch(List<String> filePath) {
        showLoading("正在上传中...");
        HttpRequest.upLoadBatchImg(LawReportSpotCheckActivity.this, filePath, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, BatchImgBean content) {
                LogUtil.d(TAG, "上传图片批量" + content.toString());
                if (content.status == 0) {
                    Objects.requireNonNull(RxToast.success(LawReportSpotCheckActivity.this, "上传成功"));
                    hideLoading();

                    for (String path : content.result) {
                        enforcementData.SamplePhotos.imageList.add(path);
                    }
                    LogUtil.d(TAG, "上传图片批量" + enforcementData.SamplePhotos.imageList.toString());
                } else {
                    Objects.requireNonNull(RxToast.error(LawReportSpotCheckActivity.this, "上传失败~"));
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
        this.mLoadingDialog = new CustomLoadingDialog(LawReportSpotCheckActivity.this);
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
