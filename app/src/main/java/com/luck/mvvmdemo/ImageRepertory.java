package com.luck.mvvmdemo;


import com.luck.mvvmdemo.bean.ImageBean;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*************************************************************************************
 * Module Name:
 * Description:
 * Author: 李桐桐
 * Date:   2019/6/4
 *************************************************************************************/
public class ImageRepertory {

    private Retrofit mRetrofit;

    public ImageRepertory() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://cn.bing.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private interface Service {

        @GET("HPImageArchive.aspx")
        Observable<ImageBean> getImage(
                @Query("format") String format,
                @Query("idx") int idx,
                @Query("n") int n
        );

    }

    public Observable<ImageBean> getImage(String format, int idx, int n) {
        return mRetrofit.create(Service.class).getImage(format, idx, n);
    }

}
