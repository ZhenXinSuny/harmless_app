package com.agridata.cdzhdj.data.db.mapper;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.CollectInfoData;
import com.agridata.cdzhdj.data.db.bean.ApplyDBean;
import com.agridata.cdzhdj.data.db.bean.CollectionDBModel;
import com.agridata.cdzhdj.data.harmless.ApplyBean;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.agridata.cdzhdj.utils.ImageLoader;
import com.agridata.network.utils.LogUtil;
import com.agridata.util.DateTimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-28 15:31.
 * @Description :描述
 */
public class CollectionModelMapper {
    public  CollectionDBModel convertToDbModel(CollectInfoData collectInfoData) {
        if (collectInfoData == null) {
            throw new IllegalArgumentException("collectInfoData can not be null");
        }
        try {
            CollectionDBModel collectionDbModel = new CollectionDBModel();
            collectionDbModel.applyGUID = collectInfoData.applyGUID;
            collectionDbModel.creatorId = collectInfoData.creatorId;
            collectionDbModel.collectNo = collectInfoData.collectNo;
            //动物
            collectionDbModel.animal = new CollectionDBModel.AnimalBean();
            collectionDbModel.animal.AnimalName = collectInfoData.animal.AnimalName;
            collectionDbModel.animal.key = collectInfoData.animal.key;
            //单位
            collectionDbModel.animalUnit = new CollectionDBModel.AnimalUnitBean();
            collectionDbModel.animalUnit.key = collectInfoData.animalUnit.key;
            collectionDbModel.animalUnit.UnitName = collectInfoData.animalUnit.UnitName;

            collectionDbModel.dieAmount = collectInfoData.dieAmount;
            collectionDbModel.dieWeight = collectInfoData.dieWeight;
            collectionDbModel.scale = collectInfoData.scale;
            collectionDbModel.isDisinfect = collectInfoData.isDisinfect;
            collectionDbModel.disinfect = collectInfoData.disinfect;
            collectionDbModel.disease = collectInfoData.disease;
            collectionDbModel.dieReasonType = collectInfoData.dieReasonType;
            collectionDbModel.isInsurance = collectInfoData.isInsurance;
            collectionDbModel.latitude = collectInfoData.latitude;
            collectionDbModel.longitude = collectInfoData.longitude;
            collectionDbModel.collectType = 1;
            //车辆
            collectionDbModel.carInfo = new CollectionDBModel.CarInfoBean();
            collectionDbModel.carInfo.Mid = collectInfoData.carInfo.Mid;
            collectionDbModel.carInfo.Name = collectInfoData.carInfo.Name;
            //保险公司
            collectionDbModel.insuranceCompany = new CollectionDBModel.InsuranceCompanyBean();
            collectionDbModel.insuranceCompany.key = collectInfoData.insuranceCompany.key;
            collectionDbModel.insuranceCompany.InsuranceCompanyName = collectInfoData.insuranceCompany.InsuranceCompanyName;

            collectionDbModel.bankName = collectInfoData.bankName;
            collectionDbModel.idcard = collectInfoData.idcard;
            collectionDbModel.bankCardNo = collectInfoData.bankCardNo;
            collectionDbModel.factoryGUID = collectInfoData.factoryGUID;
            collectionDbModel.remark = collectInfoData.remark;


            //图片
            collectionDbModel.imgFiles = new CollectionDBModel.ImgFilesBean();
            collectionDbModel.imgFiles.BankPic = collectInfoData.imgFiles.BankPic;
            collectionDbModel.imgFiles.IdCardPic = collectInfoData.imgFiles.IdCardPic;
            collectionDbModel.imgFiles.ShouYunYuanPic = collectInfoData.imgFiles.ShouYunYuanPic;
            collectionDbModel.imgFiles.YangZhiChangHuPic = collectInfoData.imgFiles.YangZhiChangHuPic;
            collectionDbModel.imgFiles.CollectCertPic = collectInfoData.imgFiles.CollectCertPic;

            //死畜照片
            collectionDbModel.imgFiles.SiChuPicList = new ArrayList<>();
            for (CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean : collectInfoData.imgFiles.SiChuPicList) {
                CollectionDBModel.ImgFilesBean.ImgMidBean imgBean = new CollectionDBModel.ImgFilesBean.ImgMidBean();
                imgBean.Mid = imgMidBean.Mid;
                collectionDbModel.imgFiles.SiChuPicList.add(imgBean);
            }


            //尸体照片
            collectionDbModel.imgFiles.ShiTiPicList = new ArrayList<>();
            for (CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean : collectInfoData.imgFiles.ShiTiPicList) {
                CollectionDBModel.ImgFilesBean.ImgMidBean imgBean = new CollectionDBModel.ImgFilesBean.ImgMidBean();
                imgBean.Mid = imgMidBean.Mid;
                collectionDbModel.imgFiles.ShiTiPicList.add(imgBean);
            }


            //装车照片
            collectionDbModel.imgFiles.ZhuangChePicList = new ArrayList<>();
            for (CollectInfoData.ImgFilesBean.ImgMidBean imgMidBean : collectInfoData.imgFiles.ZhuangChePicList) {
                CollectionDBModel.ImgFilesBean.ImgMidBean imgBean = new CollectionDBModel.ImgFilesBean.ImgMidBean();
                imgBean.Mid = imgMidBean.Mid;
                collectionDbModel.imgFiles.ZhuangChePicList.add(imgBean);
            }


            collectionDbModel.region = new CollectionDBModel.RegionBean();
            collectionDbModel.region.id = collectInfoData.region.id;
            collectionDbModel.region.RI1 = collectInfoData.region.RI1;
            collectionDbModel.region.RI2 = collectInfoData.region.RI2;
            collectionDbModel.region.RI3 = collectInfoData.region.RI3;
            collectionDbModel.region.RI4 = collectInfoData.region.RI4;
            collectionDbModel.region.RI5 = collectInfoData.region.RI5;
            collectionDbModel.region.RegionCode = collectInfoData.region.RegionCode;
            collectionDbModel.region.RegionName = collectInfoData.region.RegionName;
            collectionDbModel.region.RegionLevel = collectInfoData.region.RegionLevel;
            collectionDbModel.region.RI1RegionName = collectInfoData.region.RI1RegionName;
            collectionDbModel.region.RI2RegionName = collectInfoData.region.RI2RegionName;
            collectionDbModel.region.RI3RegionName = collectInfoData.region.RI3RegionName;
            collectionDbModel.region.RI4RegionName = collectInfoData.region.RI4RegionName;
            collectionDbModel.region.RI5RegionName = collectInfoData.region.RI5RegionName;
            collectionDbModel.region.RegionFullName = collectInfoData.region.RegionFullName;
            collectionDbModel.region.RegionParentID = collectInfoData.region.RegionParentID;
            //收集者
            collectionDbModel.worker = new CollectionDBModel.WokerBean();
            collectionDbModel.worker.Mid = collectInfoData.worker.Mid;
            collectionDbModel.worker.Name = collectInfoData.worker.Name;
            collectionDbModel.worker.Mobile = collectInfoData.worker.Mobile;


            collectionDbModel.xdrName = collectInfoData.xdrName;
            collectionDbModel.xdrMobile = collectInfoData.xdrMobile;
            collectionDbModel.xdrAddress = collectInfoData.xdrAddress;

            collectionDbModel.farmMid = collectInfoData.farmMid;
            collectionDbModel.collectionTime = DateTimeUtils.getNowTimes();


            collectionDbModel.itemDatas = new ArrayList<>();
            for (CollectInfoData.DataItemBean itemData : collectInfoData.itemDatas) {
                CollectionDBModel.DataItemBean dataItem = new CollectionDBModel.DataItemBean();
                dataItem.EarTagNo = itemData.EarTagNo;
                dataItem.AnimalID = itemData.AnimalID;
                dataItem.BodyWeight = itemData.BodyWeight;
                dataItem.Name = itemData.Name;
                dataItem.AnimalType = itemData.AnimalType;
                dataItem.IsSow = itemData.IsSow;
                dataItem.Amount = itemData.Amount;
                collectionDbModel.itemDatas.add(dataItem);
            }
            return collectionDbModel;
        } catch (Exception e) {
            LogUtil.e("CollectionDBModel出错");
            e.printStackTrace();
        }
        return null;
    }







