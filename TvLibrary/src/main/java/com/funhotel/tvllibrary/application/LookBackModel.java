package com.funhotel.tvllibrary.application;

import java.io.Serializable;

/**
 * @Title: LookBackModel
 * @Description: 回看列表的model
 * @author: Zhang Yetao
 * @data: 2016/10/9 15:03
 */
public class LookBackModel implements Serializable{
    int prevueid;//预告 ID
    String prevuecode; //预告 Code
    String prevuename;//预告名称
    String bocode;//业务运营商 Code
    String telecomcode;//节目的外部 Code，如电信 code
    String mediacode;//    媒体提供商 Code，如文广、CCTV 等的 Code
    String channelcode;//    频道 Code
    int systemrecordenable;//    是否支持系统彔制，1:支持，0:丌支持
    int privaterecordenable;//    是否支持个人彔制，1:支持，0:丌支持
    int ishot;//    是否是热点 1:是，0:否
    int isarchive;//    是否已经归档，0：否，1：是
    int hasprivaterecord;//    是否有用户申请 NPVR 彔制，0：否，1：是
    String begintime;//    开始时间（本地时间），时间格式： YYYY.MM.DD HH:24MI:SS
    String endtime;//    结束时间（本地时间），时间格式： YYYY.MM.DD HH:24MI:SS
    int savedays;//    彔制保存时间，单位：天
    String utcbegintime;//    [4.05.01.01 新增]  开始时间（ UTC 时间），时间格式： YYYY.MM.DD HH:24MI:SS
    String utcendtime;//    [4.05.01.01 新增]  结束时间（ UTC 时间），时间格式： YYYY.MM.DD HH:24MI:SS
    int seriesheadid;//    连续剧剧头 ID，非连续剧缺省为 0
    int seriesvolume;//    连续剧总集数
    int seriesnum;//    连续剧的集数，非连续剧缺省为 1
    String seriesname;//    连续剧名称
    String searchkey;//    预告搜索关键字
    String director;//    导演列表，多个导演用‘；’分割
    String directorsearchkey;//    导演搜索关键字
    String actor;//    演员列表，多个演员用‘；’分割
    String actorsearchkey;//    演员搜索关键字
    int mediaservices;  //    节目单彔制内容的媒体类型集合：  0：没有彔制的内容 >0：彔制内容媒体类型的二进制集合。如 3=1+2，表示彔制成功的为rtsp和hls的内容。 彔制内容的媒体类型的具体取值定义见通 用注释说明 mediaservice。
    String description;//    描述信息
    String createtime;//    创建时间（本地时间），时间格式： YYYY.MM.DD HH:MM:SS
    String releasedate;//    发布日期描述
    String writer;//    作者列表，以‘;‘分隔
    String detaildescribed;//    详细描述信息，可选字段，展示通常超过一 页，局点使用较少。该字段使用的场景通常 是详情页面上，在简介描述后有“详情”链接， 点击后与门一个页面展示详细描述信息。
    int istimeshift;//    [4.05.01.01 新增]  是否支持时秱(startover)，0：否，1：是
    int isarchivemode;//    [4.05.01.01 新增]  是否支持归档，1：是，0：否
    int isprotection;//    [4.05.01.01 新增]  是否支持拷贝保护，1：是，0：否
    int closedcaption ;//    [4.05.01.01 新增]  是否隐藏字幕，1：是，0：否

