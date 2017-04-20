package com.funhotel.tvllibrary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.funhotel.tvllibrary.application.Channel;
import com.funhotel.tvllibrary.application.FiberHomeChannelData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LIQI on 2016/10/25.
 */

public class FiberHomeChannelDBManager extends DBManager{

    /**
     * 保存频道数据
     * @param datasBeens
     */
    public static void addFiberHomeChannel(Context context, List<FiberHomeChannelData> datasBeens) {
        try {
            SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
            synchronized (db) {
                refreshTable(db, TableKey.TABLE_FIBERHOME_CHANNEL);
                ContentValues values = new ContentValues();
                for (FiberHomeChannelData datasBean : datasBeens) {
                    values.put(TableKey.CHANNELID, datasBean.getChannelID());
                    values.put(TableKey.CHANNELNAME, datasBean.getChannelName());
                    values.put(TableKey.USERCHANNELID, datasBean.getUserChannelID());
                    values.put(TableKey.CHANNELURL, datasBean.getChannelURL());
                    values.put(TableKey.TIMESHIFT, datasBean.getTimeShift());
                    values.put(TableKey.CHANNELSDP, datasBean.getChannelSDP());
                    values.put(TableKey.TIMESHIFTURL, datasBean.getTimeShiftURL());
                    values.put(TableKey.CHANNELLOGURL, datasBean.getChannelLogURL());
                    values.put(TableKey.CHANNELLOGOURL, datasBean.getChannelLogoURL());
                    values.put(TableKey.POSITIONX, datasBean.getPositionX());
                    values.put(TableKey.POSITIONY, datasBean.getPositionY());
                    values.put(TableKey.BEGINTIME, datasBean.getBeginTime());
                    values.put(TableKey.INTERVAL, datasBean.getInterval());
                    values.put(TableKey.LASTING, datasBean.getLasting());
                    values.put(TableKey.CHANNELTYPE, datasBean.getChannelType());
                    values.put(TableKey.CHANNELPURCHASED, datasBean.getChannelPurchased());
                    values.put(TableKey.NATIVEDATA, datasBean.getNativeData());
                    db.insert(TableKey.TABLE_FIBERHOME_CHANNEL, null, values);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所对应的数据
     * @return
     */
    public static List<FiberHomeChannelData> getFiberHomeChannel(Context context) {
        Cursor cursor = null;
        List<FiberHomeChannelData> channels = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
        try {
            synchronized (db) {
                String sql = "SELECT * FROM "+ TableKey.TABLE_FIBERHOME_CHANNEL;
                cursor = db.rawQuery(sql,null);
                while (cursor.moveToNext()){
                    FiberHomeChannelData channel = new FiberHomeChannelData();
                    channel.setChannelID(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELID)));
                    channel.setChannelName(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELNAME)));
                    channel.setUserChannelID(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.USERCHANNELID)));
                    channel.setChannelURL(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELURL)));
                    channel.setTimeShift(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TIMESHIFT)));
                    channel.setChannelSDP(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELSDP)));
                    channel.setTimeShiftURL(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TIMESHIFTURL)));
                    channel.setChannelLogURL(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELLOGURL)));
                    channel.setChannelLogoURL(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELLOGOURL)));
                    channel.setPositionX(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.POSITIONX)));
                    channel.setPositionY(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.POSITIONY)));
                    channel.setBeginTime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BEGINTIME)));
                    channel.setInterval(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.INTERVAL)));
                    channel.setChannelType(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELTYPE)));
                    channel.setLasting(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.LASTING)));
                    channel.setChannelPurchased(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELPURCHASED)));
                    channel.setNativeData(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.NATIVEDATA)));
                    channels.add(channel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(cursor!=null) {
                cursor.close();
                cursor=null;
            }
        }
        return channels;
    }

    /**
     * 通过 key-value 获取所对应的数据
     * @return
     */
    public static FiberHomeChannelData getFiberHomeChannelBykey(Context context, String key, String value) {
        Cursor cursor = null;
        FiberHomeChannelData channel = new FiberHomeChannelData();
        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
        try {
            synchronized (db) {
                String sql = "SELECT * FROM "+ TableKey.TABLE_FIBERHOME_CHANNEL + " WHERE " + key + " = ?";
                cursor = db.rawQuery(sql, new String[]{value});
                if (cursor.moveToFirst()){
                    channel.setChannelID(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELID)));
                    channel.setChannelName(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELNAME)));
                    channel.setUserChannelID(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.USERCHANNELID)));
                    channel.setChannelURL(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELURL)));
                    channel.setTimeShift(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TIMESHIFT)));
                    channel.setChannelSDP(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELSDP)));
                    channel.setTimeShiftURL(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TIMESHIFTURL)));
                    channel.setChannelLogURL(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELLOGURL)));
                    channel.setChannelLogoURL(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELLOGOURL)));
                    channel.setPositionX(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.POSITIONX)));
                    channel.setPositionY(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.POSITIONY)));
                    channel.setBeginTime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BEGINTIME)));
                    channel.setInterval(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.INTERVAL)));
                    channel.setChannelType(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELTYPE)));
                    channel.setLasting(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.LASTING)));
                    channel.setChannelPurchased(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELPURCHASED)));
                    channel.setNativeData(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.NATIVEDATA)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(cursor!=null) {
                cursor.close();
                cursor=null;
            }
        }
        return channel;
    }

    /**
     * 获取所对应的数据
     * @return
     */
    public static Map<String,String> getFiberHomeChannelForMap(Context context) {
        Cursor cursor = null;
        Map<String,String> map = new ArrayMap<String,String>();
        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
        try {
            synchronized (db) {
                String sql = "SELECT * FROM "+ TableKey.TABLE_FIBERHOME_CHANNEL;
                cursor = db.rawQuery(sql,null);
                while (cursor.moveToNext()){
                    String mixNo = cursor.getString(cursor.getColumnIndexOrThrow(TableKey.USERCHANNELID));
                    String nativeData = cursor.getString(cursor.getColumnIndexOrThrow(TableKey.NATIVEDATA));
                    map.put(mixNo, nativeData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(cursor!=null) {
                cursor.close();
                cursor=null;
            }
        }
        return map;
    }

//    /**
//     * 通过 key-value 获取所对应的数据
//     * @return
//     */
//    public static String getValueBykey(Context context, String key, String value) {
//        Cursor cursor = null;
//        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
//        try {
//            synchronized (db) {
//                String sql = "SELECT * FROM "+ TableKey.TABLE_FIBERHOME_CHANNEL + " WHERE " + key + " = ?";
//                cursor = db.rawQuery(sql, new String[]{value});
//                if (cursor.moveToFirst()){
//                    return cursor.getString(cursor.getColumnIndexOrThrow(TableKey.NATIVEDATA));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            if(cursor!=null) {
//                cursor.close();
//                cursor=null;
//            }
//        }
//        return "";
//    }
}
