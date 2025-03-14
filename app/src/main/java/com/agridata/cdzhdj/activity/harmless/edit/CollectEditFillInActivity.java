package com.agridata.cdzhdj.activity.harmless.edit;

import static com.agridata.cdzhdj.base.MyApplication.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.SPUtil.ShouJISP;
import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.AddressSP;
import com.agridata.cdzhdj.SPUtil.ConfigSP;
import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.harmless.AddAnimalMenuActivity;
import com.agridata.cdzhdj.activity.harmless.SignActivity;
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
import com.agridata.cdzhdj.data.DanHaoBean;
import com.agridata.cdzhdj.data.HarmlessAnimalBean;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.NewSendSmsBean;
import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.data.SendStatusBean;
import com.agridata.cdzhdj.data.ShouJiBianJiBean;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.StatusMeBean;
import com.agridata.cdzhdj.data.UnitBean;
import com.agridata.cdzhdj.data.XianChangRenBean;
import com.agridata.cdzhdj.databinding.ActivityCollectFillEditInBinding;
import com.agridata.cdzhdj.utils.AssertDataUtil;
import com.agridata.cdzhdj.utils.GlideEngine;
import com.agridata.cdzhdj.utils.GlideUtils;
import com.agridata.cdzhdj.utils.IdCardUtils;
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
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class CollectEditFillInActivity extends BaseActivity<ActivityCollectFillEditInBinding> implements View.OnClickListener, onDelListener , onDelSiChuPicListener
        , onDelSTPicListener, onDelZCPicListener {
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
    private OptionsPickerView pvCustomOptions, UnitpvCustomOptions, BaoXiaopvCustomOptions, BankpvCustomOptions,XiaoDupvCustomOptions;
    private AnimalBean animalBeanChoose;//
    private UnitBean unitBeanChoose;//
    private BaoXianGongSiBean baoXianGongSiBeanChoose;
    private BankBean bankBeanChoose;


    private int XiaoDuCheck = 0;
    private int YiBingCheck = 0;
    private int SiWangYaunYinCheck = 0;
    private int BaoXianCheck = 0;
    private int mBankCheck=0;
    private List<BankBean> bankBeansList;

    private String IDCardMid;
    private String  ZhiziMid;
    private String BankMid;
    private String ShouYunYuanMid;
    private String YangZhiHuMid;
    private List<String> SiChuErBiaoPicList = new ArrayList<>();
    private List<String> ShiTiPicList = new ArrayList<>();
    private List<String> ZHuangCheList = new ArrayList<>();

    private CustomLoadingDialog mLoadingDialog;
    private AlertDialog exitDialogSuccess;


    private String Mid;
    private ShouJiBianJiBean.ResultBean resultBean;
    private LoginData.ResultBean resultUserData;
    private RoleBean.DataBean roleInfo;
    private ArrayList<AnimalBean> animalBeanList;

    private int selectUnitPos;

    private boolean mIsFosterCare= false;

    public static void start(Activity context, String Mid) {
        Intent intent = new Intent(context, CollectEditFillInActivity.class);
        intent.putExtra("Mid", Mid);

        context.startActivityForResult(intent, 1002);
    }


    @Override
    protected ActivityCollectFillEditInBinding getBinding() {
        return ActivityCollectFillEditInBinding.inflate(getLayoutInflater());
    }

    private void getPermissions() {
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .permission(Permission.ACCESS_FINE_LOCATION)
                .permission(Permission.ACCESS_COARSE_LOCATION)
                // 申请多个权限
                .permission(Permission.Group.BLUETOOTH)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {

                        } else {
                            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(CollectEditFillInActivity.this, "被永久拒绝授权，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(CollectEditFillInActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(CollectEditFillInActivity.this, "获取权限失败"));
                        }
                    }
                });
    }
    @Override
    protected void initView() {
        getPermissions();
        this.getArguments();
        this.initLoadingView();

        getBianjiInfo();
        getHarAnimalInfo();
        roleInfo = RoleSP.getInstance().getRoleInfo();

        resultUserData = UserDataSP.getInstance().getUserInfo().Result;
        LogUtil.d(TAG, "获取roleInfo" + roleInfo.toString());
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏





        binding.titlebarLeft.setImageResource(R.drawable.title_back);
        binding.titlebarMiddle.setText("编辑收集清单");
        imageEngine =GlideEngine.createGlideEngine();
        binding.baoxianLayout.upBankIvBtn.setOnClickListener(this);
        binding.baoxianLayout.upCarIvBtn.setOnClickListener(this);
        binding.baoxianLayout.upDepositIvBtn.setOnClickListener(this);
        binding.animalMuen.addAnimalBtn.setOnClickListener(this);
        binding.shouyunyuanQianming.setOnClickListener(this);
        binding.yangzhichangQianming.setOnClickListener(this);
        binding.animalTypeEt.setOnClickListener(this);
        binding.unitTypeEt.setOnClickListener(this);
        binding.sureBtn.setOnClickListener(this);
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


        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        binding.recyclerDeadAnimalEartag.setLayoutManager(manager);
        binding.recyclerDeadAnimalEartag.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));
        gridImageDeathAdapter = new GridImageDeathAdapter(this, mAnimalDeathPicList);
        gridImageDeathAdapter.setDelSiChuPiC(this);
        gridImageDeathAdapter.setSelectMax(100);
        binding.recyclerDeadAnimalEartag.setAdapter(gridImageDeathAdapter);


        FullyGridLayoutManager managerShiTi = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        binding.recyclerShiti.setLayoutManager(managerShiTi);
        binding.recyclerShiti.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));
        imageShiTiAdapter = new GridImageShiTiAdapter(this, mAnimalShiTiPicList);
        imageShiTiAdapter.setDelSTPiC(this);
        imageShiTiAdapter.setSelectMax(100);
        binding.recyclerShiti.setAdapter(imageShiTiAdapter);


        FullyGridLayoutManager managerZhuangChe = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        binding.recyclerZhuangche.setLayoutManager(managerZhuangChe);
        binding.recyclerZhuangche.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));
        imageZhuangCheAdapter = new GridImageZhuangCheAdapter(this, mAnimalZhuangChePicList);
        imageZhuangCheAdapter.setDelZCPiC(this);
        imageZhuangCheAdapter.setSelectMax(100);
        binding.recyclerZhuangche.setAdapter(imageZhuangCheAdapter);
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        Mid = this.getIntent().getStringExtra("Mid");
        LogUtil.d(TAG, "MID" + Mid);
    }

    private void getBianjiInfo() {
        HttpRequest.getBianJi(CollectEditFillInActivity.this, Mid, new CallBackLis<ShouJiBianJiBean>() {
            @Override
            public void onSuccess(String method, ShouJiBianJiBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.ErrorCode == 0) {
                    resultBean = content.Result;
                    setBeiAnInfo(content);
                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }

    private void DelInfo() {
        HttpRequest.getDel(CollectEditFillInActivity.this, resultBean.Mid, resultUserData.userId, new CallBackLis<StatusData>() {
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.ErrorCode == 0) {
                    upLoadCollect();

                }
            }

            @Override
            public void onFailure(String method, String error) {
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

        bankBeansList = AssertDataUtil.initBank(this);
        LogUtil.d(TAG, "银行Json解析" + bankBeansList);

        binding.zuobiaoEt.setText(AddressSP.getInstance().getLatitude() + "," + AddressSP.getInstance().getLongitude());

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
                binding.baoxianLayout.baoxianLl.setVisibility(View.VISIBLE);
            }
        } else if (view == binding.baoxianLayout.baoxianNoRb) {
            if (isChecked) {
                BaoXianCheck = 2;
                binding.baoxianLayout.baoxianLl.setVisibility(View.GONE);
            }
        } else if (view == binding.xiaoduYesRb) { //是否消毒
            if (isChecked) {
                XiaoDuCheck = 1;
            }
        } else if (view == binding.xiaoduNoRb) { //是否消毒
            if (isChecked) {
                XiaoDuCheck = 2;
            }
        } else if (view == binding.yibingYesRb) { //是否重大疫病
            if (isChecked) {
                YiBingCheck = 1;
            }
        } else if (view == binding.yibingNoRb) { //是否重大疫病
            if (isChecked) {
                YiBingCheck = 2;
            }
        } else if (view == binding.deathYibingRb) { //死亡原因
            if (isChecked) {
                SiWangYaunYinCheck = 1;
            }
        } else if (view == binding.deathYiwaiRb) { //死亡原因
            if (isChecked) {
                SiWangYaunYinCheck = 2;
            }
        } else if (view == binding.deathPushaRb) { //死亡原因
            if (isChecked) {
                SiWangYaunYinCheck = 3;
            }
        }if (view == binding.baoxianLayout.bankYesRb) {//银行卡
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
                binding.baoxianLayout.depositAccountLl.setVisibility(View.VISIBLE);
                binding.baoxianLayout.depositImageLl.setVisibility(View.VISIBLE);
                binding.baoxianLayout.depositAccountLl.setVisibility(View.VISIBLE);
                binding.baoxianLayout.depositImageLl.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v == binding.baoxianLayout.upCarIvBtn) {//身份证号
            showPicDialog();
            PicBtnType = 1;
        } else if (v == binding.baoxianLayout.upBankIvBtn) {//银行卡号
            showPicDialog();
            PicBtnType = 2;
        }else if (v==binding.baoxianLayout.upDepositIvBtn) {
            PicBtnType = 10;
            getPermissionCream();
        } else if (v == binding.animalMuen.addAnimalBtn) { //添加动物清单
            if (!TextUtils.isEmpty(binding.animalTypeEt.getText().toString())) {
                AddAnimalMenuActivity.start(CollectEditFillInActivity.this, animalBeanChoose.AnimalName, animalBeanChoose.ID);
            } else {
                Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请选择动物在进行添加~"));
            }
        } else if (v == binding.shouyunyuanQianming) {
            SignActivity.start(CollectEditFillInActivity.this, "收运员签名", 10);
        } else if (v == binding.yangzhichangQianming) {
            SignActivity.start(CollectEditFillInActivity.this, "养殖场户签名", 11);
        } else if (v == binding.animalTypeEt) {
            showAnimalDialog();
        } else if (v == binding.unitTypeEt) {
            if (!TextUtils.isEmpty(binding.animalTypeEt.getText().toString())){
                showUnitDialog();
            }else {
                Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请先选择动物种类~"));
            }
        } else if (v == binding.baoxianLayout.insuranceCompanyEt) {
            showBaoXianGongSiDialog();
        } else  if (v == binding.chooseXiaoduBtn){
            showXiaoDuDialog();
        }else if (v == binding.baoxianLayout.bankNameEt) {
            showBankNameDialog();
        } else if (v == binding.sureBtn) {
            if (checkInfo()) {
                DelInfo();

            }
        } else  if (v==binding.upZhizidanjuIvBtn){//上传纸质单据
            PicBtnType = 999;
            showPicDialog();
        }
    }

    private void showPicDialog() {
        List<String> bottomDialogContents = new ArrayList<>();
        bottomDialogContents.add("拍照");
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
                TakePicturesAlone();
            }
        });
    }

    //单独拍照
    private void TakePicturesAlone() {
        // Take pictures alone
        PictureSelectionCameraModel cameraModel = PictureSelector.create(CollectEditFillInActivity.this)
                .openCamera(SelectMimeType.ofImage())
                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())//裁剪引擎
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())//压缩引擎
                .isOriginalControl(true);
        forOnlyCameraResult(cameraModel);
    }

    // 相册选择
    private void OpenPicture() {
        // 进入相册
        PictureSelectionModel selectionModel = PictureSelector.create(CollectEditFillInActivity.this)
                .openGallery(SelectMimeType.ofImage())
                .setSelectorUIStyle(PictureSelectorUtils.setStyle())
                .setImageEngine(imageEngine)
                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())
                .setSandboxFileEngine(PictureSelectorUtils.getMeSandboxFileEngine())
                .setSelectLimitTipsListener(PictureSelectorUtils.getMeOnSelectLimitTips())
                .setSelectionMode(SelectModeConfig.SINGLE)
                .setQuerySortOrder(MediaStore.MediaColumns.DATE_MODIFIED)
                .isDisplayTimeAxis(true)
                .isOriginalControl(true)
                .isDisplayCamera(false)
                .isPreviewFullScreenMode(true)
                .isPreviewZoomEffect(true)
                .isPreviewImage(true)
                .isMaxSelectEnabledMask(true)
                .isDirectReturnSingle(true)
                .setMaxSelectNum(1);
        forSelectResult(selectionModel, "SINGLE");
    }


    private void forOnlyCameraResult(PictureSelectionCameraModel model) {
        model.forResultActivity(new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(ArrayList<LocalMedia> result) {
                analyticalSelectResults(result, "SINGLE");
            }

            @Override
            public void onCancel() {

            }
        });
    }

    //选择回调
    private void forSelectResult(PictureSelectionModel model, String typeName) {

        switch (typeName) {
            case "SINGLE"://银行卡号照片 + 身份证照片
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
        model.forResult(new OnResultCallbackListener<LocalMedia>() {
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
        switch (typeName) {
            case "SINGLE"://银行卡号照片 + 身份证照片
                if (PicBtnType == 1) {
                    if (!result.isEmpty()) {
                        GlideUtils.createGlideEngine().loadRounderImage(CollectEditFillInActivity.this, result.get(0).getRealPath(), binding.baoxianLayout.idcardIv, 20);
                        upLoadImg(result.get(0).getRealPath());
                    }
                } else if (PicBtnType == 2) {
                    if (!result.isEmpty()) {
                        GlideUtils.createGlideEngine().loadRounderImage(CollectEditFillInActivity.this, result.get(0).getRealPath(), binding.baoxianLayout.bankIv, 20);
                        upLoadImg(result.get(0).getRealPath());
                    }

                }else if (PicBtnType == 10) {
                    if (!result.isEmpty()) {
                        if (NetworkUtils.isNetAvailable(this)) {
                            upLoadImg(result.get(0).getCompressPath()==null?result.get(0).getAvailablePath():result.get(0).getCompressPath());
                        }
                    }

                } else if (PicBtnType == 999) {
                    if (!result.isEmpty()) {
                        Bitmap sourBitmap = BitmapFactory.decodeFile(result.get(0).getRealPath());
                        Bitmap WaterBitmap = saveWaterMask(CollectEditFillInActivity.this, 4, sourBitmap, resultBean.Dep_ApplyGUID.ApplyNo, resultBean.Dep_ApplyGUID.UserName, roleInfo.harmlessUser.name, binding.animalTypeEt.getText().toString(), animalMenuBeans.size() + "");
                        String fileName = "IMG_" + new Date().getTime() + "zhizhi_danju" + ".png";
                        boolean isSaveSuccess;
                        if (Build.VERSION.SDK_INT < 29) {
                            String zhizhiDanJu = WaterMaskUtil.saveQNext(WaterBitmap, CollectEditFillInActivity.this, fileName, 60);
                            result.get(0).setCompressPath(zhizhiDanJu);
                        } else {
                            String zhizhiDanJu = WaterMaskUtil.saveQNext(WaterBitmap, CollectEditFillInActivity.this, fileName, 60);
                            LogUtil.d("lzx----------->保存", zhizhiDanJu);
                            result.get(0).setCompressPath(zhizhiDanJu);
                        }
                        upLoadImg(result.get(0).getCompressPath());
                    }
                }
                break;
            case "death"://死畜耳标照片
                PicBtnType = 5;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (LocalMedia localMedia : result) {
                            Bitmap sourBitmap = BitmapFactory.decodeFile(localMedia.getAvailablePath());
                            Bitmap WaterBitmap = saveWaterMask(CollectEditFillInActivity.this, 4, sourBitmap,  resultBean.Dep_ApplyGUID.ApplyNo, resultBean.Dep_ApplyGUID.UserName, resultBean.Worker.Name,binding.animalTypeEt.getText().toString(),animalMenuBeans.size()+"");
                            String fileName = "IMG_" + new Date().getTime() + "SiChu" + ".png";


                            boolean isSaveSuccess;
                            if (Build.VERSION.SDK_INT < 29) {
                                String sichuListPic = WaterMaskUtil.saveQNext(WaterBitmap, CollectEditFillInActivity.this, fileName, 60);
                                localMedia.setCompressPath(sichuListPic);
                            } else {
                                String sichuListPic = WaterMaskUtil.saveQNext(WaterBitmap, CollectEditFillInActivity.this, fileName, 60);
                                LogUtil.d("lzx----------->保存", sichuListPic);
                                localMedia.setCompressPath(sichuListPic);
                            }
                        }
                    }
                });
                List<String> DeathPathList = new ArrayList<>();
                for (LocalMedia localMedia : result) {
                    DeathPathList.add(localMedia.getCompressPath());
                }
                upLoadImgBatch(DeathPathList);
                break;
            case "ShiTi"://上传死畜尸体照片：
                PicBtnType = 6;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (LocalMedia localMedia : result) {
                            Bitmap sourBitmap = BitmapFactory.decodeFile(localMedia.getAvailablePath());
                            Bitmap WaterBitmap = saveWaterMask(CollectEditFillInActivity.this, 4, sourBitmap,  resultBean.Dep_ApplyGUID.ApplyNo, resultBean.Dep_ApplyGUID.UserName, resultBean.Worker.Name,binding.animalTypeEt.getText().toString(),animalMenuBeans.size()+"");
                            String fileName = "IMG_" + new Date().getTime() + "shi_ti" + ".png";


                            boolean isSaveSuccess;
                            if (Build.VERSION.SDK_INT < 29) {
                                String sichuListPic = WaterMaskUtil.saveQNext(WaterBitmap, CollectEditFillInActivity.this, fileName, 60);
                                localMedia.setCompressPath(sichuListPic);
                            } else {
                                String sichuListPic = WaterMaskUtil.saveQNext(WaterBitmap, CollectEditFillInActivity.this, fileName, 60);
                                LogUtil.d("lzx----------->保存", sichuListPic);
                                localMedia.setCompressPath(sichuListPic);
                            }
                        }
                    }
                });
                List<String> ShiTihPathList = new ArrayList<>();
                for (LocalMedia localMedia : result) {
                    ShiTihPathList.add(localMedia.getCompressPath());
                }
                upLoadImgBatch(ShiTihPathList);

                break;
            case "ZhuangChe"://上传死畜尸体照片：
                PicBtnType = 7;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (LocalMedia localMedia : result) {
                            Bitmap sourBitmap = BitmapFactory.decodeFile(localMedia.getAvailablePath());
                            Bitmap WaterBitmap = saveWaterMask(CollectEditFillInActivity.this, 4, sourBitmap,  resultBean.Dep_ApplyGUID.ApplyNo, resultBean.Dep_ApplyGUID.UserName, resultBean.Worker.Name,binding.animalTypeEt.getText().toString(),animalMenuBeans.size()+"");
                            String fileName = "IMG_" + new Date().getTime() + "zhuang_che" + ".png";


                            boolean isSaveSuccess;
                            if (Build.VERSION.SDK_INT < 29) {
                                String sichuListPic = WaterMaskUtil.saveQNext(WaterBitmap, CollectEditFillInActivity.this, fileName, 60);
                                localMedia.setCompressPath(sichuListPic);
                            } else {
                                String sichuListPic = WaterMaskUtil.saveQNext(WaterBitmap, CollectEditFillInActivity.this, fileName, 60);
                                LogUtil.d("lzx----------->保存", sichuListPic);
                                localMedia.setCompressPath(sichuListPic);
                            }
                        }
                    }
                });
                List<String> ZCPathList = new ArrayList<>();
                for (LocalMedia localMedia : result) {
                    ZCPathList.add(localMedia.getCompressPath());
                }
                upLoadImgBatch(ZCPathList);
                break;
        }
    }

    /**
     * @param position 左上为0，顺时针算起
     */
    private static Bitmap saveWaterMask(Context context, int position, Bitmap sourBitmap, String DaoHao, String ShenBaoRen, String ShouJiRen,String num,String Size) {
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
            binding.grossWeightEt.setText(all +"");
            binding.animalMuen.allNumTv.setVisibility(View.VISIBLE);
            binding.animalMuen.allNumTv.setText("总数量：" + allNum + "");
            binding.verificationNumEt.setText(allNum + "");

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

        if (resultCode == 100) {
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
                    Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this, "输入耳标已存在动物清单，请重新添加"));
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
                binding.verificationNumEt.setText(allNum + "");
                binding.animalMuen.allNumTv.setVisibility(View.VISIBLE);
                binding.animalMuen.allNumTv.setText("总数量：" + allNum + "");
                binding.grossWeightEt.setText(all + "");
            }
        } else if (requestCode == 10) {

            String Ic_QianMing = data.getStringExtra("qianming");
            Bitmap sourBitmap = BitmapFactory.decodeFile(Ic_QianMing);
            Bitmap WaterBitmap = saveWaterMask(CollectEditFillInActivity.this, 4, sourBitmap,resultBean.Dep_ApplyGUID.ApplyNo, resultBean.Dep_ApplyGUID.UserName, resultBean.Worker.Name,binding.animalTypeEt.getText().toString(),animalMenuBeans.size()+"");
            String fileName = "IMG_" + new Date().getTime() + "shouyunyuan" + ".png";
            String   shouyunyuanUrl = WaterMaskUtil.saveQNext(WaterBitmap, CollectEditFillInActivity.this, fileName, 60);
            PicBtnType = 3;
            upLoadImg(shouyunyuanUrl);

        } else if (requestCode == 11) {


            String YangZhiChangHu = data.getStringExtra("yangzhichanghu");
            Bitmap sourBitmap = BitmapFactory.decodeFile(YangZhiChangHu);
            Bitmap WaterBitmap = saveWaterMask(CollectEditFillInActivity.this, 4, sourBitmap, resultBean.Dep_ApplyGUID.ApplyNo, resultBean.Dep_ApplyGUID.UserName, resultBean.Worker.Name,binding.animalTypeEt.getText().toString(),animalMenuBeans.size()+"");
            String fileName = "IMG_" + new Date().getTime() + "yangzhihu" + ".png";
            String yangzhichang_url = WaterMaskUtil.saveQNext(WaterBitmap, CollectEditFillInActivity.this, fileName, 60);
            PicBtnType = 4;
            upLoadImg(yangzhichang_url);
        }
    }


    private void mAnimalDeathAdapter() {
        gridImageDeathAdapter.setOnItemClickListener(new GridImageDeathAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // 预览图片、视频、音频
//                PictureSelector.create(CollectEditFillInActivity.this)
//                        .openPreview()
//                        .setImageEngine(imageEngine)
//                        .setSelectorUIStyle(PictureSelectorUtils.setStyle())
//                        .isPreviewFullScreenMode(false)
//                        .startActivityPreview(position, true, gridImageDeathAdapter.getData());
                if (!TextUtils.isEmpty(gridImageDeathAdapter.getData().get(position).getCompressPath())){
                    PicActivity.start(CollectEditFillInActivity.this, gridImageDeathAdapter.getData().get(position).getCompressPath());
                }else {
                    PicActivity.start(CollectEditFillInActivity.this, gridImageDeathAdapter.getData().get(position).getPath());
                }


            }

            @Override
            public void openPicture() {

                // 进入相册
                PictureSelectionModel selectionModel = PictureSelector.create(CollectEditFillInActivity.this)
                        .openGallery(SelectMimeType.ofImage())
                        .setSelectorUIStyle(PictureSelectorUtils.setStyle())
                        .setImageEngine(imageEngine)
                        //.setCropEngine(PictureSelectorUtils.getCropFileEngine())
                        .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())
                        .setSandboxFileEngine(PictureSelectorUtils.getMeSandboxFileEngine())
                        .setSelectLimitTipsListener(PictureSelectorUtils.getMeOnSelectLimitTips())
                        .setSelectionMode(SelectModeConfig.MULTIPLE)
                        .setQuerySortOrder("")
                        .isDisplayTimeAxis(true)
                        .isOriginalControl(true)
                        .isDisplayCamera(true)
                        .isPreviewFullScreenMode(true)
                        .isPreviewZoomEffect(true)
                        .isPreviewImage(true)
                        .isMaxSelectEnabledMask(true)
                        .isDirectReturnSingle(true)
                        .setMaxSelectNum(9);
//                        .setSelectedData(gridImageDeathAdapter.getData());
                forSelectResult(selectionModel, "death");


            }
        });
    }

    private void mAnimalShiTiAdapter() {
        imageShiTiAdapter.setOnItemClickListener(new GridImageShiTiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // 预览图片、视频、音频
//                PictureSelector.create(CollectEditFillInActivity.this)
//                        .openPreview()
//                        .setImageEngine(imageEngine)
//                        .setSelectorUIStyle(PictureSelectorUtils.setStyle())
//                        .isPreviewFullScreenMode(false)
//                        .startActivityPreview(position, true, imageShiTiAdapter.getData());

                if (!TextUtils.isEmpty(imageShiTiAdapter.getData().get(position).getCompressPath())){
                    PicActivity.start(CollectEditFillInActivity.this, imageShiTiAdapter.getData().get(position).getCompressPath());
                }else {
                    PicActivity.start(CollectEditFillInActivity.this, imageShiTiAdapter.getData().get(position).getPath());
                }




            }

            @Override
            public void openPicture() {

                // 进入相册
                PictureSelectionModel selectionModel = PictureSelector.create(CollectEditFillInActivity.this)
                        .openGallery(SelectMimeType.ofImage())
                        .setSelectorUIStyle(PictureSelectorUtils.setStyle())
                        .setImageEngine(imageEngine)
                        //.setCropEngine(PictureSelectorUtils.getCropFileEngine())
                        .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())
                        .setSandboxFileEngine(PictureSelectorUtils.getMeSandboxFileEngine())
                        .setSelectLimitTipsListener(PictureSelectorUtils.getMeOnSelectLimitTips())
                        .setSelectionMode(SelectModeConfig.MULTIPLE)
                        .setQuerySortOrder("")
                        .isDisplayTimeAxis(true)
                        .isOriginalControl(true)
                        .isDisplayCamera(true)
                        .isPreviewFullScreenMode(true)
                        .isPreviewZoomEffect(true)
                        .isPreviewImage(true)
                        .isMaxSelectEnabledMask(true)
                        .isDirectReturnSingle(true)
                        .setMaxSelectNum(9);
//                        .setSelectedData(imageShiTiAdapter.getData());
                forSelectResult(selectionModel, "ShiTi");


            }
        });
    }


    private void mAnimalZhuangCheAdapter() {
        imageZhuangCheAdapter.setOnItemClickListener(new GridImageZhuangCheAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // 预览图片、视频、音频
//                PictureSelector.create(CollectEditFillInActivity.this)
//                        .openPreview()
//                        .setImageEngine(imageEngine)
//                        .setSelectorUIStyle(PictureSelectorUtils.setStyle())
//                        .isPreviewFullScreenMode(false)
//                        .startActivityPreview(position, true, imageZhuangCheAdapter.getData());

                if (!TextUtils.isEmpty(imageZhuangCheAdapter.getData().get(position).getCompressPath())){
                    PicActivity.start(CollectEditFillInActivity.this, imageZhuangCheAdapter.getData().get(position).getCompressPath());
                }else {
                    PicActivity.start(CollectEditFillInActivity.this, imageZhuangCheAdapter.getData().get(position).getPath());
                }
            }

            @Override
            public void openPicture() {
                LogUtil.d(TAG, "装车");
                // 进入相册
                PictureSelectionModel selectionModel = PictureSelector.create(CollectEditFillInActivity.this)
                        .openGallery(SelectMimeType.ofImage())
                        .setSelectorUIStyle(PictureSelectorUtils.setStyle())
                        .setImageEngine(imageEngine)
                        //.setCropEngine(PictureSelectorUtils.getCropFileEngine())
                        .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())
                        .setSandboxFileEngine(PictureSelectorUtils.getMeSandboxFileEngine())
                        .setSelectLimitTipsListener(PictureSelectorUtils.getMeOnSelectLimitTips())
                        .setSelectionMode(SelectModeConfig.MULTIPLE)
                        .setQuerySortOrder("")
                        .isDisplayTimeAxis(true)
                        .isOriginalControl(true)
                        .isDisplayCamera(true)
                        .isPreviewFullScreenMode(true)
                        .isPreviewZoomEffect(true)
                        .isPreviewImage(true)
                        .isDirectReturnSingle(true)
                        .isMaxSelectEnabledMask(true)
                        .setMaxSelectNum(9);
                forSelectResult(selectionModel, "ZhuangChe");


            }
        });
    }

    /**
     * 获取唯一单号
     */
    private void getDanHao() {
        HttpRequest.getDanHao(CollectEditFillInActivity.this, new CallBackLis<DanHaoBean>() {
            @Override
            public void onSuccess(String method, DanHaoBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.code == 200) {
                    binding.collectNumTv.setText(content.data);
                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }

    //设置跳转过来得备案信息
    @SuppressLint("NotifyDataSetChanged")
    private void setBeiAnInfo(ShouJiBianJiBean shouJiBianJiBean) {

        ShouJiBianJiBean.ResultBean result = shouJiBianJiBean.Result;

        LogUtil.d(TAG, " 申报编号" + result.Dep_ApplyGUID.ApplyNo);
        binding.declarationNumberTv.setText(result.Dep_ApplyGUID.ApplyNo);//申报编号

        binding.bohuiTv.setText(result.CheckRemark);
        binding.farmerNameTv.setText(result.Dep_ApplyGUID.UserName);
        binding.telTv.setText(result.Dep_ApplyGUID.Mobile);

        if (!TextUtils.isEmpty(result.DieAmount)){
            binding.shenbaoNumTv.setText(result.DieAmount);
        }

        binding.baoxianLayout.idcardEt.setText(result.Dep_ApplyGUID.IDCard);

//        binding.baoxianLayout.bankNameEt.setText(result.Dep_ApplyGUID.BankName);
        if (!TextUtils.isEmpty(result.Dep_ApplyGUID.BankCardNo)) {
            binding.baoxianLayout.bankNumEt.setText(result.Dep_ApplyGUID.BankCardNo);
        }

        if (result.IsFoster) {
            mIsFosterCare = true;
            binding.fosterCareYesRb.setChecked(true);
            binding.daiyangLl.setVisibility(View.VISIBLE);
            binding.daiyangNameTv.setText("代养主体名称："  + result.FosterCareName);
        } else {
            mIsFosterCare = false;
            binding.fosterCareNoRb.setChecked(true);
            binding.daiyangLl.setVisibility(View.GONE);
        }

        //動物
        animalBeanChoose = new AnimalBean("", "");
        animalBeanChoose.ID = result.Animal.key;
        animalBeanChoose.AnimalName = result.Animal.AnimalName;
        LogUtil.d(TAG, "animalBeanChoose" + animalBeanChoose);
        binding.animalTypeEt.setText(result.Animal.AnimalName);


        //單位
        unitBeanChoose = new UnitBean("", "");
        unitBeanChoose.ID = result.AnimalUnit.key;
        unitBeanChoose.UnitName = result.AnimalUnit.UnitName;
        LogUtil.d(TAG, "unitBeanChoose" + animalBeanChoose);
        binding.unitTypeEt.setText(result.AnimalUnit.UnitName);


        binding.verificationNumEt.setText(result.DieAmount);//核验数量
        binding.grossWeightEt.setText(result.DieWeight);//总重量
        binding.inventoryRelTimeEt.setText(result.Scale + "");//存栏量

        if (result.IsDisinfect) {
            XiaoDuCheck = 1;
            binding.xiaoduYesRb.setChecked(true);
        } else {
            XiaoDuCheck = 2;
            binding.xiaoduNoRb.setChecked(true);
        }

        binding.disinfectantsEt.setText(result.Disinfect);//消毒用品

        if (!TextUtils.isEmpty(result.Dep_ApplyGUID.ApplyCategory)) {
            if (result.Dep_ApplyGUID.ApplyCategory.equals("1")) {
                binding.declarationTypeTv.setText("集中存储点");
            } else if (result.Dep_ApplyGUID.ApplyCategory.equals("2")) {
                binding.declarationTypeTv.setText("无害化处理厂");
            } else {
                binding.declarationTypeTv.setText("自行处理");
            }
        }
        binding.declarationAddressTv.setText(result.Region.RegionFullName);
        binding.detailAddressTv.setText(result.Dep_ApplyGUID.ApplyAddress);


        //疫病
        if (result.Disease) {
            YiBingCheck = 1;
            binding.yibingYesRb.setChecked(true);
        } else {
            YiBingCheck = 2;
            binding.yibingNoRb.setChecked(true);
        }

        //死亡原因
        if (result.DieReasonType.equals("1")) {
            SiWangYaunYinCheck = 1;
            binding.deathYibingRb.setChecked(true);
        } else if (result.DieReasonType.equals("2")) {
            SiWangYaunYinCheck = 2;
            binding.deathYiwaiRb.setChecked(true);
        } else if (result.DieReasonType.equals("3")) {
            SiWangYaunYinCheck = 3;
            binding.deathPushaRb.setChecked(true);
        }

        //是否购买保险
        if (result.IsInsurance) {
            BaoXianCheck = 1;
            binding.baoxianLayout.baoxianYesRb.setChecked(true);
            binding.baoxianLayout.baoxianLl.setVisibility(View.VISIBLE);
            binding.baoxianLayout.insuranceCompanyEt.setText(result.InsuranceCompany.InsuranceCompanyName);
            baoXianGongSiBeanChoose = new BaoXianGongSiBean("", "");
            baoXianGongSiBeanChoose.ID = result.InsuranceCompany.key;
            baoXianGongSiBeanChoose.BaoXianGongSiName = result.InsuranceCompany.InsuranceCompanyName;
        } else {
            BaoXianCheck = 2;
            binding.baoxianLayout.baoxianNoRb.setChecked(true);
            binding.baoxianLayout.baoxianLl.setVisibility(View.GONE);
        }
        //身份证号
        binding.baoxianLayout.idcardEt.setText(result.IDCard);

        //身份证照片
        if (!TextUtils.isEmpty(result.ImgFiles.IdCardPic)) {
            IDCardMid = result.ImgFiles.IdCardPic;
            String IdCardUrl = UrlUtils.pic_url +IDCardMid;
            GlideUtils.createGlideEngine().loadRounderImage(CollectEditFillInActivity.this, IdCardUrl, binding.baoxianLayout.idcardIv, 20);
        }
//
//        //银行名称
//        binding.baoxianLayout.bankNameEt.setText(result.BankName);
        if (result.BankType==1){
            mBankCheck = 1;
            binding.baoxianLayout.bankYesRb.setChecked(true);
            binding.baoxianLayout.depositAccountLl.setVisibility(View.GONE);
            binding.baoxianLayout.depositImageLl.setVisibility(View.GONE);
            binding.baoxianLayout.bankLl.setVisibility(View.VISIBLE);
            binding.baoxianLayout.bankImageLl.setVisibility(View.VISIBLE);
            //卡号
            binding.baoxianLayout.bankNumEt.setText(result.BankCardNo);
            //银行卡照片
            if (!TextUtils.isEmpty(result.ImgFiles.BankPic)) {
                BankMid = result.ImgFiles.BankPic;
                String BankPic = UrlUtils.pic_url + BankMid;
                GlideUtils.createGlideEngine().loadRounderImage(CollectEditFillInActivity.this, BankPic, binding.baoxianLayout.bankIv, 20);
            }
        }else {
            mBankCheck = 2;
            binding.baoxianLayout.depositNoRb.setChecked(true);
            binding.baoxianLayout.bankLl.setVisibility(View.GONE);
            binding.baoxianLayout.bankImageLl.setVisibility(View.GONE);
            binding.baoxianLayout.depositAccountLl.setVisibility(View.VISIBLE);
            binding.baoxianLayout.depositImageLl.setVisibility(View.VISIBLE);
            //卡号
            binding.baoxianLayout.depositAccountEt.setText(result.BankCardNo);
            //银行卡照片
            if (!TextUtils.isEmpty(result.ImgFiles.BankPic)) {
                BankMid = result.ImgFiles.BankPic;
                String BankPic = UrlUtils.pic_url + BankMid;
                GlideUtils.createGlideEngine().loadRounderImage(CollectEditFillInActivity.this, BankPic, binding.baoxianLayout.depositIv, 20);
            }
        }




        //动物清单
        List<ShouJiBianJiBean.ResultBean.ItemDatasBean> itemDatas = result.ItemDatas;
        for (ShouJiBianJiBean.ResultBean.ItemDatasBean item : itemDatas) {
            AnimalMenuBean menuBean = new AnimalMenuBean();
            menuBean.AnimalType = item.AnimalType;
            menuBean.AnimalName = item.Name;
            menuBean.AnimalID = item.AnimalID;
            menuBean.EarTag = item.EarTagNo;
            menuBean.Weight = item.BodyWeight;
            menuBean.ShuLiang = item.Amount;
            menuBean.ShiFouMuPig = item.IsSow;
            animalMenuBeans.add(menuBean);
        }
        LogUtil.d(TAG, " animalMenuBeans" + animalMenuBeans.toString());

        if (animalMenuBeans.size() > 0) {
            LogUtil.d(TAG, "回来的数据" + animalMenuBeans.toString());
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
//                allNum = allNum + Integer.parseInt(menuBean.ShuLiang);
            }
            binding.animalMuen.hejiTv.setText("合计:" + animalMenuBeans.size());
            binding.animalMuen.allZhongliangTv.setText("总重量:" + all + "kg");
            binding.verificationNumEt.setText(result.DieAmount + "");
            binding.animalMuen.allNumTv.setVisibility(View.VISIBLE);
            binding.animalMuen.allNumTv.setText("总数量：" + result.DieAmount + "");
            binding.grossWeightEt.setText(all + "");
        }

        List<LocalMedia> localMediaList = new ArrayList<>();
        //死畜耳标照片
        List<ShouJiBianJiBean.ResultBean.ImgFilesBean.PicBen> shiTiPicList = result.ImgFiles.SiChuPicList;
        LogUtil.d(TAG," gridImageDeathAdapter" + shiTiPicList.size());
        gridImageDeathAdapter.getData().clear();
        for (ShouJiBianJiBean.ResultBean.ImgFilesBean.PicBen picBen : shiTiPicList) {
            SiChuErBiaoPicList.add(picBen.Mid);
            LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + picBen.Mid , PictureMimeType.MIME_TYPE_JPEG);
            localMediaList.add(localMedia);
            gridImageDeathAdapter.getData().add(localMedia);
        }
        LogUtil.d(TAG," gridImageDeathAdapter" + gridImageDeathAdapter.getData().size());
        gridImageDeathAdapter.notifyDataSetChanged();


        List<LocalMedia> localMediaList1 = new ArrayList<>();
        //死畜耳标照片
        List<ShouJiBianJiBean.ResultBean.ImgFilesBean.PicBen> shiTiPicList1 = result.ImgFiles.ShiTiPicList;
        for (ShouJiBianJiBean.ResultBean.ImgFilesBean.PicBen picBen : shiTiPicList1) {
            ShiTiPicList.add(picBen.Mid);
            LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + picBen.Mid , PictureMimeType.MIME_TYPE_JPEG);
            localMediaList1.add(localMedia);
            imageShiTiAdapter.getData().add(localMedia);

        }
        imageShiTiAdapter.notifyDataSetChanged();


        List<LocalMedia> localMediaList2 = new ArrayList<>();
        //死畜耳标照片
        List<ShouJiBianJiBean.ResultBean.ImgFilesBean.PicBen> shiTiPicList2 = result.ImgFiles.ZhuangChePicList;
        for (ShouJiBianJiBean.ResultBean.ImgFilesBean.PicBen picBen : shiTiPicList2) {
            ZHuangCheList.add(picBen.Mid);
            LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + picBen.Mid , PictureMimeType.MIME_TYPE_JPEG);
            localMediaList2.add(localMedia);
            imageZhuangCheAdapter.getData().add(localMedia);

        }
        imageZhuangCheAdapter.notifyDataSetChanged();
        binding.feedbackEt.setText(result.Remark);

        //银行卡照片
        if (!TextUtils.isEmpty(result.ImgFiles.ShouYunYuanPic)) {
            ShouYunYuanMid = result.ImgFiles.ShouYunYuanPic;
            String BankPic = UrlUtils.pic_url+ ShouYunYuanMid ;
            Glide.with(this).load(BankPic).into(binding.shouyunyuanQianming);
        }

        if (!TextUtils.isEmpty(result.ImgFiles.YangZhiChangHuPic)) {
            YangZhiHuMid = result.ImgFiles.YangZhiChangHuPic;
            String BankPic =UrlUtils.pic_url+ YangZhiHuMid ;
            Glide.with(this).load(BankPic).into(binding.yangzhichangQianming);
        }

        //之子单据
        if (!TextUtils.isEmpty(result.ImgFiles.CollectCertPic)) {
            ZhiziMid = result.ImgFiles.CollectCertPic;
            String IdCardUrl = UrlUtils.pic_url + ZhiziMid;
            Glide.with(this).load(IdCardUrl).into(binding.zhizidanjuIv);
        }

    }


    private void upLoadCollect() {
        CollectInfoData collectInfoData = new CollectInfoData();
        collectInfoData.farmMid = resultBean.Dep_ApplyGUID.FarmMid;
        collectInfoData.region = new CollectInfoData.RegionBean();
        collectInfoData.region.id = resultBean.Region.id;
        collectInfoData.region.RI1 = resultBean.Region.RI1;
        collectInfoData.region.RI2 = resultBean.Region.RI2;
        collectInfoData.region.RI3 = resultBean.Region.RI3;
        collectInfoData.region.RI4 = resultBean.Region.RI4;
        collectInfoData.region.RI5 = resultBean.Region.RI5;
        collectInfoData.region.RegionCode = resultBean.Region.RegionCode;
        collectInfoData.region.RegionName = resultBean.Region.RegionName;
        collectInfoData.region.RegionLevel = resultBean.Region.RegionLevel;
        collectInfoData.region.RI1RegionName = resultBean.Region.RI1RegionName;
        collectInfoData.region.RI2RegionName = resultBean.Region.RI2RegionName;
        collectInfoData.region.RI3RegionName = resultBean.Region.RI3RegionName;
        collectInfoData.region.RI4RegionName = resultBean.Region.RI4RegionName;
        collectInfoData.region.RegionFullName = resultBean.Region.RegionFullName;
        collectInfoData.region.RegionParentID = resultBean.Region.RegionParentID;
        collectInfoData.bankType = mBankCheck;


        LogUtil.d(TAG, "选择的动物" + animalBeanChoose);
        collectInfoData.applyGUID = resultBean.ApplyGUID;
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
        collectInfoData.carInfo.Mid = resultBean.CarInfo.Mid;
        collectInfoData.carInfo.Name = resultBean.CarInfo.Name;
        ;

        collectInfoData.factoryGUID = resultBean.FactoryGUID;

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
//        collectInfoData.bankName = binding.baoxianLayout.bankNameEt.getText().toString();
        collectInfoData.bankCardNo = binding.baoxianLayout.bankNumEt.getText().toString();


        if (!TextUtils.isEmpty(binding.feedbackEt.getText().toString())) {
            collectInfoData.remark = binding.feedbackEt.getText().toString();
        }

        collectInfoData.imgFiles = new CollectInfoData.ImgFilesBean();
        collectInfoData.imgFiles.IdCardPic = IDCardMid;
        collectInfoData.imgFiles.BankPic = BankMid;
        collectInfoData.imgFiles.CollectCertPic = ZhiziMid;

        collectInfoData.imgFiles.SiChuPicList = new ArrayList<>();
        for (int i = 0; i < SiChuErBiaoPicList.size(); i++) {
            CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
            imgMidBean.Mid = SiChuErBiaoPicList.get(i);
            collectInfoData.imgFiles.SiChuPicList.add(imgMidBean);
        }

        collectInfoData.imgFiles.ShiTiPicList = new ArrayList<>();
        for (int i = 0; i < ShiTiPicList.size(); i++) {
            CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
            imgMidBean.Mid = ShiTiPicList.get(i);
            collectInfoData.imgFiles.ShiTiPicList.add(imgMidBean);
        }

        collectInfoData.imgFiles.ZhuangChePicList = new ArrayList<>();
        for (int i = 0; i < ZHuangCheList.size(); i++) {
            CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
            imgMidBean.Mid = ZHuangCheList.get(i);
            collectInfoData.imgFiles.ZhuangChePicList.add(imgMidBean);
        }
        collectInfoData.imgFiles.ShouYunYuanPic = ShouYunYuanMid;
        collectInfoData.imgFiles.YangZhiChangHuPic = YangZhiHuMid;


        List<String> ids = new ArrayList<>();
        for (AnimalMenuBean animalMenuBean : animalMenuBeans) {
            if (!"-".equals(animalMenuBean.EarTag)) {
                ids.add(animalMenuBean.EarTag);
            }
        }


        Set<String> uniqueIds = new HashSet<>(ids);
        // 如果转换后的Set大小小于原List大小，说明有重复
        if (uniqueIds.size() < ids.size()) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this, "动物清单耳标重复，请修改"));
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
            dataItemBean.Amount = animalMenuBean.ShuLiang;
            collectInfoData.itemDatas.add(dataItemBean);
        }
        collectInfoData.isFoster =mIsFosterCare;
        if (mIsFosterCare){
            collectInfoData.fosterCareName = ShouJISP.getInstance().getFosterCareName();;
        }
        ConfigSP.getInstance().setXiaoDu(binding.disinfectantsEt.getText().toString());
        showLoading("正在上传中...");


        LogUtil.d(TAG, "上传的数据" + collectInfoData.toString());
        HttpRequest.getCollect(CollectEditFillInActivity.this, collectInfoData, new CallBackLis<StatusMeBean>() {
            @Override
            public void onSuccess(String method, StatusMeBean content) {
                LogUtil.d(TAG, content.toString());
                hideLoading();
                if (content.code == 200) {
                    getXianChangJianDuYuanInfo();
                    showSuccessDiaolog();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            exitDialogSuccess.dismiss();
                            Intent intent = new Intent();
                            intent.putExtra("success", 666);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }, 2000);

                } else {
                    Objects.requireNonNull(RxToast.error(CollectEditFillInActivity.this,content.msg));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });


    }

    private void getXianChangJianDuYuanInfo() {
        HttpRequest.getXianChangJianDuYuan(CollectEditFillInActivity.this, String.valueOf(resultBean.Region.id), new CallBackLis<XianChangRenBean>() {
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
        HttpRequest.sendNewSms(CollectEditFillInActivity.this, sendSmsBean, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, SendStatusBean content) {
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }
    /**
     * filePath 上传图片
     *
     * @param filePath
     */
    private void upLoadImg(String filePath) {
        showLoading("正在上传中...");
        HttpRequest.upLoadImg(CollectEditFillInActivity.this, filePath, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d(TAG, "上传图片" + content.toString());
                if (content.Status == 0) {
                    Objects.requireNonNull(RxToast.success(CollectEditFillInActivity.this, "上传成功"));
                    if (PicBtnType == 1) {
                        IDCardMid = content.Result;
                        GlideUtils.createGlideEngine().loadRounderImage(CollectEditFillInActivity.this, UrlUtils.pic_url + content.Result, binding.baoxianLayout.idcardIv, 20);
                    } else if (PicBtnType == 2) {
                        BankMid = content.Result;
                        GlideUtils.createGlideEngine().loadRounderImage(CollectEditFillInActivity.this, UrlUtils.pic_url + content.Result, binding.baoxianLayout.bankIv, 20);
                    }else if(PicBtnType==10){
                        BankMid = content.Result;
                        GlideUtils.createGlideEngine().loadRounderImage(CollectEditFillInActivity.this, UrlUtils.pic_url + content.Result, binding.baoxianLayout.depositIv, 20);
                    } else if (PicBtnType == 3) {
                        ShouYunYuanMid = content.Result;
                    } else if (PicBtnType == 4) {
                        YangZhiHuMid = content.Result;
                    } else if (PicBtnType == 999) {
                        ZhiziMid = content.Result;
                        GlideUtils.createGlideEngine().loadRounderImage(CollectEditFillInActivity.this, UrlUtils.pic_url + content.Result, binding.zhizidanjuIv, 20);
                    }
                    hideLoading();
                } else {
                    Objects.requireNonNull(RxToast.error(CollectEditFillInActivity.this, content.Result.toString()));
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
        HttpRequest.upLoadBatchImg(CollectEditFillInActivity.this, filePath, new CallBackLis<BatchImgBean>() {
            @Override
            public void onSuccess(String method, BatchImgBean content) {
                LogUtil.d(TAG, "上传图片批量" + content.toString());
                if (content.status == 0) {
                    Objects.requireNonNull(RxToast.success(CollectEditFillInActivity.this, "上传成功"));
                    File myFolder = new File(WaterMaskUtil.SavaPhotoFile);
                    WaterMaskUtil.deleteDirectory(myFolder);

                    if (PicBtnType == 5) {
                        gridImageDeathAdapter.getData().clear();
                        for (String path : content.result) {
                            SiChuErBiaoPicList.add(path);
                        }

                        for (String path : SiChuErBiaoPicList) {
                            LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + path, PictureMimeType.MIME_TYPE_JPEG);
                            gridImageDeathAdapter.getData().add(localMedia);

                        }
                        gridImageDeathAdapter.notifyDataSetChanged();


                    } else if (PicBtnType == 6) {
                        imageShiTiAdapter.getData().clear();
                        for (String path : content.result) {
                            ShiTiPicList.add(path);
                        }
                        for (String path : ShiTiPicList) {
                            LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + path, PictureMimeType.MIME_TYPE_JPEG);
                            imageShiTiAdapter.getData().add(localMedia);
                        }
                        imageShiTiAdapter.notifyDataSetChanged();

                    } else if (PicBtnType == 7) {
                        imageZhuangCheAdapter.getData().clear();
                        for (String path : content.result) {
                            ZHuangCheList.add(path);
                        }
                        for (String path : ZHuangCheList) {
                            LocalMedia localMedia = LocalMedia.generateLocalMedia(UrlUtils.pic_url + path, PictureMimeType.MIME_TYPE_JPEG);
                            imageZhuangCheAdapter.getData().add(localMedia);
                        }
                        imageZhuangCheAdapter.notifyDataSetChanged();
                    }
                    hideLoading();
                } else {
                    Objects.requireNonNull(RxToast.error(CollectEditFillInActivity.this, "上传失败~"));
                }

            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }

    /**
     * 获取动物
     */
    private  void  getHarAnimalInfo(){
        HttpRequest.getHarAnimal(CollectEditFillInActivity.this, new CallBackLis<HarmlessAnimalBean>() {
            @Override
            public void onSuccess(String method, HarmlessAnimalBean content) {
                LogUtil.d(TAG, "获取动物" + content.toString());
                animalBeanList = new ArrayList<>();
                for (HarmlessAnimalBean.ResultAnimalBean resultBean : content.Result) {
                    animalBeanList.add(new AnimalBean(String.valueOf(resultBean.AnimalID), resultBean.AnimalName));
                }
            }
            @Override
            public void onFailure(String method, String error) {
            }
        });
    }
    private void showAnimalDialog() {
        List<String> listString = new ArrayList<>();
        for (AnimalBean animalBean : animalBeanList) {
            listString.add(animalBean.AnimalName);
        }
        pvCustomOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {

            animalBeanChoose = animalBeanList.get(options1);
            binding.animalTypeEt.setText(animalBeanList.get(options1).AnimalName);
            if (binding.animalTypeEt.getText().toString().equals("猪") || binding.animalTypeEt.getText().toString().equals("牛")){
                binding.unitTypeEt.setText("头");
                selectUnitPos = 0;
                unitBeanChoose = new UnitBean("1301","头");
            }
            if (binding.animalTypeEt.getText().toString().equals("羊")){
                binding.unitTypeEt.setText("只");
                selectUnitPos = 1;
                unitBeanChoose = new UnitBean("1302","只");
            }
            if (binding.animalTypeEt.getText().toString().equals("鸡") || binding.animalTypeEt.getText().toString().equals("鸭")|| binding.animalTypeEt.getText().toString().equals("鹅")){
                binding.unitTypeEt.setText("羽");
                selectUnitPos = 2;
                unitBeanChoose = new UnitBean("1304","羽");
            }
            if (binding.animalTypeEt.getText().toString().equals("兔") || binding.animalTypeEt.getText().toString().equals("鱼")|| binding.animalTypeEt.getText().toString().equals("其他")){
                binding.unitTypeEt.setText("公斤");
                selectUnitPos = 3;
                unitBeanChoose = new UnitBean("1305","公斤");
            }


        })
                .setLayoutRes(R.layout.custom_dongwu_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    tvSubmit.setOnClickListener(v1 -> {
                        pvCustomOptions.returnData();
                        pvCustomOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> pvCustomOptions.dismiss());

                })
                .isDialog(false)
                .setContentTextSize(18)//滚轮文字大小
                .build();
        pvCustomOptions.setPicker(listString);//添加数据
        pvCustomOptions.show();
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

        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    final TextView title_tv = (TextView) v.findViewById(R.id.title_tv);
                    title_tv.setText("消毒药品");
                    tvSubmit.setOnClickListener(v1 -> {
                        XiaoDupvCustomOptions.returnData();
                        XiaoDupvCustomOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> XiaoDupvCustomOptions.dismiss());

                })
                .isDialog(false)
                .setContentTextSize(18)//滚轮文字大小
                .build();
        XiaoDupvCustomOptions.setPicker(listString);//添加数据
        XiaoDupvCustomOptions.show();
    }

    private void showUnitDialog() {
        List<UnitBean> unitBeanList = new ArrayList<>();
        unitBeanList.add(new UnitBean("1301", "头"));//0
        unitBeanList.add(new UnitBean("1302", "只"));//1
        unitBeanList.add(new UnitBean("1304", "羽"));//2
        unitBeanList.add(new UnitBean("1305", "公斤"));//3

        List<String> listString = new ArrayList<>();
        for (UnitBean unitBean : unitBeanList) {
            listString.add(unitBean.UnitName);
        }
        UnitpvCustomOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {

            unitBeanChoose = unitBeanList.get(options1);
            binding.unitTypeEt.setText(unitBeanList.get(options1).UnitName);
        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    tvSubmit.setOnClickListener(v1 -> {
                        UnitpvCustomOptions.returnData();
                        UnitpvCustomOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> UnitpvCustomOptions.dismiss());

                })
                .isDialog(false)
                .setContentTextSize(18)//滚轮文字大小
                .setSelectOptions(selectUnitPos)
                .build();
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
        baoXianGongSiBeanList.add(new BaoXianGongSiBean("ZHBX", "中航安盟财产"));
        baoXianGongSiBeanList.add(new BaoXianGongSiBean("TJCC", "锦泰财产保险"));

        List<String> listString = new ArrayList<>();
        for (BaoXianGongSiBean baoXianGongSiBean : baoXianGongSiBeanList) {
            listString.add(baoXianGongSiBean.BaoXianGongSiName);
        }
        BaoXiaopvCustomOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {

            baoXianGongSiBeanChoose = baoXianGongSiBeanList.get(options1);
            binding.baoxianLayout.insuranceCompanyEt.setText(baoXianGongSiBeanList.get(options1).BaoXianGongSiName);

        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    tvSubmit.setOnClickListener(v1 -> {
                        BaoXiaopvCustomOptions.returnData();
                        BaoXiaopvCustomOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> BaoXiaopvCustomOptions.dismiss());

                })
                .isDialog(false)
                .setContentTextSize(18)//滚轮文字大小
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

        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    tvSubmit.setOnClickListener(v1 -> {
                        BankpvCustomOptions.returnData();
                        BankpvCustomOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> BankpvCustomOptions.dismiss());

                })
                .isDialog(false)
                .setContentTextSize(18)//滚轮文字大小
                .build();
        BankpvCustomOptions.setPicker(listString);//添加数据
        BankpvCustomOptions.show();
    }

    private boolean checkInfo() {
        if (TextUtils.isEmpty(binding.animalTypeEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请选择动物畜种"));
            return false;
        }
        if (TextUtils.isEmpty(binding.unitTypeEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请选择单位"));
            return false;
        }
        if (TextUtils.isEmpty(binding.verificationNumEt.getText().toString())) {


            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请输入核验数量"));
            return false;
        }
        if (TextUtils.isEmpty(binding.grossWeightEt.getText().toString())) {

            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请输入总重量"));
            return false;
        }

        if (TextUtils.isEmpty(binding.inventoryRelTimeEt.getText().toString())) {


            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请输入实时存栏量"));
            return false;
        }

        if (XiaoDuCheck == 0) {

            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请选择是否消毒"));
            return false;
        }
        if (TextUtils.isEmpty(binding.disinfectantsEt.getText().toString())) {

            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请输入消毒药品"));
            return false;
        }
        if (YiBingCheck == 0) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请选择是否是重大疫病"));
            return false;
        }
        if (SiWangYaunYinCheck == 0) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请选择死亡原因"));
            return false;
        }
        if (BaoXianCheck == 0) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请选择是否购买保险"));
            return false;
        }
        if (BaoXianCheck == 1 && TextUtils.isEmpty(binding.baoxianLayout.insuranceCompanyEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请选择保险公司"));
            return false;
        }
        if (TextUtils.isEmpty(binding.baoxianLayout.idcardEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请输入身份证号"));
            return false;
        }
        if (!IdCardUtils.isValidatedAllIdcard(binding.baoxianLayout.idcardEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请输入正确身份证号"));
            return false;
        }
        if (TextUtils.isEmpty(IDCardMid)) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请上传身份证照片"));
            return false;
        }

        if (mBankCheck==1){
            if (TextUtils.isEmpty(binding.baoxianLayout.bankNumEt.getText().toString())) {
                Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this, "请输入银行卡号"));
                return false;
            }
        }
        if (mBankCheck==2){
            if (TextUtils.isEmpty(binding.baoxianLayout.depositAccountEt.getText().toString())) {
                Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this, "请输入基本存款账号号码"));
                return false;
            }
        }

        if (mBankCheck==1){
            if (NetworkUtils.isNetAvailable(this)) {
                if (TextUtils.isEmpty(BankMid)) {
                    Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this, "请上传银行卡照片"));
                    return false;
                }
            }
        }
        if (mBankCheck==2){
            if (NetworkUtils.isNetAvailable(this)) {
                if (TextUtils.isEmpty(BankMid)) {
                    Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this, "请上传基本存款账户图片"));
                    return false;
                }
            }
        }
        if (animalMenuBeans.isEmpty()) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请先添加动物清单信息"));
            return false;
        }
        if (SiChuErBiaoPicList.isEmpty()) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请先上传死畜耳标照片"));
            return false;
        }

        if (ShiTiPicList.isEmpty()) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"上传死畜尸体照片"));
            return false;
        }
        if (ZHuangCheList.isEmpty()) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"上传装车照片"));
            return false;
        }
        if (TextUtils.isEmpty(ShouYunYuanMid)) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请上传收运员签名"));
            return false;
        }
        if (TextUtils.isEmpty(YangZhiHuMid)) {
            Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this,"请上传养殖场户签名"));
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
        TextView viewById = view.findViewById(R.id.content_tv);
        viewById.setText("修改成功");
        WindowManager.LayoutParams params = exitDialogSuccess.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogSuccess.getWindow().setAttributes(params);
    }



    public static String saveBitmap(Context context, Bitmap mBitmap, String name) {
        String appName = context.getResources().getString(R.string.app_name);
        String folder = Environment.getExternalStorageDirectory()
                + File.separator + appName + File.separator + "photo";
        LogUtil.d("folder=" + folder);

        File folderFile = new File(folder);

        if (!folderFile.exists()) {
            boolean isSuccess = folderFile.mkdirs();
            LogUtil.d("创建文件夹路径是否成功=" + isSuccess);
        }

        String target = folder + File.separator + name;
        LogUtil.d("target=" + target);

        File targetFile = new File(target);

        try {
            targetFile.createNewFile();
        } catch (IOException e) {
            LogUtil.d("在保存图片时出错：" + e.toString());
        }

        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(targetFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return target;
    }


    /**
     * 删除死畜照片
     * @param position
     */
    @Override
    public void deleteSiChuPic(int position) {
        LogUtil.d(TAG,"删除死畜的照片pos" + position);
        if (!SiChuErBiaoPicList.isEmpty()){
            SiChuErBiaoPicList.remove(position);
        }
        LogUtil.d(TAG,"SiChuErBiaoPicList死畜" + SiChuErBiaoPicList.size()  +  SiChuErBiaoPicList.toString());


    }

    /**
     * 删除死畜尸体照片
     * @param position
     */
    @Override
    public void deleteSTPic(int position) {
        if (!ShiTiPicList.isEmpty()){
            ShiTiPicList.remove(position);
        }


    }
    /**
     * 删除装车
     * @param position
     */
    @Override
    public void deleteZCPic(int position) {
        if (!ZHuangCheList.isEmpty()){
            ZHuangCheList.remove(position);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
        if (!TextUtils.isEmpty(resultBean.FosterCareName)){
            mFosterCareEt.setText(resultBean.FosterCareName);
        }
        confirmTv.setOnClickListener(v -> {
            String string = mFosterCareEt.getText().toString().trim();
            if (!TextUtils.isEmpty(string)){
                binding.daiyangLl.setVisibility(View.VISIBLE);
                ShouJISP.getInstance().setFosterCareName(string);
                binding.daiyangNameTv.setText("【代养主体名称：" + string +"】");
                exitDialogLoginOut.dismiss();
            }else {
                Objects.requireNonNull(RxToast.warning(CollectEditFillInActivity.this, "当选择代养为是时，请输入代养主体名称"));
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

