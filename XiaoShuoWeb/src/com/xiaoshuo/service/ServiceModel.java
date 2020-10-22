package com.xiaoshuo.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.jspsmart.upload.Request;
import com.xiaoshuo.common.FileHandle;
import com.xiaoshuo.common.HtmlHandle;
import com.xiaoshuo.common.Pagination;
import com.xiaoshuo.common.StringHandle;
import com.xiaoshuo.db.MysqlUtils;

public abstract class ServiceModel<T> {
	protected List<String> fieldsArr = new ArrayList<String>(); // bean所有字段
	protected String tName = null; // 表名
	protected String where = ""; // 查询条件
	protected String kw = null; // 搜索关键字
	protected String URL1 = null; // 成功URL
	protected String URL2 = null; // 失败URL
	protected String msg1 = null; // 显示信息
	protected String msg2 = null; // 显示信息
	protected String limit = "jpg,png,gif,jpeg"; // 上传文件扩展名
	protected String uploadFieldName = ""; // 上传文件的字段名
	protected MysqlUtils mysql = null; // 数据库对象
	protected T bean = null; // bean对象
	protected String jsonData = null; // json数据
	protected Boolean flag = false; // 标记
	protected PrintWriter out = null; // 输出对象
	protected String searchFields = null; // 模糊搜索关键字列表
	protected Integer p = 1; // 页码
	protected Integer pageSize = 10; // 每页记录条数

	// 添加
	public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		msg1 = "添加失败!";
		out = response.getWriter();
		try {
			mysql = new MysqlUtils();
			FileHandle fileHandle = new FileHandle(); // 文件上传类，可以获得文件和域值
			fileHandle.upload(request, response, limit);
			Map<String, String[]> dataMap = fileHandle.getParameterMap();
			dataMap.put(uploadFieldName, new String[] { fileHandle.getUploadPath() });
			BeanUtils.populate(bean, dataMap);

			// 获得bean有值的字段名和值，并存入map中
			Map<String, String> map = getFieldsAndValues(false);
			Object[] fields = map.keySet().toArray();
			Object[] values = map.values().toArray();

			if (mysql.insert(tName, fields, values)) {
				msg1 = "添加成功！";
				flag = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			jsonData = String.format("{\"flag\":%s}", flag);
			if (mysql != null)
				mysql.close();
			HtmlHandle.alertBox(out, msg1, URL1);
		}
	}

	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException {
		out = response.getWriter();
		msg1 = "删除失败!";
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			mysql = new MysqlUtils();
			if (mysql.delete(tName, id)) {
				flag = true;
				msg1 = "删除成功!";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jsonData = String.format("{\"flag\":%s}", flag);
			if (mysql != null)
				mysql.close();
			HtmlHandle.alertBox(out, msg1, URL1);
		}
	}

