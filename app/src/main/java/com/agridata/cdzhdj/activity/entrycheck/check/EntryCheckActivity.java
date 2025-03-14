package com.agridata.cdzhdj.activity.entrycheck.check;

import static com.agridata.cdzhdj.base.MyApplication.getContext;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.data.entrycheck.IsArrivedReportData;
import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.AddressSP;
import com.agridata.cdzhdj.SPUtil.IsCheckDZHSp;
import com.agridata.cdzhdj.SPUtil.SlaughterHouseSp;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.entrycheck.CheckEgImgAdapter;
import com.agridata.cdzhdj.activity.entrycheck.CheckValueActivity;
import com.agridata.cdzhdj.activity.entrycheck.ble.lowble.LowBleEarTagActivity;
import com.agridata.cdzhdj.activity.entrycheck.ble.tagreader.TagReaderActivity;
import com.agridata.cdzhdj.activity.entrycheck.details.CertNoDetailsActivity;
import com.agridata.cdzhdj.activity.adapter.onDelListener;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.CheckInfoData;
import com.agridata.cdzhdj.data.Status2Bean;
import com.agridata.cdzhdj.data.entrycheck.CheckPointCertABean;
import com.agridata.cdzhdj.data.entrycheck.DaiZaiHouseBean;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckABean;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckBBean;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckDZHBean;
import com.agridata.cdzhdj.data.entrycheck.IsCheckDZHBean;
import com.agridata.cdzhdj.data.entrycheck.SlaughterHouseBean;
import com.agridata.cdzhdj.data.entrycheck.StaffListBean;
import com.agridata.cdzhdj.data.entrycheck.UpECETBean;
import com.agridata.cdzhdj.data.entrycheck.UpdataAnimalABean;
import com.agridata.cdzhdj.data.entrycheck.UpdataAnimalBBean;
import com.agridata.cdzhdj.data.logbean.LogData;
import com.agridata.cdzhdj.utils.ActivityManager;
import com.agridata.cdzhdj.utils.AppUtil;
import com.agridata.cdzhdj.utils.GlideUtils;
import com.agridata.cdzhdj.utils.ImageLoader;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.utils.TextStyleUtil;
import com.agridata.cdzhdj.data.entrycheck.CarNumData;
import com.agridata.cdzhdj.data.entrycheck.EarTagData;
import com.agridata.cdzhdj.databinding.ActivityEntryCheckBinding;
import com.agridata.cdzhdj.utils.GlideEngine;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.agridata.cdzhdj.utils.PictureSelectorUtils;
import com.agridata.cdzhdj.utils.RecognizeService;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.utils.WaterMaskUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.FullyGridLayoutManager;
import com.agridata.cdzhdj.view.bottomPopupDialog.BottomPopupDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.agridata.util.DateTimeUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.luck.picture.lib.basic.PictureSelectionCameraModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.PictureFileUtils;
import com.lzx.utils.RxToast;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-26 15:20.
 * @Description :描述 入场查验
 */
public class EntryCheckActivity extends BaseActivity<ActivityEntryCheckBinding> implements View.OnClickListener, onDelListener {
    private final static String TAG = "EntryCheckActivity------》";
    private CheckEgImgAdapter checkEgImgAdapter;
    private List<LocalMedia> mCheckEgImgList;
    private CustomLoadingDialog mLoadingDialog;
    private ImageEngine imageEngine;
    private String CertType;
    private boolean isAB;
    private EntryCheckBBean entryCheckBBean;


    private String OtherProvincesCertPhoto;
    private List<LocalMedia> selectList = new ArrayList<>();


    private int type = 0;//抽查是否合格
    private int CheckType = 0;//验证耳标方式
    private int photoType = 0;
    private boolean upLoadCarImg = false;
    private int maxSelectNum;

    private List<String> imageFiles = new ArrayList<>();

    private CheckInfoData checkInfoData;
    private int counts;
    private String earTagJson;


    private int endTimesType = -1;
    private int endAddressType = -1;
    private int carType = -1;
    private int designatedChannelType = -1;
    private int animalType = -1;
    private int earTagsType = -1;

    private int TakeCarNumType = -1;
    private boolean hasGotToken = false;
    private String CarHead;
    private String ImgPics;
    private final UpECETBean upECETBean = new UpECETBean();

    private boolean AgainCheck;

    private String CheckMid;

    private EntryCheckABean entryCheckABean;
    private String[] split;
    private String SlaughterID;
    private String SlaughterName;

    private SlaughterHouseBean.Result slaughterHouseBean;


    private String[] items;
    private boolean[] isCheck;
    private String[] stringsID;
    private int daizaiNum = 0;
    private List<StaffListBean> staffLists = new ArrayList<>();

    private AlertDialog alertDialog;

    private boolean isDZHData;


    private List<EntryCheckDZHBean> upDateStaffList = new ArrayList<>();

    private boolean mIsZNY;

    private boolean mIsNiu;
    private String mGFSYMid;

