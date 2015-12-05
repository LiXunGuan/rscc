package com.ruishengtech.rscc.crm.cstm.model;

import java.util.HashMap;

/**
 * 用户自定义
 * @author Frank
 *
 */
public class CustomerDesign {
	
	private Integer id;
	
	//允许添加索引
	public static String ALLOWINDEX = "1";
	//不允许添加索引
	public static String NOTALLOWINDEX = "0";
	
	//允许查看
	public static String ALLOWSELECT = "1";
	//不允许查看
	public static String NOTALLOWSELECT = "0";
	
	
	//允许显示
	public static String ALLOWSHOW = "1";
	//不允许显示
	public static String NOTALLOWSHOW = "0";
	
	
	
	/**
	 * 是否
	 */
	@SuppressWarnings("serial")
	public static HashMap<String, String> BOOLEANTYPE = new HashMap<String, String>(){
		{
			put("0", "否");
			put("1", "是");
		}
	};
	
	/**
	 * 字段类型
	 */
//	public static String INTTYPE = "INT";
//	public static String FLOATTYPE = "FLOAT";
//	public static String DATETYPE = "DATE";
//	public static String ENUMTYPE = "ENUM";
//	public static String STRINGTYPE = "VARCHAR";
	
//	@SuppressWarnings("serial")
//	public static HashMap<String, String> COLUMNTYPE = new HashMap<String, String>(){
//		{
//			put(CustomerDesign.STRINGTYPE, "字符串");
//			put(CustomerDesign.INTTYPE, "整数");
//			put(CustomerDesign.FLOATTYPE, "浮点数");
//			put(CustomerDesign.DATETYPE, "日期");
//			put(CustomerDesign.ENUMTYPE, "枚举");
//		}
//	};
	
	
	
	
	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 列名
	 */
	private String columnName;
	
	/**
	 * 数据库存的列名
	 */
	private String columnNameDB;

	/**
	 * 类型
	 */
	private String columnType;
	
	/**
	 * 列长
	 */
	private String columnValue;
	
	/**
	 * 字符属性
	 */
	private String characterProperty;
	
	
	/**
	 * 允许查询
	 */
	private String allowSelect;
	
	
	/**
	 * 允许索引
	 */
	private String allowIndex;
	
	/**
	 * 是否内置
	 */
	private String isDefault;
	
	
	/**
	 * 允许显示
	 */
	private String allowShow;
	
	
	
	/**
	 * 顺序
	 */
	private Integer orders;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

 
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public String getAllowSelect() {
		return allowSelect;
	}

	public void setAllowSelect(String allowSelect) {
		this.allowSelect = allowSelect;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}



	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getAllowIndex() {
		return allowIndex;
	}

	public void setAllowIndex(String allowIndex) {
		this.allowIndex = allowIndex;
	}

	public String getAllowShow() {
		return allowShow;
	}

	public void setAllowShow(String allowShow) {
		this.allowShow = allowShow;
	}

	public String getCharacterProperty() {
		return characterProperty;
	}

	public void setCharacterProperty(String characterProperty) {
		this.characterProperty = characterProperty;
	}

	public String getColumnNameDB() {
		return columnNameDB;
	}

	public void setColumnNameDB(String columnNameDB) {
		this.columnNameDB = columnNameDB;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

}
