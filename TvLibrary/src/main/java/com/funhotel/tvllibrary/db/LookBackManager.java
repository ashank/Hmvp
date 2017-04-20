package com.funhotel.tvllibrary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.funhotel.tvllibrary.application.LookBackModel;
import com.funhotel.tvllibrary.utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: LookBackManager
 * @Description: 回看列表
 * @author: Zhang Yetao
 * @data: 2016/10/20 17:01
 */
public class LookBackManager extends DBManager {
    /**
     *
     * @param context
     * @param stbId 用户帐号
     * @param modelLists 回看列表数据集
     */
    public static void addLookBack(Context context, String stbId, List<LookBackModel> modelLists){
        try {
            SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
            synchronized (db) {
                if (TextUtils.isEmpty(stbId)) {
                    return;
                }
                refreshTable(db, TableKey.TABLE_LOOKBACK);
                ContentValues values = new ContentValues();
                for (LookBackModel model : modelLists) {
                    values.put(TableKey.PREVUEID, model.getPrevueid());
                    values.put(TableKey.PREVUECODE, model.getPrevuecode());
                    values.put(TableKey.PREVUENAME, model.getPrevuename());
                    values.put(TableKey.BOCODE, model.getBocode());
                    values.put(TableKey.TELECOMCODE, model.getTelecomcode());
                    values.put(TableKey.MEDIACODE, model.getMediacode());
                    values.put(TableKey.CHANNELCODE, model.getChannelcode());
                    values.put(TableKey.SYSTEMRECORDENABLE, model.getSystemrecordenable());
                    values.put(TableKey.PRIVATERECORDENABLE, model.getPrivaterecordenable());
                    values.put(TableKey.ISHOT, model.getIshot());
                    values.put(TableKey.ISARCHIVE, model.getIsarchive());
                    values.put(TableKey.HASPRIVATERECORD, model.getHasprivaterecord());
                    values.put(TableKey.BEGINTIME, model.getBegintime());
                    values.put(TableKey.ENDTIME, model.getEndtime());
                    values.put(TableKey.UTCBEGINTIME, model.getUtcbegintime());
                    values.put(TableKey.UTCENDTIME, model.getUtcendtime());
                    values.put(TableKey.SAVEDAYS, model.getSavedays());
                    values.put(TableKey.SERIESHEADID, model.getSeriesheadid());
                    values.put(TableKey.SERIESVOLUME, model.getSeriesvolume());
                    values.put(TableKey.SERIESNUM, model.getSeriesnum());
                    values.put(TableKey.SERIESNAME, model.getSeriesname());
                    values.put(TableKey.SEARCHKEY, model.getSearchkey());
                    values.put(TableKey.DIRECTOR, model.getDirector());
                    values.put(TableKey.DIRECTORSEARCHKEY, model.getDirectorsearchkey());
                    values.put(TableKey.ACTOR, model.getActor());
                    values.put(TableKey.ACTORSEARCHKEY, model.getActorsearchkey());
                    values.put(TableKey.MEDIASERVICES, model.getMediaservices());
                    values.put(TableKey.DESCRIPTION, model.getDescription());
                    values.put(TableKey.CREATETIME, model.getCreatetime());
                    values.put(TableKey.RELEASEDATE, model.getReleasedate());
                    values.put(TableKey.WRITER, model.getWriter());
                    values.put(TableKey.DETAILDESCRIBED, model.getDetaildescribed());
                    values.put(TableKey.ISTIMESHIFT, model.getIstimeshift());
                    values.put(TableKey.ISARCHIVEMODE, model.getIsarchivemode());
                    values.put(TableKey.ISPROTECTION, model.getIsprotection());
                    values.put(TableKey.CLOSEDCAPTION, model.getClosedcaption());
                    values.put(TableKey.ARCHIVEMODE, model.getArchivemode());
                    values.put(TableKey.COPYPROTECTION, model.getCopyprotection());
                    values.put(TableKey.RATINGID, model.getRatingid());
                    values.put(TableKey.LANGUAGE, model.getLanguage());
                    values.put(TableKey.AUDIOLANGS, model.getAudiolangs());
                    values.put(TableKey.RELEASEYEAR, model.getReleaseyear());
                    values.put(TableKey.PLAYTYPE, model.getPlaytype());
                    values.put(TableKey.PROGRAMID, model.getProgramid());
                    values.put(TableKey.SUBTITLES, model.getSubtitles());
                    values.put(TableKey.PROGRAMTYPE, model.getProgramtype());
                    values.put(TableKey.PROGRAMSOURCE, model.getProgramsource());
                    values.put(TableKey.DOLBY, model.getDolby());
                    values.put(TableKey.TFFLAG, model.getTfflag());
                    values.put(TableKey.PREMIEREORFINALE, model.getPremiereorfinale());
                    values.put(TableKey.STARRATING, model.getStarrating());
                    values.put(TableKey.PROGRAMDESCRIPTION, model.getProgramdescription());
                    values.put(TableKey.PROGRAMLANGUAGE, model.getProgramlanguage());
                    values.put(TableKey.BITRATE, model.getBitrate());
                    values.put(TableKey.GENRE, model.getGenre());
                    values.put(TableKey.SUBGENRE, model.getSubgenre());
                    values.put(TableKey.EPISODETITLE, model.getEpisodetitle());
                    values.put(TableKey.PARENTALADVISORY, model.getParentaladvisory());
                    values.put(TableKey.POSTERFILELIST, model.getPosterfilelist());
                    values.put(TableKey.RECORDCODE, model.getRecordcode());
                    values.put(TableKey.CDNCHANNELCODE, model.getCdnchannelcode());
                    values.put(TableKey.STATUS, model.getStatus());
                    values.put(TableKey.VALIDTIME, model.getValidtime());
                    values.put(TableKey.RECORDDEFINITION, model.getRecorddefinition());
                    values.put(TableKey.RECORDTELECOMCODE, model.getRecordtelecomcode());
                    values.put(TableKey.RECORDMEDIACODE, model.getRecordmediacode());

                    values.put(TableKey.TIMESHIFTMODE, model.getTimeshiftmode());
                    values.put(TableKey.AUDIOLANG, model.getAudiolang());
                    values.put(TableKey.SUBTITLELANG, model.getSubtitlelang());
                    values.put(TableKey.TVODMODE, model.getTvodmode());
                    values.put(TableKey.CATALOGID, model.getCatalogid());

                    values.put(TableKey.STB_ID, stbId);
                    db.insert(TableKey.TABLE_LOOKBACK, null, values);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param context
     * @param stbId
     * @return 回看数据集
     */
    public static List<LookBackModel> getLookBack(Context context,String stbId){
        Cursor cursor = null;
        List<LookBackModel> lookBackModels = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
        try {
            synchronized (db) {
                String sql = "SELECT * FROM "+ TableKey.TABLE_LOOKBACK + " WHERE "+ TableKey.STB_ID + " = ?";
                cursor = db.rawQuery(sql,new String[]{stbId});
                while (cursor.moveToNext()){
                    LookBackModel lookBackModel = new LookBackModel();
                    lookBackModel.setPrevueid(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PREVUEID)));
                    lookBackModel.setPrevuecode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PREVUECODE)));
                    lookBackModel.setPrevuename(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PREVUENAME)));
                    lookBackModel.setBocode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BOCODE)));
                    lookBackModel.setTelecomcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TELECOMCODE)));
                    lookBackModel.setMediacode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.MEDIACODE)));
                    lookBackModel.setChannelcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELCODE)));
                    lookBackModel.setSystemrecordenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SYSTEMRECORDENABLE)));
                    lookBackModel.setPrivaterecordenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PRIVATERECORDENABLE)));
                    lookBackModel.setIshot(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISHOT)));
                    lookBackModel.setIsarchive(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISARCHIVE)));
                    lookBackModel.setHasprivaterecord(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.HASPRIVATERECORD)));
                    lookBackModel.setBegintime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BEGINTIME)));
                    lookBackModel.setEndtime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ENDTIME)));
                    lookBackModel.setUtcbegintime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.UTCBEGINTIME)));
                    lookBackModel.setUtcendtime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.UTCENDTIME)));
                    lookBackModel.setSavedays(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SAVEDAYS)));
                    lookBackModel.setSeriesheadid(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SERIESHEADID)));
                    lookBackModel.setSeriesvolume(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SERIESVOLUME)));
                    lookBackModel.setSeriesnum(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SERIESNUM)));
                    lookBackModel.setSeriesname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SERIESNAME)));
                    lookBackModel.setSearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SEARCHKEY)));
                    lookBackModel.setDirector(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DIRECTOR)));
                    lookBackModel.setDirectorsearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DIRECTORSEARCHKEY)));
                    lookBackModel.setActor(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ACTOR)));
                    lookBackModel.setActorsearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ACTORSEARCHKEY)));
                    lookBackModel.setMediaservices(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.MEDIASERVICES)));
                    lookBackModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DESCRIPTION)));
                    lookBackModel.setCreatetime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CREATETIME)));
                    lookBackModel.setReleasedate(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RELEASEDATE)));
                    lookBackModel.setWriter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.WRITER)));
                    lookBackModel.setDetaildescribed(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DETAILDESCRIBED)));
                    lookBackModel.setIstimeshift(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISTIMESHIFT)));
                    lookBackModel.setIsarchivemode(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISARCHIVEMODE)));
                    lookBackModel.setIsprotection(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISPROTECTION)));
                    lookBackModel.setClosedcaption(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.CLOSEDCAPTION)));
                    lookBackModel.setArchivemode(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ARCHIVEMODE)));
                    lookBackModel.setCopyprotection(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.COPYPROTECTION)));
                    lookBackModel.setRatingid(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.RATINGID)));
                    lookBackModel.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.LANGUAGE)));
                    lookBackModel.setAudiolangs(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.AUDIOLANGS)));
                    lookBackModel.setReleaseyear(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RELEASEYEAR)));
                    lookBackModel.setPlaytype(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PLAYTYPE)));
                    lookBackModel.setProgramid(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMID)));
                    lookBackModel.setSubtitles(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBTITLES)));
                    lookBackModel.setProgramtype(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PROGRAMTYPE)));
                    lookBackModel.setProgramsource(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMSOURCE)));
                    lookBackModel.setDolby(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DOLBY)));
                    lookBackModel.setTfflag(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TFFLAG)));
                    lookBackModel.setPremiereorfinale(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PREMIEREORFINALE)));
                    lookBackModel.setStarrating(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.STARRATING)));
                    lookBackModel.setProgramdescription(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMDESCRIPTION)));
                    lookBackModel.setProgramlanguage(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMLANGUAGE)));
                    lookBackModel.setBitrate(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.BITRATE)));
                    lookBackModel.setGenre(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.GENRE)));
                    lookBackModel.setSubgenre(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBGENRE)));
                    lookBackModel.setEpisodetitle(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.EPISODETITLE)));
                    lookBackModel.setParentaladvisory(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PARENTALADVISORY)));
                    lookBackModel.setPosterfilelist(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.POSTERFILELIST)));
                    lookBackModel.setRecordcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RECORDCODE)));
                    lookBackModel.setCdnchannelcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CDNCHANNELCODE)));
                    lookBackModel.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.STATUS)));
                    lookBackModel.setValidtime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.VALIDTIME)));
                    lookBackModel.setRecorddefinition(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.RECORDDEFINITION)));
                    lookBackModel.setRecordtelecomcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RECORDTELECOMCODE)));
                    lookBackModel.setRecordmediacode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RECORDMEDIACODE)));

                    lookBackModel.setTimeshiftmode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TIMESHIFTMODE)));
                    lookBackModel.setAudiolang(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.AUDIOLANG)));
                    lookBackModel.setSubtitlelang(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBTITLELANG)));
                    lookBackModel.setTvodmode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TVODMODE)));
                    lookBackModel.setCatalogid(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CATALOGID)));
                    lookBackModels.add(lookBackModel);
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
        return lookBackModels;
    }

    /**
     *  根据字段获取回看列表的model
     * @param context
     * @param stbId
     * @param key
     * @param value
     * @return lookBackModel
     */
    public static List<LookBackModel> getLookBackData(Context context,String stbId,String key,String value){
        Cursor cursor = null;
        List<LookBackModel> lookBackModels = new ArrayList<>();

        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
        try {
            synchronized (db) {
                String sql = "SELECT * FROM "+ TableKey.TABLE_LOOKBACK + " WHERE "+ TableKey.STB_ID + " = ? and " + key + " = ?";
                cursor = db.rawQuery(sql,new String[]{stbId, value});
                while (cursor.moveToNext()){
                    LookBackModel lookBackModel = new LookBackModel();
                    lookBackModel.setPrevueid(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PREVUEID)));
                    lookBackModel.setPrevuecode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PREVUECODE)));
                    lookBackModel.setPrevuename(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PREVUENAME)));
                    lookBackModel.setBocode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BOCODE)));
                    lookBackModel.setTelecomcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TELECOMCODE)));
                    lookBackModel.setMediacode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.MEDIACODE)));
                    lookBackModel.setChannelcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELCODE)));
                    lookBackModel.setSystemrecordenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SYSTEMRECORDENABLE)));
                    lookBackModel.setPrivaterecordenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PRIVATERECORDENABLE)));
                    lookBackModel.setIshot(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISHOT)));
                    lookBackModel.setIsarchive(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISARCHIVE)));
                    lookBackModel.setHasprivaterecord(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.HASPRIVATERECORD)));
                    lookBackModel.setBegintime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BEGINTIME)));
                    lookBackModel.setEndtime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ENDTIME)));
                    lookBackModel.setUtcbegintime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.UTCBEGINTIME)));
                    lookBackModel.setUtcendtime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.UTCENDTIME)));
                    lookBackModel.setSavedays(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SAVEDAYS)));
                    lookBackModel.setSeriesheadid(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SERIESHEADID)));
                    lookBackModel.setSeriesvolume(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SERIESVOLUME)));
                    lookBackModel.setSeriesnum(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SERIESNUM)));
                    lookBackModel.setSeriesname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SERIESNAME)));
                    lookBackModel.setSearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SEARCHKEY)));
                    lookBackModel.setDirector(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DIRECTOR)));
                    lookBackModel.setDirectorsearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DIRECTORSEARCHKEY)));
                    lookBackModel.setActor(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ACTOR)));
                    lookBackModel.setActorsearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ACTORSEARCHKEY)));
                    lookBackModel.setMediaservices(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.MEDIASERVICES)));
                    lookBackModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DESCRIPTION)));
                    lookBackModel.setCreatetime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CREATETIME)));
                    lookBackModel.setReleasedate(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RELEASEDATE)));
                    lookBackModel.setWriter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.WRITER)));
                    lookBackModel.setDetaildescribed(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DETAILDESCRIBED)));
                    lookBackModel.setIstimeshift(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISTIMESHIFT)));
                    lookBackModel.setIsarchivemode(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISARCHIVEMODE)));
                    lookBackModel.setIsprotection(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISPROTECTION)));
                    lookBackModel.setClosedcaption(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.CLOSEDCAPTION)));
                    lookBackModel.setArchivemode(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ARCHIVEMODE)));
                    lookBackModel.setCopyprotection(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.COPYPROTECTION)));
                    lookBackModel.setRatingid(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.RATINGID)));
                    lookBackModel.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.LANGUAGE)));
                    lookBackModel.setAudiolangs(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.AUDIOLANGS)));
                    lookBackModel.setReleaseyear(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RELEASEYEAR)));
                    lookBackModel.setPlaytype(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PLAYTYPE)));
                    lookBackModel.setProgramid(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMID)));
                    lookBackModel.setSubtitles(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBTITLES)));
                    lookBackModel.setProgramtype(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PROGRAMTYPE)));
                    lookBackModel.setProgramsource(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMSOURCE)));
                    lookBackModel.setDolby(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DOLBY)));
                    lookBackModel.setTfflag(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TFFLAG)));
                    lookBackModel.setPremiereorfinale(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PREMIEREORFINALE)));
                    lookBackModel.setStarrating(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.STARRATING)));
                    lookBackModel.setProgramdescription(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMDESCRIPTION)));
                    lookBackModel.setProgramlanguage(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMLANGUAGE)));
                    lookBackModel.setBitrate(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.BITRATE)));
                    lookBackModel.setGenre(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.GENRE)));
                    lookBackModel.setSubgenre(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBGENRE)));
                    lookBackModel.setEpisodetitle(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.EPISODETITLE)));
                    lookBackModel.setParentaladvisory(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PARENTALADVISORY)));
                    lookBackModel.setPosterfilelist(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.POSTERFILELIST)));
                    lookBackModel.setRecordcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RECORDCODE)));
                    lookBackModel.setCdnchannelcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CDNCHANNELCODE)));
                    lookBackModel.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.STATUS)));
                    lookBackModel.setValidtime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.VALIDTIME)));
                    lookBackModel.setRecorddefinition(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.RECORDDEFINITION)));
                    lookBackModel.setRecordtelecomcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RECORDTELECOMCODE)));
                    lookBackModel.setRecordmediacode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RECORDMEDIACODE)));

                    lookBackModel.setTimeshiftmode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TIMESHIFTMODE)));
                    lookBackModel.setAudiolang(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.AUDIOLANG)));
                    lookBackModel.setSubtitlelang(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBTITLELANG)));
                    lookBackModel.setTvodmode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TVODMODE)));
                    lookBackModel.setCatalogid(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CATALOGID)));
                    lookBackModels.add(lookBackModel);
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
        return lookBackModels;
    }

    /**
     *  根据当前时间获取正在播放数据和下一条数据
     * @param context
     * @param stbId
     * @param currentTime
     * @return lookBackModel
     */
    public static List<LookBackModel> getCurrentNextLookBack(Context context,String stbId,String channelCode, String currentTime){
        String starTime="";//开始时间
        String endTime="";//结束时间
//        String currentTime = "2016.10.20 18:10:00";//当前时间
        boolean isFind = false;//是否找到数据
        Cursor cursor = null;
        List<LookBackModel> lookBackModels = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
        try {
            synchronized (db) {
                String sql = "SELECT * FROM "+ TableKey.TABLE_LOOKBACK + " WHERE "+ TableKey.STB_ID + " = ? and "
                        + TableKey.CHANNELCODE + " = ?";
                cursor = db.rawQuery(sql,new String[]{stbId, channelCode});
                while (cursor.moveToNext()){
                    starTime = cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BEGINTIME));
                    endTime = cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ENDTIME));
                    if(isFind || (DateTimeUtil.getTimeDelta12(currentTime, starTime)>=0 && DateTimeUtil.getTimeDelta12(currentTime, endTime)<0)){
                        LookBackModel lookBackModel = new LookBackModel();
                        lookBackModel.setPrevueid(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PREVUEID)));
                        lookBackModel.setPrevuecode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PREVUECODE)));
                        lookBackModel.setPrevuename(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PREVUENAME)));
                        lookBackModel.setBocode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BOCODE)));
                        lookBackModel.setTelecomcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TELECOMCODE)));
                        lookBackModel.setMediacode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.MEDIACODE)));
                        lookBackModel.setChannelcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELCODE)));
                        lookBackModel.setSystemrecordenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SYSTEMRECORDENABLE)));
                        lookBackModel.setPrivaterecordenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PRIVATERECORDENABLE)));
                        lookBackModel.setIshot(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISHOT)));
                        lookBackModel.setIsarchive(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISARCHIVE)));
                        lookBackModel.setHasprivaterecord(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.HASPRIVATERECORD)));
                        lookBackModel.setBegintime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BEGINTIME)));
                        lookBackModel.setEndtime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ENDTIME)));
                        lookBackModel.setUtcbegintime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.UTCBEGINTIME)));
                        lookBackModel.setUtcendtime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.UTCENDTIME)));
                        lookBackModel.setSavedays(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SAVEDAYS)));
                        lookBackModel.setSeriesheadid(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SERIESHEADID)));
                        lookBackModel.setSeriesvolume(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SERIESVOLUME)));
                        lookBackModel.setSeriesnum(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SERIESNUM)));
                        lookBackModel.setSeriesname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SERIESNAME)));
                        lookBackModel.setSearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SEARCHKEY)));
                        lookBackModel.setDirector(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DIRECTOR)));
                        lookBackModel.setDirectorsearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DIRECTORSEARCHKEY)));
                        lookBackModel.setActor(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ACTOR)));
                        lookBackModel.setActorsearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ACTORSEARCHKEY)));
                        lookBackModel.setMediaservices(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.MEDIASERVICES)));
                        lookBackModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DESCRIPTION)));
                        lookBackModel.setCreatetime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CREATETIME)));
                        lookBackModel.setReleasedate(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RELEASEDATE)));
                        lookBackModel.setWriter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.WRITER)));
                        lookBackModel.setDetaildescribed(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DETAILDESCRIBED)));
                        lookBackModel.setIstimeshift(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISTIMESHIFT)));
                        lookBackModel.setIsarchivemode(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISARCHIVEMODE)));
                        lookBackModel.setIsprotection(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISPROTECTION)));
                        lookBackModel.setClosedcaption(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.CLOSEDCAPTION)));
                        lookBackModel.setArchivemode(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ARCHIVEMODE)));
                        lookBackModel.setCopyprotection(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.COPYPROTECTION)));
                        lookBackModel.setRatingid(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.RATINGID)));
                        lookBackModel.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.LANGUAGE)));
                        lookBackModel.setAudiolangs(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.AUDIOLANGS)));
                        lookBackModel.setReleaseyear(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RELEASEYEAR)));
                        lookBackModel.setPlaytype(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PLAYTYPE)));
                        lookBackModel.setProgramid(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMID)));
                        lookBackModel.setSubtitles(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBTITLES)));
                        lookBackModel.setProgramtype(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PROGRAMTYPE)));
                        lookBackModel.setProgramsource(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMSOURCE)));
                        lookBackModel.setDolby(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DOLBY)));
                        lookBackModel.setTfflag(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TFFLAG)));
                        lookBackModel.setPremiereorfinale(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PREMIEREORFINALE)));
                        lookBackModel.setStarrating(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.STARRATING)));
                        lookBackModel.setProgramdescription(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMDESCRIPTION)));
                        lookBackModel.setProgramlanguage(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMLANGUAGE)));
                        lookBackModel.setBitrate(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.BITRATE)));
                        lookBackModel.setGenre(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.GENRE)));
                        lookBackModel.setSubgenre(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBGENRE)));
                        lookBackModel.setEpisodetitle(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.EPISODETITLE)));
                        lookBackModel.setParentaladvisory(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PARENTALADVISORY)));
                        lookBackModel.setPosterfilelist(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.POSTERFILELIST)));
                        lookBackModel.setRecordcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RECORDCODE)));
                        lookBackModel.setCdnchannelcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CDNCHANNELCODE)));
                        lookBackModel.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.STATUS)));
                        lookBackModel.setValidtime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.VALIDTIME)));
                        lookBackModel.setRecorddefinition(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.RECORDDEFINITION)));
                        lookBackModel.setRecordtelecomcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RECORDTELECOMCODE)));
                        lookBackModel.setRecordmediacode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RECORDMEDIACODE)));
                        lookBackModel.setTimeshiftmode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TIMESHIFTMODE)));
                        lookBackModel.setAudiolang(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.AUDIOLANG)));
                        lookBackModel.setSubtitlelang(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBTITLELANG)));
                        lookBackModel.setTvodmode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TVODMODE)));
                        lookBackModel.setCatalogid(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CATALOGID)));
                        lookBackModels.add(lookBackModel);
                        if(isFind){
                            return lookBackModels;
                        }
                        isFind = true;
                    }
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
        return lookBackModels;
    }

    /**
     *  根据当前时间获取正在播放数据
     * @param context
     * @param stbId
     * @return lookBackModel
     */
    public static LookBackModel getCurrentLookBack(Context context,String stbId,String channelCode){
        String starTime="";//开始时间
        String endTime="";//结束时间
        String currentTime = DateTimeUtil.getCurrentTime();//当前时间
//        String currentTime = "2016.10.20 18:10:00";//当前时间
        Cursor cursor = null;
        LookBackModel lookBackModel = new LookBackModel();
        SQLiteDatabase db = SQLiteDatabaseManager.getSQLiteDatabaseManager(context).getSQLiteDatabase();
        try {
            synchronized (db) {
                String sql = "SELECT * FROM "+ TableKey.TABLE_LOOKBACK + " WHERE "+ TableKey.STB_ID + " = ? and "
                        + TableKey.CHANNELCODE + " = ?";
                cursor = db.rawQuery(sql,new String[]{stbId, channelCode});
                while (cursor.moveToNext()){
                    starTime = cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BEGINTIME));
                    endTime = cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ENDTIME));
                    if(DateTimeUtil.getTimeDelta12(currentTime, starTime)>=0 && DateTimeUtil.getTimeDelta12(currentTime, endTime)<0){
                        lookBackModel.setPrevueid(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PREVUEID)));
                        lookBackModel.setPrevuecode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PREVUECODE)));
                        lookBackModel.setPrevuename(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PREVUENAME)));
                        lookBackModel.setBocode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BOCODE)));
                        lookBackModel.setTelecomcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TELECOMCODE)));
                        lookBackModel.setMediacode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.MEDIACODE)));
                        lookBackModel.setChannelcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CHANNELCODE)));
                        lookBackModel.setSystemrecordenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SYSTEMRECORDENABLE)));
                        lookBackModel.setPrivaterecordenable(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PRIVATERECORDENABLE)));
                        lookBackModel.setIshot(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISHOT)));
                        lookBackModel.setIsarchive(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISARCHIVE)));
                        lookBackModel.setHasprivaterecord(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.HASPRIVATERECORD)));
                        lookBackModel.setBegintime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.BEGINTIME)));
                        lookBackModel.setEndtime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ENDTIME)));
                        lookBackModel.setUtcbegintime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.UTCBEGINTIME)));
                        lookBackModel.setUtcendtime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.UTCENDTIME)));
                        lookBackModel.setSavedays(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SAVEDAYS)));
                        lookBackModel.setSeriesheadid(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SERIESHEADID)));
                        lookBackModel.setSeriesvolume(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SERIESVOLUME)));
                        lookBackModel.setSeriesnum(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.SERIESNUM)));
                        lookBackModel.setSeriesname(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SERIESNAME)));
                        lookBackModel.setSearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SEARCHKEY)));
                        lookBackModel.setDirector(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DIRECTOR)));
                        lookBackModel.setDirectorsearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DIRECTORSEARCHKEY)));
                        lookBackModel.setActor(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ACTOR)));
                        lookBackModel.setActorsearchkey(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.ACTORSEARCHKEY)));
                        lookBackModel.setMediaservices(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.MEDIASERVICES)));
                        lookBackModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DESCRIPTION)));
                        lookBackModel.setCreatetime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CREATETIME)));
                        lookBackModel.setReleasedate(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RELEASEDATE)));
                        lookBackModel.setWriter(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.WRITER)));
                        lookBackModel.setDetaildescribed(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DETAILDESCRIBED)));
                        lookBackModel.setIstimeshift(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISTIMESHIFT)));
                        lookBackModel.setIsarchivemode(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISARCHIVEMODE)));
                        lookBackModel.setIsprotection(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ISPROTECTION)));
                        lookBackModel.setClosedcaption(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.CLOSEDCAPTION)));
                        lookBackModel.setArchivemode(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.ARCHIVEMODE)));
                        lookBackModel.setCopyprotection(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.COPYPROTECTION)));
                        lookBackModel.setRatingid(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.RATINGID)));
                        lookBackModel.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.LANGUAGE)));
                        lookBackModel.setAudiolangs(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.AUDIOLANGS)));
                        lookBackModel.setReleaseyear(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RELEASEYEAR)));
                        lookBackModel.setPlaytype(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PLAYTYPE)));
                        lookBackModel.setProgramid(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMID)));
                        lookBackModel.setSubtitles(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBTITLES)));
                        lookBackModel.setProgramtype(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.PROGRAMTYPE)));
                        lookBackModel.setProgramsource(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMSOURCE)));
                        lookBackModel.setDolby(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.DOLBY)));
                        lookBackModel.setTfflag(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TFFLAG)));
                        lookBackModel.setPremiereorfinale(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PREMIEREORFINALE)));
                        lookBackModel.setStarrating(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.STARRATING)));
                        lookBackModel.setProgramdescription(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMDESCRIPTION)));
                        lookBackModel.setProgramlanguage(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PROGRAMLANGUAGE)));
                        lookBackModel.setBitrate(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.BITRATE)));
                        lookBackModel.setGenre(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.GENRE)));
                        lookBackModel.setSubgenre(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBGENRE)));
                        lookBackModel.setEpisodetitle(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.EPISODETITLE)));
                        lookBackModel.setParentaladvisory(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.PARENTALADVISORY)));
                        lookBackModel.setPosterfilelist(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.POSTERFILELIST)));
                        lookBackModel.setRecordcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RECORDCODE)));
                        lookBackModel.setCdnchannelcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CDNCHANNELCODE)));
                        lookBackModel.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.STATUS)));
                        lookBackModel.setValidtime(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.VALIDTIME)));
                        lookBackModel.setRecorddefinition(cursor.getInt(cursor.getColumnIndexOrThrow(TableKey.RECORDDEFINITION)));
                        lookBackModel.setRecordtelecomcode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RECORDTELECOMCODE)));
                        lookBackModel.setRecordmediacode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.RECORDMEDIACODE)));
                        lookBackModel.setTimeshiftmode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TIMESHIFTMODE)));
                        lookBackModel.setAudiolang(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.AUDIOLANG)));
                        lookBackModel.setSubtitlelang(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.SUBTITLELANG)));
                        lookBackModel.setTvodmode(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.TVODMODE)));
                        lookBackModel.setCatalogid(cursor.getString(cursor.getColumnIndexOrThrow(TableKey.CATALOGID)));
                    }
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
        return lookBackModel;
    }
}
