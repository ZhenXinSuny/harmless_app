package com.agridata.cdzhdj.activity.quarantine;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.cdzhdj.SPUtil.TokenConfigSp;
import com.agridata.cdzhdj.data.AppConfigurationBean;
import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.BleSp;
import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.epidemic.eartag.lowble.ConnBlueToothActivity;
import com.agridata.cdzhdj.activity.adapter.BleListAdapter;
import com.agridata.cdzhdj.activity.adapter.ble.ScanBleTypeAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.data.ScanBleBean;
import com.agridata.cdzhdj.databinding.ActivityQuarantineWebviewBinding;
import com.agridata.cdzhdj.net.myInterface.PicInterface;
import com.agridata.cdzhdj.utils.AppConstants;
import com.agridata.cdzhdj.utils.CookieUtils;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.agridata.cdzhdj.utils.PicUtils;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.luck.picture.lib.engine.ImageEngine;
import com.lzx.utils.RxToast;
import com.org.smartbluekit.BlueDevice;
import com.org.smartbluekit.BlueManager;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-29 17:22.
 * @Description :描述 检疫受理流程运行页面嵌套html
 */
public class QuarantineWebHistoryViewActivity extends BaseActivity<ActivityQuarantineWebviewBinding> implements BlueManager.BlueManagerCallback {
    private final static String TAG = "lzx------》";
    private WebSettings webSettings;
    private static final String url = UrlUtils.JYLS;


    private ArrayList<ScanBleBean> mScanDataList;
    private BleListAdapter bleListScanAdapter;
    private BlueManager mBlueManager = null;
    private BlueDevice cunnectDevice = null;
    private List<BlueDevice> blueDeviceList;
    private Boolean showBleDialogBle = false;
    private AlertDialog exitDialog;
    private String tagReaderBle;
    private BluetoothAdapter mBluetooth;
    private int mOpenCode = 666;
    private int BleType = 0;
    private String address;


    private CustomLoadingDialog mLoadingDialog;
    public static final int SEARCH_DEVICE_CODE = 100;
    private BluetoothSocket btSocket1 = null;
    private InputStream mmInStream;
    private OutputStream mmOutStream;

    public static final int MESSAGE_CONNECT = 6;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_CONNECT_SUCCEED = 7;
    public static final int MESSAGE_CONNECT_LOST = 8;
    public static final int MESSAGE_RECV = 10;
    public static final int MESSAGE_EXCEPTION_RECV = 11;
    private Boolean bConnect = false;

    private ImageEngine imageEngine;

