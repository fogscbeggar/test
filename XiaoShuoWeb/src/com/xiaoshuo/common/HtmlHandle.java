package com.xiaoshuo.common;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.jspsmart.upload.Request;

public class HtmlHandle {
	
	//获得文件名或servlet名
	public static String getCurFileName(HttpServletRequest request) {
		String path=request.getServletPath();
		return path.substring(path.lastIndexOf("/")+1);
	}
	
	/**
	 * 获得当前网站的物理路径
	 * @param request
	 * @return
	 */
	public static String getSitePhysicalPath(HttpServletRequest request) {
		return request.getServletContext().getRealPath(File.separator);
	}
	
	/**
	 * 获得当前网站虚拟路径
	 * @param request
	 * @return
	 */
	public static String getDomain(HttpServletRequest request) {
		String urlString=request.getRequestURL().toString();
        return urlString.replace(request.getServletPath(), "");
	}

	
	/**
	 * 获得提交的所有参数数据
	 * 
	 * @param request
	 * @return 返回map
	 */
	public static Map<String, String[]> getParameterValues(HttpServletRequest request) {
		Map<String, String[]> valueMap = new HashMap<String, String[]>();

		// 获得参数名称数组
		Enumeration<String> enumeration = request.getParameterNames();

		// 遍历
		while (enumeration.hasMoreElements()) {
			// 取每一个参数的值
			String name = (String) enumeration.nextElement();
			// 将参数添加到map
			valueMap.put(name, request.getParameter(name).split(","));
		}
		return valueMap; // 返回map
	}

	/**
	 * 分页html
	 * 
	 * @param curPage   当前页码
	 * @param pageCount 总页数
	 * @return
	 */
	public static String getPageListHtml(int curPage, int pageCount, String keywords) {
		StringBuilder html = new StringBuilder();
		html.append(String.format("<a href=\"?p=1&kw=%s\">首页</a> ", keywords));
		html.append(String.format("<a href=\"?p=%d&kw=%s\">上一页</a> ", (curPage <= 0 ? 1 : curPage - 1), keywords));
		for (int i = 1; i <= pageCount; i++) {
			if (i == curPage) {
				html.append(String.format("<span class=\"current\">%d</span> ", i));
			} else {
				html.append(String.format("<a href=\"?p=%d&kw=%s\">%d</a> ", i, keywords, i));
			}
		}

		html.append(String.format("<a href=\"?p=%d&kw=%s\">下一页</a>", (curPage > pageCount ? pageCount : curPage + 1),keywords));

		html.append(String.format("<a href=\"?p=%d&kw=%s\">尾页</a> ", pageCount,keywords));

		return html.toString();
	}

	/**
	 * 弹出对话框，不转向
	 * 
	 * @param out 页面输出对象
	 * @param msg 需要弹出的信息
	 */
	public static void alertBox(PrintWriter out, String msg) {
		try {
			out.print("<script>alert('" + msg + "');</script>);");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 弹出对话框，并转向
	 * 
	 * @param out         页面输出对象
	 * @param msg         需要弹出的信息
	 * @param redirectURL 转向地址
	 */
	public static void alertBox(PrintWriter out, String msg, String redirectURL) {
		// TODO 自动生成的方法存根
		try {
			out.print("<script>alert('" + msg + "');location.href='" + redirectURL + "';</script>);");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
