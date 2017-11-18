package com.example.administrator.mycampus.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.mycampus.R;
import com.example.administrator.mycampus.cache.MyCache;
import com.example.administrator.mycampus.util.MyBmob;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import cn.bmob.v3.Bmob;

public class LoginActivity extends AppCompatActivity  {
    private Button loginButton;
    private EditText account;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, MyBmob.BMOBKEY);
        NIMClient.init(getApplicationContext(), null, null);
        initView();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    private void initView(){
        loginButton=(Button)findViewById(R.id.login_button);
        password=(EditText)findViewById(R.id.password);
        account=(EditText)findViewById(R.id.account);
    }
    private void login() {
        LoginInfo info = new LoginInfo(account.getText().toString().toLowerCase(), password.getText().toString()); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        MyCache.setAccount(account.getText().toString().toLowerCase());
                        save(account.getText().toString().toLowerCase(),password.getText().toString().toLowerCase());
                        startActivity(new Intent(LoginActivity.this, XMBActivity.class));
                        finish();
                        Toast.makeText(LoginActivity.this,"登录成功!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(int i) {
                        Toast.makeText(LoginActivity.this,"登录失败,不告诉你为什么!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Toast.makeText(LoginActivity.this,"登录失败,就不告诉你为什么,buibui",Toast.LENGTH_SHORT).show();

                    }

                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }
    private void save(String account,String password){
        SharedPreferences.Editor editor=getSharedPreferences("uData",MODE_PRIVATE).edit();
        editor.putString("account",account);
        editor.putString("password",password);
        editor.apply();
    }
}
