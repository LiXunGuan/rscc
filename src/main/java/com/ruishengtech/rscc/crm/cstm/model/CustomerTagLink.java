package com.ruishengtech.rscc.crm.cstm.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Frank
 *
 */
@Table(name = "cstm_tag_link")
public class CustomerTagLink {

	/**
	 * 标签编号
	 */
	@Key
	@Column(meta = "VARCHAR(64)", column = "tag_id")
	@Index(name = "tagId", type = IndexDefinition.TYPE_NORMAL, method = IndexDefinition.METHOD_BTREE)
	private String tagId;

	/**
	 * 客户编号
	 */
	@Key
	@Column(meta = "VARCHAR(64)", column = "customer_uuid")
	@Index(name = "customerUuid", type = IndexDefinition.TYPE_NORMAL, method = IndexDefinition.METHOD_BTREE)
	private String customerUuid;

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getCustomerUuid() {
		return customerUuid;
	}

	public void setCustomerUuid(String customerUuid) {
		this.customerUuid = customerUuid;
	}

}
