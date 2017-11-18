package com.example.administrator.mycampus.adapter;

import android.app.Notification;
import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.mycampus.R;
import com.example.administrator.mycampus.cache.MyCache;
import com.example.administrator.mycampus.info.MsgInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<MsgInfo> msgInfoList=new ArrayList<>();
    private ViewHolder viewHolder;
    private Html.ImageGetter myImageGetter;
    private Html.ImageGetter myReceiveImageGetter;
    private String receiveId;

    public MessageAdapter(Context context,Html.ImageGetter myImageGetter,Html.ImageGetter myReceiveImageGetter){
        this.context=context;
        this.myImageGetter=myImageGetter;
        this.myReceiveImageGetter=myReceiveImageGetter;
    }

    @Override
    public int getCount() {
        return msgInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return msgInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        String send=msgInfoList.get(i).getRight_text();
        Log.d("000",send);
        String receive=msgInfoList.get(i).getLeft_text();
        Spanned sendSpannedMessage;
        Spanned receiveSpannedMessage;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_message, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(send!=null){

            sendSpannedMessage=Html.fromHtml(send,myImageGetter,null);
            viewHolder.text_right.setText(sendSpannedMessage);
            viewHolder.right.setVisibility(View.VISIBLE);
            viewHolder.left.setVisibility(View.INVISIBLE);
            /**
             * 测试账号
             */
            if (MyCache.getAccount().equals("123456")){
               // viewHolder.left_sculpture.setImageResource(R.drawable.boy);
                viewHolder.right_sculpture.setImageResource(R.drawable.girl);
            }
            else {
                //viewHolder.left_sculpture.setImageResource(R.drawable.girl);
                viewHolder.right_sculpture.setImageResource(R.drawable.boy);
            }
            if(receive!=null){
                //SpannableString receiveSpannableString=new SpannableString(receive);
                //String receiveEmotion = "(<img src='(([abc]{2})[a-z])'/>){0,}";
               // Pattern patternEmotion = Pattern.compile(receiveEmotion);
               // Matcher matcherEmotion = patternEmotion.matcher(receiveSpannableString);

                receiveSpannedMessage=Html.fromHtml(receive,myReceiveImageGetter,null);
                viewHolder.text_left.setText(receiveSpannedMessage);
                viewHolder.left.setVisibility(View.VISIBLE);
                viewHolder.right.setVisibility(View.INVISIBLE);
                if(MyCache.getAccount().equals("123456")){
                    viewHolder.left_sculpture.setImageResource(R.drawable.boy);
                }
                else {
                    viewHolder.left_sculpture.setImageResource(R.drawable.girl);
                }

            }

        }
        return convertView;
    }
    public void addDataToAdapter(MsgInfo msgInfo){
        msgInfoList.add(msgInfo);
    }

    public class ViewHolder {
         View rootView;
         TextView text_left;
         LinearLayout left;
         TextView text_right;
         LinearLayout right;
         ImageView left_sculpture;
         ImageView right_sculpture;

         ViewHolder(View rootView) {
            this.rootView = rootView;
            this.text_left =  rootView.findViewById(R.id.text_left);
            this.left =  rootView.findViewById(R.id.left);
            this.text_right =  rootView.findViewById(R.id.text_right);
            this.right =  rootView.findViewById(R.id.right);
            this.left_sculpture =  rootView.findViewById(R.id.left_sculpture);
            this.right_sculpture =  rootView.findViewById(R.id.right_sculpture);
        }
    }

}
