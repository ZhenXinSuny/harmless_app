package com.agridata.cdzhdj.activity.harmless;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.BleSp;
import com.agridata.cdzhdj.activity.adapter.AnimalMenuAdapter;
import com.agridata.cdzhdj.activity.adapter.BleListAdapter;
import com.agridata.cdzhdj.activity.adapter.onDelListener;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.AnimalMenuBean;
import com.agridata.cdzhdj.data.harmless.EarTagCheckBean;
import com.agridata.cdzhdj.databinding.ActivityAddAnimalMenuBinding;
import com.agridata.cdzhdj.utils.ActivityManager;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.bottomPopupDialog.BottomPopupDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lzx.utils.RxToast;
import com.org.smartbluekit.BlueDevice;
import com.org.smartbluekit.BlueManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:38.    动物清单
 */
public class AddAnimalMenuActivity extends BaseActivity<ActivityAddAnimalMenuBinding> implements onDelListener, BlueManager.BlueManagerCallback {
    private final static String TAG = "lzx------》";


    private ArrayList<AnimalMenuBean> animalMenuBeans;
    private AnimalMenuAdapter animalMenuAdapter;
    private BleListAdapter bleListScanAdapter;


    private BlueManager mBlueManager = null;
    private BlueDevice cunnectDevice = null;
    private List<BlueDevice> blueDeviceList;
    private Boolean showBleDialogBle = false;
    private AlertDialog exitDialog;
    private String tagReaderBle;
    private String AnimalName;
    private String AnimalId;
    private int AnimalType = 3;

    private int ShiFouShiMuZhu = 0;

    private CustomLoadingDialog mLoadingDialog;

    private String isCheckTagTagCode;

    public static void start(Activity activity, String animalName, String animalID) {
        Intent intent = new Intent(activity, AddAnimalMenuActivity.class);
        intent.putExtra("animalName", animalName);
        intent.putExtra("animalID", animalID);
        activity.startActivityForResult(intent, 1001);
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        this.AnimalName = this.getIntent().getStringExtra("animalName");
        this.AnimalId = this.getIntent().getStringExtra("animalID");

        LogUtil.d(TAG, "传过来的动物ID" + this.AnimalId);
        if (this.AnimalId.equals("1")) {
            binding.animalGson.setVisibility(View.VISIBLE);
            binding.animalGsonNiu.setVisibility(View.GONE);
            binding.animalGsonYang.setVisibility(View.GONE);
            binding.shuliangLl.setVisibility(View.GONE);
            binding.muzhuLl.setVisibility(View.VISIBLE);

        } else if (this.AnimalId.equals("2")) {
            binding.animalGson.setVisibility(View.GONE);
            binding.animalGsonYang.setVisibility(View.GONE);
            binding.animalGsonNiu.setVisibility(View.VISIBLE);
            binding.shuliangLl.setVisibility(View.GONE);
            AnimalType = 1;
        } else if (this.AnimalId.equals("3")) {
            binding.animalGson.setVisibility(View.GONE);
            binding.animalGsonYang.setVisibility(View.VISIBLE);
            binding.animalGsonNiu.setVisibility(View.GONE);
            binding.shuliangLl.setVisibility(View.GONE);
        } else if (this.AnimalId.equals("6") || this.AnimalId.equals("7") || this.AnimalId.equals("8")) {
            binding.zongZhongLiangTv.setText("总重量:");
            binding.eartagLl.setVisibility(View.GONE);
            binding.animalGson.setVisibility(View.GONE);
            binding.animalGsonYang.setVisibility(View.GONE);
            binding.animalGsonNiu.setVisibility(View.GONE);
            binding.eartagBtn.setVisibility(View.GONE);
            binding.shuliangLl.setVisibility(View.VISIBLE);

        } else {
            binding.zongZhongLiangTv.setText("总重量:");
            binding.eartagLl.setVisibility(View.GONE);
            binding.animalGson.setVisibility(View.GONE);
            binding.animalGsonYang.setVisibility(View.GONE);
            binding.animalGsonNiu.setVisibility(View.GONE);
            binding.eartagBtn.setVisibility(View.GONE);
            binding.shuliangLl.setVisibility(View.GONE);
        }

    }

