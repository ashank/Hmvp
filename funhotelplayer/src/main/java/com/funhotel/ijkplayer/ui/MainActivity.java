package com.funhotel.ijkplayer.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.funhotel.ijkplayer.FunhotelPlayer;
import com.funhotel.ijkplayer.IjkVideoView;
import com.funhotel.ijkplayer.MediaControllerView;
import com.funhotel.ijkplayer.R;
import com.funhotel.ijkplayer.bean.TvodData;
import com.funhotel.tvllibrary.application.ADServiceModel;
import com.funhotel.tvllibrary.application.BaseUrl;
import com.funhotel.tvllibrary.application.Channel;
import com.funhotel.tvllibrary.application.FiberHomeChannelData;
import com.funhotel.tvllibrary.application.LookBackData;
import com.funhotel.tvllibrary.application.LookBackModel;
import com.funhotel.tvllibrary.application.PublicConstant;
import com.funhotel.tvllibrary.db.ChannelManager;
import com.funhotel.tvllibrary.db.FiberHomeChannelDBManager;
import com.funhotel.tvllibrary.db.TableKey;
import com.funhotel.tvllibrary.utils.DebugUtil;
import com.funhotel.tvllibrary.utils.IptvAuthenticationUitls;
import com.funhotel.tvllibrary.utils.ParseJson;
import com.funhotel.tvllibrary.utils.UserIdUtils;
import com.funhotel.tvllibrary.view.AdvancedWebView;
import com.funhotel.tvllibrary.view.ChannelView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MainActivity extends LiveTvBaseActivity{
    private FunhotelPlayer player;

    private  String url333 = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    private  String url100 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";


    private int position=0;
    private boolean isLive=false;
    /**
     * 快进快退步
     */
    private float forwardStep=0.0f;
    private float backStep=0.0f;
    private NetWorkBroadcastReceiver netWorkBroadcastReceiver;
    private String stbID;

    private WifiManager.MulticastLock multicastLock;
    private MulticastSocket multicastSocket = null;
    private InetAddress serverAddress = null;
    private static int BROADCAST_PORT=7998;
    private static String BROADCAST_IP="239.253.2.3";

    private Channel channel;//当前正在播放的channel
    private List<List<LookBackModel>> lookBacks = new ArrayList<>();//每一天所对应回看数据
    private int lookBackPosition;//观看的回看数据position，用于查找
    private List<Channel> allChannels = new ArrayList<>();//全部频道数据
    private int currentPosition;//当前播放频道在allChannel数组中的position
    private FiberHomeChannelData fiberHomeChannelData;
    private int playerType=0;//播放类型
    public static final int LIVE = 0;//直播
    public static final int LOOK_BACK = 1;//回看
    public static final int ON_DEMAND = 2;//点播
    public static final String PLAYER_TYPE = "player_type";
    private String playerUrl;//播放地址
    private boolean isNextProgram = false;//是否自动跳转播放的吓一个节目
    private Channel tempChannel;//当前正在播放的channel，临时数据，鉴权成功后使用
    private List<List<LookBackModel>> tempLookBacks = new ArrayList<>();//每一天所对应回看数据，临时数据，鉴权成功后使用
    private int tempLookBackPosition;//临时数据，鉴权成功后使用
    private boolean isFirstPlayer = true;//第一次进入播控标记

    private String numberCode = "";//按键台号
    /**
     * 鉴权的设置
     */
    //内容类型
    public String contenttype = "4";
    //终端类型
    public String terminalflag = "STB";
    //栏目Code，回看对应的频道的栏目code
    public String columncode = "";
    //清晰度标识
    public String definition = "0";
    //节目code
    public String programcode = "";


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    player.hideChannelView(0);
                    player.hideChannelNumber();
                    playUrl();
                    break;
                case 2:
                    //切换到回看模式播放
//                    player.play(url100);
                    break;
                case 3:
                    player.showChannelNumber(numberCode);
                    removeMessages(4);
                    sendEmptyMessageDelayed(4,3000);
                    break;
                case 4:
                    findChannelPosition(numberCode);
                    numberCode = "";
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mWebView = (AdvancedWebView) findViewById(R.id.web_view);
        iniWebView();
        stbID = UserIdUtils.newInstance(this).getUserId();
        channel = (Channel) getIntent().getSerializableExtra("channel");
        lookBacks = (List<List<LookBackModel>>) getIntent().getSerializableExtra("lookbacks");
        lookBackPosition = getIntent().getIntExtra("mGridPosition",0);
        playerType = getIntent().getIntExtra(PLAYER_TYPE,0);
        playerUrl = getIntent().getStringExtra("url");

        if(playerType==LIVE){
            isLive = true;
        }else{
            isLive = false;
        }
        //获取回看数据
        if (null!=channel && isLive){
            if (!TextUtils.isEmpty(channel.getChannelcode())){
                String url=getIP("/channelprevuforlive.jsp?channelcode="+ channel.getChannelcode());
                loadUrl(url);
            }
        }

//        allowMulticast();
//        sendServiceIP();

        //绑定广播
        netWorkBroadcastReceiver = new NetWorkBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkBroadcastReceiver, filter);
        //初始化
        IjkVideoView ijkVideoView = (IjkVideoView) findViewById(R.id.video_view);
        MediaControllerView media_control = (MediaControllerView) findViewById(R.id.media_control);
        player = new FunhotelPlayer(this, ijkVideoView);
        player.setScaleType(FunhotelPlayer.SCALETYPE_FITXY);
        player.live(isLive);
        player.type(playerType);
        player.setMediaControllerView(media_control);
        player.setCompeleteButtonListener(onCompeleteButtonListener);
        player.setNormalListener(normalListerner);
        player.setOnListViewItemClickListener(onItemClickListener);
