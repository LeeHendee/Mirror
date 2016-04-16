package com.my.mirror.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.adapter.DownListViewAdapter;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.bean.AllGoodsListData;
import com.my.mirror.adapter.LinkageListView;
import com.my.mirror.adapter.UpListViewAdapter;
import com.my.mirror.net.okhttp.INetAddress;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;


public class GoodsDetailActivity extends BaseActivity implements INetAddress, View.OnClickListener {
    private LinkageListView listView;
    private AllGoodsListData allGoodsListData;
    private Handler handler;
    private int position;
    private SimpleDraweeView background;
    private Button backBtn, picturesBtn, buyBtn;
    private List<AllGoodsListData.DataEntity.ListEntity.WearVideoEntity> picturesData;

    @Override
    protected int getLayout() {
        return R.layout.activity_goodsdetail;
    }

    @Override
    protected void initData() {
        post();
        addData();
        allGoodsListData = new AllGoodsListData();
//        Intent intent = getIntent();
//        position = intent.getIntExtra("position", 0);
//        Toast.makeText(this, "position:" + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void initView() {
        listView = (LinkageListView) findViewById(R.id.detail_listview);
        background = (SimpleDraweeView) findViewById(R.id.goodsdetail_background);
        backBtn = findId(R.id.btn_back_detail);
        picturesBtn = findId(R.id.btn_pictures);
        buyBtn = findId(R.id.btn_buy);
        //弹出的button监听;
        backBtn.setOnClickListener(this);
        picturesBtn.setOnClickListener(this);
        buyBtn.setOnClickListener(this);

        //按钮菜单滑动监听:
//        listView.getBottomListView().setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//               if (listView.getTopListView()==v){
//
//               }
//            }
//        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    private void addData() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Gson gson = new Gson();
                allGoodsListData = gson.fromJson(msg.obj.toString(), AllGoodsListData.class);
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
        builder.add(VERSION,VERSION_VALUE);
        String url = BEGIN_URL+PRODUCT_LIST;
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
                Intent buyActivity = new Intent(this,OrderDetailActivity.class);
                buyActivity.putExtra("orderDetail_picture",allGoodsListData.getData().getList().get(position).getGoods_pic());
                buyActivity.putExtra("orderDetail_name",allGoodsListData.getData().getList().get(position).getBrand());
                buyActivity.putExtra("orderDetail_describe",allGoodsListData.getData().getList().get(position).getGoods_name());
                buyActivity.putExtra("orderDetail_price",allGoodsListData.getData().getList().get(position).getGoods_price());
                startActivity(buyActivity);
                break;
        }
    }




}
