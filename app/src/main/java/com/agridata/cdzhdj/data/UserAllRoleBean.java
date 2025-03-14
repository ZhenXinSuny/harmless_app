package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-06-07 14:08.
 * @Description :描述
 */
public class UserAllRoleBean {


    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public Object message;
    @SerializedName("Result")
    public List<ResultBean> result;

    public static class ResultBean {
        public String description;
        public boolean disabled;
        public Object firstId;
        public Object firstMobileId;
        public Object groupName;
        public String id;
        public int level;
        public Object mpAppName;
        public String name;
        public int orderNo;
        public Object roleType;
        public List<String> tags;
        public Object typeGroup;
        public VariablesBean variables;

        public static class VariablesBean {
            @SerializedName("DataType")
            public String dataType;
        }
    }
}
