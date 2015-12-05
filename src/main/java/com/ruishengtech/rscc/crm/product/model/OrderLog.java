package com.ruishengtech.rscc.crm.product.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name="order_log")
public class OrderLog extends CommonDbBean{

	/**
	 * 订单编号
	 */
	@Column(meta = "VARCHAR(64)",column = "order_id")
	private String orderid;
	
	/**
	 * 订单状态
	 */
	@Column(meta = "VARCHAR(64)", column = "order_status")
	private String orderStatus;
	
	/**
	 * 状态更新时间
	 */
	@Column(meta = "DATETIME", column = "order_status_update_time")
	private Date orderStatusUpdateTime;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderStatusUpdateTime() {
		return orderStatusUpdateTime;
	}

	public void setOrderStatusUpdateTime(Date orderStatusUpdateTime) {
		this.orderStatusUpdateTime = orderStatusUpdateTime;
	}
	
}
