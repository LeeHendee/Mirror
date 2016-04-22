package com.my.mirror.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.bean.LoginFailBean;
import com.my.mirror.bean.LoginSuccessBean;
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.greendao.LoginToken;
import com.my.mirror.greendao.LoginTokenDao;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
import com.my.mirror.utils.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import de.greenrobot.event.EventBus;
import okhttp3.Call;

/**
 * Created by liangzaipan on 16/3/29.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, INetAddress {
    private EditText phoneEt, passwordEt;
    private Button loginBtn, createBtn;
    private ImageView closeIv, xinLangIv, weiXinIv;
    private TextView forgetTv;
    private List<LoginToken> beans;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        loginBtn.setOnClickListener(this);
        createBtn.setOnClickListener(this);
        closeIv.setOnClickListener(this);
        forgetTv.setOnClickListener(this);
        xinLangIv.setOnClickListener(this);
        weiXinIv.setOnClickListener(this);
        beans = new ArrayList<>();

        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (passwordEt.length() != 0) {
                    loginBtn.setBackgroundResource(R.mipmap.makeacount);
                } else {
                    loginBtn.setBackgroundResource(R.mipmap.nouse_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    @Override
    protected void initView() {
        ShareSDK.initSDK(this);
        phoneEt = findId(R.id.login_phoneNum_edit);
        passwordEt = findId(R.id.login_password_edit);
        loginBtn = findId(R.id.login_btn);
        createBtn = findId(R.id.login_mackCount_btn);
        closeIv = findId(R.id.login_close);
        xinLangIv = findId(R.id.xinlang_icon);
        weiXinIv = findId(R.id.weixin_icon);
        forgetTv = findId(R.id.login_forget);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                OkHttpUtils.post().url(BEGIN_URL + LOGIN)
                        .addParams(PHONE_NUMBER, phoneEt.getText().toString())
                        .addParams(PASSWORD, passwordEt.getText().toString()).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(final String response) {
                        Log.i("fdfd", response);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.get("data").equals("") && !jsonObject.get("msg").equals("")) {
                                        final LoginFailBean loginFailBean = new LoginFailBean(response);
                                        loginFailBean.setMsg(jsonObject.getString("msg"));
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(LoginActivity.this, loginFailBean.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else if (!jsonObject.get("data").equals("")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                                    String token = jsonObject1.getString("token");
                                                    LoginTokenDao loginTokenDao = DaoSingleton.getInstance(BaseApplication.getContext()).getLoginTokenDao();
                                                    LoginToken loginToken = new LoginToken();
                                                    loginToken.setToken(token);
                                                    beans = DaoSingleton.getInstance(BaseApplication.getContext()).queryLoginTokenList(token);
                                                    if (beans.size() == 0){
                                                        loginTokenDao.insert(loginToken);
                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                MyToast.mToast(getString(R.string.login_success));
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.putExtra("result", 1);
                                                startActivity(intent);
                                                finish();
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
                break;
            case R.id.login_mackCount_btn:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_close:
                finish();
                break;
            case R.id.xinlang_icon:
                ShareSDK.initSDK(this);
                Platform platformXl = ShareSDK.getPlatform(SinaWeibo.NAME);
                if (platformXl.isAuthValid()) {
                    platformXl.removeAccount();
                }
                platformXl.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                platformXl.SSOSetting(false);
                platformXl.showUser(null);
                break;
            case R.id.weixin_icon:
                ShareSDK.initSDK(this);
                Platform platformWx = ShareSDK.getPlatform(Wechat.NAME);
                if (platformWx.isAuthValid()) {
                    platformWx.removeAccount();
                }
                platformWx.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                platformWx.SSOSetting(false);
                platformWx.showUser(null);
                break;
            case R.id.login_forget:
                Intent intent1 = new Intent(this, ForgetPasswordActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
