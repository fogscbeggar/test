package com.xiaoshuo.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoshuo.bean.Alinovel;
import com.xiaoshuo.bean.Novelinfo;
import com.xiaoshuo.common.Pagination;
import com.xiaoshuo.db.MysqlUtils;


public class NovelService extends ServiceModel<Alinovel> {
	
	public NovelService() {
		// TODO Auto-generated constructor stub
		this.tName="alinovel";
		this.bean=new Alinovel();
	}
	
	@Override
	public void add(HttpServletRequest request,HttpServletResponse response) throws IOException 	{
		// TODO Auto-generated method stub
		this.URL1="../admin/xiaoshuoadd.jsp";
		Random random=new Random();
		bean.setHits(1000+random.nextInt(8999));
		super.add(request,response);
	}
	
	@Override
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		this.URL1="../admin/xiaoshuorecycle.jsp";
		super.del(request, response);
	}
	
	@Override
	public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		this.URL1="../admin/xiaoshuolist.jsp";
		this.URL2="../novel/one.do";
		this.msg1="修改成功！";
		this.msg2="修改失败！";
		super.edit(request, response);
	}
	
	@Override
	public void find(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		URL1="/admin/xiaoshuolist.jsp";
		this.searchFields="title,content,categorytitle,author";
		super.find(request, response);
	}
	
	public void findRecycle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		URL1="/admin/xiaoshuorecycle.jsp";
		this.searchFields="title,content";
		super.find(request, response);
	}
	
	@Override
	public void one(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		this.URL1="/admin/xiaoshuoedit.jsp";
		super.one(request, response);
	}
	
	public void recycle(HttpServletRequest request,HttpServletResponse response) throws IOException {
		URL1=URL2="../admin/xiaoshuolist.jsp";
		this.msg1="放入回收站成功!";
		this.msg2="放入回收站失败!";
		bean.setStatus(0);
		super.edit(request, response);
	}
	
	public void recover(HttpServletRequest request,HttpServletResponse response) throws IOException {
		URL1=URL2="../admin/xiaoshuorecycle.jsp";
		this.msg1="恢复成功!";
		this.msg2="恢复失败!";
		bean.setStatus(1);
		super.edit(request, response);
	}

	public void findNovel(HttpServletRequest request,HttpServletResponse response) throws IOException {
				
				MysqlUtils mysql=null;
				try {
					String paraStr="";  //查询参数
					String where="";   //搜索条件
					String limit="";  //分页参数
					Integer id=-1;
					Integer status=null;
					String forword="/admin/xiaoshuolist.jsp";
					//获得关键字
					String kw=request.getParameter("kw")==null?"":request.getParameter("kw");
					try {
						status=Integer.parseInt(request.getParameter("s"));
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					if(status==null||status!=0) {
						where=" where status!=0";
					}else{
						where=" where status=0";
						forword="/admin/xiaoshuorecycle.jsp";
					}
					
					try {
						id=Integer.parseInt(kw);
						where+=" and id="+id;
					} catch (Exception e) {
						where+=String.format(" and (title regexp '.*%s.*' or content regexp '.*%s.*')", kw,kw);
					}
					//System.out.println(where);
					
					//获得页码
					Integer p=1;  //页码
					Integer pageSize=10; //每页记录条数
					try {
						p=Integer.parseInt(request.getParameter("p"))<=0?1:Integer.parseInt(request.getParameter("p"));
					} catch (Exception e) {
						// TODO: handle exception
					}
					limit=String.format(" limit %s,%s", (p-1)*pageSize,pageSize);
					
					paraStr=where+limit;
					
					mysql = new MysqlUtils();
					List<Map<String, Object>> rows = mysql.select("alinovel", paraStr);

					//获得总条数
					int rowsCount=mysql.select("alinovel", where).size();
					
					//分页对象
					Pagination page=new Pagination(p,rowsCount,rows,pageSize);
					
					request.setAttribute("page", page);
					request.setAttribute("kw", kw);
					request.getRequestDispatcher(forword).forward(request, response);
					
				} catch (Exception e) {
					// TODO: handle exception
				}finally {
					if(mysql!=null)mysql.close();
				}
	}
	public void isExist() {
		
	}
}
