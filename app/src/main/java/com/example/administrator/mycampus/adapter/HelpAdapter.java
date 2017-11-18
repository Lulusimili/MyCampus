package com.example.administrator.mycampus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mycampus.R;
import com.example.administrator.mycampus.bean.Help;
import com.example.administrator.mycampus.bean.Lost;

import java.util.List;


public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {
    private List<Help> helpList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView publishTime;
        ImageButton imageButton;
        TextView content;
        public ViewHolder(View view){
            super(view);
            publishTime=view.findViewById(R.id.publish_time);
            imageButton=view.findViewById(R.id.image_button);
            content=view.findViewById(R.id.content);
        }

    }
    public HelpAdapter(List<Help>helpList, Context context){
         this.helpList=helpList;
        this.context=context;
    }
    public void setOnItemClickListener(HelpAdapter.OnItemClickListener listener){
        onItemClickListener=listener;
    }

    @Override
    public HelpAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_help,parent,false);
        HelpAdapter.ViewHolder holder=new HelpAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HelpAdapter.ViewHolder holder, int position) {
       holder.content.setText(helpList.get(position).getContent());
        holder.publishTime.setText(helpList.get(position).getCreatedAt());
        if (helpList.get(position).getPublishAccount().equals("123456")){
            holder.imageButton.setBackgroundResource(R.drawable.girl);
        }
        else if(helpList.get(position).getPublishAccount().equals("654321")){
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
        return helpList.size();
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onClick(View view,int position);
    }

}
