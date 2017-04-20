package com.funhotel.ijkplayer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.funhotel.ijkplayer.ui.AdvertiseFragment;
import com.funhotel.tvllibrary.application.Channel;
import com.funhotel.tvllibrary.application.LookBackModel;
import com.funhotel.tvllibrary.utils.DebugUtil;
import com.funhotel.tvllibrary.view.ChannelView;

import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.MediaInfo;

/**
 * Created by tcking on 15/10/27.
 */
public class FunhotelJsPlayer {
    /**
     * fitParent:scale the video uniformly (maintain the video's aspect ratio) so that both dimensions (width and height) of the video will be equal to or **less** than the corresponding dimension of the view. like ImageView's `CENTER_INSIDE`.等比缩放,画面填满view。
     */
    public static final String SCALETYPE_FITPARENT="fitParent";
    /**
     * fillParent:scale the video uniformly (maintain the video's aspect ratio) so that both dimensions (width and height) of the video will be equal to or **larger** than the corresponding dimension of the view .like ImageView's `CENTER_CROP`.等比缩放,直到画面宽高都等于或小于view的宽高。
     */
    public static final String SCALETYPE_FILLPARENT="fillParent";
    /**
     * wrapContent:center the video in the view,if the video is less than view perform no scaling,if video is larger than view then scale the video uniformly so that both dimensions (width and height) of the video will be equal to or **less** than the corresponding dimension of the view. 将视频的内容完整居中显示，如果视频大于view,则按比例缩视频直到完全显示在view中。
     */
    public static final String SCALETYPE_WRAPCONTENT="wrapContent";
    /**
     * fitXY:scale in X and Y independently, so that video matches view exactly.不剪裁,非等比例拉伸画面填满整个View
     */
    public static final String SCALETYPE_FITXY="fitXY";
    /**
     * 16:9:scale x and y with aspect ratio 16:9 until both dimensions (width and height) of the video will be equal to or **less** than the corresponding dimension of the view.不剪裁,非等比例拉伸画面到16:9,并完全显示在View中。
     */
    public static final String SCALETYPE_16_9="16:9";
    /**
     * 4:3:scale x and y with aspect ratio 4:3 until both dimensions (width and height) of the video will be equal to or **less** than the corresponding dimension of the view.不剪裁,非等比例拉伸画面到4:3,并完全显示在View中。
     */
    public static final String SCALETYPE_4_3="4:3";

    /**
     * 快进快退
     */
    private static final int MESSAGE_SEEK_NEW_POSITION = 3;

    /**
     * 重新播放
     */
    private static final int MESSAGE_RESTART_PLAY = 5;
    /**
     * 快进或快退后的回复播放
     */
    private static final int MESSAGE_RECOVERY_PLAY = 6;


    private  Context context;
    private final IjkVideoView videoView;
    private boolean playerSupport;//播放支持
    private String url;
    private int STATUS_ERROR=-1;
    private int STATUS_IDLE=0;
    private int STATUS_LOADING=1;
    private int STATUS_PLAYING=2;
    private int STATUS_PAUSE=3;
    private int STATUS_COMPLETED=4;
    private long pauseTime;
    private int status=STATUS_IDLE;
    private boolean isLive = false;//是否为直播
    private OrientationEventListener orientationEventListener;
    private int defaultTimeout=3000;
    private boolean isShowing;
    private boolean portrait;
    private long newPosition = -1;
    private long defaultRetryTime=5000;
    private int currentPosition;
    private boolean fullScreenOnly;
    private int initHeight;
    private boolean isPlayCompelete=false;
    private int playerType;//播放类型
    private float speed = 0.0f;//快进/快退速度
    private boolean isNormal = true;

