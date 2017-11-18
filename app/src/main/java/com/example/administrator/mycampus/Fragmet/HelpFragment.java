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
import com.example.administrator.mycampus.adapter.HelpAdapter;
import com.example.administrator.mycampus.bean.Found;
import com.example.administrator.mycampus.bean.Help;
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

public class HelpFragment extends Fragment {
    private HelpAdapter helpAdapter;
    private List<Help> helpList=new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_help,container,false);
        recyclerView=view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        helpAdapter=new HelpAdapter(helpList,getContext());
        recyclerView.setAdapter(helpAdapter);
        Log.d("Help","onCreate");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        queryHelp();
        goToDetails();
        Log.d("Help","onViewCreated");
    }
    private void queryHelp(){
        BmobQuery<Help> query = new BmobQuery<>();
        query.order("-createdAt")
                .findObjects(new FindListener<Help>() {
                    @Override
                    public void done(List<Help> object, BmobException e) {
                        if (e == null) {
                            helpList=object;
                        } else {
                        }
                    }
                });
    }
    private void goToDetails(){
        helpAdapter.setOnItemClickListener(new HelpAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getContext(), AddActivity.class);
                intent.putExtra("from","helpFragment");
                intent.putExtra("content",helpList.get(position).getContent());
                intent.putExtra("contactWay",helpList.get(position).getContactWay());
                intent.putExtra("publishAccount",helpList.get(position).getPublishAccount());
                intent.putExtra("id",helpList.get(position).getObjectId());
                startActivity(intent);
            }

            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getContext(), MessageActivity.class);
                intent.putExtra("receiveId",helpList.get(position).getPublishAccount());
                intent.putExtra("myAccount", MyCache.getAccount());
                startActivity(intent);
            }
        });
    }
}
