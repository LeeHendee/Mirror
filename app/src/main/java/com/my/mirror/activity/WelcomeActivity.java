package com.my.mirror.activity;

import android.widget.ImageView;

import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.net.okhttp.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

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
        OkHttpUtils.post().url("http://api101.test.mirroreye.cn/index.php/index/started_img").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                //iv.setImageBitmap(response);

            }
        });

    }

    @Override
    protected void initView() {
        iv = findId(R.id.welcome_iv);

    }
}
