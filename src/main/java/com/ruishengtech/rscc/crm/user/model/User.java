package com.ruishengtech.rscc.crm.user.model;

import java.util.Date;

import org.json.JSONObject;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "user_user")

public class User extends CommonDbBean { 

	@Index(type = IndexDefinition.TYPE_UNIQUE, method = IndexDefinition.METHOD_HASH)
    @Column(meta = "VARCHAR(64)")
    private String loginName;

    @Column(meta = "VARCHAR(64)")
    private String password;

    @NColumn(meta = "VARCHAR(64)")
    private String phone;
    
    @NColumn(meta = "VARCHAR(64)")
    private String mail;
    
    @NColumn(meta = "VARCHAR(64)")
    private String checkUrl;
    
	@Column(meta = "VARCHAR(64)", column = "user_describe")
    private String userDescribe;

	@Index
	@Column(meta = "VARCHAR(64)")
    private String department;
	
	@NColumn(meta = "VARCHAR(64)", column = "agent_job_number")
    private String jobNumber;
	
	@NColumn(meta = "VARCHAR(64)", column = "caller_id_name")
    private String callerIdName;
	
	@NColumn(meta = "VARCHAR(64)", column = "caller_id_number")
    private String callerIdNumber;
	
	//删除标志
	@Column(meta = "VARCHAR(1) default '0'", column = "delete_flag")
    private String deleteFlag;
	
	//
	@NColumn(meta = "VARCHAR(1) default '0'", column = "admin_flag")
    private String adminFlag;
	
	public String getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getCallerIdName() {
		return callerIdName;
	}

	public void setCallerIdName(String callerIdName) {
		this.callerIdName = callerIdName;
	}

	public String getCallerIdNumber() {
		return callerIdNumber;
	}

	public void setCallerIdNumber(String callerIdNumber) {
		this.callerIdNumber = callerIdNumber;
	}

	@Column(meta = "DATETIME")
	private Date date;

	private String departmentName;

	private String realUserUuid;
	
	private String realUserName;
	
	public String getRealUserUuid() {
		return realUserUuid;
	}

	public void setRealUserUuid(String realUserUuid) {
		this.realUserUuid = realUserUuid;
	}

	public String getRealUserName() {
		return realUserName;
	}

	public void setRealUserName(String realUserName) {
		this.realUserName = realUserName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserDescribe() {
		return userDescribe;
	}

	public void setUserDescribe(String userDescribe) {
		this.userDescribe = userDescribe;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	public String getCheckUrl() {
		return checkUrl;
	}

	public void setCheckUrl(String checkUrl) {
		this.checkUrl = checkUrl;
	}

	public JSONObject toRequest(){
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("agent_id", this.loginName);
		jsonRequest.put("agent_job_number", this.jobNumber);
		jsonRequest.put("agent_info", this.userDescribe);
		jsonRequest.put("caller_id_number", this.callerIdName);
		jsonRequest.put("caller_id_name", this.callerIdName);
		return jsonRequest;
	}
	
}
