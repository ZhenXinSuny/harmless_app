package com.agridata.cdzhdj.data.entrycheck;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-11-21 09:30.
 * @Description :描述
 */
public class EntryCheckHistoryDetailBean {


    /**
     * status
     */
    @SerializedName("Status")
    public Integer status;
    /**
     * errorCode
     */
    @SerializedName("ErrorCode")
    public Integer errorCode;
    /**
     * message
     */
    @SerializedName("Message")
    public Object message;
    /**
     * result
     */
    @SerializedName("Result")
    public Result result;

    public static class Result {
        /**
         * mid
         */
        @SerializedName("Mid")
        public String mid;
        /**
         * certNo
         */
        @SerializedName("CertNo")
        public String certNo;
        /**
         * checkType
         */
        @SerializedName("CheckType")
        public Integer checkType;
        /**
         * counts
         */
        @SerializedName("Counts")
        public Integer counts;
        /**
         * carHead
         */
        @SerializedName("CarHead")
        public String carHead;
        /**
         * checkResult
         */
        @SerializedName("CheckResult")
        public Integer checkResult;
        /**
         * timeIsPass
         */
        @SerializedName("TimeIsPass")
        public Integer timeIsPass;
        /**
         * addressIsPass
         */
        @SerializedName("AddressIsPass")
        public Integer addressIsPass;
        /**
         * carIsPass
         */
        @SerializedName("CarIsPass")
        public Integer carIsPass;
        /**
         * roadIsPass
         */
        @SerializedName("RoadIsPass")
        public Integer roadIsPass;
        /**
         * numberIsPass
         */
        @SerializedName("NumberIsPass")
        public Integer numberIsPass;
        /**
         * earTagIsPass
         */
        @SerializedName("EarTagIsPass")
        public Integer earTagIsPass;
        /**
         * checkTime
         */
        @SerializedName("CheckTime")
        public String checkTime;
        /**
         * checkUser
         */
        @SerializedName("CheckUser")
        public CheckUser checkUser;
        /**
         * certType
         */
        @SerializedName("CertType")
        public String certType;
        /**
         * slaughterHouseID
         */
        @SerializedName("SlaughterHouseID")
        public String slaughterHouseID;
        /**
         * sourceType
         */
        @SerializedName("SourceType")
        public Integer sourceType;
        /**
         * region
         */
        @SerializedName("Region")
        public Region region;
        /**
         * owner
         */
        @SerializedName("Owner")
        public String owner;
        /**
         * slaughterHouseName
         */
        @SerializedName("SlaughterHouseName")
        public String slaughterHouseName;
        /**
         * animalName
         */
        @SerializedName("AnimalName")
        public String animalName;
        /**
         * placeDepartureName
         */
        @SerializedName("PlaceDepartureName")
        public String placeDepartureName;
        /**
         * carNumber
         */
        @SerializedName("CarNumber")
        public String carNumber;

        /**
         * earTags
         */
        @SerializedName("EarTags")
        public List<String> earTags;
        /**
         * imgs
         */
        @SerializedName("Imgs")
        public List<String> imgs;
        /**
         * actualNumber
         */
        @SerializedName("ActualNumber")
        public Integer actualNumber;
        /**
         * anomalyNumber
         */
        @SerializedName("AnomalyNumber")
        public Integer anomalyNumber;
        /**
         * deathNumber
         */
        @SerializedName("DeathNumber")
        public Integer deathNumber;
        /**
         * errorEarTags
         */
        @SerializedName("ErrorEarTags")
        public String errorEarTags;
        /**
         * createAtStr
         */
        @SerializedName("CreateAtStr")
        public String createAtStr;
        /**
         * lastUpdateStr
         */
        @SerializedName("LastUpdateStr")
        public String lastUpdateStr;
        /**
         * creatorName
         */
        @SerializedName("CreatorName")
        public String creatorName;
        /**
         * modifierName
         */
        @SerializedName("ModifierName")
        public String modifierName;

        public static class CheckUser {
            /**
             * key
             */
            @SerializedName("Key")
            public String key;
            /**
             * name
             */
            @SerializedName("Name")
            public String name;
        }

        public static class Region {
            /**
             * id
             */
            @SerializedName("ID")
            public Integer id;
            /**
             * ri1
             */
            @SerializedName("RI1")
            public Integer ri1;
            /**
             * ri2
             */
            @SerializedName("RI2")
            public Integer ri2;
            /**
             * ri3
             */
            @SerializedName("RI3")
            public Integer ri3;
            /**
             * ri4
             */
            @SerializedName("RI4")
            public Integer ri4;
            /**
             * ri5
             */
            @SerializedName("RI5")
            public Integer ri5;
            /**
             * regionCode
             */
            @SerializedName("RegionCode")
            public String regionCode;
            /**
             * regionName
             */
            @SerializedName("RegionName")
            public String regionName;
            /**
             * regionLevel
             */
            @SerializedName("RegionLevel")
            public Integer regionLevel;
            /**
             * regionFullName
             */
            @SerializedName("RegionFullName")
            public String regionFullName;
            /**
             * regionParentID
             */
            @SerializedName("RegionParentID")
            public Integer regionParentID;
        }
    }
}
