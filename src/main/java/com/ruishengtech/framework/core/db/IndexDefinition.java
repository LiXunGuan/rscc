package com.ruishengtech.framework.core.db;



public class IndexDefinition {

	public static final String TYPE_UNIQUE = "UNIQUE ";
	
	public static final String TYPE_NORMAL = "";
	
	public static final String METHOD_BTREE = " BTREE";

	public static final String METHOD_HASH = " HASH";
	
    private String indexName;

    private String indexColumn;
    
    private String indexType;

    private String indexMethod;

    public IndexDefinition(String indexName, String indexColumn, String indexType, String indexMethod) {
        this.indexName = indexName;
        this.indexColumn = indexColumn;
        this.indexType = indexType;
        this.indexMethod = indexMethod;
    }

	public String getIndexName() {
		return indexName;
	}

	public String getIndexColumn() {
		return indexColumn;
	}

	public void setIndexColumn(String indexColumn) {
		this.indexColumn = indexColumn;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public String getIndexMethod() {
		return indexMethod;
	}

	public void setIndexMethod(String indexMethod) {
		this.indexMethod = indexMethod;
	}


}
