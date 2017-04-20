package com.funhotel.tvllibrary.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by zhangyetao on 2016/4/21.
 * TODO: 用户ID的工具，用于获取机顶盒的业务账号，宽带账号和密码
 */
public class UserIdUtils {

    private ContentResolver resolver;
    private static final String CONTENT_URI="content://com.starcor.mango.hndx.provider/deviceinfo";
    private static final String USER_ID="user_id";
    private static final String STB_ID="stb_id";
    private static UserIdUtils application;

    /**
     * 省份地区
     * 正式发版时,请检查是否属于对应的地区，并修改该
     */
    private String province=PRO_HEIBEI;
    private static String PRO_HEIBEI="heibei";
    private static String PRO_HUNAN="hunan";
    private static String DEBUG="debug";//调试模式
    private static String debugUserID="huanlv001";


    public static UserIdUtils newInstance(Context context) {
        if (null==application){
            application = new UserIdUtils(context);
        }
        return application;
    }

    public UserIdUtils(Context context) {
        if (null==resolver){
            resolver = context.getContentResolver();
        }
    }


    /**
     * 获取认证URL地址
     * @return
     */
    public  String getAuthUrl(){
        if (TextUtils.equals(province,PRO_HEIBEI)){
            return FirehomeStbUserUtils.getAuthUrl();
        }else if (TextUtils.equals(province,PRO_HUNAN)) {
            return "";
        }else if (TextUtils.equals(province,DEBUG)) {
            //如果是空的，或者调试模式，则返回空
            return "";
        }else {
            //如果是空的，或者没有的地区，则返回空
            return "";
        }
    }

    /**
     * 获取用户ID
     */
    public String getUserId() {
        if (TextUtils.equals(province,PRO_HEIBEI)){
            return FirehomeStbUserUtils.getUserId();
        }else if (TextUtils.equals(province,PRO_HUNAN)) {
            return getContent(USER_ID);
        }else if (TextUtils.equals(province,DEBUG)) {
            //如果是空的，或者调试模式，则返回调试账号
            return debugUserID;
        }else {
            //如果是空的，或者没有的地区，则返回调试账号
            return debugUserID;
        }
    }

    /**
     * 获取机顶盒序列号
     * */
    public String getStbId() {

        if (TextUtils.equals(province,PRO_HEIBEI)){
            return FirehomeStbUserUtils.getStbId();
        }else if (TextUtils.equals(province,PRO_HUNAN)) {
            return getContent(STB_ID);
        }else if (TextUtils.equals(province,DEBUG)) {
            //如果是空的，或者调试模式，则返回空值
            return "";
        }else {
            //如果是空的，或者没有的地区，则返回空值
            return "";
        }
    }


    /**
     * 获取认证用户密码
     * @return
     */
    public String getUserPassword(){
        if (TextUtils.equals(province,PRO_HEIBEI)){
            return FirehomeStbUserUtils.getUserPassword();
        }else if (TextUtils.equals(province,PRO_HUNAN)) {
            return "";
        }else if (TextUtils.equals(province,DEBUG)) {
            //如果是空的，或者调试模式，则返回空值
            return "";
        }else {
            //如果是空的，或者没有的地区，则返回空值
            return "";
        }
    }


    /**
     * 获取宽带类型
     * @return
     */
    public  String getAcessMethod(){
        if (TextUtils.equals(province,PRO_HEIBEI)){
            return FirehomeStbUserUtils.getAcessMethod();
        }else if (TextUtils.equals(province,PRO_HUNAN)) {
            return "";
        }else if (TextUtils.equals(province,DEBUG)) {
            //如果是空的，或者调试模式，则返回空值
            return "";
        }else {
            //如果是空的，或者没有的地区，则返回空值
            return "";
        }
    }



    /**
     * 通过键名，查找对应的值
     * @param contentKey
     * @return 返回键值名对应的值，如果表不存在，则键值为空。
     */
    private String getContent(String contentKey){
        if (null==resolver){
            return "";
        }
        Uri uri = Uri.parse(CONTENT_URI);
        Cursor cursor = resolver.query(uri, null, null, null, null);
        String content ="";
        if (null!=cursor){
            while (cursor.moveToNext()) {
                content = cursor.getString(cursor.getColumnIndex(contentKey));
            }
            cursor.close();
        }
        DebugUtil.e("UserIdUtils>>getContent>>" + content);
        return content;
    }
}
