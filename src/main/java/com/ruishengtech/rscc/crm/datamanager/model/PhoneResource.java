package com.ruishengtech.rscc.crm.datamanager.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "new_data_phone_resource")

public class PhoneResource { 

	public static String tableName = PhoneResource.class.getAnnotation(Table.class).name();
	
	//电话号码
	@Key
    @Column(meta = "VARCHAR(20)", column = "phone_number")
    private String phoneNumber;

    //批次名，该号码在哪个批次
	@Index(method = IndexDefinition.METHOD_HASH)
    @Column(meta = "VARCHAR(32)", column = "batch_uuid")
    private String batchUuid;

    //是否废弃
    @NColumn(meta = "CHAR(1) DEFAULT '0'", column = "is_abandon")
    private String isAbandon;
    
    @Index
    @NColumn(meta = "DATETIME", column = "abandon_timestamp")
    private Date abandonTimestamp;
    
    //是否黑名单
    @NColumn(meta = "CHAR(1) DEFAULT '0'", column = "is_blacklist")
    private String isBlacklist;
    
    @Index
    @NColumn(meta = "DATETIME", column = "blacklist_timestamp")
    private Date blacklistimestamp;

    //是否冷冻
    @NColumn(meta = "CHAR(1) DEFAULT '0'", column = "is_frozen")
    private String isFrozen;
    
    @Index
    @NColumn(meta = "DATETIME", column = "frozen_timestamp")
    private Date frozenTimestamp;
    
    //保存为客户时的uuid
    @Index(method = IndexDefinition.METHOD_HASH)
    @NColumn(meta = "VARCHAR(32)", column = "customer_uuid")
    private String customerUuid;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBatchUuid() {
		return batchUuid;
	}

	public void setBatchUuid(String batchUuid) {
		this.batchUuid = batchUuid;
	}

	public String getIsAbandon() {
		return isAbandon;
	}

	public void setIsAbandon(String isAbandon) {
		this.isAbandon = isAbandon;
	}

	public Date getAbandonTimestamp() {
		return abandonTimestamp;
	}

	public void setAbandonTimestamp(Date abandonTimestamp) {
		this.abandonTimestamp = abandonTimestamp;
	}

	public String getIsBlacklist() {
		return isBlacklist;
	}

	public void setIsBlacklist(String isBlacklist) {
		this.isBlacklist = isBlacklist;
	}

	public Date getBlacklistimestamp() {
		return blacklistimestamp;
	}

	public void setBlacklistimestamp(Date blacklistimestamp) {
		this.blacklistimestamp = blacklistimestamp;
	}

	public String getIsFrozen() {
		return isFrozen;
	}

	public void setIsFrozen(String isFrozen) {
		this.isFrozen = isFrozen;
	}

	public Date getFrozenTimestamp() {
		return frozenTimestamp;
	}

	public void setFrozenTimestamp(Date frozenTimestamp) {
		this.frozenTimestamp = frozenTimestamp;
	}

	public String getCustomerUuid() {
		return customerUuid;
	}

	public void setCustomerUuid(String customerUuid) {
		this.customerUuid = customerUuid;
	}
	
}
