package com.agridata.cdzhdj.activity.harmless;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.FirstLoginSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.databinding.ActivitySignBinding;
import com.agridata.cdzhdj.utils.ImageLoaderUtils;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lzx.utils.RxToast;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @auther 56454
 * @date 2022/6/25
 * @time 14:27.
 */
public class SignActivity extends BaseActivity<ActivitySignBinding> {

    private String name;
    private int mCode;
    private String filePath;

    public static void start(Activity context, String name, int code) {
        Intent intent = new Intent(context, SignActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("code", code);
        context.startActivityForResult(intent,code);
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        this.name = this.getIntent().getStringExtra("name");
        this.mCode = this.getIntent().getIntExtra("code",0);
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
                            Objects.requireNonNull(RxToast.info(SignActivity.this, "权限获取成功"));
                            FirstLoginSP.getInstance().setFirstShow(true);
                        } else {
                            Objects.requireNonNull(RxToast.warning(SignActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(SignActivity.this, "被永久拒绝授权，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(SignActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(SignActivity.this, "获取权限失败"));
                        }
                    }
                });
    }
    @Override
    protected void initView() {
        getPermissions();
        getArguments();
        ImmersionBar.with(this).statusBarDarkFont(true). statusBarView(binding.view).
                statusBarColor(R.color.white).
                autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCode = 200;
                Intent i = new Intent();
                i.putExtra("finish", "返回");

                setResult(200, i);
                finish();
            }
        });
        binding.bannerTitle.setText(name);

        binding.bannerRightText.setText("确定");
        binding.bannerRightText.setTextColor( getResources().getColor(R.color.black));
        binding.bannerRightText2.setVisibility(View.VISIBLE);
        binding.bannerRightText2.setText("清除");
        binding.bannerRightText2.setTextColor( getResources().getColor(R.color.black));
        binding.bannerRightText2.setOnClickListener(v -> binding.signaturePad.clear());

        binding.bannerRightText.setOnClickListener(v -> {
            if (binding.signaturePad.isEmpty()) return;
            Bitmap bitmap = binding.signaturePad.getSignatureBitmap();
            String fileName;
            fileName = "temp" + StrUtil.getUUID() + ".png";
            String path = getFilesDir().getPath();
            filePath = path + "/" + fileName;
            try {
                ImageLoaderUtils.saveBitmap(bitmap, filePath, true);
            } catch (IOException e) {
                e.printStackTrace();
                ToastUtil.showToast(SignActivity.this,"保存签名失败");
                return;
            }
            if (mCode==10){
                Intent i = new Intent();
                i.putExtra("qianming", filePath);
                setResult(RESULT_OK, i);
            }else {
                Intent i = new Intent();
                i.putExtra("yangzhichanghu", filePath);
                setResult(RESULT_OK, i);
            }

            finish();
        });
    }

    @Override
    protected void initDatas() {

    }
}
