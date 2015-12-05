package com.ruishengtech.rscc.crm.neworderinfo.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name="new_order_detail")
public class NewOrderDetail extends CommonDbBean{

	/**
	 * 商品编号
	 */
	@Column(meta = "VARCHAR(64)",column = "product_id")
	private String productId;
	
	/**
	 * 商品数量
	 */
	@Column(meta = "INTEGER", column = "product_number")
	private Integer productNumber;
	
	/**
	 * 订单编号
	 */
	@Column(meta = "VARCHAR(64)",column = "order_id")
	private String orderid;
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

}
