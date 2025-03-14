package com.agridata.cdzhdj.activity.entrycheck.ble.tagreader;

import static com.agridata.cdzhdj.base.MyApplication.getContext;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.BleSp;
import com.agridata.cdzhdj.activity.entrycheck.CheckEgImgAdapter;
import com.agridata.cdzhdj.activity.adapter.onDelListener;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.CheckInfoData;
import com.agridata.cdzhdj.data.ImgBean;
import com.agridata.cdzhdj.utils.TextStyleUtil;
import com.agridata.cdzhdj.databinding.ActivityTagReaderBinding;
import com.agridata.cdzhdj.utils.GlideEngine;
import com.agridata.cdzhdj.utils.PictureSelectorUtils;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.SetTypeUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.FullyGridLayoutManager;
import com.agridata.cdzhdj.view.bottomPopupDialog.BottomPopupDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
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
import com.org.smartbluekit.BlueDevice;
import com.org.smartbluekit.BlueManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import rx.functions.Action1;

/**
 * @auther 56454
 * @date 2022/1/7
 * @time 17:31.
 */
public class TagReaderActivity extends BaseActivity<ActivityTagReaderBinding> implements BlueManager.BlueManagerCallback, onDelListener {
    private TagReaderBlueToothAdapter tagReaderBlueToothAdapter;
    private BlueManager mBlueManager = null;
    private CustomLoadingDialog mLoadingDialog;
    private static final String TAG = "lzx-----》";
    public static final int SEARCH_DEVICE_CODE = 100;

    private BluetoothAdapter mBluetoothAdapter = null;
    private LowScanEarTagBleInfoAdapter lowScanEarTagInfoAdapter;

    private String address;


    private boolean isConnected = false;

    private BlueDevice cunnectDevice = null;


    private Boolean bConnect = false;
    private Vibrator mVibrator;
    private SoundPool soundPool;
    long[] pattern = {100, 400, 100, 400}; // 停止 开启 停止 开启
    private String mEarTags;
    private List<String> EarTagList;

    private boolean BluetoothStatus = false;
    private int qualified_type;//状态
    private int mType;
    private String replace;

    private String Imgs;
    private CheckEgImgAdapter adapter;
    private int maxSelectNum;
    private List<LocalMedia> selectList ;
    private int NumImg;
    private ImageEngine imageEngine;
    private List<String > imageFiles = new ArrayList<>();
    private String lastDeviceAddress;
    private AlertDialog exitDialog;

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected ActivityTagReaderBinding getBinding() {
        return ActivityTagReaderBinding.inflate(getLayoutInflater());
    }

    public static void start(Context context, String earTags, int type) {
        Intent intent = new Intent(context, TagReaderActivity.class);
        intent.putExtra("earTags", earTags);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        initLoadingView();
        getArguments();
        SetTypeUtils.setType(1);
        binding.titlebarLeft.setOnClickListener(v -> {
            if (qualified_type == 2) {
                setFinishInfo();
            } else if (qualified_type == 1) {
                setFinishInfo();
            } else {
                showTips();
            }

        });
        //获取蓝牙管理器
        //Get the bluetooth manager.
        mBlueManager = BlueManager.getInstance(this);
        //注册观察者
        //Registered observer
        mBlueManager.registerObserver(this);

        selectList = new ArrayList<>();
        imageEngine = GlideEngine.createGlideEngine();
        binding.chongxinlianjieTv.setText("重新搜索");
        binding.chongxinlianjieTv.setVisibility(View.VISIBLE);
        binding.chongxinlianjieTv.setOnClickListener(v -> initialize());


    }

