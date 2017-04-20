package com.funhotel.tvllibrary.application;

/**
 * Created by LIQI on 2016/10/25.
 */

public class FiberHomeChannelData {

    private String ChannelID;
    private String ChannelName;
    private String UserChannelID;
    private String ChannelURL;
    private String TimeShift;
    private String ChannelSDP;
    private String TimeShiftURL;
    private String ChannelLogURL;
    private String ChannelLogoURL;
    private String PositionX;
    private String PositionY;
    private String BeginTime;
    private String Interval;
    private String Lasting;
    private String ChannelType;
    private String ChannelPurchased;
    private String NativeData;

    public String getChannelID() {
        return ChannelID;
    }

    public void setChannelID(String channelID) {
        ChannelID = channelID;
    }

    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
    }

    public String getUserChannelID() {
        return UserChannelID;
    }

    public void setUserChannelID(String userChannelID) {
        UserChannelID = userChannelID;
    }

    public String getChannelURL() {
        return ChannelURL;
    }

    public void setChannelURL(String channelURL) {
        ChannelURL = channelURL;
    }

    public String getTimeShift() {
        return TimeShift;
    }

    public void setTimeShift(String timeShift) {
        TimeShift = timeShift;
    }

    public String getChannelSDP() {
        return ChannelSDP;
    }

    public void setChannelSDP(String channelSDP) {
        ChannelSDP = channelSDP;
    }

    public String getTimeShiftURL() {
        return TimeShiftURL;
    }

    public void setTimeShiftURL(String timeShiftURL) {
        TimeShiftURL = timeShiftURL;
    }

    public String getChannelLogURL() {
        return ChannelLogURL;
    }

    public void setChannelLogURL(String channelLogURL) {
        ChannelLogURL = channelLogURL;
    }

    public String getChannelLogoURL() {
        return ChannelLogoURL;
    }

    public void setChannelLogoURL(String channelLogoURL) {
        ChannelLogoURL = channelLogoURL;
    }

    public String getPositionX() {
        return PositionX;
    }

    public void setPositionX(String positionX) {
        PositionX = positionX;
    }

    public String getPositionY() {
        return PositionY;
    }

    public void setPositionY(String positionY) {
        PositionY = positionY;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public String getInterval() {
        return Interval;
    }

    public void setInterval(String interval) {
        Interval = interval;
    }

    public String getLasting() {
        return Lasting;
    }

    public void setLasting(String lasting) {
        Lasting = lasting;
    }

    public String getChannelType() {
        return ChannelType;
    }

    public void setChannelType(String channelType) {
        ChannelType = channelType;
    }

    public String getChannelPurchased() {
        return ChannelPurchased;
    }

    public void setChannelPurchased(String channelPurchased) {
        ChannelPurchased = channelPurchased;
    }

    public String getNativeData() {
        return NativeData;
    }

    public void setNativeData(String nativeData) {
        NativeData = nativeData;
    }

    @Override
    public String toString() {
        return "FiberHomeChannelData{" +
                "ChannelID='" + ChannelID + '\'' +
                ", ChannelName='" + ChannelName + '\'' +
                ", UserChannelID='" + UserChannelID + '\'' +
                ", ChannelURL='" + ChannelURL + '\'' +
                ", TimeShift='" + TimeShift + '\'' +
                ", ChannelSDP='" + ChannelSDP + '\'' +
                ", TimeShiftURL='" + TimeShiftURL + '\'' +
                ", ChannelLogURL='" + ChannelLogURL + '\'' +
                ", ChannelLogoURL='" + ChannelLogoURL + '\'' +
                ", PositionX='" + PositionX + '\'' +
                ", PositionY='" + PositionY + '\'' +
                ", BeginTime='" + BeginTime + '\'' +
                ", Interval='" + Interval + '\'' +
                ", Lasting='" + Lasting + '\'' +
                ", ChannelType='" + ChannelType + '\'' +
                ", ChannelPurchased='" + ChannelPurchased + '\'' +
                ", NativeData='" + NativeData + '\'' +
                '}';
    }
}
