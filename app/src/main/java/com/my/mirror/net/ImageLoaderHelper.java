package com.my.mirror.net;


import android.graphics.Bitmap;
import android.widget.ImageView;

import com.my.mirror.R;
import com.my.mirror.base.BaseApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by dllo on 16/4/11.
 */
public class ImageLoaderHelper {
    public static ImageLoaderHelper imageLoaderHelper;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ImageLoaderConfiguration configuration;

    private File cacheDir = BaseApplication.getContext().getFilesDir();

    private ImageLoaderHelper(){
        init();
    }

    private void init() {
        //设置网络加载图片
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)//加载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)//网址为空的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)//加载失败的时候显示的图片
                .cacheInMemory(true)//是否内存缓存
                .cacheOnDisk(true)//是否内存缓存
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码格式
                .build();

        //设置缓存
        configuration = new ImageLoaderConfiguration.Builder(BaseApplication.getContext())
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheFileCount(100)//设置缓存文件的个数
        .diskCacheFileNameGenerator(new Md5FileNameGenerator())//设置文件名，MD5格式加密
        .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(configuration);

    }

    //暴露初始化的方法
    public static ImageLoaderHelper getImageLoaderHelper(){
        if (imageLoaderHelper == null){
            synchronized (ImageLoaderHelper.class){
                if (imageLoaderHelper == null){
                    imageLoaderHelper = new ImageLoaderHelper();
                }
            }
        }
        return imageLoaderHelper;
    }

    public void loadImage(String url,ImageView imageView){
        url = url.trim();
        imageLoader.displayImage(url,imageView,options);
    }


}
