package com.my.mirror.homepage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.mirror.R;
import com.my.mirror.gson.ClassifiedBean;

import java.util.List;

/**
 * Created by dllo on 16/4/1.
 * 菜单栏的适配器
 */
public class ClassifiedAdapter extends BaseAdapter {
    private List<ClassifiedBean.DataEntity >data;
    private ClassifiedBean bean;
    private int i;

    public ClassifiedAdapter( int i,ClassifiedBean bean) {
        this.bean = bean;
        this.i = i;
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
            holder.line = (ImageView) convertView.findViewById(R.id.item_classified_iv);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.name.setText("浏览"+bean.getData().get(position).getCategory_name());
        if (i == position){
            holder.name.setAlpha(1);
            holder.line.setVisibility(View.VISIBLE);
        } else {
            holder.name.setAlpha(0.25f);
            holder.line.setVisibility(View.GONE);
        }



        return convertView;
    }

    public class MyViewHolder{
        private TextView name;
        private ImageView line;
    }

}
