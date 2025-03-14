package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:31.
 */
public class IsCheckDZHSp {
    private SharedPreferences mSharedPreferences;

    private IsCheckDZHSp() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("DZH", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static IsCheckDZHSp getInstance() {
        return IsCheckDZHSpHolder.INSTANCE;
    }

    private static class IsCheckDZHSpHolder {
        private static final IsCheckDZHSp INSTANCE = new IsCheckDZHSp();
    }

    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }


    /**
     * 保存
     *
     * @param data
     */
    public void setIsCheckDZHSp(int  data) {
        mSharedPreferences.edit().putInt("IS_DZH", data).apply();
    }

    /**
     * 获取 LowBle
     *
     * @return
     */
    public int  getIsCheckDZHSp() {
        return mSharedPreferences.getInt("IS_DZH", -1);
    }
}
