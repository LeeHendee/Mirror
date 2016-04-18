package com.my.mirror.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.net.okhttp.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by liangzaipan on 16/4/15.
 */
public class AddAddressActivity extends BaseActivity {
    private EditText nameEt,phoneEt,addressEt;
    private Button btn;
    @Override
    protected int getLayout() {
        return R.layout.activity_addadress;
    }

    @Override
    protected void initData() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.post().url("http://api101.test.mirroreye.cn/index.php/user/add_address").addParams("token","d40a6a56e4b5cc45c7aa03c76ec8e6f")
                        .addParams("username",nameEt.getText().toString())
                        .addParams("cellphone",phoneEt.getText().toString())
                        .addParams("addr_info",addressEt.getText().toString())
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Log.i("-------",response);
                    }
                });
            }
        });
    }

    @Override
    protected void initView() {
        nameEt = findId(R.id.addaddress_name_edit);
        phoneEt = findId(R.id.addaddress_phoneNum_edit);
        addressEt = findId(R.id.addaddress_address_edit);
        btn = findId(R.id.addaddress_btn);
    }
}
