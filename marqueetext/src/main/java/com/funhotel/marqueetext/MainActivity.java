package com.funhotel.marqueetext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dalong.marqueeview.MarqueeView;
import com.funhotel.tvllibrary.view.AutoScrollTextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MyReceiver myReceiver;

    private TextView  tvNotcie;
    private AutoScrollTextView mAutoScrollTextView;

    private MarqueeView mMarqueeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNotcie=(TextView)findViewById(R.id.tv_notice);
        mAutoScrollTextView=(AutoScrollTextView) findViewById(R.id.tv_roll);

        //公告广播
        myReceiver=new MyReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.funhotel.action.notice");
        registerReceiver(myReceiver,intentFilter);

        //公告服务
        Intent intent = new Intent(this, NoticeService.class);
        startService(intent);

        mMarqueeView=(MarqueeView) findViewById(R.id.mMarqueeView);
        mMarqueeView.requestFocus();
        mMarqueeView.setText("走过平湖烟雨,岁月山河,那些历尽劫数、尝遍百味的人,会更加生动而干净");
        mMarqueeView.startScroll();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    public class MyReceiver extends BroadcastReceiver {

        public MyReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TAG", "onReceive: ");
            if (intent.getAction().equals("com.funhotel.action.notice")) {
                NoticeBean noticeBean = (NoticeBean) intent.getSerializableExtra("notice");
                if (noticeBean == null || noticeBean.getDatas().size() == 0) {
                    tvNotcie.setVisibility(View.GONE);
                }else {
                    releaseTimer();
                    noticeTimer=new Timer();
                    mNoticeTimerTask=new NoticeTimerTask(noticeBean);
                    noticeTimer.schedule(mNoticeTimerTask,0,10*1000);
                }
            }
        }
    }

    private Timer noticeTimer;
    private NoticeTimerTask mNoticeTimerTask;
    private Handler mNoticeHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    NoticeBean.DatasBean bean=(NoticeBean.DatasBean)msg.obj;
                    tvNotcie.setText(bean.getTitle());
                    //
                    mAutoScrollTextView.setBackgroundColor(R.color.color_87FF00);
                    mAutoScrollTextView.setTextColor(R.color.CFFFFFF);
                    mAutoScrollTextView.setVisibility(View.VISIBLE);
                    mAutoScrollTextView.setText(bean.getTitle());
                    //设置滚动
                    mAutoScrollTextView.requestFocus();
                    mAutoScrollTextView.init(getWindowManager(),R.color.CFFFFFF);
                    mAutoScrollTextView.setEnabled(false);
                    mAutoScrollTextView.startScroll();


                    mMarqueeView.setText(bean.getTitle());
                    break;

            }
            super.handleMessage(msg);
        }
    };



    public class NoticeTimerTask extends TimerTask {
        private NoticeBean mNoticeBean;
        private int i=0;

        public NoticeTimerTask(NoticeBean noticeBean) {
            this.mNoticeBean=noticeBean;
        }

        @Override
        public void run() {
            Log.e("TAG", "run:1 "+ SystemClock.currentThreadTimeMillis());
            Message message=Message.obtain();
            message.what=0;
            message.obj=mNoticeBean.getDatas().get(i);
            mNoticeHandler.sendMessage(message);
            try {
                Thread.sleep((mNoticeBean.getDatas().get(i).getRestMill())/1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e("TAG", "run: 2"+ SystemClock.currentThreadTimeMillis());
            i++;
            if (i>mNoticeBean.getDatas().size()-1){
                cancel();
            }
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
