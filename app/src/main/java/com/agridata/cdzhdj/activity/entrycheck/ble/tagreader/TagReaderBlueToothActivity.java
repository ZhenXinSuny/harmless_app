package com.agridata.cdzhdj.activity.entrycheck.ble.tagreader;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.BleSp;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.databinding.ActivityTagreaderBluetoothBinding;
import com.agridata.cdzhdj.utils.SetTypeUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.lzx.utils.RxToast;
import com.org.smartbluekit.BlueDevice;
import com.org.smartbluekit.BlueManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther 56454
 * @date 2022/1/10
 * @time 8:49.
 */
public class TagReaderBlueToothActivity extends BaseActivity<ActivityTagreaderBluetoothBinding> implements View.OnClickListener,BlueManager.BlueManagerCallback{

    public static int CONNECT_DEVICE_OK = 1011;
    private TagReaderBlueToothAdapter tagReaderBlueToothAdapter;
    private ArrayList<String> myStrings1 = new ArrayList<String>();
    private static BluetoothAdapter mBluetoothAdapter = null;
    private BlueManager mBlueManager=null;
    private CustomLoadingDialog mLoadingDialog;
    private boolean isConnected=false;

    private BlueDevice cunnectDevice=null;

    @Override
    protected ActivityTagreaderBluetoothBinding getBinding() {
        return ActivityTagreaderBluetoothBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.addTv.setOnClickListener(this);
        //获取蓝牙管理器
        //Get the bluetooth manager.
        mBlueManager= BlueManager.getInstance(this);
        //注册观察者
        //Registered observer
        mBlueManager.registerObserver(this);

        SetTypeUtils.setType(2);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        tagReaderBlueToothAdapter = new TagReaderBlueToothAdapter(R.layout.item_low_ble_address, this);
        binding.recyclerview.setAdapter(tagReaderBlueToothAdapter);


        tagReaderBlueToothAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                BlueDevice device = tagReaderBlueToothAdapter.getData(position);
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    RxToast.warning(TagReaderBlueToothActivity.this, "不支持蓝牙设备");

                }
                if(tagReaderBlueToothAdapter.getData(position).getDeviceType().equals("TagReader")){
//                    //链接
//                    if (isConnected&&cunnectDevice!=device){
//                        // if you need Connect multiple devices , Here you can not disconnect
//                        mBlueManager.disconnectDevice(cunnectDevice);
//                    }
//                    mBlueManager.connectDevice(device);
                    mRxManager.post("device",device);
                    Intent intent = new Intent();
                    setResult(CONNECT_DEVICE_OK, intent);
                    TagReaderBlueToothActivity.this.finish();
                    BleSp.getInstance().setTagReaderBle(device.getDeviceMAC());

                }else{
                    RxToast.warning(TagReaderBlueToothActivity.this, "请添加指定设备...");
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });

        new Handler().postDelayed(() -> startScan(),1000);

    }

    @Override
    protected void initDatas() {

    }

    private void startScan(){
        showLoading("正在搜索中...");
        mBlueManager.scanForDevice(BlueManager.BFM|BlueManager.LET);
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
    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View v) {
        if (v == binding.addTv) {
            if (mBluetoothAdapter == null) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            if (mBluetoothAdapter == null) {
                RxToast.warning(TagReaderBlueToothActivity.this, "不支持蓝牙设备");
                return;
            }
           /*开始扫描周边蓝牙设备，并设置过滤，过滤格式如 BFM|LET
                        mBlueManager.scanForDevice(BlueManager.BFM|BlueManager.LET);
                若只需要获取 BFM 型号，则 if only need BFM :
                        mBlueManager.scanForDevice(BlueManager.BFM);
                BFM是背膘测定仪 支持 backfat redaer 和 tag reader； BFM support backfat/tag reader
                LET是耳标测定仪 支持 tag reader; LET support tag reader
                */
            hideLoading();
            startScan();
        }
    }

    /**
     * 检查蓝牙：是否支持蓝牙及是否打开
     * @param available true为打开
     */
    @Override
    public void onManagerBLEAvailable(boolean available) {
//        if (!available){
//            mBlueManager.openBleSetting(TagReaderBlueToothActivity.this);
//        }
    }

    /**
     * 获取周边蓝牙设备
     * @param devices 设备列表（BlueDevice类型）
     */
    @Override
    public void onManagerDevicesFound(List<BlueDevice> devices) {
        Log.d("lzx-----》", "onManagerDevicesFound: " + devices.size() + devices.toString());
        if (devices.size()==0){
            hideLoading();
            RxToast.info(TagReaderBlueToothActivity.this, "请再次搜索设备");
            return;
        }
        tagReaderBlueToothAdapter.refreshDataList(devices);
        for (BlueDevice device : devices) {
            if (device.getDeviceType().equals("TagReader")){
              hideLoading();
              return;
            }
        }
    }

    /**
     * 连接设备成功
     * @param blueDevice 对应设备
     */
    @Override
    public void onManagerDeviceConnected(BlueDevice blueDevice) {
        isConnected=true;
        cunnectDevice=blueDevice;

    }

    /**
     * 连接设备失败
     * @param device 对应设备
     */
    @Override
    public void onManagerDeviceConnectFailed(BlueDevice device) {
        //ToastUtil.showToast(TagReaderBlueToothActivity.this,"连接设备失败，请重试...");
        RxToast.error(TagReaderBlueToothActivity.this, "连接设备失败，请重试...");
    }


    /**
     * 连接设备断开
     * @param device 对应设备
     */
    @Override
    public void onManagerDeviceDisconnected(final BlueDevice device) {
        isConnected=false;

    }

    /**
     * 连接设备的扫描状态
     * @param device 对应设备
     * @param state 当前状态
     */
    @Override
    public void onScanStateWithDevice(BlueDevice device, String state) {

        Log.d("lzx----》", "onScanStateWithDevice: ***********"+state);
    }

    /**
     * 连接设备读取背膘数据
     * @param device 当前设备
     * @param backfat 背膘数据（格式：“厚度/层数”）
     */
    @Override
    public void onBackFatWithDevice(BlueDevice device, final String backfat) {


    }


    /**
     * 连接设备读取耳标数据
     * @param device 当前设备
     * @param eartag 电子耳号
     */
    @Override
    public void onEarTagWithDevice(BlueDevice device, final String eartag) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //注销观察者
        //Deregister observer
        mBlueManager.deregisterObserver(this);
        //取消扫描
        //Cancel scan
        mBlueManager.cancelScan();
        /**
         * 如果是多连接则：断开所有已连接设备
         * mBlueManager.diconnectAllDevice(devices);
         */


    }
}
