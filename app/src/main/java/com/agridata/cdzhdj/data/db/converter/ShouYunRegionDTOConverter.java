package com.agridata.cdzhdj.data.db.converter;

import androidx.room.TypeConverter;

import com.agridata.cdzhdj.data.db.rolebean.RoleBeanDB;
import com.agridata.cdzhdj.utils.GsonUtil;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-23 13:42.
 * @Description :描述
 */
public class ShouYunRegionDTOConverter {

    @TypeConverter
    public String objectToString(List<RoleBeanDB.HarmlessRegionBean> list) { //TrueEatData
        return GsonUtil.toJson(list);
    }

    @TypeConverter
    public List<RoleBeanDB.HarmlessRegionBean> stringToObject(String json) {
        return GsonUtil.toList(json);
    }
}
