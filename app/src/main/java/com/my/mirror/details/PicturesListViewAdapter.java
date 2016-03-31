package com.my.mirror.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.my.mirror.R;

/**
 * Created by dllo on 16/3/30.
 */
public class PicturesListViewAdapter extends BaseAdapter {
    private Context mContext;
    //TODO 把适配器需要的数据传过来;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pictures_listview,parent,false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (MyViewHolder) convertView.getTag();
        }
//        holder.pictureIv.setImageBitmap();

        return convertView;
    }

    class MyViewHolder {
        ImageView pictureIv;

        public MyViewHolder(View view) {
            pictureIv = (ImageView) view.findViewById(R.id.iv_pictures);
        }
    }
}
