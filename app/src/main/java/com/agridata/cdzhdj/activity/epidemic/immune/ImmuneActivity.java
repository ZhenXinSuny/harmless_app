package com.agridata.cdzhdj.activity.epidemic.immune;


import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.AnimalSP;
import com.agridata.cdzhdj.SPUtil.UpImmuneSP;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.epidemic.eartag.EarTagActivity;
import com.agridata.cdzhdj.activity.epidemic.vaccine.VaccineActivity;
import com.agridata.cdzhdj.activity.epidemic.xdr.ImmuneXdrListActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.ImmuneAnimalBean;
import com.agridata.cdzhdj.data.ImmuneXdrBean;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.data.UpImmuneBean;
import com.agridata.cdzhdj.databinding.ActivityImmuneBinding;
import com.agridata.cdzhdj.utils.AppConstants;
import com.agridata.network.utils.LogUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzx.utils.RxToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 防疫界面
 */
public class ImmuneActivity extends BaseActivity<ActivityImmuneBinding> implements View.OnClickListener {

    private final static String TAG = "ImmuneActivity------》";
    private OptionsPickerView immuneAnimalPv;
    private List<ImmuneXdrBean.Result.AnimalVariety> animalVarietyList;
    private ImmuneXdrBean.Result.PageItems pageItemsBean;
    private String dayAgeEt;
    private String animalTypeTv;
    private int IsSelfWrite;
    private String ImmuneType = "1007";
    private String eartagCode;
    private boolean mIsCullingPigs;

    public static void start(Activity context) {
        Intent intent = new Intent(context, ImmuneActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ActivityImmuneBinding getBinding() {
        return ActivityImmuneBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.chooseXdrTv.setOnClickListener(this);
        binding.animalTypeTv.setOnClickListener(this);
        binding.immuneEntryBtn.setOnClickListener(this);
        binding.epidemicWorkerTv.setText(UserDataSP.getInstance().getUserInfo().Result.name);
        binding.titlebarLeft.setOnClickListener(v -> finish());
        List<String> roleList = new ArrayList<>();
        for (LoginData.ResultBean.RolesBean role : UserDataSP.getInstance().getUserInfo().Result.roles) {
            roleList.add(role.id);
        }
        if (roleList.contains(AppConstants.FangYiYuanID) || roleList.contains(AppConstants.XZFYMaster) || roleList.contains(AppConstants.XianMaster) || roleList.contains(AppConstants.ShiMaster)) {
            int regionID = UserDataSP.getInstance().getUserInfo().Result.dependency.Dep_AgencyMID.Region.id;
            IsSelfWrite = AppConstants.IsSelfWrite.FYYMY;
        } else {
            IsSelfWrite = AppConstants.IsSelfWrite.ZZMY;
        }


        binding.firstImmunizationRb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ImmuneType = "1007";
            }
        });
        binding.againImmunizationRb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ImmuneType = "1008";
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void OnEventMainThread() {
        super.OnEventMainThread();
        mRxManager.on("IMMUNE_XDR", o -> {
            pageItemsBean = (ImmuneXdrBean.Result.PageItems) o;
            LogUtil.d(TAG, "pageItemsBean" + pageItemsBean.toString());
            setXdrInfo(pageItemsBean);
            binding.animalTypeTv.setText("");

        });
    }

