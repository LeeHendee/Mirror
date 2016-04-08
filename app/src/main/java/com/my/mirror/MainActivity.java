package com.my.mirror;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.my.mirror.base.BaseActivity;
import com.my.mirror.homepage.CarFragment;
import com.my.mirror.homepage.MainViewPager;
import com.my.mirror.homepage.MainViewPagerAdapter;
import com.my.mirror.homepage.ReuseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ImageView mirrorIcon;//mirror图标
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
        
        fragmentList.add(new ReuseFragment(getString(R.string.zc_all),0));
        fragmentList.add(new ReuseFragment(getString(R.string.zc_matt),1));
        fragmentList.add(new ReuseFragment(getString(R.string.zc_sun),2));
        fragmentList.add(new ReuseFragment(getString(R.string.zc_project),3));
        fragmentList.add(new CarFragment());
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragmentList,this);
        viewPager.setAdapter(adapter);

        
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
