package com.ruishengtech.rscc.crm.cstm.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Frank
 *
 */
@Table(name = "cstm_customer_tag")
public class CustomerTag extends CommonDbBean{

	/**
	 * 标签名称
	 */
	@Column(meta = "VARCHAR(64)", column = "tag_name")
	@Index(name = "tagName", type = IndexDefinition.TYPE_NORMAL, method = IndexDefinition.METHOD_BTREE)
	private String tagName;


	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	
}
