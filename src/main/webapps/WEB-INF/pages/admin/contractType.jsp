<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/pages/tag.jsp"%>


<html>
<head>
    <title>合同类型管理</title>
</head>
<body class="gray-bg">
<div class="col-sm-12 select-table table-striped" style="margin-left: -25px;margin-top: -25px;margin-right: -20px;">
    <div class="bootstrap-table">
        <div class="fixed-table-toolbar">
            <div class="bs-bars pull-left">
                <div class="btn-group-sm" id="toolbar" role="group">
                    <!-- 功能模块 -->
                    <a class="btn btn-success" href="#addContractTypeModal" onclick="" data-toggle="modal">
                        <i class="fa fa-plus"></i> 增加合同类型
                    </a>
                </div>
            </div>
            <div class="columns columns-right btn-group pull-right"></div>
        </div>

        <!-- 表格基本信息展示 区域  -->
        <div class="fixed-table-container">
            <div >
                <table id="bootstrap-table" data-mobile-responsive="true" class="table table-hover">
                    <thead>
                    <tr>
                        <th><div class="th-inner">合同标识id</div><div class="fht-cell"></div></th>
                        <th><div class="th-inner">合同类型</div><div class="fht-cell"></div></th>
                        <th style="text-align:center"><div class="th-inner">操作</div><div class="fht-cell"></div></th>
                    </tr>
                    </thead>
                    <tbody id="contractType_tbody"></tbody>
                </table>
            </div>
            <div class="fixed-table-footer" style="display:none;"></div>
            <!-- 记录条数  -->
            <div class="fixed-table-pagination" style="display:block;">
                <div class="pull-left pagination-detail">
                    <span class="pagination-info">共<span id="crtTypeInfoNum"></span>条记录</span>
                    <span class="page-list" style="display:none;"></span>
                </div>
            </div>
        </div>
    </div>
</div>



<%--录入模态框--%>
<div id="addContractTypeModal" class="modal fade modalAdd">
    <div class="form-content">
        <form id="addContractTypeModalForm" enctype="multipart/form-data" class="form-horizontal">
            <h4 class="form-header h4">合同类别基本信息</h4>
            <!-- 这是一行 -->
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><span style="color: red; ">*</span>合同类别名称：</label>
                        <div class="col-sm-8">
                            <input id="addContractTypeModal_name" name="addContractTypeModal_name" autocomplete="off" placeholder="请输入合同名称" class="form-control" required="required" type="text">
                        </div>
                    </div>
                </div>
                <div class="col-sm-6"></div>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col-sm-offset-5 col-sm-10">
            <button type="button" class="btn btn-sm btn-primary" onclick="addContractTypeSubmit()"><i class="fa fa-check"></i>保 存</button>&nbsp;
            <button type="button" class="btn btn-sm btn-danger" data-dismiss="modal" ><i class="fa fa-reply-all"></i>关 闭 </button>
        </div>
    </div>
</div>



<script type="text/javascript">
    jQuery(document).ready(function(){
        /*显示合同类别*/
        loadContract();
    });

    /*获取所有合同类别*/
    function loadContract(){
        console.log("loadcontract");
        $.ajax({
            url:"${basePath}admin/getContractTypes.action",
            data:{},
            type:"POST",
            dataType:"json",
            success:function(data){
                var str = "";
                if("1" == data.code){
                    var ctrtypes = data.data;
                    var ctrtypesNum = ctrtypes.length;
                    str = getCtrTbStrByList(ctrtypes);
                    $("#contractType_tbody").html(str);
                    console.log("共"+ctrtypesNum+"条记录");
                    $("#crtTypeInfoNum").text(ctrtypesNum);
                }
            }
        });
    }

    /*根据返回值获取展示字符串*/
    function getCtrTbStrByList(list){
        var str = "";
        $.each(list,function(index, value){
            // 拼接整个表格的tbody部分内容为 str
            str+="<tr>"+
                    "<td>"+value.id+"</td>"+
                    "<td>"+value.typename+"</td>"+
                    '<td style="padding:0px;width:150px;">'+
                    '<a class="btn btn-danger btn-xs" href="javascript:void(0)" onclick="delCtrTyp('+"'"+value.id+"'"+",'"+value.typename+"'"+')">'+'<i class="fa fa-remove"></i>'+"删除"+'</a>'+
                    "</tr>";
        });
        //console.log("表格list拼接html.str:\n"+str);
        return str;
    }

    /*删除某个合同类别*/
    function delCtrTyp(id, typename){
        var r = confirm("确定删除 "+ typename+" 吗?");
        if(r == true){
            $.ajax({
                url:"${basePath}admin/delCtrTyp.action",
                data:{
                    "id":id
                },
                dataType:"json",
                type:"post",
                success:function(data){
                    if("1" == data.code){
                        alert("成功删除 "+typename);
                        //获取所有合同
                        var ctrtypes = data.data;
                        var ctrtypesNum = ctrtypes.length;
                        str = getCtrTbStrByList(ctrtypes);
                        $("#contractType_tbody").html(str);
                        console.log("共"+ctrtypesNum+"条记录");
                        $("#crtTypeInfoNum").text(ctrtypesNum);
                    }else{
                        alert("没有删除 "+typename+",请联系管理员");
                    }
                }
            });
        }
    }

    /*录入合同*/
    function addContractTypeSubmit(){
        $('#addContractTypeModal').modal('hide');
        var addContractTypeModal_name = $("#addContractTypeModal_name").val();
        if(judgeBlank(addContractTypeModal_name) == false){
            alert("请输入 合同名称!");
        }else{
            //所有输入非空继续执行 && 勾选的权限符合权限规则
            console.log("录入合同的保存方法addContractTypeSubmit---addContractTypeModal_name:"+addContractTypeModal_name);
            $.ajax({
                url:"${basePath}admin/addCtrTyp.action",
                data:{
                    "typename":addContractTypeModal_name
                },
                dataType:"json",
                type:"post",
                success:function(data){
                    var str = "";
                    if("1" == data.code){
                        var ctrtypes = data.data;
                        var ctrtypesNum = ctrtypes.length;
                        str = getCtrTbStrByList(ctrtypes);
                        $("#contractType_tbody").html(str);
                        console.log("共"+ctrtypesNum+"条记录");
                        $("#crtTypeInfoNum").text(ctrtypesNum);
                    }else if("0" == data.code){
                        alert("合同名称"+typename+"重复,请重新录入");
                    }
                }
            });
        }
    }

</script>
</body>
</html>
