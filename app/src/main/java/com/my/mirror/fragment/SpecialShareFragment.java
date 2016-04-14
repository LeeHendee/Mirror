package com.my.mirror.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.adapter.SpecialShareAdapter;
import com.my.mirror.base.BaseFragment;
import com.my.mirror.bean.SpecialShareBean;
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.greendao.SpecialShare;
import com.my.mirror.greendao.SpecialShareDao;
import com.my.mirror.adapter.SpecialShareNoNetAdapter;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

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
    private List<SpecialShare> beans;
    private SpecialShareNoNetAdapter noNetAdapter;
    @Override
    protected void initData() {
        beans = new ArrayList<>();
        titleLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classifiedFragment = new ClassifiedFragment();
                Bundle args = new Bundle();
                args.putInt("four", 4);
                classifiedFragment.setArguments(args);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main_frame, classifiedFragment).commit();
            }
        });

        OkHttpUtils.post().url(BEGIN_URL+STORY_LIST).addParams(DEVICE_TYPE, DEVICE_REUSE)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                noNetAdapter = new SpecialShareNoNetAdapter(getActivity());
                recyclerView.setAdapter(noNetAdapter);
                GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
                gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(gm);

            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, SpecialShareBean.class);
                for (int i = 0; i < bean.getData().getList().size(); i++) {
                    SpecialShareDao specialShareDao = DaoSingleton.getInstance().getSpecialShareDao();
                    SpecialShare specialShare = new SpecialShare();
                    specialShare.setImg(bean.getData().getList().get(i).getStory_img());
                    specialShare.setTitle(bean.getData().getList().get(i).getStory_title());
                    //(ReUseDao.Properties.Title.eq(reUse.getTitle())).list();
                    beans = specialShareDao.queryBuilder().where(SpecialShareDao.Properties.Title.eq(specialShare.getTitle())).list();
                    beans = specialShareDao.queryBuilder().where(SpecialShareDao.Properties.Img.eq(specialShare.getImg())).list();
                    if (beans.size() == 0) {
                        specialShareDao.insert(specialShare);
                    }
                }
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
