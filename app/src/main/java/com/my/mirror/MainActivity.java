package com.my.mirror;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.gson.ClassifiedBean;
import com.my.mirror.homepage.CarFragment;
import com.my.mirror.homepage.ClassifiedAdapter;
import com.my.mirror.homepage.MainViewPager;
import com.my.mirror.homepage.MainViewPagerAdapter;
import com.my.mirror.homepage.ReuseFragment;
import com.my.mirror.homepage.SpecialShareFragment;
import com.my.mirror.lzp.LoginActivity;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
import com.my.mirror.utils.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import okhttp3.Call;

public class MainActivity extends BaseActivity implements INetAddress{
    private ImageView mirrorIcon;//mirror图标
    private List<Fragment> fragmentList;
    private MainViewPager viewPager;
    private MainViewPagerAdapter adapter;
    private ClassifiedBean bean;
    private int position;
    private TextView login;


    @Override
    protected int getLayout() {
        try {
            return R.layout.activity_main;
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void initData() {

        fragmentList = new ArrayList<>();

        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);


        OkHttpUtils.post().url(BEGIN_URL+CATEGORY_LIST).addParams(DEVICE_TYPE, DEVICE)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                MyToast.mToast("没出来啊……你还是检测网络连接吧");
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, ClassifiedBean.class);

                for (int i = 0; i < bean.getData().size(); i++) {
                    ReuseFragment fragment = new ReuseFragment();
                    Bundle args = new Bundle();
                    args.putString("stTitle",bean.getData().get(i).getCategory_name());
                    args.putInt("i",i);
                    fragment.setArguments(args);
                    fragmentList.add(fragment);
                }
                fragmentList.add(new SpecialShareFragment());
                fragmentList.add(new CarFragment());
                adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList, BaseApplication.getContext());
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(position);
            }
        });







        mirrorIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_icon_mirror);
                mirrorIcon.startAnimation(animation);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        


    }

    @Override
    protected void initView() {
        mirrorIcon = findId(R.id.main_mirror_icon);

        viewPager = findId(R.id.viewpager);
        login = findId(R.id.main_login);

    }
}
