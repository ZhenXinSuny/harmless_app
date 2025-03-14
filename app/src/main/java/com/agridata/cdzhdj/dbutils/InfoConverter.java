package com.agridata.cdzhdj.dbutils;

import com.agridata.cdzhdj.data.HarmlessAnimalBean;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther 56454
 * @date 2022/7/19
 * @time 17:40.
 */
public class InfoConverter implements PropertyConverter<List<HarmlessAnimalBean.ResultAnimalBean>, String> {
    @Override
    public List<HarmlessAnimalBean.ResultAnimalBean> convertToEntityProperty(String databaseValue) {
//        return JSON.parseObject(databaseValue , HarmlessAnimalBean.ResultAnimalBean.class);
        return GsonUtil.fromJsonList(databaseValue,HarmlessAnimalBean.ResultAnimalBean.class);
    }

    @Override
    public String convertToDatabaseValue(List<HarmlessAnimalBean.ResultAnimalBean> arrays) {
//        return JSON.toJSONString(arrays);
        return  GsonUtil.toJson(arrays);
    }


}
