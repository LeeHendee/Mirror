package com.my.mirror.gson;

import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by liangzaipan on 16/4/5.
 */
public class WelcomeBean {
    private TextView tv;
    private SimpleDraweeView simpleDraweeView;

    public WelcomeBean(TextView tv, SimpleDraweeView simpleDraweeView) {
        this.tv = tv;
        this.simpleDraweeView = simpleDraweeView;
    }

    public TextView getTv() {
        return tv;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }

    public SimpleDraweeView getSimpleDraweeView() {
        return simpleDraweeView;
    }

    public void setSimpleDraweeView(SimpleDraweeView simpleDraweeView) {
        this.simpleDraweeView = simpleDraweeView;
    }
}
