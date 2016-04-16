package com.my.mirror.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;

/**
 * Created by liangzaipan on 16/4/15.
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView addressTV,addAddressTv,titleTv,describeTv,priceTv;
    private SimpleDraweeView simpleDraweeView;
    private Button btn;
    private String iv,name,describe,price;
    @Override
    protected int getLayout() {
        return R.layout.activity_orderdetail_fisrt;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        iv = intent.getStringExtra("orderDetail_picture");
        name = intent.getStringExtra("orderDetail_name");
        describe = intent.getStringExtra("orderDetail_describe");
        price = intent.getStringExtra("orderDetail_price");
        simpleDraweeView.setImageURI(Uri.parse(iv));
        titleTv.setText(name);
        describeTv.setText(describe);
        priceTv.setText(price);
    }

    @Override
    protected void initView() {
        addAddressTv = findId(R.id.orderdetail_addaddress);
        addressTV = findId(R.id.orderdetail_address);
        titleTv = findId(R.id.orderdetail_title);
        describeTv = findId(R.id.orderdetail_describe);
        priceTv = findId(R.id.orderdetail_price);
        simpleDraweeView = findId(R.id.orderdetail_iv);
        btn = findId(R.id.orderdetail_btn);
        addAddressTv.setOnClickListener(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.orderdetail_addaddress:
                Intent intent1 = new Intent(this,AllAddressActivity.class);
                startActivity(intent1);
                break;
            case R.id.orderdetail_btn:
                break;
        }
    }
}
