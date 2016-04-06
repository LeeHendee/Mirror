package com.my.mirror.activity;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.net.okhttp.StringCallback;
import com.my.mirror.utils.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by liangzaipan on 16/3/29.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private TextView sendTv;
    private EditText phoneEt, numEv, passwordEv;
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
                if (phoneEt.getText() != null) {
                    OkHttpUtils.post().url("http://api101.test.mirroreye.cn/index.php/user/send_code")
                            .addParams("phone number", phoneEt.getText().toString())
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
                } else {
                    MyToast.mToast("请输入手机号");
                }
                break;
            case R.id.register_makeCount_btn:
                if (phoneEt.length() != 0) {
                    MyToast.mToast("请输入手机号");
                } else if (numEv.length() != 0) {
                    MyToast.mToast("请输入正确的验证码");
                } else if (passwordEv.length() != 0) {
                    MyToast.mToast("请输入密码");
                } else {
                    OkHttpUtils.post().url("http://api101.test.mirroreye.cn/index.php/user/reg")
                            .addParams("phone_number", phoneEt.getText().toString()).addParams("number", numEv.getText().toString())
                            .addParams("password", passwordEv.getText().toString()).build().execute(new StringCallback() {

                        @Override
                        public void onError(Call call, Exception e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            Log.i("HHHHH", response);
                        }
                    });
                }
                break;
        }
    }

    //倒计时
    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            sendTv.setText((millisUntilFinished / 1000) + "秒后重新发送");
        }

        @Override
        public void onFinish() {
            sendTv.setEnabled(true);
            sendTv.setText("发送验证码");
        }
    };
}