    private void setXdrInfo(ImmuneXdrBean.Result.PageItems pageItemsBean) {
        if (pageItemsBean != null) {
            animalVarietyList = pageItemsBean.animalVariety;
            binding.xdrNameTv.setText(pageItemsBean.displayName);
            if (!TextUtils.isEmpty(pageItemsBean.IDCardNo)) {
                binding.idcardTv.setText(pageItemsBean.IDCardNo);
            } else if (!TextUtils.isEmpty(pageItemsBean.iDCard)) {
                binding.idcardTv.setText(pageItemsBean.iDCard);
            } else if (!TextUtils.isEmpty(pageItemsBean.iDCardNos)) {
                binding.idcardTv.setText(pageItemsBean.iDCardNos);
            }

            binding.phoneTv.setText(pageItemsBean.mobile);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == binding.chooseXdrTv) {
            ImmuneXdrListActivity.start(ImmuneActivity.this, 1);
        } else if (v == binding.animalTypeTv) {
            if (pageItemsBean != null) {
                if (pageItemsBean.animalVariety != null && !pageItemsBean.animalVariety.isEmpty()) {
                    showImmuneAnimal();
                } else {
                    Objects.requireNonNull(RxToast.error(this, "请选择包含动物种类的畜主"));
                }
            } else {
                Objects.requireNonNull(RxToast.error(this, "请选择畜主在选择动物种类"));
            }
        } else if (v == binding.immuneEntryBtn) { //免疫录入
            if (checkInfo()) {
                UpImmuneBean upImmuneBean = new UpImmuneBean();
                upImmuneBean.XDRCoreInfo = new UpImmuneBean.XDRCoreInfoBean();
                upImmuneBean.XDRCoreInfo.Key = pageItemsBean.mid;
                upImmuneBean.XDRCoreInfo.Name = pageItemsBean.displayName; //养殖场户信息
                upImmuneBean.XDRCoreID = pageItemsBean.mid;//免疫相对人ID（养殖场户）
                upImmuneBean.Animal = new UpImmuneBean.AnimalBean();
                upImmuneBean.Animal.Key = AnimalSP.getInstance().getChooseAnimal().id;
                upImmuneBean.Animal.Name = AnimalSP.getInstance().getChooseAnimal().name; //动物
                upImmuneBean.AnimalID = AnimalSP.getInstance().getChooseAnimal().id;
                upImmuneBean.IsSelfWrite = String.valueOf(IsSelfWrite);
                if (IsSelfWrite == 1010) {
                    upImmuneBean.AHIUserID = UserDataSP.getInstance().getUserInfo().Result.userId;
                    upImmuneBean.AHIUser = new UpImmuneBean.AHIUserBean();
                    upImmuneBean.AHIUser.Key = UserDataSP.getInstance().getUserInfo().Result.userId;
                    upImmuneBean.AHIUser.Name = UserDataSP.getInstance().getUserInfo().Result.name; //防疫员
                }
                upImmuneBean.ImmuneType = ImmuneType;
                upImmuneBean.PreLiveStock = binding.animalCountEt.getText().toString();//当前存栏量
                upImmuneBean.CurrentAge = binding.dayAgeEt.getText().toString();//日龄
                upImmuneBean.Region = new UpImmuneBean.RegionBean();
                upImmuneBean.Region.iD = pageItemsBean.region.iD;
                upImmuneBean.Region.rI1 = pageItemsBean.region.rI1;
                upImmuneBean.Region.rI2 = pageItemsBean.region.rI2;
                upImmuneBean.Region.rI3 = pageItemsBean.region.rI3;
                upImmuneBean.Region.rI4 = pageItemsBean.region.rI4;
                upImmuneBean.Region.rI5 = pageItemsBean.region.rI5;
                upImmuneBean.Region.regionCode = pageItemsBean.region.regionCode;
                upImmuneBean.Region.regionName = pageItemsBean.region.regionName;
                upImmuneBean.Region.regionLevel = pageItemsBean.region.regionLevel;
                upImmuneBean.Region.regionFullName = pageItemsBean.region.regionFullName;
                upImmuneBean.Region.regionParentID = pageItemsBean.region.regionParentID;
                //TODO:2024-10-11 新增商品猪显示是否是淘汰种猪
                upImmuneBean.IsCullingPigs = mIsCullingPigs;
                int eartagCode = AnimalSP.getInstance().getChooseAnimal().eartagCode;
                if (eartagCode > 0) {
                    upImmuneBean.IsEarTagAnimal = "1011";
                } else {
                    upImmuneBean.IsEarTagAnimal = "1012";
                    upImmuneBean.ImmuneCount = binding.immuneCountEt.getText().toString();
                    upImmuneBean.ImmuneQuantity = binding.immuneCountEt.getText().toString();
                }
                UpImmuneSP.getInstance().setUpImmune(upImmuneBean);
                if (eartagCode > 0) {
                    EarTagActivity.start(ImmuneActivity.this, pageItemsBean.mid, ImmuneType);
                } else {
                    VaccineActivity.start(this);
                }
            }
        }
    }

