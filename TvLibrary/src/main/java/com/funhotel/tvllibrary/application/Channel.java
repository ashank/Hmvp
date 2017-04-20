package com.funhotel.tvllibrary.application;

import java.io.Serializable;

/**
 * Created by zhiyahan on 2016/10/14.
 *
 *      频道主要信息
 */

public class Channel implements Serializable{


    private String channelcode ; //频道 Code
    private String channelname ;//频道名称
    private String searchkey ;//频道搜索关键字
    private String mixno ;//频道全局混排号
    private int  channeltype ;//频道类型，0：直播，1：轮播频道，3： mosaic 频道，4：PIP 频道，5：本地 WEB 频道，6：全局 WEB 频道
    private String  mediaservices ;//频道的媒体服务类型集合，见通用注释 说明
    private int  ippvenable ;//是否支持 IPPV，1:支持，0：丌支持
    private int  lpvrenable ;//是否支持 LPVR，1:支持，0：丌支持
    private int  parentcontrolenable ;//是否支持家长控制，1:支持，0：丌支持
    private String  ratingid ;//内容分级唯一标识，见通用注释说明
    private int  sortnum ;//排序序列值
    private String  bocode;//业务运营商 Code
    private String  telecomcode ;//频道的外部 Code，如电信 code
    private String  mediacode ;// 媒体提供商 Code
    private String  description ;//频道描述
    private String  columncode ;//栏目 Code
    private String  columnname ;//栏目名称
    private String  filename ;//台 标 文 件 名 称 ， 相 对 路 径 为../images/markurl/
    private String   audiolang;//音频诧言信息列表，用‘;’分割
    private String   subtitlelang ;//字幕诧言信息列表，用‘;’分割
    private int  usermixno ;//用户分组混排号（分组信息）即模板展 示的频道混排号
    private int  npvravailable ;//是否有 NPVR 权限（分组权限） ，0：否， 1：是

    private int  tsavailable ;//是否有频道时秱权限（分组权限） , 0： 否，大亍 0：有权限丏该值为分配的时秱 时长，单位：分钟 备注： 1、判断频道支持时秱，需要同时满足 2 个条件： 1）频道有时秱权限（见字段 tsavailable）。 2）物理频道的属性支持时秱（见字段 timeshiftenable）。 2、频道的时秱时长计算方法： 1）频道丌支持时秱，时秱时长为 0。 2）频道支持时秱，时秱时长为物理频道 可时秱时长即 shifttime 和 tsavailable 二 者的最小值。
    private int  tvavailable ;//是否有频道权限（分组权限） ，0：否，1： 是
    private int  tvodavailable ;//是否有频道 TOVD 权限（分组权限） ，0： 否，1：是 备注： 判断频道支持 TVOD，需要同时满足 2 个 条件： 1 、频道有 TVOD 权 限 （ 见 字 段 tvodavailable）。 2、物理频道属性支持 TVOD（见字段 tvodenable）。
    private int  advertisecontent;//广告标识， 见 通 用 注 释 说 明 (advertisecontent 说明)
    private int  islocaltimeshift ;//是否支持本地时秱，1:支持，0：不支持
    private int  bitrate ;//业务频道码率 (展示字段，具体值在实体 中)，由局方内容 metadata 下发，用亍非 三屏局点，默讣值为标清码率 2000，单 位 kbps
    private int status;//彔制状态，0:未彔制，1:已彔制 备注：对亍OTT的彔制内容，默讣状态为1， 需要判断当前时间>结束时间，则展示状态 为1，否则为0

    public String getChannelcode() {
        return channelcode;
    }

    public void setChannelcode(String channelcode) {
        this.channelcode = channelcode;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public String getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }

    public String getMixno() {
        return mixno;
    }

    public void setMixno(String mixno) {
        this.mixno = mixno;
    }

    public int getChanneltype() {
        return channeltype;
    }

