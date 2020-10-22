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


public class NovelinfoService extends ServiceModel<Novelinfo> {
	
	public NovelinfoService() {
		// TODO Auto-generated constructor stub
		this.tName="novelinfo";
		this.bean=new Novelinfo();
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
}
