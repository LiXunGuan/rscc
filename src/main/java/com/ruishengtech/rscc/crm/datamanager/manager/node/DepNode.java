package com.ruishengtech.rscc.crm.datamanager.manager.node;

import java.util.Collection;

import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.datamanager.model.DepartmentData;

/**
 * Created by yaoliceng on 2015/8/18.
 */
public class DepNode extends Node {
	
	public static String tablePrefix = DepartmentData.class.getAnnotation(Table.class).name();
	
	public final static String name = "dep";
	
	public DepNode() {
		this.nodeName = name; 
	}
	
	public DepNode(String table) {
		this();
		this.setTableName(table);
	}
	
	public DepNode(String table, String[] tables) {
		this(table);
		this.setTables(tables);
	}
	
	public DepNode(String table, Collection<String> tables) {
		this(table);
		this.setTables(tables);
	}
	
}
