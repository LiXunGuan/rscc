package com.ruishengtech.framework.core.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TableDefinition {

    private Class clazz;

    private String tableName;

    private List<String> keys = new ArrayList<String>();

    private List<String> columns = new ArrayList<String>();

    private List<String> nullableColumns = new ArrayList<String>();

    private Map<String, IndexDefinition> indexs = new HashMap<>();

    public Map<String, IndexDefinition> getIndexs() {
		return indexs;
	}

	private Map<String, FieldDefinition> definitions = new HashMap<String, FieldDefinition>();

    /**
     * 通过列名找到对应的bean的field名字
     */
    private Map<String,String> columsToField = new HashMap<String, String>();
    private Map<String,String> fieldToColums = new HashMap<String, String>();
    private boolean cache;

    /**
     * 基本信息
     */
    public TableDefinition(Class clazz, String tableName) {
        this.clazz = clazz;
        this.tableName = tableName;
    }

    
    public void addKey(FieldDefinition fieldDefinition) {
        keys.add(fieldDefinition.getFieldName());
        definitions.put(fieldDefinition.getFieldName(), fieldDefinition);
        columsToField.put(fieldDefinition.getColumnName(),fieldDefinition.getFieldName());
        fieldToColums.put(fieldDefinition.getFieldName(),fieldDefinition.getColumnName());
    }

    public void addColumn(FieldDefinition fieldDefinition) {
        columns.add(fieldDefinition.getFieldName());
        definitions.put(fieldDefinition.getFieldName(), fieldDefinition);
        columsToField.put(fieldDefinition.getColumnName(),fieldDefinition.getFieldName());
        fieldToColums.put(fieldDefinition.getFieldName(),fieldDefinition.getColumnName());
    }

    public void addNColumn(FieldDefinition fieldDefinition) {
        nullableColumns.add(fieldDefinition.getFieldName());
        definitions.put(fieldDefinition.getFieldName(), fieldDefinition);
        columsToField.put(fieldDefinition.getColumnName(),fieldDefinition.getFieldName());
        fieldToColums.put(fieldDefinition.getFieldName(),fieldDefinition.getColumnName());
    }

    public void addIndex(IndexDefinition indexDefinition) {
    	indexs.put(indexDefinition.getIndexName(), indexDefinition);
    }
    
    public String getTableName() {
        return tableName;
    }

    public List<String> getKeys() {
        return keys;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<String> getNullableColumns() {
        return nullableColumns;
    }

    public FieldDefinition getDefinition(String field) {
        return definitions.get(field);
    }

    public Map<String, String> getColumsToField() {
        return columsToField;
    }
    
    public Map<String, String> getFieldToColums() {
        return fieldToColums;
    }

    public Map<String, FieldDefinition> getDefinitions() {
        return definitions;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public boolean isCache() {
        return cache;
    }

    public Class getClazz() {
        return clazz;
    }

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
    
     
}
