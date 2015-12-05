package com.ruishengtech.rscc.crm.product.service;

import java.util.List;

import com.ruishengtech.rscc.crm.product.model.OrderDetail;

public interface OrderDetailService {

	/**
	 * 保存或修改一个订单明细信息
	 * @param orderDetail
	 */
	public abstract void saveOrUpdate(OrderDetail orderDetail);
	
	/**
	 * 根据订单编号查询订单详情信息
	 * @param orderid
	 * @return
	 */
	public abstract List<OrderDetail>  getOrderDetailsByOrderId(String orderid);
	
	public abstract List<OrderDetail>  getOrderDetailsByPid(String Pid);
	
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
	public abstract OrderDetail getOrderDetailByOidAndPid(String oId, String pId);
	
}
