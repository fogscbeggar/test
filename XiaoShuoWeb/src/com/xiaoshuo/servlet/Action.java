package com.xiaoshuo.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoshuo.factory.ServiceFactory;

/**
 * Servlet implementation class Action
 */

public class Action extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Action() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 获得当前访问路径
			String path = request.getServletPath();
			String className = path.split("/")[1]; // 从路径获得类的名称
			String methodName = path.split("/")[2].split("\\.")[0]; // 从路径中获得方法名称
			Object obj = null;
			// 根据不同类的名称，创建不同的对象
			obj = ServiceFactory.createServiceObject(className);
			// 通过对象获得，指定的方法
			Method method = obj.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			// 调用方法
			method.invoke(obj, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.getWriter().print("操作异常！");
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
