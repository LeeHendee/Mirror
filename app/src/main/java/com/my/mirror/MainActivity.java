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

import com.google.gson.Gson;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.greendao.ClassiFied;
import com.my.mirror.greendao.ClassiFiedDao;
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.gson.ClassifiedBean;
import com.my.mirror.homepage.CarFragment;
import com.my.mirror.homepage.MainViewPager;
import com.my.mirror.homepage.MainViewPagerAdapter;
import com.my.mirror.homepage.ReuseFragment;
import com.my.mirror.homepage.SpecialShareFragment;
import com.my.mirror.login.LoginActivity;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
import com.my.mirror.utils.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MainActivity extends BaseActivity implements INetAddress {
    private ImageView mirrorIcon;//mirror图标
    private List<Fragment> fragmentList;
    private MainViewPager viewPager;
    private MainViewPagerAdapter adapter;
    private ClassifiedBean bean;
    private int position;
    private TextView login;
    private ClassiFiedDao classiFiedDao;
//    private ClassiFied classiFied;
    private List<ClassiFied> beans;


    @Override
    protected int getLayout() {
        try {
            return R.layout.activity_main;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void initData() {

        fragmentList = new ArrayList<>();
        beans = new ArrayList<>();
//        classiFiedDao = DaoSingleton.getInstance().getClassiFiedDao();
//        classiFied = new ClassiFied();

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);



        OkHttpUtils.post().url(BEGIN_URL + CATEGORY_LIST).addParams(DEVICE_TYPE, DEVICE)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                // MyToast.mToast("没出来啊……你还是检测网络连接吧");
                initAdapter();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, ClassifiedBean.class);

                for (int i = 0; i < bean.getData().size(); i++) {
                    ClassiFiedDao fiedDao = DaoSingleton.getInstance().getClassiFiedDao();
                    ClassiFied fied = new ClassiFied();
                    fied.setTitle(bean.getData().get(i).getCategory_name());
                    beans = fiedDao.queryBuilder().where(ClassiFiedDao.Properties.Title.eq(fied.getTitle())).list();
                    if (beans.size() == 0) {
                        fiedDao.insert(fied);
                    }
                }
                initAdapter();


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
                finish();
            }
        });


    }

    private void initAdapter() {

        classiFiedDao = DaoSingleton.getInstance().getClassiFiedDao();
        for (int i = 0; i < classiFiedDao.queryBuilder().list().size(); i++) {
            ReuseFragment fragment = new ReuseFragment();
            Bundle args = new Bundle();
            args.putInt("i", i);
            fragment.setArguments(args);
            fragmentList.add(fragment);
        }
        fragmentList.add(new SpecialShareFragment());
        fragmentList.add(new CarFragment());
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList, BaseApplication.getContext());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

    }


    @Override
    protected void initView() {
        mirrorIcon = findId(R.id.main_mirror_icon);

        viewPager = findId(R.id.viewpager);
        login = findId(R.id.main_login);


    }
}
