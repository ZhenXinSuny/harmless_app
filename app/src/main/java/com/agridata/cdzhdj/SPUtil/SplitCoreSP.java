package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-22 15:24.
 * @Description :描述
 */
public class SplitCoreSP {

    private SharedPreferences mSharedPreferences;

    private SplitCoreSP() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("ble", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static SplitCoreSP getInstance() {
        return SplitCoreSPHolder.INSTANCE;
    }

    private static class SplitCoreSPHolder {
        private static final SplitCoreSP INSTANCE = new SplitCoreSP();
    }
    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }


    public  void  setConnectStatus(String connect_status){
        mSharedPreferences.edit().putString("connect_status", connect_status).apply();
    }

    public  String getConnectStatus(){
        return mSharedPreferences.getString("connect_status", "-1");
    }

}
