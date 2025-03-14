package com.agridata.cdzhdj.SPUtil;

import com.agridata.cdzhdj.data.LoginData;
import com.agridata.network.CubeDataProvider;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-12-27 16:29.
 * @Description :描述
 */
public class AppDataProvider extends CubeDataProvider {


    @Override
    public String getUserToken() {
        LoginData userInfo = UserDataSP.getInstance().getUserInfo();
        if (userInfo == null || userInfo.Result == null) {
            return "";
        } else {
            return UserDataSP.getInstance().getUserInfo().Result.token();
        }
    }


    @Override
    public Boolean getTokenStatus() {
        return TokenConfigSp.getInstance().getTokenConfig();

    }


}
