package com.my.mirror;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.gson.ClassifiedBean;
import com.my.mirror.homepage.CarFragment;
import com.my.mirror.homepage.ClassifiedAdapter;
import com.my.mirror.homepage.MainViewPager;
import com.my.mirror.homepage.MainViewPagerAdapter;
import com.my.mirror.homepage.ReuseFragment;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
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


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

        fragmentList = new ArrayList<>();

        OkHttpUtils.post().url(BEGIN_URL+CATEGORY_LIST).addParams(DEVICE_TYPE, DEVICE)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, ClassifiedBean.class);

                for (int i = 0; i < bean.getData().size(); i++) {
                    fragmentList.add(new ReuseFragment(bean.getData().get(i).getCategory_name(),i));

                }
                fragmentList.add(new CarFragment());
                adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList, BaseApplication.getContext());
                viewPager.setAdapter(adapter);

            }
        });



        mirrorIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO mirror图标点击加动画
            }
        });

        
        Intent intent = getIntent();
        int i = intent.getIntExtra("position",0);
        viewPager.setCurrentItem(i);

    }

    @Override
    protected void initView() {
        mirrorIcon = findId(R.id.main_mirror_icon);


        viewPager = findId(R.id.viewpager);


    }
}
