package com.agridata.cdzhdj.activity.fragment;

import android.annotation.SuppressLint;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.BuildConfig;
import com.agridata.cdzhdj.activity.harmless.xcsh.OnSiteAuditHarmlessActivity;
import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.AddressSP;
import com.agridata.cdzhdj.SPUtil.AnimalSP;
import com.agridata.cdzhdj.SPUtil.FirstLoginSP;
import com.agridata.cdzhdj.SPUtil.IsCheckDZHSp;
import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.SPUtil.SlaughterHouseSp;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.harmless.CanYuWu.CanYuWuWebViewActivity;
import com.agridata.cdzhdj.activity.harmless.ChuLi.ChuLiWebViewActivity;
import com.agridata.cdzhdj.activity.farm.beastsbirds.BeastsBirdsMainActivity;
import com.agridata.cdzhdj.activity.entrycheck.bindslaughterhouse.BindSlaughterhouseActivity;
import com.agridata.cdzhdj.activity.entrycheck.history.EntryCheckHisToryActivity;
import com.agridata.cdzhdj.activity.farm.fishery.FishMainActivity;
import com.agridata.cdzhdj.activity.harmless.CollectionMainActivity;
import com.agridata.cdzhdj.activity.farm.map.ShengZhuFanYuFeiMainActivity;
import com.agridata.cdzhdj.activity.farm.map.ShengZhuYuFeiMainActivity;
import com.agridata.cdzhdj.activity.farm.map.WhhDemoActivity;
import com.agridata.cdzhdj.activity.farm.pigbreedinginputs.PigBreedingInputsMainActivity;
import com.agridata.cdzhdj.activity.farm.pigepidemic.PigEpidemicMainActivity;
import com.agridata.cdzhdj.activity.farm.pigsqm.PigsQMActivity;
import com.agridata.cdzhdj.activity.entrycheck.check.EntryCheckActivity;
import com.agridata.cdzhdj.activity.epidemic.EpidemicHomeActivity;
import com.agridata.cdzhdj.activity.quarantine.QuarantineMenuActivity;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.RegionAdminActivity;
import com.agridata.cdzhdj.activity.harmless.shouYun.ShouYunWebViewActivity;
import com.agridata.cdzhdj.activity.harmless.whhAdnim.WhhAdnimWebViewActivity;
import com.agridata.cdzhdj.activity.harmless.xdrbind.XdrBindListActivity;
import com.agridata.cdzhdj.activity.harmless.zcsh.ZhuChangShenHeWebViewActivity;
import com.agridata.cdzhdj.activity.adapter.MainMenuAdapter;
import com.agridata.cdzhdj.base.BaseFragment;
import com.agridata.cdzhdj.data.HarmlessAnimalBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.MenuData;
import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.data.SlaughterBeiAnBean;
import com.agridata.cdzhdj.data.entrycheck.IsCheckDZHBean;
import com.agridata.cdzhdj.data.entrycheck.SlaughterHouseBean;
import com.agridata.cdzhdj.data.harmless.GetQueryByRoleBean;
import com.agridata.cdzhdj.data.harmless.RedDotsShowPromptsBean;
import com.agridata.cdzhdj.databinding.FragmentMainBinding;
import com.agridata.cdzhdj.utils.ActivityManager;
import com.agridata.cdzhdj.utils.NetworkUtils;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.agridata.util.DateTimeUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lzx.utils.RxToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainFragment extends BaseFragment<FragmentMainBinding> {
    private static final int MESSAGE_LOGIN = 1;
    private List<MenuData> mMenuDataList;
    private final static String TAG = "lzx------》";
    private MainMenuAdapter mainMenuAdapter;
    public CustomLoadingDialog mLoadingDialog;
    private String SlaughterID;
    private String SlaughterName;

    private int amount;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        LogUtil.d(TAG, "进入首页");
        initLoadingView();
        setMenuData();
        getHarAnimalInfo();
    }

    @Override
    protected void initDatas() {

    }

    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(requireActivity());
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

    private void setMenuData() {
        mMenuDataList = new ArrayList<>();
        if (BuildConfig.IS_MAP) {
            MenuData menuData = new MenuData("无害化处理", R.drawable.whh, 4);
            mMenuDataList.add(menuData);
            MenuData menuData4 = new MenuData("生猪检疫管理", R.drawable.zfjd, 8);
            mMenuDataList.add(menuData4);
            MenuData menuData5 = new MenuData("生猪养殖投入品", R.drawable.zsgl, 9);
            mMenuDataList.add(menuData5);
            MenuData menuData6 = new MenuData("渔业养殖管理", R.drawable.whh, 10);
            mMenuDataList.add(menuData6);
            MenuData menuData7 = new MenuData("生猪防疫管理", R.drawable.jylcyx, 11);
            mMenuDataList.add(menuData7);
            MenuData menuData8 = new MenuData("畜禽资源管理", R.drawable.zfjd, 12);
            mMenuDataList.add(menuData8);
            MenuData menuData9 = new MenuData("生猪繁育体系", R.drawable.rccy, 13);
            mMenuDataList.add(menuData9);
            MenuData menuData10 = new MenuData("生猪育肥体系", R.drawable.zsgl, 14);
            mMenuDataList.add(menuData10);
        } else {
            MenuData menuData = new MenuData("无害化处理", R.drawable.whh, 4);
            menuData.count = amount;
            mMenuDataList.add(menuData);
            MenuData menuData1 = new MenuData("追溯管理", R.drawable.zsgl, 5);
            mMenuDataList.add(menuData1);
            MenuData menuData3 = new MenuData("检疫受理", R.drawable.jylcyx, 7);
            mMenuDataList.add(menuData3);
            MenuData menuData2 = new MenuData("入场查验", R.drawable.rccy, 6);
            mMenuDataList.add(menuData2);
        }
        setMenuUi();
    }

    /**
     * 渔业养殖管理
     * 获取动物
     */
    private void getHarAnimalInfo() {
        HttpRequest.getHarAnimal(requireActivity(), new CallBackLis<>() {
            @Override
            public void onSuccess(String method, HarmlessAnimalBean content) {
                LogUtil.d(TAG, "获取动物" + content.toString());
                if (!content.Result.isEmpty()) {
                    AnimalSP.getInstance().setHarmlessAnimalList(content.Result);
                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }

    private void setMenuUi() {
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 3);
        binding.recyclerview.setLayoutManager(layoutManager);
        //启用嵌套滚动
        binding.recyclerview.setNestedScrollingEnabled(false);
        binding.recyclerview.setFocusableInTouchMode(false);
        mainMenuAdapter = new MainMenuAdapter(R.layout.item_menu, mMenuDataList, requireActivity(), layoutManager);
        binding.recyclerview.setAdapter(mainMenuAdapter);
        mainMenuAdapter.refreshDataList(mMenuDataList);

        mainMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                MenuData data = mainMenuAdapter.getData(position);
                if (data.id == 4) {
                    if (NetworkUtils.isNetworkAvalible(requireActivity())) {
                        getRole();
                    } else {
                        String latitude = AddressSP.getInstance().getLatitude();
                        String longitude = AddressSP.getInstance().getLongitude();
                        List<RoleBean.DataBean.UserRoleBean> userRole = RoleSP.getInstance().getRoleInfo().userRole;
                        if (!latitude.isEmpty() && !longitude.isEmpty() && !userRole.isEmpty()) {
                            showRoleDialog(userRole);
                        }
                    }
                    //CollectionMainActivity.start(requireActivity());
                } else if (data.id == 5) {
                    EpidemicHomeActivity.start(requireActivity());
                } else if (data.id == 6) {
                    getSlaughter();
                } else if (data.id == 7) {//检疫受理
                    List<LoginData.ResultBean.RolesBean> roles = UserDataSP.getInstance().getUserInfo().Result.roles;
                    List<String> list = new ArrayList<>();
                    for (LoginData.ResultBean.RolesBean role : roles) {
                        list.add(role.id);
                    }
                    if (!list.contains("4c93e19688f347aaaae6527bf4f3afea")) {
                        Objects.requireNonNull(RxToast.warning(requireActivity(), "您不是官方兽医，请联系管理员增加角色"));
                    } else {
                        QuarantineMenuActivity.start(requireActivity());
                    }

                } else if (data.id == 8) {//执法检查 Law enforcementCheck
                    PigsQMActivity.start(requireActivity());
                } else if (data.id == 9) {//生猪养殖投入品
                    PigBreedingInputsMainActivity.start(requireActivity());
                } else if (data.id == 10) {
                    FishMainActivity.start(requireActivity());
                } else if (data.id == 11) {
                    PigEpidemicMainActivity.start(requireActivity());
                } else if (data.id == 12) {
                    BeastsBirdsMainActivity.start(requireActivity());
                } else if (data.id == 13) {
                    ShengZhuFanYuFeiMainActivity.start(requireActivity());
                } else if (data.id == 14) {
                    ShengZhuYuFeiMainActivity.start(requireActivity());
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("LZX---->","onResume");
        getRoleInfo();
    }

    private void getRole() {
        showLoading("获取权限中...");
        HttpRequest.queryAuth(requireActivity(), UserDataSP.getInstance().getUserInfo().Result.userId, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, RoleBean roleBean) {
                if (roleBean.code == 500) {
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(requireActivity(), roleBean.msg));
                } else {
                    hideLoading();
                    if (!roleBean.data.userRole.isEmpty()) {
                        RoleSP.getInstance().setRoleInfo(roleBean.data);
                        showRoleDialog(roleBean.data.userRole);
                        //RoleBeanRepository.getInstance().insertUser(requireActivity(),roleBean);
                    } else {
                        Objects.requireNonNull(RxToast.warning(requireActivity(), "您当前暂无无害化角色，请联系管理员"));
                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                LogUtil.d(TAG, "error" + error);
                Objects.requireNonNull(RxToast.error(requireActivity(), error));
            }
        });
    }

    private void showRoleDialog(List<RoleBean.DataBean.UserRoleBean> roleBeanList) {
        List<String> bottomDialogContents = getStrings(roleBeanList);

        if (!bottomDialogContents.isEmpty()) {
            //1、先创建出一个和list同等长度的数组
            String[] arr = new String[bottomDialogContents.size()];
            //2、调用List的toArray()方法，数组作为参数
            bottomDialogContents.toArray(arr);

            System.out.print("arr=");
            // 这种弹窗从 1.0.0版本开始实现了优雅的手势交互和智能嵌套滚动
            new XPopup.Builder(requireActivity()).isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                    .borderRadius(22).autoDismiss(true).asBottomList("我的角色信息", arr, null, new OnSelectListener() {
                        @Override
                        public void onSelect(int position, String text) {
                            String textName = arr[position];
                            intentActivity(textName);

                        }
                    }).show();
        }
    }

    private static @NonNull List<String> getStrings(List<RoleBean.DataBean.UserRoleBean> roleBeanList) {
        List<String> bottomDialogContents = new ArrayList<>();
        for (RoleBean.DataBean.UserRoleBean userRoleBean : roleBeanList) {
            bottomDialogContents.add(userRoleBean.name);
        }
        if (BuildConfig.IS_MAP) {
            bottomDialogContents.add("无害化保险公司管理");
            bottomDialogContents.add("保险公司新增");
            bottomDialogContents.add("无害化保险公司人员管理");
            bottomDialogContents.add("人员新增");
            bottomDialogContents.add("无害化已投保收集单管理");
            bottomDialogContents.add("无害化已投保收集单推送");
            bottomDialogContents.add("屠宰场无害化申报管理");


        }
        return bottomDialogContents;
    }

    private void showCheckDialog() {
        List<String> animalTypes = new ArrayList<>();
        animalTypes.add("动物A证");
        animalTypes.add("动物B证");
        animalTypes.add("入场查验记录");
        //1、先创建出一个和list同等长度的数组
        String[] arr = new String[animalTypes.size()];
        //2、调用List的toArray()方法，数组作为参数
        animalTypes.toArray(arr);
        System.out.print("arr=");

        // 这种弹窗从 1.0.0版本开始实现了优雅的手势交互和智能嵌套滚动
        new XPopup.Builder(requireActivity()).isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .borderRadius(22).autoDismiss(true).asBottomList("动物证类别", arr, null, new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        getISCheckDZH(position);
                    }
                }).show();


    }

    private void getISCheckDZH(int position) {
        HttpRequest.getIsCheckDZH(requireActivity(), SlaughterID, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, IsCheckDZHBean content) {
                if (content.code == 200) {
                    IsCheckDZHSp.getInstance().setIsCheckDZHSp(content.data);
                    getSlaughterHouseDetails(position);
                } else {
                    RxToast.error(content.message);
                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }

    /**
     * 获取屠宰场区划
     *
     * @param position
     */
    private void getSlaughterHouseDetails(int position) {
        HttpRequest.getSlaughterHouseDetails(requireActivity(), SlaughterID, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, SlaughterHouseBean content) {
                if (content.status == 0) {
                    SlaughterHouseSp.getInstance().setSlaughterHouseInfo(content.result.region);
                    if (position == 0) {
                        EntryCheckActivity.start(requireActivity(), "A", SlaughterID, SlaughterName);
                    } else if (position == 1) {
                        EntryCheckActivity.start(requireActivity(), "B", SlaughterID, SlaughterName);
                    } else {
                        EntryCheckHisToryActivity.start(requireActivity(), SlaughterID, SlaughterName);
                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }

    /**
     * 判断
     *
     * @param text
     */
    private void intentActivity(String text) {
        switch (text) {
            case "无害化现场收运员":
                if (NetworkUtils.isNetAvailable(requireActivity())) {
                    getPermissions();
                } else {
                    CollectionMainActivity.start(requireActivity());
                }
                break;
            case "无害化处理员":
                ChuLiWebViewActivity.start(requireActivity());
                break;
            case "无害化残余物管理员":
                CanYuWuWebViewActivity.start(requireActivity());
                break;
            case "无害化管理员":
                WhhAdnimWebViewActivity.start(requireActivity());
                break;
            case "无害化现场监督员":
                   OnSiteAuditHarmlessActivity.Companion.start(requireActivity());
                //XianChangShenHeWebViewActivity.start(requireActivity());
                break;
            case "无害化驻场监督员":
                ZhuChangShenHeWebViewActivity.start(requireActivity());
                break;
            case "相对人":
                // XdrWebViewActivity.start(requireActivity(),"",1);
                //getXdrBindInfo();
                XdrBindListActivity.start(requireActivity());
                break;
            case "无害化处理场电话申报员":
                //DianHuaShenBaoWebViewActivity.start(requireActivity());
                Objects.requireNonNull(RxToast.warning(requireActivity(), "此角色暂不进行使用"));
                break;
            case "无害化辖区管理员":
                RegionAdminActivity.start(requireActivity());
                break;
            case "无害化保险公司管理":
                WhhDemoActivity.start(requireActivity(), 1);
                break;
            case "保险公司新增":
                WhhDemoActivity.start(requireActivity(), 2);
                break;
            case "无害化保险公司人员管理":
                WhhDemoActivity.start(requireActivity(), 3);
                break;
            case "人员新增":
                WhhDemoActivity.start(requireActivity(), 4);
                break;
            case "无害化已投保收集单管理":
                WhhDemoActivity.start(requireActivity(), 5);
                break;
            case "无害化已投保收集单推送":
                WhhDemoActivity.start(requireActivity(), 6);
                break;
            case "屠宰场无害化申报管理":
//                WhhDemoActivity.start(requireActivity(),7);
                ShouYunWebViewActivity.start(requireActivity());
                break;
            default:
                break;
        }

    }


    private void getPermissions() {
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.ACCESS_FINE_LOCATION).permission(Permission.ACCESS_COARSE_LOCATION).request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            if (!checkGPSIsOpen(requireActivity())) {
                                //未开启定位服务
                                //可以自己加个Dialog的对话框，效果就和美团那个一样了
                                //跳转GPS设置界面
                                showOpenGps();
                            } else {
                                initGaoDe();
                                // mHandler.sendEmptyMessageDelayed(MESSAGE_LOGIN, 800);
                            }
                        } else {
                            showOpenGps();
                            Objects.requireNonNull(RxToast.warning(requireActivity(), "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(requireActivity(), "被永久拒绝定位授权，无法使用收集功能，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(requireActivity(), permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(requireActivity(), "获取权限失败"));
                        }
                    }
                });
    }

    /**
     * 已经提交过信息了
     */
    private void showOpenGps() {
        View view = getLayoutInflater().inflate(R.layout.opne_gps_layout, null);
        final android.app.AlertDialog exitDialog = new android.app.AlertDialog.Builder(requireActivity()).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        contentTv.setText("为了您更好的使用无害化收集功能，请开启GPS定位服务");
        confirmTv.setOnClickListener(view1 -> {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 666);
            exitDialog.dismiss();
        });


        if (ActivityManager.getInstance().isLiving(requireActivity())) {
            exitDialog.show();
        }
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(requireActivity()) * 0.85);
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
            AMapLocationClient.updatePrivacyShow(requireActivity(), true, true);
            AMapLocationClient.updatePrivacyAgree(requireActivity(), true);
            mLocationClient = new AMapLocationClient(requireActivity());
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
            if (checkGPSIsOpen(requireActivity())) {
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
                Objects.requireNonNull(RxToast.success(requireActivity(), "获取定位成功"));
                //ShouYunWebViewActivity.start(requireActivity());
                CollectionMainActivity.start(requireActivity());
                break;
            default:
                break;
        }
        return true;
    });

    private void getSlaughter() {
        showLoading("查询中...");
        HttpRequest.getSlaughterBei(requireActivity(), UserDataSP.getInstance().getUserInfo().Result.mobile, new CallBackLis<>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, SlaughterBeiAnBean content) {
                LogUtil.d("lzx---------》", content.toString());
                hideLoading();
                if (content.status == 0) {
                    if (content.result.size() > 0) {
                        List<String> bottomDialogContents = new ArrayList<>();
                        for (SlaughterBeiAnBean.Result userRoleBean : content.result) {
                            bottomDialogContents.add(userRoleBean.slaughterHouseName);
                        }
                        if (bottomDialogContents.size() > 0) {
                            //1、先创建出一个和list同等长度的数组
                            String[] arr = new String[bottomDialogContents.size()];
                            //2、调用List的toArray()方法，数组作为参数
                            bottomDialogContents.toArray(arr);
                            System.out.print("arr=");
                            // 这种弹窗从 1.0.0版本开始实现了优雅的手势交互和智能嵌套滚动
                            new XPopup.Builder(requireActivity()).isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                                    .borderRadius(22).autoDismiss(true).asBottomList("屠宰场列表", arr, null, (position, text) -> {
                                        String textName = arr[position];
                                        for (SlaughterBeiAnBean.Result userRoleBean : content.result) {
                                            if (textName.equals(userRoleBean.slaughterHouseName)) {
                                                SlaughterID = userRoleBean.slaughterMid;
                                                SlaughterName = userRoleBean.slaughterHouseName;
                                            }
                                        }
                                        showCheckDialog();
                                    }).show();
                        }
                    } else {

                        showBindDialog();
                        //Objects.requireNonNull(RxToast.warning(requireActivity(), "当前暂无关联的屠宰场备案"));
                    }

                } else {
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(requireActivity(), content.message));
                }

            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull(RxToast.error(requireActivity(), error));
            }
        });
    }

    private void showBindDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        AlertDialog exitDialogLoginOut = new AlertDialog.Builder(requireActivity()).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView title_tv = view.findViewById(R.id.title_tv);
        title_tv.setText("绑定屠宰场");
        contentTv.setText("您当前暂无绑定屠宰场，是否去绑定？");

        cancelTv.setOnClickListener(view12 -> exitDialogLoginOut.dismiss());
        confirmTv.setOnClickListener(v -> {
            exitDialogLoginOut.dismiss();
            BindSlaughterhouseActivity.start(requireActivity());
        });
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(requireActivity()) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }


    private void getRoleInfo() {
        HttpRequest.queryAuth(requireActivity(), UserDataSP.getInstance().getUserInfo().Result.userId, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, RoleBean roleBean) {
                if (roleBean.code == 200) {
                    if (!roleBean.data.userRole.isEmpty()) {
                        RoleSP.getInstance().setRoleInfo(roleBean.data);
                        List<LoginData.ResultBean.RolesBean> roles = UserDataSP.getInstance().getUserInfo().Result.roles;
                        if (!roles.isEmpty()) {
                            List<String> roleList = new ArrayList<>();
                            for (LoginData.ResultBean.RolesBean resultBean : roles) {
                                roleList.add(resultBean.id);
                            }
                            if (roleList.contains("20bd5e670a974e6b81c090b5c2b07cb5")) {//无害化现场收运员
                                getRedMessageTips(1);
                                LogUtil.d("lzx----》", "无害化现场收运员");
                            } else if (roleList.contains("b7189dc965c44daa848c21bfcfa843d3")) {//无害化现场监督员
                                getRedMessageTips(2);
                                LogUtil.d("lzx----》", "无害化现场监督员");
                            }
//                            else if (roleList.contains("7b3b75a98c494b259c72e906d54898ec")){//无害化处理员
//                                getRedMessageTips(3);
//                                LogUtil.d("lzx----》","无害化处理员");
//                            }else if (roleList.contains("98fc659c395f4ca491cc9bca04f8e260")){//无害化处理员
//                                getRedMessageTips(4);
//                                LogUtil.d("lzx----》","无害化驻场监督员");
//                            }
//                            if (roleList.contains("7b3b75a98c494b259c72e906d54898ec") || roleList.contains("20bd5e670a974e6b81c090b5c2b07cb5") || roleList.contains("b7189dc965c44daa848c21bfcfa843d3") || roleList.contains("98fc659c395f4ca491cc9bca04f8e260")) {
//                                getRedMessageTips();
//                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {
                LogUtil.d(TAG, "error" + error);
                //Objects.requireNonNull(RxToast.error(requireActivity(), error));
            }
        });
    }


    private void getRedMessageTips(int type) {
        GetQueryByRoleBean getQueryByRoleBean = new GetQueryByRoleBean();
        if (type == 1) {
            getQueryByRoleBean.queryType = 1;
            getQueryByRoleBean.startTime = DateTimeUtils.getSevenDaysAgoTime() + " " + "00:00:00";
            getQueryByRoleBean.endTime = DateTimeUtils.getCurrentTime() + " " + "23:59:59";
            List<String> list = new ArrayList<>();
            for (RoleBean.DataBean.HarmlessRegionBean harmlessRegionBean : RoleSP.getInstance().getRoleInfo().shouYunRegion) {
                list.add(String.valueOf(harmlessRegionBean.id));
            }
            getQueryByRoleBean.regionIdList = String.join(",", list);
        } else if (type == 2) {
            getQueryByRoleBean.queryType = 2;
            getQueryByRoleBean.startTime = DateTimeUtils.getSevenDaysAgoTime() + " " + "00:00:00";
            getQueryByRoleBean.endTime = DateTimeUtils.getCurrentTime() + " " + "23:59:59";
            List<String> list = new ArrayList<>();
            for (LoginData.ResultBean.DependencyBean.DepPermissionInfoMidBean.HarmlessRegionBean harmlessRegionBean : UserDataSP.getInstance().getUserInfo().Result.dependency.Dep_PermissionInfoMid.HarmlessRegion) {
                list.add(String.valueOf(harmlessRegionBean.id));
            }
            getQueryByRoleBean.regionIdList = String.join(",", list);
        } else if (type == 3) {
            getQueryByRoleBean.queryType = 3;
            getQueryByRoleBean.CollectType = 1;
            getQueryByRoleBean.startTime = DateTimeUtils.getSevenDaysAgoTime() + " " + "00:00:00";
            getQueryByRoleBean.endTime = DateTimeUtils.getCurrentTime() + " " + "23:59:59";
            getQueryByRoleBean.factoryGUID = RoleSP.getInstance().getRoleInfo().harmlessUser.factory.Mid;
        } else if (type == 4) {
            getQueryByRoleBean.queryType = 4;
            getQueryByRoleBean.startTime = DateTimeUtils.getSevenDaysAgoTime() + " " + "00:00:00";
            getQueryByRoleBean.endTime = DateTimeUtils.getCurrentTime() + " " + "23:59:59";
            getQueryByRoleBean.factoryGUID = RoleSP.getInstance().getRoleInfo().harmlessUser.factory.Mid;
        }
        HttpRequest.getQueryByRole(requireActivity(), getQueryByRoleBean, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, RedDotsShowPromptsBean content) {
                if (content.code == 200) {
                    LogUtil.d("lzx-------->", "SETP------------3");
                    amount = content.data.amount;
                    MenuData menuData = mMenuDataList.get(0);
                    menuData.count = amount;
                    mainMenuAdapter.refreshDataList(mMenuDataList);

                } else {
                    Objects.requireNonNull(RxToast.error(requireActivity(), content.msg));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(requireActivity(), error));
            }
        });
    }
}