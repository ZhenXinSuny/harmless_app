package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:31.
 */
public class BleSp {
    private SharedPreferences mSharedPreferences;

    private BleSp() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("ble", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static BleSp getInstance() {
        return UserHolder.INSTANCE;
    }

    private static class UserHolder {
        private static final BleSp INSTANCE = new BleSp();
    }

    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }


    /**
     * 保存 VH-75T
     *
     * @param address
     */
    public void setVH_75T(String   address) {
        mSharedPreferences.edit().putString("VH-75T", address).apply();
    }

    /**
     * 获取 LowBle
     *
     * @return
     */
    public String getVH_75T() {
        return mSharedPreferences.getString("VH-75T", "");
    }


    public void setLowBle(String   address) {
        mSharedPreferences.edit().putString("LowBle", address).apply();
    }

    /**
     * 获取 LowBle
     *
     * @return
     */
    public String getLowBle() {
        return mSharedPreferences.getString("LowBle", "");
    }


    public void setTagReaderBle(String   address) {
        mSharedPreferences.edit().putString("TagReader", address).apply();
    }

    /**
     * 获取 LowBle
     *
     * @return
     */
    public String getTagReaderBle() {
        return mSharedPreferences.getString("TagReader", "");
    }
}
