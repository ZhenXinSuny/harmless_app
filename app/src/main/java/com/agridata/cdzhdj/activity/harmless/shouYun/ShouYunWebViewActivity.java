package com.agridata.cdzhdj.activity.harmless.shouYun;

import static com.agridata.cdzhdj.base.MyApplication.getContext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.agridata.cdzhdj.SPUtil.TokenConfigSp;
import com.agridata.cdzhdj.data.AppConfigurationBean;
import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.harmless.CollectFillInActivity;
import com.agridata.cdzhdj.activity.harmless.JianDuYuanSignActivity;
import com.agridata.cdzhdj.activity.harmless.edit.CollectEditFillInActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.IdCardBankBean;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.databinding.ActivityWebviewBinding;
import com.agridata.cdzhdj.utils.CookieUtils;
import com.agridata.cdzhdj.utils.FileUtil;
import com.agridata.cdzhdj.utils.GlideEngine;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.agridata.cdzhdj.utils.PictureSelectorUtils;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.agridata.ocr_ui.ocr.camera.CameraActivity;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
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
import com.luck.picture.lib.utils.PictureFileUtils;
import com.lzx.utils.RxToast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @auther 56454
 * @date 2022/6/22
 * @time 14:04.
 */
public class ShouYunWebViewActivity extends BaseActivity<ActivityWebviewBinding> {

    private  String idCardBankPicMid;

    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_CAMERA = 102;
    private static final int REQUEST_CODE_BANKCARD = 111;

