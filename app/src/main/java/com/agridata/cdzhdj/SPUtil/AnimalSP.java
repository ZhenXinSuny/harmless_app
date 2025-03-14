package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.HarmlessAnimalBean;
import com.agridata.cdzhdj.data.ImmuneAnimalBean;
import com.agridata.cdzhdj.data.ImmuneXdrBean;
import com.agridata.cdzhdj.utils.GsonUtil;

import java.util.List;

/**
 * Created by XXX.
 * Date: 2022/11/25
 * describe
 */
public class AnimalSP {


    private SharedPreferences mSharedPreferences;

    private AnimalSP() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("IMMUNE_ANIMAL", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static AnimalSP getInstance() {
        return AnimalSPHolder.INSTANCE;
    }

    private static class AnimalSPHolder {
        private static final AnimalSP INSTANCE = new AnimalSP();
    }

    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }


    /**
     *  保存animal  list
     *
     * @param immuneAnimalBean
     */
    public void setAnimalList(List<ImmuneAnimalBean.ResultDTO> immuneAnimalBean) {
        mSharedPreferences.edit().putString("immune_animal", GsonUtil.toJson(immuneAnimalBean)).apply();
    }


    /**
     * 获取 animal list
     *
     * @return
     */
    public List<ImmuneAnimalBean.ResultDTO>  getAnimalList() {
        try {
            return GsonUtil.fromJsonList(mSharedPreferences.getString("immune_animal", null), ImmuneAnimalBean.ResultDTO.class);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     *  保存animal  list
     *
     * @param immuneAnimalBean
     */
    public void setChooseAnimal(ImmuneXdrBean.Result.AnimalVariety immuneAnimalBean) {
        mSharedPreferences.edit().putString("choose_immune_animal",GsonUtil.toJson(immuneAnimalBean)).apply();
    }


    /**
     * 获取 animal list
     *
     * @return
     */
    public ImmuneXdrBean.Result.AnimalVariety  getChooseAnimal() {
        try {
            return GsonUtil.fromJson(mSharedPreferences.getString("choose_immune_animal", null), ImmuneXdrBean.Result.AnimalVariety.class);
        } catch (Exception e) {
            return null;
        }
    }



    public void setHarmlessAnimalList(List<HarmlessAnimalBean.ResultAnimalBean> resultAnimalBeanList) {
        mSharedPreferences.edit().putString("harmless_animal", GsonUtil.toJson(resultAnimalBeanList)).apply();
    }


    public List<HarmlessAnimalBean.ResultAnimalBean>  getHarmlessAnimalList() {
        try {
            return GsonUtil.fromJsonList(mSharedPreferences.getString("harmless_animal", null), HarmlessAnimalBean.ResultAnimalBean.class);
        } catch (Exception e) {
            return null;
        }
    }
}
