package com.luck.mvvmdemo;

import android.app.Application;
import android.content.Context;

/*************************************************************************************
 * Module Name:
 * Description:
 * Author: 李桐桐
 * Date:   2019/6/6
 *************************************************************************************/
public class MyApplication extends Application {
    public static MyApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
