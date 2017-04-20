package com.funhotel.ijkplayer;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.funhotel.ijkplayer.ui.MainActivity;
import com.funhotel.tvllibrary.application.Channel;
import com.funhotel.tvllibrary.application.LookBackModel;
import com.funhotel.tvllibrary.utils.DateTimeUtil;
import com.funhotel.tvllibrary.utils.DebugUtil;
import com.funhotel.tvllibrary.view.ChannelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhiyahan on 2016/9/23.
 *  播控显示
 *
 //一开始先显示缓冲视图
 //第一次缓冲，完毕后，隐藏缓冲视图，显示控制视图3秒后消失
 //显示缓冲视图，每个一秒刷新进度条

 //往后的缓冲，都不需要显示控制视图
 //缓冲完毕就隐藏缓冲视图

 //按住信息键，弹出控制视图，3秒
 //显示缓冲视图，每个一秒刷新进度条

 //按快进快退，如果是直播，不能快退，显示一直显示控制视图，然后显示快进快退的状态，
 //显示缓冲视图，每个一秒刷新进度条

 //按确定键或者播放键。开始快进后的位置播放，并隐藏快进快按钮，延迟3秒，隐藏控制视图
 //显示缓冲视图，每个一秒刷新进度条

 //按住暂停，则一直显示控制视图
 //显示缓冲视图，每个一秒刷新进度条

 //播放完毕后，显示播放完毕的视图
 *
 */

public class MediaControllerView extends RelativeLayout implements IViewController {

    private View view;
    private ProgressBar progressBarLoading;
    private RelativeLayout llMediaControll;//播控部分根布局
    private ImageView imgLogo;//台标
    private ImageView imgTimeShiftStatus;//时间移动状态
    private SeekBar seekBar;//进度条
    private TextView tvLine;//斜线
    private TextView tvCurrentTime,tvTotalTime;//当前时间和总共时间;
    private TextView tvprogramName;//播放的节目名字
    private LinearLayout llCurrentProgram,llNextProgram;
    private TextView tvCurrentTimeDuration,tvNextTimeDuration;//当前播放时间段和下个节目播放时间段
    private TextView tvCurrentProgram,tvNextProgrm;//当前节目和下个节目
    private TextView tvChannelNumber;//频道号
    private RelativeLayout rlRoot;
    private ImageView imgPlay;
    private LinearLayout llCompeleteView,llExitView,llNoNet,llError;
    private TextView tvErrorCode,tvErrorInfo;
    private Button btExit,btSee,btCompeleteExit,btTryAgain;
    private TextView tvErrorToast;
    private ImageView ivAdForInfo,ivAdForCompelete,ivAdForExit;//信息条、完成、退出广告
    private ChannelView channelView;
    private Context context;
    private IjkVideoView ijkVideoView;
    private long duration;
    private float speed;
    private boolean isLive=false;
    private int playerType;
    private TextView btCompeleteSee;
    private VolumeBarView volumeBarView;

    private String adInfoUrl;//信息条广告地址
    private String adCompleteUrl;//播放完成广告地址
    private String adExitUrl;//退出广告地址
    private String adVolumeBarUrl;//音量广告

    private Timer timer;
    private int recLen = 5;//退出倒计时

    private Channel channel;//当前播放频道
    private List<List<LookBackModel>> lookBacks;//每天所对应的所有回看数据
    private int lookBackPosition;//回看位置
    private int datePosition=0;//
    private List<LookBackModel> nextPrograms;//直播频道未来12小时的节目
    //获取信息条频道数据
    private List<LookBackModel> infoLookBack;
    //当前时间
    private long currentTime;
    /**
     * 显示进度条，分为长期显示和限时显示
     */
    private static final int MESSAGE_SHOW_CONTROLL_VIEW = 0;
    /**
     * 隐藏进度条
     */
    private static final int MESSAGE_HIDE_CONTROLL_VIEW = 1;
    /**
     * 快进快退动作，显示进度条，显示快进快退状态图
     */
    private static final int MESSAGE_SHOW_TIMSHIFT_VIEW = 2;
    /**
     * 隐藏
     */
    private static final int MESSAGE_HIDE_TIMESIFT_VIEW= 3;

    /**
     * 重新播放
     */
    private static final int MESSAGE_RESTART_PLAY = 4;

    /**
     * 缓冲视图
     */
    private static final int MESSAGE_SHOW_LOADING_VIEW= 5;

    /**
     * 隐藏缓冲视图
     */
    private static final int MESSAGE_HIDE_LOADING_VIEW= 6;


    /**
     * 隐藏退出视图
     */
    private static final int MESSAGE_PROGRESS= 9;

    /**
     * 播放完成视图
     */
    private static final int MESSAGE_SHOW_COMPLETE= 10;

    /**
     * 隐藏完成视图
     */
    private static final int MESSAGE_HIDE_COMPLETE= 11;


    /**
    * 播放退出视图
    */
    private static final int MESSAGE_SHOW_EXIT= 12;

