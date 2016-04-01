package com.my.mirror.activity;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private EditText phoneEt,numEv,passwordEv;
    private Button btn;

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        sendTv.setOnClickListener(this);
        btn.setOnClickListener(this);

    }

    @Override
    protected void initView() {
        sendTv = findId(R.id.register_send_tv);
        phoneEt = findId(R.id.register_phoneNum_edit);
        numEv = findId(R.id.register_num_edit);
        passwordEv = findId(R.id.register_password_edit);
        btn = findId(R.id.register_makeCount_btn);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_send_tv:
                OkHttpUtils.post().url("http://api101.test.mirroreye.cn/index.php/user/send_code")
                        .addParams("phone number", "13934964737")
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws Exception {
                        timer.start();
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
            case R.id.register_makeCount_btn:
                OkHttpUtils.post().url("http://api101.test.mirroreye.cn/index.php/user/reg")
                        .addParams("phone_number", "13934964737").addParams("number",numEv.getText().toString())
                        .addParams("password", "123456").build().execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        finish();
                    }
                });
                break;
        }
    }
    //倒计时
    CountDownTimer timer = new CountDownTimer(60000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            sendTv.setText((millisUntilFinished/1000)+"秒后重新发送");
        }

        @Override
        public void onFinish() {
            sendTv.setEnabled(true);
            sendTv.setText("发送验证码");
        }
    };
}
