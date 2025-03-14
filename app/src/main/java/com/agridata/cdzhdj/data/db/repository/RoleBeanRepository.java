package com.agridata.cdzhdj.data.db.repository;

import android.content.Context;

import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.data.db.dao.AppDatabase;
import com.agridata.cdzhdj.data.db.mapper.RoleInfoMapper;
import com.agridata.network.utils.LogUtil;

import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;


/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-23 15:05.
 * @Description :描述
 */
public class RoleBeanRepository {

    private static volatile RoleBeanRepository mInstance;
    private final RoleInfoMapper roleInfoMapper;

    private RoleBeanRepository() {
        this.roleInfoMapper = new RoleInfoMapper();

    }

    public static RoleBeanRepository getInstance() {
        if (mInstance == null) {
            synchronized (RoleBeanRepository.class) {
                if (mInstance == null) {
                    mInstance = new RoleBeanRepository();
                }
            }
        }
        return mInstance;
    }


    public void insertUser(Context context, RoleBean roleInfo) {
//        AppDatabase.getInstance(context).userDao()
//                        .insertOrUpdate(roleInfoMapper.convertToDbModel(roleInfo)).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//                });
        CustomDisposable.addDisposable(AppDatabase.getInstance(context).userDao().insertOrUpdate(roleInfoMapper.convertToDbModel(roleInfo)), () -> {
            LogUtil.d("lzx----》","插入成功");
        });
    }


}
