package com.ruishengtech.rscc.crm.datamanager.manager.node;


/**
 * Created by yaoliceng on 2015/8/18.
 */

//tablename为意向名
public class IntentNode extends Node {
	
	public final static String name = "intent";
	
	public IntentNode() {
		this.nodeName = name; 
	}
	
	public IntentNode(String table) {
		this();
		this.setTableName(table);
	}
	
}
