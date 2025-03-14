package com.agridata.cdzhdj.dbutils;

import com.agridata.cdzhdj.data.BeiAnBean;
import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * @auther 56454
 * @date 2022/8/1
 * @time 16:17.
 */
public class BeiAnBeanCarInfoBean_Converter implements PropertyConverter<BeiAnBean.CarInfoBean, String> {
    @Override
    public BeiAnBean.CarInfoBean convertToEntityProperty(String databaseValue) {
        return new Gson().fromJson(databaseValue, BeiAnBean.CarInfoBean.class);
    }

    @Override
    public String convertToDatabaseValue( BeiAnBean.CarInfoBean entityProperty) {
        return new Gson().toJson(entityProperty);
    }
}
