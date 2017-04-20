package com.funhotel.ijkplayer.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.funhotel.tvllibrary.application.Channel;
import com.funhotel.tvllibrary.db.AuthenticationDBManager;
import com.funhotel.tvllibrary.utils.DebugUtil;
import com.funhotel.tvllibrary.utils.IptvAuthenticationUitls;
import com.funhotel.tvllibrary.utils.UserIdUtils;
import com.funhotel.tvllibrary.view.AdvancedWebView;
import com.funhotel.tvllibrary.view.LoadingAnimationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LiveTvBaseActivity extends AppCompatActivity implements AdvancedWebView.Listener{
    public AdvancedWebView mWebView;
    public RelativeLayout rl_load;
    public RelativeLayout rl_content;
    public LoadingAnimationView loadingAnimationView;
    public String url;
    public List<Channel> mTotelDatas = new ArrayList<>();//频道总数据
    public Handler mHandler = new Handler();
    private static  String AUTHENTICATION="Authentication";
    private ExecutorService singleThreadExecutor;
    public String action="";

    /**
     * 鉴权的设
     */
    //内容类型
    public String contenttype="4" ;
    //终端类型
    public String terminalflag="STB";
    //栏目Code，回看对应的频道的栏目code
    public String columncode="0O09";
    //清晰度标识
    public String definition="0";
    //节目code
    public String programcode="00000000070000001674";

    public LiveTvBaseActivity() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (null == singleThreadExecutor) {
            singleThreadExecutor = Executors.newCachedThreadPool();
        }
        super.onCreate(savedInstanceState);
    }


    public String getIP(String page){
        String epgdmain=IptvAuthenticationUitls.getAutoInfo(this,"EPGDomain");
        String framecode = IptvAuthenticationUitls.getAutoInfo(this,"framecode");
        if(TextUtils.isEmpty(page) || TextUtils.isEmpty(epgdmain) || TextUtils.isEmpty(framecode)){
            return "";
        }
        String ip=epgdmain.substring(0,epgdmain.indexOf("function")) + framecode + page;
        return ip;
    }


    public void iniWebView(){
        if (null==mWebView){
            return;
        }
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setListener(LiveTvBaseActivity.this, this);
        mWebView.addJavascriptInterface(new JavaScriptInterface(),AUTHENTICATION);
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.getSettings().setBlockNetworkImage(false);
    }

    public void loadUrl(String url){
        if (null==mWebView){
            return;
        }
        this.url = url;
        mWebView.addJavascriptInterface(new JavaScriptInterface(),AUTHENTICATION);
        mWebView.loadUrl(url);
    }

    /**
     * @ClassName：MyWebChromeClient
     * @Description： WebChromeClient 类 处理加载过程中的变化
     * @Author：Linker
     * @Date：2014-10-11 下午12:24:47
     */
    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO Auto-generated method stub
            DebugUtil.i("onProgressChanged  >>>>>>>>" + newProgress);
            if (newProgress == 100) {
                DebugUtil.i("加载完成");
            }

            super.onProgressChanged(view, newProgress);
        }
    }


    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {
        if (null==mWebView){
            return;
        }
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        if (null==mWebView){
            return;
        }
        mWebView.stopLoading();
        mWebView.clearView();
        //错误
    }

    @Override
    public void onDownloadRequested(String url, String userAgent,
                                    String contentDisposition,
                                    String mimetype,
                                    long contentLength) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onExternalPageRequest(String url) {
        if (url.startsWith("http:") || url.startsWith("https:")) {
            if (null==mWebView){
                return;
            }
            mWebView.loadUrl(url);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null==mWebView){
            return;
        }
        mWebView.onResume();
        mWebView.resumeTimers();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null==mWebView){
            return;
        }
        mWebView.onPause();
        mWebView.pauseTimers();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null!=mWebView){
            mWebView.onDestroy();
        }
        if(null!=singleThreadExecutor){
            singleThreadExecutor.shutdown();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (null==mWebView){
            return;
        }
        mWebView.onActivityResult(requestCode, resultCode, intent);
    }


    final class JavaScriptInterface {

        public JavaScriptInterface() {
        }

        @JavascriptInterface
        public void getChannelPrevuData(final String data,String currentTime){
            loadChannelPrevuData(data,currentTime);
        }

        @JavascriptInterface
        public void getChannelPrevuDataForLive(String data, String currentTime, String channlCode){
            loadPrevuDataForLive(data,currentTime,channlCode);
        }


        @JavascriptInterface
        public  void CTCSetConfig(final String key, final String value){
            singleThreadExecutor.execute(new AddAutoInfoThread(key, value));
        }

        @JavascriptInterface
        public String CTCGetConfig(String key){

            String value = IptvAuthenticationUitls.getAutoInfo(LiveTvBaseActivity.this,key);
            if(TextUtils.isEmpty(value)){
                value = AuthenticationDBManager.getValueByName(LiveTvBaseActivity.this, key);
            }
            return value;

        }

        @JavascriptInterface
        public void auth(final String result){
            loadAuth(result);
        }


        @JavascriptInterface
        public  void order( String result){

        }


        @JavascriptInterface
        public  void getTvodUrl(final String result, String prevuecode){
            loadTvodUrl(result, prevuecode);
        }
    }

    /**
     * 保存认证信息线程
     */
    private class AddAutoInfoThread implements Runnable {

        private String key;
        private String value;

        public AddAutoInfoThread(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            Looper.prepare();
            AuthenticationDBManager.addAuthenticaiton(LiveTvBaseActivity.this,key,value,
                    UserIdUtils.newInstance(LiveTvBaseActivity.this).getStbId());
            Looper.loop();
        }
    }

    /**
     *  加载回看列表数据
     * @param data
     */
    public void loadChannelPrevuData(String data,String currentTime){

    }

    public void loadAuth(String data){

    }

    public void loadTvodUrl(String data , String prevuecode){

    }

    public void loadPrevuDataForLive(String data,String currentTime, String channlCode){

    }
}