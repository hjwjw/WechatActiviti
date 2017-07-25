package com.hand.activiti.dto;

import java.awt.print.PrinterAbortException;

/**
 * Created by HJW on 2017/7/24 0024.
 */
public class ProcessInstanceCustom {

    private String pId;

    private String name;

    private  String key;

    private  int version;

    private  String  deploymentId;

    private String description;



    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
