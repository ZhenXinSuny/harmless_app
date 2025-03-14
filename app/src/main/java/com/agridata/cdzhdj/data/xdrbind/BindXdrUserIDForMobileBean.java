package com.agridata.cdzhdj.data.xdrbind;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-03 09:40.
 * @Description :描述
 */
public class BindXdrUserIDForMobileBean {

    public String command="BindXdrUserIDForMobile";
    public ParamsBean params;


    public   static class  ParamsBean {
        public  String UserId;
        public String XDRMid;
        public String Mobile;
    }
}
