package com.my.mirror.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.my.mirror.R;
import com.my.mirror.activity.AddAddressActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.bean.AddressBean;

import java.util.List;

/**
 * Created by liangzaipan on 16/4/18.
 */
public class AllAddressAdapter extends BaseAdapter {
    private AddressBean bean;
    private Context context;
    private String pic, title, content, price;

    public AllAddressAdapter(AddressBean bean,Context context) {
        this.bean = bean;
        this.context = context;
        notifyDataSetChanged();
    }

    public void setInformation(String pic, String title, String content, String price){
        this.pic = pic;
        this.title = title;
        this.content = content;
        this.price = price;
    }

    @Override
    public int getCount() {
        return bean.getData().getList() != null&& bean.getData().getList().size() > 0?bean.getData().getList().size():0;
    }

    @Override
    public Object getItem(int position) {
        return bean.getData().getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_alladdress_listview,parent,false);
            holder.userName = (TextView) convertView.findViewById(R.id.item_alladdress_name);
            holder.address = (TextView) convertView.findViewById(R.id.item_alladdress_location);
            holder.phoneNum = (TextView) convertView.findViewById(R.id.item_alladdress_tel);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_alladdress_btn);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (bean != null){
            holder.userName.setText(bean.getData().getList().get(position).getUsername());
            holder.address.setText(bean.getData().getList().get(position).getAddr_info());
            holder.phoneNum.setText(bean.getData().getList().get(position).getCellphone());
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddAddressActivity.class);
                    intent.putExtra("name",bean.getData().getList().get(position).getUsername());
                    intent.putExtra("address",bean.getData().getList().get(position).getAddr_info());
                    intent.putExtra("tel",bean.getData().getList().get(position).getCellphone());
                    intent.putExtra("address_id",bean.getData().getList().get(position).getAddr_id());
                    intent.putExtra("pic", pic);
                    intent.putExtra("title", title);
                    intent.putExtra("content", content);
                    intent.putExtra("price", price);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
        return convertView;
    }
    public class ViewHolder{
        private TextView userName,address,phoneNum;
        private ImageView imageView;
    }
}
