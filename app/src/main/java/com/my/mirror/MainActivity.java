package com.my.mirror;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.mirror.base.BaseActivity;
import com.my.mirror.zc.CarFragment;
import com.my.mirror.zc.MainViewPager;
import com.my.mirror.zc.MainViewPagerAdapter;
import com.my.mirror.zc.ReuseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ImageView mirrorIcon;//mirror图标

    private LinearLayout line;//分类栏整体的布局，在这里用到是否显示的属性
    private List<Fragment> fragmentList;
    private MainViewPager viewPager;
    private MainViewPagerAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mirrorIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO mirror图标点击加动画
            }
        });


        fragmentList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fragmentList.add(new ReuseFragment());
        }

        fragmentList.add(new CarFragment());
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragmentList,this);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void initView() {
        mirrorIcon = findId(R.id.main_mirror_icon);


        viewPager = findId(R.id.viewpager);



    }
}
