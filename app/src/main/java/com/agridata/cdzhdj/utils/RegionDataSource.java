package com.agridata.cdzhdj.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.agridata.cdzhdj.data.TRegion;
import com.agridata.cdzhdj.dbutils.DaoManager;

public class RegionDataSource extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static Context ctx = null;

    public RegionDataSource(Context context) {
        super(context, DaoManager.DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean syncRegions() {

        Cursor cur = this.getReadableDatabase().rawQuery("select * from TREGION", new String[] {});

        while (cur.moveToNext()) {
            TRegion entity = new TRegion();
            entity.setRegion_id(cur.getInt(0));
            entity.setRegion_guid(cur.getString(1));
            entity.setRegion_parent_id(cur.getInt(2));
            entity.setRegion_name(cur.getString(3));
            entity.setRegion_level(cur.getInt(4));
            entity.setRegion_code(cur.getString(5));
            entity.setRegion_serial(cur.getInt(6));
            entity.setRegion_shortname(cur.getString(7));
            entity.setRegion_path(cur.getString(8));
            entity.setRegion_spell(cur.getString(9));
            DaoManager.queryRegionDao().insertOrReplace(entity);
        }

        cur.close();

        return true;
    }

    public String queryRegionCode(String region_serialnumber) {
        String regionCode = null;
        Cursor cur = this.getReadableDatabase().rawQuery(
                "select * from M_LiteRegion where region_serialnumber = ?",
                new String[] { region_serialnumber });

        while (cur.moveToNext()) {
            regionCode = cur.getString(5);
        }

        cur.close();

        return regionCode;
    }

}