    int archivemode ;//    [4.05.01.01 新增]  归档模式
    int copyprotection ;//    [4.05.01.01 新增]  拷贝保护标识
    int ratingid ;//    [4.05.01.01 新增]  内容分级标识，见通用注释说明
    String language ;//    [4.05.01.01 新增]  原始诧音 Enum：kor
    String audiolangs; //    [4.05.01.01 新增]  音频诧言信息，多诧音情况下可支持的诧言 列表，以‘;’分割
    String releaseyear ;//    [4.05.01.01 新增]  发布年仹
    int playtype ;//    [4.05.01.01 新增]  播放类型，0：首播，1：重播
    String programid ;//    [4.05.01.01 新增]  节目 ID，用亍 ippv，无值则为 prevuecode
    String subtitles ;//    [4.05.01.01 新增]  字幕支持的诧言列表，以‘;’分割
    int programtype ;//    [4.05.01.01 新增]  新节目类型，0：New 节目 1：非 New
    String programsource ;//    [4.05.01.01 新增]  节目提供方名称
    String dolby ;//    [4.05.01.01 新增]  Dolby 杜比特性
    String tfflag ;//    [4.05.01.01 新增]  直播彔播标识
    String premiereorfinale ;//    [4.05.01.01 新增]  节目处亍系列的位置
    String starrating ;//    [4.05.01.01 新增]  星级，取值范围为 0-10，一星值为 2，半星 为 1
    String programdescription ;//    [4.05.01.01 新增]  节目描述信息
    String programlanguage ;//    [4.05.01.01 新增]  节目的诧言信息
    int bitrate ;//    [4.05.01.01 新增]  码率（展示字段），默讣值为标清码率 2000， 单位 kbps
    String genre ;//    [4.05.01.01 新增]  内容所属主分类描述，多种类型用‘;’分隔
    String subgenre ;//    [4.05.01.01 新增]  内容所属子分类描述
    String episodetitle ;//    [4.05.01.01 新增]  单集主题词
    String parentaladvisory ;//    [4.05.01.01 新增]  内容警告信息。例值：“叧宜 18 岁以上”
    String posterfilelist ;//    [5.02.01.01 新增] 节目单海报名称列表，此字段包含 12 张海 报，用‘;‘分割。海报排列顺序为 IPTV、 MVS-Mobile、PC、MVS-Tablets 四种终端， 每屏 3 张海报，分别为普通海报、缩略图和 剧照。 海 报 相 对 路 径 为：../images/prevueposter/
    String recordcode ;//    彔制内容的文件 Code
    String cdnchannelcode ;//    物理频道 Code
    int status  ;//            彔制状态，0:未彔制，1:已彔制 备注：对亍OTT的彔制内容，默讣状态为1， 需要判断当前时间>结束时间，则展示状态 为1，否则为0。 mediaservice int =,& 彔制内容的媒体服务类型，见通用注释说明
    String validtime  ;//    系统彔制保存有效期限（UTC 时间），时间 格式：YYYY.MM.DD HH:MM:SS
    int recorddefinition ;//    彔制内容的清晰度标识： 1:标清 SD;  2:标清高码率 SD-H;  4:高清 HD
    String recordtelecomcode; //    彔制内容的外部 Code，如电信 code
    String recordmediacode ;//    彔制内容的媒体提供商 Code，如文广 Code

    String timeshiftmode;//
    String audiolang;//
    String subtitlelang;//
    String tvodmode;//
    String catalogid;//

    public String getTimeshiftmode() {
        return timeshiftmode;
    }

    public void setTimeshiftmode(String timeshiftmode) {
        this.timeshiftmode = timeshiftmode;
    }

    public String getAudiolang() {
        return audiolang;
    }

    public void setAudiolang(String audiolang) {
        this.audiolang = audiolang;
    }

    public String getTvodmode() {
        return tvodmode;
    }

    public void setTvodmode(String tvodmode) {
        this.tvodmode = tvodmode;
    }

    public String getSubtitlelang() {
        return subtitlelang;
    }

    public void setSubtitlelang(String subtitlelang) {
        this.subtitlelang = subtitlelang;
    }

    public String getCatalogid() {
        return catalogid;
    }

    public void setCatalogid(String catalogid) {
        this.catalogid = catalogid;
    }

    public int getPrevueid() {
        return prevueid;
    }

    public void setPrevueid(int prevueid) {
        this.prevueid = prevueid;
    }

    public String getPrevuecode() {
        return prevuecode;
    }