    public CollectInfoData convertToInfoData(CollectionDBModel collectionDBModel) {
        if (collectionDBModel == null) {
            throw new IllegalArgumentException("collectInfoData can not be null");
        }
        try {
            CollectInfoData collectInfoData = new CollectInfoData();
            collectInfoData.applyGUID = collectionDBModel.applyGUID;
            collectInfoData.creatorId = collectionDBModel.creatorId;
            collectInfoData.collectNo = collectionDBModel.collectNo;
            //动物
            collectInfoData.animal = new CollectInfoData.AnimalBean();
            collectInfoData.animal.AnimalName = collectionDBModel.animal.AnimalName;
            collectInfoData.animal.key = collectionDBModel.animal.key;
            //单位
            collectInfoData.animalUnit = new CollectInfoData.AnimalUnitBean();
            collectInfoData.animalUnit.key = collectionDBModel.animalUnit.key;
            collectInfoData.animalUnit.UnitName = collectionDBModel.animalUnit.UnitName;

            collectInfoData.dieAmount = collectionDBModel.dieAmount;
            collectInfoData.dieWeight = collectionDBModel.dieWeight;
            collectInfoData.scale = collectionDBModel.scale;
            collectInfoData.isDisinfect = collectionDBModel.isDisinfect;
            collectInfoData.disinfect = collectionDBModel.disinfect;
            collectInfoData.disease = collectionDBModel.disease;
            collectInfoData.dieReasonType = collectionDBModel.dieReasonType;
            collectInfoData.isInsurance = collectionDBModel.isInsurance;
            collectInfoData.latitude = collectionDBModel.latitude;
            collectInfoData.longitude = collectionDBModel.longitude;
            collectInfoData.collectType = 1;
            //车辆
            collectInfoData.carInfo = new CollectInfoData.CarInfoBean();
            collectInfoData.carInfo.Mid = collectionDBModel.carInfo.Mid;
            collectInfoData.carInfo.Name = collectionDBModel.carInfo.Name;
            //保险公司
            collectInfoData.insuranceCompany = new CollectInfoData.InsuranceCompanyBean();
            collectInfoData.insuranceCompany.key = collectionDBModel.insuranceCompany.key;
            collectInfoData.insuranceCompany.InsuranceCompanyName = collectionDBModel.insuranceCompany.InsuranceCompanyName;

            collectInfoData.bankName = collectionDBModel.bankName;
            collectInfoData.idcard = collectionDBModel.idcard;
            collectInfoData.bankCardNo = collectionDBModel.bankCardNo;
            collectInfoData.factoryGUID = collectionDBModel.factoryGUID;
            collectInfoData.remark = collectionDBModel.remark;


            //图片
            collectInfoData.imgFiles = new CollectInfoData.ImgFilesBean();
            collectInfoData.imgFiles.BankPic = collectionDBModel.imgFiles.BankPic;
            collectInfoData.imgFiles.IdCardPic = collectionDBModel.imgFiles.IdCardPic;
            collectInfoData.imgFiles.ShouYunYuanPic = collectionDBModel.imgFiles.ShouYunYuanPic;
            collectInfoData.imgFiles.YangZhiChangHuPic = collectionDBModel.imgFiles.YangZhiChangHuPic;
            collectInfoData.imgFiles.CollectCertPic = collectionDBModel.imgFiles.CollectCertPic;


            //死畜照片
            collectInfoData.imgFiles.SiChuPicList = new ArrayList<>();

            List<CollectionDBModel.ImgFilesBean.ImgMidBean> siChuPicList = collectionDBModel.imgFiles.SiChuPicList;
            for (CollectionDBModel.ImgFilesBean.ImgMidBean imgMidBean : siChuPicList) {
                CollectInfoData.ImgFilesBean.ImgMidBean imgBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
                imgBean.Mid = imgMidBean.Mid;
                collectInfoData.imgFiles.SiChuPicList.add(imgBean);
            }


            //尸体照片
            collectInfoData.imgFiles.ShiTiPicList = new ArrayList<>();
            for (CollectionDBModel.ImgFilesBean.ImgMidBean imgMidBean : collectionDBModel.imgFiles.ShiTiPicList) {
                CollectInfoData.ImgFilesBean.ImgMidBean imgBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
                imgBean.Mid = imgMidBean.Mid;
                collectInfoData.imgFiles.ShiTiPicList.add(imgBean);
            }


            //装车照片
            collectInfoData.imgFiles.ZhuangChePicList = new ArrayList<>();
            for (CollectionDBModel.ImgFilesBean.ImgMidBean imgMidBean : collectionDBModel.imgFiles.ZhuangChePicList) {
                CollectInfoData.ImgFilesBean.ImgMidBean imgBean = new CollectInfoData.ImgFilesBean.ImgMidBean();
                imgBean.Mid = imgMidBean.Mid;
                collectInfoData.imgFiles.ZhuangChePicList.add(imgBean);
            }


            collectInfoData.region = new CollectInfoData.RegionBean();
            collectInfoData.region.id = collectionDBModel.region.id;
            collectInfoData.region.RI1 = collectionDBModel.region.RI1;
            collectInfoData.region.RI2 = collectionDBModel.region.RI2;
            collectInfoData.region.RI3 = collectionDBModel.region.RI3;
            collectInfoData.region.RI4 = collectionDBModel.region.RI4;
            collectInfoData.region.RI5 = collectionDBModel.region.RI5;
            collectInfoData.region.RegionCode = collectionDBModel.region.RegionCode;
            collectInfoData.region.RegionName = collectionDBModel.region.RegionName;
            collectInfoData.region.RegionLevel = collectionDBModel.region.RegionLevel;
            collectInfoData.region.RI1RegionName = collectionDBModel.region.RI1RegionName;
            collectInfoData.region.RI2RegionName = collectionDBModel.region.RI2RegionName;
            collectInfoData.region.RI3RegionName = collectionDBModel.region.RI3RegionName;
            collectInfoData.region.RI4RegionName = collectionDBModel.region.RI4RegionName;
            collectInfoData.region.RI5RegionName = collectionDBModel.region.RI5RegionName;
            collectInfoData.region.RegionFullName = collectionDBModel.region.RegionFullName;
            collectInfoData.region.RegionParentID = collectionDBModel.region.RegionParentID;
            //收集者
            collectInfoData.worker = new CollectInfoData.WokerBean();
            collectInfoData.worker.Mid = collectionDBModel.worker.Mid;
            collectInfoData.worker.Name = collectionDBModel.worker.Name;
            collectInfoData.worker.Mobile = collectionDBModel.worker.Mobile;


            collectInfoData.xdrName = collectionDBModel.xdrName;
            collectInfoData.xdrMobile = collectionDBModel.xdrMobile;
            collectInfoData.xdrAddress = collectionDBModel.xdrAddress;

            collectInfoData.farmMid = collectionDBModel.farmMid;



            collectInfoData.itemDatas = new ArrayList<>();
            for (CollectionDBModel.DataItemBean itemData : collectionDBModel.itemDatas) {
                CollectInfoData.DataItemBean dataItem = new CollectInfoData.DataItemBean();
                dataItem.EarTagNo = itemData.EarTagNo;
                dataItem.AnimalID = itemData.AnimalID;
                dataItem.BodyWeight = itemData.BodyWeight;
                dataItem.Name = itemData.Name;
                dataItem.AnimalType = itemData.AnimalType;
                dataItem.IsSow = itemData.IsSow;
                dataItem.Amount = itemData.Amount;
                collectInfoData.itemDatas.add(dataItem);
            }
            return collectInfoData;
        } catch (Exception e) {
            LogUtil.e("collectInfoData出错");
            e.printStackTrace();
        }
        return null;
    }











}


