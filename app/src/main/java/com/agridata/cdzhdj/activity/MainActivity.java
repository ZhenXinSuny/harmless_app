package com.agridata.cdzhdj.activity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.agridata.cdzhdj.BuildConfig;
import com.agridata.cdzhdj.SPUtil.TokenConfigSp;
import com.agridata.cdzhdj.activity.quarantine.QuarantineMenuActivity;
import com.agridata.cdzhdj.activity.welcome.WelComeActivity;
import com.agridata.cdzhdj.data.AppConfigurationBean;
import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.AddressSP;
import com.agridata.cdzhdj.SPUtil.AnimalSP;
import com.agridata.cdzhdj.SPUtil.AppFunSP;
import com.agridata.cdzhdj.SPUtil.FirstLoginSP;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.AppFunBean;
import com.agridata.cdzhdj.data.AppVerBean;
import com.agridata.cdzhdj.data.CustomerBean;
import com.agridata.cdzhdj.data.ImmuneAnimalBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.ShowPublishBean;
import com.agridata.cdzhdj.data.UserData;
import com.agridata.cdzhdj.databinding.ActivityMainBinding;

import com.agridata.cdzhdj.dbutils.DaoManager;
import com.agridata.cdzhdj.activity.fragment.MainFragment;
import com.agridata.cdzhdj.activity.fragment.MineFragment;
import com.agridata.cdzhdj.utils.ActivityManager;
import com.agridata.cdzhdj.utils.AppConstants;
import com.agridata.cdzhdj.utils.AppUtil;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.agridata.util.Base64Util;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.azhon.appupdate.view.NumberProgressBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lzx.utils.RxToast;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends BaseActivity<ActivityMainBinding> implements BottomNavigationView.OnNavigationItemSelectedListener,TencentLocationListener {
    private AlertDialog exitDialog;
    private NumberProgressBar my_bar;
    private final static String TAG = "lzx------》";
    private FragmentManager mSupportFragmentManager;
    private FragmentTransaction mTransaction;
    private List<Fragment> mFragments = new ArrayList<>();


    private MainFragment mainFragment;
    private MineFragment mineFragment;
    private int mLastIndex; //记录点击上一个fragment的索引值


    private String AppUrl;
    private TencentLocationManager mLocationManager;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityMainBinding getBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initView() {
        DaoManager.copyDataBase(this);

        Boolean ZT = FirstLoginSP.getInstance().getAddressZT();
        if (!ZT) {

        }
        getPermissions();
        //initGaoDe();

        getShowPublish();
        binding.navView.setItemIconSize(60);
        binding.navView.setOnNavigationItemSelectedListener(this);
        mainFragment = MainFragment.newInstance();
        mineFragment = MineFragment.newInstance();
        mFragments.add(mainFragment);
        mFragments.add(mineFragment);
        setFragmentPosition(0);
        getFunApp();
        getCustomer();
        getAnimalBean();
        UserData userData = new UserData();
        userData.userId = UserDataSP.getInstance().getUserInfo().Result.userId;
        userData.partitionId = "d5896b31964e425382df52f655dedfc2";
        String jsonString = GsonUtil.toJson(userData);
        String signString = null;
        try {
            signString = Base64Util.encodeData(jsonString);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        HttpRequest.getUsserInfo(MainActivity.this, signString, new CallBackLis<LoginData>() {
            @Override
            public void onSuccess(String method, LoginData loginData) {
                // 存储更新后的 loginData
                UserDataSP.getInstance().setUserInfo(loginData);

                getAppConfig();
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });

        //清空 WebView 缓存目录
        File cacheDir = new File(getApplicationContext().getCacheDir(), "webviewCache");
        if (cacheDir.exists() && cacheDir.isDirectory()) {

            for (File file : Objects.requireNonNull(cacheDir.listFiles())) {
                file.delete();
            }
        }
    }

    private void getAppConfig() {
        HttpRequest.getAppConfiguration(MainActivity.this, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, AppConfigurationBean appConfigurationBean) {
                if (appConfigurationBean.code == 200) {
                    TokenConfigSp.getInstance().setTokenConfig(appConfigurationBean.data.tokenStatus);
                    TokenConfigSp.getInstance().setKey(appConfigurationBean.data);
                } else {
                    Objects.requireNonNull(RxToast.error(MainActivity.this, appConfigurationBean.message));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(MainActivity.this, error));
            }
        });
    }

    private void getPermissions() {
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.ACCESS_FINE_LOCATION).permission(Permission.ACCESS_COARSE_LOCATION).request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean all) {
                        if (all) {
                            if (!checkGPSIsOpen(MainActivity.this)) {
                                showOpenGps();
                            } else {
                                initGaoDe();
                            }
                        } else {
                            showOpenGps();
                            Objects.requireNonNull(RxToast.warning(MainActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(MainActivity.this, "被永久拒绝定位授权，无法使用定位功能，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(MainActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(MainActivity.this, "获取权限失败"));
                        }
                    }
                });
    }

    private void getAnimalBean() {
        HttpRequest.getImmuneAnimal(MainActivity.this, new CallBackLis<ImmuneAnimalBean>() {
            @Override
            public void onSuccess(String method, ImmuneAnimalBean content) {
                LogUtil.d(TAG, "获取动物" + content.toString());
                if (content.status == 0) {
                    AnimalSP.getInstance().setAnimalList(content.result);
                } else {
                    Objects.requireNonNull(RxToast.success(MainActivity.this, content.message));
                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }

    /**
     * onNavigationItemSelected监听
     *
     * @param menuItem
     * @return
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.mainFragment:
                setFragmentPosition(0);
                break;
            case R.id.mineFragment:
                setFragmentPosition(1);
                break;
            default:
                break;
        }
        return true;
    }

    public void setFragmentPosition(int position) {
        mSupportFragmentManager = this.getSupportFragmentManager();
        mTransaction = mSupportFragmentManager.beginTransaction();
        Fragment currentFragment = mFragments.get(position);
        Fragment lastFragement = mFragments.get(mLastIndex);
        mLastIndex = position;
        mTransaction.hide(lastFragement);
        if (!currentFragment.isAdded()) {
            mTransaction.add(R.id.fragment_content, currentFragment);
        }
        mTransaction.show(currentFragment);
        mTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BuildConfig.IS_MAP) {
            getAppUpData();
        }
    }

    private void initGaoDe() {
        TencentLocationManager.setUserAgreePrivacy(true);
        mLocationManager = TencentLocationManager.getInstance(this);
        // 设置定位精度和更新频率
        TencentLocationRequest request = TencentLocationRequest.create()
                // 设置定位精度
                .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME).setGpsFirst(true).setAllowGPS(true)
                .setAllowDirection(true).setIndoorLocationMode(true)
                // 设置定位更新的时间间隔（单位：毫秒）
                .setInterval(10000);
        // 开始定位
        mLocationManager.requestSingleFreshLocation(request , this, Looper.getMainLooper());

    }

    private void hide(Fragment fragment, boolean add) {
        mTransaction = mSupportFragmentManager.beginTransaction();
        if (add) {
            mTransaction.add(R.id.fragment_content, fragment);
        }
        for (Fragment fragments : mFragments) {
            if (fragment.equals(fragments)) {
                mTransaction.show(fragments);
            } else {
                mTransaction.hide(fragments);
            }
        }
        mTransaction.commit();
    }

    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;

    /**
     * 添加返回键监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示

                Objects.requireNonNull(RxToast.normal(this, "再按一次退出程序"));
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                ActivityManager.getInstance().finishAllActivity();
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //对比进行更新
    private void getAppUpData() {
        HttpRequest.getVer(MainActivity.this, new CallBackLis<AppVerBean>() {
            @Override
            public void onSuccess(String method, AppVerBean appVerBean) {
                if (appVerBean.ErrorCode == 0) {
                    String currentVersion = AppUtil.getVersionName(MainActivity.this);  //获取当前版本名
                    if (AppUtil.compareVersionNew(appVerBean.Result.get(0).VersionNo, currentVersion) > 0) {
                        showUpDataNewVersionDialog(appVerBean.Result.get(0).VersionNo, appVerBean.Result.get(0).Remark);
                        AppUrl = AppConstants.AppUrlDown + appVerBean.Result.get(0).FilePath + "&type=file";

                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }

    private void getFunApp() {
        HttpRequest.getFunAppInfo(MainActivity.this, new CallBackLis<AppFunBean>() {
            @Override
            public void onSuccess(String method, AppFunBean appFunBean) {
                if (appFunBean.errorCode == 0) {
                    AppFunSP.getInstance().setAppFunInfo(appFunBean.result);
                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }

    private void getCustomer() {
        HttpRequest.getCustomerInfo(MainActivity.this, new CallBackLis<CustomerBean>() {
            @Override
            public void onSuccess(String method, CustomerBean customerBean) {
                if (customerBean.errorCode == 0) {
                    AppFunSP.getInstance().setCustomerInfo(customerBean.result.get(0));
                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }

    /**
     * 更新弹框
     */
    private void showUpDataNewVersionDialog(String newVersion, String Remark) {
        View view = getLayoutInflater().inflate(R.layout.dialog_update_h, null);
        exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.setCancelable(false);
        Objects.requireNonNull(exitDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Button btn_update = (Button) view.findViewById(R.id.btn_update);//确定按钮
        ImageView ib_close = (ImageView) view.findViewById(R.id.ib_close);//确定按钮
        TextView tv_description = (TextView) view.findViewById(R.id.tv_description);//内容
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);//标题
        tv_title.setText("发现新版v" + newVersion + "可以下载啦！");
        my_bar = view.findViewById(R.id.number_progress_bar);
        tv_description.setText(Remark.replace("\\n", "\n"));
        ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upApk();
            }
        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }


    private void upApk() {
        new DownloadManager.Builder(this).apkUrl(AppUrl).smallIcon(R.drawable.ic_whh).apkName("智慧动监.apk").onDownloadListener(listenerAdapter).showBgdToast(false).jumpInstallPage(true).build().download();
    }

    private OnDownloadListener listenerAdapter = new OnDownloadListener() {
        //开始下载
        @Override
        public void start() {

        }

        //下载中
        @Override
        public void downloading(int max, int progress) {
            int curr = (int) (progress / (double) max * 100.0);
            my_bar.setMax(100);
            my_bar.setProgress(curr);
        }

        //下载完成
        @Override
        public void done(File apk) {
            exitDialog.dismiss();
        }

        //取消
        @Override
        public void cancel() {

        }

        @Override
        public void error(@NonNull Throwable throwable) {

        }
    };


    private void getShowPublish() {
        HttpRequest.getShowPublish(MainActivity.this, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, ShowPublishBean customerBean) {
                if (customerBean.code == 200) {
                    if (customerBean.data.statue != 0) {
                        showDiaLogNotificationBox(customerBean.data.message);
                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }


    private void showDiaLogNotificationBox(String message) {
        View view = getLayoutInflater().inflate(R.layout.dialog_notification_box, null);
        AlertDialog exitDialog = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.setCancelable(false);
        Objects.requireNonNull(exitDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Button btn_update = (Button) view.findViewById(R.id.btn_update);//确定按钮
        ImageView ib_close = (ImageView) view.findViewById(R.id.ib_close);//确定按钮
        TextView tv_description = (TextView) view.findViewById(R.id.tv_description);//确定按钮
        tv_description.setText(message);
        ib_close.setOnClickListener(view12 -> {
            exitDialog.dismiss();

        });
        btn_update.setOnClickListener(view1 -> {
            exitDialog.dismiss();
        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.75);
        exitDialog.getWindow().setAttributes(params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationManager != null) {
            //停止定位
            // mLocationManager.removeUpdates();
        }
    }

    private void showOpenGps() {
        View view = getLayoutInflater().inflate(R.layout.opne_gps_layout, null);
        final android.app.AlertDialog exitDialog = new android.app.AlertDialog.Builder(MainActivity.this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(exitDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        TextView confirmTv = view.findViewById(R.id.positive_tv);
        TextView contentTv = view.findViewById(R.id.content_tv);
        contentTv.setText("为了您更好的使用智慧动监功能，请开启GPS定位服务");
        confirmTv.setOnClickListener(view1 -> {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 666);
            exitDialog.dismiss();
        });


        if (ActivityManager.getInstance().isLiving(MainActivity.this)) {
            exitDialog.show();
        }
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(MainActivity.this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }

    /**
     * 检查是否打开手机的gps
     *
     * @param context
     * @return
     */
    public boolean checkGPSIsOpen(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onLocationChanged(TencentLocation location, int error, String reason) {
        if (error == TencentLocation.ERROR_OK) {
            // 定位成功
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            // 你可以将定位结果显示出来，或者做进一步处理
            LogUtil.d("lzx--->", "Latitude: " + latitude + ", Longitude: " + longitude + "name: " + location.getAddress());
        } else {
            // 定位失败
            LogUtil.d("lzx--->", "Location error: " + reason);
        }
    }

    @Override
    public void onStatusUpdate(String name, int status, String desc) {
        LogUtil.d("lzx--->", "String error: " + name);
        LogUtil.d("lzx--->", "String error: " + status);
        LogUtil.d("lzx--->", "String error: " + desc);
    }
}