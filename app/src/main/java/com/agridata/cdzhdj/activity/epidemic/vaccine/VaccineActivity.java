package com.agridata.cdzhdj.activity.epidemic.vaccine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.AnimalSP;
import com.agridata.cdzhdj.SPUtil.ImmuneSp;
import com.agridata.cdzhdj.SPUtil.UpImmuneSP;
import com.agridata.cdzhdj.SPUtil.VaccineDataSp;
import com.agridata.cdzhdj.activity.epidemic.eartag.EarTagActivity;
import com.agridata.cdzhdj.activity.epidemic.immune.ImmuneActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.AnimalToDisBean;
import com.agridata.cdzhdj.data.DiseaseDictionaryBean;
import com.agridata.cdzhdj.data.DiseaseIDBean;
import com.agridata.cdzhdj.data.ImmuneEarTagBean;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.UpImmuneBean;
import com.agridata.cdzhdj.data.UpImmuneDetailsBean;
import com.agridata.cdzhdj.data.VaccineBean;
import com.agridata.cdzhdj.data.immune.ImmuneSaveBean;
import com.agridata.cdzhdj.databinding.ActivityVaccineBinding;
import com.agridata.cdzhdj.utils.ActivityManager;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzx.utils.RxToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-14 16:23.
 * @Description :描述 疫苗录入
 */
public class VaccineActivity extends BaseActivity<ActivityVaccineBinding> implements View.OnClickListener {

    public static final String TAG = "VaccineActivity-----》 ";

    private List<ImmuneEarTagBean> earTagList;


    private List<DiseaseIDBean.Result> diseaseIDBeans;
    private List<AnimalToDisBean> animalToDisBeans;

    private OptionsPickerView DiseasePv, VaccinePv;

    private int diseaseID;

    private List<VaccineBean.Result> vaccineBeanList;
    private String vaccineID;
    private boolean IsforceIm;

    private int Unit = 1;

    private CustomLoadingDialog mLoadingDialog;

