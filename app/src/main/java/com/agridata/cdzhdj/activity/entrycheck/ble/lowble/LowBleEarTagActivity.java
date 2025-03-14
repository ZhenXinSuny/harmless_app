package com.agridata.cdzhdj.activity.entrycheck.ble.lowble;

import static com.agridata.cdzhdj.base.MyApplication.getContext;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.BleSp;
import com.agridata.cdzhdj.activity.entrycheck.CheckEgImgAdapter;
import com.agridata.cdzhdj.activity.entrycheck.ble.tagreader.LowScanEarTagBleInfoAdapter;
import com.agridata.cdzhdj.activity.epidemic.eartag.lowble.ConnBlueToothActivity;
import com.agridata.cdzhdj.activity.adapter.onDelListener;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.CheckInfoData;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.utils.TextStyleUtil;
import com.agridata.cdzhdj.databinding.ActivityLowbleEartagBinding;
import com.agridata.cdzhdj.utils.AppConstants;
import com.agridata.cdzhdj.utils.GlideEngine;
import com.agridata.cdzhdj.utils.PictureSelectorUtils;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.FullyGridLayoutManager;
import com.agridata.cdzhdj.view.bottomPopupDialog.BottomPopupDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.luck.picture.lib.basic.PictureSelectionCameraModel;
import com.luck.picture.lib.basic.PictureSelectionModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.PictureFileUtils;
import com.lzx.utils.RxToast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 低频 蓝牙 设备
 */
public class LowBleEarTagActivity extends BaseActivity<ActivityLowbleEartagBinding> implements  onDelListener {
    private static final String TAG = "lzx-----》";
    private String Imgs;
    private CheckEgImgAdapter adapter;
    private int maxSelectNum;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int NumImg;
    private CustomLoadingDialog mLoadingDialog;
    public static final int SEARCH_DEVICE_CODE = 100;
    private BluetoothSocket btSocket1 = null;
    private BluetoothAdapter mBluetoothAdapter = null;
    private LowScanEarTagBleInfoAdapter lowScanEarTagInfoAdapter;
    private InputStream mmInStream;
    private OutputStream mmOutStream;
    private String address;


    public static final int MESSAGE_CONNECT = 6;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_CONNECT_SUCCEED = 7;
    public static final int MESSAGE_CONNECT_LOST = 8;
    public static final int MESSAGE_RECV = 10;
    public static final int MESSAGE_EXCEPTION_RECV = 11;

    private boolean isSimple;
    private Boolean bConnect = false;
    private Vibrator mVibrator;
    private SoundPool soundPool;
    long[] pattern = {100, 400, 100, 400}; // 停止 开启 停止 开启
    private String mEarTags;
    private List<String> EarTagList;


    private ImageEngine imageEngine;
    private List<String > imageFiles = new ArrayList<>();

    private boolean BluetoothStatus = false;
      private int qualified_type;//状态
    private int mType;
    @Override
    protected void onStart() {
        super.onStart();

    }




