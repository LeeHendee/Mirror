package com.my.mirror.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import java.util.HashMap;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by liangzaipan on 16/3/29.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText phoneEt,passwordEt;
    private Button loginBtn,createBtn;
    private ImageView closeIv,xinlangIv,weixinIv;
    private TextView forgetTv;
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
        xinlangIv.setOnClickListener(this);
        weixinIv.setOnClickListener(this);

        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (passwordEt.length() != 0){
                    loginBtn.setBackgroundResource(R.mipmap.makeacount);
                }else{
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
        xinlangIv = findId(R.id.xinlang_icon);
        weixinIv = findId(R.id.weixin_icon);
        forgetTv = findId(R.id.login_forget);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                break;
            case R.id.login_mackCount_btn:
                Intent intent = new Intent(this,RegisterActivity.class);
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
                Intent intent1 = new Intent(this,ForgetPasswordActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
