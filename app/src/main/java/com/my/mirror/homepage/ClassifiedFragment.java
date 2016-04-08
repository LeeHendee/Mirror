package com.my.mirror.homepage;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.my.mirror.MainActivity;
import com.my.mirror.R;
import com.my.mirror.base.BaseFragment;

/**
 * Created by dllo on 16/3/31.
 */
public class ClassifiedFragment extends BaseFragment implements View.OnClickListener {
    private int i;

    private LinearLayout allLine,mattLine,sunLine,projectLine,classifiedLine;
    private TextView allTv,mattTv,sunTv,projectTv;
    private ImageView allIv,mattIv,sunIv,projectIv;

    private LinearLayout specialLine,carLine, backLine, exitLine,classified;
    private TextView  specialTv,carTv;
    private ImageView  specialIv,carIv;
    private ListView listView;//需要解析出来的数据
    private ClassifiedAdapter adapter;


    public ClassifiedFragment(int i){
        this.i = i;
    }

    public ClassifiedFragment() {
    }

    @Override
    protected void initData() {

        if(i == 0){
            allTv.setAlpha(1);
            allIv.setVisibility(View.VISIBLE);
        }else if (i == 1){
            mattTv.setAlpha(1);
            mattIv.setVisibility(View.VISIBLE);
        }else if (i == 2){
            sunTv.setAlpha(1);
            sunIv.setVisibility(View.VISIBLE);
        }else if (i == 3){
            projectTv.setAlpha(1);
            projectIv.setVisibility(View.VISIBLE);
        }else if (i == 4){
            carTv.setAlpha(1);
            carIv.setVisibility(View.VISIBLE);
        }

        allLine.setOnClickListener(this);
        mattLine.setOnClickListener(this);
        sunLine.setOnClickListener(this);
        projectLine.setOnClickListener(this);
        carLine.setOnClickListener(this);
        backLine.setOnClickListener(this);
        exitLine.setOnClickListener(this);
    }

    @Override
    protected void initView() {



        carLine = findId(R.id.resure_car_line);
        carTv = findId(R.id.resure_car_tv);
        carIv = findId(R.id.resure_car_iv);

        backLine = findId(R.id.resure_back_line);
        exitLine = findId(R.id.resure_exit_line);
        classifiedLine = findId(R.id.classified_linearlayout);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_classified;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.resure_car_line:
                Intent intentCar = new Intent(getActivity(), MainActivity.class);
                intentCar.putExtra("position",4);
                startActivity(intentCar);
                break;
            case R.id.resure_back_line:
                Intent intentBack = new Intent(getActivity(), MainActivity.class);
                intentBack.putExtra("position",0);
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

    //设置动画
//    private void setAnimation(View tv) {
//        ObjectAnimator.ofFloat(tv, "translationX", 0F, 60F).setDuration(300).start();
//        ObjectAnimator.ofFloat(tv, "translationY", 0F, 40F).setDuration(300).start();
//    }
}
