package com.agridata.cdzhdj.data.entrycheck;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : AdmissionCheck
 * @Date : on 2023-11-20 10:57.
 * @Description :描述
 */
public class EntryCheckHistoryData {
    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public Object message;
    @SerializedName("Result")
    public ResultDTO result;

    public static class ResultDTO {
        public int pageSize;
        public int pageNo;
        public int pageCount;
        public int totalCount;
        public int itemCount;
        public List<PageItemsDTO> pageItems;

        public static class PageItemsDTO {
            @SerializedName("Mid")
            public String mid;
            @SerializedName("SourceId")
            public Object sourceId;
            @SerializedName("Name")
            public Object name;
            @SerializedName("CertNo")
            public String certNo;
            @SerializedName("CheckType")
            public int checkType;
            @SerializedName("Counts")
            public int counts;
            @SerializedName("CarHead")
            public String carHead;
            @SerializedName("CheckResult")
            public int checkResult;
            @SerializedName("TimeIsPass")
            public int timeIsPass;
            @SerializedName("AddressIsPass")
            public int addressIsPass;
            @SerializedName("CarIsPass")
            public int carIsPass;
            @SerializedName("RoadIsPass")
            public int roadIsPass;
            @SerializedName("NumberIsPass")
            public int numberIsPass;
            @SerializedName("EarTagIsPass")
            public int earTagIsPass;
            @SerializedName("CheckTime")
            public String checkTime;
            @SerializedName("CheckUser")
            public CheckUserDTO checkUser;
            @SerializedName("CertType")
            public String certType;
            @SerializedName("SlaughterHouseID")
            public String slaughterHouseID;
            @SerializedName("SourceType")
            public int sourceType;
            @SerializedName("Region")
            public RegionDTO region;
            @SerializedName("Owner")
            public String owner;
            @SerializedName("SlaughterHouseName")
            public String slaughterHouseName;
            @SerializedName("AnimalName")
            public String animalName;
            @SerializedName("PlaceDepartureName")
            public String placeDepartureName;
            @SerializedName("CarNumber")
            public String carNumber;
            @SerializedName("ImgsEx")
            public Object imgsEx;
            @SerializedName("EarTags")
            public Object earTags;
            @SerializedName("Imgs")
            public List<String> imgs;
            @SerializedName("ActualNumber")
            public int actualNumber;
            @SerializedName("AnomalyNumber")
            public int anomalyNumber;
            @SerializedName("DeathNumber")
            public int deathNumber;
            @SerializedName("ErrorEarTags")
            public Object errorEarTags;
            @SerializedName("CreateAt")
            public long createAt;
            @SerializedName("LastUpdate")
            public long lastUpdate;
            @SerializedName("CreateAtStr")
            public String createAtStr;
            @SerializedName("LastUpdateStr")
            public String lastUpdateStr;


            public static class CheckUserDTO {
                @SerializedName("Key")
                public String key;
                @SerializedName("Name")
                public String name;
            }

            public static class RegionDTO {
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
                    return "RegionDTO{" +
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
                return "PageItemsDTO{" +
                        "mid='" + mid + '\'' +
                        ", sourceId=" + sourceId +
                        ", name=" + name +
                        ", certNo='" + certNo + '\'' +
                        ", checkType=" + checkType +
                        ", counts=" + counts +
                        ", carHead='" + carHead + '\'' +
                        ", checkResult=" + checkResult +
                        ", timeIsPass=" + timeIsPass +
                        ", addressIsPass=" + addressIsPass +
                        ", carIsPass=" + carIsPass +
                        ", roadIsPass=" + roadIsPass +
                        ", numberIsPass=" + numberIsPass +
                        ", earTagIsPass=" + earTagIsPass +
                        ", checkTime='" + checkTime + '\'' +
                        ", checkUser=" + checkUser +
                        ", certType='" + certType + '\'' +
                        ", slaughterHouseID='" + slaughterHouseID + '\'' +
                        ", sourceType=" + sourceType +
                        ", region=" + region +
                        ", owner='" + owner + '\'' +
                        ", slaughterHouseName='" + slaughterHouseName + '\'' +
                        ", animalName='" + animalName + '\'' +
                        ", placeDepartureName='" + placeDepartureName + '\'' +
                        ", carNumber='" + carNumber + '\'' +
                        ", imgsEx=" + imgsEx +
                        ", earTags=" + earTags +
                        ", imgs=" + imgs +
                        ", actualNumber=" + actualNumber +
                        ", anomalyNumber=" + anomalyNumber +
                        ", deathNumber=" + deathNumber +
                        ", errorEarTags=" + errorEarTags +
                        ", createAt=" + createAt +
                        ", lastUpdate=" + lastUpdate +
                        ", createAtStr='" + createAtStr + '\'' +
                        ", lastUpdateStr='" + lastUpdateStr + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ResultDTO{" +
                    "pageSize=" + pageSize +
                    ", pageNo=" + pageNo +
                    ", pageCount=" + pageCount +
                    ", totalCount=" + totalCount +
                    ", itemCount=" + itemCount +
                    ", pageItems=" + pageItems +
                    '}';
        }
    }
}
