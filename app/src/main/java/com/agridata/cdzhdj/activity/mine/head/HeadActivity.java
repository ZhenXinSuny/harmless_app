package com.agridata.cdzhdj.activity.mine.head;

import static com.agridata.cdzhdj.base.MyApplication.getContext;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.databinding.ActivityHeadBinding;
import com.agridata.cdzhdj.utils.ImageLoaderUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.bottomPopupDialog.BottomPopupDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.engine.CropFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.photoview.OnPhotoTapListener;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.style.TitleBarStyle;
import com.luck.picture.lib.utils.DateUtils;
import com.luck.picture.lib.utils.StyleUtils;
import com.lzx.utils.RxToast;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropImageEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;
import top.zibin.luban.OnRenameListener;

/**
 * @auther 56454
 * @date 2022/7/8
 * @time 17:12.
 */
public class HeadActivity extends BaseActivity<ActivityHeadBinding> {
    private PictureSelectorStyle selectorStyle;
    private String PicUrl;
    private CustomLoadingDialog mLoadingDialog;
    private LoginData userInfo;

    public static void start(Activity context, String pic_url) {
        Intent intent = new Intent(context, HeadActivity.class);
        Bundle data = new Bundle();
        data.putString("pic_url", pic_url);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }


    private void getArguments() {
        Bundle data = getIntent().getBundleExtra("data");
        this.PicUrl = data.getString("pic_url");
    }

    @Override
    protected ActivityHeadBinding getBinding() {
        return ActivityHeadBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        getArguments();

        initLoadingView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        Glide.with(HeadActivity.this)
                .load(PicUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.groupHeadPv);
        binding.groupHeadPv.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                HeadActivity.this.finish();
            }
        });

        binding.groupHeadPv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                showBottomSheet();
                return true;
            }
        });
    }

    @Override
    protected void initDatas() {
        userInfo = UserDataSP.getInstance().getUserInfo();
    }

    /**
     * 显示底部弹出框
     */
    private void showBottomSheet() {
        List<String> bottomDialogContents = new ArrayList<>();
        bottomDialogContents.add(getResources().getString(R.string.select_from_album));
        bottomDialogContents.add(getResources().getString(R.string.take_pictures));
        final BottomPopupDialog bottomPopupDialog = new BottomPopupDialog(this, bottomDialogContents);
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


                    PictureSelector.create(HeadActivity.this)
                            .openSystemGallery(SelectMimeType.ofImage())
                            .setCropEngine(getCropFileEngine())
                            .setCompressEngine(getCompressFileEngine())
                            .forSystemResult(new OnResultCallbackListener<LocalMedia>() {
                                @Override
                                public void onResult(ArrayList<LocalMedia> result) {
                                    LogUtil.d("lzx------>", result.toString());
                                    LogUtil.d("lzx------>", result.get(0).getCompressPath());
                                    upLoadImg(result.get(0).getCompressPath());
                                }

                                @Override
                                public void onCancel() {

                                }
                            });


                } else if (position == 1) {//拍照设置头像
                    PictureSelector.create(HeadActivity.this).openCamera(SelectMimeType.ofImage())
                            .isCameraForegroundService(true)
                            .setCropEngine(getCropFileEngine())
                            .setCompressEngine(getCompressFileEngine())
                            .forResult(new OnResultCallbackListener<LocalMedia>() {
                                @Override
                                public void onResult(ArrayList<LocalMedia> result) {
                                    LogUtil.d("lzx------>", result.toString());
                                    LogUtil.d("lzx------>", result.get(0).getCompressPath());
                                    upLoadImg(result.get(0).getCompressPath());
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

    /**
     * filePath 上传图片
     *
     * @param filePath
     */
    private void upLoadImg(String filePath) {
        showLoading("正在上传中...");
        HttpRequest.upLoadHeadImg(this, filePath, userInfo.Result.userId, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d("lzx----->", "上传图片" + content.toString());
                if (content.Status == 0) {
                    hideLoading();
                    Glide.with(HeadActivity.this)
                            .load(content.Result)
                            .into(binding.groupHeadPv);
                    setUserInfoSp(content.Result);
                    Objects.requireNonNull(RxToast.success(HeadActivity.this, "头像更改成功"));
                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }
    private void  setUserInfoSp(String path){
        userInfo.Result.avatar = path;
        UserDataSP.getInstance().setUserInfo(userInfo);
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
                options.setStatusBarColor(ContextCompat.getColor(HeadActivity.this, R.color.ps_color_grey));
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

    /**
     * 压缩引擎
     *
     * @return
     */
    private ImageFileCompressEngine getCompressFileEngine() {
        return  new ImageFileCompressEngine();
    }

    /**
     * 自定义压缩
     */
    private static class ImageFileCompressEngine implements CompressFileEngine {

        @Override
        public void onStartCompress(Context context, ArrayList<Uri> source, OnKeyValueResultCallbackListener call) {
            Luban.with(context).load(source).ignoreBy(100).setRenameListener(new OnRenameListener() {
                @Override
                public String rename(String filePath) {
                    int indexOf = filePath.lastIndexOf(".");
                    String postfix = indexOf != -1 ? filePath.substring(indexOf) : ".jpg";
                    return DateUtils.getCreateFileName("CMP_") + postfix;
                }
            }).setCompressListener(new OnNewCompressListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(String source, File compressFile) {
                    if (call != null) {
                        call.onCallback(source, compressFile.getAbsolutePath());
                    }
                }

                @Override
                public void onError(String source, Throwable e) {
                    if (call != null) {
                        call.onCallback(source, null);
                    }
                }
            }).launch();
        }
    }

}