    public static void start(Activity context) {
        Intent intent = new Intent(context, VaccineActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityVaccineBinding getBinding() {
        return ActivityVaccineBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {

        initLoadingView();
        binding.diseaseTv.setHintTextColor(getResources().getColor(R.color.T1));
        binding.diseaseTv.setOnClickListener(this);


        binding.vaccineTv.setHintTextColor(getResources().getColor(R.color.T1));
        binding.vaccineTv.setOnClickListener(this);

        binding.vaccineFactoryTv.setHintTextColor(getResources().getColor(R.color.T1));
        binding.batchTv.setHintTextColor(getResources().getColor(R.color.T1));
        binding.doseTv.setHintTextColor(getResources().getColor(R.color.T1));


        diseaseIDBeans = new ArrayList<>();
        animalToDisBeans = new ArrayList<>();
        vaccineBeanList = new ArrayList<>();

        getDiseasesInfo();


        binding.titlebarLeft.setOnClickListener(v -> finish());

        binding.immuneBtn.setOnClickListener(this);


        binding.headOfRb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Unit = 1;
            }
        });
        binding.mlOfRb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Unit = 2;
            }
        });
    }

    @Override
    protected void initDatas() {
        ImmuneSaveBean immuneSaveInfo = ImmuneSp.getInstance().getImmuneSaveInfo();
        if (immuneSaveInfo != null) {
            binding.batchTv.setText(immuneSaveInfo.Batch);
            binding.doseTv.setText(immuneSaveInfo.Capacity);
            binding.vaccineFactoryTv.setText(immuneSaveInfo.VaccineFactory);
            if (immuneSaveInfo.Unit == 1) {
                binding.headOfRb.setChecked(true);
            } else {
                binding.mlOfRb.setChecked(true);
            }
        }
    }

    @Override
    protected void OnEventMainThread() {
        super.OnEventMainThread();
        mRxManager.on("EARTAG_DETAILS", o -> {
            earTagList = (List<ImmuneEarTagBean>) o;
            LogUtil.d(TAG, "传过来的耳标" + earTagList.toString());
        });
    }


    @SuppressLint("CheckResult")
    @Override
    public void onClick(View v) {
        if (v == binding.diseaseTv) {//疾病
            if (animalToDisBeans != null && animalToDisBeans.size() > 0) {
                showDisease();
            } else {
                RxToast.warning(VaccineActivity.this, "当前动物疫病为空");
            }

        } else if (v == binding.vaccineTv) {//疫苗
            if (TextUtils.isEmpty(binding.diseaseTv.getText().toString().trim())) {
                RxToast.warning(VaccineActivity.this, "请选择疫病在选择疫苗");
            } else {
                getVaccineInfo();
            }
        } else if (v == binding.immuneBtn) {//免疫提交
            if (TextUtils.isEmpty(binding.diseaseTv.getText().toString())) {
                RxToast.warning(VaccineActivity.this, "请选择动物疫病");
                return;
            }
            if (TextUtils.isEmpty(binding.vaccineTv.getText().toString())) {
                RxToast.warning(VaccineActivity.this, "请选择动物疫苗");
                return;
            }
            if (TextUtils.isEmpty(binding.vaccineFactoryTv.getText().toString())) {
                RxToast.warning(VaccineActivity.this, "请输入疫苗厂家");
                return;
            }
            if (TextUtils.isEmpty(binding.batchTv.getText().toString())) {
                RxToast.warning(VaccineActivity.this, "请输入批次");
                return;
            }
            if (TextUtils.isEmpty(binding.doseTv.getText().toString())) {
                RxToast.warning(VaccineActivity.this, "请输入剂量");
                return;
            }
            showLoading("免疫上传中...");
            UpLoadImmuneDetails();
        }
    }

    /**
     * 动物动物种类获取疫病  Integer.parseInt(AnimalSP.getInstance().getChooseAnimal().id)
     */
    private void getDiseasesInfo() {
        HttpRequest.getAnimalToDiseaseID(VaccineActivity.this, Integer.parseInt(AnimalSP.getInstance().getChooseAnimal().id), new CallBackLis<DiseaseIDBean>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, DiseaseIDBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    diseaseIDBeans = content.result;
                    getDiseaseDictionaryInfo();
                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }


    /**
     * 获取疫病字典表
     */
    private void getDiseaseDictionaryInfo() {
        HttpRequest.getDiseaseDictionary(VaccineActivity.this, new CallBackLis<DiseaseDictionaryBean>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, DiseaseDictionaryBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    for (DiseaseDictionaryBean.Result result : content.result) {
                        for (DiseaseIDBean.Result diseaseIDBean : diseaseIDBeans) {
                            if (result.key == diseaseIDBean.diseaseID) {
                                AnimalToDisBean animalToDisBean = new AnimalToDisBean();
                                animalToDisBean.DiseaseID = result.key;
                                animalToDisBean.DiseaseName = result.name;
                                animalToDisBeans.add(animalToDisBean);
                            }
                        }
                    }
                }
                LogUtil.i(TAG, "最终的疫病List" + animalToDisBeans.toString());
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }

    /**
     * 根据疫病获取疫苗种类  Integer.parseInt(AnimalSP.getInstance().getChooseAnimal().id)
     */
    private void getVaccineInfo() {
        HttpRequest.getVaccineInfo(VaccineActivity.this, diseaseID, Integer.parseInt(AnimalSP.getInstance().getChooseAnimal().id), new CallBackLis<VaccineBean>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, VaccineBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    vaccineBeanList = content.result;
                    if (!content.result.isEmpty()) {
                        showVaccine();//弹出疫苗新选择框
                    } else {
                        RxToast.warning(VaccineActivity.this, "当前暂无疫苗种类~");
                    }

                } else {
                    RxToast.error(VaccineActivity.this, content.message);
                }
            }

            @SuppressLint("CheckResult")
            @Override
            public void onFailure(String method, String error) {
                RxToast.error(VaccineActivity.this, error);
            }
        });
    }

    private void showDisease() {
        List<String> listString = new ArrayList<>();
        for (AnimalToDisBean animalToDisBean : animalToDisBeans) {
            listString.add(animalToDisBean.DiseaseName);
        }
        DiseasePv = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            diseaseID = animalToDisBeans.get(options1).DiseaseID;
            binding.diseaseTv.setText(animalToDisBeans.get(options1).DiseaseName);
        })
                .setLayoutRes(R.layout.custom_immune_animal_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    final TextView title_tv = (TextView) v.findViewById(R.id.title_tv);
                    title_tv.setText("选择动物疫病");
                    tvSubmit.setOnClickListener(v1 -> {
                        DiseasePv.returnData();
                        DiseasePv.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> DiseasePv.dismiss());

                })
                .isDialog(false)
                .setContentTextSize(16)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .build();
        DiseasePv.setPicker(listString);//添加数据
        DiseasePv.show();
    }


    private void showVaccine() {
        List<String> listString = new ArrayList<>();

        for (VaccineBean.Result result : vaccineBeanList) {
            listString.add(result.name);
        }

        VaccinePv = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            vaccineID = vaccineBeanList.get(options1).mid;
            binding.vaccineTv.setText(vaccineBeanList.get(options1).name);
            IsforceIm = vaccineBeanList.get(options1).isforceIm;

            if (!TextUtils.isEmpty(VaccineDataSp.getInstance().getVaccineFactory())) {
                binding.vaccineFactoryTv.setText(VaccineDataSp.getInstance().getVaccineFactory());
            }
            if (!TextUtils.isEmpty(VaccineDataSp.getInstance().getBatch())) {
                binding.batchTv.setText(VaccineDataSp.getInstance().getBatch());
            }
            if (VaccineDataSp.getInstance().getUnit() == 1) {
                binding.headOfRb.setChecked(true);
            } else if (VaccineDataSp.getInstance().getUnit() == 2) {
                binding.mlOfRb.setChecked(true);
            } else {
                binding.headOfRb.setChecked(true);
            }
        })
                .setLayoutRes(R.layout.custom_immune_animal_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    final TextView title_tv = (TextView) v.findViewById(R.id.title_tv);
                    title_tv.setText("选择疫苗种类");
                    tvSubmit.setOnClickListener(v1 -> {
                        VaccinePv.returnData();
                        VaccinePv.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> VaccinePv.dismiss());

                })
                .isDialog(false)
                .setContentTextSize(16)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .build();
        VaccinePv.setPicker(listString);//添加数据
        VaccinePv.show();
    }


    /**
     * 上传免疫详情
     */
    private void UpLoadImmuneDetails() {
        UpImmuneDetailsBean upImmuneDetailsBean = new UpImmuneDetailsBean();
        upImmuneDetailsBean.Batch = binding.batchTv.getText().toString();
        upImmuneDetailsBean.Capacity = binding.doseTv.getText().toString();
        upImmuneDetailsBean.Unit = Unit == 1 ? "头份" : "毫升";
        upImmuneDetailsBean.VaccineFactory = binding.vaccineFactoryTv.getText().toString();

        ImmuneSaveBean immuneSaveBean = new ImmuneSaveBean();
        immuneSaveBean.Batch = binding.batchTv.getText().toString();
        immuneSaveBean.Capacity = binding.doseTv.getText().toString();
        immuneSaveBean.VaccineFactory = binding.vaccineFactoryTv.getText().toString();
        immuneSaveBean.Unit = Unit;
        ImmuneSp.getInstance().setImmuneSaveInfo(immuneSaveBean);

        if (IsforceIm) {
            upImmuneDetailsBean.IIST = 1023;
        } else {
            upImmuneDetailsBean.IIST = 1024;
        }

        upImmuneDetailsBean.AnimalID = Integer.parseInt(AnimalSP.getInstance().getChooseAnimal().id);

        upImmuneDetailsBean.DiseaseID = new UpImmuneDetailsBean.DiseaseBean();
        upImmuneDetailsBean.DiseaseID.key = String.valueOf(diseaseID);
        upImmuneDetailsBean.DiseaseID.Name = binding.diseaseTv.getText().toString();

        upImmuneDetailsBean.VaccineId = new UpImmuneDetailsBean.VaccineBean();
        upImmuneDetailsBean.VaccineId.key = String.valueOf(vaccineID);
        upImmuneDetailsBean.VaccineId.Name = binding.vaccineTv.getText().toString();

        HttpRequest.upLoadImmuneDetails(VaccineActivity.this, upImmuneDetailsBean, new CallBackLis<StatusData>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.Status == 0) {
                    UpLoadImmune(content.Result);
                }

            }

            @SuppressLint("CheckResult")
            @Override
            public void onFailure(String method, String error) {
                RxToast.error(VaccineActivity.this, error);
            }
        });
    }

    private void UpLoadImmune(String detailsId) {
        UpImmuneBean upImmuneInfo = UpImmuneSP.getInstance().getUpImmuneInfo();
        upImmuneInfo.DetailID = detailsId;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        upImmuneInfo.Immuned = str;
        UpImmuneSP.getInstance().setUpImmune(upImmuneInfo);

        VaccineDataSp.getInstance().setUnit(Unit);//單位
        VaccineDataSp.getInstance().setVaccineFactory(binding.vaccineFactoryTv.getText().toString());
        VaccineDataSp.getInstance().setBatch(binding.batchTv.getText().toString());

        HttpRequest.upLoadImmune(VaccineActivity.this, UpImmuneSP.getInstance().getUpImmuneInfo(), new CallBackLis<StatusData>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.Status == 0) {
                    hideLoading();
                    RxToast.success(VaccineActivity.this, "免疫成功");
                    ActivityManager.getInstance().finishActivity(ImmuneActivity.class);
                    ActivityManager.getInstance().finishActivity(EarTagActivity.class);
                    finish();
                }

            }

            @SuppressLint("CheckResult")
            @Override
            public void onFailure(String method, String error) {
                RxToast.error(VaccineActivity.this, error);
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

}
