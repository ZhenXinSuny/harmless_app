package com.agridata.cdzhdj.data;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-08-30 14:50.
 * @Description :描述
 */
public class StatusChangePwd {
    public int code;
    public String message;
    public String data;
    public boolean ok;

    @Override
    public String toString() {
        return "StatusChangePwd{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", ok=" + ok +
                '}';
    }
}
