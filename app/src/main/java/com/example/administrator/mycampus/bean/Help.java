package com.example.administrator.mycampus.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class Help extends BmobObject{
    private String content;
    private String contactWay;
    private String publishAccount;
    private String reward;

    public String getPublishAccount() {
        return publishAccount;
    }

    public String getContactWay() {
        return contactWay;
    }

    public String getContent() {
        return content;
    }

    public String getReward() {
        return reward;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public void setPublishAccount(String publishAccount) {
        this.publishAccount = publishAccount;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
}
