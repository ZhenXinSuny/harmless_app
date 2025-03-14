package com.agridata.cdzhdj.utils;

import android.content.Context;


import com.agridata.cdzhdj.dao.TRegionDao;
import com.agridata.cdzhdj.data.TRegion;
import com.agridata.cdzhdj.dbutils.DaoManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @auther 56454
 * @date 2022/2/25
 * @time 11:06.
 */
public class AddressUtils {
    private static final String TAG = AddressUtils.class.getSimpleName();
    private DaoManager mManager;

    public AddressUtils(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }
    /**
     * 查询所有记录
     * @return
     */
    public List<TRegion> queryAllTRegion(){
        return mManager.getDaoSession().loadAll(TRegion.class);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public TRegion queryTRegionById(long key){
        return mManager.getDaoSession().load(TRegion.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<TRegion> queryTRegionByNativeSql(String sql, String[] conditions){
        return mManager.getDaoSession().queryRaw(TRegion.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     * @return
     */
    public List<TRegion> queryTRegionByQueryBuilder(long id){
        QueryBuilder<TRegion> queryBuilder = mManager.getDaoSession().queryBuilder(TRegion.class);
        return queryBuilder.where(TRegionDao.Properties.Region_id.eq(id)).list();
    }
}
