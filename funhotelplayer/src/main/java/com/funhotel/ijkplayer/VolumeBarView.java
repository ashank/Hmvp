package com.funhotel.ijkplayer;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;


/**
 * Created by dell on 2016/10/20.
 */

public class VolumeBarView extends RelativeLayout{

    private View view;
    private Context context;
    private SeekBar seekBar;
    private ImageView imageView;
    private AudioManager mAudioManager;
    private int maxVolume;
    private int currentVolume;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    currentVolume--;
                    if (currentVolume < 0)
                        currentVolume = 0;
                    setMediaVolume(currentVolume);
                    seekBar.setProgress(currentVolume);
                    break;
                case 2:
                    currentVolume++;
                    if (currentVolume >= maxVolume)
                        currentVolume = maxVolume;
                    setMediaVolume(currentVolume);
                    seekBar.setProgress(currentVolume);
                    break;

            }
            super.handleMessage(msg);
        }
    };

    public VolumeBarView(Context context) {
        super(context);
        initView(context);
    }

    public VolumeBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VolumeBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.view_volum_bar,this);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        imageView = (ImageView) view.findViewById(R.id.iv_ad);
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        setVolum();
    }

    /**
     * 设置当前音量
     */
    private void setVolum() {
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        seekBar.setMax(maxVolume);

        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekBar.setProgress(currentVolume);

//        mVolume.setText(currentVolume* 100 /maxVolume + " ");
    }

    /**
     * 当前音量
     * @param volume
     */
    private void setMediaVolume(int volume) {
        if(null!=mAudioManager) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_PLAY_SOUND);
        }
    }

    /**
     * 音量条移动监听
     */
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}
    };


    /**
     * 减少音量
     */
    public void cutVolume(){
        handler.sendEmptyMessage(1);
    }

    /**
     * 增加音量
     */
    public void addVolume(){
        handler.sendEmptyMessage(2);
    }

    public void setAd(String url) {
        if (!TextUtils.isEmpty(url))
            Glide.with(context).load(url).error(R.drawable.bg_ad_350_280).into(imageView);
    }
}
