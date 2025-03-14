package com.agridata.cdzhdj.data;

import java.io.Serializable;

/**
 * @auther 56454
 * @date 2022/6/21
 * @time 18:09.
 */
public class StatusData implements Serializable {


    public int Status;
    public int ErrorCode;
    public String Message;
    public String Result;

    @Override
    public String toString() {
        return "StatusData{" +
                "Status=" + Status +
                ", ErrorCode=" + ErrorCode +
                ", Message='" + Message + '\'' +
                ", Result='" + Result + '\'' +
                '}';
    }
}
