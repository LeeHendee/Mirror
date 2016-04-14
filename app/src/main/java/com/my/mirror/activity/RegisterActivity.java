package com.my.mirror.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.mirror.MainActivity;
import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.bean.LoginFailBean;
import com.my.mirror.bean.RegisterFailBean;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
import com.my.mirror.utils.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by liangzaipan on 16/3/29.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener, INetAddress {
    private TextView sendTv;
    private EditText phoneEt, numEv, passwordEv;
    private Button btn;
    private ImageView closeIv;

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        sendTv.setOnClickListener(this);
        btn.setOnClickListener(this);
        closeIv.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        sendTv = findId(R.id.register_send_tv);
        phoneEt = findId(R.id.register_phoneNum_edit);
        numEv = findId(R.id.register_num_edit);
        passwordEv = findId(R.id.register_password_edit);
        btn = findId(R.id.register_makeCount_btn);
        closeIv = findId(R.id.register_close);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_send_tv:
                if (phoneEt.getText() != null) {
                    OkHttpUtils.post().url(BEGIN_URL + SEND_CODE)
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
                if (phoneEt.length() == 0) {
                    MyToast.mToast("请输入手机号");
                } else if (numEv.length() == 0) {
                    MyToast.mToast("请输入正确的验证码");
                } else if (passwordEv.length() == 0) {
                    MyToast.mToast("请输入密码");
                } else {
                    OkHttpUtils.post().url(BEGIN_URL + REG)
                            .addParams("phone_number", phoneEt.getText().toString()).addParams("number", numEv.getText().toString())
                            .addParams("password", passwordEv.getText().toString()).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {

                        }

                        @Override
                        public void onResponse(final String response) {
                            Log.i("HHHHH", response);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.get("data").equals("") && !jsonObject.get("msg").equals("")) {
                                            final RegisterFailBean registerFailBean = new RegisterFailBean(response);
                                            registerFailBean.setMsg(jsonObject.getString("msg"));
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(RegisterActivity.this, registerFailBean.getMsg(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else if (!jsonObject.get("data").equals("")) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    MyToast.mToast("登陆成功");
                                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                    intent.putExtra("result", 1);
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                }
                break;
            case R.id.register_close:
                finish();
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