    private static String addressName;


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private int rI2;//起运地是成都的

    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context, String animalType, String SlaughterID, String SlaughterName) {
        Intent intent = new Intent(context, EntryCheckActivity.class);
        Bundle data = new Bundle();
        data.putSerializable("animalType", animalType);
        data.putSerializable("SlaughterID", SlaughterID);
        data.putSerializable("SlaughterName", SlaughterName);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    /**
     * 获取上一级所传过来的值  去判断加载哪一个Url
     */
    private void getArguments() {
        Bundle bundle = getIntent().getBundleExtra("data");
        CertType = bundle.getString("animalType");
        SlaughterID = bundle.getString("SlaughterID");
        SlaughterName = bundle.getString("SlaughterName");
        if (CertType.equals("A")) {
            isAB = true;
            binding.certTypeLl.setVisibility(View.VISIBLE);
        } else {
            binding.certTypeLl.setVisibility(View.GONE);
            isAB = false;
        }
        binding.certTypeTipsTv.setText("请输入动物" + CertType + "证检疫证号进行查验。");
        LogUtil.d(TAG, "SlaughterID" + SlaughterID);

        getSlaughterHouseDetails();
    }


    private void getSlaughterHouseDetails() {
        HttpRequest.getSlaughterHouseDetails(EntryCheckActivity.this, SlaughterID, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, SlaughterHouseBean content) {
                if (content.status == 0) {
                    slaughterHouseBean = content.result;
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }

    @Override
    protected ActivityEntryCheckBinding getBinding() {
        return ActivityEntryCheckBinding.inflate(getLayoutInflater());
    }

    private void getPermissions() {
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.MANAGE_EXTERNAL_STORAGE).permission(Permission.ACCESS_FINE_LOCATION).permission(Permission.ACCESS_COARSE_LOCATION)
                // 申请多个权限
                .permission(Permission.Group.BLUETOOTH).request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            initGaoDe();
                        } else {
                            Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, "被永久拒绝授权，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(EntryCheckActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, "获取权限失败"));
                        }
                    }
                });
    }

    @Override
    protected void initView() {
        initLoadingView();
        getPermissions();
        getArguments();
        initToken();//获取ocr token

        checkInfoData = new CheckInfoData();
        imageEngine = GlideEngine.createGlideEngine(); //1303298784          51000249036
        //binding.searchQuarantineEt.setText("1303298784"); //6100000200
        binding.titlebarLeft.setOnClickListener(v -> finish());
        mCheckEgImgList = new ArrayList<>();
        binding.daizaiEt.setOnClickListener(this);

        binding.certDetailsTv.setOnClickListener(this);
        binding.spotCheckEartagBtn.setOnClickListener(this);

        binding.inquireBtn.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(binding.searchQuarantineEt.getText().toString())) {
                // 截取前两位
                String firstTwoChars = binding.searchQuarantineEt.getText().toString().substring(0, 2);
                if (!isAB) {
                    if (!firstTwoChars.equals("51")) {
                        Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "请输入正确的检疫证号在进行查询"));
                        return;
                    }
                    showLoading("查询B证中...");
                    getECAB();//B证
                } else {
                    if (firstTwoChars.equals("51")) {
                        Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "请输入正确的检疫证号在进行查询"));
                        return;
                    }
                    showLoading("查询A证中...");
                    getECAA();//A证
                }
            } else {
                Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "请输入检疫证号在进行查询~"));
            }
        });
        //TODO: 2022/3/1 ab 都需要指定通道   B + 默认B  A显示是否
        if ("B".equals(CertType)) {
            binding.designatedChannelB.setVisibility(View.VISIBLE);
            binding.designatedChannelB.setChecked(true);
            binding.designatedChannelB.setClickable(false);
            binding.designatedChannelYesRb.setClickable(false);
            binding.designatedChannelNoRb.setClickable(false);
        } else {
            binding.designatedChannelB.setVisibility(View.GONE);
            binding.designatedChannelYesRb.setChecked(true);
            designatedChannelType = 1;
        }
        binding.summitBtn.setOnClickListener(this);
        binding.upCarIvBtn.setOnClickListener(this);


        binding.certTypeBtn.setOnClickListener(view -> {
            PictureSelector.create(this).openCamera(SelectMimeType.ofImage()).setCompressEngine(PictureSelectorUtils.getCompressFileEngine())//压缩引擎
                    .forResult(new OnResultCallbackListener<>() {
                        @Override
                        public void onResult(ArrayList<LocalMedia> result) {
                            String compressPath = result.get(0).getCompressPath();
                            upLoadImg(compressPath, "TAKE_CERT_A_IMG", "");
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        });

    }

    private void initToken() {
        OCR.getInstance(getApplicationContext()).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
                Log.d("lzx----》", "token" + token.toString());
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
            }
        }, getApplicationContext(), "LmuS31fNhIwQkOktjqVesrW0", "qIZY7tAqwQQSPyZODeEv1TFPeGQQIbIN");

    }

    @Override
    protected void initDatas() {
    }

    /*
     * 设置radio的点击事件，当点击的时候显示文字
     */
    public void onRadioButtonClicked(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.endTimesYesRb) {
            if (isChecked) {
                endTimesType = 1;
            }
        } else if (view == binding.endTimesNoRb) {
            if (isChecked) {
                endTimesType = 0;
            }
        } else if (view == binding.endAddressYesRb) {
            if (isChecked) {
                endAddressType = 1;
            }
        } else if (view == binding.endAddressNoRb) {
            if (isChecked) {
                endAddressType = 0;
            }
        } else if (view == binding.carYesRb) {
            if (isChecked) {
                carType = 1;
            }
        } else if (view == binding.carNoRb) {
            if (isChecked) {
                carType = 0;
            }
        } else if (view == binding.designatedChannelYesRb) {
            if (isChecked) {
                designatedChannelType = 1;
            }
        } else if (view == binding.designatedChannelNoRb) {
            if (isChecked) {
                designatedChannelType = 0;
            }
        } else if (view == binding.animalCountYesRb) {
            if (isChecked) {
                animalType = 1;
            }
        } else if (view == binding.animalCountNoRb) {
            if (isChecked) {
                animalType = 0;
            }
        } else if (view == binding.eartagsYesRb) {
            if (isChecked) {
                earTagsType = 1;
            }
        } else if (view == binding.eartagsNoRb) {
            if (isChecked) {
                earTagsType = 0;
            }
        } else if (view == binding.eartagsNoCheckRb) {
            if (isChecked) {
                earTagsType = 2;
            }
        }
    }

    private void getECAB() {
        HttpRequest.getECAB(EntryCheckActivity.this, binding.searchQuarantineEt.getText().toString().trim(), new CallBackLis<>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, EntryCheckBBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    hideLoading();
                    if (content.result != null) {
                        if (content.result.status.key == 1704) {
                            binding.noDataLl.setVisibility(View.VISIBLE);
                            binding.neScView.setVisibility(View.GONE);
                            showRecycleDialog("该证号已作废");
                            return;
                        }

                        if (content.result.status.key == 1702) {
                            binding.noDataLl.setVisibility(View.VISIBLE);
                            binding.neScView.setVisibility(View.GONE);
                            showRecycleDialog("该证号已回收");
                            return;
                        }
                        //TODO:入场查验，屠宰用途，屠宰场不对，不让查出来。饲养用途的，能够查，能入场，结果不合格. 2023-05-16
                        type = 0;
                        enableRadioGroup(binding.carNoRg);
                        enableRadioGroup(binding.timeRg);
                        enableRadioGroup(binding.addressRg);
                        LogUtil.d("lzx---------》账号", SlaughterID);
                        LogUtil.d("lzx---------》证", content.result.destinationPlaceCompany.mid);
                        if (content.result.animalType.name.contains("猪")

                                || content.result.animalType.name.contains("羊")) {
                            mIsZNY = true;
                            mIsNiu = false;
                            binding.eartagsNoCheckRb.setVisibility(View.GONE);
                        } else if (content.result.animalType.name.contains("牛")) {
                            mIsNiu = true;
                            mIsZNY = false;
                            binding.eartagsNoCheckRb.setVisibility(View.GONE);
                            binding.spotCheckEartagBtn.setVisibility(View.GONE);
                        } else {
                            mIsZNY = false;
                            mIsNiu = false;
                            earTagsType = 2;
                            binding.eartagsNoCheckRb.setChecked(true);
                            binding.spotCheckEartagBtn.setVisibility(View.GONE);
                        }
                        if(content.result.isRemedy==1 && content.result.earTags==null){
                            earTagsType = 1;
                            binding.eartagsYesRb.setChecked(true);
                        }
                        // 隐藏“noDataLl”布局
                        binding.noDataLl.setVisibility(View.GONE);
                        // 显示“neScView”布局
                        binding.neScView.setVisibility(View.VISIBLE);
                        // 如果“IsCheckDZHSp”中的“isCheckDZHSp”值为1
                        if (IsCheckDZHSp.getInstance().getIsCheckDZHSp() == 1) {
                            // 显示“dadaismLl”布局
                            binding.daizaiLl.setVisibility(View.VISIBLE);
                        }
                        entryCheckBBean = content;
                        if (!mIsZNY || mIsNiu) {
                            binding.spotCheckEartagBtn.setVisibility(View.GONE);//当不是猪牛羊时 无需进行耳标抽查
                        } else {
                            binding.spotCheckEartagBtn.setVisibility(View.VISIBLE);
                        }
                        CheckMid = content.result.mid;
                        setUIInfo(entryCheckABean, entryCheckBBean);
                        //官方兽医Mid
                        mGFSYMid = content.result.quarantineOfficer.mid;
                        if (!TextUtils.isEmpty(content.result.earTags)) {
                            getNeedNum();
                        }
                        if (content.result.usageType.key == 1202 && !SlaughterID.equals(content.result.destinationPlaceCompany.mid)) {
                            showErrorDialog();
                        }
                    } else {
                        binding.noDataLl.setVisibility(View.VISIBLE);
                        binding.neScView.setVisibility(View.GONE);
                        Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, "未查询到该检疫证号信息"));
                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, error));
            }
        });
    }

    private void getECAA() {
        HttpRequest.getECAA(EntryCheckActivity.this, binding.searchQuarantineEt.getText().toString().trim(), new CallBackLis<>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, EntryCheckABean content) {
                hideLoading();
                if (content.status == 0) {
                    if (content.result.status.key == 1704) {
                        binding.noDataLl.setVisibility(View.VISIBLE);
                        binding.neScView.setVisibility(View.GONE);
                        showRecycleDialog("该证号已作废");
                        return;
                    }

                    if (content.result.status.key == 1702) {
                        binding.noDataLl.setVisibility(View.VISIBLE);
                        binding.neScView.setVisibility(View.GONE);
                        showRecycleDialog("该证号已回收");
                        return;
                    }


                    type = 0;
                    if (content.result != null) {
                        enableRadioGroup(binding.timeRg);
                        if (content.result.animalType.name.contains("猪") || content.result.animalType.name.contains("羊")) {
                            mIsZNY = true;
                            mIsNiu = false;
                            binding.eartagsNoCheckRb.setVisibility(View.GONE);
                            binding.spotCheckEartagBtn.setVisibility(View.VISIBLE);
                        } else if (content.result.animalType.name.contains("牛")) {
                            mIsNiu = true;
                            mIsZNY = false;
                            binding.eartagsNoCheckRb.setVisibility(View.GONE);
                            binding.spotCheckEartagBtn.setVisibility(View.GONE);
                        } else {
                            binding.spotCheckEartagBtn.setVisibility(View.GONE);
                            mIsZNY = false;
                            mIsNiu = false;
                            binding.eartagsNoCheckRb.setVisibility(View.VISIBLE);
                            binding.eartagsNoCheckRb.setChecked(true);
                            earTagsType = 2;
                        }
                        if (IsCheckDZHSp.getInstance().getIsCheckDZHSp() == 1) {
                            // 显示“daizaiLl”布局
                            binding.daizaiLl.setVisibility(View.VISIBLE);
                        }
                        if (content.result.animalType.name.contains("猪")) {
                            if (content.result.usageType.key == 1202 && !SlaughterID.equals(content.result.destinationPlaceCompany.uUID)) {
                                showErrorDialog();
                            }
                            if (SlaughterID.equals(content.result.destinationPlaceCompany.uUID)) {
                                binding.endAddressYesRb.setChecked(true);
                                endAddressType = 1;
                            } else {
                                binding.endAddressNoRb.setChecked(true);
                                endAddressType = 0;
                            }
                            binding.endAddressYesRb.setClickable(false);
                        }

                        entryCheckABean = content;
                        CheckMid = content.result.mid;
                        binding.noDataLl.setVisibility(View.GONE);
                        binding.neScView.setVisibility(View.VISIBLE);
                        // getCertNoExists();
                        setUIInfo(entryCheckABean, entryCheckBBean);
                        //TODO:2024-05-31 新增 是否经过指定通道需求
                        if (content.result.checkResult.key == 2803) {
                            binding.designatedChannelNoRb.setChecked(true);
                        } else {
                            binding.designatedChannelYesRb.setChecked(true);
                        }
                        getNeedNum();
                    } else {
                        binding.noDataLl.setVisibility(View.VISIBLE);
                        binding.neScView.setVisibility(View.GONE);
                        Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, "未查询到该检疫证号信息"));

                    }
                } else {
                    Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, content.message));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, error));
            }
        });
    }


    private void getCheckPointCertA() {
        HttpRequest.getCheckPointCertA(EntryCheckActivity.this, Objects.requireNonNull(binding.searchQuarantineEt.getText()).toString().trim(), new CallBackLis<>() {
            @Override
            public void onSuccess(String method, CheckPointCertABean content) {
                if (!content.result.isEmpty()) {

                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });

    }

    @Override
    protected void OnEventMainThread() {
        super.OnEventMainThread();

        mRxManager.on("NO_ERATAG_INFO", o -> {
            boolean NO_ERATAG_INFO = (boolean) o;
            if (NO_ERATAG_INFO) {
                CheckType = 0;
            }
        });
        // 收到通知
        mRxManager.on("checkInfoData", o -> {
            checkInfoData = (CheckInfoData) o;

            if (checkInfoData != null) {
                LogUtil.d("lzx------->", "weChatInfo:" + checkInfoData.toString());
                //2 为不合格  1为合格
                type = checkInfoData.Type;

                counts = checkInfoData.Count;
                imageFiles.clear();
                ImgPics = "";
                if (!TextUtils.isEmpty(checkInfoData.imgInfo)) {
                    ImgPics = checkInfoData.imgInfo;
                    imageFiles = Arrays.asList(checkInfoData.imgInfo.split(","));
                }
                if (type == 2) {
                    binding.eartagsNoRb.setChecked(true);
                    binding.eartagsYesRb.setClickable(false);
                    earTagsType = 0;
                } else {
                    binding.eartagsYesRb.setChecked(true);
                    binding.eartagsNoRb.setClickable(false);
                    earTagsType = 1;
                }
                if (checkInfoData.EarTag != null && checkInfoData.EarTag.size() > 0 || !TextUtils.isEmpty(checkInfoData.errorEarTag)) {
                    //拼接耳标
                    CheckType = 1;
                    StringBuilder earTagString = new StringBuilder();
                    Collections.sort(checkInfoData.EarTag);
                    for (int i = 0; i < checkInfoData.EarTag.size(); i++) {
                        earTagString.append(checkInfoData.EarTag.get(i).trim() + (i == checkInfoData.EarTag.size() - 1 ? "" : ","));
                    }
                    EarTagData earTagData = new EarTagData();
                    if (!TextUtils.isEmpty(earTagString.toString())) {
                        earTagData.qualifiedEarTags = earTagString.toString();//合格
                    } else {
                        earTagData.qualifiedEarTags = "";//合格
                    }


                    earTagData.unqualifiedTags = checkInfoData.errorEarTag;//不合格

                    if (earTagData != null) {
                        earTagJson = GsonUtil.toJson(earTagData);
                        LogUtil.d("lzx-----》", " 查验耳标转的Json Info" + earTagJson);
                    }
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUIInfo(EntryCheckABean contentA, EntryCheckBBean content) {
        if (isAB) {
            EntryCheckABean.Result result = contentA.result;
            binding.transportNumberTv.setText(!TextUtils.isEmpty(result.transportNumber) ? result.transportNumber : "");
            if (!TextUtils.isEmpty(result.dateOfIssue)) {
                binding.endTimesYesRb.setClickable(false);
                binding.endTimesNoRb.setClickable(false);

                //TODO:判断开证时间+ 期限 是否超过当前时间  2024-06-04
                String name = result.validityDays.name;
                int i = StrUtil.convertChineseToNumber(name);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                try {
                    LocalDateTime issueTime = LocalDateTime.parse(result.dateOfIssue, formatter);
                    LocalDateTime threeDaysLater = issueTime.plusDays(i);
                    System.out.println("threeDaysLater" + threeDaysLater.format(formatter));
                    // 获取当前时间
                    LocalDateTime now = LocalDateTime.now();
                    System.out.println("now" + now.format(formatter));
                    // 判断是否超过当前时间
                    System.out.println("threeDaysLater.isAfter(now)" + threeDaysLater.isAfter(now));
                    if (threeDaysLater.isAfter(now)) {
                        System.out.println("证书有效期未过期");
                        binding.endTimesYesRb.setChecked(true);
                        endTimesType = 1;
                    } else {
                        System.out.println("证书已过期");
                        binding.endTimesNoRb.setChecked(true);
                        endTimesType = 0;
                    }
                } catch (Exception e) {
                    System.err.println("错误解析日期时间：" + e.getMessage());
                    binding.endTimesYesRb.setChecked(true);
                    endTimesType = 1;
                }
            }
            binding.certTypeTv.setText(CertType);
            binding.certNumTv.setText(result.factoryCode);
            binding.goodsOwnerTv.setText(result.owner);
            binding.shippersPhoneTv.setText(result.ownerTel);
            binding.animalTypeTv.setText(result.animalType.name);
            binding.startAddressTv.setText(result.startingPlaceRegion.regionFullName + "[" + result.startingPlaceCompany.name + "]");
            binding.endAddressTv.setText(result.destinationPlaceRegion.regionFullName + "[" + result.destinationPlaceCompany.name + "]");
            binding.timesTv.setText(result.dateOfIssue);//开征时间
            binding.remarkTv.setText(result.remark);//备注
            binding.eartagsTv.setText(result.earTags);
            binding.certAmountTv.setText("[" + result.amount + result.unit.name + "]");


            upECETBean.Owner = result.owner;
            upECETBean.AnimalName = result.animalType.name;
            upECETBean.PlaceDepartureName = result.startingPlaceRegion.regionFullName;
            upECETBean.CarNumber = result.transportNumber;

        } else {
            EntryCheckBBean.Result result = content.result;
            binding.transportNumberTv.setText(!TextUtils.isEmpty(result.transportNumber) ? result.transportNumber : "");
            binding.certAmountTv.setText("[" + result.amount + result.unit.name + "]");
            if (SlaughterID.equals(result.destinationPlaceCompany.mid)) {
                binding.endAddressYesRb.setChecked(true);
                endAddressType = 1;
            } else {
                binding.endAddressNoRb.setChecked(true);
                endAddressType = 0;
            }
            binding.endAddressYesRb.setClickable(false);

            if (!TextUtils.isEmpty(result.dateOfIssue)) {
                binding.endTimesYesRb.setClickable(false);
                binding.endTimesNoRb.setClickable(false);
                long times = DateTimeUtils.dateToLong(Objects.requireNonNull(DateTimeUtils.stringToDate(result.dateOfIssue, "yyyy-MM-dd HH:mm:ss")));
                boolean isDay = DateTimeUtils.isDay(times);
                if (!isDay) {//超过了
                    binding.endTimesYesRb.setChecked(true);
                    endTimesType = 1;
                } else { //未超过
                    binding.endTimesNoRb.setChecked(true);
                    endTimesType = 0;
                }
            }
            binding.certTypeTv.setText(CertType);
            binding.certNumTv.setText(result.factoryCode);
            binding.goodsOwnerTv.setText(result.owner.name);
            binding.shippersPhoneTv.setText(result.ownerTel);
            binding.animalTypeTv.setText(result.animalType.name);
            rI2 = result.startingPlaceRegion.rI2;
            binding.startAddressTv.setText(result.startingPlaceRegion.regionFullName + "[" + result.startingPlaceCompany.name + "]");
            binding.endAddressTv.setText(result.destinationPlaceRegion.regionFullName + "[" + result.destinationPlaceCompany.name + "]");
            binding.timesTv.setText(result.dateOfIssue);//开征时间
            binding.remarkTv.setText(result.remark);//备注
            binding.eartagsTv.setText(result.earTags);


        }


    }

    private void mCheckEgImgAdapter() {
        checkEgImgAdapter.setOnItemClickListener(new CheckEgImgAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ArrayList<LocalMedia> selectList = checkEgImgAdapter.getData();
                if (!selectList.isEmpty()) {
                    LocalMedia media = selectList.get(position);
                    String mimeType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(mimeType);
                    if (mediaType == SelectMimeType.TYPE_IMAGE) {
                        PictureSelector.create(EntryCheckActivity.this).openPreview().setSelectorUIStyle(PictureSelectorUtils.setStyle()).isPreviewFullScreenMode(false).setImageEngine(GlideEngine.createGlideEngine())

                                .setExternalPreviewEventListener(new OnExternalPreviewEventListener() {
                                    @Override
                                    public void onPreviewDelete(int position) {

                                    }

                                    @Override
                                    public boolean onLongPressDownload(LocalMedia media) {
                                        return false;
                                    }
                                }).startActivityPreview(position, false, selectList);
                    }
                }
            }

            @Override
            public void openPicture() {
                showPicDialog();
            }
        });
    }


    private void showPicDialog() {
        List<String> bottomDialogContents = new ArrayList<>();
        bottomDialogContents.add("拍照");
        // bottomDialogContents.add("相册选取");
        final BottomPopupDialog bottomPopupDialog = new BottomPopupDialog(this, bottomDialogContents);
        bottomPopupDialog.showCancelBtn(true);
        bottomPopupDialog.setTitleText("选择上传方式");
        bottomPopupDialog.setTitleColor(R.color.Red);
        bottomPopupDialog.setTitleSize(18);
        bottomPopupDialog.show();

        bottomPopupDialog.setCancelable(true);
        bottomPopupDialog.setOnItemClickListener((v, position) -> {
            bottomPopupDialog.dismiss();
            if (position == 0) {  //拍照
                TakeCarNumType = 1;
                getPermissionCream();
            } else if (position == 1) { //相册选取
                //OpenPicture();
            }
        });
        bottomPopupDialog.setOnDismissListener(DialogInterface::dismiss);
    }

    private void getPermissionCream() {
        XXPermissions.with(this).permission(Permission.CAMERA).request((permissions, all) -> {
            if (all) {
                TakePicturesAlone();
            }
        });
    }

    //单独拍照
    private void TakePicturesAlone() {
        // Take pictures alone
        PictureSelectionCameraModel cameraModel = PictureSelector.create(EntryCheckActivity.this).openCamera(SelectMimeType.ofImage())
                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())//裁剪引擎
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())//压缩引擎
                .isOriginalControl(true);
        forOnlyCameraResult(cameraModel);
    }

    private void forOnlyCameraResult(PictureSelectionCameraModel model) {
        model.forResultActivity(new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(ArrayList<LocalMedia> result) {
                if (TakeCarNumType == 0) {
                    analyticalSelectResults(result, "TAKE_CAR_NUM_IMG");
                } else {
                    analyticalSelectResults(result, "CHECK_EG_IMG");
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }


    private void analyticalSelectResults(ArrayList<LocalMedia> result, String typeName) {
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

            switch (typeName) {
                case "CHECK_EG_IMG"://死畜耳标照片
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.d(TAG, "media.getCompressPath() " + media.getCompressPath());
                            //checkEgImgAdapter.getData().addAll(result);
                            LogUtil.d(TAG, "result " + result.get(0).getCompressPath());
                            // checkEgImgAdapter.notifyDataSetChanged();
                            LogUtil.d(TAG, "media.getCompressPath() " + media.getCompressPath());
                        }
                    });
                    upLoadImg(media.getCompressPath(), "CHECK_EG_IMG", "");
                    break;

                case "TAKE_CAR_NUM_IMG":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.d(TAG, "media.getCompressPath() " + media.getCompressPath());
                            GlideUtils.createGlideEngine().loadRounderImage(EntryCheckActivity.this, media.getCompressPath(), binding.carIv, 22);
                        }
                    });
                    String realPath = media.getCompressPath();
                    new Handler().postDelayed(() -> {
                        if (isAB) {
                            if (!TextUtils.isEmpty(entryCheckABean.result.transportNumber)) {
                                showLoading("正在识别中...");
                                setCarNumInfo(realPath);
                            } else {
                                Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "请先查询检疫证号信息~"));
                            }
                        } else {
                            if (!TextUtils.isEmpty(entryCheckBBean.result.transportNumber)) {
                                showLoading("正在识别中...");
                                setCarNumInfo(realPath);
                            } else {
                                Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "请先查询检疫证号信息~"));
                            }
                        }
                    }, 1000);  //延迟10秒执行


                    //upLoadImg(media.getCompressPath(), "TAKE_CAR_NUM_IMG", realPath);
                    break;
                default:
                    break;
            }


        }
    }

    /**
     * filePath 上传图片
     *
     * @param filePath
     */
    private void upLoadImg(String filePath, String typeName, String path) {
        showLoading("正在上传中...");
        HttpRequest.upLoadImg(EntryCheckActivity.this, filePath, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d(TAG, "上传图片" + content.toString());
                if (content.Status == 0) {
                    Objects.requireNonNull(RxToast.success(EntryCheckActivity.this, "上传成功"));
                    hideLoading();
                    switch (typeName) {
                        case "CHECK_EG_IMG":
                            earTagsType = 1;
                            imageFiles.add(content.Result);
                            if (imageFiles.size() == Integer.parseInt(binding.needCountTv.getText().toString())) {
                                StringBuilder img = new StringBuilder();
                                for (int i = 0; i < imageFiles.size(); i++) {
                                    img.append(imageFiles.get(i).trim()).append(i == imageFiles.size() - 1 ? "" : ",");
                                }
                                ImgPics = img.toString();
                                checkInfoData.imgInfo = ImgPics;
                                checkInfoData.Type = 1;
                                binding.eartagsYesRb.setChecked(true);
                                binding.eartagsNoRb.setClickable(false);
                                LogUtil.i("Imgs-->" + ImgPics);
                                Objects.requireNonNull(RxToast.info(EntryCheckActivity.this, "已成功上传" + imageFiles.size() + "张耳标照片"));

                                checkEgImgAdapter.getData().clear();
                                for (String path : imageFiles) {
                                    LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + path, PictureMimeType.MIME_TYPE_JPEG);
                                    checkEgImgAdapter.getData().add(localMedia);
                                }
                                checkEgImgAdapter.notifyDataSetChanged();
                                type = 1;
                                photoType = 1;
                            } else {
                                int i = Integer.parseInt(binding.needCountTv.getText().toString());
                                int i1 = i - imageFiles.size();
                                Objects.requireNonNull(RxToast.info(EntryCheckActivity.this, "上传成功,您当前还需选择" + i1 + "照片"));
                                checkEgImgAdapter.getData().clear();
                                for (String path : imageFiles) {
                                    LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + path, PictureMimeType.MIME_TYPE_JPEG);
                                    checkEgImgAdapter.getData().add(localMedia);
                                }
                                checkEgImgAdapter.notifyDataSetChanged();
                            }
                            addXLog(1, 888, "");
                            break;
                        case "TAKE_CAR_NUM_IMG":
                            upLoadCarImg = true;
                            CarHead = content.Result;
                            GlideUtils.createGlideEngine().loadRounderImage(EntryCheckActivity.this, UrlUtils.pic_url + CarHead, binding.carIv, 22);
                            addXLog(1, 888, "");
                            break;

                        case "TAKE_CERT_A_IMG":
                            OtherProvincesCertPhoto = content.Result;
                            GlideUtils.createGlideEngine().loadRounderImage(EntryCheckActivity.this, UrlUtils.pic_url + OtherProvincesCertPhoto, binding.certTypeIv, 22);
                            addXLog(1, 999, "");
                            break;
                        default:
                            break;
                    }


                } else {
                    addXLog(2, 888, "Status不为0");
                    Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, content.Result.toString()));
                }

            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                addXLog(2, 888, error);
                Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, error));
            }
        });
    }

    private void setCarNumInfo(String fileName) {
        RecognizeService.recLicensePlate(getApplicationContext(), fileName, result -> {
            try {
                hideLoading();
                LogUtil.d(TAG, result.toString());
                if (result.contains("log_id")) {
                    CarNumData carNumData = GsonUtil.fromJson(result, CarNumData.class);
                    String number = carNumData.words_result.number;
                    if (!TextUtils.isEmpty(number)) {
                        binding.carNumTv.setVisibility(View.VISIBLE);
                        binding.carNumTv.setText("车牌号：" + number);
                    }
                    String CarNum = "";
                    if (isAB) {
                        CarNum = entryCheckABean.result.transportNumber;
                    } else {
                        CarNum = entryCheckBBean.result.transportNumber;
                    }
                    LogUtil.d("lzx----》", "全角转半角" + CarNum);
                    LogUtil.d("lzx----》", "是否相等" + CarNum.equals(number));
                    if (CarNum.equalsIgnoreCase(number)) {
                        carType = 1;
                        binding.carYesRb.setChecked(true);
                        Bitmap originalBitmap = ImageLoader.convertFileToBitmap(fileName);
                        Bitmap watermarkImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_sy_bg); // 替换为你的水印图片
                        Bitmap photoBitmap = addWatermarkToBitmap(originalBitmap, watermarkImage);
                        String fileNameSy = "IMG_" + new Date().getTime() + "car_image_entry" + ".png";
                        String animalImagesPath = WaterMaskUtil.saveQNext(photoBitmap, EntryCheckActivity.this, fileNameSy, 60);
                        showCarSure(1, animalImagesPath);
                    } else {
                        carType = 0;
                        showCarSure(0, "");
                        binding.carNoRb.setChecked(true);
                    }
                    binding.carYesRb.setClickable(false);
                    binding.carNoRb.setClickable(false);

                    LogUtil.d("lzx---》", "number识别返回" + number);
                    LogUtil.d("lzx---》", "识别返回" + result);
                } else {
                    binding.carNumTv.setText("车牌号：");
                    LogUtil.d(TAG, "车牌号 " + result);
                    runOnUiThread(() -> showErron(result));
                    //
                }

            } catch (Exception e) {
                e.printStackTrace(System.err);
            }


        });
    }


    public void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(this);
        this.mLoadingDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 显示加载框
     */

    private void showLoading(String tips) {
        if (this.mLoadingDialog != null && !this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.show();
            this.mLoadingDialog.setTitleVisibility(0);
            this.mLoadingDialog.setTitle(tips);
        }
    }

    /**
     * 隐藏 加载框
     */

    private void hideLoading() {
        if (this.mLoadingDialog != null && this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.hide();
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void onClick(View v) {
        if (v == binding.certDetailsTv) {
            if (isAB) {
                if (entryCheckABean == null) {

                    Objects.requireNonNull(RxToast.warning(this, "请先查询检疫证号，在点击查看详细信息"));
                } else {
                    CertNoDetailsActivity.start(this, entryCheckABean.result, CertType);
                }
            } else {
                if (entryCheckBBean == null) {
                    Objects.requireNonNull(RxToast.warning(this, "请先查询检疫证号，在点击查看详细信息"));
                } else {
                    CertNoDetailsActivity.start(this, entryCheckBBean.result, CertType);
                }
            }
        } else if (v == binding.spotCheckEartagBtn) {
            if (isAB) {
                if (TextUtils.isEmpty(entryCheckABean.result.earTags)) {
                    Objects.requireNonNull(RxToast.error(this, "当前检疫证号上耳标为空，无法进行耳标抽查。请重新查询"));
                    return;
                }
            } else {
                if (entryCheckBBean.result.isRemedy==1){
                    if (TextUtils.isEmpty(entryCheckBBean.result.earTags)) {
                        Objects.requireNonNull(RxToast.warning(this, "当前检疫证为补检证，未查询到耳标。无需进行耳标抽查，请直接提交数据"));
                        return;
                    }
                }else {
                    if (TextUtils.isEmpty(entryCheckBBean.result.earTags)) {
                        Objects.requireNonNull(RxToast.error(this, "当前检疫证号上耳标为空，无法进行耳标抽查。请重新查询"));
                        return;
                    }
                }

            }
            //为0时选择抽查类型
            if (type == 0) {
                showSpotCheckType();
            } else if (type == 1 || type == 2) {//抽查过了 去跳转页面展示信息
                if (isAB) {
                    CheckValueActivity.start(this, entryCheckABean.result.earTags, 2, checkInfoData);
                } else {
                    CheckValueActivity.start(this, entryCheckBBean.result.earTags, 2, checkInfoData);
                }
            }
        } else if (v == binding.upCarIvBtn) {


            if (isAB) {
                if (entryCheckABean == null) {
                    Objects.requireNonNull(RxToast.warning(this, "请先查询检疫证号~"));
                } else {
                    TakeCarNumType = 0;
                    getPermissionCream();
                }
            } else {
                if (entryCheckBBean == null) {
                    Objects.requireNonNull(RxToast.warning(this, "请先查询检疫证号~"));
                } else {
                    TakeCarNumType = 0;
                    getPermissionCream();
                }
            }

        } else if (v == binding.summitBtn) {
            if (checkInfo()) {
                //这里判断  如果合格 去判断 底部的6符合 是否有否 有否则为不合格
                if ("A".equals(CertType)) {
                    if (type == 1) {
                        if (endTimesType == 0 || endAddressType == 0 || carType == 0 || designatedChannelType == 0 || animalType == 0 || earTagsType == 0) {
                            type = 2;
                        }
                    } else {
                        if (endTimesType == 0 || endAddressType == 0 || carType == 0 || animalType == 0 || earTagsType == 0) {
                            type = 2;
                        } else {
                            type = 1;
                        }
                    }
                } else {
                    if (type == 1) {
                        if (endTimesType == 0 || endAddressType == 0 || carType == 0 || animalType == 0 || earTagsType == 0) {
                            type = 2;
                        }
                    } else {
                        if (endTimesType == 0 || endAddressType == 0 || carType == 0 || animalType == 0 || earTagsType == 0) {
                            type = 2;
                        } else {
                            type = 1;
                        }
                    }
                }

                //TODO:入场查验实际数量是否需要做判定，小于检疫证数量：保存时若入场实际数与检疫证数量不一致则提示，该批动物经查验与检疫证载明数量不一致，请核实！有取消和确认两个按钮。

                int normalNum = Integer.parseInt(binding.normalEt.getText().toString());
                if (isAB) {
                    if (normalNum != entryCheckABean.result.amount) {
                        showNormalNumErrorDialog();
                    } else {
                        //TODO: 入场查验保存时，若存在不合格的，弹出一个框：红色字体提醒“该批动物入场存在不合格情况，请仔细核对”，确认和取消按钮。  2024-08-07
                        if (type == 2) {
                            showCheckErrorDialog();
                        } else {
                            showLoading("数据提交中...");
                            binding.summitBtn.setClickable(false);
                            mUpECET();
                        }
                    }
                } else {
                    if (normalNum != entryCheckBBean.result.amount) {
                        showNormalNumErrorDialog();
                    } else {
                        //TODO: 入场查验保存时，若存在不合格的，弹出一个框：红色字体提醒“该批动物入场存在不合格情况，请仔细核对”，确认和取消按钮。  2024-08-07
                        if (type == 2) {
                            showCheckErrorDialog();
                        } else {
                            showLoading("数据提交中...");
                            binding.summitBtn.setClickable(false);
                            mUpECET();
                        }
                    }
                }
            }
        } else if (v == binding.daizaiEt
        ) {
            if (!isDZHData) {
                showLoading("代宰户加载中...");
                getDaiZaiHuList();
            } else {
                showDaiZaiDialog();
            }
        }
    }


    private void getDaiZaiHuList() {
        HttpRequest.getDaiZai(EntryCheckActivity.this, SlaughterID, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, DaiZaiHouseBean content) {
                hideLoading();
                if (content.code == 200) {
                    isDZHData = true;
                    setInfo(content.data);
                } else {
                    Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, content.message));

                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, error));
            }
        });
    }


    private void setInfo(List<DaiZaiHouseBean.Data> daiZaiHouseBeanList) {
        items = new String[daiZaiHouseBeanList.size()];
        stringsID = new String[daiZaiHouseBeanList.size()];
        isCheck = new boolean[daiZaiHouseBeanList.size()];
        for (int i = 0; i < daiZaiHouseBeanList.size(); i++) {
            items[i] = daiZaiHouseBeanList.get(i).BreederName;
            stringsID[i] = String.valueOf(daiZaiHouseBeanList.get(i).BreederId);
        }
        showDaiZaiDialog();
    }

    Map<Integer, String> map = new HashMap<>();
    List<String> stringList = new ArrayList<>();
    Map<Integer, String> mapDaiZaiHu = new HashMap<>();

    private void showDaiZaiDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("代宰户选择");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(items, isCheck, (dialogInterface, i, isChecked) -> {
            StaffListBean staffList = new StaffListBean();
            if (isChecked) {
                map.put(i, items[i].toString());
                isCheck[i] = true;
                LogUtil.d("lzx------>", items[i]);
                LogUtil.d("lzx------>", stringsID[i]);
                mapDaiZaiHu.put(i, stringsID[i]);

                staffList.SlaughterStaffID = Integer.parseInt(stringsID[i]);
                staffList.SlaughterStaffName = items[i];
                staffList.Amount = daizaiNum;
                staffLists.add(staffList);

            } else {
                map.remove(i);
                mapDaiZaiHu.remove(i);
                isCheck[i] = false;

                for (int x = 0; x < staffLists.size(); x++) {
                    StaffListBean staffListBean = staffLists.get(x);
                    if (staffListBean.SlaughterStaffID == Integer.parseInt(stringsID[i])) {
                        staffLists.remove(x); // 移除列表项
                        x--; // 更新索引，以防止跳过列表项
                    }
                }
                LogUtil.d("lzx---》", "staffLists-----" + staffLists.toString());
            }
        });
        alertBuilder.setPositiveButton("确定", (dialogInterface, i) -> {

            LogUtil.d("lzx---》", "staffLists" + staffLists.toString());
            int childCount = binding.addResultLl.getChildCount();
            if (childCount > 0 || staffLists.isEmpty()) {
                binding.addResultLl.removeAllViews();
            }

            for (int i1 = 0; i1 < staffLists.size(); i1++) {
                addViewItemDaiZaiHu(staffLists.get(i1).SlaughterStaffName, i1, staffLists.get(i1).SlaughterStaffID);

            }
            LogUtil.d("lzx---》", "staffLists" + staffLists.toString());


            String content = "";
            if (!map.isEmpty()) {
                stringList.clear();
                for (Map.Entry<Integer, String> entry : map.entrySet()) {
                    content += entry.getValue() + ",";
                    String value = entry.getValue();
                    stringList.add(value);
                    LogUtil.d("lzx---》", "stringList" + stringList.toString());
                    if (!TextUtils.isEmpty(content)) {
                        String substring = content.substring(0, content.length() - 1);
                        binding.daizaiEt.setText(substring + "");
                    }
                }
            } else {
                binding.daizaiEt.setText("");
                binding.daizaiEt.setHint("请选择代宰户");
            }
            alertDialog.dismiss();
        });
        alertDialog = alertBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_border_white));
        alertDialog.show();
        if (items.length > 7) {
            WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
            params.height = this.getWindowManager().getDefaultDisplay().getHeight();
            params.height = (int) (ScreenUtils.getScreenHeight(this) * 0.65);
            params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
            alertDialog.getWindow().setAttributes(params);
        }

    }

    private void addViewItemDaiZaiHu(String name, int pos, int id) {
        LinearLayout hotelEvaluateView = (LinearLayout) View.inflate(EntryCheckActivity.this, R.layout.item_add_daizaihu, null);
        //如果一个都没有，就添加一个
        TextView dai_name_tv = hotelEvaluateView.findViewById(R.id.dai_name_tv);
        EditText animal_count_et = hotelEvaluateView.findViewById(R.id.animal_count_et);
        TextView staff_id = hotelEvaluateView.findViewById(R.id.staff_id);
        staff_id.setText(id + "");
        dai_name_tv.setText(name);
        animal_count_et.setText("");
        binding.addResultLl.setTag(id);
        binding.addResultLl.addView(hotelEvaluateView);
        animal_count_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    daizaiNum = Integer.parseInt(s.toString());
                }
            }
        });


        LogUtil.d("lzx------>staffLists", staffLists.toString());
    }

    private void showSpotCheckType() {
        List<String> bottomDialogContents = new ArrayList<>();
        if (!isAB) {
            bottomDialogContents.add("BIO-TAG低频蓝牙(黑色)");
            bottomDialogContents.add("TagReader低频蓝牙(银色)");
        }
        bottomDialogContents.add("上传耳标图片");
        final BottomPopupDialog bottomPopupDialog = new BottomPopupDialog(this, bottomDialogContents);
        bottomPopupDialog.showCancelBtn(true);
        bottomPopupDialog.setTitleText("请选择抽查方式");
        bottomPopupDialog.setTitleColor(R.color.Red);
        bottomPopupDialog.setTitleSize(18);
        bottomPopupDialog.show();

        bottomPopupDialog.setCancelable(true);
        bottomPopupDialog.setOnItemClickListener((v, position) -> {
            bottomPopupDialog.dismiss();
            type = 0;
            if (isAB) {
                if (position == 0) {
                    selectList.clear();
                    binding.checkEartagImageLl.setVisibility(View.VISIBLE);
                    setImgTipsInfo();
                    CheckType = 2;
                }

            } else {
                if (position == 0) {
                    selectList.clear();
                    binding.checkEartagImageLl.setVisibility(View.GONE);
                    binding.checkEartagImageLl.setVisibility(View.GONE);
                    if (isAB) {
                        LowBleEarTagActivity.start(this, entryCheckABean.result.earTags, 1);
                    } else {
                        LowBleEarTagActivity.start(this, entryCheckBBean.result.earTags, 1);
                    }

                    CheckType = 1;
                } else if (position == 1) {
                    selectList.clear();
                    binding.checkEartagImageLl.setVisibility(View.GONE);
                    if (isAB) {
                        TagReaderActivity.start(this, entryCheckABean.result.earTags, 1);
                    } else {
                        TagReaderActivity.start(this, entryCheckBBean.result.earTags, 1);
                    }
                    CheckType = 1;
                } else {
                    selectList.clear();
                    binding.checkEartagImageLl.setVisibility(View.VISIBLE);
                    setImgTipsInfo();
                    CheckType = 2;
                }
            }

        });
        bottomPopupDialog.setOnDismissListener(DialogInterface::dismiss);
    }

    private void getNeedNum() {
        int animalAmount = 0;
        if (isAB) {
            animalAmount = entryCheckABean.result.amount;
        } else {
            split = entryCheckBBean.result.earTags.split((","));
            List<String> EarTagList = new ArrayList<>(Arrays.asList(split));
            animalAmount = EarTagList.size();
        }
        binding.eartagsCountTv.setText(animalAmount + "个");
        if (animalAmount > 10 && animalAmount < 40) {//大于10小于40  抽10
            binding.needCountTv.setText("10");
        } else if (animalAmount >= 40) { //大于等于40抽25%
            double mul = TextStyleUtil.mul(0.25, animalAmount);
            LogUtil.d("lzx---》", "mul" + mul);
            BigDecimal b = new BigDecimal(mul).setScale(0, BigDecimal.ROUND_CEILING);
            String s = b.toString();
            LogUtil.d("lzx---》", "s" + s);
            binding.needCountTv.setText(s);
        } else {//小于 等于 10抽 全部
            binding.needCountTv.setText(animalAmount + "");
        }
    }

    /**
     * 设置选中 耳标 为图片时得数据处理
     */
    private void setImgTipsInfo() {
        int animalAmount = 0;
        if (isAB) {
            animalAmount = entryCheckABean.result.amount;
        } else {
            split = entryCheckBBean.result.earTags.split((","));
            List<String> EarTagList = new ArrayList<>(Arrays.asList(split));
            animalAmount = EarTagList.size();
        }

        binding.eartagsCountTv.setText(animalAmount + "个");
        if (animalAmount > 10 && animalAmount < 40) {//大于10小于40  抽10
            binding.needCountTv.setText("10");
        } else if (animalAmount >= 40) { //大于等于40抽25%
            double mul = TextStyleUtil.mul(0.25, animalAmount);
            LogUtil.d("lzx---》", "mul" + mul);
            BigDecimal b = new BigDecimal(mul).setScale(0, BigDecimal.ROUND_CEILING);
            String s = b.toString();
            LogUtil.d("lzx---》", "s" + s);
            binding.needCountTv.setText(s);
        } else {//小于 等于 10抽 全部
            binding.needCountTv.setText(animalAmount + "");
        }
        maxSelectNum = Integer.parseInt(binding.needCountTv.getText().toString());
        LogUtil.d("lzx--->", "需要上传的图片数量" + maxSelectNum);

        FullyGridLayoutManager managerZhuangChe = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        binding.takePhotoRv.setLayoutManager(managerZhuangChe);
        binding.takePhotoRv.addItemDecoration(new GridSpacingItemDecoration(4, ScreenUtils.dip2px(this, 8), false));
        checkEgImgAdapter = new CheckEgImgAdapter(this, mCheckEgImgList);
        checkEgImgAdapter.setSelectMax(maxSelectNum);
        checkEgImgAdapter.setDel(this);
        binding.takePhotoRv.setAdapter(checkEgImgAdapter);
        mCheckEgImgAdapter();
    }


    private void showCarSure(int type, String path) {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = (TextView) view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);

        contentTv.setTextSize(16);
        if (type == 1) {
            contentTv.setText("识别的车牌号跟检疫证号车牌一致，运输车辆符合已勾选是");
        } else {
            contentTv.setText("识别的车牌号跟检疫证号车牌不一致，运输车辆符合已勾选否。请重新拍照识别");
        }

        cancelTv.setOnClickListener(view1 -> exitDialog.dismiss());

        confirmTv.setOnClickListener(view12 -> {
            exitDialog.dismiss();
            if (type == 1) {
                upLoadImg(path, "TAKE_CAR_NUM_IMG", "");
            }
        });
        if (ActivityManager.getInstance().isLiving(EntryCheckActivity.this)) {
            exitDialog.show();
        }
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }

    /**
     * 已经回收了 或者作废了
     */
    private void showErron(String title) {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = (TextView) view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        contentTv.setTextSize(16);
        contentTv.setText("识别失败，请重新识别");
        cancelTv.setOnClickListener(view1 -> exitDialog.dismiss());
        confirmTv.setOnClickListener(view12 -> exitDialog.dismiss());
        if (ActivityManager.getInstance().isLiving(EntryCheckActivity.this)) {
            exitDialog.show();
        }
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }

    private boolean checkInfo() {
        if (isAB) {
            if (entryCheckABean.result == null) {
                Objects.requireNonNull(RxToast.warning(this, "请您先查询检疫证号"));
                return false;
            }
        } else {
            if (entryCheckBBean.result == null) {
                Objects.requireNonNull(RxToast.warning(this, "请您先查询检疫证号"));
                return false;
            }
        }
        if (mIsZNY) {
            if (isAB){
                if (CheckType == 0) {
                    Objects.requireNonNull(RxToast.warning(this, "请您先进行耳标抽查"));
                    return false;
                }

                if (CheckType == 2) {
                    if (imageFiles.isEmpty()) {
                        Objects.requireNonNull(RxToast.warning(this, "请上传抽查耳标照片"));
                        return false;
                    }
                }
                if (CheckType == 2) {
                    if (imageFiles.size() != Integer.parseInt(binding.needCountTv.getText().toString())) {
                        Objects.requireNonNull(RxToast.warning(this, "请上传" + binding.needCountTv.getText().toString() + "张耳标抽查照片，当前已上传" + imageFiles.size() + "张照片，请继续上传"));
                        return false;
                    }
                }
            }else {
                if ( entryCheckBBean.result.isRemedy==0) {
                    if (CheckType == 0) {
                        Objects.requireNonNull(RxToast.warning(this, "请您先进行耳标抽查"));
                        return false;
                    }

                    if (CheckType == 2) {
                        if (imageFiles.isEmpty()) {
                            Objects.requireNonNull(RxToast.warning(this, "请上传抽查耳标照片"));
                            return false;
                        }
                    }
                    if (CheckType == 2) {
                        if (imageFiles.size() != Integer.parseInt(binding.needCountTv.getText().toString())) {
                            Objects.requireNonNull(RxToast.warning(this, "请上传" + binding.needCountTv.getText().toString() + "张耳标抽查照片，当前已上传" + imageFiles.size() + "张照片，请继续上传"));
                            return false;
                        }
                    }
                }
            }


        }

        if (!upLoadCarImg) {
            Objects.requireNonNull(RxToast.warning(this, "请您先上传车头带车牌号照片"));
            return false;
        }

        if (TextUtils.isEmpty(binding.normalEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "请输入实际数量"));
            return false;
        }
        if (TextUtils.isEmpty(binding.abnormalEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "请输入异常数量"));
            return false;
        }
        if (TextUtils.isEmpty(binding.deadEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "请输入死亡数量"));
            return false;
        }
        //todo:2024-08-16  实际数量不能大于检疫证数量
