package com.xiaoshuo.service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.xiaoshuo.common.FileHandle;

public class Detail {
	private String id;

	public String content() {
		String data=null;
		try {
			//获得网站
			String path=URLDecoder.decode(this.getClass().getClassLoader().getResource("").getPath(),"utf-8").replace("WEB-INF/classes/", "");
			List<String> list=FileHandle.readFile(path+"novelTxt/1991从芯开始/第一章 意难平.txt");
			data=list.get(0);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
