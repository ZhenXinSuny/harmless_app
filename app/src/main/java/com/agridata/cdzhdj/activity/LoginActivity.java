package com.agridata.cdzhdj.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.AccountPwdSp;
import com.agridata.cdzhdj.SPUtil.FirstLoginSP;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.mine.AgreementActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.AddLoginUserBean;
import com.agridata.cdzhdj.data.LoginAccountData;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.databinding.LoginActivityBinding;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.agridata.cdzhdj.utils.KeyBoardUtil;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.agridata.util.Base64Util;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lzx.utils.RxToast;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;

/**
 * @auther 56454
 * @date 2022/6/13
 * @time 11:09.
 * 登录页面
 */
public class LoginActivity extends BaseActivity<LoginActivityBinding> implements View.OnClickListener {


    private String account;
    private String verification;
    private MyCountDownTimer mCountDownTimer;
    private CustomLoadingDialog mLoadingDialog;
    private boolean isSmsAccountLogin = false;
    private String phoneNum;
    private String pwd;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected LoginActivityBinding getBinding() {
        return LoginActivityBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).init();//沉浸式状态栏


        binding.loginBtn.setOnClickListener(this);

        binding.sendSmsBtn.setOnClickListener(this);

        binding.serviceAgreementTv.setOnClickListener(this);
        binding.serviceAgreementTv1.setOnClickListener(this);

