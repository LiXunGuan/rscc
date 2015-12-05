package com.ruishengtech.rscc.crm.cstm.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * 客户电话号码
 * @author Frank
 *
 */
@Table(name="cstm_phone")
public class CstmPhone {

    @Key
    @Column(meta = "SERIAL")
    private Integer id;
    
    @Column(meta = "VARCHAR(64)" , column="uuid")
    private String uuid;
    /**
     * 主号码
     */
    @Column(meta = "VARCHAR(64)" , column="main_number")
    @Index(name = "mainNumber", type = IndexDefinition.TYPE_NORMAL, method = IndexDefinition.METHOD_BTREE)
    private String mainNumber;
    
    /**
     * 其他次要号码
     */
    @NColumn(meta = "VARCHAR(128)" , column="minor_number")
    @Index(name = "minorBumber", type = IndexDefinition.TYPE_NORMAL, method = IndexDefinition.METHOD_BTREE)
    private String minorBumber;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getMainNumber() {
		return mainNumber;
	}


	public void setMainNumber(String mainNumber) {
		this.mainNumber = mainNumber;
	}


	public String getMinorBumber() {
		return minorBumber;
	}


	public void setMinorBumber(String minorBumber) {
		this.minorBumber = minorBumber;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
    
}
