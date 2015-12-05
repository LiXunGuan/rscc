package com.ruishengtech.framework.core.db.condition;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Frank
 *
 */
public class Page implements ICondition {

	private HttpServletRequest request;

	private HttpServletResponse response;

	/**
	 * 从第几条数据开始
	 */
	private Integer start;

	/**
	 * 排序的列
	 */
	private String sortColumn;

	/**
	 * 排序的方向 asc or desc
	 */
	private String sortColumnDir;

	/**
	 * 每页有多少条数据
	 */
	private Integer length;

	private Integer numLimit;

	private List<String[]> orderColumn = new ArrayList<String[]>();

	/**
	 * 取到多列排序
	 * @param condition
	 * @return
	 */
	public List<String[]> getOrderColumn(ICondition condition) {

		for (int i = 0; i < condition.getClass().getDeclaredFields().length; i++) {

			if (StringUtils.isNotBlank(request.getParameter("columns["
					+ request.getParameter("order[" + i + "][column]")
					+ "][data]"))) {
				String colName = request.getParameter("columns["
						+ request.getParameter("order[" + i + "][column]")
						+ "][data]");
				String desType = request.getParameter("order[" + i + "][dir]");
				orderColumn.add(new String[] { colName, desType });
			}
		}

		return orderColumn;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortColumnDir() {
		return sortColumnDir;
	}

	public void setSortColumnDir(String sortColumnDir) {
		this.sortColumnDir = sortColumnDir;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public Integer getNumLimit() {
		return numLimit;
	}

	public void setNumLimit(Integer numLimit) {
		this.numLimit = numLimit;
	}

}
