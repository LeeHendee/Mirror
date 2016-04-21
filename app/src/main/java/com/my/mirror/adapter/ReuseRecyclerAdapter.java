package com.my.mirror.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.my.mirror.R;
import com.my.mirror.activity.GoodsDetailActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.bean.HomePageBean;
import com.my.mirror.net.ImageLoaderHelper;

/**
 * Created by dllo on 16/3/30.
 * 复用fragment的适配器
 */
public class ReuseRecyclerAdapter extends RecyclerView.Adapter<ReuseRecyclerAdapter.MyViewHolder> {

    private HomePageBean bean;
    private ImageLoaderHelper helper;
    private Context context;
    private int i;

    public ReuseRecyclerAdapter(HomePageBean bean, Context context, int i) {
        this.bean = bean;
        this.context = context;
        this.i = i;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View allView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reuse_all, parent,false);
        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reuse_project, null);
        return new MyViewHolder(allView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        helper = ImageLoaderHelper.getImageLoaderHelper();
        helper.loadImage(bean.getData().getList().get(position).getGoods_img(), holder.pic);
        //holder.pic.setImageURI(Uri.parse(bean.getData().getList().get(position).getGoods_img()));
        holder.name.setText(bean.getData().getList().get(position).getGoods_name());
        holder.price.setText(bean.getData().getList().get(position).getGoods_price());
        holder.area.setText(bean.getData().getList().get(position).getProduct_area());
        holder.type.setText(bean.getData().getList().get(position).getBrand());
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra("position", i);
                intent.putExtra("homePageBean",bean);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bean.getData().getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView pic;
        private TextView name, price, area, type;
        private LinearLayout line;

        public MyViewHolder(View itemView) {
            super(itemView);

            pic = (ImageView) itemView.findViewById(R.id.item_all_pic);
            name = (TextView) itemView.findViewById(R.id.item_all_name);
            price = (TextView) itemView.findViewById(R.id.item_all_price);
            area = (TextView) itemView.findViewById(R.id.item_all_country);
            type = (TextView) itemView.findViewById(R.id.item_all_type);
            line = (LinearLayout) itemView.findViewById(R.id.item_all_line);

        }
    }

}
