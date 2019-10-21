<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/tag.jsp" %>


<!DOCTYPE>
<html lang="zh">
<head>
    <title>测试项目首页</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>${sysName}</title>
    <style type="text/css">
        body.signin {
            height: auto;
            background: url(${basePath}img/whiteFlower.jpg) no-repeat center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }
        td{
            width: 200px;
        }
    </style>

</head>
<body class="signin">
    <table border="1px solid yellow">
        <tr>
            <td>标题</td><td>链接</td>
        </tr>
        <tr><td>测试mybatis</td><td> <a href="${basePath}tomybatis.action" target="_blank">测试mybatis</a></td></tr>
        <tr><td><a href="${basePath}getOneToMore.action" target="_blank">获取一对多内容</a></td><td></td></tr>
        <tr><td></td><td></td></tr>
    </table>


</body>
</html>
