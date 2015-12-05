package com.ruishengtech.framework.core.db;

import org.apache.commons.lang3.StringUtils;


public class FieldDefinition {

    private String fieldName;

    private String columnName;

    private String meta;

    public FieldDefinition(String fieldName, String columnName, String meta) {
        this.fieldName = fieldName;
        this.columnName = StringUtils.isBlank(columnName) ? fieldName.toLowerCase() : columnName;
        this.meta = meta;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }
}
