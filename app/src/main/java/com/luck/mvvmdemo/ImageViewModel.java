package com.luck.mvvmdemo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.luck.mvvmdemo.bean.ImageBean;
import com.luck.mvvmdemo.bean.ImageInfo;
import com.luck.mvvmdemo.bean.NetData;
import com.luck.mvvmdemo.utils.ScreenUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*************************************************************************************
 * Module Name:提供数据的，更新数据
 * Description:
 * Author: 李桐桐
 * Date:   2019/6/4
 *************************************************************************************/
public class ImageViewModel extends ViewModel {

    private MutableLiveData<NetData<ImageBean.ImagesBean>> mImage;
    private ImageRepertory mRepertory;
    private int idx;

    public ImageViewModel() {
        mImage = new MutableLiveData<>();
        mRepertory = new ImageRepertory();
        idx = 0;
    }

    public MutableLiveData<NetData<ImageBean.ImagesBean>> getImage() {
        return mImage;
    }

    public void loadImage() {
        mRepertory.getImage("js", idx, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ImageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ImageBean imageBean) {
                        mImage.setValue(new NetData<ImageBean.ImagesBean>(
                                imageBean.getImages().get(0), null
                        ));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mImage.setValue(new NetData<ImageBean.ImagesBean>(
                                null, e.getMessage()
                        ));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void nextImage() {
        mRepertory.getImage("js", ++idx, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ImageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ImageBean imageBean) {
                        mImage.setValue(new NetData<ImageBean.ImagesBean>(
                                imageBean.getImages().get(0), null
                        ));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mImage.setValue(new NetData<ImageBean.ImagesBean>(
                                null, e.getMessage()
                        ));
                        idx--;
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void previousImage() {
        if (idx <= 0) {
            mImage.setValue(new NetData<ImageBean.ImagesBean>(
                    null, "已经是第一个了"
            ));
            return;
        }
        mRepertory.getImage("js", --idx, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ImageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ImageBean imageBean) {
                        mImage.setValue(new NetData<ImageBean.ImagesBean>(
                                imageBean.getImages().get(0), null
                        ));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mImage.setValue(new NetData<ImageBean.ImagesBean>(
                                null, e.getMessage()
                        ));
                        idx++;
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public ImageInfo getImageInfo(View v) {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.imageUrl =mImage.getValue() != null ? ImageBean.ImagesBean.BASE_URL + mImage.getValue().getData().getUrl(): String.valueOf(R.mipmap.error);
        imageInfo.imageWidth = 1280;
        imageInfo.imageHeight = 720;
        imageInfo.widgetInfo = new ImageInfo().new WidgetInfo();
        imageInfo.widgetInfo.x = v.getLeft();
        imageInfo.widgetInfo.y = v.getTop();
        imageInfo.widgetInfo.width = ScreenUtils.getScreenWidth();
        imageInfo.widgetInfo.height = v.getLayoutParams().height;
        return imageInfo;
    }
}
