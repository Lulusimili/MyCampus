package com.example.administrator.mycampus.cache;

import android.content.Context;

import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;


public class MyCache {
    private static Context context;

    private static String account;

    private static StatusBarNotificationConfig config;
    private static LoginInfo loginInfo;
    public static void clear() {
        account = null;
    }
    //获取账号信息
    public static String getAccount() {
        return account;
    }

    public static void setLoginInfo(LoginInfo loginInfo) {
        MyCache.loginInfo = loginInfo;
    }

    //设置账号信息
    public static void setAccount(String account) {
        MyCache.account = account;
    }
    //获取上下文
    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyCache.context = context.getApplicationContext();
    }

    public static StatusBarNotificationConfig getConfig() {
        return config;
    }

    public static LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public static void setConfig(StatusBarNotificationConfig config) {
        MyCache.config = config;
    }
}
