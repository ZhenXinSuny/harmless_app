package com.agridata.cdzhdj.activity.epidemic.xdr;

import static com.agridata.cdzhdj.base.MyApplication.getContext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.SPUtil.TokenConfigSp;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.AppConfigurationBean;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.databinding.ActivityBaseWebviewTitleBinding;
import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.utils.CookieUtils;
import com.agridata.cdzhdj.utils.GlideEngine;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.agridata.cdzhdj.utils.PictureSelectorUtils;
import com.agridata.cdzhdj.utils.ToastUtil;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.bottomPopupDialog.BottomPopupDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.luck.picture.lib.basic.PictureSelectionCameraModel;
import com.luck.picture.lib.basic.PictureSelectionModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.MediaUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-10-12 10:51.
 * @Description :描述
 */
public class XdrEditWebViewActivity extends BaseActivity<ActivityBaseWebviewTitleBinding> {
    private final static String TAG = "lzx------》";
    private Handler mHandler = new Handler();
    private WebSettings webSettings;
    private static final String url = UrlUtils.Xdr_Edit_Url;
    private ImageEngine imageEngine;
    private String mTypeName;
    private String xdrId;


    public static void start(Context context, String menuId) {
        Intent intent = new Intent(context, XdrEditWebViewActivity.class);
        Bundle data = new Bundle();
        data.putSerializable("xdrId", menuId);
        intent.putExtra("data", data);
        context.startActivity(intent);


    }

