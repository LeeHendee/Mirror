package com.my.mirror.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * Created by dllo on 16/3/29.
 */
public class BaseApplication extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Fresco.initialize(context);
        AutoLayoutConifg.getInstance().useDeviceSize();
    }

    public static Context getContext(){
        return context;
    }
}
