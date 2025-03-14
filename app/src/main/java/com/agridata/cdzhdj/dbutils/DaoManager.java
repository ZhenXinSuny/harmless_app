package com.agridata.cdzhdj.dbutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.dao.DaoMaster;
import com.agridata.cdzhdj.dao.DaoSession;
import com.agridata.cdzhdj.dao.TRegionDao;
import com.agridata.cdzhdj.utils.RegionDataSource;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @auther 56454
 * @date 2022/7/19
 * @time 17:47.
 */
public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();
    private static final String DB_NAME = "harmless_app.db";

    private Context context;

    //多线程中要被共享的使用volatile关键字修饰
    private volatile static DaoManager manager = new DaoManager();
    private static DaoMaster sDaoMaster;
    private static DaoMaster.DevOpenHelper sHelper;
    private static DaoSession sDaoSession;

    /**
     * 单例模式获得操作数据库对象
     * @return
     */
    public static DaoManager getInstance(){
        return manager;
    }

    public void init(Context context){
        this.context = context;
    }

    /**
     * 判断是否有存在数据库，如果没有则创建
     * @return
     */
    public DaoMaster getDaoMaster(){
        if(sDaoMaster == null) {
            MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context, DB_NAME, null);
            sDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return sDaoMaster;
    }

    /**
     * 完成对数据库的添加、删除、修改、查询操作，仅仅是一个接口
     * @return
     */
    public DaoSession getDaoSession(){
        if(sDaoSession == null){
            if(sDaoMaster == null){
                sDaoMaster = getDaoMaster();
            }
            sDaoSession = sDaoMaster.newSession();
        }
        return sDaoSession;
    }

    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug(){
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper(){
        if(sHelper != null){
            sHelper.close();
            sHelper = null;
        }
    }

    public void closeDaoSession(){
        if(sDaoSession != null){
            sDaoSession.clear();
            sDaoSession = null;
        }
    }
    public static String DATABASE_NAME = "region_cd.db";

    public static DaoSession getDaoSessionFromName(String daName) {
//		DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(App.app,
//				daName, null);
        SQLiteOpenHelper helper = new RegionDataSource(MyApplication.getContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        SQLiteDatabase mDataBase = db;
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        return daoSession;
    }

    /**
     * @return 复制数据库
     */
    public static boolean copyDataBase(Context ctx) {

        String DATABASE_PATH = "/data/data/" + ctx.getPackageName() + "/databases";

        String databaseFileName = DATABASE_PATH + "/" + DATABASE_NAME;

        File mPath = new File(DATABASE_PATH);
        if (!mPath.exists()) {
            mPath.mkdir();
        }

        File mFile = new File(databaseFileName);
        if (mFile.exists()) {
            try {
                mFile.delete();
            } catch (Exception e) {
                return false;
            }
        }
        if (!mFile.exists()) {
            try {
                mFile.createNewFile();
            } catch (IOException e) {
                return false;
            }
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(
                    databaseFileName);
            InputStream inputStream = ctx.getAssets().open(DATABASE_NAME);
            byte[] buffer = new byte[10240];
            int readlen = 0;
            while ((readlen = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, readlen);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }
    /**
     *  根据区划id查询区划信息
     *
     * @return TRegionDao
     */
    public static TRegionDao queryRegionDao(){
        TRegionDao tRegionDao = getDaoSessionFromName(DATABASE_NAME).getTRegionDao();
        return tRegionDao;
    }

}
