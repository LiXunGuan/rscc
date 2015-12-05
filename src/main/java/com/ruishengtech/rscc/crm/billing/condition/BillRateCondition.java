package com.ruishengtech.rscc.crm.billing.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class BillRateCondition extends Page{
	
	private String billRateType;	 //费率类型
	private String billRateName;	 //费率名称
	private String rateSdfMoney;	 //单位金额
	private String rateSdfTime;		 //单位时间
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
	public String getRateSdfMoney() {
		return rateSdfMoney;
	}
	public void setRateSdfMoney(String rateSdfMoney) {
		this.rateSdfMoney = rateSdfMoney;
	}
	public String getRateSdfTime() {
		return rateSdfTime;
	}
	public void setRateSdfTime(String rateSdfTime) {
		this.rateSdfTime = rateSdfTime;
	}
	
	
}
