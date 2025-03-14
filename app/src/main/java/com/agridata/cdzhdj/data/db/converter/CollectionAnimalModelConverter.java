package com.agridata.cdzhdj.data.db.converter;

import androidx.room.TypeConverter;

import com.agridata.cdzhdj.data.db.bean.CollectionDBModel;
import com.agridata.cdzhdj.data.db.rolebean.RoleBeanDB;
import com.agridata.cdzhdj.utils.GsonUtil;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-05-05 12:28.
 * @Description :描述
 */
public class CollectionAnimalModelConverter {

    @TypeConverter
    public String objectToString(List<CollectionDBModel.DataItemBean> list) { //TrueEatData
        return GsonUtil.toJson(list);
    }

    @TypeConverter
    public List<CollectionDBModel.DataItemBean> stringToObject(String json) {
        return GsonUtil.fromJsonList(json,CollectionDBModel.DataItemBean.class);
    }
}
