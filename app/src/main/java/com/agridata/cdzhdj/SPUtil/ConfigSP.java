package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.utils.AppConstants;

/**
 * @auther 56454
 * @date 2022/6/9
 * @time 10:43.
 */
public class ConfigSP {
    private SharedPreferences mSharedPreferences;

    private ConfigSP() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("admission_config", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static ConfigSP getInstance() {
        return ConfigHolder.INSTANCE;
    }

    private static class ConfigHolder {
        private static final ConfigSP INSTANCE = new ConfigSP();
    }

    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }



    /**
     * 设置App资源路径
     *
     * @param resourcePath
     */
    public void setResourcePath(String resourcePath) {
        mSharedPreferences.edit().putString(AppConstants.SP.RESOURCE_PATH, resourcePath).apply();
    }

    /**
     * 获取App资源路径
     *
     * @return
     */
    public String getResourcePath() {
        return mSharedPreferences.getString(AppConstants.SP.RESOURCE_PATH, "");
    }

    public void setDeviceId(String deviceId) {
        mSharedPreferences.edit().putString(AppConstants.SP.DEVICE_ID, deviceId).apply();
    }

    public String getDeviceId() {
        return mSharedPreferences.getString(AppConstants.SP.DEVICE_ID, "");
    }


    public void setXiaoDu(String XiaoDuTv) {
        mSharedPreferences.edit().putString(AppConstants.SP.XIAO_DU_TV, XiaoDuTv).apply();
    }

    public String getXiaoDu() {
        return mSharedPreferences.getString(AppConstants.SP.XIAO_DU_TV, "");
    }

}
