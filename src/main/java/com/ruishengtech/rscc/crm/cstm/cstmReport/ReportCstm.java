package com.ruishengtech.rscc.crm.cstm.cstmReport;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Table;

/**
 * 客户报表统计
 * @author Frank
 */
@Table(name="report_cstm")
public class ReportCstm extends CommonDbBean{

	/**
	 * 统计时间
	 */
	@Column(meta="VARCHAR(64)",column = "opt_date")
	private String opt_date;
	
	/**
	 * 客户姓名
	 */
	@Column(meta="VARCHAR(64)",column = "opt_obj")
	private String opt_obj;
	
	/**
	 * 客户来源
	 */
	@Column(meta="VARCHAR(64)",column = "opt_source")
	private String opt_source;
	
	/**
	 * 客户池
	 */
	@Column(meta="VARCHAR(64)",column = "pool_name")
	private String pool_name;
	
	/**
	 * 客户数量
	 */
	@Column(meta="VARCHAR(64)",column = "opt_count")
	private String opt_count;

	public String getOpt_date() {
		return this.opt_date;
	}

	public void setOpt_date(String opt_date) {
		this.opt_date = opt_date;
	}

	public String getOpt_obj() {
		return this.opt_obj;
	}

	public void setOpt_obj(String opt_obj) {
		this.opt_obj = opt_obj;
	}

	public String getOpt_source() {
		return this.opt_source;
	}

	public void setOpt_source(String opt_source) {
		this.opt_source = opt_source;
	}

	public String getPool_name() {
		return this.pool_name;
	}

	public void setPool_name(String pool_name) {
		this.pool_name = pool_name;
	}

	public String getOpt_count() {
		return this.opt_count;
	}

	public void setOpt_count(String opt_count) {
		this.opt_count = opt_count;
	}
	
}
