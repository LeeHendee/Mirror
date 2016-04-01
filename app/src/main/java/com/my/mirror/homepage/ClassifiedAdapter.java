package com.my.mirror.homepage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.my.mirror.R;
import com.my.mirror.gson.ClassifiedBean;

import java.util.List;

/**
 * Created by dllo on 16/4/1.
 */
public class ClassifiedAdapter extends BaseAdapter {
    private List<ClassifiedBean.DataEntity >data;
    private ClassifiedBean bean;

    public ClassifiedAdapter(ClassifiedBean bean) {
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return bean.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classified,null);
            holder = new MyViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.item_classified_tv);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.name.setText(bean.getData().get(position).getCategory_name());
        return convertView;
    }

    public class MyViewHolder{
        private TextView name;
    }

}
