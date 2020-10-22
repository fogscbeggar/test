<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>网站信息</title>  
    <link rel="stylesheet" href="/XiaoShuoWeb/admin/css/pintuer.css">
    <link rel="stylesheet" href="/XiaoShuoWeb/admin/css/admin.css">
    <script src="/XiaoShuoWeb/admin/js/jquery.js"></script>
    <script src="/XiaoShuoWeb/admin/js/pintuer.js"></script>  
</head>
<body>
<div class="panel admin-panel">
  <div class="panel-head"><strong class="icon-reorder"> 分类列表</strong></div>
  <div class="padding border-bottom">  
  <form action="../category/find.do" method="get" class="searchForm" style="width: 400px; float: right">
				<li>
					<input type="hidden" name="status" value="1" />
					<input type="text" placeholder="请输入搜索关键字" name="kw" class="input" style="width: 250px; line-height: 17px; display: inline-block" value="${kw }" />
					<input type="submit" class="button border-main icon-search" value="搜索">
				</li>
			</form>
  <a class="button border-yellow" href="../admin/categoryadd.jsp"><span class="icon-plus-square-o"></span> 添加分类</a>
  <a href="../admin/categorylist.jsp" class="button border-main icon-refresh"> 全部显示</a>
  </div> 
  
			<c:if test="${page.rows==null }">
				<jsp:forward page="../category/find.do"></jsp:forward>
			</c:if>
  <table class="table table-hover text-center">
    <tr>
      <th width="5%">ID</th>     
      <th>分类名称</th>  
      <th>排序</th>     
      <th width="250">操作</th>
    </tr>
   <c:forEach var="row" items="${page.rows }">
    <tr>
      <td>${row.id }</td>      
      <td>${row.title }</td>  
      <td>${row.sort }</td>      
      <td>
      <div class="button-group">
      <a type="button" class="button border-main" href="../category/one.do?id=${row.id }"><span class="icon-edit"></span>修改</a>
       <a class="button border-red" href="javascript:void(0)" onclick="return del(${row.id })"><span class="icon-trash-o"></span> 删除</a>
      </div>
      </td>
    </tr> 
    </c:forEach>
     
    <tr>
	        <td colspan="8">
		        <div class="pagelist"> 
			        <a href="?p=${page.first }&kw=${kw }">首页</a> 
			        <a href="?p=${page.prePage }&kw=${kw }">上一页</a> 
			        <c:forEach var="i" begin="${page.startPage }" end="${page.endPage }" step="1">
				        <c:if test="${i==page.curPage }">
				        	<span class="current">${i }</span>
				        </c:if>
				        <c:if test="${i!=page.curPage }">
				        	<a href="?p=${i }&kw=${kw }">${i }</a>
				        </c:if>
			        </c:forEach>
			        <a href="?p=${page.nextPage }&kw=${kw }">下一页</a>
			        <a href="?p=${page.last }&kw=${kw }">尾页</a> 
		        </div>
	        </td>
	      </tr>
  </table>
</div>
<script>
function del(id){
	if(confirm("您确定要删除吗?")){
		location.href="../category/del.do?id="+id;
	}
}
</script>
</body></html>