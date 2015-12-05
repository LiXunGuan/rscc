package com.ruishengtech.framework.core.db.diy;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * 用户自定义
 * @author Frank
 *
 */
@Table(name="design_column")
public class ColumnDesign extends CommonDbBean{
	
	//允许添加索引
	public static String ALLOWINDEX = "1";
	//不允许添加索引
	public static String NOTALLOWINDEX = "0";
	
	//是否默认 不是默认的 否
 	public static String NOTDEFAULT = "0";
 	
 	//是否默认 默认的 是
 	public static String DEFAULT = "1";
	
	//允许查看
	public static String ALLOWSELECT = "1";
	//不允许查看
	public static String NOTALLOWSELECT = "0";
	
	
	//允许显示
	public static String ALLOWSHOW = "1";
	//不允许显示
	public static String NOTALLOWSHOW = "0";
	
	//允许为空
	public static String ALLOWEMPTY  = "1";
	//不允许为空
	public static String NOTALLOWEMPTY = "0";
	
	
	/**
	 * 是否允许为空
	 */
	@SuppressWarnings("serial")
	public static HashMap<String, String> BOOLEANEMPTY = new HashMap<String, String>(){
		{
			put(ALLOWEMPTY, "允许为空");
			put(NOTALLOWEMPTY, "不允许为空");
		}
	};
	
	
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
	public static String INTTYPE = "INT";
	public static String FLOATTYPE = "FLOAT";
	public static String DATETYPE = "DATE";
	public static String ENUMTYPE = "ENUM";
	public static String STRINGTYPE = "VARCHAR";
	
	@SuppressWarnings("serial")
	public static HashMap<String, String> COLUMNTYPE = new LinkedHashMap<String, String>(){
		{
			put(ColumnDesign.STRINGTYPE, "字符串");
			put(ColumnDesign.INTTYPE, "整数");
			put(ColumnDesign.FLOATTYPE, "浮点数");
			put(ColumnDesign.DATETYPE, "日期");
			put(ColumnDesign.ENUMTYPE, "枚举");
		}
	};
	
	@Column(meta = "SERIAL")
	private Integer id;
	
	
	/**
	 * 列名
	 */
	@Column(meta = "VARCHAR(64)", column = "column_name")
	private String columnName;
	
	/**
	 * 数据库存的列名
	 */
	@Column(meta = "VARCHAR(64)", column = "column_name_db")
	private String columnNameDB;

	/**
	 * 表名
	 */
	@Column(meta = "VARCHAR(64)", column = "tableName")
	private String tableName;
	
	/**
	 * 类型
	 */
	@Column(meta = "VARCHAR(64)", column = "column_type")
	private String columnType;
	
	/**
	 * 是否内置
	 */
	@Column(meta = "VARCHAR(10)", column = "is_default")
	private String isDefault;
	
	
	/**
	 * 列长
	 */
	@NColumn(meta = "VARCHAR(64)", column = "column_value")
	private String columnValue;
	
	/**
	 * 字符属性
	 */
	@Column(meta = "VARCHAR(64)", column = "character_property")
	private String characterProperty;
	
	
	/**
	 * 允许查询
	 */
	@Column(meta = "VARCHAR(10)", column = "allow_select")
	private String allowSelect;
	
	
	/**
	 * 允许索引
	 */
	@Column(meta = "VARCHAR(10)", column = "allow_index")
	private String allowIndex;
	
	
	/**
	 * 允许显示
	 */
	@Column(meta = "VARCHAR(10)", column = "allow_show")
	private String allowShow;
	/**
	 * 是否为空
	 */
	@Column(meta = "VARCHAR(10)", column = "allow_empty")
	private String allowEmpty;
	
	/**
	 * 是否需要手动填写，即是否是隐藏的输入框，一般只出现在default字段
	 */
	@NColumn(meta = "VARCHAR(10)", column = "is_hidden")
	private String isHidden;
	
	/**
	 * 是否是只读的，即是否是只读的输入框，一般只出现在default字段，用于不能修改的输入框
	 */
	@NColumn(meta = "VARCHAR(10)", column = "is_readonly")
	private String isReadonly;
	/**
	 * 顺序
	 */
	@Column(meta = "INT", column = "orders")
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
	
	public String getIsHidden() {
		return isHidden;
	}
	
	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}
	
	public String getIsReadonly() {
		return isReadonly;
	}
	
	public void setIsReadonly(String isReadonly) {
		this.isReadonly = isReadonly;
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

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAllowEmpty() {
		return allowEmpty;
	}

	public void setAllowEmpty(String allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

}
