<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title>网站信息</title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery.js"></script>
<script src="js/pintuer.js"></script>
</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head">
			<strong class="icon-reorder"> 用户列表(--${rows[0]['username'] }--)</strong>
		</div>
		<div class="padding border-bottom">
			<form action="" method="get" style="width: 400px; float: right">
				<li><input type="text" placeholder="请输入搜索关键字" name="keywords"
					class="input"
					style="width: 250px; line-height: 17px; display: inline-block" />
					<a href="javascript:void(0)" class="button border-main icon-search"
					onclick="changesearch()"> 搜索</a></li>
			</form>
			<a class="button border-yellow" href=""><span
				class="icon-plus-square-o"></span> 添加用户</a>

		</div>
		<table class="table table-hover text-center">
			<tr>
				<th width="5%">ID</th>
				<th>用户名</th>
				<th>真实姓名</th>
				<th>性别</th>
				<th>邮箱</th>
				<th>激活状态</th>
				<th>注册时间</th>
				<th width="250">操作</th>
			</tr>
			<c:if test="${rows==null }">
				<jsp:forward page="/UserHandle"></jsp:forward>
			</c:if>
			<c:forEach var="row" items="${rows }">
			<tr>
				<td>${row['id'] }</td>
				<td>${row['username'] }</td>
				<td>${row['realname'] }</td>
				<td>${row['sex']==1?'男':'女' }</td>
				<td>${row['email'] }</td>
				<td>${row['status']==1?'<font color="green">已激活</font>':'<font color="red">未激活</font>' }</td>
				<td>${row['create_time'] }</td>
				<td>
					<div class="button-group">
						<a type="button" class="button border-main" href="#"><span
							class="icon-edit"></span>修改</a> <a class="button border-red"
							href="javascript:void(0)" onclick="return del(17)"><span
							class="icon-trash-o"></span> 删除</a>
					</div>
				</td>
			</tr>
		</c:forEach>
			<tr>
				<td colspan="8"><div class="pagelist">
						<a href="">上一页</a> <span class="current">1</span><a href="">2</a><a
							href="">3</a><a href="">下一页</a><a href="">尾页</a>
					</div></td>
			</tr>
		</table>
	</div>
	<script>
		function del(id) {
			if (confirm("您确定要删除吗?")) {

			}
		}
	</script>

</body>
</html>