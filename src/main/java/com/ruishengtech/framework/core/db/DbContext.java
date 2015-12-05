package com.ruishengtech.framework.core.db;

import java.lang.reflect.Field;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.framework.core.db.diy.ColumnDesign;

public class DbContext {


    private static DbContext ourInstance = new DbContext();

    public static DbContext getInstance() {
        return ourInstance;
    }

    private DbContext() {
    }


    /**
     * 所有表的结构信息
     */
    private Map<Class, TableDefinition> tablesMap = new HashMap<Class, TableDefinition>();

    /**
     * 所有有table信息的class 集合
     */
    private List<Class> tableClassHolder = new ArrayList<Class>();

    public Map<Class, TableDefinition> getTablesMap() {
        return tablesMap;
    }

    /**
     * 注册数据库实体类
     */
    public void addTable(Class clazz) {
        tableClassHolder.add(clazz);
    }

    public void init() {

        for (Class<?> aClass : tableClassHolder) {

            if (aClass.isAnnotationPresent(Table.class)) {

                TableDefinition tableDefinition = getTableDefinition(aClass);
                tablesMap.put(aClass, tableDefinition);
            }
        }
    }

    public static TableDefinition getTableDefinition(Class<?> aClass) {
        Table tableAnnotation = aClass.getAnnotation(Table.class);
        TableDefinition tableDefinition = new TableDefinition(aClass, tableAnnotation.name());
        tableDefinition.setCache(tableAnnotation.cache());

        fillfield(aClass, tableDefinition);
        return tableDefinition;
    }
    
    public static TableDefinition getTableDefinition(String tableName, Class<?> aClass, Collection<ColumnDesign> columns) {
        TableDefinition tableDefinition = new TableDefinition(aClass, tableName);
        fillfield(aClass, tableDefinition);
        fillWithColumnDesign(tableDefinition, columns);
        return tableDefinition;
    }

    private static void fillWithColumnDesign(TableDefinition tableDefinition,
			Collection<ColumnDesign> columns) {
    	//新增列允许为空
    	if(columns==null)
    		return;
    	Set<String> c = tableDefinition.getColumsToField().keySet();
        for (ColumnDesign column : columns) {
        	if(!c.contains(column.getColumnNameDB())) {
	        	String meta;
	        	String length = column.getCharacterProperty();
	    		if(ColumnDesign.INTTYPE.equals(column.getColumnType())){
	    			if(StringUtils.isBlank(length)){
	    				length = String.valueOf(11);
	    			}
	    			meta = " INT("+ length +") DEFAULT " + column.getColumnValue();
	    		}else if(ColumnDesign.DATETYPE.equals(column.getColumnType())){
	    			meta = " TIMESTAMP DEFAULT NOW() ";
	    		}else if(ColumnDesign.FLOATTYPE.equals(column.getColumnType())){
	    			meta = " FLOAT(9,2) DEFAULT " + column.getColumnValue();
	    		}else{
	    			if(StringUtils.isBlank(length)){
	    				length = String.valueOf(100);
	    			}
	    			meta = " VARCHAR(" + length + ") DEFAULT " + column.getColumnValue();
	    		}
	            FieldDefinition fieldDefinition = new FieldDefinition(column.getColumnName(),
	                    column.getColumnNameDB(), meta);
	            tableDefinition.addNColumn(fieldDefinition);
        	}
        }
    }

	public static TableDefinition getTableDefinition(Class aClass, String tableName) {
        TableDefinition tableDefinition = new TableDefinition(aClass,tableName);
        tableDefinition.setCache(false);
        fillfield(aClass, tableDefinition);
        return tableDefinition;
    }

    private static void fillfield(Class<?> aClass, TableDefinition tableDefinition) {
        //所有属性
        List<Field> fields = new ArrayList<Field>();
        Collections.addAll(fields, aClass.getDeclaredFields());
        Collections.addAll(fields, aClass.getSuperclass().getDeclaredFields());

        for (Field field : fields) {

        	String columnName = "";
        	
            if (field.isAnnotationPresent(Column.class)) {

                Column columnAnnotation = field.getAnnotation(Column.class);
                FieldDefinition fieldDefinition = new FieldDefinition(field.getName(),
                        columnAnnotation.column(), columnAnnotation.meta());
                
                columnName = "".equals(columnAnnotation.column())?field.getName():columnAnnotation.column();
                
                if (field.isAnnotationPresent(Key.class)) {
                    tableDefinition.addKey(fieldDefinition);
                } else {
                    tableDefinition.addColumn(fieldDefinition);
                }

            } else if (field.isAnnotationPresent(NColumn.class)) {

                NColumn columnAnnotation = field.getAnnotation(NColumn.class);
                FieldDefinition fieldDefinition = new FieldDefinition(field.getName(),
                        columnAnnotation.column(), columnAnnotation.meta());
                
                columnName = "".equals(columnAnnotation.column())?field.getName():columnAnnotation.column();
                
                tableDefinition.addNColumn(fieldDefinition);
            }
            
            if (field.isAnnotationPresent(Index.class)) {
            	Index index = field.getAnnotation(Index.class);
            	tableDefinition.addIndex(
            			new IndexDefinition("".equals(index.name())?columnName:index.name(), columnName, index.type(), index.method()));
            }
        }
    }

    public Class getClassByTable(String tableName) {

        for (Class aClass : tablesMap.keySet()) {

            TableDefinition df = tablesMap.get(aClass);
            if (tableName.equals(df.getTableName())) {
                return aClass;
            }
        }
        return null;
    }

}
