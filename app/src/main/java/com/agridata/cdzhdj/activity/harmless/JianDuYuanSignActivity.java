package com.agridata.cdzhdj.activity.harmless;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RelativeLayout;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.AddressSP;
import com.agridata.cdzhdj.SPUtil.FirstLoginSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.databinding.ActivitySignBinding;
import com.agridata.cdzhdj.utils.ImageLoaderUtils;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.utils.ToastUtil;
import com.agridata.cdzhdj.utils.WaterMaskUtil;
import com.agridata.cdzhdj.view.WaterMaskView;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lzx.utils.RxToast;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @auther 56454
 * @date 2022/6/25
 * @time 14:27.
 */
public class JianDuYuanSignActivity extends BaseActivity<ActivitySignBinding> {
    private final static String TAG = "lzx------》";
    private String name;
    private int mCode;
    private String picMid;

    public static void start(Activity context, String name, int code) {
        Intent intent = new Intent(context, JianDuYuanSignActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("code", code);
        context.startActivityForResult(intent, code);
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        this.name = this.getIntent().getStringExtra("name");
        this.mCode = this.getIntent().getIntExtra("code", 0);
        LogUtil.d(TAG, " mCode" + this.mCode);
    }

    @Override
    protected ActivitySignBinding getBinding() {
        return ActivitySignBinding.inflate(getLayoutInflater());
    }
    private void getPermissions() {
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            Objects.requireNonNull(RxToast.info(JianDuYuanSignActivity.this, "权限获取成功"));
                            FirstLoginSP.getInstance().setFirstShow(true);
                        } else {
                            Objects.requireNonNull(RxToast.warning(JianDuYuanSignActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(JianDuYuanSignActivity.this, "被永久拒绝授权，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(JianDuYuanSignActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(JianDuYuanSignActivity.this, "获取权限失败"));
                        }
                    }
                });
    }
    @Override
    protected void initView() {
        getPermissions();
        getArguments();
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarView(binding.view).
                statusBarColor(R.color.white).
                autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("picMid", "1");
                setResult(999, i);
                finish();
            }
        });
        binding.bannerTitle.setText(name);
        binding.bannerRightText.setText("确定");
        binding.bannerRightText2.setVisibility(View.VISIBLE);
        binding.bannerRightText2.setText("清除");
        binding.bannerRightText.setTextColor( getResources().getColor(R.color.black));
        binding.bannerRightText2.setTextColor( getResources().getColor(R.color.black));
        binding.bannerRightText2.setOnClickListener(v -> binding.signaturePad.clear());
        binding.bannerRightText.setOnClickListener(v -> {
            if (binding.signaturePad.isEmpty()) return;
            Bitmap bitmap = binding.signaturePad.getSignatureBitmap();
            String fileName;
            fileName = "temp" + StrUtil.getUUID() + ".png";
            String path = getFilesDir().getPath();
            String filePath = path + "/" + fileName;

           // Bitmap sourBitmap = BitmapFactory.decodeFile(filePath);
            Bitmap WaterBitmap = saveWaterMask(JianDuYuanSignActivity.this, 4, bitmap);
            String fileNameUrl = "IMG_" + new Date().getTime() + "qianming" + ".png";
            String shouyunyuanUrl = WaterMaskUtil.saveQNext(WaterBitmap, JianDuYuanSignActivity.this, fileNameUrl, 60);

            try {
                ImageLoaderUtils.saveBitmap(bitmap, filePath, true);
            } catch (IOException e) {
                e.printStackTrace();
                ToastUtil.showToast(JianDuYuanSignActivity.this, "保存签名失败");
                return;
            }
            upLoadImg(shouyunyuanUrl);
        });
    }

    /**
     * filePath 上传图片
     *
     * @param filePath
     */
    private void upLoadImg(String filePath) {
        HttpRequest.upLoadImg(JianDuYuanSignActivity.this, filePath, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d(TAG, "上传图片" + content.toString());
                if (content.Status == 0) {
                    //  ToastUtil.showToast(JianDuYuanSignActivity.this, "上传成功~");
                }
                picMid = content.Result;
                Intent i = new Intent();
                i.putExtra("picMid", picMid);
                setResult(RESULT_OK, i);
                finish();
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }

    @Override
    protected void initDatas() {

    }


    /**
     * @param position 左上为0，顺时针算起
     */
    private static Bitmap saveWaterMask(Context context, int position, Bitmap sourBitmap) {
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
        waterMaskView.setInfoZuoBiao(AddressSP.getInstance().getLatitude() + "," + AddressSP.getInstance().getLongitude());
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
