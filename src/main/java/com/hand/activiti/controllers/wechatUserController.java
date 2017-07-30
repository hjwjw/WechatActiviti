package com.hand.activiti.controllers;

import com.hand.activiti.dto.WechatUser;
import com.hand.activiti.service.IWechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by HJW on 2017/7/29 0029.
 */
@Controller
public class wechatUserController {

    @Autowired
    IWechatUserService iWechatUserService;

    @RequestMapping("/getAssingneeList")
    @ResponseBody
    public List<WechatUser> getAssingneeList(){
        return iWechatUserService.getWechatUserList();
    }
}
