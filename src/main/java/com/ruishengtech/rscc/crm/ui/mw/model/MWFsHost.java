package com.ruishengtech.rscc.crm.ui.mw.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/10/21.
 */
@Table(name = "mw.mw_fshost")
public class MWFsHost extends CommonDbBean {

	@Column(meta = "VARCHAR(64)")
	private String name;

	@Column(meta = "VARCHAR(32)", column = "ag_ip")
	private String agip;

	@Column(meta = "VARCHAR(5)", column = "ag_port")
	private String agport;

	@Column(meta = "VARCHAR(32)", column = "esl_ip")
	private String eslip;

	@Column(meta = "VARCHAR(5)", column = "esl_port")
	private String eslport;

	@Column(meta = "VARCHAR(20)", column = "esl_password")
	private String eslpassword;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAgip() {
		return agip;
	}

	public void setAgip(String agip) {
		this.agip = agip;
	}

	public String getAgport() {
		return agport;
	}

	public void setAgport(String agport) {
		this.agport = agport;
	}

	public String getEslip() {
		return eslip;
	}

	public void setEslip(String eslip) {
		this.eslip = eslip;
	}

	public String getEslport() {
		return eslport;
	}

	public void setEslport(String eslport) {
		this.eslport = eslport;
	}

	public String getEslpassword() {
		return eslpassword;
	}

	public void setEslpassword(String eslpassword) {
		this.eslpassword = eslpassword;
	}

}
