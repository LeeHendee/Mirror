package com.my.mirror.homepage;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.base.BaseFragment;
import com.my.mirror.gson.HomePageBean;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import de.greenrobot.event.EventBus;
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
    private String  stTitle;
    private int i;
    private HomePageBean bean;


    @Override
    protected void initData() {

        stTitle = getArguments().getString("stTitle");
        i = getArguments().getInt("i");

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classifiedFragment = new ClassifiedFragment(i);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main_frame, classifiedFragment).commit();

            }
        });

        OkHttpUtils.post().url(BEGIN_URL+GOODS_LIST).addParams(DEVICE_TYPE, DEVICE_REUSE).addParams(GOOD_TYPE,DEVICE_REUSE)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, HomePageBean.class);

                adapter = new ReuseRecyclerAdapter(bean);
                recyclerView.setAdapter(adapter);
                GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
                gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(gm);
            }
        });

        title.setText(getString(R.string.reuse_title_head) + stTitle);


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