    private boolean checkInfo() {
        dayAgeEt = binding.dayAgeEt.getText().toString().trim();
        animalTypeTv = binding.animalTypeTv.getText().toString().trim();
        if (pageItemsBean == null) {
            Objects.requireNonNull(RxToast.warning(ImmuneActivity.this, "请选择畜主"));
            return false;
        }
        if (TextUtils.isEmpty(this.animalTypeTv)) {
            Objects.requireNonNull(RxToast.warning(ImmuneActivity.this, "请选择动物种类"));
            return false;
        }
        if (TextUtils.isEmpty(this.dayAgeEt)) {

            Objects.requireNonNull(RxToast.warning(ImmuneActivity.this, "请输入日龄"));
            return false;
        }
        if (TextUtils.isEmpty(this.binding.animalCountEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(ImmuneActivity.this, "请输入养殖总量"));
            return false;
        }
        int eartagCode = AnimalSP.getInstance().getChooseAnimal().eartagCode;
        //TODO:2024-04-24 免疫数量不能为空 取消eartagCode判断
        if (eartagCode <= 0) {
            if (TextUtils.isEmpty(this.binding.immuneCountEt.getText().toString())) {
                Objects.requireNonNull(RxToast.warning(ImmuneActivity.this, "请输入免疫数量"));
                return false;
            }
        }
        // 1011 是   	1012 否
        return true;
    }

    private void showImmuneAnimal() {
        List<String> listString = new ArrayList<>();
        for (ImmuneXdrBean.Result.AnimalVariety animalVariety : animalVarietyList) {
            listString.add(animalVariety.name);
        }
        immuneAnimalPv = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.animalTypeTv.setText(animalVarietyList.get(options1).name);
            if ("商品猪".equals(animalVarietyList.get(options1).name)) {
                binding.isCullingPigsLl.setVisibility(View.VISIBLE);
                binding.isCullingPigsLl.setAlpha(0f);
                binding.isCullingPigsLl.animate().alpha(1f).setDuration(300).setListener(null);
            } else {
                // 隐藏视图
                binding.isCullingPigsLl.animate().alpha(0f).setDuration(300).withEndAction(() -> binding.isCullingPigsLl.setVisibility(View.GONE));
            }

            ImmuneXdrBean.Result.AnimalVariety animalVariety = animalVarietyList.get(options1);
            List<ImmuneAnimalBean.ResultDTO> animalList = AnimalSP.getInstance().getAnimalList();
            for (ImmuneAnimalBean.ResultDTO resultDTO : animalList) {
                if (resultDTO.iD == Integer.parseInt(animalVarietyList.get(options1).id)) {
                    animalVariety.eartagCode = resultDTO.eartagCode;
                }
            }
            AnimalSP.getInstance().setChooseAnimal(animalVariety);
            eartagCode = String.valueOf(animalVariety.eartagCode);
            int eartagCode = AnimalSP.getInstance().getChooseAnimal().eartagCode;
            if (eartagCode > 0) { //1011 是   	1012 否
                binding.immuneLl.setVisibility(View.GONE);
            } else {
                binding.immuneLl.setVisibility(View.VISIBLE);
            }
        }).setLayoutRes(R.layout.custom_immune_animal_select, v -> {
            final TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
            final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
            tvSubmit.setOnClickListener(v1 -> {
                immuneAnimalPv.returnData();
                immuneAnimalPv.dismiss();
            });
            ivCancel.setOnClickListener(v12 -> immuneAnimalPv.dismiss());
        }).isDialog(false).setContentTextSize(18).setLineSpacingMultiplier(3.0f).build();
        immuneAnimalPv.setPicker(listString);
        immuneAnimalPv.show();
    }


    public void onRadioButtonClicked(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.isCullingPigsYes) {
            if (isChecked) {
                mIsCullingPigs = true;
                binding.dayAgeEt.setEnabled(false);
                binding.dayAgeEt.setText("0");
            }
        } else if (view == binding.isCullingPigsNo) {
            if (isChecked) {
                mIsCullingPigs = false;
                binding.dayAgeEt.setEnabled(true);
            }
        }
    }

}