//        int normalNum = Integer.parseInt(binding.normalEt.getText().toString());
//        if (isAB) {
//            if (normalNum > entryCheckABean.result.amount) {
//                Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "实际数量不能大于检疫证数量，请重新输入"));
//                return false;
//            }
//        } else {
//            if (normalNum > entryCheckBBean.result.amount) {
//                Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "实际数量不能大于检疫证数量，请重新输入"));
//                return false;
//            }
//        }

        if (IsCheckDZHSp.getInstance().getIsCheckDZHSp() == 1) {
            if (TextUtils.isEmpty(binding.daizaiEt.getText().toString())) {
                Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "请您选择待宰户"));
                return false;
            }
            getStaffList();
            int total = 0;
            for (int i = 0; i < upDateStaffList.size(); i++) {
                String amount = upDateStaffList.get(i).Amount;
                // 检查代宰数量是否为空
                if (TextUtils.isEmpty(amount)) {
                    Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "代宰户：" + upDateStaffList.get(i).Name + " 代宰数量不能为空，请输入"));
                    return false;
                }
                // 尝试将Amount转为整数并累加
                try {
                    total += Integer.parseInt(amount);
                } catch (NumberFormatException e) {
                    // 格式不正确时给出提示
                    Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "代宰户：" + upDateStaffList.get(i).Name + " 代宰数量格式不正确，请输入有效的数字"))  ;
                    return false;
                }
            }
            LogUtil.d(TAG, "GOGOGOGOGOGOGOGOGOOGOG`111111");
            LogUtil.d(TAG, "total" + total);
            if (isAB) {
                if (entryCheckABean.result.amount < total) {
                    Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, "代宰户代宰数量总和不能大于动物头数"));
                    return false;
                }
            } else {
                if (entryCheckBBean.result.amount < total) {
                    Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, "代宰户代宰数量总和不能大于动物头数"));
                    return false;
                }
            }

        }
        if (isAB) {
            if (TextUtils.isEmpty(OtherProvincesCertPhoto)) {
                Objects.requireNonNull(RxToast.warning(this, "当前入场动物A证时，请上传外省检疫证明照片上传"));
                return false;
            }
        }
        if (endTimesType == -1) {
            Objects.requireNonNull(RxToast.warning(this, "请您先选中到达时间是否符合"));
            return false;
        }
        if (endAddressType == -1) {
            Objects.requireNonNull(RxToast.warning(this, "请您先选中到达地点是否符合"));
            return false;
        }
        if (carType == -1) {
            Objects.requireNonNull(RxToast.warning(this, "请您先选中运输车辆是否符合"));
            return false;
        }
        if ("A".equals(CertType)) {
            if (designatedChannelType == -1) {
                Objects.requireNonNull(RxToast.warning(this, "请您先选中跨省运输是否经指定通道"));
                return false;
            }
        }
        if (animalType == -1) {
            Objects.requireNonNull(RxToast.warning(this, "请您先选中动物数量是否符合"));
            return false;
        }

        if (earTagsType == -1) {
            Objects.requireNonNull(RxToast.warning(this, "请您先选中耳标号是否符合或不抽查"));
            return false;
        }
        return true;
    }

    private void getStaffList() {
        upDateStaffList.clear();
        LogUtil.d("lzx-------->", "子view数量" + binding.addResultLl.getChildCount() + "");
        for (int i = 0; i < binding.addResultLl.getChildCount(); i++) {
            View childAt = binding.addResultLl.getChildAt(i);
            TextView dai_name_tv = childAt.findViewById(R.id.dai_name_tv);
            EditText animal_count_et = childAt.findViewById(R.id.animal_count_et);
            TextView staff_id = childAt.findViewById(R.id.staff_id);
            EntryCheckDZHBean entryCheckDZHBean = new EntryCheckDZHBean();
            entryCheckDZHBean.BreederId = staff_id.getText().toString();
            entryCheckDZHBean.Name = dai_name_tv.getText().toString();
            entryCheckDZHBean.Amount = animal_count_et.getText().toString();
            entryCheckDZHBean.CertNo = Objects.requireNonNull(binding.searchQuarantineEt.getText()).toString();
            upDateStaffList.add(entryCheckDZHBean);
        }
    }

    private void setInfo() {

        upECETBean.SlaughterHouseID = SlaughterID;
        upECETBean.SlaughterHouseName = SlaughterName;
        if (AgainCheck) {
            upECETBean.Mid = CheckMid;
        }
        if (isAB) {
            EntryCheckABean.Result result = entryCheckABean.result;
            upECETBean.CertNo = result.factoryCode;//检疫证号
            upECETBean.OtherProvincesCertPhoto = OtherProvincesCertPhoto;
        } else {
            EntryCheckBBean.Result result = entryCheckBBean.result;
            upECETBean.CertNo = result.factoryCode;//检疫证号
        }

        upECETBean.CheckType = String.valueOf(CheckType);//查验类型


        upECETBean.Imgs = new ArrayList<>();//图片
        if (!TextUtils.isEmpty(ImgPics)) {
            String[] splitImgs = ImgPics.split(",");
            upECETBean.Imgs.addAll(Arrays.asList(splitImgs));
        }

        if (mIsZNY) {
            if (CheckType == 1) { //查验数量
                upECETBean.Counts = counts;
            } else {
                upECETBean.Counts = Integer.parseInt(binding.needCountTv.getText().toString().trim());
            }
        } else {
            if (isAB) {
                upECETBean.Counts = entryCheckABean.result.amount;
            } else {
                upECETBean.Counts = entryCheckBBean.result.amount;
            }

        }

        upECETBean.CarHead = CarHead;//车头照片

        upECETBean.CheckResult = type;//查验是否合格
        upECETBean.TimeIsPass = endTimesType;//到达时间是否符合
        upECETBean.AddressIsPass = endAddressType;//到达地是否符合
        upECETBean.CarIsPass = carType;//运输车辆是否符合
        if ("A".equals(CertType)) {
            upECETBean.RoadIsPass = designatedChannelType;//跨省运输是否指定通道
        } else {
            upECETBean.RoadIsPass = 1;//跨省运输是否指定通道
        }
        upECETBean.NumberIsPass = animalType;//动物数量是否符合
        upECETBean.EarTagIsPass = earTagsType;//耳标数量是否符合

        upECETBean.CheckTime = DateTimeUtils.getNowTimes();//耳标数量是否符合

        upECETBean.CheckUser = new UpECETBean.CheckUserBean();
        upECETBean.CheckUser.Key = UserDataSP.getInstance().getUserInfo().Result.userId;
        upECETBean.CheckUser.Name = UserDataSP.getInstance().getUserInfo().Result.name;
        if ("A".equals(CertType)) {
            upECETBean.CertType = "1";
        } else if ("B".equals(CertType)) {
            upECETBean.CertType = "2";
        }
        //upECETBean.CertType = CertType;
        upECETBean.EarTags = new ArrayList<>();
        if (checkInfoData.EarTag != null && !checkInfoData.EarTag.isEmpty()) {
            upECETBean.EarTags = checkInfoData.EarTag;
        }
        if (!TextUtils.isEmpty(checkInfoData.errorEarTag)) {
            upECETBean.ErrorEarTags = checkInfoData.errorEarTag;
        } else {
            upECETBean.ErrorEarTags = "";
        }
        upECETBean.ActualNumber = Integer.parseInt(binding.normalEt.getText().toString());
        upECETBean.AnomalyNumber = Integer.parseInt(binding.abnormalEt.getText().toString());
        upECETBean.DeathNumber = Integer.parseInt(binding.deadEt.getText().toString());
        if (isAB) {
            upECETBean.Owner = entryCheckABean.result.owner;
            upECETBean.AnimalName = entryCheckABean.result.animalType.name;
            upECETBean.PlaceDepartureName = entryCheckABean.result.startingPlaceRegion.regionFullName;
            upECETBean.CarNumber = entryCheckABean.result.transportNumber;
            upECETBean.CertAmount = String.valueOf(entryCheckABean.result.amount);
        } else {
            upECETBean.Owner = entryCheckBBean.result.owner.name;
            upECETBean.AnimalName = entryCheckBBean.result.animalType.name;
            upECETBean.PlaceDepartureName = entryCheckBBean.result.startingPlaceRegion.regionFullName;
            upECETBean.CarNumber = entryCheckBBean.result.transportNumber;
            upECETBean.CertAmount = String.valueOf(entryCheckBBean.result.amount);
        }
        //TODO:区划问题
        upECETBean.Region = new UpECETBean.RegionBean();
        if ((slaughterHouseBean.region != null)) {
            SlaughterHouseBean.Result.Region region = slaughterHouseBean.region;
            upECETBean.Region.id = region.iD;
            upECETBean.Region.RI1 = region.rI1;
            upECETBean.Region.RI2 = region.rI2;
            upECETBean.Region.RI3 = region.rI3;
            upECETBean.Region.RI4 = region.rI4;
            upECETBean.Region.RI5 = region.rI5;
            upECETBean.Region.RegionCode = region.regionCode;
            upECETBean.Region.RegionName = region.regionName;
            upECETBean.Region.RegionLevel = region.regionLevel;
            upECETBean.Region.RegionFullName = region.regionFullName;
            upECETBean.Region.RegionParentID = region.regionParentID;
        } else {
            SlaughterHouseBean.Result.Region slaughterHouseInfo = SlaughterHouseSp.getInstance().getSlaughterHouseInfo();
            upECETBean.Region.id = slaughterHouseInfo.iD;
            upECETBean.Region.RI1 = slaughterHouseInfo.rI1;
            upECETBean.Region.RI2 = slaughterHouseInfo.rI2;
            upECETBean.Region.RI3 = slaughterHouseInfo.rI3;
            upECETBean.Region.RI4 = slaughterHouseInfo.rI4;
            upECETBean.Region.RI5 = slaughterHouseInfo.rI5;
            upECETBean.Region.RegionCode = slaughterHouseInfo.regionCode;
            upECETBean.Region.RegionName = slaughterHouseInfo.regionName;
            upECETBean.Region.RegionLevel = slaughterHouseInfo.regionLevel;
            upECETBean.Region.RegionFullName = slaughterHouseInfo.regionFullName;
            upECETBean.Region.RegionParentID = slaughterHouseInfo.regionParentID;
        }
        upECETBean.AppVersion = AppUtil.getVersionName(this);


        LogUtil.d("lzx---->", upECETBean.toString());
    }


    private void mUpECET() {
        setInfo();
        HttpRequest.UpDataECET(EntryCheckActivity.this, upECETBean, new CallBackLis<StatusData>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.Status == 0) {
                    if (IsCheckDZHSp.getInstance().getIsCheckDZHSp() == 1) {
                        //SEPT 1---------->上传代宰户信息
                        //SEPT  2----------->更新动物证状态
                        if (!upDateStaffList.isEmpty()) {
                            for (int i = 0; i < upDateStaffList.size(); i++) {
                                upDateStaffList.get(i).CheckMid = content.Result.trim();
                            }
                            LogUtil.d("LZX----->", upDateStaffList.toString());
                            addDZHList();
                        } else {
                            Objects.requireNonNull(RxToast.warning(EntryCheckActivity.this, "代宰户为空~"));
                        }
                    } else {
                        hideLoading();
                        Objects.requireNonNull(RxToast.success(EntryCheckActivity.this, "入场查验成功~"));
                        if (isAB) {
                            UpDataAnimalA();
                        } else {
                            UpDataAnimalB();
                        }
                    }

                }
            }

            @SuppressLint("CheckResult")
            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, error));
            }
        });
    }


    private void addDZHList() {
        HttpRequest.AddCheckDZHList(EntryCheckActivity.this, upDateStaffList, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, IsCheckDZHBean content) {
                LogUtil.d("lzx---------》", content.toString());
                hideLoading();
                Objects.requireNonNull(RxToast.success(EntryCheckActivity.this, "入场查验成功~"));
                if (isAB) {
                    UpDataAnimalA();
                } else {
                    UpDataAnimalB();
                }
            }

            @SuppressLint("CheckResult")
            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, error));

            }
        });
    }


    private void UpDataAnimalB() {
        UpdataAnimalBBean updataAnimalBBean = new UpdataAnimalBBean();
        updataAnimalBBean.Mid = CheckMid;
        updataAnimalBBean.Recycling_People = new UpdataAnimalBBean.RecyclingPeopleBean();
        updataAnimalBBean.Recycling_People.key = UserDataSP.getInstance().getUserInfo().Result.userId;
        updataAnimalBBean.Recycling_People.Name = UserDataSP.getInstance().getUserInfo().Result.name;
        updataAnimalBBean.Status = new UpdataAnimalBBean.StatusBean();
        updataAnimalBBean.Status.key = "1702";
        updataAnimalBBean.Status.Name = "已回收";


        updataAnimalBBean.ActualNumber = binding.normalEt.getText().toString();
        updataAnimalBBean.AnomalyNumber = binding.abnormalEt.getText().toString();
        updataAnimalBBean.DeathNumber = binding.deadEt.getText().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String str = sdf.format(new Date());
        updataAnimalBBean.RecoveryTime = str;
        HttpRequest.UpDataAnimalBHuiShou(EntryCheckActivity.this, updataAnimalBBean, new CallBackLis<>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.Status == 0) {
                    // new Handler().postDelayed(() -> finish(), 2000);
                    sendMessageWX();
                }
            }

            @SuppressLint("CheckResult")
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, error));
            }
        });
    }

    private void UpDataAnimalA() {
        UpdataAnimalABean updataAnimalABean = new UpdataAnimalABean();
        updataAnimalABean.Mid = CheckMid;
        updataAnimalABean.Status = new UpdataAnimalABean.StatusBean();
        updataAnimalABean.Status.key = "1702";
        updataAnimalABean.Status.Name = "已回收";
        updataAnimalABean.CheckNormalCount = binding.normalEt.getText().toString();
        updataAnimalABean.CheckAbnormalCount = binding.abnormalEt.getText().toString();
        updataAnimalABean.CheckDeadCount = binding.deadEt.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String str = sdf.format(new Date());
        updataAnimalABean.RecoveryTime = str;
        HttpRequest.UpDataAnimalAHuiShou(EntryCheckActivity.this, updataAnimalABean, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.Status == 0) {
                    finish();
                }
            }

            @SuppressLint("CheckResult")
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, error));
            }
        });
    }

    private void sendMessageWX() {
        HttpRequest.sendWxMessage(EntryCheckActivity.this, mGFSYMid, "检疫证号[" + Objects.requireNonNull(binding.searchQuarantineEt.getText()).toString().trim() + "][入场查验][已回收]", "[" + entryCheckBBean.result.owner.name + "]" + "[" + entryCheckBBean.result.animalType.name + "]" + "[" + entryCheckBBean.result.amount + "]头/只" + "[" + upECETBean.CheckTime + "]", new CallBackLis<>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, Status2Bean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.code == 200) {
                    //B证起运地区划为成都市 841883  默认向启运地报告
                    if (!isAB) {
                        if (rI2 == 841883) {
                            setIsArrivedInfo();
                        } else {
                            finish();
                        }
                    } else {
                        finish();
                    }

                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, error));
            }
        });
    }

    private void setIsArrivedInfo() {
        IsArrivedReportData isArrivedReportData = new IsArrivedReportData();
        isArrivedReportData.Mid = CheckMid;
        isArrivedReportData.IsArrived = "1";
        isArrivedReportData.ArriveInfo = new IsArrivedReportData.IsArrivedReportPeople();
        isArrivedReportData.ArriveInfo.openId = "";
        isArrivedReportData.ArriveInfo.arrivedTime = DateTimeUtils.getNowTimes();
        isArrivedReportData.ArriveInfo.userMid = UserDataSP.getInstance().getUserInfo().Result.userId;
        isArrivedReportData.ArriveInfo.userName = UserDataSP.getInstance().getUserInfo().Result.name;
        HttpRequest.commitArriveInfo(EntryCheckActivity.this, isArrivedReportData, new CallBackLis<StatusData>() {
            @Override
            public void onSuccess(String method, StatusData content) {
                if (content.Status == 0) {
                    finish();
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(EntryCheckActivity.this, error));
            }
        });
    }


    private void showUpLoadSuccessDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_success_layout, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(exitDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        view.findViewById(R.id.title_tv);
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        LinearLayout sure_ll = view.findViewById(R.id.sure_ll);
        sure_ll.setOnClickListener(v -> {
            exitDialog.dismiss();
            finish();
        });
        confirmTv.setOnClickListener(view12 -> {

            exitDialog.dismiss();
            finish();
        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }

    /**
     * 已经提交过信息了
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showSubmittedDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(exitDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = (TextView) view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        contentTv.setText("您已经提交过入场查验数据，是否继续提交，如继续提交按照最后一次提交数据为准。");
        cancelTv.setOnClickListener(view1 -> {
            exitDialog.dismiss();
            finish();
        });

        confirmTv.setOnClickListener(view12 -> {
            exitDialog.dismiss();
            if (isAB) {//A証
                setUIInfo(entryCheckABean, entryCheckBBean);
            } else {
                setUIInfo(entryCheckABean, entryCheckBBean);
            }

            binding.spotCheckEartagBtn.setVisibility(View.VISIBLE);
        });
        if (ActivityManager.getInstance().isLiving(EntryCheckActivity.this)) {
            exitDialog.show();
        }
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }

    /**
     * 已经回收了 或者作废了
     */
    private void showRecycleDialog(String title) {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = (TextView) view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        SpannableStringBuilder style = new SpannableStringBuilder(title);
        style.setSpan(new ForegroundColorSpan(Color.RED), 3, 6, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        contentTv.setText(style);
        contentTv.setTextSize(16);
        cancelTv.setOnClickListener(view1 -> exitDialog.dismiss());

        confirmTv.setOnClickListener(view12 -> exitDialog.dismiss());
        if (ActivityManager.getInstance().isLiving(EntryCheckActivity.this)) {
            exitDialog.show();
        }
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }


    private void showNormalNumErrorDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(exitDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = view.findViewById(R.id.negative_tv);
        TextView confirmTv = view.findViewById(R.id.positive_tv);
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView titleTv = view.findViewById(R.id.title_tv);
        titleTv.setVisibility(View.GONE);
        titleTv.setText("查验数量");
        contentTv.setText("该批动物经查验与检疫证载明数量不一致，请核实！");
        cancelTv.setOnClickListener(view1 -> {
            exitDialog.dismiss();
        });

        confirmTv.setOnClickListener(view1 -> {
            exitDialog.dismiss();
            //TODO: 入场查验保存时，若存在不合格的，弹出一个框：红色字体提醒“该批动物入场存在不合格情况，请仔细核对”，确认和取消按钮。  2024-08-07
            if (type == 2) {
                showCheckErrorDialog();
            } else {
                showLoading("数据提交中...");
                binding.summitBtn.setClickable(false);
                mUpECET();
            }

        });
        if (ActivityManager.getInstance().isLiving(EntryCheckActivity.this)) {
            exitDialog.show();
        }
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }


    private void showErrorDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = (TextView) view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        contentTv.setText("当前票证为屠宰用途，到达地与当前账号不符。请输入正确票证");
        cancelTv.setOnClickListener(view1 -> {
            exitDialog.dismiss();
            finish();
        });

        confirmTv.setOnClickListener(view12 -> {
            exitDialog.dismiss();
        });
        if (ActivityManager.getInstance().isLiving(EntryCheckActivity.this)) {
            exitDialog.show();
        }
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }

    public void addXLog(int UploadStatus, int UploadType, String ErrorMessage) {
        LogData logData = new LogData();
        logData.UploadTime = DateTimeUtils.getNowTimes();
        logData.CollectionName = UserDataSP.getInstance().getUserInfo().Result.name;
        logData.CollectionNumber = "入场查验";
        logData.PicType = 555;
        logData.UploadStatus = UploadStatus;
        logData.UploadType = UploadType;
        logData.ErrorMessage = ErrorMessage;
        HttpRequest.addLog(this, logData, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, StatusData content) {

            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }


    //删除照片
    @Override
    public void delete(int position) {
        checkEgImgAdapter.delete(position);
        imageFiles.remove(position);
        if (imageFiles.isEmpty()) {
            photoType = 0;
            type = 0;
        }
        LogUtil.i("lzx------->", imageFiles.toString());
    }

    public void enableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(false);
        }
    }


    private Bitmap addWatermarkToBitmap(Bitmap src, Bitmap watermark) {
        // 创建一个带有水印的 Bitmap 对象
        Bitmap resultBitmap = src.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(resultBitmap);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setAlpha(255); // 设置透明度
        canvas.drawBitmap(watermark, src.getWidth() / 2 - watermark.getWidth() / 2, src.getHeight() / 2 - watermark.getHeight() / 2, paint);
        // 绘制水印（地址、经纬度、时间等）
        paint.setColor(Color.WHITE);
        paint.setTextSize(36);
        // 添加地址、经纬度、时间等信息
        String locationText = "经纬度:" + AddressSP.getInstance().getLongitude() + AddressSP.getInstance().getLatitude();
        String timeText = "时间: " + DateFormat.getDateTimeInstance().format(new Date());
        String address = "地址: " + addressName;
        canvas.drawText(locationText, 30, src.getHeight() - 50, paint);
        canvas.drawText(timeText, 30, src.getHeight() - 120, paint);
        canvas.drawText(address, 30, src.getHeight() - 190, paint);

        Paint paint1 = new Paint();
        paint1.setColor(Color.parseColor("#1890FF")); // 设置颜色为红色

        paint1.setTextSize(spToPx(this, 22)); // 设置字体大小
        paint1.setAntiAlias(true); // 设置抗锯齿
        paint1.setAlpha(50); // 设置透明度

//        // 计算水印文字的起始位置，你可以根据需要进行调整
        int startX = 0;
        int startY = 0;
        int spacing = 300; // 设置水印文字之间的间距
        while (startX < src.getWidth() && startY < src.getHeight()) {
            // 绘制倾斜的水印文字
            drawText(canvas, "成都智慧动监", startX, startY, paint1);
            // 更新起始位置
            startX += paint.measureText("成都智慧动监") + spacing;
            // 如果超出图片宽度，则换行
            if (startX >= src.getWidth()) {
                startX = 0;
                startY += paint.getTextSize() + spacing;
            }
        }

        // 返回带有水印的 Bitmap
        return resultBitmap;
    }

    // 绘制倾斜的文字
    private static void drawText(Canvas canvas, String text, float x, float y, Paint paint) {
        // 保存 Canvas 当前状态
        canvas.save();
        // 移动 Canvas 原点到指定位置
        canvas.translate(x, y);
        // 设置倾斜角度
        canvas.rotate(-45, 0, 0);
        // 绘制文字
        canvas.drawText(text, 0, 0, paint);
        // 恢复 Canvas 状态
        canvas.restore();
    }

    private static float spToPx(Context context, float sp) {
        return sp * context.getResources().getDisplayMetrics().scaledDensity;
    }


    private void showCheckErrorDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_check_error, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(exitDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        contentTv.setTextSize(16);
        cancelTv.setOnClickListener(view1 -> exitDialog.dismiss());
        confirmTv.setOnClickListener(view1 -> {
            exitDialog.dismiss();
            showLoading("数据提交中...");
            binding.summitBtn.setClickable(false);
            mUpECET();
        });
        if (ActivityManager.getInstance().isLiving(EntryCheckActivity.this)) {
            exitDialog.show();
        }
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }


    private void initGaoDe() {
        //初始化定位
        try {
            AMapLocationClient.updatePrivacyShow(this, true, true);
            AMapLocationClient.updatePrivacyAgree(this, true);
            mLocationClient = new AMapLocationClient(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }


    private static class MyAMapLocationListener implements AMapLocationListener {


        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    LogUtil.e("位置：", aMapLocation.getAddress());

                    addressName = aMapLocation.getAddress();
                    double longitude = aMapLocation.getLongitude();//精度
                    double latitude = aMapLocation.getLatitude();//维度
                    AddressSP.getInstance().setLatitude(latitude);
                    AddressSP.getInstance().setLongitude(longitude);

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    LogUtil.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
                }
            }
        }
    }


}
