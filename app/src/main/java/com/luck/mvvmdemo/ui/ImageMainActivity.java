package com.luck.mvvmdemo.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.luck.mvvmdemo.bean.ImageBean;
import com.luck.mvvmdemo.bean.ImageInfo;
import com.luck.mvvmdemo.ImageViewModel;
import com.luck.mvvmdemo.bean.NetData;
import com.luck.mvvmdemo.R;
import com.luck.mvvmdemo.databinding.ActivityMainImageBinding;
import com.luck.mvvmdemo.utils.LogUtils;

public class ImageMainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private ActivityMainImageBinding mBinding;
    private ImageViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_image);
        mViewModel = new ViewModelProvider(
                this, new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(ImageViewModel.class);

        //监听数据变化
        mViewModel.getImage().observe(this, new Observer<NetData<ImageBean.ImagesBean>>() {
            @Override
            public void onChanged(@Nullable NetData<ImageBean.ImagesBean> imagesBeanNetData) {
                if (imagesBeanNetData.getErrorMsg() != null) {
                    Toast.makeText(ImageMainActivity.this, imagesBeanNetData.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    return;
                }
                LogUtils.d(TAG,"数据切换了");
                mBinding.setImageBean(imagesBeanNetData.getData());
                setTitle(imagesBeanNetData.getData().getCopyright());
            }
        });

        mBinding.setPresenter(new Presenter());
        mViewModel.loadImage();
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_load:
                    mViewModel.loadImage();
                    break;
                case R.id.btn_previous:
                    mViewModel.previousImage();
                    break;
                case R.id.btn_next:
                    mViewModel.nextImage();
                    break;
                case R.id.img:
                    enterDetail(mViewModel.getImageInfo(view));
                default:
                    break;
            }
        }

        private void enterDetail(ImageInfo info) {
            Intent intent = new Intent(ImageMainActivity.this, ImageShowActivity.class);
            intent.putExtra("imageInfo", info);
            startActivity(intent);
        }
    }
}