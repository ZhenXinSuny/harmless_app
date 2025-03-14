package com.agridata.cdzhdj.activity.epidemic.eartag.lowble;

import static android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_FINISHED;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.BleSp;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.LowBleData;
import com.agridata.cdzhdj.databinding.ActivityConnBluetoothBinding;
import com.agridata.cdzhdj.utils.AppConstants;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.utils.LogUtil;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lzx.utils.RxToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-22 17:23.
 * @Description :描述
 */
public class ConnBlueToothActivity extends BaseActivity<ActivityConnBluetoothBinding> {

    private static BluetoothAdapter mBluetoothAdapter = null;
    private List<LowBleData> lowBleDataList = new ArrayList<>();
    private ConnBlueToothAdapter connBlueToothAdapter;
    private static final int MESSAGE_ADD = 6;
    private CustomLoadingDialog mLoadingDialog;
    public static int CONNECT_DEVICE_OK = 1011;


    private ArrayList<String> myStrings1 = new ArrayList<String>();
    private ArrayAdapter<String> mAdapter;
    private HashMap<String, String> map1 = new HashMap<String, String>();
    @Override
    protected ActivityConnBluetoothBinding getBinding() {
        return ActivityConnBluetoothBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        initBluetooth();

        binding.titlebarLeft.setOnClickListener(view -> finish());
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        connBlueToothAdapter = new ConnBlueToothAdapter(R.layout.item_low_ble_address, this);
        binding.recyclerview.setAdapter(connBlueToothAdapter);

        setBleOpenPer();


        binding.addTv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                map1.clear();
                myStrings1.clear();
                lowBleDataList.clear();
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();
                }

                mBluetoothAdapter.startDiscovery();//来时执行搜索功能
            }
        });

        connBlueToothAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                if(myStrings1.get(position).contains("RFID-BT") || myStrings1.get(position).contains("BIO-TAG")){
//
                    RxToast.success(ConnBlueToothActivity.this,"添加设备中...");


                    UUID uuid = UUID.fromString(AppConstants.SPP_UUID);
                    BluetoothDevice btDev = mBluetoothAdapter.getRemoteDevice(map1.get(myStrings1.get(position)));
                    BluetoothSocket btSocket1;
                    try {
                        btSocket1 = btDev.createInsecureRfcommSocketToServiceRecord(uuid);
                        btSocket1.connect();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    BleSp.getInstance().setLowBle(map1.get(myStrings1.get(position)));

                    try {
                        if (btSocket1 != null) {
                            btSocket1.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Intent intent = new Intent();
                    setResult(CONNECT_DEVICE_OK, intent);
                    ConnBlueToothActivity.this.finish();


                }else{
                    RxToast.warning(ConnBlueToothActivity.this,"请添加指定设备...");
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });

    }
    /**
     * 初始化蓝牙适配器
     */
    private void initBluetooth() {
        //Android从4.3开始增加支持BLE技术（即蓝牙4.0以上版本）
        //从系统服务中获取蓝牙管理器
        BluetoothManager bm = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bm.getAdapter();
        if (mBluetoothAdapter == null) {
            Objects.requireNonNull(RxToast.warning(ConnBlueToothActivity.this, "本机未找到蓝牙功能"));
        }
    }
    private void setBleOpenPer() {
        XXPermissions.with(this)
                .permission(Permission.ACCESS_FINE_LOCATION)
                .permission(Permission.ACCESS_COARSE_LOCATION)
                // 申请多个权限
                .permission(Permission.Group.BLUETOOTH)
                .request(new OnPermissionCallback() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            mBluetoothAdapter.startDiscovery();//来时执行搜索功能
                        } else {
                            Objects.requireNonNull(RxToast.warning(ConnBlueToothActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(ConnBlueToothActivity.this, "被永久拒绝授权，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(ConnBlueToothActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(ConnBlueToothActivity.this, "获取权限失败"));
                        }
                    }
                });
    }
    @Override
    protected void initDatas() {
        Intent discoverIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 10);
        IntentFilter discoverFilter = new IntentFilter();
        discoverFilter.addAction(BluetoothDevice.ACTION_FOUND);
        discoverFilter.addAction(ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, discoverFilter);
    }
    // 创建一个接收ACTION_FOUND广播的BroadcastReceiver
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        @Override
        public void onReceive(Context context, Intent intent) {
            showLoading("扫描设备...");
            String action = intent.getAction();
            // 发现设备
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 从Intent中获取设备对象
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 将设备名称和地址放入array mAdapter，以便在ListView中显示
                if (device != null) {
                    String deviceName = device.getName();
                    LogUtil.d("lzx------》", deviceName + "deviceName");
                    if (!TextUtils.isEmpty(deviceName)) {
                        if (!map1.containsKey(deviceName)) {
                            myStrings1.add(deviceName);
                            LowBleData lowBleData = new LowBleData();
                            lowBleData.setAddress(deviceName);
                            lowBleDataList.add(lowBleData);
                            connBlueToothAdapter.refreshDataList(lowBleDataList);
                        }
                        map1.put(device.getName(), device.getAddress());
                    }
                }
            }else if (ACTION_DISCOVERY_FINISHED.equals(action)){
                            hideLoading();
                LogUtil.d("lzx----->" ,"扫描借宿");
                RxToast.info(ConnBlueToothActivity.this,"扫描结束~");
            }

        }
    };

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

    @SuppressLint("MissingPermission")
    @Override
    protected void onDestroy() {
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
