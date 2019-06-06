package com.luck.mvvmdemo.bean;

/*************************************************************************************
 * Module Name:数据类最外层，根据errorMsg是否为空判断数据是否正常
 * Description:
 * Author: 李桐桐
 * Date:   2019/6/4
 *************************************************************************************/
public class NetData<T> {

    private T mData;

    private String mErrorMsg;

    public NetData(T data, String errorMsg) {
        mData = data;
        mErrorMsg = errorMsg;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        mErrorMsg = errorMsg;
    }

}
