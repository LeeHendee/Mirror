package com.my.mirror.net.okhttp;


import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import java.io.IOException;


/**
 * Created by dllo on 16/3/30.
 */
public class OkHttpHelper {

    // 请求网络数据

    // 参数分别是1,接口文档的属性"键"; 2,属性对应的值"值"; 3,拼接的网址的拼接字段; 4,调用接口的成功失败的方法
    public void postRequest(String parms, String value, String requestType, final INetListener netListener) {
        // 创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();

        // post请求通过FormEncodingBuilder,可以添加多个键值对，然后去构造RequestBody,最后完成Request的构造

        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add(parms,value);

        // 创建一个Request
        Request request =  new Request.Builder().url("http://api101.test.mirroreye.cn/"+requestType).post(builder.build()).build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                netListener.failed("拉取失败");
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                netListener.succeed(response);
            }
            
        });
    }
}
