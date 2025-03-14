package com.agridata.cdzhdj.data.xdrbind;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-03 09:09.
 * @Description :描述 此接口需要改造
 */
public class GetUserBindXdrInfoBean {

    public String command="GetUserBindXdrInfo";
    public ParamsBean params;


    public   static class  ParamsBean {
        public  String UserId;
    }


}
