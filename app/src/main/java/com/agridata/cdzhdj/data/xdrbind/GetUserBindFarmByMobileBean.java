package com.agridata.cdzhdj.data.xdrbind;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-02 10:28.
 * @Description :描述
 */
public class GetUserBindFarmByMobileBean {

    public String command="GetUserBindFarmByMobile";
    public ParamsBean params;


  public   static class  ParamsBean {
        public  String UserId;
        public String Mobile;

    }

}
