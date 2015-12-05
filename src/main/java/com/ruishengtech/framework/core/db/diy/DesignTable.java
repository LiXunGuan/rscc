package com.ruishengtech.framework.core.db.diy;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Frank
 *表描述
 *
 */

@Table(name = "design_table")
public class DesignTable extends CommonDbBean{

	@Column (meta ="VARCHAR(64)",column = "table_name")
	private String tableName;
	
	
	@NColumn (meta ="VARCHAR(256)",column = "table_json")
	private String tableJson;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableJson() {
		return tableJson;
	}

	public void setTableJson(String tableJson) {
		this.tableJson = tableJson;
	}

}
