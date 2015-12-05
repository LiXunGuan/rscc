package com.ruishengtech.rscc.crm.datamanager.manager.node;


/**
 * Created by yaoliceng on 2015/8/18.
 */
public class BlacklistNode extends Node {
	
	public final static String name = "blacklist";
	
	public BlacklistNode() {
		this.nodeName = name; 
	}
	
	public BlacklistNode(String table) {
		this();
		this.setTableName(table);
	}
	
}
