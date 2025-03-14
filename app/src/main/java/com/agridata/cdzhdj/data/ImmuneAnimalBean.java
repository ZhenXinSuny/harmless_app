package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by XXX.
 * Date: 2022/11/24
 * describe
 */

public class ImmuneAnimalBean {


    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public String message;
    @SerializedName("Result")
    public List<ResultDTO> result;

    public static class ResultDTO {
        @SerializedName("Mid")
        public String mid;
        @SerializedName("ID")
        public int iD;
        @SerializedName("AnimalName")
        public String animalName;
        @SerializedName("AnimalLevel")
        public int animalLevel;
        @SerializedName("AnimalParentID")
        public int animalParentID;
        @SerializedName("AnimalLivedays")
        public int animalLivedays;
        @SerializedName("SortOrder")
        public int sortOrder;
        @SerializedName("EartagCode")
        public int eartagCode;

        @Override
        public String toString() {
            return "ResultDTO{" +
                    "mid='" + mid + '\'' +
                    ", iD=" + iD +
                    ", animalName='" + animalName + '\'' +
                    ", animalLevel=" + animalLevel +
                    ", animalParentID=" + animalParentID +
                    ", animalLivedays=" + animalLivedays +
                    ", sortOrder=" + sortOrder +
                    ", eartagCode=" + eartagCode +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ImmuneAnimalBean{" +
                "status=" + status +
                ", errorCode=" + errorCode +
                ", message=" + message +
                ", result=" + result +
                '}';
    }
}
