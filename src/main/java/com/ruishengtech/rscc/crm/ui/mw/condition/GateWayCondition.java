package com.ruishengtech.rscc.crm.ui.mw.condition;

import com.ruishengtech.framework.core.db.condition.Page;

/**
 * Created by yaoliceng on 2014/11/3.
 */
public class GateWayCondition extends Page {
	
	private Integer gateWayStrat;
	private Integer gateWayEnd;
	private String registration;
	
	
	public Integer getGateWayStrat() {
		return gateWayStrat;
	}
	public void setGateWayStrat(Integer gateWayStrat) {
		this.gateWayStrat = gateWayStrat;
	}
	public Integer getGateWayEnd() {
		return gateWayEnd;
	}
	public void setGateWayEnd(Integer gateWayEnd) {
		this.gateWayEnd = gateWayEnd;
	}
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	
	
}