//      player.setRightKeyDownListener(mOnRightKeyDownListener);
        //设置满屏
//        player.toggleAspectRatio();
        //设置全屏播放
        player.setFullScreenOnly(true);

        player.setCurrentChannel(channel);
        player.setLookBacks(lookBacks,lookBackPosition);


        if(isLive){
            getAllChannelThread();
        }else{
            if(!TextUtils.isEmpty(playerUrl)) {
                player.play(playerUrl);
            }
        }

        /**
         * 注册回调事件
         */
        /*//完成回调
        player.onComplete(runnable);
        //信息回掉
        player.onInfo(onInfoListener);
        //错误回掉
        player.onError(onErrorListener);*/
        //获取广告数据
        starADThread();
    }

    /**
     * 获取所有频道数据
     */
    private void getAllChannelThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                //获取全部数据
                allChannels = ChannelManager.getChannel(MainActivity.this, stbID);
                for (int i = 0; i < allChannels.size(); i++) {
                    for (int j = allChannels.size() - 1; j > i; j--) {
                        if(TextUtils.equals(allChannels.get(i).getChannelcode(), allChannels.get(j).getChannelcode())){
                            allChannels.remove(j);
                        }
                    }
                }
                if(channel!=null) {
                    findChannelPosition(channel.getMixno());
                }
                Looper.loop();
            }
        }).start();
    }

    /**
     * 根据查找channel在数组中的位置
     */
    private void findChannelPosition(String mixNo){
        if(!TextUtils.isEmpty(mixNo)){
            //去掉前面的0
            mixNo = mixNo.replaceAll("^(0+)", "");
            for (int i = 0; i < allChannels.size(); i++) {
                if(TextUtils.equals(allChannels.get(i).getMixno(),mixNo)){
                    position = i;
                    handler.sendEmptyMessage(1);
                    return;
                }
            }
        }
    }

    public void sendServiceIP(){
        try{
            multicastSocket= new MulticastSocket(BROADCAST_PORT);
            serverAddress = InetAddress.getByName(BROADCAST_IP);
            multicastSocket.joinGroup(serverAddress);
            new Thread(){
                @Override
                public void run() {
                    try{
                        DebugUtil.d("开始接收信息了=====================");
                        byte[] data= new byte[1024];
                        DatagramPacket datagramPackage = new DatagramPacket(data ,
                                data.length,
                                serverAddress,
                                BROADCAST_PORT);

                        while (true){
                            multicastSocket.receive(datagramPackage);
                            String serverIP=new String(datagramPackage.getData() ,
                                    datagramPackage.getOffset() , datagramPackage.getLength());
                            Thread.sleep(100);
                            DebugUtil.d("接收到的IP>>>>>>>："+datagramPackage.getAddress().getHostAddress()
                                    +">>>>"+datagramPackage.getPort());
                            DebugUtil.d("接收到的IP："+serverIP);
                            System.out.print("接收到的的数据>>>>>"+serverIP);
                        }
                    }catch (Exception e){
                        e.getMessage();
                    }
                }
            }.start();
        }catch (Exception e){
            e.getMessage();
        }
    }


    //android默认关闭组播功能，打开组播的功能
    //Android的Wifi，默认情况下是不接受组播的，
    private void allowMulticast(){
        WifiManager wifiManager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
        multicastLock=wifiManager.createMulticastLock("multicast.test");
        multicastLock.acquire();
    }

    private void leaveGroup(){
        if (null!=multicastSocket){
            try{
                multicastSocket.leaveGroup(serverAddress);
                multicastSocket.close();
            }catch (IOException e){

            }
        }
        if (null!=multicastLock){
            multicastLock.release();
        }

    }



    /**
     * dialog
     */
    private MediaControllerView.OnCompeleteButtonListener onCompeleteButtonListener=new MediaControllerView.OnCompeleteButtonListener() {
        @Override
        public void onExit() {
           finish();
        }
        @Override
        public void onSee() {
            //重试
            if(null==player){
                return;
            }
            if(isLive){
                if(!TextUtils.isEmpty(playerUrl)) {
                    player.play(playerUrl);
                }
            }else{
                player.start();
            }
        }

        @Override
        public void onNext(LookBackModel lookBackModel) {
            //播放下一条回看节目
            isNextProgram = true;
            palyerLookBack(channel, lookBackModel);
        }

    };


    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
        unregisterReceiver(netWorkBroadcastReceiver);
        leaveGroup();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        DebugUtil.e("KeyCode>>>>>>>"+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        DebugUtil.e("KeyCode>>>>>>>"+keyCode);
        if (keyCode!=KeyEvent.KEYCODE_BACK && keyCode!=KeyEvent.KEYCODE_DPAD_CENTER){
            if (player.isShowNetView() || player.isShowComplete() || player.isShowExit() || player.isShowError()){
                if(keyCode==KeyEvent.KEYCODE_VOLUME_UP || keyCode==KeyEvent.KEYCODE_VOLUME_DOWN){
                    //禁止系统音量键
                    return true;
                }
                return false;
            }
        }
        if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT|| keyCode==KeyEvent.KEYCODE_FORWARD
                ||keyCode==KeyEvent.KEYCODE_MEDIA_FAST_FORWARD){
            //右键、快进键
            if (player.isChannelViewShow()&&keyCode==KeyEvent.KEYCODE_DPAD_RIGHT){
//                String channelCode = player.getMenuSelectChannelCode();
//                if (!TextUtils.isEmpty(channelCode)){
//                    String url=getIP("/channelprevu.jsp?channelcode="+ channelCode);
//                    loadUrl(url);
//                }
                player.rightKeyDown();
                return false;
            }
            if (!player.isPlayCompelete()&&player.isPlaying()){
                //右键,快进
                forwardStep++;
                backStep=0.0f;
                float speed=(float) Math.pow(2,forwardStep);
                player.forward(speed);
                if (speed==32){
                    forwardStep=0;
                }
            }
        }else  if (keyCode==KeyEvent.KEYCODE_DPAD_LEFT
                ||keyCode==KeyEvent.KEYCODE_MEDIA_REWIND){
            //左键、快退键
            if (player.isChannelViewShow()&&keyCode==KeyEvent.KEYCODE_DPAD_LEFT){
                player.leftKeyDown();
                player.hideChannelView(5000);
                return false;
            }
            if (!player.isPlayCompelete()&&player.isPlaying()) {
                //左键，和快退
                forwardStep=0.0f;
                backStep++;
                float speed=(float) Math.pow(2,backStep);
                player.forward(-speed);
                if (speed==32){
                    backStep=0;
                }
            }

        }else if (keyCode==KeyEvent.KEYCODE_DPAD_UP || keyCode==KeyEvent.KEYCODE_CHANNEL_UP ){
            //上键、频道+键
            forwardStep=0.0f;
            backStep=0.0f;
            if (player.isChannelViewShow()|| !isLive){
                return false;
            }
            position++;
            if(null!=allChannels && position > allChannels.size() - 1) {
                position = 0;
            }
            playUrl();

        }else if (keyCode==KeyEvent.KEYCODE_DPAD_DOWN || keyCode==KeyEvent.KEYCODE_CHANNEL_DOWN){
            //下键、频道-键
            forwardStep=0.0f;
            backStep=0.0f;
            if (player.isChannelViewShow() || !isLive){
                return false;
            }
            position--;
            if(position<0 && allChannels!=null && allChannels.size()>0) {
                position = allChannels.size() - 1;
            }
            playUrl();

        }else if (keyCode==KeyEvent.KEYCODE_MENU || keyCode==KeyEvent.KEYCODE_DPAD_CENTER){
            //中间确认键
            forwardStep=0.0f;
            backStep=0.0f;
            if(player.isShowShiftTime()){
                //正在快进和快退，恢复正常播放

                return false;
            }
            if (isLive){
                player.showChannelView(5000);
            }else {
                if (!player.isShowNetView() && !player.isShowExit() && !player.isPlayCompelete()){
                    if (player.isPlaying() && !player.isShowShiftTime()){
                        player.pause();
                    }else {
                        player.start();
                    }
                }
            }
        }else  if (keyCode== KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE){
            //播放暂停按钮键
            forwardStep=0.0f;
            backStep=0.0f;
            if (!isLive){
                if (!player.isPlayCompelete()){
                    if (player.isPlaying() && !player.isShowShiftTime()){
                        player.pause();
                    }else {
                        player.start();
                    }
                }
            }
        }else if(keyCode==KeyEvent.KEYCODE_MEDIA_STOP){
            //停止播放键
            finish();

        }else if (keyCode==KeyEvent.KEYCODE_BACK){
            //返回键
            forwardStep=0.0f;
            backStep=0.0f;
            if(player.isShowError()){
                //显示错误提示时
                finish();
                return false;
            }
            if(player.isShowShiftTime()){
                //正在快进和快退，恢复正常播放

                return false;
            }
            if (!isLive){
                //不是直播，退出要有提示
                if (!player.isShowComplete()){
                    if (!player.isShowExit()) {
                        player.pause();
                        player.onBackPressedAction();
                        return false;
                    } else {
                        player.cancelBackPressedAction();
                        player.start();
                        return true;
                    }
                }

            }else {
                //如果是直播，如果节目单显示，则隐藏节目单，在按键退出
                if (player.isChannelViewShow()){
                    player.hideChannelView(0);
                    return false;
                }
            }
        }else if(keyCode==KeyEvent.KEYCODE_VOLUME_UP){
            //加音量
            player.addVolume();
            return true;
        }else if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN){
            //减音量
            player.cutVolume();
            return true;
        }else if(keyCode>=KeyEvent.KEYCODE_0 && keyCode<=KeyEvent.KEYCODE_9){
            //数字键换台
            if(playerType==LIVE) {
                int code = keyCode - 7;
                if (numberCode.length() > 2) {
                    numberCode = code + "";
                } else {
                    numberCode = numberCode + code;
                }
                handler.sendEmptyMessage(3);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 播放错误回掉
     *//*
    private FunhotelPlayer.OnErrorListener onErrorListener=new FunhotelPlayer.OnErrorListener() {
        @Override
        public void onError(int what, int extra) {
            Toast.makeText(getApplicationContext(), "video play error",Toast.LENGTH_SHORT).show();
        }
    };

    *//**
     * 播放的信息回调
     *//*
    private FunhotelPlayer.OnInfoListener onInfoListener=new FunhotelPlayer.OnInfoListener() {
        @Override
        public void onInfo(int what, int extra) {
            switch (what) {
                case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    //do something when buffering start
                    //开始缓冲
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    //do something when buffering end
                    //缓冲完毕
                    break;
                case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                    //download speed
//                  ((TextView) findViewById(R.id.tv_speed)).setText(Formatter.formatFileSize(getApplicationContext(),extra)+"/s");
                    break;
                case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    //渲染开始
                    //do something when video rendering
//                  findViewById(R.id.tv_speed).setVisibility(View.GONE);
                    break;
            }
        }
    };

    *//**
     * 播放完成的回调
     *//*
    private Runnable runnable= new Runnable() {
        @Override
        public void run() {
            //callback when video is finish
            Toast.makeText(getApplicationContext(), "video play completed",Toast.LENGTH_SHORT).show();

        }
    };*/

    /**
     * 恢复正常播放
     */
    private FunhotelPlayer.RecoveryNormalListener normalListerner = new FunhotelPlayer.RecoveryNormalListener() {
                @Override
                public void onNormal() {
                    forwardStep=0.0f;
                    backStep=0.0f;
                }
            };

    /**
     * 网络广播
     */
    public class NetWorkBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkInfo.State wifiState = null;
            NetworkInfo.State netState=null;
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            netState = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET).getState();
            DebugUtil.i("网络监听---》wifiState="+wifiState+ " netState=" + netState);
            if (netState != null
                    && NetworkInfo.State.CONNECTED == netState) {
                // 宽带网络连接成功
                if (player != null) {
                    player.onResume();
                    player.setNetWork(true);
                }
            } else if (wifiState != null && netState != null
                    && NetworkInfo.State.CONNECTED != wifiState
                    && NetworkInfo.State.CONNECTED != netState) {
                // 手机没有任何的网络
                if(player != null) {
                    player.showNoNet();
                    player.setNetWork(false);
                }
            } else if ( wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
                // 无线网络连接成功
                if (player != null) {
                    player.onResume();
                    player.setNetWork(true);
                }
            }
        }
    }

    /**
     * 根据频道code获取直播地址
     */
    private void playUrl(){
        if(position<0){
            position = 0;
        }
        if(allChannels!=null && position<allChannels.size()) {
            fiberHomeChannelData = FiberHomeChannelDBManager.getFiberHomeChannelBykey(MainActivity.this, TableKey.CHANNELID, allChannels.get(position).getChannelcode());
            if(null!=fiberHomeChannelData) {
                if(!TextUtils.isEmpty(fiberHomeChannelData.getChannelURL())){
                    channel = allChannels.get(position);
                    playerUrl = dealWithUrl(fiberHomeChannelData.getChannelURL());
                    player.setCurrentChannel(channel);
                    player.play(playerUrl);
                    //先把节目的回看数据置空
                    player.setNextProgram(null, null);
                    //获取节目的数据
                    String url=getIP("/channelprevuforlive.jsp?channelcode="+ channel.getChannelcode());
                    loadUrl(url);
                    isFirstPlayer = false;
                }else{
                    if(isFirstPlayer){
                        finish();
                    }
                    Toast.makeText(MainActivity.this,"无可用直播",Toast.LENGTH_LONG).show();
                }
            }else{
                if(isFirstPlayer){
                    finish();
                }
                Toast.makeText(MainActivity.this,"无可用直播",Toast.LENGTH_LONG).show();
                DebugUtil.e("烽火频道数据中没有该频道");
            }
        }else{
//            if(isFirstPlayer){
//                finish();
//            }
//            Toast.makeText(MainActivity.this,"无可用直播",Toast.LENGTH_LONG).show();
            DebugUtil.e("获取烽火频道数据失败");
        }
    }

    /**
     *
     * @param url
     * @return
     */
    private String dealWithUrl(String url){
        String str = "";
        if(!TextUtils.isEmpty(url)){
            str =  "rtp" + url.substring(url.indexOf("://"));
        }
        return str;
    }

    /**
     * 获取广告数据
     */
    private void starADThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                //获取信息条广告
                OkHttpUtils.get().url(BaseUrl.ADVERTISE_URL + PublicConstant.USERID+stbID+ PublicConstant.LOCATIONID
                        +PublicConstant.AD_INFO+PublicConstant.AMOUNT + 1).build().readTimeOut(5000).execute(new ResultCallback(PublicConstant.AD_INFO));
                //获取播放完成广告
                OkHttpUtils.get().url(BaseUrl.ADVERTISE_URL + PublicConstant.USERID+stbID+ PublicConstant.LOCATIONID
                        +PublicConstant.AD_FINISH+PublicConstant.AMOUNT + 1).build().readTimeOut(5000).execute(new ResultCallback(PublicConstant.AD_FINISH));
                //获取退出广告
                OkHttpUtils.get().url(BaseUrl.ADVERTISE_URL + PublicConstant.USERID+stbID+ PublicConstant.LOCATIONID
                        +PublicConstant.AD_EXIT+PublicConstant.AMOUNT + 1).build().readTimeOut(5000).execute(new ResultCallback(PublicConstant.AD_EXIT));
                //音量条广告
                OkHttpUtils.get().url(BaseUrl.ADVERTISE_URL + PublicConstant.USERID+stbID+ PublicConstant.LOCATIONID
                        +PublicConstant.AD_EXIT+PublicConstant.AMOUNT + 1).build().readTimeOut(5000).execute(new ResultCallback(PublicConstant.AD_AUDIO));
                Looper.loop();
            }
        }).start();
    }

    /**
     * 数据结果监听
     */
    private class ResultCallback extends StringCallback{
        private String frame;//广告位置

        public ResultCallback(String frame) {
            this.frame = frame;
        }

        @Override
        public void onError(Call call, Exception e) {
            DebugUtil.i(frame+"位置广告获取失败");
        }

        @Override
        public void onResponse(String response) {
            DebugUtil.i("ad response--->"+response);
            ADServiceModel advertiseModel = new ParseJson().stringToObject(response,ADServiceModel.class);
            if(null==advertiseModel || advertiseModel.getResult()!=0){
                return;
            }
            if(null==advertiseModel.getAdinfos() || advertiseModel.getAdinfos().size()==0){
                return;
            }
            String adUrl = advertiseModel.getAdinfos().get(0).getUrl();
            if(TextUtils.isEmpty(adUrl)) {
                return;
            }
            if (TextUtils.equals(frame, PublicConstant.AD_INFO)) {
                //信息条广告
                player.setAdUrl(adUrl,"","","");
            } else if (TextUtils.equals(frame, PublicConstant.AD_FINISH)) {
                //播放完成广告
                player.setAdUrl("",adUrl,"","");
            } else if (TextUtils.equals(frame, PublicConstant.AD_EXIT)) {
                //退出广告
                player.setAdUrl("","",adUrl,"");
            } else if (TextUtils.equals(frame, PublicConstant.AD_AUDIO)) {
                //音量条广告
                player.setAdUrl("","","",adUrl);
            }
        }
    }

    /**
     * 菜单点击事件
     */
    private ChannelView.OnListViewItemClickListener onItemClickListener = new ChannelView.OnListViewItemClickListener() {
        @Override
        public void onLookBackItemClick(Channel currentChannel, List<List<LookBackModel>> lookBackModels, int position) {
            //回看
            isNextProgram = false;
            tempChannel = currentChannel;
            tempLookBacks = lookBackModels;
            tempLookBackPosition = position;
            if(lookBackModels!=null && lookBackModels.get(0)!=null && lookBackModels.get(0).size()>position){
                palyerLookBack(currentChannel, lookBackModels.get(0).get(position));
                player.hideChannelView(0);
            }else{
                DebugUtil.e("无播放地址");
            }

        }

        @Override
        public void onChannelItemClick(Channel model) {
            //频道
            if(model!=null && channel!=null) {
                if(!TextUtils.equals(channel.getMixno(),model.getMixno())) {
                    isLive = true;
                    channel = model;
                    player.setLive(isLive);
                    player.setPlayerType(LIVE);
                    player.setCurrentChannel(channel);
                    if (channel != null) {
                        findChannelPosition(channel.getMixno());
                    }
                }else{
                    player.showControlView(3000);
                }
            }
        }

        @Override
        public void onRightKeyDown(String channelCode) {
            //查看频道回看数据
            if (!TextUtils.isEmpty(channelCode)){
                String url=getIP("/channelprevu.jsp?channelcode="+ channelCode);
                loadUrl(url);
            }
        }
    };

