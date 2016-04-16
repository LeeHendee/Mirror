package com.my.mirror.adapter;

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

import com.my.mirror.R;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.bean.SpecialShareBean;
import com.my.mirror.activity.SpecialShareContentActivity;
import com.my.mirror.net.ImageLoaderHelper;

/**
 * Created by dllo on 16/4/6.
 *专题分享适配器
 */
public class SpecialShareAdapter extends RecyclerView.Adapter<SpecialShareAdapter.SpecialViewHolder> {

    private Context context;
    private ImageLoaderHelper helper;
    private SpecialShareBean bean;

    public SpecialShareAdapter(SpecialShareBean bean,Context context) {
        this.bean = bean;
        this.context = context;
    }

    @Override
    public SpecialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reuse_project, parent,false);
        return new SpecialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpecialViewHolder holder, int position) {
        helper = ImageLoaderHelper.getImageLoaderHelper();
        helper.loadImage(bean.getData().getList().get(position).getStory_img(), holder.simpleDraweeView);
        holder.type.setText(bean.getData().getList().get(position).getStory_title());
        holder.pos = position;
        //添加动画
        Animation animation = AnimationUtils.loadAnimation(BaseApplication.getContext(), R.anim.loading);
        holder.loading.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return bean.getData().getList().size();
    }

    class SpecialViewHolder extends RecyclerView.ViewHolder {
        private ImageView simpleDraweeView;
        private TextView type;
        private ImageView loading;
        private LinearLayout linearLayout;
        private int pos;

        public SpecialViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearlaout);
            simpleDraweeView = (ImageView) itemView.findViewById(R.id.item_project_pic);
            type = (TextView) itemView.findViewById(R.id.item_project_type);
            loading = (ImageView) itemView.findViewById(R.id.item_project_loading);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SpecialShareContentActivity.class);
                    context.startActivity(intent);
                }
            });

        }

    }
}
