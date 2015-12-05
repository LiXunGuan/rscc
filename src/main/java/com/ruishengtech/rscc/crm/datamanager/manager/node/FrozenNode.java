package com.ruishengtech.rscc.crm.datamanager.manager.node;


/**
 * Created by yaoliceng on 2015/8/18.
 */
public class FrozenNode extends Node {
	
	public final static String name = "frozen";
	
	public FrozenNode() {
		this.nodeName = name; 
	}
	
	public FrozenNode(String table) {
		this();
		this.setTableName(table);
	}
	
}
