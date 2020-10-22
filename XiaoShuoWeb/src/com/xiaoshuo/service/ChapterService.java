package com.xiaoshuo.service;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoshuo.bean.Alinovelchapter;

public class ChapterService extends ServiceModel<Alinovelchapter> {
	
	public ChapterService() {
		this.bean=new Alinovelchapter();
		this.tName="Alinovelchapter";
	}
	
	@Override
	public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		this.URL1="../admin/xiaoshuolist.jsp";
		super.add(request, response);
	}
	
	@Override
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		super.edit(request, response);
	}
	
	@Override
	public void find(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		super.find(request, response);
	}
	
	@Override
	public void ajaxFind(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		this.searchFields="id,chapter_title";
		super.ajaxFind(request, response);
	}
}
