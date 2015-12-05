package com.ruishengtech.rscc.crm.datamanager.manager.node;

import java.util.Collection;

import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;

/**
 * Created by yaoliceng on 2015/8/18.
 */

//只有usertodep和deptouser时，node中的tableName存的才是useruuid，其他情况下都存的是user所在表名，useruuid保存在transferdata中
public class UserNode extends Node  {
	
	public static String tablePrefix = UserData.class.getAnnotation(Table.class).name();
	
	private final static String name = "user";
	
	public UserNode() {
		this.nodeName = name; 
	}
	
	//这里的table一律存表名，即使是UserNode这里也不存userUuid。
	public UserNode(String table) {
		this();
		this.setTableName(table);
	}
	
	public UserNode(String table, String[] tables) {
		this(table);
		this.setTables(tables);
	}
	
	public UserNode(String table, Collection<String> tables) {
		this(table);
		this.setTables(tables);
	}
	
}
