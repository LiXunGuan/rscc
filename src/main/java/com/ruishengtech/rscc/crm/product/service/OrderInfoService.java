package com.ruishengtech.rscc.crm.product.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.ui.Model;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.rscc.crm.product.model.OrderInfo;

public interface OrderInfoService {

	/**
	 * 取到所有titles
	 * @param model
	 */
	public void getTitles(Model model);
	
	/**
	 * 得到表头和对应数据字段
	 * 
	 * @return
	 */
	public JSONObject getTitleAndData();
	
	/**
	 * 查询不是默认的字段列
	 */
	public Map<String, ColumnDesign> getNotDefaultColumn();
	
	/**
	 * 查符合条件的所有订单
	 * 
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public PageResult<Map> queryPage(Map<String, ColumnDesign> str, HttpServletRequest request);
	
	/**
	 * 取值
	 * @param str
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public JSONObject getJsonObject(Map str);
	
	/**
	 * 查询所有的产品列
	 * @return
	 */
	public Map<String, ColumnDesign>  getAllColumns();
	
	public OrderInfo getOrderByUUID(String uuid);
	
	public OrderInfo getOrderByOrderid(String oid);
	
	public abstract Map<String, Object> getOrderInfoByUUId(String uuid);
	
	/**
	 * 根据uuid删除订单信息
	 * @param uuid
	 */
	public abstract void deleteOrderByUUid(String uuid);
	
	/**
	 * 保存或修改一个订单信息
	 * @param orderInfo
	 */
	public abstract void saveOrUpdateInfo(OrderInfo orderInfo);
	
}
