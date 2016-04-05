package com.my.mirror.homepage;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.my.mirror.MainActivity;
import com.my.mirror.R;
import com.my.mirror.base.BaseFragment;
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
    private int i;
    private LinearLayout specialLine,carLine, backLine, exitLine;
    private TextView  specialTv,carTv;
    private ImageView  specialIv,carIv;
    private ListView listView;//需要解析出来的数据
    private ClassifiedAdapter adapter;


    public ClassifiedFragment(int i) {
        this.i = i;
    }

    @Override
    protected void initData() {

        OkHttpUtils.post().url(BEGIN_URL + CATEGORY_LIST).addParams(DEVICE_TYPE, DEVICE)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                //请求失败
            }

            @Override
            public void onResponse(String response) {
                //请求成功 将请求的数据解析出来
                Gson gson = new Gson();
                ClassifiedBean bean = gson.fromJson(response, ClassifiedBean.class);
                adapter = new ClassifiedAdapter(i,bean);
                listView.setAdapter(adapter);

            }
        });

        if (i == 5) {
            //设置菜单栏里的透明图和下面的条条是否显示
            carTv.setAlpha(1);
            carIv.setVisibility(View.VISIBLE);
        } else if (i == 4){
            specialTv.setAlpha(1);
            specialIv.setVisibility(View.VISIBLE);
        }


        specialLine.setOnClickListener(this);
        carLine.setOnClickListener(this);
        backLine.setOnClickListener(this);
        exitLine.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentAll = new Intent(getActivity(), MainActivity.class);
                intentAll.putExtra("position", position);
                startActivity(intentAll);
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
        //setAnimation(classifiedLine);
    }

    // TODO 未成功的动画设置，有瑕疵  需要修改
    //设置动画
//    private void setAnimation(View tv) {
//        ObjectAnimator.ofFloat(tv, "translationX", 0F, 60F).setDuration(300).start();
//        ObjectAnimator.ofFloat(tv, "translationY", 0F, 40F).setDuration(300).start();
//    }
}
