package com.hand.activiti.controllers;

import com.hand.activiti.dto.User;
import com.hand.activiti.service.IUserService;
import org.activiti.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by HJW on 2017/7/23 0023.
 */
@Controller
@RequestMapping("/login")
public class LoginCtr {

    @Autowired
    IUserService service;


    @RequestMapping(value = "/userLogin",method = {RequestMethod.POST,RequestMethod.GET})
    public void userLogin(User u, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        User user = service.selectByName(u.getUsername());

        if (user!= null){

            HttpSession session =  req.getSession();
            session.setAttribute("username",user.getUsername());

            if (user.getPassword().equals(u.getPassword())){
                if (u.getUsername().equals("admin")){
                    resp.sendRedirect("/view/adminMain.html");
                }else {
                    resp.sendRedirect("/view/userMain.html");
                }
            }else {
                resp.sendRedirect("/login.html?erro=true");
            }
        }else {
            resp.sendRedirect("/login.html?erro=true");
        }
    }

    @RequestMapping(value = "/userLogOut",method = {RequestMethod.POST,RequestMethod.GET})
    public void userLogOut(HttpServletResponse resp, HttpServletRequest req) throws IOException {

        HttpSession session = req.getSession();
        session.removeAttribute("username");
        resp.sendRedirect("/login.html?logout=true");

    }
}
