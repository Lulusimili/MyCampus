package com.example.administrator.mycampus.Fragmet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mycampus.R;


public class BaseFragment extends Fragment {

    public RecyclerView recyclerView;
    public BaseFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view =inflater.inflate(R.layout.fragment_base, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        return view;
    }

}
