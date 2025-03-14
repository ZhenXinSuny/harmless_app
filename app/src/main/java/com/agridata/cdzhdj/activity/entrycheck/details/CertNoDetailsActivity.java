package com.agridata.cdzhdj.activity.entrycheck.details;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckABean;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckBBean;
import com.agridata.cdzhdj.databinding.ActivityCertnoDetailsBinding;
import com.agridata.network.utils.LogUtil;

import java.io.Serializable;

/**
 * @ProjectName : AdmissionCheck
 * @Author :
 * @Time : 2021/10/11 9:51
 * @Description :
 */
public class CertNoDetailsActivity extends BaseActivity<ActivityCertnoDetailsBinding> {

    private  EntryCheckBBean.Result  certInfoBean;
    private  EntryCheckABean.Result  certInfoBeanA;
    private String animalType;

    public static void start(Context context, EntryCheckBBean.Result certInfoBean, String animalType) {
        Intent intent = new Intent(context, CertNoDetailsActivity.class);
        intent.putExtra("certInfoBean", (Serializable) certInfoBean);
        intent.putExtra("animalType",animalType);
        context.startActivity(intent);
    }
    public static void start(Context context, EntryCheckABean.Result certInfoBean, String animalType) {
        Intent intent = new Intent(context, CertNoDetailsActivity.class);
        intent.putExtra("certInfoBeanA", (Serializable) certInfoBean);
        intent.putExtra("animalType",animalType);
        context.startActivity(intent);
    }
    @Override
    protected ActivityCertnoDetailsBinding getBinding() {
        return ActivityCertnoDetailsBinding.inflate(getLayoutInflater());
    }
    /**
     * 获取参数
     */
    private void getArguments() {
        this.animalType = this.getIntent().getStringExtra("animalType");
        if (this.animalType.equals("A")){
            this.certInfoBeanA = (EntryCheckABean.Result) this.getIntent().getSerializableExtra("certInfoBeanA");
        }else {
            this.certInfoBean = (EntryCheckBBean.Result) this.getIntent().getSerializableExtra("certInfoBean");
        }
    }
    @Override
    protected void initView() {
        getArguments();
        binding.titlebarLeft.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void initDatas() {
        if (animalType.equals("A")){
            binding.certTypeTv.setText("动物A证");
        }else {
            binding.certTypeTv.setText("动物B证");
        }
        if (animalType.equals("B")){
            binding.certNumTv.setText(certInfoBean.factoryCode);
            binding.goodsOwnerTv.setText(certInfoBean.owner.name);
            binding.shippersPhoneTv.setText(certInfoBean.ownerTel);
            binding.animalTypeTv.setText(certInfoBean.animalType.name);
            binding.numCountTv.setText(certInfoBean.amount +"");
            binding.unitTv.setText(certInfoBean.unit.name);
            binding.startAddressTv.setText("启运地场所详细地址:"+ certInfoBean.startingPlaceRegion.regionFullName + certInfoBean.startingPlaceCompany.name);
            binding.endAddressTv.setText("目的地场所详细地址:"+ certInfoBean.destinationPlaceRegion.regionFullName+ certInfoBean.destinationPlaceCompany.name);
            binding.useTv.setText(certInfoBean.usageType.name);
            binding.carrierGradeTv.setText(certInfoBean.transportNumber);
            binding.veterinariansNameTv.setText(certInfoBean.quarantineOfficer.name);
            binding.dateTimeTv.setText(certInfoBean.dateOfIssue);
            if(!TextUtils.isEmpty(certInfoBean.earTags)){
                binding.eartagsTv.setText("耳标号:" + certInfoBean.earTags);
            }else {
                binding.eartagsLl.setVisibility(View.GONE);
            }
            binding.carrierNameLl.setVisibility(View.GONE);
            binding.carrierPhoneLl.setVisibility(View.GONE);
            binding.disinfectLl.setVisibility(View.GONE);
            binding.animalOfficePhoneLl.setVisibility(View.GONE);
            binding.deliveryMethodLl.setVisibility(View.GONE);
            binding.remkerTv.setText(certInfoBean.remark);
        }else {
            binding.certNumTv.setText(certInfoBeanA.factoryCode);
            binding.goodsOwnerTv.setText(certInfoBeanA.owner);
            binding.shippersPhoneTv.setText(certInfoBeanA.ownerTel);
            binding.animalTypeTv.setText(certInfoBeanA.animalType.name);
            binding.numCountTv.setText(certInfoBeanA.amount +"");
            binding.unitTv.setText(certInfoBeanA.unit.name);
            binding.startAddressTv.setText("启运地场所详细地址:"+ certInfoBeanA.startingPlaceRegion.regionFullName);
            binding.endAddressTv.setText("目的地场所详细地址:"+ certInfoBeanA.destinationPlaceRegion.regionFullName);
            binding.useTv.setText(certInfoBeanA.usageType.name);
            binding.carrierGradeTv.setText(certInfoBeanA.transportNumber);
            binding.veterinariansNameTv.setText(certInfoBeanA.quarantineOfficerName);
            binding.dateTimeTv.setText(certInfoBeanA.dateOfIssue);
            if(!TextUtils.isEmpty(certInfoBeanA.earTags)){
                binding.eartagsTv.setText("耳标号:" + certInfoBeanA.earTags);
            }else {
                binding.eartagsLl.setVisibility(View.GONE);
            }
        if (!TextUtils.isEmpty(certInfoBeanA.agencyTel)){
            binding.animalOfficePhoneNum.setText(certInfoBeanA.agencyTel);
        }else {
            binding.animalOfficePhoneLl.setVisibility(View.GONE);
        }
            binding.remkerTv.setText(certInfoBeanA.remark);
            binding.carrierPhoneTv.setText(certInfoBeanA.carrierTel);
            binding.deliveryMethodTv.setText(certInfoBeanA.transportType.name);
            binding.carrierNameTv.setText(certInfoBeanA.carrier);
            binding.remkerTv.setText(certInfoBeanA.remark);
            binding.disinfectTv.setText(certInfoBeanA.disinfectType);
        }
    }
}
