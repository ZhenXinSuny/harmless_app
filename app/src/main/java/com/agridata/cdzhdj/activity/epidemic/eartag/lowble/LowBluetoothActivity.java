package com.agridata.cdzhdj.activity.epidemic.eartag.lowble;

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
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.BleSp;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.LowBleData;
import com.agridata.cdzhdj.databinding.ActivityLowbleToothBinding;
import com.agridata.cdzhdj.utils.AppConstants;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.utils.LogUtil;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lzx.utils.RxToast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-22 16:43.
 * @Description :描述
 */
public class LowBluetoothActivity extends BaseActivity<ActivityLowbleToothBinding> implements LowScanEarTagInfoAdapter.onEarTagDelListener {
    public static final int SEARCH_DEVICE_CODE = 100;
    private AlertDialog exitDialog;
    private  LowScanEarTagInfoAdapter lowScanEarTagInfoAdapter;
    private BluetoothAdapter mBluetooth;

    private Vibrator mVibrator;
    private SoundPool soundPool;
    long[] pattern = {100, 400, 100, 400}; // 停止 开启 停止 开启
    private int mOpenCode = 666;

    private String address;
    private CustomLoadingDialog  mLoadingDialog;

    private BluetoothSocket btSocket1 = null;

    private List<LowBleData> blueDeviceList = new ArrayList<>();

    public static final int MESSAGE_CONNECT = 6;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_CONNECT_SUCCEED = 7;
    public static final int MESSAGE_CONNECT_LOST = 8;
    public static final int MESSAGE_RECV = 10;
    public static final int MESSAGE_EXCEPTION_RECV = 11;

