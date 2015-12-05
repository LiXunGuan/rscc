package com.ruishengtech.rscc.crm.cstm.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * 日志类
 * 
 * @author Frank
 */
@Table(name="cstm_log")
public class CstmLog extends CommonDbBean {

	public CstmLog() {
		super();
	}

	/* ------------------------------- 客户来源 ---------------------------- */
	/**
	 * 客户来源1
	 */
	public static String RESORCE1 = "origin";
	
	/**
	 * 客户来源2
	 */
	public static String RESORCE2 = "";
	/**
	 * 客户来源3
	 */
	public static String RESORCE3 = "";
	
	@SuppressWarnings("serial")
	public static Map<String, String> RESOURCE = new HashMap<String, String>(){{
		
		put(RESORCE1, "");
		
	}};
	/* ------------------------------- 客户来源 ---------------------------- */
	
	
	public CstmLog(String optUser, String optUserUUID, Date optDate,
			String optObjUUID, String optObj, String optAction,String objPool) {
		super();
		this.optUser = optUser;
		this.optUserUUID = optUserUUID;
		this.optDate = optDate;
		this.optObjUUID = optObjUUID;
		this.optObj = optObj;
		this.optAction = optAction;
	}
 
	public CstmLog(String optUser, String optUserUUID, Date optDate,
			String optObjUUID, String optObj, String optAction, String options,String objPool) {
		super();
		this.optUser = optUser;
		this.optUserUUID = optUserUUID;
		this.optDate = optDate;
		this.optObjUUID = optObjUUID;
		this.optObj = optObj;
		this.optAction = optAction;
		this.options = options;
		this.objPool = objPool;
	}


	/**
	 * 新增客户
	 */
	public static String OPT1 = "add";
	
	/**
	 * 移入
	 */
	public static String OPT_1 = "addIn";
	
	/**
	 * 移除
	 */
	public static String OPT_3 = "moveOut";
	/**
	 * 修改客户
	 */
	public static String OPT2 = "update";
	/**
	 * 删除客户
	 */
	public static String OPT3 = "delete";
	/**
	 * 添加客户字段
	 */
	public static String OPT4 = "添加了一个客户字段";
	/**
	 * 删除客户字段
	 */
	public static String OPT5 = "删除了一个客户字段";
	

	/**
	 * 操作人
	 */
	@Column(meta = "VARCHAR(64)", column = "opt_user")
	private String optUser;
	/**
	 * 操作人编号
	 */
	@Column(meta = "VARCHAR(64)", column = "opt_user_uuid")
	private String optUserUUID;

	/**
	 * 操作时间
	 */
	@Column(meta = "DATETIME", column = "opt_date")
	private Date optDate;

	/**
	 * 操作的对象编号
	 */
	@NColumn(meta = "VARCHAR(64)", column = "opt_obj_uuid")
	private String optObjUUID;
	
	/**
	 * 操作的对象  防止更名操作指向对象错误
	 */
	@NColumn(meta = "VARCHAR(64)", column = "opt_obj")
	private String optObj;
	
	/**
	 * 做了什么操作
	 */
	@Column(meta = "VARCHAR(128)", column = "opt_action")
	private String optAction;
	
	/**
	 * 其他的选项
	 */
	@NColumn(meta = "VARCHAR(128)", column = "options")
	private String options;
	
	/**
	 * 操作池对象
	 */
	@NColumn(meta = "VARCHAR(128)", column = "obj_pool")
	private String objPool;
	
	/**
	 * 操作池对象
	 */
	@NColumn(meta = "VARCHAR(64)", column = "opt_type")
	private String optType;
	
	
	/**
	 * 操作个数
	 */
	@NColumn(meta = "INT", column = "opt_count")
	private Integer optCount;
	
	
	/**
	 * 数据来源
	 */
	@NColumn(meta = "VARCHAR(64)", column = "opt_source")
	private String optSource;
	
	public String getOptUser() {
		return optUser;
	}

	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}

	public Date getOptDate() {
		return optDate;
	}

	public void setOptDate(Date optDate) {
		this.optDate = optDate;
	}

	public String getOptObj() {
		return optObj;
	}

	public void setOptObj(String optObj) {
		this.optObj = optObj;
	}

	public String getOptObjUUID() {
		return optObjUUID;
	}

	public void setOptObjUUID(String optObjUUID) {
		this.optObjUUID = optObjUUID;
	}

	public String getOptAction() {
		return optAction;
	}

	public void setOptAction(String optAction) {
		this.optAction = optAction;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getOptUserUUID() {
		return optUserUUID;
	}

	public void setOptUserUUID(String optUserUUID) {
		this.optUserUUID = optUserUUID;
	}

	public String getObjPool() {
		return this.objPool;
	}


	public void setObjPool(String objPool) {
		this.objPool = objPool;
	}


	public String getOptType() {
		return this.optType;
	}


	public void setOptType(String optType) {
		this.optType = optType;
	}


	public Integer getOptCount() {
		return this.optCount;
	}

	public void setOptCount(Integer optCount) {
		this.optCount = optCount;
	}


	public String getOptSource() {
		return this.optSource;
	}


	public void setOptSource(String optSource) {
		this.optSource = optSource;
	}
 

}
