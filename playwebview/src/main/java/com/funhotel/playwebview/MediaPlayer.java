package com.funhotel.playwebview;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.RelativeLayout;
import com.funhotel.ijkplayer.FunhotelJsPlayer;
import com.funhotel.ijkplayer.FunhotelPlayer;
import com.funhotel.ijkplayer.IjkVideoView;

/**
 * Created by zhiyahan on 2017/4/12.
 *
 *example on JS :
 *
 * mp.initMediaPlayer(0,0,0,1280,720,0,0,0,0,0,0,0,0,0)
 * mp.setSingleMedia("rtmp://live.hkstv.hk.lxdns.com/live/hks");
 * mp.playFromStart();
 *
 */
public class MediaPlayer implements FunhotelJsPlayer.OnInfoListener
                                    ,FunhotelJsPlayer.OnErrorListener
                                    ,Runnable{

    private static final String TAG = "FunhotelJsPlayer";
    private FunhotelJsPlayer mFunhotelPlayer;
    private IjkVideoView ijkVideoView;
    private int nativePlayerInstanceID;
    private String mediaUrl;
    private int videoDisplayMode=0;
    private int singleOrPlaylistMode;
    /**
     * 视频距离上下左右的距离
     */
    private int videoDisplayLeft,
            videoDisplayTop,
            videoDisplayWidth,
            videoDisplayHeight;
    /**
     * 设置有声或者静音标记
     */
    private int muteFlag;
    /**
     * 视频透明度
     */
    private int videoAlph;
    /**
     * 是否循环播放
     */
    private int cycleFlag;

    private int mediaDuration;

    private int currentPlayTime;

    /**
     * 播放器的当前播放模式
     */
    private int playbackMode;

    private Context mContext;

    private AudioManager audioManager;

    private RelativeLayout.LayoutParams layoutParams;


    public MediaPlayer(Context mContext) {
        Log.e(TAG, "MediaPlayer: new ");
        this.mContext = mContext;

        init();


    }

    public MediaPlayer(IjkVideoView ijkVideoView, Context context) {
        this.ijkVideoView = ijkVideoView;
        mContext = context;
        init();
    }


    private void init() {
        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

    }


    public void setIjkVideoView(IjkVideoView ijkVideoView) {
        this.ijkVideoView = ijkVideoView;
    }


    /**
     * 设置使用ijkplayer还是使用android mediaPlayer
     * @param usingAndroidPlayer  true为使用android 原生的，false为使用ijkplayer
     */
    public void setUsingAndroidPlayer(boolean usingAndroidPlayer) {
        if (mFunhotelPlayer==null){
            return;
        }
        mFunhotelPlayer.setUsingAndroidPlayer(usingAndroidPlayer);
    }

/*
    *//**
     * 终端上某个媒体流或播放器实例的标识，
     * 由STB 本地负责生成和维护，
     * 一旦针对某个新生成的 MediaPlayer 对象绑定该 ID，
     * 就意味着该对象和 STB 本地的某个媒体播放实例进行了绑定或关 联。
     * 如果无法找到nativePlayerInstanceID对应的本地播放器实例，
     * 则操作失败，返回-1。 参考：MediaPlayer 控制的本地播放器实例的生命周
     *
     * @param nativePlayerInstanceID 一个字节长度的无符号 整数，0－255
     * @return 0：表示绑定成功； -1：表示绑定失败
     *//*
    public int bindNativePlayerInstance(int nativePlayerInstanceID) {
        return 0;
    }

    *//**
     * @return 一个字节长度的无符号正整数（1－ 255）获得 STB 本地播放器实例的 instanceID。
     *//*
    @JavascriptInterface
    public int getNativePlayerInstanceID() {
        return 0;
    }*/

    /**
     * 初始化播控
     */
    @JavascriptInterface
    public void newMediaplayer() {

        if (mFunhotelPlayer==null){
            mFunhotelPlayer = new FunhotelJsPlayer(mContext, ijkVideoView);
        }
        //完成回调
        mFunhotelPlayer.onComplete(this);
        //信息回掉
        mFunhotelPlayer.onInfo(this);
        //错误回掉
        mFunhotelPlayer.onError(this);

        ijkVideoView.post(new Runnable() {
            @Override
            public void run() {

                layoutParams= new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                ijkVideoView.setLayoutParams(layoutParams);

                mFunhotelPlayer.setScaleType(FunhotelPlayer.SCALETYPE_FITXY);
                mFunhotelPlayer.live(false);
                mFunhotelPlayer.type(2);
                //设置满屏
                mFunhotelPlayer.toggleAspectRatio();
                //设置全屏播放
                mFunhotelPlayer.setFullScreenOnly(true);
            }
        });
    }


    /**
     * 初始化 MediaPlayer 的属性。
     * 当新创建的 MediaPlayer 是为了和已有的 STB 本地某个媒 体播放实例
     * （由先前其它页面中创建 MediaPlayer 对象时创建的）进行绑定，无需调 用该函数。
     *
     * @param nativePlayerinstanceID
     * @param playlistFlag
     * @param videoDisplayMode
     * @param height                 高度
     * @param width                  宽度
     * @param left                   距离左边的距离
     * @param top                    距离上边的距离
     * @param muteFlag               0：设置为有声(默认值) 1：设置为静音
     * @param useNativeUIFlag        0：不使用 Player 的本地 UI 显示功能 1：使用 Player 的本地 UI 显示功能(默 认值)可
     * @param subtitleFlag           0：不显示字幕(默认值) 1：显示字幕
     * @param videoAlpha             0－100 之间的整数值，0 表示不透明， 100 表示完全透明。 (默认值为 0)
     *                               可
     * @param cycleFlag              0：设置为循环播放 1：设置为单次播放（默认值）
     * @param randomFlag             0：设置为随机播放（默认值） 1：设置为随机播放
     */
    @JavascriptInterface
    public void initMediaPlayer(int nativePlayerinstanceID, int playlistFlag,
                                int videoDisplayMode,
                                final int height, final int width, int left, int top, int muteFlag,
                                int useNativeUIFlag, int subtitleFlag,
                                int videoAlpha, int cycleFlag,
                                int randomFlag, int autoDelFlag) {
        Log.e(TAG, "initMediaPlayer: init");
        if (mFunhotelPlayer==null){
            mFunhotelPlayer = new FunhotelJsPlayer(mContext, ijkVideoView);
        }
         //完成回调
        mFunhotelPlayer.onComplete(this);
        //信息回掉
        mFunhotelPlayer.onInfo(this);
        //错误回掉
        mFunhotelPlayer.onError(this);
        videoDisplayHeight = height;
        videoDisplayWidth = width;
        videoDisplayTop = top;
        videoDisplayLeft = left;

        Log.e(TAG, "initMediaPlayer: width:"+width+"height:"+height+" left:"+left+" top:"+top );
        ijkVideoView.post(new Runnable() {
            @Override
            public void run() {
                if (width>50&&height>30){
                    layoutParams = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.leftMargin = videoDisplayLeft;
                    layoutParams.topMargin = videoDisplayTop;
                    layoutParams.width = videoDisplayWidth;
                    layoutParams.height = videoDisplayHeight;
                    ijkVideoView.setLayoutParams(layoutParams);
                }
                mFunhotelPlayer.setScaleType(FunhotelPlayer.SCALETYPE_FITXY);
                mFunhotelPlayer.live(false);
                mFunhotelPlayer.type(2);
                //设置满屏
                mFunhotelPlayer.toggleAspectRatio();
                //设置全屏播放
                mFunhotelPlayer.setFullScreenOnly(true);

            }
        });
    }


    /**
     * 释放播控
     *
     * @param nativePlayerInstanceID
     */
    @JavascriptInterface
    public void releaseMediaPlayer(int nativePlayerInstanceID) {
        mFunhotelPlayer.onDestroy();
    }


    /**
     * 设置单个播放的媒体。
     * 传入字符串 mediaStr 中的媒体对象的 mediaURL。
     * rtsp：//的单播地址，则要求连接单播地址进行 播放；
     * igmp：//的组播地址，要求连接组播地址进行 播放；
     * http：//的地址用于播放 mp3、WAV 等音频，
     * 如http：//xxxxx/test.mp3、test.wav
     *
     * @param mediaUrl 播放地址
     */
    @JavascriptInterface
    public void setSingleMedia(String mediaUrl) {
        Log.e(TAG, "setSingleMedia: " + mediaUrl);
        this.mediaUrl = mediaUrl;

    }


    /**
     * 从媒体起始点开始播放。 对 TVchannel，以实时 TV 的方式开始播放。
     */
    @JavascriptInterface
    public void playFromStart() {
        if (mFunhotelPlayer == null) {
            Log.e(TAG, "playFromStart: null");
            return;
        }
        Log.e(TAG, "playFromStart: start");
        ijkVideoView.post(new Runnable() {
            @Override
            public void run() {
                mFunhotelPlayer.play(mediaUrl);
            }
        });

    }


    /**
     * 开始播放的方法
     */
    @JavascriptInterface
    public void start(){
        if (mFunhotelPlayer==null)
            return;
        mFunhotelPlayer.start();
    }




    /**
     * 在某个时间段播放
     *
     * @param type：1：参见     RFC2326 中的 Normal Play Time (NPT)；
     *                      2：参见 RFC2326 中的 Absolute Time (Clock Time) z
     * @param timestamp     ：参见 RFC2326 中的 Normal Play Time (NPT)
     *                      和 Absolute Time (Clock Time)两种时间类型的格 式。
     *                      timestamp：对 VoD 而言是从媒体起始点开 始计算的相对时间；
     *                      对 TVoD 等有时间基的媒体 而言就是绝对时间。
     * @param speed：播放速度，可选 参数
     */
   /* @JavascriptInterface
    public void playByTime(int type, String timestamp, int speed) {
        if (mFunhotelPlayer == null)
            return;
    }*/


    /**
     * 暂停
     */
    @JavascriptInterface
    public void pause() {
        if (mFunhotelPlayer == null)
            return;
        mFunhotelPlayer.pause();

    }


    /**
     * 快进
     * @param speed Float 类型，2 至 32
     */
    @JavascriptInterface
    public void fastForward(float speed) {
        if (mFunhotelPlayer == null)
            return;
        mFunhotelPlayer.forward(speed);
    }


    /**
     * 快退
     * @param speed Float 类型，-2 至 -32
     */
    @JavascriptInterface
    public void fastRewind(float speed) {
        if (mFunhotelPlayer == null)
            return;
        mFunhotelPlayer.forward(speed);
    }


    /**
     * 从暂停或者停止状态恢复到播放状态
     */
    @JavascriptInterface
    public void resume() {
        if (mFunhotelPlayer == null)
            return;
        mFunhotelPlayer.onResume();
    }

    /**
     * 停止
     */
    @JavascriptInterface
    public void stop() {
        if (mFunhotelPlayer == null)
            return;
        mFunhotelPlayer.stop();
    }


    /**
     * 根据 videoDisplayMode,vedioDisplayArea 属性,调整视频的显示。
     * 所以设定 Area 和 Mode 参 数后并不是立即生效，
     * 而是要在显式调用该函数 后才会生效.
     */
    @JavascriptInterface
    public void refreshVideoDisplay() {


    }


    /**
     * 设置系统音量
     *
     * @param volume 0-100，表示音量， 0 为静音，100 为大音量。
     */
    @JavascriptInterface
    public void setVolume(int volume) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
    }


    /**
     * 获取当前系统音量
     *
     * @return 当前系统音量， 0-100
     */
    @JavascriptInterface
    public int getVolume() {
        Log.e(TAG, "getVolume: "+audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) );
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }


    /**
     * 是否在播放中
     *
     * @return 返回是否在播放的状态
     */
    @JavascriptInterface
    public boolean isPlaying() {
        if (mFunhotelPlayer == null)
            return false;
        return mFunhotelPlayer.isPlaying();
    }

    /**
     * 0：单媒体的播放模式(默认值)
     * 1：播放列表的播放模式
     *
     * @param mode
     */
    @JavascriptInterface
    public void setSingleOrPlaylistMode(int mode) {
        this.singleOrPlaylistMode = mode;
    }

    /**
     * @return 播放模式
     * @see {setSingleOrPlaylistMode}
     */
    @JavascriptInterface
    public int getSingleOrPlaylistMode() {
        return singleOrPlaylistMode;
    }

    /**
     * 0：按 setVideoDisplayArea()中设定的 Height,Width,Left,Top 属性所指定的 位置和大小来显示视频
     * 1：全屏显示，按全屏高度和宽度显示 (默认值)
     * 2：按宽度显示，指在不改变原有图像 纵横比的情况下按全屏宽度显示
     * 3：按高度显示，指在不改变原有图像 纵横比的情况下按全屏高度显示
     * 255：视频显示窗口将被关闭。它将在 保持媒体流连接的前提下，隐藏视频窗口。
     * 如果流媒体播放没有被暂停，将继 续播放音频。
     * <p>
     * MediaPlayer 对象对应的视频窗口 的显示模式。
     * 每次调用该函数后，视 频显示窗口并不会被立即重新刷新
     * 以反映更改后的显示效果只有等到
     * 显式调用 refreshVideoDisplay()后 才会刷新
     *
     * @param videoDisplayMode
     */
    @JavascriptInterface
    public void setVideoDisplayMode(int videoDisplayMode) {
        this.videoDisplayMode = videoDisplayMode;
    }

    /**
     * @return
     * @see {setVideoDisplayMode(int videoDisplayMode)}
     */
    @JavascriptInterface
    public int getVideoDisplayMode() {
        return videoDisplayMode;
    }

    /**
     * 每次调用该函数后，视频显示窗口并 不会被立即重新刷新以反映更改后
     * 的显示效果只有等到
     * 显式调用 refreshVideoDisplay()后才会刷新
     *
     * @param left   相对于所在浏览器窗口左上角的 右向偏移的象素点个数(默认值为 0)
     * @param top    相对于所在浏览器窗口左上角的 向下偏移的象素点个数(默认值为 0)
     * @param width  显示视频的窗口宽度的象素点 个数(默认值为默认页面设计空间的宽 度，对 PAL 来说为 720)
     * @param height 显示视频的窗口高度的象素点 个数(默认值为默认页面设计空间的高 度，对 PAL 来说为 576)
     */
    @JavascriptInterface
    public void setVideoDisplayArea(int left, int top, int width, int height) {
        if (videoDisplayMode == 0) {
            this.videoDisplayLeft = left;
            this.videoDisplayTop = top;
            this.videoDisplayWidth = width;
            this.videoDisplayHeight = height;

            ijkVideoView.post(new Runnable() {
                @Override
                public void run() {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.leftMargin = videoDisplayLeft;
                    layoutParams.topMargin = videoDisplayTop;
                    layoutParams.width = videoDisplayWidth;
                    layoutParams.height = videoDisplayHeight;
                    ijkVideoView.setLayoutParams(layoutParams);
                }
            });
        } else if (videoDisplayMode == 1) {
            //全屏
            //默认全屏无需显示
        }

    }

    @JavascriptInterface
    public int getVideoDisplayLeft() {
        return videoDisplayLeft;
    }

    @JavascriptInterface
    public int getVideoDisplayTop() {
        return videoDisplayTop;
    }

    @JavascriptInterface
    public int getVideoDisplayWidth() {
        return videoDisplayWidth;
    }

    @JavascriptInterface
    public int getVideoDisplayHeight() {
        return videoDisplayHeight;
    }


    @JavascriptInterface
    public int getMuteFlag() {
        return muteFlag;
    }

    /**
     * MediaPlayer 对应的本地播放器实例
     * 是否静音（sessionscope，Mute 键所触发的 MUTE 状态为全局 MUTE 状态，不影响该值），
     * 该值并 不影响 STB 本地其它音频有关应用 的 Mute 状态。 设置后立即生效。
     * 0：设置为有声(默认值)
     * 1：设置为静音
     *
     * @param muteFlag
     */
    @JavascriptInterface
    public void setMuteFlag(int muteFlag) {
        this.muteFlag = muteFlag;
        if (muteFlag==0){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }else if (muteFlag==1){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }else {
            this.muteFlag = 0;
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }

    @JavascriptInterface
    public int getVideoAlph() {
        return videoAlph;
    }

    /**
     * @param videoAlph 0－100 之间的整数值，0 表示不透明， 100 表示完全透明。 (默认值为 0)
     */
    @JavascriptInterface
    public void setVideoAlph(int videoAlph) {
        this.videoAlph = videoAlph;
    }

    @JavascriptInterface
    public int getCycleFlag() {
        return cycleFlag;
    }

    /**
     * @param cycleFlag 0：设置为循环播放 1：设置为单次播放（默认值）
     */
    @JavascriptInterface
    public void setCycleFlag(int cycleFlag) {
        this.cycleFlag = cycleFlag;
    }

    /**
     * @return 获取媒体的时长
     */
    @JavascriptInterface
    public int getMediaDuration() {
        if (mFunhotelPlayer==null){
            return -1;
        }
        mediaDuration=mFunhotelPlayer.getDuration();
        Log.e(TAG, "getMediaDuration: "+mediaDuration );
        return mediaDuration;
    }

    /**
     * @return 获取媒体播放的当前时间
     */
    @JavascriptInterface
    public int getCurrentPlayTime() {
        if (mFunhotelPlayer==null)
            return -1;
        currentPlayTime=mFunhotelPlayer.getCurrentPosition();
        Log.e(TAG, "getCurrentPlayTime: "+currentPlayTime );
        return currentPlayTime;
    }

    /**
     * @return 播放器的当前播放模式。返回值为 JSON 字符串，
     * 其中至少包括“播放模 式”和“模式相关参数”两类信息，
     * 播放模 式分：NormalPlay ， Pause ， Trickmode；
     * 当模式为 Trickmode 时必 须带 2x/-2x,4x/-4x,8x/-8x,16x/-16x,32x/-32x
     * 参数来表示快进/快退的速度参数，
     * 如： {PlayMode：“NormalPlay”, Speed：“1x”} 当
     */
    @JavascriptInterface
    public int getPlaybackMode() {
        return playbackMode;
    }


    /**
     * 退出APP
     */
    @JavascriptInterface
    public void exitAPP(){
        ((PlayWebViewActivity)mContext).finish();
    }


    /**
     * 播放错误回调
     * @param what
     * @param extra
     */
    @JavascriptInterface
    @Override
    public void onError(int what, int extra) {
        if(onPlayStatusListener!=null){
            onPlayStatusListener.onPlayStatus(1001);
        }
    }


    /**
     * 播放信息回调
     * @param what
     * @param extra
     */
    @JavascriptInterface
    @Override
    public void onInfo(int what, int extra) {
        if(onPlayStatusListener!=null){
            onPlayStatusListener.onPlayStatus(1002);
        }
    }

    /**
     * 播放完成
     */
    @Override
    public void run() {
        if(onPlayStatusListener!=null){
            onPlayStatusListener.onPlayStatus(1000);
        }
    }

    public interface OnPlayStatusListener{
        /**
         * @param status  1000为播放完成，1001为播放错误，1002为播放警告
         */
        void onPlayStatus(int status);
    }
    public OnPlayStatusListener  onPlayStatusListener;
    public void setOnPlayStatusListener(OnPlayStatusListener onPlayStatusListener) {
        this.onPlayStatusListener = onPlayStatusListener;
    }

}







