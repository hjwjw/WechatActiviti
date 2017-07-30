package com.hand.activiti.controllers;

import com.hand.activiti.dto.UserTask;
import com.hand.activiti.service.IUserTaskService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by HJW on 2017/7/23 0023.
 */
@Controller
public class UserTaskCtr {

    @Autowired
    IUserTaskService iUserTaskService;


    //查询当前用户当前任务
    @RequestMapping("/userTaskQuery")
    @ResponseBody
    public List<UserTask> userTaskQuery( HttpServletRequest req, HttpServletResponse resp){
        HttpSession session =req.getSession();
        String username = (String)session.getAttribute("username");
        return iUserTaskService.getUserTaskList(username);
    }
    //根据ID查询任务
    @RequestMapping("/taskQueryById")
    @ResponseBody
    public UserTask taskQueryById(String taskId,HttpServletRequest req, HttpServletResponse resp){
        return iUserTaskService.getUserTaskById(taskId);
    }

    //查询当前正在执行的所有任务
    @RequestMapping("/taskQuery")
    @ResponseBody
    public List<UserTask> TaskQuery(HttpServletRequest req, HttpServletResponse resp){

        return iUserTaskService.getAllRuningTask();
    }

    //完成任务
    @RequestMapping("/finishTask")
    public void  finishTask( String taskId, String executionId ,String send, String opinion, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        iUserTaskService.finishuserTask(taskId,executionId,send,opinion);
        //executionID="4901"是为了确定流程实例的
        resp.sendRedirect("view/adminMain.html");
    }


}
