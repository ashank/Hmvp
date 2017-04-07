package com.funhotel.mvp.module.http;

import com.funhotel.mvp.common.BaseModel;
import com.funhotel.mvp.entity.AdEntity;
import com.funhotel.mvp.entity.Admodel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhiyahan on 2017/3/24.
 *
 * 接口调用
 */

public interface FunhotelAPI {


    @GET("adservices/getads")
    Call<BaseModel<List<Admodel>>> getAds(@Query("userId") String user,
                                          @Query("locationId") String locationId,
                                          @Query("amount") String amount);


    @GET("adservices/getads")
    Observable<BaseModel<List<Admodel>>> getAds2(@Query("userId") String user,
                                                 @Query("locationId") String locationId,
                                                 @Query("amount") String amount);



    @GET("adservices/getads")
    Observable<AdEntity> getAds3(@Query("userId") String user,
                                 @Query("locationId") String locationId,
                                 @Query("amount") String amount);


}
