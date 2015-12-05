package com.ruishengtech.rscc.crm.datamanager.manager.node;

import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.datamanager.model.DataGlobalShare;

/**
 * Created by yaoliceng on 2015/8/18.
 */
public class ShareNode extends Node {
	
	public static final String name = "global_share";
	
	public static String tableName = DataGlobalShare.class.getAnnotation(Table.class).name();
	
	public ShareNode() {
		this.nodeName = name; 
	}
	
	public ShareNode(String table) {
		this();
		this.setTableName(table);
	}
	
}
