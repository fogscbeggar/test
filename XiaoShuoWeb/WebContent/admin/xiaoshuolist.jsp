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
<title>网站信息</title><link rel="stylesheet" href="/XiaoShuoWeb/admin/css/pintuer.css">
<link rel="stylesheet" href="/XiaoShuoWeb/admin/css/admin.css">
<script src="/XiaoShuoWeb/admin/js/jquery.js"></script>
<script src="/XiaoShuoWeb/admin/js/pintuer.js"></script>

</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head">
			<strong class="icon-reorder"> 小说列表</strong>
		</div>
		<div class="padding border-bottom">
			<form action="../novelinfo/find.do?status=1" method="get" class="searchForm" style="width: 400px; float: right">
				<li>
					<input type="hidden" name="status" value="1" />
					<input type="text" placeholder="id,标题,分类,作者" name="kw" class="input" style="width: 250px; line-height: 17px; display: inline-block" value="${kw }" />
					<input type="submit" class="button border-main icon-search" value="搜索">
				</li>
			</form>
			<a class="button border-yellow" href="../admin/xiaoshuoadd.jsp"><span class="icon-plus-square-o"></span> 添加小说</a>
			<a href="../admin/xiaoshuolist.jsp" class="button border-main icon-refresh"> 全部显示</a>

		</div>
		<table class="table table-hover text-center">
			<tr>
				<th width="5%">ID</th>
				<th>封面</th>
				<th>标题</th>
				<th>分类</th>
				<th>作者</th>
				<th>浏览次数</th>
				<th>连载</th>
				<th>上架/下架</th>
				<th>章节</th>
				<th>评论</th>
				<th>发布时间</th>
				<th width="250">操作</th>
			</tr>
			<c:if test="${page.rows==null }">
				<jsp:forward page="../novelinfo/find.do?status=1"></jsp:forward>
			</c:if>
			<c:forEach var="row" items="${page.rows }">
			<tr>
				<td>${row.id }</td>
				<td>${row.pic }</td>
				<td>${row.title }</td>
				<td>${row.categorytitle }</td>
				<td>${row.author }</td>
				<td>${row.hits }</td>
				<td>${row.serialize==0?'<font color="blue">连载</font>':'<font color="green">完结</font>'  }</td>
				<td>${row.upshelf?'<font color="green">上架</font>':'<font color="red">下架</font>' }</td>				
				<td>
					<span>${row.chapter_count }章</span>
					<a href="../admin/chapteradd.jsp?${row.id }-${row.title }" class="border-yellow icon-plus-square-o"></a>
				</td>
				<td><a href="../admin/chapteradd.jsp">${row.hits }</a></td>
				<td>${row.create_time }</td>
				<td>
					<div class="button-group">
						<a type="button" class="button border-main" href="javascript:void(0)" onclick="edit(${row.id})">
						<span class="icon-edit"></span>修改
						</a>
						<a class="button border-red" href="javascript:void(0)" onclick="return del(${row.id})">
						<span class="icon-trash-o"></span> 回收站</a>
					</div>
				</td>
			</tr>
			</c:forEach>
	      <tr>
	        <td colspan="10">
		        <div class="pagelist"> 
			        <a href="?status=1&p=${page.first }&kw=${kw }">首页</a> 
			        <a href="?status=1&p=${page.prePage }&kw=${kw }">上一页</a> 
			        <c:forEach var="i" begin="${page.startPage }" end="${page.endPage }" step="1">
				        <c:if test="${i==page.curPage }">
				        	<span class="current">${i }</span>
				        </c:if>
				        <c:if test="${i!=page.curPage }">
				        	<a href="?status=1&p=${i }&kw=${kw }">${i }</a>
				        </c:if>
			        </c:forEach>
			        <a href="?status=1&p=${page.nextPage }&kw=${kw }">下一页</a>
			        <a href="?status=1&p=${page.last }&kw=${kw }">尾页</a> 
		        </div>
	        </td>
	      </tr>
		</table>
	</div>
	<script>
		function del(id) {
			if (confirm("您确定放入回收站吗?")) {
				location.href="../novel/recycle.do?id="+id;
			}
		}
		
		function edit(id){
			location.href="../novel/one.do?id="+id;
		}
	</script>

</body>
</html>