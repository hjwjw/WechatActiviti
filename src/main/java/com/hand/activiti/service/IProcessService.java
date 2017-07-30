package com.hand.activiti.service;

import com.hand.activiti.dto.ProcessInstanceCustom;

import java.util.List;

/**
 * 流程管理类
 * Created by HJW on 2017/7/29 0029.
 */
public interface IProcessService {
    /**
     * 启动流程实例
     * @param pdkey 流程ID
     * @return
     */
    public void processStart(String pdkey,String applicant,String username);

    /**
     * 查询已经布置的流程
     * @return
     */
    public List<ProcessInstanceCustom> queryProcessDeploy();

    /**
     * 删除流程部署
     * @param depId 部署ID
     * @return
     */
    public void delDelpoy( String depId);
}
