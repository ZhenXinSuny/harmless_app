package com.agridata.cdzhdj.data.db.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.agridata.cdzhdj.data.db.bean.ApplyDBean;
import com.agridata.cdzhdj.data.db.bean.CollectionDBModel;
import com.agridata.cdzhdj.data.db.rolebean.RoleBeanDB;
import com.agridata.cdzhdj.utils.GlideUtils;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-22 11:51.
 * @Description :描述
 */
@Database(entities = {RoleBeanDB.class, ApplyDBean.class, CollectionDBModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RoleBeanDBDao userDao();

    public abstract ApplyBeanDBDao applyDao();


    public abstract CollectionDBDao collectionDao();

    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_harmless") //这个是没有任何迁移策略 数据会丢失
                    .build();
        }
        return instance;
    }


}
