package com.my.mirror.homepage;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.base.BaseFragment;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by dllo on 16/4/5.
 */
public class SpecialShareFragment extends BaseFragment implements INetAddress{
    private LinearLayout titleLine;
    private ClassifiedFragment classifiedFragment;
    private RecyclerView recyclerView;
    private SpecialShareAdapter adapter;
    private SpecialShareBean bean;
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

        OkHttpUtils.post().url(BEGIN_URL+STORY_LIST).addParams(DEVICE_TYPE, DEVICE_REUSE)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response,SpecialShareBean.class);

                adapter = new SpecialShareAdapter(bean,getActivity());
                recyclerView.setAdapter(adapter);
                GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
                gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(gm);
            }
        });



    }

    @Override
    protected void initView() {
        titleLine = findId(R.id.special_title_line);
        recyclerView = findId(R.id.special_recycler_view);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_special_share;
    }
}
