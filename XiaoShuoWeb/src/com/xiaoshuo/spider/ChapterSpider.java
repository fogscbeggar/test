package com.xiaoshuo.spider;

//章节蜘蛛
public class ChapterSpider implements Runnable {
	private String regex;
	private String urlAddress;
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
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
