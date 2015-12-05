package com.ruishengtech.framework.core.db.service;


import com.ruishengtech.framework.core.db.DbContext;
import com.ruishengtech.framework.core.db.FieldDefinition;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.TableDefinition;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service(value="tableService")
@Transactional
public class TableService extends BaseService {

    public static final String NOT_NULL = " NOT NULL";

    public void createTable() {

        Map<Class, TableDefinition> tablesMap = DbContext.getInstance().getTablesMap();
        for (TableDefinition tableDefinition : tablesMap.values()) {
            getTemplate().update(createTable(tableDefinition));
        }
    }

    public void createTable(Class aClass, String tableName) {
        TableDefinition tableDefinition = DbContext.getTableDefinition(aClass, tableName);
        tableDefinition.setTableName(tableName);
        getTemplate().update(createTable(tableDefinition));
    }
    
    public void createTable(Class aClass, String tableName, Collection<ColumnDesign> columns) {

        TableDefinition tableDefinition = DbContext.getTableDefinition(tableName, aClass, columns);
        tableDefinition.setTableName(tableName);
        getTemplate().update(createTable(tableDefinition));
}

    private String createTable(TableDefinition tableDefinition) {

        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE  IF NOT EXISTS ").append(tableDefinition.getTableName());
        sql.append(" ( ");

        StringBuilder keySql = new StringBuilder();
        for (String key : tableDefinition.getKeys()) {
            FieldDefinition def = tableDefinition.getDefinition(key);
            sql.append(def.getColumnName()).append(" ").append(def.getMeta()).append(NOT_NULL).append(", ");
            keySql.append(def.getColumnName()).append(",");
        }
        for (String col : tableDefinition.getColumns()) {
            FieldDefinition def = tableDefinition.getDefinition(col);
            sql.append(def.getColumnName()).append(" ").append(def.getMeta()).append(NOT_NULL).append(", ");
        }
        for (String col : tableDefinition.getNullableColumns()) {
            FieldDefinition def = tableDefinition.getDefinition(col);
            sql.append(def.getColumnName()).append(" ").append(def.getMeta()).append(", ");
        }
        sql.append("PRIMARY KEY (" + keySql.deleteCharAt(keySql.length() - 1) + ") ");

        for (IndexDefinition index:tableDefinition.getIndexs().values()) {
        	sql.append(",").append(index.getIndexType()).append(" INDEX ").append(index.getIndexName())
        		.append("(").append(index.getIndexColumn()).append(")").append(" USING ").append(index.getIndexMethod());
        }
        
        sql.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");

        return sql.toString();
    }
    
    public void deleteTable(String tableName) {
		jdbcTemplate.update("drop table if exists " + tableName);
	}

}
