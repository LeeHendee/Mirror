package com.my.mirror.homepage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.my.mirror.R;

/**
 * Created by dllo on 16/3/30.
 */
public class ReuseRecyclerAdapter extends RecyclerView.Adapter<ReuseRecyclerAdapter.MyViewHolder> {

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View allView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reuse_all,null);
        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reuse_project,null);
        // TODO 解析判断用哪个行布局
        return new MyViewHolder(allView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
