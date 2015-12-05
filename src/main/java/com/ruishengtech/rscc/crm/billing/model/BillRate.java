package com.ruishengtech.rscc.crm.billing.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name="billing_rate")
public class BillRate extends CommonDbBean{
	
	/**
	 * 费率类型
	 */
	@Column(meta="VARCHAR(64)",column="billratetype")
	private String billRateType;
	
	/**
	 * 费率名称
	 */
	@Column(meta="VARCHAR(64)",column="billratename")
	private String billRateName;

	
	/**
	 * 费率单位金额
	 */
	@Column(meta="FLOAT",column="rateSdfMoney")
	private Float rateSdfMoney;
	
	/**
	 * 费率单位时间
	 */
	@Column(meta="INT",column="rateSdfTime")
	private Integer rateSdfTime;
	
	/**
	 * 费率--将金额和时间拼起来组成费率
	 * @return
	 */
	private String rateinfo;

	public String getBillRateType() {
		return billRateType;
	}

	public void setBillRateType(String billRateType) {
		this.billRateType = billRateType;
	}

	public String getBillRateName() {
		return billRateName;
	}

	public void setBillRateName(String billRateName) {
		this.billRateName = billRateName;
	}

	public Float getRateSdfMoney() {
		return rateSdfMoney;
	}

	public void setRateSdfMoney(Float rateSdfMoney) {
		this.rateSdfMoney = rateSdfMoney;
	}

	public Integer getRateSdfTime() {
		return rateSdfTime;
	}

	public void setRateSdfTime(Integer rateSdfTime) {
		this.rateSdfTime = rateSdfTime;
	}

	public String getRateinfo() {
		return rateinfo;
	}

	public void setRateinfo(String rateinfo) {
		this.rateinfo = rateinfo;
	}
	

}
