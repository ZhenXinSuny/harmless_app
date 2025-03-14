package com.agridata.cdzhdj.data.db.repository;

import android.content.Context;
import android.text.TextUtils;

import com.agridata.cdzhdj.data.db.bean.ApplyDBean;
import com.agridata.cdzhdj.data.db.dao.AppDatabase;
import com.agridata.cdzhdj.data.db.mapper.ApplyMapper;
import com.agridata.cdzhdj.data.harmless.ApplyBean;
import com.agridata.network.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-28 16:41.
 * @Description :描述
 */
public class ApplyBeanRepository {


    private static volatile ApplyBeanRepository mInstance;
    private final ApplyMapper applyMapper;

    private ApplyBeanRepository() {
        this.applyMapper = new ApplyMapper();

    }

    public static ApplyBeanRepository getInstance() {
        if (mInstance == null) {
            synchronized (RoleBeanRepository.class) {
                if (mInstance == null) {
                    mInstance = new ApplyBeanRepository();
                }
            }
        }
        return mInstance;
    }


    public void insertApply(Context context, List<ApplyBean.ResultBean> resultBeans) {
        for (ApplyBean.ResultBean resultBean : resultBeans) {
            ApplyMapper.convertToDbModel(resultBean, new ApplyMapper.OnConversionCompleteListener() {
                @Override
                public void onConversionComplete(ApplyDBean dbBean) {
                    LogUtil.d("lzx----》", "dbBean" + dbBean.toString());
                    CustomDisposable.addDisposable(AppDatabase.getInstance(context).applyDao().insertOrUpdate(dbBean), () -> {
                        LogUtil.d("lzx----》", "插入成功");
                    });
                }
                @Override
                public void onConversionFailed(Exception e) {
                }
            });
        }

    }

    public void deleteAll(Context context, List<ApplyBean.ResultBean> resultBeans) {
        CustomDisposable.addDisposable(AppDatabase.getInstance(context).applyDao().deleteAll(), () -> {
            LogUtil.d("lzx----》", "删除成功");
            for (ApplyBean.ResultBean resultBean : resultBeans) {
                ApplyMapper.convertToDbModel(resultBean, new ApplyMapper.OnConversionCompleteListener() {
                    @Override
                    public void onConversionComplete(ApplyDBean dbBean) {
                        LogUtil.d("lzx----》", "dbBean" + dbBean.toString());
                        CustomDisposable.addDisposable(AppDatabase.getInstance(context).applyDao().insertOrUpdate(dbBean), () -> {
                            LogUtil.d("lzx----》", "插入成功");
                        });
                    }
                    @Override
                    public void onConversionFailed(Exception e) {
                    }
                });
            }
        });
    }


    public void deleteById(Context context, String mid) {
        CustomDisposable.addDisposable(AppDatabase.getInstance(context).applyDao().deleteById(mid), () -> {
            LogUtil.d("lzx----》", "删除成功");
        });
    }


    public void insert(Context context, ApplyBean.ResultBean resultBeans) {
            ApplyMapper.convertToDbModel(resultBeans, new ApplyMapper.OnConversionCompleteListener() {
                @Override
                public void onConversionComplete(ApplyDBean dbBean) {
                    LogUtil.d("lzx----》", "dbBean" + dbBean.toString());
                    CustomDisposable.addDisposable(AppDatabase.getInstance(context).applyDao().insert(dbBean), () -> {
                        LogUtil.d("lzx----》", "单挑插入成功");
                    });
                }
                @Override
                public void onConversionFailed(Exception e) {
                }
            });


    }
}
