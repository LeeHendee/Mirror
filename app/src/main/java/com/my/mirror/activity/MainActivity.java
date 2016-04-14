package com.my.mirror.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.greendao.ClassiFied;
import com.my.mirror.greendao.ClassiFiedDao;
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.fragment.CarFragment;

import com.my.mirror.bean.ClassifiedBean;

import com.my.mirror.adapter.MainViewPager;
import com.my.mirror.adapter.MainViewPagerAdapter;
import com.my.mirror.fragment.ReuseFragment;
import com.my.mirror.fragment.SpecialShareFragment;

import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
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
    private List<ClassiFied> beans;
    private LinearLayout mainLine;

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

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_classified);
        mainLine.startAnimation(animation);

        fragmentList = new ArrayList<>();
        beans = new ArrayList<>();

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);



        OkHttpUtils.post().url(BEGIN_URL + CATEGORY_LIST).addParams(DEVICE_TYPE, DEVICE)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
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
                    fied.setCategoryId(bean.getData().get(i).getCategory_id());
                    beans = fiedDao.queryBuilder().where(ClassiFiedDao.Properties.Title.eq(fied.getTitle())).list();
                    beans = fiedDao.queryBuilder().where(ClassiFiedDao.Properties.CategoryId.eq(fied.getCategoryId())).list();
                    if (beans.size() == 0) {
                        fiedDao.insert(fied);
                    }

                    ReuseFragment fragment = new ReuseFragment();
                    Bundle args = new Bundle();
                    args.putInt("fragmentInt", i);
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
                finish();
            }
        });

        viewPager.setCurrentItem(2);
        viewPager.setCurrentItem(0);

    }

    private void initAdapter() {

        classiFiedDao = DaoSingleton.getInstance().getClassiFiedDao();
        for (int i = 0; i < classiFiedDao.queryBuilder().list().size(); i++) {
            ReuseFragment fragment = new ReuseFragment();
            Bundle args = new Bundle();
            args.putInt("fragmentInt", i);
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
        mainLine = findId(R.id.main_line);

    }

}