    private BroadcastReceiver connectDevices = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                    LogUtil.d("lzx---》","ACTION_ACL_CONNECTED");
            } else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                if (BluetoothStatus){
                    hideLoading();
                    Objects.requireNonNull(RxToast.info(LowBleEarTagActivity.this, "已断开连接"));
                }

                LogUtil.d("lzx---》","ACTION_ACL_DISCONNECTED");
            }
        }
    };

    @Override
    protected ActivityLowbleEartagBinding getBinding() {
        return ActivityLowbleEartagBinding.inflate(getLayoutInflater());
    }
    public static void start(Context context,String earTags,int type) {
        Intent intent = new Intent(context, LowBleEarTagActivity.class);
        intent.putExtra("earTags", earTags);
        intent.putExtra("type", type);
        context.startActivity(intent);




    }

    @Override
    protected void initView() {
        initLoadingView();
        getArguments();
        imageEngine = GlideEngine.createGlideEngine();
        binding.titlebarLeft.setOnClickListener(v ->{
            if ( qualified_type  == 2){
                setFinishInfo();
            }else  if (qualified_type  == 1){
                setFinishInfo();
            }else {
                showTips();
            }

        });
        binding.upLoadBtn.setOnClickListener(v ->  upLoadImage());
    }

    private void  setFinishInfo(){
        hideLoading();
        CheckInfoData checkInfoData = new CheckInfoData();
        checkInfoData.Count = Integer.parseInt(binding.needCountTv.getText().toString());//查验数量
        checkInfoData.Type = qualified_type;//查验状态
        checkInfoData.errorEarTag = binding.eartagErrorTv.getText().toString();//错误耳标号
        checkInfoData.EarTag = new ArrayList<>();
        if (lowScanEarTagInfoAdapter.getDataList()!=null &&  lowScanEarTagInfoAdapter.getDataList().size()>0){
                for (int i = 0; i < lowScanEarTagInfoAdapter.getDataList().size(); i++) {
                    checkInfoData.EarTag.add(lowScanEarTagInfoAdapter.getDataList().get(i));
                }
        }
        checkInfoData.imgInfo = Imgs;
        LogUtil.d("lzx---》","  checkInfoData.EarTag" +  checkInfoData.EarTag.toString());
        mRxManager.post("checkInfoData",checkInfoData);
        finish();
    }
    /**
     * 获取参数
     */
    private void getArguments() {
        this.mEarTags = this.getIntent().getStringExtra("earTags");
        this.mType = this.getIntent().getIntExtra("type",-1);
        LogUtil.d("lzx---》","mEarTags" + mEarTags.toString());
    }
    @Override
    protected void initDatas() {
        EarTagList = new ArrayList<>();
        EarTagList.clear();
        String[] split = mEarTags.split((","));
        LogUtil.d("lzx---》","split" + split.toString());
        for (String s : split) {
            EarTagList.add(s);
        }
        binding.eartagsCountTv.setText(EarTagList.size()+"个");
        int earTagSize = EarTagList.size();
        if (earTagSize>10 && earTagSize<40){//大于10小于40  抽10
            binding.needCountTv.setText("10");
            binding.needCountImage.setText("10");
            NumImg = 10;
        }else if (earTagSize>=40){ //大于40抽25%
            double mul = TextStyleUtil.mul(0.25, earTagSize);
            LogUtil.d("lzx---》","mul" + mul);
            BigDecimal b = new BigDecimal(mul).setScale(0, BigDecimal.ROUND_CEILING );
            String s = b.toString();
            LogUtil.d("lzx---》","s" + s);
            binding.needCountTv.setText(s);
            binding.needCountImage.setText(s);
            NumImg =Integer.parseInt(s);
        }else if (earTagSize==10 || earTagSize<10){//小于10抽 全部
            binding.needCountTv.setText(earTagSize+"");
            binding.needCountImage.setText(earTagSize+"");
            NumImg =earTagSize;
        }



        LogUtil.d("lzx----》",EarTagList.size() + "总数量");
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lowScanEarTagInfoAdapter = new LowScanEarTagBleInfoAdapter(R.layout.item_eartag_ec, this);
        binding.recyclerview.setAdapter(lowScanEarTagInfoAdapter);

        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.beep, 1);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                Objects.requireNonNull(RxToast.info(LowBleEarTagActivity.this, "本机无蓝牙，连接失败"));
                finish();
                return;
            }
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
            registerReceiver(connectDevices, filter);
            //  initialize();
            address = BleSp.getInstance().getLowBle();
            if (!TextUtils.isEmpty(address)){
                if (!mBluetoothAdapter.isEnabled()) {
                    showOpenBleDialog();
                }else {
                    initialize();
                }
            }else {
                initialize();
            }
        }

    public void initialize() {
        address = BleSp.getInstance().getLowBle();
        LogUtil.d("lzx-------》", address + " 低功耗蓝牙 ");
        if (!TextUtils.isEmpty(address)) {
            showLoading("正在连接设备");
            mHandler.sendEmptyMessageDelayed(MESSAGE_CONNECT, 1000);
        } else {
            Intent intent = new Intent(LowBleEarTagActivity.this, ConnBlueToothActivity.class);
            startActivityForResult(intent, SEARCH_DEVICE_CODE);
        }

//        timer.schedule(new MyTask(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_DEVICE_CODE) {
            if (resultCode == ConnBlueToothActivity.CONNECT_DEVICE_OK) {
                initialize();
                LogUtil.d("lzx---->", "连接返回，开始扫面。。。。");
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ( qualified_type  == 2){
                setFinishInfo();
            }else  if (qualified_type  == 1){
                setFinishInfo();
            }else {
                showTips();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
    protected void onDestroy() {
        try {
            release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    // Hander
    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_CONNECT:
                    new Thread(new Runnable() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void run() {
                            InputStream tmpIn;
                            OutputStream tmpOut;
                            try {
                                UUID uuid = UUID.fromString(AppConstants.SPP_UUID);
                                BluetoothDevice btDev = mBluetoothAdapter.getRemoteDevice(address);
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

                    showLoading("开始扫描耳标...");
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
                        Objects.requireNonNull(RxToast.error(LowBleEarTagActivity.this,"设备异常,请重新扫描..."));
                        disconnect();
//                        if (lowScanEarTagInfoAdapter.getDataList().size() > 0) {
//                            Intent intent = new Intent();
//                            intent.putStringArrayListExtra("eartaglist", (ArrayList<String>) lowScanEarTagInfoAdapter.getDataList());
//                            setResult(RESULT_OK, intent);
//                        }
                        finish();
                    }
                    break;
                case MESSAGE_CONNECT_LOST:
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(LowBleEarTagActivity.this,"蓝牙连接失败，请检查设备是否打开并进行重新连接"));
                    disconnect();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent1 = new Intent(LowBleEarTagActivity.this, ConnBlueToothActivity.class);
                            startActivityForResult(intent1, SEARCH_DEVICE_CODE);
                        }
                    },800);

//                    if (lowScanEarTagInfoAdapter.getDataList().size() > 0) {
//                        Intent intent = new Intent();
//                        intent.putStringArrayListExtra("eartaglist", (ArrayList<String>) lowScanEarTagInfoAdapter.getDataList());
//                        setResult(RESULT_OK, intent);
//                    }
//                    //finish();
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
                        Objects.requireNonNull(RxToast.error(LowBleEarTagActivity.this,"请扫描指定耳标"));
                    } else {
                        if (binaryCode.matches("^[0-9_]+$")) {
                            //判断扫描耳标是否与动物类型匹配
                            AddEartagNumTolistView(binaryCode);
                        }
                    }
                    break;
            }
        }
    };
