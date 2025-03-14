package com.agridata.cdzhdj.dbutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.agridata.cdzhdj.dao.DaoMaster;
import com.agridata.cdzhdj.dao.HarmlessAnimalBeanDao;
import com.agridata.cdzhdj.dao.TRegionDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

/**
 * @auther 56454
 * @date 2022/7/19
 * @time 17:48.
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, HarmlessAnimalBeanDao.class);
    }
}
