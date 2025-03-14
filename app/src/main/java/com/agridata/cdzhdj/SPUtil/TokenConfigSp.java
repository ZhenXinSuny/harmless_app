package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.AppConfigurationBean;
import com.agridata.cdzhdj.utils.GsonUtil;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-12-29 14:17.
 * @Description :描述
 */
public class TokenConfigSp {

    private final SharedPreferences mSharedPreferences;

    private TokenConfigSp() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("token_config", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static TokenConfigSp getInstance() {
        return TokenConfigSp.TokenConfigSpHolder.INSTANCE;
    }

    private static class TokenConfigSpHolder {
        private static final TokenConfigSp INSTANCE = new TokenConfigSp();
    }

    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }


    public void setTokenConfig(boolean   tokenStatus) {
        mSharedPreferences.edit().putBoolean("tokenStatus", tokenStatus).apply();
    }

    /**
     * 获取 LowBle
     *
     * @return
     */
    public boolean getTokenConfig() {
        return mSharedPreferences.getBoolean("tokenStatus", false);
    }


    public void setKey(AppConfigurationBean.DataBean appConfigurationBean) {

        mSharedPreferences.edit().putString("appConfigurationBean", GsonUtil.toJson(appConfigurationBean)).apply();
    }

    /**
     * 获取 当前登录的用户信息
     *
     * @return
     */
    public AppConfigurationBean.DataBean getKey() {
        try {
            return GsonUtil.fromJson(mSharedPreferences.getString("appConfigurationBean", null), AppConfigurationBean.DataBean.class);
        } catch (Exception e) {
            return null;
        }
    }

}
