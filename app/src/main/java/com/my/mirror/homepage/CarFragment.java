package com.my.mirror.homepage;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;

import com.my.mirror.R;
import com.my.mirror.base.BaseFragment;

/**
 * Created by dllo on 16/3/30.
 * 购物车的fragment
 */
public class CarFragment extends BaseFragment {
    private LinearLayout titleLine;
    private ClassifiedFragment classifiedFragment;


    @Override
    protected void initData() {

        //给上面那个标题栏 设置监听  点击替换fragment
        titleLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classifiedFragment = new ClassifiedFragment();
                Bundle args = new Bundle();
                args.putInt("five",5);
                classifiedFragment.setArguments(args);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main_frame, classifiedFragment).commit();

            }
        });
    }

    @Override
    protected void initView() {
        titleLine = findId(R.id.fragment_car_title_line);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_car;
    }
}
