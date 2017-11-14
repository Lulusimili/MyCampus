package com.example.administrator.mycampus.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class Lost extends BmobObject {

    private String content;
    private String contactWay;
    private BmobFile photo;
    private String publishAccount;
    private String reward;


    public void setContent(String contentWay) {
        this.content = contentWay;
    }

    public void setContact(String contactWay) {
        this.contactWay = contactWay;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public void setContentWay(String content) {
        this.content = content;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }

    public void setPublishAccount(String publishAccount) {
        this.publishAccount = publishAccount;
    }


    public String getContentWay() {
        return content;
    }

    public String getContact() {
        return contactWay;
    }

    public BmobFile getPhoto() {
        return photo;
    }

    public String getPublishAccount() {
        return publishAccount;
    }

    public String getReward() {
        return reward;
    }
}
