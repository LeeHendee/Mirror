package com.my.mirror.net;

import android.graphics.Bitmap;

/**
 * Created by dllo on 16/3/29.
 */
public interface IVolleyImage {
    void succeedListener(Bitmap bitmap);
    void failedListener(String s);
}
