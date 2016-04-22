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

import com.my.mirror.R;
import com.my.mirror.activity.SpecialShareContentActivity;
import com.my.mirror.base.BaseApplication;
import com.my.mirror.greendao.DaoSingleton;
import com.my.mirror.greendao.SpecialShareDao;
import com.my.mirror.net.ImageLoaderHelper;

/**
 * Created by dllo on 16/4/14.
 */
public class SpecialShareNoNetAdapter extends RecyclerView.Adapter<SpecialShareNoNetAdapter.SpecialViewHolder> {

    private Context context;
    private SpecialShareDao specialShareDao;
    private ImageLoaderHelper helper;

    public SpecialShareNoNetAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SpecialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special_share, null);
        return new SpecialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpecialViewHolder holder, int position) {
        helper = ImageLoaderHelper.getImageLoaderHelper();
        helper.loadImage(specialShareDao.queryBuilder().list().get(position).getImg(), holder.simpleDraweeView);
        holder.type.setText(specialShareDao.queryBuilder().list().get(position).getTitle());
        holder.pos = position;
    }

    @Override
    public int getItemCount() {
        specialShareDao = DaoSingleton.getInstance(BaseApplication.getContext()).getSpecialShareDao();
        return specialShareDao.queryBuilder().list().size();
    }

    class SpecialViewHolder extends RecyclerView.ViewHolder {
        private ImageView simpleDraweeView;
        private TextView type;
        private LinearLayout linearLayout;
        private int pos;

        public SpecialViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearlaout);
            simpleDraweeView = (ImageView) itemView.findViewById(R.id.item_project_pic);
            type = (TextView) itemView.findViewById(R.id.item_project_type);
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