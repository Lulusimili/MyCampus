package com.example.administrator.mycampus.activity;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
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
import com.example.administrator.mycampus.Manifest;
import com.example.administrator.mycampus.R;
import com.example.administrator.mycampus.adapter.FragmentAdapter;
import com.example.administrator.mycampus.bean.Found;
import com.example.administrator.mycampus.bean.Help;
import com.example.administrator.mycampus.bean.Lost;
import com.example.administrator.mycampus.cache.MyCache;
import com.example.administrator.mycampus.util.MyBmob;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
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
    private ImageView photo;
    private Button changeButton;
    private boolean isPhotoNull=true;
    private Intent intent;
    private String imageUrl;
    Toast mToast;


    public static final int CHOOSE_PHOTO = 2;
    private Uri imageUri;
    private String myImagePath="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Bmob.initialize(this, MyBmob.BMOBKEY);
        intent=getIntent();
        objectId=intent.getStringExtra("id");
        initView();
        setTitle();
        setOnClickListener();
        handelFromFragment();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_photo:
                if (ContextCompat.checkSelfPermission(AddActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddActivity.this, new String[]{ android.Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
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


    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        myImagePath=imagePath;
        displayImage(imagePath); // 根据图片路径显示图片
    }


    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /**
     * 获取图片路径
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 显示图片
     * @param imagePath
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            photo.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
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
        photo=(ImageView)findViewById(R.id.photo);

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

        final BmobFile bmobFile=new BmobFile(new File(myImagePath));
        myImagePath="";
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                Lost lost = new Lost();
                lost.setImageUrl(bmobFile.getFileUrl());
                lost.setContent(content);
                lost.setContactWay(contactWay);
                lost.setPublishAccount(publishAccount);
                lost.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            showToast("添加成功啦！");
                            finish();
                        }else {
                            showToast("创建失败了：" + e.getMessage());
                        }
                    }
                });
            }
        });

    }

    /**
     * 添加Found数据
     */
    private void addFound(){
        final BmobFile bmobFile=new BmobFile(new File(myImagePath));
        myImagePath="";
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null) {
                    Found found=new Found();
                    found.setImageUrl(bmobFile.getFileUrl());
                    found.setContent(content);
                    found.setContactWay(contactWay);
                    found.setPublishAccount(publishAccount);
                    found.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                showToast("添加成功啦！");
                                finish();
                            }else {
                                showToast("创建失败了：" + e.getMessage());
                            }
                        }
                    });
                }
                else
                    e.printStackTrace();
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
        if (myImagePath.equals("")){
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
        imageUrl=intent.getStringExtra("imageUrl");
        content = edtContent.getText().toString();
        contactWay= edtContactWay.getText().toString();
        publishAccount= MyCache.getAccount();
        if (imageUrl==null&&intent.getStringExtra("help")==null){
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
        String mPath;
        if (myImagePath=="")
            mPath=intent.getStringExtra("imageUrl");
        else
            mPath=myImagePath;
        final BmobFile bmobFile=new BmobFile(new File(mPath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                Found found=new Found();
                found.setContent(content);
                found.setContactWay(contactWay);
                found.setImageUrl(bmobFile.getFileUrl());
                found.update(objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        showToast("修改成功啦！");
                    }
                });
            }
        });

    }

    /**
     * 修改Lost
     */
    private void changeLost(){
        String mPath;
        if(myImagePath=="")
            mPath=intent.getStringExtra("imageUrl");
        else
            mPath=myImagePath;
        final BmobFile bmobFile =new BmobFile(new File(mPath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                Lost lost=new Lost();
                lost.setContent(content);
                lost.setImageUrl(bmobFile.getFileUrl());
                lost.setContactWay(contactWay);
                lost.update(objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        showToast("修改成功啦！");
                    }
                });
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
                    Log.d("999",intent.getStringExtra("imageUrl"));
                    Glide.with(getApplicationContext())
                            .load(intent.getStringExtra("imageUrl"))
                            .into(photo);
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

                    break;
                case "foundFragment":
                    edtContactWay.setText(intent.getStringExtra("contactWay"));
                    edtContent.setText(intent.getStringExtra("content"));
                    Log.d("999",intent.getStringExtra("imageUrl"));
                    Glide.with(getApplicationContext())
                            .load(intent.getStringExtra("imageUrl"))
                            .into(photo);
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
