<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
if(application.getAttribute("count")==null){
	application.setAttribute("count", 0);
}
application.setAttribute("count", (int)application.getAttribute("count")+1);
%>
${sessionScope.vcode }
<div class="copyright">
	请支持作者的书友购买正版光盘或者正版书籍，其版权归相关影音公司或原作者所有。<br> Copyright © 2020 全本小说网
	All Rights Reserved.访问：${applicationScope.count }<%=application.getAttribute("count") %>${cookie.uname.value }
</div>