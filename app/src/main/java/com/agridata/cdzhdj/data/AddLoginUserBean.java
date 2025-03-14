package com.agridata.cdzhdj.data;

import java.io.Serializable;

/**
 * @auther 56454
 * @date 2022/6/29
 * @time 16:25.
 */

public class AddLoginUserBean implements Serializable {

    public int code;
    public DataBean data;
    public String msg;

    public static class DataBean implements Serializable {
        public boolean addNew;
        public String userId;
    }

    @Override
    public String toString() {
        return "AddLoginUserBean{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
