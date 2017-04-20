package com.funhotel.tvllibrary.utils;

import android.os.SystemClock;
import android.text.TextUtils;
import android.text.format.Time;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName：DateTimeUtil
 * @Description：TODO 时间处理工具类
 */
public class DateTimeUtil {


    /**
     * HH:mm:ss格式
     */
    public final static String HH_mm_ss = "HH:mm:ss";

    /**
     * HH:mm格式
     */
    public final static String HH_mm = "HH:mm";

    /**
     * yyyy-MM-dd HH:mm:ss格式
     */
    public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm:ss格式
     */
    public final static String yyyyPointMMPointdd_HH_mm_ss = "yyyy.MM.dd HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm:ss格式
     */
    public final static String MM月dd日_HH_mm = "MM月dd日 HH:mm";
    /**
     * yyyy-MM-dd HH:mm格式
     */
    public final static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    /**
     * yyyyMMdd_HHmmss格式
     */
    public final static String yyyyMMdd_HHmmss = "yyyyMMdd_HHmmss";
    /**
     * yyyyMMdd.HHmmss格式
     */
    public final static String yyyyMMddPointHHmmss = "yyyyMMdd.HHmmss";
    /**
     * yyyy-MM-dd 格式
     */
    public final static String yyyy_MM_dd = "yyyy-MM-dd";


    public final static String y_M_d_H_m_s = "%Y-%m-%d %H:%M:%S";

    /**
     * yyyy-MM-dd HH:mm:ss格式
     */
    public final static String yyyyMMdd = "yyyy/MM/dd";

    public static final int mm = 60 * 1000;//一分钟
    public static final int HH = 60 * 60 * 1000;//小时
    public static final int H24 = 24 * 60 * 60 * 1000;//24小时
    public static final int H48 = 48 * 60 * 60 * 1000;//48小时
    public static final int H72 = 72 * 60 * 60 * 1000;//72小时
    public static long MILLIS_IN_MINUTE = 60000L;