    private void setFinishInfo() {
        hideLoading();
        CheckInfoData checkInfoData = new CheckInfoData();
        checkInfoData.Count = Integer.parseInt(binding.needCountTv.getText().toString());//查验数量
        checkInfoData.Type = qualified_type;//查验状态
        checkInfoData.errorEarTag = binding.eartagErrorTv.getText().toString();//错误耳标号
        checkInfoData.EarTag = new ArrayList<>();
        if (lowScanEarTagInfoAdapter.getDataList() != null && lowScanEarTagInfoAdapter.getDataList().size() > 0) {
            checkInfoData.EarTag.addAll(lowScanEarTagInfoAdapter.getDataList());
        }
        checkInfoData.imgInfo = Imgs;
        LogUtil.d("lzx---》", "  checkInfoData.EarTag" + checkInfoData.EarTag.toString());
        mRxManager.post("checkInfoData", checkInfoData);
        finish();
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        this.mEarTags = this.getIntent().getStringExtra("earTags");
        this.mType = this.getIntent().getIntExtra("type", -1);
        LogUtil.d("lzx---》", "mEarTags" + mEarTags.toString());
    }

    @Override
    protected void initDatas() {
        EarTagList = new ArrayList<>();
        EarTagList.clear();
        String[] split = mEarTags.split((","));
        LogUtil.d("lzx---》", "split" + split.toString());
        for (String s : split) {
            EarTagList.add(s);
        }
        binding.eartagsCountTv.setText(EarTagList.size() + "个");
        int earTagSize = EarTagList.size();
        if (earTagSize > 10 && earTagSize < 40) {//大于10小于40  抽10
            binding.needCountTv.setText("10");
            binding.needCountImage.setText("10");
            NumImg = 10;
        } else if (earTagSize >= 40) { //大于40抽25%
            double mul = TextStyleUtil.mul(0.25, earTagSize);
            LogUtil.d("lzx---》", "mul" + mul);
            BigDecimal b = new BigDecimal(mul).setScale(0, BigDecimal.ROUND_CEILING );
            String s = b.toString();
            LogUtil.d("lzx---》", "s" + s);
            binding.needCountTv.setText(s);
            binding.needCountImage.setText(s);
            NumImg = Integer.parseInt(s);
        } else if (earTagSize == 10 || earTagSize < 10) {//小于10抽 全部
            binding.needCountTv.setText(earTagSize + "");
            binding.needCountImage.setText(earTagSize + "");
            NumImg = earTagSize;
        }


        LogUtil.d("lzx----》", EarTagList.size() + "总数量");
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lowScanEarTagInfoAdapter = new LowScanEarTagBleInfoAdapter(R.layout.item_eartag_ec, this);
        binding.recyclerview.setAdapter(lowScanEarTagInfoAdapter);

        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.beep, 1);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            RxToast.warning(this, "本机无蓝牙，连接失败");
            finish();
            return;
        }
        address = BleSp.getInstance().getTagReaderBle();
        if (!mBluetoothAdapter.isEnabled()) {
            showOpenBleDialog();
        } else {
            initialize();
        }

        binding.upLoadBtn.setOnClickListener(v -> upLoadImage());
    }

    public void initialize() {
        address = BleSp.getInstance().getTagReaderBle();
        LogUtil.d("lzx-------》", address + " 低功耗蓝牙 ");
        showLoading("正在搜索中...");
        mHandler.postDelayed(() -> mBlueManager.scanForDevice(BlueManager.BFM | BlueManager.LET), 2000);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showBleDialog();
            }
        },400);
    }


    @Override
    protected void OnEventMainThread() {
        super.OnEventMainThread();
        mRxManager.on("device", new Action1<Object>() {
            @Override
            public void call(Object o) {
                BlueDevice blueDevice = (BlueDevice) o;
                LogUtil.d("lzx---->", "device" + blueDevice.toString());
                mBlueManager.connectDevice(blueDevice);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_DEVICE_CODE) {
            if (resultCode == TagReaderBlueToothActivity.CONNECT_DEVICE_OK) {
                LogUtil.d("lzx---->", "连接返回，开始扫面。。。。");

            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.d("lzx----》", "系统返回");
        mLoadingDialog.hide();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (qualified_type == 2) {
                setFinishInfo();
            } else if (qualified_type == 1) {
                setFinishInfo();
            } else {
                showTips();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void release() {
        try {
            Thread.sleep(500);


            hideLoading();
            soundPool.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //不合格
    public void AddEartagNumTolistView(String eartagNo) {
        if (!EarTagList.contains(eartagNo)) {
            qualified_type = 2;
            //todo:断开
            BluetoothStatus = true;
            if (cunnectDevice != null) {
                //断开当前连接
                //Disconnect device
                mBlueManager.disconnectDevice(cunnectDevice);
                hideLoading();
            }
            binding.mismatchLl.setVisibility(View.VISIBLE);
            if (lowScanEarTagInfoAdapter.getDataList().size() == 0) {
                binding.recyclerview.setVisibility(View.GONE);
                binding.noDataLl.setVisibility(View.VISIBLE);
                binding.eartagErrorTv.setText(eartagNo);
                binding.qualifiedTypeIv.setImageDrawable(getDrawable(R.drawable.unqualified_iv));
                binding.qualifiedTypeIv.setVisibility(View.VISIBLE);
                binding.imgLl.setVisibility(View.GONE);
            } else {
                binding.eartagErrorTv.setText(eartagNo);
                binding.qualifiedTypeIv.setVisibility(View.VISIBLE);
                binding.qualifiedTypeIv.setImageDrawable(getDrawable(R.drawable.unqualified_iv));
                binding.imgLl.setVisibility(View.GONE);
            }

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setFinishInfo();
                    mRxManager.post("qualified_type", 1);//发送合格状态
                }
            }, 1000);

            return;
        }
        if (!lowScanEarTagInfoAdapter.getDataList().contains(eartagNo)) {
            lowScanEarTagInfoAdapter.addData(eartagNo);

            Collections.sort(lowScanEarTagInfoAdapter.getDataList());
            lowScanEarTagInfoAdapter.notifyDataSetChanged();
            int nCount = lowScanEarTagInfoAdapter.getDataList().size();
            if (nCount == 0) {
                binding.selectNumTv.setText("0");

            } else {
                binding.selectNumTv.setText(nCount + "");
                binding.needCountImage.setText(NumImg - nCount + "");
            }
            int count = Integer.parseInt(binding.needCountTv.getText().toString());
            if (nCount == count || nCount > count) {
                hideLoading();

                binding.qualifiedTypeIv.setImageDrawable(getDrawable(R.drawable.qualified_iv));
                binding.qualifiedTypeIv.setVisibility(View.VISIBLE);
                qualified_type = 1;//合格
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setFinishInfo();
                    }
                }, 1000);
            }
            mVibrator.vibrate(pattern, -1);
            soundPool.play(1, 1, 1, 0, 0, 1);
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

    @Override
    protected void onResume() {
        super.onResume();
        SetTypeUtils.setType(1);

    }

    @SuppressLint("MissingPermission")
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
            mBluetoothAdapter.enable();
            initialize();

        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }

    /**
     * 检查蓝牙：是否支持蓝牙及是否打开
     *
     * @param available true为打开
     */
    @Override
    public void onManagerBLEAvailable(boolean available) {
//        if (!available){
//            mBlueManager.openBleSetting(TagReaderActivity.this);
//        }
        Log.d(TAG, "onManagerBLEAvailable: ");
    }

    /**
     * 获取周边蓝牙设备
     *
     * @param devices 设备列表（BlueDevice类型）
     */
    @Override
    public void onManagerDevicesFound(List<BlueDevice> devices) {
        LogUtil.d("lzx---》", " 扫描的设备" + devices.size());
        if (lastDeviceAddress!=null) {
            for (BlueDevice device : devices) {
                if (device.getDeviceMAC().equals(lastDeviceAddress)) {
                    mBlueManager.connectDevice(device);
                    lastDeviceAddress=null;
                    break;
                }
            }
            return;
        }
            if (devices.size()==0){
                exitDialog.dismiss();
                hideLoading();
                Objects.requireNonNull(RxToast.info(TagReaderActivity.this, "请再次搜索设备"));
                binding.chongxinlianjieTv.setVisibility(View.VISIBLE);
                binding.chongxinlianjieTv.setText("重新搜索");
            }else {
                hideLoading();
                tagReaderBlueToothAdapter.refreshDataList(devices);
                for (BlueDevice device : devices) {
                    if (device.getDeviceType().equals("TagReader")){
                        hideLoading();
                        return;
                    }
                }
            }
    }


    /**
     * 连接设备成功
     *
     * @param blueDevice 对应设备
     */
    @Override
    public void onManagerDeviceConnected(BlueDevice blueDevice) {
        isConnected = true;
        cunnectDevice = blueDevice;
        LogUtil.d("lzx---》", "blueDevice" + blueDevice.getDeviceType() + blueDevice.getDeviceMAC());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideLoading();
                binding.chongxinlianjieTv.setVisibility(View.GONE);
                binding.lianjieLl.setVisibility(View.VISIBLE);
                showLoading("连接设备成功,开始扫描...");
            }
        });


    }

    /**
     * 连接设备失败
     *
     * @param device 对应设备
     */
    @Override
    public void onManagerDeviceConnectFailed(BlueDevice device) {
        Objects.requireNonNull(RxToast.error(TagReaderActivity.this, "连接设备失败，请重试..."));
        mHandler.sendEmptyMessageDelayed(2000, 800);
    }


    /**
     * 连接设备断开
     *
     * @param device 对应设备
     */
    @Override
    public void onManagerDeviceDisconnected(final BlueDevice device) {
        isConnected = false;
        LogUtil.d("lzx--------》","设备断开");
        mHandler.sendEmptyMessageDelayed(3000, 400);
    }

    /**
     * 连接设备的扫描状态
     *
     * @param device 对应设备
     * @param state  当前状态
     */
    @Override
    public void onScanStateWithDevice(BlueDevice device, String state) {

        Log.d(TAG, "onScanStateWithDevice: ***********" + state);
    }

    /**
     * 连接设备读取背膘数据
     *
     * @param device  当前设备
     * @param backfat 背膘数据（格式：“厚度/层数”）
     */
    @Override
    public void onBackFatWithDevice(BlueDevice device, final String backfat) {


    }


    /**
     * 连接设备读取耳标数据
     *
     * @param device 当前设备
     * @param eartag 电子耳号
     */
    @Override
    public void onEarTagWithDevice(BlueDevice device, final String eartag) {
        replace = eartag.replace("-", "");
        LogUtil.d("lzx----->", "耳标号" + replace);
        mHandler.sendEmptyMessageDelayed(1000, 800);
    }


    // Hander
    public final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1000:
                    AddEartagNumTolistView(replace);
                    break;
                case 2000:

                    cancel();
                    break;
                case  3000:
                    hideLoading();
                    binding.lianjieLl.setVisibility(View.GONE);
                    Objects.requireNonNull(RxToast.success(TagReaderActivity.this, "设备已断开"));
                    binding.chongxinlianjieTv.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;

            }
        }
    };

    private void cancel() {
        //注销观察者
        //Deregister observer
        mBlueManager.deregisterObserver(this);
        //取消扫描
        //Cancel scan
        mBlueManager.cancelScan();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
        //注销观察者
        //Deregister observer
        mBlueManager.deregisterObserver(this);
        //取消扫描
        //Cancel scan
        mBlueManager.cancelScan();
        //断开当前连接
        //Disconnect device
        mBlueManager.disconnectDevice(cunnectDevice);
        /**
         * 如果是多连接则：断开所有已连接设备
         * mBlueManager.diconnectAllDevice(devices);
         */
    }


    /**
     * 弹出选择相册或者拍照
     */
    private void showChoices() {
        final BottomPopupDialog bottomPopupDialog;
        List<String> bottomDialogContents;//弹出列表的内容
        bottomDialogContents = new ArrayList<>();
        bottomDialogContents.add(getString(R.string.take_pictures));
//        bottomDialogContents.add(getString(R.string.select_from_album));
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
        PictureSelectionCameraModel cameraModel = PictureSelector.create(TagReaderActivity.this)
                .openCamera(SelectMimeType.ofImage())
                //.setCropEngine(PictureSelectorUtils.getCropFileEngine())//裁剪引擎
                .setCompressEngine(PictureSelectorUtils.getCompressFileEngine())//压缩引擎
                .isOriginalControl(true);
        forOnlyCameraResult(cameraModel);
    }

    // 相册选择
    private void OpenPicture() {
        // 进入相册
        PictureSelectionModel selectionModel = PictureSelector.create(TagReaderActivity.this)
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
        adapter.setDel(this);
        adapter.setSelectMax(maxSelectNum);
        binding.recyclerviewImg.setAdapter(adapter);

        adapter.setOnItemClickListener(new CheckEgImgAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ArrayList<LocalMedia> selectList = adapter.getData();
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String mimeType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(mimeType);
                    switch (mediaType) {
                        case SelectMimeType.TYPE_IMAGE:
                            PictureSelector.create(TagReaderActivity.this)
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
        HttpRequest.upLoadImg(TagReaderActivity.this, filePath, new CallBackLis<ImgBean>() {
            @Override
            public void onSuccess(String method, ImgBean content) {
                LogUtil.d(TAG, "上传图片" + content.toString());
                if (content.Status == 0) {
                    Objects.requireNonNull(RxToast.success(TagReaderActivity.this, "上传成功"));
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
                        Objects.requireNonNull(RxToast.success(TagReaderActivity.this,"已成功上传" + imageFiles.size() + "张耳标照片"));
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
                        Objects.requireNonNull(RxToast.info(TagReaderActivity.this, "您当前还需选择" + i1 + "张照片"));
                    }



                }else {
                    Objects.requireNonNull(RxToast.error(TagReaderActivity.this, content.Result.toString()));
                }

            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }
    private void showTips() {
        LogUtil.d("lzx--------》", "GOGOGOGOGO");
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
            //断开当前连接
            //Disconnect device
            mBlueManager.disconnectDevice(cunnectDevice);
            exitDialog.dismiss();
            mRxManager.post("NO_ERATAG_INFO", true);
            finish();
        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }


    private void showBleDialog() {
        LogUtil.d("lzx--------》", "GOGOGOGOGO");
        View view = getLayoutInflater().inflate(R.layout.dialog_common_ble_recyclerview, null);
        exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(true);
        RecyclerView ble_recyclerview = view.findViewById(R.id.ble_recyclerview);
        ble_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        tagReaderBlueToothAdapter = new TagReaderBlueToothAdapter(R.layout.item_low_ble_address, this);
        ble_recyclerview.setAdapter(tagReaderBlueToothAdapter);
        tagReaderBlueToothAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                exitDialog.dismiss();
                mBlueManager.connectDevice(tagReaderBlueToothAdapter.getData(position));
                BleSp.getInstance().setTagReaderBle(tagReaderBlueToothAdapter.getData(position).getDeviceMAC());
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });

        Button last_used_ble_bt = view.findViewById(R.id.last_used_ble_bt);
        last_used_ble_bt.setOnClickListener(view1 -> {
            if (!TextUtils.isEmpty(BleSp.getInstance().getTagReaderBle())){
                lastDeviceAddress = BleSp.getInstance().getTagReaderBle();
                mBlueManager.scanForDevice(BlueManager.BFM|BlueManager.LET);
                exitDialog.dismiss();
            }else {
                Objects.requireNonNull(RxToast.warning(TagReaderActivity.this,"未查询到上次链接的设备~"));
            }
        });

        Objects.requireNonNull(exitDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }

}
