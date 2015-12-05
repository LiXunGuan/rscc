package com.ruishengtech.rscc.crm.data.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "data_item_")
public class DataItem extends CommonDbBean { 

    @Column(meta = "VARCHAR(64)", column = "item_name")
    private String itemName;

    @Index(name = "itemPhone", type = IndexDefinition.TYPE_NORMAL, method = IndexDefinition.METHOD_HASH)
    @Column(meta = "VARCHAR(64)", column = "item_phone")
    private String itemPhone;

    @NColumn(meta = "VARCHAR(64)", column = "item_address")
    private String itemAddress;
    
    @NColumn(meta = "VARCHAR(64)", column = "item_json")
    private String itemJson;
    
    @Index
    @NColumn(meta = "VARCHAR(64)", column = "item_owner")
    private String itemOwner;
    
    @NColumn(meta = "INT DEFAULT 0 ", column = "call_times")
    private Integer callTimes;
    
    @Index
    @NColumn(meta = "DATETIME" , column = "allocate_time")
    private Date allocateTime;
    
    @NColumn(meta = "INT DEFAULT 0 " , column = "stat_flag")
    private Integer statFlag;
    
    @Index
    @NColumn(meta = "VARCHAR(64) " , column = "data_from")
    private String dataFrom;
	
	public String getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemPhone() {
		return itemPhone;
	}

	public void setItemPhone(String itemPhone) {
		this.itemPhone = itemPhone;
	}

	public String getItemJson() {
		return itemJson;
	}

	public void setItemJson(String itemJson) {
		this.itemJson = itemJson;
	}

	public String getItemAddress() {
		return itemAddress;
	}

	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}

	public String getItemOwner() {
		return itemOwner;
	}

	public void setItemOwner(String itemOwner) {
		this.itemOwner = itemOwner;
	}

	public Integer getCallTimes() {
		return callTimes;
	}

	public void setCallTimes(Integer callTimes) {
		this.callTimes = callTimes;
	}

	public Date getAllocateTime() {
		return allocateTime;
	}

	public void setAllocateTime(Date allocateTime) {
		this.allocateTime = allocateTime;
	}

	public Integer getStatFlag() {
		return statFlag;
	}

	public void setStatFlag(Integer statFlag) {
		this.statFlag = statFlag;
	}

}
