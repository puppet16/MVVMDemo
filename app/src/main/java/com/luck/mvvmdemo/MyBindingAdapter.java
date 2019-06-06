package com.luck.mvvmdemo;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/*************************************************************************************
 * Module Name:
 * Description:
 * Author: 李桐桐
 * Date:   2019/6/4
 *************************************************************************************/

public class MyBindingAdapter {

    /**
     * 自己定义的url属性，在xml文件中使用
     * @param imageView
     * @param url
     */
    @BindingAdapter("url")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.mipmap.error)
                .placeholder(R.mipmap.loading)
                .into(imageView);
    }

}