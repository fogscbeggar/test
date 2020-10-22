package com.xiaoshuo.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoshuo.bean.Sitesetting;

public class SitesettingService extends ServiceModel<Sitesetting> {

	public SitesettingService() {
		// TODO Auto-generated constructor stub
		this.tName = "sitesetting";
		this.bean = new Sitesetting();
	}

//	@Override
//	public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		// TODO Auto-generated method stub
//		this.URL1 = "../admin/sitesetting.jsp";
//		this.URL2 = "../sitesetting/one.do";
//		this.msg1 = "修改成功！";
//		this.msg2 = "修改失败！";
//		this.limit="jpg,png,gif,jpeg,bmp";
//		super.edit(request, response);
//	}
	
	@Override
	public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		this.URL1 = "../admin/sitesetting.jsp";
		this.URL2 = "../sitesetting/one.do";
		this.msg1 = "修改成功！";
		this.msg2 = "修改失败！";
		this.limit="jpg,png,gif,jpeg,bmp";  //设置上传文件扩展名
		this.uploadFieldName="logo";  //设置上传字段
		super.edit(request, response);
	}

	@Override
	public void one(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		this.URL1 = "/admin/sitesetting.jsp";
		super.one(request, response);
	}
}
