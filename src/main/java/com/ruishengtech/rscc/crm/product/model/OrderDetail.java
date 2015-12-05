package com.ruishengtech.rscc.crm.product.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name="order_detail")
public class OrderDetail extends CommonDbBean{

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
	
	private String productName;
	
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
