package com.agridata.cdzhdj.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.databinding.ActivityAgreementBinding;
import com.agridata.cdzhdj.utils.AppConstants;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;

/**
 * @auther 56454
 * @date 2022/8/4
 * @time 15:06.
 */
public class AgreementActivity extends BaseActivity<ActivityAgreementBinding> {

    private TextView mTitleTv;
    private int mType;
    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context, int type) {
        Intent intent = new Intent(context, AgreementActivity.class);
        Bundle data = new Bundle();
        data.putInt("type", type);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @Override
    protected ActivityAgreementBinding getBinding() {
        return ActivityAgreementBinding.inflate(getLayoutInflater());
    }

    /**
     * 获取上一级所传过来的值  去判断加载哪一个Url
     */
    private void getArguments() {
        Bundle bundle = getIntent().getBundleExtra("data");
        mType = bundle.getInt("type");

    }
    @Override
    protected void initView() {
        getArguments();
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setImageResource(R.drawable.title_back);

        binding.titlebarLeft.setOnClickListener(v -> finish());

        WebSettings settings = binding.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        
        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);
        binding.webView.setHorizontalScrollBarEnabled(false);//水平不显示
        binding.webView.setVerticalScrollBarEnabled(true); //垂直不显示

    }

    @Override
    protected void initDatas() {
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  // 忽略SSL验证AS
            }
        });

        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                binding.titlebarMiddle.setText(title);
                LogUtil.d("lzx----》", "title" + title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        if (mType == 0) {
            binding.titlebarMiddle.setText("用户协议");
            binding.webView.loadUrl(AppConstants.AGREEMENT_URL);
        } else {
            //隐私协议
            binding.titlebarMiddle.setText("隐私协议");
            binding.webView.loadUrl(AppConstants.PRIVACY_URL);
        }

    }
}
