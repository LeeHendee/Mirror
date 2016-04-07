package com.my.mirror.homepage;

import android.widget.TextView;

import com.my.mirror.R;
import com.my.mirror.base.BaseFragment;

/**
 * Created by dllo on 16/4/7.
 */
public class SpecialShareContentFragment extends BaseFragment {
    private TextView title,smallTitle,subTitle;
    private String getTitle,getSmallTitle,getSubtitle;


    @Override
    protected void initData() {
        getTitle = getArguments().getString("title");
        getSmallTitle = getArguments().getString("smallTitle");
        getSubtitle = getArguments().getString("subTitle");
        title.setText(getTitle);
        smallTitle.setText(getSmallTitle);
        subTitle.setText(getSubtitle);
    }

    @Override
    protected void initView() {
        title = findId(R.id.big_title);
        smallTitle = findId(R.id.small_title);
        subTitle = findId(R.id.sub_title);


    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_special_share_content;
    }
}
