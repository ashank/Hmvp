package com.funhotel.tvllibrary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.funhotel.tvllibrary.utils.DebugUtil;

/**
 * Created by LIQI on 2016/10/25.
 */

public class AuthenticationDBManager extends DBManager{

    /**
     * 保存数据，存在name的话更新数据
     * @param context
     * @param name
     * @param value
     * @param stbID
     */
    public static void addAuthenticaiton(Context context, String name, String value, String stbID){
        try {
            SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
            synchronized (db) {
                if(isExistName(db,name,stbID)){
                    //存在name,更新数据
                    String sql = "update " + TableKey.TABLE_AUTHENTICATION + " set " + TableKey.AUTO_VALUE + " = \"" + value
                            + "\" where " + TableKey.AUTO_NAME + " = \"" + name + "\" and " + TableKey.STB_ID + " = \"" + stbID + "\"";
                    db.execSQL(sql);
                    DebugUtil.e("更新数据>>"+sql);
                }else{
                    //不存在，添加数据
                    ContentValues values = new ContentValues();
                    values.put(TableKey.STB_ID, stbID);
                    values.put(TableKey.AUTO_NAME, name);
                    values.put(TableKey.AUTO_VALUE, value);
                    long i = db.insert(TableKey.TABLE_AUTHENTICATION, null, values);
                    DebugUtil.e("插入数据>>>"+values.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 通过name获取value
     * @param name
     */
    public static String getValueByName(Context context, String name){
        String value = "";
        Cursor cursor = null;
        try {
            SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
            synchronized (db){
                String sql = "select " + TableKey.AUTO_VALUE + " from " + TableKey.TABLE_AUTHENTICATION + " where " + TableKey.AUTO_NAME + " = ?";
                cursor = db.rawQuery(sql, new String[]{name});
                if(cursor.moveToFirst()){
                    value = cursor.getString(cursor.getColumnIndexOrThrow(TableKey.AUTO_VALUE));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return value;
    }


    /**
     * 判断是否存在name
     * @param db
     * @param name
     * @param stbID
     * @return
     */
    public static boolean isExistName(SQLiteDatabase db, String name, String stbID){
        boolean isExist = false;
        Cursor cursor = null;
        try{
            synchronized (db){
                String sql = "select * from " + TableKey.TABLE_AUTHENTICATION + " where " + TableKey.AUTO_NAME + " = \"" + name + "\" and "
                        + TableKey.STB_ID + " = \"" + stbID + "\"";
                cursor = db.rawQuery(sql,null);
                if(cursor!=null && cursor.getCount()>0){
                    isExist = true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return isExist;
    }
}
