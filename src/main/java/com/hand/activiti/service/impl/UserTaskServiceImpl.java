package com.hand.activiti.service.impl;

import com.hand.activiti.dto.UserTask;
import com.hand.activiti.service.IUserTaskService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HJW on 2017/7/29 0029.
 */
@Service
public class UserTaskServiceImpl implements IUserTaskService {
    @Override
    public List<UserTask> getUserTaskList(String username) {
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
            u.setApplicant(t.getOwner());
            u.setApplicant((String) processEngine.getTaskService().getVariable(t.getId(),"applicant"));
            userTaskList.add(u);
        }
        return userTaskList;
    }

    @Override
    public List<UserTask> getAllRuningTask() {
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

    @Override
    public UserTask getUserTaskById(String taskId) {
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

    @Override
    public void finishuserTask(String taskId, String executionId, String content,String opinion) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("send", content);
        variables.put("opinion", opinion);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete(taskId,variables);
        //发送继续执行流程信号
        processEngine.getRuntimeService()
                .signal(executionId);
    }

    @Override
    public List<Task> backTaskList() {
        return null;
    }
}
