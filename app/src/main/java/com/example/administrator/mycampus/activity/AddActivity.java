package com.example.administrator.mycampus.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.mycampus.R;

public class AddActivity extends AppCompatActivity {
    private ImageView photo;
    private Button addButton;
    private EditText edtContent;
    private EditText edtContactWay;
    private Button commitButton;
    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
    }
    private void initView(){
        photo=(ImageView)findViewById(R.id.photo);
        addButton=(Button)findViewById(R.id.add_photo);
        edtContent=(EditText)findViewById(R.id.edt_content);
        edtContactWay=(EditText)findViewById(R.id.edt_contact_way);
        backButton=(Button)findViewById(R.id.back);
        commitButton=(Button)findViewById(R.id.commit);
    }

}
