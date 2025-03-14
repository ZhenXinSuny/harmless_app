package com.agridata.cdzhdj.activity.welcome;

import android.app.AlertDialog;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.FirstLoginSP;
import com.agridata.cdzhdj.SPUtil.FirstSpUtils;
import com.agridata.cdzhdj.SPUtil.TokenConfigSp;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.mine.AgreementActivity;
import com.agridata.cdzhdj.activity.LoginActivity;
import com.agridata.cdzhdj.activity.MainActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.AppConfigurationBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.databinding.ActivityWelBinding;
import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lzx.utils.RxToast;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.Objects;

/**
 * @auther 56454
 * @date 2022/8/5
 * @time 9:36.
 */
public class WelComeActivity  extends BaseActivity<ActivityWelBinding> {


    @Override
    protected ActivityWelBinding getBinding() {
        return ActivityWelBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        //沉浸式状态栏
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.J5).init();

        boolean firstShow = FirstSpUtils.getInstance().getFirstShow();
        //为true不显示
        if (!firstShow) {
            showFirstDialog();
        }else {
            Boolean login = FirstLoginSP.getInstance().getLogin();
            LoginData userInfo = UserDataSP.getInstance().getUserInfo();
            if (login && userInfo!=null) {
               handler.postDelayed(this::getHome,2000);
            }else {
                handler.postDelayed(() -> {
                    LoginActivity.start(WelComeActivity.this);
                    finish();
                },2000);


            }
        }
    }

    @Override
    protected void initDatas() {

    }


    /**
     * 协议框
     */
    private void showFirstDialog() {
        String str = getResources().getString(R.string.show_privacy_message);
        View view = getLayoutInflater().inflate(R.layout.dialog_first_service, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = (TextView) view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(str);
        final int start = str.indexOf("《");//第一个出现的位置
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                AgreementActivity.start(WelComeActivity.this, 1);
            }
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.J5));
                ds.setUnderlineText(false);
            }
        }, start, start + 6, 0);

        int end = str.lastIndexOf("《");
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                AgreementActivity.start(WelComeActivity.this, 0);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.J5));
                ds.setUnderlineText(false);
            }
        }, end, end + 6, 0);

        contentTv.setMovementMethod(LinkMovementMethod.getInstance());
        contentTv.setText(ssb, TextView.BufferType.SPANNABLE);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
                System.exit(0);//退出
            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstSpUtils.getInstance().setFirstShow(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UMConfigure.setLogEnabled(true);
                        UMConfigure.init(
                                getApplicationContext(),
                                "64ab9974bd4b621232cdf976",		//在UMeng+申请的KEY值
                                "Umeng",										//自己定义的渠道名字
                                UMConfigure.DEVICE_TYPE_PHONE,
                                null
                        );
                        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

                        Boolean login = FirstLoginSP.getInstance().getLogin();
                        if (login){
                            getHome();
                        }else {
                            LoginActivity.start(WelComeActivity.this);
                            finish();
                        }

                    }
                },2000);
                exitDialog.dismiss();
            }
        });
        exitDialog.show();
        Window alertWindow = exitDialog.getWindow();
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lParams = alertWindow.getAttributes();
        // lParams.height = (int) (display.getHeight() * 0.8);
        lParams.width = (int) (display.getWidth() * 0.9);
        alertWindow.setAttributes(lParams);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };
    public void getHome(){
        MainActivity.start(WelComeActivity.this);
        finish();
    }
}
