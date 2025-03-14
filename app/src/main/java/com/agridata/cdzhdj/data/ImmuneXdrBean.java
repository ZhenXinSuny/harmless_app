package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-11-28 16:10.
 * @Description :描述
 */
public class ImmuneXdrBean {

    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public Object message;
    @SerializedName("Result")
    public Result result;

    public static class Result {
        public int pageSize;
        public int pageNo;
        public int pageCount;
        public int totalCount;
        public int itemCount;
        public List<PageItems> pageItems;

        public static class PageItems {
            @SerializedName("Mid")
            public String mid;
            @SerializedName("Name")
            public Object name;
            @SerializedName("XDRType")
            public int xDRType;
            @SerializedName("IDCard")
            public String iDCard;
            @SerializedName("ImmuneType")
            public Object immuneType;
            @SerializedName("DisplayName")
            public String displayName;
            @SerializedName("Mobile")
            public String mobile;
            @SerializedName("RegionID")
            public Object regionID;
            @SerializedName("Region")
            public Region region;
            @SerializedName("Addresss")
            public String addresss;
            @SerializedName("ExamineUserId")
            public Object examineUserId;
            @SerializedName("ExamineTime")
            public Object examineTime;
            @SerializedName("ExamineStatus")
            public int examineStatus;
            public Object userMid;
            @SerializedName("PhotoID")
            public Object photoID;
            @SerializedName("ItRaise")
            public Object itRaise;
            @SerializedName("LegalPersonTel")
            public String legalPersonTel;
            @SerializedName("IDCardNos")
            public String iDCardNos;

            @SerializedName("IDCardNo")
            public String IDCardNo;
            @SerializedName("IDCardPhotos")
            public String iDCardPhotos;
            @SerializedName("UserId")
            public Object userId;

            @SerializedName("AnimalVariety")
            public List<AnimalVariety> animalVariety;

            @Override
            public String toString() {
                return "PageItems{" +
                        "mid='" + mid + '\'' +
                        ", name=" + name +
                        ", xDRType=" + xDRType +
                        ", iDCard=" + iDCard +
                        ", immuneType=" + immuneType +
                        ", displayName='" + displayName + '\'' +
                        ", mobile='" + mobile + '\'' +
                        ", regionID=" + regionID +
                        ", region=" + region +
                        ", addresss='" + addresss + '\'' +
                        ", examineUserId=" + examineUserId +
                        ", examineTime=" + examineTime +
                        ", examineStatus=" + examineStatus +
                        ", userMid=" + userMid +
                        ", photoID=" + photoID +
                        ", itRaise=" + itRaise +
                        ", legalPersonTel=" + legalPersonTel +
                        ", iDCardNos=" + iDCardNos +
                        ", iDCardPhotos='" + iDCardPhotos + '\'' +
                        ", userId=" + userId +
                        ", animalVariety=" + animalVariety +
                        '}';
            }
        }

        public static class  AnimalVariety{
            @SerializedName("ID")
            public String id;
            @SerializedName("Name")
            public String name;
            @SerializedName("EartagCode")
            public int eartagCode;
            @SerializedName("CurrentInventory")
            public String currentInventory;
            public String AnimalParentID;

            @Override
            public String toString() {
                return "AnimalVariety{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", eartagCode='" + eartagCode + '\'' +
                        ", currentInventory='" + currentInventory + '\'' +
                        ", AnimalParentID='" + AnimalParentID + '\'' +
                        '}';
            }
        }
            public static class Region {
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
                @SerializedName("RI1RegionName")
                public String rI1RegionName;
                @SerializedName("RI2RegionName")
                public String rI2RegionName;
                @SerializedName("RI3RegionName")
                public String rI3RegionName;
                @SerializedName("RI4RegionName")
                public String rI4RegionName;
                @SerializedName("RI5RegionName")
                public Object rI5RegionName;
                @SerializedName("RegionFullName")
                public String regionFullName;
                @SerializedName("RegionParentID")
                public int regionParentID;

                @Override
                public String toString() {
                    return "Region{" +
                            "iD=" + iD +
                            ", rI1=" + rI1 +
                            ", rI2=" + rI2 +
                            ", rI3=" + rI3 +
                            ", rI4=" + rI4 +
                            ", rI5=" + rI5 +
                            ", regionCode='" + regionCode + '\'' +
                            ", regionName='" + regionName + '\'' +
                            ", regionLevel=" + regionLevel +
                            ", rI1RegionName='" + rI1RegionName + '\'' +
                            ", rI2RegionName='" + rI2RegionName + '\'' +
                            ", rI3RegionName='" + rI3RegionName + '\'' +
                            ", rI4RegionName='" + rI4RegionName + '\'' +
                            ", rI5RegionName=" + rI5RegionName +
                            ", regionFullName='" + regionFullName + '\'' +
                            ", regionParentID=" + regionParentID +
                            '}';
                }
            }
        }
}
