package com.luck.mvvmdemo.bean;

/*************************************************************************************
 * Module Name:
 * Description:
 * Author: 李桐桐
 * Date:   2019/6/6
 *************************************************************************************/

import java.io.Serializable;

public class ImageInfo implements Serializable {
    public String imageUrl;
    public float imageWidth;
    public float imageHeight;
    public WidgetInfo widgetInfo;

    @Override
    public String toString() {
        return "ImageInfo{" +
                "imageUrl='" + imageUrl + '\'' +
                ", imageWidth=" + imageWidth +
                ", imageHeight=" + imageHeight +
                ", widgetInfo=" + widgetInfo +
                '}';
    }

    public class WidgetInfo implements Serializable {
        public float x;
        public float y;
        public float width;
        public float height;

        @Override
        public String toString() {
            return "WidgetInfo{" +
                    "x=" + x +
                    ", y=" + y +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }
    }
}
