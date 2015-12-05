package com.ruishengtech.rscc.crm.datamanager.manager.node;


/**
 * Created by yaoliceng on 2015/8/18.
 */
public class AbandonNode extends Node {
	
	public final static String name = "abandon";
	
	public AbandonNode() {
		this.nodeName = name; 
	}
	
	public AbandonNode(String table) {
		this();
		this.setTableName(table);
	}
	
}
