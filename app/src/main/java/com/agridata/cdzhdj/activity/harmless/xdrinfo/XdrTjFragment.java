package com.agridata.cdzhdj.activity.harmless.xdrinfo;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.harmless.CollectFillInActivity;
import com.agridata.cdzhdj.activity.harmless.JianDuYuanSignActivity;
import com.agridata.cdzhdj.activity.harmless.edit.CollectEditFillInActivity;
import com.agridata.cdzhdj.base.BaseFragment;
import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.databinding.FragmentXdrTjBinding;
import com.agridata.cdzhdj.utils.CookieUtils;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.network.utils.LogUtil;

public class XdrTjFragment extends BaseFragment<FragmentXdrTjBinding> {

    private final static String TAG = "XdrTjFragment------》";
    private WebSettings webSettings;
    private static  final  String url= UrlUtils.XDRST;
    private String Mid;
    public static XdrTjFragment newInstance(String Mid) {
        XdrTjFragment fragment = new XdrTjFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mid", Mid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView() {
        this.Mid = getArguments().getString("mid");
        settingWebView();
        settingWebViewClient();

    }

    @Override
    protected void initDatas() {

    }
    /**
     * 对 webview 进行必要的配置
     */
    private void settingWebView() {
        webSettings = binding.webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        webSettings.setJavaScriptEnabled(true);
//        binding.webView.addJavascriptInterface(this,"nativeMethod");

        binding.webView.addJavascriptInterface(new JavaScriptCallbackThisJavaMethod(), "android");
        webSettings.setDomStorageEnabled(true);  // 开启 DOM storage 功能
        webSettings.setAppCacheMaxSize(1024*1024*8);
        String appCachePath = MyApplication.getContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);    // 可以读取文件缓存
         binding.webView.clearCache(true);
webSettings.setAppCacheEnabled(false);    //开启H5(APPCache)缓存功能
        webSettings.setDatabaseEnabled(true);

        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没有网络时加载缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        binding.webView.setVerticalScrollBarEnabled(false); //垂直不显示
        //Android 5.0及以上版本使用WebView不能存储第三方Cookies解决方案</span>
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(binding.webView, true);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    /**
     * 设置 WebViewClient 和 WebChromeClient
     */
    private void settingWebViewClient() {
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtil.d("onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                callVueJSUserInfo();
                writeData();
                LogUtil.d("onPageFinished");
            }

            // 链接跳转都会走这个方法</span>
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.d("url: ", url);
                view.loadUrl(url);// 强制在当前 WebView 中加载 url
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }
        });


        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                binding.webBar.setProgress(newProgress);
//                if (newProgress == 100) {
//                    binding.webBar.setVisibility(View.GONE);
//                } else {
//                    binding.webBar.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                LogUtil.d("标题：" + title);
            }
        });
    }

    public void callVueJSUserInfo() {
        binding.webView.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.d(TAG,"调用js的方法");

                binding.webView.loadUrl("javascript:callVueJSUserInfo('"+Mid+"')");

            }
        });
    }

    public void writeData(){

        LoginData userInfo = UserDataSP.getInstance().getUserInfo();

        String key = "vbm_saas_userinfo";
        String vbm_saas_userinfo = GsonUtil.toJson(userInfo.Result);

        LogUtil.d(TAG,"userInfo" + vbm_saas_userinfo);
        String key2 = "vbm_saas_partition";
        String vbm_saas_partition = GsonUtil.toJson(userInfo.Result.partition);
        LogUtil.d(TAG,"vbm_saas_partition" + vbm_saas_partition);



        String key3 = "vbm_saas_harmless";
        RoleBean.DataBean roleInfo = RoleSP.getInstance().getRoleInfo();
        String vbm_saas_harmless = GsonUtil.toJson(roleInfo);
        LogUtil.d(TAG,"vbm_saas_harmless" + vbm_saas_harmless);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            binding.webView.evaluateJavascript("window.localStorage.setItem('"+ key +"','"+ vbm_saas_userinfo +"');", null);
            binding.webView.evaluateJavascript("window.localStorage.setItem('"+ key2 +"','"+ vbm_saas_partition +"');", null);
                binding.webView.evaluateJavascript("window.localStorage.setItem('"+ key3 +"','"+ vbm_saas_harmless +"');", null);
         String key4 = "vbm_saas_token";
binding.webView.evaluateJavascript("window.localStorage.setItem('" + key4 + "','" + UserDataSP.getInstance().getUserInfo().Result.token + "');",null);
  
        } else {
            LogUtil.d("lzx----->","保存");
            binding.webView.loadUrl("javascript:localStorage.setItem('"+ key +"','"+ vbm_saas_userinfo +"');");
            binding.webView.loadUrl("javascript:localStorage.setItem('"+ key2 +"','"+ vbm_saas_partition +"');");
            binding.webView.loadUrl("javascript:localStorage.setItem('"+ key3 +"','"+ vbm_saas_harmless +"');");
            binding.webView.reload();
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("onResume " + url);
        //同步 cookie 到 webview
        syncCookie(url);
        binding.webView.loadUrl(url);

    }

    /**
     * 同步 webview 的Cookie
     */
    private void syncCookie(String url) {
        boolean b = CookieUtils.syncCookie(url);
        LogUtil.d("设置 cookie 结果： " + b);
    }
    /**
     * 对安卓返回键的处理。如果webview可以返回，则返回上一页。如果webview不能返回了，则退出当前webview
     */




    public void callVueJSPic(String id) {
        binding.webView.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.d(TAG,"调用js的方法");
                binding.webView.loadUrl("javascript:callJsFunction('"+id+"')");

            }
        });
    }



    private class JavaScriptCallbackThisJavaMethod {
        @JavascriptInterface
        public void toActivity(String activityName) {
            //此处应该定义常量对应，同时提供给web页面编写者
            CollectFillInActivity.start(requireActivity(),activityName);

        }

        @JavascriptInterface
        public void toBianJiActivity(String activityName) {
            //此处应该定义常量对应，同时提供给web页面编写者
            CollectEditFillInActivity.start(requireActivity(),activityName);

        }

        @JavascriptInterface
        public void toSignActivity(String activityName) {
            //此处应该定义常量对应，同时提供给web页面编写者

            JianDuYuanSignActivity.start(requireActivity(),activityName,1004);
        }

        @JavascriptInterface
        public void toPicCreamActivity(int activityName) {
            //此处应该定义常量对应，同时提供给web页面编写者

        }

        @JavascriptInterface
        public void toPicXiangCengActivity(int activityName) {
            //此处应该定义常量对应，同时提供给web页面编写者


        }
        @JavascriptInterface
        public void killActivity() {
            //此处应该定义常量对应，同时提供给web页面编写者

        }
    }




    public void callVueJS() {
        binding.webView.post(new Runnable() {
            @Override
            public void run() {

                binding.webView.loadUrl("javascript:callJsFunction()");
            }
        });
    }


















}
