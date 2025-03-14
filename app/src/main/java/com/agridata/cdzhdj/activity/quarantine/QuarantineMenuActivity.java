package com.agridata.cdzhdj.activity.quarantine;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.AddressSP;
import com.agridata.cdzhdj.SPUtil.FirstLoginSP;
import com.agridata.cdzhdj.activity.adapter.MainMenuAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.MenuData;
import com.agridata.cdzhdj.databinding.ActivityImmuneMenuBinding;
import com.agridata.cdzhdj.utils.ActivityManager;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.utils.LogUtil;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lzx.utils.RxToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-01-04 16:52.
 * @Description :描述
 */
public class QuarantineMenuActivity extends BaseActivity<ActivityImmuneMenuBinding> {
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private List<MenuData> mMenuDataList;
    private final static String TAG = "QuarantineMenuActivity------》";
    private MainMenuAdapter mainMenuAdapter;
    private static final int MESSAGE_LOGIN = 1;

    public static void start(Activity context) {
        Intent intent = new Intent(context, QuarantineMenuActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityImmuneMenuBinding getBinding() {
        return ActivityImmuneMenuBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.titlebarMiddle.setText("检疫与受理");
        setMenuData();

        setMenuUi();
    }

    @Override
    protected void initDatas() {

    }

    private void setMenuData() {
        mMenuDataList = new ArrayList<>();
        MenuData menuData = new MenuData("申报受理", R.drawable.ic_jysl, 4);
        mMenuDataList.add(menuData);
        MenuData menuData1 = new MenuData("申报检疫", R.drawable.ic_sbjy, 5);
        MenuData menuData2 = new MenuData("检疫历史", R.drawable.ic_jy_history, 6);
        MenuData menuData3 = new MenuData("无纸化出证", R.drawable.dzcz, 7);
        mMenuDataList.add(menuData1);
        mMenuDataList.add(menuData2);
        mMenuDataList.add(menuData3);

    }


    private void setMenuUi() {
        GridLayoutManager layoutManager = new GridLayoutManager(QuarantineMenuActivity.this, 3);
        binding.recyclerview.setLayoutManager(layoutManager);
        mainMenuAdapter = new MainMenuAdapter(R.layout.item_menu, mMenuDataList, QuarantineMenuActivity.this, layoutManager);
        binding.recyclerview.setAdapter(mainMenuAdapter);
        mainMenuAdapter.refreshDataList(mMenuDataList);
        mainMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                MenuData data = mainMenuAdapter.getData(position);
                if (data.id == 4) {
                    QuarantineWebViewActivity.start(QuarantineMenuActivity.this);
                } else if (data.id == 5) {
                    getPermissions();
                } else if (data.id == 6) {
                    QuarantineWebHistoryViewActivity.start(QuarantineMenuActivity.this);
                }else if (data.id == 7) {
                    PaperlessCertificationActivity.start(QuarantineMenuActivity.this);
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
    }


    private void getPermissions() {
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.MANAGE_EXTERNAL_STORAGE).permission(Permission.ACCESS_FINE_LOCATION).permission(Permission.ACCESS_COARSE_LOCATION).request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean all) {
                        if (!permissions.contains(Permission.MANAGE_EXTERNAL_STORAGE)) {
                            Objects.requireNonNull(RxToast.warning(QuarantineMenuActivity.this, "以便于您更好的使用检疫申报，请打开文件管理权限"));
                            return;
                        }
                        LogUtil.d("lzx---->", permissions.toString());
                        if (all) {
                            if (!checkGPSIsOpen(QuarantineMenuActivity.this)) {
                                showOpenGps();
                            } else {
                                initGaoDe();
                            }
                        } else {
                            showOpenGps();
                            Objects.requireNonNull(RxToast.warning(QuarantineMenuActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(QuarantineMenuActivity.this, "被永久拒绝定位授权，无法使用收集功能，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(QuarantineMenuActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(QuarantineMenuActivity.this, "获取权限失败"));
                        }
                    }
                });
    }

    /**
     * 已经提交过信息了
     */
    private void showOpenGps() {
        View view = getLayoutInflater().inflate(R.layout.opne_gps_layout, null);
        final android.app.AlertDialog exitDialog = new android.app.AlertDialog.Builder(QuarantineMenuActivity.this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        contentTv.setText("为了您更好的使用检疫受理功能，请开启GPS定位服务");
        confirmTv.setOnClickListener(view1 -> {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 666);
            exitDialog.dismiss();
        });


        if (ActivityManager.getInstance().isLiving(QuarantineMenuActivity.this)) {
            exitDialog.show();
        }
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(QuarantineMenuActivity.this) * 0.85);
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

    private void initGaoDe() {
        //初始化定位
        try {
            AMapLocationClient.updatePrivacyShow(QuarantineMenuActivity.this, true, true);
            AMapLocationClient.updatePrivacyAgree(QuarantineMenuActivity.this, true);
            mLocationClient = new AMapLocationClient(QuarantineMenuActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //启动定位
        mLocationClient.startLocation();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666) {
            LogUtil.i(TAG, "回传执行");
            if (checkGPSIsOpen(QuarantineMenuActivity.this)) {
                initGaoDe();
            }
        }
    }

    private class MyAMapLocationListener implements AMapLocationListener {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {

                    LogUtil.i("位置：", aMapLocation.toString());
                    double longitude = aMapLocation.getLongitude();//精度
                    double latitude = aMapLocation.getLatitude();//维度
                    AddressSP.getInstance().setLatitude(latitude);
                    AddressSP.getInstance().setLongitude(longitude);
                    AddressSP.getInstance().setLongitude(longitude);
                    AddressSP.getInstance().setAddress(aMapLocation.getAddress());
                    FirstLoginSP.getInstance().setAddressZT(true);
                    mHandler.sendEmptyMessageDelayed(MESSAGE_LOGIN, 800);

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    LogUtil.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
                }
            }
        }
    }

    private Handler mHandler = new Handler(Looper.getMainLooper(), msg -> {

        switch (msg.what) {
            case MESSAGE_LOGIN:
                Objects.requireNonNull(RxToast.success(QuarantineMenuActivity.this, "获取定位成功"));
                QuarantineWebAcceptViewActivity.start(QuarantineMenuActivity.this);
                break;
            default:
                break;
        }
        return true;
    });


}
