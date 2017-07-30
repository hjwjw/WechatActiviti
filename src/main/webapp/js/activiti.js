/**
 * Created by HJW on 2017/7/24 0024.
 */
//已部署的流程列表
function delopyProcessList() {
    $("#panelTitle").text("已部署的流程");
    $("#grid").kendoRemoveClass("panel-body");
    $("#grid").kendoGrid({
        dataSource: {
            transport: {
                read: "/queryProcessDeploy"
            },
            pageSize: 20
        },
        resizable: false,
        batch: false,
        scrollable: true,
        navigatable: false,
        pageable: {
            pageSizes: [5, 10, 20, 50],
            refresh: false,
            buttonCount: 5
        },
        editable: false,
        noRecords: true
    });
    var grid = $("#grid").data("kendoGrid");
    grid.setOptions({
        columns: [{
            field: "pId",
            title: "流程ID"
        },{
            field: "name",
            title: "流程名称"
        }, {
            field: "key",
            title: "流程KEY"
        }, {
            field: "version",
            title: "流程版本"
        }, {
            field: "deploymentId",
            title: "部署ID"
        },{
            field: "description",
            title: "描述"
        },{
            command : [
                {
                    text:"删除",//名称
                    click:function (e) {
                        var tr = $(e.target).closest("tr");
                        var data = this.dataItem(tr);
                        $.ajax({
                            url:"/delDelpoy",
                            type:"post",
                            dataType: "json",
                            data:{
                                depId:data.deploymentId
                            },
                            success:function (data) {
                                if (data =="success"){
                                    //显示正在运行的任务
                                    runtimeTask();
                                }else {
                                    console.log("权限不足");
                                    alert("对不起，权限不足");
                                    delopyProcessList();
                                }
                            },
                            error:function (data) {
                                alert("删除失败");
                                delopyProcessList()
                            }
                        });
                    }
                },
                {
                    text:"启动",//名称
                    click:function (e) {
                        // e.target is the DOM element representing the button
                        var tr = $(e.target).closest("tr"); // get the current table row (tr)
                        // get the data bound to the current table row
                        var rowData = this.dataItem(tr);
                        $.ajax({
                            url:"/getAssingneeList",
                            type:"post",
                            success:function (data) {
                                console.log(data);
                                for (var i =0;i < data.length;i++){
                                    $("#assingnee").append("<option value='"+data[i].wechatId+"'>"+data[i].userName+"</option>");
                                }
                                $("#pdkey").val(rowData.key);
                                $('#SelectAssigneeModal').modal();
                            }
                        });

                        /*$.ajax({
                            url:"/userTaskSet",
                            type:"post",
                            dataType: "json",
                            data:{
                                pdkey:data.key
                            },
                            success:function (data) {
                                if (data =="success"){
                                    //显示正在运行的任务
                                    runtimeTask();
                                }else {
                                    delopyProcessList();
                                }
                            },
                            error:function (data) {
                                alert("启动失败");
                                delopyProcessList()
                            }
                        });*/
                    }
                }
            ],
            title : "操作", //表头名称
            width : "200px" //列宽
        }]
    });
}

//当前用户任务
function userTask(urlType) {
    var url="/userTaskQuery";
    if (urlType =="0"){
        url = "/taskQuery"
    }
    $("#panelTitle").text("我的待办");
    $("#grid").kendoRemoveClass("panel-body");
    $("#grid").kendoGrid({
        dataSource: {
            transport: {
                read: url
            },
            pageSize: 20
        },
        resizable: true,
        batch: true,
        scrollable: true,
        navigatable: false,
        pageable: {
            pageSizes: [5, 10, 20, 50],
            refresh: true,
            buttonCount: 5
        },
        editable: "popup",
        noRecords: true
    });
    var grid = $("#grid").data("kendoGrid");
    grid.setOptions({
        columns: [{
            field: "taskId",
            title: "任务ID"
        }, {
            field: "taskName",
            title: "审批环节"
        }, {
            field: "procDefId",
            title: "流程定义ID"
        },{
            field: "assignee",
            title: "当前处理人   "
        },{
            command : [
                {
                    text:"审批",//名称
                    click:function (e) {
                        // e.target is the DOM element representing the button
                        var tr = $(e.target).closest("tr"); // get the current table row (tr)
                        // get the data bound to the current table row
                        var data = this.dataItem(tr);
                        console.log("事件");
                        console.log(data);
                        $.ajax({
                            url:"/taskQueryById",
                            dataType:"json",
                            data:{
                                taskId:data.taskId
                            },success:function (data) {
                                console.log(data.taskName);
                                $("#taskId").val( data.taskId);
                                $("#taskName").val( data.taskName);
                                $("#executionId").val( data.executionId);
                                console.log(data);
                            },error:function (data) {
                                console.log("错误 ");
                                console.log(data);
                            }
                        });
                        $('#approveModal').modal();
                    }
                }
            ],
            title : "操作", //表头名称
            width : "150px" //列宽
        }]
    });
}

//运行中的任务
function runtimeTask() {
    $("#panelTitle").text("流程监控");
    $("#grid").kendoRemoveClass("panel-body");
    $("#grid").kendoGrid({
        dataSource: {
            transport: {
                read: "/taskQuery"
            },
            pageSize: 20
        },
        resizable: true,
        batch: true,
        scrollable: true,
        navigatable: false,
        noRecords: true,
        pageable: {
            pageSizes: [5, 10, 20, 50],
            refresh: true,
            buttonCount: 5
        },
        editable: false
    });
    var grid = $("#grid").data("kendoGrid");
    grid.setOptions({
        columns: [{
            field: "taskId",
            title: "任务ID"
        }, {
            field: "taskName",
            title: "审批环节"
        }, {
            field: "procDefId",
            title: "流程定义ID"
        },{
            field: "assignee",
            title: "当前处理人"
        }]
    });
}

    //部署流程
    function deploy() {
        $("#panelTitle").text("流程部署");
        var deployTemplate = kendo.template($("#deployTemplate").html());
        $("#grid").html(deployTemplate);
        $("#grid").kendoAddClass("panel-body");
    }
