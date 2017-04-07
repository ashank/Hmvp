package com.funhotel.mvp.module.http;

import android.util.Log;

import com.funhotel.mvp.common.BaseModel;
import com.funhotel.mvp.entity.Admodel;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhiyahan on 2017/3/28.
 * Http交互
 */

public class HttpManager {

    private final static String TAG = "MainActivity";
    private FunhotelAPI mFunhotelAPI;
    private static HttpManager mHttpManager;
    private RetrofitManager manager;
    private String url;

    public HttpManager(String url) {
        manager = new RetrofitManager(url);
        mFunhotelAPI = manager.getService(FunhotelAPI.class);
        this.url=url;
    }

  /*  public static HttpManager getInstance(String url) {
        if (mHttpManager == null) {
            mHttpManager = new HttpManager(url);
        }
        return mHttpManager;
    }*/

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取广告
     * @param user       UserID
     * @param locationId 位置ID
     * @param amount     返回的数量
     * @return
     */
    public Observable<BaseModel<List<Admodel>>> getAds2(String user, String locationId, String amount) {
        Observable<BaseModel<List<Admodel>>> ob=mFunhotelAPI.getAds2(user, locationId, amount)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<BaseModel<List<Admodel>>, BaseModel<List<Admodel>>>() {
                    @Override
                    public BaseModel<List<Admodel>> apply(BaseModel<List<Admodel>> listBaseModel) throws Exception {
                        return listBaseModel;
                    }
                });
        return ob;
    }


}
