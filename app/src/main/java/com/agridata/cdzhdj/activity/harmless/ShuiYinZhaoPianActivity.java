package com.agridata.cdzhdj.activity.harmless;

import static com.agridata.cdzhdj.base.MyApplication.getContext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.databinding.ShuiyinActivityBinding;
import com.agridata.cdzhdj.utils.ImageLoader;
import com.agridata.cdzhdj.utils.PictureSelectorUtils;
import com.agridata.network.utils.LogUtil;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.luck.picture.lib.basic.PictureSelectionCameraModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.PictureFileUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-05-09 17:55.
 * @Description :描述
 */
public class ShuiYinZhaoPianActivity extends BaseActivity<ShuiyinActivityBinding> {

    private final static String TAG = "lzx------》";
    private Bitmap photoBitmap;

    public static void start(Context context) {
        Intent intent = new Intent(context, ShuiYinZhaoPianActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ShuiyinActivityBinding getBinding() {
        return ShuiyinActivityBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {

        binding.paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XXPermissions.with(ShuiYinZhaoPianActivity.this).permission(Permission.CAMERA).request((permissions, all) -> {
                    if (all) {
                        TakePicturesAlone("SINGLE");
                    }
                });
            }
        });

    }


    private void TakePicturesAlone(String type) {
        // Take pictures alone
        PictureSelectionCameraModel cameraModel = PictureSelector.create(ShuiYinZhaoPianActivity.this).openCamera(SelectMimeType.ofImage())

                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())//裁剪引擎
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())//压缩引擎
                .isOriginalControl(false);
        forOnlyCameraResult(cameraModel, type);
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
                if (!result.isEmpty()) {
                    //TODO:2024-04-30 离线身份证
                    Bitmap originalBitmap = ImageLoader.convertFileToBitmap(result.get(0).getCompressPath());
                    //Bitmap watermarkedBitmap = addWatermarkToBitmap(originalBitmap);
                    Bitmap watermarkImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_sy_bg); // 替换为你的水印图片
                    photoBitmap = addWatermarkToBitmap(originalBitmap, watermarkImage);
                    binding.shuiyinIc.setImageBitmap(photoBitmap);
                }


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
        String locationText = "Location: Your location here";
        String timeText = "Time: " + DateFormat.getDateTimeInstance().format(new Date());
        canvas.drawText(locationText, 30, src.getHeight() - 50, paint);
        canvas.drawText(timeText, 30, src.getHeight() - 120, paint);


        Paint paint1 = new Paint();
        paint1.setColor(Color.parseColor("#1890FF")); // 设置颜色为红色

        paint1.setTextSize(spToPx(this, 22)); // 设置字体大小
        paint1.setAntiAlias(true); // 设置抗锯齿
        paint1.setAlpha(50); // 设置透明度

//        // 计算水印文字的起始位置，你可以根据需要进行调整
        int startX = 0;
        int startY = 0;
        int spacing = 300; // 设置水印文字之间的间距
//
//        // 在Canvas上绘制水印文字
        LogUtil.d("lzx-----》",src.getWidth() +"");
        LogUtil.d("lzx-----》",src.getHeight() +"");
        while (startX < src.getWidth() && startY < src.getHeight()) {
            LogUtil.d("lzx-----》","GGAGDGASHDJASHD");
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

    // 将sp转换为px
    private static float spToPx(Context context, float sp) {
        return sp * context.getResources().getDisplayMetrics().scaledDensity;
    }

    @Override
    protected void initDatas() {

    }
}