        Boolean login = FirstLoginSP.getInstance().getLogin();
        Boolean firstShow = FirstLoginSP.getInstance().getFirstShow();
        if (!firstShow) {
            //showDialog();
        }
        binding.accountEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                setBgDrawable(binding.phoneLl, true);
            } else {
                setBgDrawable(binding.phoneLl, false);
            }
        });
        binding.verificationEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                setBgDrawable(binding.smsLl, true);
            } else {
                setBgDrawable(binding.smsLl, false);
            }
        });

        binding.phoneNumEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                setBgDrawable(binding.phoneNumLl, true);
            } else {
                setBgDrawable(binding.phoneNumLl, false);
            }
        });
        binding.pwdNumEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                setBgDrawable(binding.passwordLl, true);
            } else {
                setBgDrawable(binding.passwordLl, false);
            }
        });
        binding.radioGroupWeight.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.account_login_rb) {

                binding.phoneLoginLl.setVisibility(View.VISIBLE);
                binding.smsLoginLl.setVisibility(View.GONE);
                isSmsAccountLogin = true;
            } else if (checkedId == R.id.sms_login_rb) {
                binding.phoneLoginLl.setVisibility(View.GONE);
                binding.smsLoginLl.setVisibility(View.VISIBLE);
                isSmsAccountLogin = false;
            }
        });

        if (AccountPwdSp.getInstance().getAccountCb()) {
            binding.savePhoneNumCb.setChecked(true);
            binding.phoneNumEt.setText(AccountPwdSp.getInstance().getAccount());
        } else {
            binding.savePhoneNumCb.setChecked(false);
        }

        if (AccountPwdSp.getInstance().getPwdCb()) {
            binding.savePwdNumCb.setChecked(true);
            binding.pwdNumEt.setText(AccountPwdSp.getInstance().getPwd());
        } else {
            binding.savePwdNumCb.setChecked(false);
        }


        binding.savePhoneNumCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AccountPwdSp.getInstance().setAccount(binding.phoneNumEt.getText().toString());
            }
            AccountPwdSp.getInstance().setAccountCb(isChecked);
        });
        binding.savePwdNumCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AccountPwdSp.getInstance().setPwd(binding.pwdNumEt.getText().toString());
            }
            AccountPwdSp.getInstance().setPwdCb(isChecked);
        });

    }

    private void setBgDrawable(LinearLayout linearLayout, boolean hasFocus) {
        if (hasFocus) {
            linearLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edittext_bg_focused, null));
        } else {
            linearLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edittext_selector, null));
        }

    }

    private void getPermissions() {
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
//                .permission(Permission.WRITE_EXTERNAL_STORAGE)
//                .permission(Permission.READ_EXTERNAL_STORAGE)
//                .permission(Permission.CAMERA)
//                .permission(Permission.ACCESS_FINE_LOCATION)
//                .permission(Permission.ACCESS_COARSE_LOCATION)
//                // 申请多个权限
              //  .permission(Permission.Group.BLUETOOTH)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            Objects.requireNonNull(RxToast.info(LoginActivity.this, "权限获取成功"));
                            FirstLoginSP.getInstance().setFirstShow(true);
                        } else {
                            Objects.requireNonNull(RxToast.warning(LoginActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(LoginActivity.this, "被永久拒绝授权，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(LoginActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(LoginActivity.this, "获取权限失败"));
                        }
                    }
                });
    }

    @Override
    protected void initDatas() {
        this.mCountDownTimer = new MyCountDownTimer(60 * 1000, 1000, binding.sendSmsBtn);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (v == binding.loginBtn) {
            //false 为短信登录 true 为账号密码登录
            if (!isSmsAccountLogin) {
                if (checkInfo()) {
                    addUserLogin();
                }
            } else {
                if (checkInfoAccount()) {
                    if (binding.savePhoneNumCb.isChecked()) {
                        AccountPwdSp.getInstance().setAccount(binding.phoneNumEt.getText().toString());
                    }
                    if (binding.savePwdNumCb.isChecked()) {
                        AccountPwdSp.getInstance().setPwd(binding.pwdNumEt.getText().toString());
                    }
                    try {
                        getLoginAccount();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else if (v == binding.sendSmsBtn) {
            if (sendMsmInfo()) {
                if (this.mCountDownTimer != null) {
                    this.mCountDownTimer.start();
                    getSendSms();
                }
            }
        } else if (v == binding.serviceAgreementTv) {
            AgreementActivity.start(LoginActivity.this, 0);
        } else if (v == binding.serviceAgreementTv1) {
            AgreementActivity.start(LoginActivity.this, 1);
        }
    }

    /**
     * 校验信息
     *
     * @return
     */
    private boolean checkInfo() {
        account = binding.accountEt.getText().toString().trim();
        verification = binding.verificationEt.getText().toString().trim();
        if (TextUtils.isEmpty(this.account)) {

            Objects.requireNonNull(RxToast.warning(LoginActivity.this, "请输入手机号"));


            return false;
        }
        if (!TextUtils.isEmpty(this.account) && !StrUtil.isMobileNumber(account)) {

            Objects.requireNonNull(RxToast.warning(LoginActivity.this, "请输入正确手机号"));
            return false;
        }

        if (TextUtils.isEmpty(this.verification)) {
            Objects.requireNonNull(RxToast.warning(LoginActivity.this, "请输入验证码"));
            return false;
        }
        if (!binding.agreeCb.isChecked()) {
            Objects.requireNonNull(RxToast.warning(LoginActivity.this, getString(R.string.service_agreementtv)));
            binding.llShake.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_checkbox_shake));
            return false;
        }

        return true;
    }


    private boolean checkInfoAccount() {
        phoneNum = binding.phoneNumEt.getText().toString().trim();
        pwd = binding.pwdNumEt.getText().toString().trim();
        if (TextUtils.isEmpty(this.phoneNum)) {
            Objects.requireNonNull(RxToast.warning(LoginActivity.this, "请输入手机号"));
            return false;
        }
        if (!TextUtils.isEmpty(this.phoneNum) && !StrUtil.isMobileNumber(phoneNum)) {
            Objects.requireNonNull(RxToast.warning(LoginActivity.this, "请输入正确手机号"));
            return false;
        }

        if (TextUtils.isEmpty(this.pwd)) {
            Objects.requireNonNull(RxToast.warning(LoginActivity.this, "请输入密码"));
            return false;
        }
        if (!binding.agreeCb.isChecked()) {
            Objects.requireNonNull(RxToast.warning(LoginActivity.this,  getString(R.string.service_agreementtv)));
            binding.llShake.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_checkbox_shake));
            return false;
        }

        return true;
    }


    private boolean sendMsmInfo() {
        account = binding.accountEt.getText().toString().trim();
        if (TextUtils.isEmpty(this.account)) {
            Objects.requireNonNull(RxToast.warning(LoginActivity.this, "请输入手机号"));
            return false;
        }
        if (!TextUtils.isEmpty(this.account) && !StrUtil.isMobileNumber(account)) {
            Objects.requireNonNull(RxToast.warning(LoginActivity.this, "请输入正确手机号"));
            return false;
        }
        return true;
    }

    private void getSendSms() {
        showLoading("获取短信验证...");
        HttpRequest.getSendSms(LoginActivity.this, binding.accountEt.getText().toString().trim(), new CallBackLis<StatusData>() {
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.Status == 0) {
                    hideLoading();
                    Objects.requireNonNull(RxToast.success(LoginActivity.this, "短信验证码发送成功,注意查收"));
                } else {
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(LoginActivity.this, content.Message));
                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
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

    private void getLogin() {

        HttpRequest.getLogin(LoginActivity.this, binding.accountEt.getText().toString().trim(), binding.verificationEt.getText().toString().trim(), new CallBackLis<LoginData>() {
            @Override
            public void onSuccess(String method, LoginData loginData) {
                if (loginData.Status == 500) {
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(LoginActivity.this,  loginData.Message));
                } else if (loginData.Status == 500) {
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(LoginActivity.this,  loginData.Message));
                } else if (loginData.ErrorCode == 10005) {
                    Objects.requireNonNull(RxToast.error(LoginActivity.this,  loginData.Message));
                }else if (loginData.Status==403){
                    Objects.requireNonNull(RxToast.error(LoginActivity.this,  loginData.Message));
                }
                else {
                    LogUtil.d("lzx---------》", loginData.toString());
                    FirstLoginSP.getInstance().setLogin(true);
                    UserDataSP.getInstance().setUserInfo(loginData);
                    MainActivity.start(LoginActivity.this);
                    finish();
                }

            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull(RxToast.error(LoginActivity.this,  error));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getLoginAccount() throws UnsupportedEncodingException {
        phoneNum = binding.phoneNumEt.getText().toString().trim();
        pwd = binding.pwdNumEt.getText().toString().trim();
        LoginAccountData  loginAccountData = new LoginAccountData();
        loginAccountData.account = phoneNum;
        loginAccountData.password = pwd;

        String jsonString = GsonUtil.toJson(loginAccountData);
        String signString = Base64Util.encodeData(jsonString);

        showLoading("正在登录中...");
        HttpRequest.login(LoginActivity.this, signString, new CallBackLis<LoginData>() {
            @Override
            public void onSuccess(String method, LoginData loginData) {
                hideLoading();
                if (loginData.Status == 500) {
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(LoginActivity.this,  loginData.Message));
                } else if (loginData.Status == 500) {
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(LoginActivity.this,  loginData.Message));
                } else if (loginData.ErrorCode == 10005) {
                    Objects.requireNonNull(RxToast.error(LoginActivity.this,  loginData.Message));
                }  else if (loginData.Status == 100){
                    Objects.requireNonNull(RxToast.error(LoginActivity.this,  loginData.Message));
                }else if (loginData.ErrorCode == 40303){
                    Objects.requireNonNull(RxToast.error(LoginActivity.this,  loginData.Message));
                }
                else {
                    LogUtil.d("lzx---------》", loginData.toString());
                    FirstLoginSP.getInstance().setLogin(true);
                    UserDataSP.getInstance().setUserInfo(loginData);
                    MainActivity.start(LoginActivity.this);
                    finish();
                }

            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }

    private void addUserLogin() {
        showLoading("正在登录中...");
        HttpRequest.addUserLogin(LoginActivity.this, binding.accountEt.getText().toString().trim(), binding.accountEt.getText().toString().trim(), new CallBackLis<AddLoginUserBean>() {
            @Override
            public void onSuccess(String method, AddLoginUserBean loginData) {
                if (loginData.code == 500) {
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(LoginActivity.this,  loginData.msg));
                } else {
                    hideLoading();
                    LogUtil.d("lzx---------》", loginData.toString());
                    getLogin();
                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }

    /**
     * 倒计时器
     */
    private class MyCountDownTimer extends CountDownTimer {

        private TextView mTextView;

        /**
         * 构造方法
         *
         * @param millisInFuture    倒计时的总时间, 单位ms
         * @param countDownInterval 倒计时的间隔时间, 单位ms
         * @param textView          倒计时的view
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView textView) {
            super(millisInFuture, countDownInterval);
            this.mTextView = textView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            @SuppressLint("StringFormatMatches")
            String content = String.format(getString(R.string.sms_time), (millisUntilFinished / 1000));
            this.mTextView.setEnabled(false);
            this.mTextView.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
            this.mTextView.setText(content);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onFinish() {
            this.mTextView.setEnabled(true);
            this.mTextView.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
            this.mTextView.setText("重新发送");
        }
    }


    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        AlertDialog exitDialogLoginOut = new AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView title_tv = view.findViewById(R.id.title_tv);
        title_tv.setVisibility(View.VISIBLE);
        title_tv.setText("权限设置");
        contentTv.setText("应用保存运行状态，需要获取读写手机存储权限,相机权限，蓝牙权限。请允许。");
        cancelTv.setOnClickListener(view12 -> exitDialogLoginOut.dismiss());
        confirmTv.setOnClickListener(v -> {
                    exitDialogLoginOut.dismiss();
                    getPermissions();
                }
        );
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = this.getCurrentFocus();
            if (KeyBoardUtil.isShouldHideInput(v, ev)) {
                KeyBoardUtil.closeKeyboard(v);
            }
            return super.dispatchTouchEvent(ev);
        }
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }
}
