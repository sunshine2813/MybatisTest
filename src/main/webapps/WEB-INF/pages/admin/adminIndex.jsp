<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/tag.jsp"%>



<!DOCTYPE>
<html  lang="zh">
	<title>${sysName}</title>
	<style type="text/css">
		.nav > li:hover .dropdown-menu
		{
			display: block;
		}
		#content-main.max {
			height: calc(100% - 110px); overflow: hidden; width: 100%; height: 100%; left: 0px; position: absolute; top: 0px; z-index: 9998; margin: 0;
		}
	</style>
    <script>
        /*在common_js.jsp中添加*/
        /*/!*获取顶层窗口对象*!/
        function getTopWindow(){
            var p = window;
            while(p != p.parent){
                p = p.parent;
            }
            return p;
        }
        var top = getTopWindow();
        /!*如果不是顶层对象, 则跳转到login页面*!/
        if(top != window){
            top.location.href = "${basePath}gologin.action";
        }*/
    </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height: 30px;">
        <nav style="background-color: #95B8E7;width: 100%;height:100%;">
            <ul style="float: right">
                <li>
                    当前用户:
                </li>
            </ul>
        </nav>
	</div>
	<%--左侧树形菜单栏--%>
	<div data-options="region:'west'" title="菜单" style="width: 15%;">
		<ul class="easyui-tree" id="leftMenu">
			<li>
				<span>用户管理</span>
				<ul>
					<li data-options="url:'${basePath}admin/userInfo.action'">
						<span>用户基本信息管理</span>
					</li>
					<li data-options="url:'${basePath}admin/userPms.action'">
						<span>用户权限管理</span>
					</li>
				</ul>
			</li>
			<li data-options="url:'${basePath}admin/contractType.action'">
				<span>合同类别管理</span>
			</li>
		</ul>
	</div>
	<%--中间部分--%>
	<div data-options="region:'center'" title="首页" style="">
		<div id="tabs" class="easyui-tabs"></div>
	</div>

    <div data-options="region:'south'" style="height: 50px;">
            <div class="pull-right"> 主办：<a href="http://10.104.66.13" target="_blank" rel="nofollow">山东黄河信息中心</a> 版权所有© 设计制作/维护管理：<a href="http://10.104.66.13" target="_blank" rel="nofollow">山东黄河信息中心</a><br>
                联系电话：0531-86987089 地址：济南市历下区东关大街111号 邮编：250013<br>
            </div>
    </div>




	<script type="text/javascript">
		$(document).ready(function(){

			/*左侧树形结构的点击事件*/
			$("#leftMenu").tree({
				onClick:function(node){
					if($("#leftMenu").tree("isLeaf", node.target)){
						var tabs = $("#tabs");
						var tab = tabs.tabs("getTab", node.text);
						if(tab){
							tabs.tabs("select", node.text);
						}else{
							tabs.tabs("add",{
								title: node.text,
								href: node.url,
								closable: true
							})
						}
					}
				}
			})
		});

	</script>
</body>

</html>
