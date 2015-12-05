package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/10/21.
 */
@Table(name = "mw.report_agentkpi")
public class ReportAgentkpi extends CommonDbBean {
	
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
    
    
    
    @Column(meta = "DATETIME")
    private Date timestamp;
    
    /**
     * 坐席名
     */
    @Column(meta = "VARCHAR(30)")
   	private String name;

    /**
     * 坐席描述
     */
    @NColumn(meta = "VARCHAR(300)")
    private String info;
    
    /**
     * 在线时长
     */
    @NColumn(meta = "INT")
    private	Integer  online_time=0;
    
//    /**
//     * 话后次数
//     */
//    @NColumn(meta = "INT")
//    private	Integer  acw_count=0;
//
//    /**
//     * 话后时长
//     */
//    @NColumn(meta = "INT")
//    private	Integer  acw_duration=0;
    
    /**
     * 评分次数
     */
    @NColumn(meta = "INT")
    private	Integer  score_count=0;
    
    /**
     * 评分分数
     */
    @NColumn(meta = "INT")
    private	Integer  score_sum=0;
    
    /**
     * 致忙时长
     */
    @NColumn(meta = "INT",column="pause_reason_1_count")
    private Integer pause_reason_1_count=0;
    @NColumn(meta = "INT",column="pause_reason_2_count")
    private Integer pause_reason_2_count=0;
    @NColumn(meta = "INT",column="pause_reason_3_count")
    private Integer pause_reason_3_count=0;
    @NColumn(meta = "INT",column="pause_reason_4_count")
    private Integer pause_reason_4_count=0;
    @NColumn(meta = "INT",column="pause_reason_5_count")
    private Integer pause_reason_5_count=0;
    @NColumn(meta = "INT",column="pause_reason_6_count")
    private Integer pause_reason_6_count=0;
    @NColumn(meta = "INT",column="pause_reason_7_count")
    private Integer pause_reason_7_count=0;
    @NColumn(meta = "INT",column="pause_reason_8_count")
    private Integer pause_reason_8_count=0;
    @NColumn(meta = "INT",column="pause_reason_9_count")
    private Integer pause_reason_9_count=0;
    @NColumn(meta = "INT",column="pause_reason_10_count")
    private Integer pause_reason_10_count=0;

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
	public Integer getOnline_time() {
		return online_time;
	}
	public void setOnline_time(Integer online_time) {
		this.online_time = online_time;
	}

	public Integer getScore_count() {
		return score_count;
	}
	public void setScore_count(Integer score_count) {
		this.score_count = score_count;
	}
	public Integer getScore_sum() {
		return score_sum;
	}
	public void setScore_sum(Integer score_sum) {
		this.score_sum = score_sum;
	}
	public Integer getPause_reason_1_count() {
		return pause_reason_1_count;
	}
	public void setPause_reason_1_count(Integer pause_reason_1_count) {
		this.pause_reason_1_count = pause_reason_1_count;
	}
	public Integer getPause_reason_2_count() {
		return pause_reason_2_count;
	}
	public void setPause_reason_2_count(Integer pause_reason_2_count) {
		this.pause_reason_2_count = pause_reason_2_count;
	}
	public Integer getPause_reason_3_count() {
		return pause_reason_3_count;
	}
	public void setPause_reason_3_count(Integer pause_reason_3_count) {
		this.pause_reason_3_count = pause_reason_3_count;
	}
	public Integer getPause_reason_4_count() {
		return pause_reason_4_count;
	}
	public void setPause_reason_4_count(Integer pause_reason_4_count) {
		this.pause_reason_4_count = pause_reason_4_count;
	}
	public Integer getPause_reason_5_count() {
		return pause_reason_5_count;
	}
	public void setPause_reason_5_count(Integer pause_reason_5_count) {
		this.pause_reason_5_count = pause_reason_5_count;
	}
	public Integer getPause_reason_6_count() {
		return pause_reason_6_count;
	}
	public void setPause_reason_6_count(Integer pause_reason_6_count) {
		this.pause_reason_6_count = pause_reason_6_count;
	}
	public Integer getPause_reason_7_count() {
		return pause_reason_7_count;
	}
	public void setPause_reason_7_count(Integer pause_reason_7_count) {
		this.pause_reason_7_count = pause_reason_7_count;
	}
	public Integer getPause_reason_8_count() {
		return pause_reason_8_count;
	}
	public void setPause_reason_8_count(Integer pause_reason_8_count) {
		this.pause_reason_8_count = pause_reason_8_count;
	}
	public Integer getPause_reason_9_count() {
		return pause_reason_9_count;
	}
	public void setPause_reason_9_count(Integer pause_reason_9_count) {
		this.pause_reason_9_count = pause_reason_9_count;
	}
	public Integer getPause_reason_10_count() {
		return pause_reason_10_count;
	}
	public void setPause_reason_10_count(Integer pause_reason_10_count) {
		this.pause_reason_10_count = pause_reason_10_count;
	}
	

}
