package com.funhotel.tvllibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.funhotel.tvllibrary.R;
import com.funhotel.tvllibrary.utils.DebugUtil;

import java.util.Timer;

/**
 * @Title: BoutiqueSouvenirFragment
 * @Description: H5
 * @author: LinWeiDong
 * @data: 2016/1/29 11:54
 */
public class BoutiqueFragment extends Fragment implements AdvancedWebView.Listener{


    private Context mContext;
    private static RelativeLayout load, error;
    private String titleName;
    private View view;
    private int titleId;
    private View mCustomView;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private String url;

    private boolean isLoadHotelGoods = false;//酒店商品需要从后台获取数据

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    load.setVisibility(View.GONE);
                    error.setVisibility(View.VISIBLE);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private Timer timer;
    private static final String ARG_PARAM1 = "url";
    private static final String ARG_PARAM2 = "name";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private AdvancedWebView mWebView;


    public BoutiqueFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragement_web, null);
        Bundle data = getArguments();
        url = data.getString("url");
        mWebView = (AdvancedWebView) view.findViewById(R.id.webview);
        load = (RelativeLayout) view.findViewById(R.id.load);
        error = (RelativeLayout) view.findViewById(R.id.error);
        load.setVisibility(View.GONE);

        return view;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        titleName = this.getArguments().getString("name");
        // 获取ID
        titleId = this.getArguments().getInt("sort");

        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                load.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);
                mWebView.loadUrl(url);

            }
        });
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setListener(getActivity(), this);
//      mWebView.setWebChromeClient(new MyWebChromeClient());

        mWebView.loadUrl(url);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //在Android系统4.3.1~3.0版本,系统webview默认添加了searchBoxJavaBridge_接口,如果未移除该接口可能导致低版本Android系统远程命令执行漏洞，
            // 当Android的SDK版本>=11 <17时，调用removeJavascriptInterface方法移除“searchBoxJavaBridge_”接口
            try {
                mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
            } catch (Throwable tr) {
                tr.printStackTrace();
            }
        }

        //按键监听事件
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  //表示按返回键 时的操作
                    mWebView.goBack();
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_BACK && !mWebView.canGoBack()) {
//                    getActivity().moveTaskToBack(false);
                    return false;
                }else {
                    return false;
                }
            }
        });

        super.onActivityCreated(savedInstanceState);
    }


    /**
     * @ClassName：MyWebChromeClient
     * @Description： WebChromeClient 类 处理加载过程中的变化
     * @Author：Linker
     * @Date：2014-10-11 下午12:24:47
     */
    private class MyWebChromeClient extends WebChromeClient {

        // 进度发生改变时的处理
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO Auto-generated method stub
            DebugUtil.i("onProgressChanged  >>>>>>>>" + newProgress);
            if (newProgress == 100) {
                load.setVisibility(View.GONE);
                error.setVisibility(View.GONE);
                DebugUtil.i("加载完成");
            }
//            else {
//                if (load.getVisibility() == View.GONE) {
//                    load.setVisibility(View.VISIBLE);
//                    error.setVisibility(View.GONE);
//                }
//            }
            super.onProgressChanged(view, newProgress);
        }
    }



    @Override
    public void onDownloadRequested(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        load.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
    }


    @Override
    public void onPageFinished(String url) {
        mWebView.getSettings().setBlockNetworkImage(false);
        load.setVisibility(View.GONE);
        error.setVisibility(View.GONE);

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        mWebView.stopLoading();
        mWebView.clearView();
        load.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
    }

    @Override
    public void onExternalPageRequest(String url) {

        if (url.startsWith("http:") || url.startsWith("https:")) {
            mWebView.loadUrl(url);
        }else {
            try {
                // Otherwise allow the OS to handle things like tel, mailto, etc.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();
//        if (mWebView.getProgress()!=100){
//            load.setVisibility(View.VISIBLE);
//            error.setVisibility(View.GONE);
//        }else {
//            load.setVisibility(View.GONE);
//            error.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onPause() {
        mWebView.onPause();
        mWebView.pauseTimers();
        load.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        // ...
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

}