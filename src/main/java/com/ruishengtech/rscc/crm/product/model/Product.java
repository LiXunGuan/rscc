package com.ruishengtech.rscc.crm.product.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * 产品模型
 * @author Frank
 *
 */
@Table(name="product")
public class Product extends CommonDbBean{

	/**
	 * 商品编号
	 */
	@Column(meta = "VARCHAR(64)",column = "product_id")
	private String productId;
	
	/**
	 * 商品名称
	 */
	@Column(meta = "VARCHAR(64)",column = "product_name")
	private String productName;

	/**
	 * 商品图片
	 */
	@NColumn(meta = "VARCHAR(200)",column = "product_picture")
	private String productPicture;
	
	/**
	 * 商品描述
	 */
	@Column(meta = "VARCHAR(200)",column = "product_remark")
	private String productRemark;
	
	/**
	 * 商品价格
	 */
	@Column(meta = "DOUBLE",column = "product_price")
	private double productPrice;
	
	/**
	 * 商品数量
	 */
	@Column(meta = "INTEGER",column = "product_number")
	private Integer productNumber;
	
	/**
	 * 创建时间
	 */
	@Column(meta = "DATETIME", column = "product_create_time")
	private Date productCreateTime;
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPicture() {
		return productPicture;
	}

	public void setProductPicture(String productPicture) {
		this.productPicture = productPicture;
	}

	public String getProductRemark() {
		return productRemark;
	}

	public void setProductRemark(String productRemark) {
		this.productRemark = productRemark;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Date getProductCreateTime() {
		return productCreateTime;
	}

	public void setProductCreateTime(Date productCreateTime) {
		this.productCreateTime = productCreateTime;
	}
	
}
