package com.funhotel.mvp.common;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by zhiyahan on 2017/3/24.
 */

public  class BaseModel<T> implements Serializable{

    public static final int RESULT_OK=0;
    private int result;
    private String message;
    @SerializedName("adinfos")
    public T data;

    public  int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "BaseModel{" +
                "result=" + result +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean isSuccess(){
        if (result==RESULT_OK){
            return true;
        }else {
            return false;
        }
    }






}
