package com.agridata.cdzhdj.data.db.mapper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.db.bean.ApplyDBean;
import com.agridata.cdzhdj.data.harmless.ApplyBean;
import com.agridata.cdzhdj.utils.GlideUtils;
import com.agridata.cdzhdj.utils.ImageLoader;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.network.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-28 15:31.
 * @Description :描述
 */
public class ApplyMapper {


    public interface OnConversionCompleteListener {
        void onConversionComplete(ApplyDBean dbBean);
        void onConversionFailed(Exception e);
    }


    private static File fileImage;

    public static void  convertToDbModel(ApplyBean.ResultBean resultBean,final OnConversionCompleteListener listener) {
        if (resultBean == null) {
            throw new IllegalArgumentException("ApplyBean can not be null");
        }

        try {
            ApplyDBean applyDbean = new ApplyDBean();

            applyDbean.mid = resultBean.mid;
            applyDbean.userName = resultBean.userName;
            applyDbean.mobile = resultBean.mobile;
            applyDbean.dieAmount = resultBean.dieAmount;
            applyDbean.dieWeight = resultBean.dieWeight;
            applyDbean.animalID = resultBean.animalID;


            applyDbean.animal = new ApplyDBean.AnimalBean();
            applyDbean.animal.iD = resultBean.animal.iD;
            applyDbean.animal.name = resultBean.animal.name;


            applyDbean.applyType = resultBean.applyType;


            applyDbean.farmMid = resultBean.farmMid;
            applyDbean.remark = resultBean.remark;
            applyDbean.applyTime = resultBean.applyTime;
            applyDbean.checkUser = resultBean.checkUser;
            applyDbean.checkTime = resultBean.checkTime;
            applyDbean.checkSignature = resultBean.checkSignature;
            applyDbean.checkRemark = resultBean.checkRemark;
            applyDbean.checkStatus = resultBean.checkStatus;

            applyDbean.region = new ApplyDBean.RegionBean();
            applyDbean.region.iD = resultBean.region.iD;
            applyDbean.region.rI1 = resultBean.region.rI1;
            applyDbean.region.rI2 = resultBean.region.rI2;
            applyDbean.region.rI3 = resultBean.region.rI3;
            applyDbean.region.rI4 = resultBean.region.rI4;
            applyDbean.region.rI5 = resultBean.region.rI5;
            applyDbean.region.regionName = resultBean.region.regionName;
            applyDbean.region.regionCode = resultBean.region.regionCode;
            applyDbean.region.regionLevel = resultBean.region.regionLevel;
            applyDbean.region.regionFullName = resultBean.region.regionFullName;
            applyDbean.region.regionParentID = resultBean.region.regionParentID;

            applyDbean.regionID = resultBean.regionID;
            applyDbean.regionRI1 = resultBean.regionRI1;
            applyDbean.regionRI2 = resultBean.regionRI2;
            applyDbean.regionRI3 = resultBean.regionRI3;
            applyDbean.regionRI4 = resultBean.regionRI4;
            applyDbean.regionRI5 = resultBean.regionRI5;
            applyDbean.applyCategory = resultBean.applyCategory;

            applyDbean.applyPoint = new ApplyDBean.ApplyPointBean();
            applyDbean.applyPoint.iD = resultBean.applyPoint.iD;
            applyDbean.applyPoint.name = resultBean.applyPoint.name;

            applyDbean.applyAddress = resultBean.applyAddress;
            applyDbean.isApplySelf = resultBean.isApplySelf;
            applyDbean.applyNo = resultBean.applyNo;

            applyDbean.iDCard = resultBean.iDCard;
            applyDbean.bankName = resultBean.bankName;
            applyDbean.bankCardNo = resultBean.bankCardNo;
            applyDbean.sourceType = resultBean.sourceType;
            applyDbean.createAt = resultBean.createAt;
            applyDbean.lastUpdate = resultBean.lastUpdate;
            applyDbean.createAtStr = resultBean.createAtStr;
            applyDbean.lastUpdateStr = resultBean.lastUpdateStr;
            CompletableFuture<String> future1 = ImageLoader.loadImageAndSave(MyApplication.getContext(),resultBean.imgFiles.idCardPic,"idCardPic",resultBean.mid);
            CompletableFuture<String> future2 = ImageLoader.loadImageAndSave(MyApplication.getContext(),resultBean.imgFiles.bankPic,"bankPic",resultBean.mid);
            // 等待两张图片加载和保存完成后执行操作
            CompletableFuture.allOf(future1, future2).thenAcceptAsync(ignored -> {
                try {
                    // 图片加载和保存完成，执行数据转换操作
                    applyDbean.imgFiles = new ApplyDBean.ImgFilesBean();
                    String imagePath1 = future1.get();
                    if (imagePath1 != null) {
                        applyDbean.imgFiles.idCardPic = imagePath1;
                    }
                    String imagePath2 = future2.get();
                    if (imagePath2 != null) {
                        applyDbean.imgFiles.bankPic = imagePath2;
                    }
                    // 数据转换完成，通过回调通知
                    listener.onConversionComplete(applyDbean);
                } catch (Exception e) {
                    // 处理异常情况
                    e.printStackTrace();
                    listener.onConversionFailed(e);
                }
            });

        } catch (Exception e) {
            LogUtil.e("ApplyDBean出错");
            e.printStackTrace();
        }
    }














