package com.funhotel.tvllibrary.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @Title: RoomNumberManager
 * @Description:
 * @author: Zhang Yetao
 * @data: 2016/7/15 16:11
 */
public class RoomNumberManager {
    /**
     *
     * @param db
     * @param roomNumber 房间号
     */
        public static void addRoomNumber(SQLiteDatabase db , String stbId ,String roomNumber){
        try {
            synchronized (db){
                ContentValues values = new ContentValues();
                values.put(TableKey.ROOM_NUMBER ,roomNumber);
                values.put(TableKey.STB_ID ,stbId);
                db.insert(TableKey.TABLE_ROOM_NUMBER,null,values);
                values.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param db
     * @return 房间号
     */
        public static String getRoomNumber(SQLiteDatabase db,String stbId){
        Cursor cursor = null;
        String roomNumber = null;
        try{
            synchronized (db){
                String sql = "select * from " + TableKey.TABLE_ROOM_NUMBER + " where "+TableKey.STB_ID + " = ?";
                String[] args = {stbId};
                cursor = db.rawQuery(sql,args);
                while (cursor.moveToNext()){
                    roomNumber = cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ROOM_NUMBER));
                }
                cursor.close();
            }
            return roomNumber;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null!=cursor){
                cursor.close();
            }
        }
        return roomNumber;
    }
}
