package com.my.mirror.net.okhttp;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.util.HashMap;

/**
 * Created by dllo on 16/3/30.
 */
public class NetLoader {

    //回调接口
    Callback callback;

    public RequestBody formBody;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void getDataByPost(String url, HashMap<String, String> head) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        for (String key : head.keySet()) {
            formBody = formEncodingBuilder.add(key, head.get(key)).build();
        }
        final Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        //安卓自带回调方法
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);

    }
}
