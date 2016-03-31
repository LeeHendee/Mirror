package com.my.mirror.net.okhttp;

/**
 * Created by dllo on 16/3/30.
 */
public interface INetListener {
    void succeed (com.squareup.okhttp.Response response);
    void failed(String s);
}
