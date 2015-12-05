package com.ruishengtech.rscc.crm.datamanager.condition;

import java.util.Date;
import java.util.List;

import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.framework.core.db.condition.Page;
import com.ruishengtech.rscc.crm.datamanager.model.UserDataLog;

public class UserDataLogCondition extends Page{
	
public static String tableName = UserDataLog.class.getAnnotation(Table.class).name();
	
	// 电话号码
	private String phone_number;

	private String op_user;
	
	private String op_type;

	private String batch_uuid;
	
	private Date op_time;

	private String call_result;

	private String call_record;

	private String call_session_uuid;
	
	private String startTime;
	
	private String endTime;
	
	private String selectionReport;
	
	private List<String> op_users;
	
	public List<String> getOp_users() {
		return op_users;
	}

	public void setOp_users(List<String> op_users) {
		this.op_users = op_users;
	}

	public static String getTableName() {
		return tableName;
	}

	public static void setTableName(String tableName) {
		UserDataLogCondition.tableName = tableName;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getOp_user() {
		return op_user;
	}

	public void setOp_user(String op_user) {
		this.op_user = op_user;
	}

	public String getOp_type() {
		return op_type;
	}

	public void setOp_type(String op_type) {
		this.op_type = op_type;
	}

	public String getBatch_uuid() {
		return batch_uuid;
	}

	public void setBatch_uuid(String batch_uuid) {
		this.batch_uuid = batch_uuid;
	}

	public Date getOp_time() {
		return op_time;
	}

	public void setOp_time(Date op_time) {
		this.op_time = op_time;
	}

	public String getCall_result() {
		return call_result;
	}

	public void setCall_result(String call_result) {
		this.call_result = call_result;
	}

	public String getCall_record() {
		return call_record;
	}

	public void setCall_record(String call_record) {
		this.call_record = call_record;
	}

	public String getCall_session_uuid() {
		return call_session_uuid;
	}

	public void setCall_session_uuid(String call_session_uuid) {
		this.call_session_uuid = call_session_uuid;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSelectionReport() {
		return selectionReport;
	}

	public void setSelectionReport(String selectionReport) {
		this.selectionReport = selectionReport;
	}
	
	
}
