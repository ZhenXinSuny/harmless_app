package com.agridata.cdzhdj.data;

import java.io.Serializable;

/**
 * @auther 56454
 * @date 2022/6/27
 * @time 11:29.
 */

public class DanHaoBean implements Serializable {

    public int code;
    public String msg;
    public String data;

    @Override
    public String toString() {
        return "DanHaoBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
