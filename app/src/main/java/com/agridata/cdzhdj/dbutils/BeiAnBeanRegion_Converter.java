package com.agridata.cdzhdj.dbutils;

import com.agridata.cdzhdj.data.BeiAnBean;
import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * @auther 56454
 * @date 2022/8/1
 * @time 16:17.
 */
public class BeiAnBeanRegion_Converter  implements PropertyConverter<BeiAnBean.RegionBean, String> {
    @Override
    public BeiAnBean.RegionBean convertToEntityProperty(String databaseValue) {
        return new Gson().fromJson(databaseValue, BeiAnBean.RegionBean.class);
    }

    @Override
    public String convertToDatabaseValue( BeiAnBean.RegionBean entityProperty) {
        return new Gson().toJson(entityProperty);
    }
}
