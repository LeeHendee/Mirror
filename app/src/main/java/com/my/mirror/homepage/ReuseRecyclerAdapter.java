package com.my.mirror.homepage;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.my.mirror.R;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.gson.HomePageBean;

/**
 * Created by dllo on 16/3/30.
 */
public class ReuseRecyclerAdapter extends RecyclerView.Adapter<ReuseRecyclerAdapter.MyViewHolder> {

    private HomePageBean bean;

    public ReuseRecyclerAdapter(HomePageBean bean) {
        this.bean = bean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View allView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reuse_all,null);
        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reuse_project,null);
        // TODO 解析判断用哪个行布局
        return new MyViewHolder(allView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.pic.setImageURI(Uri.parse(bean.getData().getList().get(position).getGoods_img()));
        holder.name.setText(bean.getData().getList().get(position).getGoods_name());
        holder.price.setText(bean.getData().getList().get(position).getGoods_price());
        holder.area.setText(bean.getData().getList().get(position).getProduct_area());
        holder.type.setText(bean.getData().getList().get(position).getBrand());

        Animation animation = AnimationUtils.loadAnimation(BaseApplication.getContext(), R.anim.loading);
        holder.loading.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return bean.getData().getList().size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView pic;
        private TextView name,price,area,type;
        private ImageView loading;

        public MyViewHolder(View itemView) {
            super(itemView);

            pic = (SimpleDraweeView) itemView.findViewById(R.id.item_all_pic);
            name = (TextView) itemView.findViewById(R.id.item_all_name);
            price = (TextView) itemView.findViewById(R.id.item_all_price);
            area = (TextView) itemView.findViewById(R.id.item_all_country);
            type = (TextView) itemView.findViewById(R.id.item_all_type);
            loading = (ImageView) itemView.findViewById(R.id.item_reuse_loading);

        }
    }
}
