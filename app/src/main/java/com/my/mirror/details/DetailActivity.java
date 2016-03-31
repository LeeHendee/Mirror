package com.my.mirror.details;



import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.net.okhttp.OkHttpClientManager;
import com.squareup.okhttp.FormEncodingBuilder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by dllo on 16/3/29.
 */
public class DetailActivity extends BaseActivity implements View.OnClickListener {
    private Button bactBtn,picturesBtn,buyBtn;
    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        bactBtn = findId(R.id.btn_back_detail);
        picturesBtn = findId(R.id.btn_pictures);
        buyBtn = findId(R.id.btn_buy);
        bactBtn.setOnClickListener(this);
        picturesBtn.setOnClickListener(this);
        buyBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back_detail:
                finish();
                break;
            case R.id.btn_pictures:
                Intent picturesActivity = new Intent();
                startActivity(picturesActivity);
                break;
            case R.id.btn_buy:
                Intent buyActivity = new Intent();
                startActivity(buyActivity);
                break;
        }
    }
}
