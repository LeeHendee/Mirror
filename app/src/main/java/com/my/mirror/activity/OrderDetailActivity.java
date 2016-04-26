package com.my.mirror.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.bean.AlipayBean;
import com.my.mirror.bean.PayBean;
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.greendao.LoginTokenDao;
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

    private PopupWindow popupWindow;
    private Button aliPayBtn,btn;
    private AlipayBean bean;
    private String token, order_no, addr_id, goodsName, goodsId,
                    title, content, price, pic, name, address, tel;
    private String payInfo;
    private TextView addAddressTv, titleTv, describeTv, priceTv, nameTv, addressTv, telTv;
    private SimpleDraweeView simpleDraweeView;
    private int code;
    private ImageView back;
    private SharedPreferences sp;
    private LoginTokenDao loginTokenDao;

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
        goodsId = intent.getStringExtra("goodsId");
        //判断
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

        //从数据库获得token
        sp = getSharedPreferences("login",MODE_PRIVATE);
        if (sp.getInt("loginInt",0) == 1){
            loginTokenDao = DaoSingleton.getInstance(BaseApplication.getContext()).getLoginTokenDao();
            token = loginTokenDao.queryBuilder().list().get(0).getToken();
        }

        //解析获得支付宝用的东西
        OkHttpUtils.post().url(BEGIN_URL + SUB)
                .addParams(TOKEN, token)
                .addParams(DEVICE_TYPE, DEVICE_REUSE)
                .addParams(GOODS_ID, goodsId)
                .addParams(GOODS_PRICE, price)
                .addParams(GOODS_NUM, DEVICE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, AlipayBean.class);
                order_no = bean.getData().getOrder_id();
                addr_id = bean.getData().getAddress().getAddr_id();
                goodsName = bean.getData().getGoods().getGoods_name();
                OkHttpUtils.post().url(BEGIN_URL + ALI_PAY)
                        .addParams(TOKEN, token)
                        .addParams(ORDER_NO, order_no)
                        .addParams(ADDR_ID, addr_id)
                        .addParams(GOODSNAME, goodsName).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {

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
        back = findId(R.id.register_close);
        back.setOnClickListener(this);
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
                intent1.putExtra("goodsId",goodsId);
                startActivity(intent1);
                break;
            case R.id.orderdetail_btn:
                //支付宝。
                appearPay(v);
                break;
            case R.id.register_close:
                finish();
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