    private Boolean bConnect = false;
    private InputStream mmInStream;
    private OutputStream mmOutStream;
    @Override
    protected ActivityLowbleToothBinding getBinding() {
        return ActivityLowbleToothBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lowScanEarTagInfoAdapter = new LowScanEarTagInfoAdapter(R.layout.item_eartag, this);
        lowScanEarTagInfoAdapter.setmOnEarTagDelListener(this);
        binding.recyclerview.setAdapter(lowScanEarTagInfoAdapter);
        initBluetooth();


        binding.duankaiTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect();
            }
        });

        binding.commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgeEartag();
            }
        });
    }

    @Override
    protected void initDatas() {
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.beep, 1);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(connectDevices, filter);





        setBleOpenPer();

    }
    private BroadcastReceiver connectDevices = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {

            } else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                hideLoading();
               // RxToast.warning(LowBluetoothActivity.this, "请检查设备...");
            }
        }
    };


    public void initialize() {
        address = BleSp.getInstance().getLowBle();
        address="";
        LogUtil.d("lzx-------》", address + " 低功耗蓝牙 ");
        if (!TextUtils.isEmpty(address)) {
            showLoading("正在连接设备...");
            mHandler.sendEmptyMessageDelayed(MESSAGE_CONNECT, 1000);
        } else {
            Intent intent = new Intent(LowBluetoothActivity.this, ConnBlueToothActivity.class);
            startActivityForResult(intent, SEARCH_DEVICE_CODE);
        }
//        timer.schedule(new MyTask(), 0);
    }
    @SuppressLint("MissingPermission")
    private  void queryBle(){
        mBluetooth.startDiscovery();//来时执行搜索功能
    }
    @Override
    public void onDel(int pos) {

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
            Objects.requireNonNull(RxToast.warning(LowBluetoothActivity.this, "本机未找到蓝牙功能"));
        }
    }
    private void setBleOpenPer() {
        XXPermissions.with(this)
                .permission(Permission.ACCESS_FINE_LOCATION)
                .permission(Permission.ACCESS_COARSE_LOCATION)
                // 申请多个权限
                .permission(Permission.Group.BLUETOOTH)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            Objects.requireNonNull(RxToast.info(LowBluetoothActivity.this, "权限获取成功"));

                            if (!mBluetooth.isEnabled()) {
                                showOpenBleDialog();
                            } else {
                                initialize();
                            }


                        } else {
                            Objects.requireNonNull(RxToast.warning(LowBluetoothActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(LowBluetoothActivity.this, "被永久拒绝授权，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(LowBluetoothActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(LowBluetoothActivity.this, "获取权限失败"));
                        }
                    }
                });
    }

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666) {//来自允许蓝牙扫描的对话框
            //延迟50毫秒后启动蓝牙设备的刷新任务
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initialize();
                }
            }, 50);
        } else  if (requestCode == SEARCH_DEVICE_CODE){
            if (resultCode == ConnBlueToothActivity.CONNECT_DEVICE_OK) {
                //initialize();
                address = BleSp.getInstance().getLowBle();
                showLoading("正在连接设备...");
                mHandler.sendEmptyMessageDelayed(MESSAGE_CONNECT, 1000);
                LogUtil.d("lzx---->", "连接返回，开始扫面。。。。");
            }
        }
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
    // Hander
    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_CONNECT:
                    new Thread(new Runnable() {
                        @SuppressLint("MissingPermission")
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
                                LogUtil.d("lz----》",e.toString());
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
                    binding.bleLayout.setVisibility(View.VISIBLE);
                    binding.bleTitle.setText("手持设备连接成功...");
                    LogUtil.d("lzx----->", "手持设备连接成功...");

                   // showLoading("开始扫描耳标...");
                    new Thread(new Runnable() {
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
                                        if (nPacket[52 * t] == 0)
                                            t = t - 1;
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
                        RxToast.error(LowBluetoothActivity.this, "设备已断开...");
                        disconnect();
//                        if (lowScanEarTagInfoAdapter.getDataList().size() > 0) {
//                            Intent intent = new Intent();
//                            intent.putStringArrayListExtra("eartaglist", (ArrayList<String>) lowScanEarTagInfoAdapter.getDataList());
//                            setResult(RESULT_OK, intent);
//                        }
                       // finish();
                    }
                    break;
                case MESSAGE_CONNECT_LOST:
                    hideLoading();
                    RxToast.warning(LowBluetoothActivity.this, "蓝牙连接失败，请检查设备是否打开并进行重新连接...");
                    disconnect();
//                    if (lowScanEarTagInfoAdapter.getDataList().size() > 0) {
//                        Intent intent = new Intent();
//                        intent.putStringArrayListExtra("eartaglist", (ArrayList<String>) lowScanEarTagInfoAdapter.getDataList());
//                        setResult(RESULT_OK, intent);
//                    }
                    finish();
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

                    if (bBuf[1] == 0x01)
                        bRef[1] = (byte) 0x81;
                    else if (bBuf[1] == 0x02)
                        bRef[1] = (byte) 0x82;
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
                        RxToast.warning(LowBluetoothActivity.this, "请扫描指定耳标...");
                    } else {
                        if (binaryCode.matches("^[0-9_]+$")) {
                            //判断扫描耳标是否与动物类型匹配 TODO
                            AddEartagNumTolistView(binaryCode);
                        }
                    }
                    break;
            }
        }
    };
    public void AddEartagNumTolistView(String eartagNo) {
        if (!lowScanEarTagInfoAdapter.getDataList().contains(eartagNo)) {
            lowScanEarTagInfoAdapter.addData(eartagNo);
            lowScanEarTagInfoAdapter.notifyDataSetChanged();
            int nCount = lowScanEarTagInfoAdapter.getDataList().size();
            binding.earTagNum.setText(nCount +"");
            mVibrator.vibrate(pattern, -1);
            soundPool.play(1, 1, 1, 0, 0, 1);
        }else {
            RxToast.warning(LowBluetoothActivity.this,"该耳标已存在,请不要重复扫描");
        }
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
        binding.bleLayout.setVisibility(View.GONE);
    }

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
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(
                    hexString.substring(i, i + 1), 16));
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
    private void release() {
        try {
            Thread.sleep(500);
            if (btSocket1 != null) {
                btSocket1.close();
            }
            unregisterReceiver(connectDevices);
            hideLoading();
            soundPool.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            judgeEartag();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void judgeEartag() {
        hideLoading();
        if (lowScanEarTagInfoAdapter.getDataList().size() > 0) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra("eartaglist", (ArrayList<String>) lowScanEarTagInfoAdapter.getDataList());
            setResult(RESULT_OK, intent);
            LowBluetoothActivity.this.finish();
            LogUtil.d("lzx---->", "有值返回");
        } else {
            showOpenNoDataDialog();//返回
            LogUtil.d("lzx---->", "弹框");
        }
    }

    private void showOpenNoDataDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = (TextView) view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        contentTv.setText("您还未添加耳标，确认返回？");

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();

            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
                LowBluetoothActivity.this.finish();
            }
        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }


    @Override
    protected void onDestroy() {
        try {
            release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
