package com.my.mirror.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.bean.WelcomeBean;
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.greendao.WelcomeIv;
import com.my.mirror.greendao.WelcomeIvDao;
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
    private ImageView imageView;
    private WelcomeBean bean;
    private ImageLoaderHelper helper;
    private List<WelcomeIv> list;

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initData() {
        helper = ImageLoaderHelper.getImageLoaderHelper();
        OkHttpUtils.post().url(BEGIN_URL + STARTED_IMG).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                WelcomeIvDao welcomeIvNoNetDao = DaoSingleton.getInstance(BaseApplication.getContext()).getWelcomeIvDao();
                String uri = welcomeIvNoNetDao.queryBuilder().list().get(0).getUri();
                helper.loadImage(uri,imageView);
                timer.start();
            }
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response,WelcomeBean.class);
                WelcomeIvDao welcomeIvDao = DaoSingleton.getInstance(BaseApplication.getContext()).getWelcomeIvDao();
                WelcomeIv welcomeIv = new WelcomeIv();
                welcomeIv.setUri(bean.getImg());
                list = DaoSingleton.getInstance(BaseApplication.getContext()).queryWelcomeIvList(bean.getImg());
                if (list.size() == 0){
                    welcomeIvDao.insert(welcomeIv);
                }
                helper.loadImage(bean.getImg(), imageView);
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
