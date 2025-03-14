package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-02 14:41.
 * @Description :描述
 */
public class CheckEarTagNumBean {


    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public String message;
    @SerializedName("Result")
    public Result result;

    public static class Result {
        public int pageSize;
        public int pageNo;
        public int pageCount;
        public int totalCount;
        public int itemCount;
        public List<ImmuneEarTagBean> pageItems;


        public class ImmuneEarTagBean{
            public String EarTag;
            public boolean isSelected = false;
        }
    }
}
