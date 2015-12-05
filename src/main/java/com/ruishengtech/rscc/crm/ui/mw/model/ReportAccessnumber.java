package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/10/21.
 */
@Table(name = "mw.report_accessnumber")
public class ReportAccessnumber extends CommonDbBean {
	
    /**
     * 时间
     */
    @Column(meta = "INT")
    private Integer year;
    
    @Column(meta = "INT")
    private Integer month;
    
    @Column(meta = "INT")
    private Integer week;
    
    @Column(meta = "INT")
    private Integer day;
    
    @Column(meta = "INT")
    private Integer hour;
    
    @Column(meta = "DATETIME")
    private Date timestamp;

    /**
     * 接入号
     */
    @Column(meta = "VARCHAR(30)")
   	private String name;

    /**
     * 接入号描述
     */
    @NColumn(meta = "VARCHAR(300)")
    private String info;
    
    /**
     * 呼出次数（未接通）
     */
    @Column(meta = "INT")
    private Integer out_f_callcount=0;
    
    /**
     * 呼入次数（未接通）
     */
    @Column(meta = "INT")
    private Integer in_f_callcount=0;
    
    /**
     * 呼出次数（接通）
     */
    @Column(meta = "INT")
    private Integer out_t_callcount=0;

    /**
     * 呼入次数（接通）
     */
    @Column(meta = "INT")
    private Integer in_t_callcount=0;
    
    /**
     * 呼出通话总时长
     */
    @Column(meta = "INT")
    private Integer out_t_duration=0;
    
    /**
     * 呼入通话总时长
     */
    @Column(meta = "INT")
    private Integer in_t_duration=0;
    
    /**
     * 
     */
    @Column(meta = "INT")
    private Integer max_concurrent=0;
    
    /**
     * 
     */
    @Column(meta = "INT")
    private Integer min_concurrent=0;
    
    //导出数据用，组织时间格式
    private String exportdate;
    
    
	public String getExportdate() {
		return exportdate;
	}

	public void setExportdate(String exportdate) {
		this.exportdate = exportdate;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getOut_f_callcount() {
		return out_f_callcount;
	}

	public void setOut_f_callcount(Integer out_f_callcount) {
		this.out_f_callcount = out_f_callcount;
	}

	public Integer getIn_f_callcount() {
		return in_f_callcount;
	}

	public void setIn_f_callcount(Integer in_f_callcount) {
		this.in_f_callcount = in_f_callcount;
	}

	public Integer getOut_t_callcount() {
		return out_t_callcount;
	}

	public void setOut_t_callcount(Integer out_t_callcount) {
		this.out_t_callcount = out_t_callcount;
	}

	public Integer getIn_t_callcount() {
		return in_t_callcount;
	}

	public void setIn_t_callcount(Integer in_t_callcount) {
		this.in_t_callcount = in_t_callcount;
	}

	public Integer getOut_t_duration() {
		return out_t_duration;
	}

	public void setOut_t_duration(Integer out_t_duration) {
		this.out_t_duration = out_t_duration;
	}

	public Integer getIn_t_duration() {
		return in_t_duration;
	}

	public void setIn_t_duration(Integer in_t_duration) {
		this.in_t_duration = in_t_duration;
	}

	public Integer getMax_concurrent() {
		return max_concurrent;
	}

	public void setMax_concurrent(Integer max_concurrent) {
		this.max_concurrent = max_concurrent;
	}

	public Integer getMin_concurrent() {
		return min_concurrent;
	}

	public void setMin_concurrent(Integer min_concurrent) {
		this.min_concurrent = min_concurrent;
	}

}
