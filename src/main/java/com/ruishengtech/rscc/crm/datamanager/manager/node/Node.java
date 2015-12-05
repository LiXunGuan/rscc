package com.ruishengtech.rscc.crm.datamanager.manager.node;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by yaoliceng on 2015/8/18.
 */
public abstract class Node {
	
	protected String nodeName;
	
	protected String tableName;
	
	protected Collection<String> tables;

	public String getNodeName() {
		return nodeName;
	}

	protected void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public Collection<String> getTables() {
		return tables;
	}
	
	public void setTables(Collection<String> tables) {
		this.tables = tables;
	}
	
	public void setTables(String[] tables) {
		this.tables = Arrays.asList(tables);
	}
	
}
