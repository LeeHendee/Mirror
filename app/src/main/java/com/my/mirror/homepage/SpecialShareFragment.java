package com.my.mirror.homepage;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;

import com.my.mirror.R;
import com.my.mirror.base.BaseFragment;

/**
 * Created by dllo on 16/4/5.
 */
public class SpecialShareFragment extends BaseFragment {
    private LinearLayout titleLine;
    private ClassifiedFragment classifiedFragment;
    @Override
    protected void initData() {
        titleLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classifiedFragment = new ClassifiedFragment(4);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main_frame, classifiedFragment).commit();

            }
        });

    }

    @Override
    protected void initView() {
        titleLine = findId(R.id.special_title_line);

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_special_share;
    }
}
