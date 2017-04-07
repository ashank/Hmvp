package com.funhotel.mvp.module.http;

/**
 * Created by zhiyahan on 2017/3/28.
 *
 * http异常提示
 */

public class ApiException extends Exception {

    private int code;
    private String message;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
