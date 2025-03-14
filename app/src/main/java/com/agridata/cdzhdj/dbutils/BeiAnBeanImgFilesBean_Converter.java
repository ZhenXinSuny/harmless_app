package com.agridata.cdzhdj.dbutils;

import com.agridata.cdzhdj.data.BeiAnBean;
import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * @auther 56454
 * @date 2022/8/1
 * @time 16:17.
 */
public class BeiAnBeanImgFilesBean_Converter implements PropertyConverter<BeiAnBean.ImgFilesBean, String> {
    @Override
    public BeiAnBean.ImgFilesBean convertToEntityProperty(String databaseValue) {
        return new Gson().fromJson(databaseValue, BeiAnBean.ImgFilesBean.class);
    }

    @Override
    public String convertToDatabaseValue( BeiAnBean.ImgFilesBean entityProperty) {
        return new Gson().toJson(entityProperty);
    }
}
