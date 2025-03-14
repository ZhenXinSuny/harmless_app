package com.agridata.cdzhdj.base;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.viewbinding.ViewBinding;

import com.agridata.cdzhdj.databinding.ActivityBaseWebviewBinding;
import com.agridata.cdzhdj.databinding.ActivityBaseWebviewTitleBinding;
import com.agridata.cdzhdj.databinding.ActivityMainBinding;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-12-31 17:39.
 * @Description :描述
 */
public class BaseWebViewActivity  extends BaseActivity<ActivityBaseWebviewBinding>{


    @Override
    protected ActivityBaseWebviewBinding getBinding() {
        return ActivityBaseWebviewBinding.inflate(getLayoutInflater());
    }
    @Override
    protected void initView() {
        // 启用 JavaScript 支持
        binding.webView.getSettings().setJavaScriptEnabled(true);
        // 启用缓存
        binding.webView.getSettings().setAppCacheEnabled(true);
        // 或者根据需求使用其他缓存模式
        binding.webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 启用 LocalStorage
        binding.webView.getSettings().setDomStorageEnabled(true);

        // 设置 WebViewClient 处理 URL 跳转
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 可以在页面加载完成后写入 localStorage
                injectLocalStorageData();
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    private void injectLocalStorageData() {
        // JavaScript 注入代码
        String javascript = "localStorage.setItem('key', 'value');";
        // 写入 localStorage
        binding.webView.evaluateJavascript(javascript, null);
    }
}
