package com.funhotel.tvllibrary.update;

import android.content.Context;
import android.text.TextUtils;

import com.funhotel.tvllibrary.utils.DebugUtil;
import com.funhotel.tvllibrary.utils.ParseJson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by zhiyahan on 2016/7/14.
 * @author zhiyahan
 */
public class UpdateManager {

    private Thread thread;
    private Context context;
    private static final int RESULT=0;//访问结果的标识
    private static final int ISUPDATE=1;//是否需要更新的标识
//    private static final int GET_UPDATE=1002;
//    private int getType=1001;

    public UpdateManager(Context context) {
        this.context=context;
    }

    public void update(final String url){
        if(TextUtils.isEmpty(url)){
            return;
        }
        if (null!=thread){
            if (thread.isAlive()){
                thread.interrupt();
                thread=null;
            }
        }
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.get().url(url).build().readTimeOut(5000).execute(stringCallback);
            }
        });
        thread.start();
    }

    private StringCallback stringCallback=new StringCallback() {
        @Override
        public void onError(Call call, Exception e) {
            //错误
            DebugUtil.e("升级地址错误");
        }

        @Override
        public void onResponse(String response) {
            DebugUtil.e("更新结果 :"+response);
            UpdateUrlBean updateUrlBean=new ParseJson().stringToObject(response,UpdateUrlBean.class);
            if (null==updateUrlBean||updateUrlBean.getResult()!=RESULT){
                return;
            }
            int updateCode = updateUrlBean.getUpdate();
            if (updateCode==ISUPDATE){
                //需要更新

                //获取当前版本号
                AppInfo info=new AppInfo(context);
                int code=info.getCurrentVersionCode();
                //获取服务器版本号
                int versionCode = updateUrlBean.getVersionCode();
                DebugUtil.e("code>>>>"+code +"   versionCode>>>>"+versionCode);
                if (code==versionCode){
                    //版本号相同
                    return;
                }else {
                    //获取更新地址
                    String versionUrl = updateUrlBean.getDownLoadUrl();
                    if (TextUtils.isEmpty(versionUrl)){
                        return;
                    }
                    //开始去下载更新
                    AppUpdate appUpdate=new AppUpdate(context,updateUrlBean);
                    appUpdate.updateApp(true);
                    DebugUtil.e("开始下载更新");
                }
            }else {
                //不需要更新
                return;
            }










           /* if (getType==GET_URL){
                UpdateUrlBean updateUrlBean=new ParseJson().stringToObject(response,UpdateUrlBean.class);
                if (null==updateUrlBean||updateUrlBean.getResultCode()!=RespConstants.SUCC){
                    //失败
                    return;
                }
                UpdateUrlBean.DataBean dataBean=updateUrlBean.getData();
                if (null==dataBean||TextUtils.isEmpty(dataBean.getUrl())){
                    //失败
                    return;
                }
                getType=GET_UPDATE;
                UserIdUtils userIdUtils=new UserIdUtils(context);
                String stbId=userIdUtils.getUserId();
                AppInfo info=new AppInfo(context);
                int code=info.getCurrentVersionCode();
                update(dataBean.getUrl()+"?stbId="+stbId+"&versionCheck="+ code);
            }else if (getType==GET_UPDATE){
                BaseData baseData=new ParseJson().stringToObject(response,BaseData.class);
                if (null==baseData){
                    return;
                }
                UpdateBean updateBean=baseData.getData();
                if (null==updateBean){
                    return;
                }
                if (TextUtils.isEmpty(updateBean.getDownLoadUrl())){
                    return;
                }
                Log.e("获得的bean",updateBean.getDownLoadUrl());
                AppUpdate appUpdate=new AppUpdate(context,updateBean);
                appUpdate.updateApp(false);
                getType=GET_URL;
            }*/


        }
    };
}