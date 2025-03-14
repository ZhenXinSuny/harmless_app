package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;

/**
 * @auther 56454
 * @date 2022/6/21
 * @time 16:02.
 */
public class FirstLoginSP {

    private SharedPreferences mSharedPreferences;

    private FirstLoginSP() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("first_login", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static FirstLoginSP getInstance() {
        return FirstLoginSPHolder.INSTANCE;
    }

    private static class FirstLoginSPHolder {
        private static final FirstLoginSP INSTANCE = new FirstLoginSP();
    }
    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }

    /**
     * 保存boolean 值 是否首次显示dialog
     * @param FirstShow
     */
    public  void  setFirstShow(Boolean FirstShow){
        mSharedPreferences.edit().putBoolean("IS_LOGIN", FirstShow).apply();
    }

    public  Boolean getFirstShow(){
        return mSharedPreferences.getBoolean("IS_LOGIN", false);
    }


    public  void  setLogin(Boolean FirstShow){
        mSharedPreferences.edit().putBoolean("IS_LOGIN_FIRST", FirstShow).apply();
    }

    public  Boolean getLogin(){
        return mSharedPreferences.getBoolean("IS_LOGIN_FIRST", false);
    }


    public  void  setAddressZT(Boolean Zt){
        mSharedPreferences.edit().putBoolean("address_zt", Zt).apply();
    }

    public  Boolean getAddressZT(){
        return mSharedPreferences.getBoolean("address_zt", false);
    }
}
