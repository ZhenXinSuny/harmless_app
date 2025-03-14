package com.agridata.cdzhdj.data.law;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-06-16 13:51.
 * @Description :描述
 */
public class CheckTjData {

    public int code;
    public String message;
    public DataDTO data;
    public boolean ok;

    public static class DataDTO {
        public int chudongrenyuancishu;
        public int zhuti;
        public int jianchacishu;
        public int daiwanchengfuchashu;
        public int fuchawanchengcishu;
    }
}
