package com.agridata.cdzhdj.activity.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.harmless.offlinesubmission.OfflineSubmissionCollectionActivity;
import com.agridata.cdzhdj.activity.mine.head.HeadActivity;
import com.agridata.cdzhdj.activity.mine.setting.AboutActivity;
import com.agridata.cdzhdj.activity.mine.setting.SettingActivity;
import com.agridata.cdzhdj.base.BaseFragment;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.data.UserData;
import com.agridata.cdzhdj.databinding.FragmentMineBinding;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.agridata.cdzhdj.utils.ImageLoaderUtils;
import com.agridata.cdzhdj.utils.NetworkUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.bottomPopupDialog.BottomPopupDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.agridata.util.Base64Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CropFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.style.TitleBarStyle;
import com.luck.picture.lib.utils.StyleUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lzx.utils.RxToast;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropImageEngine;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MineFragment extends BaseFragment<FragmentMineBinding> {

    private final static String TAG = "lzx------》";
    private PictureSelectorStyle selectorStyle;
    private LoginData userInfo;
    private CustomLoadingDialog mLoadingDialog;


    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected void initView() {
        initLoadingView();
        userInfo = UserDataSP.getInstance().getUserInfo();
        if (userInfo!=null ) {
            binding.nameTv.setText(userInfo.Result.name);
        } else {
            binding.nameTv.setText(userInfo.Result.mobile);
        }
        binding.homeCode.setText("账号：" + userInfo.Result.mobile);
        ArrayList<String> rolesList = new ArrayList<>();
        for (LoginData.ResultBean.RolesBean role : userInfo.Result.roles) {
            LogUtil.d("lzx----->", "名字" + role.toString());
            rolesList.add(role.name);
        }
        String[] strings = rolesList.toArray(new String[0]);
        binding.labeledLs.setLabels(rolesList, (label, position, data) -> {
            // label就是标签项，在这里可以对标签项单独设置一些属性，比如文本样式等。
            //根据data和position返回label需要显示的数据。
            return data;
        });
        binding.homeRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这种弹窗从 1.0.0版本开始实现了优雅的手势交互和智能嵌套滚动
                new XPopup.Builder(requireActivity()).isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .borderRadius(22).maxHeight(1000).asCenterList("我的角色信息", strings, null, new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {

                            }
                        }).show();
            }
        });
    }

    @Override
    protected void initDatas() {
        selectorStyle = new PictureSelectorStyle();
        binding.settingLl.setOnClickListener(v -> SettingActivity.start(requireActivity()));
        binding.aboutLl.setOnClickListener(v -> AboutActivity.start(requireActivity()));
        binding.headIv.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(userInfo.Result.avatar)) {
                HeadActivity.start(requireActivity(), userInfo.Result.avatar);
            } else {
                showBottomSheet();
            }
        });
        binding.myInfoUpdataLl.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    getUserInfo();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        //离线提交
        binding.collectionInfoLl.setOnClickListener(v -> {
            if (NetworkUtils.isNetAvailable(requireActivity())){
                getRole();
            }else {
                Objects.requireNonNull(RxToast.warning(requireActivity(),"当前暂无网络，请检查您的网络情况"));
            }
        });
    }


    private void getRole() {
        showLoading("获取权限中...");
        HttpRequest.queryAuth(requireActivity(), UserDataSP.getInstance().getUserInfo().Result.userId, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, RoleBean roleBean) {
                if (roleBean.code == 500) {
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(requireActivity(), roleBean.msg));
                } else {
                    hideLoading();
                    if (!roleBean.data.userRole.isEmpty()) {
                        RoleSP.getInstance().setRoleInfo(roleBean.data);
                        List<String> roles = new ArrayList<>();
                        for (RoleBean.DataBean.UserRoleBean userRoleBean : roleBean.data.userRole) {
                            roles.add(userRoleBean.id);
                        }
                        if (roles.contains("20bd5e670a974e6b81c090b5c2b07cb5")) {
                            OfflineSubmissionCollectionActivity.start(requireActivity());
                        } else {
                            Objects.requireNonNull(RxToast.warning(requireActivity(), "您当前暂无无害化现场收运员角色，无法进行离线数据提交"));
                        }

                    } else {
                        Objects.requireNonNull(RxToast.warning(requireActivity(), "您当前暂无无害化角色，请联系管理员"));
                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                LogUtil.d(TAG, "error" + error);
                Objects.requireNonNull(RxToast.error(requireActivity(), error));
            }
        });
    }


    /**
     * 获取个人信息并同步Sp
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getUserInfo() throws UnsupportedEncodingException {
        UserData userData = new UserData();
        userData.userId = UserDataSP.getInstance().getUserInfo().Result.userId;
        userData.partitionId = "d5896b31964e425382df52f655dedfc2";
        String jsonString = GsonUtil.toJson(userData);
        String signString = Base64Util.encodeData(jsonString);
        showLoading("正在同步中...");
        HttpRequest.getUsserInfo(requireActivity(), signString, new CallBackLis<LoginData>() {
            @Override
            public void onSuccess(String method, LoginData loginData) {
                hideLoading();
                LogUtil.d("lzx---------》", loginData.toString());
                // 先获取当前存储的 UserInfo
//                LoginData existingData = UserDataSP.getInstance().getUserInfo();
//                if (existingData != null && existingData.Result.token() != null) {
//                    // 只保留旧 token，防止被覆盖
//                    loginData.Result.token = existingData.Result.token;
//                }
                // 存储更新后的 loginData
                UserDataSP.getInstance().setUserInfo(loginData);
                Objects.requireNonNull(RxToast.success(requireActivity(), "同步完成"));
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(requireActivity(), error));
            }
        });
    }

    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(requireActivity());
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

    /**
     * 显示底部弹出框
     */
    private void showBottomSheet() {
        List<String> bottomDialogContents = new ArrayList<>();
        bottomDialogContents.add(getResources().getString(R.string.select_from_album));
        bottomDialogContents.add(getResources().getString(R.string.take_pictures));
        final BottomPopupDialog bottomPopupDialog = new BottomPopupDialog(getContext(), bottomDialogContents);
        bottomPopupDialog.setTitleText("请选择上传方式");
        bottomPopupDialog.setTitleColor(R.color.Red);
        bottomPopupDialog.showCancelBtn(true);
        bottomPopupDialog.show();
        bottomPopupDialog.setCancelable(true);
        bottomPopupDialog.setOnItemClickListener(new BottomPopupDialog.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                bottomPopupDialog.dismiss();
                if (position == 0) {    // 相册选择头像


                    PictureSelector.create(requireActivity()).openSystemGallery(SelectMimeType.ofImage()).setCropEngine(getCropFileEngine()).forSystemResult(new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(ArrayList<LocalMedia> result) {
                            LogUtil.d("lzx------>", result.toString());
                            LogUtil.d("lzx------>", result.get(0).getRealPath());
                            upLoadImg(result.get(0).getRealPath());
                        }

                        @Override
                        public void onCancel() {

                        }
                    });


                } else if (position == 1) {//拍照设置头像
                    PictureSelector.create(requireActivity()).openCamera(SelectMimeType.ofImage()).isCameraForegroundService(true).setCropEngine(getCropFileEngine()).forResult(new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(ArrayList<LocalMedia> result) {
                            LogUtil.d("lzx------>", result.toString());
                            LogUtil.d("lzx------>", result.get(0).getRealPath());
                            upLoadImg(result.get(0).getRealPath());
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
            }
        });
        bottomPopupDialog.setOnDismissListener(DialogInterface::dismiss);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(UserDataSP.getInstance().getUserInfo().Result.avatar)) {
            Glide.with(requireActivity()).load(UserDataSP.getInstance().getUserInfo().Result.avatar).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(binding.headIv);
        }
    }

    private void setUserInfoSp(String path) {
        userInfo.Result.avatar = path;
        UserDataSP.getInstance().setUserInfo(userInfo);
    }

    /**
     * filePath 上传图片
     *
     * @param filePath
     */
    private void upLoadImg(String filePath) {
        showLoading("正在上传中...");
        HttpRequest.upLoadHeadImg(requireActivity(), filePath, userInfo.Result.userId, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d(TAG, "上传图片" + content.toString());
                hideLoading();
                if (content.Status == 0) {
                    Glide.with(requireActivity()).load(content.Result).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(binding.headIv);
                    setUserInfoSp(content.Result);
                    Objects.requireNonNull(RxToast.success(requireActivity(), "头像上传成功"));
                } else {
                    Objects.requireNonNull(RxToast.error(requireActivity(), "头像上传失败"));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull(RxToast.error(requireActivity(), error.toString()));
            }
        });
    }


    /**
     * 裁剪引擎
     *
     * @return
     */
    private ImageFileCropEngine getCropFileEngine() {
        return new ImageFileCropEngine();
    }

    /**
     * 自定义裁剪
     */
    private class ImageFileCropEngine implements CropFileEngine {

        @Override
        public void onStartCrop(Fragment fragment, Uri srcUri, Uri destinationUri, ArrayList<String> dataSource, int requestCode) {
            UCrop.Options options = buildOptions();
            UCrop uCrop = UCrop.of(srcUri, destinationUri, dataSource);
            uCrop.withOptions(options);
            uCrop.setImageEngine(new UCropImageEngine() {
                @Override
                public void loadImage(Context context, String url, ImageView imageView) {
                    if (!ImageLoaderUtils.assertValidRequest(context)) {
                        return;
                    }
                    LogUtil.d("lzx----》", "裁剪" + url);
                    Glide.with(context).load(url).override(180, 180).into(imageView);
                }

                @Override
                public void loadImage(Context context, Uri url, int maxWidth, int maxHeight, OnCallbackListener<Bitmap> call) {
                }
            });
            uCrop.start(fragment.requireActivity(), fragment, requestCode);
        }
    }

    /**
     * 配制UCrop，可根据需求自我扩展
     *
     * @return
     */
    private UCrop.Options buildOptions() {
        UCrop.Options options = new UCrop.Options();
        options.setHideBottomControls(true);
        options.setShowCropGrid(true);
        options.setCircleDimmedLayer(true);
        options.isCropDragSmoothToCenter(false);
        options.isForbidCropGifWebp(true);
        options.isForbidSkipMultipleCrop(false);
        options.setMaxScaleMultiplier(100);
        if (selectorStyle != null && selectorStyle.getSelectMainStyle().getStatusBarColor() != 0) {
            SelectMainStyle mainStyle = selectorStyle.getSelectMainStyle();
            boolean isDarkStatusBarBlack = mainStyle.isDarkStatusBarBlack();
            int statusBarColor = mainStyle.getStatusBarColor();
            options.isDarkStatusBarBlack(isDarkStatusBarBlack);
            if (StyleUtils.checkStyleValidity(statusBarColor)) {
                options.setStatusBarColor(statusBarColor);
                options.setToolbarColor(statusBarColor);
            } else {
                options.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.ps_color_grey));
                options.setToolbarColor(ContextCompat.getColor(getContext(), R.color.ps_color_grey));
            }
            TitleBarStyle titleBarStyle = selectorStyle.getTitleBarStyle();
            if (StyleUtils.checkStyleValidity(titleBarStyle.getTitleTextColor())) {
                options.setToolbarWidgetColor(titleBarStyle.getTitleTextColor());
            } else {
                options.setToolbarWidgetColor(ContextCompat.getColor(getContext(), R.color.ps_color_white));
            }
        } else {
            options.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.ps_color_grey));
            options.setToolbarColor(ContextCompat.getColor(getContext(), R.color.ps_color_grey));
            options.setToolbarWidgetColor(ContextCompat.getColor(getContext(), R.color.ps_color_white));
        }
        return options;
    }

}