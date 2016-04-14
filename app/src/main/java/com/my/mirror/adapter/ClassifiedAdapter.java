package com.my.mirror.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.mirror.R;
import com.my.mirror.greendao.ClassiFied;
import com.my.mirror.greendao.ClassiFiedDao;
import com.my.mirror.greendao.DaoSingleton;

/**
 * Created by dllo on 16/4/1.
 * 菜单栏的适配器
 */
public class ClassifiedAdapter extends BaseAdapter {
    private int i;
    private ClassiFiedDao classiFiedDao;
    private ClassiFied classiFied;

    public ClassifiedAdapter( int i) {
        this.i = i;
    }

    @Override
    public int getCount() {
        net();
        return classiFiedDao.queryBuilder().list().size();
    }

    @Override
    public Object getItem(int position) {
        net();
        return classiFiedDao.queryBuilder().list().get(position);
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
        net();

            holder.name.setText("浏览"+classiFiedDao.queryBuilder().list().get(position).getTitle());
        if (i == position){
            holder.name.setAlpha(1);
            holder.line.setVisibility(View.VISIBLE);
        }  else {
            holder.name.setAlpha(0.25f);
            holder.line.setVisibility(View.GONE);
        }



            return convertView;
    }

    private void net(){
        classiFiedDao = DaoSingleton.getInstance().getClassiFiedDao();
        classiFied = classiFiedDao.queryBuilder().list().get(0);
    }

    public class MyViewHolder{
        private TextView name;
        private ImageView line;
    }

}
