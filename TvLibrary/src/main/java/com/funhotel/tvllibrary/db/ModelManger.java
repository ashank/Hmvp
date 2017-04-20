package com.funhotel.tvllibrary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.system.ErrnoException;
import android.text.TextUtils;

import com.funhotel.tvllibrary.application.BaseModel;
import com.funhotel.tvllibrary.utils.DebugUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：zhiyahan on 16/4/28 21:24
 *
 *
 *按钮 数据
 */
public class ModelManger extends DBManager{



    /**
     * 保存模板数据
     * @param datasBeens
     * @param stbId
     * @param parentId
     */
    public static void addModel(Context context, List<BaseModel.DatasBean> datasBeens, String stbId, int parentId){
        try{
            SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
            synchronized (db){
                if(TextUtils.isEmpty(stbId)){
                    return;
                }
                //先删除parentId数据
                deleteModelByParentId(db,stbId,parentId);
                DebugUtil.i("保存 parentId---》"+parentId);
                ContentValues values = new ContentValues();
                for(BaseModel.DatasBean datasBean : datasBeens){
                    values.put(TableKey.MODEL_ID,datasBean.getId());
                    values.put(TableKey.NAME,datasBean.getName());
                    values.put(TableKey.RESOURCENAME,datasBean.getResourceName());
                    values.put(TableKey.POSITION,datasBean.getPosition());
                    values.put(TableKey.TEXT,datasBean.getText());
                    values.put(TableKey.HREF,datasBean.getHref());
                    values.put(TableKey.BUTTON_NORMAL,datasBean.getBeforeImgUrl());
                    values.put(TableKey.BUTTON_SELECTED,datasBean.getBackImgUrl());
                    values.put(TableKey.MEDIAURL,datasBean.getMediaUrl());
                    values.put(TableKey.SHOWTYPE,datasBean.getShowType());
                    values.put(TableKey.SORT,datasBean.getSort());
                    values.put(TableKey.TYPE,datasBean.getType());
                    values.put(TableKey.TEMPLATE_ID,datasBean.getTemplateId());
                    values.put(TableKey.TEMPLATENAME,datasBean.getTemplateName());
                    values.put(TableKey.EXTENTION1,datasBean.getExtention1());
                    values.put(TableKey.EXTENTION2,datasBean.getExtention2());
                    values.put(TableKey.CLASS_NAME,datasBean.getCls());
                    values.put(TableKey.PACKAGE_NAME,datasBean.getPck());
                    values.put(TableKey.STB_ID,stbId);
                    values.put(TableKey.PARENT_ID,parentId);
                    db.insert(TableKey.TABLE_MODEL,null,values);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取parantId所对应的数据
     * @param stbId
     * @param parentId
     * @return
     */
    public static List<BaseModel.DatasBean> getModelByParentId(Context context, String stbId ,int parentId, String type){
        Cursor cursor = null;
        List<BaseModel.DatasBean> baseModels = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
        try {
            synchronized (db){
                DebugUtil.i("查询的 parentId---》"+parentId);
                if(!TextUtils.isEmpty(type)){
                    String sql = "select * from " + TableKey.TABLE_MODEL + " where "+TableKey.STB_ID + " = ? " +" and "+ TableKey.PARENT_ID + " = ? "
                            + " and "+ TableKey.TYPE + " = ? ";
                    String[] args = {stbId, String.valueOf(parentId), type};
                    cursor = db.rawQuery(sql,args);
                }else{
                    String sql = "select * from " + TableKey.TABLE_MODEL + " where "+TableKey.STB_ID + " = ? " +" and "+ TableKey.PARENT_ID + " = ?";
                    String[] args = {stbId,String.valueOf(parentId)};
                    cursor = db.rawQuery(sql,args);
                }
                while (cursor.moveToNext()){
                    BaseModel.DatasBean datasBean = new BaseModel.DatasBean();
                    datasBean.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.MODEL_ID)));//资源名
                    datasBean.setName(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.NAME)));//文字内容
                    datasBean.setResourceName(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RESOURCENAME)));
                    datasBean.setPosition(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.POSITION)));
                    datasBean.setText(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TEXT)));
                    datasBean.setHref(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.HREF)));
                    datasBean.setBeforeImgUrl(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BUTTON_NORMAL)));
                    datasBean.setBackImgUrl(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BUTTON_SELECTED)));
                    datasBean.setMediaUrl(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.MEDIAURL)));
                    datasBean.setShowType(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SHOWTYPE)));
                    datasBean.setSort(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SORT)));
                    datasBean.setType(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TYPE)));
                    datasBean.setTemplateId(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.TEMPLATE_ID)));
                    datasBean.setTemplateName(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TEMPLATENAME)));
                    datasBean.setExtention1(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.EXTENTION1)));
                    datasBean.setExtention2(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.EXTENTION2)));
                    datasBean.setCls(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CLASS_NAME)));
                    datasBean.setPck(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PACKAGE_NAME)));
                    baseModels.add(datasBean);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null) {
                cursor.close();
                cursor=null;
            }
        }
        return baseModels;
    }

    /**
     * 删除parentId模板
     * @param db
     * @param stbId
     * @param parentId
     */
    public static void deleteModelByParentId(SQLiteDatabase db, String stbId, int parentId){
        try{
            synchronized (db){
                db.delete(TableKey.TABLE_MODEL, TableKey.STB_ID + " = ? and " + TableKey.PARENT_ID + " =?", new String[]{stbId, String.valueOf(parentId)});
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 通过key获取value
     * @param context
     * @param stbId
     * @param parentId
     */
    public static String getValueByKey(Context context, String stbId ,int parentId, String key){
        String value = "";
        Cursor cursor = null;
        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
        try{
            synchronized (db) {
                String sql = "SELECT " + key + " FROM " + TableKey.TABLE_MODEL + " where " + TableKey.STB_ID + " = "+ stbId + " and "+ TableKey.PARENT_ID + " = "+ parentId;
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    value = cursor.getString(cursor.getColumnIndexOrThrow(key));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
                cursor = null;
            }
        }
        return value;
    }

}
