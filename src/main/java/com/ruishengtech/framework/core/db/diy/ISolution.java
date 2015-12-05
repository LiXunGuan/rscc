package com.ruishengtech.framework.core.db.diy;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Frank
 *
 */
public interface ISolution {
	
    /**
     * 拼接查询条件
     * @param strs
     * @param whereSql
     * @param request
     */
    public void getWhere(Map<String, ColumnDesign> str, StringBuilder whereSql, HttpServletRequest request, List<Object> params);

    /**
     * 查询条数
     * @param countSql
     * @param whereSql
     */
    public void getCountSql(StringBuilder countSql, StringBuilder whereSql);

    /**
     * 带分页的
     * @param pageSql
     * @param whereSql
     * @param strs
     * @param params
     */
    public void getPageSql(StringBuilder pageSql, StringBuilder whereSql, Map<String, ColumnDesign> str, HttpServletRequest request);

}
