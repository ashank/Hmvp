package com.funhotel.tvllibrary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.funhotel.tvllibrary.application.ColumnModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ColumnManager
 * @Description: 频道分类
 * @author: Zhang Yetao
 * @data: 2016/10/20 17:02
 */
public class ColumnManager extends DBManager {
    /**
     * @param context
     * @param stbId      用户帐号
     * @param modelLists 分类列表model集合
     */
    public static void addColumn(Context context, String stbId, List<ColumnModel> modelLists) {
        try {
            SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
            synchronized (db) {
                if (TextUtils.isEmpty(stbId)) {
                    return;
                }
                refreshTable(db, TableKey.TABLE_COLUMN);
                ContentValues values = new ContentValues();
                for (ColumnModel model : modelLists) {
                    values.put(TableKey.COLUMNCODE, model.getColumncode());
                    values.put(TableKey.COLUMNTYPE, model.getColumntype());
                    values.put(TableKey.HASPOSTER, model.getHasposter());
                    values.put(TableKey.CUSTOMPRICE, model.getCustomprice());
                    values.put(TableKey.STATUS, model.getStatus());
                    values.put(TableKey.BOCODE, model.getBocode());
                    values.put(TableKey.POSTERFILELIST, model.getPosterfilelist());
                    values.put(TableKey.DESCRIPTION, model.getDescription());
                    values.put(TableKey.ADVERTISED, model.getAdvertised());
                    values.put(TableKey.SHORTDESC, model.getShortdesc());
                    values.put(TableKey.PROGRAMNAME, model.getProgramname());
                    values.put(TableKey.NORMALPOSTER, model.getNormalposter());
                    values.put(TableKey.SMALLPOSTER, model.getSmallposter());
                    values.put(TableKey.BIGPOSTER, model.getBigposter());
                    values.put(TableKey.ICONPOSTER, model.getIconposter());
                    values.put(TableKey.TITLEPOSTER, model.getTitleposter());
                    values.put(TableKey.ADVERTISEMENTPOSTER, model.getAdvertisementposter());
                    values.put(TableKey.SKETCHPOSTER, model.getSketchposter());
                    values.put(TableKey.BGPOSTER, model.getBgposter());
                    values.put(TableKey.OTHERPOSTER1, model.getOtherposter1());
                    values.put(TableKey.OTHERPOSTER2, model.getOtherposter2());
                    values.put(TableKey.OTHERPOSTER3, model.getOtherposter3());
                    values.put(TableKey.OTHERPOSTER4, model.getOtherposter4());
                    values.put(TableKey.ISHIDDEN, model.getIshidden());
                    values.put(TableKey.VODCOUNT, model.getVodcount());
                    values.put(TableKey.CHANNELCODE, model.getChannelcode());

                    values.put(TableKey.COLUMNNAME, model.getColumnname());
                    values.put(TableKey.PARENTCODE, model.getParentcode());
                    values.put(TableKey.SUBEXIST, model.getSubexist());
                    values.put(TableKey.TELECOMCODE, model.getTelecomcode());
                    values.put(TableKey.MEDIACODE, model.getMediacode());
                    values.put(TableKey.UPDATETIME, model.getUpdatetime());
                    values.put(TableKey.CREATETIME, model.getCreatetime());
                    values.put(TableKey.SORTNUM, model.getSortnum());
                    values.put(TableKey.SORTNAME, model.getSortname());

                    values.put(TableKey.STB_ID, stbId);
                    db.insert(TableKey.TABLE_COLUMN, null, values);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ColumnModel> getColumn(Context context,String stbId){
        Cursor cursor = null;
        List<ColumnModel> columnModels = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
        try {
            synchronized (db) {
                String sql = "SELECT * FROM "+ TableKey.TABLE_COLUMN + " WHERE "+ TableKey.STB_ID + " = ?";
                cursor = db.rawQuery(sql,new String[]{stbId});
                while (cursor.moveToNext()){
                    ColumnModel columnModel = new ColumnModel();
                    columnModel.setColumncode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.COLUMNCODE)));
                    columnModel.setColumntype(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.COLUMNTYPE)));
                    columnModel.setHasposter(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.HASPOSTER)));
                    columnModel.setCustomprice(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.CUSTOMPRICE)));
                    columnModel.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.STATUS)));
                    columnModel.setBocode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BOCODE)));
                    columnModel.setPosterfilelist(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.POSTERFILELIST)));
                    columnModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DESCRIPTION)));
                    columnModel.setAdvertised(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ADVERTISED)));
                    columnModel.setShortdesc(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SHORTDESC)));
                    columnModel.setProgramname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMNAME)));
                    columnModel.setNormalposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.NORMALPOSTER)));
                    columnModel.setSmallposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SMALLPOSTER)));
                    columnModel.setBigposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BIGPOSTER)));
                    columnModel.setIconposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ICONPOSTER)));
                    columnModel.setTitleposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TITLEPOSTER)));
                    columnModel.setAdvertisementposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ADVERTISEMENTPOSTER)));
                    columnModel.setSketchposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SKETCHPOSTER)));
                    columnModel.setBgposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BGPOSTER)));
                    columnModel.setOtherposter1(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.OTHERPOSTER1)));
                    columnModel.setOtherposter2(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.OTHERPOSTER2)));
                    columnModel.setOtherposter3(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.OTHERPOSTER3)));
                    columnModel.setOtherposter4(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.OTHERPOSTER4)));
                    columnModel.setIshidden(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISHIDDEN)));
                    columnModel.setVodcount(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.VODCOUNT)));
                    columnModel.setChannelcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELCODE)));

                    columnModel.setColumnname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.COLUMNNAME)));
                    columnModel.setParentcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PARENTCODE)));
                    columnModel.setSubexist(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SUBEXIST)));
                    columnModel.setTelecomcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TELECOMCODE)));
                    columnModel.setMediacode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.MEDIACODE)));
                    columnModel.setUpdatetime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.UPDATETIME)));
                    columnModel.setCreatetime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CREATETIME)));
                    columnModel.setSortnum(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SORTNUM)));
                    columnModel.setSortname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SORTNAME)));
                    columnModels.add(columnModel);
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
        return columnModels;
    }

    /**
     *  根据字段查找对应栏目的model
     * @param context
     * @param stbId
     * @param key
     * @param value
     * @return ColumnModel
     */
    public static ColumnModel getColumnData(Context context,String stbId,String key,String value){
        Cursor cursor = null;
        ColumnModel columnModel = new ColumnModel();
        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
        try {
            synchronized (db) {
                String sql = "SELECT * FROM "+ TableKey.TABLE_COLUMN + " WHERE "+ TableKey.STB_ID + " = ? and " + key + " = ?";
                cursor = db.rawQuery(sql,new String[]{stbId, value});
                while (cursor.moveToNext()){
                    columnModel.setColumncode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELCODE)));
                    columnModel.setColumntype(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.COLUMNTYPE)));
                    columnModel.setHasposter(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.HASPOSTER)));
                    columnModel.setCustomprice(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.CUSTOMPRICE)));
                    columnModel.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.STATUS)));
                    columnModel.setBocode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BOCODE)));
                    columnModel.setPosterfilelist(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.POSTERFILELIST)));
                    columnModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DESCRIPTION)));
                    columnModel.setAdvertised(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ADVERTISED)));
                    columnModel.setShortdesc(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SHORTDESC)));
                    columnModel.setProgramname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMNAME)));
                    columnModel.setNormalposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.NORMALPOSTER)));
                    columnModel.setSmallposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SMALLPOSTER)));
                    columnModel.setBigposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BIGPOSTER)));
                    columnModel.setIconposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ICONPOSTER)));
                    columnModel.setTitleposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TITLEPOSTER)));
                    columnModel.setAdvertisementposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ADVERTISEMENTPOSTER)));
                    columnModel.setSketchposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SKETCHPOSTER)));
                    columnModel.setBgposter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BGPOSTER)));
                    columnModel.setOtherposter1(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.OTHERPOSTER1)));
                    columnModel.setOtherposter2(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.OTHERPOSTER2)));
                    columnModel.setOtherposter3(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.OTHERPOSTER3)));
                    columnModel.setOtherposter4(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.OTHERPOSTER4)));
                    columnModel.setIshidden(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISHIDDEN)));
                    columnModel.setVodcount(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.VODCOUNT)));
                    columnModel.setChannelcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELCODE)));

                    columnModel.setColumnname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.COLUMNNAME)));
                    columnModel.setParentcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PARENTCODE)));
                    columnModel.setSubexist(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SUBEXIST)));
                    columnModel.setTelecomcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TELECOMCODE)));
                    columnModel.setMediacode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.MEDIACODE)));
                    columnModel.setUpdatetime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.UPDATETIME)));
                    columnModel.setCreatetime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CREATETIME)));
                    columnModel.setSortnum(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SORTNUM)));
                    columnModel.setSortname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SORTNAME)));
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
        return columnModel;
    }
}
