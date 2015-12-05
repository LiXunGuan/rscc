package com.ruishengtech.rscc.crm.cstm.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.rscc.crm.cstm.condition.CustomerDesignCondition;
import com.ruishengtech.rscc.crm.cstm.model.CstmTable;

/**
 * @author Frank
 *
 */
public interface CustomerDesignService {

	
	/**
	 * 添加咧
	 * @param string
	 * @return 
	 * @throws Exception 
	 */
	public abstract void alterAdd(ColumnDesign customerDesign) throws Exception;

	/**
	 * 删除咧
	 * @param string
	 * @return 
	 */
	public abstract Integer alterDrop(String cstmId);
	
	/**
	 * 删除
	 * @param uuid
	 */
	public abstract void delete(UUID uuid);
	
	/**
	 * 查询添加的列
	 */
	public Map<String, ColumnDesign>  getAddedColumn();
	
	/**
	 * 查询指定表所有的列
	 * tableName 查询的表名
	 */
	public Map<String, ColumnDesign>  getAddedColumn(String tableName);
	
	public abstract PageResult<ColumnDesign> queryPage(CustomerDesignCondition condition);

	public abstract String getColumnName();
	/**
	 * 查询单个对象
	 * @param id
	 * @return
	 */
	public ColumnDesign getCustomerDesign(String id);
	
	/**
	 * 更新
	 * @param columnDesign
	 * @param str
	 */
	public void update(ColumnDesign columnDesign, String[] str);
	
	/**
	 * 保存
	 * @param columnDesign
	 * @param str
	 */
	public void save(ColumnDesign columnDesign, String[] str);
	
	
	/**
	 * 赋值
	 * @param columnDesign
	 * @param request
	 */
	public void setValue(ColumnDesign columnDesign, HttpServletRequest request);
	
	
	/**
	 * 查询不是默认的列的列
	 */
	public Map<String, ColumnDesign> getNotDefaultColumn();
	
	/**
	 * 根据列名查询客服信息
	 * @param columnName
	 * @return
	 */
	public ColumnDesign getCstmserviceByColumnName(String columnName);
	
	/**
	 * 校验列明重复
	 * @param request
	 * @param response
	 * @param columnName
	 * @throws java.io.IOException
	 */
	public void checkNameRepeat(HttpServletRequest request, HttpServletResponse response, String columnName) throws IOException;
	
	/**
	 * 校验顺序重复
	 * @param request
	 * @param response
	 * @param orders
	 * @throws java.io.IOException
	 */
	public void checkOrdersRepeat(HttpServletRequest request, HttpServletResponse response, String orders) throws IOException;
	
	/**
	 * 长度判断
	 * @param request
	 * @param response
	 * @param orders
	 * @throws java.io.IOException
	 */
	public void comparelength(HttpServletRequest request, HttpServletResponse response, String orders) throws IOException;
	
	
	/**
	 * 修改顺序
	 * @param uid
	 * @param orders
	 * @param tag
	 */
	public String alterOrders(String uid, String orders, String tag);

	/**
	 * 查询指定自定义表的所有列
	 * @param tableName
	 */
	public abstract Map<String, ColumnDesign> getAllColumns(String tableName);

	/**
	 * 查询所有自定义表
	 * @return
	 */
	public abstract List<CstmTable> getDesignTables();
	
	
}
