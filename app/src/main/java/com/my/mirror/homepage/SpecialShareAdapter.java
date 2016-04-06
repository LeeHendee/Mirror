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

/**
 * Created by dllo on 16/4/6.
 */
public class SpecialShareAdapter extends RecyclerView.Adapter<SpecialShareAdapter.SpecialViewHolder> {
    private SpecialShareBean bean;

    public SpecialShareAdapter(SpecialShareBean bean) {
        this.bean = bean;
    }

    @Override
    public SpecialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reuse_project,null);

        return new SpecialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpecialViewHolder holder, int position) {
        holder.simpleDraweeView.setImageURI(Uri.parse(bean.getData().getList().get(position).getStory_img()));
        holder.type.setText(bean.getData().getList().get(position).getStory_title());

        //添加动画
        Animation animation = AnimationUtils.loadAnimation(BaseApplication.getContext(), R.anim.loading);
        holder.loading.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return bean.getData().getList().size();
    }

    class SpecialViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView simpleDraweeView;
        private TextView type;
        private ImageView loading;

        public SpecialViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.item_project_pic);
            type = (TextView) itemView.findViewById(R.id.item_project_type);
            loading = (ImageView) itemView.findViewById(R.id.item_project_loading);
        }
    }
}
