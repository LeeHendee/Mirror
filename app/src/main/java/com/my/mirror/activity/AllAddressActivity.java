package com.my.mirror.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.my.mirror.R;
import com.my.mirror.adapter.AllAddressAdapter;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.bean.AddressBean;
import com.my.mirror.net.okhttp.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import java.util.List;
import okhttp3.Call;

/**
 * Created by liangzaipan on 16/4/15.
 */
public class AllAddressActivity extends BaseActivity {
    private AllAddressAdapter adapter;
    private ListView listView;
    private List<AddressBean> data;
    private TextView addaddressTv;
    @Override
    protected int getLayout() {
        return R.layout.activity_alladdress;
    }

    @Override
    protected void initData() {
        OkHttpUtils.post().url("http://api101.test.mirroreye.cn/index.php/user/address_list")
                .addParams("token","d40a6a56e4b5cc45c7aa03c76ec8e6f").addParams("device_type","2")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {


            }
        });
//        adapter = new AllAddressAdapter(data,this);
//        listView.setAdapter(adapter);
    }

    @Override
    protected void initView() {
        listView = findId(R.id.allAddress_listView);
        addaddressTv = findId(R.id.allAddress_addaddress);
        addaddressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllAddressActivity.this,AddAddressActivity.class);
                startActivity(intent);
            }
        });
    }
}
