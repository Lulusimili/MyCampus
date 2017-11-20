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
import com.example.administrator.mycampus.adapter.LostAdapter;
import com.example.administrator.mycampus.bean.Found;
import com.example.administrator.mycampus.bean.Lost;
import com.example.administrator.mycampus.cache.MyCache;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.example.administrator.mycampus.util.MyUtils.showToast;


public class LostFragment extends BaseFragment {
    private LostAdapter lostAdapter;
    private List<Lost> lostList=new ArrayList<>();
   // private RecyclerView recyclerView;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.fragment_lost,container,false);
//        recyclerView=view.findViewById(R.id.recycler_view);
//
//        queryLost();
//        Log.d("Lost","onCreate");
//        return view;
//    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       // recyclerView=view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        queryLost();
        lostAdapter=new LostAdapter(lostList,getContext());
        int a=lostList.size();
        recyclerView.setAdapter(lostAdapter);
        goToDetails();
    }

    private void queryLost(){
        BmobQuery<Lost> query = new BmobQuery<>();
        query.order("-createdAt")
                .findObjects(new FindListener<Lost>() {
                    @Override
                    public void done(List<Lost> object, BmobException e) {
                        if (e == null) {
//                            showToast("获取数据成功！");
                            lostList=object;
                            int a=lostList.size();
                        } else {
//                            showToast("数据获取失败"+e.getMessage());
                        }
                    }
                });
    }
    private void goToDetails(){
        lostAdapter.setOnItemClickListener(new LostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getContext(), AddActivity.class);
                intent.putExtra("from","lostFragment");
                intent.putExtra("imageUrl",lostList.get(position).getImageUrl());
                intent.putExtra("content",lostList.get(position).getContent());
                intent.putExtra("contactWay",lostList.get(position).getContactWay());
                intent.putExtra("publishAccount",lostList.get(position).getPublishAccount());
                intent.putExtra("id",lostList.get(position).getObjectId());
                startActivity(intent);
            }

            @Override
            public void onClick(View view, int position) {
                    Intent intent = new Intent(getContext(), MessageActivity.class);
                    intent.putExtra("receiveId", lostList.get(position).getPublishAccount());
                    intent.putExtra("myAccount", MyCache.getAccount());
                    startActivity(intent);

            }
        });
    }
}
