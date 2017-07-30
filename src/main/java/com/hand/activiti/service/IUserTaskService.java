package com.hand.activiti.service;

import com.hand.activiti.dto.UserTask;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * 任务管理类
 * Created by HJW on 2017/7/29 0029.
 */
public interface IUserTaskService {



    /**
     * 获得用户待办事项列表
     * @param username
     * @return
     */
    public List<UserTask> getUserTaskList(String username);


    /**
     * 查询所有任务列表
     * @return
     */
    public List<UserTask> getAllRuningTask();

    /**
     * 根据任务ID查询用户任务
     * @param taskId
     * @return
     */
    public UserTask getUserTaskById(String taskId);

    /**
     * 审核任务
     * @param taskId 任务ID
     * @param executionId 流程实例ID
     * @param content 说明信息
     */
    public void finishuserTask(String taskId,String executionId ,String content,String opinion);

    public List<Task> backTaskList();

}