    public void setChanneltype(int channeltype) {
        this.channeltype = channeltype;
    }

    public String getMediaservices() {
        return mediaservices;
    }

    public void setMediaservices(String mediaservices) {
        this.mediaservices = mediaservices;
    }

    public int getIppvenable() {
        return ippvenable;
    }

    public void setIppvenable(int ippvenable) {
        this.ippvenable = ippvenable;
    }

    public int getLpvrenable() {
        return lpvrenable;
    }

    public void setLpvrenable(int lpvrenable) {
        this.lpvrenable = lpvrenable;
    }

    public int getParentcontrolenable() {
        return parentcontrolenable;
    }

    public void setParentcontrolenable(int parentcontrolenable) {
        this.parentcontrolenable = parentcontrolenable;
    }

    public String getRatingid() {
        return ratingid;
    }

    public void setRatingid(String ratingid) {
        this.ratingid = ratingid;
    }

    public int getSortnum() {
        return sortnum;
    }

    public void setSortnum(int sortnum) {
        this.sortnum = sortnum;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAudiolang() {
        return audiolang;
    }

    public void setAudiolang(String audiolang) {
        this.audiolang = audiolang;
    }

    public String getSubtitlelang() {
        return subtitlelang;
    }

    public void setSubtitlelang(String subtitlelang) {
        this.subtitlelang = subtitlelang;
    }

    public int getUsermixno() {
        return usermixno;
    }

    public void setUsermixno(int usermixno) {
        this.usermixno = usermixno;
    }

    public int getNpvravailable() {
        return npvravailable;
    }

    public void setNpvravailable(int npvravailable) {
        this.npvravailable = npvravailable;
    }

    public int getTsavailable() {
        return tsavailable;
    }

    public void setTsavailable(int tsavailable) {
        this.tsavailable = tsavailable;
    }

    public int getTvavailable() {
        return tvavailable;
    }

    public void setTvavailable(int tvavailable) {
        this.tvavailable = tvavailable;
    }

    public int getTvodavailable() {
        return tvodavailable;
    }

    public void setTvodavailable(int tvodavailable) {
        this.tvodavailable = tvodavailable;
    }

    public int getAdvertisecontent() {
        return advertisecontent;
    }

    public void setAdvertisecontent(int advertisecontent) {
        this.advertisecontent = advertisecontent;
    }

    public int getIslocaltimeshift() {
        return islocaltimeshift;
    }

    public void setIslocaltimeshift(int islocaltimeshift) {
        this.islocaltimeshift = islocaltimeshift;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelcode='" + channelcode + '\'' +
                ", channelname='" + channelname + '\'' +
                ", searchkey='" + searchkey + '\'' +
                ", mixno='" + mixno + '\'' +
                ", channeltype=" + channeltype +
                ", mediaservices='" + mediaservices + '\'' +
                ", ippvenable=" + ippvenable +
                ", lpvrenable=" + lpvrenable +
                ", parentcontrolenable=" + parentcontrolenable +
                ", ratingid='" + ratingid + '\'' +
                ", sortnum=" + sortnum +
                ", bocode='" + bocode + '\'' +
                ", telecomcode='" + telecomcode + '\'' +
                ", mediacode='" + mediacode + '\'' +
                ", description='" + description + '\'' +
                ", columncode='" + columncode + '\'' +
                ", columnname='" + columnname + '\'' +
                ", filename='" + filename + '\'' +
                ", audiolang='" + audiolang + '\'' +
                ", subtitlelang='" + subtitlelang + '\'' +
                ", usermixno=" + usermixno +
                ", npvravailable=" + npvravailable +
                ", tsavailable=" + tsavailable +
                ", tvavailable=" + tvavailable +
                ", tvodavailable=" + tvodavailable +
                ", advertisecontent=" + advertisecontent +
                ", islocaltimeshift=" + islocaltimeshift +
                ", bitrate=" + bitrate +
                ", status=" + status +
                '}';
    }
}
