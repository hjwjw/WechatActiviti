package com.hand.activiti.dto;

import javax.xml.ws.WebEndpoint;

public class WechatUser {
    private Integer id;

    private String wechatId;

    private String userName;

    private Integer userType;

    public WechatUser(){
        super();
    }

    public WechatUser(String wechatId,String userName){
        this.setWechatId(wechatId);
        this.setUserName(userName);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId == null ? null : wechatId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}