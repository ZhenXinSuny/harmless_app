package com.agridata.cdzhdj.data.entrycheck;

import com.google.gson.annotations.SerializedName;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-09-05 17:35.
 * @Description :描述
 */
public class IsArrivedReportData {

    public IsArrivedReportPeople ArriveInfo;
    public String CategoryId = "44be5d010ab14aaaa99383eb72875145";
    public String _PartId = "d5896b31964e425382df52f655dedfc2";
    public String Mid;
    public String IsArrived ="1";

    public static class IsArrivedReportPeople {
        @SerializedName("OpenId")
        public String openId;
        @SerializedName("UserMid")
        public String userMid;
        @SerializedName("UserName")
        public String userName;
        @SerializedName("ArrivedTime")
        public String arrivedTime;
    }


}
