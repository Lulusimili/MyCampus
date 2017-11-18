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
import com.example.administrator.mycampus.bean.Found;
import com.example.administrator.mycampus.bean.Lost;

import java.util.List;

import static cn.bmob.v3.Bmob.getApplicationContext;


public class FoundAdapter extends RecyclerView.Adapter<FoundAdapter.ViewHolder> {
    private List<Found> foundList;
    private Context context;
    private FoundAdapter.OnItemClickListener onItemClickListener;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        TextView publishTime;
        ImageButton imageButton;
        TextView content;

        public ViewHolder(View view){
            super(view);
            photo=view.findViewById(R.id.photo);
            publishTime=view.findViewById(R.id.publish_time);
            imageButton=view.findViewById(R.id.image_button);
            content=view.findViewById(R.id.content);
        }

    }

    public void setOnItemClickListener(FoundAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public FoundAdapter(List<Found>foundList, Context context){
        this.foundList=foundList;
        this.context=context;
    }

    @Override
    public FoundAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);
        FoundAdapter.ViewHolder holder=new FoundAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final FoundAdapter.ViewHolder holder,
                                 int position) {

        holder.content.setText(foundList.get(position).getContent());
        holder.publishTime.setText(foundList.get(position).getCreatedAt());
        try {
            Glide.with(context)
                    .load(foundList.get(position)
                            .getImageUrl())
                    .into(holder.photo);
        }catch (Exception e){
            e.printStackTrace();
        }
        //测试账号
        if (foundList.get(position).getPublishAccount().equals("123456")){
            holder.imageButton.setBackgroundResource(R.drawable.girl);
        }
        else if(foundList.get(position).getPublishAccount().equals("654321")){
            holder.imageButton.setBackgroundResource(R.drawable.boy);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null) {
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
        return foundList.size();
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onClick(View view,int position);
    }
}
