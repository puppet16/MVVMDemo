package com.luck.mvvmdemo.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.luck.mvvmdemo.bean.ImageInfo;
import com.luck.mvvmdemo.utils.LogUtils;
import com.luck.mvvmdemo.R;
import com.luck.mvvmdemo.utils.ScreenUtils;

/*************************************************************************************
 * Module Name:
 * Description:
 * Author: 李桐桐
 * Date:   2019/6/6
 *************************************************************************************/
public class ImageShowActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private RelativeLayout mRootView;
    private ImageView mIvShow;
    private ImageInfo mImageInfo;
    // 屏幕宽度
    public float WIDTH;
    // 屏幕高度
    public float HEIGHT;

    //原图高
    private float y_img_h;
    private float size, size_h, img_w, img_h;
    protected float to_x = 0;
    protected float to_y = 0;
    private float tx;
    private float ty;


    private final Spring spring = SpringSystem
            .create()
            .createSpring()
            .addListener(new ExampleSpringListener());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        LogUtils.d("--->ShowImageActivity onCreate");
        mRootView = findViewById(R.id.MainView);
        init();
        initListener();
    }

    private void init() {
        WIDTH = ScreenUtils.getScreenWidth();
        HEIGHT = ScreenUtils.getScreenHeight();

        mImageInfo = (ImageInfo) getIntent().getSerializableExtra("imageInfo");
        if (mImageInfo == null) {
            LogUtils.d("--->mImageInfo==null");
            finish();
        }

        mIvShow = new ImageView(this);
        mIvShow.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LogUtils.d(TAG,"传递数据："+ mImageInfo.toString());
        Glide.with(ImageShowActivity.this).load(mImageInfo.imageUrl).into(mIvShow);

        img_w = mImageInfo.widgetInfo.width;
        img_h = mImageInfo.widgetInfo.height - 300;
        size = WIDTH / img_w;
        y_img_h = mImageInfo.imageHeight * WIDTH / mImageInfo.imageWidth;
        size_h = y_img_h / img_h;

        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams((int) mImageInfo.widgetInfo.width,
                (int) mImageInfo.widgetInfo.height);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        p.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        mIvShow.setLayoutParams(p);
        p.setMargins((int) mImageInfo.widgetInfo.x,
                (int) mImageInfo.widgetInfo.y, (int) (WIDTH - (mImageInfo.widgetInfo.x + mImageInfo.widgetInfo.width)),
                (int) (HEIGHT - (mImageInfo.widgetInfo.y + mImageInfo.widgetInfo.height)));
        mRootView.addView(mIvShow);

        new Handler().post(new Runnable() {
            public void run() {
                ShowImageView();
            }
        });
    }

    private void initListener() {
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImageView();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d("--->ShowImageActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d("--->ShowImageActivity onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d("--->ShowImageActivity onDestroy");
    }

    private class ExampleSpringListener implements SpringListener {

        @Override
        public void onSpringUpdate(Spring spring) {
            double CurrentValue = spring.getCurrentValue();
            float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, size);
            float mapy = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, size_h);
            mIvShow.setScaleX(mappedValue);
            mIvShow.setScaleY(mapy);
            if (CurrentValue == 1) {
//    showImageView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onSpringAtRest(Spring spring) {

        }

        @Override
        public void onSpringActivate(Spring spring) {

        }

        @Override
        public void onSpringEndStateChange(Spring spring) {

        }
    }

    //实现效果
    private void MoveView() {

        ObjectAnimator.ofFloat(mRootView, "alpha", 0.8f).setDuration(0).start();
        mRootView.setVisibility(View.VISIBLE);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(mIvShow, "translationX", tx).setDuration(200),
                ObjectAnimator.ofFloat(mIvShow, "translationY", ty).setDuration(200),
                ObjectAnimator.ofFloat(mRootView, "alpha", 1).setDuration(200)

        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIvShow.setScaleType(ImageView.ScaleType.FIT_XY);
                spring.setEndValue(1);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();

    }

    //关闭页面
    private void MoveBackView() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(mIvShow, "translationX", to_x).setDuration(200),
                ObjectAnimator.ofFloat(mIvShow, "translationY", to_y).setDuration(200)
        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();
    }

    //具体动画处理类
    private void ShowImageView() {
        if (spring.getEndValue() == 0) {
            //弹动摩擦力
            spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(300, 5));
            //动画结束后出现的位置
            tx = 0;
            ty = HEIGHT / 2 - (mImageInfo.widgetInfo.y + img_h + 600);
            MoveView();
            return;
        }
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(1, 5));
        spring.setEndValue(0);
        new Handler().post(new Runnable() {
            public void run() {
                MoveBackView();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mIvShow.setVisibility(View.VISIBLE);
            ShowImageView();

        }
        return true;
    }

}