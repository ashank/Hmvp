package com.funhotel.tvllibrary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.funhotel.tvllibrary.utils.DebugUtil;

/**
 * @Title: DBHelper
 * @Description: 创建数据库
 * @author: LinWeiDong
 * @data: 2016/4/19 15:06
 */
public class DBHelper extends SQLiteOpenHelper {

    private static String dbName = "hebei.db";
    private static int version = 7;
    private String TABLE_CREATE = "create table IF NOT EXISTS";
    private String TABLE_ID_AUTO = "id INTEGER primary key autoincrement";


    public DBHelper(Context context) {
        super(context, dbName, null, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//      模版数据表
        StringBuilder modelSQL = new StringBuilder();
        modelSQL.append(TABLE_CREATE);
        modelSQL.append(" " + TableKey.TABLE_MODEL);
        modelSQL.append(" " + "(");
        modelSQL.append(" " + TABLE_ID_AUTO + ",");
        modelSQL.append(" " + TableKey.MODEL_ID + " INTEGER,");
        modelSQL.append(" " + TableKey.NAME + " TEXT,");
        modelSQL.append(" " + TableKey.RESOURCENAME + " TEXT,");
        modelSQL.append(" " + TableKey.POSITION + " INTEGER,");
        modelSQL.append(" " + TableKey.TEXT + " TEXT,");
        modelSQL.append(" " + TableKey.BUTTON_NORMAL + " TEXT,");
        modelSQL.append(" " + TableKey.BUTTON_SELECTED + " TEXT,");
        modelSQL.append(" " + TableKey.MEDIAURL + " TEXT,");
        modelSQL.append(" " + TableKey.SHOWTYPE + " INTEGER,");
        modelSQL.append(" " + TableKey.SORT + " INTEGER,");
        modelSQL.append(" " + TableKey.TYPE + " TEXT,");
        modelSQL.append(" " + TableKey.TEMPLATE_ID + " INTEGER,");
        modelSQL.append(" " + TableKey.TEMPLATENAME + " TEXT,");
        modelSQL.append(" " + TableKey.HREF + " TEXT,");
        modelSQL.append(" " + TableKey.EXTENTION1 + " TEXT,");
        modelSQL.append(" " + TableKey.EXTENTION2 + " TEXT,");
        modelSQL.append(" " + TableKey.CLASS_NAME + " TEXT,");
        modelSQL.append(" " + TableKey.PACKAGE_NAME + " TEXT,");
        modelSQL.append(" " + TableKey.STB_ID + " TEXT,");
        modelSQL.append(" " + TableKey.PARENT_ID + " INTEGER");
        modelSQL.append(" );");
        db.execSQL(modelSQL.toString());

        //房间数据表
        StringBuilder numberSQL = new StringBuilder();
        numberSQL.append(TABLE_CREATE);
        numberSQL.append(" " + TableKey.TABLE_ROOM_NUMBER);
        numberSQL.append(" " + "(");
        numberSQL.append(" " + TABLE_ID_AUTO + ",");
        numberSQL.append(" " + TableKey.STB_ID +" TEXT,");
        numberSQL.append(" " + TableKey.ROOM_NUMBER + " TEXT");
        numberSQL.append(" );");
        db.execSQL(numberSQL.toString());

        //频道列表数据
        StringBuilder channelSQL = new StringBuilder();
        channelSQL.append(TABLE_CREATE);
        channelSQL.append(" " + TableKey.TABLE_CHANNEL);
        channelSQL.append(" " + "(");
        channelSQL.append(" " + TABLE_ID_AUTO + ",");
        channelSQL.append(" " + TableKey.STB_ID +" TEXT,");
        channelSQL.append(" " + TableKey.CHANNELCODE +" TEXT,");
        channelSQL.append(" " + TableKey.CHANNELNAME +" TEXT,");
        channelSQL.append(" " + TableKey.SEARCHKEY +" TEXT,");
        channelSQL.append(" " + TableKey.MIXNO +" TEXT,");
        channelSQL.append(" " + TableKey.CHANNELTYPE +" INTEGER,");
        channelSQL.append(" " + TableKey.MEDIASERVICES +" TEXT,");
        channelSQL.append(" " + TableKey.IPPVENABLE +" INTEGER,");
        channelSQL.append(" " + TableKey.LPVRENABLE +" INTEGER,");
        channelSQL.append(" " + TableKey.PARENTCONTROLENABLE +" INTEGER,");
        channelSQL.append(" " + TableKey.RATINGID +" TEXT,");
        channelSQL.append(" " + TableKey.SORTNUM +" INTEGER,");
        channelSQL.append(" " + TableKey.BOCODE +" TEXT,");
        channelSQL.append(" " + TableKey.TELECOMCODE +" TEXT,");
        channelSQL.append(" " + TableKey.MEDIACODE +" TEXT,");
        channelSQL.append(" " + TableKey.DESCRIPTION +" TEXT,");
        channelSQL.append(" " + TableKey.COLUMNCODE +" TEXT,");
        channelSQL.append(" " + TableKey.COLUMNNAME +" TEXT,");
        channelSQL.append(" " + TableKey.FILENAME +" TEXT,");
        channelSQL.append(" " + TableKey.AUDIOLANG +" TEXT,");
        channelSQL.append(" " + TableKey.SUBTITLELANG +" TEXT,");
        channelSQL.append(" " + TableKey.USERMIXNO +" INTEGER,");
        channelSQL.append(" " + TableKey.NPVRAVAILABLE +" INTEGER,");
        channelSQL.append(" " + TableKey.TSAVAILABLE +" INTEGER,");
        channelSQL.append(" " + TableKey.TVAVAILABLE +" INTEGER,");
        channelSQL.append(" " + TableKey.TVODAVAILABLE +" INTEGER,");
        channelSQL.append(" " + TableKey.ADVERTISECONTENT +" INTEGER,");
        channelSQL.append(" " + TableKey.ISLOCALTIMESHIFT +" INTEGER,");
        channelSQL.append(" " + TableKey.BITRATE +" INTEGER");
        channelSQL.append(" );");
        db.execSQL(channelSQL.toString());

        //回看列表数据
        StringBuilder lookbackSQL = new StringBuilder();
        lookbackSQL.append(TABLE_CREATE);
        lookbackSQL.append(" " + TableKey.TABLE_LOOKBACK);
        lookbackSQL.append(" " + "(");
        lookbackSQL.append(" " + TABLE_ID_AUTO + ",");
        lookbackSQL.append(" " + TableKey.STB_ID +" TEXT,");
        lookbackSQL.append(" " + TableKey.PREVUEID +" INTEGER,");
        lookbackSQL.append(" " + TableKey.PREVUECODE +" TEXT,");
        lookbackSQL.append(" " + TableKey.PREVUENAME +" TEXT,");
        lookbackSQL.append(" " + TableKey.BOCODE +" TEXT,");
        lookbackSQL.append(" " + TableKey.TELECOMCODE +" TEXT,");
        lookbackSQL.append(" " + TableKey.MEDIACODE +" TEXT,");
        lookbackSQL.append(" " + TableKey.CHANNELCODE +" TEXT,");
        lookbackSQL.append(" " + TableKey.SYSTEMRECORDENABLE +" INTEGER,");
        lookbackSQL.append(" " + TableKey.PRIVATERECORDENABLE +" INTEGER,");
        lookbackSQL.append(" " + TableKey.ISHOT +" INTEGER,");
        lookbackSQL.append(" " + TableKey.ISARCHIVE +" INTEGER,");
        lookbackSQL.append(" " + TableKey.HASPRIVATERECORD +" INTEGER,");
        lookbackSQL.append(" " + TableKey.BEGINTIME +" TEXT,");
        lookbackSQL.append(" " + TableKey.ENDTIME +" TEXT,");
        lookbackSQL.append(" " + TableKey.UTCBEGINTIME +" TEXT,");
        lookbackSQL.append(" " + TableKey.UTCENDTIME +" TEXT,");
        lookbackSQL.append(" " + TableKey.SAVEDAYS +" INTEGER,");
        lookbackSQL.append(" " + TableKey.SERIESHEADID +" INTEGER,");
        lookbackSQL.append(" " + TableKey.SERIESVOLUME +" INTEGER,");
        lookbackSQL.append(" " + TableKey.SERIESNUM +" INTEGER,");
        lookbackSQL.append(" " + TableKey.SERIESNAME +" TEXT,");
        lookbackSQL.append(" " + TableKey.SEARCHKEY +" TEXT,");
        lookbackSQL.append(" " + TableKey.DIRECTOR +" TEXT,");
        lookbackSQL.append(" " + TableKey.DIRECTORSEARCHKEY +" TEXT,");
        lookbackSQL.append(" " + TableKey.ACTOR +" TEXT,");
        lookbackSQL.append(" " + TableKey.ACTORSEARCHKEY +" TEXT,");
        lookbackSQL.append(" " + TableKey.MEDIASERVICES +" INTEGER,");
        lookbackSQL.append(" " + TableKey.DESCRIPTION +" TEXT,");
        lookbackSQL.append(" " + TableKey.CREATETIME +" TEXT,");
        lookbackSQL.append(" " + TableKey.RELEASEDATE +" TEXT,");
        lookbackSQL.append(" " + TableKey.WRITER +" TEXT,");
        lookbackSQL.append(" " + TableKey.DETAILDESCRIBED +" TEXT,");
        lookbackSQL.append(" " + TableKey.ISTIMESHIFT +" INTEGER,");
        lookbackSQL.append(" " + TableKey.ISARCHIVEMODE +" INTEGER,");
        lookbackSQL.append(" " + TableKey.ISPROTECTION +" INTEGER,");
        lookbackSQL.append(" " + TableKey.CLOSEDCAPTION +" INTEGER,");
        lookbackSQL.append(" " + TableKey.ARCHIVEMODE +" INTEGER,");
        lookbackSQL.append(" " + TableKey.COPYPROTECTION +" INTEGER,");
        lookbackSQL.append(" " + TableKey.RATINGID +" INTEGER,");
        lookbackSQL.append(" " + TableKey.LANGUAGE +" TEXT,");
        lookbackSQL.append(" " + TableKey.AUDIOLANGS +" TEXT,");
        lookbackSQL.append(" " + TableKey.RELEASEYEAR +" TEXT,");
        lookbackSQL.append(" " + TableKey.PLAYTYPE +" INTEGER,");
        lookbackSQL.append(" " + TableKey.PROGRAMID +" TEXT,");
        lookbackSQL.append(" " + TableKey.SUBTITLES +" TEXT,");
        lookbackSQL.append(" " + TableKey.PROGRAMTYPE +" INTEGER,");
        lookbackSQL.append(" " + TableKey.PROGRAMSOURCE +" TEXT,");
        lookbackSQL.append(" " + TableKey.DOLBY +" TEXT,");
        lookbackSQL.append(" " + TableKey.TFFLAG +" TEXT,");
        lookbackSQL.append(" " + TableKey.PREMIEREORFINALE +" INTEGER,");
        lookbackSQL.append(" " + TableKey.STARRATING +" INTEGER,");
        lookbackSQL.append(" " + TableKey.PROGRAMDESCRIPTION +" TEXT,");
        lookbackSQL.append(" " + TableKey.PROGRAMLANGUAGE +" TEXT,");
        lookbackSQL.append(" " + TableKey.BITRATE +" INTEGER,");
        lookbackSQL.append(" " + TableKey.GENRE +" TEXT,");
        lookbackSQL.append(" " + TableKey.SUBGENRE +" TEXT,");
        lookbackSQL.append(" " + TableKey.EPISODETITLE +" TEXT,");
        lookbackSQL.append(" " + TableKey.PARENTALADVISORY +" TEXT,");
        lookbackSQL.append(" " + TableKey.POSTERFILELIST +" TEXT,");
        lookbackSQL.append(" " + TableKey.RECORDCODE +" TEXT,");
        lookbackSQL.append(" " + TableKey.CDNCHANNELCODE +" TEXT,");
        lookbackSQL.append(" " + TableKey.STATUS +" INTEGER,");
        lookbackSQL.append(" " + TableKey.VALIDTIME +" TEXT,");
        lookbackSQL.append(" " + TableKey.RECORDDEFINITION +" INTEGER,");
        lookbackSQL.append(" " + TableKey.RECORDTELECOMCODE +" TEXT,");

        lookbackSQL.append(" " + TableKey.TIMESHIFTMODE +" TEXT,");
        lookbackSQL.append(" " + TableKey.AUDIOLANG +" TEXT,");
        lookbackSQL.append(" " + TableKey.SUBTITLELANG +" TEXT,");
        lookbackSQL.append(" " + TableKey.TVODMODE +" TEXT,");
        lookbackSQL.append(" " + TableKey.CATALOGID +" TEXT,");

        lookbackSQL.append(" " + TableKey.RECORDMEDIACODE +" TEXT");
        lookbackSQL.append(" );");
        db.execSQL(lookbackSQL.toString());


        //频道分类数据
        StringBuilder columnSQL = new StringBuilder();
        columnSQL.append(TABLE_CREATE);
        columnSQL.append(" " + TableKey.TABLE_COLUMN);
        columnSQL.append(" " + "(");
        columnSQL.append(" " + TABLE_ID_AUTO + ",");
        columnSQL.append(" " + TableKey.STB_ID +" TEXT,");
        columnSQL.append(" " + TableKey.COLUMNCODE +" TEXT,");

        columnSQL.append(" " + TableKey.COLUMNNAME +" TEXT,");
        columnSQL.append(" " + TableKey.PARENTCODE +" TEXT,");
        columnSQL.append(" " + TableKey.SUBEXIST +" INTEGER,");
        columnSQL.append(" " + TableKey.TELECOMCODE +" TEXT,");
        columnSQL.append(" " + TableKey.MEDIACODE +" TEXT,");
        columnSQL.append(" " + TableKey.UPDATETIME +" TEXT,");
        columnSQL.append(" " + TableKey.CREATETIME +" TEXT,");
        columnSQL.append(" " + TableKey.SORTNUM +" INTEGER,");
        columnSQL.append(" " + TableKey.SORTNAME +" TEXT,");

        columnSQL.append(" " + TableKey.COLUMNTYPE +" INTEGER,");
        columnSQL.append(" " + TableKey.HASPOSTER +" INTEGER,");
        columnSQL.append(" " + TableKey.CUSTOMPRICE +" INTEGER,");
        columnSQL.append(" " + TableKey.STATUS +" INTEGER,");
        columnSQL.append(" " + TableKey.BOCODE +" TEXT,");
        columnSQL.append(" " + TableKey.POSTERFILELIST +" TEXT,");
        columnSQL.append(" " + TableKey.DESCRIPTION +" TEXT,");
        columnSQL.append(" " + TableKey.ADVERTISED +" INTEGER,");
        columnSQL.append(" " + TableKey.SHORTDESC +" TEXT,");
        columnSQL.append(" " + TableKey.PROGRAMNAME +" TEXT,");
        columnSQL.append(" " + TableKey.NORMALPOSTER +" TEXT,");
        columnSQL.append(" " + TableKey.SMALLPOSTER +" TEXT,");
        columnSQL.append(" " + TableKey.BIGPOSTER +" TEXT,");
        columnSQL.append(" " + TableKey.ICONPOSTER +" TEXT,");
        columnSQL.append(" " + TableKey.TITLEPOSTER +" TEXT,");
        columnSQL.append(" " + TableKey.ADVERTISEMENTPOSTER +" TEXT,");
        columnSQL.append(" " + TableKey.SKETCHPOSTER +" TEXT,");
        columnSQL.append(" " + TableKey.BGPOSTER +" TEXT,");
        columnSQL.append(" " + TableKey.OTHERPOSTER1 +" TEXT,");
        columnSQL.append(" " + TableKey.OTHERPOSTER2 +" TEXT,");
        columnSQL.append(" " + TableKey.OTHERPOSTER3 +" TEXT,");
        columnSQL.append(" " + TableKey.OTHERPOSTER4 +" TEXT,");
        columnSQL.append(" " + TableKey.ISHIDDEN +" INTEGER,");
        columnSQL.append(" " + TableKey.VODCOUNT +" INTEGER,");
        columnSQL.append(" " + TableKey.CHANNELCODE +" TEXT");
        columnSQL.append(" );");
        db.execSQL(columnSQL.toString());


        StringBuilder autoSQL = new StringBuilder();
        autoSQL.append(TABLE_CREATE);
        autoSQL.append(" " + TableKey.TABLE_AUTHENTICATION);
        autoSQL.append(" " + "(");
        autoSQL.append(" " + TABLE_ID_AUTO + ",");
        autoSQL.append(" " + TableKey.STB_ID +" TEXT,");
        autoSQL.append(" " + TableKey.AUTO_NAME + " TEXT,");
        autoSQL.append(" " + TableKey.AUTO_VALUE + " TEXT");
        autoSQL.append(" );");
        db.execSQL(autoSQL.toString());

        //烽火数据
        StringBuilder fiberHomeChannelSQL = new StringBuilder();
        fiberHomeChannelSQL.append(TABLE_CREATE);
        fiberHomeChannelSQL.append(" " + TableKey.TABLE_FIBERHOME_CHANNEL);
        fiberHomeChannelSQL.append(" " + "(");
        fiberHomeChannelSQL.append(" " + TABLE_ID_AUTO + ",");
        fiberHomeChannelSQL.append(" " + TableKey.CHANNELID + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.CHANNELNAME + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.USERCHANNELID + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.CHANNELURL + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.TIMESHIFT + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.CHANNELSDP + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.TIMESHIFTURL + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.CHANNELLOGURL + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.CHANNELLOGOURL + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.POSITIONX + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.POSITIONY + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.BEGINTIME + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.INTERVAL + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.CHANNELTYPE + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.LASTING + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.CHANNELPURCHASED + " TEXT,");
        fiberHomeChannelSQL.append(" " + TableKey.NATIVEDATA + " TEXT");
        fiberHomeChannelSQL.append(" );");
        db.execSQL(fiberHomeChannelSQL.toString());

    }

    /**
     * 版本升级的时候也做数据库升级处理，后期版本数据库添加字段时在这里添加
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        onCreate(db);
        try {
            if (newVersion > oldVersion) {
                //新增NATIVEDATA字段，用于保存原始数据
                if (!checkColumnExist(db, TableKey.TABLE_FIBERHOME_CHANNEL, TableKey.NATIVEDATA)) {
                    db.execSQL("ALTER TABLE " + TableKey.TABLE_FIBERHOME_CHANNEL + " ADD " + TableKey.NATIVEDATA + " INTEGER");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 检查某表列是否存在
     *
     * @param db
     * @param tableName  表名
     * @param columnName 列名
     * @return
     */
    public boolean checkColumnExist(SQLiteDatabase db, String tableName,
                                    String columnName) {
        boolean result = false;
        Cursor cursor = null;
        try {
            // 查询一行
            cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0",
                    null);
            result = cursor != null && cursor.getColumnIndex(columnName) != -1;
        } catch (Exception e) {
            DebugUtil.e("checkColumnExists1..." + e.getMessage());
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

}