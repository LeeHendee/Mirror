package com.my.mirror.details;

import android.widget.ListView;

import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;

/**
 * Created by dllo on 16/3/29.
 */
public class PicturesActivity extends BaseActivity {
    private ListView mListView;
    private PicturesListViewAdapter mAdapter;
    @Override
    protected int getLayout() {
        return R.layout.activity_pictures;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mListView = findId(R.id.lv_pictures);
    }
}
