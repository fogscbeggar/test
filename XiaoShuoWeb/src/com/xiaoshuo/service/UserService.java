package com.xiaoshuo.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.ndktools.javamd5.core.MD5;
import com.xiaoshuo.bean.Aliuser;
import com.xiaoshuo.common.HtmlHandle;
import com.xiaoshuo.common.Pagination;
import com.xiaoshuo.common.StringHandle;
import com.xiaoshuo.db.MysqlUtils;

public class UserService {

	// 注册
	public void regUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Aliuser bean = new Aliuser();
		PrintWriter out = response.getWriter();
		boolean ok = false;
		try {
			BeanUtils.populate(bean, request.getParameterMap());
			validateUserData(bean,new Object[] {request.getParameter("userpwd2"),true});
			// 验证码

			// 数据库操作
			MysqlUtils db = new MysqlUtils();
			boolean flag = db.insert("aliuser", new String[] { "username", "userpwd", "realname", "sex", "email" },new String[] { bean.getUsername(), bean.getUserpwd(), bean.getRealname(), bean.getSex().toString(),bean.getEmail() });
			db.close();
			if (flag) {
				ok = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.print(ok);
		}
	}

	public void delUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		MysqlUtils mysql = null;
		PrintWriter out = response.getWriter();
		boolean flag = false;
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			mysql = new MysqlUtils();
			if (mysql.delete("aliuser", id)) {
				flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (mysql != null)
				mysql.close();
			out.print(flag);
		}
	}

	public void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Aliuser bean = new Aliuser(); //实例化bean，用于保存提交的数据
		MysqlUtils mysql=new MysqlUtils();
		try {
			List<String> fields=new ArrayList<String>();
			List<String> values=new ArrayList<String>();
			//通过beanUtils工具一次性获得提交的数据 ，并对应保存到bean中
			BeanUtils.populate(bean, request.getParameterMap());
			//验证提交的用户数据
			validateUserData(bean,new Object[] {request.getParameter("userpwd2"),false});

			if(bean.getRealname()!=null && !"".equals(bean.getRealname())) {
				fields.add("realname");
				values.add(bean.getRealname());
			}
			if(bean.getUserpwd()!=null && !"".equals(bean.getUserpwd())) {
				fields.add("userpwd");
				values.add(bean.getUserpwd());
			}
			fields.add("username");
			values.add(bean.getUsername());
			fields.add("sex");
			values.add(bean.getSex().toString());
			fields.add("email");
			values.add(bean.getEmail());
			fields.add("status");
			values.add(bean.getStatus().toString());
			
			String tName="aliuser";  //表名
			//需要修改的字段数组
			String[] fieldsArr= StringHandle.objectArrayToStringArray(fields.toArray()) ; 
			//需要修改的值数组
			String[] valuesArr= StringHandle.objectArrayToStringArray(values.toArray()) ;
			//需要修改的id
			String where="id="+bean.getId();
			//调用数据库操作中的修改方法
			if(!mysql.update(tName, fieldsArr, valuesArr, where)) {
				throw new Exception();
			}
			HtmlHandle.alertBox(response.getWriter(), "修改成功", "../admin/userlist.html");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HtmlHandle.alertBox(response.getWriter(), "修改失败", "useredit.html?"+bean.getId());
		}
	}

	// 获得一个用户的信息
	public void getOneUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean flag = false;
		MysqlUtils mysql = null;
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			mysql = new MysqlUtils();
			String where = String.format("where id=%s", id);
			Map<String, Object> map = mysql.select("aliuser", where).get(0);
			if (map.size() == 0) {
				throw new Exception();
			}
			response.getWriter().print(JSON.toJSONString(map));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.getWriter().print(flag);
		} finally {
			if (mysql != null)
				mysql.close();
		}
	}

	// 获得所有用户
	public void allUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 所有用户
		PrintWriter out = response.getWriter();
		
		MysqlUtils mysql=null;
		try {
			String paraStr="";  //查询参数
			String where="";   //搜索条件
			String limit="";  //分页参数
			Integer id=-1;
			//获得关键字
			String kw=request.getParameter("kw")==null?"":request.getParameter("kw");
			try {
				id=Integer.parseInt(kw);
				where=" where id="+id;
			} catch (Exception e) {
				where=String.format("where username regexp '.*%s.*' or realname regexp '.*%s.*'", kw,kw);
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
			List<Map<String, Object>> rows = mysql.select("aliuser", paraStr);

			//获得总条数
			int rowsCount=mysql.select("aliuser", where).size();
			
			//分页对象
			Pagination page=new Pagination(p,rowsCount,rows,pageSize);
			
			// 将list数据转为json格式数据
			String jsonData = JSON.toJSONString(page);
			System.out.println(jsonData);
			out.print(jsonData);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if(mysql!=null)mysql.close();
		}
	}

	// 检测用户名是否占用
	public void isExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String msg = "<font color=\"red\">不能为空！</font>";
		MysqlUtils mysql = null;
		try {
			if (request.getParameter("username") == null || "".equals(request.getParameter("username"))) {
				throw new Exception("<font color=\"red\">不能为空！</font>");
			}
			String username = request.getParameter("username");
			mysql = new MysqlUtils();
			if (mysql.isExists("aliuser", "username='" + username + "'")) {
				throw new Exception("<font color=\"red\">用户已存在！</font>");
			} else {
				msg = "<font color=\"green\">可用</font>";
			}

		} catch (Exception e) {
			// TODO: handle exception
			msg = e.getMessage();
		} finally {
			if (mysql != null)
				mysql.close();
			out.print("{\"message\":\"" + msg + "\"}");
		}
	}

	// 检测用户是否登录
	public void isLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("username") != null && !"".equals(session.getAttribute("username"))) {
			response.getWriter().print("{\"username\":\"" + session.getAttribute("username") + "\",\"stat\":true}");
		} else {
			response.getWriter().print("{\"stat\":false}");
		}
	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// ajax用户登录
		String username = request.getParameter("username");
		String userpwd = request.getParameter("userpwd");
		String jzpwd = request.getParameter("jzpwd");
		String vcode=request.getParameter("vcode");
		if(!vcode.equalsIgnoreCase(request.getSession().getAttribute("vcode").toString())) {
			response.getWriter().print(false);
			return;
		}
		MysqlUtils mysql = new MysqlUtils();
		String tName = "aliuser";
		String where = "userpwd='" + new MD5().getMD5ofStr(userpwd) + "' and username='" + username + "'";
		if (mysql.isExists(tName, where)) {
			if (jzpwd != null && "1".equals(jzpwd)) {
				Cookie c = new Cookie("username", username);
				c.setMaxAge(3 * 24 * 60 * 60); // 60秒
				response.addCookie(c);
			}

			HttpSession session = request.getSession();
			session.setAttribute("username", username);
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

	// 用户数据验证
	@SuppressWarnings("unused")
	private void validateUserData(Aliuser bean,Object... elseData) throws Exception {
		// 验证用户名
		if (bean.getUsername() == null || "".equals(bean.getUsername())) {
			throw new Exception("用户名不能为空！");
		} else {
			if (!StringHandle.regexMacher("[a-zA-Z]\\w{4,17}", bean.getUsername())) {
				throw new Exception("帐号由5~18位的数字、下划线、英文字母组成，必须以字母开头!");
			}
		}
		// 验证密码
		if(Boolean.parseBoolean(elseData[1].toString())) {
			if ((bean.getUserpwd() == null || "".equals(bean.getUserpwd()))) {
				throw new Exception("密码不能为空！");
			} else if (!bean.getUserpwd().equals(elseData[0])) {
				throw new Exception("输入的两次密码不一致！");
			} else if (!StringHandle.regexMacher(".{6,30}", bean.getUserpwd())) {
				throw new Exception("密码长度建议在6～30位之内！");
			}			
		}
		bean.setUserpwd(new MD5().getMD5ofStr(bean.getUserpwd())); // md5加密

		// 验证email
		if (bean.getEmail() == null || "".equals(bean.getEmail())) {
			throw new Exception("email不能为空！");
		} else if (!StringHandle.regexMacher(".+@.+", bean.getEmail())) {
			throw new Exception("不符合email的规则！");
		}
	}
}
