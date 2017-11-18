package com.example.administrator.mycampus.info;

/**
 * Created by luguanchen on 2017/5/13.
 */
//*********************初始化消息******************
public class MsgInfo {

    private String right_text;
    private String left_text;
    private String id;

    public MsgInfo(String left_text, String right_text,String id) {
        this.left_text=left_text;
        this.right_text=right_text;
        this.id=id;

    }

    public String getLeft_text() {                                             //获取接收到的消息
        return left_text;
    }

    public String getRight_text() {                                                //获取发送的消息
        return right_text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}