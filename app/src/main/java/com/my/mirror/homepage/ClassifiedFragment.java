package com.my.mirror.homepage;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.my.mirror.MainActivity;
import com.my.mirror.R;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.base.BaseFragment;
import com.my.mirror.greendao.ClassiFied;
import com.my.mirror.greendao.ClassiFiedDao;
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.gson.ClassifiedBean;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by dllo on 16/3/31.
 *菜单栏的fragment
 */
public class ClassifiedFragment extends BaseFragment implements View.OnClickListener, INetAddress {
    private int i,five,four;
    private LinearLayout specialLine,carLine, backLine, exitLine,classified;
    private TextView  specialTv,carTv;
    private ImageView  specialIv,carIv;
    private ListView listView;
    private ClassifiedAdapter adapter;
    private ReuseFragment reuseFragment;





    @Override
    protected void initData() {

        i = getArguments().getInt("titleInt");
        five = getArguments().getInt("five");
        four = getArguments().getInt("four");


        if (five == 5) {
            //设置菜单栏里的透明图和下面的条条是否显示
            carTv.setAlpha(1);
            carIv.setVisibility(View.VISIBLE);
        } else if (four == 4){
            specialTv.setAlpha(1);
            specialIv.setVisibility(View.VISIBLE);
        }

        if (five == 5) {
            i = five;
        } else if (four == 4) {
            i = four;
        }

        adapter = new ClassifiedAdapter(i);
        listView.setAdapter(adapter);

        specialLine.setOnClickListener(this);
        carLine.setOnClickListener(this);
        backLine.setOnClickListener(this);
        exitLine.setOnClickListener(this);
        reuseFragment = new ReuseFragment();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putInt("i",position);
                reuseFragment.setArguments(args);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main_frame, reuseFragment).commit();
            }
        });
    }

    @Override
    protected void initView() {

        carLine = findId(R.id.resure_car_line);
        carTv = findId(R.id.resure_car_tv);
        carIv = findId(R.id.resure_car_iv);

       specialLine = findId(R.id.resure_special_line);
        specialTv = findId(R.id.resure_special_tv);
        specialIv = findId(R.id.resure_special_iv);
        classified = findId(R.id.classified);


        backLine = findId(R.id.resure_back_line);
        exitLine = findId(R.id.resure_exit_line);
        listView = findId(R.id.classified_lv);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_classified;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resure_special_line:
                Intent intentSpecial = new Intent(getActivity(), MainActivity.class);
                intentSpecial.putExtra("position", 3);
                startActivity(intentSpecial);
                break;

            case R.id.resure_car_line:
                Intent intentCar = new Intent(getActivity(), MainActivity.class);
                intentCar.putExtra("position", 4);
                startActivity(intentCar);
                break;

            case R.id.resure_back_line:
                Intent intentBack = new Intent(getActivity(), MainActivity.class);
                intentBack.putExtra("position", 0);
                startActivity(intentBack);
                break;

            case R.id.resure_exit_line:
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Animation animation = AnimationUtils.loadAnimation(BaseApplication.getContext(), R.anim.anim_classified);
        classified.setAnimation(animation);
    }


}
