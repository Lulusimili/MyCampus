package com.example.administrator.mycampus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.mycampus.R;
import com.example.administrator.mycampus.bean.Lost;

import java.util.List;

import static cn.bmob.v3.Bmob.getApplicationContext;
import static cn.bmob.v3.Bmob.initialize;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class LostAdapter extends RecyclerView.Adapter<LostAdapter.ViewHolder> {
    private List<Lost> lostList;
    private Context context;
    private LostAdapter.OnItemClickListener onItemClickListener;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        TextView publishTime;
        ImageButton imageButton;
        TextView content;

        public ViewHolder(View view){
            super(view);
            photo=view.findViewById(R.id.image);
            publishTime=view.findViewById(R.id.publish_time);
            imageButton=view.findViewById(R.id.image_button);
            content=view.findViewById(R.id.content);
        }

    }

    public void setOnItemClickListener(LostAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public LostAdapter(List<Lost>lostList, Context context){
        this.lostList=lostList;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.content.setText(lostList.get(position).getContent());
        holder.publishTime.setText(lostList.get(position).getCreatedAt());
        try {
            Glide.with(context)
                    .load(lostList.get(position).getImageUrl())
                    .into(holder.photo);
        }catch (Exception e){
            e.printStackTrace();
        }

        /**
         * 显示测试账号头像*/
        if (lostList.get(position).getPublishAccount().equals("123456")){
            holder.imageButton.setBackgroundResource(R.drawable.girl);
        }
        else if(lostList.get(position).getPublishAccount().equals("654321")){
            holder.imageButton.setBackgroundResource(R.drawable.boy);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(onItemClickListener!=null){
                        int pos = holder.getLayoutPosition();

                        onItemClickListener.onItemClick(holder.itemView, pos);


                    }
                }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    int pos=holder.getLayoutPosition();
                    onItemClickListener.onClick(holder.imageButton,pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lostList.size();
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onClick(View view,int position);
    }

}
