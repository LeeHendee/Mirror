package com.my.mirror.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.my.mirror.R;
import com.my.mirror.adapter.AllAddressAdapter;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.bean.AddressBean;
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
public class AllAddressActivity extends BaseActivity implements View.OnClickListener,INetAddress {
    private AllAddressAdapter adapter;
    private SwipeMenuListView listView;
    private TextView addAddressTv;
    private AddressBean bean;
    private ImageView closeIv;
    private LoginTokenDao loginTokenDao;
    private String token,pic,title,content,price;

    @Override
    protected int getLayout() {
        return R.layout.activity_alladdress;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        pic = intent.getStringExtra("pic");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        price = intent.getStringExtra("price");

        loginTokenDao = DaoSingleton.getInstance(BaseApplication.getContext()).getLoginTokenDao();
        token = loginTokenDao.queryBuilder().list().get(0).getToken();
        String url = BEGIN_URL + ADDRESS_LIST;
        OkHttpUtils.post().url(url).addParams(TOKEN, token)
                .addParams(DEVICE_TYPE, DEVICE_REUSE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, AddressBean.class);
                adapter = new AllAddressAdapter(bean, BaseApplication.getContext());
                adapter.setInformation(pic, title, content, price);
                listView.setAdapter(adapter);
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xE6, 0x28, 0x44)));
                deleteItem.setWidth(150);
                deleteItem.setTitleSize(13);
                deleteItem.setTitle(getString(R.string.all_address_delete));
                deleteItem.setTitleColor(Color.WHITE);

                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        OkHttpUtils.post().url(BEGIN_URL + DEL_ADDRESS).addParams(TOKEN,token)
                                .addParams(ADDR_ID,bean.getData().getList().get(position).getAddr_id()).build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                MyToast.mToast(getString(R.string.all_address_del_address));

                            }
                        });
                        bean.getData().getList().remove(position);
                        adapter = new AllAddressAdapter(bean, getApplicationContext());
                        listView.setAdapter(adapter);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void initView() {
        listView = findId(R.id.allAddress_listView);
        addAddressTv = findId(R.id.allAddress_addaddress);
        closeIv = findId(R.id.alladdress_close);
        addAddressTv.setOnClickListener(this);
        closeIv.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                OkHttpUtils.post().url(BEGIN_URL+MR_ADDRESS)
                        .addParams(TOKEN, token).addParams(ADDR_ID, "").build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Intent intent1 = new Intent(AllAddressActivity.this, OrderDetailActivity.class);
                        intent1.putExtra("name", bean.getData().getList().get(position).getUsername());
                        intent1.putExtra("address", bean.getData().getList().get(position).getAddr_info());
                        intent1.putExtra("tel", bean.getData().getList().get(position).getCellphone());
                        intent1.putExtra("myTitle",title);
                        intent1.putExtra("myPrice",price);
                        intent1.putExtra("myContent",content);
                        intent1.putExtra("myPic",pic);
                        intent1.putExtra("code",1);
                        startActivity(intent1);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allAddress_addaddress:
                Intent intent = new Intent(AllAddressActivity.this, AddAddressActivity.class);
                intent.putExtra("pic", pic);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("price", price);
                startActivity(intent);
                break;
            case R.id.alladdress_close:
                finish();
                break;
        }
    }
}
