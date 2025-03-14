package com.agridata.cdzhdj.activity.epidemic.immune.updata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.AnimalToDisBean;
import com.agridata.cdzhdj.data.DiseaseDictionaryBean;
import com.agridata.cdzhdj.data.DiseaseIDBean;
import com.agridata.cdzhdj.data.ImmuneEarTagBean;
import com.agridata.cdzhdj.data.QueryImmuneDetailsBean;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.UpDataImmuneDetailsBean;
import com.agridata.cdzhdj.data.UpImmuneDetailsBean;
import com.agridata.cdzhdj.data.VaccineBean;
import com.agridata.cdzhdj.databinding.ActivityVaccineBinding;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzx.utils.RxToast;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-14 16:23.
 * @Description :描述 疫苗录入 修改
 */
public class UpDataVaccineActivity extends BaseActivity<ActivityVaccineBinding> implements View.OnClickListener {

    public static final  String TAG="VaccineActivity-----》 ";

    private List<ImmuneEarTagBean> earTagList;


    private List<DiseaseIDBean.Result> diseaseIDBeans;
    private List<AnimalToDisBean>   animalToDisBeans;

    private OptionsPickerView DiseasePv,VaccinePv;

    private int diseaseID;

    private List<VaccineBean.Result> vaccineBeanList;
    private String vaccineID;
    private  boolean IsforceIm;

    private  int Unit=1;

    private CustomLoadingDialog mLoadingDialog;

