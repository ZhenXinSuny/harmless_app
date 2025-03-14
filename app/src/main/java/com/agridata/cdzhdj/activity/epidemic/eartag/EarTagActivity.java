package com.agridata.cdzhdj.activity.epidemic.eartag;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.AnimalSP;
import com.agridata.cdzhdj.SPUtil.BleSp;
import com.agridata.cdzhdj.SPUtil.SplitCoreSP;
import com.agridata.cdzhdj.SPUtil.UpImmuneSP;
import com.agridata.cdzhdj.activity.epidemic.eartag.lowble.LowBluetoothActivity;
import com.agridata.cdzhdj.activity.epidemic.vaccine.VaccineActivity;
import com.agridata.cdzhdj.activity.mine.scan.ScanEartagManager;
import com.agridata.cdzhdj.activity.adapter.BleListAdapter;
import com.agridata.cdzhdj.activity.adapter.ble.ScanBleTypeAdapter;
import com.agridata.cdzhdj.activity.adapter.eartag.EarTagListAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.AgainEarTagBean;
import com.agridata.cdzhdj.data.EarTagStartEndNumBean;
import com.agridata.cdzhdj.data.FaFangEarTagBean;
import com.agridata.cdzhdj.data.ImmuneAnimalBean;
import com.agridata.cdzhdj.data.ImmuneEarTagBean;
import com.agridata.cdzhdj.data.ScanBleBean;
import com.agridata.cdzhdj.data.UpImmuneBean;
import com.agridata.cdzhdj.databinding.ActivityEartagBinding;
import com.agridata.cdzhdj.utils.RxBus;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.lzx.utils.RxToast;
import com.org.smartbluekit.BlueDevice;
import com.org.smartbluekit.BlueManager;
import com.smartahc.android.splitcore_androidx.AppType;
import com.smartahc.android.splitcore_androidx.Common;
import com.smartahc.android.splitcore_androidx.ShowWindowLocation;
import com.smartahc.android.splitcore_androidx.SmartUser;
import com.smartahc.android.splitcore_androidx.SplitCore;
import com.smartahc.android.splitcore_androidx.VersionName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-11-29 17:35.
 * @Description :描述
 */
public class EarTagActivity extends BaseActivity<ActivityEartagBinding> implements View.OnClickListener, BlueManager.BlueManagerCallback {

    private EarTagListAdapter earTagListAdapter;
    private List<ImmuneEarTagBean> immuneEarTagBeanList;
    private boolean isSelectAll = false; //是否是全选状态
    private ArrayList<ScanBleBean> mScanDataList;
    private final static String TAG = "EarTagActivity------》";
    private EarTagStartEndNumBean earTagStartEndNumBean;
    private String XdrId;


    private BluetoothAdapter mBluetooth;

