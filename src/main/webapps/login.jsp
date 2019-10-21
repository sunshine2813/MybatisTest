<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/tag.jsp" %>
<!DOCTYPE>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>${sysName}</title>
    <link rel="stylesheet" type="text/css" href="${basePath }css/login.min.css"/>
    <style type="text/css">label.error {
        position: inherit;
    }</style>
    <script>
        /*
         if(window.top!==window.self){window.top.location=window.location};
         */
        /*获取顶层窗口对象*/
        function getTopWindow() {
            var p = window;
            while (p != p.parent) {
                p = p.parent;
            }
            return p;
        }
        var w_top = getTopWindow();
        /*如果不是顶层对象, 则跳转到login页面*/
        if (w_top != window) {
            w_top.location.href = "${basePath}gologin.action";
        }
    </script>
</head>

<body class="signin" style="margin-top:15%;margin-left:-20%">
<div class="signinpanel">
    <div class="row">
        <div class="col-sm-6"></div>
        <div class="col-sm-5">
            <form id="loginForm" method="post">
                <p class="m-t-md">

                <h3 style="text-align: center">${sysName}</h3>
                <input type="text" id="username" name="username" class="form-control uname" placeholder="输入用户名"/>
                <input type="password" id="password" name="password" class="form-control pword" placeholder="输入密码"/>

                <div class="row">
                    <div class="col-xs-12">
                        <button class="btn btn-success btn-block" onclick="my_login(1)">登&nbsp;&nbsp;&nbsp;&nbsp;录
                        </button>
                    </div>
                    <%--<div class="col-xs-6">
                    <button class="btn btn-success btn-block" onclick="my_login(2)">管理员登录</button>
                    </div>--%>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        ${requestScope.tip}
                        ${tip}
                    </div>
                </div>
            </form>
        </div>
        <div class="col-sm-1"></div>
    </div>
    <div class="signup-footer">
        <div class="pull-left">
            主办：山东黄河信息中心 版权所有© 设计制作/维护管理：<a href="http://10.104.66.13" target="_blank" rel="nofollow">山东黄河信息中心</a><br>
            联系电话：0531-86987598 联系信箱：WEB@SDHH.GOV.CN 地址：济南市历下区东关大街111号 邮编：250013<br>
            <br>
        </div>
    </div>
</div>
<script type="text/javascript">
    function my_login(type) {
        var username = $("#username").val();
        var password = $("#password").val();
        if ("1" == type) {
            if (rmvBlank(username) == "" || rmvBlank(password) == "")
                alert("用户名和密码不能为空");
            else {
                //console.log("-username:--"+username+"\npassword:"+password);
                $("#loginForm").attr("action", "${basePath}userLogin.action").submit();
            }
        }
    }

    $(document).keyup(function (e) {
        if (e.keyCode == 13) {
            my_login("1");
        }
    });
</script>

</body>
</html>
