package com.xiaoshuo.builder;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.xiaoshuo.common.StringHandle;
import com.xiaoshuo.utils.DataSourceUtils;
import com.xiaoshuo.utils.DatabaseUtils;

/**
 * Bean生成器，自动生成
 * 
 * @author Administrator
 *
 */
public class BeanBuilder {
	static String beanPath = null;  //bean相对路径
	static String physicalPath = null;  //bean物理路径
	static String dbName = null;  //数据库名
	static {
		//加载config.properties文件
		ResourceBundle bundle = ResourceBundle.getBundle("config");
		//读取对应键名的值
		beanPath = bundle.getString("beanpath");
		//读取数据库名
		dbName = bundle.getString("dbname");
		//构建物理路径
		String path=beanPath.replace(".", "/");
		physicalPath = System.getProperty("user.dir") + "/src/" + path + "/";
	}

	public static boolean Building() {
		DatabaseUtils db = null;  //声明数据库操作对象
		boolean isOk = false;
		try {
			db = new DatabaseUtils();// 实例化数据库操作对象
			db.setDataSource(DataSourceUtils.getDataSource());  //设置数据源
			String fieldName = ""; // 存储字段名
			String fieldType = ""; // 存储字段类型
			String tableName = null; // 表名
			File file = null;  //文件对象声明

			// 获得所有表"select table_name from information_schema.tables where table_schema='数据库名'"
			List<Map<String, Object>> list = db
					.find("select table_name from information_schema.tables where table_schema='" + dbName + "'");
			
			// 遍历所有表
			for (int i = 0; i < list.size(); i++) {
				// 获得表名
				tableName = list.get(i).get("TABLE_NAME").toString();
				// 注意修改“config.properties”中的路径来设置bean所在文件夹
				file = new File(physicalPath + StringHandle.firstLetterToUpper(tableName) + ".java");// 根据当前项目路径创建bean文件
				// 创建文件写入流
				FileWriter fw = new FileWriter(file);
				// 字符串生成器
				StringBuilder sb = new StringBuilder();
				//构建包路径
				String packagePath = beanPath;
				sb.append("package " + packagePath + ";\r\n");
				sb.append(String.format("public class %s {\r\n", StringHandle.firstLetterToUpper(tableName)));
				// 查询出当前表名的所有字段信息
				List<Map<String, Object>> fields = db.find("desc " + tableName);

				// 遍历当前表中的所有字段
				for (int j = 0; j < fields.size(); j++) {
					// 获得字段名
					fieldName = fields.get(j).get("Field").toString();
					// 获得字段类型
					fieldType = fields.get(j).get("Type").toString().indexOf("int") < 0 ? "String" : "Integer";

					sb.append(String.format("private %s %s;\r\n", fieldType, fieldName));
					sb.append(String.format("public %s get%s(){\r\n", fieldType,
							StringHandle.firstLetterToUpper(fieldName)));
					sb.append(String.format("return %s;\r\n", fieldName));
					sb.append("}\r\n");
					sb.append(String.format("public void set%s(%s %s){\r\n", StringHandle.firstLetterToUpper(fieldName),
							fieldType, fieldName));
					sb.append(String.format("\tthis.%s = %s;\r\n", fieldName, fieldName));
					sb.append("}\r\n");
				}
				sb.append("}\r\n");
				// 将sb生成器中的字符串写入指定文件中，生成model文件
				fw.write(sb.toString());
				fw.close(); // 关闭写入
				System.out.println(file.getPath() + "文件已生成");
			}
			isOk = true;
		} catch (Exception e) {
			e.printStackTrace();
			isOk = false;
		}
		return isOk;
	}
}
