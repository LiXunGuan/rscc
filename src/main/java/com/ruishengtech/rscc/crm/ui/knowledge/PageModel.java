package com.ruishengtech.rscc.crm.ui.knowledge;

import javax.servlet.http.HttpServletRequest;

public class PageModel {
	private int pageNum=0;    //当前页
	private int pageSize=10;  //每页大小
	private int countPage=0;  //总页数
	private int countRecord=0;//总记录数
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getCountPage() {
		countPage = countRecord % pageSize == 0 ? countRecord / pageSize : countRecord / pageSize + 1;
		return countPage;
	}
	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}
	public int getCountRecord() {
		return countRecord;
	}
	public void setCountRecord(int countRecord) {
		this.countRecord = countRecord;
	}
	public void checkPageNum(HttpServletRequest req) {
		if(req.getParameter("pageNum") == null || "".equals(req.getParameter("pageNum"))){
			pageNum = 0;
		}else if(Integer.parseInt(req.getParameter("pageNum")) < 0){
			pageNum = 0;
		}else if(Integer.parseInt(req.getParameter("pageNum")) >= getCountPage()){
			pageNum = getCountPage();
		}else{
			pageNum = Integer.parseInt(req.getParameter("pageNum"));
		}
	}
	public void checkErrorPageNum(HttpServletRequest req, String point, String operation) {
		if(req.getParameter(point+"pageNum") == null || "".equals(req.getParameter(point+"pageNum"))){
			if("query".equals(operation)){
				pageNum = 0;
			}else{
				if(req.getSession().getAttribute(point+"pageNum") == null || "".equals(req.getSession().getAttribute(point+"pageNum"))){
					pageNum = 0;
				}else{
					pageNum = (Integer) req.getSession().getAttribute(point+"pageNum") <= 0 ? 0 : (Integer) req.getSession().getAttribute(point+"pageNum");
				}
			}
		}else if(Integer.parseInt(req.getParameter(point+"pageNum")) < 0){
			pageNum = 0;
		}else if(Integer.parseInt(req.getParameter(point+"pageNum"))>= getCountPage()){
			pageNum = getCountPage();
		}else{
			pageNum = Integer.parseInt(req.getParameter(point+"pageNum"));
		}
	}
	
}
