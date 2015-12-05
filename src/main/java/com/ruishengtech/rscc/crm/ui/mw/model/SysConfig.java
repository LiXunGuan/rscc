package com.ruishengtech.rscc.crm.ui.mw.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * 
 * @author chengxin
 *
 */
@Table(name = "mw.sys_config")
public class SysConfig extends CommonDbBean {

    /*
    * 名称
     */
    @Column(meta = "VARCHAR(32)")
    private String name;

    /*
    * 值
     */
    @Column(meta = "VARCHAR(500)")
    private String val;
    
    /**
     * 坐席模板属性
     */
    private String status;
    private String busyReason;
    private String maxNoAnswer;
    private String wrapUpTime;
    private String rejectDelayTime;
    private String busyDelayTime;
    private String noAnswerDelayTime;
    
    
    /**
     * 默认路由属性
     */
    private String gatewayName;
    private String accessnumber;
    private String content;
    
    /**
     * 致忙原因属性
     * @return
     */
    private String name_1;
    private Integer state_1;
    private String name_2;
    private Integer state_2;
    private String name_3;
    private Integer state_3;
    private String name_4;
    private Integer state_4;
    private String name_5;
    private Integer state_5;
    private String name_6;
    private Integer state_6;
//    private String name_7;
//    private Integer state_7;
//    private String name_8;
//    private Integer state_8;
//    private String name_9;
//    private Integer state_9;
//    private String name_10;
//    private Integer state_10;
    
    /**
     * 并发属性
     */
    private String concurrentName;
    private String concurrentNumber;

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMaxNoAnswer() {
		return maxNoAnswer;
	}

	public void setMaxNoAnswer(String maxNoAnswer) {
		this.maxNoAnswer = maxNoAnswer;
	}

	public String getWrapUpTime() {
		return wrapUpTime;
	}

	public void setWrapUpTime(String wrapUpTime) {
		this.wrapUpTime = wrapUpTime;
	}

	public String getRejectDelayTime() {
		return rejectDelayTime;
	}

	public void setRejectDelayTime(String rejectDelayTime) {
		this.rejectDelayTime = rejectDelayTime;
	}

	public String getBusyDelayTime() {
		return busyDelayTime;
	}

	public void setBusyDelayTime(String busyDelayTime) {
		this.busyDelayTime = busyDelayTime;
	}

	public String getNoAnswerDelayTime() {
		return noAnswerDelayTime;
	}

	public void setNoAnswerDelayTime(String noAnswerDelayTime) {
		this.noAnswerDelayTime = noAnswerDelayTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName_1() {
		return name_1;
	}

	public void setName_1(String name_1) {
		this.name_1 = name_1;
	}

	public Integer getState_1() {
		return state_1;
	}

	public void setState_1(Integer state_1) {
		this.state_1 = state_1;
	}

	public String getName_2() {
		return name_2;
	}

	public void setName_2(String name_2) {
		this.name_2 = name_2;
	}

	public Integer getState_2() {
		return state_2;
	}

	public void setState_2(Integer state_2) {
		this.state_2 = state_2;
	}

	public String getName_3() {
		return name_3;
	}

	public void setName_3(String name_3) {
		this.name_3 = name_3;
	}

	public Integer getState_3() {
		return state_3;
	}

	public void setState_3(Integer state_3) {
		this.state_3 = state_3;
	}

	public String getName_4() {
		return name_4;
	}

	public void setName_4(String name_4) {
		this.name_4 = name_4;
	}

	public Integer getState_4() {
		return state_4;
	}

	public void setState_4(Integer state_4) {
		this.state_4 = state_4;
	}

	public String getName_5() {
		return name_5;
	}

	public void setName_5(String name_5) {
		this.name_5 = name_5;
	}

	public Integer getState_5() {
		return state_5;
	}

	public void setState_5(Integer state_5) {
		this.state_5 = state_5;
	}

	public String getName_6() {
		return name_6;
	}

	public void setName_6(String name_6) {
		this.name_6 = name_6;
	}

	public Integer getState_6() {
		return state_6;
	}

	public void setState_6(Integer state_6) {
		this.state_6 = state_6;
	}

//	public String getName_7() {
//		return name_7;
//	}
//
//	public void setName_7(String name_7) {
//		this.name_7 = name_7;
//	}
//
//	public Integer getState_7() {
//		return state_7;
//	}
//
//	public void setState_7(Integer state_7) {
//		this.state_7 = state_7;
//	}
//
//	public String getName_8() {
//		return name_8;
//	}
//
//	public void setName_8(String name_8) {
//		this.name_8 = name_8;
//	}
//
//	public Integer getState_8() {
//		return state_8;
//	}
//
//	public void setState_8(Integer state_8) {
//		this.state_8 = state_8;
//	}
//
//	public String getName_9() {
//		return name_9;
//	}
//
//	public void setName_9(String name_9) {
//		this.name_9 = name_9;
//	}
//
//	public Integer getState_9() {
//		return state_9;
//	}
//
//	public void setState_9(Integer state_9) {
//		this.state_9 = state_9;
//	}
//
//	public String getName_10() {
//		return name_10;
//	}
//
//	public void setName_10(String name_10) {
//		this.name_10 = name_10;
//	}
//
//	public Integer getState_10() {
//		return state_10;
//	}
//
//	public void setState_10(Integer state_10) {
//		this.state_10 = state_10;
//	}

	public String getConcurrentName() {
		return concurrentName;
	}

	public void setConcurrentName(String concurrentName) {
		this.concurrentName = concurrentName;
	}

	public String getConcurrentNumber() {
		return concurrentNumber;
	}

	public void setConcurrentNumber(String concurrentNumber) {
		this.concurrentNumber = concurrentNumber;
	}

	public String getGatewayName() {
		return gatewayName;
	}

	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	public String getAccessnumber() {
		return accessnumber;
	}

	public void setAccessnumber(String accessnumber) {
		this.accessnumber = accessnumber;
	}

	public String getBusyReason() {
		return busyReason;
	}

	public void setBusyReason(String busyReason) {
		this.busyReason = busyReason;
	}



}
