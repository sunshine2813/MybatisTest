<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/pages/tag.jsp"%>



<html>
    <head>
        <title>用户权限信息管理</title>
    </head>
    <body class="gray-bg">
        <div class="container-div ui-layout-center" style="padding: 10px 10px;">
            <div>
                <!-- 管理员查询用户信息区域 -->
                <div class="col-sm-12 search-collapse" style="margin-left: -25px;">
                    <form id="user-form">
                        <div>
                            用户姓名(汉语)：<input id="search_username"  class="ipt1"/>&nbsp;&nbsp;&nbsp;
                            <a href="javascript:void(0)" class="btn btn-primary  btn-sm" onclick="userPmsSearchUser()"><i class="fa fa-search"></i>搜索</a>
                        </div>
                    </form>
                </div>
                <!-- 管理员查询用户信息区域  END -->
                <div class="col-sm-12 select-table table-striped" style="margin-left: -25px;">
                    <div class="bootstrap-table" style="margin-top: -20px;margin-left: -15px;">
                        <div class="fixed-table-toolbar">
                            <div class="bs-bars pull-left">
                                <div class="btn-group-sm" id="toolbar" role="group">
                                    <!-- 功能模块 -->
                                   <%--<a class="btn btn-success" href="#addUserModal" onclick="" data-toggle="modal">
                                        <i class="fa fa-plus"></i> 录入用户权限
                                    </a>--%>
                                </div>
                            </div>
                        </div>
                        <!-- 表格基本信息展示 区域  -->
                        <div class="fixed-table-container">
                            <div >
                                <table id="bootstrap-table" data-mobile-responsive="true" class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th><div class="th-inner">用户ID</div><div class="fht-cell"></div></th>
                                        <th><div class="th-inner">用户代码</div><div class="fht-cell"></div></th>
                                        <th><div class="th-inner">姓名</div><div class="fht-cell"></div></th>
                                        <th><div class="th-inner">hashed密码</div><div class="fht-cell"></div></th>
                                        <th><div class="th-inner">凭单权限</div><div class="fht-cell"></div></th>
                                        <th><div class="th-inner">合同权限</div><div class="fht-cell"></div></th>
                                        <th><div class="th-inner">财务资料权限</div><div class="fht-cell"></div></th>
                                        <th style="text-align:center"><div class="th-inner">操作</div><div class="fht-cell"></div></th>
                                    </tr>
                                    </thead>
                                    <tbody id="user_perms_tbody"></tbody>
                                </table>
                            </div>
                            <div class="fixed-table-footer" style="display:none;"></div>
                            <!-- 记录条数  -->
                            <div class="fixed-table-pagination" style="display:block;">
                                <div class="pull-left pagination-detail">
                                    <span class="pagination-info">共<span id="user_perms_tbody_recordNum"></span>条记录</span>
                                    <span class="page-list" style="display:none;"></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            jQuery(document).ready(function(){
                /*默认显示所有用户的基本信息*/
                loadUserPerms();

            });  //document.ready
        </script>


    </body>
</html>
