package com.my.mirror.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.greendao.LoginTokenDao;
import com.my.mirror.net.okhttp.INetAddress;
import com.my.mirror.net.okhttp.StringCallback;
import com.my.mirror.utils.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by liangzaipan on 16/4/15.
 */
public class AddAddressActivity extends BaseActivity implements INetAddress{
    private EditText nameEt, phoneEt, addressEt;
    private Button btn;
    private ImageView closeIv;
    private LoginTokenDao loginTokenDao;
    private String token;
    private String id, title, content, price, pic, name, address, tel;

    @Override
    protected int getLayout() {
        return R.layout.activity_addadress;
    }

    @Override
    protected void initData() {
        final Intent intent = getIntent();
        id = intent.getStringExtra("address_id");
        pic = intent.getStringExtra("pic");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        price = intent.getStringExtra("price");

        name = intent.getStringExtra("name");
        tel = intent.getStringExtra("tel");
        address = intent.getStringExtra("address");
        nameEt.setText(name);
        phoneEt.setText(tel);
        addressEt.setText(address);

        loginTokenDao = DaoSingleton.getInstance(BaseApplication.getContext()).getLoginTokenDao();
        token = loginTokenDao.queryBuilder().list().get(0).getToken();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == null) {
                    name = nameEt.getText().toString();
                    tel = phoneEt.getText().toString();
                    address = addressEt.getText().toString();
                    if (name.length() > 0 && tel.length() > 0 && address.length() > 0) {
                        OkHttpUtils.post().url(BEGIN_URL+ADD_DRESS)
                                .addParams(TOKEN, token)
                                .addParams(USERNAME, name)
                                .addParams(CELLPHONE, tel)
                                .addParams(ADDR_INFO, address)
                                .build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                Intent intent = new Intent(AddAddressActivity.this, AllAddressActivity.class);
                                intent.putExtra("pic", pic);
                                intent.putExtra("title", title);
                                intent.putExtra("content", content);
                                intent.putExtra("price", price);
                                startActivity(intent);
                            }
                        });
                    } else {
                        MyToast.mToast(getString(R.string.please_write_all_info));
                    }
                } else {
                    if (nameEt.getText().toString().length() > 0 && phoneEt.getText().toString().length() > 0 && addressEt.getText().toString().length() > 0) {
                        OkHttpUtils.post().url(BEGIN_URL + EDIT_ADDRESS)
                                .addParams(TOKEN, token)
                                .addParams(USERNAME, nameEt.getText().toString())
                                .addParams(CELLPHONE, phoneEt.getText().toString())
                                .addParams(ADDR_INFO, addressEt.getText().toString())
                                .addParams(ADDR_ID, id)
                                .build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                Intent intent = new Intent(AddAddressActivity.this, AllAddressActivity.class);
                                intent.putExtra("pic", pic);
                                intent.putExtra("title", title);
                                intent.putExtra("content", content);
                                intent.putExtra("price", price);
                                startActivity(intent);
                            }
                        });
                    } else {
                        MyToast.mToast(getString(R.string.please_write_all_info));
                    }
                }
            }
        });
    }

    @Override
    protected void initView() {
        nameEt = findId(R.id.addaddress_name_edit);
        phoneEt = findId(R.id.addaddress_phoneNum_edit);
        addressEt = findId(R.id.addaddress_address_edit);
        btn = findId(R.id.addaddress_btn);
        closeIv = findId(R.id.addaddress_close);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
