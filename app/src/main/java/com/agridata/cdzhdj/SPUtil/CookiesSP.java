package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;

/**
 * @auther 56454
 * @date 2022/6/22
 * @time 11:48.
 */
public class CookiesSP {


    private SharedPreferences mSharedPreferences;

    private CookiesSP() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("cookie_sp", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static CookiesSP getInstance() {
        return CookiesSPHolder.INSTANCE;
    }

    private static class CookiesSPHolder {
        private static final CookiesSP INSTANCE = new CookiesSP();
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
}
