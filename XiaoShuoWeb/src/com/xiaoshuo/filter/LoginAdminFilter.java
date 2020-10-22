package com.xiaoshuo.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xiaoshuo.common.StringHandle;

/**
 * Servlet Filter implementation class LoginAdminFilter
 */
@WebFilter("/admin/*")
public class LoginAdminFilter implements Filter {
	// 保存不需要过滤文件的正则表达式
	List<String> filterList = new ArrayList<String>();
	
	{
		// 加载filter.properties配置文件
		ResourceBundle bundle = ResourceBundle.getBundle("filter");
		// 读取键名为FilterFile的值
		String filterFile = bundle.getString("FilterFile");
		// 构建正则字符串
		filterFile = ".*" + filterFile.replace(",", ".*,.*") + ".*";
		// 分离每一个文件正则表达式
		filterList = Arrays.asList(filterFile.split(","));
	}

	/**
	 * Default constructor.
	 */
	public LoginAdminFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 获得当前访问的页面路径
		String path = ((HttpServletRequest) request).getServletPath();
		boolean flag = false; // 用于判断是否需要过滤
		// 将列表集合中的正则依次与文件路径进行匹配
		for (String reg : filterList) {
			// 如果匹配成功，则不需要过滤
			if (StringHandle.regexMacher(reg, path)) {
				flag = true; // 不需要过滤的标识
				break;
			}
		}

		// 如果flag为false表示需要过滤
		if (!flag) {
			// 获得session，因为过滤时，要判断当前是否已经登录，未登录则转向登录页面
			HttpSession session = ((HttpServletRequest) request).getSession();
			// 判断session
			if (session == null || session.getAttribute("loginname") == null
					|| "".equals(session.getAttribute("admin_username"))) {
				//转向到登录页面
				//((HttpServletResponse) response).sendRedirect("login.html");
				//return;
			}
		}

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
