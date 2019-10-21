<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/pages/tag.jsp"%>


<!DOCTYPE>
<html  lang="zh" >
<head>
    <meta charset="utf-8">
    <title>合同默认主页面</title>
	<style>
        table{
            table-layout: fixed;
            width: 100%;
            text-align: center;
        }
        table thead{
            display: inline-block;
            width: 100%;
            height: 40px;
        }
        table tbody{
            display: inline-block;
            width: 100%;
            overflow: auto;
            max-height: 300px;
        }
        table tr{
            display: flex;
            width: 99%;
            /*height: 40px;
            line-height: 40px;*/
        }
        table td{
            /*display: inline-block;*/
          /*  white-space:nowrap;/!*不换行*!/
            overflow: hidden;/!*溢出的隐藏*!/
            text-overflow: ellipsis;/!*文本溢出用省略号代替*!/*/
        }
        /*合同名称 合同承担公司*/
        table td:nth-child(1), table thead tr th:nth-child(1){
			width: 170px;
		}
        table td:nth-child(6), table thead tr th:nth-child(6){
            width: 150px;
        }
        /*合同类型 合同金额 合同所属科室*/
        table td:nth-child(2), table thead tr th:nth-child(2),
        table td:nth-child(3), table thead tr th:nth-child(3),
        table td:nth-child(7), table thead tr th:nth-child(7),
        table td:nth-child(8), table thead tr th:nth-child(8){
            width: 70px;
        }

        /*设置表格的第3,4,5列的宽度 合同编号-年-月*/
        table td:nth-child(4), table thead tr th:nth-child(4),
        table td:nth-child(5), table thead tr th:nth-child(5){
            width: 50px;
        }
        /* 文档下载 操作*/
        table td:nth-child(10),table th:nth-child(10),
        table td:nth-child(11),table th:nth-child(11){
             width: 150px;
         }
	</style>
