package com.funhotel.tvllibrary.db;

/**
 * @Title: TableKey
 * @Description: 数据库表的key
 * @author: LinWeiDong
 * @data: 2016/4/19 15:11
 */
public class TableKey {

    public static final String TABLE_MODEL = "table_model";//模版数据表

    public static final String TABLE_ROOM_NUMBER = "table_room_number";//房间号数据表
    public static final String TABLE_CHANNEL = "table_channel";//频道
    public static final String TABLE_LOOKBACK = "table_look_back";//回看列表
    public static final String TABLE_COLUMN = "table_column";//分类列表
    public static final String TABLE_AUTHENTICATION = "table_authentication"; //认证信息表
    public static final String TABLE_FIBERHOME_CHANNEL = "table_fiberhome_channel";//烽火频道数据

    public static final String MODEL_ID="model_id";//元素ID
    public static final String NAME="name";//名称
    public static final String RESOURCENAME="resourceName";//资源名称
    public static final String POSITION="position";//位置
    public static final String TEXT="text";//文字描述
    public static final String HREF="href";
    public static final String MEDIAURL="mediaUrl";//视频背景地址
    public static final String SHOWTYPE="showType";//背景类型
    public static final String SORT="sort";//排序(不用)
    public static final String TYPE="type";//资源类型
    public static final String TEMPLATE_ID="templateId";//模版ID
    public static final String TEMPLATENAME="templateName";//模版名
    public static final String BUTTON_NORMAL="beforeImgUrl";//未选中的按钮图片
    public static final String BUTTON_SELECTED="backImgUrl";//选中的按钮图片
    public static final String EXTENTION1="extention1";//扩展字段（价格）
    public static final String EXTENTION2="extention2";//扩展字段（星级）
    public static final String STB_ID="stb_id";//用户帐号
    public static final String ROOM_NUMBER="room_number";//房间号
    public static final String PARENT_ID = "parent_id";//每一模板的父模板id，用于标示模板为id下的所有模板
    public static final String CLASS_NAME = "class_name";//类名
    public static final String PACKAGE_NAME = "package_name";//包名

    //频道列表字段
//    public static final String CHANNELCODE = "channelcode";
    public static final String CHANNELNAME = "channelname";
//    public static final String SEARCHKEY = "searchkey";
    public static final String MIXNO = "mixno";
    public static final String CHANNELTYPE = "channeltype";
//    public static final String MEDIASERVICES = "mediaservices";
    public static final String IPPVENABLE = "ippvenable";
    public static final String LPVRENABLE = "lpvrenable";
    public static final String PARENTCONTROLENABLE = "parentcontrolenable";
//    public static final String RATINGID = "ratingid";
//    public static final String SORTNUM = "sortnum";
//    public static final String BOCODE = "bocode";
//    public static final String TELECOMCODE = "telecomcode";
//    public static final String MEDIACODE = "mediacode";
//    public static final String DESCRIPTION = "description";
//    public static final String COLUMNCODE = "columncode";
//    public static final String COLUMNNAME = "columnname";
    public static final String FILENAME = "filename";
//    public static final String AUDIOLANG = "audiolang";
//    public static final String SUBTITLELANG = "subtitlelang";
    public static final String USERMIXNO = "usermixno";
    public static final String NPVRAVAILABLE = "npvravailable";
    public static final String TSAVAILABLE = "tsavailable";
    public static final String TVAVAILABLE = "tvavailable";
    public static final String TVODAVAILABLE = "tvodavailable";
    public static final String ADVERTISECONTENT = "advertisecontent";
    public static final String ISLOCALTIMESHIFT = "islocaltimeshift";
//    public static final String BITRATE = "bitrate";

