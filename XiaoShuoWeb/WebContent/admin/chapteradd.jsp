<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title></title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery.js"></script>
<script src="js/pintuer.js"></script>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="ueditor/lang/zh-cn/zh-cn.js"></script>
</head>

<body>
<div class="panel admin-panel">
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>添加章节</strong></div>
  <div class="body-content">
    <form method="post" class="form-x" action="../chapter/add.do"> 
    <input type="hidden" name="novel_id" value=""> 
  	  <div class="form-group">
        <div class="label">
          <label>已有章节：</label>
        </div>
        <div class="field chapters">
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label>本章标题：</label>
        </div>
        <div class="field">
          <input type="text" class="input w50" value="" name="chapter_title" data-validate="required:请输入标题" />
          <div class="tips"></div>
        </div>
      </div>    
      
        <div class="form-group">
          <div class="label">
            <label>小说名称：</label>
          </div>
          <div class="field">
            	<a class="button border-yellow" href="javascript:edit()">
        		<span class="icon-book"></span> <i></i></a>
            <div class="tips"></div>
          </div>
        </div>
        
      <div class="form-group">
        <div class="label">
          <label>关键字：</label>
        </div>
        <div class="field">
          <input type="text" class="input" name="keywords" value=""/>
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label>描述：</label>
        </div>
        <div class="field">
          <textarea class="input" name="note" style=" height:90px;"></textarea>
          <div class="tips"></div>
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label>内容：</label>
        </div>
        <div class="field">
          <script id="editor" type="text/plain" style=" width:100%; height:300px;"></script>
        </div>
      </div>
     
      <div class="clear"></div>
      <div class="form-group">
        <div class="label">
          <label>排序：</label>
        </div>
        <div class="field">
          <input type="text" class="input w50" name="sort" value="0"  data-validate="number:排序必须为数字" />
          <div class="tips"></div>
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label>点击次数：</label>
        </div>
        <div class="field">
          <input type="text" class="input w50" name="hits" value="" data-validate="member:只能为数字"  />
          <div class="tips"></div>
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label></label>
        </div>
        <div class="field">
          <button class="button bg-main icon-check-square-o" type="submit"> 提交</button>
        </div>
      </div>
    </form>
  </div>
</div>
<script>
//实例化编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var ue = UE.getEditor('editor',{
	textarea:'chapter_content'
});
var id
$(function(){
	$("input[name=hits]").val(100+Math.ceil(Math.random()*9899));
	var paraArr=location.href.split('?')[1].split('-');
	$("input[name=novel_id]").val(paraArr[0]);
	$(".field i").html(decodeURI(paraArr[1]));
	id=paraArr[0];
	
	

	$.get("../chapter/ajaxFind.do",{"novel_id":id},function(d){
		var items=d.rows;
		var contains=$(".chapters");
		var html="";
		$.each(items,function(i){
			html+="<a class=\"button\" title=\""+items[i].chapter_title+"\" href=\"../admin/xiaoshuoadd.jsp\">第"+(i+1)+"章</a>";
		})
		contains.append(html);
	},"json");
	
	
})
function edit(){
	location.href="../novel/one.do?id="+id;
}
</script>
</body></html>