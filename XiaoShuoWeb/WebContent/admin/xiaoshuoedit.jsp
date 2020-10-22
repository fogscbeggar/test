<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title></title>
<link rel="stylesheet" href="/XiaoShuoWeb/admin/css/pintuer.css">
<link rel="stylesheet" href="/XiaoShuoWeb/admin/css/admin.css">
<script src="/XiaoShuoWeb/admin/js/jquery.js"></script>
<script src="/XiaoShuoWeb/admin/js/pintuer.js"></script>
    <script type="text/javascript" charset="utf-8" src="/XiaoShuoWeb/admin/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/XiaoShuoWeb/admin/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="/XiaoShuoWeb/admin/ueditor/lang/zh-cn/zh-cn.js"></script>
<script>
$(function(){
	$.get("../category/ajaxFind.do",function(d){
		var items=d.rows;
		var select=$("select[name=category]");
		var opts="";
		$.each(items,function(i){
			opts+="<option value=\""+items[i].id+"\" "+(${row.category}==items[i].id?"selected='selected'":"")+">"+items[i].title+"</option>";
		})
		select.append(opts);
	},"json");
	
	$.get("../chapter/ajaxFind.do",{"novel_id":${row.id}},function(d){
		console.log(d.rows)
		var items=d.rows;
		var contains=$(".chapters");
		var html="";
		$.each(items,function(i){
			html+="<a class=\"button\" title=\""+items[i].chapter_title+"\" href=\"../admin/xiaoshuoadd.jsp\">第"+(i+1)+"章</a>";
		})
		contains.append(html);
	},"json");
})
</script>
</head>
<body>
<div class="panel admin-panel">
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>修改小说</strong></div>
  <div class="body-content">
    <form method="post" class="form-x" action="../novel/edit.do">  
    	<input type="hidden" name="id" value="${row.id }" />
      <div class="form-group">
        <div class="label">
          <label>标题：</label>
        </div>
        <div class="field">
          <input type="text" class="input w50" value="${row.title }" name="title" data-validate="required:请输入标题" />
          <div class="tips"></div>
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label>图片：</label>
        </div>
        <div class="field">
          <input type="text" id="url1" name="pic" class="input tips" style="width:25%; float:left;"  value=""  data-toggle="hover" data-place="right" data-image="" />
          <input type="button" class="button bg-blue margin-left" id="image1" value="+ 浏览上传"  style="float:left;">
          <div class="tipss">图片尺寸：500*500</div>
        </div>
      </div>     
      
      <if condition="$iscid eq 1">
        <div class="form-group">
          <div class="label">
            <label>分类：</label>
          </div>
          <div class="field">
            <select name="category" class="input w50">
              
            </select>
            <div class="tips"></div>
          </div>
        </div>
         <div class="form-group">
	        <div class="label">
	          <label>已有章节：</label>
	        </div>
	        <div class="field chapters">
	        	<a href="../admin/chapteradd.jsp?${row.id }-${row.title }" class="button border-yellow icon-plus-square-o"></a>
	        </div>
	      </div>
        <div class="form-group">
          <div class="label">
            <label>其他属性：</label>
          </div>
          <div class="field" style="padding-top:8px;"> 
            首页 <input id="ishome"  type="checkbox" />
            推荐 <input id="isvouch"  type="checkbox" />
            置顶 <input id="istop"  type="checkbox" /> 
         
          </div>
        </div>
      </if>
      <div class="form-group">
        <div class="label">
          <label>关键字：</label>
        </div>
        <div class="field">
          <input type="text" class="input" name="keywords" value="${row.keywords }"/>
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label>描述：</label>
        </div>
        <div class="field">
          <textarea class="input" name="description" style=" height:90px;">${row.description }</textarea>
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
          <label>作者：</label>
        </div>
        <div class="field">
          <input type="text" class="input w50" name="author"  data-validate="required:作者不能为空" value="${row.author }"  />
          <div class="tips"></div>
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label>点击次数：</label>
        </div>
        <div class="field">
          <input type="text" class="input w50" name="hits" value="${row.hits }" data-validate="member:只能为数字"  />
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
		textarea:'content',
		 toolbars: [['undo', 'redo', '|',
	         'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	         'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	         'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
	         'directionalityltr', 'directionalityrtl', 'indent', '|',
	         'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase']]
	});
	
	//设置编辑器的值
	ue.ready(function(){
		var str='${row.content}';
		ue.setContent(str);
	})
</script>
</body></html>