package com.ruishengtech.rscc.crm.datamanager.manager.node;


/**
 * Created by yaoliceng on 2015/8/18.
 */

//tablename为客户的uuid
public class CustomerNode extends Node {
	
	public final static String name = "customer";
	
	public CustomerNode() {
		this.nodeName = name; 
	}
	
	public CustomerNode(String table) {
		this();
		this.setTableName(table);
	}
	
}
