package com.my.mirror.activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.adapter.AllAddressAdapter;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.bean.AddressBean;
import com.my.mirror.bean.OrderDetailBean;
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.greendao.LoginTokenDao;
import com.my.mirror.net.okhttp.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by liangzaipan on 16/4/15.
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView addAddressTv, titleTv, describeTv, priceTv, nameTv, addressTv, telTv;
    private SimpleDraweeView simpleDraweeView;
    private Button btn;
    private String title, content, price, pic, name, address, tel;
    private int code;

    @Override
    protected int getLayout() {
        return R.layout.activity_orderdetail_fisrt;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        code = intent.getIntExtra("code", 2);
        if (code == 0) {
            pic = intent.getStringExtra("pic");
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("content");
            price = intent.getStringExtra("price");

            simpleDraweeView.setImageURI(Uri.parse(pic));
            titleTv.setText(title);
            describeTv.setText(content);
            priceTv.setText(price);
        } else if (code == 1) {
            name = intent.getStringExtra("name");
            address = intent.getStringExtra("address");
            tel = intent.getStringExtra("tel");
            pic = intent.getStringExtra("myPic");
            title = intent.getStringExtra("myTitle");
            content = intent.getStringExtra("myContent");
            price = intent.getStringExtra("myPrice");
            nameTv.setText(getString(R.string.order_detail_name) + name);
            addressTv.setText(getString(R.string.order_detail_address) + address);
            telTv.setText(getString(R.string.order_detail_number) + tel);
            simpleDraweeView.setImageURI(Uri.parse(pic));
            titleTv.setText(title);
            describeTv.setText(content);
            priceTv.setText(price);
        }
    }


    @Override
    protected void initView() {
        addAddressTv = findId(R.id.orderdetail_addaddress);
        titleTv = findId(R.id.orderdetail_title);
        describeTv = findId(R.id.orderdetail_describe);
        priceTv = findId(R.id.orderdetail_price);
        simpleDraweeView = findId(R.id.orderdetail_iv);
        btn = findId(R.id.orderdetail_btn);
        addAddressTv.setOnClickListener(this);
        btn.setOnClickListener(this);
        nameTv = findId(R.id.orderdetail_name);
        addressTv = findId(R.id.orderdetail_address);
        telTv = findId(R.id.orderdetail_tel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderdetail_addaddress:
                Intent intent1 = new Intent(this, AllAddressActivity.class);
                intent1.putExtra("pic", pic);
                intent1.putExtra("title", title);
                intent1.putExtra("content", content);
                intent1.putExtra("price", price);
                startActivity(intent1);
                break;
            case R.id.orderdetail_btn:
                break;
        }
    }
}