</head>
<body class="gray-bg">
	<!-- 右侧 -->
	<div class="container-div ui-layout-center">
		<!-- 默认合同首页的 查询区域 -->
		<div class="col-sm-12 search-collapse">
			<form id="user-form">
				<div>
					年份：<select id="search_year" class="form-control" style="width:150px;display:inline;">
							<option value="0"></option>
							<option value="2019">2019</option><option value="2018">2018</option>
							<option value="2017">2017</option><option value="2016">2016</option>
						</select>&nbsp;&nbsp;&nbsp;
					合同类型：<select id="search_type" name="search_type" class="form-control" style="width:150px;display:inline;">
								<%--<option value="0"></option>
								<option value="防汛">防汛</option>
								<option value="运维">运维</option>
								<option value="水资源">水资源</option>
								<option value="公用经费">公用经费</option>
								<option value="上级补助收入">上级补助收入</option>
								<option value="其他">其他</option>
								<option value="企业">企业</option>--%>
							</select>&nbsp;&nbsp;&nbsp;
					合同名称：<input type="text" id="search_name" name="search_name"  class="ipt1"/>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" class="btn btn-primary  btn-sm" onclick="searchContract()"><i class="fa fa-search"></i>搜索</a>
				</div>
			</form>
		</div>
		<!-- 合同默认首页的查询区域 END -->
		

		<div class="col-sm-12 select-table table-striped">
			<!-- 合同功能列表 -->
			<div class="bootstrap-table">
				<div class="fixed-table-toolbar">
					<div class="bs-bars pull-left">
						<div class="btn-group-sm" id="toolbar" role="group">
							<!-- 功能模块 -->
							<a href="#addContractModal" class="btn btn-success" onclick="" data-toggle="modal" >
								<i class="fa fa-plus"></i> 录入
							</a>
						</div>
					</div>
					<div class="columns columns-right btn-group pull-right"></div>
				</div>
				
				<!-- 合同表格基本信息展示 区域  -->
				<div class="fixed-table-container" style="padding-bottom:0px;">
					<div class="fixed-table-container" style="padding-bottom:0px;">
						<div class="fixed-table-header" style="display:none;"></div>
						<div>
							<table id="bootstrap-table" data-mobile-responsive="true" class="table table-hover">
								<thead>
									<tr id="contract_trID">
										<!-- <th  style="width:36px;"><input type='checkbox' name="ContractListch"/></th> -->
                                        <th id="c_th_name"><div class="th-inner">合同名称</div><div class="fht-cell"></div></th>
										<th id="c_th_type"><div class="th-inner">合同类型</div><div class="fht-cell"></div></th>
										<th id="c_th_number"><div class="th-inner">合同编号</div><div class="fht-cell"></div></th>
										<th id="c_th_year"><div class="th-inner">年</div><div class="fht-cell"></div></th>
										<th id="c_th_month"><div class="th-inner">月</div><div class="fht-cell"></div></th>
										<th id="c_th_compay"><div class="th-inner">合同承担公司</div><div class="fht-cell"></div></th>
										<th id="c_th_money"><div class="th-inner">合同金额</div><div class="fht-cell"></div></th>
										<th id="c_th_dept"><div class="th-inner">所属科室</div><div class="fht-cell"></div></th>
										<th id="th9"><div class="th-inner">合同说明</div><div class="fht-cell"></div></th>
										<th id="c_th_attach"><div class="th-inner" style="text-align:center">文档下载</div><div class="fht-cell"></div></th>
										<th id="c_th_opt"><div class="th-inner" style="text-align:center">操作</div><div class="fht-cell"></div></th>
									</tr>
								</thead>
								<tbody id="Contract_tbody"></tbody>
							</table>
						</div>
						<div class="fixed-table-footer" style="display:none;"></div>
						<!-- 记录条数  -->
						<div class="fixed-table-pagination" style="display:block;">
							<div class="pull-left pagination-detail">
								<span class="pagination-info">共<span id="recodeNum"></span>条记录</span>
								<span class="page-list" style="display:none;"></span>
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
		
		<!-- 展示合同图片 区域 -->
		<div class="col-sm-12 select-table">
			<div id="showImgDiv"></div>
		</div>
	</div>
	
	<!-- addContractModal 录入合同的模态框 只录入合同的基本信息  和修改合同基本信息的模态框 复用 通过id=flag标记传参数进行判断-->	
	<div id="addContractModal" class="modal fade modalAdd">
		<div class="form-content">
			<form id="addContractImgForm" enctype="multipart/form-data" class="form-horizontal">
				<h4 class="form-header h4">合同基本信息</h4>
				<!-- 这是一行 -->
				<div class="row">					
					<div class="col-sm-4">
						<div class="form-group">
							<label class="col-sm-4 control-label"><span style="color: red; ">*</span>合同名称：</label>
							<div class="col-sm-8">
								<input id="addContractModal_name" name="addContractModal_name" autocomplete="off" placeholder="请输入合同名称" class="form-control" required="required" type="text">
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group">
							<label class="col-sm-4 control-label"><span style="color: red; ">*</span>合同类型：</label>
							<div class="col-sm-8">
								<div class="input-group" style="width: 100%">
									<select id="addContractModal_type" name="addContractModal_type" class="form-control" >
										<option value="防汛">防汛</option>
										<option value="运维">运维</option>
										<option value="水资源">水资源</option>
										<option value="公用经费">公用经费</option>
										<option value="上级补助收入">上级补助收入</option>
										<option value="其他">其他</option>
										<option value="企业">企业</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group">
							<label class="col-sm-4 control-label"><span style="color: red; ">*</span>合同编号：</label>
							<div class="col-sm-8">
								<input id="addContractModal_number" name="addContractModal_number" autocomplete="off" placeholder="请输入合同编号" class="form-control" required="required" type="text">
							</div>
						</div>
					</div>
				</div>
				<!-- 这是一行 -->
				<div class="row">	
					<div class="col-sm-4">
						<div class="form-group">
							<label class="col-sm-4 control-label"><span style="color: red; ">*</span>所属年份：</label>
							<div class="col-sm-8">
								<div class="input-group" style="width: 100%">
									<select id="addContractModal_year" name="addContractModal_year" class="form-control" >
										<option value="2019">2019</option>
										<option value="2018">2018</option>
										<option value="2018">2017</option>
										<option value="2018">2016</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group">
							<label class="col-sm-4 control-label"><span style="color: red; ">*</span>所属月份：</label>
							<div class="col-sm-8">
								<div class="input-group" style="width: 100%">
									<select id="addContractModal_month" name="addContractModal_month" class="form-control" >
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group">
							<label class="col-sm-4 control-label"><span style="color: red; ">*</span>承担单位：</label>
							<div class="col-sm-8">
								<input id="addContractModal_partb" name="addContractModal_partb" autocomplete="off" placeholder="请输入合同承担单位" class="form-control" required="required" type="text">
							</div>
						</div>
					</div>
				</div>
				<!-- 这是一行 -->
				<div class="row">
					<div class="col-sm-4">
						<div class="form-group">
							<label class="col-sm-4 control-label"><span style="color: red; ">*</span>合同金额：</label>
							<div class="col-sm-8">
								<input id="addContractModal_money" name="addContractModal_money" autocomplete="off" placeholder="请输入合同金额" class="form-control" required="required" type="text" >
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group">
							<label class="col-sm-4 control-label"><span style="color: red; ">*</span>所属科室：</label>
							<div class="col-sm-8">
								<div class="input-group" style="width: 100%">
									<select id="addContractModal_department" name="addContractModal_department" class="form-control" >
										<option value="办公室">办公室</option>
										<option value="建管科">建管科</option>
										<option value="通信科">通信科</option>
										<option value="运维科">运维科</option>
										<option value="财务科">财务科</option>
										<option value="人劳科">人劳科</option>
										<option value="泺口站">泺口站</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group">
							<label class="col-sm-4 control-label"><span style="color: red; "></span>合同备注：</label>
							<div class="col-sm-8">
								<input id="addContractModal_detail" name="addContractModal_detail" autocomplete="off" placeholder="请输入合同备注" class="form-control" type="text">
							</div>
						</div>
					</div>
				</div>
			</form>
			</div>
			<!-- 这是一行 保存 关闭 按钮 -->
			<div class="row">
				<div class="col-sm-offset-5 col-sm-10">
					<button type="button" class="btn btn-sm btn-primary" onclick="addContractSubmit()"><i class="fa fa-check"></i>保 存</button>&nbsp;
					<button type="button" class="btn btn-sm btn-danger" data-dismiss="modal" ><i class="fa fa-reply-all"></i>关 闭 </button>
					<!-- addOrUpdateFlag 这里用来设置传递的参数 -->
					<!-- <input type="text" style="display:none" id="addOrUpdateFlag"/> -->
				</div>
			</div>
		</div>
			
			
	 <!-- 上传image的模态框 -->
	 <div class="modal fade" id="imageUploadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content" >
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabelImg">选择上传的图片</h4>
	            </div>
	            <div class="modal-body">
	            	选择图片:<input type="file" id="fileImage" name="fileImage" />
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	                <button type="button" id="buttonImage">确定</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	
	 
	  <!-- 上传文件的模态框 -->
	 <div class="modal fade" id="fileUploadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabel">选择上传的文件(支持pdf/doc/xls/txt的类型)</h4>
	            </div>
	            <div class="modal-body">
	            	选择文件:<input type="file" id="file3" name="file3" />
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	                <button type="button" id="uploadFileBtn">确定</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	
	
	<!-- 放大image的模态框 -->
	 <div class="modal fade" id="imageEnlargeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content" style="width:900px;margin-left:-150px;">
	            <!-- <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabel">选择上传的image</h4>
	            </div> -->
	            <div class="modal-body">
	            	<img class="enlargeContractImgModalCls" id="imageEnlargeBody"/>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>

	
	<script type="text/javascript">
		//前后端参数传递时,有特殊字符都需要编码/解码
		var name="", type="", year="" ,month="",number="",partb="",money=0.0,department="", detail="", imageurl="", attachurl = "";
		 //imageurl : xxzx_Contract%5C2019-1-1%5Cimg%5C
		// attachurl: xxzx_Contract%5C2019-1-1%5Cattach%5C
		 
		/* 后端返回的list 转为 合同 表格tbody的html */
		function getTbStrByList(list){
			var str = "";
			$.each(list,function(index, value){
				// 先把attachurl解码 再把/分隔符替换为_ (这里没有判断操作系统的分隔符 直接把\替换，linux操作系统的文件分隔符是/ ！！！！)
				// attachurl: xxzx_Contract\2019-1-1\attach\
				// attachurlId:"xxzx_Contract_2019-1-1_attach_"
				// attachurlId 用于显示td的追加文档
				var attachurlId = getFormattedAttachurl(value.attachurl); 
				var attachStr = "";
				var tmp = value.attachnames;
				//处理图片、附件返回值格式，字符串用逗号分隔
				if(tmp!= null && tmp != ""){
					var arr = new Array();
					arr = tmp.split(",");
					// attachStr是所有附件名称
					for(var i=0; i<arr.length; i++){
						attachStr += '<a class="attachACls" style="display:block" href="${basePath}downloadFileByAttachurlDatetimeFilename.action?attachurl='+value.attachurl+'&datetimeFilename='+arr[i]+'">'+arr[i]+ '</a>';
					}
					//console.log("attachStr:\n"+attachStr);
				}
				// 拼接整个表格的tbody部分内容为 str
				str+="<tr>"+
                    "<td name='cname'>"+value.name+"</td>"+
                    "<td name='ctype'>"+value.type+"</td>"+
                    "<td name='cnumber'>"+value.number+"</td>"+
                    "<td name='cyear'>"+value.year+"</td>"+
                    "<td name='cmonth'>"+value.month+"</td>"+
                    "<td name='cpartb'>"+value.partb+"</td>"+
                    "<td name='cmoney'>"+value.money+"</td>"+
                    "<td name='cdept'>"+value.department+"</td>"+
                    '<td name="cdetail">'+value.detail+"</td>"+
                    '<td name="vattach" id="'+ attachurlId+'">'+attachStr+'</td>'+
                    '<td name="vopt" style="padding:0;">'+
                    '</td>'+
				"</tr>";
				//拼接str字符串！注意a标签的href和参数，最外面用单引号，单引号里面包含双引号用来标注href的URL。
			});
			//console.log("表格list拼接html.str:\n"+str);
			return str;
		}
		
		/* 1. 录入合同 保存 方法 */
		function addContractSubmit(){
			//清空图片显示区域
			$("#showImgDiv").html("");
			name = $("#addContractModal_name").val();
			type = $("#addContractModal_type option:selected").text();
			number = $("#addContractModal_number").val();
			year = $("#addContractModal_year option:selected").text();
			month = $("#addContractModal_month option:selected").text();
			partb = $("#addContractModal_partb").val();
			money = $("#addContractModal_money").val();
			department = $("#addContractModal_department option:selected").text();
			detail = $("#addContractModal_detail").val();
			var blankFlag = 1;
			if(judgeBlank(name) == false){
				alert("请输入合同名称!");
				blankFlag = 0;
			}
			if(judgeBlank(number) == false){
				alert("请输入合同编号!");
				blankFlag = 0;
			}
			if(judgeBlank(partb) == false){
				alert("请输入合同承担单位!");
				blankFlag = 0;
			}
			if(judgeBlank(money) == false){
				alert("请输入合同金额!");
				blankFlag = 0;
			}
			if(judgeBlank(department) == false){
				alert("请输入合同所属科室!");
				blankFlag = 0;
			}
			if(blankFlag == 1){
				//非空 继续执行	
				console.log("录入合同的保存方法addContractSubmit---name:"+name+",year:"+year+",month:"+month+",number:"+number+",detail:"+detail);
				$.ajax({
					url:"${basePath}addC.action",
					data:{
						"name":name,
						"type":type,
						"number":number,
						"year":year,
						"month":month,
						"partb":partb,
						"money":money,
						"department":department,
						"detail":detail
					},
					dataType:"json",
					type:"post",
					success:function(data){
						var str = "";				  	
						if("1" == data.code){
						//获取这个年月的所有合同进行显示
							var cymContracts = data.data;
							var recodeNum = cymContracts.length;
							str = getTbStrByList(cymContracts);
                            /*$("#Contract_tbody").height($(window).height() * 0.5);*/
							$("#Contract_tbody").html(str);
                            ini_td9_width();
							console.log("共"+recodeNum+"条记录");
							$("#recodeNum").text(recodeNum);
							$('#addContractModal').modal('hide');
							//查询重置为 当前录入合同的 单位-年-月
							$("#search_year").val(year);
							$("#search_type").val(type);
							$("#search_name").val("");
						}else if("0" == data.code){
							alert("合同重复,一个单位/年份/月份下的合同号不能重复,请重新录入");
						}
					}
				});
			}
		}
		
		/* 1. 查询合同 */
		function searchContract(){
			//清空图片显示区域
			$("#showImgDiv").html("");
			year = $("#search_year").val();		//year可能是0
			type = $("#search_type").val();		//type可能是0
			name = rmvBlank($("#search_name").val());
			if("" == name)
				name = "0";
			console.log("查询合同searchContract---year:"+year+",type:"+type+",name:"+name+"=====");
			$.ajax({
				url:"${basePath}getC.action",
				data:{
					"year":year,
					"type":type,
					"name":name
				},
				type:"post",
				dataType:"json",
				success:function(data){
					if("1" == data.code){
						var str2 = getTbStrByList(data.data);
						$("#Contract_tbody").html(str2);
                        ini_td9_width();
						console.log("共"+data.data.length+"条记录");
						$("#recodeNum").text(data.data.length);
					}
				}
			});
		}
		
		/* 删除合同 id用来删除数据库记录 和文件-图片,剩下的参数用来返回数据*/
		function delContract(id,name,type,number,year,month,partb,money,department,imageurl,attachurl){
			console.log("删除合同delContract---\nid："+id+",name:"+name+"type:"+type+",year:"+year+",month:"+month+"imageurl:"+imageurl+"attachurl:"+attachurl);
			// 清空已经显示的图片区域的内容
			$("#showImgDiv").html("");
			$.ajax({
				url:"${basePath}delC.action",
				data:{
					"id":id,
					"name":name,
					"type":type,
					"number":number,
					"year":year,
					"month":month,
					"partb":partb,
					"money":money,
					"department":department,
					"imageurl":imageurl,
					"attachurl":attachurl
				},
				type:"post",
				dataType:"json",
				success:function(data){
					if("1" == data.code){
						var str3 = getTbStrByList(data.data);
						$("#Contract_tbody").html(str3);
                        ini_td9_width();
						$("#recodeNum").text(data.data.length);
					}else if("0" == data.code){
						$("#Contract_tbody").html("");
					}
				}
			});
		}
		
		/*上传合同图片*/
		/* 表单通过ajax上传图片，无刷新显示缩略图 */
		 //1. 弹出上传图片的模态框	
		 function imageUpload(imageurlParam){
		 	var fileObj = document.getElementById("fileImage");
		 	fileObj.outerHTML = fileObj.outerHTML;
		 	//alert("选择的文件清空了吗");
		 	imageurl = imageurlParam;
		 	console.log("上传图片的imageurl:"+imageurl);
			 $("#imageUploadModal").modal();
		 }
		
		// 1.2 上传图片的模态框的确定按钮
		 $("#buttonImage").on("click",function(){
			 var s = $("#fileImage")[0].files[0];
			 console.log("#buttonImage.onclick. file.type:"+s.type);
			 if(judgeFileType(s.type) == "attach")
				 alert("请选择正确的图片类型,不要选择图片");
			 else{
				var formData = new FormData();
				formData.append("file_data", s);
				// 传来的形参imageurl 已经被encode，所以这里不需要再encode
				formData.append("imageurl", imageurl);
				var encodedAbspath = "";
				var datetimeFileName = "";
				$.ajax({
					url:"${basePath }uploadImageC.action",
					type:"post",
					cache:false,
					data: formData,
					processData:false,
					contentType:false,
					success:function(data){
						if("1" == data.code){
							encodedAbspath = data.encodedAbspath;
							datetimeFileName = data.datetimeFileName;
							$("#imageUploadModal").modal('hide');
							// 上传完一张图片 在显示区域 id=image1的html增加一个img标签，并设置其src路径，src是请求uploadImage后返回的磁盘路径
							// img标签的id用它自己的文件名标识,把文件名中的.替换为_
							if(encodedAbspath != ""){
								//把 datetimeFileName 中所有的.替换为_ 作为image的ID，其中.是特殊字符，需要用\转义
								var imgId = datetimeFileName.replace(/\./g,"_");
								console.log("imgId:"+imgId);
								//图片展示区域的动态生成html代码
								var strImg = '<div class="uplThumbnailDiv"><img class="uplThumbnail" alt="'+ imgId+'" id="'+imgId+'"/>'
								+ '<div class="twoLinkCenter"><a href="javascript:void(0)" onclick="enlargeImg('+"'"+ encodedAbspath +"'" +')">放大</a>&nbsp;&nbsp;'
								+ '<a href="${basePath}downloadFileImgC.action?abspath='+ encodedAbspath +'">下载</a></div>'
								+'</div>';
								console.log("encodedAbspath:\n"+encodedAbspath);
								console.log("strImg:\n"+strImg);
								// 图片显示区域的追加img标签
								$("#showImgDiv").append(strImg);
								var obj = $("#"+imgId);
								obj.attr("src","${basePath}showImg2C.action?abspath="+encodedAbspath);
							}
						}
					},
					error:function(err){
					}
				});
			 }
		 }); //
		 
		 // 点击上传文件获取attachurl参数  传给点击上传文档模态框的确定按钮
		 function fileUpload(attachurlParam){
			 	var file3Obj = document.getElementById("file3");
			 	file3Obj.outerHTML = file3Obj.outerHTML;
			 	attachurl = attachurlParam;
			 	console.log("上传文档的attachurl:"+attachurl);
				 $("#fileUploadModal").modal();
			 }
		 
		 
		// 1. 上传文档模态框的确定按钮
		 $("#uploadFileBtn").on("click",function(){
			 var s = $("#file3")[0].files[0];
			 console.log("#uploadFileBtn.onclick. file.type:"+s.type);
			 if(judgeFileType(s.type) == "image")
				 alert("请选择正确的文档类型,不要选择图片");
			 else{
				var formData = new FormData();
				formData.append("file_data", s);
				formData.append("attachurl", attachurl);
				$.ajax({
					url:"${basePath }uploadFileC.action",
					type:"post",
					cache:false,
					data: formData,
					processData:false,
					contentType:false,
					success:function(data){
						if("1" == data.code){
							encodedAbspath = data.encodedAbspath;
							datetimeFileName = data.datetimeFileName;
							$("#fileUploadModal").modal('hide');
							// 上传完一张图片 在显示区域 id=image1的html增加一个img标签，并设置其src路径，src是请求uploadImage后返回的磁盘路径
							// img标签的id用它自己的文件名标识,把文件名中的.替换为_
							if(encodedAbspath != ""){														
								var strAttach = 
								'<a class="attachACls" href="${basePath}downloadFileImgC.action?abspath=' + encodedAbspath+'">'
								+ decodeURIComponent(datetimeFileName)+'</a><br>'
								/* + '<button type="button" onclick="delFile('+"'"+encodedAbspath+"'"+')">'+"删除"+'</button><br>' */
								;									
								console.log("encodedAbspath:\n"+encodedAbspath);
								console.log("strAttach:\n"+strAttach);
								// 图片显示区域的追加img标签
								//显示该合同的文档下载的对应的表格td的id是getFormattedAttachurl(attachurl),在这个区域里进行append动态生成的html代码
								var attachurlObj = $("#"+getFormattedAttachurl(attachurl));
								attachurlObj.append(strAttach);
							}
						}
					},
					error:function(err){
					}
				});
			 }
		 }); //
		 
		 //1. 放大图片 弹出模态框
		 function enlargeImg(encodedAbspath){
			 $("#imageEnlargeModal").modal();
			 $("#imageEnlargeBody").attr("src","${basePath}showImg2C.action?abspath="+encodedAbspath);
		 }
		 
		 
		 //1.点击合同的查看图片(图片下载)
		 function seeImage(imageurl){
			 // 先 清空图片显示区域的内容
			 	$("#showImgDiv").html("");
				var formData = new FormData();
				// 传来的形参imageurl 已经被encode，所以这里不需要再encode
				formData.append("imageurl", imageurl);
				$.ajax({
					url:"${basePath }showImgByDir.action",
					type:"post",
					cache:false,
					data: formData,
					processData:false,
					contentType:false,
					success:function(data){
						if("1" == data.code){
							//data ia Array
							var filesInfo = data.data;
							$.each(filesInfo, function(index, value){
								var encodedAbspath = value.encodedAbspath;
								var datetimeFileName = value.datetimeFileName;
								// 上传完一张图片 在显示区域 id=image1的html增加一个img标签，并设置其src路径，src是请求uploadImage后返回的磁盘路径
								// img标签的id用它自己的文件名标识,把文件名中的.替换为_
								if(encodedAbspath != ""){
									//把 datetimeFileName 中所有的.替换为_ 作为image的ID，其中.是特殊字符，需要用\转义
									var imgId = datetimeFileName.replace(/\./g,"_");
									console.log("imgId:"+imgId);
									var strImg = '<div class="uplThumbnailDiv"><img class="uplThumbnail" alt="'+ imgId+'" id="'+imgId+'"/>'
									+ '<div class="twoLinkCenter"><a href="javascript:void(0)" onclick="enlargeImg('+"'"+ encodedAbspath +"'" +')">放大</a>&nbsp;&nbsp;'
									+ '<a href="${basePath}downloadFileImgC.action?abspath='+ encodedAbspath +'">下载</a></div>'
									+'</div>';
									console.log("encodedAbspath:\n"+encodedAbspath);
									console.log("strImg:\n"+strImg);
									// 图片显示区域的追加img标签
									$("#showImgDiv").append(strImg);
									var obj = $("#"+imgId);
									// 下载图片
									obj.attr("src","${basePath}showImg2C.action?abspath="+encodedAbspath);
								}
							});
						}else if("0" == data.code){
							alert("此合同还没有添加图片!");
						}
					},
					error:function(err){
					}
				});
		 }//seeImage
		 
		
	jQuery(document).ready(function(){
        initSearchType();
    });  //document.ready

        /*初始化合同类型 动态获取*/
        function initSearchType(){
            $.ajax({
                url:"${basePath}admin/getContractTypes.action",
                data:{},
                dataType:"json",
                type:"post",
                success:function(data){
                    var str = "";
                    if("1" == data.code){
                        var ctrtypes = data.data;
                        var ctrtypesNum = ctrtypes.length;
                        str = getCtrTypTbStrByList(ctrtypes);
                        $("#search_type").html(str);
                        console.log("共"+ctrtypesNum+"条记录");
                    }
                }
            });
        }

		/*初始化第9列合同说明的宽度*/
        function ini_td9_width(){
			var sum_exp_td9 = $("#c_th_name").width()+$("#c_th_type").width()+$("#c_th_number").width()+$("#c_th_year").width()
					+$("#c_th_month").width()+$("#c_th_compay").width()+$("#c_th_money").width()+$("#c_th_dept").width()
					+$("#c_th_attach").width()+$("#c_th_opt").width();
			console.log("sum_exp_td9"+sum_exp_td9);

			/*获取表格一行的宽度*/
            var trWidth = $("#contract_trID").width()-10;
            console.log("trwidth:"+trWidth);
			//var sum_td_exp_detail = $("#td_name").width() + $("#").width()
			/*表格除了第9列的宽度之和是 sum_exp_td9 */
            var td9Width = trWidth - sum_exp_td9;
            console.log("td9Width:"+td9Width);
            $("#th9").width(td9Width);
           /* $("td[name='cname']").width(wd150);
			$("td[name='vtype']").width(wd70);
			$("td[name='vnumber']").width(wd70);
			$("td[name='vyear']").width(wd50);
			$("td[name='vmonth']").width(wd50);
			$("td[name='vpartb']").width(wd150);
			$("td[name='vmoney']").width(wd70);
			$("td[name='vdept']").width(wd70);*/
            $("td[name='cdetail']").width(td9Width);
			/*$("td[name='vattach']").width(wd150);
			$("td[name='vopt']").width(wd150);*/

            /*$("td[name='td9']").each(function(){
             $(this).width(td9Width);
             });*/
        }


        /*拼接合同类型的option*/
        function getCtrTypTbStrByList(list) {
            var str = '<option value="0"></option>';
            $.each(list, function (index, value) {
                // 拼接整个表格的tbody部分内容为 str
                str += '<option value="'+value.typename+'">'+value.typename+'</option>';
            });
            //console.log("option拼接html.str:\n" + str);
            return str;
        }

	</script>
</body>
</html>
