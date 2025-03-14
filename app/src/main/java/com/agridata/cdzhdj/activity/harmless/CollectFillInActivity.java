package com.agridata.cdzhdj.activity.harmless;

import static com.agridata.cdzhdj.base.MyApplication.getContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.AddressSP;
import com.agridata.cdzhdj.SPUtil.AnimalSP;
import com.agridata.cdzhdj.SPUtil.ConfigSP;
import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.SPUtil.ShouJISP;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.mine.pic.PicActivity;
import com.agridata.cdzhdj.activity.adapter.AnimalMenuAdapter;
import com.agridata.cdzhdj.activity.adapter.GridImageDeathAdapter;
import com.agridata.cdzhdj.activity.adapter.GridImageShiTiAdapter;
import com.agridata.cdzhdj.activity.adapter.GridImageZhuangCheAdapter;
import com.agridata.cdzhdj.activity.adapter.onDelListener;
import com.agridata.cdzhdj.activity.adapter.onDelSTPicListener;
import com.agridata.cdzhdj.activity.adapter.onDelSiChuPicListener;
import com.agridata.cdzhdj.activity.adapter.onDelZCPicListener;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.AnimalBean;
import com.agridata.cdzhdj.data.AnimalMenuBean;
import com.agridata.cdzhdj.data.BankBean;
import com.agridata.cdzhdj.data.BaoXianGongSiBean;
import com.agridata.cdzhdj.data.BatchImgBean;
import com.agridata.cdzhdj.data.BeiAnBean;
import com.agridata.cdzhdj.data.CollectInfoData;
import com.agridata.cdzhdj.data.HarmlessAnimalBean;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.data.NewSendSmsBean;
import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.data.SendStatusBean;
import com.agridata.cdzhdj.data.ShouJiHuanCunBean;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.StatusMeBean;
import com.agridata.cdzhdj.data.UnitBean;
import com.agridata.cdzhdj.data.XianChangRenBean;
import com.agridata.cdzhdj.data.db.dao.AppDatabase;
import com.agridata.cdzhdj.data.db.repository.CollectionDBModelRepository;
import com.agridata.cdzhdj.data.harmless.CollectionImageBean;
import com.agridata.cdzhdj.data.logbean.LogData;
import com.agridata.cdzhdj.databinding.ActivityCollectFillInBinding;
import com.agridata.cdzhdj.utils.AssertDataUtil;
import com.agridata.cdzhdj.utils.FileUtil;
import com.agridata.cdzhdj.utils.GlideEngine;
import com.agridata.cdzhdj.utils.GlideUtils;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.agridata.cdzhdj.utils.IdCardUtils;
import com.agridata.cdzhdj.utils.ImageLoader;
import com.agridata.cdzhdj.utils.NetworkUtils;
import com.agridata.cdzhdj.utils.PictureSelectorUtils;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.utils.WaterMaskUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.FullyGridLayoutManager;
import com.agridata.cdzhdj.view.WaterMaskView;
import com.agridata.cdzhdj.view.bottomPopupDialog.BottomPopupDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.agridata.ocr_ui.ocr.camera.CameraActivity;
import com.agridata.util.DateTimeUtils;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.luck.picture.lib.basic.PictureSelectionCameraModel;
import com.luck.picture.lib.basic.PictureSelectionModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.PictureFileUtils;
import com.lzx.utils.RxToast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @auther 56454
 * @date 2022/6/22
 * @time 15:55.
 * 收集单
 */
public class CollectFillInActivity extends BaseActivity<ActivityCollectFillInBinding> implements View.OnClickListener, onDelListener, onDelSiChuPicListener, onDelSTPicListener, onDelZCPicListener {
    private final static String TAG = "lzx------》";
    private ImageEngine imageEngine;
    private int PicBtnType = 0;
    private int QianMingType = 0;
    private int WeighType = 1;//选中重量Type
    private List<AnimalMenuBean> animalMenuBeans;
    private AnimalMenuAdapter animalMenuAdapter;

    private GridImageDeathAdapter gridImageDeathAdapter;
    private List<LocalMedia> mAnimalDeathPicList;


    private GridImageShiTiAdapter imageShiTiAdapter;
    private List<LocalMedia> mAnimalShiTiPicList;


    private GridImageZhuangCheAdapter imageZhuangCheAdapter;
    private List<LocalMedia> mAnimalZhuangChePicList;

    private String BeiAnInfo;
    private BeiAnBean beiAnBean;
    private OptionsPickerView pvCustomOptions, UnitpvCustomOptions, BaoXiaopvCustomOptions, BankpvCustomOptions, XiaoDupvCustomOptions;
    private AnimalBean animalBeanChoose;//
    private UnitBean unitBeanChoose;//
    private BaoXianGongSiBean baoXianGongSiBeanChoose;
    private BankBean bankBeanChoose;


    private int XiaoDuCheck = 0;
    private int YiBingCheck = 2;
    private int SiWangYaunYinCheck = 0;
    private int BaoXianCheck = 0;
    private int mBankCheck=0;
    private List<BankBean> bankBeansList;

    private String IDCardMid;
    private String BankMid;
    private String ZhiziMid;
    private String ShouYunYuanMid;
    private String YangZhiHuMid;
    private List<String> SiChuErBiaoPicList = new ArrayList<>();
    private List<String> ShiTiPicList = new ArrayList<>();
    private List<String> ZHuangCheList = new ArrayList<>();

    private CustomLoadingDialog mLoadingDialog;
    private AlertDialog exitDialogSuccess;
    private String targetFileUrl;
    private RoleBean.DataBean roleInfo;
    private String shouyunyuanUrl;
    private List<AnimalBean> animalBeanList;


    private ShouJiHuanCunBean shouJiHuanCunBean;


    private int selectUnitPos;


    private boolean hasDuplicatesSC = false;
    private boolean hasDuplicatesST = false;
    private boolean hasDuplicatesZC = false;


    private static final int REQUEST_CODE_CAMERA = 102;
    private static final int REQUEST_CODE_BANKCARD = 111;

    //TODO:2024-05-05 离线保存照片实体
    private CollectionImageBean collectionImageBean;


    private List<String> deathPathList = new ArrayList<>();

    private List<String> ShiTiPathList = new ArrayList<>();

    private List<String> ZCPathList = new ArrayList<>();

    private boolean mIsFosterCare= false;
    public static void start(Activity context, String BeiAnInfo) {
        Intent intent = new Intent(context, CollectFillInActivity.class);
        intent.putExtra("BeiAnInfo", BeiAnInfo);
        context.startActivityForResult(intent, 1002);
    }


    @Override
    protected ActivityCollectFillInBinding getBinding() {
        return ActivityCollectFillInBinding.inflate(getLayoutInflater());
    }

