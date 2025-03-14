package com.agridata.cdzhdj.utils;

import static com.agridata.cdzhdj.base.MyApplication.getContext;

import android.app.Activity;
import android.content.DialogInterface;
import android.provider.MediaStore;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.net.myInterface.PicInterface;
import com.agridata.cdzhdj.view.bottomPopupDialog.BottomPopupDialog;
import com.agridata.network.utils.LogUtil;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.luck.picture.lib.basic.PictureSelectionCameraModel;
import com.luck.picture.lib.basic.PictureSelectionModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.PictureFileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-30 15:54.
 * @Description :描述  html 调用android 上传照片方法
 */
public class PicUtils {

    private final static String TAG = "lzx------》";
    private static Activity mActivity;
    private static String  mType;
    public static void showPicDialog(Activity activity,String type) {
        mActivity = activity;
        mType = type;
        List<String> bottomDialogContents = new ArrayList<>();
        bottomDialogContents.add("拍照");
        //bottomDialogContents.add("相册选取");
        final BottomPopupDialog bottomPopupDialog = new BottomPopupDialog(activity, bottomDialogContents);
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

    private static void getPermissionCream() {
        XXPermissions.with(mActivity).permission(Permission.CAMERA).request((permissions, all) -> {
            if (all) {
                TakePicturesAlone();
            }
        });
    }

    //单独拍照
    private static void TakePicturesAlone() {
        // Take pictures alone
        PictureSelectionCameraModel cameraModel = PictureSelector.create(mActivity)
                .openCamera(SelectMimeType.ofImage())
                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())//裁剪引擎
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())//压缩引擎
                .isOriginalControl(true);
        forOnlyCameraResult(cameraModel);
    }

    // 相册选择
    private static void OpenPicture() {
        // 进入相册
        PictureSelectionModel selectionModel = PictureSelector.create(mActivity)
                .openGallery(SelectMimeType.ofImage())
                .setSelectorUIStyle(PictureSelectorUtils.setStyle())
                .setImageEngine(GlideEngine.createGlideEngine())
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



    private static void forOnlyCameraResult(PictureSelectionCameraModel model) {
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
    private static void forSelectResult(PictureSelectionModel model, String typeName) {

        switch (typeName) {
            case "SINGLE"://银行卡号照片 + 身份证照片
                setModel(model, "SINGLE");
                break;
        }

    }
    private static void setModel(PictureSelectionModel model, String typeName) {
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


    private static void analyticalSelectResults(ArrayList<LocalMedia> result, String typeName) {
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
            //upLoadImg(media.getCompressPath());
            if ("SINGLE".equals(typeName)) {
                mCallBack.Success(media.getCompressPath() == null ? media.getRealPath() : media.getCompressPath(), mType);
            }
        }
    }
    private static PicInterface.CallBack mCallBack;


    public static  void setCallBack(PicInterface.CallBack callBack) {
        mCallBack = callBack;
    }
}