    private final static String TAG = "lzx------》";
    private Handler mHandler = new Handler();
    private WebSettings webSettings;
    //SHOUYUN_URL_NEW
    private static  final  String url= UrlUtils.SHOUYUN_URL_NEW;
    private ImageEngine imageEngine;
    private  int PicBtnType  = 0;
    private CustomLoadingDialog mLoadingDialog;
    private   String path;
    public static void start(Context context) {
        Intent intent = new Intent(context, ShouYunWebViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ActivityWebviewBinding getBinding() {
        return ActivityWebviewBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        imageEngine = GlideEngine.createGlideEngine();
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).init();//沉浸式状态栏
        binding.webBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.J5), android.graphics.PorterDuff.Mode.SRC_IN);
        settingWebView();
        settingWebViewClient();
        syncCookie(url);
        binding.webView.loadUrl(url);
    }

    @Override
    protected void initDatas() {
        initToken();
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

        AppConfigurationBean.DataBean key1 = TokenConfigSp.getInstance().getKey();
        String keyInfo = GsonUtil.toJson(key1);
        String keyAppConfig = "globalConfigLocal";
        String userToken = "vbm_saas_token";
        Map<String, String> data = new HashMap<>();
        data.put(key, vbm_saas_userinfo);
        data.put(key2, vbm_saas_partition);
        data.put(key3, vbm_saas_harmless);
        data.put(keyAppConfig, keyInfo);
        data.put(userToken, UserDataSP.getInstance().getUserInfo().Result.token);
        setLocalStorage(data);
    }

    // 转义 JSON 字符串中的特殊字符，防止 JavaScript 语法错误
    private String escapeJson(String value) {
        return value.replace("'", "\\'").replace("\"", "\\\"");
    }
    public void setLocalStorage(Map<String, String> data) {
        StringBuilder script = new StringBuilder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = escapeJson(entry.getKey());
            String value = escapeJson(entry.getValue());
            script.append("window.localStorage.setItem('").append(key).append("', '").append(value).append("');");
        }
        binding.webView.evaluateJavascript(script.toString(), null);
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       switch (requestCode){
           case 1002:
               callVueJS();
               break;
           case 1004:
                if (!TextUtils.isEmpty(data.getStringExtra("picMid"))){
                    String picMid = data.getStringExtra("picMid");
                    assert picMid != null;
                    if (!"1".equals(picMid)){
                        callVueJSPic(data.getStringExtra("picMid"));
                        LogUtil.d(TAG,"回调的签名照片"+ data.getStringExtra("picMid"));
                    }
                }
                break;
           case REQUEST_CODE_CAMERA:
               if (data != null) {
                   String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                   String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                   if (!TextUtils.isEmpty(contentType)) {
                       if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                          // recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                           LogUtil.i(TAG,"身份证File文件路径" + filePath);
                           showLoading("身份证照片上传中...");
                           upLoadImg(filePath);
                       }
                   }
               }
               break;
           case REQUEST_CODE_BANKCARD:
               if (data != null) {
                   String filePath = FileUtil.getSaveFileBank(getApplicationContext()).getAbsolutePath();
                   showLoading("银行卡照片上传中...");
                   upLoadImg(filePath);
               }

               break;
       }
    }



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
            CollectFillInActivity.start(ShouYunWebViewActivity.this,activityName);

        }

        @JavascriptInterface
        public void toBianJiActivity(String activityName) {
            //此处应该定义常量对应，同时提供给web页面编写者
            CollectEditFillInActivity.start(ShouYunWebViewActivity.this,activityName);

        }

        @JavascriptInterface
        public void toSignActivity(String activityName) {
            //此处应该定义常量对应，同时提供给web页面编写者
            LogUtil.d(TAG,activityName);
            JianDuYuanSignActivity.start(ShouYunWebViewActivity.this,activityName,1004);
        }

        @JavascriptInterface
        public void toPicCreamActivity(int activityName) {
            //此处应该定义常量对应，同时提供给web页面编写者
            LogUtil.d(TAG, activityName +"");
            PicBtnType=  activityName;
            showPicDialog();
        }

        @JavascriptInterface
        public void toPicXiangCengActivity(int activityName) {
            //此处应该定义常量对应，同时提供给web页面编写者
            LogUtil.d(TAG, activityName +"");
            PicBtnType=  activityName;
            showPicDialog();

        }
        @JavascriptInterface
        public void killActivity() {
            //此处应该定义常量对应，同时提供给web页面编写者
            finish();
        }
    }




    public void callVueJS() {
        binding.webView.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.d(TAG, "调用js的方法");
                binding.webView.loadUrl("javascript:callJsFunction()");
            }
        });
    }



    public void callVueJSPicPaiZhao(String idCardBankInfo) {
        binding.webView.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.d(TAG,"调用js的方法");
                binding.webView.loadUrl("javascript:callJsFunction('"+idCardBankInfo+"')");

            }
        });
    }

    public void callVueJSPicXiangC(String idCardBankInfo) {
        binding.webView.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.d(TAG,"调用js的方法");
                binding.webView.loadUrl("javascript:callJsFunctionBankPic('"+idCardBankInfo+"')");

            }
        });
    }



    private void showPicDialog() {
//        List<String> bottomDialogContents = new ArrayList<>();
//        bottomDialogContents.add("拍照");
//        bottomDialogContents.add("相册选取");
//        final BottomPopupDialog bottomPopupDialog = new BottomPopupDialog(this, bottomDialogContents);
//        bottomPopupDialog.showCancelBtn(true);
//        bottomPopupDialog.setTitleText("选择上传方式");
//        bottomPopupDialog.setTitleColor(R.color.Red);
//        bottomPopupDialog.setTitleSize(18);
//        bottomPopupDialog.show();
//
//        bottomPopupDialog.setCancelable(true);
//        bottomPopupDialog.setOnItemClickListener((v, position) -> {
//            bottomPopupDialog.dismiss();
//            if (position == 0) {  //拍照
//                getPermissionCream();
//            } else if (position == 1) { //相册选取
//                OpenPicture();
//            }
//        });
//        bottomPopupDialog.setOnDismissListener(DialogInterface::dismiss);
        if (PicBtnType==1){//身份证
            Intent intent = new Intent(ShouYunWebViewActivity.this, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    FileUtil.getSaveFile(getApplication()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        }else if (PicBtnType==2){//银行卡照片

            Intent intent = new Intent(ShouYunWebViewActivity.this, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    FileUtil.getSaveFileBank(getApplication()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                    CameraActivity.CONTENT_TYPE_BANK_CARD);
            startActivityForResult(intent, REQUEST_CODE_BANKCARD);
        }

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
        PictureSelectionCameraModel cameraModel = PictureSelector.create(ShouYunWebViewActivity.this)
                .openCamera(SelectMimeType.ofImage())
                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())//裁剪引擎
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())//压缩引擎
                .isOriginalControl(true);
        forOnlyCameraResult(cameraModel);
    }

    // 相册选择
    private void OpenPicture() {
        // 进入相册
        PictureSelectionModel selectionModel = PictureSelector.create(ShouYunWebViewActivity.this)
                .openGallery(SelectMimeType.ofImage())
                .setSelectorUIStyle(PictureSelectorUtils.setStyle())
                .setImageEngine(imageEngine)
                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())
                .setSandboxFileEngine(PictureSelectorUtils.getMeSandboxFileEngine())
                .setSelectLimitTipsListener(PictureSelectorUtils.getMeOnSelectLimitTips())
                .setSelectionMode(SelectModeConfig.SINGLE)
                .setQuerySortOrder(MediaStore.MediaColumns.DATE_MODIFIED)
                .isDisplayTimeAxis(true)
                .isOriginalControl(true)
                .isDisplayCamera(false)
                .isPreviewFullScreenMode(true)
                .isPreviewZoomEffect(true)
                .isPreviewImage(true)
                .isMaxSelectEnabledMask(true)
                .isDirectReturnSingle(true)
                .setMaxSelectNum(1);
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
            LogUtil.i(TAG, "文件大小: " + PictureFileUtils.formatAccurateUnitFileSize(media.getSize()));

            switch (typeName) {
                case "SINGLE"://银行卡号照片 + 身份证照片
                    if (PicBtnType == 1) {
                        if (!TextUtils.isEmpty(media.getCompressPath())){
                            upLoadImg(media.getCompressPath());
                        }else {
                            upLoadImg(media.getRealPath());
                        }

                    } else {
                        if (!TextUtils.isEmpty(media.getCompressPath())){
                            upLoadImg(media.getCompressPath());
                        }else {
                            upLoadImg(media.getRealPath());
                        }
                    }
                    break;
                default:
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
        HttpRequest.upLoadImg(ShouYunWebViewActivity.this, filePath, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d(TAG, "上传图片" + content.toString());
                if (PicBtnType == 1) {
                    LogUtil.d(TAG, "身份证号Mid" +  content.Result);
                    idCardBankPicMid = content.Result;
                    Objects.requireNonNull(RxToast.info(ShouYunWebViewActivity.this, "身份证照片上传成功"));
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        showLoading("身份证识别中...");
                        recIDCard(filePath);
                    },200); // 延时1秒
                } else if (PicBtnType == 2) {
                    idCardBankPicMid = content.Result;
                    Objects.requireNonNull(RxToast.info(ShouYunWebViewActivity.this, "银行卡照片上传成功"));
                    //callVueJSPicXiangC(content.Result);
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        showLoading("银行卡识别中...");
                        recBankCard(filePath);
                    },200); // 延时1秒
                }
            }
            @Override
            public void onFailure(String method, String error) {
            }
        });
    }

    private void recIDCard( String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(IDCardParams.ID_CARD_SIDE_FRONT);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);
        param.setDetectRisk(true);
        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    hideLoading();
                    showCardInfoDialog(result);
                    deleteLocalPhoto(filePath);
                    LogUtil.i(TAG,result.toString());
                }
            }
            @Override
            public void onError(OCRError error) {
                LogUtil.i(TAG,error.getMessage());
            }
        });
    }


    private void  recBankCard(String filePath){
        BankCardParams param = new BankCardParams();
        param.setImageFile(new File(filePath));
        OCR.getInstance(this).recognizeBankCard(param, new OnResultListener<BankCardResult>() {
            @Override
            public void onResult(BankCardResult result) {
                if (result != null) {
                    hideLoading();
                    showBankInfoDialog(result);
                    deleteLocalPhoto(filePath);
                    LogUtil.i(TAG,result.toString());
                }
            }

            @Override
            public void onError(OCRError error) {
                LogUtil.i(TAG,error.getMessage());
            }
        });
    }
    public void deleteLocalPhoto(String photoPath) {
        File photoFile = new File(photoPath);
        if (photoFile.exists()) {
            if (photoFile.delete()) {
                LogUtil.i(TAG,"删除成功");
            } else {
                // 文件删除失败
                LogUtil.i(TAG,"删除失败");
            }
        } else {
            LogUtil.i(TAG,"文件不存在");
        }
    }
    private void showCardInfoDialog(IDCardResult result) {
        View view = getLayoutInflater().inflate(R.layout.dialog_ocr_info, null);
        AlertDialog exitDialogLoginOut = new AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.setCancelable(false);
        Objects.requireNonNull(exitDialogLoginOut.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView content1Tv = view.findViewById(R.id.content1_tv);
        TextView titleTv = view.findViewById(R.id.title_tv);
        titleTv.setVisibility(View.VISIBLE);
        titleTv.setText("身份证信息");
        contentTv.setText("姓名：" + result.getName());
        content1Tv.setText("身份证号：" + result.getIdNumber());
        confirmTv.setOnClickListener(v -> {
            exitDialogLoginOut.dismiss();
            IdCardBankBean idCardBankBean = new IdCardBankBean();
            idCardBankBean.picMid = idCardBankPicMid;
            idCardBankBean.name = result.getName().toString();
            idCardBankBean.idCardNum = result.getIdNumber().toString();
            LogUtil.i(TAG,GsonUtil.toJson(idCardBankBean));
            callVueJSPicPaiZhao(GsonUtil.toJson(idCardBankBean));
        }
        );
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }



    private void showBankInfoDialog(BankCardResult result) {
        View view = getLayoutInflater().inflate(R.layout.dialog_ocr_info, null);
        AlertDialog exitDialogLoginOut = new AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.setCancelable(false);
        Objects.requireNonNull(exitDialogLoginOut.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView content1Tv = view.findViewById(R.id.content1_tv);
        TextView titleTv = view.findViewById(R.id.title_tv);
        titleTv.setVisibility(View.VISIBLE);
        titleTv.setText("银行卡信息");
        contentTv.setText("银行名称：" + result.getBankName());
        content1Tv.setText("银行卡名称：" + result.getBankCardNumber());
        confirmTv.setOnClickListener(v -> {
                    exitDialogLoginOut.dismiss();
                    IdCardBankBean idCardBankBean = new IdCardBankBean();
                    idCardBankBean.picMid = idCardBankPicMid;
                    idCardBankBean.bankName = result.getBankName().toString();
                    idCardBankBean.bankNumber = result.getBankCardNumber().toString();
                    LogUtil.i(TAG,GsonUtil.toJson(idCardBankBean));
                  callVueJSPicXiangC(GsonUtil.toJson(idCardBankBean));
                }
        );
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }


    private void initToken() {
        OCR.getInstance(getApplicationContext()).initAccessTokenWithAkSk(new OnResultListener<>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                Log.d("lzx----》", "token" + token.toString());
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
            }
        }, getApplicationContext(), "LmuS31fNhIwQkOktjqVesrW0", "qIZY7tAqwQQSPyZODeEv1TFPeGQQIbIN");

    }
    public void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(this);
        this.mLoadingDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 显示加载框
     */

    private void showLoading(String tips) {
        if (this.mLoadingDialog != null && !this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.show();
            this.mLoadingDialog.setTitleVisibility(0);
            this.mLoadingDialog.setTitle(tips);
        }
    }

    /**
     * 隐藏 加载框
     */

    private void hideLoading() {
        if (this.mLoadingDialog != null && this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.hide();
        }
    }

}