    private BleListAdapter bleListScanAdapter;
    private BlueManager mBlueManager = null;
    private BlueDevice cunnectDevice = null;
    private List<BlueDevice> blueDeviceList;
    private Boolean showBleDialogBle = false;
    private AlertDialog exitDialog;
    private String tagReaderBle;
    private int mOpenCode = 666;
    private String s2;
    private String ImmuneType;//区分是否是首次还是再次
    private int BleType = 0;
    private String lastDeviceAddress;

    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context, String XdrId, String ImmuneType) {
        Intent intent = new Intent(context, EarTagActivity.class);
        Bundle data = new Bundle();
        data.putString("XdrId", XdrId);
        data.putString("ImmuneType", ImmuneType);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    /**
     * 获取上一级所传过来的值  去判断加载哪一个Url
     */
    private void getArguments() {
        Bundle bundle = getIntent().getBundleExtra("data");
        XdrId = bundle.getString("XdrId");
        ImmuneType = bundle.getString("ImmuneType");
    }

    @Override
    protected ActivityEartagBinding getBinding() {
        return ActivityEartagBinding.inflate(getLayoutInflater());
    }

    /**
     * 接收
     */
    private BroadcastReceiver broadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())) {

                case Common.SERVICE_BROADCAST_ACTION_DEVICE:
                    final String deviceInfo = intent.getStringExtra(Common.SERVICE_BROADCAST_ACTION_DEVICE_RESPONSE);
                    Log.d("lzx----》", "deviceInfo" + deviceInfo);
                    break;
                case Common.SERVICE_BROADCAST_ACTION_TAGCHECK:
                    final int TAGCHECK_COUNT = intent.getIntExtra(Common.SERVICE_BROADCAST_ACTION_TAGCHECK_COUNT, -1);
                    final String TAGCHECK_END = intent.getStringExtra(Common.SERVICE_BROADCAST_ACTION_TAGCHECK_END);
                    String TAGCHECK_ERROR = intent.getStringExtra(Common.SERVICE_BROADCAST_ACTION_TAGCHECK_ERROR);
                    Log.d("lzx------》", " TAGCHECK_COUNT数量" + TAGCHECK_COUNT);
                    Log.d("lzx------》", " TAGCHECK_END结束" + TAGCHECK_END);
                    Log.d("lzx------》", " TAGCHECK_ERROR错误" + TAGCHECK_ERROR);


                    if (!TextUtils.isEmpty(TAGCHECK_END) && "end".equals(TAGCHECK_END)) {
                        RxToast.info(EarTagActivity.this, "已断开电子耳标钳连接");
                        SplitCore.INSTANCE.onStopService();
                    }

                    break;

                case Common.SERVICE_BROADCAST_ACTION_FILTER_SUSPENSION:
                    final String earTag = intent.getStringExtra(Common.SERVICE_BROADCAST_KEY_EARTAG_SUSPENSION);
                    boolean booleanExtra = intent.getBooleanExtra(Common.SERVICE_BROADCAST_KEY_SERVICE_STYLE, true);
                    final String stringExtraDev = intent.getStringExtra(Common.SERVICE_BROADCAST_KEY_DEVICE_SUSPENSION);


                    Log.d("lzx----》", "booleanExtra " + booleanExtra);


                    if (!TextUtils.isEmpty(stringExtraDev) && !stringExtraDev.equals("")) {

                        Log.d("lzx------》", " stringExtraDev" + stringExtraDev);
                        String[] split = stringExtraDev.split(",");
                        String result = split[1];
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String str = sdf.format(new Date());
                        if (!TextUtils.isEmpty(stringExtraDev)) {
                            binding.bleLayout.setVisibility(View.VISIBLE);
                            binding.bleTitle.setText("ALMA206智能卡钳（低频）已连接...");
                        } else {
                            binding.bleLayout.setVisibility(View.GONE);
                        }
                    }

                    binding.duankaiTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            binding.bleLayout.setVisibility(View.GONE);
                            SplitCore.INSTANCE.startUploadTagcheck();
                            SplitCore.INSTANCE.onStopService();
                        }
                    });

                    if (!TextUtils.isEmpty(earTag)) {
                        String S1 = earTag.substring(2);
                        s2 = S1.substring(0, S1.length() - 2);
                        Log.d("lzx----》", " substring " + s2);
                    }

                    if (!TextUtils.isEmpty(earTag)) {
                        runOnUiThread(() -> {
                            if (!earTagListAdapter.getDataList().isEmpty()) {
                                for (ImmuneEarTagBean earTagBean : earTagListAdapter.getDataList()) {
                                    if (earTagBean.EarTag.equals(s2)) {
                                        LogUtil.d(TAG, "耳标相等");
                                        Objects.requireNonNull(RxToast.warning(EarTagActivity.this, "该耳标已存在，请重新扫描"));
                                        return;
                                    }
                                }
                            }

                            ImmuneEarTagBean immuneEarTagBean = new ImmuneEarTagBean();
                            immuneEarTagBean.EarTag = s2;
                            immuneEarTagBeanList.add(immuneEarTagBean);
                            binding.notDataRl.setVisibility(View.GONE);
                            binding.recyclerview.setVisibility(View.VISIBLE);
                            earTagListAdapter.refreshDataList(immuneEarTagBeanList);
                            if (!earTagListAdapter.getDataList().isEmpty()) {
                                binding.chooseAllTv.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    break;

            }
        }
    };

    private void getPermissions() {
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.ACCESS_FINE_LOCATION).permission(Permission.ACCESS_COARSE_LOCATION)
                // 申请多个权限
                .permission(Permission.Group.BLUETOOTH).request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                        } else {
                            Objects.requireNonNull(RxToast.warning(EarTagActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(EarTagActivity.this, "被永久拒绝授权，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(EarTagActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(EarTagActivity.this, "获取权限失败"));
                        }
                    }
                });
    }


    @Override
    protected void initView() {
        getPermissions();
        getArguments();
        initBluetooth();
        binding.titlebarLeft.setOnClickListener(v -> finish());

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        earTagListAdapter = new EarTagListAdapter(R.layout.item_eartag_list, this, immuneEarTagBeanList);
        binding.recyclerview.setAdapter(earTagListAdapter);

        binding.chooseAllTv.setOnClickListener(this);
        binding.delBtn.setOnClickListener(this);
        immuneEarTagBeanList = new ArrayList<>();
        binding.vaccineInputBtn.setOnClickListener(this);

        //TODO:此处为了测试 添加假数据 请注意删除或者注释  2022/12/14

        //获取蓝牙管理器
        //Get the bluetooth manager.
        mBlueManager = BlueManager.getInstance(this);
        //注册观察者
        //Registered observer
        mBlueManager.registerObserver(this);
        blueDeviceList = new ArrayList<>();

        setALMA206();

        //断开Ble 链接设置
        binding.duankaiTv.setOnClickListener(v -> {
            if (BleType == 3) {
                if (cunnectDevice != null) {
                    //断开当前连接
                    //Disconnect device
                    mBlueManager.disconnectDevice(cunnectDevice);
                    binding.bleLayout.setVisibility(View.GONE);
                }
            } else if (BleType == 2) {
                SplitCore.INSTANCE.onStopService();
                binding.bleLayout.setVisibility(View.GONE);
            }

        });
        addFabData();
        binding.speedDial.setOnClickListener(view -> {
            if (binding.speedDial.isOpen()) {
                binding.speedDial.close();
            } else {
                binding.speedDial.open();
            }
        });
        binding.speedDial.setOnActionSelectedListener(actionItem -> {
            binding.speedDial.close();
            if (actionItem.getId() == R.id.one) {
                ManualEntryEarTagActivity.start(EarTagActivity.this, !earTagListAdapter.getDataList().isEmpty() ? earTagListAdapter.getDataList().size() : 0);
            } else if (actionItem.getId() == R.id.two) {

            } else if (actionItem.getId() == R.id.three) {
                BleType = 1;
                scanBluetooth();
            } else if (actionItem.getId() == R.id.four) {
                BleType = 3;
                setBleOpenPer();
            }
            return false;
        });
    }

    private void setALMA206() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            SplitCore.INSTANCE.onSplitCreate(EarTagActivity.this, new SmartUser("dongjian", "1234567", null, null, true), AppType.APP_DRIVER, VersionName.v2_1_1, ShowWindowLocation.top_left);
            SplitCore.INSTANCE.onSetFileProvider("com.agridata.cdzhdj.fileprovider");
            SplitCore.INSTANCE.requestDeviceInfo();
        } else {
            SplitCore.INSTANCE.requestDeviceInfo();
        }
        new Handler().postDelayed(() -> {
            String connectStatus = SplitCoreSP.getInstance().getConnectStatus();
            Log.d("lzx----》", "保存的connect_status" + connectStatus);
            if (connectStatus.equals("1")) {
                binding.bleLayout.setVisibility(View.VISIBLE);
                binding.bleTitle.setText("ALMA206智能卡钳（低频）已连接...");
                binding.duankaiTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SplitCore.INSTANCE.onStopService();
                        binding.bleLayout.setVisibility(View.GONE);
                    }
                });
            } else {
                binding.bleLayout.setVisibility(View.GONE);
            }
        }, 1600);
    }

    @Override
    protected void initDatas() {
        if (ImmuneType.equals("1007")) {//首次
            binding.titlebarMiddle.setText("免疫耳标（首次）");
            getInfo();
        } else { //再次
            binding.titlebarMiddle.setText("免疫耳标（在次）");
            getAgainEarTag();
        }
    }

    private void getAgainEarTag() {
        HttpRequest.getAgainEarTag(EarTagActivity.this, XdrId, AnimalSP.getInstance().getChooseAnimal().id, new CallBackLis<>() {

            private ImmuneEarTagBean immuneEarTagBean;
            private List<String> stringList = new ArrayList<>();

            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, AgainEarTagBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    List<AgainEarTagBean.Result> result = content.result;

                    if (result.size() > 0) {
                        for (AgainEarTagBean.Result result1 : result) {
                            for (String s : result1.eagTagsList) {
//                                immuneEarTagBean = new ImmuneEarTagBean();
//                                immuneEarTagBean.EarTag = s;
//                                immuneEarTagBeanList.add(immuneEarTagBean);
                                stringList.add(s);
                            }
                        }

                        List<String> list = StrUtil.removeDuplicate(stringList);

                        for (String s : list) {
                            immuneEarTagBean = new ImmuneEarTagBean();
                            immuneEarTagBean.EarTag = s;
                            immuneEarTagBeanList.add(immuneEarTagBean);
                        }
                        binding.chooseAllTv.setVisibility(View.VISIBLE);
                        earTagListAdapter.refreshDataList(immuneEarTagBeanList);

                        LogUtil.d("lzx---------》", "再次免疫" + immuneEarTagBeanList.toString());
                    } else {
                        binding.notDataRl.setVisibility(View.VISIBLE);
                        RxToast.warning(EarTagActivity.this, "当前暂无免疫过的耳标，请先进行首次免疫");
                    }


                }

            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }

    @Override
    protected void OnEventMainThread() {
        super.OnEventMainThread();
        mRxManager.on("eartag_num_edit", o -> {
            earTagStartEndNumBean = (EarTagStartEndNumBean) o;
            LogUtil.d(TAG, "pageItemsBean" + earTagStartEndNumBean.toString());

            for (int i = 0; i < earTagStartEndNumBean.EndNum - earTagStartEndNumBean.StartNum + 1; i++) {
                boolean flag = false;
                String eartagNo = String.format("%d%08d", earTagStartEndNumBean.Num, earTagStartEndNumBean.StartNum + i);
                for (ImmuneEarTagBean t : earTagListAdapter.getDataList()) {
                    if (eartagNo.equals(t.EarTag)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    ImmuneEarTagBean eb = new ImmuneEarTagBean();
                    eb.EarTag = eartagNo;
                    earTagListAdapter.getDataList().add(eb);
                }
            }

            binding.notDataRl.setVisibility(View.GONE);
            earTagListAdapter.notifyDataSetChanged();
            if (earTagListAdapter.getDataList().size() > 0) {
                binding.chooseAllTv.setVisibility(View.VISIBLE);
            }

        });
    }


    private void getInfo() {
        int AnimalParentID = 0;
        List<ImmuneAnimalBean.ResultDTO> animalList = AnimalSP.getInstance().getAnimalList();

        for (ImmuneAnimalBean.ResultDTO resultDTO : animalList) {
            if (resultDTO.iD == Integer.parseInt(AnimalSP.getInstance().getChooseAnimal().id)) {
                AnimalParentID = resultDTO.animalParentID;
            }

        }
        HttpRequest.getCheckEarTagNum(EarTagActivity.this, XdrId, AnimalParentID, new CallBackLis<FaFangEarTagBean>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, FaFangEarTagBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    if (content.result.pageItems.size() > 0) {
                        binding.notDataRl.setVisibility(View.GONE);
                        binding.chooseAllTv.setVisibility(View.VISIBLE);
                        List<FaFangEarTagBean.Result.PageItems> pageItems = content.result.pageItems;
                        List<EarTagStartEndNumBean> earTagStartEndNumBeanList = new ArrayList<>();
                        for (int i = 0; i < pageItems.size(); i++) {
                            EarTagStartEndNumBean earTagStartEndNumBean = new EarTagStartEndNumBean();
                            earTagStartEndNumBean.Num = Integer.parseInt(pageItems.get(i).prefix);
                            earTagStartEndNumBean.StartNum = Integer.parseInt(pageItems.get(i).rangeStart);
                            earTagStartEndNumBean.EndNum = Integer.parseInt(pageItems.get(i).rangeEnd);
                            earTagStartEndNumBeanList.add(earTagStartEndNumBean);
                        }

                        for (int i = 0; i < earTagStartEndNumBeanList.size(); i++) {

                            EarTagStartEndNumBean earTagStartEndNumBean = earTagStartEndNumBeanList.get(i);
                            for (int k = 0; k < earTagStartEndNumBean.EndNum - earTagStartEndNumBean.StartNum + 1; k++) {
                                String eartagNo = String.format("%d%08d", earTagStartEndNumBean.Num, earTagStartEndNumBean.StartNum + k);
                                ImmuneEarTagBean immuneEarTagBean = new ImmuneEarTagBean();
                                immuneEarTagBean.EarTag = eartagNo;
                                immuneEarTagBean.isSelected = false;
                                immuneEarTagBeanList.add(immuneEarTagBean);
                            }
                        }


                        earTagListAdapter.refreshDataList(immuneEarTagBeanList);
                    } else {
                        RxToast.warning(EarTagActivity.this, "当前暂无签收耳标，请手动录入或使用蓝牙设备进行耳标录入");
                        binding.chooseAllTv.setVisibility(View.INVISIBLE);
                        binding.notDataRl.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClick(View v) {
        if (v == binding.chooseAllTv) {
            if (earTagListAdapter.getSelectedItem().size() < immuneEarTagBeanList.size()) {
                isSelectAll = false;
            }
            if (isSelectAll) {
                isSelectAll = false;
                earTagListAdapter.clearAllSelected();
                setselectAll(earTagListAdapter.getSelectedItem().size(), "全选");
            } else {
                earTagListAdapter.checkAllItem();
                isSelectAll = true;
                setselectAll(earTagListAdapter.getSelectedItem().size(), "取消全选");
            }
            earTagListAdapter.notifyDataSetChanged();

            binding.eartagNumTv.setText(earTagListAdapter.getSelectedItem().size() + "个");
        } else if (v == binding.delBtn) {
            earTagListAdapter.deleteSelected();

            LogUtil.d(TAG, "earTagListAdapter.getSelectedItem()" + earTagListAdapter.getSelectedItem().toString());
            if (earTagListAdapter.getDataList().isEmpty()) {
                binding.chooseEartagLl.setVisibility(View.GONE);
                binding.chooseAllTv.setVisibility(View.INVISIBLE);
                binding.chooseAllTv.setText("全选");
                binding.notDataRl.setVisibility(View.VISIBLE);
            } else {

                if (!earTagListAdapter.getSelectedItem().isEmpty()) {
                    binding.eartagNumTv.setText(earTagListAdapter.getSelectedItem().size() + "个");
                }

            }
        } else if (v == binding.vaccineInputBtn) {
            //疫苗录入
            String s = UpImmuneSP.getInstance().getUpImmuneInfo().toString();
            LogUtil.d(TAG, "保存的信息之前" + s);
            UpImmuneBean upImmuneInfo = UpImmuneSP.getInstance().getUpImmuneInfo();
            List<String> earTags = new ArrayList<>();
            for (ImmuneEarTagBean immuneEarTagBean : earTagListAdapter.getSelectedItem()) {
                earTags.add(immuneEarTagBean.EarTag);
            }
            //upImmuneInfo.EagTagsList = earTags;
            String join = String.join(",", earTags);
            upImmuneInfo.EarTags = join;
            upImmuneInfo.ImmuneCount = String.valueOf(earTagListAdapter.getSelectedItem().size());
            upImmuneInfo.ImmuneQuantity = String.valueOf(earTagListAdapter.getSelectedItem().size());
            UpImmuneSP.getInstance().setUpImmune(upImmuneInfo);
            LogUtil.d(TAG, "保存的信息之后" + UpImmuneSP.getInstance().getUpImmuneInfo().toString());
            if (earTagListAdapter.getSelectedItem().isEmpty()) {
                Objects.requireNonNull(RxToast.warning(EarTagActivity.this, "请选择耳标在进行疫病疫苗录入"));
                return;
            }
            VaccineActivity.start(this);
            RxBus.getInstance().post("EARTAG_DETAILS", earTagListAdapter.getSelectedItem());
        }

    }

    private void setselectAll(int size, String titleTips) {
        if (size > 0) {
            binding.chooseEartagLl.setVisibility(View.VISIBLE);
        } else {
            binding.chooseEartagLl.setVisibility(View.GONE);
        }
        binding.chooseAllTv.setText(titleTips);
    }

    public void setChooseEarTagLl() {
        if (earTagListAdapter.getSelectedItem().size() == earTagListAdapter.getDataList().size()) {
            setselectAll(earTagListAdapter.getSelectedItem().size(), "取消全选");
        } else {
            setselectAll(earTagListAdapter.getSelectedItem().size(), "全选");
        }
        binding.eartagNumTv.setText(earTagListAdapter.getSelectedItem().size() + "个");
    }


    /**
     * add 数据
     */
    private void addFabData() {
        // 添加 "手工批量录入"
        binding.speedDial.addActionItem(new SpeedDialActionItem.Builder(R.id.one, R.drawable.ic_pencil_alt_white_24dp).setLabel("手工批量录入").setFabBackgroundColor(getColor(R.color.select_ear_tag_color1)).setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.white, getTheme())).create());

// 添加 "库存耳标选择"
        Drawable drawable = AppCompatResources.getDrawable(this, R.drawable.ic_list_white_24dp);
        binding.speedDial.addActionItem(new SpeedDialActionItem.Builder(R.id.two, drawable).setFabBackgroundColor(getColor(R.color.J5)).setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.white, getTheme())).setLabel("库存耳标选择").create());

// 添加 "BIO-TAG低频蓝牙设备(黑色)"
        Drawable drawable1 = AppCompatResources.getDrawable(this, R.drawable.ic_theme_white_24dp);
        binding.speedDial.addActionItem(new SpeedDialActionItem.Builder(R.id.three, drawable1).setFabBackgroundColor(getColor(R.color.J3)).setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.white, getTheme())).setLabel("BIO-TAG低频蓝牙设备(黑色)").create());

