package com.funhotel.ijkplayer.bean;

/**
 * Created by LIQI on 2016/11/4.
 */

public class AuthData {
    private int flag;
    private int errorCode;
    private String  message;
    private String data;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AuthData{" +
                "flag=" + flag +
                ", errorCode=" + errorCode +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
