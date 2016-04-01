package com.my.mirror.base;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;


/**
 * Created by dllo on 16/3/29.
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayout());
        initView();
        initData();
    }

    protected abstract int getLayout();

    protected abstract void initData();

    protected abstract void initView();

    protected  <T extends View>T findId(int id){
        return (T) findViewById(id);
    }

}
