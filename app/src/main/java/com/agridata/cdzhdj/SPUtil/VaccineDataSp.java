package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-25 10:58.
 * @Description :描述
 */
public class VaccineDataSp {


    private SharedPreferences mSharedPreferences;

    private VaccineDataSp() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("vaccinedata_sp", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static VaccineDataSp getInstance() {
        return VaccineDataSp.VaccineDataHolder.INSTANCE;
    }

    private static class VaccineDataHolder {
        private static final VaccineDataSp INSTANCE = new VaccineDataSp();
    }

    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }

    /**
     * 疫苗厂家
     * @param vaccineFactory
     */
    public  void  setVaccineFactory(String vaccineFactory){
        mSharedPreferences.edit().putString("VaccineFactory", vaccineFactory ).apply();
    }

    public  String getVaccineFactory(){
        return mSharedPreferences.getString("VaccineFactory", "");
    }

    /**
     * 批次
     * @param batch
     */
    public  void  setBatch(String batch){
        mSharedPreferences.edit().putString("batch", batch ).apply();
    }

    public  String getBatch(){
        return mSharedPreferences.getString("batch", "");
    }


    public  void  setUnit(int  unit){
        mSharedPreferences.edit().putInt("unit", unit ).apply();
    }

    public  int  getUnit(){
        return mSharedPreferences.getInt("unit", -1);
    }

}
