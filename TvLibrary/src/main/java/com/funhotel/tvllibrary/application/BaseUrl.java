package com.funhotel.tvllibrary.application;

/**
 * @Title: UrlConstant
 * @Description: Http url地址 集合类
 * @author: zhangyetao
 * @data: 2016/4/17 11:27
 */
public class BaseUrl {

    /**
     * 测试IP
     * */

    public static final String BASE_URL ="http://10.0.2.188:8888";//河北正式接口
//    public static final String BASE_URL ="http://120.76.77.152:8888";//河北测试接口
//    public static final String BASE_URL ="http://120.24.152.240:8888";//全国测试接口
//    public static final String BASE_URL ="http://172.16.0.88:8080";//公司测试接口

    public static final String PROVINCE_INTERFACE="/hebei-iptv-interface/";

    public static final String APK="apk/";



    /**
     * 广告接口
     */
    public static final String ADVERTISE_URL =BASE_URL+"/adservices/getads";

    /**
     * 首页接口
     */
//    http://172.16.0.88:8080/hubei-iptv-interface/apk/queryIndex?stbId=star7
    public static final String MAIN_URL =BASE_URL+PROVINCE_INTERFACE+APK+"queryIndex?stbId=";

    /**
     * 时间接口
     */
    //http://172.16.0.88:8080/hubei-iptv-interface/apk/getTime
    public static final String TIME_URL = BASE_URL+PROVINCE_INTERFACE+APK+"getTime";

    /**
     * 内页接口
     */
    //http://172.16.0.88:8080/hubei-iptv-interface/apk/queryById?id=11
    public static final String PRODUCT_URL = BASE_URL+PROVINCE_INTERFACE+APK+"queryById?id=";
    /**
     *  获取房间号接口
     */
    public static final String ROOM_NUMBER = BASE_URL+PROVINCE_INTERFACE+APK+"queryRoomNoByStbId?stbId=";
    /**
     *  apk升级地址
     */
    public static final String UPDATE_APK_URL = BASE_URL+PROVINCE_INTERFACE+APK+"versionCheck?stbId=";
}