    @Override
    protected ActivityBaseWebviewTitleBinding getBinding() {
        return ActivityBaseWebviewTitleBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        getArguments();
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).init();//沉浸式状态栏
        binding.webBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.J5), android.graphics.PorterDuff.Mode.SRC_IN);
        imageEngine = GlideEngine.createGlideEngine();
        settingWebView();
        settingWebViewClient();
        syncCookie(url);
        binding.webView.loadUrl(url);

        binding.titlebarLeft.setOnClickListener(v -> finish());
    }

    private void getArguments() {
        Bundle bundle = getIntent().getBundleExtra("data");
        assert bundle != null;
        xdrId = bundle.getString("xdrId");
        binding.titlebarMiddle.setText("养殖户备案编辑");


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
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getContext().getCacheDir().getAbsolutePath();
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
                binding.webBar.setProgress(newProgress);
                if (newProgress == 100) {
                    binding.webBar.setVisibility(View.GONE);
                } else {
                    binding.webBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                LogUtil.d("标题：" + title);
            }
        });
    }

    public void writeData() {
        LoginData userInfo = UserDataSP.getInstance().getUserInfo();
        String key = "vbm_saas_userinfo";
        String vbm_saas_userinfo = GsonUtil.toJson(userInfo.Result);
        LogUtil.d(TAG, "userInfo" + vbm_saas_userinfo);
        String key2 = "vbm_saas_partition";
        String vbm_saas_partition = GsonUtil.toJson(userInfo.Result.partition);
        LogUtil.d(TAG, "vbm_saas_partition" + vbm_saas_partition);
        String key3 = "vbm_saas_harmless";
        RoleBean.DataBean roleInfo = RoleSP.getInstance().getRoleInfo();
        String vbm_saas_harmless = GsonUtil.toJson(roleInfo);
        String key4 = "xdr_mid";


        AppConfigurationBean.DataBean key1 = TokenConfigSp.getInstance().getKey();
        String keyInfo = GsonUtil.toJson(key1);
        String keyAppConfig = "globalConfigLocal";
        binding.webView.evaluateJavascript("window.localStorage.setItem('" + key + "','" + vbm_saas_userinfo + "');", null);
        binding.webView.evaluateJavascript("window.localStorage.setItem('" + keyAppConfig + "','" + keyInfo + "');", null);
        binding.webView.evaluateJavascript("window.localStorage.setItem('" + keyAppConfig + "','" + keyInfo + "');", null);
        binding.webView.evaluateJavascript("window.localStorage.setItem('" + key2 + "','" + vbm_saas_partition + "');", null);
        binding.webView.evaluateJavascript("window.localStorage.setItem('" + key3 + "','" + vbm_saas_harmless + "');", null);
        binding.webView.evaluateJavascript("window.localStorage.setItem('" + key4 + "','" + xdrId + "');", null);
        String key11 = "vbm_saas_token";
        binding.webView.evaluateJavascript("window.localStorage.setItem('" + key11 + "','" + UserDataSP.getInstance().getUserInfo().Result.token + "');", null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("onResume " + url);
        //同步 cookie 到 webview
        syncCookie(url);

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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    private void callVueJSPicPaiZhao(String id) {


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("PicMid", id);
            jsonObject.put("Type", mTypeName);
            binding.webView.post(() -> {
                LogUtil.d(TAG, "调用js的方法");
                binding.webView.loadUrl("javascript:callJsFunctionIdCardImg('" + jsonObject.toString() + "')");
            });
        } catch (Exception e) {

        }

    }

    private class JavaScriptCallbackThisJavaMethod {
        @JavascriptInterface
        public void toPicCreamActivity(String TypeName) {
            //此处应该定义常量对应，同时提供给web页面编写者
            mTypeName = TypeName;
            LogUtil.d(TAG, mTypeName);
            showPicDialog();
        }

        @JavascriptInterface
        public void finishActivity() {
            //此处应该定义常量对应，同时提供给web页面编写者
            finish();
        }
    }

    private void showPicDialog() {
        List<String> bottomDialogContents = new ArrayList<>();
        bottomDialogContents.add("拍照");
        bottomDialogContents.add("相册选取");
        final BottomPopupDialog bottomPopupDialog = new BottomPopupDialog(this, bottomDialogContents);
        bottomPopupDialog.showCancelBtn(true);
        bottomPopupDialog.setTitleText("选择上传方式");
        bottomPopupDialog.setTitleColor(R.color.Red);
        bottomPopupDialog.setTitleSize(18);
        bottomPopupDialog.show();

        bottomPopupDialog.setCancelable(true);
        bottomPopupDialog.setOnItemClickListener((v, position) -> {
            bottomPopupDialog.dismiss();
            if (position == 0) {  //拍照
                getPermissionCream();

            } else if (position == 1) { //相册选取
                OpenPicture();
            }
        });
        bottomPopupDialog.setOnDismissListener(bottomPopupDialog::dismiss);
    }

    private void getPermissionCream() {
        XXPermissions.with(this).permission(Permission.CAMERA).request((permissions, all) -> {
            if (all) {
                TakePicturesAlone();
            }
        });
    }

    //单独拍照
    private void TakePicturesAlone() {
        // Take pictures alone
        PictureSelectionCameraModel cameraModel = PictureSelector.create(XdrEditWebViewActivity.this).openCamera(SelectMimeType.ofImage())
                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())//裁剪引擎
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())//压缩引擎
                .isOriginalControl(true);
        forOnlyCameraResult(cameraModel);
    }

    // 相册选择
    private void OpenPicture() {
        // 进入相册
        PictureSelectionModel selectionModel = PictureSelector.create(XdrEditWebViewActivity.this).openGallery(SelectMimeType.ofImage()).setSelectorUIStyle(PictureSelectorUtils.setStyle()).setImageEngine(imageEngine)
                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine()).setSandboxFileEngine(PictureSelectorUtils.getMeSandboxFileEngine()).setSelectLimitTipsListener(PictureSelectorUtils.getMeOnSelectLimitTips()).setSelectionMode(SelectModeConfig.SINGLE).setQuerySortOrder(MediaStore.MediaColumns.DATE_MODIFIED).isDisplayTimeAxis(true).isOriginalControl(true).isDisplayCamera(false).isPreviewFullScreenMode(true).isPreviewZoomEffect(true).isPreviewImage(true).isMaxSelectEnabledMask(true).isDirectReturnSingle(true).setMaxSelectNum(1);
        forSelectResult(selectionModel, "SINGLE");
    }


    private void forOnlyCameraResult(PictureSelectionCameraModel model) {
        model.forResultActivity(new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(ArrayList<LocalMedia> result) {
                analyticalSelectResults(result, "SINGLE");
            }

            @Override
            public void onCancel() {

            }
        });
    }


    //选择回调
    private void forSelectResult(PictureSelectionModel model, String typeName) {

        switch (typeName) {
            case "SINGLE"://银行卡号照片 + 身份证照片
                setModel(model, "SINGLE");
                break;
        }

    }

    private void setModel(PictureSelectionModel model, String typeName) {
        model.forResult(new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(ArrayList<LocalMedia> result) {
                analyticalSelectResults(result, typeName);
            }

            @Override
            public void onCancel() {
            }
        });
    }


    private void analyticalSelectResults(ArrayList<LocalMedia> result, String typeName) {
        for (LocalMedia media : result) {
            if (media.getWidth() == 0 || media.getHeight() == 0) {
                if (PictureMimeType.isHasImage(media.getMimeType())) {
                    MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(getContext(), media.getPath());
                    media.setWidth(imageExtraInfo.getWidth());
                    media.setHeight(imageExtraInfo.getHeight());
                }
            }
            LogUtil.i("lzx---》", "文件名: " + media.getFileName());
            LogUtil.i(TAG, "是否压缩:" + media.isCompressed());
            LogUtil.i(TAG, "压缩:" + media.getCompressPath());
            LogUtil.i(TAG, "初始路径:" + media.getPath());
            LogUtil.i(TAG, "绝对路径:" + media.getRealPath());
            LogUtil.i(TAG, "是否裁剪:" + media.isCut());
            LogUtil.i(TAG, "裁剪路径:" + media.getCutPath());
            LogUtil.i(TAG, "是否开启原图:" + media.isOriginal());
            LogUtil.i(TAG, "原图路径:" + media.getOriginalPath());
            LogUtil.i(TAG, "沙盒路径:" + media.getSandboxPath());
            LogUtil.i(TAG, "水印路径:" + media.getWatermarkPath());
            LogUtil.i(TAG, "视频缩略图:" + media.getVideoThumbnailPath());
            LogUtil.i(TAG, "原始宽高: " + media.getWidth() + "x" + media.getHeight());
            LogUtil.i(TAG, "裁剪宽高: " + media.getCropImageWidth() + "x" + media.getCropImageHeight());

            switch (typeName) {
                case "SINGLE"://银行卡号照片 + 身份证照片

                    upLoadImg(media.getCompressPath());

                    break;

            }
        }
    }


    /**
     * filePath 上传图片
     *
     * @param filePath
     */
    private void upLoadImg(String filePath) {
        HttpRequest.upLoadImg(XdrEditWebViewActivity.this, filePath, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d(TAG, "上传图片" + content.toString());
                if (content.Status == 0) {
                    ToastUtil.showToast(XdrEditWebViewActivity.this, "上传成功~");
                    callVueJSPicPaiZhao(content.Result);
                }

            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }


}
