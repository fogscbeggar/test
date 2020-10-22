package com.xiaoshuo.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ndktools.javamd5.core.MD5;
import com.xiaoshuo.db.MysqlUtils;

public class AdminService {

	public void addAdmin() {

	}

	public void delAdmin() {

	}

	public void editAdmin() {

	}

	public void findAdmin() {

	}

	public void isExist() {

	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String loginname = request.getParameter("loginname");
		String loginpwd = request.getParameter("loginpwd");
		String vcode=request.getParameter("vcode");
		if(!vcode.equalsIgnoreCase(request.getSession().getAttribute("vcode").toString())) {
			response.getWriter().print(false);
			return;
		}
		MysqlUtils mysql = new MysqlUtils();
		String tName = "admins";
		String where = "loginpwd='" + new MD5().getMD5ofStr(loginpwd) + "' and loginname='" + loginname + "'";
		if (mysql.isExists(tName, where)) {

			HttpSession session = request.getSession();
			session.setAttribute("loginname", loginname);
			session.setMaxInactiveInterval(3 * 60);
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	public void out(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		response.getWriter().print("<script>alert('退出成功！');history.back();</script>");
	}
}
