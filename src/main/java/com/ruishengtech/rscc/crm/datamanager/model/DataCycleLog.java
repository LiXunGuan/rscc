package com.ruishengtech.rscc.crm.datamanager.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Wangyao
 *
 */
@Table(name = "new_data_cycle_log")

//批次表

public class DataCycleLog extends CommonDbBean { 

	public static String tableName = DataCycleLog.class.getAnnotation(Table.class).name();
	
	//从哪里
	@Column(meta = "VARCHAR(64)", column = "from_data")
    private String fromData;
	
    //到哪里
	@Column(meta = "VARCHAR(64)", column = "to_data")
    private String toData;
	
	//操作人
	@NColumn(meta = "VARCHAR(64)", column = "operator")
	private String operator;
	
	//操作时间
	@NColumn(meta = "TIMESTAMP DEFAULT NOW()", column = "operate_time")
	private Date operateTime;
	
	//操作谁
	@NColumn(meta = "VARCHAR(32)", column = "operate_object")
	private String operateObject;

	public String getFromData() {
		return fromData;
	}

	public void setFromData(String fromData) {
		this.fromData = fromData;
	}

	public String getToData() {
		return toData;
	}

	public void setToData(String toData) {
		this.toData = toData;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateObject() {
		return operateObject;
	}

	public void setOperateObject(String operateObject) {
		this.operateObject = operateObject;
	}
	
}