    /**
     * @return String
     * @Title: convertCurrentDateToWeek
     * @Description: TODO  利用当前的时间计算出星期
     */
    public static String convertCurrentDateToWeek(String[] strings) {

        String week = null;
        Calendar calendar = Calendar.getInstance();
        Date mDate = parseDate(getSystemTimeBysetToNow(), yyyy_MM_dd);
        calendar.setTime(mDate);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex >= 1) {
            week = strings[dayIndex - 1];
        }
        return week;
    }

    /**
     * @return String(%Y-%m-%d格式)
     * @Title: getSystemTimeBysetToNow
     * @Description: TODO 取当前系统的时间
     */
    public static String getSystemTimeBysetToNow() {
        // TODO Auto-generated method stub
        Time localTime = new Time();
        localTime.setToNow();
        return localTime.format("%Y-%m-%d");
    }


    /**
     * @return String
     * @Title: getCurrentTime
     * @Description: TODO 获取当前时间,格式为 yyyy_MM_dd_HH_mm_ss
     */
    public static String getCurrentTime() {
        // TODO Auto-generated method stub
        Time localTime = new Time(Time.getCurrentTimezone());
        localTime.setToNow();
        return localTime.format(y_M_d_H_m_s);
    }

    /**
     * @return String
     * @Title: getCurrentTime
     * @Description: TODO 获取当前时间,格式为 yyyy_MM_dd_HH_mm_ss
     */
    public static String getCurrentTime(String format) {
        // TODO Auto-generated method stub
        Time localTime = new Time(Time.getCurrentTimezone());
        localTime.setToNow();
        return localTime.format(format);
    }

    /**
     * @return String(返回yyyy-MM-dd HH:mm:ss格式)
     * @Title: getSystemTimeBySystemClock
     * @Description: TODO 获取当前时间
     */
    public static String getSystemTimeBySystemClock() {
        String date = null;
        SimpleDateFormat format = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        Date currentDate = new Date(SystemClock.uptimeMillis());
        date = format.format(currentDate);
        return date;
    }

    /**
     * @return String(返回yyyy-MM-dd HH:mm:ss格式)
     * @Title: getSystemTimeBySystemClock
     * @Description: TODO 获取当前时间
     */
    public static String getSystemTimeBySystemClock(String format) {
        String date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date currentDate = new Date(SystemClock.uptimeMillis());
        date = dateFormat.format(currentDate);
        return date;
    }


    /**
     * @Title: getCurrentTimeMillis
     * @Description: TODO 获取当前的时间，单位是毫秒
     */
    public static String getCurrentTimeMillis() {
        // TODO Auto-generated method stub
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * @Title: getCurrentTimeMillis
     * @Description: TODO 获取当前的时间，单位是毫秒
     */
    public static long getCurrentTimeMillisForLong() {
        // TODO Auto-generated method stub
        return System.currentTimeMillis();
    }


    /**
     * @param datetime 时间
     * @Title: mofifyTimeByRootPermission
     * @Description: TODO 修改系统的时间 此法需要获得root权限
     */
    public static void mofifyTimeByRootPermission(String datetime) {
        try {
            //格式化时间
            String formatTime = formatTime(datetime, yyyy_MM_dd_HH_mm_ss, yyyyMMddPointHHmmss);
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(
                    process.getOutputStream());
            os.writeBytes("setprop persist.sys.timezone GMT-8\n");
            os.writeBytes("/system/bin/date -s " + formatTime + "\n");
            os.writeBytes("clock -w\n");
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param dataTime
     * @Title: mofifyTimeBySystemPermission
     * @Description: TODO  修改系统的时间 通过system权限
     */
    public static void mofifyTimeBySystemPermission(String dataTime) {
        try {
            Date date = parseDate(dataTime, yyyy_MM_dd_HH_mm_ss);
            long when = date.getTime();
            if (when / 1000 < Integer.MAX_VALUE) {
                SystemClock.setCurrentTimeMillis(when);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * @param date
     * @return ArrayList<Integer>
     * @Title: getTime
     * @Description: TODO 根据时间来获得时间的年、月、日、时、分、秒
     */
    @SuppressWarnings({"deprecation", "unused"})
    private static ArrayList<Integer> getTime(String date) {
        ArrayList<Integer> alList = new ArrayList<Integer>();
        try {
            SimpleDateFormat format = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
            Date end_date = format.parse(date);
            alList.add(end_date.getYear());
            alList.add(end_date.getMonth() + 1);
            alList.add(end_date.getDay());
            alList.add(end_date.getHours());
            alList.add(end_date.getMinutes());
            alList.add(end_date.getSeconds());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return alList;
    }

    /**
     * @param endTime   结束的时间
     * @param startTime 开始的时间
     * @return String
     * @Title: getDelta
     * @Description: TODO 获取时间差
     */
    public static String getTimeDelta(String endTime, String startTime) {
        // TODO Auto-generated method stub
        String serverTime = null;
        try {
            if (null == endTime || endTime.equals("") || null == startTime || startTime.equals("")) {
                serverTime = "00:00:00";
            } else {
                Date end_date = parseDate(endTime, yyyy_MM_dd_HH_mm_ss);
                Date start_date = parseDate(startTime, yyyy_MM_dd_HH_mm_ss);
                long l = end_date.getTime() - start_date.getTime();
                long hour = l / (60 * 60 * 1000);
                long min = ((l / (60 * 1000)) - hour * 60);
                long s = (l / 1000 - hour * 60 * 60 - min * 60);
                serverTime = hour + ":" + min + ":" + s;
                serverTime = formatTime(serverTime, HH_mm_ss, HH_mm_ss);
            }
        } catch (Exception e) {
            // TODO: handle exception
            DebugUtil.e("DateTimeUtil---getDelta--" + e.getMessage());
        }
        return serverTime;
    }

    /**
     * @param endTime   结束的时间
     * @param startTime 开始的时间
     * @return int   毫秒
     * @Title: getDelta
     * @Description: TODO 获取时间差
     */
    public static int getTimeDelta11(String endTime, String startTime) {
        // TODO Auto-generated method stub
        int l = 0;
        try {
            if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                Date end_date = parseDate(endTime, yyyy_MM_dd_HH_mm_ss);
                Date start_date = parseDate(startTime, yyyy_MM_dd_HH_mm_ss);
                l = (int) (end_date.getTime() - start_date.getTime());
            } else {
                l = 0;
            }
        } catch (Exception e) {
            // TODO: handle exception
            DebugUtil.e("DateTimeUtil---getDelta--" + e.getMessage());
        }
        DebugUtil.d("自定义计算事件的时长 = " + l);
        return l;
    }

    /**
     * @param endTime   结束的时间 yyyy.MM.dd HH_mm_ss
     * @param startTime 开始的时间 yyyy.MM.dd HH_mm_ss
     * @return int   毫秒
     * @Title: getDelta
     * @Description: TODO 获取时间差
     */
    public static int getTimeDelta12(String endTime, String startTime) {
        // TODO Auto-generated method stub
        int l = 0;
        try {
            if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                Date end_date = parseDate(endTime, yyyy_MM_dd_HH_mm_ss);
                Date start_date = parseDate(startTime, yyyyPointMMPointdd_HH_mm_ss);
                l = (int) (end_date.getTime() - start_date.getTime());
            } else {
                l = 0;
            }
        } catch (Exception e) {
            // TODO: handle exception
            DebugUtil.e("DateTimeUtil---getDelta--" + e.getMessage());
        }
        DebugUtil.d("自定义计算事件的时长 = " + l);
        return l;
    }
    /**
     * @param endTime   结束的时间 yyyy.MM.dd HH_mm_ss
     * @param startTime 开始的时间 yyyy.MM.dd HH_mm_ss
     * @return int   毫秒
     * @Title: getDelta
     * @Description: TODO 获取时间差
     */
    public static int getTimeDelta13(String endTime, String startTime) {
        // TODO Auto-generated method stub
        int l = 0;
        try {
            if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                Date end_date = parseDate(endTime, "HH:mm");
                Date start_date = parseDate(startTime, "HH:mm");
                l = (int) (end_date.getTime() - start_date.getTime());
            } else {
                l = 0;
            }
        } catch (Exception e) {
            // TODO: handle exception
            DebugUtil.e("DateTimeUtil---getDelta--" + e.getMessage());
        }
        DebugUtil.d("自定义计算事件的时长 = " + l);
        return l;
    }

    /**
     * @param fileTime   文件生成的时间 格式为 yyyyMMdd_HHmmss
     * @param serverTime 当前的系统时间  格式为  yyyy-MM-dd HH:mm:ss
     * @return boolean
     * @Title: isOver
     * @Description: TODO 判断文件生成的时间是否超过一周
     */
    public static boolean isTimeOver(String fileTime, String serverTime, int overTime) {
        // TODO Auto-generated method stub
        try {
            if ((null != fileTime && !fileTime.equals(""))
                    && (null != serverTime && !serverTime.equals(""))) {
                Date fileDate = parseDate(fileTime, yyyyMMdd_HHmmss);
                Date serverDate = parseDate(serverTime, yyyy_MM_dd_HH_mm_ss);
                long l = serverDate.getTime() - fileDate.getTime();
                long day = l / (60 * 60 * 1000 * 24);
                if (day - overTime >= 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            DebugUtil.e("DateTimeUtil---getDelta02--" + e.getMessage());
            return false;
        }
    }

    /**
     * 时间比较
     *
     * @param str1     开始时间 格式yyyy-MM-dd HH:mm
     * @param str2     结束时间 格式yyyy-MM-dd HH:mm
     * @param interval 时间间隔
     * @return
     */
    public static boolean timeCompare(String str1, String str2, long interval) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
            Date d1 = dateFormat.parse(str1);
            Date d2 = dateFormat.parse(str2);
            long diff = d2.getTime() - d1.getTime();
            DebugUtil.i("时间间隔--》" + diff);
            if (diff < interval) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * @param timeString
     * @param bFormatStyle 转化前的时间格式
     * @param aFormatStyle 要转化的时间格式
     * @return String
     * @Title: formatTime
     * @Description: TODO 转换时间的显示格式
     */
    public static String formatTime(String timeString, String bFormatStyle, String aFormatStyle) {
        // TODO Auto-generated method stub
        String serverTime = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(bFormatStyle);
            //把String的时间按照bFormatStyle转化成Date
            Date date = format.parse(timeString);
            SimpleDateFormat afterDateFormat = new SimpleDateFormat(aFormatStyle);
            //把时间按照aFormatStyle形式格式化
            serverTime = afterDateFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            DebugUtil.e("DateTimeUtil---formatTime--" + e.getMessage());
        }
        return serverTime;
    }

    /**
     * @return Date
     * @Title: parseDate
     * @Description: TODO 把时间转化成另一种时间格式
     */
    public static Date parseDate(String time, String formatStle) {
        SimpleDateFormat format = new SimpleDateFormat(formatStle);
        Date datetime = null;
        try {
            datetime = format.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return datetime;
    }

    /**
     * @param date
     * @return String
     * @Title: getMonth
     * @Description: TODO 获取月份
     */
    public static String getMonth(String date) {
        String month = null;
        month = date.substring(date.indexOf("-") + 1, date.lastIndexOf("-"));
        return month;
    }

    /**
     * @param date
     * @return String
     * @Title: getDay
     * @Description: TODO 获取哪一天
     */
    public static String getDay(String date) {
        String day = null;
        day = date.substring(date.lastIndexOf("-") + 1, date.indexOf(" "));
        return day;
    }

    /**
     * 对比时间处理时间
     * 时间规则
     * 刚刚：<=1分钟
     * XX分钟：<1小时
     * XX小时：<24小时
     * 昨天：>=24小时 ，<48小时
     * 前天：>=48小时，<72小时
     * yy-MM-dd HH:mm：>=72小时
     *
     * @param time
     */
    public static String dealWithTime(String time) {
        String timeString = "";
        try {
            if (!TextUtils.isEmpty(time)) {
                Date end_date = parseDate(time, yyyy_MM_dd_HH_mm_ss);
                //获得时间差
                long l = System.currentTimeMillis() - end_date.getTime();
                long hour = l / (60 * 60 * 1000);
                long min = ((l / (60 * 1000)) - hour * 60);
                long s = (l / 1000 - hour * 60 * 60 - min * 60);
                if (l <= mm) {
                    timeString = "刚刚";
                } else if (l > mm && l < HH) {
                    timeString = (int) min + "分钟";
                } else if (l >= HH && l < H24) {
                    timeString = (int) hour + "小时";
                } else if (l >= H24 && l < H48) {
                    timeString = "昨天";
                } else if (l >= H48 && l < H72) {
                    timeString = "前天";
                } else if (l >= H72) {
                    timeString = time;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            DebugUtil.e("DateTimeUtil---getDelta--" + e.getMessage());
        }
        return timeString;
    }


    /**
     * 4.0.0版本统一的时间规格
     * 对比时间处理时间
     * 时间规则
     * 刚刚：<=5分钟
     * XX分钟前：<1小时
     * XX小时前：<24小时
     * 月日 ：>=24小时
     *
     * @param time 2016-08-22 01:12:07
     */
    public static String dealWithTime1(String time) {
        String timeString = "";
        try {
            if (!TextUtils.isEmpty(time)) {
                Date end_date = parseDate(time, yyyy_MM_dd_HH_mm_ss);
                //获得时间差
                long l = System.currentTimeMillis() - end_date.getTime();
                long hour = l / (60 * 60 * 1000);
                long min = ((l / (60 * 1000)) - hour * 60);
                long s = (l / 1000 - hour * 60 * 60 - min * 60);
                if (l <= mm) {
                    timeString = "刚刚";
                } else if (l > mm && l < HH) {
                    timeString = (int) min + "分钟前";
                } else if (l >= HH && l < H24) {
                    timeString = (int) hour + "小时前";
                } else if (l >= H24) {
                    timeString = time.substring(5, time.length() - 9);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            DebugUtil.e("DateTimeUtil---getDelta--" + e.getMessage());
        }
        return timeString;
    }


    /**
     * 对比时间处理时间
     * 时间规则
     * 刚刚：<=1分钟
     * XX分钟：<1小时
     * XX小时：<24小时
     * 昨天：>=24小时 ，<48小时
     * 前天：>=48小时，<72小时
     * yy-MM-dd HH:mm：>=72小时
     *
     * @param time1
     * @param time2
     */
    public static String dealWithTime(String time1, String time2) {
        String timeString = "";
        try {
            if (!TextUtils.isEmpty(time1) && !TextUtils.isEmpty(time2)) {
                Date date1 = parseDate(time1, yyyy_MM_dd_HH_mm_ss);
                Date date2 = parseDate(time2, yyyy_MM_dd_HH_mm_ss);
                //获得时间差
                long l = date2.getTime() - date1.getTime();
                long hour = l / (60 * 60 * 1000);
                long min = ((l / (60 * 1000)) - hour * 60);
                long s = (l / 1000 - hour * 60 * 60 - min * 60);
                if (l <= mm) {
                    timeString = "刚刚";
                } else if (l > mm && l < HH) {
                    timeString = (int) min + "分钟";
                } else if (l >= HH && l < H24) {
                    timeString = (int) hour + "小时";
                } else if (l >= H24 && l < H48) {
                    timeString = "昨天";
                } else if (l >= H48 && l < H72) {
                    timeString = "前天";
                } else if (l >= H72) {
                    timeString = time1;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            DebugUtil.e("DateTimeUtil---getDelta--" + e.getMessage());
        }
        return timeString;

    }


    /**
     * 聊天时间处理时间
     *
     * @param time
     * @param time
     */
    public static String dealWithCHatTime(String time) {
        String timeString = "";
        try {
            if (!TextUtils.isEmpty(time)) {
                Date date1 = parseDate(time, yyyy_MM_dd_HH_mm_ss);
                //获得时间差
                long l = System.currentTimeMillis() - date1.getTime();
                long hour = l / (60 * 60 * 1000);
                long min = ((l / (60 * 1000)) - hour * 60);
                long s = (l / 1000 - hour * 60 * 60 - min * 60);
                if (l <= H24) {
                    //今天
                    String timeString1 = formatTime(time, yyyy_MM_dd_HH_mm_ss, HH_mm_ss);
                    if (!TextUtils.isEmpty(timeString1)) {
                        timeString = timeString1.substring(0, timeString1.length() - 3);
                    } else {
                        timeString = timeString1;
                    }
                } else if (l > H24) {
                    ///今天
                    if (!TextUtils.isEmpty(time)) {
                        timeString = formatTime(time, yyyy_MM_dd_HH_mm_ss, yyyy_MM_dd_HH_mm);
                    } else {
                        timeString = time;
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            DebugUtil.e("DateTimeUtil---getDelta--" + e.getMessage());
        }
        return timeString;

    }


    public static int equalTime(String time1, String time2) {
        int l = 0;
        try {
            if (!TextUtils.isEmpty(time1) && !TextUtils.isEmpty(time2)) {
                Date date1 = parseDate(time1, yyyy_MM_dd_HH_mm_ss);
                Date date2 = parseDate(time2, yyyy_MM_dd_HH_mm_ss);
                //获得时间差
                l = (int) (date2.getTime() - date1.getTime()) / (1000 * 60);
            }
        } catch (Exception e) {
            // TODO: handle exception
            DebugUtil.e("DateTimeUtil---getDelta--" + e.getMessage());
        }
        return l;
    }


    /**
     * 是否是相同的时间
     *
     * @param date1
     * @param date2
     * @return
     */
    public boolean isSameMinuteOfMillis(long date1, long date2) {
        long minute1 = date1 / MILLIS_IN_MINUTE;
        long minute2 = date2 / MILLIS_IN_MINUTE;
        return minute1 == minute2;
    }

    /**
     * 获取两个时间天数
     *
     * @param beforeTime
     * @param afterTime
     */
    public static int getDay(String beforeTime, String afterTime) {
        int day = 0;
        try {
            if (!TextUtils.isEmpty(beforeTime) && !TextUtils.isEmpty(afterTime)) {
                Date date1 = parseDate(beforeTime, yyyyMMdd);
                Date date2 = parseDate(afterTime, yyyyMMdd);
                //获得时间差
                long l = date2.getTime() - date1.getTime();
                long hour = l / (60 * 60 * 1000);
                day = (int) hour / 24;
            }
        } catch (Exception e) {
            // TODO: handle exception
            DebugUtil.e("DateTimeUtil---getDelta--" + e.getMessage());
        }
        return day;

    }

    /**
     * 获取两个时间天数
     *
     * @param beforeTime
     * @param afterTime
     */
    public static int getDay(String beforeTime, String afterTime, String format) {
        int day = 0;
        try {
            if (!TextUtils.isEmpty(beforeTime) && !TextUtils.isEmpty(afterTime)) {
                Date date1 = parseDate(beforeTime, format);
                Date date2 = parseDate(afterTime, format);
                //获得时间差
                long l = date2.getTime() - date1.getTime();
                long hour = l / (60 * 60 * 1000);
                day = (int) hour / 24;
            }
        } catch (Exception e) {
            // TODO: handle exception
            DebugUtil.e("DateTimeUtil---getDelta--" + e.getMessage());
        }
        return day;

    }


    /**
     * @param date
     * @return ArrayList<Integer>
     * @Title: getTime
     * @Description: TODO 计算年龄
     */
    public static int getAge(String date) {
        int age = 21;
        if (TextUtils.isEmpty(date)) {
            return age;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(yyyy_MM_dd);
            Date end_date = format.parse(date);
            Date nowDate = new Date();
            age = nowDate.getYear() - end_date.getYear();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DebugUtil.i("DateTimeUtil>>>>>getAge>>>" + age);
        return age;
    }

    /**
     * 活动时间转化 、yyyy_MM_dd_HH_mm_ss转化yyyy_MM_dd_HH_mm
     *
     * @param timeString
     * @return
     */
    public static String activityTime(String timeString) {
        String time = "";
        try {
            time = formatTime(timeString, yyyy_MM_dd_HH_mm_ss, yyyy_MM_dd_HH_mm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 活动时间转化 、yyyy_MM_dd_HH_mm_ss转化MM月dd日 HH：mm
     *
     * @param timeString
     * @return
     */
    public static String activityTime1(String timeString) {
        String time = "";
        try {
            time = formatTime(timeString, yyyy_MM_dd_HH_mm_ss, MM月dd日_HH_mm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     *  判断当前时间是否在某个时间段内
     * @param begintime 开始时间
     * @param endtime 结束时间
     * @param currenttime 当前时间
     * @return
     */
    public static boolean betweenTime(String begintime,String endtime,String currenttime){
        try {
            if (!TextUtils.isEmpty(begintime) && !TextUtils.isEmpty(endtime) && !TextUtils.isEmpty(currenttime)){
                SimpleDateFormat dateFormat = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
                Date d1 = dateFormat.parse(begintime);
                Date d2 = dateFormat.parse(endtime);
                Date d3 = dateFormat.parse(currenttime);
                long time1 = d1.getTime();
                long time2 = d2.getTime();
                long time3 = d3.getTime();
                if (time3>=time1 && time3<=time2){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     *  判断当前时间是否在某个时间段内
     * @param begintime 开始时间
     * @param endtime 结束时间
     * @param currenttime 当前时间
     * @return
     */
    public static boolean betweenTime2(String begintime,String endtime,String currenttime){
        try {
            if (!TextUtils.isEmpty(begintime) && !TextUtils.isEmpty(endtime) && !TextUtils.isEmpty(currenttime)){
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                Date d1 = dateFormat.parse(begintime);
                Date d2 = dateFormat.parse(endtime);
                Date d3 = dateFormat.parse(currenttime);
                long time1 = d1.getTime();
                long time2 = d2.getTime();
                long time3 = d3.getTime();
                if (time3>=time1 && time3<time2){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
