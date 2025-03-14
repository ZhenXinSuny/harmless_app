package com.agridata.cdzhdj.data;

/**
 * @auther 56454
 * @date 2022/6/28
 * @time 9:58.
 */
public class StatusMeBean {
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
