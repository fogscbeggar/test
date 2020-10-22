package com.xiaoshuo.common;

import java.util.List;
import java.util.Map;

import com.sun.security.auth.NTDomainPrincipal;

/**
 * 分页
 * @author Administrator
 *
 */
public class Pagination {
	private int curPage; //当前页
	private int prePage; //上一页
	private int nextPage; //下一页
	private int first=1; //首页
	private int last; //末页
	private int startPage;  //开始页码
	private int endPage;  //结束页码
	private int curPageCount=10;   //显示多少个页 
	private int totalPage;  //总页数
	private int totalRows;  //总条数
	private int pageSize=10;   //每页条数，默认为10条
	private List<Map<String, Object>> rows; //记录集合
	
	public Pagination(Integer p,int totalRows,List<Map<String, Object>> rows){
		//当前页
		this.curPage=p;
		
		//总条数
		this.totalRows=totalRows;
		
		//计算总页数
		totalPage=(int)Math.ceil(totalRows/(double)pageSize);
		
		//上一页
		prePage=(p-1)<=1?1:(p-1);
		
		//下一页
		nextPage=(p+1)>totalPage?totalPage:(p+1);
		
		//最后一页
		last=totalPage;
		
		//当前记录
		this.rows=rows;
	}

	public Pagination(Integer p,int totalRows,List<Map<String, Object>> rows,int pageSize) {
		//当前页
		this.curPage=p;
		
		//总条数
		this.totalRows=totalRows;
		
		//设置每页条数
		this.pageSize=pageSize;
		
		//计算总页数
		totalPage=(int)Math.ceil((double)totalRows/(double)pageSize);
		
		//上一页
		prePage=(p-1)<=1?1:(p-1);
		
		//下一页
		nextPage=(p+1)>totalPage?totalPage:(p+1);
		
		//最后一页
		this.last=totalPage;
		
		//开始页码
		if(totalPage>10) {  //如果总页数大于10页
			if(totalPage-p<curPageCount/2-1) {
				startPage=totalPage-curPageCount+1;   //开始页码
				endPage=totalPage;  //结束页码
			}else {
				if(p<curPageCount/2+1) {
					startPage=1;
					endPage=curPageCount;
				}else {
					startPage=p-curPageCount/2;
					endPage=p+curPageCount/2-1;
				}
				
			}
		}else {  //否则总页数小于10页，就全部显示页码
			startPage=1;
			endPage=totalPage;
		}
		
		//当前记录
		this.rows=rows;
	}
	public int getCurPage() {
		return curPage;
	}
	public int getPrePage() {
		return prePage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public int getFirst() {
		return first;
	}
	public int getLast() {
		return last;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}
	public int getTotalPage() {
		last=totalPage;
		return totalPage;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public int getPageSize() {
		return pageSize;
	}
	public List<Map<String, Object>> getRows() {
		return rows;
	}
	
}
