package com.agridata.cdzhdj.activity.daoUtils;

import android.content.Context;


import com.agridata.cdzhdj.data.HarmlessAnimalBean;
import com.agridata.cdzhdj.dbutils.DaoManager;


import java.util.List;

/**
 * @auther 56454
 * @date 2022/7/19
 * @time 17:50.
 */
public class HarmlessAnimalBeanUtils<T> {


    private static final String TAG = HarmlessAnimalBeanUtils.class.getSimpleName();
    private static DaoManager mManager;



    /**
     * 获取单例
     *
     * @return
     */
    public static HarmlessAnimalBeanUtils getInstance(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
        return ConfigHolder.INSTANCE;
    }

    private static class ConfigHolder {
        private static final HarmlessAnimalBeanUtils INSTANCE = new HarmlessAnimalBeanUtils();
    }



    /**
     * 插入记录，如果表未创建，先创建表
     */
    public boolean insert(T pEntity) {
        return mManager.getDaoSession().getHarmlessAnimalBeanDao().insert((HarmlessAnimalBean) pEntity) != -1;
    }




    /**
     * 删除单条记录
     * @param User
     * @return
     */
    public boolean deleteUser(HarmlessAnimalBean User){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(User);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     * @return
     */
    public boolean deleteAll(){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(HarmlessAnimalBean.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<HarmlessAnimalBean> queryAllUser(){
        return mManager.getDaoSession().loadAll(HarmlessAnimalBean.class);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public HarmlessAnimalBean queryUserById(long key){
        return mManager.getDaoSession().load(HarmlessAnimalBean.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<HarmlessAnimalBean> queryUserByNativeSql(String sql, String[] conditions){
        return mManager.getDaoSession().queryRaw(HarmlessAnimalBean.class, sql, conditions);
    }


}
