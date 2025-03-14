package com.agridata.cdzhdj.activity.harmless;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.harmless.collection.ApplyActivity;
import com.agridata.cdzhdj.activity.harmless.record.RecordMainActivity;
import com.agridata.cdzhdj.activity.harmless.statistic.StatisticsActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.MenuData;
import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.db.bean.ApplyDBean;
import com.agridata.cdzhdj.data.harmless.BindCarBean;
import com.agridata.cdzhdj.data.harmless.CarNumberBean;
import com.agridata.cdzhdj.data.harmless.GetQueryByRoleBean;
import com.agridata.cdzhdj.data.harmless.RedDotsShowPromptsBean;
import com.agridata.cdzhdj.databinding.ActivityCollectionMainBinding;
import com.agridata.cdzhdj.utils.NetworkStateReceiver;
import com.agridata.cdzhdj.utils.NetworkUtils;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.utils.ToastUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.agridata.util.DateTimeUtils;
import com.lxj.xpopup.XPopup;
import com.lzx.utils.RxToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-26 10:59.
 * @Description :描述
 */
public class CollectionMainActivity extends BaseActivity<ActivityCollectionMainBinding> implements NetworkStateReceiver.NetworkStateChangedListener {
    private List<MenuData> mMenuDataList;
    private CollectionMainMenuAdapter mainMenuAdapter;
    private CustomLoadingDialog mLoadingDialog;
    private final static String TAG = "CollectionMainActivity------》";

    private List<CarNumberBean.ResultBean> listCar = new ArrayList<>();
    private ApplyDBean applyDBean;


