package com.my.mirror.zc;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.mirror.R;
import com.my.mirror.base.BaseFragment;

import de.greenrobot.event.EventBus;

/**
 * Created by dllo on 16/3/30.
 */
public class ReuseFragment extends BaseFragment {
    private LinearLayout line;
    private RecyclerView recyclerView;
    private ReuseRecyclerAdapter adapter;
    private ClassifiedFragment classifiedFragment;
    private TextView title;
    private String stTitle;
    private int i;



    public ReuseFragment (String stTitle,int i){
        this.stTitle = stTitle;
        this.i = i;
    }

    @Override
    protected void initData() {
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classifiedFragment = new ClassifiedFragment(i);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main_frame, classifiedFragment).commit();

            }
        });
        title.setText(stTitle);

        adapter = new ReuseRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
        gm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gm);
    }


    @Override
    protected void initView() {
        line = findId(R.id.resure_title_line);
        recyclerView = findId(R.id.resure_recycler_view);
        title = findId(R.id.reuse_title);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_reuse;
    }
}
