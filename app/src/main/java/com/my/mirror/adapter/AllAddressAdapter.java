package com.my.mirror.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.my.mirror.R;
import com.my.mirror.bean.AddressBean;

import java.util.List;

/**
 * Created by liangzaipan on 16/4/18.
 */
public class AllAddressAdapter extends BaseAdapter {
    private AddressBean bean;
    private Context context;

    public AllAddressAdapter(AddressBean bean,Context context) {
        this.bean = bean;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_alladdress_listview,null);
            holder.userName = (TextView) convertView.findViewById(R.id.item_alladdress_name);
            holder.address = (TextView) convertView.findViewById(R.id.item_alladdress_location);
            holder.phoneNum = (TextView) convertView.findViewById(R.id.item_alladdress_tel);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (bean != null){
//            holder.userName.setText(bean.getData().getList().get(position).
//            holder.address.setText(bean.get(position).getAddr_info());
//            holder.phoneNum.setText(bean.get(position).getCellphone());
        }
        return null;
    }
    public class ViewHolder{
        private TextView userName,address,phoneNum;
    }
}
