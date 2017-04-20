package com.funhotel.tvllibrary.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.io.IOException;

/**
 *
 *
 * 适用于烽火盒子
 * Created by zhangyetao on 2016/4/21.
 * TODO: 用户ID的工具，用于获取机顶盒的业务账号，宽带账号和密码
 *
 * 获取auth_url值中输入参数section、variable对应business和authinfo
 * 获取stb_id值中输入参数section、variable对应device和stbid
 * 获取user_id值中输入参数section、variable对应business和username
 * 获取user_password值中输入参数section、variable对应business和password
 * 获取access_method值中输入参数section、variable对应network和type
 *
 */
public class FirehomeStbUserUtils {
    //文件路径
    private static final String CONFIGFILE = "/params/iptvsetting.properties";
    //认证地址
    private static final String BUSINESS_STRING = "business";
    private static final String AUTHINFO = "authinfo";
    //stbID
    private static final String DEVICE_STRING = "device";
    private static final String STBID = "stbid";
    //用户
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    //用户宽带帐号类型
    private static final String NETWORK = "network";
    private static final String TYPE = "type";


    /**
     * 调试模式，正式发版时设置为false
     */
    private boolean isDebug= true;

    /**
     * 获取认证URL地址
     * @return
     */
    public static String getAuthUrl(){
        String string="";
        try {
            string = UseIni.getProfileString (CONFIGFILE,BUSINESS_STRING, AUTHINFO, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }


    /**
     * 获取认证STBID
     * @return
     */
    public static String getStbId(){
        String string="";
        try {
            string = UseIni.getProfileString (CONFIGFILE,DEVICE_STRING, STBID, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }


    /**
     * 获取认证用户名称
     * @return
     */
    public static String getUserId(){
        String string="";
        try {
            string = UseIni.getProfileString (CONFIGFILE,BUSINESS_STRING,USERNAME, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 获取认证用户密码
     * @return
     */
    public static String getUserPassword(){
        String string="";
        try {
            string = UseIni.getProfileString (CONFIGFILE,BUSINESS_STRING,PASSWORD, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }


    /**
     * 获取宽带类型
     * @return
     */
    public static String getAcessMethod(){
        String string="";
        try {
            string = UseIni.getProfileString (CONFIGFILE,NETWORK,TYPE, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

}