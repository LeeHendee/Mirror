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

import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
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

                if (phoneEt.getText().toString() != null) {
                    OkHttpUtils.post().url(BEGIN_URL + SEND_CODE)
                            .addParams(PHONENUMBER,phoneEt.getText().toString())
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                        }

                        @Override
                        public void onResponse(String response) {
                            timer.start();
                        }
                    });
                } else {
                    MyToast.mToast(getString(R.string.please_enet_phone_number));
                }
                break;
            case R.id.register_makeCount_btn:
                if (phoneEt.length() == 0) {
                    MyToast.mToast(getString(R.string.please_enet_phone_number));
                } else if (numEv.length() == 0) {
                    MyToast.mToast(getString(R.string.please_enter_correct_verify_code));
                } else if (passwordEv.length() == 0) {
                    MyToast.mToast(getString(R.string.please_enter_password));
                } else {
                    OkHttpUtils.post().url(BEGIN_URL + REG)
                            .addParams(PHONE_NUMBER, phoneEt.getText().toString())
                            .addParams(NUMBER, numEv.getText().toString())
                            .addParams(PASSWORD, passwordEv.getText().toString()).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {

                        }

                        @Override
                        public void onResponse(final String response) {
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
                                                    MyToast.mToast(getString(R.string.login_success));
                                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
            sendTv.setText((millisUntilFinished / 1000) + getString(R.string.resend_after));
        }

        @Override
        public void onFinish() {
            sendTv.setEnabled(true);
            sendTv.setText(R.string.send_verification_code);
        }
    };
}