    //回看列表字段
    public static final String PREVUEID = "prevueid";
    public static final String PREVUECODE = "prevuecode";
    public static final String PREVUENAME = "prevuename";
//    public static final String BOCODE = "bocode";
//    public static final String TELECOMCODE = "telecomcode";
//    public static final String MEDIACODE = "mediacode";
//    public static final String CHANNELCODE = "channelcode";
    public static final String SYSTEMRECORDENABLE = "systemrecordenable";
    public static final String PRIVATERECORDENABLE = "privaterecordenable";
    public static final String ISHOT = "ishot";
    public static final String ISARCHIVE = "isarchive";
    public static final String HASPRIVATERECORD = "hasprivaterecord";
    public static final String BEGINTIME = "begintime";
    public static final String ENDTIME = "endtime";
    public static final String UTCBEGINTIME = "utcbegintime";
    public static final String UTCENDTIME = "utcendtime";
    public static final String SAVEDAYS = "savedays";
    public static final String SERIESHEADID = "seriesheadid";
    public static final String SERIESVOLUME = "seriesvolume";
    public static final String SERIESNUM = "seriesnum";
    public static final String SERIESNAME = "seriesname";
    public static final String SEARCHKEY = "searchkey";
    public static final String DIRECTOR = "director";
    public static final String DIRECTORSEARCHKEY = "directorsearchkey";
    public static final String ACTOR = "actor";
    public static final String ACTORSEARCHKEY = "actorsearchkey";
    public static final String MEDIASERVICES = "mediaservices";
//    public static final String DESCRIPTION = "description";
//    public static final String CREATETIME = "createtime";
    public static final String RELEASEDATE = "releasedate";
    public static final String WRITER = "writer";
    public static final String DETAILDESCRIBED = "detaildescribed";
    public static final String ISTIMESHIFT = "istimeshift";
    public static final String ISARCHIVEMODE = "isarchivemode";
    public static final String ISPROTECTION = "isprotection";
    public static final String CLOSEDCAPTION = "closedcaption";
    public static final String ARCHIVEMODE = "archivemode";
    public static final String COPYPROTECTION = "copyprotection";
    public static final String RATINGID = "ratingid";
    public static final String LANGUAGE = "language";
    public static final String AUDIOLANGS = "audiolangs";
    public static final String RELEASEYEAR = "releaseyear";
    public static final String PLAYTYPE = "playtype";
    public static final String PROGRAMID = "programid";
    public static final String SUBTITLES = "subtitles";
    public static final String PROGRAMTYPE = "programtype";
    public static final String PROGRAMSOURCE = "programsource";
    public static final String DOLBY = "dolby";
    public static final String TFFLAG = "tfflag";
    public static final String PREMIEREORFINALE = "premiereorfinale";
    public static final String STARRATING = "starrating";
    public static final String PROGRAMDESCRIPTION = "programdescription";
    public static final String PROGRAMLANGUAGE = "programlanguage";
    public static final String BITRATE = "bitrate";
    public static final String GENRE = "genre";
    public static final String SUBGENRE = "subgenre";
    public static final String EPISODETITLE = "episodetitle";
    public static final String PARENTALADVISORY = "parentaladvisory";
//    public static final String POSTERFILELIST = "posterfilelist";
    public static final String RECORDCODE = "recordcode";
    public static final String CDNCHANNELCODE = "cdnchannelcode";
//    public static final String STATUS = "status";
    public static final String VALIDTIME = "validtime";
    public static final String RECORDDEFINITION = "recorddefinition";
    public static final String RECORDTELECOMCODE = "recordtelecomcode";
    public static final String RECORDMEDIACODE = "recordmediacode";

    //频道分类字段
    public static final String COLUMNCODE = "columncode";
    public static final String COLUMNTYPE = "columntype";
    public static final String HASPOSTER = "hasposter";
    public static final String CUSTOMPRICE = "customprice";
    public static final String STATUS = "status";
    public static final String BOCODE = "bocode";
    public static final String POSTERFILELIST = "posterfilelist";
    public static final String DESCRIPTION = "description";
    public static final String ADVERTISED = "advertised";
    public static final String SHORTDESC = "shortdesc";
    public static final String PROGRAMNAME = "programname";
    public static final String NORMALPOSTER = "normalposter";
    public static final String SMALLPOSTER = "smallposter";
    public static final String BIGPOSTER = "bigposter";
    public static final String ICONPOSTER = "iconposter";
    public static final String TITLEPOSTER = "titleposter";
    public static final String ADVERTISEMENTPOSTER = "advertisementposter";
    public static final String SKETCHPOSTER = "sketchposter";
    public static final String BGPOSTER = "bgposter";
    public static final String OTHERPOSTER1 = "otherposter1";
    public static final String OTHERPOSTER2 = "otherposter2";
    public static final String OTHERPOSTER3 = "otherposter3";
    public static final String OTHERPOSTER4 = "otherposter4";
    public static final String ISHIDDEN = "ishidden";
    public static final String VODCOUNT = "vodcount";
    public static final String CHANNELCODE = "channelcode";

    public static final String COLUMNNAME = "columnname";
    public static final String PARENTCODE = "parentcode";
    public static final String SUBEXIST = "subexist";
    public static final String TELECOMCODE = "telecomcode";
    public static final String MEDIACODE = "mediacode";
    public static final String UPDATETIME = "updatetime";
    public static final String CREATETIME = "createtime";
    public static final String SORTNUM = "sortnum";
    public static final String SORTNAME = "sortname";

    //认证信息
    public static final String AUTO_NAME = "name";
    public static final String AUTO_VALUE = "value";

    //烽火数据
    public static final String CHANNELID = "ChannelID";
//    public static final String CHANNELNAME = "ChannelName";
    public static final String USERCHANNELID = "UserChannelID";
    public static final String CHANNELURL = "ChannelURL";
    public static final String TIMESHIFT = "TimeShift";
    public static final String CHANNELSDP = "ChannelSDP";
    public static final String TIMESHIFTURL = "TimeShiftURL";
    public static final String CHANNELLOGURL = "ChannelLogURL";
    public static final String CHANNELLOGOURL = "ChannelLogoURL";
    public static final String POSITIONX = "PositionX";
    public static final String POSITIONY = "PositionY";
//    public static final String BEGINTIME = "BeginTime";
    public static final String INTERVAL = "Interval";
    public static final String LASTING = "Lasting";
//    public static final String CHANNELTYPE = "ChannelType";
    public static final String CHANNELPURCHASED = "naChannelPurchasedme";
    public static final String NATIVEDATA = "nativedata";



    //缺失字段的补充
    public static final String TIMESHIFTMODE = "timeshiftmode";
    public static final String AUDIOLANG = "audiolang";
    public static final String SUBTITLELANG = "subtitlelang";
    public static final String TVODMODE = "tvodmode";
    public static final String CATALOGID = "catalogid";


}