package com.my.mirror.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.bean.AlipayBean;
import com.my.mirror.bean.PayBean;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by liangzaipan on 16/4/15.
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener, INetAddress {
    private TextView addressTV, addAddressTv, titleTv, describeTv, priceTv;
    private SimpleDraweeView simpleDraweeView;
    private Button btn;
    private String iv, name, describe, price, goodsId;
    private PopupWindow popupWindow;
    private Button aliPayBtn;
    private AlipayBean bean;
    private String token, order_no, addr_id, goodsName;
    private PayBean payBean;
    private String payInfo;


    //支付宝调用
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResultActvity payResultActvity = new PayResultActvity((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResultActvity.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResultActvity.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OrderDetailActivity.this, R.string.buy_details_activity_pay_success, Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderDetailActivity.this, R.string.buy_details_activity_pay_waiting, Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OrderDetailActivity.this, R.string.buy_details_activity_pay_fail, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

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
        goodsId = intent.getStringExtra("orderDetail_id");
        simpleDraweeView.setImageURI(Uri.parse(iv));
        titleTv.setText(name);
        describeTv.setText(describe);
        priceTv.setText(price);

        OkHttpUtils.post().url(BEGIN_URL + SUB)
                .addParams("token", "add853db56c099080449091b5bd88d0e")
                .addParams(DEVICE_TYPE, DEVICE_REUSE)
                .addParams(GOODS_ID, goodsId)
                .addParams(GOODS_PRICE, price)
                .addParams(GOODS_NUM, DEVICE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {



               // {"result":"1","msg":"","data":{"order_id":"14612855370OE","
               // address":{"addr_id":"1044",
               // {"goods_name":"SEE CONCEPT","goods_num":"","des":"玳瑁復古花紋閱讀鏡","price":"450","pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/Seeo0102549e4bee5442391fa715e7d33f6864c3.jpg","book_copy":"文案（订购商品）文案（订购商品）文案（订购商品）文案（订购商品）"},"if_ordain":"1"}}

            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, AlipayBean.class);
                order_no = bean.getData().getOrder_id();
                addr_id = bean.getData().getAddress().getAddr_id();
                goodsName = bean.getData().getGoods().getGoods_name();

                OkHttpUtils.post().url(BEGIN_URL + ALI_PAY)
                        .addParams("token", "add853db56c099080449091b5bd88d0e")
                        .addParams(ORDER_NO, order_no)
                        .addParams(ADDR_ID, addr_id)
                        .addParams(GOODSNAME, goodsName).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
//                Gson gson = new Gson();
//                payBean = gson.fromJson(response, PayBean.class);
//                payInfo = payBean.getData().getStr();
                        try {
                            JSONObject obj = new JSONObject(response);
                            payInfo = obj.getJSONObject("data").getString("str");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });


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
        switch (v.getId()) {
            case R.id.orderdetail_addaddress:
                Intent intent1 = new Intent(this, AllAddressActivity.class);
                startActivity(intent1);
                break;
            case R.id.orderdetail_btn:
                //支付宝。
                appearPay(v);
                break;
        }
    }


    public void appearPay(View v) {
        popupWindow = new PopupWindow(this);
        View view = LayoutInflater.from(this).inflate(R.layout.item_popupwindow_pay, null);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.update();
        //实例化popwindow内的组件
        initPopwindow(view);
    }

    private void initPopwindow(View view) {
        aliPayBtn = (Button) view.findViewById(R.id.ali_pay_btn);
        aliPayBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 完整的符合支付宝参数规范的订单信息
                 */
                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(OrderDetailActivity.this);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(payInfo, true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();


            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击popwindow界面退出popwindow
                popupWindow.dismiss();
            }
        });
    }
}
