package com.my.mirror.utils;

import android.widget.Toast;

import com.my.mirror.base.BaseApplication;

/**
 * Created by dllo on 16/3/29.
 */
public class MyToast {
    public static void mToast(String s){
        Toast.makeText(BaseApplication.getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
