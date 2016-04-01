package com.my.mirror.activity;

import android.content.Intent;
import android.graphics.Color;
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

        if (phoneEt.getText()  != null && passwordEt.getText() != null){
            // TODO 这里判断无效  需要更改
            loginBtn.setBackgroundColor(Color.RED);
        }
    }

    @Override
    protected void initView() {
        ShareSDK.initSDK(this);
        phoneEt = (EditText) findViewById(R.id.login_phoneNum_edit);
        passwordEt = (EditText) findViewById(R.id.login_password_edit);
        loginBtn = (Button) findViewById(R.id.login_btn);
        createBtn = (Button) findViewById(R.id.login_mackCount_btn);
        closeIv = (ImageView) findViewById(R.id.login_close);
        xinlangIv = (ImageView) findViewById(R.id.xinlang_icon);
        weixinIv = (ImageView) findViewById(R.id.weixin_icon);
        forgetTv = (TextView) findViewById(R.id.login_forget);
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
