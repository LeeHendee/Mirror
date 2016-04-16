package com.my.mirror.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.my.mirror.R;
import com.my.mirror.activity.GoodsDetailActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.greendao.ClassiFiedDao;
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.greendao.ReUseDao;
import com.my.mirror.net.ImageLoaderHelper;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/4/14.
 * 复用的fragment没有网的适配器
 */
public class ReuseNoNetAdapter extends RecyclerView.Adapter<ReuseNoNetAdapter.ViewHolder> {

    private ReUseDao reUseDao;
    private ImageLoaderHelper helper;
    private Context context;
    private int postions;

    public void setPostion(int postion) {
        this.postions = postion;
    }

    public ReuseNoNetAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View allView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reuse_all,null);
        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reuse_project,null);
        return new ViewHolder(allView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        helper  = ImageLoaderHelper.getImageLoaderHelper();
        helper.loadImage(reUseDao.queryBuilder().where(ReUseDao.Properties.TypeId.eq(String.valueOf(postions))).list().get(position).getImg(), holder.pic);
        holder.name.setText(reUseDao.queryBuilder().where(ReUseDao.Properties.TypeId.eq(String.valueOf(postions))).list().get(position).getName());
        holder.area.setText(reUseDao.queryBuilder().where(ReUseDao.Properties.TypeId.eq(String.valueOf(postions))).list().get(position).getArea());
        holder.type.setText(reUseDao.queryBuilder().where(ReUseDao.Properties.TypeId.eq(String.valueOf(postions))).list().get(position).getBrand());
        holder.price.setText(reUseDao.queryBuilder().where(ReUseDao.Properties.TypeId.eq(String.valueOf(postions))).list().get(position).getPrice());

        Animation animation = AnimationUtils.loadAnimation(BaseApplication.getContext(), R.anim.loading);
        holder.loading.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        net();
        return reUseDao.queryBuilder().where(ReUseDao.Properties.TypeId.eq(String.valueOf(postions))).list().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name,price,area,type;
        private ImageView loading,pic;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, R.string.nonet_toast, Toast.LENGTH_SHORT).show();
                }
            });

            pic = (ImageView) itemView.findViewById(R.id.item_all_pic);
            name = (TextView) itemView.findViewById(R.id.item_all_name);
            price = (TextView) itemView.findViewById(R.id.item_all_price);
            area = (TextView) itemView.findViewById(R.id.item_all_country);
            type = (TextView) itemView.findViewById(R.id.item_all_type);
            loading = (ImageView) itemView.findViewById(R.id.item_reuse_loading);
        }
    }

    private void net(){
        reUseDao = DaoSingleton.getInstance(BaseApplication.getContext()).getReUseDao();
        //classiFied = reUseDao.queryBuilder().list().get(0);
    }
    private List<String> getCategoryIds(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i <DaoSingleton.getInstance(BaseApplication.getContext()).getClassiFiedDao().queryBuilder().list().size() ; i++) {
            list.add(DaoSingleton.getInstance(BaseApplication.getContext()).getClassiFiedDao().queryBuilder().list().get(i).getCategoryId());

        }
        return  list  ;

    }
}
