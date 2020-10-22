package com.xiaoshuo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;
import com.xiaoshuo.common.StringHandle;

/**
 * 数据库操作实体类
 * @author Administrator
 *
 */
public final class MysqlUtils {
	private Connection conn;
	private PreparedStatement preStat;
	private ResultSet result;
	//数据库连接代码块
	{
		//加载资料文件，注意只需要设置文件名，不要加扩展名
		ResourceBundle resourceBundle=ResourceBundle.getBundle("db");
		String driver=resourceBundle.getString("driver");
		String url=resourceBundle.getString("url");
		String user=resourceBundle.getString("user");
		String password=resourceBundle.getString("password");
		try {
			Class.forName(driver);
			conn= DriverManager.getConnection(url, user, password);
			conn.setCatalog("xiaoshuodb");  //设置数据库
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/***
	 * 添加数据
	 * @param tName 表名
	 * @param fieldsArr 字段名数组
	 * @param valueArr 字段值数组
	 * @return 添加成功返回true，否则返回false
	 */
	public Boolean insert(String tName,Object[] fieldsArr,Object[] valueArr) {
		boolean flag=false;
		if(fieldsArr.length==valueArr.length) {
			String fieldsStr=String.join(",", StringHandle.objectArrayToStringArray(fieldsArr));
			String valueStr="'"+String.join("','", StringHandle.objectArrayToStringArray(valueArr))+"'";
			
			String sql=String.format("insert into %s(%s)values(%s)", tName,fieldsStr,valueStr);
			if(run(sql)>0) {
				flag=true;
			}else {
				flag=false;
			}
		}
		return flag;
	}
	
	/**
	 * 单行删除数据
	 * @param tName 表名
	 * @param id 主键id
	 * @return 删除成功返回true，否则返回false
	 */
	public Boolean delete(String tName,Integer id) {
		boolean flag=false;
		String sql=String.format("delete from %s where id=%d", tName,id);
		if(run(sql)>0) {
			flag=true;
		}else {
			flag=false;
		}
		return flag;
	}
	
	/**
	 * 批量删除数据
	 * @param tName 表名
	 * @param ids 主键id字符串数组
	 */
	public void delete(String tName,String[] ids) {
		String idsStr=String.join(",", ids);
		String sql=String.format("delete from %s where id in (%s)", tName,idsStr);
		run(sql);
	}
	
	/**
	 * 更新行数据
	 * @param tName 表名
	 * @param fieldsArr 需要更新的字段名数组
	 * @param valuesArr 需要更新的字段值数组，必须与字段数组个数相同
	 * @param where 更新条件 例：姓名='jack'
	 * @return 更新成功返回true，否则返回false
	 */
	public boolean update(String tName,Object[] fieldsArr,Object[] valuesArr,String where) {
		List<String> para=new ArrayList<String>();
		boolean flag=false;
		if(fieldsArr.length==valuesArr.length) {
			for(int i=0;i<fieldsArr.length;i++) {
				para.add(String.format("%s='%s'", fieldsArr[i],valuesArr[i]));
			}
			where=where==null||"".equals(where)?"":" where "+where;
			String sql=String.format("update %s set %s %s", tName,String.join(",", para),where);
			
			if(run(sql)>0)flag=true;
		}
		return flag;
	}
	
	/**
	 * 查询数据
	 * @param tName 表名
	 * @param paraStr select子句，可以是任意的子句
	 * @return 返回记录list集合，每一个元素为map，调用map时使用字段名输出字段值
	 */
	public List<Map<String, Object>> select(String tName,String paraStr) {
		if(paraStr==null)paraStr="";
		String sql=String.format("select * from %s %s", tName,paraStr);
		System.out.println(sql);
		result=runSelect(sql);
		List<String> colNameList=new ArrayList<String>();
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			for(int i=1;i<=result.getMetaData().getColumnCount();i++) {
				colNameList.add(result.getMetaData().getColumnName(i));
			}
			
			while(result.next()) {
				Map<String, Object> map=new HashMap<String, Object>();
				for(String name:colNameList) {
					map.put(name, result.getObject(name));
				}
				list.add(map);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获得指定表的所有记录条数
	 * @param tName 表名
	 * @return 条数
	 */
	public int getCount(String tName) {
		int count=0;
		result=runSelect("select count(id) cnt from "+tName);
		try {
			result.next();
			count=result.getInt("cnt");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 判断满足条件的记录是否存在 
	 * @param tName 表名
	 * @param where 查询条件
	 * @return 存在返回true，否则返回false
	 */
	public boolean isExists(String tName,String where) {
		boolean flag=false;
		if(where!=null||"".equals(where)) {
			where=where==null||"".equals(where)?"":" where "+where;
			result=runSelect(String.format("select count(id) cnt from %s %s", tName,where));
			try {
				result.next();
				flag=result.getInt("cnt")==1?true:false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	@SuppressWarnings("unused")
	private Integer run(String sql) {
		System.out.println(sql);
		Integer flag=0;
		PreparedStatement preStat = null;
		try {
			preStat = (PreparedStatement) conn.prepareStatement(sql);
			flag= preStat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	@SuppressWarnings("unused")
	private ResultSet runSelect(String sql) {
		try {
			preStat = (PreparedStatement) conn.prepareStatement(sql);
			result= preStat.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void close() {

		try {
			if(result!=null)result.close();
			if(preStat!=null)preStat.close();
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
