package com.agridata.cdzhdj.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.agridata.cdzhdj.data.db.bean.ApplyDBean;
import com.agridata.cdzhdj.data.db.bean.CollectionDBModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-05-06 09:22.
 * @Description :描述
 */

@Dao
public interface CollectionDBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrUpdate(CollectionDBModel  collectionDBModel);



    @Query("SELECT * FROM CollectionDBModel")
    Flowable<List<CollectionDBModel>> queryAllAsList();


    /**
     * 单条
     * @param collectionNo
     * @return
     */
    @Query("SELECT * FROM CollectionDBModel WHERE collectNo = :collectionNo")
    Flowable<List<CollectionDBModel>> queryList(String collectionNo);


    @Query("DELETE FROM CollectionDBModel WHERE collectNo = :collectionNo")
    Completable deleteById(String collectionNo);

}