//不合格
    public void AddEartagNumTolistView(String eartagNo) {
        if (!EarTagList.contains(eartagNo)){
            qualified_type = 2;
            disconnect();
            BluetoothStatus = true;

            binding.mismatchLl.setVisibility(View.VISIBLE);
            if (lowScanEarTagInfoAdapter.getDataList().isEmpty()){
                binding.recyclerview.setVisibility(View.GONE);
                binding.noDataLl.setVisibility(View.VISIBLE);
                binding.eartagErrorTv.setText(eartagNo);
                binding.qualifiedTypeIv.setImageDrawable(getDrawable(R.drawable.unqualified_iv));
                binding.qualifiedTypeIv.setVisibility(View.VISIBLE);
                binding.imgLl.setVisibility(View.GONE);
            }else {
                binding.eartagErrorTv.setText(eartagNo);
                binding.qualifiedTypeIv.setVisibility(View.VISIBLE);
                binding.qualifiedTypeIv.setImageDrawable(getDrawable(R.drawable.unqualified_iv));
                binding.imgLl.setVisibility(View.GONE);
            }

            //延时3s执行
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setFinishInfo();
                    mRxManager.post("qualified_type",1);//发送合格状态
                }
            }, 2000);


            return;
        }
        if (!lowScanEarTagInfoAdapter.getDataList().contains(eartagNo)) {
            lowScanEarTagInfoAdapter.addData(eartagNo);

            Collections.sort(lowScanEarTagInfoAdapter.getDataList());
            lowScanEarTagInfoAdapter.notifyDataSetChanged();
            int nCount = lowScanEarTagInfoAdapter.getDataList().size();
            if (nCount==0){
                binding.selectNumTv.setText("0");
            }else {
                binding.selectNumTv.setText(nCount+"");
                binding.needCountImage.setText(NumImg - nCount +"");
            }
            int count = Integer.parseInt(binding.needCountTv.getText().toString());
            if (nCount == count){
                binding.qualifiedTypeIv.setImageDrawable(getDrawable(R.drawable.qualified_iv));
                binding.qualifiedTypeIv.setVisibility(View.VISIBLE);
                qualified_type = 1;//合格
                setFinishInfo();
            }
            mVibrator.vibrate(pattern, -1);
            soundPool.play(1, 1, 1, 0, 0, 1);
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
    private void showOpenBleDialog() {
        LogUtil.d("lzx--------》","GOGOGOGOGO");
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
            mBluetoothAdapter.enable();
            initialize();

        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }


    private void showTips() {
        LogUtil.d("lzx--------》","GOGOGOGOGO");
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = (TextView) view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        contentTv.setText("您的耳标抽查未结束，是否确认返回？");

        cancelTv.setOnClickListener(view1 -> exitDialog.dismiss());
        confirmTv.setOnClickListener(view12 -> {
            exitDialog.dismiss();
            mRxManager.post("NO_ERATAG_INFO", true);
            finish();

        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }


    /**
     * 弹出选择相册或者拍照
     */
    private void showChoices() {
        final BottomPopupDialog bottomPopupDialog;
        List<String> bottomDialogContents;//弹出列表的内容
        bottomDialogContents = new ArrayList<>();
        bottomDialogContents.add(getString(R.string.take_pictures));
        bottomDialogContents.add(getString(R.string.select_from_album));
        bottomPopupDialog = new BottomPopupDialog(this, bottomDialogContents);
        bottomPopupDialog.show();
        bottomPopupDialog.showCancelBtn(true);
        bottomPopupDialog.setOnItemClickListener((v, position) -> {
            if (position == 0) {//拍照
                getPermissionCream();

            } else {
                OpenPicture();
            }
        });
    }
    private void getPermissionCream() {
        XXPermissions.with(this).permission(Permission.CAMERA).request((permissions, all) -> {
            if (all) {
                TakePicturesAlone();
            }
        });
    }
    //单独拍照
    private void TakePicturesAlone() {
        // Take pictures alone
        PictureSelectionCameraModel cameraModel = PictureSelector.create(LowBleEarTagActivity.this)
                .openCamera(SelectMimeType.ofImage())
                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())//裁剪引擎
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())//压缩引擎
                .isOriginalControl(true);
        forOnlyCameraResult(cameraModel);
    }


    // 相册选择
    private void OpenPicture() {
        // 进入相册
        PictureSelectionModel selectionModel = PictureSelector.create(LowBleEarTagActivity.this)
                .openGallery(SelectMimeType.ofImage())
                .setSelectorUIStyle(PictureSelectorUtils.setStyle())
                .setImageEngine(imageEngine)
                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())
                .setSandboxFileEngine(PictureSelectorUtils.getMeSandboxFileEngine())
                .setSelectLimitTipsListener(PictureSelectorUtils.getMeOnSelectLimitTips())
                .setSelectionMode(SelectModeConfig.SINGLE)
                .setQuerySortOrder(MediaStore.MediaColumns.DATE_MODIFIED)
                .isDisplayTimeAxis(true)
                .isOriginalControl(true)
                .isDisplayCamera(false)
                .isPreviewFullScreenMode(true)
                .isPreviewZoomEffect(true)
                .isPreviewImage(true)
                .isMaxSelectEnabledMask(true)
                .isDirectReturnSingle(true)
                .setMaxSelectNum(1);
        forSelectResult(selectionModel, "ERBIAO_PIC");
    }
    private void forOnlyCameraResult(PictureSelectionCameraModel model) {
        model.forResultActivity(new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(ArrayList<LocalMedia> result) {
                analyticalSelectResults(result, "ERBIAO_PIC");
            }

            @Override
            public void onCancel() {

            }
        });
    }
    private void upLoadImage() {
        maxSelectNum = Integer.parseInt(binding.needCountImage.getText().toString());
        LogUtil.d("lzx--->", "需要上传的图片数量" + maxSelectNum);

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        binding.recyclerviewImg.setLayoutManager(manager);
        binding.recyclerviewImg.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));
        adapter = new CheckEgImgAdapter(this, selectList);
        adapter.setSelectMax(maxSelectNum);
        adapter.setDel(this);
        binding.recyclerviewImg.setAdapter(adapter);

        adapter.setOnItemClickListener(new CheckEgImgAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                if (!TextUtils.isEmpty(adapter.getData().get(position).getCompressPath())) {
//                    PicActivity.start(LowBleEarTagActivity.this, adapter.getData().get(position).getCompressPath());
//                } else {
//                    PicActivity.start(LowBleEarTagActivity.this, adapter.getData().get(position).getPath());
//                }
                ArrayList<LocalMedia> selectList = adapter.getData();
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String mimeType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(mimeType);
                    switch (mediaType) {
                        case SelectMimeType.TYPE_IMAGE:
                            PictureSelector.create(LowBleEarTagActivity.this)
                                    .openPreview()
                                    .setSelectorUIStyle(PictureSelectorUtils.setStyle())
                                    .isPreviewFullScreenMode(false)
                                    .setImageEngine(GlideEngine.createGlideEngine())
                                    .setExternalPreviewEventListener(new OnExternalPreviewEventListener() {
                                        @Override
                                        public void onPreviewDelete(int position) {

                                        }

                                        @Override
                                        public boolean onLongPressDownload(LocalMedia media) {
                                            return false;
                                        }
                                    }).startActivityPreview(position, false, selectList);
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void openPicture() {
                showChoices();
            }
        });

    }
    //删除照片
    @Override
    public void delete(int position) {
        adapter.delete(position);
        imageFiles.remove(position);
        LogUtil.i("lzx------->", imageFiles.toString());
    }
    //选择回调
    private void forSelectResult(PictureSelectionModel model, String typeName) {

        switch (typeName) {
            case "ERBIAO_PIC"://上传耳标照片
                setModel(model, "ERBIAO_PIC");
                break;
        }

    }
    private void setModel(PictureSelectionModel model, String typeName) {
        model.forResult(new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(ArrayList<LocalMedia> result) {
                analyticalSelectResults(result, typeName);
            }

            @Override
            public void onCancel() {
            }
        });
    }
    private void analyticalSelectResults(ArrayList<LocalMedia> result, String typeName) {
        for (LocalMedia media : result) {
            if (media.getWidth() == 0 || media.getHeight() == 0) {
                if (PictureMimeType.isHasImage(media.getMimeType())) {
                    MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(getContext(), media.getPath());
                    media.setWidth(imageExtraInfo.getWidth());
                    media.setHeight(imageExtraInfo.getHeight());
                }
            }
            LogUtil.i("lzx---》", "文件名: " + media.getFileName());
            LogUtil.i(TAG, "是否压缩:" + media.isCompressed());
            LogUtil.i(TAG, "压缩:" + media.getCompressPath());
            LogUtil.i(TAG, "初始路径:" + media.getPath());
            LogUtil.i(TAG, "绝对路径:" + media.getRealPath());
            LogUtil.i(TAG, "是否裁剪:" + media.isCut());
            LogUtil.i(TAG, "裁剪路径:" + media.getCutPath());
            LogUtil.i(TAG, "是否开启原图:" + media.isOriginal());
            LogUtil.i(TAG, "原图路径:" + media.getOriginalPath());
            LogUtil.i(TAG, "沙盒路径:" + media.getSandboxPath());
            LogUtil.i(TAG, "水印路径:" + media.getWatermarkPath());
            LogUtil.i(TAG, "视频缩略图:" + media.getVideoThumbnailPath());
            LogUtil.i(TAG, "原始宽高: " + media.getWidth() + "x" + media.getHeight());
            LogUtil.i(TAG, "裁剪宽高: " + media.getCropImageWidth() + "x" + media.getCropImageHeight());
            LogUtil.i(TAG, "文件大小: " + PictureFileUtils.formatAccurateUnitFileSize(media.getSize()));

            switch (typeName) {
                case "ERBIAO_PIC"://耳标照片

                    LogUtil.d(TAG, "media.getCompressPath() " + media.getCompressPath());
                    adapter.getData().addAll(result);
                    LogUtil.d(TAG, "result " + result.get(0).getCompressPath());
                    adapter.notifyDataSetChanged();


                    upLoadImg(media.getCompressPath());
                    break;



            }


        }
    }


    /**
     * filePath 上传图片
     *
     * @param filePath
     */
    private void upLoadImg(String filePath) {
        showLoading("正在上传中...");
        HttpRequest.upLoadImg(LowBleEarTagActivity.this, filePath, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d(TAG, "上传图片" + content.toString());
                if (content.Status == 0) {
                    Objects.requireNonNull(RxToast.success(LowBleEarTagActivity.this, "上传成功"));
                    hideLoading();

                    imageFiles.add(content.Result);
                    if (imageFiles.size()==Integer.parseInt(binding.needCountImage.getText().toString())){
                        StringBuilder img = new StringBuilder();
                        for (int i = 0; i < imageFiles.size(); i++) {
                            img.append(imageFiles.get(i).trim() +
                                    (i == imageFiles.size() - 1 ? "" : ","));
                        }
                        Imgs = img.toString();
                        LogUtil.i("Imgs-->" + Imgs);
                        Objects.requireNonNull(RxToast.success(LowBleEarTagActivity.this,"已成功上传" + imageFiles.size() + "张耳标照片"));
                        binding.qualifiedTypeIv.setImageDrawable(getDrawable(R.drawable.qualified_iv));
                        binding.qualifiedTypeIv.setVisibility(View.VISIBLE);
                        qualified_type = 1;//合格
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setFinishInfo();
                            }
                        }, 1000);


                    }else {
                        int i = Integer.parseInt(binding.needCountImage.getText().toString());
                        int i1 = i - imageFiles.size();
                        Objects.requireNonNull(RxToast.info(LowBleEarTagActivity.this, "您当前还需选择" + i1 + "照片"));
                    }
                }else {
                    Objects.requireNonNull(RxToast.error(LowBleEarTagActivity.this, content.Result.toString()));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }

}
