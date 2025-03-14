package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.details;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.law.EnforcementDetailBean;
import com.agridata.cdzhdj.databinding.ActivityRealmDetailsBinding;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.lzx.utils.RxToast;

import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-18 16:46.
 * @Description :描述
 */
public class RealmDetailsActivity extends BaseActivity<ActivityRealmDetailsBinding> {


    private String name;
    private String mid;
    private CustomLoadingDialog mLoadingDialog;

    public static void start(Context context, String name,String mid) {
        Intent intent = new Intent(context, RealmDetailsActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("mid", mid);
        context.startActivity(intent);
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        this.name = this.getIntent().getStringExtra("name");
        this.mid = this.getIntent().getStringExtra("mid");
    }

    @Override
    protected ActivityRealmDetailsBinding getBinding() {
        return ActivityRealmDetailsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.titlebarLeft.setOnClickListener(v -> finish());
        getArguments();
        initLoadingView();
        getEnforcementDetails();

        binding.titlebarMiddle.setText(name);
    }

    @Override
    protected void initDatas() {

    }

    private void getEnforcementDetails() {
        showLoading("正在加载中...");
        HttpRequest.getEnforcementDetails(this, mid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, EnforcementDetailBean content) {
                hideLoading();
                if (content.status==0){
                    if ("10".equals(content.result.inspectionField.id) && "101".equals(content.result.checkList.id)){
                        binding.layoutSeed.getRoot().setVisibility(View.VISIBLE);
                        setSeedFieldData(content.result.seedField);
                    }else  if ("20".equals(content.result.inspectionField.id) && "201".equals(content.result.checkList.id)){
                        binding.layoutSyOne.getRoot().setVisibility(View.VISIBLE);
                        setSYOneData(content.result.veterinaryDrugField);
                    }else  if ("20".equals(content.result.inspectionField.id) && "202".equals(content.result.checkList.id)){
                        binding.layoutSyTwo.getRoot().setVisibility(View.VISIBLE);
                        setSYTwoData(content.result.veterinaryDrugField);
                    }else  if ("30".equals(content.result.inspectionField.id) && "301".equals(content.result.checkList.id)){
                        binding.layoutNy.getRoot().setVisibility(View.VISIBLE);
                        setNyData(content.result.pesticideField);
                    }else  if ("40".equals(content.result.inspectionField.id) && "401".equals(content.result.checkList.id)){
                        binding.layoutNjOne.getRoot().setVisibility(View.VISIBLE);
                        setNjOneData(content.result.agriculturalMachineryField);
                    }
                    else  if ("40".equals(content.result.inspectionField.id) && "402".equals(content.result.checkList.id)){
                        binding.layoutNjTwo.getRoot().setVisibility(View.VISIBLE);
                        setNjTwoData(content.result.agriculturalMachineryField);
                    }
                    else  if ("40".equals(content.result.inspectionField.id) && "403".equals(content.result.checkList.id)){
                        binding.layoutNjThree.getRoot().setVisibility(View.VISIBLE);
                        setNjThreeData(content.result.agriculturalMachineryField);
                    }
                    else  if ("40".equals(content.result.inspectionField.id) && "404".equals(content.result.checkList.id)){
                        binding.layoutNjFour.getRoot().setVisibility(View.VISIBLE);
                        setNjFourData(content.result.agriculturalMachineryField);
                    }
                    else  if ("50".equals(content.result.inspectionField.id) && "501".equals(content.result.checkList.id)){
                        binding.layoutFl.getRoot().setVisibility(View.VISIBLE);
                        setFLData(content.result.fertilizerField);
                    }
                    else  if ("60".equals(content.result.inspectionField.id) && "601".equals(content.result.checkList.id)){
                        binding.layoutNcpaq.getRoot().setVisibility(View.VISIBLE);
                        setNCPAQData(content.result.agriculturalProductSafetyField);
                    } else  if ("70".equals(content.result.inspectionField.id) && "701".equals(content.result.checkList.id)){
                        binding.layoutSlOne.getRoot().setVisibility(View.VISIBLE);
                        setSlOneData(content.result.feedField);
                    } else  if ("70".equals(content.result.inspectionField.id) && "702".equals(content.result.checkList.id)){
                        binding.layoutSlTwo.getRoot().setVisibility(View.VISIBLE);
                        setSlTwoData(content.result.feedField);
                    }else  if ("80".equals(content.result.inspectionField.id) && "801".equals(content.result.checkList.id)){
                        binding.layoutPigOne.getRoot().setVisibility(View.VISIBLE);
                        setPigOneData(content.result.hogSlaughteringArea);
                    }else  if ("80".equals(content.result.inspectionField.id) && "802".equals(content.result.checkList.id)){
                        binding.layoutPigTwo.getRoot().setVisibility(View.VISIBLE);
                        setPigTwoData(content.result.hogSlaughteringArea);
                    }else  if ("80".equals(content.result.inspectionField.id) && "803".equals(content.result.checkList.id)){
                        binding.layoutPigTwo.getRoot().setVisibility(View.VISIBLE);
                        setPigThreeData(content.result.hogSlaughteringArea);
                    }else  if ("80".equals(content.result.inspectionField.id) && "804".equals(content.result.checkList.id)){
                        binding.layoutPigFour.getRoot().setVisibility(View.VISIBLE);
                        setPigFourData(content.result.hogSlaughteringArea);
                    }else  if ("80".equals(content.result.inspectionField.id) && "805".equals(content.result.checkList.id)){
                    binding.layoutPigFive.getRoot().setVisibility(View.VISIBLE);
                    setPigFiveData(content.result.hogSlaughteringArea);
                    }else  if ("80".equals(content.result.inspectionField.id) && "806".equals(content.result.checkList.id)){
                        binding.layoutPigSix.getRoot().setVisibility(View.VISIBLE);
                        setPigSixData(content.result.hogSlaughteringArea);
                    }else  if ("90".equals(content.result.inspectionField.id) && "901".equals(content.result.checkList.id)){
                        binding.layoutPlant.getRoot().setVisibility(View.VISIBLE);
                        setPlantData(content.result.fieldPlantQuarantine);
                    }else  if ("100".equals(content.result.inspectionField.id) && "1001".equals(content.result.checkList.id)){
                        binding.layoutBreed.getRoot().setVisibility(View.VISIBLE);
                        setZXQOneData(content.result.livestockPoultryFields);
                    }else  if ("100".equals(content.result.inspectionField.id) && "1002".equals(content.result.checkList.id)){
                        binding.layoutBreedTwo.getRoot().setVisibility(View.VISIBLE);
                        setZXQTwoData(content.result.livestockPoultryFields);
                    }
                    else  if ("110".equals(content.result.inspectionField.id) && "1101".equals(content.result.checkList.id)){
                        binding.layoutFishOne.getRoot().setVisibility(View.VISIBLE);
                        setFishOneData(content.result.fisheryArea);
                    }
                    else  if ("110".equals(content.result.inspectionField.id) && "1102".equals(content.result.checkList.id)){
                        binding.layoutFishTwo.getRoot().setVisibility(View.VISIBLE);
                        setFishTwoData(content.result.fisheryArea);
                    }
                    else  if ("110".equals(content.result.inspectionField.id) && "1103".equals(content.result.checkList.id)){
                        binding.layoutFishThree.getRoot().setVisibility(View.VISIBLE);
                        setFishThreeData(content.result.fisheryArea);
                    }
                }
            }


            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(RealmDetailsActivity.this,error));
            }
        });
    }


    /**
     * 种子领域
     * @param content
     */
    private void setSeedFieldData(EnforcementDetailBean.Result.SeedField content){

        binding.layoutSeed.creditCodeEt.setFocusable(false);
        binding.layoutSeed.businessCodeEt.setFocusable(false);
        binding.layoutSeed.creditCodeEt.setText(content.creditCode);
        binding.layoutSeed.businessCodeEt.setText(content.businessCode);


        if (content.infringePlant==1){
            binding.layoutSeed.infringePlantOk.setChecked(true);
        }else if(content.infringePlant==0) {
            binding.layoutSeed.infringePlantNo.setChecked(true);
        }

        if (content.fakePlant==1){
            binding.layoutSeed.fakePlantOk.setChecked(true);
        }else if(content.fakePlant==0) {
            binding.layoutSeed.fakePlantNo.setChecked(true);
        }

        if (content.dengJi==1){
            binding.layoutSeed.registerRbOk.setChecked(true);
        }else if(content.dengJi==0) {
            binding.layoutSeed.registerRbOk.setChecked(true);
        }

        if (content.baozhaung==1){
            binding.layoutSeed.baoZhaungOk.setChecked(true);
        }else if(content.baozhaung==0) {
            binding.layoutSeed.baoZhaungOk.setChecked(true);
        }

        if (content.biaoqian==1){
            binding.layoutSeed.biaoqianOk.setChecked(true);
        }else if(content.biaoqian==0) {
            binding.layoutSeed.biaoqianNo.setChecked(true);
        }
        if (content.biaoqianneirong==1){
            binding.layoutSeed.biaoqianNeirongOk.setChecked(true);
        }else if(content.biaoqianneirong==0) {
            binding.layoutSeed.biaoqianNeirongNo.setChecked(true);
        }
        if (content.shengchandangan==1){
            binding.layoutSeed.shengchandanganOk.setChecked(true);
        }else if(content.shengchandangan==0) {
            binding.layoutSeed.shengchandanganNo.setChecked(true);
        }
        if (content.jingyingdangan==1){
            binding.layoutSeed.jingyingdanganOk.setChecked(true);
        }else if(content.jingyingdangan==0) {
            binding.layoutSeed.jingyingdanganNo.setChecked(true);
        }
        if (content.beian==1){
            binding.layoutSeed.beianOk.setChecked(true);
        }else if(content.beian==0) {
            binding.layoutSeed.beianNo.setChecked(true);
        }
        if (content.songjian==1){
            binding.layoutSeed.chouyangOk.setChecked(true);
        }else if(content.songjian==0) {
            binding.layoutSeed.chouyangNo.setChecked(true);
        }
    }


    /**
     * 	兽药领域1
     * @param content
     */
    private void setSYOneData(EnforcementDetailBean.Result.VeterinaryDrugField content){

        binding.layoutSyOne.businessLicenseNumberEt.setFocusable(false);
        binding.layoutSyOne.businessLicenseNumberEt.setText(content.mBusinessLicenseNumber);


        if (content.gMPZhengShu==1){
            binding.layoutSyOne.GMPZhengShuOk.setChecked(true);
        }else if(content.gMPZhengShu==0) {
            binding.layoutSyOne.GMPZhengShuNo.setChecked(true);
        }



        if (content.youXiaoqi==1){
            binding.layoutSyOne.youXiaoqiOk.setChecked(true);
        }else if(content.youXiaoqi==0) {
            binding.layoutSyOne.youXiaoqiNo.setChecked(true);
        }



        if (content.yuanLiaoFuLiaoFuHe==1){
            binding.layoutSyOne.yuanLiaoFuLiaoFuHeOk.setChecked(true);
        }else if(content.yuanLiaoFuLiaoFuHe==0) {
            binding.layoutSyOne.yuanLiaoFuLiaoFuHeNo.setChecked(true);
        }



        if (content.storageAndSafekeeping==1){
            binding.layoutSyOne.StorageAndSafekeepingOK.setChecked(true);
        }else if(content.storageAndSafekeeping==0) {
            binding.layoutSyOne.StorageAndSafekeepingNO.setChecked(true);
        }

        if (content.wenJianJiZai==1){
            binding.layoutSyOne.wenJianJiZaiOK.setChecked(true);
        }else if(content.wenJianJiZai==0) {
            binding.layoutSyOne.wenJianJiZaiNo.setChecked(true);
        }


        if (content.caoZuoRenQianMing==1){
            binding.layoutSyOne.caoZuoRenQianMingOk.setChecked(true);
        }else if(content.caoZuoRenQianMing==0) {
            binding.layoutSyOne.caoZuoRenQianMingNo.setChecked(true);
        }
        if (content.piHaoGuiDang==1){
            binding.layoutSyOne.piHaoGuiDangOk.setChecked(true);
        }else if(content.piHaoGuiDang==0) {
            binding.layoutSyOne.piHaoGuiDangNo.setChecked(true);
        }


        if (content.zhiLiangJianYan==1){
            binding.layoutSyOne.zhiLiangJianYanOk.setChecked(true);
        }else if(content.zhiLiangJianYan==0) {
            binding.layoutSyOne.zhiLiangJianYanNo.setChecked(true);
        }
        if (content.biaoQianshuoming==1){
            binding.layoutSyOne.biaoQianshuomingOk.setChecked(true);
        }else if(content.biaoQianshuoming==0) {
            binding.layoutSyOne.biaoQianshuomingNo.setChecked(true);
        }
        if (content.biaozhuerweima==1){
            binding.layoutSyOne.biaozhuerweimaOk.setChecked(true);
        }else if(content.biaozhuerweima==0) {
            binding.layoutSyOne.biaozhuerweimaNo.setChecked(true);
        }
        if (content.guifanyinzhi==1){
            binding.layoutSyOne.guifanyinzhiOk.setChecked(true);
        }else if(content.guifanyinzhi==0) {
            binding.layoutSyOne.guifanyinzhiNo.setChecked(true);
        }


        if (content.piqianfa==1){
            binding.layoutSyOne.piqianfaOk.setChecked(true);
        }else if(content.piqianfa==0) {
            binding.layoutSyOne.piqianfaNo.setChecked(true);
        }

        if (content.zhaohuizhidu==1){
            binding.layoutSyOne.zhaohuizhiduOk.setChecked(true);
        }else if(content.zhaohuizhidu==0) {
            binding.layoutSyOne.zhaohuizhiduNo.setChecked(true);
        }

        if (content.anquanshengchananquanshengchan==1){
            binding.layoutSyOne.anquanshengchanOk.setChecked(true);
        }else if(content.anquanshengchananquanshengchan==0) {
            binding.layoutSyOne.anquanshengchanNo.setChecked(true);
        }

        if (content.gMPhouxujianguan==1){
            binding.layoutSyOne.GMPhouxujianguanOk.setChecked(true);
        }else if(content.gMPhouxujianguan==0) {
            binding.layoutSyOne.GMPhouxujianguanNo.setChecked(true);
        }
    }


    /**
     * 	兽药领域 2
     * @param content
     */
    private void setSYTwoData(EnforcementDetailBean.Result.VeterinaryDrugField content){

        if (content.shouyaoxukezhengzizhi==1){
            binding.layoutSyTwo.shouyaoxukezhengzizhiOk.setChecked(true);
        }else if(content.shouyaoxukezhengzizhi==0) {
            binding.layoutSyTwo.shouyaoxukezhengzizhiNo.setChecked(true);
        }

        if (content.shifouyouxiaoqi==1){
            binding.layoutSyTwo.shifouyouxiaoqiOk.setChecked(true);
        }else if(content.shifouyouxiaoqi==0) {
            binding.layoutSyTwo.shifouyouxiaoqiNo.setChecked(true);
        }

        if (content.jingyingchangsuoxiangshiying==1){
            binding.layoutSyTwo.jingyingchangsuoxiangshiyingOk.setChecked(true);
        }else if(content.jingyingchangsuoxiangshiying==0) {
            binding.layoutSyTwo.jingyingchangsuoxiangshiyingNo.setChecked(true);
        }

        if (content.shebeiqiquan==1){
            binding.layoutSyTwo.shebeiqiquanOk.setChecked(true);
        }else if(content.shebeiqiquan==0) {
            binding.layoutSyTwo.shebeiqiquanNo.setChecked(true);
        }

        if (content.guizhangzhidu==1){
            binding.layoutSyTwo.guizhangzhiduOk.setChecked(true);
        }else if(content.guizhangzhidu==0) {
            binding.layoutSyTwo.guizhangzhiduNo.setChecked(true);
        }

        if (content.caigouruku==1){
            binding.layoutSyTwo.caigourukuOk.setChecked(true);
        }else if(content.caigouruku==0) {
            binding.layoutSyTwo.caigourukuNo.setChecked(true);
        }


        if (content.chenliechuxuOne==1){
            binding.layoutSyTwo.chenliechuxuOneOk.setChecked(true);
        }else if(content.chenliechuxuOne==0) {
            binding.layoutSyTwo.chenliechuxuOneNo.setChecked(true);
        }

        if (content.chenliechuxuTwo==1){
            binding.layoutSyTwo.chenliechuxuTwoOk.setChecked(true);
        }else if(content.chenliechuxuTwo==0) {
            binding.layoutSyTwo.chenliechuxuTwoNo.setChecked(true);
        }

        if (content.chenliechuxuThree==1){
            binding.layoutSyTwo.chenliechuxuThreeOk.setChecked(true);
        }else if(content.chenliechuxuThree==0) {
            binding.layoutSyTwo.chenliechuxuThreeNo.setChecked(true);
        }
        if (content.chenliechuxuFour==1){
            binding.layoutSyTwo.chenliechuxuFourOk.setChecked(true);
        }else if(content.chenliechuxuFour==0) {
            binding.layoutSyTwo.chenliechuxuFourNo.setChecked(true);
        }

        if (content.shouyaoxiaoshouOne==1){
            binding.layoutSyTwo.shouyaoxiaoshouOneOk.setChecked(true);
        }else if(content.shouyaoxiaoshouOne==0) {
            binding.layoutSyTwo.shouyaoxiaoshouOneOk.setChecked(true);
        }
        if (content.shouyaoxiaoshouTwo==1){
            binding.layoutSyTwo.shouyaoxiaoshouTwoOk.setChecked(true);
        }else if(content.shouyaoxiaoshouTwo==0) {
            binding.layoutSyTwo.shouyaoxiaoshouTwoOk.setChecked(true);
        }
        if (content.shouyaoshengwuzhipinOne==1){
            binding.layoutSyTwo.shouyaoshengwuzhipinOneOk.setChecked(true);
        }else if(content.shouyaoshengwuzhipinOne==0) {
            binding.layoutSyTwo.shouyaoshengwuzhipinOneNo.setChecked(true);
        }else if(content.shouyaoshengwuzhipinOne==2) {
            binding.layoutSyTwo.shouyaoshengwuzhipinOneUninvolved.setChecked(true);
        }

        if (content.shouyaoshengwuzhipinTwo==1){
            binding.layoutSyTwo.shouyaoshengwuzhipinTwoOk.setChecked(true);
        }else if(content.shouyaoshengwuzhipinTwo==0) {
            binding.layoutSyTwo.shouyaoshengwuzhipinTwoNo.setChecked(true);
        }else if(content.shouyaoshengwuzhipinTwo==2) {
            binding.layoutSyTwo.shouyaoshengwuzhipinTwoUninvolved.setChecked(true);
        }
        if (content.shouyaoshengwuzhipinThree==1){
            binding.layoutSyTwo.shouyaoshengwuzhipinThreeOk.setChecked(true);
        }else if(content.shouyaoshengwuzhipinThree==0) {
            binding.layoutSyTwo.shouyaoshengwuzhipinThreeNo.setChecked(true);
        }else if(content.shouyaoshengwuzhipinThree==2) {
            binding.layoutSyTwo.shouyaoshengwuzhipinThreeUninvolved.setChecked(true);
        }

        if (content.gMPhouxujianguanTwo==1){
            binding.layoutSyTwo.GMPhouxujianguanTwoOk.setChecked(true);
        }else if(content.gMPhouxujianguanTwo==0) {
            binding.layoutSyTwo.GMPhouxujianguanTwoNo.setChecked(true);
        }
    }


    /**
     * 	农药领域
     * @param content
     */

    private void   setNyData(EnforcementDetailBean.Result.PesticideField content){
       binding.layoutNy.businessLicenseNumber.setFocusable(false);//营业执照号码
        binding.layoutNy.businessLicenseNumber.setText(content.businessLicenseNumber);
        binding.layoutNy.pesticideOperationLicenseNumber.setFocusable(false);
        binding.layoutNy.pesticideOperationLicenseNumber.setText(content.pesticideOperationLicenseNumber);    //农药经营许可证编号

        if (content.productionAndManagementOne==1){
            binding.layoutNy.productionAndManagementOneOk.setChecked(true);
        }else if(content.productionAndManagementOne==0) {
            binding.layoutNy.productionAndManagementOneNo.setChecked(true);
        }

        if (content.productionAndManagementTwo==1){
            binding.layoutNy.productionAndManagementTwoOk.setChecked(true);
        }else if(content.productionAndManagementTwo==0) {
            binding.layoutNy.productionAndManagementTwoNo.setChecked(true);
        }
        if (content.productionAndManagementThree==1){
            binding.layoutNy.productionAndManagementThreeOk.setChecked(true);
        }else if(content.productionAndManagementThree==0) {
            binding.layoutNy.productionAndManagementThreeNo.setChecked(true);
        }

        if (content.productionAndManagementFour==1){
            binding.layoutNy.productionAndManagementFourOk.setChecked(true);
        }else if(content.productionAndManagementFour==0) {
            binding.layoutNy.productionAndManagementFourNo.setChecked(true);
        }

        if (content.chanPinBiaoQianOne==1){
            binding.layoutNy.chanPinBiaoQianOneOk.setChecked(true);
        }else if(content.chanPinBiaoQianOne==0) {
            binding.layoutNy.chanPinBiaoQianOneNo.setChecked(true);
        }
        if (content.chanPinBiaoQianTwo==1){
            binding.layoutNy.chanPinBiaoQianTwoOk.setChecked(true);
        }else if(content.chanPinBiaoQianTwo==0) {
            binding.layoutNy.chanPinBiaoQianTwoNo.setChecked(true);
        }
        if (content.chanPinBiaoQianThree==1){
            binding.layoutNy.chanPinBiaoQianThreeOk.setChecked(true);
        }else if(content.chanPinBiaoQianThree==0) {
            binding.layoutNy.chanPinBiaoQianThreeNo.setChecked(true);
        }


        if (content.jinXianNongYaoOne==1){
            binding.layoutNy.jinXianNongYaoOneOk.setChecked(true);
        }else if(content.jinXianNongYaoOne==0) {
            binding.layoutNy.jinXianNongYaoOneNo.setChecked(true);
        }
        if (content.jinXianNongYaoTwo==1){
            binding.layoutNy.jinXianNongYaoTwoOk.setChecked(true);
        }else if(content.jinXianNongYaoTwo==0) {
            binding.layoutNy.jinXianNongYaoTwoNo.setChecked(true);
        }

        if (content.taiZhang==1){
            binding.layoutNy.taiZhangOk.setChecked(true);
        }else if(content.taiZhang==0) {
            binding.layoutNy.taiZhangNo.setChecked(true);
        }

        if (content.wangShangXiaoShou==1){
            binding.layoutNy.wangShangXiaoShouOk.setChecked(true);
        }else if(content.wangShangXiaoShou==0) {
            binding.layoutNy.wangShangXiaoShouNo.setChecked(true);
        }

        if (content.chanPinZhiLiangOne==1){
            binding.layoutNy.chanPinZhiLiangOneOk.setChecked(true);
        }else if(content.chanPinZhiLiangOne==0) {
            binding.layoutNy.chanPinZhiLiangOneNo.setChecked(true);
        }

        if (content.chanPinZhiLiangTwo==1){
            binding.layoutNy.chanPinZhiLiangTwoOk.setChecked(true);
        }else if(content.chanPinZhiLiangTwo==0) {
            binding.layoutNy.chanPinZhiLiangTwoNo.setChecked(true);
        }
    }

    /**
     * 		农机领域1
     * @param content
     */
    private void   setNjOneData(EnforcementDetailBean.Result.AgriculturalMachineryField content){


        if (content.zhizi==1){
            binding.layoutNjOne.zhiziOk.setChecked(true);
        }else if(content.zhizi==0) {
            binding.layoutNjOne.zhiziNo.setChecked(true);
        }
        if (content.dengjizhucengOne==1){
            binding.layoutNjOne.dengjizhucengOneOk.setChecked(true);
        }else if(content.dengjizhucengOne==0) {
            binding.layoutNjOne.dengjizhucengOneNo.setChecked(true);
        }
        if (content.dengjizhucengTwo==1){
            binding.layoutNjOne.dengjizhucengTwoOk.setChecked(true);
        }else if(content.dengjizhucengTwo==0) {
            binding.layoutNjOne.dengjizhucengTwoNo.setChecked(true);
        }
        if (content.dengjizhucengThree==1){
            binding.layoutNjOne.dengjizhucengThreeOk.setChecked(true);
        }else if(content.dengjizhucengThree==0) {
            binding.layoutNjOne.dengjizhucengThreeNo.setChecked(true);
        }

        if (content.danganziliao==1){
            binding.layoutNjOne.danganziliaoOk.setChecked(true);
        }else if(content.danganziliao==0) {
            binding.layoutNjOne.danganziliaoOneNo.setChecked(true);
        }
        if (content.danganziliaoTwo==1){
            binding.layoutNjOne.danganziliaoTwoOk.setChecked(true);
        }else if(content.danganziliaoTwo==0) {
            binding.layoutNjOne.danganziliaoTwoNo.setChecked(true);
        }

        if (content.niandujianyan==1){
            binding.layoutNjOne.niandujianyanOk.setChecked(true);
        }else if(content.niandujianyan==0) {
            binding.layoutNjOne.niandujianyanNo.setChecked(true);
        }
    }


    /**
     * 		农机领域2
     * @param content
     * */
    private void   setNjTwoData(EnforcementDetailBean.Result.AgriculturalMachineryField content){

        if (content.idcard==1){
            binding.layoutNjTwo.idcardOk.setChecked(true);
        }else if(content.idcard==0) {
            binding.layoutNjTwo.idcardNo.setChecked(true);
        }
        if (content.dengjiPersonOne==1){
            binding.layoutNjTwo.dengjiPersonOneOk.setChecked(true);
        }else if(content.dengjiPersonOne==0) {
            binding.layoutNjTwo.dengjiPersonOneNo.setChecked(true);
        }
        if (content.dengjiPersonTwo==1){
            binding.layoutNjTwo.dengjiPersonTwoOk.setChecked(true);
        }else if(content.dengjiPersonTwo==0) {
            binding.layoutNjTwo.dengjiPersonTwoOk.setChecked(true);
        }
        if (content.dengjiPersonThree==1){
            binding.layoutNjTwo.dengjiPersonThreeOk.setChecked(true);
        }else if(content.dengjiPersonThree==0) {
            binding.layoutNjTwo.dengjiPersonThreeOk.setChecked(true);
        }

        if (content.caozuoPersonOne==1){
            binding.layoutNjTwo.caozuoPersonOneOk.setChecked(true);
        }else if(content.caozuoPersonOne==0) {
            binding.layoutNjTwo.caozuoPersonOneNo.setChecked(true);
        }
        if (content.caozuoPersonTwo==1){
            binding.layoutNjTwo.caozuoPersonTwoOk.setChecked(true);
        }else if(content.caozuoPersonTwo==0) {
            binding.layoutNjTwo.caozuoPersonTwoNo.setChecked(true);
        }
        if (content.caozuoPersonThree==1){
            binding.layoutNjTwo.caozuoPersonThreeOk.setChecked(true);
        }else if(content.caozuoPersonThree==0) {
            binding.layoutNjTwo.caozuoPersonThreeNo.setChecked(true);
        }
        if (content.caozuoPersonFour==1){
            binding.layoutNjTwo.caozuoPersonFourOk.setChecked(true);
        }else if(content.caozuoPersonFour==0) {
            binding.layoutNjTwo.caozuoPersonFourNo.setChecked(true);
        }
        if (content.niandujianyanPerson==1){
            binding.layoutNjTwo.niandujianyanPersonOk.setChecked(true);
        }else if(content.niandujianyanPerson==0) {
            binding.layoutNjTwo.niandujianyanPersonNo.setChecked(true);
        }


    }

    /**
     * 		农机领域3
     * @param content
     * */
    private void   setNjThreeData(EnforcementDetailBean.Result.AgriculturalMachineryField content){
        if (content.weixiudianzhizi==1){
            binding.layoutNjThree.weixiudianzhiziOk.setChecked(true);
        }else if(content.weixiudianzhizi==0) {
            binding.layoutNjThree.weixiudianzhiziNo.setChecked(true);
        }
        if (content.weixiuqingkuangOne==1){
            binding.layoutNjThree.weixiuqingkuangOneOk.setChecked(true);
        }else if(content.weixiuqingkuangOne==0) {
            binding.layoutNjThree.weixiuqingkuangOneNo.setChecked(true);
        }
        if (content.weixiuqingkuangTwo==1){
            binding.layoutNjThree.weixiuqingkuangTwoOk.setChecked(true);
        }else if(content.weixiuqingkuangTwo==0) {
            binding.layoutNjThree.weixiuqingkuangTwoNo.setChecked(true);
        }
        if (content.weixiuqingkuangThree==1){
            binding.layoutNjThree.weixiuqingkuangThreeOk.setChecked(true);
        }else if(content.weixiuqingkuangThree==0) {
            binding.layoutNjThree.weixiuqingkuangThreeNo.setChecked(true);
        }
    }

    /**
     * 		农机领域4
     * @param content
     * */
    private void   setNjFourData(EnforcementDetailBean.Result.AgriculturalMachineryField content){
        if (content.jianshizhizhi==1){
            binding.layoutNjFour.jianshizhizhiOk.setChecked(true);
        }else if(content.jianshizhizhi==0) {
            binding.layoutNjFour.jianshizhizhiNo.setChecked(true);
        }
        if (content.jianshiPeiXunYeWuOne==1){
            binding.layoutNjFour.jianshiPeiXunYeWuOneOk.setChecked(true);
        }else if(content.jianshiPeiXunYeWuOne==0) {
            binding.layoutNjFour.jianshiPeiXunYeWuOneNo.setChecked(true);
        }
        if (content.jianshiPeiXunYeWuTwo==1){
            binding.layoutNjFour.jianshiPeiXunYeWuTwoOk.setChecked(true);
        }else if(content.jianshiPeiXunYeWuTwo==0) {
            binding.layoutNjFour.jianshiPeiXunYeWuTwoNo.setChecked(true);
        }
        if (content.jianshiPeiXunYeWuThree==1){
            binding.layoutNjFour.jianshiPeiXunYeWuThreeOk.setChecked(true);
        }else if(content.jianshiPeiXunYeWuThree==0) {
            binding.layoutNjFour.jianshiPeiXunYeWuThreeNo.setChecked(true);
        }
    }

    /**
     * 		肥料领域
     * @param content
     * */
    private void   setFLData(EnforcementDetailBean.Result.FertilizerField content){

        binding.layoutFl.businesslicensenumber.setFocusable(false);
        binding.layoutFl.businesslicensenumber.setText(content.businesslicensenumber);

        if (content.feiliaodengjiOne==1){
            binding.layoutFl.feiliaodengjiOneOk.setChecked(true);
        }else if(content.feiliaodengjiOne==0) {
            binding.layoutFl.feiliaodengjiOneNo.setChecked(true);
        }
        if (content.feiliaodengjiTwo==1){
            binding.layoutFl.feiliaodengjiTwoOk.setChecked(true);
        }else if(content.feiliaodengjiTwo==0) {
            binding.layoutFl.feiliaodengjiTwoNo.setChecked(true);
        }
        if (content.feiliaodengjiThree==1){
            binding.layoutFl.feiliaodengjiThreeOk.setChecked(true);
        }else if(content.feiliaodengjiThree==0) {
            binding.layoutFl.feiliaodengjiThreeNo.setChecked(true);
        }
        if (content.chanpinbiaoqianOne==1){
            binding.layoutFl.chanpinbiaoqianOneOk.setChecked(true);
        }else if(content.chanpinbiaoqianOne==0) {
            binding.layoutFl.chanpinbiaoqianOneNo.setChecked(true);
        }

        if (content.chanpinbiaoqianTwo==1){
            binding.layoutFl.chanpinbiaoqianTwoOk.setChecked(true);
        }else if(content.chanpinbiaoqianTwo==0) {
            binding.layoutFl.chanpinbiaoqianTwoNo.setChecked(true);
        }
        if (content.chanpinbiaoqianThree==1){
            binding.layoutFl.chanpinbiaoqianThreeOk.setChecked(true);
        }else if(content.chanpinbiaoqianThree==0) {
            binding.layoutFl.chanpinbiaoqianThreeNo.setChecked(true);
        }
        if (content.chanpinbiaoqianFour==1){
            binding.layoutFl.chanpinbiaoqianFourOk.setChecked(true);
        }else if(content.chanpinbiaoqianFour==0) {
            binding.layoutFl.chanpinbiaoqianFourNo.setChecked(true);
        }
        if (content.zhiliang==1){
            binding.layoutFl.zhiliangOk.setChecked(true);
        }else if(content.zhiliang==0) {
            binding.layoutFl.zhiliangNo.setChecked(true);
        }

    }


    /**
     * 	农产品安全领域
     * @param content
     * */
    private void   setNCPAQData(EnforcementDetailBean.Result.AgriculturalProductSafetyField content) {
        binding.layoutNcpaq.unifiedCodeEt.setFocusable(false);
        binding.layoutNcpaq.unifiedCodeEt.setText(content.unifiedCode);

        binding.layoutNcpaq.otherQualificationsEt.setFocusable(false);
        binding.layoutNcpaq.otherQualificationsEt.setText(content.otherQualifications);

        binding.layoutNcpaq.dominantVarietyEt.setFocusable(false);
        binding.layoutNcpaq.dominantVarietyEt.setText(content.dominantVariety);


        if (content.shengchanjiluOne==1){
            binding.layoutNcpaq.shengchanjiluOneOk.setChecked(true);
        }else if(content.shengchanjiluOne==0) {
            binding.layoutNcpaq.shengchanjiluOneNo.setChecked(true);
        }


        if (content.shengchanjiluTwo==1){
            binding.layoutNcpaq.shengchanjiluTwoOk.setChecked(true);
        }else if(content.shengchanjiluTwo==0) {
            binding.layoutNcpaq.shengchanjiluTwoNo.setChecked(true);
        }


        if (content.trpNyOne==1){
            binding.layoutNcpaq.trpNyOneOk.setChecked(true);
        }else if(content.trpNyOne==0) {
            binding.layoutNcpaq.trpNyOneNo.setChecked(true);
        }


        if (content.trpNyTwo==1){
            binding.layoutNcpaq.trpNyTwoOk.setChecked(true);
        }else if(content.trpNyTwo==0) {
            binding.layoutNcpaq.trpNyTwoNo.setChecked(true);
        }


        if (content.trpNyThree==1){
            binding.layoutNcpaq.trpNyThreeOk.setChecked(true);
        }else if(content.trpNyThree==0) {
            binding.layoutNcpaq.trpNyThreeNo.setChecked(true);
        }


        if (content.trpNyFour==1){
            binding.layoutNcpaq.trpNyFourOk.setChecked(true);
        }else if(content.trpNyFour==0) {
            binding.layoutNcpaq.trpNyFourNo.setChecked(true);
        }


        if (content.trpNyFive==1){
            binding.layoutNcpaq.trpNyFiveOk.setChecked(true);
        }else if(content.trpNyFive==0) {
            binding.layoutNcpaq.trpNyFiveNo.setChecked(true);
        }


        if (content.trpFlOne==1){
            binding.layoutNcpaq.trpFlOneOk.setChecked(true);
        }else if(content.trpFlOne==0) {
            binding.layoutNcpaq.trpFlOneNo.setChecked(true);
        }


        if (content.trpFlTwo==1){
            binding.layoutNcpaq.trpFlTwoOk.setChecked(true);
        }else if(content.trpFlTwo==0) {
            binding.layoutNcpaq.trpFlTwoNo.setChecked(true);
        }


        if (content.bzfqw==1){
            binding.layoutNcpaq.bzfqwOk.setChecked(true);
        }else if(content.bzfqw==0) {
            binding.layoutNcpaq.bzfqwNo.setChecked(true);
        }



        if (content.dlbz==1){
            binding.layoutNcpaq.dlbzOk.setChecked(true);
        }else if(content.dlbz==0) {
            binding.layoutNcpaq.dlbzNo.setChecked(true);
        }

        if (content.lsyj==1){
            binding.layoutNcpaq.lsyjOk.setChecked(true);
        }else if(content.lsyj==0) {
            binding.layoutNcpaq.lsyjNo.setChecked(true);
        }
        if (content.anquan==1){
            binding.layoutNcpaq.anquanOk.setChecked(true);
        }else if(content.anquan==0) {
            binding.layoutNcpaq.anquanNo.setChecked(true);
        }
        if (content.zhiliang==1){
            binding.layoutNcpaq.zhiliangOk.setChecked(true);
        }else if(content.zhiliang==0) {
            binding.layoutNcpaq.zhiliangNo.setChecked(true);
        }


    }


    /**
     * 	饲料领域 1
     * @param content
     * */
    private void   setSlOneData(EnforcementDetailBean.Result.FeedField content) {

        if (content.jingyingtiaojianOne==1){
            binding.layoutSlOne.jingyingtiaojianOneOk.setChecked(true);
        }else if(content.jingyingtiaojianOne==0) {
            binding.layoutSlOne.jingyingtiaojianOneNo.setChecked(true);
        }


        if (content.jingyingtiaojianTwo==1){
            binding.layoutSlOne.jingyingtiaojianTwoOk.setChecked(true);
        }else if(content.jingyingtiaojianTwo==0) {
            binding.layoutSlOne.jingyingtiaojianTwoNo.setChecked(true);
        }


        if (content.jingyingtiaojianThree==1){
            binding.layoutSlOne.jingyingtiaojianThreeOk.setChecked(true);
        }else if(content.jingyingtiaojianThree==0) {
            binding.layoutSlOne.jingyingtiaojianThreeNo.setChecked(true);
        }


        if (content.jingyingchanpinOne==1){
            binding.layoutSlOne.jingyingchanpinOneOk.setChecked(true);
        }else if(content.jingyingchanpinOne==0) {
            binding.layoutSlOne.jingyingchanpinOneNo.setChecked(true);
        }


        if (content.jingyingchanpinTwo==1){
            binding.layoutSlOne.jingyingchanpinTwoOk.setChecked(true);
        }else if(content.jingyingchanpinTwo==0) {
            binding.layoutSlOne.jingyingchanpinTwoNo.setChecked(true);
        }


        if (content.jingyingchanpinThree==1){
            binding.layoutSlOne.jingyingchanpinThreeOk.setChecked(true);
        }else if(content.jingyingchanpinThree==0) {
            binding.layoutSlOne.jingyingchanpinThreeNo.setChecked(true);
        }


        if (content.jingyingchanpinFour==1){
            binding.layoutSlOne.jingyingchanpinFourOk.setChecked(true);
        }else if(content.jingyingchanpinFour==0) {
            binding.layoutSlOne.jingyingchanpinFourNo.setChecked(true);
        }


        if (content.jingyingchanpinFive==1){
            binding.layoutSlOne.jingyingchanpinFiveOk.setChecked(true);
        }else if(content.jingyingchanpinFive==0) {
            binding.layoutSlOne.jingyingchanpinFiveNo.setChecked(true);
        }


        if (content.jingyingchanpinSix==1){
            binding.layoutSlOne.jingyingchanpinSixOk.setChecked(true);
        }else if(content.jingyingchanpinSix==0) {
            binding.layoutSlOne.jingyingchanpinSixNo.setChecked(true);
        }


        if (content.jingyingchanpinSeven==1){
            binding.layoutSlOne.jingyingchanpinSevenOk.setChecked(true);
        }else if(content.jingyingchanpinSeven==0) {
            binding.layoutSlOne.jingyingchanpinSevenNo.setChecked(true);
        }



        if (content.jingyingchanpinEight==1){
            binding.layoutSlOne.jingyingchanpinEightOk.setChecked(true);
        }else if(content.jingyingchanpinEight==0) {
            binding.layoutSlOne.jingyingchanpinEightNo.setChecked(true);
        }

        if (content.jingyingchanpinNine==1){
            binding.layoutSlOne.jingyingchanpinNineOk.setChecked(true);
        }else if(content.jingyingchanpinNine==0) {
            binding.layoutSlOne.jingyingchanpinNineNo.setChecked(true);
        }
        if (content.gouxiangjiluOne==1){
            binding.layoutSlOne.gouxiangjiluOneOk.setChecked(true);
        }else if(content.gouxiangjiluOne==0) {
            binding.layoutSlOne.gouxiangjiluOneNo.setChecked(true);
        }
        if (content.gouxiangjiluTwo==1){
            binding.layoutSlOne.gouxiangjiluTwoOk.setChecked(true);
        }else if(content.gouxiangjiluTwo==0) {
            binding.layoutSlOne.gouxiangjiluTwoNo.setChecked(true);
        }
        if (content.gouxiangjiluThree==1){
            binding.layoutSlOne.gouxiangjiluThreeOk.setChecked(true);
        }else if(content.gouxiangjiluThree==0) {
            binding.layoutSlOne.gouxiangjiluThreeNo.setChecked(true);
        }

    }


    /**
     * 	饲料领域 2
     * @param content
     * */
    private void   setSlTwoData(EnforcementDetailBean.Result.FeedField content) {

        if (content.tjjzhizhi1==1){
            binding.layoutSlTwo.tjjzhizhi1Ok.setChecked(true);
        }else if(content.tjjzhizhi1==0) {
            binding.layoutSlTwo.tjjzhizhi1No.setChecked(true);
        }

        if (content.tjjzhizhi2==1){
            binding.layoutSlTwo.tjjzhizhi2Ok.setChecked(true);
        }else if(content.tjjzhizhi2==0) {
            binding.layoutSlTwo.tjjzhizhi2No.setChecked(true);
        }

        if (content.tjjsctj1==1){
            binding.layoutSlTwo.tjjsctj1Ok.setChecked(true);
        }else if(content.tjjsctj1==0) {
            binding.layoutSlTwo.tjjsctj1No.setChecked(true);
        }

        if (content.tjjsctj2==1){
            binding.layoutSlTwo.tjjsctj2Ok.setChecked(true);
        }else if(content.tjjsctj2==0) {
            binding.layoutSlTwo.tjjsctj2No.setChecked(true);
        }

        if (content.tjjsctj3==1){
            binding.layoutSlTwo.tjjsctj3Ok.setChecked(true);
        }else if(content.tjjsctj3==0) {
            binding.layoutSlTwo.tjjsctj3No.setChecked(true);
        }

        if (content.tjjsctj4==1){
            binding.layoutSlTwo.tjjsctj4Ok.setChecked(true);
        }else if(content.tjjsctj4==0) {
            binding.layoutSlTwo.tjjsctj4No.setChecked(true);
        }

        if (content.tjjsctj5==1){
            binding.layoutSlTwo.tjjsctj5Ok.setChecked(true);
        }else if(content.tjjsctj5==0) {
            binding.layoutSlTwo.tjjsctj5No.setChecked(true);
        }


        if (content.tjjylsy1==1){
            binding.layoutSlTwo.tjjylsy1Ok.setChecked(true);
        }else if(content.tjjylsy1==0) {
            binding.layoutSlTwo.tjjylsy1No.setChecked(true);
        }


        if (content.tjjylsy2==1){
            binding.layoutSlTwo.tjjylsy2Ok.setChecked(true);
        }else if(content.tjjylsy2==0) {
            binding.layoutSlTwo.tjjylsy2No.setChecked(true);
        }


        if (content.tjjgfqk1==1){
            binding.layoutSlTwo.tjjgfqk1Ok.setChecked(true);
        }else if(content.tjjgfqk1==0) {
            binding.layoutSlTwo.tjjgfqk1No.setChecked(true);
        }



        if (content.tjjgfqk2==1){
            binding.layoutSlTwo.tjjgfqk2Ok.setChecked(true);
        }else if(content.tjjgfqk2==0) {
            binding.layoutSlTwo.tjjgfqk2No.setChecked(true);
        }

        if (content.tjjgfqk3==1){
            binding.layoutSlTwo.tjjgfqk3Ok.setChecked(true);
        }else if(content.tjjgfqk3==0) {
            binding.layoutSlTwo.tjjgfqk3No.setChecked(true);
        }
        if (content.tjjgfqk4==1){
            binding.layoutSlTwo.tjjgfqk4Ok.setChecked(true);
        }else if(content.tjjgfqk4==0) {
            binding.layoutSlTwo.tjjgfqk4No.setChecked(true);
        }
        if (content.tjjgfqk5==1){
            binding.layoutSlTwo.tjjgfqk5Ok.setChecked(true);
        }else if(content.tjjgfqk5==0) {
            binding.layoutSlTwo.tjjgfqk5No.setChecked(true);
        }
        if (content.tjjaqsc1==1){
            binding.layoutSlTwo.tjjaqsc1Ok.setChecked(true);
        }else if(content.tjjaqsc1==0) {
            binding.layoutSlTwo.tjjaqsc1No.setChecked(true);
        }


        if (content.tjjaqsc2==1){
            binding.layoutSlTwo.tjjaqsc2Ok.setChecked(true);
        }else if(content.tjjaqsc2==0) {
            binding.layoutSlTwo.tjjaqsc2No.setChecked(true);
        }
        if (content.tjjaqsc3==1){
            binding.layoutSlTwo.tjjaqsc3Ok.setChecked(true);
        }else if(content.tjjaqsc3==0) {
            binding.layoutSlTwo.tjjaqsc3No.setChecked(true);
        }
        if (content.tjjaqsc4==1){
            binding.layoutSlTwo.tjjaqsc4Ok.setChecked(true);
        }else if(content.tjjaqsc4==0) {
            binding.layoutSlTwo.tjjaqsc4No.setChecked(true);
        }
        if (content.tjjaqsc5==1){
            binding.layoutSlTwo.tjjaqsc5Ok.setChecked(true);
        }else if(content.tjjaqsc5==0) {
            binding.layoutSlTwo.tjjaqsc5No.setChecked(true);
        }
        if (content.tjjsccp1==1){
            binding.layoutSlTwo.tjjsccp1Ok.setChecked(true);
        }else if(content.tjjsccp1==0) {
            binding.layoutSlTwo.tjjsccp1No.setChecked(true);
        }

        if (content.tjjsccp2==1){
            binding.layoutSlTwo.tjjsccp2Ok.setChecked(true);
        }else if(content.tjjsccp2==0) {
            binding.layoutSlTwo.tjjsccp2No.setChecked(true);
        }
        if (content.tjjsccp3==1){
            binding.layoutSlTwo.tjjsccp3Ok.setChecked(true);
        }else if(content.tjjsccp3==0) {
            binding.layoutSlTwo.tjjsccp3No.setChecked(true);
        }
        if (content.tjjsccp4==4){
            binding.layoutSlTwo.tjjsccp4Ok.setChecked(true);
        }else if(content.tjjsccp4==0) {
            binding.layoutSlTwo.tjjsccp4No.setChecked(true);
        }
        if (content.tjjsccp5==1){
            binding.layoutSlTwo.tjjsccp5Ok.setChecked(true);
        }else if(content.tjjsccp5==0) {
            binding.layoutSlTwo.tjjsccp5No.setChecked(true);
        }
        if (content.tjjscly==1){
            binding.layoutSlTwo.tjjsclyOk.setChecked(true);
        }else if(content.tjjscly==0) {
            binding.layoutSlTwo.tjjsclyNo.setChecked(true);
        }
    }







    private void  setPigOneData(EnforcementDetailBean.Result.HogSlaughteringArea content) {

        binding.layoutPigOne.hatchPlayEggsEt.setFocusable(false);
        binding.layoutPigOne.hatchEggsEt.setFocusable(false);
        binding.layoutPigOne.hatchBroodHatchedEt.setFocusable(false);
        binding.layoutPigOne.managementSystemEt.setFocusable(false);
        binding.layoutPigOne.lastJanuaryOutNumEt.setFocusable(false);
        binding.layoutPigOne.quarantineReportEt.setFocusable(false);

        binding.layoutPigOne.hatchPlayEggsEt.setText(content.hatchPlayEggs);
        binding.layoutPigOne.hatchEggsEt.setText(content.hatchEggs);
        binding.layoutPigOne.hatchBroodHatchedEt.setText(content.hatchBroodHatched);
        binding.layoutPigOne.managementSystemEt.setText(content.managementSystem);
        binding.layoutPigOne.lastJanuaryOutNumEt.setText(content.lastJanuaryOutNum);
        binding.layoutPigOne.quarantineReportEt.setText(content.quarantineReport);


        if (content.hatchJCFYSS1 == 1) {
            binding.layoutPigOne.hatchJCFYSS1Ok.setChecked(true);
        } else if (content.hatchJCFYSS1 == 0) {
            binding.layoutPigOne.hatchJCFYSS1No.setChecked(true);
        }


        if (content.hatchJCFYSS2 == 1) {
            binding.layoutPigOne.hatchJCFYSS2Ok.setChecked(true);
        } else if (content.hatchJCFYSS2 == 0) {
            binding.layoutPigOne.hatchJCFYSS2No.setChecked(true);
        }

        if (content.hatchJCDJ1 == 1) {
            binding.layoutPigOne.hatchJCDJ1Ok.setChecked(true);
        } else if (content.hatchJCDJ1 == 0) {
            binding.layoutPigOne.hatchJCDJ1No.setChecked(true);
        }
        if (content.hatchJCDJ2 == 1) {
            binding.layoutPigOne.hatchJCDJ2Ok.setChecked(true);
        } else if (content.hatchJCDJ2 == 0) {
            binding.layoutPigOne.hatchJCDJ2No.setChecked(true);
        }
        if (content.hatchJCDJ3 == 1) {
            binding.layoutPigOne.hatchJCDJ3Ok.setChecked(true);
        } else if (content.hatchJCDJ3 == 0) {
            binding.layoutPigOne.hatchJCDJ3No.setChecked(true);
        }


        if (content.hatchWSXD1 == 1) {
            binding.layoutPigOne.hatchWSXD1Ok.setChecked(true);
        } else if (content.hatchWSXD1 == 0) {
            binding.layoutPigOne.hatchWSXD1No.setChecked(true);
        }
        if (content.hatchWSXD2 == 1) {
            binding.layoutPigOne.hatchWSXD2Ok.setChecked(true);
        } else if (content.hatchWSXD2 == 0) {
            binding.layoutPigOne.hatchWSXD2No.setChecked(true);
        }
        if (content.hatchWHH1 == 1) {
            binding.layoutPigOne.hatchWHH1Ok.setChecked(true);
        } else if (content.hatchWHH1 == 0) {
            binding.layoutPigOne.hatchWHH1No.setChecked(true);
        }
        if (content.hatchWHH2 == 1) {
            binding.layoutPigOne.hatchWHH2Ok.setChecked(true);
        } else if (content.hatchWHH2 == 0) {
            binding.layoutPigOne.hatchWHH2No.setChecked(true);
        }
        if (content.hatchYQBG == 1) {
            binding.layoutPigOne.hatchYQBGOk.setChecked(true);
        } else if (content.hatchYQBG == 0) {
            binding.layoutPigOne.hatchYQBGNo.setChecked(true);
        } else if (content.hatchYQBG == 2) {
            binding.layoutPigOne.hatchYQBGWY.setChecked(true);
        }
        if (content.hatchGXJL == 1) {
            binding.layoutPigOne.hatchGXJLOk.setChecked(true);
        } else if (content.hatchGXJL == 0) {
            binding.layoutPigOne.hatchGXJLNo.setChecked(true);
        }
    }




    private void  setPigTwoData(EnforcementDetailBean.Result.HogSlaughteringArea content) {
        if (content.tZZD1 == 1) {
            binding.layoutPigTwo.TZZD1Ok.setChecked(true);
        } else if (content.tZZD1 == 0) {
            binding.layoutPigTwo.TZZD1No.setChecked(true);
        }

        if (content.tZZD2 == 1) {
            binding.layoutPigTwo.TZZD2Ok.setChecked(true);
        } else if (content.tZZD2 == 0) {
            binding.layoutPigTwo.TZZD2No.setChecked(true);
        }

        if (content.tZZD3 == 1) {
            binding.layoutPigTwo.TZZD3Ok.setChecked(true);
        } else if (content.tZZD3 == 0) {
            binding.layoutPigTwo.TZZD3No.setChecked(true);
        }

        if (content.tZZD4 == 1) {
            binding.layoutPigTwo.TZZD4Ok.setChecked(true);
        } else if (content.tZZD4 == 0) {
            binding.layoutPigTwo.TZZD4No.setChecked(true);
        }

        if (content.tZZD5 == 1) {
            binding.layoutPigTwo.TZZD5Ok.setChecked(true);
        } else if (content.tZZD5 == 0) {
            binding.layoutPigTwo.TZZD5No.setChecked(true);
        }

        if (content.tZZD6 == 1) {
            binding.layoutPigTwo.TZZD6Ok.setChecked(true);
        } else if (content.tZZD6 == 0) {
            binding.layoutPigTwo.TZZD6No.setChecked(true);
        }

        if (content.tZZD7 == 1) {
            binding.layoutPigTwo.TZZD7Ok.setChecked(true);
        } else if (content.tZZD7 == 0) {
            binding.layoutPigTwo.TZZD7No.setChecked(true);
        }

        if (content.tZZD8 == 1) {
            binding.layoutPigTwo.TZZD8Ok.setChecked(true);
        } else if (content.tZZD8 == 0) {
            binding.layoutPigTwo.TZZD8No.setChecked(true);
        }

        if (content.tZZD9== 1) {
            binding.layoutPigTwo.TZZD9Ok.setChecked(true);
        } else if (content.tZZD9 == 0) {
            binding.layoutPigTwo.TZZD9No.setChecked(true);
        }

        if (content.tZZD10 == 1) {
            binding.layoutPigTwo.TZZD10Ok.setChecked(true);
        } else if (content.tZZD10 == 0) {
            binding.layoutPigTwo.TZZD10No.setChecked(true);
        }


        if (content.tZSSSBOne == 1) {
            binding.layoutPigTwo.TZSSSBOneOk.setChecked(true);
        } else if (content.tZSSSBOne == 0) {
            binding.layoutPigTwo.TZSSSBOneNo.setChecked(true);
        }

        if (content.tZSSSBTwo == 1) {
            binding.layoutPigTwo.TZSSSBTwoOk.setChecked(true);
        } else if (content.tZSSSBTwo == 0) {
            binding.layoutPigTwo.TZSSSBTwoNo.setChecked(true);
        }

        if (content.tZJCOne == 1) {
            binding.layoutPigTwo.TZJCOneOk.setChecked(true);
        } else if (content.tZJCOne == 0) {
            binding.layoutPigTwo.TZJCOneNo.setChecked(true);
        }

        if (content.tZJCTwo == 1) {
            binding.layoutPigTwo.TZJCTwoOk.setChecked(true);
        } else if (content.tZJCTwo == 0) {
            binding.layoutPigTwo.TZJCTwoNo.setChecked(true);
        }

        if (content.tZJCThree == 1) {
            binding.layoutPigTwo.TZJCThreeOk.setChecked(true);
        } else if (content.tZJCThree == 0) {
            binding.layoutPigTwo.TZJCThreeNo.setChecked(true);
        }

        if (content.tZDZOne == 1) {
            binding.layoutPigTwo.TZDZOneOk.setChecked(true);
        } else if (content.tZDZOne == 0) {
            binding.layoutPigTwo.TZDZOneNo.setChecked(true);
        }

        if (content.tZDZTwo == 1) {
            binding.layoutPigTwo.TZDZTwoOk.setChecked(true);
        } else if (content.tZDZTwo == 0) {
            binding.layoutPigTwo.TZDZTwoNo.setChecked(true);
        }
        if (content.tZDZThree == 1) {
            binding.layoutPigTwo.TZDZThreeOk.setChecked(true);
        } else if (content.tZDZThree == 0) {
            binding.layoutPigTwo.TZDZThreeNo.setChecked(true);
        }

        if (content.tZDZFour == 1) {
            binding.layoutPigTwo.TZDZFourOk.setChecked(true);
        } else if (content.tZDZFour == 0) {
            binding.layoutPigTwo.TZDZFourNo.setChecked(true);
        }

        if (content.tZDZFive == 1) {
            binding.layoutPigTwo.TZDZFiveOk.setChecked(true);
        } else if (content.tZDZFive == 0) {
            binding.layoutPigTwo.TZDZFiveNo.setChecked(true);
        }

        if (content.tZDZSix == 1) {
            binding.layoutPigTwo.TZDZSixOk.setChecked(true);
        } else if (content.tZDZSix == 0) {
            binding.layoutPigTwo.TZDZSixNo.setChecked(true);
        }

        if (content.tZWHHOne == 1) {
            binding.layoutPigTwo.TZWHHOneOk.setChecked(true);
        } else if (content.tZWHHOne == 0) {
            binding.layoutPigTwo.TZWHHOneNo.setChecked(true);
        }

        if (content.tZWHHTwo == 1) {
            binding.layoutPigTwo.TZWHHTwoOk.setChecked(true);
        } else if (content.tZWHHTwo == 0) {
            binding.layoutPigTwo.TZWHHTwoNo.setChecked(true);
        }

        if (content.tZCCSZOne == 1) {
            binding.layoutPigTwo.TZCCSZOneOk.setChecked(true);
        } else if (content.tZCCSZOne == 0) {
            binding.layoutPigTwo.TZCCSZOneOk.setChecked(true);
        }

        if (content.tZCCSZTwo == 1) {
            binding.layoutPigTwo.TZCCSZTwoOk.setChecked(true);
        } else if (content.tZCCSZTwo == 0) {
            binding.layoutPigTwo.TZCCSZTwoNo.setChecked(true);
        }
        if (content.tZCCSZThree == 1) {
            binding.layoutPigTwo.TZCCSZThreeOk.setChecked(true);
        } else if (content.tZCCSZThree == 0) {
            binding.layoutPigTwo.TZCCSZThreeNo.setChecked(true);
        }

        if (content.tZRYTJOne == 1) {
            binding.layoutPigTwo.TZRYTJOneOk.setChecked(true);
        } else if (content.tZRYTJOne == 0) {
            binding.layoutPigTwo.TZRYTJOneNo.setChecked(true);
        }
        if (content.tZRYTJTwo == 1) {
            binding.layoutPigTwo.TZRYTJTwoOk.setChecked(true);
        } else if (content.tZRYTJTwo == 0) {
            binding.layoutPigTwo.TZRYTJTwoNo.setChecked(true);
        }
        if (content.tZXXBSOne == 1) {
            binding.layoutPigTwo.TZXXBSOneOk.setChecked(true);
        } else if (content.tZXXBSOne == 0) {
            binding.layoutPigTwo.TZXXBSOneNo.setChecked(true);
        }

        if (content.tZXXBSTwo == 1) {
            binding.layoutPigTwo.TZXXBSTwoOk.setChecked(true);
        } else if (content.tZXXBSTwo == 0) {
            binding.layoutPigTwo.TZXXBSTwoOk.setChecked(true);
        }
        if (content.tZXXBSThree == 1) {
            binding.layoutPigTwo.TZXXBSThreeOk.setChecked(true);
        } else if (content.tZXXBSThree == 0) {
            binding.layoutPigTwo.TZXXBSThreeNo.setChecked(true);
        }
        if (content.tZDAGL== 1) {
            binding.layoutPigTwo.TZDAGLOk.setChecked(true);
        } else if (content.tZDAGL == 0) {
            binding.layoutPigTwo.TZDAGLNo.setChecked(true);
        }

        if (content.tuzai1== 1) {
            binding.layoutPigTwo.tuizai1Ok.setChecked(true);
        } else if (content.tuzai1 == 0) {
            binding.layoutPigTwo.tuizai1No.setChecked(true);
        }
        if (content.tuzai2== 1) {
            binding.layoutPigTwo.tuizai2Ok.setChecked(true);
        } else if (content.tuzai2 == 0) {
            binding.layoutPigTwo.tuizai2No.setChecked(true);
        }
        if (content.tuzai3== 1) {
            binding.layoutPigTwo.tuizai3Ok.setChecked(true);
        } else if (content.tuzai3 == 0) {
            binding.layoutPigTwo.tuizai3No.setChecked(true);
        }
        if (content.tuzai4== 1) {
            binding.layoutPigTwo.tuizai4Ok.setChecked(true);
        } else if (content.tuzai4 == 0) {
            binding.layoutPigTwo.tuizai4No.setChecked(true);
        }
        if (content.tuzai5== 1) {
            binding.layoutPigTwo.tuizai5Ok.setChecked(true);
        } else if (content.tuzai5 == 0) {
            binding.layoutPigTwo.tuizai5No.setChecked(true);
        }
        if (content.tuzai6== 1) {
            binding.layoutPigTwo.tuizai6Ok.setChecked(true);
        } else if (content.tuzai6 == 0) {
            binding.layoutPigTwo.tuizai6No.setChecked(true);
        }
        if (content.tuzai7== 1) {
            binding.layoutPigTwo.tuizai7Ok.setChecked(true);
        } else if (content.tuzai7 == 0) {
            binding.layoutPigTwo.tuizai7No.setChecked(true);
        }


    }






    private void  setPigThreeData(EnforcementDetailBean.Result.HogSlaughteringArea content) {

        binding.layoutPigThree.recordNoEt.setFocusable(false);
        binding.layoutPigThree.carNumEt.setFocusable(false);
        binding.layoutPigThree.traffickingAnimalEt.setFocusable(false);
        binding.layoutPigThree.traffickedNumEt.setFocusable(false);
        binding.layoutPigThree.badgesNotWornEt.setFocusable(false);


        binding.layoutPigThree.recordNoEt.setText(content.recordNo);
        binding.layoutPigThree.carNumEt.setText(content.carNum);
        binding.layoutPigThree.traffickingAnimalEt.setText(content.traffickingAnimal);
        binding.layoutPigThree.traffickedNumEt.setText(content.traffickedNum);
        binding.layoutPigThree.badgesNotWornEt.setText(content.badgesNotWorn);


        if (content.clinical == 1) {
            binding.layoutPigThree.clinicalOk.setChecked(true);
        } else if (content.clinical == 0) {
            binding.layoutPigThree.clinicalNo.setChecked(true);
        }
        if (content.traffickQuarantineCertificate == 1) {
            binding.layoutPigThree.traffickQuarantineCertificateOk.setChecked(true);
        } else if (content.traffickQuarantineCertificate == 0) {
            binding.layoutPigThree.traffickQuarantineCertificateNo.setChecked(true);
        }
        if (content.waterflooding == 1) {
            binding.layoutPigThree.waterfloodingOk.setChecked(true);
        } else if (content.waterflooding == 0) {
            binding.layoutPigThree.waterfloodingNo.setChecked(true);
        }

        if (content.carRequirement == 1) {
            binding.layoutPigThree.carRequirementOk.setChecked(true);
        } else if (content.carRequirement == 0) {
            binding.layoutPigThree.carRequirementNo.setChecked(true);
        }
        if (content.vehicleDisinfection == 1) {
            binding.layoutPigThree.vehicleDisinfectionOk.setChecked(true);
        } else if (content.vehicleDisinfection == 0) {
            binding.layoutPigThree.vehicleDisinfectionNo.setChecked(true);
        }
        if (content.wasteDisposalMethod == 1) {
            binding.layoutPigThree.wasteDisposalMethodOk.setChecked(true);
        } else if (content.wasteDisposalMethod == 0) {
            binding.layoutPigThree.wasteDisposalMethodNo.setChecked(true);
        }
        if (content.designatedChannel == 1) {
            binding.layoutPigThree.designatedChannelOk.setChecked(true);
        } else if (content.designatedChannel == 0) {
            binding.layoutPigThree.designatedChannelNo.setChecked(true);
        }
        if (content.ledger == 1) {
            binding.layoutPigThree.ledgerOk.setChecked(true);
        } else if (content.ledger == 0) {
            binding.layoutPigThree.ledgerNo.setChecked(true);
        }else if (content.ledger == 2) {
            binding.layoutPigThree.ledgerWu.setChecked(true);
        }

        if (content.healthCertificate == 1) {
            binding.layoutPigThree.healthCertificateOk.setChecked(true);
        } else if (content.healthCertificate == 0) {
            binding.layoutPigThree.healthCertificateNo.setChecked(true);
        }

    }


    private void  setPigFourData(EnforcementDetailBean.Result.HogSlaughteringArea content) {

        binding.layoutPigFour.animalMedicalPermitEt.setFocusable(false);
        binding.layoutPigFour.licenceIssuedEt.setFocusable(false);
        binding.layoutPigFour.medicalInstitutionsTypeEt.setFocusable(false);
        binding.layoutPigFour.employeesNumEt.setFocusable(false);
        binding.layoutPigFour.licensedVeterinarianNumEt.setFocusable(false);
        binding.layoutPigFour.practicingAssistantVeterinarianNumEt.setFocusable(false);
        binding.layoutPigFour.practiceProjectEt.setFocusable(false);
        binding.layoutPigFour.useRegistrationEt.setFocusable(false);


        binding.layoutPigFour.animalMedicalPermitEt.setText(content.animalMedicalPermit);
        binding.layoutPigFour.licenceIssuedEt.setText(content.licenceIssued);
        binding.layoutPigFour.medicalInstitutionsTypeEt.setText(content.medicalInstitutionsType);
        binding.layoutPigFour.employeesNumEt.setText(content.employeesNum);
        binding.layoutPigFour.licensedVeterinarianNumEt.setText(content.licensedVeterinarianNum);
        binding.layoutPigFour.practicingAssistantVeterinarianNumEt.setText(content.practicingAssistantVeterinarianNum);
        binding.layoutPigFour.practiceProjectEt.setText(content.practiceProject);
        binding.layoutPigFour.useRegistrationEt.setText(content.useRegistration);

        if (content.consistentSituation == 1) {
            binding.layoutPigFour.consistentSituationOk.setChecked(true);
        } else if (content.consistentSituation == 0) {
            binding.layoutPigFour.consistentSituationNo.setChecked(true);
        }
        if (content.diagnosisTreatmentManagement == 1) {
            binding.layoutPigFour.diagnosisTreatmentManagementOk.setChecked(true);
        } else if (content.diagnosisTreatmentManagement == 0) {
            binding.layoutPigFour.diagnosisTreatmentManagementNo.setChecked(true);
        }


        if (content.employeesInformation == 1) {
            binding.layoutPigFour.employeesInformationOk.setChecked(true);
        } else if (content.employeesInformation == 0) {
            binding.layoutPigFour.employeesInformationNo.setChecked(true);
        }

        if (content.prescribedMedication == 1) {
            binding.layoutPigFour.prescribedMedicationOk.setChecked(true);
        } else if (content.prescribedMedication == 0) {
            binding.layoutPigFour.prescribedMedicationNo.setChecked(true);
        }



        if (content.useVeterinaryDrugs == 1) {
            binding.layoutPigFour.useVeterinaryDrugsOk.setChecked(true);
        } else if (content.useVeterinaryDrugs == 0) {
            binding.layoutPigFour.useVeterinaryDrugsNo.setChecked(true);
        }


        if (content.drugProhibition == 1) {
            binding.layoutPigFour.drugProhibitionOk.setChecked(true);
        } else if (content.drugProhibition == 0) {
            binding.layoutPigFour.drugProhibitionNo.setChecked(true);
        }
        if (content.departmentalApproval == 1) {
            binding.layoutPigFour.departmentalApprovalOk.setChecked(true);
        } else if (content.departmentalApproval == 0) {
            binding.layoutPigFour.departmentalApprovalNo.setChecked(true);
        }

        if (content.organizationName == 1) {
            binding.layoutPigFour.organizationNameOk.setChecked(true);
        } else if (content.organizationName == 0) {
            binding.layoutPigFour.organizationNameNo.setChecked(true);
        }


        if (content.termPreservation == 1) {
            binding.layoutPigFour.termPreservationOk.setChecked(true);
        } else if (content.termPreservation == 0) {
            binding.layoutPigFour.termPreservationNo.setChecked(true);
        }


        if (content.reportEpidemic == 1) {
            binding.layoutPigFour.reportEpidemicOk.setChecked(true);
        } else if (content.reportEpidemic == 0) {
            binding.layoutPigFour.reportEpidemicNo.setChecked(true);
        }else if (content.reportEpidemic == 2) {
            binding.layoutPigFour.reportEpidemicWu.setChecked(true);
        }


        if (content.giveTreatment == 1) {
            binding.layoutPigFour.giveTreatmentOk.setChecked(true);
        } else if (content.giveTreatment == 0) {
            binding.layoutPigFour.giveTreatmentNo.setChecked(true);
        }else if (content.giveTreatment == 2) {
            binding.layoutPigFour.giveTreatmentWu.setChecked(true);
        }


        if (content.harmlessAnimal == 1) {
            binding.layoutPigFour.harmlessAnimalOk.setChecked(true);
        } else if (content.harmlessAnimal == 0) {
            binding.layoutPigFour.harmlessAnimalNo.setChecked(true);
        }

        if (content.wasteWater == 1) {
            binding.layoutPigFour.wasteWaterOk.setChecked(true);
        } else if (content.wasteWater == 0) {
            binding.layoutPigFour.wasteWaterNo.setChecked(true);
        }

        if (content.reportIssuingAuthority == 1) {
            binding.layoutPigFour.reportIssuingAuthorityOk.setChecked(true);
        } else if (content.reportIssuingAuthority == 0) {
            binding.layoutPigFour.reportIssuingAuthorityNo.setChecked(true);
        }

        if (content.peopleHealthCertificate == 1) {
            binding.layoutPigFour.peopleHealthCertificateOk.setChecked(true);
        } else if (content.peopleHealthCertificate == 0) {
            binding.layoutPigFour.peopleHealthCertificateNo.setChecked(true);
        }
        if (content.requiredRecord == 1) {
            binding.layoutPigFour.requiredRecordOk.setChecked(true);
        } else if (content.requiredRecord == 0) {
            binding.layoutPigFour.requiredRecordNo.setChecked(true);
        }
        if (content.standardFilling == 1) {
            binding.layoutPigFour.standardFillingOk.setChecked(true);
        } else if (content.standardFilling == 0) {
            binding.layoutPigFour.standardFillingNo.setChecked(true);
        }

        if (content.reportFilingAuthority == 1) {
            binding.layoutPigFour.reportFilingAuthorityOk.setChecked(true);
        } else if (content.reportFilingAuthority == 0) {
            binding.layoutPigFour.reportFilingAuthorityNo.setChecked(true);
        }
        if (content.recordsComplete == 1) {
            binding.layoutPigFour.recordsCompleteOk.setChecked(true);
        } else if (content.recordsComplete == 0) {
            binding.layoutPigFour.recordsCompleteNo.setChecked(true);
        }
    }

    private void  setPigFiveData(EnforcementDetailBean.Result.HogSlaughteringArea content){

        binding.layoutPigFive.countrysideAnimalMedicalPermitEt.setFocusable(false);
        binding.layoutPigFive.countrysideDateIssueEt.setFocusable(false);
        binding.layoutPigFive.countrysideEmployeesTotalNumEt.setFocusable(false);
        binding.layoutPigFive.countrysideLicensedVeterinarianEt.setFocusable(false);
        binding.layoutPigFive.countrysidePracticingAssistantVeterinarianEt.setFocusable(false);
        binding.layoutPigFive.countrysideRuralVeterinarianEt.setFocusable(false);
        binding.layoutPigFive.countrysideVeterinarianNameEt.setFocusable(false);
        binding.layoutPigFive.countrysideEmploymentCertificateNumberEt.setFocusable(false);


        binding.layoutPigFive.countrysideAnimalMedicalPermitEt.setText(content.countrysideAnimalMedicalPermit);
        binding.layoutPigFive.countrysideDateIssueEt.setText(content.countrysideDateIssue);
        binding.layoutPigFive.countrysideEmployeesTotalNumEt.setText(content.countrysideEmployeesTotalNum);
        binding.layoutPigFive.countrysideLicensedVeterinarianEt.setText(content.countrysideLicensedVeterinarian);
        binding.layoutPigFive.countrysidePracticingAssistantVeterinarianEt.setText(content.countrysidePracticingAssistantVeterinarian);
        binding.layoutPigFive.countrysideRuralVeterinarianEt.setText(content.countrysideRuralVeterinarian);
        binding.layoutPigFive.countrysideVeterinarianNameEt.setText(content.countrysideVeterinarianName);
        binding.layoutPigFive.countrysideEmploymentCertificateNumberEt.setText(content.countrysideEmploymentCertificateNumber);

        if (content.countrysideOne == 1) {
            binding.layoutPigFive.countrysideOneOk.setChecked(true);
        } else if (content.countrysideOne == 0) {
            binding.layoutPigFive.countrysideOneNo.setChecked(true);
        }
        if (content.countrysideTwo == 1) {
            binding.layoutPigFive.countrysideTwoOk.setChecked(true);
        } else if (content.countrysideTwo == 0) {
            binding.layoutPigFive.countrysideTwoNo.setChecked(true);
        }
        if (content.countrysideThree== 1) {
            binding.layoutPigFive.countrysideThreeOk.setChecked(true);
        } else if (content.countrysideThree == 0) {
            binding.layoutPigFive.countrysideThreeNo.setChecked(true);
        }
        if (content.countrysideFour== 1) {
            binding.layoutPigFive.countrysideFourOk.setChecked(true);
        } else if (content.countrysideFour == 0) {
            binding.layoutPigFive.countrysideFourNo.setChecked(true);
        }
        if (content.countrysideFive== 1) {
            binding.layoutPigFive.countrysideFiveOk.setChecked(true);
        } else if (content.countrysideFive == 0) {
            binding.layoutPigFive.countrysideFiveNo.setChecked(true);
        }
        if (content.countrysideSix== 1) {
            binding.layoutPigFive.countrysideSixOk.setChecked(true);
        } else if (content.countrysideSix == 0) {
            binding.layoutPigFive.countrysideSixNo.setChecked(true);
        } else if (content.countrysideSix == 2) {
            binding.layoutPigFive.countrysideSixWu.setChecked(true);
        }
        if (content.countrysideSeven== 1) {
            binding.layoutPigFive.countrysideSevenOk.setChecked(true);
        } else if (content.countrysideSeven == 0) {
            binding.layoutPigFive.countrysideSevenNo.setChecked(true);
        } else if (content.countrysideSeven == 2) {
            binding.layoutPigFive.countrysideSevenWu.setChecked(true);
        }
        if (content.countrysideEight== 1) {
            binding.layoutPigFive.countrysideEightOk.setChecked(true);
        } else if (content.countrysideEight == 0) {
            binding.layoutPigFive.countrysideEightNo.setChecked(true);
        }
    }

    private void  setPigSixData(EnforcementDetailBean.Result.HogSlaughteringArea content){
        if (content.slaughter1 == 1) {
            binding.layoutPigSix.slaughter1Ok.setChecked(true);
        } else if (content.slaughter1 == 0) {
            binding.layoutPigSix.slaughter1No.setChecked(true);
        }
        if (content.slaughter2 == 1) {
            binding.layoutPigSix.slaughter2Ok.setChecked(true);
        } else if (content.slaughter2 == 0) {
            binding.layoutPigSix.slaughter2No.setChecked(true);
        }
        if (content.slaughter3== 1) {
            binding.layoutPigSix.slaughter3Ok.setChecked(true);
        } else if (content.slaughter3 == 0) {
            binding.layoutPigSix.slaughter3No.setChecked(true);
        }
        if (content.slaughter4== 1) {
            binding.layoutPigSix.slaughter4Ok.setChecked(true);
        } else if (content.slaughter4 == 0) {
            binding.layoutPigSix.slaughter4No.setChecked(true);
        }
        if (content.slaughter5== 1) {
            binding.layoutPigSix.slaughter5Ok.setChecked(true);
        } else if (content.slaughter5 == 0) {
            binding.layoutPigSix.slaughter5No.setChecked(true);
        }
        if (content.slaughter6== 1) {
            binding.layoutPigSix.slaughter6Ok.setChecked(true);
        } else if (content.slaughter6 == 0) {
            binding.layoutPigSix.slaughter6No.setChecked(true);
        }
        if (content.slaughter7== 1) {
            binding.layoutPigSix.slaughter7Ok.setChecked(true);
        } else if (content.slaughter7 == 0) {
            binding.layoutPigSix.slaughter7Ok.setChecked(true);
        }
        if (content.slaughter8== 1) {
            binding.layoutPigSix.slaughter8Ok.setChecked(true);
        } else if (content.slaughter8 == 0) {
            binding.layoutPigSix.slaughter8No.setChecked(true);
        }
        if (content.slaughter8== 1) {
            binding.layoutPigSix.slaughter8Ok.setChecked(true);
        } else if (content.slaughter8 == 0) {
            binding.layoutPigSix.slaughter8No.setChecked(true);
        }
        if (content.slaughter10== 1) {
            binding.layoutPigSix.slaughter10Ok.setChecked(true);
        } else if (content.slaughter10 == 0) {
            binding.layoutPigSix.slaughter10No.setChecked(true);
        }
        if (content.slaughter11== 1) {
            binding.layoutPigSix.slaughter11Ok.setChecked(true);
        } else if (content.slaughter11 == 0) {
            binding.layoutPigSix.slaughter11No.setChecked(true);
        }
        if (content.slaughter12 == 1) {
            binding.layoutPigSix.slaughter12Ok.setChecked(true);
        } else if (content.slaughter12 == 0) {
            binding.layoutPigSix.slaughter12No.setChecked(true);
        }
        if (content.slaughter13== 1) {
            binding.layoutPigSix.slaughter13Ok.setChecked(true);
        } else if (content.slaughter13 == 0) {
            binding.layoutPigSix.slaughter13No.setChecked(true);
        }
        if (content.slaughter14== 1) {
            binding.layoutPigSix.slaughter14Ok.setChecked(true);
        } else if (content.slaughter14 == 0) {
            binding.layoutPigSix.slaughter14No.setChecked(true);
        }
        if (content.slaughter15== 1) {
            binding.layoutPigSix.slaughter15Ok.setChecked(true);
        } else if (content.slaughter15 == 0) {
            binding.layoutPigSix.slaughter15No.setChecked(true);
        }
        if (content.slaughter16== 1) {
            binding.layoutPigSix.slaughter16Ok.setChecked(true);
        } else if (content.slaughter16 == 0) {
            binding.layoutPigSix.slaughter16No.setChecked(true);
        }
        if (content.slaughter17== 1) {
            binding.layoutPigSix.slaughter17Ok.setChecked(true);
        } else if (content.slaughter17 == 0) {
            binding.layoutPigSix.slaughter17No.setChecked(true);
        }
    }


    private void  setPlantData(EnforcementDetailBean.Result.FieldPlantQuarantine content){
        if (content.plant1 == 1) {
            binding.layoutPlant.plant1Ok.setChecked(true);
        } else if (content.plant1 == 0) {
            binding.layoutPlant.plant1No.setChecked(true);
        }

        if (content.plant2 == 1) {
            binding.layoutPlant.plant2Ok.setChecked(true);
        } else if (content.plant2 == 0) {
            binding.layoutPlant.plant2No.setChecked(true);
        }
        if (content.plant3 == 1) {
            binding.layoutPlant.plant3Ok.setChecked(true);
        } else if (content.plant3 == 0) {
            binding.layoutPlant.plant3No.setChecked(true);
        }
        if (content.plant4 == 1) {
            binding.layoutPlant.plant4Ok.setChecked(true);
        } else if (content.plant4 == 0) {
            binding.layoutPlant.plant4No.setChecked(true);
        }
        if (content.plant5 == 1) {
            binding.layoutPlant.plant5Ok.setChecked(true);
        } else if (content.plant5 == 0) {
            binding.layoutPlant.plant5No.setChecked(true);
        }
        if (content.plant6 == 1) {
            binding.layoutPlant.plant6Ok.setChecked(true);
        } else if (content.plant6 == 0) {
            binding.layoutPlant.plant6No.setChecked(true);
        }
        if (content.plant7 == 1) {
            binding.layoutPlant.plant7Ok.setChecked(true);
        } else if (content.plant7 == 0) {
            binding.layoutPlant.plant7No.setChecked(true);
        }
        if (content.plant8 == 1) {
            binding.layoutPlant.plant8Ok.setChecked(true);
        } else if (content.plant8 == 0) {
            binding.layoutPlant.plant8No.setChecked(true);
        }
        if (content.plant9 == 1) {
            binding.layoutPlant.plant9Ok.setChecked(true);
        } else if (content.plant9 == 0) {
            binding.layoutPlant.plant9No.setChecked(true);
        }
        if (content.plant10 == 1) {
            binding.layoutPlant.plant10Ok.setChecked(true);
        } else if (content.plant10 == 0) {
            binding.layoutPlant.plant10No.setChecked(true);
        }
        if (content.plant11 == 1) {
            binding.layoutPlant.plant11Ok.setChecked(true);
        } else if (content.plant11 == 0) {
            binding.layoutPlant.plant11No.setChecked(true);
        }
        if (content.plant12 == 1) {
            binding.layoutPlant.plant12Ok.setChecked(true);
        } else if (content.plant12 == 0) {
            binding.layoutPlant.plant12No.setChecked(true);
        }
        if (content.plant13 == 1) {
            binding.layoutPlant.plant13Ok.setChecked(true);
        } else if (content.plant13 == 0) {
            binding.layoutPlant.plant13No.setChecked(true);
        }
    }


    private  void  setZXQOneData(EnforcementDetailBean.Result.LivestockPoultryFields content){
        if (content.zxq1 == 1) {
            binding.layoutBreed.zxq1Ok.setChecked(true);
        } else if (content.zxq1 == 0) {
            binding.layoutBreed.zxq1No.setChecked(true);
        }
        if (content.zxq2 == 1) {
            binding.layoutBreed.zxq2Ok.setChecked(true);
        } else if (content.zxq2 == 0) {
            binding.layoutBreed.zxq2No.setChecked(true);
        }
        if (content.zxq3 == 1) {
            binding.layoutBreed.zxq3Ok.setChecked(true);
        } else if (content.zxq3 == 0) {
            binding.layoutBreed.zxq3No.setChecked(true);
        }
        if (content.zxq4 == 1) {
            binding.layoutBreed.zxq4Ok.setChecked(true);
        } else if (content.zxq4 == 0) {
            binding.layoutBreed.zxq4No.setChecked(true);
        }
        if (content.zxq5 == 1) {
            binding.layoutBreed.zxq5Ok.setChecked(true);
        } else if (content.zxq5 == 0) {
            binding.layoutBreed.zxq5No.setChecked(true);
        }
        if (content.zxq6 == 1) {
            binding.layoutBreed.zxq6Ok.setChecked(true);
        } else if (content.zxq6 == 0) {
            binding.layoutBreed.zxq6No.setChecked(true);
        }
        if (content.zxq7 == 1) {
            binding.layoutBreed.zxq7Ok.setChecked(true);
        } else if (content.zxq7 == 0) {
            binding.layoutBreed.zxq7No.setChecked(true);
        }
        if (content.zxq8 == 1) {
            binding.layoutBreed.zxq8Ok.setChecked(true);
        } else if (content.zxq8 == 0) {
            binding.layoutBreed.zxq8No.setChecked(true);
        }
        if (content.zxq9 == 1) {
            binding.layoutBreed.zxq9Ok.setChecked(true);
        } else if (content.zxq9 == 0) {
            binding.layoutBreed.zxq9No.setChecked(true);
        }
        if (content.zxq10 == 1) {
            binding.layoutBreed.zxq10Ok.setChecked(true);
        } else if (content.zxq10 == 0) {
            binding.layoutBreed.zxq10No.setChecked(true);
        }
        if (content.zxq11 == 1) {
            binding.layoutBreed.zxq11Ok.setChecked(true);
        } else if (content.zxq11 == 0) {
            binding.layoutBreed.zxq11No.setChecked(true);
        }
        if (content.zxq12 == 1) {
            binding.layoutBreed.zxq12Ok.setChecked(true);
        } else if (content.zxq12 == 0) {
            binding.layoutBreed.zxq12No.setChecked(true);
        }
    }


    private  void  setZXQTwoData(EnforcementDetailBean.Result.LivestockPoultryFields content){
        if (content.zxqTwo1 == 1) {
            binding.layoutBreedTwo.zxqTwo1Ok.setChecked(true);
        } else if (content.zxqTwo1 == 0) {
            binding.layoutBreedTwo.zxqTwo1No.setChecked(true);
        }
        if (content.zxqTwo2 == 1) {
            binding.layoutBreedTwo.zxqTwo2Ok.setChecked(true);
        } else if (content.zxqTwo2 == 0) {
            binding.layoutBreedTwo.zxqTwo2No.setChecked(true);
        }
        if (content.zxqTwo3 == 1) {
            binding.layoutBreedTwo.zxqTwo3Ok.setChecked(true);
        } else if (content.zxqTwo3 == 0) {
            binding.layoutBreedTwo.zxqTwo3No.setChecked(true);
        }
        if (content.zxqTwo4 == 1) {
            binding.layoutBreedTwo.zxqTwo4Ok.setChecked(true);
        } else if (content.zxqTwo4 == 0) {
            binding.layoutBreedTwo.zxqTwo4No.setChecked(true);
        }
        if (content.zxqTwo5 == 1) {
            binding.layoutBreedTwo.zxqTwo5Ok.setChecked(true);
        } else if (content.zxqTwo5 == 0) {
            binding.layoutBreedTwo.zxqTwo5No.setChecked(true);
        }
        if (content.zxqTwo6 == 1) {
            binding.layoutBreedTwo.zxqTwo6Ok.setChecked(true);
        } else if (content.zxqTwo6 == 0) {
            binding.layoutBreedTwo.zxqTwo6No.setChecked(true);
        }
        if (content.zxqTwo7 == 1) {
            binding.layoutBreedTwo.zxqTwo7Ok.setChecked(true);
        } else if (content.zxqTwo7 == 0) {
            binding.layoutBreedTwo.zxqTwo7No.setChecked(true);
        }
        if (content.zxqTwo8 == 1) {
            binding.layoutBreedTwo.zxqTwo8Ok.setChecked(true);
        } else if (content.zxqTwo8 == 0) {
            binding.layoutBreedTwo.zxqTwo8No.setChecked(true);
        }
        if (content.zxqTwo9 == 1) {
            binding.layoutBreedTwo.zxqTwo9Ok.setChecked(true);
        } else if (content.zxqTwo9 == 0) {
            binding.layoutBreedTwo.zxqTwo9No.setChecked(true);
        }
    }



    private  void  setFishOneData(EnforcementDetailBean.Result.FisheryArea content){
        if (content.fishOne1 == 1) {
            binding.layoutFishOne.fishOne1Ok.setChecked(true);
        } else if (content.fishOne1 == 0) {
            binding.layoutFishOne.fishOne1No.setChecked(true);
        }
        if (content.fishOne2 == 1) {
            binding.layoutFishOne.fishOne2Ok.setChecked(true);
        } else if (content.fishOne2 == 0) {
            binding.layoutFishOne.fishOne2No.setChecked(true);
        }
        if (content.fishOne3 == 1) {
            binding.layoutFishOne.fishOne3Ok.setChecked(true);
        } else if (content.fishOne3 == 0) {
            binding.layoutFishOne.fishOne3No.setChecked(true);
        }
        if (content.fishOne4 == 1) {
            binding.layoutFishOne.fishOne4Ok.setChecked(true);
        } else if (content.fishOne4 == 0) {
            binding.layoutFishOne.fishOne4No.setChecked(true);
        }
        if (content.fishOne5 == 1) {
            binding.layoutFishOne.fishOne5Ok.setChecked(true);
        } else if (content.fishOne5 == 0) {
            binding.layoutFishOne.fishOne5No.setChecked(true);
        }
        if (content.fishOne6 == 1) {
            binding.layoutFishOne.fishOne6Ok.setChecked(true);
        } else if (content.fishOne6 == 0) {
            binding.layoutFishOne.fishOne6No.setChecked(true);
        }
        if (content.fishOne7 == 1) {
            binding.layoutFishOne.fishOne7Ok.setChecked(true);
        } else if (content.fishOne7 == 0) {
            binding.layoutFishOne.fishOne7No.setChecked(true);
        }
        if (content.fishOne8 == 1) {
            binding.layoutFishOne.fishOne8Ok.setChecked(true);
        } else if (content.fishOne8 == 0) {
            binding.layoutFishOne.fishOne8No.setChecked(true);
        }
        if (content.fishOne9 == 1) {
            binding.layoutFishOne.fishOne9Ok.setChecked(true);
        } else if (content.fishOne9 == 0) {
            binding.layoutFishOne.fishOne9No.setChecked(true);
        }
        if (content.fishOne10 == 1) {
            binding.layoutFishOne.fishOne10Ok.setChecked(true);
        } else if (content.fishOne10 == 0) {
            binding.layoutFishOne.fishOne10No.setChecked(true);
        }
        if (content.fishOne11 == 1) {
            binding.layoutFishOne.fishOne11Ok.setChecked(true);
        } else if (content.fishOne11 == 0) {
            binding.layoutFishOne.fishOne11No.setChecked(true);
        }
        if (content.fishOne12 == 1) {
            binding.layoutFishOne.fishOne12Ok.setChecked(true);
        } else if (content.fishOne12 == 0) {
            binding.layoutFishOne.fishOne12No.setChecked(true);
        }
        if (content.fishOne13 == 1) {
            binding.layoutFishOne.fishOne13Ok.setChecked(true);
        } else if (content.fishOne13 == 0) {
            binding.layoutFishOne.fishOne13No.setChecked(true);
        }
        if (content.fishOne14 == 1) {
            binding.layoutFishOne.fishOne14Ok.setChecked(true);
        } else if (content.fishOne14 == 0) {
            binding.layoutFishOne.fishOne14No.setChecked(true);
        }
        if (content.fishOne15 == 1) {
            binding.layoutFishOne.fishOne15Ok.setChecked(true);
        } else if (content.fishOne15 == 0) {
            binding.layoutFishOne.fishOne15No.setChecked(true);
        }
    }

    private  void  setFishTwoData(EnforcementDetailBean.Result.FisheryArea content){
        if (content.fishTwo1 == 1) {
            binding.layoutFishTwo.fishTwo1Ok.setChecked(true);
        } else if (content.fishTwo1 == 0) {
            binding.layoutFishTwo.fishTwo1No.setChecked(true);
        }
        if (content.fishTwo2 == 1) {
            binding.layoutFishTwo.fishTwo2Ok.setChecked(true);
        } else if (content.fishTwo2 == 0) {
            binding.layoutFishTwo.fishTwo2No.setChecked(true);
        }
        if (content.fishTwo3 == 1) {
            binding.layoutFishTwo.fishTwo3Ok.setChecked(true);
        } else if (content.fishTwo3 == 0) {
            binding.layoutFishTwo.fishTwo3No.setChecked(true);
        }
        if (content.fishTwo4 == 1) {
            binding.layoutFishTwo.fishTwo4Ok.setChecked(true);
        } else if (content.fishTwo4 == 0) {
            binding.layoutFishTwo.fishTwo4No.setChecked(true);
        }
        if (content.fishTwo5 == 1) {
            binding.layoutFishTwo.fishTwo5Ok.setChecked(true);
        } else if (content.fishTwo5 == 0) {
            binding.layoutFishTwo.fishTwo5No.setChecked(true);
        }
        if (content.fishTwo6 == 1) {
            binding.layoutFishTwo.fishTwo6Ok.setChecked(true);
        } else if (content.fishTwo6 == 0) {
            binding.layoutFishTwo.fishTwo6No.setChecked(true);
        }
        if (content.fishTwo7 == 1) {
            binding.layoutFishTwo.fishTwo7Ok.setChecked(true);
        } else if (content.fishTwo7 == 0) {
            binding.layoutFishTwo.fishTwo7No.setChecked(true);
        }
        if (content.fishTwo8 == 1) {
            binding.layoutFishTwo.fishTwo8Ok.setChecked(true);
        } else if (content.fishTwo8 == 0) {
            binding.layoutFishTwo.fishTwo8No.setChecked(true);
        }
        if (content.fishTwo9 == 1) {
            binding.layoutFishTwo.fishTwo9Ok.setChecked(true);
        } else if (content.fishTwo9 == 0) {
            binding.layoutFishTwo.fishTwo9No.setChecked(true);
        }
    }

    private  void  setFishThreeData(EnforcementDetailBean.Result.FisheryArea content){
        if (content.fishThree1 == 1) {
            binding.layoutFishThree.fishThree1Ok.setChecked(true);
        } else if (content.fishThree1 == 0) {
            binding.layoutFishThree.fishThree1No.setChecked(true);
        }
        if (content.fishThree2 == 1) {
            binding.layoutFishThree.fishThree2Ok.setChecked(true);
        } else if (content.fishThree2 == 0) {
            binding.layoutFishThree.fishThree2No.setChecked(true);
        }
        if (content.fishThree3 == 1) {
            binding.layoutFishThree.fishThree3Ok.setChecked(true);
        } else if (content.fishThree3 == 0) {
            binding.layoutFishThree.fishThree3No.setChecked(true);
        }
        if (content.fishThree4 == 1) {
            binding.layoutFishThree.fishThree4Ok.setChecked(true);
        } else if (content.fishThree4 == 0) {
            binding.layoutFishThree.fishThree4No.setChecked(true);
        }
        if (content.fishThree5 == 1) {
            binding.layoutFishThree.fishThree5Ok.setChecked(true);
        } else if (content.fishThree5 == 0) {
            binding.layoutFishThree.fishThree5No.setChecked(true);
        }
        if (content.fishThree6 == 1) {
            binding.layoutFishThree.fishThree6Ok.setChecked(true);
        } else if (content.fishThree6 == 0) {
            binding.layoutFishThree.fishThree6No.setChecked(true);
        }
        if (content.fishThree7 == 1) {
            binding.layoutFishThree.fishThree7Ok.setChecked(true);
        } else if (content.fishThree7 == 0) {
            binding.layoutFishThree.fishThree7No.setChecked(true);
        }
        if (content.fishThree8 == 1) {
            binding.layoutFishThree.fishThree8Ok.setChecked(true);
        } else if (content.fishThree8 == 0) {
            binding.layoutFishThree.fishThree8No.setChecked(true);
        }
        if (content.fishThree9 == 1) {
            binding.layoutFishThree.fishThree9Ok.setChecked(true);
        } else if (content.fishThree9 == 0) {
            binding.layoutFishThree.fishThree9No.setChecked(true);
        }
        if (content.fishThree10 == 1) {
            binding.layoutFishThree.fishThree10Ok.setChecked(true);
        } else if (content.fishThree10 == 0) {
            binding.layoutFishThree.fishThree10No.setChecked(true);
        }
        if (content.fishThree11 == 1) {
            binding.layoutFishThree.fishThree11Ok.setChecked(true);
        } else if (content.fishThree11 == 0) {
            binding.layoutFishThree.fishThree11No.setChecked(true);
        }
        if (content.fishThree12 == 1) {
            binding.layoutFishThree.fishThree12Ok.setChecked(true);
        } else if (content.fishThree12 == 0) {
            binding.layoutFishThree.fishThree12No.setChecked(true);
        }
        if (content.fishThree13 == 1) {
            binding.layoutFishThree.fishThree13Ok.setChecked(true);
        } else if (content.fishThree13 == 0) {
            binding.layoutFishThree.fishThree13No.setChecked(true);
        }
        if (content.fishThree14 == 1) {
            binding.layoutFishThree.fishThree14Ok.setChecked(true);
        } else if (content.fishThree14 == 0) {
            binding.layoutFishThree.fishThree14No.setChecked(true);
        }
        if (content.fishThree15 == 1) {
            binding.layoutFishThree.fishThree15Ok.setChecked(true);
        } else if (content.fishThree15 == 0) {
            binding.layoutFishThree.fishThree15No.setChecked(true);
        }
        if (content.fishThree16 == 1) {
            binding.layoutFishThree.fishThree16Ok.setChecked(true);
        } else if (content.fishThree16 == 0) {
            binding.layoutFishThree.fishThree16No.setChecked(true);
        }
    }
    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(RealmDetailsActivity.this);
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
