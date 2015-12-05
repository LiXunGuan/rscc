package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/11/19.
 */

@Table(name = "mw.mw_accessnumber_route")
public class MWAccessnumberRoute extends CommonDbBean{
	
	@Column(meta = "VARCHAR(30)")
	private String name;

    @Column(meta = "VARCHAR(30)")
    private String number;

    @Column(meta = "DATETIME")
    private Date startDate;

    @Column(meta = "DATETIME")
    private Date endDate;

    @Column(meta = "VARCHAR(1)")
    private String type;

    @Column(meta = "VARCHAR(500)")
    private String period;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(meta = "INT")
    private Long extenRouteId;

    @Column(meta = "VARCHAR(30)")
    private String extension;

    @Column(meta = "INT")
    private Integer rank;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getExtenRouteId() {
        return extenRouteId;
    }

    public void setExtenRouteId(Long extenRouteId) {
        this.extenRouteId = extenRouteId;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    
}
