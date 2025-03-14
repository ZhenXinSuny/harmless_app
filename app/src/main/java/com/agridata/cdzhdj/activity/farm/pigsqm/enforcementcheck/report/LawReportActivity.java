package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.report;

import static com.agridata.cdzhdj.base.MyApplication.getContext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.adapter.ImgeViewAdapter;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.adapter.ImgeViewProblemAdapter;
import com.agridata.cdzhdj.activity.mine.pic.PicActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.BatchImgBean;
import com.agridata.cdzhdj.data.ImageViewData;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.ProvinceData;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.law.AgencyData;
import com.agridata.cdzhdj.data.law.AgencyPersonData;
import com.agridata.cdzhdj.data.law.AgriculturalMachineryFieldBean;
import com.agridata.cdzhdj.data.law.AgriculturalProductSafetyFieldBean;
import com.agridata.cdzhdj.data.law.CheckListData;
import com.agridata.cdzhdj.data.law.CheckResultData;
import com.agridata.cdzhdj.data.law.EnforcementData;
import com.agridata.cdzhdj.data.law.FeedFieldBean;
import com.agridata.cdzhdj.data.law.FertilizerFieldBean;
import com.agridata.cdzhdj.data.law.FieldPlantQuarantineBean;
import com.agridata.cdzhdj.data.law.FisheryAreaBean;
import com.agridata.cdzhdj.data.law.HogSlaughteringAreaBean;
import com.agridata.cdzhdj.data.law.LivestockPoultryFieldsBean;
import com.agridata.cdzhdj.data.law.PesticideFieldBean;
import com.agridata.cdzhdj.data.law.SeedFieldBean;
import com.agridata.cdzhdj.data.law.TerritoryData;
import com.agridata.cdzhdj.data.law.VeterinaryDrugFieldBean;
import com.agridata.cdzhdj.databinding.ActivityLawReportBinding;
import com.agridata.cdzhdj.utils.AssertDataUtil;
import com.agridata.cdzhdj.utils.LayoutNYRBUtils;
import com.agridata.cdzhdj.utils.LayoutSYOneRBUtils;
import com.agridata.cdzhdj.utils.LayoutSYTwoRBUtils;
import com.agridata.cdzhdj.utils.LayoutSeedRBUtils;
import com.agridata.cdzhdj.utils.PictureSelectorUtils;
import com.agridata.cdzhdj.utils.TimeDialogUtils;
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
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.PictureFileUtils;
import com.lzx.utils.RxToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * @Date : on 2023-03-31 13:49.
 * @Description :描述
 */
public class LawReportActivity extends BaseActivity<ActivityLawReportBinding> implements View.OnClickListener, OnAddressChangeListener {
    private final static String TAG = "LawReportActivity------》";
    private LoginData.ResultBean userData;
    private ImgeViewAdapter imgeViewAdapter;
    private final List<ImageViewData> imageViewDataList = new ArrayList<>();




    private ImgeViewProblemAdapter imgeViewProblemAdapter;
    private final List<ImageViewData> imageViewDataListProblem = new ArrayList<>();

    private OptionsPickerView territoryOptions;
    private final List<TerritoryData> territoryDataList = new ArrayList<>();
    private int territoryPos=0;
    private int territoryID;

    private final List<CheckListData> checkListDataList = new ArrayList<>();
    private final List<CheckListData> checkListDataTerritoryList  = new ArrayList<>();
    private OptionsPickerView CheckListOptions;
    private int CheckListCode;
    private int CheckListPos=0;

    private AddressPickerDialog dialog;
    private int mAddressId;


    private OptionsPickerView JointlyUnitOptions;
    private int JointlyUnitPos=0;
    private String JointlyUnitMid;

    private OptionsPickerView ReportingAgencyOptions;
    private int ReportingAgencyPos=0;
    private String ReportingAgencyMid;




    private OptionsPickerView JointlyUnitPersonOptions;
    private int JointlyUnitPersonPos=0;
    private String JointlyUnitPersonMid;


    private OptionsPickerView CheckResultOptions;
    private int CheckResultPos=0;
    private final List<CheckResultData > checkResultDataList = new ArrayList<>();


    private OptionsPickerView managementOptions;

    private OptionsPickerView animalOptions;

    private OptionsPickerView practiceProjectOptions;

    /************** 种子领域 *************/
    private  SeedFieldBean mSeedField = new SeedFieldBean();

    /************** 兽药领域one  **********/
    private  VeterinaryDrugFieldBean mVeterinaryDrugField = new VeterinaryDrugFieldBean();

    /************** 兽药领域two  **********/
    private  VeterinaryDrugFieldBean mVeterinaryDrugFieldTwo = new VeterinaryDrugFieldBean();

    /************** 饲料领域one  **********/
    private FeedFieldBean mFeedFieldBean = new FeedFieldBean();

    /************** 	生猪屠宰领域  **********/
    private HogSlaughteringAreaBean mHogSlaughteringAreaBean = new HogSlaughteringAreaBean();

    /************** 	植物检疫领域  **********/
    private FieldPlantQuarantineBean  mFieldPlantQuarantineBean= new FieldPlantQuarantineBean();

    /************** 	种畜禽领域  **********/
    private LivestockPoultryFieldsBean mLivestockPoultryFieldsBean= new LivestockPoultryFieldsBean();

    /************** 		渔业水产领域  **********/
    private FisheryAreaBean mFisheryAreaBean = new FisheryAreaBean();


    private CustomLoadingDialog mLoadingDialog;
    private EnforcementData enforcementData;

    private TimePickerView pvTime;
    private SeedFieldBean seedField;
    private VeterinaryDrugFieldBean veterinaryDrugFieldBean;
    private VeterinaryDrugFieldBean veterinaryDrugFieldBeanTwo;

    //农药
    private PesticideFieldBean mPesticideFieldBean = new PesticideFieldBean();
    private PesticideFieldBean  pesticideFieldBean;
    //农机
    private AgriculturalMachineryFieldBean agriculturalMachineryFieldBean = new AgriculturalMachineryFieldBean();
    //肥料领域
    private FertilizerFieldBean fertilizerFieldBean = new FertilizerFieldBean();

    //农产品质量安全领域
    private AgriculturalProductSafetyFieldBean agriculturalProductSafetyFieldBean = new AgriculturalProductSafetyFieldBean();


    private OptionsPickerView medicalInstitutionsTypeOptions;

    private OptionsPickerView useRegistrationOptions;

