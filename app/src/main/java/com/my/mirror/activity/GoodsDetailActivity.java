package com.my.mirror.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.adapter.DownListViewAdapter;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.bean.AllGoodsListBean;
import com.my.mirror.adapter.LinkageListView;
import com.my.mirror.adapter.UpListViewAdapter;
import com.my.mirror.bean.HomePageBean;
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.greendao.LoginTokenDao;
import com.my.mirror.net.okhttp.INetAddress;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import de.greenrobot.event.EventBus;


public class GoodsDetailActivity extends BaseActivity implements INetAddress, View.OnClickListener {
    private LinkageListView listView;
    private AllGoodsListBean allGoodsListData;
    private Handler handler;
    private int position;
    private SimpleDraweeView background;
    private Button backBtn, picturesBtn, buyBtn;
    private List<AllGoodsListBean.DataEntity.ListEntity.WearVideoEntity> picturesData;
    private HomePageBean bean;
    private ImageView loading;
    private LinearLayout line;
    private boolean flagRight = false, flagLeft = false, flag = false;
    private LoginTokenDao loginTokenDao;
    private String token;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected int getLayout() {
        return R.layout.activity_goodsdetail;
    }

    @Override
    protected void initData() {


        //从轻量级数据库中获得登录成功返回的VELUE 然后判断是否可以购买
        sp = getSharedPreferences("login", MODE_PRIVATE);
        editor = sp.edit();
        if (sp.getInt("loginInt", 0) == 1) {
            loginTokenDao = DaoSingleton.getInstance(BaseApplication.getContext()).getLoginTokenDao();
            token = loginTokenDao.queryBuilder().list().get(0).getToken();
        } else  {
            token = null;
        }

        post();
        addData();
        allGoodsListData = new AllGoodsListBean();
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 1);
        switch (position) {
            case 0:
                position = 1;
                break;
            case 1:
                position = 2;
                break;
            case 2:
                position = 0;
                break;
        }
        bean = (HomePageBean) intent.getSerializableExtra("homePageBean");
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        listView = (LinkageListView) findViewById(R.id.detail_listview);
        background = (SimpleDraweeView) findViewById(R.id.goodsdetail_background);
        backBtn = findId(R.id.btn_back_detail);
        picturesBtn = findId(R.id.btn_pictures);
        buyBtn = findId(R.id.btn_buy);
        loading = findId(R.id.goodsdetail_loading);
        line = findId(R.id.goodsdetail_line);
        //弹出的button监听;
        backBtn.setOnClickListener(this);
        picturesBtn.setOnClickListener(this);
        buyBtn.setOnClickListener(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.loading);
        loading.startAnimation(animation);

    }

    public void onEventMainThread(Integer x) {
        if (x == 1 && flagRight == true) {
            //回去
            setToLeftAima();
            line.setVisibility(View.INVISIBLE);
            flagRight = true;
        } else if (x == 2 && flagLeft == false&&flag==false) {
            //出来
            setToRightAima();
            line.setVisibility(View.VISIBLE);
            flagLeft = true;
        } else if (x == 3 && flagLeft == false&&flag==false){
            //出来
            setToRightAima();
            line.setVisibility(View.VISIBLE);
        } else if (x > 3) {
            flagLeft = false;
            flagRight = true;
            flag=true;
        }else if (x==0){
            flagLeft = false;
            flagRight = false;
            flag=false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        System.gc();
    }
     private void addData() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Gson gson = new Gson();
                allGoodsListData = gson.fromJson(msg.obj.toString(), AllGoodsListBean.class);
                picturesData = allGoodsListData.getData().getList().get(position).getWear_video();
                listView.setAdapter(new UpListViewAdapter(allGoodsListData, getApplication(), position), new DownListViewAdapter(allGoodsListData, getApplication(), position));
                listView.setLinkageSpeed(1.2f);
                background.setImageURI(Uri.parse(allGoodsListData.getData().getList().get(position).getGoods_img()));
                return false;
            }
        });
    }

    private void post() {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add(DEVICE_TYPE, DEVICE);
        builder.add(VERSION, VERSION_VALUE);
        String url = BEGIN_URL + PRODUCT_LIST;
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                Message message = new Message();
                message.obj = s;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_detail:
                finish();
                break;
            case R.id.btn_pictures:
                Intent picturesActivity = new Intent(this, PicturesActivity.class);
                picturesActivity.putExtra("video_picture", (Serializable) picturesData);
                startActivity(picturesActivity);
                break;
            case R.id.btn_buy:
                if (token != null) {
                    Intent intent = new Intent(this, OrderDetailActivity.class);
                    intent.putExtra("pic", allGoodsListData.getData().getList().get(position).getGoods_pic());
                    intent.putExtra("title", allGoodsListData.getData().getList().get(position).getGoods_name());
                    intent.putExtra("content", allGoodsListData.getData().getList().get(position).getBrand());
                    intent.putExtra("price", allGoodsListData.getData().getList().get(position).getGoods_price());
                    intent.putExtra("goodsId", allGoodsListData.getData().getList().get(position).getGoods_id());
                    intent.putExtra("code", 0);
                    startActivity(intent);
                }else{
                    Intent loginIntent = new Intent(this,LoginActivity.class);
                    startActivity(loginIntent);
                }
                break;
        }
    }

    public void setToRightAima() {
        line.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(BaseApplication.getContext(), R.anim.anim_goods_line_toright);
        line.startAnimation(animation);
    }

    public void setToLeftAima() {
        Animation animation = AnimationUtils.loadAnimation(BaseApplication.getContext(), R.anim.anim_goods_line_toleft);
        line.startAnimation(animation);
    }


}
