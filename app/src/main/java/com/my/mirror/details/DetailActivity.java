package com.my.mirror.details;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.gson.ProductDetail;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.callback.StringCallback;
import com.my.mirror.utils.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;

import java.net.InetAddress;
import java.util.List;

import okhttp3.Call;


/**
 * Created by dllo on 16/3/29.
 */
public class DetailActivity extends BaseActivity implements View.OnClickListener,INetAddress {
    private Button backBtn,picturesBtn,buyBtn;
    private ImageView backgroundIv;
    private ListView pListView,tListView;
    private DetailPictureAdapter pAdapter;
    private TextView nameTv,brandTv,descTv,priceTv;
    private List<ProductDetail.DataEntity.ListEntity.DesignDesEntity> datas;
    private List<ProductDetail.DataEntity.ListEntity> data;


    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initData() {
        OkHttpUtils
                .post()
                .url(BEGIN_URL+PRODUCT_LIST)
                .addParams(DEVICE_TYPE, DEVICE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        MyToast.mToast("加载失败");
                    }

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        ProductDetail productDetail = gson.fromJson(response, ProductDetail.class);
                        datas = productDetail.getData().getList().get(0).getDesign_des();
                        pAdapter.addData(datas);
                        pListView.setAdapter(pAdapter);

                        data = productDetail.getData().getList();
                        //设置图片层,头文件显示的内容:
                        nameTv.setText(data.get(0).getGoods_name());
                        brandTv.setText(data.get(0).getBrand());
                        descTv.setText(data.get(0).getInfo_des());
                        priceTv.setText(data.get(0).getGoods_price());

                    }
                });
    }

    @Override
    protected void initView() {
        backBtn = findId(R.id.btn_back_detail);
        picturesBtn = findId(R.id.btn_pictures);
        buyBtn = findId(R.id.btn_buy);
        backgroundIv = findId(R.id.iv_background);
        pListView = findId(R.id.lv_pictures_detail);
        tListView = findId(R.id.lv_text_detail);
        //加载头布局
        View firstHeadView = LayoutInflater.from(this).inflate(R.layout.first_headview,null);
        nameTv = (TextView) firstHeadView.findViewById(R.id.tv_name_detail);
        brandTv = (TextView) firstHeadView.findViewById(R.id.tv_brand_detail);
        descTv = (TextView) firstHeadView.findViewById(R.id.tv_description_detail);
        priceTv = (TextView) firstHeadView.findViewById(R.id.tv_price_detail);
        pListView.addHeaderView(firstHeadView);
        //加载第二个头布局;
        View secondHeadView = LayoutInflater.from(this).inflate(R.layout.second_headview,null);
        pListView.addHeaderView(secondHeadView);
        //弹出的button监听;
        backBtn.setOnClickListener(this);
        picturesBtn.setOnClickListener(this);
        buyBtn.setOnClickListener(this);
        //初始化pictureAdapter;
        pAdapter = new DetailPictureAdapter(DetailActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back_detail:
                finish();
                break;
            case R.id.btn_pictures:
                Intent picturesActivity = new Intent();
                startActivity(picturesActivity);
                break;
            case R.id.btn_buy:
                Intent buyActivity = new Intent();
                startActivity(buyActivity);
                break;
        }
    }

    class DetailPictureAdapter extends BaseAdapter{
        private Context context;
        private List<ProductDetail.DataEntity.ListEntity.DesignDesEntity> datas;

        public DetailPictureAdapter(Context context) {
            this.context = context;

        }

        public void addData(List<ProductDetail.DataEntity.ListEntity.DesignDesEntity> datas){
            this.datas = datas;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return datas!=null?datas.size():0;
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder holder;
            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_detail_second,parent,false);
                holder = new MyViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (MyViewHolder) convertView.getTag();
            }
            Log.d("tag","--->>"+datas.get(position).getType());

            holder.simpleDraweeView.setImageURI(Uri.parse(datas.get(position).getImg()));

            return convertView;
        }
        class MyViewHolder {
            SimpleDraweeView simpleDraweeView;

            public MyViewHolder(View view) {
                simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_second);
            }
        }
    }
}
