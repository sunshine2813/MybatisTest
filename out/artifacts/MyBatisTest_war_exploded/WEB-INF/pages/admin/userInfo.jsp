<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/pages/tag.jsp"%>



<html>
<head>
    <title></title>
</head>
<body class="gray-bg">
  <div class="col-sm-12 select-table table-striped" style="margin-left: -25px;margin-top: -25px;margin-right: -20px;">
    <div class="bootstrap-table">
      <div class="fixed-table-toolbar">
        <div class="bs-bars pull-left">
          <div class="btn-group-sm" id="toolbar" role="group">
            <!-- 功能模块 -->
            <a class="btn btn-success" href="#addUserModal" onclick="" data-toggle="modal">
              <i class="fa fa-plus"></i> 增加用户
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
                <!-- <th  style="width:36px;"><input type='checkbox' name="voucherListch"/></th> -->
                <th><div class="th-inner">用户ID</div><div class="fht-cell"></div></th>
                <th><div class="th-inner">用户代码</div><div class="fht-cell"></div></th>
                <th><div class="th-inner">姓名</div><div class="fht-cell"></div></th>
                <th><div class="th-inner">Hashed密码</div><div class="fht-cell"></div></th>
                <th style="text-align:center"><div class="th-inner">操作</div><div class="fht-cell"></div></th>
              </tr>
              </thead>
              <tbody id="userInfo_tbody"></tbody>
            </table>
          </div>
          <div class="fixed-table-footer" style="display:none;"></div>
          <!-- 记录条数  -->
          <div class="fixed-table-pagination" style="display:block;">
            <div class="pull-left pagination-detail">
              <span class="pagination-info">共<span id="userInfoNum"></span>条记录</span>
              <span class="page-list" style="display:none;"></span>
            </div>
          </div>
        </div>
      </div>
    </div>



  <%--录入用户的模态框--%>
  <div id="addUserModal" class="modal fade modalAdd">
      <div class="form-content">
          <form id="addUserModalForm" enctype="multipart/form-data" class="form-horizontal">
              <h4 class="form-header h4">用户基本信息</h4>
              <!-- 这是一行 -->
              <div class="row">
                  <div class="col-sm-4">
                      <div class="form-group">
                          <label class="col-sm-4 control-label"><span style="color: red; ">*</span>用户姓名首字母：</label>
                          <div class="col-sm-8">
                              <input id="add_usercode" name="addContractModal_name" autocomplete="off" placeholder="请输入用户代码" class="form-control" required="required" type="text">
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-4">
                      <div class="form-group">
                          <label class="col-sm-4 control-label"><span style="color: red; ">*</span>用户真实姓名：</label>
                          <div class="col-sm-8">
                              <input id="add_username" name="addContractModal_number" autocomplete="off" placeholder="请输入用户姓名" class="form-control" required="required" type="text">
                          </div>
                      </div>
                  </div>

              </div>
              <!-- 这是一行 -->
              <div class="row">
                  <div class="col-sm-4">
                      <div class="form-group">
                          <label class="col-sm-4 control-label"><span style="color: red; ">*</span>用户明文密码：</label>
                          <div class="col-sm-8">
                              <input id="add_password" name="addContractModal_number" autocomplete="off" placeholder="请输入用户的明文密码" class="form-control" required="required" type="text">
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-4">
                      <%--<div class="form-group">
                          <label class="col-sm-4 control-label"><span style="color: red; ">*</span>用户角色：</label>
                          <div class="col-sm-8">
                              <div class="input-group" style="width: 100%">
                                  <select id="add_role" name="add_role" class="form-control" >
                                      <option value="主任">主任</option>
                                      <option value="财务科科长">财务科科长</option>
                                      <option value="业务科普通职员">业务科普通职员</option>
                                      <option value="业务科科长">业务科科长</option>
                                      <option value="财务科普通职员">财务科普通职员</option>
                                      <option value="其他科室">其他科室</option>
                                  </select>
                              </div>
                          </div>
                      </div>--%>
                  </div>
                  <div class="col-sm-4"></div>
              </div>
              <!-- 这是一行 -->
              <div class="row">
                  <div class="col-sm-4">
                      <div class="form-group">
                          <label class="col-sm-4 control-label"><span style="color: red; ">*</span>凭单权限：</label>
                          <div class="col-sm-8">
                              <input type="checkbox" id="VQ" value="查询" />查询凭单<br>
                              <input type="checkbox" id="VC" value="录入" />录入凭单<br>
                              <input type="checkbox" id="VD" value="删除" />删除凭单
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-4">
                      <div class="form-group">
                          <label class="col-sm-4 control-label"><span style="color: red; "></span>合同权限：</label>
                          <div class="col-sm-8">
                              <input type="checkbox" id="CQ" value="查询" />查询合同<br>
                              <input type="checkbox" id="CC" value="录入" />录入合同<br>
                              <input type="checkbox" id="CD" value="删除" />删除合同
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-4">
                      <div class="form-group">
                          <label class="col-sm-4 control-label"><span style="color: red; "></span>财务资料权限：</label>
                          <div class="col-sm-8">
                              <input type="checkbox" id="FQ" value="查询" />查询财务资料<br>
                              <input type="checkbox" id="FQAll" value="查询" />查询所有财务资料<br>
                              <input type="checkbox" id="FC" value="录入" />录入财务资料<br>
                              <input type="checkbox" id="FD" value="删除" />删除财务资料
                          </div>
                      </div>
                  </div>
              </div>
          </form>
      </div>
      <div class="row">
          <div class="col-sm-offset-5 col-sm-10">
              <button type="button" class="btn btn-sm btn-primary" onclick="addUserSubmit()"><i class="fa fa-check"></i>保 存</button>&nbsp;
              <button type="button" class="btn btn-sm btn-danger" data-dismiss="modal" ><i class="fa fa-reply-all"></i>关 闭 </button>
          </div>
      </div>
  </div>

<%--修改用户密码的模态框--%>
  <div id="editUserModal" class="modal fade modalAdd">
    <div class="form-content">
      <form id="editUserForm">
        <h4 class="form-header h4">修改用户密码</h4>
        <div class="row">
          <div class="col-sm-3">
            <div class="form-group">
              <label class="col-sm-4 control-label">用户ID：</label>
              <div class="col-sm-8">
                <span id="edit_id"></span>
              </div>
            </div>
          </div>
          <div class="col-sm-3">
            <div class="form-group">
              <label class="col-sm-4 control-label">用户名称：</label>
              <div class="col-sm-8">
                <span id="edit_username"></span>
              </div>
            </div>
          </div>
          <div class="col-sm-6">
            <div class="form-group">
              <label class="col-sm-4 control-label"><span style="color: red; ">*</span>password：</label>
              <div class="col-sm-8">
                <input id="edit_new_password" autocomplete="off" placeholder="请输入用户的新密码" class="form-control" type="text" required/>
                 <span style="color:red">*密码必须包含字母+数字+特殊字符,长度不少于8位</span>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
    <div class="row">
      <div class="col-sm-offset-5 col-sm-10">
        <button type="button" class="btn btn-sm btn-primary" onclick="editUserSubmit()"><i class="fa fa-check"></i>保 存</button>&nbsp;
        <button type="button" class="btn btn-sm btn-danger" data-dismiss="modal" ><i class="fa fa-reply-all"></i>关 闭 </button>
      </div>
    </div>
  </div>

  <script type="text/javascript">
    jQuery(document).ready(function(){
  /*默认显示所有用户的基本信息*/
      loadUsers();

    });  //document.ready
  </script>
</body>
</html>
