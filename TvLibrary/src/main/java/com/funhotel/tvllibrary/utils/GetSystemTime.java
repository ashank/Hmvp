package com.funhotel.tvllibrary.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangyetao on 2016/4/12.
 * TODO获取系统时间
 */
public class GetSystemTime {
    /**
     * 获取系统的日期
     * */
    public String getDate(){
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());
        String date = format.format(curDate);
        Log.d("日期 ：", date);
        return date;
    }

    /**
     * 获取系统的日期
     * */
    public String getDates(){

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());
        String date = format.format(curDate);
        Log.d("日期 ：", date);
        return date;
    }
    /**
     * 获取系统的时间
     * */
    public String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date curTime = new Date(System.currentTimeMillis());
        String time = format.format(curTime);
        String [] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        if (hour < 12){
            return time;
        }
        return time;
    }

    /**
     * 获取带有上午还是下午的系统的时间
     * */
    public String getTimes(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date curTime = new Date(System.currentTimeMillis());
        String time = format.format(curTime);
        String [] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        if (hour < 12){
            return "上午 "+time;
        }
        return "下午 "+time;
    }


    /**
     * 获取星期几
     * */
    public String getWeek(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(date);
        return week;
    }



}
