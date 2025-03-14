package com.agridata.cdzhdj.base;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.agridata.cdzhdj.BuildConfig;
import com.agridata.cdzhdj.SPUtil.AppDataProvider;
import com.agridata.cdzhdj.SPUtil.ConfigSP;
import com.agridata.cdzhdj.SPUtil.FirstSpUtils;
import com.agridata.cdzhdj.SPUtil.SplitCoreSP;
import com.agridata.cdzhdj.utils.AppUtil;
import com.agridata.network.CubeUI;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.smartahc.android.splitcore_androidx.AppType;
import com.smartahc.android.splitcore_androidx.Common;
import com.smartahc.android.splitcore_androidx.ShowWindowLocation;
import com.smartahc.android.splitcore_androidx.SmartUser;
import com.smartahc.android.splitcore_androidx.SplitCore;
import com.smartahc.android.splitcore_androidx.VersionName;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;
import java.util.Objects;


public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks{

    public static Context mContext;
    private int  mActivityCount; // activity计数器
    //获取消息推送代理示例

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        clearWebViewCacheOnUpdate();
        initLogger();//初始化日志
        //设置App资源路径
        ConfigSP.getInstance().setResourcePath(AppUtil.createAppStorageDir(AppUtil.PATH_APP, this));
        initThirdService();
        // 注册App所有Activity的生命周期回调监听器
        registerActivityLifecycleCallbacks(this);
        setBleManager();
       // initX5app(this);
        boolean firstShow = FirstSpUtils.getInstance().getFirstShow();
        if (firstShow) { //为true不显示
            UMConfigure.setLogEnabled(true);
            UMConfigure.init(
                    getApplicationContext(),
                    "64ab9974bd4b621232cdf976",		//在UMeng+申请的KEY值
                    "Umeng",										//自己定义的渠道名字
                    UMConfigure.DEVICE_TYPE_PHONE,
                    null
            );
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

        }
        CubeUI.getInstance().init(this, new AppDataProvider());
    }
    private void clearWebViewCacheOnUpdate() {
        SharedPreferences preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int lastVersion = preferences.getInt("last_version", -1);
        int currentVersion = BuildConfig.VERSION_CODE;
        if (currentVersion > lastVersion) {
            clearWebViewCache();
            preferences.edit().putInt("last_version", currentVersion).apply();
        }
    }
    public void clearWebViewCache() {
        deleteDir(new File(getApplicationContext().getCacheDir(), "webviewCache"));
    }

    private void deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                deleteDir(file);
            }
        }
        if (dir != null) {
            dir.delete();
        }
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(this);
    }
    private void initLogger() {
        LogConfiguration config = new LogConfiguration.Builder()
                .tag("XLOG")                                         // 指定 TAG，默认为 "X-LOG"
//                .t()                                                   // 允许打印线程信息，默认禁止
//                .st(1)                                                 // 允许打印深度为2的调用栈信息，默认禁止
//                .b()                                                   // 允许打印日志边框，默认禁止
                .build();

        com.elvishew.xlog.printer.Printer androidPrinter = new AndroidPrinter();             // 通过 android.util.Log 打印日志的打印器
        XLog.init(                                                 // 初始化 XLog
                config,                                                // 指定日志配置，如果不指定，会默认使用 new LogConfiguration.Builder().build()
                androidPrinter                                      // 添加任意多的打印器。如果没有添加任何打印器，会默认使用 AndroidPrinter(Android)/ConsolePrinter(java)
        );
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    /**
     * 开启异步线程去初始化第三仿
     */
    private void initThirdService() {
        new Thread() {
            @Override
            public void run() {
                super.run();
            }
        }.start();
    }




    /**
     * 获取上下文
     */
    public static Context getContext() {
        return mContext;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        this.mActivityCount++;
        if (0 == this.mActivityCount - 1) {

        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        this.mActivityCount--;
        if (0 == this.mActivityCount) {
        }
        //此处判断是小米机型并且是在后台则调用
        if (Build.MANUFACTURER.equalsIgnoreCase("xiaomi") && !isForeground()) {
            //NotificationUtil.setXiaoMiBadgeNum(mContext,3);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


    //当前应用是否处于前台
    public boolean isForeground() {
        android.app.ActivityManager am = (android.app.ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(getPackageName())) {
            return true;
        }
        return false;
    }



    private  void  setBleManager(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SplitCore.INSTANCE.onSplitCreate(getApplicationContext(), new SmartUser("dongjian", "1234567", null, null, true), AppType.APP_DRIVER,
                    VersionName.v2_1_1, ShowWindowLocation.top_left);
            SplitCore.INSTANCE.onSetFileProvider("com.agridata.cdzhdj.fileprovider");
            SplitCore.INSTANCE.requestDeviceInfo();
        }
        registryBroadcast();
    }

    /**
     * 注册广播
     */
    private void registryBroadcast() {
         registerReceiver(broadcast, new IntentFilter(Common.SERVICE_BROADCAST_ACTION_FILTER_SUSPENSION));
        registerReceiver(broadcast, new IntentFilter(Common.SERVICE_BROADCAST_ACTION_TAGCHECK));
         registerReceiver(broadcast, new IntentFilter(Common.SERVICE_BROADCAST_ACTION_DEVICE));
    }

    /**
     * 接收
     */
    private BroadcastReceiver broadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {

                case Common.SERVICE_BROADCAST_ACTION_DEVICE:
                    final String deviceInfo = intent.getStringExtra(Common.SERVICE_BROADCAST_ACTION_DEVICE_RESPONSE);
                    Log.d("lzx----》App", "deviceInfo" + deviceInfo);
                    if (deviceInfo != null) {
                        if (!deviceInfo.equals("")) {
                            SplitCoreSP.getInstance().setConnectStatus("1");
                        } else {
                            SplitCoreSP.getInstance().setConnectStatus("2");
                        }
                    } else {
                        SplitCoreSP.getInstance().setConnectStatus("2");
                    }
                    break;
                case Common.SERVICE_BROADCAST_ACTION_TAGCHECK:
                    final int TAGCHECK_COUNT = intent.getIntExtra(Common.SERVICE_BROADCAST_ACTION_TAGCHECK_COUNT, -1);
                    final String TAGCHECK_END = intent.getStringExtra(Common.SERVICE_BROADCAST_ACTION_TAGCHECK_END);
                    String TAGCHECK_ERROR = intent.getStringExtra(Common.SERVICE_BROADCAST_ACTION_TAGCHECK_ERROR);
                    Log.d("lzx------》App", " TAGCHECK_COUNT数量" + TAGCHECK_COUNT);
                    Log.d("lzx------》App", " TAGCHECK_END结束" + TAGCHECK_END);
                    Log.d("lzx------》App", " TAGCHECK_ERROR错误" + TAGCHECK_ERROR);

                    break;
                case Common.SERVICE_BROADCAST_ACTION_FILTER_SUSPENSION:
                    final String earTag = intent.getStringExtra(Common.SERVICE_BROADCAST_KEY_EARTAG_SUSPENSION);
                    boolean booleanExtra = intent.getBooleanExtra(Common.SERVICE_BROADCAST_KEY_SERVICE_STYLE, true);
                    final String stringExtraDev = intent.getStringExtra(Common.SERVICE_BROADCAST_KEY_DEVICE_SUSPENSION);
                    Log.d("lzx------》App", " stringExtraDev" + stringExtraDev);
                    if (!TextUtils.isEmpty(stringExtraDev) && !stringExtraDev.equals("")) {
                        String[] split = stringExtraDev.split(",");
                        String result = split[1];
                    }

            }
        }
    };
}