    public void setPrevuecode(String prevuecode) {
        this.prevuecode = prevuecode;
    }

    public String getPrevuename() {
        return prevuename;
    }

    public void setPrevuename(String prevuename) {
        this.prevuename = prevuename;
    }

    public String getBocode() {
        return bocode;
    }

    public void setBocode(String bocode) {
        this.bocode = bocode;
    }

    public String getTelecomcode() {
        return telecomcode;
    }

    public void setTelecomcode(String telecomcode) {
        this.telecomcode = telecomcode;
    }

    public String getMediacode() {
        return mediacode;
    }

    public void setMediacode(String mediacode) {
        this.mediacode = mediacode;
    }

    public int getSystemrecordenable() {
        return systemrecordenable;
    }

    public void setSystemrecordenable(int systemrecordenable) {
        this.systemrecordenable = systemrecordenable;
    }

    public String getChannelcode() {
        return channelcode;
    }

    public void setChannelcode(String channelcode) {
        this.channelcode = channelcode;
    }

    public int getPrivaterecordenable() {
        return privaterecordenable;
    }

    public void setPrivaterecordenable(int privaterecordenable) {
        this.privaterecordenable = privaterecordenable;
    }

    public int getIshot() {
        return ishot;
    }

    public void setIshot(int ishot) {
        this.ishot = ishot;
    }

    public int getIsarchive() {
        return isarchive;
    }

    public void setIsarchive(int isarchive) {
        this.isarchive = isarchive;
    }

    public int getHasprivaterecord() {
        return hasprivaterecord;
    }