    /**
     * 隐藏退出视图
     */
    private static final int MESSAGE_HIDE_EXIT= 13;


    private static  final  int MESSAGE_SHOW_CHANNEL_VIEW=14;
    private static  final  int MESSAGE_HIDE_CHANNEL_VIEW=15;

    /**
     * 显示音量条
     */
    private static final int MESSAGE_SHOW_VOLUME_BAR = 16;
    /**
     * 隐藏音量条
     */
    private static final int MESSAGE_HIDE_VOLUME_BAR = 17;
    /**
     * 退出倒计时
     */
    private static final int MESSAGE_COUNT_DOWN = 18;
    /**
     * 显示频道号
     */
    private static final int MESSAGE_SHOW_CHANNEL_NUMBER = 19;
    /**
     * 隐藏频道号
     */
    private static final int MESSAGE_HIDE_CHANNEL_NUMBER = 20;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_SHOW_CONTROLL_VIEW:
                    tvErrorToast.setVisibility(GONE);
                    llMediaControll.setVisibility(View.VISIBLE);
                    hideExitView();
                    hideNoNetView();
                    hideErrorView();
                    //重新刷新进度条
                    removeMessages(MESSAGE_PROGRESS);
                    sendEmptyMessage(MESSAGE_PROGRESS);
                    break;
                case MESSAGE_HIDE_CONTROLL_VIEW:
                    //隐藏控制条
                    llMediaControll.setVisibility(View.GONE);
                    break;
                case MESSAGE_PROGRESS:
                    //刷新进度条
                    setProgress();
                    if (isShowing()) {
                        //是否显示，显示则更新状态
                        msg = obtainMessage(MESSAGE_PROGRESS);
                        sendMessageDelayed(msg, 1000);
                    }
                    break;
                case MESSAGE_SHOW_TIMSHIFT_VIEW:
                    tvErrorToast.setVisibility(GONE);
                   /* if (null!=msg&&null!=msg.obj){
                        speed=(int)msg.obj*10;
                    }*/
                    DebugUtil.e(">>>>时间移动>>>"+speed);
                    //显示时间移动的状态图
                    //显示按钮
                    //刷新进度条
                    imgTimeShiftStatus.setVisibility(View.VISIBLE);
                    //判断快进还是快退
                    if (speed>0){
                        //快进
                        if (speed==2){
                            imgTimeShiftStatus.setImageResource(R.drawable.ic_fastforward_2);
                        }else if (speed==4){
                            imgTimeShiftStatus.setImageResource(R.drawable.ic_fastforward_4);
                        }else if (speed==8){
                            imgTimeShiftStatus.setImageResource(R.drawable.ic_fastforward_8);
                        }else if (speed==16){
                            imgTimeShiftStatus.setImageResource(R.drawable.ic_fastforward_16);
                        }else if (speed==32){
                            imgTimeShiftStatus.setImageResource(R.drawable.ic_fastforward_32);
                        }else {
                            imgTimeShiftStatus.setImageResource(R.drawable.ic_fastforward_2);
                        }
                    }else if (speed<0){
                        //快退
                        if (Math.abs(speed)==2){
                            imgTimeShiftStatus.setImageResource(R.drawable.ic_backforward_2);
                        }else if (Math.abs(speed)==4){
                            imgTimeShiftStatus.setImageResource(R.drawable.ic_backforward_4);
                        }else if (Math.abs(speed)==8){
                            imgTimeShiftStatus.setImageResource(R.drawable.ic_backforward_8);
                        }else if (Math.abs(speed)==16){
                            imgTimeShiftStatus.setImageResource(R.drawable.ic_backforward_16);
                        }else if (Math.abs(speed)==32){
                            imgTimeShiftStatus.setImageResource(R.drawable.ic_backforward_32);
                        }else {
                            imgTimeShiftStatus.setImageResource(R.drawable.ic_backforward_2);
                        }
                    }else {
                        imgTimeShiftStatus.setVisibility(View.GONE);
                    }
                    removeMessages(MESSAGE_SHOW_CONTROLL_VIEW);
                    sendEmptyMessage(MESSAGE_SHOW_CONTROLL_VIEW);
                    break;
                case MESSAGE_HIDE_TIMESIFT_VIEW:
                    imgTimeShiftStatus.setVisibility(View.GONE);
                    break;
                case MESSAGE_SHOW_LOADING_VIEW:
                    //显示缓冲视图
                    hidePauseView();
//                    hideTimeShiftView();
                    hideCompleteView();
                    hideNoNetView();
                    hideErrorView();
                    tvErrorToast.setVisibility(GONE);
                    progressBarLoading.setVisibility(View.VISIBLE);
                    break;
                case MESSAGE_HIDE_LOADING_VIEW:
                    //隐藏缓冲视图
                    progressBarLoading.setVisibility(View.GONE);
                    break;
                case MESSAGE_SHOW_COMPLETE:
                    //显示播放完成视图
                    hidePauseView();
                    hideLoadingView();
                    hideTimeShiftView();
                    hideExitView();
                    hideControlView();
                    hideNoNetView();
                    hideErrorView();
                    tvErrorToast.setVisibility(GONE);
                    llCompeleteView.setVisibility(VISIBLE);
                    break;
                case MESSAGE_HIDE_COMPLETE:
                    //隐藏播放完成视图
                    llCompeleteView.setVisibility(View.GONE);
                    //取消倒计时
                    removeMessages(MESSAGE_COUNT_DOWN);
                    break;
                case MESSAGE_SHOW_EXIT:
                    //显示退出视图
                    hidePauseView();
                    hideLoadingView();
                    hideTimeShiftView();
                    hideNoNetView();
                    hideErrorView();
                    tvErrorToast.setVisibility(GONE);
                    llCompeleteView.setVisibility(GONE);
                    llExitView.setVisibility(VISIBLE);
                    break;
                case MESSAGE_SHOW_CHANNEL_VIEW:
                    tvErrorToast.setVisibility(GONE);
                    channelView.setVisibility(View.VISIBLE);
                    channelView.setData();
                    //隐藏控制条
                    llMediaControll.setVisibility(View.GONE);
                    hideExitView();
                    hideNoNetView();
                    hideErrorView();
                    break;
                case MESSAGE_HIDE_CHANNEL_VIEW:
                    //隐藏菜单
                    channelView.setVisibility(View.GONE);
                    break;
                case MESSAGE_SHOW_VOLUME_BAR:
                    //显示音量条
                    volumeBarView.setVisibility(VISIBLE);
                    llMediaControll.setVisibility(View.GONE);
                    hideExitView();
                    hideNoNetView();
                    hideErrorView();
                    hideControlView();
                    break;
                case MESSAGE_HIDE_VOLUME_BAR:
                    //显隐藏量条
                    volumeBarView.setVisibility(GONE);
                    break;

