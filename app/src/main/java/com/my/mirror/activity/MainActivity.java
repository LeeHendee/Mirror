package com.my.mirror.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.adapter.MainViewPager;
import com.my.mirror.adapter.MainViewPagerAdapter;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.bean.ClassifiedBean;
import com.my.mirror.fragment.ReuseFragment;
import com.my.mirror.fragment.ShopingCarFragment;
import com.my.mirror.fragment.SpecialShareFragment;
import com.my.mirror.greendao.ClassiFied;
import com.my.mirror.greendao.ClassiFiedDao;
import com.my.mirror.greendao.DaoSingleton;
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
    private int result;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

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


        sp = getSharedPreferences("login", MODE_PRIVATE);
        editor = sp.edit();


        Log.i("loginInt", sp.getInt("loginInt", 0) + "----" + result);
        if (sp.getInt("loginInt", 0) == 1) {
            login.setText("购物车");
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(4);
                }
            });
        } else {
            login.setText("登陆");
            //登陆 点击跳转
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
            });
        }

        //菜单栏跳转回来加的动画效果
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_classified);
        mainLine.startAnimation(animation);

        fragmentList = new ArrayList<>();
        beans = new ArrayList<>();
        //接收菜单栏，点击时传过来的position 好让ViewPager知道跳到哪页
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);


        //请求
        OkHttpUtils.post().url(BEGIN_URL + CATEGORY_LIST).addParams(DEVICE_TYPE, DEVICE)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                initAdapter();
            }

            @Override
            public void onResponse(String response) {
                //请求之后解析，将解析的数据放到实体类中
                Gson gson = new Gson();
                bean = gson.fromJson(response, ClassifiedBean.class);

                for (int i = 0; i < bean.getData().size(); i++) {
                    //通过for循环 将解析出来的数据存到数据库里
                    ClassiFiedDao fiedDao = DaoSingleton.getInstance(BaseApplication.getContext()).getClassiFiedDao();
                    ClassiFied fied = new ClassiFied();
                    fied.setTitle(bean.getData().get(i).getCategory_name());
                    fied.setCategoryId(bean.getData().get(i).getCategory_id());
                    beans = DaoSingleton.getInstance(BaseApplication.getContext()).queryClassiFiedList(bean.getData().get(i).getCategory_name());
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
                fragmentList.add(new ShopingCarFragment());
                adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList, BaseApplication.getContext());
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(position);//根据跳转接收的position  让viewPager显示那一页

            }
        });

        //首页的mirror图标点击动画
        mirrorIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_icon_mirror);
                mirrorIcon.startAnimation(animation);
            }
        });

        viewPager.setCurrentItem(2);
        viewPager.setCurrentItem(0);

    }

    private void initAdapter() {
        //这个是没有网络的时候 使用的方法
        classiFiedDao = DaoSingleton.getInstance(BaseApplication.getContext()).getClassiFiedDao();
        for (int i = 0; i < classiFiedDao.queryBuilder().list().size(); i++) {
            ReuseFragment fragment = new ReuseFragment();
            Bundle args = new Bundle();
            args.putInt("fragmentInt", i);
            fragment.setArguments(args);
            fragmentList.add(fragment);
        }
        fragmentList.add(new SpecialShareFragment());
        fragmentList.add(new ShopingCarFragment());
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

    @Override
    protected void onResume() {
        super.onResume();

        Intent resultIntent = getIntent();
        result = resultIntent.getIntExtra("result", 0);

        if (result == 1) {
            if (sp.getBoolean("what", true)) {
                editor.putBoolean("what", false);
                editor.putInt("loginInt", result);
                editor.commit();
            }
            login.setText("购物车");
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(4);
                }
            });
        }
    }
}
