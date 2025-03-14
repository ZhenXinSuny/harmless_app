package com.agridata.cdzhdj.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.network.utils.LogUtil;

import java.io.File;
import java.util.List;



public class AppUtil {
    private Context mContext;
    /**
     * App存储目录
     */
    public static final String PATH_APP = "HarmlessApp";    // 农安星根目录
    public static final String PATH_APP_LOG = PATH_APP + "/log";    // 日志目录
    public static final String PATH_APP_RESOURCE = PATH_APP + "/.resource";  // 资源目录，隐藏目录
    public static final String PATH_APP_IMAGE = PATH_APP + "/image";   // 图片目录
    public static final String PATH_APP_FILE = PATH_APP + "/file";   // 文件目录
    public static final String PATH_APP_THUMB = PATH_APP + "/.thumb";  // 缩略图目录，隐藏目录
    public static final String ABSOLUTE_PATH = MyApplication.getContext().getExternalCacheDir().getAbsolutePath();    // 绝对根路径
    public static final String ABSOLUTE_PATH_APP_IMAGE = ABSOLUTE_PATH + "/" + PATH_APP_IMAGE;   // 绝对图片目录
    public static final String ABSOLUTE_PATH_APP_FILE = ABSOLUTE_PATH + "/" + PATH_APP_FILE;   // 绝对文件目录
    public static final String ABSOLUTE_PATH_APP_THUMB = ABSOLUTE_PATH + "/" + PATH_APP_THUMB;  // 绝对缩略图目录，隐藏目录



    private static Boolean mIsDebug = true; // 是否是debug模式

    static {
        LogUtil.isDebug = true;             // 是否打印日志
    }


    /**
     * 是否是debug模式
     *
     * @return
     */
    public static boolean isDebug() {
        return mIsDebug != null && mIsDebug;
    }

    /**
     * 同步app的debug模式
     * 注意：应该在Application中初始化
     *
     * @param context
     */
    public static void syncIsDebug(Context context) {
        if (mIsDebug == null) {
            mIsDebug = context.getApplicationInfo() != null && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }

    /**
     * 获取应用程序名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回app版本名字
     * 对应build.gradle中的versionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 返回app版本号
     * 对应build.gradle中的versionCode
     *
     * @param context
     * @return
     */
    public static String getVersionCode(Context context) {
        String versionCode = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = String.valueOf(packInfo.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * 显示应用详细信息页面
     *
     * @param context
     */
    public static void showInstalledAppDetails(Context context) {
        Intent intent = new Intent();
        final int apiLevel = Build.VERSION.SDK_INT;
        if (apiLevel >= 9) { // 2.3（ApiLevel 9）以上，使用SDK提供的接口
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
        } else { // 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）
            // 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。
            final String appPkgName = (apiLevel == 8 ? "pkg" : "com.android.settings.ApplicationPkgName");
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra(appPkgName, context.getPackageName());
        }
        context.startActivity(intent);
    }

    /**
     * 获取当前App进程的id
     *
     * @return
     */
    public static int getAppProcessId() {
        return android.os.Process.myPid();
    }

    /**
     * 判断当前应用是否是debug状态
     *
     * @param context
     * @return
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 创建应用程序存储目录
     *
     * @param storageDir
     * @param context
     * @return
     */
    public static String createAppStorageDir(String storageDir, Context context) {
        String dir = null;
        if (SDCardUtil.isSDCardEnable()) {     // 如果存在SD卡
            dir = SDCardUtil.getSDCardPath() + File.separator + storageDir;
        } else {    // 不存在SD卡，就放到缓存文件夹内
            context = context.getApplicationContext();
            dir = context.getCacheDir().getAbsolutePath() + File.separator + storageDir;
        }

        File folder = new File(dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folder.getAbsolutePath();
    }

    /**
     * 应用是否处于后台
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
            }
        }
        return false;
    }

    /**
     * 获取当前系统Launcher
     *
     * @param context
     * @return
     */
    public static String getLauncherName(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo != null) {
            return resolveInfo.activityInfo.packageName;
        } else {
            return "";
        }
    }
    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     *
     * @param versionNew
     * @param versionOld
     */
    public static int compareVersion(String versionNew, String versionOld) {
        if (isNotEmpty(versionNew) && isNotEmpty(versionOld)) {
            //注意此处为正则匹配，不能用"."
            String[] versionArrayNew = versionNew.split("\\.");
            String[] versionArrayOld = versionOld.split("\\.");
            LogUtil.d("Lzx-----》"," versionArrayNew" + versionArrayNew.toString());
            LogUtil.d("Lzx-----》"," versionArrayOld" + versionArrayOld.toString());
            int oldLength = versionArrayOld.length;
            int newLength = versionArrayNew.length;
            //取最小长度值
            int minLength = Math.min(oldLength, newLength);
            LogUtil.d("Lzx-----》"," oldLength" + oldLength +"");
            LogUtil.d("Lzx-----》"," newLength" + newLength +"");

            LogUtil.d("Lzx-----》"," minLength" + minLength +"");
            for (int i = 0; i < minLength; i++) {
                if (Integer.parseInt(versionArrayNew[i]) > Integer.parseInt(versionArrayOld[i])) {
                    return 1;
                }
            }
            if (newLength > oldLength) {
                return 1;
            }
            return 0;
        } else if (isEmpty(versionNew) && isEmpty(versionOld)) {
            return 0;
        } else if (isNotEmpty(versionNew)) {
            return 1;
        } else {
            return -1;
        }
    }


    /**

     * 版本号比较
     * compareTo()方法返回值为int类型，就是比较两个值，如：x.compareTo(y)。如果前者大于后者，返回1，前者等于后者则返回0，前者小于后者则返回-1
     * @param s1
     * @param s2
     * @return 范围可以是"2.20.", "2.10.0" ,".20","2.10.0",2.1.3 ，3.7.5，10.2.0
     */

    public static int compareVersionNew(String s1, String s2) {
        String[] s1Split = s1.split("\\.", -1);
        String[] s2Split = s2.split("\\.", -1);
        int len1 = s1Split.length;
        int len2 = s2Split.length;
        int lim = Math.min(len1, len2);
        int i = 0;
        while (i < lim) {
            int c1 = "".equals(s1Split[i]) ? 0 : Integer.parseInt(s1Split[i]);
            int c2 = "".equals(s2Split[i]) ? 0 : Integer.parseInt(s2Split[i]);
            if (c1 != c2) {
                return c1 - c2;
            }
            i++;
        }
        return len1 - len2;
    }


    /**
     * 判断字符串是否非空
     *
     * @param str
     *
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }
    /**
     * 判断字符串是否为空
     *
     * @param str
     *
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
