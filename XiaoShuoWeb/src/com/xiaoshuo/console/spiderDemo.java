package com.xiaoshuo.console;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.xiaoshuo.spider.BookSpider;

public class spiderDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 1; i <= 5; i++) {
			String strURL = "https://www.qidian.com/all?orderId=&page="+i+"&vip=0&style=1&pageSize=20&siteid=1&pubflag=0&hiddenField=0";
			//<h4><a href="//book.qidian.com/info/1023639215" target="_blank" data-eid="qd_B58" data-bid="1023639215">大秦之系统骗我在仙侠世界</a></h4>
			String regex="<h4><a href=\"//(.*?)\".*?>(.*?)</a></h4>";
			BookSpider spider=new BookSpider(strURL,regex);  //线程对象
			
			 //使用Executors工具类中的方法创建线程池
	        ExecutorService pool = Executors.newFixedThreadPool(5);
	        //为线程池中的线程分配任务,使用submit方法，传入的参数可以是Runnable的实现类，也可以是Callable的实现类
	        pool.submit(spider);
	        //关闭线程池
	        //shutdown ： 以一种平和的方式关闭线程池，在关闭线程池之前，会等待线程池中的所有的任务都结束，不在接受新任务
	        //shutdownNow ： 立即关闭线程池
	        pool.shutdown();
		}
		
	}
}
