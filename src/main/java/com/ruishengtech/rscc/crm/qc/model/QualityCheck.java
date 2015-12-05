package com.ruishengtech.rscc.crm.qc.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Frank
 *
 */
/**
 * @author Frank
 *
 */
/**
 * @author Frank
 *
 */
/**
 * @author Frank
 *
 */
@Table(name = "qualitycheck")
public class QualityCheck extends CommonDbBean{
	
	/**
	 * 质检人
	 */
	@Column(meta = "VARCHAR(64)" ,column ="reporter" )
	private String reporter;
	
	/**
	 * 质检时间
	 */
	@Column(meta = "VARCHAR(64)" ,column ="date" )
	private Date date;
	
	/**
	 * 质检分数
	 */
	@Column(meta = "VARCHAR(64)" ,column ="score" )
	private String score;
	
	/**
	 * 质检对象
	 */
	@Index(name = "uuid_obj", type = IndexDefinition.TYPE_NORMAL, method = IndexDefinition.METHOD_BTREE)
	@Column(meta = "VARCHAR(64)" ,column ="uuid_obj" )
	private String uuidObj;
	
	
	/**
	 * 备注
	 */
	@Column(meta = "VARCHAR(64)" ,column ="remark" )
	private String remark;

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getUuidObj() {
		return uuidObj;
	}

	public void setUuidObj(String uuidObj) {
		this.uuidObj = uuidObj;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
