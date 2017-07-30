package com.hand.activiti.utils;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HJW on 2017/7/23 0023.
 */
public class PDManager {

    //流程部署
    @Test
    public void testDeploy_Classpath(){
        //得到一个默认的流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("WeChat.bpmn")
                .addClasspathResource("WeChat.png")
                .deploy();
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }

    /**
     * 查询已经部署的流程
     */
    @Test
    public void queryProcessDeploy(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessDefinition> processDefinition = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .list();
        for (ProcessDefinition p: processDefinition) {
            System.out.println(p.getId());
            System.out.println(p.getKey());
        }
    }
    /**
     * 启动实例
     */
    @Test
    public void testStartProcessInstance(){
        //pdkey是流程实例名
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("user", "user");//session中得到
        String pdkey = "weChat";//传过来
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceByKey(pdkey,variables);

    }

    /**
     * 查找用户的任务
     */
    @Test
    public void testUserTask(){
        String userName = "user"; //传入
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(userName).list();
        if (tasks.size()==0){
            System.out.println("没有任务");
        }
        for (Task t: tasks) {
            System.out.println(t.getId());
            System.out.println(t.getName());
        }
    }
    /**
     * user 在完成任务的时候，给下一个节点的流程变量的名称赋值.admin执行任务
     */
    @Test
    public void testFinishTask(){
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("user", "admin");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("22505",variables);
    }
    /**
     * admin 在完成任务的时候，给下一个节点的 send 是否为false.流程进入receiveTask。等 待接收活动信号
     */
    @Test
    public void testAdminFinishTask(){
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("send", "true");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("25002",variables);
    }

    //让ReceiveTask接收信息继续执行
    @Test
    public void testExecutionNext(){
        /*
        * 这里执行发送动作
        *
        * */
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .signal("107505");//executionID="4901"是为了确定流程实例的
    }

    /**
     * 判断当前的流程实例已经结束
     *    根据piid查询流程实例，如果查询出来的结果为null，流程实例已经结束了
     *                          如果不为null,流程实例没有结束
     */
    @Test
    public void testIsEndPI(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance pi = processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId("130001")
                .singleResult();
        System.out.println(pi);
    }

    @Test
    public void testDelete(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    //		processEngine.getRepositoryService()
    //		.deleteDeployment("1");//用该API只能删除流程定义的内容和部署的内容
        processEngine.getRepositoryService()
                .deleteDeployment("55001", true);//删除了关于deploymentID为1的所有的数据，包括：流程定义、流程部署、任务等信息
    }
}