    public ApplyBean.ResultBean convertToBeanModel(ApplyDBean applyDBean) {
        if (applyDBean == null) {
            throw new IllegalArgumentException("resultBean can not be null");
        }
        try {
            ApplyBean.ResultBean resultBean = new ApplyBean.ResultBean();
            resultBean.imgFiles = new ApplyBean.ResultBean.ImgFilesBean();
            resultBean.imgFiles.idCardPic = applyDBean.imgFiles.idCardPic;
            resultBean.imgFiles.bankPic = applyDBean.imgFiles.bankPic;
            resultBean.imgFiles.idCardPicBg = applyDBean.imgFiles.idCardPicBg;
            resultBean.mid = applyDBean.mid;
            resultBean.userName = applyDBean.userName;
            resultBean.mobile = applyDBean.mobile;
            resultBean.dieAmount = applyDBean.dieAmount;
            resultBean.dieWeight = applyDBean.dieWeight;
            resultBean.animalID = applyDBean.animalID;
            resultBean.animal = new ApplyBean.ResultBean.AnimalBean();
            resultBean.animal.iD = applyDBean.animal.iD;
            resultBean.animal.name = applyDBean.animal.name;
            resultBean.applyType = applyDBean.applyType;
            resultBean.farmMid = applyDBean.farmMid;
            resultBean.remark = applyDBean.remark;
            resultBean.applyTime = applyDBean.applyTime;
            resultBean.checkUser = applyDBean.checkUser;
            resultBean.checkTime = applyDBean.checkTime;
            resultBean.checkSignature = applyDBean.checkSignature;
            resultBean.checkRemark = applyDBean.checkRemark;
            resultBean.checkStatus = applyDBean.checkStatus;
            resultBean.region = new ApplyBean.ResultBean.RegionBean();
            resultBean.region.iD = applyDBean.region.iD;
            resultBean.region.rI1 = applyDBean.region.rI1;
            resultBean.region.rI2 = applyDBean.region.rI2;
            resultBean.region.rI3 = applyDBean.region.rI3;
            resultBean.region.rI4 = applyDBean.region.rI4;
            resultBean.region.rI5 = applyDBean.region.rI5;
            resultBean.region.regionName = applyDBean.region.regionName;
            resultBean.region.regionCode = applyDBean.region.regionCode;
            resultBean.region.regionLevel = applyDBean.region.regionLevel;
            resultBean.region.regionFullName = applyDBean.region.regionFullName;
            resultBean.region.regionParentID = applyDBean.region.regionParentID;

            resultBean.regionID = applyDBean.regionID;
            resultBean.regionRI1 = applyDBean.regionRI1;
            resultBean.regionRI2 = applyDBean.regionRI2;
            resultBean.regionRI3 = applyDBean.regionRI3;
            resultBean.regionRI4 = applyDBean.regionRI4;
            resultBean.regionRI5 = applyDBean.regionRI5;
            resultBean.applyCategory = applyDBean.applyCategory;

            resultBean.applyPoint = new ApplyBean.ResultBean.ApplyPointBean();
            resultBean.applyPoint.iD = applyDBean.applyPoint.iD;
            resultBean.applyPoint.name = applyDBean.applyPoint.name;

            resultBean.applyAddress = applyDBean.applyAddress;
            resultBean.isApplySelf = applyDBean.isApplySelf;
            resultBean.applyNo = applyDBean.applyNo;

            resultBean.iDCard = applyDBean.iDCard;
            resultBean.bankName = applyDBean.bankName;
            resultBean.bankCardNo = applyDBean.bankCardNo;
            resultBean.sourceType = applyDBean.sourceType;
            resultBean.createAt = applyDBean.createAt;
            resultBean.lastUpdate = applyDBean.lastUpdate;
            resultBean.createAtStr = applyDBean.createAtStr;
            resultBean.lastUpdateStr = applyDBean.lastUpdateStr;

            return resultBean;
        } catch (Exception e) {
            LogUtil.e("resultBean出错");
            e.printStackTrace();
            return null;
        }
    }
















}