                case MESSAGE_COUNT_DOWN:
                    //退出倒计时
                    dealWithCountDown();
                    break;
                case MESSAGE_SHOW_CHANNEL_NUMBER:
                    //显示频道号
                    tvChannelNumber.setText((String) msg.obj);
                    tvChannelNumber.setVisibility(VISIBLE);
                    break;
                case MESSAGE_HIDE_CHANNEL_NUMBER:
                    //隐藏频道号
                    tvChannelNumber.setVisibility(GONE);
                    break;

                case 100:
                    currentTime = currentTime + 60 * 1000;
                    sendEmptyMessageDelayed(100, 60*1000);
                    break;
            }


            super.handleMessage(msg);
        }
    };

    public MediaControllerView(Context context) {
        super(context);
        initView(context);
    }

    public MediaControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MediaControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

/*
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MediaControllerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }
*/

    public void initView(Context context){
        this.context=context;
        view= LayoutInflater.from(context).inflate(R.layout.play_media_menu,this);
        rlRoot=(RelativeLayout)view.findViewById(R.id.rl_root);
        rlRoot.setVisibility(VISIBLE);
        progressBarLoading=(ProgressBar)view.findViewById(R.id.pb_video_loading);
        tvLine = (TextView) view.findViewById(R.id.tv_video_line);
        tvCurrentTime=(TextView)view.findViewById(R.id.tv_video_currentTime);
        tvprogramName = (TextView) view.findViewById(R.id.tv_program_name);
        tvTotalTime=(TextView)view.findViewById(R.id.tv_video_endTime);
        llCurrentProgram = (LinearLayout) view.findViewById(R.id.ll_current_program);
        llNextProgram = (LinearLayout) view.findViewById(R.id.ll_next_program);
        tvCurrentTimeDuration=(TextView)view.findViewById(R.id.tv_current_time_duration);
        tvNextTimeDuration=(TextView)view.findViewById(R.id.tv_next_time_duration);
        tvCurrentProgram=(TextView)view.findViewById(R.id.tv_current_time_program);
        tvNextProgrm=(TextView)view.findViewById(R.id.tv_next_time_program);
        ivAdForInfo=(ImageView)view.findViewById(R.id.img_adforinfo);
        imgLogo=(ImageView)view.findViewById(R.id.iv_logo);
        imgTimeShiftStatus=(ImageView)view.findViewById(R.id.iv_timeshift);
        seekBar=(SeekBar) view.findViewById(R.id.sb_video_progress);
        seekBar.setMax(1000);
        llMediaControll=(RelativeLayout)view.findViewById(R.id.ll_media_controll);
        imgPlay=(ImageView)view.findViewById(R.id.img_play);
        llCompeleteView=(LinearLayout)view.findViewById(R.id.view_compelete);
        llExitView=(LinearLayout)view.findViewById(R.id.view_exit);
        btExit=(Button)view.findViewById(R.id.bt_exit_exit);
        btSee=(Button)view.findViewById(R.id.bt_exit_see);
        btCompeleteExit=(Button)view.findViewById(R.id.bt_compelete_exit);
        ivAdForCompelete=(ImageView)view.findViewById(R.id.img_ad_compelete);
        ivAdForExit=(ImageView)view.findViewById(R.id.img_ad_exit);
        tvErrorToast=(TextView)view.findViewById(R.id.tv_err_toast);
        llNoNet=(LinearLayout)view.findViewById(R.id.view_nonet);
        llError = (LinearLayout) view.findViewById(R.id.view_error);
        btTryAgain=(Button)view.findViewById(R.id.bt_try_again);
        channelView=(ChannelView)view.findViewById(R.id.view_channel);
        volumeBarView = (VolumeBarView) view.findViewById(R.id.volume_bar_view);
        btCompeleteSee = (TextView) view.findViewById(R.id.bt_compelete_see);
        tvChannelNumber = (TextView) view.findViewById(R.id.tv_channel_number);
        tvErrorCode = (TextView) findViewById(R.id.tv_error_code);
        tvErrorInfo = (TextView) findViewById(R.id.tv_error_info);

        channelView.setOnListViewItemClickListener(onItemClickListener);
    }

    /**
     *  设置回看数据
     */
    public void setLookBackModelList(List<LookBackModel> list,String time){
        channelView.setLookBackDatas(list,time);
    }

    /**
     * 设置视频视图
     * @param ijkVideoView
     */
    public void setIjkVideoView(IjkVideoView ijkVideoView) {
        this.ijkVideoView = ijkVideoView;
    }

    /**
     * 设置信息条广告
     * @param adInfoUrl
     */
    public void setAdInfoUrl(String adInfoUrl) {
        this.adInfoUrl = adInfoUrl;
    }
    /**
     * 设置播放完成广告
     * @param adCompleteUrl
     */
    public void setAdCompleteUrl(String adCompleteUrl) {
        this.adCompleteUrl = adCompleteUrl;
    }
    /**
     * 设置退出广告
     * @param adExitUrl
     */
    public void setAdExitUrl(String adExitUrl) {
        this.adExitUrl = adExitUrl;
    }

    /**
     * 设置音量广告
     * @param adVolumeBarUrl
     */
    public void setAdVolumeBarUrl(String adVolumeBarUrl) {
        this.adVolumeBarUrl = adVolumeBarUrl;
    }

    public void showLoadingView() {
        handler.sendEmptyMessage(MESSAGE_SHOW_LOADING_VIEW);
    }

    public void hideLoadingView() {
        handler.sendEmptyMessage(MESSAGE_HIDE_LOADING_VIEW);
    }

    /**
     * 设置正在播放和即将播放的数据
     */
    public void setProgramInfo(){
        if(playerType== MainActivity.LIVE) {
            //直播
            if(channel!=null) {
                infoLookBack = getCurrentNextLookBack();
                //设置节目名
                if (!TextUtils.isEmpty(channel.getChannelname())) {
                    tvprogramName.setText(channel.getChannelname());
                    tvprogramName.setVisibility(VISIBLE);
                }else{
                    tvprogramName.setVisibility(GONE);
                }
            }
            tvTotalTime.setVisibility(GONE);
            tvLine.setVisibility(GONE);

        }else if(playerType== MainActivity.LOOK_BACK){
            //回看
            infoLookBack = getCurrentNextLookBack();
            if(null!=infoLookBack && infoLookBack.size()>0){
                tvprogramName.setText(infoLookBack.get(0).getPrevuename());
            }
            tvTotalTime.setVisibility(VISIBLE);
            tvLine.setVisibility(VISIBLE);
        }

        if(null!=infoLookBack && infoLookBack.size()>0){
            for (int i = 0; i < infoLookBack.size(); i++) {
                String starTime="";
                String endTime="";
                if(playerType== MainActivity.LIVE){
                    starTime = DateTimeUtil.formatTime(infoLookBack.get(i).getBegintime(), DateTimeUtil.yyyyPointMMPointdd_HH_mm_ss, DateTimeUtil.HH_mm);
                    endTime = DateTimeUtil.formatTime(infoLookBack.get(i).getEndtime(), DateTimeUtil.yyyyPointMMPointdd_HH_mm_ss, DateTimeUtil.HH_mm);
                }else {
                    starTime = infoLookBack.get(i).getBegintime();
                    endTime = infoLookBack.get(i).getEndtime();
                }
                if(i==0){
                    tvCurrentProgram.setText(infoLookBack.get(i).getPrevuename());
                    tvCurrentTimeDuration.setText(starTime+"-"+endTime);
                    tvNextProgrm.setText("暂无节目");
                    tvNextTimeDuration.setText("");
                }else if(i==1){
                    tvNextProgrm.setText(infoLookBack.get(i).getPrevuename());
                    tvNextTimeDuration.setText(starTime+"-"+endTime);
                }
            }
        }else{
            tvCurrentProgram.setText("暂无节目");
            tvCurrentTimeDuration.setText("");
            tvNextProgrm.setText("暂无节目");
            tvNextTimeDuration.setText("");
        }
    }

    /**
     * 获取当前播放节目和下个节目
     * @return
     */
    private List<LookBackModel> getCurrentNextLookBack() {
        List<LookBackModel> looks = new ArrayList<>();
        if(playerType== MainActivity.LIVE) {
            boolean isFind = false;
            if(nextPrograms==null || nextPrograms.size()==0){
                return looks;
            }
            for (LookBackModel backModel : nextPrograms) {
                if (isFind || (DateTimeUtil.getTimeDelta12(getCurrentTime(), backModel.getBegintime()) >= 0
                        && DateTimeUtil.getTimeDelta12(getCurrentTime(), backModel.getEndtime()) < 0)) {
                    looks.add(backModel);
                    if(isFind){
                        return looks;
                    }
                    isFind = true;
                }
            }

        }else if(playerType== MainActivity.LOOK_BACK){
            if(lookBacks!=null && lookBacks.size()>datePosition){
                if(lookBacks.get(datePosition)!=null && lookBacks.get(datePosition).size()>lookBackPosition) {
                    looks.add(lookBacks.get(datePosition).get(lookBackPosition));
                    if(lookBackPosition < lookBacks.get(datePosition).size()-1){
                        looks.add(lookBacks.get(datePosition).get(lookBackPosition+1));
                    }else{
                        if(datePosition < lookBacks.size()-1){
                            looks.add(lookBacks.get(datePosition+1).get(0));
                        }
                    }
                }
            }
        }
        return looks;
    }

    /**
     * 获取影片总时间
     * @return
     */
    public long getDuration() {
        return duration;
    }


    /**
     * 显示时间移动视图
     * @param speed
     */
    public void showTimshiftView(float speed) {
        this.speed=speed;
        Message mesage=handler.obtainMessage();
        mesage.what=MESSAGE_SHOW_TIMSHIFT_VIEW;
        mesage.obj=speed;
        handler.sendMessage(mesage);
    }

    public void hideTimeShiftView(){
        handler.sendEmptyMessage(MESSAGE_HIDE_TIMESIFT_VIEW);
    }


    /**
     * 显示暂停视图
     */
    public void showPauseView() {
        rlRoot.setBackgroundColor(context.getResources().getColor(R.color.color_20000000));
        progressBarLoading.setVisibility(GONE);
        imgTimeShiftStatus.setVisibility(GONE);
        imgPlay.setVisibility(View.VISIBLE);
    }

    public boolean isShowShift(){
        if(imgTimeShiftStatus.getVisibility()==View.VISIBLE){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 隐藏暂停视图
     */
    public void hidePauseView() {
        rlRoot.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        imgPlay.setVisibility(View.GONE);
    }

    /**
     * 是否显示
     * @return
     */
    public boolean isShowing() {
        if (llMediaControll.getVisibility()==View.VISIBLE){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 显示控制条，然后在指定时间后隐藏控制条，如果timeout<=0,则，认为是不隐藏进度条
     * 显示控制条期间每隔1秒刷新进度
     * @param timeout
     */
    public void showControlView(int timeout) {
        //设置信息
        setProgramInfo();
        if (!TextUtils.isEmpty(adInfoUrl)){
            Glide.with(context).load(adInfoUrl).error(R.drawable.bg_ad_350_280).into(ivAdForInfo);
        }
        handler.removeMessages(MESSAGE_HIDE_CONTROLL_VIEW);
        handler.sendEmptyMessage(MESSAGE_SHOW_CONTROLL_VIEW);
        if (timeout>0){
            //如果时间大于0，则在延时timeout秒后隐藏
            handler.sendEmptyMessageDelayed(MESSAGE_HIDE_CONTROLL_VIEW,timeout);
        }
        //隐藏菜单
        handler.sendEmptyMessage(MESSAGE_HIDE_CHANNEL_VIEW);
        //隐藏音量视图
        handler.sendEmptyMessage(MESSAGE_HIDE_VOLUME_BAR);

    }

    public void hideControlView() {
        handler.sendEmptyMessage(MESSAGE_HIDE_CONTROLL_VIEW);
    }

    /**
     * 播放结束视图
     */
    public void showCompleteView() {
        handler.sendEmptyMessage(MESSAGE_SHOW_COMPLETE);
        btCompeleteExit.setFocusable(true);
        btCompeleteExit.setFocusableInTouchMode(true);
        btCompeleteExit.requestFocus();
        if (!TextUtils.isEmpty(adCompleteUrl)) {
            Glide.with(context).load(adCompleteUrl).error(R.drawable.bg_ad_600_280).into(ivAdForCompelete);
        }

        btCompeleteExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                destoryTime();
                if (null!=onCompeleteButtonListener){
                    onCompeleteButtonListener.onExit();
                }
            }
        });
        //倒计时
        timer = new Timer();
        timer.schedule(new MyTask(),0,1000);
    }

    class MyTask extends TimerTask {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            handler.sendEmptyMessage(MESSAGE_COUNT_DOWN);
        }
    }

    /**
     * 停止倒计时
     */
    public void destoryTime(){
        if(null!=timer){
            timer.cancel();
            timer.purge();
        }
    }

    /**
     * 处理倒计时
     */
    private void dealWithCountDown(){
        if(recLen<1){
            destoryTime();
            if(playerType==MainActivity.LOOK_BACK) {
                //回看，自动播放下一个节目
                if (null != onCompeleteButtonListener) {
                    if(null!=infoLookBack && infoLookBack.size()>1){
                        llError.setVisibility(GONE);
                        onCompeleteButtonListener.onNext(infoLookBack.get(infoLookBack.size()-1));
                        lookBackPosition++;
                        if(lookBacks.size()>datePosition && lookBacks.get(datePosition).size()==lookBackPosition){
                            if(datePosition < lookBacks.size()-1){
                                datePosition++;
                                lookBackPosition=0;
                            }
                        }
                        recLen = 5;
                    }else {
                        onCompeleteButtonListener.onExit();
                    }
                }
            }else if(playerType==MainActivity.ON_DEMAND){
                //点播，退出
                if (null != onCompeleteButtonListener) {
                    onCompeleteButtonListener.onExit();
                }
            }
        }else{
            if(playerType==MainActivity.LOOK_BACK && null!=infoLookBack && infoLookBack.size()>1) {
                btCompeleteSee.setText(recLen + "秒后自动播放下一个节目");
            }else{
                btCompeleteSee.setText(recLen + "秒后自动退出");
            }
        }
        recLen--;
    }

    /**
     * 隐藏播放完成视图
     */
    public void hideCompleteView() {
        handler.sendEmptyMessage(MESSAGE_HIDE_COMPLETE);
    }

    /**
     * 显示退出视图
     */
    public void showExitView() {
        handler.sendEmptyMessage(MESSAGE_SHOW_EXIT);
        btExit.setFocusable(true);
        btExit.setFocusableInTouchMode(true);
        btExit.requestFocus();
        if (!TextUtils.isEmpty(adExitUrl)) {
            Glide.with(context).load(adExitUrl).error(R.drawable.bg_ad_600_280).into(ivAdForExit);
        }
        btExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null!=onCompeleteButtonListener) {
                    onCompeleteButtonListener.onExit();
                }
            }
        });
        btSee.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null!=onCompeleteButtonListener) {
                    onCompeleteButtonListener.onSee();
                }
            }
        });
    }


    /**
     * 隐藏退出视图
     */
    public void hideExitView() {
        llExitView.setVisibility(GONE);
    }


    /**
     * 显示无网络视图
     */
    public void showNoNet(){
        hideControlView();
        hideExitView();
        hidePauseView();
        hideLoadingView();
        hideCompleteView();
        hideTimeShiftView();
        hideChannelView(0);
        hideVolumeBarView(0);
        hideErrorView();
        llNoNet.setVisibility(VISIBLE);
    }


    /**
     * 隐藏无网络视图
     */
    public void hideNoNetView() {
        llNoNet.setVisibility(GONE);
    }

    /**
     * 显示播放错误视图
     * @param tryAgainButtonListener 重试回调接口
     */
    public void showError(OnTryAgainButtonListener tryAgainButtonListener){
        this.onTryAgainButtonListener=tryAgainButtonListener;
        hideControlView();
        hideExitView();
        hidePauseView();
        hideLoadingView();
        hideCompleteView();
        hideTimeShiftView();
        hideChannelView(0);
        hideVolumeBarView(0);
        hideNoNetView();
        btTryAgain.setFocusable(true);
        btTryAgain.requestFocus();
        btTryAgain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null!=onTryAgainButtonListener){
                    onTryAgainButtonListener.tryAgain();
                }
            }
        });
        if(playerType==MainActivity.LIVE){
            tvErrorCode.setText("错误代码：0005");
            tvErrorInfo.setText("错误信息：频道无法播放");
        }else{
            tvErrorCode.setText("错误代码：0023");
            tvErrorInfo.setText("错误信息：节目播放失败");
        }
        llError.setVisibility(VISIBLE);

    }


    /**
     * 隐藏错误视图
     */
    public void hideErrorView() {
        llError.setVisibility(GONE);
    }

    /**
     * 错误视图是否显示
     * @return
     */
    public boolean isShowErrorView(){
        if (llError.getVisibility()==View.VISIBLE){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 无网络视图是否显示
     * @return
     */
    public boolean isShowNetView(){
        if (llNoNet.getVisibility()==View.VISIBLE){
            return true;
        }else {
            return false;
        }
    }

    /**
     *播放完成视图是否显示
     * @return
     */
    public boolean isShowComplete(){
        if (llCompeleteView.getVisibility()==View.VISIBLE){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 退出视图是否显示
     * @return
     */
    public boolean isShowExit(){
        if (llExitView.getVisibility()==View.VISIBLE){
            return true;
        }else {
            return false;
        }
    }


    /**
     * 显示节目选择器
     * @param
     */
    public void showChannelView(int timeout){
        handler.removeMessages(MESSAGE_HIDE_CHANNEL_VIEW);
        handler.sendEmptyMessage(MESSAGE_SHOW_CHANNEL_VIEW);
        if (timeout>0){
            //如果时间大于0，则在延时timeout秒后隐藏
            handler.sendEmptyMessageDelayed(MESSAGE_HIDE_CHANNEL_VIEW,timeout);
        }
        //隐藏信息条
        hideControlView();
        //隐藏音量条
        hideVolumeBarView(0);
    }

    /**
     * 是否显示直播节目单
     * @return
     */
    public  boolean isChannelViewShow(){
        if (channelView.getVisibility()==VISIBLE){
            return true;
        }else {
            return false;
        }
    }

    public void rightKeyDown(){
        channelView.rightKeyDown();
        hideChannelView(6000);
    }


    public void leftKeyDown(){
        channelView.leftKeyDown();
        hideChannelView(6000);
    }

    /**
     * listview选择监听
     * @param itemSelected
     */
    public void selectedItemListener(ChannelView.ListViewItemSelected itemSelected){
        channelView.setItemSelected(itemSelected);
    }

    /**
     * 隐藏节目选择菜单
     */
    public void hideChannelView(int timeeout){
        handler.removeMessages(MESSAGE_HIDE_CHANNEL_VIEW);
        if (timeeout>=0){
            handler.sendEmptyMessageDelayed(MESSAGE_HIDE_CHANNEL_VIEW,timeeout);
        }
    }

    /**
     * 显示频道号
     * @param number
     */
    public void showChannelNumber(String number){
        Message msg = new Message();
        msg.obj = number;
        msg.what = MESSAGE_SHOW_CHANNEL_NUMBER;
        handler.sendMessage(msg);
        hideChannelNumber(3000);
    }

    /**
     * 隐藏频道号
     * @param timeeout
     */
    public void hideChannelNumber(int timeeout){
        handler.removeMessages(MESSAGE_HIDE_CHANNEL_NUMBER);
        if (timeeout>=0){
            handler.sendEmptyMessageDelayed(MESSAGE_HIDE_CHANNEL_NUMBER,timeeout);
        }
    }

    /**
     * 显示音量条
     */
    public void showVolumeBarView(){
        handler.sendEmptyMessage(MESSAGE_SHOW_VOLUME_BAR);
        hideVolumeBarView(3000);

        if (!TextUtils.isEmpty(adVolumeBarUrl)){
            volumeBarView.setAd(adVolumeBarUrl);
        }
    }

    /**
     * 隐藏音量条
     * @param timeeout
     */
    public void hideVolumeBarView(int timeeout){
        handler.removeMessages(MESSAGE_HIDE_VOLUME_BAR);
        if (timeeout>=0){
            handler.sendEmptyMessageDelayed(MESSAGE_HIDE_VOLUME_BAR,timeeout);
        }
    }

    /**
     * 减少音量
     */
    public void cutVolume(){
        showVolumeBarView();
        volumeBarView.cutVolume();
    }
    /**
     * 增加音量
     */
    public void addVolume(){
        showVolumeBarView();
        volumeBarView.addVolume();
    }

    /**
     * 完成视图的按钮回调
     */
    private OnCompeleteButtonListener onCompeleteButtonListener;
    /**
     * 重试按钮回调
     */
    private OnTryAgainButtonListener onTryAgainButtonListener;

    public void setOnCompeleteButtonListener(OnCompeleteButtonListener onCompeleteButtonListener) {
        this.onCompeleteButtonListener = onCompeleteButtonListener;
    }

    public void setOnTryAgainButtonListener(OnTryAgainButtonListener onTryAgainButtonListener) {
        this.onTryAgainButtonListener = onTryAgainButtonListener;
    }

    public interface OnCompeleteButtonListener{
        //退出
        void onExit();
        //继续观看
        void onSee();
        //回看播放下一个节目
        void onNext(LookBackModel lookBackModel);
    }


    public interface OnTryAgainButtonListener{
        void tryAgain();
    }

    /**
     * 显示Logo
     */
    public void showLogo(){
        imgLogo.setVisibility(VISIBLE);
    }

    /**
     * 隐藏LOGO
     */
    public void hideLogo(){
        imgLogo.setVisibility(GONE);
    }

    /**
     * 获取整个View的高度
     * @return
     */
    public  int getRootViewHeight(){
        return rlRoot.getHeight();
    };


    /**
     * 设置整个高度
     * @param height
     * @param dip
     */
    public void setRootViewHeight(int height, boolean dip){
        height(rlRoot,height,dip);
    }



    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener mSeekListener){
        seekBar.setOnSeekBarChangeListener(mSeekListener);
    }


    /**\
     * 设置View的宽和高
     * @param view
     * @param width
     * @param n
     * @param dip
     */
    private void size(View view,boolean width, int n, boolean dip){
        if(view != null){
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if(n > 0 && dip){
                n = dip2pixel(context, n);
            }
            if(width){
                lp.width = n;
            }else{
                lp.height = n;
            }
            view.setLayoutParams(lp);
        }
    }

    /**
     * 设置View的宽和高
     * @param view
     * @param height
     * @param dip
     */
    private void height(View view,int height, boolean dip) {
        size(view,false,height,dip);
    }

    /**
     * dp转成px单位
     * @param context
     * @param n
     * @return
     */
    private int dip2pixel(Context context, float n){
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, n, context.getResources().getDisplayMetrics());
        return value;
    }

    /**
     * 设置进度和时间 单位毫秒
     * @return 返回当前的播放时间
     */
    private long setProgress() {

        long position = ijkVideoView.getCurrentPosition();
        long duration = ijkVideoView.getDuration();
        DebugUtil.e("position>>"+position+">>>duration>>>"+duration);
        if (isLive){
            if (seekBar != null) {
                long pos = 1000;
                seekBar.setProgress((int) pos);
            }
            this.duration = position;
            if(null!=channelView) {
                tvCurrentTime.setText(DateTimeUtil.formatTime(getCurrentTime(), DateTimeUtil.yyyy_MM_dd_HH_mm_ss, DateTimeUtil.HH_mm));
            }
        }else {
            if (seekBar != null) {
                if (duration > 0) {
                    long pos = 1000L * position / duration;
                    seekBar.setProgress((int) pos);
                }
                int percent = ijkVideoView.getBufferPercentage();
                DebugUtil.e("position>>>>percent>>"+percent);
                seekBar.setSecondaryProgress(percent * 10);
            }
            this.duration = duration;
            //设置时间
            //在直播状态的时间，是否还需要，待定
            tvCurrentTime.setText(generateTime(position));
            tvTotalTime.setText(generateTime(this.duration));

        }
        return position;
    }

    /**
     * 生成时间
     * @param time 时间毫秒单位
     * @return
     */
    private String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }


    /**
     * 是否是直播
     * @param live
     */
    public void setLive(boolean live) {
        isLive = live;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public void removeCallBack(){
        handler.removeCallbacksAndMessages(null);
        destoryTime();
    }

    /**
     * 设置直播频道数据
     * @param channel
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    /**
     * 设置所有回看数据，以及播放的回看数组postion
     * @param lookBacks
     * @param lookBackPosition
     */
    public void setLookBack(List<List<LookBackModel>> lookBacks, int lookBackPosition){
        this.lookBacks = lookBacks;
        this.lookBackPosition = lookBackPosition;
        this.datePosition=0;
    }

    /**
     * 直播模式下设置的未来播放节目
     * @param nextPrograms
     */
    public void setNextPrograms(List<LookBackModel> nextPrograms, String time) {
        this.nextPrograms = nextPrograms;
        try {
            if(!TextUtils.isEmpty(time)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtil.yyyy_MM_dd_HH_mm_ss);
                Date data = dateFormat.parse(time);
                currentTime = data.getTime();
                handler.removeMessages(100);
                handler.sendEmptyMessageDelayed(100, 60 * 1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ChannelView.OnListViewItemClickListener onListViewItemClickListener;

    public void setOnListViewItemClickListener(ChannelView.OnListViewItemClickListener onListViewItemClickListener) {
        this.onListViewItemClickListener = onListViewItemClickListener;
    }

    /**
     * 菜单点击事件
     */
    ChannelView.OnListViewItemClickListener onItemClickListener = new ChannelView.OnListViewItemClickListener() {

        @Override
        public void onLookBackItemClick(Channel channel, List<List<LookBackModel>> lookBacks, int position) {
            if(onListViewItemClickListener!=null){
                onListViewItemClickListener.onLookBackItemClick(channel,lookBacks, position);
            }
        }

        @Override
        public void onChannelItemClick(Channel model) {
            if(onListViewItemClickListener!=null){
                onListViewItemClickListener.onChannelItemClick(model);
            }
        }

        @Override
        public void onRightKeyDown(String channelCode) {
            if(onListViewItemClickListener!=null){
                onListViewItemClickListener.onRightKeyDown(channelCode);
            }
        }
    };


    public String getMenuSelectChannelCode(){
        return channelView.selectChannelCode();
    }

    /**
     * 获取当前时间
     * @return
     */
    public String getCurrentTime(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtil.yyyy_MM_dd_HH_mm_ss);
            if (currentTime == 0) {
                currentTime = DateTimeUtil.getCurrentTimeMillisForLong();
            }
            return dateFormat.format(new Date(currentTime));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
