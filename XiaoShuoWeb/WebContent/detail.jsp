<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="css/common.css" />
		<style>
			.content {background: rgb(250, 250, 250);}
			.content{ width: 1318px; margin: 0 auto 0 auto; border: #ddd 1px solid; border-top:none;}
			.content h1{ text-align: center; font-size: 30px; padding: 10px 10px 0 10px;}
			.content .writer{ border-bottom: #ddd 1px dashed; text-align: center; padding: 10px;}
			.content .cont{ padding: 20px 40px; line-height: 60px; color: rgb(85,85,85);}
			.content .cont *{ font-size: 24px; text-indent: 2em; }
			.content .bottom{ text-align: center; line-height: 24px; padding: 10px 0; background: rgb(253,253,253);}
			.content  a,.copyright a{ margin: 0 5px;}
		</style>
		<script src="js/jquery-1.11.1.min.js"></script>
		<script>
		$(function(){
			$("#head").load("head.html");
			$(".content h1").html("1991从芯开始");
			
		})
		</script>
	</head>
	<body>
	<jsp:useBean id="detail" class="com.xiaoshuo.service.Detail">
	</jsp:useBean>
	<jsp:setProperty property="id" name="detail" param="nid"/>
		<div id="head"></div>
		<div class="loc">
			<div class="right">
				<span>字体:[<a href="#">大</a> <a href="#">中</a> <a href="#">小</a> ]</span>
			</div>
			<div class="left">
				<a href="#">阿狸小说网</a> >
				<a href="#">斗罗大陆4终极斗罗</a> >
				<span>第一章 那是什么？</span>
			</div>
		</div>
		<div class="content">
			<h1>楔子 那一箭的风情</h1>
			<div class="writer">
				<span>作者：录事参军</span>
				<a href="#">返回目录</a>
				<a href="#">加入书签</a>
				<a href="#">投票推荐</a>
			</div>
			<div class="hot">
				<span>人气小说：</span>
				<a href="#">鬼帝狂妻：纨绔大小姐</a>
				<a href="#">抗日之特战兵王</a>
				<a href="#">汉乡</a>
				<a href="#">偷香</a>
				<a href="#">银狐</a>
				<a href="#">盛唐风华</a>
				<a href="#">福晋有喜：爷，求不约</a>
			</div>
			<div class="cont">${detail.content() }</div>
			<div class="bottom">
				<a href="#">投票推荐</a>
				<a href="#">上一章</a>
				<a href="#">返回目录</a>
				<a href="#">下一章</a>
				<a href="#">加入书签</a><br>
				<span>温馨提示：方向键左右(← →)前后翻页，上下(↑ ↓)上下滚用， 回车键：返回列表</span>
			</div>
		</div>
		<div class="links">
			<dl>
				<dt>友情链接</dt>
				<dd>
					<a href="#">免费小说</a>
					<a href="#">阿狸免费小说</a>
				</dd>
			</dl>
		</div>
		<div class="copyright">
			<span>本站推荐：</span>
			<a href="#">我的帝国无双</a>
			<a href="#">明天下</a>
			<a href="#">唐枭</a>
			<a href="#">乘龙佳婿</a>
			<a href="#">长宁帝军</a>
			<a href="#">医妃惊世</a>
			<a href="#">1852铁血中华</a>
			<a href="#">超级兵王</a>
			<a href="#">天才小毒妃</a><br>
			<span>请支持作者的书友购买正版光盘或者正版书籍，其版权归相关影音公司或原作者所有。</span><br> 
			<span>Copyright © 2020 全本小说网 All Rights Reserved.</span>
		</div>
	</body>

</html>