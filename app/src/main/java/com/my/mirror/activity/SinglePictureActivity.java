package com.my.mirror.activity;

import android.content.Intent;
import android.widget.ImageView;

import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/4/6.
 */
public class SinglePictureActivity extends BaseActivity{
    private ImageView showPicture;
    @Override
    protected int getLayout() {
        return R.layout.activity_single_picture;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("bitmap_url");
        Picasso.with(this).load(url).into(showPicture);
    }

    @Override
    protected void initView() {
        showPicture = findId(R.id.iv_single_picture);
    }
}
