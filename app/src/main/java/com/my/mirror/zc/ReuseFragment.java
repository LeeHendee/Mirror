package com.my.mirror.zc;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.my.mirror.R;
import com.my.mirror.base.BaseFragment;

/**
 * Created by dllo on 16/3/30.
 */
public class ReuseFragment extends BaseFragment {
    private LinearLayout line;
    private LinearLayout mainLine;
    private RecyclerView recyclerView;
    private ReuseRecyclerAdapter adapter;
    @Override
    protected void initData() {

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainLine.setVisibility(View.VISIBLE);
            }
        });
        mainLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainLine.setVisibility(View.GONE);
            }
        });

        adapter = new ReuseRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
        gm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gm);
    }

    @Override
    protected void initView() {
        line = findId(R.id.resure_title_line);
        mainLine = findId(R.id.resure_classified_line);
        recyclerView = findId(R.id.resure_recycler_view);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_reuse;
    }
}