    public static void start(Context context) {
        Intent intent = new Intent(context, LawReportActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityLawReportBinding getBinding() {
        return ActivityLawReportBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        enforcementData = new EnforcementData();//提交的原数据
        //主办执法单位
        enforcementData.SponsorEnforcementUnit = new EnforcementData.SponsorEnforcementUnitBean();
        //协办人员
        enforcementData.CoOrganizer = new EnforcementData.CoOrganizerBean();
        //主办人员
        enforcementData.Organizer = new EnforcementData.OrganizerBean();
        //检查领域
        enforcementData.InspectionField = new EnforcementData.InspectionFieldBean();
        //检查领域
        enforcementData.CheckList = new EnforcementData.CheckListBean();
        //协办执法单位
        enforcementData.CoOrganizeEnforcementUnits = new EnforcementData.CoOrganizeEnforcementUnitsBean();

        //上报机构
        enforcementData.ReportingAgency = new EnforcementData.ReportingAgencyBean();
        //區劃
        enforcementData.Region = new EnforcementData.RegionBean();
        //检查结果
        enforcementData.InspectionResult = new EnforcementData.InspectionResultBean();
        //问题照片
        enforcementData.ProblemShooting = new EnforcementData.ProblemShootingBean();
        enforcementData.ProblemShooting.imageList = new ArrayList<>();
        //种子领域
        enforcementData.SeedField = new SeedFieldBean();

        getAddressInfo();
        userData = UserDataSP.getInstance().getUserInfo().Result;

        binding.hostUnitTv.setText(userData.dependency.Dep_AgencyMID.Name);
        binding.hostPersonTv.setText(userData.name);


        enforcementData.SponsorEnforcementUnit.Name = userData.dependency.Dep_AgencyMID.Name;
        enforcementData.SponsorEnforcementUnit.Mid = userData.dependency.Dep_AgencyMID.Mid;

        enforcementData.Organizer.Name = userData.name;
        enforcementData.Organizer.UserID = userData.dependency.Mid;


        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.addBeInspectedPicLl.setOnClickListener(this);
        binding.inspectionFieldEt.setOnClickListener(this);//领域选择
        binding.checkListEt.setOnClickListener(this);//表单选择
        binding.addressEt.setOnClickListener(this);//区划选择
        binding.jointlyUnitEt.setOnClickListener(this);//协办单位
        binding.coorganizerEt.setOnClickListener(this);//协办人员
        binding.problemPicLl.setOnClickListener(this);//发现问题拍照
        binding.checkResultEt.setOnClickListener(this);//检查结果
        binding.timesEt.setOnClickListener(this);//截至整改日期
        binding.reportingAgencyEt.setOnClickListener(this);//上報機構
        binding.submitBtn.setOnClickListener(this);//提交
        binding.zbryIv.setOnClickListener(this);//主办人员签名
        binding.bjcfzrIv.setOnClickListener(this);//
        binding.jzrIv.setOnClickListener(this);//
        binding.layoutPigThree.traffickingAnimalEt.setOnClickListener(this);//动物种类
        binding.layoutPigOne.managementSystemEt.setOnClickListener(this);//生猪领域 管理制度
        binding.layoutPigFour.practiceProjectEt.setOnClickListener(this);//生猪领域4 诊疗资质（执业项目）
        binding.layoutPigFour.medicalInstitutionsTypeEt.setOnClickListener(this);//生猪领域4 诊疗机构类型
        binding.layoutPigFour.useRegistrationEt.setOnClickListener(this);//使用登记

        binding.recyclerviewBeInspected.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        imgeViewAdapter = new ImgeViewAdapter(R.layout.item_be_inspected_pic, this);
        binding.recyclerviewBeInspected.setAdapter(imgeViewAdapter);

        imgeViewAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                PicActivity.start(LawReportActivity.this, imgeViewAdapter.getData(position).mImageViewUrl);
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });


        binding.recyclerviewProblem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        imgeViewProblemAdapter = new ImgeViewProblemAdapter(R.layout.item_be_inspected_pic, this);
        binding.recyclerviewProblem.setAdapter(imgeViewProblemAdapter);

        imgeViewProblemAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                PicActivity.start(LawReportActivity.this, imgeViewProblemAdapter.getData(position).mImageViewUrl);
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });



        String jsonStr = new AssertDataUtil().getStringData(LawReportActivity.this, "territory.json");//获取assets目录下的json文件数据

        Gson gson = new Gson();
        TerritoryData territoryData;
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                territoryData = gson.fromJson(jsonObj.toString(), TerritoryData.class);
                territoryDataList.add(territoryData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtil.d(TAG,"领域" +territoryDataList);
        String jsonStrCheckList = new AssertDataUtil().getStringData(LawReportActivity.this, "checklist.json");//获取assets目录下的json文件数据
        Gson gsonCheckList = new Gson();
        CheckListData checkListData;
        try {
            JSONArray jsonArray = new JSONArray(jsonStrCheckList);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                checkListData = gsonCheckList.fromJson(jsonObj.toString(), CheckListData.class);
                checkListDataList.add(checkListData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        LogUtil.d(TAG,"表单" +checkListDataList);


        getCheckResultInfo();
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void OnEventMainThread() {
        super.OnEventMainThread();
        mRxManager.on("ZBRYQM", o -> {
            String ZhuBanRenYuanPic = (String) o;
            LogUtil.d(TAG,"ZhuBanRenYuanPic" +ZhuBanRenYuanPic);

            Bitmap sourBitmap = BitmapFactory.decodeFile(ZhuBanRenYuanPic);
            Bitmap WaterBitmap = saveWaterMask(LawReportActivity.this, 4, sourBitmap);
            String fileName = "IMG_" + new Date().getTime() + "bjcr" + ".png";
            ZhuBanRenYuanPic = WaterMaskUtil.saveQNext(WaterBitmap, LawReportActivity.this, fileName, 60);

            upLoadImg(ZhuBanRenYuanPic,2);
        });
        mRxManager.on("DWFZRQM", o -> {
            String DWFZRQMPic = (String) o;
            LogUtil.d(TAG,"DWFZRQMPic" +DWFZRQMPic);


            Bitmap sourBitmap = BitmapFactory.decodeFile(DWFZRQMPic);
            Bitmap WaterBitmap = saveWaterMask(LawReportActivity.this, 4, sourBitmap);
            String fileName = "IMG_" + new Date().getTime() + "DWFZRQMPic" + ".png";
            DWFZRQMPic = WaterMaskUtil.saveQNext(WaterBitmap, LawReportActivity.this, fileName, 60);

            upLoadImg(DWFZRQMPic,3);
        });
        mRxManager.on("JZRQM", o -> {
            String JianZhengRenPic = (String) o;
            Bitmap sourBitmap = BitmapFactory.decodeFile(JianZhengRenPic);
            Bitmap WaterBitmap = saveWaterMask(LawReportActivity.this, 4, sourBitmap);
            String fileName = "IMG_" + new Date().getTime() + "JZRQM" + ".png";
            JianZhengRenPic = WaterMaskUtil.saveQNext(WaterBitmap, LawReportActivity.this, fileName, 60);

            LogUtil.d(TAG,"JianZhengRenPic" +JianZhengRenPic);
            upLoadImg(JianZhengRenPic,4);
        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.addBeInspectedPicLl){
            getCamera(666);
        }else if (v==binding.inspectionFieldEt){//领域选择
            setTerritoryUi();
        }else if (v==binding.checkListEt){
           if (!TextUtils.isEmpty(binding.inspectionFieldEt.getText().toString())){
               checkListDataTerritoryList.clear();
               for (CheckListData checkListData : checkListDataList) {
                   if (territoryID==checkListData.parentid){
                       checkListDataTerritoryList.add(checkListData);
                   }
               }
               setCheckListUi();
           }else {
             Objects.requireNonNull(RxToast.warning(this,"请选择填报领域"));
           }
        }else  if (v==binding.addressEt){
           showAddressDialog();
        }else  if (v==binding.jointlyUnitEt){//协办检查单位
            showLoading("正在加载中...");
            getAgencyInfo(1);
        }else  if (v==binding.coorganizerEt){
            if (!TextUtils.isEmpty(binding.jointlyUnitEt.getText().toString())){
                if (!TextUtils.isEmpty(JointlyUnitMid)){
                    showLoading("协助人员加载中...");
                    getAgencyPersonInfo(JointlyUnitMid);
                }

            }else {
               Objects.requireNonNull(RxToast.warning(LawReportActivity.this,"请先选择协助执法单位")) ;
            }
        }else  if (v==binding.problemPicLl){//发现问题拍照
            getCamera(888);
        }else  if (v==binding.checkResultEt){//检查结果
            setCheckResultUi();
        }else if (v==binding.timesEt){//整改截止日期
            initTimePicker2(LawReportActivity.this);
            pvTime.show();
        }else if (v==binding.submitBtn){//提交
            SubmitData();
        }else  if (v==binding.zbryIv){//主办人员签名
            SignCheckActivity.start(LawReportActivity.this,"主办人员签名");
        }else  if (v==binding.bjcfzrIv){//单位负责人签名
            SignCheckActivity.start(LawReportActivity.this,"被检查单位负责人签名");
        }else if (v==binding.jzrIv) {//见证人签名
            SignCheckActivity.start(LawReportActivity.this, "见证人签名");
        }else if (v==binding.layoutPigOne.managementSystemEt){//生猪领域 管理制度
            setManagementSystemUi();
        }else if (v==binding.layoutPigThree.traffickingAnimalEt){//生猪领域 3 动物选择
            setAnimalUi();

        }else  if (v==binding.layoutPigFour.practiceProjectEt){////生猪领域 4诊疗资质（执业项目）
            setPracticeProjectUi();
        }else  if (v== binding.layoutPigFour.medicalInstitutionsTypeEt){
            setMedicalInstitutionsTypeUi();
        }else if (v== binding.layoutPigFour.useRegistrationEt){//
            setUseRegistrationUi();
        }else if (v== binding.reportingAgencyEt){//上報機構
            showLoading("正在加载中...");
            getAgencyInfo(2);
        }
    }

   private void getCamera(int type){
       imageViewDataList.clear();
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
                               case 666://单张
                                   Bitmap sourBitmap = BitmapFactory.decodeFile(media.getAvailablePath());
                                   Bitmap WaterBitmap = saveWaterMask(LawReportActivity.this, 4, sourBitmap);
                                   String fileName = "IMG_" + new Date().getTime() + "check" + ".png";

                                   if (Build.VERSION.SDK_INT < 29) {
                                       String sichuListPic = WaterMaskUtil.saveQNext(WaterBitmap, LawReportActivity.this, fileName, 60);
                                       media.setCompressPath(sichuListPic);
                                   } else {
                                       String checkPic = WaterMaskUtil.saveQNext(WaterBitmap, LawReportActivity.this, fileName, 60);
                                       LogUtil.d("lzx----------->保存", checkPic);
                                       media.setCompressPath(checkPic);
                                   }

                                   ImageViewData imageViewData = new ImageViewData();
                                   imageViewData.mImageSize = PictureFileUtils.formatAccurateUnitFileSize(media.getSize());
                                   imageViewData.mImageViewUrl = media.getCompressPath();
                                   imageViewData.mPathName = media.getFileName();
                                   imageViewDataList.add(imageViewData);
                                   break;
                               case 888://多张

                                   Bitmap sourBitmap1 = BitmapFactory.decodeFile(media.getAvailablePath());
                                   Bitmap WaterBitmap1 = saveWaterMask(LawReportActivity.this, 4, sourBitmap1);
                                   String fileName1 = "IMG_" + new Date().getTime() + "check" + ".png";

                                   if (Build.VERSION.SDK_INT < 29) {
                                       String sichuListPic = WaterMaskUtil.saveQNext(sourBitmap1, LawReportActivity.this, fileName1, 60);
                                       media.setCompressPath(sichuListPic);
                                   } else {
                                       String checkPic = WaterMaskUtil.saveQNext(WaterBitmap1, LawReportActivity.this, fileName1, 60);
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
                           case 666://单张
                               binding.recyclerviewBeInspected.setVisibility(View.VISIBLE);
                               imgeViewAdapter.refreshDataList(imageViewDataList);
                               upLoadImg(imageViewDataList.get(0).mImageViewUrl,1);
                               break;
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

    //制度管理
    private void setManagementSystemUi(){
        List<String> listString = new ArrayList<>();
        listString.add("免疫制度");
        listString.add("检疫申报制度");
        listString.add("用药制度");
        listString.add("疫情报告制度");
        listString.add("消毒制度");
        listString.add("无害化处理制度");
        listString.add("其他");
        managementOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.layoutPigOne.managementSystemEt.setText(listString.get(options1));

        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit =  v.findViewById(R.id.tv_finish);
                    final TextView title_tv =  v.findViewById(R.id.title_tv);
                    title_tv.setText("管理制度");
                    tvSubmit.setOnClickListener(v1 -> {
                        managementOptions.returnData();
                        managementOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> managementOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(18)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setSelectOptions(0)
                .build();
        managementOptions.setPicker(listString);//添加数据
        managementOptions.show();
    }


    //制度管理
    private void setAnimalUi(){
        List<String> listString = new ArrayList<>();
        listString.add("猪");
        listString.add("牛");
        listString.add("羊");
        listString.add("禽");
        listString.add("其他");
        animalOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.layoutPigThree.traffickingAnimalEt.setText(listString.get(options1));

        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit =  v.findViewById(R.id.tv_finish);
                    final TextView title_tv =  v.findViewById(R.id.title_tv);
                    title_tv.setText("动物选择");
                    tvSubmit.setOnClickListener(v1 -> {
                        animalOptions.returnData();
                        animalOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> animalOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(18)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setSelectOptions(0)
                .build();
        animalOptions.setPicker(listString);//添加数据
        animalOptions.show();
    }

    /**
     * 生猪领域4  执业项目
     */
    private void setPracticeProjectUi(){
        List<String> listString = new ArrayList<>();
        listString.add("动物疫病诊断");
        listString.add("动物疫病治疗");
        listString.add("动物免疫");
        listString.add("动物护理和保健");
        listString.add("动物小型手术");
        listString.add("动物三腔（颅腔、胸腔、腹腔）手术");
        listString.add("动物健康检查");
        listString.add("宠物芯片埋植");
        listString.add("其它");
        practiceProjectOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.layoutPigFour.practiceProjectEt.setText(listString.get(options1));

        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit =  v.findViewById(R.id.tv_finish);
                    final TextView title_tv =  v.findViewById(R.id.title_tv);
                    title_tv.setText("执业项目");
                    tvSubmit.setOnClickListener(v1 -> {
                        practiceProjectOptions.returnData();
                        practiceProjectOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> practiceProjectOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(18)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setSelectOptions(0)
                .build();
        practiceProjectOptions.setPicker(listString);//添加数据
        practiceProjectOptions.show();
    }


    /**
     * 生猪领域4  诊疗机构类型
     */
    private void setMedicalInstitutionsTypeUi(){
        List<String> listString = new ArrayList<>();
        listString.add("医院");
        listString.add("诊所");
        medicalInstitutionsTypeOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.layoutPigFour.medicalInstitutionsTypeEt.setText(listString.get(options1));
        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit =  v.findViewById(R.id.tv_finish);
                    final TextView title_tv =  v.findViewById(R.id.title_tv);
                    title_tv.setText("诊疗机构类型");
                    tvSubmit.setOnClickListener(v1 -> {
                        medicalInstitutionsTypeOptions.returnData();
                        medicalInstitutionsTypeOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> medicalInstitutionsTypeOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(18)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setSelectOptions(0)
                .build();
        medicalInstitutionsTypeOptions.setPicker(listString);//添加数据
        medicalInstitutionsTypeOptions.show();
    }

    /**
     * 生猪领域4  使用登记
     */
    private void setUseRegistrationUi(){
        List<String> listString = new ArrayList<>();
        listString.add("疫苗");
        listString.add("免疫证（标）");
        listString.add("诊断液");
        listString.add("药品（毒性药品、麻醉药品）");
        listString.add("器械");
        listString.add("其他");
        useRegistrationOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.layoutPigFour.useRegistrationEt.setText(listString.get(options1));
        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit =  v.findViewById(R.id.tv_finish);
                    final TextView title_tv =  v.findViewById(R.id.title_tv);
                    title_tv.setText("使用登记");
                    tvSubmit.setOnClickListener(v1 -> {
                        useRegistrationOptions.returnData();
                        useRegistrationOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> useRegistrationOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(18)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setSelectOptions(0)
                .build();
        useRegistrationOptions.setPicker(listString);//添加数据
        useRegistrationOptions.show();
    }
   //选取领域
   private void setTerritoryUi(){
       List<String> listString = new ArrayList<>();
       for (TerritoryData territoryData : territoryDataList) {
           LogUtil.d(TAG,"数据" + territoryData.name);
           listString.add(territoryData.name);
       }
       territoryOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
           binding.inspectionFieldEt.setText(territoryDataList.get(options1).name);
           territoryID = territoryDataList.get(options1).code;
           territoryPos = options1;
           binding.checkListEt.setText("");

        if(territoryID==111 ||territoryID==112){
          binding.checkListLl.setVisibility(View.GONE);
            binding.checkListViewLl.setVisibility(View.GONE);
        }else {
            binding.checkListLl.setVisibility(View.VISIBLE);
            binding.checkListViewLl.setVisibility(View.VISIBLE);
        }

           enforcementData.InspectionField.Name = territoryDataList.get(options1).name;
           enforcementData.InspectionField.Id = String.valueOf(territoryDataList.get(options1).code);
       })
               .setLayoutRes(R.layout.custom_unit_select, v -> {
                   final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                   final TextView tvSubmit =  v.findViewById(R.id.tv_finish);
                   final TextView title_tv =  v.findViewById(R.id.title_tv);
                   title_tv.setText("领域选择");
                   tvSubmit.setOnClickListener(v1 -> {
                       territoryOptions.returnData();
                       territoryOptions.dismiss();
                   });
                   iv_cancel.setOnClickListener(v12 -> territoryOptions.dismiss());
               })
               .isDialog(false)
               .setContentTextSize(18)//滚轮文字大小
               .setLineSpacingMultiplier(3.0f)
               .setSelectOptions(territoryPos)
               .build();
       territoryOptions.setPicker(listString);//添加数据
       territoryOptions.show();
   }
   //选择表单
    private void setCheckListUi(){
        List<String> listString = new ArrayList<>();
        for (CheckListData checkListData : checkListDataTerritoryList) {
            listString.add(checkListData.name);
        }
        CheckListOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.checkListEt.setText(checkListDataTerritoryList.get(options1).name);
            CheckListPos = options1;
            CheckListCode = checkListDataTerritoryList.get(options1).code;

            enforcementData.CheckList.Name = checkListDataTerritoryList.get(options1).name;
            enforcementData.CheckList.Id = String.valueOf(checkListDataTerritoryList.get(options1).code);

            switch (checkListDataTerritoryList.get(options1).code){
                case 101://种子（一张表）

                    setBeanNull();
                    setSeedCheckFalse();
                    LogUtil.d(TAG,"种子（一张表）" + mSeedField.toString());
                    binding.layoutSeed.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 201://兽药（one）
                    setBeanNull();
                    setSyOneCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 202://兽药（two）
                    setBeanNull();
                    setSyTwoCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 301://农药
                    setBeanNull();
                    setNYCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 401://农机one
                    setBeanNull();
                    setNJOneCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 402://农机two
                    setBeanNull();
                    setNJTwoCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 403://农机three
                    setBeanNull();
                    setNJThreeCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 404://农机4
                    setBeanNull();
                    setNJFourCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 501://肥料领域
                    setBeanNull();
                    setFLCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 601://农产品安全领域
                    setBeanNull();
                    setNCPCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 701://饲料领域 1
                    setBeanNull();
                    setSLOneCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 702://饲料领域 2
                    setBeanNull();
                    setSLTwoCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 801://	生猪屠宰领域 1
                    setBeanNull();
                    setPigOneCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 802://	生猪屠宰领域 2
                    setBeanNull();
                    setPigTwoCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 803://	生猪屠宰领域 3
                    setBeanNull();
                    setPigThreeCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 804://	生猪屠宰领域 4
                    setBeanNull();
                    setPigFourCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 805://	生猪屠宰领域 5
                    setBeanNull();
                    setPigFiveCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 806://	生猪屠宰领域 6
                    setBeanNull();
                    setPigSixCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 901://	植物检疫领域
                    setBeanNull();
                    setPlantCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 1001://	种畜禽领域 成都市畜禽养殖场执法检查记录表
                    setBeanNull();
                    setZXQOneCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 1002://	种畜禽领域 成都市种畜禽和水产种苗执法检查记录表
                    setBeanNull();
                    setZXQTwoCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 1101://	渔业水产领域 成都市水产养殖场执法检查记录表
                    setBeanNull();
                    setFishOneCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 1102://	渔业水产领域 成都市种畜禽和水产种苗执法检查记录表
                    setBeanNull();
                    setFishTwoCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.VISIBLE);
                    binding.layoutFishThree.getRoot().setVisibility(View.GONE);
                    break;
                case 1103://	渔业水产领域 成都市渔船执法检查记录表
                    setBeanNull();
                    setFishThreeCheckFalse();
                    binding.layoutSeed.getRoot().setVisibility(View.GONE);
                    binding.layoutSyOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSyTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNy.getRoot().setVisibility(View.GONE);
                    binding.layoutNjOne.getRoot().setVisibility(View.GONE);
                    binding.layoutNjTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutNjThree.getRoot().setVisibility(View.GONE);
                    binding.layoutNjFour.getRoot().setVisibility(View.GONE);
                    binding.layoutFl.getRoot().setVisibility(View.GONE);
                    binding.layoutNcpaq.getRoot().setVisibility(View.GONE);
                    binding.layoutSlOne.getRoot().setVisibility(View.GONE);
                    binding.layoutSlTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigOne.getRoot().setVisibility(View.GONE);
                    binding.layoutPigTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutPigThree.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFour.getRoot().setVisibility(View.GONE);
                    binding.layoutPigFive.getRoot().setVisibility(View.GONE);
                    binding.layoutPigSix.getRoot().setVisibility(View.GONE);
                    binding.layoutPlant.getRoot().setVisibility(View.GONE);
                    binding.layoutBreed.getRoot().setVisibility(View.GONE);
                    binding.layoutBreedTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishOne.getRoot().setVisibility(View.GONE);
                    binding.layoutFishTwo.getRoot().setVisibility(View.GONE);
                    binding.layoutFishThree.getRoot().setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit =  v.findViewById(R.id.tv_finish);
                    final TextView title_tv =  v.findViewById(R.id.title_tv);
                    title_tv.setText("表单选择");
                    tvSubmit.setOnClickListener(v1 -> {
                        CheckListOptions.returnData();
                        CheckListOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> CheckListOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(16)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setItemVisibleCount(9)
                .setSelectOptions(CheckListPos)
                .build();
        CheckListOptions.setPicker(listString);//添加数据
        CheckListOptions.show();
    }

    private void setBeanNull(){
        mSeedField = new SeedFieldBean();
        seedField = new SeedFieldBean();
        mVeterinaryDrugField = new VeterinaryDrugFieldBean();
        veterinaryDrugFieldBean = new VeterinaryDrugFieldBean();
        mVeterinaryDrugFieldTwo = new VeterinaryDrugFieldBean();
        veterinaryDrugFieldBeanTwo = new VeterinaryDrugFieldBean();
        mPesticideFieldBean = new PesticideFieldBean();
        agriculturalMachineryFieldBean = new AgriculturalMachineryFieldBean();
        fertilizerFieldBean = new FertilizerFieldBean();
        agriculturalProductSafetyFieldBean = new AgriculturalProductSafetyFieldBean();
        mFeedFieldBean = new FeedFieldBean();
        mHogSlaughteringAreaBean = new HogSlaughteringAreaBean();
        mFieldPlantQuarantineBean = new FieldPlantQuarantineBean();
        mLivestockPoultryFieldsBean = new LivestockPoultryFieldsBean();
        mFisheryAreaBean = new FisheryAreaBean();
    }
    //选择执法单位
    private void setJointlyUnitUi(List<AgencyData.Result> agencyDataList){
        List<String> listString = new ArrayList<>();
        for (AgencyData.Result agencyData : agencyDataList) {
            listString.add(agencyData.name);
        }
        JointlyUnitOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.coorganizerEt.setText("");
            binding.jointlyUnitEt.setText(agencyDataList.get(options1).name);
            JointlyUnitPos = options1;
            JointlyUnitMid = agencyDataList.get(options1).mid;


            enforcementData.CoOrganizeEnforcementUnits.Name = agencyDataList.get(options1).name;
            enforcementData.CoOrganizeEnforcementUnits.Mid = agencyDataList.get(options1).mid;
        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit =  v.findViewById(R.id.tv_finish);
                    final TextView title_tv =  v.findViewById(R.id.title_tv);
                    title_tv.setText("协办执法单位选择");
                    tvSubmit.setOnClickListener(v1 -> {
                        JointlyUnitOptions.returnData();
                        JointlyUnitOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> JointlyUnitOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(16)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setItemVisibleCount(9)
                .setSelectOptions(JointlyUnitPos)
                .build();
        JointlyUnitOptions.setPicker(listString);//添加数据
        JointlyUnitOptions.show();
    }


    private void setReportingAgencyUi(List<AgencyData.Result> agencyDataList){
        List<String> listString = new ArrayList<>();
        for (AgencyData.Result agencyData : agencyDataList) {
            listString.add(agencyData.name);
        }
        ReportingAgencyOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.reportingAgencyEt.setText(agencyDataList.get(options1).name);
            ReportingAgencyPos = options1;
            ReportingAgencyMid = agencyDataList.get(options1).mid;

            enforcementData.ReportingAgency.Name = agencyDataList.get(options1).name;
            enforcementData.ReportingAgency.Mid = agencyDataList.get(options1).mid;
        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit =  v.findViewById(R.id.tv_finish);
                    final TextView title_tv =  v.findViewById(R.id.title_tv);
                    title_tv.setText("上报机构");
                    tvSubmit.setOnClickListener(v1 -> {
                        ReportingAgencyOptions.returnData();
                        ReportingAgencyOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> ReportingAgencyOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(16)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setItemVisibleCount(9)
                .setSelectOptions(ReportingAgencyPos)
                .build();
        ReportingAgencyOptions.setPicker(listString);//添加数据
        ReportingAgencyOptions.show();
    }

    //选择执法单位人员
    private void setJointlyUnitPersonUi(List<AgencyPersonData.Result> agencyPersonDataList){
        List<String> listString = new ArrayList<>();
        for (AgencyPersonData.Result agencyData : agencyPersonDataList) {
            listString.add(agencyData.name);
        }
            JointlyUnitPersonOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.coorganizerEt.setText(agencyPersonDataList.get(options1).name);
            JointlyUnitPersonPos = options1;
            JointlyUnitPersonMid = agencyPersonDataList.get(options1).mid;

                //协办人员
                enforcementData.CoOrganizer.Name = agencyPersonDataList.get(options1).name;
                enforcementData.CoOrganizer.Mid = agencyPersonDataList.get(options1).mid;
        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit =  v.findViewById(R.id.tv_finish);
                    final TextView title_tv =  v.findViewById(R.id.title_tv);
                    title_tv.setText("协办人员选择");
                    tvSubmit.setOnClickListener(v1 -> {
                        JointlyUnitPersonOptions.returnData();
                        JointlyUnitPersonOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> JointlyUnitPersonOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(16)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setItemVisibleCount(9)
                .setSelectOptions(JointlyUnitPersonPos)
                .build();
        JointlyUnitPersonOptions.setPicker(listString);//添加数据
        JointlyUnitPersonOptions.show();
    }

    /**
     * 解析检查结果方法
     */
    private  void   getCheckResultInfo(){
        String checkresult = new AssertDataUtil().getStringData(LawReportActivity.this, "checkresult.json");//获取assets目录下的json文件数据
        Gson gsonCheckResult = new Gson();
        CheckResultData checkResultData;
        try {
            JSONArray jsonArray = new JSONArray(checkresult);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                checkResultData = gsonCheckResult.fromJson(jsonObj.toString(), CheckResultData.class);
                checkResultDataList.add(checkResultData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LogUtil.d(TAG,"检查结果" +checkResultDataList);
    }
    //检查结果选择
    private void setCheckResultUi(){
        List<String> listString = new ArrayList<>();
        for (CheckResultData checkResultData : checkResultDataList) {
            listString.add(checkResultData.Name);
        }
        CheckResultOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.checkResultEt.setText(checkResultDataList.get(options1).Name);
            CheckResultPos = options1;
            binding.reportingAgencyEt.setText("");
            binding.timesEt.setText("");

            if (!"1".equals(checkResultDataList.get(options1).ID)){
                binding.takePicLlView.setVisibility(View.VISIBLE);
                binding.takePicLl.setVisibility(View.VISIBLE);
            }else {
                binding.takePicLlView.setVisibility(View.GONE);
                binding.takePicLl.setVisibility(View.GONE);
            }


            if ("4".equals(checkResultDataList.get(options1).ID)){
                binding.timesLl.setVisibility(View.VISIBLE);
                binding.reportingAgencyLl.setVisibility(View.VISIBLE);
            }else {
                binding.timesLl.setVisibility(View.GONE);
                binding.reportingAgencyLl.setVisibility(View.GONE);

            }
            //检查结果
            enforcementData.InspectionResult.Name = checkResultDataList.get(options1).Name;
            enforcementData.InspectionResult.Id = checkResultDataList.get(options1).ID;
        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit =  v.findViewById(R.id.tv_finish);
                    final TextView title_tv =  v.findViewById(R.id.title_tv);
                    title_tv.setText("检查结果选择");
                    tvSubmit.setOnClickListener(v1 -> {
                        CheckResultOptions.returnData();
                        CheckResultOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> CheckResultOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(16)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setItemVisibleCount(9)
                .setSelectOptions(CheckResultPos)
                .build();
        CheckResultOptions.setPicker(listString);//添加数据
        CheckResultOptions.show();
    }

    private void  getAddressInfo(){
        HttpRequest.getRegionInfo(LawReportActivity.this, 814883,4, new CallBackLis<ProvinceData>() {
            @Override
            public void onSuccess(String method, ProvinceData provinceData) {
                LogUtil.d("lzx---------》", provinceData.toString());
                AddressDialogUtil.setAddressInfo(provinceData.data.children);
            }
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(LawReportActivity.this,error));
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
     * 获取机构
     */
    private void  getAgencyInfo(int type){
        HttpRequest.getAgencyData(LawReportActivity.this, new CallBackLis<AgencyData>() {
            @Override
            public void onSuccess(String method, AgencyData agencyData) {
                hideLoading();
                if (agencyData.status==0){
                    if (type==1){
                        setJointlyUnitUi(agencyData.result);
                    }else {
                        setReportingAgencyUi(agencyData.result);
                    }

                }
                LogUtil.d("lzx---------》", agencyData.toString());
            }
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(LawReportActivity.this,error));
            }
        });
    }

    /**
     * 获取机构人员
     * @param AgencyMid
     */
    private void  getAgencyPersonInfo(String AgencyMid){
        HttpRequest.getAgencyPersonData(LawReportActivity.this, AgencyMid , new CallBackLis<>() {
            @Override
            public void onSuccess(String method, AgencyPersonData agencyPersonData) {
                hideLoading();
                if (agencyPersonData.status==0){
                    if (agencyPersonData.result.size()>0){
                        setJointlyUnitPersonUi(agencyPersonData.result);
                    }else {
                        Objects.requireNonNull(RxToast.warning(LawReportActivity.this,"当前协助执法单位下暂无人员"));
                    }

                }
                LogUtil.d("lzx---------》", agencyPersonData.toString());
            }
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(LawReportActivity.this,error));
            }
        });
    }



   /**
 * 种子领悟点击单选监听
 */
   public void  onRadioButtonClickedSeed(View view){
       seedField = LayoutSeedRBUtils.setRBSeed(mSeedField,view, binding.layoutSeed.infringePlantOk,
               binding.layoutSeed.infringePlantNo, binding.layoutSeed.fakePlantOk,
               binding.layoutSeed.fakePlantNo, binding.layoutSeed.registerRbOk,
               binding.layoutSeed.registerRbNo, binding.layoutSeed.baoZhaungOk,
               binding.layoutSeed.baoZhaungNo, binding.layoutSeed.biaoqianOk,
               binding.layoutSeed.biaoqianNo, binding.layoutSeed.biaoqianNeirongOk,
               binding.layoutSeed.biaoqianNeirongNo, binding.layoutSeed.shengchandanganOk,
               binding.layoutSeed.shengchandanganNo, binding.layoutSeed.jingyingdanganOk,
               binding.layoutSeed.jingyingdanganNo, binding.layoutSeed.beianOk,
               binding.layoutSeed.beianNo, binding.layoutSeed.chouyangOk,
               binding.layoutSeed.chouyangNo);
    LogUtil.d(TAG,"种子领域"+ seedField.toString());
}

    /**
     * 兽医One领悟点击单选监听
     */
    public void  onRadioButtonClickedSYOne(View view){
        veterinaryDrugFieldBean = LayoutSYOneRBUtils.setRBSeed(mVeterinaryDrugField,view, binding.layoutSyOne.GMPZhengShuOk,
                  binding.layoutSyOne.GMPZhengShuNo, binding.layoutSyOne.youXiaoqiOk,
                  binding.layoutSyOne.youXiaoqiNo, binding.layoutSyOne.yuanLiaoFuLiaoFuHeOk,
                  binding.layoutSyOne.yuanLiaoFuLiaoFuHeNo, binding.layoutSyOne.StorageAndSafekeepingOK,
                  binding.layoutSyOne.StorageAndSafekeepingNO, binding.layoutSyOne.wenJianJiZaiOK,
                  binding.layoutSyOne.wenJianJiZaiNo, binding.layoutSyOne.caoZuoRenQianMingOk,
                  binding.layoutSyOne.caoZuoRenQianMingNo, binding.layoutSyOne.piHaoGuiDangOk,
                  binding.layoutSyOne.piHaoGuiDangNo, binding.layoutSyOne.zhiLiangJianYanOk,
                  binding.layoutSyOne.zhiLiangJianYanNo, binding.layoutSyOne.biaoQianshuomingOk,
                  binding.layoutSyOne.biaoQianshuomingNo, binding.layoutSyOne.biaozhuerweimaOk,
                  binding.layoutSyOne.biaozhuerweimaNo,
                  binding.layoutSyOne.guifanyinzhiOk,
                  binding.layoutSyOne.guifanyinzhiNo,
                  binding.layoutSyOne.piqianfaOk,
                 binding.layoutSyOne.piqianfaNo, binding.layoutSyOne.zhaohuizhiduOk,
                 binding.layoutSyOne.zhaohuizhiduNo,binding.layoutSyOne.anquanshengchanOk,
                 binding.layoutSyOne.anquanshengchanNo,binding.layoutSyOne.GMPhouxujianguanOk,
                 binding.layoutSyOne.GMPhouxujianguanNo
                );
        LogUtil.d(TAG,"兽医领域One领域"+ veterinaryDrugFieldBean.toString());
    }
    /**
     * 兽医Two领悟点击单选监听
     */
    public void  onRadioButtonClickedSYTwo(View view){
        veterinaryDrugFieldBeanTwo = LayoutSYTwoRBUtils.setRBSeed(mVeterinaryDrugFieldTwo,view, binding.layoutSyTwo.shouyaoxukezhengzizhiOk,
                binding.layoutSyTwo.shouyaoxukezhengzizhiNo, binding.layoutSyTwo.shifouyouxiaoqiOk,
                binding.layoutSyTwo.shifouyouxiaoqiNo, binding.layoutSyTwo.jingyingchangsuoxiangshiyingOk,
                binding.layoutSyTwo.jingyingchangsuoxiangshiyingNo, binding.layoutSyTwo.shebeiqiquanOk,
                binding.layoutSyTwo.shebeiqiquanNo, binding.layoutSyTwo.guizhangzhiduOk,
                binding.layoutSyTwo.guizhangzhiduNo, binding.layoutSyTwo.caigourukuOk,
                binding.layoutSyTwo.caigourukuNo, binding.layoutSyTwo.chenliechuxuOneOk,
                binding.layoutSyTwo.chenliechuxuOneNo, binding.layoutSyTwo.chenliechuxuTwoOk,
                binding.layoutSyTwo.chenliechuxuTwoNo, binding.layoutSyTwo.chenliechuxuThreeOk,
                binding.layoutSyTwo.chenliechuxuThreeNo, binding.layoutSyTwo.chenliechuxuFourOk,
                binding.layoutSyTwo.chenliechuxuFourNo,
                binding.layoutSyTwo.shouyaoxiaoshouOneOk,
                binding.layoutSyTwo.shouyaoxiaoshouOneNo,
                binding.layoutSyTwo.shouyaoxiaoshouTwoOk,
                binding.layoutSyTwo.shouyaoxiaoshouTwoNo, binding.layoutSyTwo.shouyaoshengwuzhipinOneOk,
                binding.layoutSyTwo.shouyaoshengwuzhipinOneNo,binding.layoutSyTwo.shouyaoshengwuzhipinOneUninvolved,
                binding.layoutSyTwo.shouyaoshengwuzhipinTwoOk,binding.layoutSyTwo.shouyaoshengwuzhipinTwoNo,
                binding.layoutSyTwo.shouyaoshengwuzhipinTwoUninvolved,
                binding.layoutSyTwo.shouyaoshengwuzhipinThreeOk,binding.layoutSyTwo.shouyaoshengwuzhipinThreeNo,
                binding.layoutSyTwo.shouyaoshengwuzhipinThreeUninvolved,binding.layoutSyTwo.GMPhouxujianguanTwoOk,
                binding.layoutSyTwo.GMPhouxujianguanTwoNo
        );
        LogUtil.d(TAG,"兽医Two领悟点击单选监听"+ veterinaryDrugFieldBeanTwo.toString());
    }


    public void  onRadioButtonClickedNY(View view){
        pesticideFieldBean = LayoutNYRBUtils.setRBSeed(mPesticideFieldBean,view, binding.layoutNy.productionAndManagementOneOk,
                binding.layoutNy.productionAndManagementOneNo, binding.layoutNy.productionAndManagementTwoOk,
                binding.layoutNy.productionAndManagementTwoNo
                , binding.layoutNy.productionAndManagementThreeOk,
                binding.layoutNy.productionAndManagementThreeNo,
                binding.layoutNy.productionAndManagementFourOk,
                binding.layoutNy.productionAndManagementFourNo, binding.layoutNy.chanPinBiaoQianOneOk,
                binding.layoutNy.chanPinBiaoQianOneNo, binding.layoutNy.chanPinBiaoQianTwoOk,
                binding.layoutNy.chanPinBiaoQianTwoNo, binding.layoutNy.chanPinBiaoQianThreeOk,
                binding.layoutNy.chanPinBiaoQianThreeNo, binding.layoutNy.jinXianNongYaoOneOk,
                binding.layoutNy.jinXianNongYaoOneNo, binding.layoutNy.jinXianNongYaoTwoOk,
                binding.layoutNy.jinXianNongYaoTwoNo, binding.layoutNy.taiZhangOk,
                binding.layoutNy.taiZhangNo,
                binding.layoutNy.wangShangXiaoShouOk,
                binding.layoutNy.wangShangXiaoShouNo,
                binding.layoutNy.chanPinZhiLiangOneOk,
                binding.layoutNy.chanPinZhiLiangOneNo, binding.layoutNy.chanPinZhiLiangTwoOk,
                binding.layoutNy.chanPinZhiLiangTwoNo
        );
        LogUtil.d(TAG,"农药领悟点击单选监听"+ pesticideFieldBean.toString());
    }
    /**
     * 提交数据
     */
  private void SubmitData(){
       enforcementData.CompanyName = binding.nameUnitEt.getText().toString();//单位名称（个人姓名）
       enforcementData.LegalRepresentative = binding.legalRepresentativeEt.getText().toString();//法定代表人（负责人）
       enforcementData.Tel = binding.telEt.getText().toString();//联系电话
       enforcementData.DetailedAddress = binding.detailsAddressEt.getText().toString();//详细地址
       enforcementData.OtherProblems = binding.otherProEt.getText().toString();//发现其他问题
       enforcementData.DeadlineForRectification = binding.timesEt.getText().toString();//限期整改截止日期
       enforcementData. CheckType = 1;//1为填报 2抽查
      enforcementData.SeedField = new SeedFieldBean();
      enforcementData.VeterinaryDrugField =new VeterinaryDrugFieldBean();
      enforcementData.PesticideField = new PesticideFieldBean();
      enforcementData.AgriculturalMachineryField = new AgriculturalMachineryFieldBean();
      enforcementData.FertilizerField = new FertilizerFieldBean();
      enforcementData.AgriculturalProductSafetyField = new AgriculturalProductSafetyFieldBean();
      enforcementData.FeedField = new FeedFieldBean();
      enforcementData.HogSlaughteringArea= new HogSlaughteringAreaBean();
      enforcementData.FieldPlantQuarantine=  new FieldPlantQuarantineBean();
      enforcementData.LivestockPoultryFields=  new LivestockPoultryFieldsBean();
      enforcementData.FisheryArea = new  FisheryAreaBean();
      if ("4".equals(enforcementData.InspectionResult.Id)){
          enforcementData.ReviewStatus = 0;//复查状态
      }

      if (territoryID==10 && CheckListCode==101 ){

          enforcementData.SeedField = seedField;//种子领域
          enforcementData.SeedField.creditCode = binding.layoutSeed.creditCodeEt.getText().toString();
          enforcementData.SeedField.businessCode = binding.layoutSeed.businessCodeEt.getText().toString();

      }else if (territoryID==20 && CheckListCode==201){//兽医领域1

          enforcementData.VeterinaryDrugField = veterinaryDrugFieldBean;
          enforcementData.VeterinaryDrugField.mBusinessLicenseNumber = binding.layoutSyOne.businessLicenseNumberEt.getText().toString();

      }else if (territoryID==20 && CheckListCode==202){//兽医领域2
          enforcementData.VeterinaryDrugField = veterinaryDrugFieldBeanTwo;

      }else if (territoryID==30 && CheckListCode==301){//农药领悟
          enforcementData.PesticideField = pesticideFieldBean;
          enforcementData.PesticideField.businessLicenseNumber = binding.layoutNy.businessLicenseNumber.getText().toString();//营业执照号码
          enforcementData.PesticideField.pesticideOperationLicenseNumber = binding.layoutNy.pesticideOperationLicenseNumber.getText().toString();//农药经营许可证编号
      }else if (territoryID==40 && CheckListCode==401){//农机领域1
          enforcementData.AgriculturalMachineryField = agriculturalMachineryFieldBean;
      }else if (territoryID==40 && CheckListCode==402){//农机领域1
          enforcementData.AgriculturalMachineryField = agriculturalMachineryFieldBean;
      }else if (territoryID==40 && CheckListCode==403){//农机领域1
          enforcementData.AgriculturalMachineryField = agriculturalMachineryFieldBean;
      }else if (territoryID==40 && CheckListCode==404){//农机领域1
          enforcementData.AgriculturalMachineryField = agriculturalMachineryFieldBean;
      }else if (territoryID==50 && CheckListCode==501){//肥料
          enforcementData.FertilizerField = fertilizerFieldBean;
          enforcementData.FertilizerField.businesslicensenumber = binding.layoutFl.businesslicensenumber.getText().toString();
      }else if (territoryID==60 && CheckListCode==601){//农产品质量安全领域
          enforcementData.AgriculturalProductSafetyField = agriculturalProductSafetyFieldBean;
          enforcementData.AgriculturalProductSafetyField.unifiedCode = binding.layoutNcpaq.unifiedCodeEt.getText().toString();
          enforcementData.AgriculturalProductSafetyField.otherQualifications = binding.layoutNcpaq.otherQualificationsEt.getText().toString();
          enforcementData.AgriculturalProductSafetyField.dominantVariety = binding.layoutNcpaq.dominantVarietyEt.getText().toString();
      }else if (territoryID==70 && CheckListCode==701){//饲料领域1
          enforcementData.FeedField = mFeedFieldBean;
      }else if (territoryID==70 && CheckListCode==702){//饲料领域2
          enforcementData.FeedField = mFeedFieldBean;
      }else if (territoryID==80 && CheckListCode==801){//生猪领域 1
          enforcementData.HogSlaughteringArea= mHogSlaughteringAreaBean;
          enforcementData.HogSlaughteringArea.hatchPlayEggs =   binding.layoutPigOne.hatchPlayEggsEt.getText().toString();
          enforcementData.HogSlaughteringArea.hatchEggs =  binding.layoutPigOne.hatchEggsEt.getText().toString();
          enforcementData.HogSlaughteringArea.hatchBroodHatched =binding.layoutPigOne.hatchBroodHatchedEt.getText().toString();
          enforcementData.HogSlaughteringArea.managementSystem = binding.layoutPigOne.managementSystemEt.getText().toString();
          enforcementData.HogSlaughteringArea.lastJanuaryOutNum = binding.layoutPigOne.lastJanuaryOutNumEt.getText().toString();
          enforcementData.HogSlaughteringArea.quarantineReport = binding.layoutPigOne.quarantineReportEt.getText().toString();
      }else if (territoryID==80 && CheckListCode==802){//生猪领域 2
          enforcementData.HogSlaughteringArea= mHogSlaughteringAreaBean;

      }else if (territoryID==80 && CheckListCode==803){//生猪领域 3
          enforcementData.HogSlaughteringArea= mHogSlaughteringAreaBean;
          enforcementData.HogSlaughteringArea.recordNo = binding.layoutPigThree.recordNoEt.getText().toString();
          enforcementData.HogSlaughteringArea.carNum = binding.layoutPigThree.carNumEt.getText().toString();
          enforcementData.HogSlaughteringArea.traffickingAnimal = binding.layoutPigThree.traffickingAnimalEt.getText().toString();
          enforcementData.HogSlaughteringArea.traffickedNum = binding.layoutPigThree.traffickedNumEt.getText().toString();
          enforcementData.HogSlaughteringArea.badgesNotWorn = binding.layoutPigThree.badgesNotWornEt.getText().toString();

      }else if (territoryID==80 && CheckListCode==804){//生猪领域 4
          enforcementData.HogSlaughteringArea= mHogSlaughteringAreaBean;
          enforcementData.HogSlaughteringArea.animalMedicalPermit =   binding.layoutPigFour.animalMedicalPermitEt.getText().toString();
          enforcementData.HogSlaughteringArea.licenceIssued =   binding.layoutPigFour.licenceIssuedEt.getText().toString();
          enforcementData.HogSlaughteringArea.medicalInstitutionsType =     binding.layoutPigFour.medicalInstitutionsTypeEt.getText().toString();
          enforcementData.HogSlaughteringArea.employeesNum =   binding.layoutPigFour.employeesNumEt.getText().toString();
          enforcementData.HogSlaughteringArea.licensedVeterinarianNum =    binding.layoutPigFour.licensedVeterinarianNumEt.getText().toString();
          enforcementData.HogSlaughteringArea.practicingAssistantVeterinarianNum =   binding.layoutPigFour.practicingAssistantVeterinarianNumEt.getText().toString();
          enforcementData.HogSlaughteringArea.practiceProject =   binding.layoutPigFour.practiceProjectEt.getText().toString();
          enforcementData.HogSlaughteringArea.useRegistration =   binding.layoutPigFour.useRegistrationEt.getText().toString();
      }else  if (territoryID==80 && CheckListCode ==805){//生猪领域 5
          enforcementData.HogSlaughteringArea= mHogSlaughteringAreaBean;
          enforcementData.HogSlaughteringArea.countrysideAnimalMedicalPermit =  binding.layoutPigFive.countrysideAnimalMedicalPermitEt.getText().toString();
          enforcementData.HogSlaughteringArea.countrysideDateIssue = binding.layoutPigFive.countrysideDateIssueEt.getText().toString();
          enforcementData.HogSlaughteringArea.countrysideEmployeesTotalNum = binding.layoutPigFive.countrysideEmployeesTotalNumEt.getText().toString();
          enforcementData.HogSlaughteringArea.countrysideLicensedVeterinarian = binding.layoutPigFive.countrysideLicensedVeterinarianEt.getText().toString();
          enforcementData.HogSlaughteringArea.countrysidePracticingAssistantVeterinarian = binding.layoutPigFive.countrysidePracticingAssistantVeterinarianEt.getText().toString();
          enforcementData.HogSlaughteringArea.countrysideRuralVeterinarian = binding.layoutPigFive.countrysideRuralVeterinarianEt.getText().toString();
          enforcementData.HogSlaughteringArea.countrysideVeterinarianName = binding.layoutPigFive.countrysideVeterinarianNameEt.getText().toString();
          enforcementData.HogSlaughteringArea.countrysideEmploymentCertificateNumber =  binding.layoutPigFive.countrysideEmploymentCertificateNumberEt.getText().toString();
      }else  if (territoryID==80 && CheckListCode ==806){//生猪领域 6
          enforcementData.HogSlaughteringArea= mHogSlaughteringAreaBean;

      }else  if (territoryID==90 && CheckListCode ==901){//植物检疫领域
          enforcementData.FieldPlantQuarantine= mFieldPlantQuarantineBean;
      }else  if (territoryID==100 && CheckListCode ==1001){//	种畜禽领域
          enforcementData.LivestockPoultryFields= mLivestockPoultryFieldsBean;
      }else  if (territoryID==100 && CheckListCode ==1002){//	种畜禽领域2
          enforcementData.LivestockPoultryFields= mLivestockPoultryFieldsBean;
      }else if (territoryID==110 && CheckListCode ==1101){ //渔业水产领域 1
          enforcementData.FisheryArea =   mFisheryAreaBean;
      }else if (territoryID==110 && CheckListCode ==1102){ //渔业水产领域 2
          enforcementData.FisheryArea =   mFisheryAreaBean;
      }else if (territoryID==110 && CheckListCode ==1103){ //渔业水产领域 3
          enforcementData.FisheryArea =   mFisheryAreaBean;
      }
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
      HttpRequest.addDataEnforcementData(LawReportActivity.this, enforcementData , new CallBackLis<>() {
          @Override
          public void onSuccess(String method, StatusData status) {
                hideLoading();
              if (status.ErrorCode==0){
                 Objects.requireNonNull(RxToast.success(LawReportActivity.this,"提交成功")) ;
                  finish();
              }
          }
          @Override
          public void onFailure(String method, String error) {
              Objects.requireNonNull(RxToast.error(LawReportActivity.this,error));
          }
      });
  }
    private boolean checkInfo() {
        if (TextUtils.isEmpty(enforcementData.CoOrganizeEnforcementUnits.Mid)) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请选择协办执法单位"));
            return false;
        }
        if (TextUtils.isEmpty(enforcementData.CoOrganizer.Mid)) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请选择协办人员"));
            return false;
        }
        if (TextUtils.isEmpty(enforcementData.InspectionField.Id)) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请选择检查领域"));
            return false;
        }
        if (territoryID!=111 ||territoryID!=112 ){
            if (TextUtils.isEmpty(enforcementData.CheckList.Id)) {
                Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请选择检查表"));
                return false;
            }
        }

        if (TextUtils.isEmpty(enforcementData.FrontViewOfTheInspectedUnit)) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请上传被检查单位正面照片"));
            return false;
        }

        if (TextUtils.isEmpty(binding.nameUnitEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请输入单位名称（个人姓名）"));
            return false;
        }

        if (TextUtils.isEmpty(binding.legalRepresentativeEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请输入法定代表人"));
            return false;
        }
        if (TextUtils.isEmpty(binding.telEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请输入联系电话"));
            return false;
        }
        if (TextUtils.isEmpty(enforcementData.Region.RegionName)) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请选择区划"));
            return false;
        }
        if (TextUtils.isEmpty(enforcementData.DetailedAddress)) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请输入详细地址"));
            return false;
        }
        if (TextUtils.isEmpty(enforcementData.EyewitnessSignature)) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请上传见证人签名"));
            return false;
        }
        if (TextUtils.isEmpty(enforcementData.UnitUnderInspectionSignature)) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请上传被检查单位负责人签名"));
            return false;
        }
        if (TextUtils.isEmpty(enforcementData.SignatureOfOrganizer)) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请上传主办人员签名"));
            return false;
        }
        if (TextUtils.isEmpty(enforcementData.InspectionResult.Id)) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请选择检查结果"));
            return false;
        }
        if (!TextUtils.isEmpty(enforcementData.InspectionResult.Id) && "4".equals(enforcementData.InspectionResult.Id) &&
                TextUtils.isEmpty(binding.timesEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请选择整改截止日期"));
            return false;
        }

        if (enforcementData.ProblemShooting.imageList.size() == 0 &&  !"未发现问题".contentEquals(binding.checkResultEt.getText())) {
            Objects.requireNonNull(RxToast.warning(LawReportActivity.this, "请上传发现问题照片"));
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
        HttpRequest.upLoadImg(LawReportActivity.this, filePath, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d(TAG, "上传图片" + content.toString());
                if (content.Status == 0) {
                    Objects.requireNonNull(RxToast.success(LawReportActivity.this, "上传成功"));
                    if (PicBtnType == 1) {
                      enforcementData.FrontViewOfTheInspectedUnit  = content.Result;//被检查单位正面照片
                    } else if (PicBtnType == 2) {
                        enforcementData.SignatureOfOrganizer  = content.Result;//主办人员签名
                        Glide.with(LawReportActivity.this)
                                .load(UrlUtils.pic_url + content.Result)
                                .centerCrop()
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(binding.zbryIv);
                    } else if (PicBtnType == 3) {
                        enforcementData.UnitUnderInspectionSignature  = content.Result;//被检查单位负责人签名
                        Glide.with(LawReportActivity.this)
                                .load(UrlUtils.pic_url + content.Result)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .into(binding.bjcfzrIv);
                    } else if (PicBtnType == 4) {
                        enforcementData.EyewitnessSignature  = content.Result;//见证人签名
                        Glide.with(LawReportActivity.this)
                                .load(UrlUtils.pic_url + content.Result)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .into(binding.jzrIv);
                    }
                    hideLoading();
                } else {
                    Objects.requireNonNull(RxToast.error(LawReportActivity.this, content.Result));
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
        HttpRequest.upLoadBatchImg(LawReportActivity.this, filePath, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, BatchImgBean content) {
                LogUtil.d(TAG, "上传图片批量" + content.toString());
                if (content.status == 0) {
                    Objects.requireNonNull(RxToast.success(LawReportActivity.this, "上传成功"));
                    hideLoading();

                    for (String path : content.result) {
                        enforcementData.ProblemShooting.imageList.add(path);
                    }
                    LogUtil.d(TAG, "上传图片批量" + enforcementData.ProblemShooting.imageList.toString());
                } else {
                    Objects.requireNonNull(RxToast.error(LawReportActivity.this, "上传失败~"));
                }

            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }


    private   void initTimePicker2(Context context) {//选择出生年月日
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        SimpleDateFormat formatter_year = new SimpleDateFormat("yyyy ");
        String year_str = formatter_year.format(curDate);
        int year_int = (int) Double.parseDouble(year_str);
        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM ");
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);
        SimpleDateFormat formatter_day = new SimpleDateFormat("dd ");
        String day_str = formatter_day.format(curDate);
        int day_int = (int) Double.parseDouble(day_str);
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Date time = selectedDate.getTime();
        selectedDate.setTime(TimeDialogUtils.getTimeMonth());//设置选中的默认时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2010, mouth_int - 1, day_int);
        LogUtil.d("lzx---》" , mouth_int-1 +"");
        Calendar endDate = Calendar.getInstance();
        endDate.set(2030, mouth_int - 1, day_int);
        pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                LogUtil.d("lzx---》","当前选中的时间" + date.getDate());
                LogUtil.d("lzx---》","当前选中的时间" + TimeDialogUtils.getTime(date));
                binding.timesEt.setText(TimeDialogUtils.getTime(date));
            }
        })       .setTitleSize(20)
                .setTitleText("开始时间")//标题
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.submit_tv);
                        TextView ivCancel = (TextView) v.findViewById(R.id.cancel_tv);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .setTextXOffset(-40, -40, -40, -40, -40, -40)
                .isCenterLabel(false)
                .setLineSpacingMultiplier(2.f)
                .setTitleColor(R.color.black)
                .setDividerColor(context.getResources().getColor(R.color.D1))
                .setDate(selectedDate)
                .isDialog(false)
                .isCyclic(false)
                .setRangDate(startDate, endDate)
                .build();

    }



    private void  setSeedCheckFalse(){
        binding.layoutSeed.infringePlantRg.clearCheck();
        binding.layoutSeed.fakePlantRg.clearCheck();
        binding.layoutSeed.registerRg.clearCheck();
        binding.layoutSeed.baozhuangRg.clearCheck();
        binding.layoutSeed.biaoqianRg.clearCheck();
        binding.layoutSeed.biaoqianRg.clearCheck();
        binding.layoutSeed.biaozhuRg.clearCheck();
        binding.layoutSeed.shengchandanganRg.clearCheck();
        binding.layoutSeed.jingyingdanganRg.clearCheck();
        binding.layoutSeed.beianRg.clearCheck();
        binding.layoutSeed.chouyangRg.clearCheck();
    }
    private void  setSyOneCheckFalse(){
        binding.layoutSyOne.syRg1.clearCheck();
        binding.layoutSyOne.syRg2.clearCheck();
        binding.layoutSyOne.syRg3.clearCheck();
        binding.layoutSyOne.syRg4.clearCheck();
        binding.layoutSyOne.syRg5.clearCheck();
        binding.layoutSyOne.syRg6.clearCheck();
        binding.layoutSyOne.syRg7.clearCheck();
        binding.layoutSyOne.syRg8.clearCheck();
        binding.layoutSyOne.syRg9.clearCheck();
        binding.layoutSyOne.syRg10.clearCheck();
        binding.layoutSyOne.syRg11.clearCheck();
        binding.layoutSyOne.syRg12.clearCheck();
        binding.layoutSyOne.syRg13.clearCheck();
        binding.layoutSyOne.syRg14.clearCheck();
        binding.layoutSyOne.syRg15.clearCheck();
    }
    private void  setSyTwoCheckFalse(){
        binding.layoutSyTwo.syRg1.clearCheck();
        binding.layoutSyTwo.syRg2.clearCheck();
        binding.layoutSyTwo.syRg3.clearCheck();
        binding.layoutSyTwo.syRg4.clearCheck();
        binding.layoutSyTwo.syRg5.clearCheck();
        binding.layoutSyTwo.syRg6.clearCheck();
        binding.layoutSyTwo.syRg7.clearCheck();
        binding.layoutSyTwo.syRg8.clearCheck();
        binding.layoutSyTwo.syRg9.clearCheck();
        binding.layoutSyTwo.syRg10.clearCheck();
        binding.layoutSyTwo.syRg11.clearCheck();
        binding.layoutSyTwo.syRg12.clearCheck();
        binding.layoutSyTwo.syRg13.clearCheck();
        binding.layoutSyTwo.syRg14.clearCheck();
        binding.layoutSyTwo.syRg15.clearCheck();
        binding.layoutSyTwo.syRg16.clearCheck();
    }
    private void  setNYCheckFalse(){
        binding.layoutNy.nyrg1.clearCheck();
        binding.layoutNy.nyrg2.clearCheck();
        binding.layoutNy.nyrg3.clearCheck();
        binding.layoutNy.nyrg4.clearCheck();
        binding.layoutNy.nyrg5.clearCheck();
        binding.layoutNy.nyrg6.clearCheck();
        binding.layoutNy.nyrg7.clearCheck();
        binding.layoutNy.nyrg8.clearCheck();
        binding.layoutNy.nyrg9.clearCheck();
        binding.layoutNy.nyrg10.clearCheck();
        binding.layoutNy.nyrg11.clearCheck();
        binding.layoutNy.nyrg12.clearCheck();
        binding.layoutNy.nyrg13.clearCheck();

    }
    /**
     * 农机领域one
     * @param view
     */
    public void onRadioButtonClickedNJOne(View view){
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutNjOne.zhiziOk) {
            if (isChecked) {
                agriculturalMachineryFieldBean.zhizi = 1;
            }
        }else if (view == binding.layoutNjOne.zhiziNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.zhizi = 0;
            }
        }else if (view == binding.layoutNjOne.dengjizhucengOneOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.dengjizhucengOne = 1;
            }
        }else if (view == binding.layoutNjOne.dengjizhucengOneNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.dengjizhucengOne = 0;
            }
        }else if (view == binding.layoutNjOne.dengjizhucengTwoOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.dengjizhucengTwo = 1;
            }
        }else if (view == binding.layoutNjOne.dengjizhucengTwoNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.dengjizhucengTwo = 0;
            }
        }else if (view == binding.layoutNjOne.dengjizhucengThreeOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.dengjizhucengThree = 1;
            }
        }else if (view == binding.layoutNjOne.dengjizhucengThreeNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.dengjizhucengThree = 0;
            }
        }
        else if (view == binding.layoutNjOne.danganziliaoOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.danganziliao = 1;
            }
        }else if (view == binding.layoutNjOne.danganziliaoOneNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.danganziliao = 0;
            }
        }
        else if (view == binding.layoutNjOne.danganziliaoTwoOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.danganziliaoTwo = 1;
            }
        }else if (view == binding.layoutNjOne.danganziliaoTwoNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.danganziliaoTwo = 0;
            }
        }else if (view == binding.layoutNjOne.niandujianyanOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.niandujianyan = 1;
            }
        }else if (view == binding.layoutNjOne.niandujianyanNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.niandujianyan = 0;
            }
        }
        LogUtil.d(TAG,"农机领域1" + agriculturalMachineryFieldBean.toString());
    }
    private void  setNJOneCheckFalse(){
        binding.layoutNjOne.njrg1.clearCheck();
        binding.layoutNjOne.njrg2.clearCheck();
        binding.layoutNjOne.njrg3.clearCheck();
        binding.layoutNjOne.njrg4.clearCheck();
        binding.layoutNjOne.njrg5.clearCheck();
        binding.layoutNjOne.njrg6.clearCheck();
        binding.layoutNjOne.njrg7.clearCheck();

    }
    /**
     * 农机领域two
     * @param view
     */
    public void onRadioButtonClickedNJTwo(View view){
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutNjTwo.idcardOk) {
            if (isChecked) {
                agriculturalMachineryFieldBean.idcard = 1;
            }
        }else if (view == binding.layoutNjTwo.idcardNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.idcard = 0;
            }
        }else if (view == binding.layoutNjTwo.dengjiPersonOneOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.dengjiPersonOne = 1;
            }
        }else if (view == binding.layoutNjTwo.dengjiPersonOneNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.dengjiPersonOne = 0;
            }
        }else if (view == binding.layoutNjTwo.dengjiPersonTwoOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.dengjiPersonTwo = 1;
            }
        }else if (view == binding.layoutNjTwo.dengjiPersonTwoNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.dengjiPersonTwo = 0;
            }
        }else if (view == binding.layoutNjTwo.dengjiPersonThreeOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.dengjiPersonThree = 1;
            }
        }else if (view == binding.layoutNjTwo.dengjiPersonThreeNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.dengjiPersonThree = 0;
            }
        }
        else if (view == binding.layoutNjTwo.caozuoPersonOneOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.caozuoPersonOne = 1;
            }
        }else if (view == binding.layoutNjTwo.caozuoPersonOneNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.caozuoPersonOne = 0;
            }
        }
        else if (view == binding.layoutNjTwo.caozuoPersonTwoOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.caozuoPersonTwo = 1;
            }
        }else if (view == binding.layoutNjTwo.caozuoPersonTwoNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.caozuoPersonTwo = 0;
            }
        }else if (view == binding.layoutNjTwo.caozuoPersonThreeOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.caozuoPersonThree = 1;
            }
        }else if (view == binding.layoutNjTwo.caozuoPersonThreeNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.caozuoPersonThree = 0;
            }
        }

        else if (view == binding.layoutNjTwo.caozuoPersonFourOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.caozuoPersonFour = 1;
            }
        }else if (view == binding.layoutNjTwo.caozuoPersonFourNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.caozuoPersonFour = 0;
            }
        }
        else if (view == binding.layoutNjTwo.niandujianyanPersonOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.niandujianyanPerson = 1;
            }
        }else if (view == binding.layoutNjTwo.niandujianyanPersonNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.niandujianyanPerson = 0;
            }
        }
        LogUtil.d(TAG,"农机领域2" + agriculturalMachineryFieldBean.toString());
    }
    private void   setNJTwoCheckFalse(){
        binding.layoutNjTwo.njtworg1.clearCheck();
        binding.layoutNjTwo.njtworg2.clearCheck();
        binding.layoutNjTwo.njtworg3.clearCheck();
        binding.layoutNjTwo.njtworg4.clearCheck();
        binding.layoutNjTwo.njtworg5.clearCheck();
        binding.layoutNjTwo.njtworg6.clearCheck();
        binding.layoutNjTwo.njtworg7.clearCheck();
        binding.layoutNjTwo.njtworg8.clearCheck();
        binding.layoutNjTwo.njtworg9.clearCheck();
    }
    /**
     * 农机领域three
     * @param view
     */
    public void onRadioButtonClickedNJThree(View view){
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutNjThree.weixiudianzhiziOk) {
            if (isChecked) {
                agriculturalMachineryFieldBean.weixiudianzhizi = 1;
            }
        }else if (view == binding.layoutNjThree.weixiudianzhiziNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.weixiudianzhizi = 0;
            }
        }else if (view == binding.layoutNjThree.weixiuqingkuangOneOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.weixiuqingkuangOne = 1;
            }
        }else if (view == binding.layoutNjThree.weixiuqingkuangOneNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.weixiuqingkuangOne = 0;
            }
        }else if (view == binding.layoutNjThree.weixiuqingkuangTwoOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.weixiuqingkuangTwo = 1;
            }
        }else if (view == binding.layoutNjThree.weixiuqingkuangTwoNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.weixiuqingkuangTwo = 0;
            }
        }else if (view == binding.layoutNjThree.weixiuqingkuangThreeOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.weixiuqingkuangThree = 1;
            }
        }else if (view == binding.layoutNjThree.weixiuqingkuangThreeNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.weixiuqingkuangThree = 0;
            }
        }

        LogUtil.d(TAG,"农机领域3" + agriculturalMachineryFieldBean.toString());
    }
    private void   setNJThreeCheckFalse(){
        binding.layoutNjThree.njthreerg1.clearCheck();
        binding.layoutNjThree.njthreerg2.clearCheck();
        binding.layoutNjThree.njthreerg3.clearCheck();
        binding.layoutNjThree.njthreerg4.clearCheck();

    }
    /**
     * 农机领域four
     * @param view
     */
    public void onRadioButtonClickedNJFour(View view){
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutNjFour.jianshizhizhiOk) {
            if (isChecked) {
                agriculturalMachineryFieldBean.jianshizhizhi = 1;
            }
        }else if (view == binding.layoutNjFour.jianshizhizhiNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.jianshizhizhi = 0;
            }
        }else if (view == binding.layoutNjFour.jianshiPeiXunYeWuOneOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.jianshiPeiXunYeWuOne = 1;
            }
        }else if (view == binding.layoutNjFour.jianshiPeiXunYeWuOneNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.jianshiPeiXunYeWuOne = 0;
            }
        }else if (view == binding.layoutNjFour.jianshiPeiXunYeWuTwoOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.jianshiPeiXunYeWuTwo = 1;
            }
        }else if (view == binding.layoutNjFour.jianshiPeiXunYeWuTwoNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.jianshiPeiXunYeWuTwo = 0;
            }
        }else if (view == binding.layoutNjFour.jianshiPeiXunYeWuThreeOk){
            if (isChecked) {
                agriculturalMachineryFieldBean.jianshiPeiXunYeWuThree = 1;
            }
        }else if (view == binding.layoutNjFour.jianshiPeiXunYeWuThreeNo){
            if (isChecked) {
                agriculturalMachineryFieldBean.jianshiPeiXunYeWuThree = 0;
            }
        }

        LogUtil.d(TAG,"农机领域4" + agriculturalMachineryFieldBean.toString());
    }
    private void setNJFourCheckFalse(){
        binding.layoutNjFour.njfourrg1.clearCheck();
        binding.layoutNjFour.njfourrg2.clearCheck();
        binding.layoutNjFour.njfourrg3.clearCheck();
        binding.layoutNjFour.njfourrg4.clearCheck();
    }
    /**
     * 肥料领域
     * @param view
     */
    public void onRadioButtonClickedFL(View view){
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutFl.feiliaodengjiOneOk) {
            if (isChecked) {
                fertilizerFieldBean.feiliaodengjiOne = 1;
            }
        }else if (view == binding.layoutFl.feiliaodengjiOneNo){
            if (isChecked) {
                fertilizerFieldBean.feiliaodengjiOne = 0;
            }
        }else if (view == binding.layoutFl.feiliaodengjiTwoOk){
            if (isChecked) {
                fertilizerFieldBean.feiliaodengjiTwo = 1;
            }
        }else if (view == binding.layoutFl.feiliaodengjiTwoNo){
            if (isChecked) {
                fertilizerFieldBean.feiliaodengjiTwo = 0;
            }
        }else if (view == binding.layoutFl.feiliaodengjiThreeOk){
            if (isChecked) {
                fertilizerFieldBean.feiliaodengjiThree = 1;
            }
        }else if (view == binding.layoutFl.feiliaodengjiThreeNo){
            if (isChecked) {
                fertilizerFieldBean.feiliaodengjiThree = 0;
            }
        }else if (view == binding.layoutFl.chanpinbiaoqianOneOk){
            if (isChecked) {
                fertilizerFieldBean.chanpinbiaoqianOne = 1;
            }
        }else if (view == binding.layoutFl.chanpinbiaoqianOneNo){
            if (isChecked) {
                fertilizerFieldBean.chanpinbiaoqianOne = 0;
            }
        }else if (view == binding.layoutFl.chanpinbiaoqianTwoOk){
            if (isChecked) {
                fertilizerFieldBean.chanpinbiaoqianTwo = 1;
            }
        }else if (view == binding.layoutFl.chanpinbiaoqianTwoNo){
            if (isChecked) {
                fertilizerFieldBean.chanpinbiaoqianTwo = 0;
            }
        }else if (view == binding.layoutFl.chanpinbiaoqianThreeOk){
            if (isChecked) {
                fertilizerFieldBean.chanpinbiaoqianThree = 1;
            }
        }else if (view == binding.layoutFl.chanpinbiaoqianThreeNo){
            if (isChecked) {
                fertilizerFieldBean.chanpinbiaoqianThree = 0;
            }
        }else if (view == binding.layoutFl.chanpinbiaoqianFourOk){
            if (isChecked) {
                fertilizerFieldBean.chanpinbiaoqianFour = 1;
            }
        }else if (view == binding.layoutFl.chanpinbiaoqianFourNo){
            if (isChecked) {
                fertilizerFieldBean.chanpinbiaoqianFour = 0;
            }
        }
        else if (view == binding.layoutFl.zhiliangOk){
            if (isChecked) {
                fertilizerFieldBean.zhiliang = 1;
            }
        }else if (view == binding.layoutFl.zhiliangNo){
            if (isChecked) {
                fertilizerFieldBean.zhiliang = 0;
            }
        }
        LogUtil.d(TAG,"肥料领域" + fertilizerFieldBean.toString());
    }
    private void setFLCheckFalse(){
        binding.layoutFl.flrg1.clearCheck();
        binding.layoutFl.flrg2.clearCheck();
        binding.layoutFl.flrg3.clearCheck();
        binding.layoutFl.flrg4.clearCheck();
        binding.layoutFl.flrg5.clearCheck();
        binding.layoutFl.flrg6.clearCheck();
        binding.layoutFl.flrg7.clearCheck();
        binding.layoutFl.flrg8.clearCheck();
    }
    /**
     * 农产品安全领域
     * @param view
     */
    public void onRadioButtonClickedNCPAQ(View view){
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutNcpaq.shengchanjiluOneOk) {
            if (isChecked) {
                agriculturalProductSafetyFieldBean.shengchanjiluOne = 1;
            }
        }else if (view == binding.layoutNcpaq.shengchanjiluOneNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.shengchanjiluOne = 0;
            }
        }else if (view == binding.layoutNcpaq.shengchanjiluTwoOk){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.shengchanjiluTwo = 1;
            }
        }else if (view == binding.layoutNcpaq.shengchanjiluTwoNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.shengchanjiluTwo = 0;
            }
        }else if (view == binding.layoutNcpaq.trpNyOneOk){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpNyOne = 1;
            }
        }else if (view == binding.layoutNcpaq.trpNyOneNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpNyOne = 0;
            }
        }else if (view == binding.layoutNcpaq.trpNyTwoOk){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpNyTwo = 1;
            }
        }else if (view == binding.layoutNcpaq.trpNyTwoNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpNyTwo = 0;
            }
        }else if (view == binding.layoutNcpaq.trpNyThreeOk){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpNyThree = 1;
            }
        }else if (view == binding.layoutNcpaq.trpNyThreeNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpNyThree = 0;
            }
        }else if (view == binding.layoutNcpaq.trpNyFourOk){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpNyFour = 1;
            }
        }else if (view == binding.layoutNcpaq.trpNyFourNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpNyFour = 0;
            }
        }else if (view == binding.layoutNcpaq.trpNyFiveOk){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpNyFive = 1;
            }
        }else if (view == binding.layoutNcpaq.trpNyFiveNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpNyFive = 0;
            }
        }else if (view == binding.layoutNcpaq.trpFlOneOk){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpFlOne = 1;
            }
        }else if (view == binding.layoutNcpaq.trpFlOneNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpFlOne = 0;
            }
        }else if (view == binding.layoutNcpaq.trpFlTwoOk){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpFlTwo = 1;
            }
        }else if (view == binding.layoutNcpaq.trpFlTwoNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.trpFlTwo = 0;
            }
        }else if (view == binding.layoutNcpaq.bzfqwOk){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.bzfqw = 1;
            }
        }else if (view == binding.layoutNcpaq.bzfqwNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.bzfqw = 0;
            }
        }else if (view == binding.layoutNcpaq.dlbzOk){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.dlbz = 1;
            }
        }else if (view == binding.layoutNcpaq.dlbzNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.dlbz = 0;
            }
        }else if (view == binding.layoutNcpaq.lsyjOk){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.lsyj = 1;
            }
        }else if (view == binding.layoutNcpaq.lsyjNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.lsyj = 0;
            }
        }else if (view == binding.layoutNcpaq.anquanOk){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.anquan = 1;
            }
        }else if (view == binding.layoutNcpaq.anquanNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.anquan = 0;
            }
        }
        else if (view == binding.layoutNcpaq.zhiliangOk){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.zhiliang = 1;
            }
        }else if (view == binding.layoutNcpaq.zhiliangNo){
            if (isChecked) {
                agriculturalProductSafetyFieldBean.zhiliang = 0;
            }
        }
        LogUtil.d(TAG,"农产品质量安全领域" + agriculturalProductSafetyFieldBean.toString());
    }
    private void  setNCPCheckFalse(){
        binding.layoutNcpaq.hcprg1.clearCheck();
        binding.layoutNcpaq.hcprg2.clearCheck();
        binding.layoutNcpaq.hcprg3.clearCheck();
        binding.layoutNcpaq.hcprg4.clearCheck();
        binding.layoutNcpaq.hcprg5.clearCheck();
        binding.layoutNcpaq.hcprg6.clearCheck();
        binding.layoutNcpaq.hcprg7.clearCheck();
        binding.layoutNcpaq.hcprg8.clearCheck();
        binding.layoutNcpaq.hcprg9.clearCheck();
        binding.layoutNcpaq.hcprg10.clearCheck();
        binding.layoutNcpaq.hcprg11.clearCheck();
        binding.layoutNcpaq.hcprg12.clearCheck();
        binding.layoutNcpaq.hcprg13.clearCheck();
        binding.layoutNcpaq.hcprg14.clearCheck();

    }
    /**
     * 饲料领域  成都市饲料和饲料添加剂经营单位执法检查记录表
     * @param view
     */
    public void onRadioButtonClickedSLOne(View view){
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutSlOne.jingyingtiaojianOneOk) {
            if (isChecked) {
                mFeedFieldBean.jingyingtiaojianOne = 1;
            }
        }else if (view == binding.layoutSlOne.jingyingtiaojianOneNo){
            if (isChecked) {
                mFeedFieldBean.jingyingtiaojianOne = 0;
            }
        }else if (view == binding.layoutSlOne.jingyingtiaojianTwoOk){
            if (isChecked) {
                mFeedFieldBean.jingyingtiaojianTwo = 1;
            }
        }else if (view == binding.layoutSlOne.jingyingtiaojianTwoNo){
            if (isChecked) {
                mFeedFieldBean.jingyingtiaojianTwo = 0;
            }
        }else if (view == binding.layoutSlOne.jingyingtiaojianThreeOk){
            if (isChecked) {
                mFeedFieldBean.jingyingtiaojianThree = 1;
            }
        }else if (view == binding.layoutSlOne.jingyingtiaojianThreeNo){
            if (isChecked) {
                mFeedFieldBean.jingyingtiaojianThree = 0;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinOneOk){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinOne = 1;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinOneNo){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinOne = 0;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinTwoOk){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinTwo = 1;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinTwoNo){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinTwo = 0;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinThreeOk){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinThree = 1;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinThreeNo){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinThree = 0;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinFourOk){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinFour = 1;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinFourNo){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinFour = 0;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinFiveOk){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinFive = 1;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinFiveNo){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinFive = 0;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinSixOk){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinSix = 1;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinSixNo){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinSix = 0;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinSevenOk){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinSeven = 1;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinSevenNo){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinSeven = 0;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinEightOk){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinEight = 1;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinEightNo){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinEight = 0;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinNineOk){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinNine = 1;
            }
        }else if (view == binding.layoutSlOne.jingyingchanpinNineNo){
            if (isChecked) {
                mFeedFieldBean.jingyingchanpinNine = 0;
            }
        }else if (view == binding.layoutSlOne.gouxiangjiluOneOk){
            if (isChecked) {
                mFeedFieldBean.gouxiangjiluOne = 1;
            }
        }else if (view == binding.layoutSlOne.gouxiangjiluOneNo){
            if (isChecked) {
                mFeedFieldBean.gouxiangjiluOne = 0;
            }
        }
        else if (view == binding.layoutSlOne.gouxiangjiluTwoOk){
            if (isChecked) {
                mFeedFieldBean.gouxiangjiluTwo = 1;
            }
        }else if (view == binding.layoutSlOne.gouxiangjiluTwoNo){
            if (isChecked) {
                mFeedFieldBean.gouxiangjiluTwo = 0;
            }
        }else if (view == binding.layoutSlOne.gouxiangjiluThreeOk){
            if (isChecked) {
                mFeedFieldBean.gouxiangjiluThree = 1;
            }
        }else if (view == binding.layoutSlOne.gouxiangjiluThreeNo){
            if (isChecked) {
                mFeedFieldBean.gouxiangjiluThree = 0;
            }
        }
        LogUtil.d(TAG,"饲料领域One" + mFeedFieldBean.toString());
    }
    private void  setSLOneCheckFalse(){
        binding.layoutSlOne.slrg1.clearCheck();
        binding.layoutSlOne.slrg2.clearCheck();
        binding.layoutSlOne.slrg3.clearCheck();
        binding.layoutSlOne.slrg4.clearCheck();
        binding.layoutSlOne.slrg5.clearCheck();
        binding.layoutSlOne.slrg6.clearCheck();
        binding.layoutSlOne.slrg7.clearCheck();
        binding.layoutSlOne.slrg8.clearCheck();
        binding.layoutSlOne.slrg9.clearCheck();
        binding.layoutSlOne.slrg10.clearCheck();
        binding.layoutSlOne.slrg11.clearCheck();
        binding.layoutSlOne.slrg12.clearCheck();
        binding.layoutSlOne.slrg13.clearCheck();
        binding.layoutSlOne.slrg14.clearCheck();
        binding.layoutSlOne.slrg15.clearCheck();
    }
    /**
     * 饲料领域  成都市饲料和饲料添加剂经营单位执法检查记录表
     * @param view
     */
    public void onRadioButtonClickedSLTwo(View view){
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutSlTwo.tjjzhizhi1Ok) {
            if (isChecked) {
                mFeedFieldBean.tjjzhizhi1 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjzhizhi1No){
            if (isChecked) {
                mFeedFieldBean.tjjzhizhi1 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjzhizhi2Ok){
            if (isChecked) {
                mFeedFieldBean.tjjzhizhi2 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjzhizhi2No){
            if (isChecked) {
                mFeedFieldBean.tjjzhizhi2 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjsctj1Ok){
            if (isChecked) {
                mFeedFieldBean.tjjsctj1 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjsctj1No){
            if (isChecked) {
                mFeedFieldBean.tjjsctj1 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjsctj2Ok){
            if (isChecked) {
                mFeedFieldBean.tjjsctj2 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjsctj2No){
            if (isChecked) {
                mFeedFieldBean.tjjsctj2 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjsctj3Ok){
            if (isChecked) {
                mFeedFieldBean.tjjsctj3 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjsctj3No){
            if (isChecked) {
                mFeedFieldBean.tjjsctj3 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjsctj4Ok){
            if (isChecked) {
                mFeedFieldBean.tjjsctj4 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjsctj4No){
            if (isChecked) {
                mFeedFieldBean.tjjsctj4 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjsctj5Ok){
            if (isChecked) {
                mFeedFieldBean.tjjsctj5 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjsctj5No){
            if (isChecked) {
                mFeedFieldBean.tjjsctj5 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjylsy1Ok){
            if (isChecked) {
                mFeedFieldBean.tjjylsy1 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjylsy1No){
            if (isChecked) {
                mFeedFieldBean.tjjylsy1 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjylsy2Ok){
            if (isChecked) {
                mFeedFieldBean.tjjylsy2 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjylsy2No){
            if (isChecked) {
                mFeedFieldBean.tjjylsy2 = 0;
            }
        }
        else if (view == binding.layoutSlTwo.tjjgfqk1Ok){
            if (isChecked) {
                mFeedFieldBean.tjjgfqk1 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjgfqk1No){
            if (isChecked) {
                mFeedFieldBean.tjjgfqk1 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjgfqk2Ok){
            if (isChecked) {
                mFeedFieldBean.tjjgfqk2 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjgfqk2No){
            if (isChecked) {
                mFeedFieldBean.tjjgfqk2 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjgfqk3Ok){
            if (isChecked) {
                mFeedFieldBean.tjjgfqk3 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjgfqk3No){
            if (isChecked) {
                mFeedFieldBean.tjjgfqk3 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjgfqk4Ok){
            if (isChecked) {
                mFeedFieldBean.tjjgfqk4 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjgfqk4No){
            if (isChecked) {
                mFeedFieldBean.tjjgfqk4 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjgfqk5Ok){
            if (isChecked) {
                mFeedFieldBean.tjjgfqk5 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjgfqk5No){
            if (isChecked) {
                mFeedFieldBean.tjjgfqk5 = 0;
            }
        }
        else if (view == binding.layoutSlTwo.tjjaqsc1Ok){
            if (isChecked) {
                mFeedFieldBean.tjjaqsc1 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjaqsc1No){
            if (isChecked) {
                mFeedFieldBean.tjjaqsc1 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjaqsc2Ok){
            if (isChecked) {
                mFeedFieldBean.tjjaqsc2 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjaqsc2No){
            if (isChecked) {
                mFeedFieldBean.tjjaqsc2 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjaqsc3Ok){
            if (isChecked) {
                mFeedFieldBean.tjjaqsc3 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjaqsc3No){
            if (isChecked) {
                mFeedFieldBean.tjjaqsc3 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjaqsc4Ok){
            if (isChecked) {
                mFeedFieldBean.tjjaqsc4 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjaqsc4No){
            if (isChecked) {
                mFeedFieldBean.tjjaqsc4 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjaqsc5Ok){
            if (isChecked) {
                mFeedFieldBean.tjjaqsc5 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjaqsc5No){
            if (isChecked) {
                mFeedFieldBean.tjjaqsc5 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjsccp1Ok){
            if (isChecked) {
                mFeedFieldBean.tjjsccp1 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjsccp1No){
            if (isChecked) {
                mFeedFieldBean.tjjsccp1 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjsccp2Ok){
            if (isChecked) {
                mFeedFieldBean.tjjsccp2 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjsccp2No){
            if (isChecked) {
                mFeedFieldBean.tjjsccp2 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjsccp3Ok){
            if (isChecked) {
                mFeedFieldBean.tjjsccp3 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjsccp3No){
            if (isChecked) {
                mFeedFieldBean.tjjsccp3 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjsccp4Ok){
            if (isChecked) {
                mFeedFieldBean.tjjsccp4 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjsccp4No){
            if (isChecked) {
                mFeedFieldBean.tjjsccp4 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjsccp5Ok){
            if (isChecked) {
                mFeedFieldBean.tjjsccp5 = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjsccp5No){
            if (isChecked) {
                mFeedFieldBean.tjjsccp5 = 0;
            }
        }else if (view == binding.layoutSlTwo.tjjsclyOk){
            if (isChecked) {
                mFeedFieldBean.tjjscly = 1;
            }
        }else if (view == binding.layoutSlTwo.tjjsclyNo){
            if (isChecked) {
                mFeedFieldBean.tjjscly = 0;
            }
        }
        LogUtil.d(TAG,"饲料领域Two" + mFeedFieldBean.toString());
    }
    private void   setSLTwoCheckFalse(){
        binding.layoutSlTwo.slrg1.clearCheck();
        binding.layoutSlTwo.slrg2.clearCheck();
        binding.layoutSlTwo.slrg3.clearCheck();
        binding.layoutSlTwo.slrg4.clearCheck();
        binding.layoutSlTwo.slrg5.clearCheck();
        binding.layoutSlTwo.slrg6.clearCheck();
        binding.layoutSlTwo.slrg7.clearCheck();
        binding.layoutSlTwo.slrg8.clearCheck();
        binding.layoutSlTwo.slrg9.clearCheck();
        binding.layoutSlTwo.slrg10.clearCheck();
        binding.layoutSlTwo.slrg11.clearCheck();
        binding.layoutSlTwo.slrg12.clearCheck();
        binding.layoutSlTwo.slrg13.clearCheck();
        binding.layoutSlTwo.slrg14.clearCheck();
        binding.layoutSlTwo.slrg15.clearCheck();
        binding.layoutSlTwo.slrg15.clearCheck();
        binding.layoutSlTwo.slrg16.clearCheck();
        binding.layoutSlTwo.slrg17.clearCheck();
        binding.layoutSlTwo.slrg18.clearCheck();
        binding.layoutSlTwo.slrg19.clearCheck();
        binding.layoutSlTwo.slrg20.clearCheck();
        binding.layoutSlTwo.slrg21.clearCheck();
        binding.layoutSlTwo.slrg22.clearCheck();
        binding.layoutSlTwo.slrg23.clearCheck();
        binding.layoutSlTwo.slrg24.clearCheck();
        binding.layoutSlTwo.slrg25.clearCheck();
    }
    /**
     * 生猪屠宰领域 1   成都市孵化场动物卫生监督执法检查记录表
     * @param view
     */
    public void onRadioButtonClickedPigOne(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutPigOne.hatchJCFYSS1Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchJCFYSS1 = 1;
            }
        } else if (view == binding.layoutPigOne.hatchJCFYSS1No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchJCFYSS1 = 0;
            }
        } else if (view == binding.layoutPigOne.hatchJCFYSS2Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchJCFYSS2 = 1;
            }
        } else if (view == binding.layoutPigOne.hatchJCFYSS2No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchJCFYSS2 = 0;
            }
        } else if (view == binding.layoutPigOne.hatchJCDJ1Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchJCDJ1 = 1;
            }
        } else if (view == binding.layoutPigOne.hatchJCDJ1No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchJCDJ1 = 0;
            }
        } else if (view == binding.layoutPigOne.hatchJCDJ2Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchJCDJ2 = 1;
            }
        } else if (view == binding.layoutPigOne.hatchJCDJ2No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchJCDJ2 = 0;
            }
        } else if (view == binding.layoutPigOne.hatchJCDJ3Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchJCDJ3 = 1;
            }
        } else if (view == binding.layoutPigOne.hatchJCDJ3No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchJCDJ3 = 0;
            }
        } else if (view == binding.layoutPigOne.hatchWSXD1Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchWSXD1 = 1;
            }
        } else if (view == binding.layoutPigOne.hatchWSXD1No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchWSXD1 = 0;
            }
        } else if (view == binding.layoutPigOne.hatchWSXD2Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchWSXD2 = 1;
            }
        } else if (view == binding.layoutPigOne.hatchWSXD2No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchWSXD2 = 0;
            }
        } else if (view == binding.layoutPigOne.hatchWHH1Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchWHH1 = 1;
            }
        } else if (view == binding.layoutPigOne.hatchWHH1No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchWHH1 = 0;
            }
        }else if (view == binding.layoutPigOne.hatchWHH2Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchWHH2 = 1;
            }
        }
        else if (view == binding.layoutPigOne.hatchWHH2No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchWHH2 = 0;
            }
        }else if (view == binding.layoutPigOne.hatchYQBGOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchYQBG = 1;
            }
        }else if (view == binding.layoutPigOne.hatchYQBGNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchYQBG = 0;
            }
        }else if (view == binding.layoutPigOne.hatchYQBGWY) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchYQBG = 2;
            }
        }else if (view == binding.layoutPigOne.hatchGXJLOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchGXJL = 1;
            }
        }else if (view == binding.layoutPigOne.hatchGXJLNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.hatchGXJL = 0;
            }
        }
    }
    private void  setPigOneCheckFalse(){
        binding.layoutPigOne.pigrg1.clearCheck();
        binding.layoutPigOne.pigrg2.clearCheck();
        binding.layoutPigOne.pigrg3.clearCheck();
        binding.layoutPigOne.pigrg4.clearCheck();
        binding.layoutPigOne.pigrg5.clearCheck();
        binding.layoutPigOne.pigrg6.clearCheck();
        binding.layoutPigOne.pigrg7.clearCheck();
        binding.layoutPigOne.pigrg8.clearCheck();
        binding.layoutPigOne.pigrg9.clearCheck();
        binding.layoutPigOne.pigrg10.clearCheck();
        binding.layoutPigOne.pigrg11.clearCheck();

        binding.layoutPigOne.hatchPlayEggsEt.setText("");
        binding.layoutPigOne.hatchEggsEt.setText("");
        binding.layoutPigOne.hatchBroodHatchedEt.setText("");
        binding.layoutPigOne.managementSystemEt.setText("");
        binding.layoutPigOne.lastJanuaryOutNumEt.setText("");
        binding.layoutPigOne.quarantineReportEt.setText("");
    }
    /**
     * 生猪屠宰领域2    成都市孵化场动物卫生监督执法检查记录表
     * @param view
     */
    public void onRadioButtonClickedPigTwo(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutPigTwo.TZZD1Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD1 = 1;
            }
        } else if (view == binding.layoutPigTwo.TZZD1No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD1 = 0;
            }
        } else if (view == binding.layoutPigTwo.TZZD2Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD2 = 1;
            }
        } else if (view == binding.layoutPigTwo.TZZD2No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD2 = 0;
            }
        } else if (view == binding.layoutPigTwo.TZZD3Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD3 = 1;
            }
        } else if (view == binding.layoutPigTwo.TZZD3No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD3 = 0;
            }
        } else if (view == binding.layoutPigTwo.TZZD4Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD4 = 1;
            }
        } else if (view == binding.layoutPigTwo.TZZD4No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD4 = 0;
            }
        } else if (view == binding.layoutPigTwo.TZZD5Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD5 = 1;
            }
        } else if (view == binding.layoutPigTwo.TZZD5No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD5 = 0;
            }
        } else if (view == binding.layoutPigTwo.TZZD6Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD6 = 1;
            }
        } else if (view == binding.layoutPigTwo.TZZD6No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD6 = 0;
            }
        } else if (view == binding.layoutPigTwo.TZZD7Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD7 = 1;
            }
        } else if (view == binding.layoutPigTwo.TZZD7No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD7 = 0;
            }
        } else if (view == binding.layoutPigTwo.TZZD8Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD8 = 1;
            }
        } else if (view == binding.layoutPigTwo.TZZD8No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD8 = 0;
            }
        }else if (view == binding.layoutPigTwo.TZZD9Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD9 = 1;
            }
        }
        else if (view == binding.layoutPigTwo.TZZD9No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD9 = 0;
            }
        }else if (view == binding.layoutPigTwo.TZZD10Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD10 = 1;
            }
        }else if (view == binding.layoutPigTwo.TZZD10No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZZD10 = 0;
            }
        }else if (view == binding.layoutPigTwo.TZSSSBOneOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZSSSBOne = 1;
            }
        }else if (view == binding.layoutPigTwo.TZSSSBOneNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZSSSBOne = 0;
            }
        }else if (view == binding.layoutPigTwo.TZSSSBTwoOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZSSSBTwo = 1;
            }
        }else if (view == binding.layoutPigTwo.TZSSSBTwoNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZSSSBTwo = 0;
            }
        }else if (view == binding.layoutPigTwo.TZJCOneOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZJCOne = 1;
            }
        }else if (view == binding.layoutPigTwo.TZJCOneNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZJCOne = 0;
            }
        }else if (view == binding.layoutPigTwo.TZJCTwoOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZJCTwo = 1;
            }
        }else if (view == binding.layoutPigTwo.TZJCTwoNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZJCTwo = 0;
            }
        }else if (view == binding.layoutPigTwo.TZJCThreeOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZJCThree = 1;
            }
        }else if (view == binding.layoutPigTwo.TZJCThreeNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZJCThree = 0;
            }
        }else if (view == binding.layoutPigTwo.TZDZOneOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDZOne = 1;
            }
        }else if (view == binding.layoutPigTwo.TZDZOneNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDZOne = 0;
            }
        }else if (view == binding.layoutPigTwo.TZDZTwoOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDZTwo = 1;
            }
        }else if (view == binding.layoutPigTwo.TZDZTwoNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDZTwo = 0;
            }
        }else if (view == binding.layoutPigTwo.TZDZThreeOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDZThree = 1;
            }
        }else if (view == binding.layoutPigTwo.TZDZThreeNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDZThree = 0;
            }
        }else if (view == binding.layoutPigTwo.TZDZFourOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDZFour = 1;
            }
        }else if (view == binding.layoutPigTwo.TZDZFourNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDZFour = 0;
            }
        }else if (view == binding.layoutPigTwo.TZDZFiveOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDZFive = 1;
            }
        }else if (view == binding.layoutPigTwo.TZDZFiveNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDZFive = 0;
            }
        }else if (view == binding.layoutPigTwo.TZDZSixOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDZSix = 1;
            }
        }else if (view == binding.layoutPigTwo.TZDZSixNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDZSix = 0;
            }
        }else if (view == binding.layoutPigTwo.TZWHHOneOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZWHHOne = 1;
            }
        }else if (view == binding.layoutPigTwo.TZWHHOneNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZWHHOne = 0;
            }
        }else if (view == binding.layoutPigTwo.TZWHHTwoOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZWHHTwo = 1;
            }
        }else if (view == binding.layoutPigTwo.TZWHHTwoNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZWHHTwo = 0;
            }
        }else if (view == binding.layoutPigTwo.TZCCSZOneOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZCCSZOne = 1;
            }
        }else if (view == binding.layoutPigTwo.TZCCSZOneNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZCCSZOne = 0;
            }
        }else if (view == binding.layoutPigTwo.TZCCSZTwoOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZCCSZTwo = 1;
            }
        }else if (view == binding.layoutPigTwo.TZCCSZTwoNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZCCSZTwo = 0;
            }
        }else if (view == binding.layoutPigTwo.TZCCSZThreeOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZCCSZThree = 1;
            }
        }else if (view == binding.layoutPigTwo.TZCCSZThreeNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZCCSZThree = 0;
            }
        }

        else if (view == binding.layoutPigTwo.TZRYTJOneOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZRYTJOne = 1;
            }
        }else if (view == binding.layoutPigTwo.TZRYTJOneNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZRYTJOne = 0;
            }
        }else if (view == binding.layoutPigTwo.TZRYTJTwoOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZRYTJTwo = 1;
            }
        }else if (view == binding.layoutPigTwo.TZRYTJTwoNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZRYTJTwo = 0;
            }
        }else if (view == binding.layoutPigTwo.TZXXBSOneOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZXXBSOne = 1;
            }
        }else if (view == binding.layoutPigTwo.TZXXBSOneNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZXXBSOne = 0;
            }
        }else if (view == binding.layoutPigTwo.TZXXBSTwoOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZXXBSTwo = 1;
            }
        }else if (view == binding.layoutPigTwo.TZXXBSTwoNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZXXBSTwo = 0;
            }
        }else if (view == binding.layoutPigTwo.TZXXBSThreeOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZXXBSThree = 1;
            }
        }else if (view == binding.layoutPigTwo.TZXXBSThreeNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZXXBSThree = 0;
            }
        }else if (view == binding.layoutPigTwo.TZDAGLOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDAGL = 1;
            }
        }else if (view == binding.layoutPigTwo.TZDAGLNo) {
            if (isChecked) {
                mHogSlaughteringAreaBean.TZDAGL = 0;
            }
        }else if (view == binding.layoutPigTwo.tuizai1Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai1 = 1;
            }
        }else if (view == binding.layoutPigTwo.tuizai1No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai1 = 0;
            }
        }
        else if (view == binding.layoutPigTwo.tuizai2Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai2 = 1;
            }
        }else if (view == binding.layoutPigTwo.tuizai2No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai2 = 0;
            }
        }else if (view == binding.layoutPigTwo.tuizai3Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai3 = 1;
            }
        }else if (view == binding.layoutPigTwo.tuizai3No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai3 = 0;
            }
        }else if (view == binding.layoutPigTwo.tuizai4Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai4 = 1;
            }
        }else if (view == binding.layoutPigTwo.tuizai4No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai4 = 0;
            }
        }else if (view == binding.layoutPigTwo.tuizai5Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai5 = 1;
            }
        }else if (view == binding.layoutPigTwo.tuizai5No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai5 = 0;
            }
        }else if (view == binding.layoutPigTwo.tuizai6Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai6 = 1;
            }
        }else if (view == binding.layoutPigTwo.tuizai6No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai6 = 0;
            }
        }else if (view == binding.layoutPigTwo.tuizai7Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai7 = 1;
            }
        }else if (view == binding.layoutPigTwo.tuizai7No) {
            if (isChecked) {
                mHogSlaughteringAreaBean.tuzai7 = 0;
            }
        }
    }
    private void   setPigTwoCheckFalse(){
        binding.layoutPigTwo.pigrg1.clearCheck();
        binding.layoutPigTwo.pigrg2.clearCheck();
        binding.layoutPigTwo.pigrg3.clearCheck();
        binding.layoutPigTwo.pigrg4.clearCheck();
        binding.layoutPigTwo.pigrg5.clearCheck();
        binding.layoutPigTwo.pigrg6.clearCheck();
        binding.layoutPigTwo.pigrg7.clearCheck();
        binding.layoutPigTwo.pigrg8.clearCheck();
        binding.layoutPigTwo.pigrg9.clearCheck();
        binding.layoutPigTwo.pigrg10.clearCheck();
        binding.layoutPigTwo.pigrg11.clearCheck();
        binding.layoutPigTwo.pigrg12.clearCheck();
        binding.layoutPigTwo.pigrg13.clearCheck();
        binding.layoutPigTwo.pigrg14.clearCheck();
        binding.layoutPigTwo.pigrg15.clearCheck();
        binding.layoutPigTwo.pigrg16.clearCheck();
        binding.layoutPigTwo.pigrg17.clearCheck();
        binding.layoutPigTwo.pigrg18.clearCheck();
        binding.layoutPigTwo.pigrg19.clearCheck();
        binding.layoutPigTwo.pigrg20.clearCheck();
        binding.layoutPigTwo.pigrg21.clearCheck();
        binding.layoutPigTwo.pigrg22.clearCheck();
        binding.layoutPigTwo.pigrgzd1.clearCheck();
        binding.layoutPigTwo.pigrgzd2.clearCheck();
        binding.layoutPigTwo.pigrgzd3.clearCheck();
        binding.layoutPigTwo.pigrgzd4.clearCheck();
        binding.layoutPigTwo.pigrgzd5.clearCheck();
        binding.layoutPigTwo.pigrgzd6.clearCheck();
        binding.layoutPigTwo.pigrgzd7.clearCheck();
        binding.layoutPigTwo.pigrgzd8.clearCheck();
        binding.layoutPigTwo.pigrgzd9.clearCheck();
        binding.layoutPigTwo.pigrgzd10.clearCheck();

        binding.layoutPigTwo.tuizairg1.clearCheck();
        binding.layoutPigTwo.tuizairg2.clearCheck();
        binding.layoutPigTwo.tuizairg3.clearCheck();
        binding.layoutPigTwo.tuizairg4.clearCheck();
        binding.layoutPigTwo.tuizairg5.clearCheck();
        binding.layoutPigTwo.tuizairg6.clearCheck();
        binding.layoutPigTwo.tuizairg7.clearCheck();

    }
    /****
     * 生猪领域 3 成都市畜禽收购贩运户动物卫生监督检查记录表
     * @param view
     */
    public void onRadioButtonClickedPigThree(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutPigThree.clinicalOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.clinical = 1;
            }
        }else if (view == binding.layoutPigThree.clinicalNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.clinical = 0;
            }
        }else if (view == binding.layoutPigThree.traffickQuarantineCertificateOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.traffickQuarantineCertificate = 1;
            }
        }else if (view == binding.layoutPigThree.traffickQuarantineCertificateNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.traffickQuarantineCertificate = 0;
            }
        }else if (view == binding.layoutPigThree.waterfloodingOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.waterflooding = 1;
            }
        }else if (view == binding.layoutPigThree.waterfloodingNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.waterflooding = 0;
            }
        }else if (view == binding.layoutPigThree.carRequirementOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.carRequirement = 1;
            }
        }else if (view == binding.layoutPigThree.carRequirementNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.carRequirement = 0;
            }
        }else if (view == binding.layoutPigThree.vehicleDisinfectionOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.vehicleDisinfection = 1;
            }
        }else if (view == binding.layoutPigThree.vehicleDisinfectionNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.vehicleDisinfection = 0;
            }
        }else if (view == binding.layoutPigThree.wasteDisposalMethodOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.wasteDisposalMethod = 1;
            }
        }else if (view == binding.layoutPigThree.wasteDisposalMethodNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.wasteDisposalMethod = 0;
            }
        }else if (view == binding.layoutPigThree.designatedChannelOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.designatedChannel = 1;
            }
        }else if (view == binding.layoutPigThree.designatedChannelNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.designatedChannel = 0;
            }
        }else if (view == binding.layoutPigThree.ledgerOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.ledger = 1;
            }
        }else if (view == binding.layoutPigThree.ledgerNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.ledger = 0;
            }
        }else if (view == binding.layoutPigThree.ledgerWu){
            if (isChecked) {
                mHogSlaughteringAreaBean.ledger = 2;
            }
        }
        else if (view == binding.layoutPigThree.healthCertificateOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.healthCertificate = 1;
            }
        }else if (view == binding.layoutPigThree.healthCertificateNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.healthCertificate = 0;
            }
        }
    }
    private void   setPigThreeCheckFalse(){
        binding.layoutPigThree.traffickedrg1.clearCheck();
        binding.layoutPigThree.traffickedrg2.clearCheck();
        binding.layoutPigThree.traffickedrg3.clearCheck();
        binding.layoutPigThree.traffickedrg4.clearCheck();
        binding.layoutPigThree.traffickedrg5.clearCheck();
        binding.layoutPigThree.traffickedrg6.clearCheck();
        binding.layoutPigThree.traffickedrg7.clearCheck();
        binding.layoutPigThree.traffickedrg8.clearCheck();
        binding.layoutPigThree.traffickedrg9.clearCheck();

       binding.layoutPigThree.recordNoEt.setText("");
       binding.layoutPigThree.carNumEt.setText("");
       binding.layoutPigThree.traffickingAnimalEt.setText("");
       binding.layoutPigThree.traffickedNumEt.setText("");
       binding.layoutPigThree.badgesNotWornEt.setText("");
    }
    /****
     * 生猪领域 4 成都市动物诊疗机构动物卫生监督执法检查记录表
     * @param view
     */
    public void onRadioButtonClickedPigFour(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutPigFour.consistentSituationOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.consistentSituation = 1;
            }
        }else if (view == binding.layoutPigFour.consistentSituationNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.consistentSituation = 0;
            }
        }else if (view == binding.layoutPigFour.diagnosisTreatmentManagementOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.diagnosisTreatmentManagement = 1;
            }
        }else if (view == binding.layoutPigFour.diagnosisTreatmentManagementNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.diagnosisTreatmentManagement = 0;
            }
        }else if (view == binding.layoutPigFour.employeesInformationOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.employeesInformation = 1;
            }
        }else if (view == binding.layoutPigFour.employeesInformationNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.employeesInformation = 0;
            }
        }else if (view == binding.layoutPigFour.prescribedMedicationOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.prescribedMedication = 1;
            }
        }else if (view == binding.layoutPigFour.prescribedMedicationNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.prescribedMedication = 0;
            }
        }else if (view == binding.layoutPigFour.useVeterinaryDrugsOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.useVeterinaryDrugs = 1;
            }
        }else if (view == binding.layoutPigFour.useVeterinaryDrugsNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.useVeterinaryDrugs = 0;
            }
        }else if (view == binding.layoutPigFour.drugProhibitionOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.drugProhibition = 1;
            }
        }else if (view == binding.layoutPigFour.drugProhibitionNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.drugProhibition = 0;
            }
        }else if (view == binding.layoutPigFour.departmentalApprovalOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.departmentalApproval = 1;
            }
        }else if (view == binding.layoutPigFour.departmentalApprovalNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.departmentalApproval = 0;
            }
        }else if (view == binding.layoutPigFour.organizationNameOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.organizationName = 1;
            }
        }else if (view == binding.layoutPigFour.organizationNameNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.organizationName = 0;
            }
        }else if (view == binding.layoutPigFour.termPreservationOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.termPreservation = 1;
            }
        }
        else if (view == binding.layoutPigFour.termPreservationNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.termPreservation = 0;
            }
        }else if (view == binding.layoutPigFour.reportEpidemicOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.reportEpidemic = 1;
            }
        }else if (view == binding.layoutPigFour.reportEpidemicNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.reportEpidemic = 0;
            }
        }else if (view == binding.layoutPigFour.reportEpidemicWu){
            if (isChecked) {
                mHogSlaughteringAreaBean.reportEpidemic = 2;
            }
        }else if (view == binding.layoutPigFour.giveTreatmentOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.giveTreatment = 1;
            }
        }else if (view == binding.layoutPigFour.giveTreatmentNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.giveTreatment = 0;
            }
        }else if (view == binding.layoutPigFour.giveTreatmentWu){
            if (isChecked) {
                mHogSlaughteringAreaBean.giveTreatment = 2;
            }
        }else if (view == binding.layoutPigFour.harmlessAnimalOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.harmlessAnimal = 1;
            }
        }else if (view == binding.layoutPigFour.harmlessAnimalNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.harmlessAnimal = 0;
            }
        }else if (view == binding.layoutPigFour.wasteWaterOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.wasteWater = 1;
            }
        }else if (view == binding.layoutPigFour.wasteWaterNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.wasteWater = 0;
            }
        }else if (view == binding.layoutPigFour.reportIssuingAuthorityOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.reportIssuingAuthority = 1;
            }
        }else if (view == binding.layoutPigFour.reportIssuingAuthorityNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.reportIssuingAuthority = 0;
            }
        }
        else if (view == binding.layoutPigFour.peopleHealthCertificateOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.peopleHealthCertificate = 1;
            }
        }else if (view == binding.layoutPigFour.peopleHealthCertificateNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.peopleHealthCertificate = 0;
            }
        } else if (view == binding.layoutPigFour.requiredRecordOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.requiredRecord = 1;
            }
        }else if (view == binding.layoutPigFour.requiredRecordNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.requiredRecord = 0;
            }
        }
        else if (view == binding.layoutPigFour.standardFillingOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.standardFilling = 1;
            }
        }else if (view == binding.layoutPigFour.standardFillingNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.standardFilling = 0;
            }
        }
        else if (view == binding.layoutPigFour.reportFilingAuthorityOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.reportFilingAuthority = 1;
            }
        }else if (view == binding.layoutPigFour.reportFilingAuthorityNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.reportFilingAuthority = 0;
            }
        }
        else if (view == binding.layoutPigFour.recordsCompleteOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.recordsComplete = 1;
            }
        }else if (view == binding.layoutPigFour.recordsCompleteNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.recordsComplete = 0;
            }
        }
    }
    private void    setPigFourCheckFalse(){
        binding.layoutPigFour.pigFourrg1.clearCheck();
        binding.layoutPigFour.pigFourrg2.clearCheck();
        binding.layoutPigFour.pigFourrg3.clearCheck();
        binding.layoutPigFour.pigFourrg4.clearCheck();
        binding.layoutPigFour.pigFourrg5.clearCheck();
        binding.layoutPigFour.pigFourrg6.clearCheck();
        binding.layoutPigFour.pigFourrg7.clearCheck();
        binding.layoutPigFour.pigFourrg8.clearCheck();
        binding.layoutPigFour.pigFourrg9.clearCheck();
        binding.layoutPigFour.pigFourrg10.clearCheck();
        binding.layoutPigFour.pigFourrg11.clearCheck();
        binding.layoutPigFour.pigFourrg12.clearCheck();
        binding.layoutPigFour.pigFourrg13.clearCheck();
        binding.layoutPigFour.pigFourrg14.clearCheck();
        binding.layoutPigFour.pigFourrg15.clearCheck();
        binding.layoutPigFour.pigFourrg16.clearCheck();
        binding.layoutPigFour.pigFourrg17.clearCheck();
        binding.layoutPigFour.pigFourrg18.clearCheck();

        binding.layoutPigFour.animalMedicalPermitEt.setText("");
        binding.layoutPigFour.licenceIssuedEt.setText("");
        binding.layoutPigFour.medicalInstitutionsTypeEt.setText("");
        binding.layoutPigFour.employeesNumEt.setText("");
        binding.layoutPigFour.licensedVeterinarianNumEt.setText("");
        binding.layoutPigFour.practicingAssistantVeterinarianNumEt.setText("");
        binding.layoutPigFour.practiceProjectEt.setText("");
        binding.layoutPigFour.useRegistrationEt.setText("");


    }
    /****
     * 生猪领域 5 成都市动物诊疗机构动物卫生监督执法检查记录表
     * @param view
     */
    public void onRadioButtonClickedPigFive(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutPigFive.countrysideOneOk) {
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideOne = 1;
            }
        }else if (view == binding.layoutPigFive.countrysideOneNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideOne = 0;
            }
        }else if (view == binding.layoutPigFive.countrysideTwoOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideTwo = 1;
            }
        }else if (view == binding.layoutPigFive.countrysideTwoNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideTwo = 0;
            }
        }else if (view == binding.layoutPigFive.countrysideThreeOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideThree = 1;
            }
        }else if (view == binding.layoutPigFive.countrysideThreeNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideThree = 0;
            }
        }else if (view == binding.layoutPigFive.countrysideFourOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideFour = 1;
            }
        }else if (view == binding.layoutPigFive.countrysideFourNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideFour = 0;
            }
        }else if (view == binding.layoutPigFive.countrysideFiveOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideFive = 1;
            }
        }else if (view == binding.layoutPigFive.countrysideFiveNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideFive = 0;
            }
        }else if (view == binding.layoutPigFive.countrysideSixOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideSix = 1;
            }
        }else if (view == binding.layoutPigFive.countrysideSixNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideSix = 0;
            }
        }else if (view == binding.layoutPigFive.countrysideSixWu){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideSix = 2;
            }
        }else if (view == binding.layoutPigFive.countrysideSevenOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideSeven = 1;
            }
        }else if (view == binding.layoutPigFive.countrysideSevenNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideSeven = 0;
            }
        }else if (view == binding.layoutPigFive.countrysideSevenWu){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideSeven = 2;
            }
        }else if (view == binding.layoutPigFive.countrysideEightOk){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideEight = 1;
            }
        }
        else if (view == binding.layoutPigFive.countrysideEightNo){
            if (isChecked) {
                mHogSlaughteringAreaBean.countrysideEight = 0;
            }
        }
    }
    private void  setPigFiveCheckFalse(){
        binding.layoutPigFive.pigFiveRg1.clearCheck();
        binding.layoutPigFive.pigFiveRg2.clearCheck();
        binding.layoutPigFive.pigFiveRg3.clearCheck();
        binding.layoutPigFive.pigFiveRg4.clearCheck();
        binding.layoutPigFive.pigFiveRg5.clearCheck();
        binding.layoutPigFive.pigFiveRg6.clearCheck();
        binding.layoutPigFive.pigFiveRg7.clearCheck();
        binding.layoutPigFive.pigFiveRg8.clearCheck();

        binding.layoutPigFive.countrysideAnimalMedicalPermitEt.setText("");
        binding.layoutPigFive.countrysideDateIssueEt.setText("");
        binding.layoutPigFive.countrysideEmployeesTotalNumEt.setText("");
        binding.layoutPigFive.countrysideLicensedVeterinarianEt.setText("");
        binding.layoutPigFive.countrysidePracticingAssistantVeterinarianEt.setText("");
        binding.layoutPigFive.countrysideRuralVeterinarianEt.setText("");
        binding.layoutPigFive.countrysideVeterinarianNameEt.setText("");
        binding.layoutPigFive.countrysideEmploymentCertificateNumberEt.setText("");
    }
    public void onRadioButtonClickedPigSix(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutPigSix.slaughter1Ok) {
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter1 = 1;
            }
        }else if (view == binding.layoutPigSix.slaughter1No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter1 = 0;
            }
        }else if (view == binding.layoutPigSix.slaughter2Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter2 = 1;
            }
        }else if (view == binding.layoutPigSix.slaughter2No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter2 = 0;
            }
        }else if (view == binding.layoutPigSix.slaughter3Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter3 = 1;
            }
        }else if (view == binding.layoutPigSix.slaughter3No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter3 = 0;
            }
        }else if (view == binding.layoutPigSix.slaughter4Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter4 = 1;
            }
        }else if (view == binding.layoutPigSix.slaughter4No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter4 = 0;
            }
        }else if (view == binding.layoutPigSix.slaughter5Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter5 = 1;
            }
        }else if (view == binding.layoutPigSix.slaughter5No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter5 = 0;
            }
        }else if (view == binding.layoutPigSix.slaughter6Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter6 = 1;
            }
        }else if (view == binding.layoutPigSix.slaughter6No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter6 = 0;
            }
        }else if (view == binding.layoutPigSix.slaughter7Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter7 = 1;
            }
        }else if (view == binding.layoutPigSix.slaughter7No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter7 = 0;
            }
        }else if (view == binding.layoutPigSix.slaughter8Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter8 = 1;
            }
        }else if (view == binding.layoutPigSix.slaughter8No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter8 = 0;
            }
        }else if (view == binding.layoutPigSix.slaughter9Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter9 = 1;
            }
        }
        else if (view == binding.layoutPigSix.slaughter9No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter9 = 0;
            }
        }
        else if (view == binding.layoutPigSix.slaughter10Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter10 = 1;
            }
        }
        else if (view == binding.layoutPigSix.slaughter10No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter10 = 0;
            }
        }else if (view == binding.layoutPigSix.slaughter11Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter11 = 1;
            }
        }
        else if (view == binding.layoutPigSix.slaughter11No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter11 = 0;
            }
        }
        else if (view == binding.layoutPigSix.slaughter12Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter12 = 1;
            }
        }
        else if (view == binding.layoutPigSix.slaughter12No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter12 = 0;
            }
        }
        else if (view == binding.layoutPigSix.slaughter13Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter13 = 1;
            }
        }
        else if (view == binding.layoutPigSix.slaughter13No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter13 = 0;
            }
        }
        else if (view == binding.layoutPigSix.slaughter14Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter14 = 1;
            }
        }
        else if (view == binding.layoutPigSix.slaughter14No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter14 = 0;
            }
        }
        else if (view == binding.layoutPigSix.slaughter15Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter15 = 1;
            }
        }
        else if (view == binding.layoutPigSix.slaughter15No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter15 = 0;
            }
        }
        else if (view == binding.layoutPigSix.slaughter16Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter16 = 1;
            }
        }
        else if (view == binding.layoutPigSix.slaughter16No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter16 = 0;
            }
        }
        else if (view == binding.layoutPigSix.slaughter17Ok){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter17 = 1;
            }
        }
        else if (view == binding.layoutPigSix.slaughter17No){
            if (isChecked) {
                mHogSlaughteringAreaBean.slaughter17 = 0;
            }
        }
    }
    private void setPigSixCheckFalse(){
        binding.layoutPigSix.pigSixRg1.clearCheck();
        binding.layoutPigSix.pigSixRg2.clearCheck();
        binding.layoutPigSix.pigSixRg3.clearCheck();
        binding.layoutPigSix.pigSixRg4.clearCheck();
        binding.layoutPigSix.pigSixRg5.clearCheck();
        binding.layoutPigSix.pigSixRg6.clearCheck();
        binding.layoutPigSix.pigSixRg7.clearCheck();
        binding.layoutPigSix.pigSixRg8.clearCheck();
        binding.layoutPigSix.pigSixRg9.clearCheck();
        binding.layoutPigSix.pigSixRg10.clearCheck();
        binding.layoutPigSix.pigSixRg11.clearCheck();
        binding.layoutPigSix.pigSixRg12.clearCheck();
        binding.layoutPigSix.pigSixRg13.clearCheck();
        binding.layoutPigSix.pigSixRg14.clearCheck();
        binding.layoutPigSix.pigSixRg15.clearCheck();
        binding.layoutPigSix.pigSixRg16.clearCheck();
        binding.layoutPigSix.pigSixRg17.clearCheck();
    }
    /****
     * 植物检疫领域  成都市植物检疫检查记录表
     * @param view
     */
    public void onRadioButtonClickedPlant(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutPlant.plant1Ok) {
            if (isChecked) {
                mFieldPlantQuarantineBean.plant1 = 1;
            }
        }else if (view == binding.layoutPlant.plant1No){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant1 = 0;
            }
        }else if (view == binding.layoutPlant.plant2Ok){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant2 = 1;
            }
        }else if (view == binding.layoutPlant.plant2No){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant2 = 0;
            }
        }else if (view == binding.layoutPlant.plant3Ok){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant3 = 1;
            }
        }else if (view == binding.layoutPlant.plant3No){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant3 = 0;
            }
        }else if (view == binding.layoutPlant.plant4Ok){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant4 = 1;
            }
        }else if (view == binding.layoutPlant.plant4No){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant4 = 0;
            }
        }else if (view == binding.layoutPlant.plant5Ok){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant5 = 1;
            }
        }else if (view == binding.layoutPlant.plant5No){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant5 = 0;
            }
        }else if (view == binding.layoutPlant.plant6Ok){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant6 = 1;
            }
        }else if (view == binding.layoutPlant.plant6No){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant6 = 0;
            }
        }else if (view == binding.layoutPlant.plant7Ok){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant7 = 1;
            }
        }else if (view == binding.layoutPlant.plant7No){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant7 = 0;
            }
        }else if (view == binding.layoutPlant.plant8Ok){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant8 = 1;
            }
        }else if (view == binding.layoutPlant.plant8No){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant8 = 0;
            }
        }else if (view == binding.layoutPlant.plant9Ok){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant9 = 1;
            }
        }
        else if (view == binding.layoutPlant.plant9No){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant9 = 0;
            }
        }
        else if (view == binding.layoutPlant.plant10Ok){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant10 = 1;
            }
        }
        else if (view == binding.layoutPlant.plant10No){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant10 = 0;
            }
        }else if (view == binding.layoutPlant.plant11Ok){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant11 = 1;
            }
        }
        else if (view == binding.layoutPlant.plant11No){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant12 = 0;
            }
        }
        else if (view == binding.layoutPlant.plant12Ok){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant12 = 1;
            }
        }
        else if (view == binding.layoutPlant.plant12No){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant12 = 0;
            }
        }
        else if (view == binding.layoutPlant.plant13Ok){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant13 = 1;
            }
        }
        else if (view == binding.layoutPlant.plant13No){
            if (isChecked) {
                mFieldPlantQuarantineBean.plant13 = 0;
            }
        }
    }
    private void setPlantCheckFalse(){
        binding.layoutPlant.plantRg1.clearCheck();
        binding.layoutPlant.plantRg2.clearCheck();
        binding.layoutPlant.plantRg3.clearCheck();
        binding.layoutPlant.plantRg4.clearCheck();
        binding.layoutPlant.plantRg5.clearCheck();
        binding.layoutPlant.plantRg6.clearCheck();
        binding.layoutPlant.plantRg7.clearCheck();
        binding.layoutPlant.plantRg8.clearCheck();
        binding.layoutPlant.plantRg9.clearCheck();
        binding.layoutPlant.plantRg10.clearCheck();
        binding.layoutPlant.plantRg11.clearCheck();
        binding.layoutPlant.plantRg12.clearCheck();
        binding.layoutPlant.plantRg13.clearCheck();

    }
    /****
     * 种畜禽领域  (成都市畜禽养殖场执法检查记录表)
     * @param view
     */
    public void onRadioButtonClickedZXQ(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutBreed.zxq1Ok) {
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq1 = 1;
            }
        }else if (view == binding.layoutBreed.zxq1No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq1 = 0;
            }
        }else if (view == binding.layoutBreed.zxq2Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq2 = 1;
            }
        }else if (view == binding.layoutBreed.zxq2No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq2 = 0;
            }
        }else if (view == binding.layoutBreed.zxq3Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq3 = 1;
            }
        }else if (view == binding.layoutBreed.zxq3No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq3 = 0;
            }
        }else if (view == binding.layoutBreed.zxq4Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq4 = 1;
            }
        }else if (view == binding.layoutBreed.zxq4No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq4 = 0;
            }
        }else if (view == binding.layoutBreed.zxq5Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq5 = 1;
            }
        }else if (view == binding.layoutBreed.zxq5No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq5 = 0;
            }
        }else if (view == binding.layoutBreed.zxq6Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq6 = 1;
            }
        }else if (view == binding.layoutBreed.zxq6No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq6 = 0;
            }
        }else if (view == binding.layoutBreed.zxq7Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq7 = 1;
            }
        }else if (view == binding.layoutBreed.zxq7No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq7 = 0;
            }
        }else if (view == binding.layoutBreed.zxq8Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq8 = 1;
            }
        }else if (view == binding.layoutBreed.zxq8No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq8 = 0;
            }
        }else if (view == binding.layoutBreed.zxq9Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq9 = 1;
            }
        }
        else if (view == binding.layoutBreed.zxq9No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq9 = 0;
            }
        }
        else if (view == binding.layoutBreed.zxq10Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq10 = 1;
            }
        }
        else if (view == binding.layoutBreed.zxq10No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq10 = 0;
            }
        }else if (view == binding.layoutBreed.zxq11Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq11 = 1;
            }
        }
        else if (view == binding.layoutBreed.zxq11No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq11 = 0;
            }
        }
        else if (view == binding.layoutBreed.zxq12Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq12 = 1;
            }
        }
        else if (view == binding.layoutBreed.zxq12No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxq12 = 0;
            }
        }

    }
    private void setZXQOneCheckFalse(){
        binding.layoutBreed.zxqRg1.clearCheck();
        binding.layoutBreed.zxqRg2.clearCheck();
        binding.layoutBreed.zxqRg3.clearCheck();
        binding.layoutBreed.zxqRg4.clearCheck();
        binding.layoutBreed.zxqRg5.clearCheck();
        binding.layoutBreed.zxqRg6.clearCheck();
        binding.layoutBreed.zxqRg7.clearCheck();
        binding.layoutBreed.zxqRg8.clearCheck();
        binding.layoutBreed.zxqRg9.clearCheck();
        binding.layoutBreed.zxqRg10.clearCheck();
        binding.layoutBreed.zxqRg11.clearCheck();
        binding.layoutBreed.zxqRg12.clearCheck();


    }
    /****
     * 种畜禽领域  (成都市畜禽养殖场执法检查记录表)
     * @param view
     */
    public void onRadioButtonClickedZXQTwo(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutBreedTwo.zxqTwo1Ok) {
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo1 = 1;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo1No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo1 = 0;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo2Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo2 = 1;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo2No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo2 = 0;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo3Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo3 = 1;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo3No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo3 = 0;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo4Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo4 = 1;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo4No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo4 = 0;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo5Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo5 = 1;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo5No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo5 = 0;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo6Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo6 = 1;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo6No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo6 = 0;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo7Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo7 = 1;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo7No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo7 = 0;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo8Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo8 = 1;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo8No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo8 = 0;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo9Ok){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo9 = 1;
            }
        }else if (view == binding.layoutBreedTwo.zxqTwo9No){
            if (isChecked) {
                mLivestockPoultryFieldsBean.zxqTwo9 = 1;
            }
        }
    }
    private void     setZXQTwoCheckFalse(){
        binding.layoutBreedTwo.zxqTwoRg1.clearCheck();
        binding.layoutBreedTwo.zxqTwoRg2.clearCheck();
        binding.layoutBreedTwo.zxqTwoRg3.clearCheck();
        binding.layoutBreedTwo.zxqTwoRg4.clearCheck();
        binding.layoutBreedTwo.zxqTwoRg5.clearCheck();
        binding.layoutBreedTwo.zxqTwoRg6.clearCheck();
        binding.layoutBreedTwo.zxqTwoRg7.clearCheck();
        binding.layoutBreedTwo.zxqTwoRg8.clearCheck();
        binding.layoutBreedTwo.zxqTwoRg9.clearCheck();
    }
    /****
     * 渔业水产领域  (成都市水产养殖场执法检查记录表)
     * @param view
     */
    public void onRadioButtonClickedFishOne(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutFishOne.fishOne1Ok) {
            if (isChecked) {
                mFisheryAreaBean.fishOne1 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne1No){
            if (isChecked) {
                mFisheryAreaBean.fishOne1 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne2Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne2 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne2No){
            if (isChecked) {
                mFisheryAreaBean.fishOne2 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne3Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne3 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne3No){
            if (isChecked) {
                mFisheryAreaBean.fishOne3 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne4Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne4 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne4No){
            if (isChecked) {
                mFisheryAreaBean.fishOne4 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne5Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne5 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne5No){
            if (isChecked) {
                mFisheryAreaBean.fishOne5 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne6Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne6 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne6No){
            if (isChecked) {
                mFisheryAreaBean.fishOne6 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne7Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne7 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne7No){
            if (isChecked) {
                mFisheryAreaBean.fishOne7 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne8Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne8 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne8No){
            if (isChecked) {
                mFisheryAreaBean.fishOne8 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne9Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne9 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne9No){
            if (isChecked) {
                mFisheryAreaBean.fishOne9 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne10Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne10 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne10No){
            if (isChecked) {
                mFisheryAreaBean.fishOne10 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne11Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne11 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne11No){
            if (isChecked) {
                mFisheryAreaBean.fishOne11 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne12Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne12 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne12No){
            if (isChecked) {
                mFisheryAreaBean.fishOne12 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne13Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne13 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne13No){
            if (isChecked) {
                mFisheryAreaBean.fishOne13 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne14Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne14 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne14No){
            if (isChecked) {
                mFisheryAreaBean.fishOne14 = 0;
            }
        }else if (view == binding.layoutFishOne.fishOne15Ok){
            if (isChecked) {
                mFisheryAreaBean.fishOne15 = 1;
            }
        }else if (view == binding.layoutFishOne.fishOne15No){
            if (isChecked) {
                mFisheryAreaBean.fishOne15 = 0;
            }
        }
    }
    private void   setFishOneCheckFalse(){
        binding.layoutFishOne.fishOneRg1.clearCheck();
        binding.layoutFishOne.fishOneRg2.clearCheck();
        binding.layoutFishOne.fishOneRg3.clearCheck();
        binding.layoutFishOne.fishOneRg4.clearCheck();
        binding.layoutFishOne.fishOneRg5.clearCheck();
        binding.layoutFishOne.fishOneRg6.clearCheck();
        binding.layoutFishOne.fishOneRg7.clearCheck();
        binding.layoutFishOne.fishOneRg8.clearCheck();
        binding.layoutFishOne.fishOneRg9.clearCheck();
        binding.layoutFishOne.fishOneRg10.clearCheck();
        binding.layoutFishOne.fishOneRg11.clearCheck();
        binding.layoutFishOne.fishOneRg12.clearCheck();
        binding.layoutFishOne.fishOneRg13.clearCheck();
        binding.layoutFishOne.fishOneRg14.clearCheck();
        binding.layoutFishOne.fishOneRg15.clearCheck();

    }
    public void onRadioButtonClickedFishTwo(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutFishTwo.fishTwo1Ok) {
            if (isChecked) {
                mFisheryAreaBean.fishTwo1 = 1;
            }
        }else if (view == binding.layoutFishTwo.fishTwo1No){
            if (isChecked) {
                mFisheryAreaBean.fishTwo1 = 0;
            }
        }else if (view == binding.layoutFishTwo.fishTwo2Ok){
            if (isChecked) {
                mFisheryAreaBean.fishTwo2 = 1;
            }
        }else if (view == binding.layoutFishTwo.fishTwo2No){
            if (isChecked) {
                mFisheryAreaBean.fishTwo2 = 0;
            }
        }else if (view == binding.layoutFishTwo.fishTwo3Ok){
            if (isChecked) {
                mFisheryAreaBean.fishTwo3 = 1;
            }
        }else if (view == binding.layoutFishTwo.fishTwo3No){
            if (isChecked) {
                mFisheryAreaBean.fishTwo3 = 0;
            }
        }else if (view == binding.layoutFishTwo.fishTwo4Ok){
            if (isChecked) {
                mFisheryAreaBean.fishTwo4 = 1;
            }
        }else if (view == binding.layoutFishTwo.fishTwo4No){
            if (isChecked) {
                mFisheryAreaBean.fishTwo4 = 0;
            }
        }else if (view == binding.layoutFishTwo.fishTwo5Ok){
            if (isChecked) {
                mFisheryAreaBean.fishTwo5 = 1;
            }
        }else if (view == binding.layoutFishTwo.fishTwo5No){
            if (isChecked) {
                mFisheryAreaBean.fishTwo5 = 0;
            }
        }else if (view == binding.layoutFishTwo.fishTwo6Ok){
            if (isChecked) {
                mFisheryAreaBean.fishTwo6 = 1;
            }
        }else if (view == binding.layoutFishTwo.fishTwo6No){
            if (isChecked) {
                mFisheryAreaBean.fishTwo6 = 0;
            }
        }else if (view == binding.layoutFishTwo.fishTwo7Ok){
            if (isChecked) {
                mFisheryAreaBean.fishTwo7 = 1;
            }
        }else if (view == binding.layoutFishTwo.fishTwo7No){
            if (isChecked) {
                mFisheryAreaBean.fishTwo7 = 0;
            }
        }else if (view == binding.layoutFishTwo.fishTwo8Ok){
            if (isChecked) {
                mFisheryAreaBean.fishTwo8 = 1;
            }
        }else if (view == binding.layoutFishTwo.fishTwo8No){
            if (isChecked) {
                mFisheryAreaBean.fishTwo8 = 0;
            }
        }else if (view == binding.layoutFishTwo.fishTwo9Ok){
            if (isChecked) {
                mFisheryAreaBean.fishTwo9 = 1;
            }
        }else if (view == binding.layoutFishTwo.fishTwo9No){
            if (isChecked) {
                mFisheryAreaBean.fishTwo9 = 0;
            }
        }
    }
    private void setFishTwoCheckFalse(){
        binding.layoutFishTwo.fishTwoRg1.clearCheck();
        binding.layoutFishTwo.fishTwoRg2.clearCheck();
        binding.layoutFishTwo.fishTwoRg3.clearCheck();
        binding.layoutFishTwo.fishTwoRg4.clearCheck();
        binding.layoutFishTwo.fishTwoRg5.clearCheck();
        binding.layoutFishTwo.fishTwoRg6.clearCheck();
        binding.layoutFishTwo.fishTwoRg7.clearCheck();
        binding.layoutFishTwo.fishTwoRg8.clearCheck();
        binding.layoutFishTwo.fishTwoRg9.clearCheck();
    }
    public void onRadioButtonClickedFishThree(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.layoutFishThree.fishThree1Ok) {
            if (isChecked) {
                mFisheryAreaBean.fishThree1 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree1No){
            if (isChecked) {
                mFisheryAreaBean.fishThree1 = 0;
            }
        }else if (view == binding.layoutFishThree.fishThree2Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree2 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree2No){
            if (isChecked) {
                mFisheryAreaBean.fishThree2 = 0;
            }
        }else if (view == binding.layoutFishThree.fishThree3Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree3 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree3No){
            if (isChecked) {
                mFisheryAreaBean.fishThree3 = 0;
            }
        }else if (view == binding.layoutFishThree.fishThree4Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree4 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree4No){
            if (isChecked) {
                mFisheryAreaBean.fishThree4 = 0;
            }
        }else if (view == binding.layoutFishThree.fishThree5Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree5 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree5No){
            if (isChecked) {
                mFisheryAreaBean.fishThree5 = 0;
            }
        }else if (view == binding.layoutFishThree.fishThree6Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree6 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree6No){
            if (isChecked) {
                mFisheryAreaBean.fishThree6 = 0;
            }
        }else if (view == binding.layoutFishThree.fishThree7Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree7 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree7No){
            if (isChecked) {
                mFisheryAreaBean.fishThree7 = 0;
            }
        }else if (view == binding.layoutFishThree.fishThree8Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree8 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree8No){
            if (isChecked) {
                mFisheryAreaBean.fishThree8 = 0;
            }
        }else if (view == binding.layoutFishThree.fishThree9Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree9 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree9No){
            if (isChecked) {
                mFisheryAreaBean.fishThree9 = 0;
            }
        }
        else if (view == binding.layoutFishThree.fishThree10Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree10 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree10No){
            if (isChecked) {
                mFisheryAreaBean.fishThree10 = 0;
            }
        }  else if (view == binding.layoutFishThree.fishThree11Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree11 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree11No){
            if (isChecked) {
                mFisheryAreaBean.fishThree11 = 0;
            }
        }  else if (view == binding.layoutFishThree.fishThree12Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree12 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree12No){
            if (isChecked) {
                mFisheryAreaBean.fishThree12 = 0;
            }
        }  else if (view == binding.layoutFishThree.fishThree13Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree13 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree13No){
            if (isChecked) {
                mFisheryAreaBean.fishThree13 = 0;
            }
        }  else if (view == binding.layoutFishThree.fishThree14Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree14 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree14No){
            if (isChecked) {
                mFisheryAreaBean.fishThree14 = 0;
            }
        }  else if (view == binding.layoutFishThree.fishThree15Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree15 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree15No){
            if (isChecked) {
                mFisheryAreaBean.fishThree15 = 0;
            }
        }  else if (view == binding.layoutFishThree.fishThree16Ok){
            if (isChecked) {
                mFisheryAreaBean.fishThree16 = 1;
            }
        }else if (view == binding.layoutFishThree.fishThree16No){
            if (isChecked) {
                mFisheryAreaBean.fishThree16 = 0;
            }
        }
    }
    private void setFishThreeCheckFalse(){
        binding.layoutFishThree.fishThreeRg1.clearCheck();
        binding.layoutFishThree.fishThreeRg2.clearCheck();
        binding.layoutFishThree.fishThreeRg3.clearCheck();
        binding.layoutFishThree.fishThreeRg4.clearCheck();
        binding.layoutFishThree.fishThreeRg5.clearCheck();
        binding.layoutFishThree.fishThreeRg6.clearCheck();
        binding.layoutFishThree.fishThreeRg7.clearCheck();
        binding.layoutFishThree.fishThreeRg8.clearCheck();
        binding.layoutFishThree.fishThreeRg9.clearCheck();
        binding.layoutFishThree.fishThreeRg10.clearCheck();
        binding.layoutFishThree.fishThreeRg11.clearCheck();
        binding.layoutFishThree.fishThreeRg12.clearCheck();
        binding.layoutFishThree.fishThreeRg13.clearCheck();
        binding.layoutFishThree.fishThreeRg14.clearCheck();
        binding.layoutFishThree.fishThreeRg15.clearCheck();
        binding.layoutFishThree.fishThreeRg16.clearCheck();
    }
    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(LawReportActivity.this);
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
