package com.agridata.cdzhdj.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.agridata.cdzhdj.data.db.bean.ApplyDBean;
import com.agridata.cdzhdj.data.db.rolebean.RoleBeanDB;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-28 16:41.
 * @Description :描述
 */
@Dao
public interface ApplyBeanDBDao {


    //(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrUpdate(ApplyDBean... applyDbeans);


    @Query("SELECT * FROM ApplyDBean WHERE strftime('%Y-%m-%d %H:%M:%S', applyTime) BETWEEN :startTime AND :endTime AND  checkStatus= :checkStatus")
    Flowable<List<ApplyDBean>> queryAllAsList(String startTime, String endTime, int checkStatus);


    @Query("DELETE FROM ApplyDBean")
    Completable deleteAll(); // 方法用于清空表


    @Query("SELECT * FROM ApplyDBean WHERE   mid= :mid")
    Flowable<List<ApplyDBean>> queryList(String mid);


    @Query("DELETE FROM ApplyDBean WHERE mid = :mid")
    Completable deleteById(String mid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(ApplyDBean applyDbeans);



    @Query("UPDATE ApplyDBean SET checkStatus = 1 WHERE mid = :mid")
    void updateApplyStatus(String mid);
}
