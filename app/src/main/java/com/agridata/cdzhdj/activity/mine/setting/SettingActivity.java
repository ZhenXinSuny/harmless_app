package com.agridata.cdzhdj.activity.mine.setting;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebStorage;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.CookiesSP;
import com.agridata.cdzhdj.SPUtil.FirstLoginSP;
import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.mine.AgreementActivity;
import com.agridata.cdzhdj.activity.LoginActivity;
import com.agridata.cdzhdj.activity.mine.setting.securityverification.SecurityVerificationActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.databinding.ActivitySettingBinding;
import com.agridata.cdzhdj.utils.ActivityManager;
import com.agridata.cdzhdj.utils.CookieUtils;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lzx.utils.RxToast;

import java.util.Objects;

/**
 * @author 56454
 * @auther 56454
 * @date 2022/8/4
 * @time 11:30.
 */
public class SettingActivity extends BaseActivity<ActivitySettingBinding> {

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivitySettingBinding getBinding() {
        return ActivitySettingBinding.inflate(getLayoutInflater());
    }
    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setImageResource(R.drawable.title_back);
        binding.titlebarMiddle.setText("设置");
        binding.titlebarLeft.setOnClickListener(v -> finish());

        binding.aboutLl.setOnClickListener(v -> {
           AboutActivity.start(SettingActivity.this);
        });

        binding.llAgreement.setOnClickListener(v -> {
            AgreementActivity.start(SettingActivity.this, 0);
        });

        binding.llPrivacy.setOnClickListener(v -> {
            AgreementActivity.start(SettingActivity.this, 1);
        });


        binding.btnExitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog(2);
            }
        });

        binding.logoutLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog(1);
            }
        });

        binding.changepasswordLl.setOnClickListener(v -> {
            SecurityVerificationActivity.start(this);
        });
    }

    @Override
    protected void initDatas() {

    }
    private void showExitDialog(int Type) {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        AlertDialog exitDialogLoginOut = new AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(exitDialogLoginOut.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView title_tv = view.findViewById(R.id.title_tv);
        title_tv.setVisibility(View.VISIBLE);
        if (Type==1){
            title_tv.setText("退出登录");
            contentTv.setText("您确定要退出登录吗？");
        }else {
            title_tv.setText("注销账号");
            contentTv.setText("您确定要注销当前账号吗？一旦注销且无法找回。");
        }

        cancelTv.setOnClickListener(view12 -> exitDialogLoginOut.dismiss());
        confirmTv.setOnClickListener(v -> {
                    exitDialogLoginOut.dismiss();
                    Logout(Type);

                }
        );
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }
    private void Logout(int Type) {
        LoginData result = UserDataSP.getInstance().getUserInfo();
        HttpRequest.getLogout(SettingActivity.this, result.Result.userId, new CallBackLis<StatusData>() {
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.Status == 0) {
                    if (Type==1){
                        Objects.requireNonNull(RxToast.success(SettingActivity.this,"退出登录成功"));
                    }else {
                        Objects.requireNonNull(RxToast.success(SettingActivity.this,"账号注销成功"));
                    }

                    UserDataSP.getInstance().clear();
                    RoleSP.getInstance().clear();
                    CookiesSP.getInstance().clear();
                    WebStorage.getInstance().deleteAllData(); //清空WebView的localStorage
                    CookieUtils.clearCookie();
                    FirstLoginSP.getInstance().setLogin(false);
                    FirstLoginSP.getInstance().setFirstShow(true);
                    ActivityManager.getInstance().finishAllActivity();
                    System.gc();
                    LoginActivity.start(SettingActivity.this);
                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }


}
