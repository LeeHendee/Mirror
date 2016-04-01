package com.my.mirror.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.net.okhttp.OkHttpHelper;
import com.my.mirror.net.okhttp.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by liangzaipan on 16/3/29.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private TextView sendTv;
    private EditText phoneEt;
    private int time = 60;

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        sendTv.setOnClickListener(this);

    }

    @Override
    protected void initView() {
        sendTv = findId(R.id.register_send_tv);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_send_tv:
                OkHttpUtils.post().url("http://api101.test.mirroreye.cn/index.php/user/send_code")
                        .addParams("phone number", "18624284279")
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws Exception {
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {

                    }

                });
                break;
        }
    }
}
