package com.hand.activiti.controllers;

import com.hand.activiti.dto.ProcessInstanceCustom;
import com.hand.activiti.service.IProcessService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * Created by HJW on 2017/7/24 0024.
 */
@Controller
public class activitiCtr {

    @Autowired
    IProcessService iProcessService;



    /**
     * 流程部署
     */
    @RequestMapping("/deploy")
    public void deploy(String deployName,@RequestParam(value = "bpmnFile", required = false) MultipartFile file, HttpServletRequest req, HttpServletResponse resp) throws FileNotFoundException {

        String path = req.getSession().getServletContext().getRealPath("upload/bpmn");
        String fileName = file.getOriginalFilename();
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String bpmnPath = "upload/bpmn/"+fileName;
        System.out.println(targetFile.getPath());

        InputStream inputStream = new FileInputStream(targetFile.getPath());
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addZipInputStream(zipInputStream)
                .name(deployName)
                .deploy();
        try {
            resp.sendRedirect("view/adminMain.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //用户启动流程
    @RequestMapping("/userTaskSet")
    @ResponseBody
    public String userTaskSet( String pdkey,String assingnee,HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session =req.getSession();
        String applicant = (String) session.getAttribute("username");
        iProcessService.processStart(pdkey,applicant,assingnee);
        resp.sendRedirect("view/adminMain.html");
        return "success";
    }

    /**
     * 查询已经布置的流程
     * @return
     */
    @RequestMapping("/queryProcessDeploy")
    @ResponseBody
    public List<ProcessInstanceCustom> queryProcessDeploy(){
        return iProcessService.queryProcessDeploy();
    }

    //删除部署
    @RequestMapping("/delDelpoy")
    @ResponseBody
    public String delDelpoy( String depId,HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        if (!username.equals("admin")){
            return "erro";
        }
        iProcessService.delDelpoy(depId);
        return "success";
    }
}
