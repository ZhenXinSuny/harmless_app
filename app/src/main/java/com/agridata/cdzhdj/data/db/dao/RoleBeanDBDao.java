package com.agridata.cdzhdj.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.agridata.cdzhdj.data.db.rolebean.RoleBeanDB;

import io.reactivex.Completable;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-22 11:49.
 * @Description :描述
 */
@Dao
public interface  RoleBeanDBDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrUpdate(RoleBeanDB roleBean);


    @Query("SELECT * FROM RoleBeanDB WHERE RoleUserInfoDBUserID = :userId ")
    RoleBeanDB findUserById(String userId);
}
