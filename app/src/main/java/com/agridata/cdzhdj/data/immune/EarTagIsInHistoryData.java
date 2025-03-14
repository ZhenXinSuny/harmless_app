package com.agridata.cdzhdj.data.immune;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-08-09 15:30.
 * @Description :描述
 */
public class EarTagIsInHistoryData {


    public int code;
    public String message;
    public List<DataBean> data;

    public static class DataBean {
        public long lower;
        public long upper;
    }
}
