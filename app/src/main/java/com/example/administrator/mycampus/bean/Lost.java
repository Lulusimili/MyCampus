package com.example.administrator.mycampus.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class Lost extends BmobObject {

    private String content;
    private String contactWay;
    private String imageUrl;
    private String publishAccount;
    private String reward;


    public void setContent(String contentWay) {
        this.content = contentWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public void setContentWay(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setPublishAccount(String publishAccount) {
        this.publishAccount = publishAccount;
    }


    public String getContent() {
        return content;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishAccount() {
        return publishAccount;
    }

    public String getReward() {
        return reward;
    }


}
