package com.funhotel.tvllibrary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.funhotel.tvllibrary.application.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/10/18.
 */

public class ChannelManager extends DBManager{

    /**
     * 保存频道数据
     * @param datasBeens
     * @param stbId
     */
    public static void addChannel(Context context, List<Channel> datasBeens, String stbId){
        try{
            SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
            synchronized (db){
                if(TextUtils.isEmpty(stbId)){
                    return;
                }
                refreshTable(db,TableKey.TABLE_CHANNEL);
                ContentValues values = new ContentValues();
                for(Channel datasBean : datasBeens){
                    if(dealWithMixNo(datasBean.getMixno())) {
                        values.put(TableKey.CHANNELCODE, datasBean.getChannelcode());
                        values.put(TableKey.CHANNELNAME, datasBean.getChannelname());
                        values.put(TableKey.SEARCHKEY, datasBean.getSearchkey());
                        values.put(TableKey.MIXNO, datasBean.getMixno());
                        values.put(TableKey.CHANNELTYPE, datasBean.getChanneltype());
                        values.put(TableKey.MEDIASERVICES, datasBean.getMediaservices());
                        values.put(TableKey.IPPVENABLE, datasBean.getIppvenable());
                        values.put(TableKey.LPVRENABLE, datasBean.getLpvrenable());
                        values.put(TableKey.PARENTCONTROLENABLE, datasBean.getParentcontrolenable());
                        values.put(TableKey.RATINGID, datasBean.getRatingid());
                        values.put(TableKey.SORTNUM, datasBean.getSortnum());
                        values.put(TableKey.BOCODE, datasBean.getBocode());
                        values.put(TableKey.TELECOMCODE, datasBean.getTelecomcode());
                        values.put(TableKey.MEDIACODE, datasBean.getMediacode());
                        values.put(TableKey.DESCRIPTION, datasBean.getDescription());
                        values.put(TableKey.COLUMNCODE, datasBean.getColumncode());
                        values.put(TableKey.COLUMNNAME, datasBean.getColumnname());
                        values.put(TableKey.FILENAME, datasBean.getFilename());
                        values.put(TableKey.SUBTITLELANG, datasBean.getSubtitlelang());
                        values.put(TableKey.AUDIOLANG, datasBean.getAudiolang());
                        values.put(TableKey.USERMIXNO, datasBean.getUsermixno());
                        values.put(TableKey.NPVRAVAILABLE, datasBean.getNpvravailable());
                        values.put(TableKey.TSAVAILABLE, datasBean.getTsavailable());
                        values.put(TableKey.TVAVAILABLE, datasBean.getTvavailable());
                        values.put(TableKey.TVODAVAILABLE, datasBean.getTvodavailable());
                        values.put(TableKey.ADVERTISECONTENT, datasBean.getAdvertisecontent());
                        values.put(TableKey.ISLOCALTIMESHIFT, datasBean.getIslocaltimeshift());
                        values.put(TableKey.BITRATE, datasBean.getBitrate());
                        values.put(TableKey.STB_ID, stbId);
                        db.insert(TableKey.TABLE_CHANNEL, null, values);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取parantId所对应的数据
     * @param stbId
     * @return
     */
    public static List<Channel> getChannel(Context context, String stbId) {
        Cursor cursor = null;
        List<Channel> channels = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
        try {
            synchronized (db) {
                String sql = "SELECT * FROM "+ TableKey.TABLE_CHANNEL + " WHERE "+ TableKey.STB_ID + " = ?";
                cursor = db.rawQuery(sql,new String[]{stbId});
                while (cursor.moveToNext()){
                    Channel channel = new Channel();
                    channel.setChannelcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELCODE)));
                    channel.setChannelname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELNAME)));
                    channel.setSearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SEARCHKEY)));
                    channel.setMixno(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.MIXNO)));
                    channel.setChanneltype(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.CHANNELTYPE)));
                    channel.setMediaservices(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.MEDIASERVICES)));
                    channel.setIppvenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.IPPVENABLE)));
                    channel.setLpvrenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.LPVRENABLE)));
                    channel.setParentcontrolenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PARENTCONTROLENABLE)));
                    channel.setRatingid(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RATINGID)));
                    channel.setSortnum(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SORTNUM)));
                    channel.setBocode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BOCODE)));
                    channel.setTelecomcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TELECOMCODE)));
                    channel.setMediacode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.MEDIACODE)));
                    channel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DESCRIPTION)));
                    channel.setColumncode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.COLUMNCODE)));
                    channel.setColumnname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.COLUMNNAME)));
                    channel.setFilename(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.FILENAME)));
                    channel.setAudiolang(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.AUDIOLANG)));
                    channel.setSubtitlelang(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBTITLELANG)));
                    channel.setUsermixno(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.USERMIXNO)));
                    channel.setNpvravailable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.NPVRAVAILABLE)));
                    channel.setTsavailable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.TSAVAILABLE)));
                    channel.setTvavailable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.TVAVAILABLE)));
                    channel.setTvodavailable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.TVODAVAILABLE)));
                    channel.setAdvertisecontent(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ADVERTISECONTENT)));
                    channel.setIslocaltimeshift(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISLOCALTIMESHIFT)));
                    channel.setBitrate(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.BITRATE)));
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

    public static  List<Channel> getChannelData(Context context,String stbId,String key,String value){
        Cursor cursor = null;
        List<Channel> channels = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
        try {
            synchronized (db) {
                String sql = "SELECT * FROM "+ TableKey.TABLE_CHANNEL + " WHERE "+ TableKey.STB_ID + " = ? and " + key + " = ?";
                cursor = db.rawQuery(sql,new String[]{stbId, value});
                while (cursor.moveToNext()){
                    Channel channel = new Channel();
                    channel.setChannelcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELCODE)));
                    channel.setChannelname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELNAME)));
                    channel.setSearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SEARCHKEY)));
                    channel.setMixno(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.MIXNO)));
                    channel.setChanneltype(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.CHANNELTYPE)));
                    channel.setMediaservices(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.MEDIASERVICES)));
                    channel.setIppvenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.IPPVENABLE)));
                    channel.setLpvrenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.LPVRENABLE)));
                    channel.setParentcontrolenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PARENTCONTROLENABLE)));
                    channel.setRatingid(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RATINGID)));
                    channel.setSortnum(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SORTNUM)));
                    channel.setBocode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BOCODE)));
                    channel.setTelecomcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TELECOMCODE)));
                    channel.setMediacode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.MEDIACODE)));
                    channel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DESCRIPTION)));
                    channel.setColumncode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.COLUMNCODE)));
                    channel.setColumnname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.COLUMNNAME)));
                    channel.setFilename(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.FILENAME)));
                    channel.setAudiolang(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.AUDIOLANG)));
                    channel.setSubtitlelang(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBTITLELANG)));
                    channel.setUsermixno(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.USERMIXNO)));
                    channel.setNpvravailable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.NPVRAVAILABLE)));
                    channel.setTsavailable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.TSAVAILABLE)));
                    channel.setTvavailable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.TVAVAILABLE)));
                    channel.setTvodavailable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.TVODAVAILABLE)));
                    channel.setAdvertisecontent(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ADVERTISECONTENT)));
                    channel.setIslocaltimeshift(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISLOCALTIMESHIFT)));
                    channel.setBitrate(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.BITRATE)));
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
     * 处理一些不需要的地方台
     */
    public static boolean dealWithMixNo(String mixNo){
        if(!TextUtils.isEmpty(mixNo)){
            try{
                if(Integer.valueOf(mixNo)==21 || Integer.valueOf(mixNo)==185 || (Integer.valueOf(mixNo)>=130 && Integer.valueOf(mixNo)<=137)
                        ||(Integer.valueOf(mixNo)>=140 && Integer.valueOf(mixNo)<=143)||(Integer.valueOf(mixNo)>=150 && Integer.valueOf(mixNo)<=153)
                        ||(Integer.valueOf(mixNo)>=160 && Integer.valueOf(mixNo)<=162)||(Integer.valueOf(mixNo)>=165 && Integer.valueOf(mixNo)<=167)
                        ||(Integer.valueOf(mixNo)>=170 && Integer.valueOf(mixNo)<=172)||(Integer.valueOf(mixNo)>=175 && Integer.valueOf(mixNo)<=177)
                        ||(Integer.valueOf(mixNo)>=180 && Integer.valueOf(mixNo)<=182)
                        ||(Integer.valueOf(mixNo)>=701 && Integer.valueOf(mixNo)<=706)||(Integer.valueOf(mixNo)>=778 && Integer.valueOf(mixNo)<=779)
                        ||(Integer.valueOf(mixNo)>=791 && Integer.valueOf(mixNo)<=792)||(Integer.valueOf(mixNo)>=811 && Integer.valueOf(mixNo)<=819)
                        ||(Integer.valueOf(mixNo)>=821 && Integer.valueOf(mixNo)<=823)||(Integer.valueOf(mixNo)>=851 && Integer.valueOf(mixNo)<=854)
                        ||Integer.valueOf(mixNo)==155||Integer.valueOf(mixNo)==156||Integer.valueOf(mixNo)==158 ||Integer.valueOf(mixNo)==868
                        ||Integer.valueOf(mixNo)==869||Integer.valueOf(mixNo)==889||Integer.valueOf(mixNo)==890 ||Integer.valueOf(mixNo)==999){
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }
}
