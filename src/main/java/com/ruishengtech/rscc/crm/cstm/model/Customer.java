package com.ruishengtech.rscc.crm.cstm.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Frank
 *
 */
@Table(name = "cstm_customer")
public class Customer extends CommonDbBean{

	/**
	 * 意向
	 */
	public static String CUSTOMER_INTENT= "0";
	
	/**
	 * 成交
	 */
	public static String CUSTOMER_DEAL = "1";
	
	/**
	 * 客户基本分类
	 */
	public static Map<String, String> CUSTOMER_TYPE = new LinkedHashMap<String, String>(){
		{
			put(CUSTOMER_DEAL, "成交客户");
			put(CUSTOMER_INTENT, "意向客户");
		}
	};
	
//	public static String finalStr = "0";
	
	/**
	 * 客户名字
	 */
	@Column(meta = "VARCHAR(64)", column = "cstm_name")
	private String customerName;
	
	/**
	 * 客户编号
	 */
	@Column(meta = "VARCHAR(64)", column = "customer_id")
	@Index(name = "customerName", type = IndexDefinition.TYPE_NORMAL)
	private String customerId;
	
	/**
	 * 客户归属池编号
	 */
	@NColumn(meta = "VARCHAR(64)", column = "pool_id")
	@Index(name = "poolId", type = IndexDefinition.TYPE_NORMAL)
	private String poolId;
	
	/**
	 * 客户归属人编号  无主客户可为空
	 */
	@NColumn(meta = "varchar(64)", column = "own_id")
	@Index(name = "ownId", type = IndexDefinition.TYPE_NORMAL)
	private String ownId;
	
	/**
	 * 电话号码
	 */
	@Column(meta = "varchar(64)", column = "phone_number")
	@Index(name = "phoneNumber", type = IndexDefinition.TYPE_NORMAL,method = IndexDefinition.METHOD_BTREE)
	private String phoneNumber;
	
	/**
	 * 客户描述
	 */
	@NColumn(meta = "VARCHAR(64)", column = "customer_des")
	private String customerDes;
	
	/**
	 * 客户进入系统时间
	 */
	@NColumn(meta = "DATETIME", column = "start_date")
	@Index(name = "startDate", type = IndexDefinition.TYPE_NORMAL,method = IndexDefinition.METHOD_BTREE)
	private Date startDate;
	
	/**
	 *客户状态 
	 */
	@NColumn(meta = "VARCHAR(64)", column = "status")
	private String status;
	
	/**
	 * 客户标签
	 */
	private String customerTags;
	
	
	/**
	 * 字号码
	 */
	private String minorNumber;
	

	public String getCustomerTags() {
		return customerTags;
	}

	public void setCustomerTags(String customerTags) {
		this.customerTags = customerTags;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerDes() {
		return customerDes;
	}

	public void setCustomerDes(String customerDes) {
		this.customerDes = customerDes;
	}

	public String getPoolId() {
		return poolId;
	}

	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}

	public String getOwnId() {
		return ownId;
	}

	public void setOwnId(String ownId) {
		this.ownId = ownId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMinorNumber() {
		return this.minorNumber;
	}

	public void setMinorNumber(String minorNumber) {
		this.minorNumber = minorNumber;
	}
	
}
