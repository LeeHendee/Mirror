package com.my.mirror;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.mirror.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private ImageView mirrorIcon;//mirror图标
    //               测试文字  全部，  平镜   太阳镜  专题分享  购物车  返回首页  退出
    private TextView textView,allTv,mattTv,sunTv,projectTv,carTv,backTv,exitTv;
    private LinearLayout line;//分类栏整体的布局，在这里用到是否显示的属性


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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.GONE);
                line.setVisibility(View.VISIBLE);
            }
        });
        allTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        mirrorIcon = findId(R.id.main_mirror_icon);
        //测试文字  需要删除
        textView = findId(R.id.main_ceshi);
        line = findId(R.id.main_classified_line);

        //setAlpha是设置字体的透明度
        allTv = findId(R.id.main_all_tv);
        allTv.setAlpha(0.25f);

        mattTv = findId(R.id.main_matt_tv);
        mattTv.setAlpha(0.25f);

        sunTv = findId(R.id.main_sun_tv);
        sunTv.setAlpha(0.25f);

        projectTv = findId(R.id.main_project_tv);
        projectTv.setAlpha(0.25f);

        carTv = findId(R.id.main_car_tv);
        carTv.setAlpha(0.25f);

        backTv = findId(R.id.main_back_tv);
        backTv.setAlpha(0.25f);

        exitTv = findId(R.id.main_exit_tv);
        exitTv.setAlpha(0.25f);
    }
}
