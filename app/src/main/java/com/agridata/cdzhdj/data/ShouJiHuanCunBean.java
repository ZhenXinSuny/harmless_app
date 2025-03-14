package com.agridata.cdzhdj.data;

import java.io.Serializable;
import java.util.List;

/**
 * @auther 56454
 * @date 2022/8/2
 * @time 9:46.
 */
public class ShouJiHuanCunBean {

    public String Mid;
    public AnimalData animalData;//动物
    public String ShiShiCunLanLiang;//试试存栏量
    public UnitData unitData;//单位
    public String heYanShuLiang;//核验数量
    public String ZongZhongLiang;//总重量
    public int XiaoDuCheck;//消毒情况
    public String XiaoDuYaoPin;//消毒药品
    public int YiBingCheck = 2 ;//疫病
    public int SiWangYaunYinCheck;//死亡原因
    public int BaoXianCheck;//是否购买保险
    public BaoXiaoGongSiBean BaoXiaoGongSi;//保险公司

    public String IDCardNum;//ID Num
    public String  bankName;//保险公司
    public String  bankNum;//银行卡号

    public String feedBook;//备注



    public List<AnimalMenuBeanData> animalMenuBean;//动物清单

    public  ImgFilesBeanData  imgFilesBeanData;//照片

    public static class ImgFilesBeanData implements Serializable {
        public String IdCardPic;
        public String BankPic;
        public List<String> SiChuPicList;
        public List<String> ShiTiPicList;
        public List<String> ZhuangChePicList;
        public String ShouYunYuanPic;
        public String YangZhiChangHuPic;
        public String  CollectCertPic;


        @Override
        public String toString() {
            return "ImgFilesBeanData{" +
                    "IdCardPic='" + IdCardPic + '\'' +
                    ", BankPic='" + BankPic + '\'' +
                    ", SiChuPicList=" + SiChuPicList +
                    ", ShiTiPicList=" + ShiTiPicList +
                    ", ZhuangChePicList=" + ZhuangChePicList +
                    ", ShouYunYuanPic='" + ShouYunYuanPic + '\'' +
                    ", YangZhiChangHuPic='" + YangZhiChangHuPic + '\'' +
                    ", CollectCertPic='" + CollectCertPic + '\'' +
                    '}';
        }
    }

    public static class AnimalMenuBeanData implements Serializable {
        public int Pos;
        public String EarTag;
        public String Weight;
        public String AnimalName;
        public String AnimalID;
        public int AnimalType;
        public String ShuLiang;
        public int IsSow;


        @Override
        public String toString() {
            return "AnimalMenuBeanData{" +
                    "Pos=" + Pos +
                    ", EarTag='" + EarTag + '\'' +
                    ", Weight='" + Weight + '\'' +
                    ", AnimalName='" + AnimalName + '\'' +
                    ", AnimalID='" + AnimalID + '\'' +
                    ", AnimalType=" + AnimalType +
                    ", ShuLiang='" + ShuLiang + '\'' +
                    ", IsSow=" + IsSow +
                    '}';
        }
    }

    public static class BaoXiaoGongSiBean implements Serializable {
        public String key;
        public String InsuranceCompanyName;

        @Override
        public String toString() {
            return "BaoXiaoGongSiBean{" +
                    "key='" + key + '\'' +
                    ", InsuranceCompanyName='" + InsuranceCompanyName + '\'' +
                    '}';
        }
    }

    public static class AnimalData implements Serializable {
        public String ID;
        public String AnimalName;

        @Override
        public String toString() {
            return "AnimalData{" +
                    "ID='" + ID + '\'' +
                    ", AnimalName='" + AnimalName + '\'' +
                    '}';
        }
    }

    public static class UnitData implements Serializable {
        public String ID;
        public String UnitName;

        @Override
        public String toString() {
            return "UnitData{" +
                    "ID='" + ID + '\'' +
                    ", UnitName='" + UnitName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ShouJiHuanCunBean{" +
                "animalData=" + animalData +
                ", ShiShiCunLanLiang='" + ShiShiCunLanLiang + '\'' +
                ", unitData=" + unitData +
                ", heYanShuLiang='" + heYanShuLiang + '\'' +
                ", ZongZhongLiang='" + ZongZhongLiang + '\'' +
                ", XiaoDuCheck=" + XiaoDuCheck +
                ", XiaoDuYaoPin='" + XiaoDuYaoPin + '\'' +
                ", YiBingCheck=" + YiBingCheck +
                ", SiWangYaunYinCheck=" + SiWangYaunYinCheck +
                ", BaoXianCheck=" + BaoXianCheck +
                ", BaoXiaoGongSi=" + BaoXiaoGongSi +
                ", IDCardNum='" + IDCardNum + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankNum='" + bankNum + '\'' +
                ", feedBook='" + feedBook + '\'' +
                ", animalMenuBean=" + animalMenuBean +
                ", imgFilesBeanData=" + imgFilesBeanData +
                '}';
    }
}
