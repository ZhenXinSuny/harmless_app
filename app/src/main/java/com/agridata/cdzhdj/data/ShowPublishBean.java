package com.agridata.cdzhdj.data;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-07-10 14:46.
 * @Description :描述
 */
public class ShowPublishBean {

    public int code;
    public String message;
    public DataBean data;

    public static class DataBean {
        public int statue;
        public String message;

        @Override
        public String toString() {
            return "DataBean{" +
                    "statue=" + statue +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ShowPublishBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
