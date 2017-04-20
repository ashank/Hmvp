package com.funhotel.marqueetext;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

public class NoticeService extends Service {

    private static final String TAG="NoticeService";

    private Timer noticeTimer;

    private NoticeTimerTask mNoticeTimerTask;

    public NoticeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        releaseTimer();
        noticeTimer=new Timer();
        mNoticeTimerTask=new NoticeTimerTask();
        noticeTimer.schedule(mNoticeTimerTask,0,10*1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public class NoticeTimerTask extends TimerTask{

        public NoticeTimerTask() {

        }

        @Override
        public void run() {
            Log.e(TAG, "run: ");
            String url="http://172.16.0.238:8080/iptv-interface/runhourselamp/getRunhourselamps?stbId=HAINAN_121231&second=10";
            OkHttpUtils.get()
                    .url(url)
                    .build().
                    readTimeOut(10000)
                    .execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {

                    Log.e(TAG, "onError: " );
                }

                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    NoticeBean noticeBean = gson.fromJson(response, NoticeBean.class);
                    Log.e(TAG, "onResponse: " +noticeBean.toString());
                    Intent intent=new Intent();
                    intent.setAction("com.funhotel.action.notice");
                    intent.putExtra("notice",noticeBean);
                    sendBroadcast(intent);
                }
            });
        }
    }

    protected  void releaseTimer(){
        if(noticeTimer!=null){
            noticeTimer.cancel();
            noticeTimer=null;
        }

        if(mNoticeTimerTask!=null){
            mNoticeTimerTask.cancel();
            mNoticeTimerTask=null;
        }

    }






}
