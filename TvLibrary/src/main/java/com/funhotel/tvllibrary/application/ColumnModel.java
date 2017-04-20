package com.funhotel.tvllibrary.application;

import java.io.Serializable;

/**
 * @Title: ColumnModel
 * @Description: 栏目分类的Model
 * @author: Zhang Yetao
 * @data: 2016/10/19 10:27
 */
public class ColumnModel implements Serializable{
    String columncode  ;// 栏目 Code
    int columntype  ;// 栏目类型： 1-vod 节目栏目  2-频道栏目  8-卡拉 OK 栏目  26-增值业务栏目 111-测试栏目 112-所有影片栏目  113-最新影片栏目  114-最热影片栏目 117-与题栏目 subexist int = 是否有子栏目，0：无，1：有
    int hasposter;//    是否有海报，0：无，1：有
    int customprice;//    是否自定义节目价格，0：否，1：是
    int status;//    状态，０：待审核，１：审核发布 telecomcode String = 栏目的外部 Code，如电信 code mediacode String = 媒体提供商 Code
    String bocode;//    业务运营商 Code
    String posterfilelist;//    栏目海报名称列表，包含 12 张海报，用‘;’分割， EPG 服务器的相对路径为../images/poster/，具 体见下面各个海报的说明。
    String description;//    栏目描述
    int advertised;//    [4.07.01.01 修改支持三屏广告] 是否支持广 告，0：丌支持，大亍 0：支持广告的媒体服务 类型集合，见通用注释说明(mediaservices 说明) sortnum int sort 排序值
    String shortdesc;//    最新更新的节目规点，非常简短的剧情描述
    String programname;//    栏目下最新更新的节目名称
    String normalposter;//    普通海报（相对路径）
    String smallposter;//    缩略图（相对路径）
    String bigposter;//    剧照（相对路径）
    String iconposter;//    图标（相对路径）
    String titleposter;//    标题图（相对路径）
    String advertisementposter;//    广告图（相对路径）
    String sketchposter;//    草图（相对路径）
    String bgposter;//    背景图（相对路径）
    String otherposter1;//    其他海报 1（相对路径）
    String otherposter2;//    其他海报 2（相对路径）
    String otherposter3;//    其他海报 3（相对路径）
    String otherposter4;//    其他海报 4（相对路径）
    int ishidden;//    [4.05.01.01 新增] 是否强制隐藏栏目，0：否，1：是\n'
    int vodcount;//     [4.05.01.01 新增] 栏目下的已审核节目数
    String channelcode;//    [4.08.01.01 新增] SVOD 栏目归属的频道 code，非 SVOD 栏目为空

    String columnname;//栏目名称
    String parentcode;//父栏目 code
    int subexist;//是否有子栏目，0：无，1：有
    String telecomcode;//栏目的外部 Code，如电信 code
    String mediacode;//媒体提供商 Code
    String updatetime;//
    String createtime;//
    int sortnum;//排序值
    String sortname;//

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public int getSubexist() {
        return subexist;
    }

    public void setSubexist(int subexist) {
        this.subexist = subexist;
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

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getSortnum() {
        return sortnum;
    }

    public void setSortnum(int sortnum) {
        this.sortnum = sortnum;
    }



    public String getColumncode() {
        return columncode;
    }

    public void setColumncode(String columncode) {
        this.columncode = columncode;
    }

    public String getColumnname() {
        return columnname;
    }

    public void setColumnname(String columnname) {
        this.columnname = columnname;
    }

    public String getParentcode() {
        return parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    public int getColumntype() {
        return columntype;
    }

    public void setColumntype(int columntype) {
        this.columntype = columntype;
    }

    public int getHasposter() {
        return hasposter;
    }

    public void setHasposter(int hasposter) {
        this.hasposter = hasposter;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCustomprice() {
        return customprice;
    }

    public void setCustomprice(int customprice) {
        this.customprice = customprice;
    }

    public String getBocode() {
        return bocode;
    }

    public void setBocode(String bocode) {
        this.bocode = bocode;
    }

    public String getPosterfilelist() {
        return posterfilelist;
    }

    public void setPosterfilelist(String posterfilelist) {
        this.posterfilelist = posterfilelist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAdvertised() {
        return advertised;
    }

    public void setAdvertised(int advertised) {
        this.advertised = advertised;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getProgramname() {
        return programname;
    }

    public void setProgramname(String programname) {
        this.programname = programname;
    }

    public String getNormalposter() {
        return normalposter;
    }

    public void setNormalposter(String normalposter) {
        this.normalposter = normalposter;
    }

    public String getSmallposter() {
        return smallposter;
    }

    public void setSmallposter(String smallposter) {
        this.smallposter = smallposter;
    }

    public String getBigposter() {
        return bigposter;
    }

    public void setBigposter(String bigposter) {
        this.bigposter = bigposter;
    }

    public String getIconposter() {
        return iconposter;
    }

    public void setIconposter(String iconposter) {
        this.iconposter = iconposter;
    }

    public String getTitleposter() {
        return titleposter;
    }

    public void setTitleposter(String titleposter) {
        this.titleposter = titleposter;
    }

    public String getAdvertisementposter() {
        return advertisementposter;
    }

    public void setAdvertisementposter(String advertisementposter) {
        this.advertisementposter = advertisementposter;
    }

    public String getSketchposter() {
        return sketchposter;
    }

    public void setSketchposter(String sketchposter) {
        this.sketchposter = sketchposter;
    }

    public String getOtherposter1() {
        return otherposter1;
    }

    public void setOtherposter1(String otherposter1) {
        this.otherposter1 = otherposter1;
    }

    public String getBgposter() {
        return bgposter;
    }

    public void setBgposter(String bgposter) {
        this.bgposter = bgposter;
    }

    public String getOtherposter2() {
        return otherposter2;
    }

    public void setOtherposter2(String otherposter2) {
        this.otherposter2 = otherposter2;
    }

    public String getOtherposter3() {
        return otherposter3;
    }

    public void setOtherposter3(String otherposter3) {
        this.otherposter3 = otherposter3;
    }

    public String getOtherposter4() {
        return otherposter4;
    }

    public void setOtherposter4(String otherposter4) {
        this.otherposter4 = otherposter4;
    }

    public int getIshidden() {
        return ishidden;
    }

    public void setIshidden(int ishidden) {
        this.ishidden = ishidden;
    }

    public int getVodcount() {
        return vodcount;
    }

    public void setVodcount(int vodcount) {
        this.vodcount = vodcount;
    }

    public String getChannelcode() {
        return channelcode;
    }

    public void setChannelcode(String channelcode) {
        this.channelcode = channelcode;
    }
}