    @Override
    protected ActivityAddAnimalMenuBinding getBinding() {
        return ActivityAddAnimalMenuBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        getArguments();


        animalMenuBeans = new ArrayList<>();
        blueDeviceList = new ArrayList<>();
        //获取蓝牙管理器
        //Get the bluetooth manager.
        mBlueManager = BlueManager.getInstance(this);
        //注册观察者
        //Registered observer
        mBlueManager.registerObserver(this);


        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setImageResource(R.drawable.title_back);
        binding.titlebarMiddle.setText("添加动物清单");
        binding.titlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancel();
            }
        });

        binding.recyclerAnimal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerAnimal.setNestedScrollingEnabled(false);
        animalMenuAdapter = new AnimalMenuAdapter(R.layout.item_animal_menu, this);
        animalMenuAdapter.setDel(this);
        binding.recyclerAnimal.setAdapter(animalMenuAdapter);


        setOnCheckedChange(this.AnimalId);

        binding.addBtn.setOnClickListener(v -> {
            LogUtil.d(TAG, "确定按钮");
            switch (AnimalId) {
                case "1"://猪
                    if (AnimalType == 3) {
                        if (TextUtils.isEmpty(binding.weightEt.getText().toString())) {
                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入重量"));
                            return;
                        }
                        if (!TextUtils.isEmpty(binding.weightEt.getText().toString()) && Integer.parseInt(binding.weightEt.getText().toString()) < 20) {
                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入大于20kg的数"));
                            return;
                        }
                        if (TextUtils.isEmpty(binding.eartagEt.getText().toString())) {
                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "当数量大于20kg时，请填写动物耳标"));
                            return;
                        }
                        if (binding.eartagEt.getText().toString().length() > 15 || binding.eartagEt.getText().toString().length() < 15) {
                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入15位耳标数量"));
                            return;
                        }
                    } else if (AnimalType == 2) {
                        if (!TextUtils.isEmpty(binding.weightEt.getText().toString()) ){
                            if ( Integer.parseInt(binding.weightEt.getText().toString()) >= 20 || Integer.parseInt(binding.weightEt.getText().toString()) < 5) {
                                Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入大于等于5kg小于等于20kg的数"));
                                return;
                            }
                        }
                        if (TextUtils.isEmpty(binding.eartagEt.getText().toString())) {
                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "当数量大于等于20kg时，请填写动物耳标"));
                            return;
                        }
                        if (binding.eartagEt.getText().toString().length() > 15 || binding.eartagEt.getText().toString().length() < 15) {
                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入15位耳标数量"));
                            return;
                        }
                    } else {
                        if (TextUtils.isEmpty(binding.weightEt.getText().toString())) {
                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入总重量"));
                            return;
                        }
                        if (TextUtils.isEmpty(binding.shuliangPigEt.getText().toString())) {
                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入数量"));
                            return;
                        }
                    }

                    AnimalMenuBean animalMenuBean = new AnimalMenuBean();
                    if (AnimalType == 3) {
                        animalMenuBean.EarTag = binding.eartagEt.getText().toString();
                        animalMenuBean.AnimalType = 3;
                        animalMenuBean.ShuLiang = "1";
                        LogUtil.d(TAG, "大于20kg");
                        binding.radioGroupWeightMuzhu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                LogUtil.d(TAG, "是否母猪");
                                if (checkedId == R.id.shi_muzhu) {
                                    ShiFouShiMuZhu = 1;
                                    LogUtil.d(TAG, "是母猪" + ShiFouShiMuZhu);
                                } else if (checkedId == R.id.bushi_muzhu) {
                                    ShiFouShiMuZhu = 0;
                                    LogUtil.d(TAG, "不是母猪" + ShiFouShiMuZhu);

                                }
                            }
                        });
                    } else if (AnimalType == 2) {
                        animalMenuBean.EarTag = binding.eartagEt.getText().toString();
                        animalMenuBean.AnimalType = 2;
                        animalMenuBean.ShuLiang = "1";
                    } else {
                        animalMenuBean.EarTag = "-";
                        animalMenuBean.AnimalType = 1;
                        animalMenuBean.ShuLiang = binding.shuliangPigEt.getText().toString();
                    }
                    animalMenuBean.Weight = binding.weightEt.getText().toString();
                    animalMenuBean.AnimalName = AnimalName;
                    animalMenuBean.AnimalID = AnimalId;
                    animalMenuBean.ShiFouMuPig = ShiFouShiMuZhu;

                    if (animalMenuBeans != null && !animalMenuBeans.isEmpty()) {
                        for (int i = 0; i < animalMenuBeans.size(); i++) {
                            if (!"-".equals(animalMenuBeans.get(i).EarTag)) {
                                if (animalMenuBeans.get(i).EarTag.equals(binding.eartagEt.getText().toString())) {
                                    Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "输入耳标重复，请重新输入或修改"));
                                    return;
                                }
                            }
                        }

                    }
                    //151013203130375
                    if (AnimalType == 3 || AnimalType == 2) {
                        HttpRequest.checkEartagIsusedNew(this, binding.eartagEt.getText().toString(), new CallBackLis<>() {
                            @Override
                            public void onSuccess(String method, EarTagCheckBean content) {
                                LogUtil.d("lzx---------》", content.toString());
                                if (content.code == 200) {
                                    if (content.data.code==1) {
                                        Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, content.data.message));
                                    } else {
                                        animalMenuBeans.add(animalMenuBean);
                                        binding.noDataIv.setVisibility(View.GONE);
                                        binding.recyclerAnimal.setVisibility(View.VISIBLE);
                                        LogUtil.d(TAG, "动物清单数据" + animalMenuBeans.toString());
                                        animalMenuAdapter.refreshDataList(animalMenuBeans);
                                        if (AnimalType == 3 || AnimalType == 2) {
                                            binding.eartagEt.setText("");
                                            binding.weightEt.setText("");
                                        } else {
                                            binding.weightEt.setText("");
                                            binding.shuliangPigEt.setText("");
                                        }
                                        binding.hejiTv.setText(animalMenuBeans.size() + "");
                                        if (animalMenuBeans.size() > 0) {
                                            binding.hejiAllLl.setVisibility(View.VISIBLE);
                                        }
                                        int allZhu = 0;
                                        int allZhuShuLiang = 0;
                                        for (AnimalMenuBean menuBean : animalMenuBeans) {
                                            LogUtil.d(TAG, "转换" + Integer.parseInt(menuBean.Weight));
                                            allZhu = allZhu + Integer.parseInt(menuBean.Weight);
                                            allZhuShuLiang = allZhuShuLiang + Integer.parseInt(menuBean.ShuLiang);
                                        }
                                        LogUtil.d(TAG, "animalMenuBeans" + animalMenuBeans.toString());

                                        binding.allShuliangTv.setVisibility(View.VISIBLE);
                                        binding.allShuliangTv.setText("总数量：" + allZhuShuLiang);
                                        binding.hejiTv.setText("合计:" + animalMenuBeans.size());
                                        binding.allZhongliangTv.setText("总重量:" + allZhu + "kg");
                                    }
                                } else {
                                    Objects.requireNonNull(RxToast.error(AddAnimalMenuActivity.this, content.msg));
                                }
                            }

                            @Override
                            public void onFailure(String method, String error) {
                                Objects.requireNonNull(RxToast.error(AddAnimalMenuActivity.this, error));
                            }
                        });

                    } else {
                        animalMenuBeans.add(animalMenuBean);
                        binding.noDataIv.setVisibility(View.GONE);
                        binding.recyclerAnimal.setVisibility(View.VISIBLE);
                        LogUtil.d(TAG, "动物清单数据" + animalMenuBeans.toString());
                        animalMenuAdapter.refreshDataList(animalMenuBeans);

                        if (AnimalType == 3 || AnimalType == 2) {
                            binding.eartagEt.setText("");
                            binding.weightEt.setText("");
                        } else {
                            binding.weightEt.setText("");
                            binding.shuliangPigEt.setText("");
                        }
                        binding.hejiTv.setText(animalMenuBeans.size() + "");
                        if (!animalMenuBeans.isEmpty()) {
                            binding.hejiAllLl.setVisibility(View.VISIBLE);
                        }
                        int allZhu = 0;
                        int allZhuShuLiang = 0;
                        for (AnimalMenuBean menuBean : animalMenuBeans) {
                            LogUtil.d(TAG, "转换" + Integer.parseInt(menuBean.Weight));
                            allZhu = allZhu + Integer.parseInt(menuBean.Weight);
                            allZhuShuLiang = allZhuShuLiang + Integer.parseInt(menuBean.ShuLiang);
                        }
                        LogUtil.d(TAG, "animalMenuBeans" + animalMenuBeans.toString());
                        binding.allShuliangTv.setVisibility(View.VISIBLE);
                        binding.allShuliangTv.setText("总数量：" + allZhuShuLiang);
                        binding.hejiTv.setText("合计:" + animalMenuBeans.size());
                        binding.allZhongliangTv.setText("总重量:" + allZhu + "kg");
                    }


                    break;
                case "2"://牛
                    if (TextUtils.isEmpty(binding.weightEt.getText().toString())) {
                        Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入重量"));
                        return;
                    }
                    if (TextUtils.isEmpty(binding.eartagEt.getText().toString())) {
                        Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入耳标号"));
                        return;
                    }
                    if (binding.eartagEt.getText().toString().length() > 15 || binding.eartagEt.getText().toString().length() < 15) {
                        Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入15位耳标数量"));
                        return;
                    }
                    AnimalMenuBean animalMenuBeanNiu = new AnimalMenuBean();
                    if (AnimalType == 3) {
                        animalMenuBeanNiu.AnimalType = 3;
                        animalMenuBeanNiu.ShuLiang = "1";
                    } else if (AnimalType == 2) {
                        animalMenuBeanNiu.AnimalType = 2;
                        animalMenuBeanNiu.ShuLiang = "1";
                    } else {
                        animalMenuBeanNiu.AnimalType = 1;
                        animalMenuBeanNiu.ShuLiang = "1";
                    }
                    animalMenuBeanNiu.EarTag = binding.eartagEt.getText().toString();
                    animalMenuBeanNiu.Weight = binding.weightEt.getText().toString();
                    animalMenuBeanNiu.AnimalName = AnimalName;
                    animalMenuBeanNiu.AnimalID = AnimalId;

                    if (animalMenuBeans != null && animalMenuBeans.size() > 0) {
                        for (int i = 0; i < animalMenuBeans.size(); i++) {
                            if (!"-".equals(animalMenuBeans.get(i).EarTag)) {
                                if (animalMenuBeans.get(i).EarTag.equals(binding.eartagEt.getText().toString())) {
                                    Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "输入耳标重复，请重新输入或修改"));
                                    return;
                                }
                            }
                        }
                    }
                    HttpRequest.checkEartagIsusedNew(this, binding.eartagEt.getText().toString(), new CallBackLis<>() {
                        @Override
                        public void onSuccess(String method, EarTagCheckBean content) {
                            LogUtil.d("lzx---------》", content.toString());
                            if (content.code == 200) {
                                if (content.data.code==1) {
                                    Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, content.data.message));
                                }  else {
                                    animalMenuBeans.add(animalMenuBeanNiu);
                                    binding.noDataIv.setVisibility(View.GONE);
                                    binding.recyclerAnimal.setVisibility(View.VISIBLE);
                                    LogUtil.d(TAG, "动物清单数据" + animalMenuBeans.toString());
                                    animalMenuAdapter.refreshDataList(animalMenuBeans);
                                    binding.eartagEt.setText("");
                                    binding.weightEt.setText("");
                                    binding.hejiTv.setText(animalMenuBeans.size() + "");
                                    if (animalMenuBeans.size() > 0) {
                                        binding.hejiAllLl.setVisibility(View.VISIBLE);
                                    }
                                    Double allNiu = 0.0;
                                    int allNuiShuLiang = 0;
                                    for (AnimalMenuBean menuBean : animalMenuBeans) {

                                        LogUtil.d(TAG, "转换" + Integer.parseInt(menuBean.Weight));
                                        allNiu = allNiu + Integer.parseInt(menuBean.Weight);
                                        allNuiShuLiang = allNuiShuLiang + Integer.parseInt(menuBean.ShuLiang);
                                    }
                                    binding.hejiTv.setText("合计:" + animalMenuBeans.size());
                                    binding.allZhongliangTv.setText("总重量:" + allNiu + "kg");
                                    LogUtil.d(TAG, "animalMenuBeans" + animalMenuBeans.toString());

                                    binding.allShuliangTv.setVisibility(View.VISIBLE);
                                    binding.allShuliangTv.setText("总数量：" + allNuiShuLiang);
                                }
                            } else {
                                Objects.requireNonNull(RxToast.error(AddAnimalMenuActivity.this, content.msg));
                            }
                        }

                        @Override
                        public void onFailure(String method, String error) {
                            Objects.requireNonNull(RxToast.error(AddAnimalMenuActivity.this, error));
                        }
                    });

                    break;


                case "3"://羊
                    if (AnimalType == 3) {
                        if (TextUtils.isEmpty(binding.weightEt.getText().toString())) {
                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入重量"));
                            return;
                        }
                        if (Integer.parseInt(binding.weightEt.getText().toString()) < 15) {
                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入大于15kg的数"));
                            return;
                        }
                        if (TextUtils.isEmpty(binding.eartagEt.getText().toString())) {
                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "当数量大于15kg时，请填写动物耳标"));
                            return;
                        }
                        if (binding.eartagEt.getText().toString().length() > 15 || binding.eartagEt.getText().toString().length() < 15) {
                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入15位耳标数量"));
                            return;
                        }
                    } else if (AnimalType == 2) {
                        if (Integer.parseInt(binding.weightEt.getText().toString()) > 15 || Integer.parseInt(binding.weightEt.getText().toString()) < 5) {

                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入大于等于5kg小于等于15kg的数"));
                            return;
                        }
                        if (TextUtils.isEmpty(binding.eartagEt.getText().toString())) {


                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "当数量大于等于5kg小于等于15kg时，请填写动物耳标"));
                            return;
                        }
                        if (binding.eartagEt.getText().toString().length() > 15 || binding.eartagEt.getText().toString().length() < 15) {

                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入15位耳标数量"));
                            return;
                        }
                    } else {
                        if (Integer.parseInt(binding.weightEt.getText().toString()) > 5) {
                            Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入小于5kg的数"));
                            return;
                        }
                    }

                    AnimalMenuBean animalMenuBeanYang = new AnimalMenuBean();
                    if (AnimalType == 3) {
                        animalMenuBeanYang.EarTag = binding.eartagEt.getText().toString();
                        animalMenuBeanYang.AnimalType = 3;

                    } else if (AnimalType == 2) {
                        animalMenuBeanYang.EarTag = binding.eartagEt.getText().toString();
                        animalMenuBeanYang.AnimalType = 2;
                    } else {
                        animalMenuBeanYang.EarTag = "-";
                        animalMenuBeanYang.AnimalType = 1;
                    }
                    animalMenuBeanYang.ShuLiang = "1";
                    animalMenuBeanYang.Weight = binding.weightEt.getText().toString();
                    animalMenuBeanYang.AnimalName = AnimalName;
                    animalMenuBeanYang.AnimalID = AnimalId;


                    if (animalMenuBeans != null && !animalMenuBeans.isEmpty()) {
                        for (int i = 0; i < animalMenuBeans.size(); i++) {
                            if (!"-".equals(animalMenuBeans.get(i).EarTag)) {
                                if (animalMenuBeans.get(i).EarTag.equals(binding.eartagEt.getText().toString())) {
                                    Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "输入耳标重复，请重新输入或修改"));
                                    return;
                                }
                            }


                        }
                    }
                    if (AnimalType == 3 || AnimalType == 2) {
                        HttpRequest.checkEartagIsusedNew(this, binding.eartagEt.getText().toString(), new CallBackLis<>() {
                            @Override
                            public void onSuccess(String method, EarTagCheckBean content) {
                                LogUtil.d("lzx---------》", content.toString());
                                if (content.code == 200) {
                                    if (content.data.code==1) {
                                        Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, content.data.message));
                                    }  else {
                                        animalMenuBeans.add(animalMenuBeanYang);

                                        binding.noDataIv.setVisibility(View.GONE);
                                        binding.recyclerAnimal.setVisibility(View.VISIBLE);
                                        LogUtil.d(TAG, "动物清单数据" + animalMenuBeans.toString());
                                        animalMenuAdapter.refreshDataList(animalMenuBeans);
                                        if (AnimalType == 3 || AnimalType == 2) {
                                            binding.eartagEt.setText("");
                                            binding.weightEt.setText("");
                                        } else {
                                            binding.weightEt.setText("");
                                        }
                                        binding.hejiTv.setText(animalMenuBeans.size() + "");
                                        if (animalMenuBeans.size() > 0) {
                                            binding.hejiAllLl.setVisibility(View.VISIBLE);
                                        }
                                        Double all = 0.0;
                                        int allYangShuLiang = 0;
                                        for (AnimalMenuBean menuBean : animalMenuBeans) {
                                            LogUtil.d(TAG, "转换" + Integer.parseInt(menuBean.Weight));
                                            all = all + Integer.parseInt(menuBean.Weight);
                                            allYangShuLiang = allYangShuLiang + Integer.parseInt(menuBean.ShuLiang);
                                        }
                                        binding.hejiTv.setText("合计:" + animalMenuBeans.size());
                                        binding.allZhongliangTv.setText("总重量:" + all + "kg");
                                        LogUtil.d(TAG, "animalMenuBeans" + animalMenuBeans.toString());
                                        binding.allShuliangTv.setVisibility(View.VISIBLE);
                                        binding.allShuliangTv.setText("总数量：" + allYangShuLiang);
                                    }
                                } else {
                                    Objects.requireNonNull(RxToast.error(AddAnimalMenuActivity.this, content.msg));
                                }
                            }

                            @Override
                            public void onFailure(String method, String error) {
                                Objects.requireNonNull(RxToast.error(AddAnimalMenuActivity.this, error));
                            }
                        });
                    } else {
                        animalMenuBeans.add(animalMenuBeanYang);
                        binding.noDataIv.setVisibility(View.GONE);
                        binding.recyclerAnimal.setVisibility(View.VISIBLE);
                        LogUtil.d(TAG, "动物清单数据" + animalMenuBeans.toString());
                        animalMenuAdapter.refreshDataList(animalMenuBeans);

                        if (AnimalType == 3 || AnimalType == 2) {
                            binding.eartagEt.setText("");
                            binding.weightEt.setText("");
                        } else {
                            binding.weightEt.setText("");
                        }

                        binding.hejiTv.setText(animalMenuBeans.size() + "");
                        if (animalMenuBeans.size() > 0) {
                            binding.hejiAllLl.setVisibility(View.VISIBLE);
                        }
                        Double all = 0.0;
                        int allYangShuLiang = 0;
                        for (AnimalMenuBean menuBean : animalMenuBeans) {

                            LogUtil.d(TAG, "转换" + Integer.parseInt(menuBean.Weight));

                            all = all + Integer.parseInt(menuBean.Weight);
                            allYangShuLiang = allYangShuLiang + Integer.parseInt(menuBean.ShuLiang);
                        }
                        binding.hejiTv.setText("合计:" + animalMenuBeans.size());
                        binding.allZhongliangTv.setText("总重量:" + all + "kg");
                        LogUtil.d(TAG, "animalMenuBeans" + animalMenuBeans.toString());

                        binding.allShuliangTv.setVisibility(View.VISIBLE);
                        binding.allShuliangTv.setText("总数量：" + allYangShuLiang);

                    }


                    break;


                case "6":
                case "7":
                case "8":
                    if (TextUtils.isEmpty(binding.weightEt.getText().toString())) {
                        Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入重量"));
                        return;
                    }
                    if (TextUtils.isEmpty(binding.shuliangEt.getText().toString())) {
                        Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入数量"));
                        return;
                    }
                    AnimalMenuBean animalMenuBeanJYE = new AnimalMenuBean();
                    animalMenuBeanJYE.AnimalType = 0;
                    animalMenuBeanJYE.EarTag = "-";
                    animalMenuBeanJYE.Weight = binding.weightEt.getText().toString();
                    animalMenuBeanJYE.AnimalName = AnimalName;
                    animalMenuBeanJYE.AnimalID = AnimalId;

                    animalMenuBeanJYE.ShuLiang = binding.shuliangEt.getText().toString();
                    animalMenuBeans.add(animalMenuBeanJYE);
                    binding.noDataIv.setVisibility(View.GONE);
                    binding.recyclerAnimal.setVisibility(View.VISIBLE);
                    LogUtil.d(TAG, "动物清单数据" + animalMenuBeans.toString());
                    animalMenuAdapter.refreshDataList(animalMenuBeans);

                    binding.weightEt.setText("");
                    binding.shuliangEt.setText("");
                    binding.hejiTv.setText(animalMenuBeans.size() + "");
                    if (!animalMenuBeans.isEmpty()) {
                        binding.hejiAllLl.setVisibility(View.VISIBLE);
                    }
                    Double allJYE = 0.0;

                    int allJYNum = 0;
                    for (AnimalMenuBean menuBean : animalMenuBeans) {

                        LogUtil.d(TAG, "转换" + Integer.parseInt(menuBean.Weight));

                        allJYE = allJYE + Integer.parseInt(menuBean.Weight);

                        allJYNum = allJYNum + Integer.parseInt(menuBean.ShuLiang);
                    }
                    binding.hejiTv.setText("合计:" + animalMenuBeans.size());
                    binding.allZhongliangTv.setText("总重量:" + allJYE + "kg");
                    binding.allShuliangTv.setVisibility(View.VISIBLE);
                    binding.allShuliangTv.setText("总数量:" + allJYNum);
                    LogUtil.d(TAG, "animalMenuBeans" + animalMenuBeans.toString());

                    break;

                //其他
                case "16":

                case "95":
                case "250":
                    if (TextUtils.isEmpty(binding.weightEt.getText().toString())) {
                        Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "请输入重量"));
                        return;
                    }
                    AnimalMenuBean animalMenuBeanQiTa = new AnimalMenuBean();
                    animalMenuBeanQiTa.AnimalType = 0;
                    animalMenuBeanQiTa.EarTag = "-";
                    animalMenuBeanQiTa.Weight = binding.weightEt.getText().toString();
                    animalMenuBeanQiTa.AnimalName = AnimalName;
                    animalMenuBeanQiTa.AnimalID = AnimalId;
                    animalMenuBeanQiTa.ShuLiang = "1";
                    animalMenuBeans.add(animalMenuBeanQiTa);
                    binding.noDataIv.setVisibility(View.GONE);
                    binding.recyclerAnimal.setVisibility(View.VISIBLE);
                    LogUtil.d(TAG, "动物清单数据" + animalMenuBeans.toString());
                    animalMenuAdapter.refreshDataList(animalMenuBeans);

                    binding.weightEt.setText("");

                    binding.hejiTv.setText(animalMenuBeans.size() + "");
                    if (animalMenuBeans.size() > 0) {
                        binding.hejiAllLl.setVisibility(View.VISIBLE);
                    }
                    Double all1 = 0.0;
                    int allQiTaShuLiang = 0;
                    for (AnimalMenuBean menuBean : animalMenuBeans) {

                        LogUtil.d(TAG, "转换" + Integer.parseInt(menuBean.Weight));

                        all1 = all1 + Integer.parseInt(menuBean.Weight);
                        allQiTaShuLiang = allQiTaShuLiang + Integer.parseInt(menuBean.ShuLiang);
                    }
                    binding.hejiTv.setText("合计:" + animalMenuBeans.size());
                    binding.allZhongliangTv.setText("总重量:" + all1 + "kg");
                    binding.allShuliangTv.setVisibility(View.VISIBLE);
                    binding.allShuliangTv.setText("总数量:" + allQiTaShuLiang);
                    LogUtil.d(TAG, "animalMenuBeans" + animalMenuBeans.toString());
                    break;
            }

        });


        binding.saveTv.setOnClickListener(v -> {
            if (!animalMenuBeans.isEmpty()) {
                Intent intent = new Intent();
                intent.putExtra("data", (Serializable) animalMenuBeans);
                setResult(RESULT_OK, intent);
                finish();

            } else {
                Objects.requireNonNull(RxToast.warning(AddAnimalMenuActivity.this, "您当前暂无数据，无法保存。请添加数据..."));
            }
        });
        binding.eartagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tagReaderBle = BleSp.getInstance().getTagReaderBle();
                if (!TextUtils.isEmpty(tagReaderBle)) { //如果连接过一次 下次就自动连接
                    mBlueManager.scanForDevice(BlueManager.BFM | BlueManager.LET);

                } else {
                    showBleDialog();
                }

            }
        });

        binding.duankaiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cunnectDevice != null) {
                    //断开当前连接
                    //Disconnect device
                    mBlueManager.disconnectDevice(cunnectDevice);

                }
            }
        });
    }


    private void setOnCheckedChange(String AnimalID) {
        switch (AnimalID) {
            case "1": //猪
                binding.radioGroupWeight.setOnCheckedChangeListener((group, checkedId) -> {
                    if (checkedId == R.id.weight_big) {
                        binding.zongZhongLiangTv.setText("重量数:");
                        binding.eartagLl.setVisibility(View.VISIBLE);
                        binding.shuliangPigLl.setVisibility(View.GONE);
                        binding.eartagEt.setInputType(InputType.TYPE_CLASS_TEXT);
                        binding.weightEt.setText("");
                        binding.eartagEt.setText("");
                        binding.eartagBtn.setVisibility(View.VISIBLE);
                        binding.muzhuLl.setVisibility(View.VISIBLE);
                        AnimalType = 3;
                    } else if (checkedId == R.id.weight_small) {
                        binding.zongZhongLiangTv.setText("重量数:");
                        binding.muzhuLl.setVisibility(View.GONE);
                        binding.eartagEt.setInputType(InputType.TYPE_CLASS_TEXT);
                        binding.weightEt.setText("");
                        binding.eartagEt.setText("");
                        binding.eartagLl.setVisibility(View.VISIBLE);
                        binding.shuliangPigLl.setVisibility(View.GONE);
                        binding.eartagBtn.setVisibility(View.VISIBLE);
                        AnimalType = 2;
                    } else if (checkedId == R.id.five_small) { //小于5公斤一下 显示 重量+数量
                        binding.muzhuLl.setVisibility(View.GONE);
                        binding.shuliangPigLl.setVisibility(View.VISIBLE);
                        binding.eartagLl.setVisibility(View.GONE);
                        binding.weightEt.setText("");
                        binding.zongZhongLiangTv.setText("总重量:");
                        AnimalType = 1;
                    }
                });
                break;
            case "2"://牛
                binding.radioGroupWeightNiu.setOnCheckedChangeListener((group, checkedId) -> {
                    if (checkedId == R.id.chan_nai_niu) {
                        binding.weightEt.setText("");
                        binding.eartagEt.setText("");
                        AnimalType = 1;
                    } else if (checkedId == R.id.yu_cheng_niu) {
                        binding.weightEt.setText("");
                        binding.eartagEt.setText("");
                        AnimalType = 2;
                    } else if (checkedId == R.id.du_niu) {
                        binding.weightEt.setText("");
                        AnimalType = 3;
                    }
                });
                binding.eartagEt.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.eartagBtn.setVisibility(View.VISIBLE);
                break;

            case "3"://羊
                binding.radioGroupWeightYang.setOnCheckedChangeListener((group, checkedId) -> {
                    if (checkedId == R.id.weight_big_yang) {
                        binding.eartagEt.setInputType(InputType.TYPE_CLASS_TEXT);
                        binding.weightEt.setText("");
                        binding.eartagEt.setText("");
                        binding.eartagBtn.setVisibility(View.VISIBLE);
                        AnimalType = 3;
                    } else if (checkedId == R.id.weight_small_yang) {
                        binding.weightEt.setText("");
                        binding.eartagEt.setText("");
                        binding.eartagEt.setInputType(InputType.TYPE_CLASS_TEXT);
                        binding.eartagBtn.setVisibility(View.VISIBLE);
                        AnimalType = 2;
                    } else if (checkedId == R.id.five_small_yang) {
                        binding.weightEt.setText("");
                        binding.eartagEt.setText("-");
                        binding.eartagEt.setInputType(InputType.TYPE_NULL);
                        binding.eartagBtn.setVisibility(View.GONE);
                        AnimalType = 1;
                    }
                });

                break;
        }

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void delete(int position) {
        LogUtil.d(TAG, "POS" + position);
        animalMenuAdapter.removeData(position);
        List<AnimalMenuBean> dataList = animalMenuAdapter.getDataList();
        if (dataList.size() == 0) {
            binding.hejiAllLl.setVisibility(View.GONE);
            binding.recyclerAnimal.setVisibility(View.GONE);
            binding.noDataIv.setVisibility(View.VISIBLE);
        } else {
            animalMenuAdapter.refreshDataList(dataList);
            int all = 0;
            int allNum = 0;
            for (AnimalMenuBean menuBean : dataList) {
                all = all + Integer.parseInt(menuBean.Weight);
                allNum = allNum + Integer.parseInt(menuBean.ShuLiang);
            }
            binding.hejiTv.setText("合计:" + dataList.size());
            binding.allZhongliangTv.setText("总重量:" + all + "kg");
            binding.allShuliangTv.setText("总数量:" + allNum);
        }
    }

    private void showCancel() {
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
        title_tv.setText("放弃数据保存");
        contentTv.setText("您确定要放弃数据保存吗？放弃需重新添加。");
        cancelTv.setOnClickListener(view12 -> exitDialogLoginOut.dismiss());
        confirmTv.setOnClickListener(v -> {
            exitDialogLoginOut.dismiss();
            Intent intent = new Intent();
            intent.putExtra("data", (Serializable) animalMenuBeans);
            setResult(100, intent);
            finish();
        });
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }

    /**
     * 蓝牙弹框
     */
    private void showBleDialog() {
        List<String> bottomDialogContents = new ArrayList<>();
        bottomDialogContents.add("TagReader低频蓝牙(银色)");
        final BottomPopupDialog bottomPopupDialog = new BottomPopupDialog(this, bottomDialogContents);
        bottomPopupDialog.showCancelBtn(true);
        bottomPopupDialog.setTitleText("选择蓝牙设备");
        bottomPopupDialog.setTitleColor(R.color.Red);
        bottomPopupDialog.setTitleSize(18);
        bottomPopupDialog.show();
        bottomPopupDialog.setCancelable(true);
        bottomPopupDialog.setOnItemClickListener((v, position) -> {
            bottomPopupDialog.dismiss();
            if (position == 0) {  //TagReader低频蓝牙(银色)
                if (!checkGPSIsOpen(this)) {
                    //跳转GPS设置界面
                    showOpenGps();
                } else {
                    Objects.requireNonNull(RxToast.normal(AddAnimalMenuActivity.this, "开始搜索蓝牙设备..."));
                    mBlueManager.scanForDevice(BlueManager.BFM | BlueManager.LET);
                }
            }
        });
        bottomPopupDialog.setOnDismissListener(DialogInterface::dismiss);
    }

    /**
     * 已经提交过信息了
     */
    private void showOpenGps() {
        View view = getLayoutInflater().inflate(R.layout.opne_gps_layout, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        contentTv.setText("为了使用蓝牙耳标钳进行耳标抽查，请开启GPS定位服务");
        confirmTv.setOnClickListener(view1 -> {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 666);
            exitDialog.dismiss();
        });
        if (ActivityManager.getInstance().isLiving(AddAnimalMenuActivity.this)) {
            exitDialog.show();
        }
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
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

    /**
     * 显示Ble list 列表
     */
    private void showBleListDialog() {
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
//                    //链接
//                    if (isConnected&&cunnectDevice!=device){
//                        // if you need Connect multiple devices , Here you can not disconnect
//                        mBlueManager.disconnectDevice(cunnectDevice);
//                    }
                    mBlueManager.connectDevice(data);

                    BleSp.getInstance().setTagReaderBle(data.getDeviceMAC());

                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
        exitDialog.show();
        // showBleDialogBle = true;
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666) {
            if (checkGPSIsOpen(this)) {


                Objects.requireNonNull(RxToast.normal(AddAnimalMenuActivity.this, "开始搜索蓝牙设备..."));
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                bleListScanAdapter.refreshDataList(blueDeviceList);
            }
        }
    };

    @Override
    public void onManagerBLEAvailable(boolean b) {

    }

    @Override
    public void onManagerDevicesFound(List<BlueDevice> devices) {
        LogUtil.d(TAG, "查询的蓝牙" + devices.toString());


        if (!TextUtils.isEmpty(tagReaderBle)) {
            for (BlueDevice device : devices) {
                if (device.getDeviceMAC().equals(tagReaderBle)) {
                    mBlueManager.connectDevice(device);
                    break;
                }
            }
            return;
        }
        blueDeviceList.clear();
        for (BlueDevice device : devices) {
            blueDeviceList.add(device);
        }
        if (!showBleDialogBle) {
            showBleListDialog();
        }
        for (BlueDevice blueDevice : blueDeviceList) {
            LogUtil.d(TAG, "查询的蓝牙名字" + blueDevice.getDeviceType());
        }
        handler.sendEmptyMessage(1);

    }

    @Override
    public void onManagerDeviceConnected(BlueDevice blueDevice) {
        LogUtil.d(TAG, "onManagerDeviceConnected: ");
        cunnectDevice = blueDevice;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Objects.requireNonNull(RxToast.success(AddAnimalMenuActivity.this, "连接成功..."));
                binding.duankaiBtn.setVisibility(View.VISIBLE);
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
                binding.duankaiBtn.setVisibility(View.GONE);
                Objects.requireNonNull(RxToast.success(AddAnimalMenuActivity.this, "断开连接成功..."));
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
                binding.eartagEt.setText(replace);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showCancel();

        }
        return false;
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
        //断开当前连接
        //Disconnect device
        mBlueManager.disconnectDevice(cunnectDevice);
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


}