    public void setHasprivaterecord(int hasprivaterecord) {
        this.hasprivaterecord = hasprivaterecord;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getUtcbegintime() {
        return utcbegintime;
    }

    public void setUtcbegintime(String utcbegintime) {
        this.utcbegintime = utcbegintime;
    }

    public String getUtcendtime() {
        return utcendtime;
    }

    public void setUtcendtime(String utcendtime) {
        this.utcendtime = utcendtime;
    }

    public int getSavedays() {
        return savedays;
    }

    public void setSavedays(int savedays) {
        this.savedays = savedays;
    }

    public int getSeriesvolume() {
        return seriesvolume;
    }

    public void setSeriesvolume(int seriesvolume) {
        this.seriesvolume = seriesvolume;
    }

    public int getSeriesheadid() {
        return seriesheadid;
    }

    public void setSeriesheadid(int seriesheadid) {
        this.seriesheadid = seriesheadid;
    }

    public int getSeriesnum() {
        return seriesnum;
    }

    public void setSeriesnum(int seriesnum) {
        this.seriesnum = seriesnum;
    }

    public String getSeriesname() {
        return seriesname;
    }

    public void setSeriesname(String seriesname) {
        this.seriesname = seriesname;
    }

    public String getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirectorsearchkey() {
        return directorsearchkey;
    }

    public void setDirectorsearchkey(String directorsearchkey) {
        this.directorsearchkey = directorsearchkey;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getActorsearchkey() {
        return actorsearchkey;
    }

    public void setActorsearchkey(String actorsearchkey) {
        this.actorsearchkey = actorsearchkey;
    }

    public int getMediaservices() {
        return mediaservices;
    }

    public void setMediaservices(int mediaservices) {
        this.mediaservices = mediaservices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDetaildescribed() {
        return detaildescribed;
    }

    public void setDetaildescribed(String detaildescribed) {
        this.detaildescribed = detaildescribed;
    }

    public int getIstimeshift() {
        return istimeshift;
    }

    public void setIstimeshift(int istimeshift) {
        this.istimeshift = istimeshift;
    }

    public int getIsarchivemode() {
        return isarchivemode;
    }

    public void setIsarchivemode(int isarchivemode) {
        this.isarchivemode = isarchivemode;
    }

    public int getIsprotection() {
        return isprotection;
    }

    public void setIsprotection(int isprotection) {
        this.isprotection = isprotection;
    }

    public int getClosedcaption() {
        return closedcaption;
    }

    public void setClosedcaption(int closedcaption) {
        this.closedcaption = closedcaption;
    }

    public int getArchivemode() {
        return archivemode;
    }

    public void setArchivemode(int archivemode) {
        this.archivemode = archivemode;
    }

    public int getCopyprotection() {
        return copyprotection;
    }

    public void setCopyprotection(int copyprotection) {
        this.copyprotection = copyprotection;
    }

    public int getRatingid() {
        return ratingid;
    }

    public void setRatingid(int ratingid) {
        this.ratingid = ratingid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAudiolangs() {
        return audiolangs;
    }

    public void setAudiolangs(String audiolangs) {
        this.audiolangs = audiolangs;
    }

    public String getReleaseyear() {
        return releaseyear;
    }

    public void setReleaseyear(String releaseyear) {
        this.releaseyear = releaseyear;
    }

    public int getPlaytype() {
        return playtype;
    }

    public void setPlaytype(int playtype) {
        this.playtype = playtype;
    }

    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles;
    }

    public int getProgramtype() {
        return programtype;
    }

    public void setProgramtype(int programtype) {
        this.programtype = programtype;
    }

    public String getProgramsource() {
        return programsource;
    }

    public void setProgramsource(String programsource) {
        this.programsource = programsource;
    }

    public String getDolby() {
        return dolby;
    }

    public void setDolby(String dolby) {
        this.dolby = dolby;
    }

    public String getTfflag() {
        return tfflag;
    }

    public void setTfflag(String tfflag) {
        this.tfflag = tfflag;
    }

    public String getPremiereorfinale() {
        return premiereorfinale;
    }

    public void setPremiereorfinale(String premiereorfinale) {
        this.premiereorfinale = premiereorfinale;
    }

    public String getStarrating() {
        return starrating;
    }

    public void setStarrating(String starrating) {
        this.starrating = starrating;
    }

    public String getProgramdescription() {
        return programdescription;
    }

    public void setProgramdescription(String programdescription) {
        this.programdescription = programdescription;
    }

    public String getProgramlanguage() {
        return programlanguage;
    }

    public void setProgramlanguage(String programlanguage) {
        this.programlanguage = programlanguage;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSubgenre() {
        return subgenre;
    }

    public void setSubgenre(String subgenre) {
        this.subgenre = subgenre;
    }

    public String getEpisodetitle() {
        return episodetitle;
    }

    public void setEpisodetitle(String episodetitle) {
        this.episodetitle = episodetitle;
    }

    public String getParentaladvisory() {
        return parentaladvisory;
    }

    public void setParentaladvisory(String parentaladvisory) {
        this.parentaladvisory = parentaladvisory;
    }

    public String getPosterfilelist() {
        return posterfilelist;
    }

    public void setPosterfilelist(String posterfilelist) {
        this.posterfilelist = posterfilelist;
    }

    public String getRecordcode() {
        return recordcode;
    }

    public void setRecordcode(String recordcode) {
        this.recordcode = recordcode;
    }

    public String getCdnchannelcode() {
        return cdnchannelcode;
    }

    public void setCdnchannelcode(String cdnchannelcode) {
        this.cdnchannelcode = cdnchannelcode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getValidtime() {
        return validtime;
    }

    public void setValidtime(String validtime) {
        this.validtime = validtime;
    }

    public int getRecorddefinition() {
        return recorddefinition;
    }

    public void setRecorddefinition(int recorddefinition) {
        this.recorddefinition = recorddefinition;
    }

    public String getRecordtelecomcode() {
        return recordtelecomcode;
    }

    public void setRecordtelecomcode(String recordtelecomcode) {
        this.recordtelecomcode = recordtelecomcode;
    }

    public String getRecordmediacode() {
        return recordmediacode;
    }

    public void setRecordmediacode(String recordmediacode) {
        this.recordmediacode = recordmediacode;
    }
}