    public static void start(Context context) {
        Intent intent = new Intent(context, QuarantineWebHistoryViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ActivityQuarantineWebviewBinding getBinding() {
        return ActivityQuarantineWebviewBinding.inflate(getLayoutInflater());
    }

    /**
     * 初始化蓝牙适配器
     */
    private void initBluetooth() {
        //Android从4.3开始增加支持BLE技术（即蓝牙4.0以上版本）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //从系统服务中获取蓝牙管理器
            BluetoothManager bm = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetooth = bm.getAdapter();
        } else {
            //获取系统默认的蓝牙适配器
            mBluetooth = BluetoothAdapter.getDefaultAdapter();
        }
        if (mBluetooth == null) {
            Objects.requireNonNull(RxToast.warning(QuarantineWebHistoryViewActivity.this, "本机未找到蓝牙功能"));
        }
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).init();//沉浸式状态栏
        binding.webBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.J5), android.graphics.PorterDuff.Mode.SRC_IN);
        initLoadingView();
        settingWebView();
        settingWebViewClient();
        syncCookie(url);
        binding.webView.loadUrl(url);


        initBluetooth();//初始化蓝牙

        //获取蓝牙管理器
        //Get the bluetooth manager.
        mBlueManager = BlueManager.getInstance(this);
        //注册观察者
        //Registered observer
        mBlueManager.registerObserver(this);
        blueDeviceList = new ArrayList<>();

        //断开Ble 链接设置
        binding.duankaiTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BleType == 3) {
                    if (cunnectDevice != null) {
                        //断开当前连接
                        //Disconnect device
                        mBlueManager.disconnectDevice(cunnectDevice);
                    }
                } else if (BleType == 1) {
                    disconnect();
                }

            }
        });

    }

    @Override
    protected void initDatas() {


        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(connectDevices, filter);

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
        if (requestCode == SEARCH_DEVICE_CODE) {
            if (resultCode == ConnBlueToothActivity.CONNECT_DEVICE_OK) {
                initialize();
                LogUtil.d("lzx---->", "连接返回，开始扫面。。。。");
            }
        }
    }


    public void callVueJSPic(String id) {
        binding.webView.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.d(TAG, "调用js的方法");
                binding.webView.loadUrl("javascript:callJsFunction('" + id + "')");

            }
        });
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

    public void callVueJSPicUpLoad(String id, String type) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("PicMid", id);
            jsonObject.put("Type", type);
            binding.webView.post(() -> {
                LogUtil.d(TAG, "调用js的方法");
                binding.webView.loadUrl("javascript:callJsFunctionPic('" + jsonObject.toString() + "')");
            });
        } catch (Exception e) {

        }
    }

    public void callVueJSEarTag(String eartag) {
        binding.webView.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.d(TAG, "调用js的方法"); //151012906409136
                binding.webView.loadUrl("javascript:callJsFunctionEarTag('" + eartag + "')");
            }
        });
    }

    private class JavaScriptCallbackThisJavaMethod {
        @JavascriptInterface
        public void toActivity(String activityName) {
            //此处应该定义常量对应，同时提供给web页面编写者
            LogUtil.d(TAG, activityName);


        }

        @JavascriptInterface
        public void toSignActivity(String activityName) {
            //此处应该定义常量对应，同时提供给web页面编写者
            LogUtil.d(TAG, activityName);
        }

        @JavascriptInterface
        public void killActivity() {
            //此处应该定义常量对应，同时提供给web页面编写者
            finish();
        }


        @JavascriptInterface
        public void toPicActivity(String type) {
            //此处应该定义常量对应，同时提供给web页面编写者
            LogUtil.d(TAG, type + "");
            PicUtils.showPicDialog(QuarantineWebHistoryViewActivity.this, type);
            PicUtils.setCallBack(new PicInterface.CallBack() {
                @Override
                public void Success(String path, String type) {
                    upLoadImg(path, type);
                }
            });
        }

        //蓝牙方法
        @JavascriptInterface
        public void toBleSetting() {
            showScanDialog();
        }
    }

    /****************************************    TagReaderBle *****************************/
    @Override
    public void onManagerBLEAvailable(boolean b) {
    }

    @Override
    public void onManagerDevicesFound(List<BlueDevice> devices) {
        LogUtil.d(TAG, "查询的蓝牙" + devices.toString());
        if (devices.size() == 0) {
            mBlueManager.scanForDevice(BlueManager.BFM | BlueManager.LET);
            return;
        }
        if (!TextUtils.isEmpty(tagReaderBle)) {
            for (BlueDevice device : devices) {
                if (device.getDeviceMAC().equals(tagReaderBle)) {
                    mBlueManager.connectDevice(device);
                    break;
                }
            }
            return;
        }
        blueDeviceList.clear();
        for (BlueDevice device : devices) {
            blueDeviceList.add(device);
        }
        if (!showBleDialogBle) {
            showBleListDialog();
        }
        for (BlueDevice blueDevice : blueDeviceList) {
            LogUtil.d(TAG, "查询的蓝牙名字" + blueDevice.getDeviceType());
        }
        mHandler.sendEmptyMessage(1);
    }

    @Override
    public void onManagerDeviceConnected(BlueDevice blueDevice) {
        LogUtil.d(TAG, "onManagerDeviceConnected: ");
        cunnectDevice = blueDevice;
        runOnUiThread(() -> {
            Objects.requireNonNull(RxToast.success(QuarantineWebHistoryViewActivity.this, "连接成功..."));
            binding.bleTitle.setText("TagReader低频蓝牙(银色)已连接...");
            binding.bleLayout.setVisibility(View.VISIBLE);
            if (exitDialog != null) {
                exitDialog.dismiss();
            }

        });
    }

    @Override
    public void onManagerDeviceConnectFailed(BlueDevice blueDevice) {

    }

    @Override
    public void onManagerDeviceDisconnected(BlueDevice blueDevice) {
        runOnUiThread(() -> {
            binding.bleLayout.setVisibility(View.GONE);
            Objects.requireNonNull(RxToast.success(QuarantineWebHistoryViewActivity.this, "断开连接成功..."));
        });
    }

    @Override
    public void onScanStateWithDevice(BlueDevice blueDevice, String s) {

    }

    @Override
    public void onBackFatWithDevice(BlueDevice blueDevice, String s) {

    }

    @Override
    public void onEarTagWithDevice(BlueDevice blueDevice, String eartag) {
        String replace = eartag.replace("-", "");
        LogUtil.d(TAG, "扫描的耳标" + replace);
        callVueJSEarTag(replace);
    }

    /**
     * add 数据
     */
    private void addData() {
        mScanDataList = new ArrayList<>();
        mScanDataList.clear();
        String[] strings = {"BIO-TAG低频蓝牙设备(黑色)", "TagReader低频蓝牙(银色)"};
        int id = 0;
        int GroupID = 0;
        for (String string : strings) {
            id++;
            GroupID++;
            ScanBleBean scanData = new ScanBleBean();
            scanData.Name = string;
            scanData.Id = GroupID;
            mScanDataList.add(scanData);
        }
    }

    /**
     * 显示list 列表
     */
    private void showScanDialog() {
        addData();
        View view = getLayoutInflater().inflate(R.layout.scan_ble_dialog, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(true);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_ble);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, 1));
        ScanBleTypeAdapter scanBleTypeAdapter = new ScanBleTypeAdapter(R.layout.item_eartag_ble_type, this);
        recyclerView.setAdapter(scanBleTypeAdapter);
        scanBleTypeAdapter.refreshDataList(mScanDataList);
        scanBleTypeAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                switch (position) {
                    case 0://BIO-TAG低频蓝牙设备(黑色)
                        BleType = 1;
                        exitDialog.dismiss();
                        scanBluetooth();
                        //initialize();
                        break;
//                    case 1://LMA206智能卡钳（低频）
//                        BleType = 2;
//                        exitDialog.dismiss();
//                        SplitCore.INSTANCE.onSetFileProvider("com.agridata.cdzhdj.fileprovider");
//                        SplitCore.INSTANCE.onSplitResume();
//                        if (SplitCore.INSTANCE.isPackageInstalled()) {
//                            String params = "{\"id\":23,\"func\":1,\"hardware_selection\":[\"耳标钳\"],\"app_show_name\":\"戴标\",\"categoryfunc\":12}";
//                            SplitCore.INSTANCE.onOpenService(params);
//                        } else {
//                            Objects.requireNonNull(RxToast.warning(QuarantineWebViewActivity.this,"请先安装睿驱动，在进行使用！"));
//                        }
//                        break;
                    case 1://TagReader低频蓝牙(银色)
                        BleType = 3;
                        exitDialog.dismiss();
                        setBleOpenPer();
                        break;
//                    case 3://手工批量录入
//                        exitDialog.dismiss();
//                        //ManualEntryEarTagActivity.start(EarTagActivity.this, earTagListAdapter.getDataList().size() != 0 ? earTagListAdapter.getDataList().size() : 0);
//                        break;
                    default:
                } //"BIO-TAG低频蓝牙设备(黑色)","ALMA206智能卡钳（低频）","TagReader低频蓝牙(银色)","手工批量录入"
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }


    private void setBleOpenPer() {
        XXPermissions.with(this).permission(Permission.ACCESS_FINE_LOCATION).permission(Permission.ACCESS_COARSE_LOCATION)
                // 申请多个权限
                .permission(Permission.Group.BLUETOOTH).request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            Objects.requireNonNull(RxToast.info(QuarantineWebHistoryViewActivity.this, "权限获取成功"));
                            if (!mBluetooth.isEnabled()) {
                                showOpenBleDialog();
                            } else {
                                BleSp.getInstance().setTagReaderBle("");
                                tagReaderBle = BleSp.getInstance().getTagReaderBle();
                                if (!TextUtils.isEmpty(tagReaderBle)) { //如果连接过一次 下次就自动连接
                                    mBlueManager.scanForDevice(BlueManager.BFM | BlueManager.LET);
                                } else {
                                    Objects.requireNonNull(RxToast.success(QuarantineWebHistoryViewActivity.this, "开始搜索蓝牙设备..."));
                                    mBlueManager.scanForDevice(BlueManager.BFM | BlueManager.LET);
                                }
                            }


                        } else {
                            Objects.requireNonNull(RxToast.warning(QuarantineWebHistoryViewActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(QuarantineWebHistoryViewActivity.this, "被永久拒绝授权，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(QuarantineWebHistoryViewActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(QuarantineWebHistoryViewActivity.this, "获取权限失败"));
                        }
                    }
                });
    }

    /**
     * 判断蓝牙是否打开
     */
    private void showOpenBleDialog() {
        LogUtil.d("lzx--------》", "GOGOGOGOGO");
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = (TextView) view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        contentTv.setText("蓝牙功能尚未打开，是否打开蓝牙？");

        cancelTv.setOnClickListener(view1 -> exitDialog.dismiss());
        confirmTv.setOnClickListener(view12 -> {
            exitDialog.dismiss();
            //弹出是否允许扫描蓝牙设备的选择对话框
            Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            startActivityForResult(intent, mOpenCode);

        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }


    /**
     * 显示Ble list 列表
     */
    private void showBleListDialog() {
        View view = getLayoutInflater().inflate(R.layout.layout_ble_list, null);
        exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(true);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        RecyclerView recyclerview = view.findViewById(R.id.recyclerview_ble);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        bleListScanAdapter = new BleListAdapter(R.layout.item_ble_list, this);
        recyclerview.setAdapter(bleListScanAdapter);
        bleListScanAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                BlueDevice data = bleListScanAdapter.getData(position);
                if (data.getDeviceType().equals("TagReader")) {
                    mBlueManager.connectDevice(data);
                    //  BleSp.getInstance().setTagReaderBle(data.getDeviceMAC());
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
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
    /****************************************    BIO-BLE *****************************/

    /**
     * 添加低频耳标
     */
    protected void scanBluetooth() {
        Intent intent = new Intent(QuarantineWebHistoryViewActivity.this, ConnBlueToothActivity.class);
        startActivityForResult(intent, SEARCH_DEVICE_CODE);
    }


    public void initialize() {
        address = BleSp.getInstance().getLowBle();
        LogUtil.d("lzx-------》", address + " 低功耗蓝牙 ");
        if (!TextUtils.isEmpty(address)) {
            showLoading("正在连接设备");
            mHandler.sendEmptyMessageDelayed(MESSAGE_CONNECT, 1000);
        } else {
            Intent intent = new Intent(QuarantineWebHistoryViewActivity.this, ConnBlueToothActivity.class);
            startActivityForResult(intent, SEARCH_DEVICE_CODE);
        }

    }


    // Hander
    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    bleListScanAdapter.refreshDataList(blueDeviceList);
                    break;

                case MESSAGE_CONNECT:
                    new Thread(new Runnable() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void run() {
                            InputStream tmpIn;
                            OutputStream tmpOut;
                            try {
                                UUID uuid = UUID.fromString(AppConstants.SPP_UUID);
                                BluetoothDevice btDev = mBluetooth.getRemoteDevice(address);
                                btSocket1 = btDev.createInsecureRfcommSocketToServiceRecord(uuid);
                                // .createRfcommSocketToServiceRecord(uuid);
                                btSocket1.connect();
                                tmpIn = btSocket1.getInputStream();
                                tmpOut = btSocket1.getOutputStream();
                            } catch (Exception e) {
                                bConnect = false;
                                mmInStream = null;
                                mmOutStream = null;
                                btSocket1 = null;
                                e.printStackTrace();
                                mHandler.sendEmptyMessage(MESSAGE_CONNECT_LOST);
                                return;
                            }
                            mmInStream = tmpIn;
                            mmOutStream = tmpOut;
                            LogUtil.d("lzx----->", "正在连接...");
                            mHandler.sendEmptyMessage(MESSAGE_CONNECT_SUCCEED);
                        }

                    }).start();
                    break;
                case MESSAGE_CONNECT_SUCCEED:
                    bConnect = true;
                    showLoading("手持设备连接成功！");
                    hideLoading();
                    LogUtil.d("lzx----->", "手持设备连接成功...");
                    binding.bleLayout.setVisibility(View.VISIBLE);
                    binding.bleTitle.setText("IO-TAG低频蓝牙连接成功~");
                    // showLoading("开始扫描耳标...");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            byte[] bufRecv = new byte[1024];
                            int nRecv = 0;
                            while (bConnect) {
                                try {
                                    byte[] nPacket = new byte[1024];
                                    int t = 0;
                                    int byteread = 0;
                                    do {
                                        nRecv = mmInStream.read(bufRecv);
                                        byteread += nRecv;
                                        if (nRecv < 1) {
                                            Thread.sleep(100);
                                            continue;
                                        }

                                        for (int ii = 0; ii <= nRecv; ii++) {
                                            nPacket[ii + byteread - nRecv] = bufRecv[ii];
                                        }
                                    } while (byteread < 5);

                                    int i = nPacket.length;
                                    for (; ; t++) {
                                        if (nPacket[52 * t] == 0) t = t - 1;
                                        break;
                                    }
                                    byte bdata[] = new byte[52 + 52 * t];
                                    for (i = 0; i < (52 + 52 * t); i++) {
                                        bdata[i] = nPacket[i];
                                    }
                                    mHandler.obtainMessage(MESSAGE_RECV, bdata.length, 0, bdata).sendToTarget();
                                    Thread.sleep(100);
                                } catch (Exception e) {
                                    mHandler.sendEmptyMessage(MESSAGE_EXCEPTION_RECV);
                                    break;
                                }
                            }
                        }
                    }).start();
                    break;
                case MESSAGE_EXCEPTION_RECV:
                    if (!isFinishing()) {
                        Objects.requireNonNull(RxToast.error(QuarantineWebHistoryViewActivity.this, "断开成功..."));
                        binding.bleLayout.setVisibility(View.GONE);
                        disconnect();
                    }
                    break;
                case MESSAGE_CONNECT_LOST:
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(QuarantineWebHistoryViewActivity.this, "蓝牙连接失败，请检查设备是否打开并进行重新连接"));
                    disconnect();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent1 = new Intent(QuarantineWebHistoryViewActivity.this, ConnBlueToothActivity.class);
                            startActivityForResult(intent1, SEARCH_DEVICE_CODE);
                        }
                    }, 800);
                    break;
                case MESSAGE_WRITE:

                    break;
                case MESSAGE_READ:

                    break;
                case MESSAGE_RECV:
                    byte[] bBuf = (byte[]) msg.obj;

                    if (bBuf[bBuf.length - 2] != xor(bBuf)) {
                        break;
                    }

                    byte[] bRef = new byte[5];
                    bRef[0] = (byte) 0xAA;

                    if (bBuf[1] == 0x01) bRef[1] = (byte) 0x81;
                    else if (bBuf[1] == 0x02) bRef[1] = (byte) 0x82;
                    bRef[2] = (byte) 0x00;
                    bRef[3] = xor(bRef);
                    bRef[4] = (byte) 0x55;

                    String data16 = bytesToHexString(bBuf);
                    String iso;//ISO号
                    String binaryCode = null;//二维码号
                    //判断是否为二维码芯片
                    if (bBuf[1] == 0x01) {
                        String data2 = hexString2binaryString(data16.substring(36, 100));
                        iso = data16.substring(5, 20);
                        binaryCode = data16.substring(21, 36);
                    } else if (bBuf[1] == 0x02) {
                        iso = data16.substring(5, 20);
                        binaryCode = data16.substring(5, 20);
                    }
                    if (TextUtils.isEmpty(binaryCode)) {
                        Objects.requireNonNull(RxToast.error(QuarantineWebHistoryViewActivity.this, "请扫描指定耳标"));
                    } else {
                        if (binaryCode.matches("^[0-9_]+$")) {
                            //判断扫描耳标是否与动物类型匹配 TODO
                            LogUtil.d(TAG, "bio-ble耳标" + binaryCode);
                            callVueJSEarTag(binaryCode);
                        }
                    }
                    break;
            }
        }
    };

    //异或校验
    private byte xor(byte[] bytes) {
        byte b = bytes[0];
        for (int i = 1; i < bytes.length - 2; i++) {
            b ^= bytes[i];
        }
        return b;
    }

    public String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private void disconnect() {
        try {
            if (mmInStream != null) {
                mmInStream.close();
            }
            if (mmOutStream != null) {
                mmOutStream.close();
            }
            if (btSocket1 != null) {
                btSocket1.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mmInStream = null;
            mmOutStream = null;
            btSocket1 = null;
            bConnect = false;
        }
    }

    private void release() {
        try {
            Thread.sleep(500);
            if (btSocket1 != null) {
                btSocket1.close();
            }
            unregisterReceiver(connectDevices);
            hideLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BroadcastReceiver connectDevices = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                LogUtil.d("lzx---》", "ACTION_ACL_CONNECTED");
            } else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                LogUtil.d("lzx---》", "ACTION_ACL_DISCONNECTED");
            }
        }
    };


    @Override
    protected void onDestroy() {
        try {
            release();
            //注销观察者
            //Deregister observer
            mBlueManager.deregisterObserver(this);
            //取消扫描
            //Cancel scan
            mBlueManager.cancelScan();
            //断开当前连接
            //Disconnect device
            mBlueManager.disconnectDevice(cunnectDevice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


/**************************************  上传图片 ****************************************/

    /**
     * filePath 上传图片
     *
     * @param filePath
     */
    private void upLoadImg(String filePath, String type) {
        showLoading("图片上传中...");
        HttpRequest.upLoadImg(QuarantineWebHistoryViewActivity.this, filePath, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d(TAG, "上传图片" + content.toString());
                if (content.Status == 0) {
                    hideLoading();
                    Objects.requireNonNull(RxToast.success(QuarantineWebHistoryViewActivity.this, "上传成功~"));
                    callVueJSPicUpLoad(content.Result, type);
                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }


}


