package com.example.administrator.mycampus.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.mycampus.Fragmet.FoundFragment;
import com.example.administrator.mycampus.Fragmet.LostFragment;
import com.example.administrator.mycampus.R;
import com.example.administrator.mycampus.adapter.FragmentAdapter;
import com.example.administrator.mycampus.bean.Found;
import com.example.administrator.mycampus.bean.Help;
import com.example.administrator.mycampus.bean.Lost;
import com.example.administrator.mycampus.cache.MyCache;
import com.example.administrator.mycampus.util.MyBmob;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView photo;
    private Button addButton;
    private EditText edtContent;
    private EditText edtContactWay;
    private Button commitButton;
    private Button backButton;
    private ImageButton rewardButton;
    private LinearLayout rewardLayout;
    private TextView titleText;
    private String currentPager;
    private LinearLayout addPhotoLayout;
    private String content;
    private String contactWay;
    private String publishAccount;
    private String from;
    private String objectId;
    private Button changeButton;
    private boolean isPhotoNull=true;
    private Intent intent;
    private String imageUrl;
    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Bmob.initialize(this, MyBmob.BMOBKEY);
        intent=getIntent();
        initView();
        setTitle();
        setOnClickListener();
        handelFromFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_photo:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.commit:
                addByType();
                break;
            case R.id.reward_button:
                showToast("打赏功能尚未开通哦！");
                break;
            case R.id.change_button:
                changeByType();
                break;
            default:
                break;
        }
    }

    private void setOnClickListener(){
        backButton.setOnClickListener(this);
        commitButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        rewardButton.setOnClickListener(this);
        changeButton.setOnClickListener(this);
    }

    private void initView(){
        photo=(ImageView)findViewById(R.id.photo);
        addButton=(Button)findViewById(R.id.add_photo);
        edtContent=(EditText)findViewById(R.id.edt_content);
        edtContactWay=(EditText)findViewById(R.id.edt_contact_way);
        backButton=(Button)findViewById(R.id.back);
        commitButton=(Button)findViewById(R.id.commit);
        rewardButton=(ImageButton)findViewById(R.id.reward_button);
        rewardLayout=(LinearLayout)findViewById(R.id.reward_layout);
        titleText=(TextView)findViewById(R.id.title_text);
        addPhotoLayout=(LinearLayout)findViewById(R.id.add_photo_layout);
        changeButton=(Button)findViewById(R.id.change_button);

    }

    /**
     * 初始化标题
     */
    private void setTitle(){
        if(intent.getStringExtra("currentPager")!=null) {
            changeButton.setVisibility(View.GONE);
            currentPager=intent.getStringExtra("currentPager");
            if (currentPager.equals("lost")) {
                rewardLayout.setVisibility(View.GONE);
                titleText.setText("添加失物信息");
            }
            if (currentPager.equals("help")) {
                addPhotoLayout.setVisibility(View.GONE);
                titleText.setText("找人帮忙");

            }
            if (currentPager.equals("found")) {
                titleText.setText("添加招领信息");
            }
        }
        if(intent.getStringExtra("from")!=null){
            titleText.setText("详情");
        }
    }

    /**
     * 添加Lost数据
     */
    private void addLost(){
        Lost lost = new Lost();
        lost.setContent(content);
        lost.setContactWay(contactWay);
        lost.setPublishAccount(publishAccount);
        //lost.setPhoto();
        lost.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    showToast("添加成功啦！");
                    setResult(RESULT_OK);
                    finish();
                }else {
                    showToast("创建失败了：" + e.getMessage());
                }
            }
        });
    }

    /**
     * 添加Found数据
     */
    private void addFound(){
        Found found=new Found();
        found.setContent(content);
        found.setContactWay(contactWay);
        found.setPublishAccount(publishAccount);
        //found.setPhoto();
        found.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    showToast("添加成功啦！");
                    setResult(RESULT_OK);
                    finish();
                }else {
                    showToast("创建失败了：" + e.getMessage());
                }
            }
        });
    }

    /**
     * 添加Help数据
     */
    private void addHelp(){
        Help help=new Help();
        help.setContent(content);
        help.setContactWay(contactWay);
        help.setPublishAccount(publishAccount);
        help.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    showToast("添加成功啦！");
                    setResult(RESULT_OK);
                    finish();
                }else {
                    showToast("创建失败了：" + e.getMessage());
                }
            }
        });
    }
    /**
     * 根据类型添加数据
     */
    private void addByType(){
        content = edtContent.getText().toString();
        contactWay= edtContactWay.getText().toString();
        publishAccount= MyCache.getAccount();
        if (isPhotoNull){
            showToast("还有照片呢？");
            return;
        }
        if(TextUtils.isEmpty(content)){
            showToast("要填写描述的哟！");
            return;
        }
        if(TextUtils.isEmpty(contactWay)){
            showToast("填一下写联系方式嘛！");
            return;
        }
        if(currentPager.equals("lost")){
            addLost();
            return;
        }
        else if(currentPager.equals("found")){
            addFound();
            return;
        }
        else if (currentPager.equals("help")){
            addHelp();
            return;
        }
    }

    /**
     * 根据类型修改数据
     */
    private void changeByType(){
        content = edtContent.getText().toString();
        contactWay= edtContactWay.getText().toString();
        publishAccount= MyCache.getAccount();
        if (isPhotoNull){
            showToast("还有照片呢？");
            return;
        }
        if(TextUtils.isEmpty(content)){
            showToast("要填写描述的哟！");
            return;
        }
        if(TextUtils.isEmpty(contactWay)){
            showToast("填一下写联系方式嘛！");
            return;
        }
        if(from.equals("lost")){
            changeLost();
            return;
        }
        else if(from.equals("found")){
            changeFound();
            return;
        }
        else if (from.equals("help")){
            changeHelp();
            return;
        }

    }

    /**
     * 修改Found
     */
    private void changeFound(){
        Found found=new Found();
        found.setContent(content);
        found.setContactWay(contactWay);
        found.setImageUrl(imageUrl);
        found.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                showToast("修改成功啦！");
            }
        });
    }

    /**
     * 修改Lost
     */
    private void changeLost(){
        Lost lost=new Lost();
        lost.setContent(content);
        lost.setImageUrl(imageUrl);
        lost.setContactWay(contactWay);
        lost.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                showToast("修改成功啦！");
            }
        });

    }

    /**
     * 修改Help
     */
    private void changeHelp(){
        Help help=new Help();
        help.setContactWay(contactWay);
        help.setContent(content);
        help.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                showToast("修改成功啦！");
            }
        });

    }

    private void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }
    private void handelFromFragment(){
        if(intent.getStringExtra("from")==null)
            return;
        try {
            commitButton.setVisibility(View.GONE);
            from =intent.getStringExtra("from");
            switch (from){
                case "lostFragment":
                    if (MyCache.getAccount().equals(intent.getStringExtra("publishAccount"))){
                        rewardButton.setVisibility(View.GONE);
                        edtContactWay.setSelection(contactWay.length());
                        edtContent.setSelection(content.length());
                    }
                    else {
                        changeButton.setVisibility(View.GONE);
                        addButton.setVisibility(View.GONE);
                        edtContent.setFocusable(false);
                        edtContactWay.setFocusable(false);
                        edtContent.setFocusableInTouchMode(false);
                        edtContactWay.setFocusableInTouchMode(false);
                    }
                    edtContactWay.setText(intent.getStringExtra("contactWay"));
                    edtContent.setText(intent.getStringExtra("content"));
                    Glide.with(getApplicationContext())
                            .load(intent.getStringExtra("imageUrl"))
                            .into(photo);
                    break;
                case "foundFragment":
                    edtContactWay.setText(intent.getStringExtra("contactWay"));
                    edtContent.setText(intent.getStringExtra("content"));
                    if (MyCache.getAccount().equals(intent.getStringExtra("publishAccount"))){
                        rewardButton.setVisibility(View.GONE);
                        edtContactWay.setSelection(contactWay.length());
                        edtContent.setSelection(content.length());
                    }
                    else {
                        changeButton.setVisibility(View.GONE);
                        addButton.setVisibility(View.GONE);
                        edtContent.setFocusable(false);
                        edtContactWay.setFocusable(false);
                        edtContent.setFocusableInTouchMode(false);
                        edtContactWay.setFocusableInTouchMode(false);
                    }
                    Glide.with(getApplicationContext())
                            .load(intent.getStringExtra("imageUrl"))
                            .into(photo);
                    break;
                case "helpFragment":
                    edtContactWay.setText(intent.getStringExtra("contactWay"));
                    edtContent.setText(intent.getStringExtra("content"));
                    addPhotoLayout.setVisibility(View.GONE);
                    if (MyCache.getAccount().equals(intent.getStringExtra("publishAccount"))){
                        rewardButton.setVisibility(View.GONE);
                        edtContactWay.setSelection(contactWay.length());
                        edtContent.setSelection(content.length());
                    }
                    else {
                        changeButton.setVisibility(View.GONE);
                        edtContent.setFocusable(false);
                        edtContactWay.setFocusable(false);
                        edtContent.setFocusableInTouchMode(false);
                        edtContactWay.setFocusableInTouchMode(false);
                    }
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