    @SuppressWarnings("HandlerLeak")
    private Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SEEK_NEW_POSITION:
                    onProgressSlide(speed);
                    if (!isLive && newPosition >= 0) {
                        videoView.seekTo((int) newPosition);
                        if(!isNormal){
                            newPosition = -1;
                            msg = obtainMessage(MESSAGE_SEEK_NEW_POSITION);
                            sendMessageDelayed(msg, 1000);
                        }
                    }
                    break;
                case MESSAGE_RECOVERY_PLAY:
                    if (!isLive && newPosition >= 0) {
                        videoView.seekTo((int) newPosition);
                    }else{
                        play(url);
                    }
                    newPosition = -1;
                    speed = 0.0f;
                    break;
                case MESSAGE_RESTART_PLAY:
                    play(url);
                    if(status == STATUS_ERROR){
                        msg = obtainMessage(MESSAGE_RESTART_PLAY);
                        sendMessageDelayed(msg, 5000);
                    }
                    break;
            }
        }
    };


    public FunhotelJsPlayer(final Context context, IjkVideoView videoView) {
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
            playerSupport=true;
        } catch (Throwable e) {
            Log.e("GiraffePlayer", "loadLibraries error", e);
        }
        this.context=context;
        this.videoView=videoView;
        //完成事件
        this.videoView.setOnCompletionListener(onCompletionListener);
        //错误回调取
        this.videoView.setOnErrorListener(iMediaPlayerOnErrorListener);
        //信息事件
        this.videoView.setOnInfoListener(iMediaPlayerOnInfoListener);

        //是否设置全屏
        if (fullScreenOnly) {
            ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        portrait=getScreenOrientation()==ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;


        orientationEventListener = new OrientationEventListener(context) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation >= 0 && orientation <= 30 || orientation >= 330 || (orientation >= 150 && orientation <= 210)) {
                    //竖屏
                    if (portrait) {
                        ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                        orientationEventListener.disable();
                    }
                } else if ((orientation >= 90 && orientation <= 120) || (orientation >= 240 && orientation <= 300)) {
                    if (!portrait) {
                        ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                        orientationEventListener.disable();
                    }
                }
            }
        };
        if (fullScreenOnly) {
            ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        portrait=getScreenOrientation()==ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
//        initHeight=activity.findViewById(R.id.app_video_box).getLayoutParams().height;
        if (!playerSupport) {
            Toast.makeText(context,context.getResources().getString(R.string.not_support),Toast.LENGTH_LONG);
        }
    }

    /**
     * try to play when error(only for live video)
     * 直播的缓冲时间
     * @param defaultRetryTime millisecond,0 will stop retry,default is 5000 millisecond
     */
    public void setDefaultRetryTime(long defaultRetryTime) {
        this.defaultRetryTime = defaultRetryTime;
    }

    public void setLive(boolean live) {
        this.isLive = live;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

//    private long duration;
//    private boolean instantSeeking;
//    private final SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            if (!fromUser)
//                return;
//            //移动时隐藏掉状态image
//            int newPosition = (int) ((duration * progress*1.0) / 1000);
//            String time = generateTime(newPosition);2
//            if (instantSeeking){
//                videoView.seekTo(newPosition);
//            }
//            iViewController.setCurrentTime(time);
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//            showControlView(3600000);
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            if (!instantSeeking){
//                videoView.seekTo((int) ((duration * seekBar.getProgress()*1.0) / 1000));
//            }
//            showControlView(defaultTimeout);
//            handler.removeMessages(MESSAGE_SHOW_PROGRESS);
//        }
//    };


    /**
     * 只设置全屏
     * @param fullScreenOnly
     */
    public void setFullScreenOnly(boolean fullScreenOnly) {
        this.fullScreenOnly = fullScreenOnly;
        tryFullScreen(fullScreenOnly);
        if (fullScreenOnly) {
            ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }

    /**
     * 设置全屏
     * @param fullScreen
     */
    private void tryFullScreen(boolean fullScreen) {
        if (context instanceof AppCompatActivity) {
            ActionBar supportActionBar = ((AppCompatActivity) context).getSupportActionBar();
            if (supportActionBar != null) {
                if (fullScreen) {
                    supportActionBar.hide();
                } else {
                    supportActionBar.show();
                }
            }
        }
        setFullScreen(fullScreen);
    }


    private void setFullScreen(boolean fullScreen) {
        if (context != null) {
            WindowManager.LayoutParams attrs = ((Activity)context).getWindow().getAttributes();
            if (fullScreen) {
                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                ((Activity)context).getWindow().setAttributes(attrs);
                ((Activity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            } else {
                attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                ((Activity)context).getWindow().setAttributes(attrs);
                ((Activity)context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
        }
    }

    public void play(String url) {
        this.url = url;
        if (playerSupport || videoView.isUsingAndroidPlayer()) {
            videoView.setVideoPath(url);
            videoView.start();
        }else {
            Toast.makeText(context,"不支持播放",Toast.LENGTH_LONG).show();
        }
    }

    private String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    private int getScreenOrientation() {
        int rotation = ((Activity)context).getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0
                || rotation == Surface.ROTATION_180) && height > width ||
                (rotation == Surface.ROTATION_90
                        || rotation == Surface.ROTATION_270) && width > height) {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        }
        // if the device's natural orientation is landscape or if the device
        // is square:
        else {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }
        return orientation;
    }

    /**
     * using constants in GiraffePlayer,eg: GiraffePlayer.SCALETYPE_FITPARENT
     * @param scaleType
     */
    public void setScaleType(String scaleType) {
        if (SCALETYPE_FITPARENT.equals(scaleType)) {
            videoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        }else if (SCALETYPE_FILLPARENT.equals(scaleType)) {
            videoView.setAspectRatio(IRenderView.AR_ASPECT_FILL_PARENT);
        }else if (SCALETYPE_WRAPCONTENT.equals(scaleType)) {
            videoView.setAspectRatio(IRenderView.AR_ASPECT_WRAP_CONTENT);
        }else if (SCALETYPE_FITXY.equals(scaleType)) {
            videoView.setAspectRatio(IRenderView.AR_MATCH_PARENT);
        }else if (SCALETYPE_16_9.equals(scaleType)) {
            videoView.setAspectRatio(IRenderView.AR_16_9_FIT_PARENT);
        }else if (SCALETYPE_4_3.equals(scaleType)) {
            videoView.setAspectRatio(IRenderView.AR_4_3_FIT_PARENT);
        }
    }

    public void start() {
        videoView.start();
    }

    public void pause() {
        videoView.pause();
    }


    public boolean onBackPressed() {
        if (!fullScreenOnly && getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return true;
        }
        return false;
    }

    /**
     * is player support this device
     * @return
     */
    public boolean isPlayerSupport() {
        return playerSupport;
    }

    /**
     * 是否正在播放
     * @return
     */
    public boolean isPlaying() {
        return videoView!=null?videoView.isPlaying():false;
    }

    public void stop(){
        videoView.stopPlayback();
    }

    /**
     * get video duration
     * @return
     */
    public int getDuration(){
        return videoView.getDuration();
    }

    public interface OnErrorListener{
        void onError(int what, int extra);
    }

    public interface OnControlPanelVisibilityChangeListener{
        void change(boolean isShowing);
    }

    public interface OnInfoListener{
        void onInfo(int what, int extra);
    }

    public FunhotelJsPlayer onError(OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
        return this;
    }

    public FunhotelJsPlayer onComplete(Runnable complete) {
        this.oncomplete = complete;
        return this;
    }

    public FunhotelJsPlayer onInfo(OnInfoListener onInfoListener) {
        this.onInfoListener = onInfoListener;
        return this;
    }

    public FunhotelJsPlayer onControlPanelVisibilityChang(OnControlPanelVisibilityChangeListener listener){
        this.onControlPanelVisibilityChangeListener = listener;
        return this;
    }

    /**
     * 快进kuai -- 如果在直播，或者百分比>1 获取百分比<1  则不处理
     * @param percent 百分比
     * @return
     */
    public FunhotelJsPlayer forward(float percent) {
        if (isLive) {
            return this;
        }
        //显示触摸的进度
        this.speed = percent;
        handler.sendEmptyMessage(MESSAGE_SEEK_NEW_POSITION);
//        onProgressSlide(percent);
        return this;
    }

    private void onProgressSlide(float percent) {
        this.speed = percent;
        long position = videoView.getCurrentPosition();
        long duration = videoView.getDuration();
        /*long deltaMax = Math.min(100 * 1000, duration - position);
        long delta = (long) (deltaMax * percent);
        newPosition = delta + position;
        if (newPosition > duration) {
            newPosition = duration;
        } else if (newPosition <= 0) {
            newPosition=0;
            delta=-position;
        }
        int showDelta = (int) delta / 1000;
        DebugUtil.e(">>>>onProgressSlide>>position>"+position);
        DebugUtil.e(">>>>onProgressSlide>duration>>"+duration);
        DebugUtil.e(">>>>onProgressSlide>>>deltaMax>>"+deltaMax);
        DebugUtil.e(">>>>onProgressSlide>>>delta>>"+delta);
        DebugUtil.e(">>>>onProgressSlide>>>newPosition>>"+newPosition);
        DebugUtil.e(">>>>onProgressSlide>>>showDelta>>"+showDelta);
        if (showDelta != 0) {
            //不等于则显示时移视图
            iViewController.showTimshiftView(percent);
        }
        */
        long speedDuration=(long) (percent*1000);
        newPosition=position+speedDuration;
        DebugUtil.e(">>>>onProgressSlide>>>newPosition>>"+newPosition
                +">>>>speedDuration>>>"+speedDuration+">>>duration>"+duration);
//        if(newPosition>duration){
//            newPosition=duration;
//            handler.removeMessages(MESSAGE_SEEK_NEW_POSITION);
//            handler.sendEmptyMessage(MESSAGE_RECOVERY_PLAY);
//        }else if (newPosition < 0) {
//            newPosition=0;
//            handler.removeMessages(MESSAGE_SEEK_NEW_POSITION);
//            handler.sendEmptyMessage(MESSAGE_RECOVERY_PLAY);
//        }
        if (newPosition<duration&&newPosition>0){
            //不等于则显示时移视图
            isNormal = false;
            handler.removeMessages(MESSAGE_SEEK_NEW_POSITION);
//          handler.sendEmptyMessage(MESSAGE_SEEK_NEW_POSITION);
        }else{
            isNormal = true;
            if(newPosition>=duration){
                newPosition=duration;
            }else if((newPosition <= 0)){
                newPosition=0;
            }
            //快退或快进超出视频可播放位置，则恢复自动正常播放
            handler.removeMessages(MESSAGE_SEEK_NEW_POSITION);
            handler.sendEmptyMessage(MESSAGE_RECOVERY_PLAY);
        }
    }

    public void onResume() {
        pauseTime=0;
        if (status==STATUS_PLAYING) {
            if (isLive) {
                //直播重新开始
                videoView.seekTo(0);
//              play(url);
            } else {
                //其他模式，直接定位当前的点
                if (currentPosition>0) {
                    videoView.seekTo(currentPosition);
                }
            }
            videoView.start();
        }
    }

    public void onPause() {
        DebugUtil.i("onPause--------------》");
        pauseTime=System.currentTimeMillis();
        if(videoView!=null) {
            videoView.pause();
            if (status == STATUS_PLAYING) {
                //如果不是直播，则记录当前播放的位置，下次可以恢复
                if (!isLive) {
                    currentPosition = videoView.getCurrentPosition();
                }
            }
        }
    }

    public void onDestroy() {
        orientationEventListener.disable();
        videoView.stopPlayback();
        handler.removeCallbacksAndMessages(null);
    }


    public void onConfigurationChanged(final Configuration newConfig) {
        portrait = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT;
        doOnConfigurationChanged(portrait);
    }

    private void doOnConfigurationChanged(final boolean portrait) {
        if (videoView != null && !fullScreenOnly) {
            videoView.post(new Runnable() {
                @Override
                public void run() {
                    tryFullScreen(!portrait);
                    //设置横屏和竖屏的大小
//                    updateFullScreenButton();
                }
            });
            orientationEventListener.enable();
        }
    }

    /**
     * set is live (can't seek forward)
     * @param isLive
     * @return
     */
    public FunhotelJsPlayer live(boolean isLive) {
        this.isLive = isLive;
        return this;
    }

    public FunhotelJsPlayer type(int type) {
        this.playerType = type;
        return this;
    }

    public FunhotelJsPlayer toggleAspectRatio(){
        if (videoView != null) {
            videoView.toggleAspectRatio();
        }
        return this;
    }

    public FunhotelJsPlayer onControlPanelVisibilityChange(OnControlPanelVisibilityChangeListener listener){
        this.onControlPanelVisibilityChangeListener = listener;
        return this;
    }


    public boolean isPlayCompelete() {
        return isPlayCompelete;
    }

    /**
     * 错误回调
     */
    private OnErrorListener onErrorListener;

    /**
     * 完成回调
     */
    private Runnable oncomplete;
    /**
     * 信息回调
     */
    private OnInfoListener onInfoListener;

    private OnControlPanelVisibilityChangeListener onControlPanelVisibilityChangeListener=new OnControlPanelVisibilityChangeListener() {
        @Override
        public void change(boolean isShowing) {
        }
    };

    IMediaPlayer.OnErrorListener iMediaPlayerOnErrorListener=new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {

            switch (what) {
                case IMediaPlayer.MEDIA_ERROR_IO:
                    DebugUtil.e("---->MEDIA_ERROR_IO 视频流错误");
                    break;
                case IMediaPlayer.MEDIA_ERROR_SERVER_DIED:
                    DebugUtil.e("---->MEDIA_ERROR_SERVER_DIED 媒体的后台服务出错");
                    break;
                case IMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    DebugUtil.e("---->MEDIA_ERROR_UNKNOWN");
                    break;
                case IMediaPlayer.MEDIA_ERROR_TIMED_OUT:
                    DebugUtil.e("---->MEDIA_ERROR_TIMED_OUT");
                    break;
                case IMediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                    //播放错误（一般视频播放比较慢或视频本身有问题会引发）。
                    DebugUtil.e("---->MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
                    break;
                case IMediaPlayer.MEDIA_ERROR_MALFORMED:
                    //文件格式错误
                    DebugUtil.e("---->MEDIA_ERROR_MALFORMED");
                    break;
                case IMediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                    //不支持
                    DebugUtil.e("---->MEDIA_ERROR_UNSUPPORTED");
                    break;
            }

            statusChange(STATUS_ERROR);
            if (null!=onErrorListener){
                onErrorListener.onError(what,extra);
            }
            return true;
        }
    };

    /**
     * 信息回调事件
     */
    IMediaPlayer.OnInfoListener iMediaPlayerOnInfoListener=new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer mp, int what, int extra) {
            switch (what) {
                case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    statusChange(STATUS_LOADING);
                    DebugUtil.e(">>>>>>MEDIA_INFO_BUFFERING_START");
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    DebugUtil.e(">>>>>>MEDIA_INFO_BUFFERING_END");
                    statusChange(STATUS_PLAYING);
                    break;
                case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                    //显示 下载速度
//                  Toaster.show("download rate:" + extra);
                    break;
                case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    DebugUtil.e(">>>>>>MEDIA_INFO_VIDEO_RENDERING_START");
                    statusChange(STATUS_PLAYING);
                    break;
                case IMediaPlayer.MEDIA_ERROR_IO:
                    Toast.makeText(context,"流错误",Toast.LENGTH_LONG).show();
                    break;

                case IMediaPlayer.MEDIA_ERROR_TIMED_OUT:
                    Toast.makeText(context,"超时",Toast.LENGTH_LONG).show();
                    break;

                case IMediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                    Toast.makeText(context,"不支持",Toast.LENGTH_LONG).show();
                    break;
                case IMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    Toast.makeText(context,"未知错误",Toast.LENGTH_LONG).show();
                    break;

                case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                    Toast.makeText(context,"不支持时间移动",Toast.LENGTH_LONG).show();
                    break;
            }
            if (null!=onInfoListener){
                onInfoListener.onInfo(what,extra);
            }
            return false;
        }
    };

    IMediaPlayer.OnCompletionListener onCompletionListener =new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            pause();
            statusChange(STATUS_COMPLETED);
            if (null!=oncomplete){
                oncomplete.run();
            }
        }
    };

    private void statusChange(int newStatus) {
        status=newStatus;
        DebugUtil.i("status---->"+status);
        if (!isLive && newStatus==STATUS_COMPLETED) {
            isPlayCompelete = true;

        }else if (newStatus == STATUS_ERROR) {
            //错误提示
            onControlPanelVisibilityChangeListener.change(false);
            if (isLive) {
                if(!TextUtils.isEmpty(url)) {
                    play(url);
                }
//                if (defaultRetryTime>0) {
//                    handler.sendEmptyMessageDelayed(MESSAGE_RESTART_PLAY, defaultRetryTime);
//                }
            }
            isPlayCompelete=false;
        } else if(newStatus==STATUS_LOADING){
            isPlayCompelete=false;
        } else if (newStatus == STATUS_PLAYING) {
            isPlayCompelete=false;
        }
    }

    public MediaInfo getMediaInfo() {
        return videoView.getMediaInfo();
    }


    public int getCurrentPosition() {
        currentPosition=videoView.getCurrentPosition();
        return currentPosition;
    }


    public void setUsingAndroidPlayer(boolean usingAndroidPlayer) {
       videoView.setUsingAndroidPlayer(usingAndroidPlayer);
    }

}
