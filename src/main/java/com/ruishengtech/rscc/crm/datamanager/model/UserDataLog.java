package com.ruishengtech.rscc.crm.datamanager.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

import java.util.Date;

/**
 * @author Wangyao
 *
 */
@Table(name = "new_data_department_user_log")

//批次表
public class UserDataLog extends CommonDbBean {

	public static String tableName = UserDataLog.class.getAnnotation(Table.class).name();
	
	// 电话号码
	@Index
	@Column(meta = "VARCHAR(20)", column = "phone_number")
	private String phone_number;

	@Index
	@NColumn(meta = "VARCHAR(64)", column = "op_user")
	private String op_user;
	
	@Index
	@NColumn(meta = "VARCHAR(64)", column = "old_stat")
	private String old_stat;
	
	@Index
	@NColumn(meta = "VARCHAR(64)", column = "op_type")
	private String op_type;
	
	@Index
	@NColumn(meta = "VARCHAR(64)", column = "type_des")
	private String type_des;

	@Index
	@NColumn(meta = "VARCHAR(64)", column = "batch_uuid")
	private String batch_uuid;
	
	@Index
	@NColumn(meta = "DATETIME", column = "op_time")
	private Date op_time;

	@NColumn(meta = "VARCHAR(100)", column = "call_result")
	private String call_result;

	@NColumn(meta = "VARCHAR(100)", column = "call_record")
	private String call_record;

	@Index(method = IndexDefinition.METHOD_HASH)
	@NColumn(meta = "VARCHAR(100)", column = "call_session_uuid")
	private String call_session_uuid;

    //============================================================================================


    public String getPhone_number() {
        return phone_number;
    }

    public String getOp_type() {
		return op_type;
	}

	public void setOp_type(String op_type) {
		this.op_type = op_type;
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

	public String getBatch_uuid() {
		return batch_uuid;
	}

	public void setBatch_uuid(String batch_uuid) {
		this.batch_uuid = batch_uuid;
	}

	public String getOld_stat() {
		return old_stat;
	}

	public void setOld_stat(String old_stat) {
		this.old_stat = old_stat;
	}

	public String getType_des() {
		return type_des;
	}

	public void setType_des(String type_des) {
		this.type_des = type_des;
	}
}
