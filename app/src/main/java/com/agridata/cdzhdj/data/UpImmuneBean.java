package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-22 10:36.
 * @Description :描述
 */
public class UpImmuneBean {

    public XDRCoreInfoBean  XDRCoreInfo;
    public String XDRCoreID;
    public String DetailID;
    public String Immuned;
    public AnimalBean  Animal;

    public String IsSelfWrite;

    public String AHIUserID;
    public AHIUserBean AHIUser;

    public String AnimalID;
    public String ImmuneCount;
    public List<String>  EagTagsList;
    public String EarTags;
    public String ImmuneType;
    public String PreLiveStock;
    public String ImmuneQuantity;
    public String  CurrentAge;
    public RegionBean   Region;
    public String IsEarTagAnimal;
    public String CategoryId="d0559e21622549148de9911439202147";
    public String CategoryName ="1017A06B438BB8F8-T_IMMUNE_Branch";
    public String _PartId ="d5896b31964e425382df52f655dedfc2";
    public boolean IsCullingPigs =false;

    public static class XDRCoreInfoBean{
        public String Name;
        public String Key;

        @Override
        public String toString() {
            return "XDRCoreInfoBean{" +
                    "Name='" + Name + '\'' +
                    ", Key='" + Key + '\'' +
                    '}';
        }
    }
    public static class AnimalBean{
        public String Name;
        public String Key;

        @Override
        public String toString() {
            return "AnimalBean{" +
                    "Name='" + Name + '\'' +
                    ", Key='" + Key + '\'' +
                    '}';
        }
    }

    public static class AHIUserBean{
        public String Name;
        public String Key;

        @Override
        public String toString() {
            return "AHIUserBean{" +
                    "Name='" + Name + '\'' +
                    ", Key='" + Key + '\'' +
                    '}';
        }
    }

    public static  class  RegionBean{

        @SerializedName("ID")
        public int iD;
        @SerializedName("RI1")
        public int rI1;
        @SerializedName("RI2")
        public int rI2;
        @SerializedName("RI3")
        public int rI3;
        @SerializedName("RI4")
        public int rI4;
        @SerializedName("RI5")
        public int rI5;
        @SerializedName("RegionCode")
        public String regionCode;
        @SerializedName("RegionName")
        public String regionName;
        @SerializedName("RegionLevel")
        public int regionLevel;
        @SerializedName("RegionFullName")
        public String regionFullName;
        @SerializedName("RegionParentID")
        public int regionParentID;

        @Override
        public String toString() {
            return "RegionBean{" +
                    "iD=" + iD +
                    ", rI1=" + rI1 +
                    ", rI2=" + rI2 +
                    ", rI3=" + rI3 +
                    ", rI4=" + rI4 +
                    ", rI5=" + rI5 +
                    ", regionCode='" + regionCode + '\'' +
                    ", regionName='" + regionName + '\'' +
                    ", regionLevel=" + regionLevel +
                    ", regionFullName='" + regionFullName + '\'' +
                    ", regionParentID=" + regionParentID +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UpImmuneBean{" +
                "XDRCoreInfo=" + XDRCoreInfo +
                ", XDRCoreID='" + XDRCoreID + '\'' +
                ", DetailID='" + DetailID + '\'' +
                ", Animal=" + Animal +
                ", IsSelfWrite='" + IsSelfWrite + '\'' +
                ", AHIUserID='" + AHIUserID + '\'' +
                ", AHIUser=" + AHIUser +
                ", AnimalID='" + AnimalID + '\'' +
                ", ImmuneCount='" + ImmuneCount + '\'' +
                ", EagTagsList=" + EagTagsList +
                ", EarTags='" + EarTags + '\'' +
                ", ImmuneType='" + ImmuneType + '\'' +
                ", PreLiveStock='" + PreLiveStock + '\'' +
                ", ImmuneQuantity='" + ImmuneQuantity + '\'' +
                ", CurrentAge='" + CurrentAge + '\'' +
                ", Region=" + Region +
                ", IsEarTagAnimal='" + IsEarTagAnimal + '\'' +
                '}';
    }
}
