package com.agridata.cdzhdj.data.harmless;

import com.agridata.cdzhdj.data.xdrbind.GetUserBindFarmByMobileBean;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-05-15 09:55.
 * @Description :描述
 */
public class CheckEartagIsusedBean {
    public String command="checkEartagIsused";
    public ParamsBean params;

    public   static class  ParamsBean {
        public  String eartag;

    }


}
