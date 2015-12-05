package com.ruishengtech.rscc.crm.knowledge.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "knowledge_label")
public class KnowledgeLabel extends CommonDbBean{
	
	/**
	 * 标签名
	 */
	@Column(meta = "VARCHAR(32)", column = "label_name")
	private String labelName;

	@NColumn(meta = "VARCHAR(64)", column = "label_remark")
	private String labelRemark;
	
	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getLabelRemark() {
		return labelRemark;
	}

	public void setLabelRemark(String labelRemark) {
		this.labelRemark = labelRemark;
	}
	
}
