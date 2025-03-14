package com.agridata.cdzhdj.activity.mine.setting.securityverification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.mine.setting.changepwd.ChangePwdActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.databinding.ActivittySecurityVerificationBinding;
import com.agridata.cdzhdj.utils.KeyBoardUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.SplitEditText;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.agridata.util.HideDataUtils;
import com.lzx.utils.RxToast;

import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-08-30 09:35.
 * @Description :描述  修改密码安全验证
 */
public class SecurityVerificationActivity extends BaseActivity<ActivittySecurityVerificationBinding> {
    private MyCountDownTimer mCountDownTimer;
    private CustomLoadingDialog mLoadingDialog;
    private String mobilePhone;
    public static void start(Context context) {
        Intent intent = new Intent(context, SecurityVerificationActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivittySecurityVerificationBinding getBinding() {
        return ActivittySecurityVerificationBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        initLoadingView();
        binding.titlebarLeft.setOnClickListener(view ->finish());
        mobilePhone = UserDataSP.getInstance().getUserInfo().Result.mobile;
        KeyBoardUtil.openKeyboardForce(this,binding.codeEt);
        binding.phoneNumTv.setText("+86" +" " + HideDataUtils.mobilePhone(mobilePhone));
        this.mCountDownTimer = new MyCountDownTimer(60 * 1000, 1000, binding.sendSmsTv);
        getSendSms();
        binding.codeEt.setOnTextInputListener(new SplitEditText.OnTextInputListener() {
            @Override
            public void onTextInputChanged(String text, int length) {
                //TODO 文本输入改变
            }

            @Override
            public void onTextInputCompleted(String text) {
                //TODO 文本输入完成
                LogUtil.d("lzx-------》",text);
                verifySms(text);
            }
        });

        binding.sendSmsTv.setOnClickListener(v -> {
            if (mCountDownTimer != null) {
                getSendSms();
            }
        });
    }

    @Override
    protected void initDatas() {

    }
    private void getSendSms() {
        showLoading("获取短信验证...");
        mCountDownTimer.start();
        HttpRequest.getSendSms(SecurityVerificationActivity.this, mobilePhone, new CallBackLis<StatusData>() {
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.Status == 0) {
                    hideLoading();
                    Objects.requireNonNull(RxToast.success(SecurityVerificationActivity.this, "短信验证码发送成功,注意查收"));
                } else {
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(SecurityVerificationActivity.this, content.Message));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(SecurityVerificationActivity.this, error));
            }
        });
    }

    private void verifySms(String code) {
        HttpRequest.verifySms(SecurityVerificationActivity.this, mobilePhone,code, new CallBackLis<StatusData>() {
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.Status == 0) {
                    ChangePwdActivity.start(SecurityVerificationActivity.this);
                } else {
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(SecurityVerificationActivity.this, content.Message));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(SecurityVerificationActivity.this, error));
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
            this.mTextView.setBackground(null);
            this.mTextView.setTextColor(ContextCompat.getColor(SecurityVerificationActivity.this, R.color.T2));
            this.mTextView.setText(content);
        }

        @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
        @Override
        public void onFinish() {
            this.mTextView.setEnabled(true);
            this.mTextView.setBackground(ContextCompat.getDrawable(SecurityVerificationActivity.this,R.drawable.shape_label_orange));
            this.mTextView.setTextColor(ContextCompat.getColor(SecurityVerificationActivity.this, R.color.T2));
            this.mTextView.setText("重新发送");
        }
    }


}
