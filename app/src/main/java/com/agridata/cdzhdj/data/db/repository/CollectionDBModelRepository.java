package com.agridata.cdzhdj.data.db.repository;

import android.content.Context;

import com.agridata.cdzhdj.data.CollectInfoData;
import com.agridata.cdzhdj.data.db.bean.ApplyDBean;
import com.agridata.cdzhdj.data.db.dao.AppDatabase;
import com.agridata.cdzhdj.data.db.mapper.ApplyMapper;
import com.agridata.cdzhdj.data.db.mapper.CollectionModelMapper;
import com.agridata.cdzhdj.data.harmless.ApplyBean;
import com.agridata.network.utils.LogUtil;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-28 16:41.
 * @Description :描述
 */
public class CollectionDBModelRepository {


    private static volatile CollectionDBModelRepository mInstance;
    private final CollectionModelMapper collectionModelMapper;

    private CollectionDBModelRepository() {
        this.collectionModelMapper = new CollectionModelMapper();

    }

    public static CollectionDBModelRepository getInstance() {
        if (mInstance == null) {
            synchronized (RoleBeanRepository.class) {
                if (mInstance == null) {
                    mInstance = new CollectionDBModelRepository();
                }
            }
        }
        return mInstance;
    }


    public void insert(Context context, CollectInfoData collection) {
        CustomDisposable.addDisposable(AppDatabase.getInstance(context).collectionDao().insertOrUpdate(collectionModelMapper.convertToDbModel(collection)), () -> {
            LogUtil.d("lzx----》", "插入成功");
        });

    }

    public void deleteById(Context context, String collectionNo) {
        CustomDisposable.addDisposable(AppDatabase.getInstance(context).collectionDao().deleteById(collectionNo), () -> {
            LogUtil.d("lzx----》", "删除成功");
        });
    }

}
