package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-09 19:02.
 * @Description :描述
 */
public class BatchImgBean {


    @SerializedName("Status")
    public int status;
    @SerializedName("Result")
    public List<String> result;

    @Override
    public String toString() {
        return "BatchImgBean{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
