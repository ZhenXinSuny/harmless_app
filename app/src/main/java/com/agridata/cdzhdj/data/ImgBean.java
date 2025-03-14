package com.agridata.cdzhdj.data;

import java.io.Serializable;

/**
 * @auther 56454
 * @date 2022/6/27
 * @time 20:16.
 */

public class ImgBean implements Serializable {


    public int Status;
    public String Result;

    @Override
    public String toString() {
        return "ImgBean{" +
                "Status=" + Status +
                ", Result='" + Result + '\'' +
                '}';
    }
}
