package com.hand.activiti.service.impl;

import com.hand.activiti.dto.ProcessInstanceCustom;
import com.hand.activiti.service.IProcessService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
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
public class ProcessServiceImpl implements IProcessService {
    @Override
    public void processStart(String pdkey,String applicant,String assingnee) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("applicant",applicant);//发起人为当前用户
        variables.put("user", assingnee);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceByKey(pdkey,variables);
    }

    @Override
    public List<ProcessInstanceCustom> queryProcessDeploy() {
        List<ProcessInstanceCustom> picList = new ArrayList<ProcessInstanceCustom>();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessDefinition> processDefinition = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .list();
        for (ProcessDefinition p: processDefinition) {
            ProcessInstanceCustom pic = new ProcessInstanceCustom();
            pic.setpId(p.getId());
            pic.setName(p.getName());
            pic.setKey(p.getKey());
            pic.setVersion(p.getVersion());
            pic.setDeploymentId(p.getDeploymentId());
            pic.setDescription(p.getDescription());

            picList.add(pic);
        }
        return picList;
    }

    @Override
    public void delDelpoy(String depId) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .deleteDeployment(depId,true);
    }
}
