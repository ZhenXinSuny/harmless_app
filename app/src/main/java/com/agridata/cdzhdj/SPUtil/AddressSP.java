package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;

/**
 * @auther 56454
 * @date 2022/6/27
 * @time 11:24.
 */
public class AddressSP {
    private SharedPreferences mSharedPreferences;

    private AddressSP() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("address", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static AddressSP getInstance() {
        return AddressSPHolder.INSTANCE;
    }

    private static class AddressSPHolder {
        private static final AddressSP INSTANCE = new AddressSP();
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
     * @param Longitude
     */
    public void setLongitude(double Longitude) {
        mSharedPreferences.edit().putString("Longitude", String.valueOf(Longitude)).apply();
    }

    /**
     * 获取 LowBle
     *
     * @return
     */
    public String getLongitude() {
        return mSharedPreferences.getString("Longitude", "");
    }



    /**
     * 保存 VH-75T
     *
     * @param Latitude
     */
    public void setLatitude(double Latitude) {
        mSharedPreferences.edit().putString("Latitude", String.valueOf(Latitude)).apply();
    }

    /**
     * 获取 LowBle
     *
     * @return
     */
    public String getLatitude() {
        return mSharedPreferences.getString("Latitude", "");
    }



    public void setAddress(String address) {
        mSharedPreferences.edit().putString("address", address).apply();
    }

    /**
     * 获取 LowBle
     *
     * @return
     */
    public String getAddress() {
        return mSharedPreferences.getString("address", "");
    }


}
