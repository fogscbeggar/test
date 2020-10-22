package com.xiaoshuo.spider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.xiaoshuo.common.FileHandle;
import com.xiaoshuo.common.StringHandle;

//小说蜘蛛
public class BookSpider implements Runnable {

	private String regex;
	private String urlAddress;
	private String charset = "utf-8"; // 编码
	public static List<String> list = new ArrayList<String>();
	private static int i=1;

	public BookSpider() {

	}

	public BookSpider(String urlAddress, String regex) {
		this.urlAddress = urlAddress;
		this.regex = regex;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String content = getContent(urlAddress);
			//章节正则
			//<li data-rid="1"><a href="//read.qidian.com/chapter/6fHlmQBJ4-bLDxeCJGEX4g2/UvDxn6t6YDa2uJcMpdsVgA2" target="_blank" data-eid="qd_G55" data-cid="//read.qidian.com/chapter/6fHlmQBJ4-bLDxeCJGEX4g2/UvDxn6t6YDa2uJcMpdsVgA2" title="首发时间：2020-09-11 10:41:24 章节字数：2063">第1章 诶，仙侠世界也有秦始皇？！</a>
            
        //</li>
			String regex1="<li data-rid=\".*?\"><a href=\"//(.*?)\".*?>(.*?)</a>.*?</li>";
			//内容正则
			String regex2="<div class=\"read-content j_readContent \">(.*?)</div>";
			
			Matcher m = StringHandle.regex(content, regex);
			while (m.find()) {
				content=getContent("https://"+m.group(1));
				System.out.println(m.group(2));
				String path="d:/aa/"+m.group(2);
				File file=new File(path);
				FileHandle.mkDir(file);
				Matcher m1=StringHandle.regex(content, regex1);
				while(m1.find()) {
					if(m1.group(1).indexOf("www")==-1) {
						String fullPath=path+"/"+m1.group(2).replaceAll("[/\\?#$%^&\\*]", "").replace("\\", "")+".txt";
						file=new File(fullPath);
						if(!file.exists()) {
							FileOutputStream fos=new FileOutputStream(file);
							//System.out.println(m1.group(1));
							content=getContent("https://"+m1.group(1));
							Matcher m2=StringHandle.regex(content, regex2);
							m2.find();
							//System.out.println(m2.group(1));
							fos.write(m2.group(1).getBytes());							
						}
					}
				}
				
				
				i++;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			//每执行完后显示
			System.out.println(list.size());
		}

	}

	private String getContent(String urlStr) {
		try {
			// 1、创建url对象
			URL url = new URL(urlStr);
			// 2、打开连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 3、读取流
			InputStreamReader in = new InputStreamReader(conn.getInputStream(), charset);
			BufferedReader reader = new BufferedReader(in);
			// 4、存储内容
			StringBuilder sb = new StringBuilder();
			String data = null;
			while ((data = reader.readLine()) != null) {
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

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getUrlAddress() {
		return urlAddress;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

}
