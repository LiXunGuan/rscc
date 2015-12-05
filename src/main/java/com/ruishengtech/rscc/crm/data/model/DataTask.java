package com.ruishengtech.rscc.crm.data.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "data_task_")
public class DataTask extends CommonDbBean { 

    @Column(meta = "VARCHAR(64)", column = "item_name")
    private String itemName;

    @Index(method = IndexDefinition.METHOD_HASH)
    @Column(meta = "VARCHAR(64)", column = "item_phone")
    private String itemPhone;

    @NColumn(meta = "VARCHAR(64)", column = "item_address")
    private String itemAddress;
    
    @NColumn(meta = "VARCHAR(64)", column = "item_json")
    private String itemJson;
    
    @NColumn(meta = "VARCHAR(64)", column = "item_owner")
    private String itemOwner;
    
    @Index
    @NColumn(meta = "TIMESTAMP DEFAULT NOW()", column = "allocate_time")
    private Date allocateTime;

    @NColumn(meta = "INT DEFAULT 0 ", column = "call_times")
    private Integer callTimes;
    
    //数据来源，可以是任意一个容器
    @Index
    @NColumn(meta = "VARCHAR(64)", column = "data_source")
    private String dataSource;
    
    private Date lastcalltime;
    
	public Date getLastcalltime() {
		return lastcalltime;
	}

	public void setLastcalltime(Date lastcalltime) {
		this.lastcalltime = lastcalltime;
	}

	//记录要做的操作
    private String moveTo;
    
    public String getMoveTo() {
		return moveTo;
	}

	public void setMoveTo(String moveTo) {
		this.moveTo = moveTo;
	}

	public Integer getCallTimes() {
		return callTimes;
	}

	public void setCallTimes(Integer callTimes) {
		this.callTimes = callTimes;
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

	public Date getAllocateTime() {
		return allocateTime;
	}

	public void setAllocateTime(Date allocateTime) {
		this.allocateTime = allocateTime;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getItemJson() {
		return itemJson;
	}

	public void setItemJson(String itemJson) {
		this.itemJson = itemJson;
	}
	
	@Override
	public boolean equals(Object dataTask) {
		return this.getUid().equals(((DataTask)dataTask).getUid());
	}
	
}
