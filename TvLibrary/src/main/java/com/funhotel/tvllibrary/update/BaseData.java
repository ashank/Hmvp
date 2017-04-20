package com.funhotel.tvllibrary.update;

/**
 * Created by zhiyahan on 2016/7/20.
 */
public class BaseData {

    /**
     * resultCode : 1000
     * message : ??
     * data : {"packageName":"com.iptv","versionName":"1.1","versionCode":2,"appName":"1213131","downLoadUrl":"http://localhost:8083/apk1231.apk"}
     */
    private int resultCode;
    private String message;
    private UpdateBean data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UpdateBean getData() {
        return data;
    }

    public void setData(UpdateBean data) {
        this.data = data;
    }
}
