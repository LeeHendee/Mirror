package com.my.mirror.homepage;

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
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.greendao.ReUseDao;
import com.my.mirror.net.ImageLoaderHelper;

/**
 * Created by dllo on 16/4/14.
 */
public class ReuseNoNetAdapter extends RecyclerView.Adapter<ReuseNoNetAdapter.ViewHolder> {

    private ReUseDao reUseDao;
    private ImageLoaderHelper helper;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View allView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reuse_all,null);
        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reuse_project,null);
        return new ViewHolder(allView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        helper  = ImageLoaderHelper.getImageLoaderHelper();
        helper.loadImage(reUseDao.queryBuilder().list().get(position).getImg(),holder.pic);
        holder.name.setText(reUseDao.queryBuilder().list().get(position).getName());
        holder.area.setText(reUseDao.queryBuilder().list().get(position).getArea());
        holder.type.setText(reUseDao.queryBuilder().list().get(position).getBrand());
        holder.price.setText(reUseDao.queryBuilder().list().get(position).getPrice());
        Animation animation = AnimationUtils.loadAnimation(BaseApplication.getContext(), R.anim.loading);
        holder.loading.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        net();
        return reUseDao.queryBuilder().list().size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView pic;
        private TextView name,price,area,type;
        private ImageView loading;

        public ViewHolder(View itemView) {
            super(itemView);


            pic = (SimpleDraweeView) itemView.findViewById(R.id.item_all_pic);
            name = (TextView) itemView.findViewById(R.id.item_all_name);
            price = (TextView) itemView.findViewById(R.id.item_all_price);
            area = (TextView) itemView.findViewById(R.id.item_all_country);
            type = (TextView) itemView.findViewById(R.id.item_all_type);
            loading = (ImageView) itemView.findViewById(R.id.item_reuse_loading);
        }
    }

    private void net(){
        reUseDao = DaoSingleton.getInstance().getReUseDao();
        //classiFied = reUseDao.queryBuilder().list().get(0);
    }
}
