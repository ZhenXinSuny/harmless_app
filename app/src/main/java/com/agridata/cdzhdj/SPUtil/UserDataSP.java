package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.utils.GsonUtil;

/**
 * @auther 56454
 * @date 2022/6/22
 * @time 11:03.
 */
public class UserDataSP {

    private SharedPreferences mSharedPreferences;

    private UserDataSP() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static UserDataSP getInstance() {
        return UserDataSPHolder.INSTANCE;
    }

    private static class UserDataSPHolder {
        private static final UserDataSP INSTANCE = new UserDataSP();
    }
    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

    /**
     * 保存 当前登录的用户信息
     *
     * @param LoginData
     */
    public void setUserInfo(LoginData LoginData) {

        mSharedPreferences.edit().putString("user_info", GsonUtil.toJson(LoginData)).apply();
    }

    /**
     * 获取 当前登录的用户信息
     *
     * @return
     */
    public LoginData getUserInfo() {
        try {
            return GsonUtil.fromJson(mSharedPreferences.getString("user_info", null), LoginData.class);
        } catch (Exception e) {
            return null;
        }
    }
}
