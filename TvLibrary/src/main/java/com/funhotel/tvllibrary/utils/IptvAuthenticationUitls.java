package com.funhotel.tvllibrary.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * 获取烽火的认证信息
 * Created by LIQI on 2016/10/25.
 */

public class IptvAuthenticationUitls {

    /**
     * 通过key获取对应的信息
     * @param key
     * @return
     */
    public static String getAutoInfo(Context context, String key){
        String value="";
        Uri uri = Uri.parse("content://stbconfig/authentication");
        Cursor cursor = context.getContentResolver().query(uri,null,null,null,null);
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                if(name.equals(key)) {
                    value = cursor.getString(cursor.getColumnIndex("value"));
                    break;
                }
            }
            cursor.close();
        }
        return value;
    }
}
