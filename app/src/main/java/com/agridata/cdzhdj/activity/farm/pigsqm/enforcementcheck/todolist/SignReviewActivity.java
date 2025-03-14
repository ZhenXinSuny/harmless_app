package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.todolist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.databinding.ActivitySignBinding;
import com.agridata.cdzhdj.utils.ImageLoaderUtils;
import com.agridata.cdzhdj.utils.RxBus;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.IOException;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-10 11:24.
 * @Description :描述
 */
public class SignReviewActivity extends BaseActivity<ActivitySignBinding> {

    private String name;
    private String filePath;

    public static void start(Activity context, String name) {
        Intent intent = new Intent(context, SignReviewActivity.class);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        this.name = this.getIntent().getStringExtra("name");
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
                    }
                    @Override
                    public void onDenied(List<String> permissions, boolean never) {

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
        binding.bannerTitle.setText(this.name);
        binding.titlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.bannerTitle.setText(name);

        binding.bannerRightText.setText("确定");
        binding.bannerRightText.setTextColor(getResources().getColor(R.color.black));
        binding.bannerRightText2.setVisibility(View.VISIBLE);
        binding.bannerRightText2.setText("清除");
        binding.bannerRightText2.setTextColor(getResources().getColor(R.color.black));
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
                ToastUtil.showToast(SignReviewActivity.this, "保存签名失败");
                return;
            }
            switch (name){
                case "复查人员签名":
                    RxBus.getInstance().post("ReviewCheckZFRY", filePath);
                    break;
                case "被检查单位负责人签名":
                    RxBus.getInstance().post("ReviewCheckDWFZRQM", filePath);
                    break;
                default:
                    break;

            }

            finish();
        });
    }

    @Override
    protected void initDatas() {

    }
}