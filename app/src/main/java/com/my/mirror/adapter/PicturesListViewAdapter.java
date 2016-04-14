package com.my.mirror.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.my.mirror.R;
import com.my.mirror.bean.AllGoodsListData;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/3/30.
 */
public class PicturesListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<AllGoodsListData.DataEntity.ListEntity.WearVideoEntity> picturesData;

    public PicturesListViewAdapter(Context mContext, List<AllGoodsListData.DataEntity.ListEntity.WearVideoEntity> picturesData) {
        this.mContext = mContext;
        this.picturesData = picturesData;
    }


    @Override
    public int getCount() {
        return picturesData!=null?picturesData.size():0;
    }

    @Override
    public Object getItem(int position) {
        return picturesData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pictures_listview,parent,false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (MyViewHolder) convertView.getTag();
        }
        if (Integer.valueOf(picturesData.get(position).getType())==5){
            Picasso.with(mContext).load(Uri.parse(picturesData.get(position).getData())).into(holder.pictureIv);
        }

        return convertView;
    }

    class MyViewHolder {
        ImageView pictureIv;

        public MyViewHolder(View view) {
            pictureIv = (ImageView) view.findViewById(R.id.iv_pictures);

        }
    }

}
