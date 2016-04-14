package com.my.mirror.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.base.BaseFragment;
import com.my.mirror.bean.HomePageBean;
import com.my.mirror.greendao.ClassiFied;
import com.my.mirror.greendao.ClassiFiedDao;
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.greendao.ReUse;
import com.my.mirror.greendao.ReUseDao;
import com.my.mirror.adapter.ReuseNoNetAdapter;
import com.my.mirror.adapter.ReuseRecyclerAdapter;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by dllo on 16/3/30.
 */
public class ReuseFragment extends BaseFragment implements INetAddress {
    private LinearLayout line;//每个fragment的左上角的标题的线性布局
    private RecyclerView recyclerView;
    private ReuseRecyclerAdapter adapter;
    private ClassifiedFragment classifiedFragment;
    private TextView title;//标题
    private int i;
    private HomePageBean bean;
    private ClassiFiedDao classiFiedDao;
    private ClassiFied classiFied;
    private List<ReUse> beans;
    private ReuseNoNetAdapter noNetAdapter;

    @Override
    protected void initData() {

        //接收菜单栏的fragment替换时传过来的值
        i = getArguments().getInt("fragmentInt");
        beans = new ArrayList<>();


        classiFiedDao = DaoSingleton.getInstance().getClassiFiedDao();
        classiFied = classiFiedDao.queryBuilder().list().get(i);
        title.setText(getString(R.string.reuse_title_head) + classiFied.getTitle());
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classifiedFragment = new ClassifiedFragment();
                Bundle args = new Bundle();
                args.putInt("titleInt", i);
                classifiedFragment.setArguments(args);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().add(R.id.main_frame, classifiedFragment).addToBackStack(null).commit();

            }
        });

        OkHttpUtils.post().url(BEGIN_URL+GOODS_LIST)
                .addParams(DEVICE_TYPE, DEVICE_REUSE)
                .addParams(GOOD_TYPE,DEVICE_REUSE)
                .addParams(VERSION,VERSION_VALUE)
                .addParams(CATEGORY_ID,classiFiedDao.queryBuilder().list().get(i).getCategoryId())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                noNetAdapter = new ReuseNoNetAdapter(getActivity());
                recyclerView.setAdapter(noNetAdapter);
                GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
                gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(gm);
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, HomePageBean.class);
                for (int i = 0; i < bean.getData().getList().size(); i++) {
                    ReUseDao reUseDao = DaoSingleton.getInstance().getReUseDao();
                    ReUse reUse = new ReUse();
                    reUse.setTitle(bean.getData().getList().get(i).getGoods_name());
                    reUse.setImg(bean.getData().getList().get(i).getGoods_img());
                    reUse.setArea(bean.getData().getList().get(i).getProduct_area());
                    reUse.setName(bean.getData().getList().get(i).getGoods_name());
                    reUse.setBrand(bean.getData().getList().get(i).getBrand());
                    reUse.setPrice(bean.getData().getList().get(i).getGoods_price());
                    beans = reUseDao.queryBuilder().where(ReUseDao.Properties.Title.eq(reUse.getTitle())).list();
                    beans = reUseDao.queryBuilder().where(ReUseDao.Properties.Img.eq(reUse.getImg())).list();
                    beans = reUseDao.queryBuilder().where(ReUseDao.Properties.Area.eq(reUse.getArea())).list();
                    beans = reUseDao.queryBuilder().where(ReUseDao.Properties.Name.eq(reUse.getName())).list();
                    beans = reUseDao.queryBuilder().where(ReUseDao.Properties.Brand.eq(reUse.getBrand())).list();
                    beans = reUseDao.queryBuilder().where(ReUseDao.Properties.Price.eq(reUse.getPrice())).list();

                    if (beans.size() == 0) {
                        reUseDao.insert(reUse);
                    }

                    adapter = new ReuseRecyclerAdapter(bean,getActivity());
                    recyclerView.setAdapter(adapter);
                    GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
                    gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerView.setLayoutManager(gm);
                }

            }
        });

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



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
