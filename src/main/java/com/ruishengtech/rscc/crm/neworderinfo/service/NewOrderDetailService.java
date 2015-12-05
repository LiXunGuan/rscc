package com.ruishengtech.rscc.crm.neworderinfo.service;

import java.util.List;

import com.ruishengtech.rscc.crm.neworderinfo.model.NewOrderDetail;

public interface NewOrderDetailService {

	/**
	 * 保存或修改一个订单明细信息
	 * @param orderDetail
	 */
	public abstract void saveOrUpdate(NewOrderDetail orderDetail);
	
	/**
	 * 根据订单编号查询订单详情信息
	 * @param orderid
	 * @return
	 */
	public abstract List<NewOrderDetail>  getOrderDetailsByOrderId(String orderid);
	
	public abstract List<NewOrderDetail>  getOrderDetailsByPid(String Pid);
	
	/**
	 * 根据订单编号删除订单详情信息
	 * @param OrderId
	 */
	public abstract void deleteOrderDetailsByOrderId(String orderId);
	
	/**
	 * 查询商品详情信息
	 * @param oId
	 * @param pId
	 */
	public abstract NewOrderDetail getOrderDetailByOidAndPid(String oId, String pId);
	
}
