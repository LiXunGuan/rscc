package com.ruishengtech.rscc.crm.ui;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Frank
 *
 */
@SuppressWarnings("serial")
public class TableBeans implements Serializable{

	public static final String TAB = "TAB";
	public static final String TABSELECTED = "TABSELECTED";
	
	
	/**
	 * 存放分页大小
	 */
	public static Map<String, HashMap<String, String>> lengthMap = new HashMap<String, HashMap<String, String>>(){
		{
			
		}
	};
	
	
	private String name;

	private String label;

	private String type;
	
	public TableBeans(String name, String label,String type) {
		this.name = name;
		this.label = label;
		this.type = type;
	}
	
	public TableBeans(String name, String label) {
		this.name = name;
		this.label = label;
	}
	public TableBeans() {
		super();
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
