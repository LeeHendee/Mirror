package com.my.mirror.activity;

import android.widget.ImageView;

import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by liangzaipan on 16/4/1.
 */
public class WelcomeActivity extends BaseActivity {
    private ImageView iv;
    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initData() {
        OkHttpUtils.post().url("");

    }

    @Override
    protected void initView() {
        iv = findId(R.id.welcome_iv);

    }
}
