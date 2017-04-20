package com.funhotel.tvllibrary.db;

import android.database.sqlite.SQLiteDatabase;

import com.funhotel.tvllibrary.utils.DebugUtil;

/**
 * @Title: DBManager
 * @Description: 清空表数据
 * @author: LinWeiDong
 * @data: 2016/4/19 15:10
 */
public abstract class DBManager {

    /**
     * @Title: RefreshTable
     * @Description: TODO 清空表数据
     * @param database  SQLiteDatabase
     * @param tableName String
     */
    public static void refreshTable(SQLiteDatabase database,String tableName) {
        synchronized (database) {
            String sqlString="DELETE FROM " + tableName;
            try {
                database.execSQL(sqlString);
                DebugUtil.e("清空表>>>>tableName>>>>>>>"+tableName);
            } catch (Exception e) {
                // TODO: handle exception
            }finally{
                sqlString=null;
            }
        }
    }



}