	public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, String> map = null;
		try {
			FileHandle fileHandle = new FileHandle(); // 文件上传类，可以获得文件和域值
			fileHandle.upload(request, response, limit);
			Map<String, String[]> dataMap = fileHandle.getParameterMap();
			if (fileHandle.getUploadPath() != null && !"".equals(fileHandle.getUploadPath())) {
				dataMap.put(uploadFieldName, new String[] { fileHandle.getUploadPath() });
			}
			BeanUtils.populate(bean, dataMap);
			map = getFieldsAndValues(true);
			where = "id=" + map.get("id");
			Object[] fields = map.keySet().toArray();
			Object[] values = map.values().toArray();

			mysql = new MysqlUtils();
			// 调用数据库操作中的修改方法
			if (!mysql.update(tName, fields, values, where)) {
				throw new Exception();
			}
			HtmlHandle.alertBox(response.getWriter(), msg1, URL1);

		} catch (Exception e) {

		}
	}

	public void find(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute("page", findRows(request, response));
		request.setAttribute("kw", kw);
		try {
			request.getRequestDispatcher(URL1).forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ajaxFind(HttpServletRequest request, HttpServletResponse response) throws IOException {
		findRows(request, response);
		response.getWriter().print(jsonData);
	}

	private Pagination findRows(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = null;
		Pagination page = null;
		try {
			BeanUtils.populate(bean, request.getParameterMap());
			map = getFieldsAndValues(true);

			List<String> whereField = new ArrayList<String>();
			for (String key : map.keySet()) {
				// where += String.format(" %s=%s and ", key, map.get(key));
				whereField.add(String.format(" %s=%s", key, map.get(key)));
			}
			if (whereField.size() > 0) {
				where += String.join(" and ", whereField);
				where+=" and ";
			}

			if (map.keySet().size() > 0) {
				where = " where " + where;
			} else {
				where = " where ";
			}

			String paraStr = ""; // 查询参数
			String limit = ""; // 分页参数
			Integer id = 0;
			// 获得关键字
			kw = request.getParameter("kw") == null ? "" : request.getParameter("kw");

			try {
				id = Integer.parseInt(kw);
				where += " id=" + id;
			} catch (Exception e) {

				String[] searchFieldArr = searchFields.split(",");
				List<String> list = new ArrayList<String>();
				for (String string : searchFieldArr) {
					list.add(string + " regexp '.*" + kw + ".*' ");
				}
				String val = String.join(" or ", StringHandle.objectArrayToStringArray(list.toArray()));

				where += "(" + val + ")";

			}

			try {
				p = Integer.parseInt(request.getParameter("p")) <= 0 ? 1 : Integer.parseInt(request.getParameter("p"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			limit = String.format(" limit %s,%s", (p - 1) * pageSize, pageSize);

			paraStr = where + limit;

			mysql = new MysqlUtils();
			List<Map<String, Object>> rows = mysql.select(tName, paraStr);
			// 获得总条数
			int rowsCount = mysql.select(tName, where).size();

			// 分页对象
			page = new Pagination(p, rowsCount, rows, pageSize);
			jsonData = JSON.toJSONString(page);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (mysql != null)
				mysql.close();
		}
		return page;
	}

	public void one(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			BeanUtils.populate(bean, request.getParameterMap());
			Map<String, String> map = getFieldsAndValues(true);
			mysql = new MysqlUtils();
			String where = String.format("where id=%s", map.get("id"));
			Map<String, Object> row = mysql.select(tName, where).get(0);
			request.setAttribute("row", row);
			request.getRequestDispatcher(URL1).forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (mysql != null)
				mysql.close();
		}
	}

	public void isExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		out = response.getWriter();
		msg1 = "<font color=\"red\">不能为空！</font>";
		try {
			BeanUtils.populate(bean, request.getParameterMap());
			Map<String, String> map = getFieldsAndValues(true);
			String field = map.keySet().toArray()[0].toString();
			String value = map.values().toArray()[0].toString();
			mysql = new MysqlUtils();
			if (value == null || "".equals(value.trim())) {
				throw new Exception("<font color=\\\"red\\\">不能为空！</font>");
			}

			where = String.format("%s=%s", field, value);

			if (mysql.isExists(tName, where)) {
				throw new Exception("<font color=\\\"red\\\">已存在！</font>");
			} else {
				msg1 = "<font color=\\\"green\\\">可用</font>";
			}
		} catch (Exception e) {
			// TODO: handle exception
			msg1 = e.getMessage();
		} finally {
			if (mysql != null)
				mysql.close();
			out.print("{\"message\":\"" + msg1 + "\"}");
		}
	}

	// 获得bean的字段和值
	private Map<String, String> getFieldsAndValues(Boolean useID) {
		Map<String, String> map = new HashMap<String, String>();
		Class<?> cls = bean.getClass();
		Field[] fields = cls.getDeclaredFields();
		String key = null;
		Object value = null;
		try {
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				key = fields[i].getName();
				value = fields[i].get(bean);
				fieldsArr.add(key); // 获得所有字段

				// 判断id或没有值的字段
				if ("id".equalsIgnoreCase(key) && !useID || value == null) {
					continue;
				}
				map.put(key, value.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}
}
