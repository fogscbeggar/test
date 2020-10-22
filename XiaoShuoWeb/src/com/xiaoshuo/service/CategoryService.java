package com.xiaoshuo.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoshuo.bean.Alicategory;

public class CategoryService extends ServiceModel<Alicategory> {
	public CategoryService() {
		// TODO Auto-generated constructor stub
		this.tName="alicategory";
		this.bean=new Alicategory();
	}
	@Override
	public void add(HttpServletRequest request,HttpServletResponse response) throws IOException 	{
		// TODO Auto-generated method stub
		this.URL1="../admin/categoryadd.jsp";
		super.add(request,response);
	}
	
	@Override
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		this.URL1="../admin/categorylist.jsp";
		super.del(request, response);
	}
	
	@Override
	public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		this.URL1="../admin/categorylist.jsp";
		this.URL2="../category/one.do";
		this.msg1="修改成功！";
		this.msg2="修改失败！";
		super.edit(request, response);
	}
	
	@Override
	public void find(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		URL1="/admin/categorylist.jsp";
		this.searchFields="title";
		super.find(request, response);
	}
	
	@Override
	public void ajaxFind(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		this.searchFields="title";
		this.pageSize=100000;
		super.ajaxFind(request, response);
	}
	
	@Override
	public void one(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		this.URL1="/admin/categoryedit.jsp";
		super.one(request, response);
	}
}