    private String Mid;
    private String AnimalID;
    private String ImmuneDetailsMid;
    private int animalId;

    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context, String Mid,int animalId) {
        Intent intent = new Intent(context, UpDataVaccineActivity.class);
        Bundle data = new Bundle();
        data.putString("Mid", Mid);
        data.putInt("animalId", animalId);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    /**
     * 获取上一级所传过来的值  去判断加载哪一个Url
     */
    private void getArguments() {
        Bundle bundle = getIntent().getBundleExtra("data");
        Mid =  bundle.getString("Mid");
        animalId = bundle.getInt("animalId");
    }

    @Override
    protected ActivityVaccineBinding getBinding() {
        return ActivityVaccineBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        getArguments();
        if (!TextUtils.isEmpty(Mid)){
            getInfo(Mid);
        }
        initLoadingView();
        binding.diseaseTv.setHintTextColor(getResources().getColor(R.color.T1));
        binding.diseaseTv.setOnClickListener(this);


        binding.vaccineTv.setHintTextColor(getResources().getColor(R.color.T1));
        binding.vaccineTv.setOnClickListener(this);

        binding.vaccineFactoryTv.setHintTextColor(getResources().getColor(R.color.T1));
        binding.batchTv.setHintTextColor(getResources().getColor(R.color.T1));
        binding.doseTv.setHintTextColor(getResources().getColor(R.color.T1));

        binding.titlebarMiddle.setText("疫病疫苗修改");
        binding.immuneBtn.setText("防疫信息修改");

        diseaseIDBeans = new ArrayList<>();
        animalToDisBeans = new ArrayList<>();
        vaccineBeanList = new ArrayList<>();

        getDiseasesInfo();



        binding.titlebarLeft.setOnClickListener(v -> finish());

        binding.immuneBtn.setOnClickListener(this);


        binding.headOfRb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Unit =1;
            }
        });
        binding.mlOfRb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Unit =2 ;
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    private void getInfo(String Mid) {
        HttpRequest.getHistoryImmuneDetails(UpDataVaccineActivity.this, Mid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, QueryImmuneDetailsBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {


                    QueryImmuneDetailsBean.Result result = content.result;
                    ImmuneDetailsMid = result.mid;


                    diseaseID = Integer.parseInt(result.diseaseID.key);
                    vaccineID = result.vaccineId.name;

                    if (result.iIST==1023) {
                        IsforceIm = true;
                    } else {
                        IsforceIm = false;
                    }
                    AnimalID = String.valueOf(result.dep_AnimalID.iD);

                    UpDataImmuneDetailsBean upImmuneDetailsBean = new UpDataImmuneDetailsBean();
                    upImmuneDetailsBean.Mid = result.mid;
                    upImmuneDetailsBean.Batch = result.batch;
                    upImmuneDetailsBean.Capacity = result.capacity;
                    if ( result.unit.equals("头份")){
                        binding.headOfRb.setChecked(true);
                    }else {
                        binding.mlOfRb.setChecked(true);
                    }
                    upImmuneDetailsBean.Unit = result.unit;



                    upImmuneDetailsBean.VaccineFactory = result.vaccineFactory;

                    if (result.iIST==1023) {
                        upImmuneDetailsBean.IIST =1023;
                    } else {
                        upImmuneDetailsBean.IIST = 1024;
                    }
                    upImmuneDetailsBean.AnimalID =result.dep_AnimalID.iD;

                    upImmuneDetailsBean.DiseaseID = new UpImmuneDetailsBean.DiseaseBean();

                    upImmuneDetailsBean.DiseaseID .key = result.diseaseID.key;
                    upImmuneDetailsBean.DiseaseID .Name =result.diseaseID.name;



                    upImmuneDetailsBean.VaccineId = new UpImmuneDetailsBean.VaccineBean();
                    upImmuneDetailsBean.VaccineId .key = result.vaccineId.key;
                    upImmuneDetailsBean.VaccineId .Name =result.vaccineId.name;
                    binding.diseaseTv.setText(result.diseaseID.name);//疫病
                    binding.vaccineTv.setText(result.vaccineId.name);//疫苗
                    binding.vaccineFactoryTv.setText(result.vaccineFactory);//厂家
                    binding.batchTv.setText(result.batch);//批次
                    binding.doseTv.setText(result.capacity);//剂量

                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }
    @SuppressLint("CheckResult")
    @Override
    public void onClick(View v) {
        if (v==binding.diseaseTv){//疾病
            showDisease();
        }else  if (v==binding.vaccineTv){//疫苗
            if (TextUtils.isEmpty(binding.diseaseTv.getText().toString().trim())){
                RxToast.warning(UpDataVaccineActivity.this,"请选择疫病在选择疫苗");
            }else {
                getVaccineInfo();
            }
        }else  if (v==binding.immuneBtn){//免疫提交
            if (TextUtils.isEmpty(binding.diseaseTv.getText().toString())){
                RxToast.warning(UpDataVaccineActivity.this,"请选择动物疫病");
                return;
            }
            if (TextUtils.isEmpty(binding.vaccineTv.getText().toString())){
                RxToast.warning(UpDataVaccineActivity.this,"请选择动物疫苗");
                return;
            }
            if (TextUtils.isEmpty(binding.vaccineFactoryTv.getText().toString())){
                RxToast.warning(UpDataVaccineActivity.this,"请输入疫苗厂家");
                return;
            }
            if (TextUtils.isEmpty(binding.batchTv.getText().toString())){
                RxToast.warning(UpDataVaccineActivity.this,"请输入批次");
                return;
            }
            if (TextUtils.isEmpty(binding.doseTv.getText().toString())){
                RxToast.warning(UpDataVaccineActivity.this,"请输入剂量");
                return;
            }
            showLoading("免疫修改中...");
            UpLoadImmuneDetails();
        }
    }

    /**
     * 动物动物种类获取疫病  Integer.parseInt(AnimalSP.getInstance().getChooseAnimal().id)
     */
    private void getDiseasesInfo() {
        HttpRequest.getAnimalToDiseaseID(UpDataVaccineActivity.this,animalId , new CallBackLis<DiseaseIDBean>() {
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
        HttpRequest.getDiseaseDictionary(UpDataVaccineActivity.this, new CallBackLis<DiseaseDictionaryBean>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, DiseaseDictionaryBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    for (DiseaseDictionaryBean.Result result : content.result) {
                        for (DiseaseIDBean.Result diseaseIDBean : diseaseIDBeans) {
                            if (result.key == diseaseIDBean.diseaseID){
                                AnimalToDisBean  animalToDisBean = new AnimalToDisBean();
                                animalToDisBean.DiseaseID = result.key;
                                animalToDisBean.DiseaseName = result.name;
                                animalToDisBeans.add(animalToDisBean);
                            }
                        }
                    }
                }
                LogUtil.i(TAG,"最终的疫病List" +animalToDisBeans.toString());
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
        HttpRequest.getVaccineInfo(UpDataVaccineActivity.this,diseaseID,animalId , new CallBackLis<VaccineBean>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, VaccineBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    vaccineBeanList = content.result;
                    showVaccine();//弹出疫苗新选择框
                }else {
                    RxToast.error(UpDataVaccineActivity.this,content.message);
                }
            }

            @SuppressLint("CheckResult")
            @Override
            public void onFailure(String method, String error) {
                RxToast.error(UpDataVaccineActivity.this,error);
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
                .setLineSpacingMultiplier(2.0f)
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
                .setLineSpacingMultiplier(2.0f)
                .build();
        VaccinePv.setPicker(listString);//添加数据
        VaccinePv.show();
    }


    /**
     * 上传免疫详情
     */
    private  void  UpLoadImmuneDetails(){

        UpDataImmuneDetailsBean upImmuneDetailsBean = new UpDataImmuneDetailsBean();
        upImmuneDetailsBean.Mid = ImmuneDetailsMid;
        upImmuneDetailsBean.Batch = binding.batchTv.getText().toString();
        upImmuneDetailsBean.Capacity = binding.doseTv.getText().toString();
        upImmuneDetailsBean.Unit = Unit==1?"头份":"毫升";
        upImmuneDetailsBean.VaccineFactory = binding.vaccineFactoryTv.getText().toString();
        if (IsforceIm) {
            upImmuneDetailsBean.IIST =1023;
        } else {
            upImmuneDetailsBean.IIST = 1024;
        }
        upImmuneDetailsBean.AnimalID = Integer.parseInt(AnimalID);

        upImmuneDetailsBean.DiseaseID = new UpImmuneDetailsBean.DiseaseBean();
        upImmuneDetailsBean.DiseaseID .key = String.valueOf(diseaseID);
        upImmuneDetailsBean.DiseaseID .Name =binding.diseaseTv.getText().toString();

        upImmuneDetailsBean.VaccineId = new UpImmuneDetailsBean.VaccineBean();
        upImmuneDetailsBean.VaccineId .key = String.valueOf(vaccineID);
        upImmuneDetailsBean.VaccineId .Name =binding.vaccineTv.getText().toString();

        HttpRequest.upDataImmuneDetails(UpDataVaccineActivity.this, upImmuneDetailsBean,new CallBackLis<StatusData>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.Status==0){
                    hideLoading();
                    RxToast.success(UpDataVaccineActivity.this,"修改成功~");
                    finish();
                }

            }

            @SuppressLint("CheckResult")
            @Override
            public void onFailure(String method, String error) {
                RxToast.error(UpDataVaccineActivity.this,error);
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