//    private ChannelView.OnRightKeyDownListener mOnRightKeyDownListener = new ChannelView.OnRightKeyDownListener() {
//        @Override
//        public void onRightKeyDown(String channelCode) {
//            Toast.makeText(MainActivity.this,"1234",Toast.LENGTH_SHORT).show();
//            if (!TextUtils.isEmpty(channelCode)){
//                String url=getIP("/channelprevu.jsp?channelcode="+ channelCode);
//                loadUrl(url);
//            }
//        }
//    };

    /**
     * 播放回看时，鉴权拿播放地址
     */
    private void palyerLookBack(Channel channel, LookBackModel model){
        //内容类型
        contenttype = "4";
        //终端类型
        terminalflag = "STB";
        //栏目Code，回看对应的频道的栏目code
        columncode = channel.getColumncode();
        //清晰度标识
        definition = "0";
        //节目code
        programcode = model.getPrevuecode();

        String framecode = IptvAuthenticationUitls.getAutoInfo(MainActivity.this,"framecode");
        String path = "/auth.jsp" + "?contenttype=" + contenttype + "&terminalflag=" + terminalflag
                + "&columncode=" + columncode + "&definition=" + definition + "&programcode=" + programcode
                + "&channelcode="+ channel.getChannelcode() + "&modulecode=" + framecode;
        String url = getIP(path);
        loadUrl(url);
    }

    /**
     * 获得回看数据
     *
     * @param data
     */
    @Override
    public void loadChannelPrevuData(final String data, final String currentTime) {
        super.loadChannelPrevuData(data,currentTime);

        LookBackData lookBackData = new ParseJson().stringToObject(data, LookBackData.class);
        if (null == lookBackData || null == lookBackData.getData() || lookBackData.getData().size() == 0 || TextUtils.isEmpty(currentTime)) {
            return;
        }
        List<LookBackModel> lookBackModels =  lookBackData.getData();
        if (null!=lookBackModels && lookBackModels.size()>0){
            //设置选择菜单频道的回看数据
            player.setLookBackDatas(lookBackModels,currentTime);
        }
    }

    /**
     * 鉴权
     * @param data
     */
    @Override
    public void loadAuth(String data) {
        super.loadAuth(data);
//        AuthData authData = new ParseJson().stringToObject(data, AuthData.class);
        View view_dialog = getLayoutInflater().inflate(R.layout.view_dialog,null);
        Button btn_sure = (Button) view_dialog.findViewById(R.id.bt_sure);
        final AlertDialog builder = new AlertDialog.Builder(MainActivity.this).create();
        builder.show();
        builder.setContentView(view_dialog);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
    }

    /**
     * 获取播放地址
     * @param data 回看节目数据
     * @param prevuecode 回看节目的code
     */
    @Override
    public void loadTvodUrl(String data, String prevuecode) {
        super.loadTvodUrl(data, prevuecode);
        TvodData authData = new ParseJson().stringToObject(data, TvodData.class);
        if(TextUtils.equals(programcode,prevuecode) && null!=authData && !TextUtils.isEmpty(authData.getPlayurl())) {
            if(!isNextProgram) {
                //不是自动播放的下调节目，则重新设置属性
                isLive = false;
                lookBacks = tempLookBacks;
                lookBackPosition = tempLookBackPosition;
                channel = tempChannel;
                playerType = LOOK_BACK;
                player.setLive(isLive);
                player.setPlayerType(playerType);
                player.setCurrentChannel(channel);
                player.setLookBacks(tempLookBacks, tempLookBackPosition);
            }
            playerUrl = authData.getPlayurl();
            handler.sendEmptyMessage(2);
        }
    }

    /**
     * 直播中换台是，获取回看节目单
     * @param data
     * @param currentTime
     * @param channlCode
     */
    @Override
    public void loadPrevuDataForLive(String data, String currentTime, String channlCode) {
        super.loadPrevuDataForLive(data, currentTime, channlCode);
        LookBackData lookBackData = new ParseJson().stringToObject(data, LookBackData.class);
        if (null == lookBackData || null == lookBackData.getData() || lookBackData.getData().size() == 0) {
            return;
        }
        List<LookBackModel> lookBackModels =  lookBackData.getData();
        if(channel!=null && TextUtils.equals(channel.getChannelcode(), channlCode)) {
            if (null != lookBackModels && lookBackModels.size() > 0) {
                //设置选择菜单频道的回看数据
                player.setNextProgram(lookBackModels, currentTime);
            }
        }
    }
}
