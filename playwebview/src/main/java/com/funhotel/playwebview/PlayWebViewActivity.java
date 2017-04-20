package com.funhotel.playwebview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.funhotel.ijkplayer.FunhotelJsPlayer;
import com.funhotel.ijkplayer.FunhotelPlayer;
import com.funhotel.ijkplayer.IjkVideoView;
import com.funhotel.ijkplayer.MediaControllerView;
import com.funhotel.mvp.utils.DesityUtil;

public class PlayWebViewActivity extends AppCompatActivity
        implements AdvancedWebView.Listener,
        MediaPlayer.OnPlayStatusListener{
    private  final String TAG= "PlayWebViewActivity";
    private AdvancedWebView mAdvancedWebView;
    private IjkVideoView mIjkVideoView;
    private ProgressBar mProgressBar;
    private FunhotelPlayer mFunhotelPlayer;
    private  MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_webview);
        initView();
        /*initPlayer();
        start("rtmp://live.hkstv.hk.lxdns.com/live/hks");*/
        initWebView();
    }

    private  void initView(){
        mAdvancedWebView=(AdvancedWebView)findViewById(R.id.webview);
        mIjkVideoView=(IjkVideoView)findViewById(R.id.video);
        mProgressBar=(ProgressBar)findViewById(R.id.progress);
    }

    private void initPlayer(){
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.leftMargin=300;
        layoutParams.topMargin=200;
        layoutParams.width=300;
        layoutParams.height=200;
        mIjkVideoView.setLayoutParams(layoutParams);

        mFunhotelPlayer=new FunhotelPlayer(this,mIjkVideoView);
        mFunhotelPlayer.setFullScreenOnly(true);
        mFunhotelPlayer.setScaleType(FunhotelPlayer.SCALETYPE_FITXY);
        mFunhotelPlayer.live(false);
        mFunhotelPlayer.type(2);
        //设置满屏
        mFunhotelPlayer.toggleAspectRatio();
        //设置全屏播放
        mFunhotelPlayer.setFullScreenOnly(true);
        /*//完成回调
        mFunhotelPlayer.onComplete(runnable);
        //信息回掉
        mFunhotelPlayer.onInfo(onInfoListener);
        //错误回掉
        mFunhotelPlayer.onError(onErrorListener);*/
    }

    public void start(String url){
        mFunhotelPlayer.play(url);
    }

    public void initWebView(){
       mAdvancedWebView.setGeolocationEnabled(true);
        //设置webview的背景
        mAdvancedWebView.setBackgroundColor(0);
        mediaPlayer=new MediaPlayer(this);
        mediaPlayer.setIjkVideoView(mIjkVideoView);
        mediaPlayer.setOnPlayStatusListener(this);
        mAdvancedWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mAdvancedWebView.setListener(this, this);
        mAdvancedWebView.addJavascriptInterface(mediaPlayer,"mp");
        mAdvancedWebView.loadUrl("http://172.16.0.88:888/test/play.html");
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        mProgressBar.setVisibility(View.VISIBLE);


    }

    @Override
    public void onPageFinished(String url) {
        /*mAdvancedWebView.addJavascriptInterface(mediaPlayer,"mp");*/
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        //下载的过程

    }

    @Override
    public void onExternalPageRequest(String url) {
        //加载

    }

    @Override
    public void onPlayStatus(int status) {
        mAdvancedWebView.loadUrl("javascript:playStatus("+status+")");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG, "onKeyDown: " );
      /*mAdvancedWebView.addJavascriptInterface(mediaPlayer,"mp");
        mAdvancedWebView.loadUrl("http://172.16.0.88:888/test/play.html");*/
        mAdvancedWebView.loadUrl("javascript:onKeyDown("+keyCode+")");
        return false;
    }
}
