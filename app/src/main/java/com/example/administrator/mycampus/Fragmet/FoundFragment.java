package com.example.administrator.mycampus.Fragmet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mycampus.R;
import com.example.administrator.mycampus.activity.AddActivity;
import com.example.administrator.mycampus.activity.MessageActivity;
import com.example.administrator.mycampus.adapter.FoundAdapter;
import com.example.administrator.mycampus.bean.Found;
import com.example.administrator.mycampus.cache.MyCache;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.example.administrator.mycampus.util.MyUtils.showToast;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class FoundFragment extends BaseFragment {
    private FoundAdapter foundAdapter;
    private List<Found> foundList=new ArrayList<>();
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //recyclerView=view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        queryFound();
        foundAdapter=new FoundAdapter(foundList,getContext());
        System.out.print("88888哈哈哈哈哈哈哈哈哈哈哈"+foundList.size());
        recyclerView.setAdapter(foundAdapter);
        goToDetails();
    }
    private void queryFound(){
        BmobQuery<Found> query = new BmobQuery<>();
        query.order("-createdAt")
                .findObjects(new FindListener<Found>() {
                    @Override
                    public void done(List<Found> object, BmobException e) {
                        if (e == null) {
                             foundList=object;
                             Log.d("长度",foundList.get(0).getImageUrl());
                        } else {
                            showToast("数据获取失败"+e.getMessage());
                        }
                    }
                });
      }
      private void goToDetails(){
          foundAdapter.setOnItemClickListener(new FoundAdapter.OnItemClickListener() {
              @Override
              public void onItemClick(View view, int position) {
                  Intent intent=new Intent(getContext(), AddActivity.class);
                  intent.putExtra("from","foundFragment");
                  intent.putExtra("imageUrl",foundList.get(position).getImageUrl());
                  intent.putExtra("content",foundList.get(position).getContent());
                  intent.putExtra("contactWay",foundList.get(position).getContactWay());
                  intent.putExtra("publishAccount",foundList.get(position).getPublishAccount());
                  intent.putExtra("id",foundList.get(position).getObjectId());
                  startActivity(intent);
              }

              @Override
              public void onClick(View view, int position) {

                      Intent intent = new Intent(getContext(), MessageActivity.class);
                      intent.putExtra("receiveId", foundList.get(position).getPublishAccount());
                      intent.putExtra("myAccount", MyCache.getAccount());
                      startActivity(intent);


              }
          });
      }
}
