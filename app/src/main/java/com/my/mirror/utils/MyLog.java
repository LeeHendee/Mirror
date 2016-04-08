package com.my.mirror.utils;

import android.util.Log;

/**
 * Created by dllo on 16/4/6.
 */
public class MyLog {
    static boolean  flag = true;
    public static void mLog(String tag,String s){
        if (flag){
            Log.d(tag,s);
        }
    }
}
