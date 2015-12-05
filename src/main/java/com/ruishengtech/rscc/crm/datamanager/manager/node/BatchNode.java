package com.ruishengtech.rscc.crm.datamanager.manager.node;

import java.util.Collection;

import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;

/**
 * Created by yaoliceng on 2015/8/18.
 */
public class BatchNode extends Node {
	
	public static String tablePrefix = DataBatchData.class.getAnnotation(Table.class).name();
	
	public final static String name = "batch";
	
	public BatchNode() {
		this.nodeName = name; 
	}
	
	public BatchNode(String table) {
		this();
		this.setTableName(table);
	}
	
	public BatchNode(String table, String[] tables) {
		this();
		this.setTables(tables);
	}
	
	public BatchNode(String table, Collection<String> tables) {
		this();
		this.setTables(tables);
	}
	
}
