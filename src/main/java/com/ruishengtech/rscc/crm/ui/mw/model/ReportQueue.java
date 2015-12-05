package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/10/21.
 */
@Table(name = "mw.report_queue")
public class ReportQueue extends CommonDbBean {
	
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
     * 技能组名
     */
    @Column(meta = "VARCHAR(30)")
   	private String name;

    /**
     * 技能组描述
     */
    @NColumn(meta = "VARCHAR(300)")
    private String info;
    
    /**
     * 呼入次数（未接通）
     */
    @Column(meta = "INT")
    private Integer in_f_callcount=0;
    
    /**
     * 呼入次数（接通）
     */
    @Column(meta = "INT")
    private Integer in_t_callcount=0;
    
    /**
     * 呼入等待时长（未接通）
     */
    @Column(meta = "INT")
    private Integer in_f_wait=0;
    
    /**
     * 呼入等待时长（接通）
     */
    @Column(meta = "INT")
    private Integer in_t_wait=0;
   
    /**
     * 呼入通话总时长
     */
    @Column(meta = "INT")
    private Integer in_t_duration=0;
    
    /**
     * 最大排队数  
     */
    @Column(meta = "INT")
    private Integer max_queued=0;
    
    /**
     * 最小排队数
     */
    @Column(meta = "INT")
    private Integer min_queued=0;
    
    /**
     * 几秒内接起电话
     */
    @Column(meta = "INT")
    private Integer answercount_0_5=0;
    @Column(meta = "INT")
    private Integer answercount_5_10=0;
    @Column(meta = "INT")
    private Integer answercount_10_15=0;
    @Column(meta = "INT")
    private Integer answercount_15_20=0;
    @Column(meta = "INT")
    private Integer answercount_20_25=0;
    @Column(meta = "INT")
    private Integer answercount_25_30=0;
    @Column(meta = "INT")
    private Integer answercount_30_40=0;
    @Column(meta = "INT")
    private Integer answercount_40_50=0;
    @Column(meta = "INT")
    private Integer answercount_50_60=0;
    @Column(meta = "INT")
    private Integer answercount_60_max=0;
    
    
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
	public Integer getIn_f_callcount() {
		return in_f_callcount;
	}
	public void setIn_f_callcount(Integer in_f_callcount) {
		this.in_f_callcount = in_f_callcount;
	}
	public Integer getIn_t_callcount() {
		return in_t_callcount;
	}
	public void setIn_t_callcount(Integer in_t_callcount) {
		this.in_t_callcount = in_t_callcount;
	}
	public Integer getIn_f_wait() {
		return in_f_wait;
	}
	public void setIn_f_wait(Integer in_f_wait) {
		this.in_f_wait = in_f_wait;
	}
	public Integer getIn_t_wait() {
		return in_t_wait;
	}
	public void setIn_t_wait(Integer in_t_wait) {
		this.in_t_wait = in_t_wait;
	}
	public Integer getIn_t_duration() {
		return in_t_duration;
	}
	public void setIn_t_duration(Integer in_t_duration) {
		this.in_t_duration = in_t_duration;
	}
	public Integer getMax_queued() {
		return max_queued;
	}
	public void setMax_queued(Integer max_queued) {
		this.max_queued = max_queued;
	}
	public Integer getMin_queued() {
		return min_queued;
	}
	public void setMin_queued(Integer min_queued) {
		this.min_queued = min_queued;
	}
	public Integer getAnswercount_0_5() {
		return answercount_0_5;
	}
	public void setAnswercount_0_5(Integer answercount_0_5) {
		this.answercount_0_5 = answercount_0_5;
	}
	public Integer getAnswercount_5_10() {
		return answercount_5_10;
	}
	public void setAnswercount_5_10(Integer answercount_5_10) {
		this.answercount_5_10 = answercount_5_10;
	}
	public Integer getAnswercount_10_15() {
		return answercount_10_15;
	}
	public void setAnswercount_10_15(Integer answercount_10_15) {
		this.answercount_10_15 = answercount_10_15;
	}
	public Integer getAnswercount_15_20() {
		return answercount_15_20;
	}
	public void setAnswercount_15_20(Integer answercount_15_20) {
		this.answercount_15_20 = answercount_15_20;
	}
	public Integer getAnswercount_20_25() {
		return answercount_20_25;
	}
	public void setAnswercount_20_25(Integer answercount_20_25) {
		this.answercount_20_25 = answercount_20_25;
	}
	public Integer getAnswercount_25_30() {
		return answercount_25_30;
	}
	public void setAnswercount_25_30(Integer answercount_25_30) {
		this.answercount_25_30 = answercount_25_30;
	}
	public Integer getAnswercount_30_40() {
		return answercount_30_40;
	}
	public void setAnswercount_30_40(Integer answercount_30_40) {
		this.answercount_30_40 = answercount_30_40;
	}
	public Integer getAnswercount_40_50() {
		return answercount_40_50;
	}
	public void setAnswercount_40_50(Integer answercount_40_50) {
		this.answercount_40_50 = answercount_40_50;
	}
	public Integer getAnswercount_50_60() {
		return answercount_50_60;
	}
	public void setAnswercount_50_60(Integer answercount_50_60) {
		this.answercount_50_60 = answercount_50_60;
	}
	public Integer getAnswercount_60_max() {
		return answercount_60_max;
	}
	public void setAnswercount_60_max(Integer answercount_60_max) {
		this.answercount_60_max = answercount_60_max;
	}
	
    
}
