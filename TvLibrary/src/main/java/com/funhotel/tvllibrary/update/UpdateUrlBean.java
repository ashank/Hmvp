package com.funhotel.tvllibrary.update;

/**
 * Created by zhiyahan on 2016/7/20.
 */
public class UpdateUrlBean {
    /**
     * result : 0
     * update : 1
     * version : v1.1
     * download : http://10.0.2.187:8080/img/2016/09/18/10_58_53_11864ab3ac6db6d4c33b4feeb83166fa5f9.apk
     */

    private int result;//访问结果
    private int update;//是否需要更新
    private String downLoadUrl;//下载地址
    private String packageName;//包名
    private String versionName;//版本号名
    private int versionCode;//版本号
    private String appName;//apk名称

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }


    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }



    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }



/*
    *//**
     * resultCode : 0000
     * message : ??
     * data : {"url":"http://172.16.0.88:8080/apk/getApkInfo"}
     *//*

    private int resultCode;
    private String message;
    *//**
     * url : http://172.16.0.88:8080/apk/getApkInfo
     *//*

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }*/


}