    private void getPermissions() {
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                // 申请多个权限
                .permission(Permission.Group.BLUETOOTH).request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean all) {
                        if (!all) {
                            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(@NonNull List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(CollectFillInActivity.this, "被永久拒绝授权，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(CollectFillInActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(CollectFillInActivity.this, "获取权限失败"));
                        }
                    }
                });
    }

    @Override
    protected void initView() {
        getPermissions();
        //TODO:2024-05-05 离线保存照片实体
        collectionImageBean = new CollectionImageBean();
        collectionImageBean.zhaungCheListPath = new ArrayList<>();
        collectionImageBean.siChuListPath = new ArrayList<>();
        collectionImageBean.siChuListPath = new ArrayList<>();
        //初始化ocr
        initToken();
        this.getArguments();
        this.initLoadingView();
        roleInfo = RoleSP.getInstance().getRoleInfo();
        LogUtil.d(TAG, "获取roleInfo" + roleInfo.toString());
        //getHarAnimalInfo();//获取动物种类
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        String xiaoDu = ConfigSP.getInstance().getXiaoDu();
        if (!TextUtils.isEmpty(xiaoDu)) {
            binding.disinfectantsEt.setText(xiaoDu);
        }
        binding.titlebarLeft.setImageResource(R.drawable.title_back);
        binding.titlebarMiddle.setText("收集清单");
        imageEngine = GlideEngine.createGlideEngine();
        binding.baoxianLayout.upBankIvBtn.setOnClickListener(this);
        binding.baoxianLayout.upCarIvBtn.setOnClickListener(this);
        binding.baoxianLayout.upDepositIvBtn.setOnClickListener(this);
        binding.animalMuen.addAnimalBtn.setOnClickListener(this);
        binding.shouyunyuanQianming.setOnClickListener(this);
        binding.yangzhichangQianming.setOnClickListener(this);
        binding.animalTypeEt.setOnClickListener(this);
        binding.unitTypeEt.setOnClickListener(this);
        binding.sureBtn.setOnClickListener(this);
        binding.baoxianLayout.idcardIv.setOnClickListener(this);
        binding.baoxianLayout.bankNameEt.setOnClickListener(this);
        binding.baoxianLayout.insuranceCompanyEt.setOnClickListener(this);
        binding.upZhizidanjuIvBtn.setOnClickListener(this);
        binding.chooseXiaoduBtn.setOnClickListener(this);
        binding.titlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAnimalDeathPicList = new ArrayList<>();
        mAnimalShiTiPicList = new ArrayList<>();
        mAnimalZhuangChePicList = new ArrayList<>();


        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        binding.recyclerDeadAnimalEartag.setLayoutManager(manager);
        binding.recyclerDeadAnimalEartag.addItemDecoration(new GridSpacingItemDecoration(4, ScreenUtils.dip2px(this, 8), false));
        gridImageDeathAdapter = new GridImageDeathAdapter(this, mAnimalDeathPicList);
        gridImageDeathAdapter.setDelSiChuPiC(this);
        gridImageDeathAdapter.setSelectMax(100);
        binding.recyclerDeadAnimalEartag.setAdapter(gridImageDeathAdapter);


        FullyGridLayoutManager managerShiTi = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        binding.recyclerShiti.setLayoutManager(managerShiTi);
        binding.recyclerShiti.addItemDecoration(new GridSpacingItemDecoration(4, ScreenUtils.dip2px(this, 8), false));
        imageShiTiAdapter = new GridImageShiTiAdapter(this, mAnimalShiTiPicList);
        imageShiTiAdapter.setDelSTPiC(this);
        imageShiTiAdapter.setSelectMax(100);
        binding.recyclerShiti.setAdapter(imageShiTiAdapter);


        FullyGridLayoutManager managerZhuangChe = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        binding.recyclerZhuangche.setLayoutManager(managerZhuangChe);
        binding.recyclerZhuangche.addItemDecoration(new GridSpacingItemDecoration(4, ScreenUtils.dip2px(this, 8), false));
        imageZhuangCheAdapter = new GridImageZhuangCheAdapter(this, mAnimalZhuangChePicList);
        imageZhuangCheAdapter.setDelZCPiC(this);
        imageZhuangCheAdapter.setSelectMax(100);
        binding.recyclerZhuangche.setAdapter(imageZhuangCheAdapter);
        if (ShouJISP.getInstance().getShouJiHuanCunInfo() != null) {
            if (!beiAnBean.Mid.equals(ShouJISP.getInstance().getShouJiHuanCunInfo().Mid)) {
                ShouJISP.getInstance().clear();
                shouJiHuanCunBean = new ShouJiHuanCunBean();
            } else {
                shouJiHuanCunBean = ShouJISP.getInstance().getShouJiHuanCunInfo();
            }
        } else {
            shouJiHuanCunBean = new ShouJiHuanCunBean();
        }
        if (shouJiHuanCunBean.imgFilesBeanData == null) {
            shouJiHuanCunBean.imgFilesBeanData = new ShouJiHuanCunBean.ImgFilesBeanData();
        }
        if (shouJiHuanCunBean.imgFilesBeanData.ShiTiPicList == null) {
            shouJiHuanCunBean.imgFilesBeanData.ShiTiPicList = new ArrayList<>();
        }
        if (shouJiHuanCunBean.imgFilesBeanData.SiChuPicList == null) {
            shouJiHuanCunBean.imgFilesBeanData.SiChuPicList = new ArrayList<>();
        }
        if (shouJiHuanCunBean.imgFilesBeanData.ZhuangChePicList == null) {
            shouJiHuanCunBean.imgFilesBeanData.ZhuangChePicList = new ArrayList<>();
        }
        EdittextOn();
    }

    @Override
    protected void initDatas() {
        animalMenuBeans = new ArrayList<>();
        binding.animalMuen.recyclerAnimal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.animalMuen.recyclerAnimal.setNestedScrollingEnabled(false);
        animalMenuAdapter = new AnimalMenuAdapter(R.layout.item_animal_menu, this);
        animalMenuAdapter.setDel(this);
        binding.animalMuen.recyclerAnimal.setAdapter(animalMenuAdapter);
        mAnimalDeathAdapter();//上传死畜耳标照片 监听
        mAnimalShiTiAdapter();//上传死畜尸体照片 监听
        mAnimalZhuangCheAdapter();//上传装车照片 监听
        getDanHao();//获取单号
        setBeiAnInfo();//设置备案信息
        bankBeansList = AssertDataUtil.initBank(this);
        LogUtil.d(TAG, "银行Json解析" + bankBeansList);
        binding.zuobiaoEt.setText(AddressSP.getInstance().getLatitude() + "," + AddressSP.getInstance().getLongitude());

    }


    /**
     * 删除死畜照片
     *
     * @param position
     */
    @Override
    public void deleteSiChuPic(int position) {
        LogUtil.d(TAG, "删除死畜的照片pos" + position);
        if (NetworkUtils.isNetAvailable(this)) {
            if (!SiChuErBiaoPicList.isEmpty()) {
                SiChuErBiaoPicList.remove(position);
            }
            LogUtil.d(TAG, "SiChuErBiaoPicList死畜" + SiChuErBiaoPicList.size() + SiChuErBiaoPicList.toString());
            if (!shouJiHuanCunBean.imgFilesBeanData.SiChuPicList.isEmpty()) {
                shouJiHuanCunBean.imgFilesBeanData.SiChuPicList.remove(position);
            }
            ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
            LogUtil.d(TAG, "SiChuErBiaoPicList死畜1231" + shouJiHuanCunBean.imgFilesBeanData.SiChuPicList.size() + shouJiHuanCunBean.imgFilesBeanData.SiChuPicList.toString());
        } else {
            collectionImageBean.siChuListPath.remove(position);
        }
    }

    /**
     * 删除死畜尸体照片
     *
     * @param position
     */
    @Override
    public void deleteSTPic(int position) {


        if (NetworkUtils.isNetAvailable(this)) {
            if (!ShiTiPicList.isEmpty()) {
                ShiTiPicList.remove(position);
            }
            if (!shouJiHuanCunBean.imgFilesBeanData.ShiTiPicList.isEmpty()) {
                shouJiHuanCunBean.imgFilesBeanData.ShiTiPicList.remove(position);
            }
            ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
        } else {
            collectionImageBean.siTiListPath.remove(position);
        }
    }

    /**
     * 删除装车
     *
     * @param position
     */
    @Override
    public void deleteZCPic(int position) {
        if (NetworkUtils.isNetAvailable(this)) {
            if (!ZHuangCheList.isEmpty()) {
                ZHuangCheList.remove(position);
            }
            if (!shouJiHuanCunBean.imgFilesBeanData.ZhuangChePicList.isEmpty()) {
                shouJiHuanCunBean.imgFilesBeanData.ZhuangChePicList.remove(position);
            }
            ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
        } else {
            collectionImageBean.zhaungCheListPath.remove(position);
        }
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        this.BeiAnInfo = this.getIntent().getStringExtra("BeiAnInfo");
        LogUtil.d(TAG, "beiAnBean" + this.BeiAnInfo.toString());
        beiAnBean = GsonUtil.fromJson(this.BeiAnInfo, BeiAnBean.class);

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


    private void setHuanCun() {
        if (ShouJISP.getInstance().getShouJiHuanCunInfo() != null) {
            ShouJiHuanCunBean shouJiHuanCunInfo = ShouJISP.getInstance().getShouJiHuanCunInfo();
            LogUtil.d(TAG, "shouJiHuanCunInfo" + shouJiHuanCunInfo.toString());

            if (shouJiHuanCunInfo.animalData != null && shouJiHuanCunInfo.animalData.AnimalName != null) {
                //動物
                animalBeanChoose = new AnimalBean("", "");
                animalBeanChoose.ID = shouJiHuanCunInfo.animalData.ID;
                animalBeanChoose.AnimalName = shouJiHuanCunInfo.animalData.AnimalName;
                LogUtil.d(TAG, "animalBeanChoose" + animalBeanChoose);
                binding.animalTypeEt.setText(shouJiHuanCunInfo.animalData.AnimalName);
            }

            //單位
            if (shouJiHuanCunInfo.unitData != null) {
                unitBeanChoose = new UnitBean("", "");
                unitBeanChoose.ID = shouJiHuanCunInfo.unitData.ID;
                unitBeanChoose.UnitName = shouJiHuanCunInfo.unitData.UnitName;
                LogUtil.d(TAG, "unitBeanChoose" + animalBeanChoose);
                binding.unitTypeEt.setText(shouJiHuanCunInfo.unitData.UnitName);
            }

//            //核验总数   总重量
//            if (shouJiHuanCunInfo.animalMenuBean.size()>0){
//                binding.verificationNumEt.setText(shouJiHuanCunInfo.heYanShuLiang);
//                binding.grossWeightEt.setText(shouJiHuanCunInfo.ZongZhongLiang);
//            }
            //试试存栏量
            if (!TextUtils.isEmpty(shouJiHuanCunInfo.ShiShiCunLanLiang)) {
                binding.inventoryRelTimeEt.setText(shouJiHuanCunInfo.ShiShiCunLanLiang);
            }
            //消毒选项
            if (shouJiHuanCunInfo.XiaoDuCheck == 1) {
                XiaoDuCheck = 1;
                binding.xiaoduYesRb.setChecked(true);
            } else {
                XiaoDuCheck = 2;
                binding.xiaoduNoRb.setChecked(true);
            }
            //消毒产品
            if (!TextUtils.isEmpty(shouJiHuanCunInfo.XiaoDuYaoPin)) {
                binding.disinfectantsEt.setText(shouJiHuanCunInfo.XiaoDuYaoPin);
            }

            //疫病
            if (shouJiHuanCunInfo.YiBingCheck == 1) {
                YiBingCheck = 1;
                binding.yibingYesRb.setChecked(true);
            } else {
                YiBingCheck = 2;
                binding.yibingNoRb.setChecked(true);
            }

            //死亡原因
            if (shouJiHuanCunInfo.SiWangYaunYinCheck == 1) {
                SiWangYaunYinCheck = 1;
                binding.deathYibingRb.setChecked(true);
            } else if (shouJiHuanCunInfo.SiWangYaunYinCheck == 2) {
                SiWangYaunYinCheck = 2;
                binding.deathYiwaiRb.setChecked(true);
            } else if (shouJiHuanCunInfo.SiWangYaunYinCheck == 3) {
                SiWangYaunYinCheck = 3;
                binding.deathPushaRb.setChecked(true);
            }

            //保险公司
            if (shouJiHuanCunInfo.BaoXianCheck == 1) {
                BaoXianCheck = 1;
                binding.baoxianLayout.baoxianYesRb.setChecked(true);
                binding.baoxianLayout.baoxianLl.setVisibility(View.VISIBLE);
                if (shouJiHuanCunInfo.BaoXiaoGongSi != null) {
                    binding.baoxianLayout.insuranceCompanyEt.setText(shouJiHuanCunInfo.BaoXiaoGongSi.InsuranceCompanyName);
                    baoXianGongSiBeanChoose = new BaoXianGongSiBean("", "");
                    baoXianGongSiBeanChoose.ID = shouJiHuanCunInfo.BaoXiaoGongSi.key;
                    baoXianGongSiBeanChoose.BaoXianGongSiName = shouJiHuanCunInfo.BaoXiaoGongSi.InsuranceCompanyName;
                } else {
                    baoXianGongSiBeanChoose = new BaoXianGongSiBean("", "");
                }
            } else {
                BaoXianCheck = 2;
                binding.baoxianLayout.baoxianNoRb.setChecked(true);
                binding.baoxianLayout.baoxianLl.setVisibility(View.GONE);
            }
            //身份证号
            if (!TextUtils.isEmpty(shouJiHuanCunInfo.IDCardNum)) {
                LogUtil.d(TAG, "身份证号" + shouJiHuanCunInfo.IDCardNum);
                binding.baoxianLayout.idcardEt.setText(shouJiHuanCunInfo.IDCardNum);
            }

            if (NetworkUtils.isNetAvailable(this)) {
                //身份证号
                if (shouJiHuanCunInfo.imgFilesBeanData != null && !TextUtils.isEmpty(shouJiHuanCunInfo.imgFilesBeanData.IdCardPic)) {
                    IDCardMid = shouJiHuanCunInfo.imgFilesBeanData.IdCardPic;
                    LogUtil.d(TAG, "缓存身份证照片" + IDCardMid);
                    Glide.with(this).load(UrlUtils.pic_url + shouJiHuanCunInfo.imgFilesBeanData.IdCardPic).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).into(binding.baoxianLayout.idcardIv);
                }
            }


            //卡号
            if (!TextUtils.isEmpty(shouJiHuanCunInfo.bankNum)) {
                binding.baoxianLayout.bankNumEt.setText(shouJiHuanCunInfo.bankNum);
            }


            if (NetworkUtils.isNetAvailable(this)) {
                //银行卡照片
                if (shouJiHuanCunInfo.imgFilesBeanData != null && !TextUtils.isEmpty(shouJiHuanCunInfo.imgFilesBeanData.BankPic)) {
                    BankMid = shouJiHuanCunInfo.imgFilesBeanData.BankPic;
                    Glide.with(this).load(UrlUtils.pic_url + shouJiHuanCunInfo.imgFilesBeanData.BankPic).apply(RequestOptions.bitmapTransform(new RoundedCorners(12))).into(binding.baoxianLayout.bankIv);
                }
            }


            if (shouJiHuanCunInfo.animalMenuBean != null && !shouJiHuanCunInfo.animalMenuBean.isEmpty()) {
                for (ShouJiHuanCunBean.AnimalMenuBeanData animalMenuBeanData : shouJiHuanCunInfo.animalMenuBean) {
                    AnimalMenuBean menuBean = new AnimalMenuBean();
                    menuBean.AnimalType = animalMenuBeanData.AnimalType;
                    menuBean.AnimalName = animalMenuBeanData.AnimalName;
                    menuBean.AnimalID = animalMenuBeanData.AnimalID;
                    menuBean.EarTag = animalMenuBeanData.EarTag;
                    menuBean.Weight = animalMenuBeanData.Weight;
                    menuBean.ShiFouMuPig = animalMenuBeanData.IsSow;
                    if (!TextUtils.isEmpty(animalMenuBeanData.ShuLiang)) {
                        menuBean.ShuLiang = animalMenuBeanData.ShuLiang;
                    }

                    animalMenuBeans.add(menuBean);
                }
                LogUtil.d(TAG, " animalMenuBeans" + animalMenuBeans.toString());

                binding.animalMuen.noDataIv.setVisibility(View.GONE);
                binding.animalMuen.recyclerAnimal.setVisibility(View.VISIBLE);
                animalMenuAdapter.refreshDataList(animalMenuBeans);
                binding.animalMuen.hejiTv.setText(animalMenuBeans.size() + "");
                if (animalMenuBeans.size() > 0) {
                    binding.animalMuen.hejiAllLl.setVisibility(View.VISIBLE);
                }
                int all = 0;
                int allNum = 0;
                for (AnimalMenuBean menuBean : animalMenuBeans) {
                    all = all + Integer.parseInt(menuBean.Weight);
                    allNum = allNum + Integer.parseInt(menuBean.ShuLiang);
                }
                binding.animalMuen.hejiTv.setText("合计:" + animalMenuBeans.size());
                binding.animalMuen.allZhongliangTv.setText("总重量:" + all + "kg");
                binding.animalMuen.allNumTv.setText("总数量：" + allNum + "");
                binding.verificationNumEt.setText(allNum + "");
                binding.grossWeightEt.setText(all + "");
            }


            if (shouJiHuanCunInfo.imgFilesBeanData != null && shouJiHuanCunInfo.imgFilesBeanData.SiChuPicList != null && shouJiHuanCunInfo.imgFilesBeanData.SiChuPicList.size() > 0) {


                for (String EarTagPic : shouJiHuanCunInfo.imgFilesBeanData.SiChuPicList) {
                    SiChuErBiaoPicList.add(EarTagPic);
                    LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + EarTagPic, PictureMimeType.MIME_TYPE_JPEG);
                    gridImageDeathAdapter.getData().add(localMedia);
                    LogUtil.d(TAG, "循环几次" + EarTagPic);
                }

                LogUtil.d(TAG, " SiChuErBiaoPicList" + SiChuErBiaoPicList.toString());
                gridImageDeathAdapter.notifyDataSetChanged();
            }


            if (shouJiHuanCunInfo.imgFilesBeanData != null && shouJiHuanCunInfo.imgFilesBeanData.ShiTiPicList != null && shouJiHuanCunInfo.imgFilesBeanData.ShiTiPicList.size() > 0) {


                for (String EarTagPic : shouJiHuanCunInfo.imgFilesBeanData.ShiTiPicList) {
                    ShiTiPicList.add(EarTagPic);
                    LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + EarTagPic, PictureMimeType.MIME_TYPE_JPEG);
                    imageShiTiAdapter.getData().add(localMedia);
                }
                imageShiTiAdapter.notifyDataSetChanged();
            }

            if (shouJiHuanCunInfo.imgFilesBeanData != null && shouJiHuanCunInfo.imgFilesBeanData.ZhuangChePicList != null && shouJiHuanCunInfo.imgFilesBeanData.ZhuangChePicList.size() > 0) {

                for (String EarTagPic : shouJiHuanCunInfo.imgFilesBeanData.ZhuangChePicList) {
                    ZHuangCheList.add(EarTagPic);
                    LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + EarTagPic, PictureMimeType.MIME_TYPE_JPEG);
                    imageZhuangCheAdapter.getData().add(localMedia);
                }
                imageZhuangCheAdapter.notifyDataSetChanged();
            }

            if (!TextUtils.isEmpty(shouJiHuanCunInfo.feedBook)) {
                binding.feedbackEt.setText(shouJiHuanCunInfo.feedBook);
            }

            //
            if (shouJiHuanCunInfo.imgFilesBeanData != null && !TextUtils.isEmpty(shouJiHuanCunInfo.imgFilesBeanData.ShouYunYuanPic)) {
                ShouYunYuanMid = shouJiHuanCunInfo.imgFilesBeanData.ShouYunYuanPic;
                Glide.with(this).load(UrlUtils.pic_url + shouJiHuanCunInfo.imgFilesBeanData.ShouYunYuanPic).into(binding.shouyunyuanQianming);
            }


            if (shouJiHuanCunInfo.imgFilesBeanData != null && !TextUtils.isEmpty(shouJiHuanCunInfo.imgFilesBeanData.YangZhiChangHuPic)) {
                YangZhiHuMid = shouJiHuanCunInfo.imgFilesBeanData.YangZhiChangHuPic;
                Glide.with(this).load(UrlUtils.pic_url + shouJiHuanCunInfo.imgFilesBeanData.YangZhiChangHuPic).into(binding.yangzhichangQianming);
            }


            //银行卡照片
            if (shouJiHuanCunInfo.imgFilesBeanData != null && !TextUtils.isEmpty(shouJiHuanCunInfo.imgFilesBeanData.CollectCertPic)) {
                ZhiziMid = shouJiHuanCunInfo.imgFilesBeanData.CollectCertPic;
                Glide.with(this).load(UrlUtils.pic_url + shouJiHuanCunInfo.imgFilesBeanData.CollectCertPic).into(binding.zhizidanjuIv);
            }

            ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunInfo);

        }

    }

    /*
     * 设置radio的点击事件，当点击的时候显示文字
     */
    public void onRadioButtonClicked(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.baoxianLayout.baoxianYesRb) {
            if (isChecked) {
                BaoXianCheck = 1;
                setCheckRb(BaoXianCheck, XiaoDuCheck, YiBingCheck, SiWangYaunYinCheck);
                binding.baoxianLayout.baoxianLl.setVisibility(View.VISIBLE);
            }
        } else if (view == binding.baoxianLayout.baoxianNoRb) {
            if (isChecked) {
                BaoXianCheck = 2;
                setCheckRb(BaoXianCheck, XiaoDuCheck, YiBingCheck, SiWangYaunYinCheck);
                binding.baoxianLayout.baoxianLl.setVisibility(View.GONE);
            }
        } else if (view == binding.xiaoduYesRb) { //是否消毒
            if (isChecked) {
                XiaoDuCheck = 1;
                setCheckRb(BaoXianCheck, XiaoDuCheck, YiBingCheck, SiWangYaunYinCheck);
            }
        } else if (view == binding.xiaoduNoRb) { //是否消毒
            if (isChecked) {
                XiaoDuCheck = 2;
                setCheckRb(BaoXianCheck, XiaoDuCheck, YiBingCheck, SiWangYaunYinCheck);
            }
        } else if (view == binding.yibingYesRb) { //是否重大疫病
            if (isChecked) {
                YiBingCheck = 1;
                setCheckRb(BaoXianCheck, XiaoDuCheck, YiBingCheck, SiWangYaunYinCheck);
            }
        } else if (view == binding.yibingNoRb) { //是否重大疫病
            if (isChecked) {
                YiBingCheck = 2;
                setCheckRb(BaoXianCheck, XiaoDuCheck, YiBingCheck, SiWangYaunYinCheck);
            }
        } else if (view == binding.deathYibingRb) { //死亡原因
            if (isChecked) {
                SiWangYaunYinCheck = 1;
                setCheckRb(BaoXianCheck, XiaoDuCheck, YiBingCheck, SiWangYaunYinCheck);
            }
        } else if (view == binding.deathYiwaiRb) { //死亡原因
            if (isChecked) {
                SiWangYaunYinCheck = 2;
                setCheckRb(BaoXianCheck, XiaoDuCheck, YiBingCheck, SiWangYaunYinCheck);
            }
        } else if (view == binding.deathPushaRb) { //死亡原因
            if (isChecked) {
                SiWangYaunYinCheck = 3;
                setCheckRb(BaoXianCheck, XiaoDuCheck, YiBingCheck, SiWangYaunYinCheck);
            }
        } if (view == binding.baoxianLayout.bankYesRb) {//银行卡
            if (isChecked) {
                mBankCheck = 1;
                binding.baoxianLayout.depositAccountLl.setVisibility(View.GONE);
                binding.baoxianLayout.depositImageLl.setVisibility(View.GONE);
                binding.baoxianLayout.bankLl.setVisibility(View.VISIBLE);
                binding.baoxianLayout.bankImageLl.setVisibility(View.VISIBLE);
            }
        } else if (view == binding.baoxianLayout.depositNoRb) {
            if (isChecked) {
                mBankCheck = 2;
                binding.baoxianLayout.bankLl.setVisibility(View.GONE);
                binding.baoxianLayout.bankImageLl.setVisibility(View.GONE);
                binding.baoxianLayout.depositAccountLl.setVisibility(View.VISIBLE);
                binding.baoxianLayout.depositImageLl.setVisibility(View.VISIBLE);
            }
        }

    }

    private void setCheckRb(int BaoXianCheck, int XiaoDuCheck, int YiBingCheck, int SiWangYaunYinCheck) {
        shouJiHuanCunBean.XiaoDuCheck = XiaoDuCheck;
        shouJiHuanCunBean.BaoXianCheck = BaoXianCheck;
        shouJiHuanCunBean.YiBingCheck = YiBingCheck;
        shouJiHuanCunBean.SiWangYaunYinCheck = SiWangYaunYinCheck;

        ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
    }

    @Override
    public void onClick(View v) {

        if (v == binding.baoxianLayout.upCarIvBtn) {//身份证号
//            showPicDialog();
            PicBtnType = 1;

            if (NetworkUtils.isNetAvailable(CollectFillInActivity.this)) {
                Intent intent = new Intent(CollectFillInActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            } else {
                showPicDialog();
            }

        } else if (v == binding.baoxianLayout.upBankIvBtn) {//银行卡号
            PicBtnType = 2;
            if (NetworkUtils.isNetAvailable(CollectFillInActivity.this)) {
                Intent intent = new Intent(CollectFillInActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFileBank(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent, REQUEST_CODE_BANKCARD);
            } else {
                showPicDialog();
            }
        } else if (v==binding.baoxianLayout.upDepositIvBtn) {
            PicBtnType = 10;
            getPermissionCream();

        } else if (v == binding.animalMuen.addAnimalBtn) { //添加动物清单
            if (!TextUtils.isEmpty(binding.animalTypeEt.getText().toString())) {
                AddAnimalMenuActivity.start(CollectFillInActivity.this, animalBeanChoose.AnimalName, animalBeanChoose.ID);
            } else {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请选择动物在进行添加~"));
            }
        } else if (v == binding.shouyunyuanQianming) {
            if (!animalMenuBeans.isEmpty()) {
                SignActivity.start(CollectFillInActivity.this, "收运员签名", 10);
            } else {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请先填写动物数据"));
            }

        } else if (v == binding.yangzhichangQianming) {
            if (!animalMenuBeans.isEmpty()) {
                SignActivity.start(CollectFillInActivity.this, "养殖场户签名", 11);
            } else {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请先填写动物数据"));
            }
        } else if (v == binding.animalTypeEt) {
            //TODO:2024-04-30更改获取动物方式
            List<HarmlessAnimalBean.ResultAnimalBean> harmlessAnimalList = AnimalSP.getInstance().getHarmlessAnimalList();
            animalBeanList = new ArrayList<>();
            for (HarmlessAnimalBean.ResultAnimalBean resultBean : harmlessAnimalList) {
                animalBeanList.add(new AnimalBean(String.valueOf(resultBean.AnimalID), resultBean.AnimalName));
            }
            showAnimalDialog();
        } else if (v == binding.unitTypeEt) {
            if (!TextUtils.isEmpty(binding.animalTypeEt.getText().toString())) {
                showUnitDialog();
            } else {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请先选择动物种类"));
            }

        } else if (v == binding.baoxianLayout.insuranceCompanyEt) {
            showBaoXianGongSiDialog();
        } else if (v == binding.baoxianLayout.bankNameEt) {
            showBankNameDialog();
        } else if (v == binding.chooseXiaoduBtn) {
            showXiaoDuDialog();
        } else if (v == binding.baoxianLayout.idcardIv) {

            if (!TextUtils.isEmpty(targetFileUrl)) {
                PicActivity.start(this, targetFileUrl);
            }


        } else if (v == binding.sureBtn) {
            if (checkInfo()) {
                upLoadCollect();
            }
        } else if (v == binding.upZhizidanjuIvBtn) {//上传纸质单据
            PicBtnType = 999;
            showPicDialog();
        }
    }

    private void showPicDialog() {
        List<String> bottomDialogContents = new ArrayList<>();
        bottomDialogContents.add("拍照");
        //TODO:至为对接 要求必须拍照 （纸质单据重复）2024-01-03
        //bottomDialogContents.add("相册选取");
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
                getPermissionCream();
            } else if (position == 1) { //相册选取
                OpenPicture();
            }
        });
        bottomPopupDialog.setOnDismissListener(DialogInterface::dismiss);
    }

    private void getPermissionCream() {
        XXPermissions.with(this).permission(Permission.CAMERA).request((permissions, all) -> {
            if (all) {
                TakePicturesAlone("SINGLE");
            }
        });
    }

    //单独拍照
    private void TakePicturesAlone(String type) {
        // Take pictures alone
        PictureSelectionCameraModel cameraModel = PictureSelector.create(CollectFillInActivity.this).openCamera(SelectMimeType.ofImage())

                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())//裁剪引擎
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())//压缩引擎
                .isOriginalControl(false);
        forOnlyCameraResult(cameraModel, type);
    }

    // 相册选择
    private void OpenPicture() {
        // 进入相册
        PictureSelectionModel selectionModel = PictureSelector.create(CollectFillInActivity.this).openGallery(SelectMimeType.ofImage()).setSelectorUIStyle(PictureSelectorUtils.setStyle()).setImageEngine(imageEngine)
                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine()).setSandboxFileEngine(PictureSelectorUtils.getMeSandboxFileEngine()).setSelectLimitTipsListener(PictureSelectorUtils.getMeOnSelectLimitTips()).setSelectionMode(SelectModeConfig.SINGLE).setQuerySortOrder(MediaStore.MediaColumns.DATE_MODIFIED).isDisplayTimeAxis(true).isOriginalControl(false).isDisplayCamera(false).isPreviewFullScreenMode(true).isPreviewZoomEffect(true).isPreviewImage(true).isMaxSelectEnabledMask(true).isDirectReturnSingle(true).setMaxSelectNum(1);
        forSelectResult(selectionModel, "SINGLE");
    }


    private void forOnlyCameraResult(PictureSelectionCameraModel model, String type) {
        model.forResultActivity(new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(ArrayList<LocalMedia> result) {
                analyticalSelectResults(result, type); //SINGLE
            }

            @Override
            public void onCancel() {

            }
        });
    }

    //选择回调
    private void forSelectResult(PictureSelectionModel model, String typeName) {

        switch (typeName) {
            case "SINGLE"://银行卡号照片 + 身份证照片 + 纸质单据
                setModel(model, "SINGLE");
                break;
            case "death"://死畜耳标照片
                setModel(model, "death");
                break;
            case "ShiTi"://上传死畜尸体照片
                setModel(model, "ShiTi");
                break;
            case "ZhuangChe"://上传装车照片
                setModel(model, "ZhuangChe");
                break;
        }

    }

    private void setModel(PictureSelectionModel model, String typeName) {
        model.forResult(new OnResultCallbackListener<>() {
            @Override
            public void onResult(ArrayList<LocalMedia> result) {
                analyticalSelectResults(result, typeName);
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
            LogUtil.i(TAG, "可用路径: " + media.getAvailablePath());


        }
        LogUtil.d("lzx----->",typeName);
        switch (typeName) {
            case "SINGLE"://银行卡号照片 + 身份证照片
                if (PicBtnType == 1) {
                    if (!result.isEmpty()) {
                        //TODO:2024-04-30 离线身份证
                        if (NetworkUtils.isNetAvailable(this)) {
                            GlideUtils.createGlideEngine().loadRounderImage(CollectFillInActivity.this, result.get(0).getRealPath(), binding.baoxianLayout.idcardIv, 20);
                            upLoadImg(result.get(0).getRealPath());
                        } else {
                            Bitmap sourBitmap = BitmapFactory.decodeFile(result.get(0).getCompressPath());
                            String cardPath = ImageLoader.saveToExternalStorageCollection(this, sourBitmap, "idcard", binding.collectNumTv.getText().toString());
                            LogUtil.d("lzx---->", "省份证号" + cardPath);
                            collectionImageBean.idCardPath = cardPath;
                            GlideUtils.createGlideEngine().loadRounderImage(CollectFillInActivity.this, "file://" + cardPath, binding.baoxianLayout.idcardIv, 20);
                        }
                    }
                } else if (PicBtnType == 2) {
                    if (!result.isEmpty()) {
                        //TODO:2024-05-05 离线银行卡号
                        if (NetworkUtils.isNetAvailable(this)) {
                            GlideUtils.createGlideEngine().loadRounderImage(CollectFillInActivity.this, result.get(0).getRealPath(), binding.baoxianLayout.bankIv, 20);
                            upLoadImg(result.get(0).getRealPath());
                        } else {
                            Bitmap sourBitmap = BitmapFactory.decodeFile(result.get(0).getCompressPath());
                            String bankCard = ImageLoader.saveToExternalStorageCollection(this, sourBitmap, "bankCard", binding.collectNumTv.getText().toString());
                            LogUtil.d("lzx---->", "银行卡号" + bankCard);
                            collectionImageBean.bankPath = bankCard;
                            GlideUtils.createGlideEngine().loadRounderImage(CollectFillInActivity.this, "file://" + bankCard, binding.baoxianLayout.bankIv, 20);
                        }

                    }

                } else if (PicBtnType == 10) {
                    if (!result.isEmpty()) {
                        if (NetworkUtils.isNetAvailable(this)) {
                            upLoadImg(result.get(0).getCompressPath()==null?result.get(0).getAvailablePath():result.get(0).getCompressPath());
                        }
                    }

                }else if (PicBtnType == 999) {
                    if (!result.isEmpty()) {
                        Bitmap sourBitmap = ImageLoader.convertFileToBitmap(result.get(0).getCompressPath());
                        Bitmap WaterBitmap = saveWaterMask(CollectFillInActivity.this, 4, sourBitmap, beiAnBean.ApplyNo, beiAnBean.UserName, roleInfo.harmlessUser.name, binding.animalTypeEt.getText().toString(), animalMenuBeans.size() + "");
                        if (NetworkUtils.isNetAvailable(this)) {
                            String fileName = "IMG_" + new Date().getTime() + "zhizhi_danju" + ".png";
                            String zhizhiDanJu = WaterMaskUtil.saveQNext(WaterBitmap, CollectFillInActivity.this, fileName, 60);
                            result.get(0).setCompressPath(zhizhiDanJu);
                            upLoadImg(result.get(0).getCompressPath());
                        } else {
                            String zhizhiDanJu = ImageLoader.saveToExternalStorageCollection(this, WaterBitmap, "zhizhidanju", binding.collectNumTv.getText().toString());
                            LogUtil.d("lzx---->", "纸质单据" + zhizhiDanJu);
                            collectionImageBean.zhizhiDanJu = zhizhiDanJu;
                            GlideUtils.createGlideEngine().loadRounderImage(CollectFillInActivity.this, "file://" + zhizhiDanJu, binding.zhizidanjuIv, 20);

                        }

                    }
                }
                break;
            //死畜耳标照片
            case "death":
                PicBtnType = 5;
                //TODO: 离线操作
                if (NetworkUtils.isNetAvailable(this)) {
                    runOnUiThread(() -> {
                        for (LocalMedia localMedia : result) {
                            Bitmap sourBitmap;
                            if(!TextUtils.isEmpty(localMedia.getCompressPath())){
                                 sourBitmap = ImageLoader.convertFileToBitmap(localMedia.getCompressPath());
                            }else {
                                 sourBitmap = ImageLoader.convertFileToBitmap(localMedia.getAvailablePath());
                            }
                            Bitmap WaterBitmap = saveWaterMask(CollectFillInActivity.this, 4, sourBitmap, beiAnBean.ApplyNo, beiAnBean.UserName, roleInfo.harmlessUser.name, binding.animalTypeEt.getText().toString(), animalMenuBeans.size() + "");
                            String fileName = "IMG_" + new Date().getTime() + "SiChu" + ".png";
                            String sichuListPic = WaterMaskUtil.saveQNext(WaterBitmap, CollectFillInActivity.this, fileName, 60);
                            localMedia.setCompressPath(sichuListPic);
                        }
                    });
                    List<String> DeathPathList = new ArrayList<>();
                    for (LocalMedia localMedia : result) {
                        DeathPathList.add(localMedia.getCompressPath());
                    }
                    upLoadImgBatch(DeathPathList);
                } else {

                    runOnUiThread(() -> {
                        for (LocalMedia localMedia : result) {
                            Bitmap sourBitmap;
                            if(!TextUtils.isEmpty(localMedia.getCompressPath())){
                                sourBitmap = ImageLoader.convertFileToBitmap(localMedia.getCompressPath());
                            }else {
                                sourBitmap = ImageLoader.convertFileToBitmap(localMedia.getAvailablePath());
                            }
                            Bitmap WaterBitmap = saveWaterMask(CollectFillInActivity.this, 4, sourBitmap, beiAnBean.ApplyNo, beiAnBean.UserName, roleInfo.harmlessUser.name, binding.animalTypeEt.getText().toString(), animalMenuBeans.size() + "");
                            Bitmap bitmap = ImageLoader.compressQuality(WaterBitmap);
                            String s = ImageLoader.saveToExternalStorageCollection(this, bitmap, "death", String.valueOf(DateTimeUtils.getSixNum()));
                            deathPathList.add(s);
                            localMedia.setCompressPath(s);
                        }
                    });
                    collectionImageBean.siChuListPath = deathPathList;
                    LogUtil.d(TAG, "离线死畜照片path" + collectionImageBean.siChuListPath.toString());
                    boolean isMaxSize = result.size() == gridImageDeathAdapter.getSelectMax();
                    int oldSize = gridImageDeathAdapter.getData().size();
                    gridImageDeathAdapter.notifyItemRangeRemoved(0, isMaxSize ? oldSize + 1 : oldSize);
                    gridImageDeathAdapter.getData().addAll(result);
                    gridImageDeathAdapter.notifyItemRangeInserted(0, result.size());
                }

                break;
            case "ShiTi"://上传死畜尸体照片：
                PicBtnType = 6;
                if (NetworkUtils.isNetAvailable(this)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (LocalMedia localMedia : result) {
                                Bitmap sourBitmap;
                                if(!TextUtils.isEmpty(localMedia.getCompressPath())){
                                    sourBitmap = ImageLoader.convertFileToBitmap(localMedia.getCompressPath());
                                }else {
                                    sourBitmap = ImageLoader.convertFileToBitmap(localMedia.getAvailablePath());
                                }
                                Bitmap WaterBitmap = saveWaterMask(CollectFillInActivity.this, 4, sourBitmap, beiAnBean.ApplyNo, beiAnBean.UserName, roleInfo.harmlessUser.name, binding.animalTypeEt.getText().toString(), animalMenuBeans.size() + "");
                                String fileName = "IMG_" + new Date().getTime() + "shi_ti" + ".png";
                                String sichuListPic = WaterMaskUtil.saveQNext(WaterBitmap, CollectFillInActivity.this, fileName, 60);
                                localMedia.setCompressPath(sichuListPic);
                            }
                        }
                    });
                    List<String> ShiTihPathList = new ArrayList<>();
                    for (LocalMedia localMedia : result) {
                        ShiTihPathList.add(localMedia.getCompressPath());
                    }
                    upLoadImgBatch(ShiTihPathList);
                } else {

                    runOnUiThread(() -> {
                        for (LocalMedia localMedia : result) {
                            Bitmap sourBitmap = ImageLoader.convertFileToBitmap(localMedia.getCompressPath());
                            Bitmap WaterBitmap = saveWaterMask(CollectFillInActivity.this, 4, sourBitmap, beiAnBean.ApplyNo, beiAnBean.UserName, roleInfo.harmlessUser.name, binding.animalTypeEt.getText().toString(), animalMenuBeans.size() + "");
                            Bitmap bitmap = ImageLoader.compressQuality(WaterBitmap);
                            String s = ImageLoader.saveToExternalStorageCollection(this, bitmap, "ShiTi", String.valueOf(DateTimeUtils.getSixNum()));
                            ShiTiPathList.add(s);
                            localMedia.setCompressPath(s);
                        }
                    });
                    collectionImageBean.siTiListPath = ShiTiPathList;
                    LogUtil.d(TAG, "离线实体照片path" + collectionImageBean.siTiListPath.toString());
                    boolean isMaxSize = result.size() == imageShiTiAdapter.getSelectMax();
                    int oldSize = imageShiTiAdapter.getData().size();
                    imageShiTiAdapter.notifyItemRangeRemoved(0, isMaxSize ? oldSize + 1 : oldSize);
                    imageShiTiAdapter.getData().addAll(result);
                    imageShiTiAdapter.notifyItemRangeInserted(0, result.size());
                }


                break;
            case "ZhuangChe"://上传死畜尸体照片：
                LogUtil.d("lzx----->","装车");
                PicBtnType = 7;
                if (NetworkUtils.isNetAvailable(this)) {
                    runOnUiThread(() -> {
                        for (LocalMedia localMedia : result) {
                            Bitmap sourBitmap;
                            if(!TextUtils.isEmpty(localMedia.getCompressPath())){
                                sourBitmap = ImageLoader.convertFileToBitmap(localMedia.getCompressPath());
                            }else {
                                sourBitmap = ImageLoader.convertFileToBitmap(localMedia.getAvailablePath());
                            }
                            Bitmap WaterBitmap = saveWaterMask(CollectFillInActivity.this, 4, sourBitmap, beiAnBean.ApplyNo, beiAnBean.UserName, roleInfo.harmlessUser.name, binding.animalTypeEt.getText().toString(), animalMenuBeans.size() + "");
                            String fileName = "IMG_" + new Date().getTime() + "zhuang_che" + ".png";
                            String sichuListPic = WaterMaskUtil.saveQNext(WaterBitmap, CollectFillInActivity.this, fileName, 60);
                            localMedia.setCompressPath(sichuListPic);
                        }
                    });
                    List<String> ZCPathList = new ArrayList<>();
                    for (LocalMedia localMedia : result) {
                        ZCPathList.add(localMedia.getCompressPath());
                    }
                    LogUtil.d("lzx----->","装车上传" + ZCPathList.toString());
                    upLoadImgBatch(ZCPathList);
                } else {
                    runOnUiThread(() -> {
                        for (LocalMedia localMedia : result) {
                            Bitmap sourBitmap = ImageLoader.convertFileToBitmap(localMedia.getCompressPath());
                            Bitmap WaterBitmap = saveWaterMask(CollectFillInActivity.this, 4, sourBitmap, beiAnBean.ApplyNo, beiAnBean.UserName, roleInfo.harmlessUser.name, binding.animalTypeEt.getText().toString(), animalMenuBeans.size() + "");
                            Bitmap bitmap = ImageLoader.compressQuality(WaterBitmap);
                            String s = ImageLoader.saveToExternalStorageCollection(this, bitmap, "ZhuangChe", String.valueOf(DateTimeUtils.getSixNum()));
                            ZCPathList.add(s);
                            localMedia.setCompressPath(s);
                        }
                    });
                    collectionImageBean.zhaungCheListPath = ZCPathList;
                    LogUtil.d(TAG, "离线装车照片path" + collectionImageBean.zhaungCheListPath.toString());
                    boolean isMaxSize = result.size() == imageZhuangCheAdapter.getSelectMax();
                    int oldSize = imageZhuangCheAdapter.getData().size();
                    imageZhuangCheAdapter.notifyItemRangeRemoved(0, isMaxSize ? oldSize + 1 : oldSize);
                    imageZhuangCheAdapter.getData().addAll(result);
                    imageZhuangCheAdapter.notifyItemRangeInserted(0, result.size());
                }
                break;
        }
    }

    /**
     * @param position 左上为0，顺时针算起
     */
    private static Bitmap saveWaterMask(Context context, int position, Bitmap sourBitmap, String DaoHao, String ShenBaoRen, String ShouJiRen, String num, String Size) {
        WaterMaskView waterMaskView = new WaterMaskView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        waterMaskView.setLayoutParams(params);
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());
        int year = nowCalendar.get(Calendar.YEAR);
        int month = nowCalendar.get(Calendar.MONTH) + 1;
        int DAY_OF_MONTH = nowCalendar.get(Calendar.DAY_OF_MONTH);
        int HOUR_OF_DAY = nowCalendar.get(Calendar.HOUR_OF_DAY);
        int MINUTE = nowCalendar.get(Calendar.MINUTE);
        int SECOND = nowCalendar.get(Calendar.SECOND);
        waterMaskView.setInfoDate(year + "-" + month + "-" + DAY_OF_MONTH + " " + HOUR_OF_DAY + ":" + MINUTE + ":" + SECOND);
        waterMaskView.setInfoZuoBiao(AddressSP.getInstance().getLatitude() + "，" + AddressSP.getInstance().getLongitude());
        waterMaskView.setInfoDanHao(DaoHao);
        waterMaskView.setInfoShenBaoRen(ShenBaoRen);
        waterMaskView.setInfoShouJiRen(ShouJiRen);
        waterMaskView.settype_animal_tv(num);
        waterMaskView.setnum_tv(Size);
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


    //删除动物清单
    @Override
    public void delete(int position) {
        LogUtil.d(TAG, "POS" + position);
        animalMenuAdapter.removeData(position);
        List<AnimalMenuBean> dataList = animalMenuAdapter.getDataList();
        if (dataList.size() == 0) {
            binding.verificationNumEt.setText("");
            binding.grossWeightEt.setText("");
            binding.animalMuen.hejiAllLl.setVisibility(View.GONE);
            binding.animalMuen.recyclerAnimal.setVisibility(View.GONE);
            binding.animalMuen.noDataIv.setVisibility(View.VISIBLE);
            shouJiHuanCunBean.animalMenuBean = new ArrayList<>();
            shouJiHuanCunBean.animalMenuBean.clear();
            ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
        } else {
            animalMenuAdapter.refreshDataList(dataList);
            int all = 0;
            int allNum = 0;
            for (AnimalMenuBean menuBean : dataList) {
                all = all + Integer.parseInt(menuBean.Weight);
                allNum = allNum + Integer.parseInt(menuBean.ShuLiang);
            }
            binding.animalMuen.hejiTv.setText("合计:" + dataList.size());
            binding.animalMuen.allZhongliangTv.setText("总重量:" + all + "kg");
            binding.grossWeightEt.setText(all + "");
            binding.animalMuen.allNumTv.setText("总数量：" + allNum + "");
            binding.verificationNumEt.setText(allNum + "");

            shouJiHuanCunBean.heYanShuLiang = binding.verificationNumEt.getText().toString();
            shouJiHuanCunBean.ZongZhongLiang = binding.grossWeightEt.getText().toString();

            shouJiHuanCunBean.animalMenuBean = new ArrayList<>();
            for (AnimalMenuBean item : dataList) {
                ShouJiHuanCunBean.AnimalMenuBeanData animalMenuBeanData = new ShouJiHuanCunBean.AnimalMenuBeanData();
                animalMenuBeanData.AnimalType = item.AnimalType;
                animalMenuBeanData.AnimalName = item.AnimalName;
                animalMenuBeanData.AnimalID = item.AnimalID;
                animalMenuBeanData.EarTag = item.EarTag;
                animalMenuBeanData.Weight = item.Weight;
                animalMenuBeanData.ShuLiang = item.ShuLiang;
                shouJiHuanCunBean.animalMenuBean.add(animalMenuBeanData);
            }
            ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
        }
    }


    public static boolean hasDuplicateId(List<String> list1, List<String> list2) {
        for (String id : list1) {
            if (list2.contains(id)) {
                return true; // 找到重复，直接返回true
            }
        }
        return false; // 遍历结束未找到重复，返回false
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d(TAG, "resultCode" + resultCode);
        LogUtil.d(TAG, "requestCode" + requestCode);
        if (resultCode == 100) {
            return;
        }
        if (resultCode == 200) {
            return;
        }
        if (requestCode == 1001) {
            List<AnimalMenuBean> animalMenuBeans1 = (List<AnimalMenuBean>) data.getExtras().getSerializable("data");
            List<String> addEarTagsList = new ArrayList<>();
            for (AnimalMenuBean animalMenuBean : animalMenuBeans1) {
                if (!"-".equals(animalMenuBean.EarTag)) {
                    addEarTagsList.add(animalMenuBean.EarTag);
                }
            }
            if (!animalMenuBeans.isEmpty()) {
                List<String> earTagsList = new ArrayList<>();
                for (AnimalMenuBean animalMenuBean : animalMenuBeans) {
                    if (!"-".equals(animalMenuBean.EarTag)) {
                        earTagsList.add(animalMenuBean.EarTag);
                    }
                }
                if (hasDuplicateId(addEarTagsList, earTagsList)) {
                    Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "输入耳标已存在动物清单，请重新添加"));
                    return;
                } else {
                    animalMenuBeans.addAll(animalMenuBeans1);
                }
            } else {
                animalMenuBeans.addAll(animalMenuBeans1);
            }

            if (!animalMenuBeans.isEmpty()) {
                LogUtil.d(TAG, "回来的数据" + animalMenuBeans.toString());
                binding.animalMuen.noDataIv.setVisibility(View.GONE);
                binding.animalMuen.recyclerAnimal.setVisibility(View.VISIBLE);
                animalMenuAdapter.refreshDataList(animalMenuBeans);
                binding.animalMuen.hejiTv.setText(animalMenuBeans.size() + "");
                if (!animalMenuBeans.isEmpty()) {
                    binding.animalMuen.hejiAllLl.setVisibility(View.VISIBLE);
                }
                int all = 0;
                int allNum = 0;
                for (AnimalMenuBean menuBean : animalMenuBeans) {
                    all = all + Integer.parseInt(menuBean.Weight);
                    allNum = allNum + Integer.parseInt(menuBean.ShuLiang);
                }
                binding.animalMuen.hejiTv.setText("合计:" + animalMenuBeans.size());
                binding.animalMuen.allZhongliangTv.setText("总重量:" + all + "kg");
                binding.verificationNumEt.setText(allNum + "");
                binding.animalMuen.allNumTv.setVisibility(View.VISIBLE);
                binding.animalMuen.allNumTv.setText("总数量：" + allNum + "");
                binding.grossWeightEt.setText(all + "");


                shouJiHuanCunBean.heYanShuLiang = binding.verificationNumEt.getText().toString();
                shouJiHuanCunBean.ZongZhongLiang = binding.grossWeightEt.getText().toString();

                //保存
                shouJiHuanCunBean.animalMenuBean = new ArrayList<>();
                for (AnimalMenuBean item : animalMenuBeans) {
                    ShouJiHuanCunBean.AnimalMenuBeanData animalMenuBeanData = new ShouJiHuanCunBean.AnimalMenuBeanData();
                    animalMenuBeanData.AnimalType = item.AnimalType;
                    animalMenuBeanData.AnimalName = item.AnimalName;
                    animalMenuBeanData.AnimalID = item.AnimalID;
                    animalMenuBeanData.EarTag = item.EarTag;
                    animalMenuBeanData.Weight = item.Weight;
                    animalMenuBeanData.ShuLiang = item.ShuLiang;
                    animalMenuBeanData.IsSow = item.ShiFouMuPig;
                    shouJiHuanCunBean.animalMenuBean.add(animalMenuBeanData);
                }
                ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
            }
        } else if (requestCode == 10) {
            PicBtnType = 3;
            String Ic_QianMing = data.getStringExtra("qianming");
            if (NetworkUtils.isNetAvailable(this)) {
                Bitmap sourBitmap = ImageLoader.convertFileToBitmap(Ic_QianMing);
                Bitmap WaterBitmap = saveWaterMask(CollectFillInActivity.this, 4, sourBitmap, beiAnBean.ApplyNo, beiAnBean.UserName, roleInfo.harmlessUser.name, binding.animalTypeEt.getText().toString(), animalMenuBeans.size() + "");
                String fileName = "IMG_" + new Date().getTime() + "shouyunyuan" + ".png";
                shouyunyuanUrl = WaterMaskUtil.saveQNext(WaterBitmap, CollectFillInActivity.this, fileName, 60);
                upLoadImg(shouyunyuanUrl);
            } else {
                Bitmap sourBitmap = ImageLoader.convertFileToBitmap(Ic_QianMing);
                Bitmap WaterBitmap = saveWaterMask(CollectFillInActivity.this, 4, sourBitmap, beiAnBean.ApplyNo, beiAnBean.UserName, roleInfo.harmlessUser.name, binding.animalTypeEt.getText().toString(), animalMenuBeans.size() + "");
                Bitmap bitmap = ImageLoader.compressQuality(WaterBitmap);
                String s = ImageLoader.saveToExternalStorageCollection(this, bitmap, "shouyunyuan", String.valueOf(DateTimeUtils.getSixNum()));
                collectionImageBean.shouyunyuanSignPath = s;
                GlideUtils.createGlideEngine().loadRounderImage(CollectFillInActivity.this, "file://" + s, binding.shouyunyuanQianming, 20);
            }

        } else if (requestCode == 11) {
            PicBtnType = 4;
            String YangZhiChangHu = data.getStringExtra("yangzhichanghu");
            if (NetworkUtils.isNetAvailable(this)) {
                Bitmap sourBitmap =ImageLoader.convertFileToBitmap(YangZhiChangHu);
                Bitmap WaterBitmap = saveWaterMask(CollectFillInActivity.this, 4, sourBitmap, beiAnBean.ApplyNo, beiAnBean.UserName, roleInfo.harmlessUser.name, binding.animalTypeEt.getText().toString(), animalMenuBeans.size() + "");
                String fileName = "IMG_" + new Date().getTime() + "yangzhihu" + ".png";
                String yangzhichang_url = WaterMaskUtil.saveQNext(WaterBitmap, CollectFillInActivity.this, fileName, 60);
                upLoadImg(yangzhichang_url);
            } else {
                Bitmap sourBitmap = ImageLoader.convertFileToBitmap(YangZhiChangHu);
                Bitmap WaterBitmap = saveWaterMask(CollectFillInActivity.this, 4, sourBitmap, beiAnBean.ApplyNo, beiAnBean.UserName, roleInfo.harmlessUser.name, binding.animalTypeEt.getText().toString(), animalMenuBeans.size() + "");
                Bitmap bitmap = ImageLoader.compressQuality(WaterBitmap);
                String s = ImageLoader.saveToExternalStorageCollection(this, bitmap, "yangzhichanghu", String.valueOf(DateTimeUtils.getSixNum()));
                collectionImageBean.xdrSignPath = s;
                GlideUtils.createGlideEngine().loadRounderImage(CollectFillInActivity.this, "file://" + s, binding.yangzhichangQianming, 20);
            }

        } else if (requestCode == 88) {
            String shenhejianduyuan = data.getStringExtra("shenhejianduyuan");
            Bitmap sourBitmap = ImageLoader.convertFileToBitmap(shenhejianduyuan);
            Bitmap WaterBitmap = saveWaterMask(CollectFillInActivity.this, 4, sourBitmap, beiAnBean.ApplyNo, beiAnBean.UserName, roleInfo.harmlessUser.name, binding.animalTypeEt.getText().toString(), animalMenuBeans.size() + "");
            String fileName = "IMG_" + new Date().getTime() + "shenhejianduyuan" + ".png";
            String yangzhichang_url = WaterMaskUtil.saveQNext(WaterBitmap, CollectFillInActivity.this, fileName, 60);
            PicBtnType = 5;
            upLoadImg(yangzhichang_url);
        } else if (requestCode == REQUEST_CODE_CAMERA) {//拍照识别回传照片
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        // recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                        LogUtil.i(TAG, "身份证File文件路径" + filePath);
                        showLoading("身份证照片上传中...");
                        upLoadImg(filePath);
                    }
                }
            }
        } else if (requestCode == REQUEST_CODE_BANKCARD) {
            String filePath = FileUtil.getSaveFileBank(getApplicationContext()).getAbsolutePath();
            showLoading("银行卡照片上传中...");
            upLoadImg(filePath);

        }
    }


    private void mAnimalDeathAdapter() {
        gridImageDeathAdapter.setOnItemClickListener(new GridImageDeathAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (!TextUtils.isEmpty(gridImageDeathAdapter.getData().get(position).getCompressPath())) {
                    PicActivity.start(CollectFillInActivity.this, gridImageDeathAdapter.getData().get(position).getCompressPath());
                } else {
                    PicActivity.start(CollectFillInActivity.this, gridImageDeathAdapter.getData().get(position).getPath());
                }


            }

            @Override
            public void openPicture() {
                // 进入相册
                PictureSelectionModel selectionModel = PictureSelector.create(CollectFillInActivity.this).openGallery(SelectMimeType.ofImage()).setSelectorUIStyle(PictureSelectorUtils.setStyle()).setImageEngine(imageEngine)
                        //.setCropEngine(PictureSelectorUtils.getCropFileEngine())
                        .setCompressEngine(PictureSelectorUtils.getCompressFileEngine()).setSandboxFileEngine(PictureSelectorUtils.getMeSandboxFileEngine()).setSelectLimitTipsListener(PictureSelectorUtils.getMeOnSelectLimitTips()).setSelectionMode(SelectModeConfig.MULTIPLE).setQuerySortOrder("").isDisplayTimeAxis(true).isOriginalControl(false).isDisplayCamera(true).isPreviewFullScreenMode(true).isPreviewZoomEffect(true).isPreviewImage(true).isMaxSelectEnabledMask(true).isDirectReturnSingle(true).setMaxSelectNum(9);
                //.setSelectedData(gridImageDeathAdapter.getData());
                forSelectResult(selectionModel, "death");

                TakePicturesAlone("death");
            }
        });
    }

    private void mAnimalShiTiAdapter() {
        imageShiTiAdapter.setOnItemClickListener(new GridImageShiTiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                // 预览图片、视频、音频
//                PictureSelector.create(CollectFillInActivity.this)
//                        .openPreview()
//                        .setImageEngine(imageEngine)
//                        .setSelectorUIStyle(PictureSelectorUtils.setStyle())
//                        .isPreviewFullScreenMode(false)
//                        .startActivityPreview(position, true, imageShiTiAdapter.getData());
                if (!TextUtils.isEmpty(imageShiTiAdapter.getData().get(position).getCompressPath())) {
                    PicActivity.start(CollectFillInActivity.this, imageShiTiAdapter.getData().get(position).getCompressPath());
                } else {
                    PicActivity.start(CollectFillInActivity.this, imageShiTiAdapter.getData().get(position).getPath());
                }
            }

            @Override
            public void openPicture() {

                // 进入相册
                PictureSelectionModel selectionModel = PictureSelector.create(CollectFillInActivity.this)
                        .openGallery(SelectMimeType.ofImage())
                        .setSelectorUIStyle(PictureSelectorUtils.setStyle())
                        .setImageEngine(imageEngine)
                        //.setCropEngine(PictureSelectorUtils.getCropFileEngine())
                        .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())
                        .setSandboxFileEngine(PictureSelectorUtils.getMeSandboxFileEngine())
                        .setSelectLimitTipsListener(PictureSelectorUtils.getMeOnSelectLimitTips())
                        .setSelectionMode(SelectModeConfig.MULTIPLE).setQuerySortOrder("")
                        .isDisplayTimeAxis(true)
                        .isOriginalControl(false)
                        .isDisplayCamera(true)
                        .isPreviewFullScreenMode(true)
                        .isPreviewZoomEffect(true).isPreviewImage(true)
                        .isMaxSelectEnabledMask(true)
                        .isDirectReturnSingle(true)
                        .setMaxSelectNum(9);
//                        .setSelectedData(imageShiTiAdapter.getData());
                forSelectResult(selectionModel, "ShiTi");
                TakePicturesAlone("ShiTi");

            }
        });
    }


    private void mAnimalZhuangCheAdapter() {
        imageZhuangCheAdapter.setOnItemClickListener(new GridImageZhuangCheAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // 预览图片、视频、音频
                if (!TextUtils.isEmpty(imageZhuangCheAdapter.getData().get(position).getCompressPath())) {
                    PicActivity.start(CollectFillInActivity.this, imageZhuangCheAdapter.getData().get(position).getCompressPath());
                } else {
                    PicActivity.start(CollectFillInActivity.this, imageZhuangCheAdapter.getData().get(position).getPath());
                }
            }

            @Override
            public void openPicture() {
                LogUtil.d(TAG, "装车");
                // 进入相册
                PictureSelectionModel selectionModel = PictureSelector.create(CollectFillInActivity.this).
                        openGallery(SelectMimeType.ofImage())
                        .setSelectorUIStyle(PictureSelectorUtils.setStyle())
                        .setImageEngine(imageEngine)
                        //.setCropEngine(PictureSelectorUtils.getCropFileEngine())
                        .setCompressEngine(PictureSelectorUtils.getCompressFileEngine()).
                        setSandboxFileEngine(PictureSelectorUtils.getMeSandboxFileEngine())
                        .setSelectLimitTipsListener(PictureSelectorUtils.getMeOnSelectLimitTips())
                        .setSelectionMode(SelectModeConfig.MULTIPLE).setQuerySortOrder("")
                        .isDisplayTimeAxis(true).isOriginalControl(false)
                        .isDisplayCamera(true)
                        .isPreviewFullScreenMode(true)
                        .isPreviewZoomEffect(true)
                        .isPreviewImage(true)
                        .isDirectReturnSingle(true)
                        .isMaxSelectEnabledMask(true)
                        .setMaxSelectNum(9);
                forSelectResult(selectionModel, "ZhuangChe");

                //TakePicturesAlone("ZhuangChe");
            }
        });
    }

    /**
     * 获取唯一单号
     */
    private void getDanHao() {
        binding.collectNumTv.setText(DateTimeUtils.getCollectionDanHao());
    }

    private void getXianChangJianDuYuanInfo() {
        HttpRequest.getXianChangJianDuYuan(CollectFillInActivity.this, String.valueOf(beiAnBean.Region.id), new CallBackLis<XianChangRenBean>() {
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
        String times = year + "年" + month + "月" + DAY_OF_MONTH + "日";
        NewSendSmsBean sendSmsBean = new NewSendSmsBean();
        sendSmsBean.phone = phone;
        sendSmsBean.template = new NewSendSmsBean.TemplateBean();
        sendSmsBean.template.farm = beiAnBean.UserName;
        sendSmsBean.template.userName = roleInfo.harmlessUser.name;
        sendSmsBean.template.datetime = times;
        sendSmsBean.template.animalAmount = binding.animalTypeEt.getText().toString() + binding.verificationNumEt.getText().toString() + "头";
        HttpRequest.sendNewSms(CollectFillInActivity.this, sendSmsBean, new CallBackLis<>() {
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

    //设置跳转过来得备案信息
    private void setBeiAnInfo() {
        RoleBean.DataBean.CarInfoBean carInfo = RoleSP.getInstance().getRoleInfo().carInfo;

        if (carInfo == null) {
            carInfo = new RoleBean.DataBean.CarInfoBean();
        }
        shouJiHuanCunBean.Mid = beiAnBean.Mid;
        ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);


        binding.declarationNumberTv.setText(beiAnBean.ApplyNo);//申报编号
        binding.farmerNameTv.setText(beiAnBean.UserName);
        binding.telTv.setText(beiAnBean.Mobile);
        binding.shenbaoNumTv.setText(beiAnBean.DieAmount);

//        if (ShouJISP.getInstance().getShouJiHuanCunInfo() == null) {
//            binding.baoxianLayout.idcardEt.setText(beiAnBean.IDCard);
//        } else if (ShouJISP.getInstance().getShouJiHuanCunInfo() != null && TextUtils.isEmpty(ShouJISP.getInstance().getShouJiHuanCunInfo().IDCardNum)) {
//            binding.baoxianLayout.idcardEt.setText(beiAnBean.IDCard);
//        }

        binding.baoxianLayout.idcardEt.setText(beiAnBean.IDCard);
        if (!TextUtils.isEmpty(beiAnBean.BankCardNo)) {
            binding.baoxianLayout.bankNumEt.setText(beiAnBean.BankCardNo);
            binding.baoxianLayout.depositAccountEt.setText(beiAnBean.BankCardNo);
        }

        if (!TextUtils.isEmpty(beiAnBean.ImgFiles.IdCardPic)) {
            if (!"null".equals(beiAnBean.ImgFiles.IdCardPic)) {
                IDCardMid = beiAnBean.ImgFiles.IdCardPic;
                collectionImageBean.idCardPath = beiAnBean.ImgFiles.IdCardPic;
                String IdCardUrl = UrlUtils.pic_url + IDCardMid;
                //TODO:2024-04-30 断网判断
                LogUtil.d(TAG, "IDCardMid" + IDCardMid);
                if (NetworkUtils.isNetAvailable(this)) {//断网情况
                    LogUtil.d(TAG, "IDCardMid" + IDCardMid);
                    Glide.with(this).load(IdCardUrl).apply(RequestOptions.bitmapTransform(new RoundedCorners(22))).into(binding.baoxianLayout.idcardIv);
                } else {
                    LogUtil.d(TAG, "IDCardMid111111111111111" + IDCardMid);
                    Glide.with(this).load("file://" + IDCardMid).apply(RequestOptions.bitmapTransform(new RoundedCorners(22))).into(binding.baoxianLayout.idcardIv);
                }
            } else {
                Glide.with(this).load(R.drawable.ic_default_iv).apply(RequestOptions.bitmapTransform(new RoundedCorners(22))).into(binding.baoxianLayout.idcardIv);
                LogUtil.d(TAG, "是否为空1111");
            }
        }
        if (!TextUtils.isEmpty(beiAnBean.ImgFiles.BankPic)) {
            if (!beiAnBean.ImgFiles.BankPic.equals("null")) {
                BankMid = beiAnBean.ImgFiles.BankPic;
                String BankUrl = UrlUtils.pic_url + BankMid;
                collectionImageBean.bankPath = beiAnBean.ImgFiles.BankPic;
                //TODO:2024-04-30 断网判断
                if (NetworkUtils.isNetAvailable(this)) {//断网情况
                    GlideUtils.createGlideEngine().loadRounderImage(this, BankUrl, binding.baoxianLayout.bankIv, 22);
                    GlideUtils.createGlideEngine().loadRounderImage(this, BankUrl, binding.baoxianLayout.depositIv, 22);
                } else {
                    GlideUtils.createGlideEngine().loadRounderImage(this, "file://" + BankMid, binding.baoxianLayout.bankIv, 22);
                }
            } else {
                Glide.with(this).load(R.drawable.ic_default_iv).into(binding.baoxianLayout.bankIv);
            }
        }
        if (!TextUtils.isEmpty(beiAnBean.ApplyCategory)) {
            if (beiAnBean.ApplyCategory.equals("1")) {
                binding.declarationTypeTv.setText("集中存储点");
            } else if (beiAnBean.ApplyCategory.equals("2")) {
                binding.declarationTypeTv.setText("无害化处理厂");
            } else {
                binding.declarationTypeTv.setText("自行处理");
            }
        }
        binding.declarationAddressTv.setText(beiAnBean.Region.RegionFullName);
        binding.detailAddressTv.setText(beiAnBean.ApplyAddress);
        //setHuanCun();
    }


    private void upLoadCollect() {
        CollectInfoData collectInfoData = new CollectInfoData();
        collectInfoData.region = new CollectInfoData.RegionBean();
        collectInfoData.farmMid = beiAnBean.FarmMid;
        collectInfoData.region.id = beiAnBean.Region.id;
        collectInfoData.region.RI1 = beiAnBean.Region.RI1;
        collectInfoData.region.RI2 = beiAnBean.Region.RI2;
        collectInfoData.region.RI3 = beiAnBean.Region.RI3;
        collectInfoData.region.RI4 = beiAnBean.Region.RI4;
        collectInfoData.region.RI5 = beiAnBean.Region.RI5;
        collectInfoData.region.RegionCode = beiAnBean.Region.RegionCode;
        collectInfoData.region.RegionName = beiAnBean.Region.RegionName;
        collectInfoData.region.RegionLevel = beiAnBean.Region.RegionLevel;
        collectInfoData.region.RI1RegionName = beiAnBean.Region.RI1RegionName;
        collectInfoData.region.RI2RegionName = beiAnBean.Region.RI2RegionName;
        collectInfoData.region.RI3RegionName = beiAnBean.Region.RI3RegionName;
        collectInfoData.region.RI4RegionName = beiAnBean.Region.RI4RegionName;
        collectInfoData.region.RI5RegionName = beiAnBean.Region.RI5RegionName;
        collectInfoData.region.RegionFullName = beiAnBean.Region.RegionFullName;
        collectInfoData.region.RegionParentID = beiAnBean.Region.RegionParentID;
        collectInfoData.bankType = mBankCheck;
        LogUtil.d(TAG, "选择的动物" + animalBeanChoose);
        collectInfoData.applyGUID = beiAnBean.Mid;
        collectInfoData.latitude = AddressSP.getInstance().getLatitude();
        collectInfoData.longitude = AddressSP.getInstance().getLongitude();

        RoleBean.DataBean roleInfo = RoleSP.getInstance().getRoleInfo();
        collectInfoData.creatorId = roleInfo.harmlessUser.userId;
        collectInfoData.collectType = 1;

        LogUtil.d(TAG, "获取缓存" + roleInfo.toString());
        collectInfoData.worker = new CollectInfoData.WokerBean();
        collectInfoData.worker.Mid = roleInfo.harmlessUser.mid;
        collectInfoData.worker.Name = roleInfo.harmlessUser.name;
        collectInfoData.worker.Mobile = roleInfo.harmlessUser.mobile;
        LogUtil.d(TAG, "mid" + roleInfo.harmlessUser.mid);
        LogUtil.d(TAG, "Name" + roleInfo.harmlessUser.name);
        LogUtil.d(TAG, "mobile" + roleInfo.harmlessUser.mobile);
        LogUtil.d(TAG, "worker" + collectInfoData.worker);

        collectInfoData.carInfo = new CollectInfoData.CarInfoBean();
        collectInfoData.carInfo.Mid = roleInfo.carInfo.id;
        collectInfoData.carInfo.Name = roleInfo.carInfo.Name;
        collectInfoData.factoryGUID = roleInfo.harmlessUser.factory.Mid;
        collectInfoData.collectNo = binding.collectNumTv.getText().toString();


        collectInfoData.animal = new CollectInfoData.AnimalBean();
        collectInfoData.animal.key = animalBeanChoose.ID;
        collectInfoData.animal.AnimalName = animalBeanChoose.AnimalName;

        collectInfoData.animalUnit = new CollectInfoData.AnimalUnitBean();
        collectInfoData.animalUnit.key = unitBeanChoose.ID;
        collectInfoData.animalUnit.UnitName = unitBeanChoose.UnitName;


        collectInfoData.dieAmount = binding.verificationNumEt.getText().toString();//核验数量
        collectInfoData.dieWeight = binding.grossWeightEt.getText().toString();//总重量
        collectInfoData.scale = Integer.parseInt(binding.inventoryRelTimeEt.getText().toString());//存栏量
        if (XiaoDuCheck == 1) {
            collectInfoData.isDisinfect = true;
        } else {
            collectInfoData.isDisinfect = false;
        }
        collectInfoData.disinfect = binding.disinfectantsEt.getText().toString();
        if (YiBingCheck == 1) {
            collectInfoData.disease = true;
        } else {
            collectInfoData.disease = false;
        }
        collectInfoData.dieReasonType = String.valueOf(SiWangYaunYinCheck);

        collectInfoData.insuranceCompany = new CollectInfoData.InsuranceCompanyBean();
        if (BaoXianCheck == 1) {
            collectInfoData.isInsurance = true;
            collectInfoData.insuranceCompany.key = baoXianGongSiBeanChoose.ID;
            collectInfoData.insuranceCompany.InsuranceCompanyName = baoXianGongSiBeanChoose.BaoXianGongSiName;
        } else {
            collectInfoData.isInsurance = false;
            collectInfoData.insuranceCompany.key = "";
            collectInfoData.insuranceCompany.InsuranceCompanyName = "";
        }

        collectInfoData.idcard = binding.baoxianLayout.idcardEt.getText().toString();

        if (mBankCheck==1){
            collectInfoData.bankCardNo = binding.baoxianLayout.bankNumEt.getText().toString();
        }else {
            collectInfoData.bankCardNo = binding.baoxianLayout.depositAccountEt.getText().toString();
        }



        if (!TextUtils.isEmpty(binding.feedbackEt.getText().toString())) {
            collectInfoData.remark = binding.feedbackEt.getText().toString();
        }

        collectInfoData.imgFiles = new CollectInfoData.ImgFilesBean();

        if (NetworkUtils.isNetAvailable(this)) {
            collectInfoData.imgFiles.IdCardPic = IDCardMid;
            collectInfoData.imgFiles.BankPic = BankMid;
            collectInfoData.imgFiles.SiChuPicList = new ArrayList<>();
            for (int i = 0; i < SiChuErBiaoPicList.size(); i++) {
                CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
                imgMidBean.Mid = SiChuErBiaoPicList.get(i).toString();
                collectInfoData.imgFiles.SiChuPicList.add(imgMidBean);
            }

            collectInfoData.imgFiles.ShiTiPicList = new ArrayList<>();
            for (int i = 0; i < ShiTiPicList.size(); i++) {
                CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
                imgMidBean.Mid = ShiTiPicList.get(i).toString();
                collectInfoData.imgFiles.ShiTiPicList.add(imgMidBean);
            }

            collectInfoData.imgFiles.ZhuangChePicList = new ArrayList<>();
            for (int i = 0; i < ZHuangCheList.size(); i++) {
                CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
                imgMidBean.Mid = ZHuangCheList.get(i).toString();
                collectInfoData.imgFiles.ZhuangChePicList.add(imgMidBean);
            }
            collectInfoData.imgFiles.ShouYunYuanPic = ShouYunYuanMid;
            collectInfoData.imgFiles.YangZhiChangHuPic = YangZhiHuMid;
            collectInfoData.imgFiles.CollectCertPic = ZhiziMid;
        } else {
            collectInfoData.imgFiles.IdCardPic = collectionImageBean.idCardPath;
            collectInfoData.imgFiles.BankPic = collectionImageBean.bankPath;
            collectInfoData.imgFiles.SiChuPicList = new ArrayList<>();
            for (int i = 0; i < collectionImageBean.siChuListPath.size(); i++) {
                CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
                imgMidBean.Mid = collectionImageBean.siChuListPath.get(i).toString();
                collectInfoData.imgFiles.SiChuPicList.add(imgMidBean);
            }


            collectInfoData.imgFiles.ShiTiPicList = new ArrayList<>();
            for (int i = 0; i < collectionImageBean.siTiListPath.size(); i++) {
                CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
                imgMidBean.Mid = collectionImageBean.siTiListPath.get(i).toString();
                collectInfoData.imgFiles.ShiTiPicList.add(imgMidBean);
            }

            collectInfoData.imgFiles.ZhuangChePicList = new ArrayList<>();
            for (int i = 0; i < collectionImageBean.zhaungCheListPath.size(); i++) {
                CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
                imgMidBean.Mid = collectionImageBean.zhaungCheListPath.get(i).toString();
                collectInfoData.imgFiles.ZhuangChePicList.add(imgMidBean);
            }
            collectInfoData.imgFiles.ShouYunYuanPic = collectionImageBean.shouyunyuanSignPath;
            collectInfoData.imgFiles.YangZhiChangHuPic = collectionImageBean.xdrSignPath;
            collectInfoData.imgFiles.CollectCertPic = collectionImageBean.zhizhiDanJu;

        }


        List<String> ids = new ArrayList<>();
        for (AnimalMenuBean animalMenuBean : animalMenuBeans) {
            if (!"-".equals(animalMenuBean.EarTag)) {
                ids.add(animalMenuBean.EarTag);
            }
        }
        Set<String> uniqueIds = new HashSet<>(ids);
        // 如果转换后的Set大小小于原List大小，说明有重复
        if (uniqueIds.size() < ids.size()) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "动物清单耳标重复，请修改"));
            return;
        }



        collectInfoData.itemDatas = new ArrayList<>();
        for (AnimalMenuBean animalMenuBean : animalMenuBeans) {
            CollectInfoData.DataItemBean dataItemBean = new CollectInfoData.DataItemBean();
            dataItemBean.Name = animalMenuBean.AnimalName;
            dataItemBean.AnimalID = animalMenuBean.AnimalID;
            dataItemBean.BodyWeight = animalMenuBean.Weight;
            dataItemBean.EarTagNo = animalMenuBean.EarTag;
            dataItemBean.AnimalType = animalMenuBean.AnimalType;
            dataItemBean.IsSow = animalMenuBean.ShiFouMuPig;
            dataItemBean.Amount = animalMenuBean.ShuLiang;
            collectInfoData.itemDatas.add(dataItemBean);
        }


        ConfigSP.getInstance().setXiaoDu(binding.disinfectantsEt.getText().toString());
        LogUtil.d(TAG, "上传的数据" + collectInfoData.toString());


        collectInfoData.xdrName = beiAnBean.UserName;
        collectInfoData.xdrMobile = beiAnBean.Mobile;
        collectInfoData.xdrAddress = beiAnBean.ApplyAddress;

        collectInfoData.isFoster =mIsFosterCare;
        if (mIsFosterCare){
            collectInfoData.fosterCareName = ShouJISP.getInstance().getFosterCareName();;
        }
        LogUtil.d(TAG, "collectInfoData"+ collectInfoData.toString());
        if (NetworkUtils.isNetAvailable(this)) {
            if (NetworkUtils.isNetAvailable(this)) {
                showLoading("正在上传中...");
                HttpRequest.getCollect(CollectFillInActivity.this, collectInfoData, new CallBackLis<StatusMeBean>() {
                    @Override
                    public void onSuccess(String method, StatusMeBean content) {
                        LogUtil.d(TAG, content.toString());
                        hideLoading();
                        if (content.code == 200) {
                            getXianChangJianDuYuanInfo();
                            showSuccessDiaolog();
                            new Handler().postDelayed(() -> {
                                                                exitDialogSuccess.dismiss();
//                            Intent intent = new Intent();
//                            intent.putExtra("success", 666);
//                            setResult(RESULT_OK, intent);
                                finish();
                            }, 2000);
                        } else {
                            Objects.requireNonNull(RxToast.error(CollectFillInActivity.this, content.msg));
                        }
                    }

                    @Override
                    public void onFailure(String method, String error) {
                        hideLoading();
                    }
                });
            } else {

                LogUtil.d(TAG, "上传的数据" + collectInfoData.toString());

            }
        } else {
            showNetwork(collectInfoData);
        }
    }


    /**
     * filePath 上传图片
     *
     * @param filePath
     */
    private void upLoadImg(String filePath) {
        showLoading("正在上传中...");
        HttpRequest.upLoadImg(CollectFillInActivity.this, filePath, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d(TAG, "上传图片" + content.toString());
                if (content.Status == 0) {
                    Objects.requireNonNull(RxToast.success(CollectFillInActivity.this, "上传成功"));
                    if (PicBtnType == 1) {
                        IDCardMid = content.Result;
                        shouJiHuanCunBean.imgFilesBeanData.IdCardPic = IDCardMid;
                        ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
                        GlideUtils.createGlideEngine().loadRounderImage(CollectFillInActivity.this, UrlUtils.pic_url + content.Result, binding.baoxianLayout.idcardIv, 20);
                        if (NetworkUtils.isNetworkAvalible(CollectFillInActivity.this)) {//有网络去识别SDK
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                showLoading("身份证识别中...");
                                recIDCard(filePath);
                            }, 200); // 延时1秒
                        }
                    } else if (PicBtnType == 2) {
                        BankMid = content.Result;
                        shouJiHuanCunBean.imgFilesBeanData.BankPic = BankMid;
                        ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
                        GlideUtils.createGlideEngine().loadRounderImage(CollectFillInActivity.this, UrlUtils.pic_url + content.Result, binding.baoxianLayout.bankIv, 20);

                        if (NetworkUtils.isNetworkAvalible(CollectFillInActivity.this)) {//有网络去识别SDK
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                showLoading("银行卡识别中...");
                                recBankCard(filePath);
                            }, 200); // 延时1秒
                        }
                    }else if(PicBtnType==10){
                        BankMid = content.Result;
                        GlideUtils.createGlideEngine().loadRounderImage(CollectFillInActivity.this, UrlUtils.pic_url + content.Result, binding.baoxianLayout.depositIv, 20);
                    }else if (PicBtnType == 3) {
                        ShouYunYuanMid = content.Result;
                        shouJiHuanCunBean.imgFilesBeanData.ShouYunYuanPic = ShouYunYuanMid;
                        ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
                        GlideUtils.createGlideEngine().loadRounderImage(CollectFillInActivity.this, UrlUtils.pic_url + content.Result, binding.shouyunyuanQianming, 20);

                    } else if (PicBtnType == 4) {
                        YangZhiHuMid = content.Result;
                        shouJiHuanCunBean.imgFilesBeanData.YangZhiChangHuPic = YangZhiHuMid;
                        ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
                        GlideUtils.createGlideEngine().loadRounderImage(CollectFillInActivity.this, UrlUtils.pic_url + content.Result, binding.yangzhichangQianming, 20);
                    } else if (PicBtnType == 999) {
                        ZhiziMid = content.Result;
                        GlideUtils.createGlideEngine().loadRounderImage(CollectFillInActivity.this, UrlUtils.pic_url + content.Result, binding.zhizidanjuIv, 20);
                        shouJiHuanCunBean.imgFilesBeanData.CollectCertPic = ZhiziMid;
                        ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
                        addXLog(1, 888, "");
                    }
                    hideLoading();
                } else {
                    hideLoading();
                    addXLog(2, 888, "");
                    Objects.requireNonNull(RxToast.error(CollectFillInActivity.this, content.Result.toString()));
                }

            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                addXLog(2, 888, error);
            }
        });
    }


    private void upLoadImgBatch(List<String> filePath) {
        showLoading("正在上传中...");
        HttpRequest.upLoadBatchImg(CollectFillInActivity.this, filePath, new CallBackLis<BatchImgBean>() {
            @Override
            public void onSuccess(String method, BatchImgBean content) {
                LogUtil.d(TAG, "上传图片批量" + content.toString());
                if (content.status == 0) {
                    Objects.requireNonNull(RxToast.success(CollectFillInActivity.this, "上传成功"));
                    File myFolder = new File(WaterMaskUtil.SavaPhotoFile);
                    WaterMaskUtil.deleteDirectory(myFolder);
                    //TODO:"判断返回的id是否有重复，如果有提示" 2024-01-30
                    if (PicBtnType == 5) {  //死畜耳标照片
                        if (content.result.size() != new HashSet<>(content.result).size()) {
                            hasDuplicatesSC = true;
                        }
                        for (String path : content.result) {
                            SiChuErBiaoPicList.add(path);
                            shouJiHuanCunBean.imgFilesBeanData.SiChuPicList.add(path);
                        }
                        ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
                        LogUtil.d(TAG, "上传的元数据" + SiChuErBiaoPicList.toString() + SiChuErBiaoPicList.size());

                        gridImageDeathAdapter.getData().clear();

                        for (String path : SiChuErBiaoPicList) {
                            LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + path, PictureMimeType.MIME_TYPE_JPEG);
                            LogUtil.d("lzx------->", localMedia.toString());
                            gridImageDeathAdapter.getData().add(localMedia);

                        }
                        gridImageDeathAdapter.notifyDataSetChanged();
                        addXLog(1, 666, "");

                    } else if (PicBtnType == 6) { //实体
                        if (content.result.size() != new HashSet<>(content.result).size()) {
                            hasDuplicatesST = true;
                        }

                        for (String path : content.result) {
                            ShiTiPicList.add(path);
                            shouJiHuanCunBean.imgFilesBeanData.ShiTiPicList.add(path);
                        }
                        ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
                        imageShiTiAdapter.getData().clear();
                        for (String path : ShiTiPicList) {
                            LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + path, PictureMimeType.MIME_TYPE_JPEG);
                            imageShiTiAdapter.getData().add(localMedia);
                        }
                        imageShiTiAdapter.notifyDataSetChanged();
                        addXLog(1, 666, "");

                    } else if (PicBtnType == 7) {
                        LogUtil.d("lzx------>","上传装车之后");
                        if (content.result.size() != new HashSet<>(content.result).size()) {
                            hasDuplicatesZC = true;
                        }
                        for (String path : content.result) {
                            ZHuangCheList.add(path);
                            shouJiHuanCunBean.imgFilesBeanData.ZhuangChePicList.add(path);
                        }
                        ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
                        imageZhuangCheAdapter.getData().clear();
                        for (String path : ZHuangCheList) {
                            LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + path, PictureMimeType.MIME_TYPE_JPEG);
                            imageZhuangCheAdapter.getData().add(localMedia);
                        }
                        imageZhuangCheAdapter.notifyDataSetChanged();
                        addXLog(1, 666, "");
                        LogUtil.d("lzx------>","上传装车之后666");
                    }
                    hideLoading();
                } else {
                    addXLog(2, 666, "");
                    Objects.requireNonNull(RxToast.error(CollectFillInActivity.this, "上传失败~"));
                }

            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                addXLog(2, 666, error);
            }
        });
    }


    public void addXLog(int UploadStatus, int UploadType, String ErrorMessage) {
        LogData logData = new LogData();
        logData.UploadTime = DateTimeUtils.getNowTimes();
        logData.CollectionName = UserDataSP.getInstance().getUserInfo().Result.name;
        logData.CollectionNumber = binding.collectNumTv.getText().toString();
        logData.PicType = PicBtnType;
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


    private void setUnitSp(String id, String name) {
        shouJiHuanCunBean.unitData = new ShouJiHuanCunBean.UnitData();
        shouJiHuanCunBean.unitData.ID = id;
        shouJiHuanCunBean.unitData.UnitName = name;
        unitBeanChoose = new UnitBean(id, name);
        ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
    }

    private void showAnimalDialog() {
        List<String> listString = new ArrayList<>();
        for (AnimalBean animalBean : animalBeanList) {
            listString.add(animalBean.AnimalName);
        }
        pvCustomOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {

            animalBeanChoose = animalBeanList.get(options1);
            binding.animalTypeEt.setText(animalBeanList.get(options1).AnimalName);

            shouJiHuanCunBean.animalData = new ShouJiHuanCunBean.AnimalData();
            shouJiHuanCunBean.animalData.ID = animalBeanList.get(options1).ID;
            shouJiHuanCunBean.animalData.AnimalName = animalBeanList.get(options1).AnimalName;
            ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);

            if (binding.animalTypeEt.getText().toString().equals("猪") || binding.animalTypeEt.getText().toString().equals("牛") || binding.animalTypeEt.getText().toString().equals("其他")) {
                binding.unitTypeEt.setText("头");
                selectUnitPos = 0;
                setUnitSp("1301", "头");

            }
            if (binding.animalTypeEt.getText().toString().equals("羊") || binding.animalTypeEt.getText().toString().equals("兔")) {
                binding.unitTypeEt.setText("只");
                selectUnitPos = 1;
                setUnitSp("1302", "只");
            }
            if (binding.animalTypeEt.getText().toString().equals("鸡") || binding.animalTypeEt.getText().toString().equals("鸭") || binding.animalTypeEt.getText().toString().equals("鹅")) {
                binding.unitTypeEt.setText("羽");
                selectUnitPos = 2;
                setUnitSp("1304", "羽");
            }
            if (binding.animalTypeEt.getText().toString().equals("鱼")) {
                binding.unitTypeEt.setText("尾");
                selectUnitPos = 3;
                setUnitSp("1313", "尾");
            }

        }).setLayoutRes(R.layout.custom_dongwu_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    tvSubmit.setOnClickListener(v1 -> {
                        pvCustomOptions.returnData();
                        pvCustomOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> pvCustomOptions.dismiss());

                }).isDialog(false).setContentTextSize(18)//滚轮文字大小
                .setLineSpacingMultiplier(2.2f).build();
        pvCustomOptions.setPicker(listString);//添加数据
        pvCustomOptions.show();
    }


    private void showUnitDialog() {
        List<UnitBean> unitBeanList = new ArrayList<>();
        unitBeanList.add(new UnitBean("1301", "头"));//0
        unitBeanList.add(new UnitBean("1302", "只"));//1
        unitBeanList.add(new UnitBean("1304", "羽"));//2
        unitBeanList.add(new UnitBean("1313", "尾"));//3

        List<String> listString = new ArrayList<>();
        for (UnitBean unitBean : unitBeanList) {
            listString.add(unitBean.UnitName);
        }
        UnitpvCustomOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {

            unitBeanChoose = unitBeanList.get(options1);
            binding.unitTypeEt.setText(unitBeanList.get(options1).UnitName);

            shouJiHuanCunBean.unitData = new ShouJiHuanCunBean.UnitData();
            shouJiHuanCunBean.unitData.ID = unitBeanList.get(options1).ID;
            shouJiHuanCunBean.unitData.UnitName = unitBeanList.get(options1).UnitName;
            ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);

        }).setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    tvSubmit.setOnClickListener(v1 -> {
                        UnitpvCustomOptions.returnData();
                        UnitpvCustomOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> UnitpvCustomOptions.dismiss());

                }).isDialog(false).setContentTextSize(18).setLineSpacingMultiplier(2.2f)//滚轮文字大小
                .setSelectOptions(selectUnitPos).build();
        UnitpvCustomOptions.setPicker(listString);//添加数据
        UnitpvCustomOptions.show();
    }


    private void showBaoXianGongSiDialog() {
        List<BaoXianGongSiBean> baoXianGongSiBeanList = new ArrayList<>();
        baoXianGongSiBeanList.add(new BaoXianGongSiBean("PICC", "中国人民保险"));
        baoXianGongSiBeanList.add(new BaoXianGongSiBean("CICP", "中华联合保险"));
        baoXianGongSiBeanList.add(new BaoXianGongSiBean("PAIC", "平安保险"));
        baoXianGongSiBeanList.add(new BaoXianGongSiBean("CPIC", "太平洋保险"));
        baoXianGongSiBeanList.add(new BaoXianGongSiBean("TPIC", "太平保险"));
        baoXianGongSiBeanList.add(new BaoXianGongSiBean("TSHIC", "泰山保险"));
        baoXianGongSiBeanList.add(new BaoXianGongSiBean("GPIC", "人寿保险"));
        baoXianGongSiBeanList.add(new BaoXianGongSiBean("AHIC ", "安华保险"));
        baoXianGongSiBeanList.add(new BaoXianGongSiBean("ZYIC", "中原保险"));
        baoXianGongSiBeanList.add(new BaoXianGongSiBean("ZHBX", "中航安盟财产保险"));
        baoXianGongSiBeanList.add(new BaoXianGongSiBean("TJCC", "锦泰财产保险"));
        List<String> listString = new ArrayList<>();
        for (BaoXianGongSiBean baoXianGongSiBean : baoXianGongSiBeanList) {
            listString.add(baoXianGongSiBean.BaoXianGongSiName);
        }
        BaoXiaopvCustomOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {

            baoXianGongSiBeanChoose = baoXianGongSiBeanList.get(options1);
            binding.baoxianLayout.insuranceCompanyEt.setText(baoXianGongSiBeanList.get(options1).BaoXianGongSiName);


            shouJiHuanCunBean.BaoXiaoGongSi = new ShouJiHuanCunBean.BaoXiaoGongSiBean();
            shouJiHuanCunBean.BaoXiaoGongSi.InsuranceCompanyName = baoXianGongSiBeanList.get(options1).BaoXianGongSiName;
            shouJiHuanCunBean.BaoXiaoGongSi.key = baoXianGongSiBeanList.get(options1).ID;
            ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);

        }).setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    tvSubmit.setOnClickListener(v1 -> {
                        BaoXiaopvCustomOptions.returnData();
                        BaoXiaopvCustomOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> BaoXiaopvCustomOptions.dismiss());

                }).isDialog(false).setContentTextSize(18).setLineSpacingMultiplier(2.2f)//滚轮文字大小
                .build();
        BaoXiaopvCustomOptions.setPicker(listString);//添加数据
        BaoXiaopvCustomOptions.show();
    }

    private void showBankNameDialog() {

        List<String> listString = new ArrayList<>();
        for (BankBean bankBean : bankBeansList) {
            listString.add(bankBean.bankName);
        }
        BankpvCustomOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            bankBeanChoose = bankBeansList.get(options1);
            binding.baoxianLayout.bankNameEt.setText(bankBeansList.get(options1).bankName);

            shouJiHuanCunBean.bankName = bankBeansList.get(options1).bankName;
            ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);

        }).setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    tvSubmit.setOnClickListener(v1 -> {
                        BankpvCustomOptions.returnData();
                        BankpvCustomOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> BankpvCustomOptions.dismiss());

                }).isDialog(false).setContentTextSize(18).setLineSpacingMultiplier(2.2f)//滚轮文字大小
                .build();
        BankpvCustomOptions.setPicker(listString);//添加数据
        BankpvCustomOptions.show();
    }

    private void showXiaoDuDialog() {
        List<String> listString = new ArrayList<>();
        listString.add("三氯异氰尿酸粉");
        listString.add("月苄三钾氯铵溶液");
        listString.add("聚维酮碘");
        listString.add("戊二醛");
        listString.add("二氧化氯");
        listString.add("次氯酸纳");

        XiaoDupvCustomOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.disinfectantsEt.setText(listString.get(options1));

        }).setLayoutRes(R.layout.custom_unit_select, v -> {
            final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
            final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
            final TextView title_tv = (TextView) v.findViewById(R.id.title_tv);
            title_tv.setText("消毒药品");
            tvSubmit.setOnClickListener(v1 -> {
                XiaoDupvCustomOptions.returnData();
                XiaoDupvCustomOptions.dismiss();
            });
            iv_cancel.setOnClickListener(v12 -> XiaoDupvCustomOptions.dismiss());

        }).isDialog(false).setContentTextSize(18).setLineSpacingMultiplier(2.2f).build();

        XiaoDupvCustomOptions.setPicker(listString);//添加数据
        XiaoDupvCustomOptions.show();
    }


    private boolean checkInfo() {
        if (TextUtils.isEmpty(binding.animalTypeEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请选择动物畜种"));
            return false;
        }
        if (TextUtils.isEmpty(binding.unitTypeEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请选择单位"));
            return false;
        }
        if (TextUtils.isEmpty(binding.verificationNumEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请输入核验数量"));
            return false;
        }
        if (TextUtils.isEmpty(binding.grossWeightEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请输入总重量"));
            return false;
        }

        if (TextUtils.isEmpty(binding.inventoryRelTimeEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请输入实时存栏量"));
            return false;
        }

        if (XiaoDuCheck == 0) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请选择是否消毒"));
            return false;
        }
        if (TextUtils.isEmpty(binding.disinfectantsEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请输入消毒药品"));
            return false;
        }
        if (YiBingCheck == 0) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请选择是否是重大疫病"));
            return false;
        }
        if (SiWangYaunYinCheck == 0) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请选择死亡原因"));
            return false;
        }
        if (BaoXianCheck == 0) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请选择是否购买保险"));
            return false;
        }
        if (BaoXianCheck == 1 && TextUtils.isEmpty(binding.baoxianLayout.insuranceCompanyEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请选择保险公司"));
            return false;
        }
        if (TextUtils.isEmpty(binding.baoxianLayout.idcardEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请输入身份证号"));
            return false;
        }
        if (!IdCardUtils.isValidatedAllIdcard(binding.baoxianLayout.idcardEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请输入正确身份证号"));
            return false;
        }
        if (NetworkUtils.isNetAvailable(this)) {
            if (TextUtils.isEmpty(IDCardMid)) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请上传身份证照片"));
                return false;
            }
        } else {
            if (TextUtils.isEmpty(collectionImageBean.idCardPath)) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请上传身份证照片"));
                return false;
            }
        }

        if (mBankCheck==1){
            if (TextUtils.isEmpty(binding.baoxianLayout.bankNumEt.getText().toString())) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请输入银行卡号"));
                return false;
            }
        }
        if (mBankCheck==2){
            if (TextUtils.isEmpty(binding.baoxianLayout.depositAccountEt.getText().toString())) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请输入基本存款账号号码"));
                return false;
            }
        }
        if (mBankCheck==1){
            if (NetworkUtils.isNetAvailable(this)) {
                if (TextUtils.isEmpty(BankMid)) {
                    Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请上传银行卡照片"));
                    return false;
                }
            } else {
                if (TextUtils.isEmpty(collectionImageBean.bankPath)) {
                    Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请上传银行卡照片"));
                    return false;
                }
            }
        }
        if (mBankCheck==2){
            if (NetworkUtils.isNetAvailable(this)) {
                if (TextUtils.isEmpty(BankMid)) {
                    Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请上传基本存款账户图片"));
                    return false;
                }
            }
        }


        if (animalMenuBeans.isEmpty()) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请先添加动物清单信息"));
            return false;
        }


        if (NetworkUtils.isNetAvailable(this)) {
            if (SiChuErBiaoPicList.isEmpty()) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请先上传死畜耳标照片"));
                return false;
            }
        } else {
            if (collectionImageBean.siChuListPath.isEmpty()) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请先上传死畜耳标照片"));
                return false;
            }
        }


        if (NetworkUtils.isNetAvailable(this)) {
            if (ShiTiPicList.isEmpty()) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请先上传死畜尸体照片"));
                return false;
            }
        } else {
            if (collectionImageBean.siTiListPath.isEmpty()) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请先上传死畜尸体照片"));
                return false;
            }
        }


        if (NetworkUtils.isNetAvailable(this)) {
            if (ZHuangCheList.isEmpty()) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请先上传装车照片"));
                return false;
            }
        } else {
            if (collectionImageBean.zhaungCheListPath.isEmpty()) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请先上传装车照片"));
                return false;
            }
        }


        if (NetworkUtils.isNetAvailable(this)) {
            if (TextUtils.isEmpty(ZhiziMid)) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请上传纸质单据照片"));
                return false;
            }
        } else {
            if (TextUtils.isEmpty(collectionImageBean.zhizhiDanJu)) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请上传纸质单据照片"));
                return false;
            }
        }


        if (NetworkUtils.isNetAvailable(this)) {
            if (TextUtils.isEmpty(ShouYunYuanMid)) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请上传收运员签名"));
                return false;
            }
        } else {
            if (TextUtils.isEmpty(collectionImageBean.shouyunyuanSignPath)) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请上传收运员签名"));
                return false;
            }
        }

        if (NetworkUtils.isNetAvailable(this)) {
            if (TextUtils.isEmpty(YangZhiHuMid)) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请上传养殖场户签名"));
                return false;
            }
        } else {
            if (TextUtils.isEmpty(collectionImageBean.xdrSignPath)) {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "请上传养殖场户签名"));
                return false;
            }
        }

        if (hasDuplicatesSC) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "死畜耳标照片重复，请手动删除重新提交"));
            return false;
        }
        if (hasDuplicatesST) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "死畜尸体照片重复，请手动删除重新提交"));
            return false;
        }
        if (hasDuplicatesZC) {
            Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "死畜装车照片重复，请手动删除重新提交"));
            return false;
        }

        return true;
    }


    private void showSuccessDiaolog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_success, null);
        exitDialogSuccess = new AlertDialog.Builder(this).create();
        exitDialogSuccess.setView(view);
        exitDialogSuccess.setCanceledOnTouchOutside(false);
        exitDialogSuccess.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        exitDialogSuccess.show();
        WindowManager.LayoutParams params = exitDialogSuccess.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogSuccess.getWindow().setAttributes(params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void EdittextOn() {

        binding.inventoryRelTimeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtil.d("lzx-----》", "存栏量" + s.toString());
                shouJiHuanCunBean.ShiShiCunLanLiang = s.toString();
                ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
            }
        });

        binding.baoxianLayout.idcardEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtil.d("lzx-----》", "身份证号" + s.toString());
                shouJiHuanCunBean.IDCardNum = s.toString();
                ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
            }
        });


        binding.baoxianLayout.bankNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                shouJiHuanCunBean.bankNum = s.toString();
                ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
            }
        });


        binding.disinfectantsEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                shouJiHuanCunBean.XiaoDuYaoPin = s.toString();
                ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
            }
        });

        binding.feedbackEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                shouJiHuanCunBean.feedBook = s.toString();
                ShouJISP.getInstance().setShouJiHuanCunBeanInfo(shouJiHuanCunBean);
            }
        });
    }

    private void recBankCard(String filePath) {
        BankCardParams param = new BankCardParams();
        param.setImageFile(new File(filePath));
        OCR.getInstance(this).recognizeBankCard(param, new OnResultListener<>() {
            @Override
            public void onResult(BankCardResult result) {
                if (result != null) {
                    hideLoading();
                    showBankInfoDialog(result);
                    deleteLocalPhoto(filePath);
                    LogUtil.i(TAG, result.toString());
                }
            }

            @Override
            public void onError(OCRError error) {
                LogUtil.i(TAG, error.getMessage());
            }
        });
    }

    private void recIDCard(String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(IDCardParams.ID_CARD_SIDE_FRONT);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);
        param.setDetectRisk(true);
        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    hideLoading();
                    showCardInfoDialog(result);
                    deleteLocalPhoto(filePath);
                    LogUtil.i(TAG, result.toString());
                }
            }

            @Override
            public void onError(OCRError error) {
                LogUtil.i(TAG, error.getMessage());
            }
        });
    }

    public void deleteLocalPhoto(String photoPath) {
        File photoFile = new File(photoPath);
        if (photoFile.exists()) {
            if (photoFile.delete()) {
                LogUtil.i(TAG, "删除成功");
            } else {
                // 文件删除失败
                LogUtil.i(TAG, "删除失败");
            }
        } else {
            LogUtil.i(TAG, "文件不存在");
        }
    }

    private void showCardInfoDialog(IDCardResult result) {
        View view = getLayoutInflater().inflate(R.layout.dialog_ocr_info, null);
        androidx.appcompat.app.AlertDialog exitDialogLoginOut = new androidx.appcompat.app.AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.setCancelable(false);
        Objects.requireNonNull(exitDialogLoginOut.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView content1Tv = view.findViewById(R.id.content1_tv);
        TextView titleTv = view.findViewById(R.id.title_tv);
        titleTv.setVisibility(View.VISIBLE);
        titleTv.setText("身份证信息");
        contentTv.setText("姓名：" + result.getName());
        content1Tv.setText("身份证号：" + result.getIdNumber());
        confirmTv.setOnClickListener(v -> {
            exitDialogLoginOut.dismiss();
            binding.baoxianLayout.idcardEt.setText(result.getIdNumber().toString());
        });
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }


    private void showBankInfoDialog(BankCardResult result) {
        View view = getLayoutInflater().inflate(R.layout.dialog_ocr_info, null);
        androidx.appcompat.app.AlertDialog exitDialogLoginOut = new androidx.appcompat.app.AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.setCancelable(false);
        Objects.requireNonNull(exitDialogLoginOut.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView content1Tv = view.findViewById(R.id.content1_tv);
        TextView titleTv = view.findViewById(R.id.title_tv);
        titleTv.setVisibility(View.VISIBLE);
        titleTv.setText("银行卡信息");
        contentTv.setText("银行名称：" + result.getBankName());
        content1Tv.setText("银行卡名称：" + result.getBankCardNumber());
        confirmTv.setOnClickListener(v -> {
            exitDialogLoginOut.dismiss();
            binding.baoxianLayout.bankNumEt.setText(result.getBankCardNumber().toString());
        });
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }

    private void initToken() {
        OCR.getInstance(getApplicationContext()).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                Log.d("lzx----》", "token" + token.toString());
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
            }
        }, getApplicationContext(), "LmuS31fNhIwQkOktjqVesrW0", "qIZY7tAqwQQSPyZODeEv1TFPeGQQIbIN");

    }


    private void showNetwork(CollectInfoData collectInfoData) {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        androidx.appcompat.app.AlertDialog exitDialogLoginOut = new androidx.appcompat.app.AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView title_tv = view.findViewById(R.id.title_tv);
        title_tv.setVisibility(View.VISIBLE);
        title_tv.setText("提交信息");
        contentTv.setText("检测到您当前暂无网络，会将您填入的数据保存到本地。请到个人中心进行提交");
        cancelTv.setOnClickListener(view12 -> exitDialogLoginOut.dismiss());
        confirmTv.setOnClickListener(v -> {
            exitDialogLoginOut.dismiss();
            showLoading("数据写入本地中...");
            insertInfo(collectInfoData);


        });
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }

    private void insertInfo(CollectInfoData collectInfoData) {
        LogUtil.d("lzx-----》", collectInfoData.applyGUID);
        new Thread(() -> {
            AppDatabase.getInstance(CollectFillInActivity.this).applyDao().updateApplyStatus(collectInfoData.applyGUID);
            LogUtil.d("lzx----》", "状态更新成功");
        }).start();
        CollectionDBModelRepository.getInstance().insert(this, collectInfoData);
        hideLoading();
        finish();
    }


    public void onRadioButtonClickedDaiYang(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.fosterCareYesRb) {
            if (isChecked) {
                mIsFosterCare = true;
                showFosterCareInfoDialog();
            }
        } else if (view == binding.fosterCareNoRb) {
            if (isChecked) {
                mIsFosterCare = false;
                binding.daiyangLl.setVisibility(View.GONE);
            }
        }
    }

    private void showFosterCareInfoDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_foster_care_info, null);
        androidx.appcompat.app.AlertDialog exitDialogLoginOut = new androidx.appcompat.app.AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.setCancelable(false);
        Objects.requireNonNull(exitDialogLoginOut.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        TextView confirmTv = view.findViewById(R.id.positive_tv);
        TextView negativeTv = view.findViewById(R.id.negative_tv);
        EditText mFosterCareEt = view.findViewById(R.id.foster_care_et);
        String fosterCareName = ShouJISP.getInstance().getFosterCareName();
        if (!TextUtils.isEmpty(fosterCareName)){
            mFosterCareEt.setText(fosterCareName);
        }
        confirmTv.setOnClickListener(v -> {
            String string = mFosterCareEt.getText().toString().trim();
            if (!TextUtils.isEmpty(string)){
                ShouJISP.getInstance().setFosterCareName(string);
                binding.daiyangLl.setVisibility(View.VISIBLE);
                binding.daiyangNameTv.setText("【代养主体名称：" + string +"】");
                exitDialogLoginOut.dismiss();
            }else {
                Objects.requireNonNull(RxToast.warning(CollectFillInActivity.this, "当选择代养为是时，请输入代养主体名称"));
            }
        });
        negativeTv.setOnClickListener(v -> {
            exitDialogLoginOut.dismiss();
        });
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }


}

