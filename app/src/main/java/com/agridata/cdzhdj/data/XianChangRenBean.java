package com.agridata.cdzhdj.data;

import java.io.Serializable;

/**
 * @auther 56454
 * @date 2022/7/15
 * @time 19:18.
 */
public class XianChangRenBean implements Serializable {

    public int code;
    public String msg;
    public DataBean data;

    public static class DataBean implements Serializable {
        public String mobiles;
    }
}
