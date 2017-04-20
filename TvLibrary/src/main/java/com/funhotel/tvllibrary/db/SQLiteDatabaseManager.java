package com.funhotel.tvllibrary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @Title: SQLiteDatabaseManager
 * @Description: 数据的管理
 * @author: Zhiyahan
 * @data: 2016/6/27 16:46
 */
public class SQLiteDatabaseManager {

    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private static SQLiteDatabaseManager sqLiteDatabaseManager;
    private Context context;


    public SQLiteDatabaseManager(Context context) {
        this.context = context;
    }

    public static SQLiteDatabaseManager getSQLiteDatabaseManager(Context context) {
        if (null == sqLiteDatabaseManager) {
            sqLiteDatabaseManager = new SQLiteDatabaseManager(context);
        }
        return sqLiteDatabaseManager;
    }

    public synchronized DBHelper getHelper() {
        if (null == dbHelper) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    public synchronized SQLiteDatabase getSQLiteDatabase() {
        // TODO Auto-generated method stub
        if (null == database) {
            database = getHelper().getWritableDatabase();
        }
        return database;
    }


}