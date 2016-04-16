package com.my.mirror.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.adapter.MainViewPager;
import com.my.mirror.adapter.MainViewPagerAdapter;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.bean.SpecialShareContentBean;
import com.my.mirror.fragment.SpecialShareContentFragment;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;

/**
 * Created by dllo on 16/4/7.
 */
public class SpecialShareContentActivity extends BaseActivity implements INetAddress{
    private MainViewPager viewPager;
    private MainViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private ImageView back,loading;
    private SimpleDraweeView backgroundIv;
    private LinearLayout linearLayout;
    private SpecialShareContentBean bean;
    private Context context;

    @Override
    protected int getLayout() {
        return R.layout.activity_special_share_content;
    }

    @Override
    protected void initData() {


        fragmentList = new ArrayList<>();

        OkHttpUtils.post().url(BEGIN_URL+INFO)
                .addParams(DEVICE_TYPE,DEVICE_REUSE)
                .addParams(STORY_ID,DEVICE_REUSE)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, SpecialShareContentBean.class);
                for (int i = 0; i < bean.getData().getStory_data().getImg_array().size(); i++) {
                    SpecialShareContentFragment fragment = new SpecialShareContentFragment();
                    Bundle args = new Bundle();
                    //通过setArguments方法，将解析出来的数据放到每一个fragment中
                    args.putString("title",bean.getData().getStory_data().getText_array().get(i).getTitle());
                    args.putString("smallTitle",bean.getData().getStory_data().getText_array().get(i).getSmallTitle());
                    args.putString("subTitle",bean.getData().getStory_data().getText_array().get(i).getSubTitle());
                    fragment.setArguments(args);
                    fragmentList.add(fragment);
                    backgroundIv.setImageURI(Uri.parse(bean.getData().getStory_data().getImg_array().get(0)));

                }


                //监听viewPager上下滑动  切换页面  然后更换背景
                viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        backgroundIv.setImageURI(Uri.parse(bean.getData().getStory_data().getImg_array().get(position)));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList, context);
                viewPager.setAdapter(adapter);
            }
        });

        for (int i = 0; i < fragmentList.size(); i++) {
            linearLayout.setTag(fragmentList.get(i));
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout.setVisibility(View.GONE);
                }
            });
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //背景加载动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.loading);
        loading.startAnimation(animation);
    }

    @Override
    protected void initView() {
        viewPager = findId(R.id.viewpager);
        backgroundIv = findId(R.id.background_iv);
        back = findId(R.id.share_content_back);
        loading = findId(R.id.share_content_loading);
        View view = LayoutInflater.from(this).inflate(R.layout.fragment_special_share_content,null);
        linearLayout = (LinearLayout) view.findViewById(R.id.viewpager_linear_layout);


    }
}
