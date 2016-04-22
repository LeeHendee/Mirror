package com.my.mirror.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.bean.WelcomeBean;
import com.my.mirror.greendao.WelcomeIv;
import com.my.mirror.net.ImageLoaderHelper;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import java.util.List;
import okhttp3.Call;

/**
 * Created by liangzaipan on 16/4/1.
 */
public class WelcomeActivity extends BaseActivity implements INetAddress{
    //测试
    private SimpleDraweeView imageView;
    private WelcomeBean bean;
    private ImageLoaderHelper imageLoaderHelper;
    private List<WelcomeIv> list;

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initData() {
        imageLoaderHelper = ImageLoaderHelper.getImageLoaderHelper();
        OkHttpUtils.post().url(BEGIN_URL + STARTED_IMG).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

                timer.start();
            }
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response,WelcomeBean.class);
                imageView.setImageURI(Uri.parse(bean.getImg()));
                timer.start();
            }
        });
    }

    @Override
    protected void initView() {
        imageView = findId(R.id.welcome_iv);
    }

    //倒计时
    CountDownTimer timer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };


}
