package com.hand.activiti.controllers;

import com.hand.activiti.dto.UserTask;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
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

    //用户启动任务
    @RequestMapping("/userTaskSet")
    @ResponseBody
    public String userTaskSet( String pdkey,HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session =req.getSession();
        Map<String, Object> variables = new HashMap<String, Object>();
        if (session.getAttribute("username")==null){
            resp.sendRedirect("view/login.html");
            return "error";
        }
        variables.put("applicant",session.getAttribute("user"));//session中得到
        variables.put("user", "admin");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceByKey(pdkey,variables);
        return "success";
    }

    //查询当前用户当前任务
    @RequestMapping("/userTaskQuery")
    @ResponseBody
    public List<UserTask> userTaskQuery( HttpServletRequest req, HttpServletResponse resp){
        HttpSession session =req.getSession();
        String username = (String)session.getAttribute("username");
        List<UserTask> userTaskList = new ArrayList<UserTask>();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(username)
                .list();
        UserTask u  = null;
        for (Task t:taskList
             ) {
            u  = new UserTask();
            u.setTaskId(t.getId());
            u.setAssignee(t.getAssignee());
            u.setTaskName(t.getName());
            u.setCreateTime(t.getCreateTime());
            u.setDescription(t.getDescription());
            u.setProcInstId(t.getProcessInstanceId());
            u.setProcDefId(t.getProcessDefinitionId());
            u.setExecutionId(t.getExecutionId());
            userTaskList.add(u);
        }
        return userTaskList;
    }
    //根据ID查询任务
    @RequestMapping("/taskQueryById")
    @ResponseBody
    public UserTask taskQueryById(String taskId,HttpServletRequest req, HttpServletResponse resp){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Task t = processEngine.getTaskService()
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();
            UserTask u = new UserTask();
            u.setTaskId(t.getId());
            u.setAssignee(t.getAssignee());
            u.setTaskName(t.getName());
            u.setCreateTime(t.getCreateTime());
            u.setDescription(t.getDescription());
            u.setProcInstId(t.getProcessInstanceId());
            u.setProcDefId(t.getProcessDefinitionId());
            u.setExecutionId(t.getExecutionId());

        return u;
    }

    //查询当前正在执行的所有任务
    @RequestMapping("/taskQuery")
    @ResponseBody
    public List<UserTask> TaskQuery(HttpServletRequest req, HttpServletResponse resp){
        List<UserTask> userTaskList = new ArrayList<UserTask>();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .list();
        for (Task t: taskList) {
            UserTask u = new UserTask();
            u.setTaskId(t.getId());
            u.setAssignee(t.getAssignee());
            u.setTaskName(t.getName());
            u.setCreateTime(t.getCreateTime());
            u.setDescription(t.getDescription());
            u.setProcInstId(t.getProcessInstanceId());
            u.setProcDefId(t.getProcessDefinitionId());
            u.setExecutionId(t.getExecutionId());
            userTaskList.add(u);
        }
        return userTaskList;
    }

    //完成任务
    @RequestMapping("/finishTask")
    public void  finishTask( String taskId, String executionId ,String send, String opinion, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("send", send);
        variables.put("opinion", opinion);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete(taskId,variables);
        /*
        * 发送继续执行流程信号
        *
        * */
        processEngine.getRuntimeService()
                .signal(executionId);
        //executionID="4901"是为了确定流程实例的
        resp.sendRedirect("view/adminMain.html");
    }


}
