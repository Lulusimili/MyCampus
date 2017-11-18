package com.example.administrator.mycampus.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;
import com.example.administrator.mycampus.Fragmet.FoundFragment;
import com.example.administrator.mycampus.Fragmet.HelpFragment;
import com.example.administrator.mycampus.Fragmet.LostFragment;
import com.example.administrator.mycampus.R;
import com.example.administrator.mycampus.adapter.FragmentAdapter;
import com.example.administrator.mycampus.util.MyBmob;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;

public class XMBActivity extends FragmentActivity implements View.OnClickListener {
    private String currentPager;
    private ViewPager viewPager;
    private PagerSlidingTabStrip tab;
    private FragmentAdapter fragmentAdapter ;
    private Intent intent;
    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmb);
        Bmob.initialize(this, MyBmob.BMOBKEY);
        initView();
        viewPager.setAdapter(fragmentAdapter);
        tab.setViewPager(viewPager);
        addButton.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.add_button){
            if (viewPager.getCurrentItem()==0){
                currentPager="lost";
                intent=new Intent(XMBActivity.this,AddActivity.class);
                intent.putExtra("currentPager",currentPager);
                startActivity(intent);

            }
            else if(viewPager.getCurrentItem()==1){
                currentPager="found";
                intent=new Intent(XMBActivity.this,AddActivity.class);
                intent.putExtra("currentPager",currentPager);
                startActivity(intent);
            }
            else if(viewPager.getCurrentItem()==2){
                currentPager = "help";
                intent=new Intent(XMBActivity.this,AddActivity.class);
                intent.putExtra("currentPager", currentPager);
                startActivity(intent);
            }
        }
    }

    private void initView(){
        addButton=findViewById(R.id.add_button);
        viewPager=findViewById(R.id.view_pager);
        tab=findViewById(R.id.tab);
        fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());
    }

}
