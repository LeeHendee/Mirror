package com.my.mirror.gson;

import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by liangzaipan on 16/4/5.
 */
public class WelcomeBean {
    private String text,img;

    public WelcomeBean(String text, String img) {
        this.text = text;
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