    public static void start(Context context) {
        Intent intent = new Intent(context, CollectionMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ActivityCollectionMainBinding getBinding() {
        return ActivityCollectionMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.titlebarLeft.setImageResource(R.drawable.title_back);
        binding.titlebarLeft.setOnClickListener(v -> finish());
        initLoadingView();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.gridRecyclerview.setLayoutManager(layoutManager);
        mainMenuAdapter = new CollectionMainMenuAdapter(R.layout.item_collection_main, mMenuDataList, this, layoutManager);
        binding.gridRecyclerview.setAdapter(mainMenuAdapter);
        binding.titlebarLeft.setOnClickListener(v -> finish());


        binding.applyLl.setOnClickListener(view -> {
            if (RoleSP.getInstance().getRoleInfo().carInfo==null){
                Objects.requireNonNull(RxToast.warning(CollectionMainActivity.this, "您当前暂未绑定车辆信息，请绑定车辆信息在进行申报收集"));
                return;
            }
            String carName = RoleSP.getInstance().getRoleInfo().carInfo.Name;
            if (!StrUtil.isEmpty(carName)) {
                showCarDialog(carName, 1);
            } else {
                showCarDialog(carName, 2);
            }

        });

        binding.sjLl.setOnClickListener(v -> {
            RecordMainActivity.start(CollectionMainActivity.this);
        });
        mainMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                if (mainMenuAdapter.getData(position).id == 1) {
                    if (NetworkUtils.isNetAvailable(CollectionMainActivity.this)) {
                        StatisticsActivity.start(CollectionMainActivity.this, 1);
                    } else {
                        Objects.requireNonNull(RxToast.info(CollectionMainActivity.this, "请检查网络设置，在进行操作"));
                    }


                } else {
                    if (NetworkUtils.isNetAvailable(CollectionMainActivity.this)) {
                        StatisticsActivity.start(CollectionMainActivity.this, 2);
                    } else {
                        Objects.requireNonNull(RxToast.info(CollectionMainActivity.this, "请检查网络设置，在进行操作"));
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });


        if (RoleSP.getInstance().getRoleInfo().carInfo==null){
            binding.carNameTv.setText("暂无绑定车辆");
        }else {
            if (!StrUtil.isEmpty(RoleSP.getInstance().getRoleInfo().carInfo.Name)) {
                binding.carNameTv.setText(RoleSP.getInstance().getRoleInfo().carInfo.Name);
            } else {
                binding.carNameTv.setText("暂无绑定车辆");
            }
        }

        binding.carNameTv.setOnClickListener(view -> {
            if (NetworkUtils.isNetAvailable(CollectionMainActivity.this)) {
                getCarInfo();
            } else {
                Objects.requireNonNull(RxToast.warning(CollectionMainActivity.this, "请检查网络设置，在进行操作"));
            }

        });

        if (NetworkUtils.isNetAvailable(CollectionMainActivity.this)) {
            binding.noNetworkLayout.noNetworkTipLl.setVisibility(View.GONE);
            getRedMessageTips();
        } else {
            binding.noNetworkLayout.noNetworkTipLl.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void initDatas() {
        setMenuData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        NetworkStateReceiver.getInstance().addNetworkStateChangedListener(this); //添加网络监听器
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onNetworkStateChanged(boolean isNetAvailable) {
        if (isNetAvailable) {
            binding.noNetworkLayout.noNetworkTipLl.setVisibility(View.GONE);
        } else {
            binding.noNetworkLayout.noNetworkTipLl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkStateReceiver.getInstance().removeNetworkStateChangedListener(this); //添加网络监听器
    }

    private void setMenuData() {
        mMenuDataList = new ArrayList<>();
        MenuData menuData = new MenuData("病死动物统计", R.drawable.ic_apply_animial_tj, 1);
        mMenuDataList.add(menuData);
        MenuData menuData1 = new MenuData("申报单数统计", R.drawable.ic_apply_tj, 2);
        mMenuDataList.add(menuData1);
        mainMenuAdapter.refreshDataList(mMenuDataList);
    }


    private void showCarDialog(String carName, int type) {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        AlertDialog exitDialogLoginOut = new AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(exitDialogLoginOut.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView title_tv = view.findViewById(R.id.title_tv);
        title_tv.setVisibility(View.VISIBLE);
        title_tv.setText("出车提示");
        if (!StrUtil.isEmpty(carName)) {
            contentTv.setText("您当前绑定的车辆为" + carName + "是否出车？如需换绑请点击右上角进行车辆操作");
        } else {
            contentTv.setText("您当前暂无绑定车辆，请点击右上角进行车辆绑定");
        }
        cancelTv.setOnClickListener(view12 -> exitDialogLoginOut.dismiss());
        confirmTv.setOnClickListener(v -> {
            if (type == 1) {
                ApplyActivity.start(this);
            }
            exitDialogLoginOut.dismiss();
        });
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }


    private void getCarInfo() {
        showLoading("获取车辆中...");
        HttpRequest.getCarNumberInfo(CollectionMainActivity.this, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, CarNumberBean content) {
                LogUtil.d("lzx---------》", content.toString());
                hideLoading();
                if (!content.result.isEmpty()) {
                    listCar = content.result;
                    showCarPop(content);
                } else {
                    Objects.requireNonNull(RxToast.warning(CollectionMainActivity.this, "当前处理厂下暂无车辆信息"));
                }


            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                ToastUtil.showToast(CollectionMainActivity.this, error);

            }
        });
    }

    private void showCarPop(CarNumberBean content) {

        List<String> carList = new ArrayList<>();
        for (CarNumberBean.ResultBean resultBean : content.result) {
            carList.add(resultBean.carNo);
        }
       LogUtil.i("lzx-----》车辆的数量", carList.size() +"");
        String[] arr = new String[content.result.size()];
        carList.toArray(arr);
        new XPopup.Builder(CollectionMainActivity.this).maxHeight(1200).isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .borderRadius(22).autoDismiss(true).asBottomList("车辆选择", arr, (position, text) -> {
                    showBindCar(arr[position]);

                }).show();
    }


    private void showBindCar(String carName) {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        AlertDialog exitDialogLoginOut = new AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView title_tv = view.findViewById(R.id.title_tv);
        title_tv.setVisibility(View.VISIBLE);
        title_tv.setText("出车绑定");
        contentTv.setText("是否选择当前车辆出车，车牌号为" + carName);
        cancelTv.setOnClickListener(view12 -> exitDialogLoginOut.dismiss());
        confirmTv.setOnClickListener(v -> {
            updateCar(carName);

            exitDialogLoginOut.dismiss();
        });
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }


    private void updateCar(String carName) {
        showLoading("出车中...");
        BindCarBean bindCarBean = new BindCarBean();
        bindCarBean.Mid = RoleSP.getInstance().getRoleInfo().harmlessUser.mid;
        bindCarBean.CarInfo = new BindCarBean.CarInfoBean();
        bindCarBean.CarInfo.LimitTime = DateTimeUtils.getCurrentTime();
        for (CarNumberBean.ResultBean resultBean : listCar) {
            if (carName.equals(resultBean.carNo)) {
                bindCarBean.CarInfo.ID = resultBean.mid;
                bindCarBean.CarInfo.Name = resultBean.carNo;
            }

        }
        HttpRequest.upDataCar(CollectionMainActivity.this, bindCarBean, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                hideLoading();
                Objects.requireNonNull(RxToast.success(CollectionMainActivity.this, "出车成功"));
                binding.carNameTv.setText(carName);
                getRole();
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                ToastUtil.showToast(CollectionMainActivity.this, error);

            }
        });
    }


    private void getRole() {
        HttpRequest.queryAuth(CollectionMainActivity.this, UserDataSP.getInstance().getUserInfo().Result.userId, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, RoleBean roleBean) {
                if (roleBean.code == 500) {
                    Objects.requireNonNull(RxToast.error(CollectionMainActivity.this, roleBean.msg));
                } else {
                    if (!roleBean.data.userRole.isEmpty()) {
                        RoleSP.getInstance().setRoleInfo(roleBean.data);
                    } else {
                        Objects.requireNonNull(RxToast.warning(CollectionMainActivity.this, "您当前暂无无害化角色，请联系管理员"));
                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(CollectionMainActivity.this, error));
            }
        });
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

    private void getRedMessageTips() {
        GetQueryByRoleBean getQueryByRoleBean = new GetQueryByRoleBean();
        getQueryByRoleBean.queryType = 1;
        getQueryByRoleBean.startTime = DateTimeUtils.getSevenDaysAgoTime() + " " + "00:00:00";
        getQueryByRoleBean.endTime = DateTimeUtils.getCurrentTime() + " " + "23:59:59";
        List<String> list = new ArrayList<>();
        for (RoleBean.DataBean.HarmlessRegionBean harmlessRegionBean : RoleSP.getInstance().getRoleInfo().shouYunRegion) {
            list.add(String.valueOf(harmlessRegionBean.id));
        }
        getQueryByRoleBean.regionIdList = String.join(",", list);
        HttpRequest.getQueryByRole(CollectionMainActivity.this, getQueryByRoleBean, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, RedDotsShowPromptsBean content) {
                if (content.code == 200) {
                    LogUtil.d("lzx-------->","SETP------------3");
                    if (content.data.amount>0){
                        binding.messageTv.setVisibility(View.VISIBLE);
                        binding.messageTv.setText(content.data.amount +"");
                    }else {
                        binding.messageTv.setVisibility(View.GONE);
                    }



                } else {
                    Objects.requireNonNull(RxToast.error(CollectionMainActivity.this, content.msg));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(CollectionMainActivity.this, error));
            }
        });
    }
}
