package com.example.administrator.mycampus.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class Found extends BmobObject {
    private String content;
    private String contactWay;
    private String imageUrl;
    private String publishAccount;


    public void setContent(String content) {
        this.content= content;
    }

    public void setContactWay(String contact) {
        this.contactWay = contact;
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
}
