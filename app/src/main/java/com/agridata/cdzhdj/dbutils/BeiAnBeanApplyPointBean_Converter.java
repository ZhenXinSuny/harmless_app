package com.agridata.cdzhdj.dbutils;

import com.agridata.cdzhdj.data.BeiAnBean;
import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * @auther 56454
 * @date 2022/8/1
 * @time 16:17.
 */
public class BeiAnBeanApplyPointBean_Converter implements PropertyConverter<BeiAnBean.ApplyPointBean, String> {
    @Override
    public BeiAnBean.ApplyPointBean convertToEntityProperty(String databaseValue) {
        return new Gson().fromJson(databaseValue, BeiAnBean.ApplyPointBean.class);
    }

    @Override
    public String convertToDatabaseValue( BeiAnBean.ApplyPointBean entityProperty) {
        return new Gson().toJson(entityProperty);
    }
}
