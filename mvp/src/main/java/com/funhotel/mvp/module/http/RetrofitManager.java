package com.funhotel.mvp.module.http;

import android.util.ArrayMap;
import android.util.Log;

import com.funhotel.mvp.common.BaseModel;

import org.reactivestreams.Subscriber;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络管理器
 *
 * Created by zhiyahan on 2017/2/28.
 */
public class RetrofitManager {

    private Retrofit retrofit;
    private static RetrofitManager retrofitManager;

    /**
     * example
     * RetrofitManager retrofitManager=new RetrofitManager("http://222.68.210.55:33500");
     * @param baseUrl
     */
    public RetrofitManager(String baseUrl) {

        Interceptor interceptor = new LogInterceptor(new LogInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("LOG", message);
            }
        });

        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

 /*   public static RetrofitManager getInstance(String baseUrl) {
        if (retrofitManager==null){
            retrofitManager=new RetrofitManager(baseUrl);
        }
        return retrofitManager;
    }*/

    /**
     *  examplee
     *
         FunhotelService service=retrofitManager.getService(FunhotelService.class);
         Call<Login> call= service.getAds("1233","hd_hdframe_031","1");
         call.enqueue(new Callback<Login>(){
        @Override
        public void onResponse(Call<Login> call, Response<Login> response) {}

        @Override
        public void onFailure(Call<com.android.nretrofitmodule.Login> call, Throwable t) {}
        });
     *
     * @param cls  API 接口
     * @param <T> API接口类
     * @return  返回API接口示例
     */
    public <T> T getService(Class<T> cls){
        if (retrofit==null) return null;
        return   retrofit.create(cls);
    }


    /**
     * 请求Http时设置请求头，生成请求头后
     *
     * retrofit中设置请求头方式如下:
     *
     * retrofit = new Retrofit.Builder()
     * .client(OkHttpClient)
     *
     * @param map header map
     * @return  OkHttpClient
     */
    public OkHttpClient createClient(final ArrayMap<Object,Object> map) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //网络拦截器，可以在这里打印信息，或者额外的事情
                        return chain.proceed(addHeader(map,chain));
                    }
                });
        return httpClient.build();
    }

    /**
     * 添加请求头
     * @param map 请求头集合
     * @param chain @link{Interceptor.Chain}
     * @return Request
     */
    private Request addHeader(final ArrayMap<Object, Object> map, Interceptor.Chain chain) {
        Request.Builder request = chain.request().newBuilder();
        if (map == null || map.size() == 0) return null;
        if (null != map && !map.isEmpty()) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object value = entry.getValue();
                request.addHeader((String) entry.getKey(), (String) value);
            }
        }
        return request.build();
    }


    /**
     * 对网络接口返回的Response进行分割操作
     * @param result
     * @param <T>
     * @return
     */
    public <T> Observable<BaseModel<T>> flatResponse(final BaseModel<T> result) {

        return Observable.create(new ObservableOnSubscribe<BaseModel<T>>() {
            @Override
            public void subscribe(ObservableEmitter<BaseModel<T>> e) throws Exception {

                if (result.isSuccess()) {
                    if (!e.isDisposed()) {
                        e.onNext(result);
                    }

                }else {
                    if (!e.isDisposed()) {
                        e.onError(
                                new ApiException(result.getResult(),
                                        result.getMessage()));
                    }
                    return;
                }
                if (!e.isDisposed()) {
                    e.onComplete();
                }


            }
        });

       /* return Observable.create(new Observable.OnSubscribe<BaseModel<T>>() {

            @Override
            public void call(Subscriber<? super BaseModel<T>> subscriber) {
                if (result.isSuccess()) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext();
                    }
                } else {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(
                                new ApiException(result.getResult(),
                                        result.getMessage()));
                    }
                    return;
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });*/

    }

}