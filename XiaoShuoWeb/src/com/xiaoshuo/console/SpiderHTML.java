package com.xiaoshuo.console;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;

import com.xiaoshuo.common.StringHandle;

public class SpiderHTML {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * 1、创建url对象
		 * 2、打开连接
		 * 3、读取流
		 * 4、存储内容
		 * 5、在内容中使用正则表达式匹配需要的“超链接”数据
		 * 6、获得小说章节
		 * 7、获得小说内容
		 * 8、跳转到下一篇小说，再重复执行前面的步骤
		 * */
		try {
			String content=getContent("https://www.qidian.com/all?orderId=&page=1&vip=0&style=1&pageSize=20&siteid=1&pubflag=0&hiddenField=0");
	
			//小说正则
			String regex="<h4><a href=\"//(.*?)\".*?</h4>";
			//章节正则
			String regex1="<li data-rid=\".*?\"><a href=\"//(.*?)\".*?>(.*?)</a>.*?</li>";
			//内容正则
			String regex2="<div class=\"read-content j_readContent \">(.*?)</div>";
			
			Matcher m=StringHandle.regex(content.toString(), regex);
			while(m.find()) {
				content=getContent("https://"+m.group(1));
				Matcher m1=StringHandle.regex(content, regex1); 
				while(m1.find()) {
					if(m1.group(1).indexOf("www")==-1) {
						//System.out.println(m1.group(1));
						content=getContent("https://"+m1.group(1));
						Matcher m2=StringHandle.regex(content, regex2);
						System.out.println(content);
						System.out.println(m2.find());
						System.out.println(m2.group(1));
					}
				}
				System.out.println(m.group(1));
			}
//			m.find();
//			content=getContent("https://"+m.group(1));
			//<li data-rid="66"><a href="//read.qidian.com/chapter/eane5WtLwDf36JmDw--oJQ2/aB7SaEC6KxVOBDFlr9quQA2" target="_blank" data-eid="qd_G55" data-cid="//read.qidian.com/chapter/eane5WtLwDf36JmDw--oJQ2/aB7SaEC6KxVOBDFlr9quQA2" title="首发时间：2020-10-16 18:03:54 章节字数：2963">第六十六章 哈士奇的邀请</a>
            
        //</li>
		} catch (Exception e) {
			// TODO: handle exception
		}	
		
	}
	
	private static String getContent(String urlStr) {
		try {
			//1、创建url对象
			URL url=new URL(urlStr);
			//2、打开连接
			HttpURLConnection conn= (HttpURLConnection)url.openConnection();
			//3、读取流
			InputStreamReader in= new InputStreamReader(conn.getInputStream(), "utf-8");
			BufferedReader reader=new BufferedReader(in);
			//4、存储内容
			StringBuilder sb=new StringBuilder();
			String data=null;
			while((data=reader.readLine())!=null) {
				sb.append(data);
			}
			reader.close();
			in.close();
			return sb.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

}