// 添加 "TagReader低频蓝牙(银色)"
        Drawable drawable2 = AppCompatResources.getDrawable(this, R.drawable.ic_bluetooth);
        binding.speedDial.addActionItem(new SpeedDialActionItem.Builder(R.id.four, drawable2).setFabBackgroundColor(getColor(R.color.J7)).setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.white, getTheme())).setLabel("TagReader低频蓝牙(银色)").create());

    }

    /**
     * 添加低频耳标
     */
    protected void scanBluetooth() {
        ScanEartagManager.getInstance().SetScanDeviceType(ScanEartagManager.ScanDeviceEnum.DEVICE_BLUETOOTH_BIO);
        startActivityForResult(new Intent(this, LowBluetoothActivity.class), 1026);
    }

    @Override
    public void onManagerBLEAvailable(boolean b) {

    }

    @Override
    public void onManagerDevicesFound(List<BlueDevice> devices) {
        LogUtil.d(TAG, "查询的蓝牙" + devices.toString());
        if (lastDeviceAddress != null) {
            for (BlueDevice device : devices) {
                if (device.getDeviceMAC().equals(lastDeviceAddress)) {
                    mBlueManager.connectDevice(device);
                    lastDeviceAddress = null;
                    break;
                }
            }
            return;
        }
        if (devices.size() > 0) {
            blueDeviceList = devices;
            handler.sendEmptyMessage(1);
        } else {
            Objects.requireNonNull(RxToast.info(EarTagActivity.this, "未查询到蓝牙设备，请先打开蓝牙设备"));
        }
    }

    @Override
    public void onManagerDeviceConnected(BlueDevice blueDevice) {
        LogUtil.d(TAG, "onManagerDeviceConnected: ");
        cunnectDevice = blueDevice;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Objects.requireNonNull(RxToast.success(EarTagActivity.this, "连接成功..."));
                binding.bleLayout.setVisibility(View.VISIBLE);
                binding.bleTitle.setText("TagReader低频蓝牙(银色)已连接...");
                if (exitDialog != null) {
                    exitDialog.dismiss();
                }

            }
        });
    }

    @Override
    public void onManagerDeviceConnectFailed(BlueDevice blueDevice) {

    }

    @Override
    public void onManagerDeviceDisconnected(BlueDevice blueDevice) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Objects.requireNonNull(RxToast.success(EarTagActivity.this, "断开连接成功..."));
                binding.bleLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onScanStateWithDevice(BlueDevice blueDevice, String s) {

    }

    @Override
    public void onBackFatWithDevice(BlueDevice blueDevice, String s) {

    }

    @Override
    public void onEarTagWithDevice(BlueDevice blueDevice, String eartag) {
        String replace = eartag.replace("-", "");
        LogUtil.d(TAG, "扫描的耳标" + replace);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                for (ImmuneEarTagBean earTagBean : earTagListAdapter.getDataList()) {
                    if (earTagBean.EarTag.equals(replace)) {
                        Objects.requireNonNull(RxToast.warning(EarTagActivity.this, "该耳标已存在，请重新扫描"));
                        return;
                    }
                }

                ImmuneEarTagBean immuneEarTagBean = new ImmuneEarTagBean();
                immuneEarTagBean.EarTag = replace;
                immuneEarTagBeanList.add(immuneEarTagBean);

                binding.notDataRl.setVisibility(View.GONE);
                binding.recyclerview.setVisibility(View.VISIBLE);
                earTagListAdapter.refreshDataList(immuneEarTagBeanList);
                if (earTagListAdapter.getDataList().size() > 0) {
                    binding.chooseAllTv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                bleListScanAdapter.refreshDataList(blueDeviceList);
            }
        }
    };


    /**
     * 显示Ble list 列表
     */
    private void showBleListDialog() {
        showBleDialogBle = true;
        View view = getLayoutInflater().inflate(R.layout.layout_ble_list, null);
        exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(true);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        RecyclerView recyclerview = view.findViewById(R.id.recyclerview_ble);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        bleListScanAdapter = new BleListAdapter(R.layout.item_ble_list, this);
        recyclerview.setAdapter(bleListScanAdapter);
        bleListScanAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                BlueDevice data = bleListScanAdapter.getData(position);
                if (data.getDeviceType().equals("TagReader")) {

                    mBlueManager.connectDevice(data);
                    BleSp.getInstance().setTagReaderBle(data.getDeviceMAC());
                    exitDialog.dismiss();
                    showBleDialogBle = false;
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
        Button last_used_ble_bt = view.findViewById(R.id.last_used_ble_bt);
        last_used_ble_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(BleSp.getInstance().getTagReaderBle())) {
                    lastDeviceAddress = BleSp.getInstance().getTagReaderBle();
                    mBlueManager.scanForDevice(BlueManager.BFM | BlueManager.LET);
                    exitDialog.dismiss();
                    showBleDialogBle = false;
                } else {
                    Objects.requireNonNull(RxToast.warning(EarTagActivity.this, "未查询到上次链接的设备~"));
                }
            }
        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }


    private void setBleOpenPer() {
        XXPermissions.with(this).permission(Permission.ACCESS_FINE_LOCATION).permission(Permission.ACCESS_COARSE_LOCATION)
                // 申请多个权限
                .permission(Permission.Group.BLUETOOTH).request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            Objects.requireNonNull(RxToast.info(EarTagActivity.this, "权限获取成功"));

                            if (!mBluetooth.isEnabled()) {
                                showOpenBleDialog();
                            } else {
                                tagReaderBle = BleSp.getInstance().getTagReaderBle();
                                showBleListDialog();
                                if (!TextUtils.isEmpty(tagReaderBle)) { //如果连接过一次 下次就自动连接
                                    mBlueManager.scanForDevice(BlueManager.BFM | BlueManager.LET);
                                } else {
                                    Objects.requireNonNull(RxToast.success(EarTagActivity.this, "开始搜索蓝牙设备..."));
                                    mBlueManager.scanForDevice(BlueManager.BFM | BlueManager.LET);
                                }
                            }
                        } else {
                            Objects.requireNonNull(RxToast.warning(EarTagActivity.this, "获取部分权限成功，但部分权限未正常授予"));
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Objects.requireNonNull(RxToast.error(EarTagActivity.this, "被永久拒绝授权，请手动开启"));
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(EarTagActivity.this, permissions);
                        } else {
                            Objects.requireNonNull(RxToast.error(EarTagActivity.this, "获取权限失败"));
                        }
                    }
                });
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
            Objects.requireNonNull(RxToast.warning(EarTagActivity.this, "本机未找到蓝牙功能"));
        }
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
                    tagReaderBle = BleSp.getInstance().getTagReaderBle();
                    if (!TextUtils.isEmpty(tagReaderBle)) { //如果连接过一次 下次就自动连接
                        mBlueManager.scanForDevice(BlueManager.BFM | BlueManager.LET);
                    } else {
                        Objects.requireNonNull(RxToast.success(EarTagActivity.this, "开始搜索蓝牙设备..."));
                        mBlueManager.scanForDevice(BlueManager.BFM | BlueManager.LET);
                    }
                }
            }, 50);
        } else if (requestCode == 1026) {
            if (resultCode == RESULT_OK) {
                if (ScanEartagManager.getInstance().getScanDeviceType() == ScanEartagManager.ScanDeviceEnum.DEVICE_BLUETOOTH_BIO) {
                    ArrayList<String> egList = data.getStringArrayListExtra("eartaglist");
                    Collections.sort(egList);


                    int nCount = earTagListAdapter.getDataList().size();
                    for (int i = 0; i < nCount; i++) {
                        ImmuneEarTagBean immuneEarTagBean = earTagListAdapter.getDataList().get(i);
                        if (egList.contains(immuneEarTagBean.EarTag)) {
                            egList.remove(immuneEarTagBean.EarTag);
                        }
                    }
                    nCount = egList.size();
                    for (int i = 0; i < nCount; i++) {
                        ImmuneEarTagBean eb = new ImmuneEarTagBean();
                        eb.EarTag = egList.get(i);
                        earTagListAdapter.getDataList().add(0, eb);
                    }
                    binding.notDataRl.setVisibility(View.GONE);
                    earTagListAdapter.notifyDataSetChanged();
                    if (earTagListAdapter.getDataList().size() > 0) {
                        binding.chooseAllTv.setVisibility(View.VISIBLE);
                    }

                }
            }

        }
    }


    /**
     * 注册广播
     */
    private void registryBroadcast() {
        registerReceiver(broadcast, new IntentFilter(Common.SERVICE_BROADCAST_ACTION_FILTER_SUSPENSION));
        registerReceiver(broadcast, new IntentFilter(Common.SERVICE_BROADCAST_ACTION_TAGCHECK));
        registerReceiver(broadcast, new IntentFilter(Common.SERVICE_BROADCAST_ACTION_DEVICE));
    }

    /**
     * 解注册
     */
    private void unRegistryBroadcast() {
        unregisterReceiver(broadcast);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registryBroadcast();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegistryBroadcast();
        //注销观察者
        //Deregister observer
        mBlueManager.deregisterObserver(this);
        //取消扫描
        //Cancel scan
        mBlueManager.cancelScan();
        //断开当前连接
        //Disconnect device
        mBlueManager.disconnectDevice(cunnectDevice);
    }

}
