package com.ruishengtech.rscc.crm.billing.manager;

import java.util.HashMap;
import java.util.Map;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.rscc.crm.billing.model.BillRate;
import com.ruishengtech.rscc.crm.billing.service.BillRateService;


public class RateManager {

	private static RateManager singleInstance = new RateManager();

	public static RateManager getInstance() {
		return singleInstance;
	}
	
	private RateManager(){
		BillRateService billRateService = ApplicationHelper.getApplicationContext().getBean(BillRateService.class);
		this.agentRateMap = billRateService.getSipuserRateMap();
		this.groupRateMap = billRateService.getSkillGroupRateMap();
	}

	private Map<String, BillRate> agentRateMap = new HashMap<>();
	
	private Map<String, BillRate> groupRateMap = new HashMap<>();
	
	public void addAgent(String key, BillRate rate) {
		this.agentRateMap.put(key, rate);
	}
	
	public void addGroup(String key, BillRate rate) {
		this.groupRateMap.put(key, rate);
	}
	
	public void deleteAgent(String key, BillRate rate) {
		this.agentRateMap.remove(key);
	}
	
	public void deleteGroup(String key, BillRate rate) {
		this.groupRateMap.remove(key);
	}
	
	public void refresh() {
		BillRateService billRateService = ApplicationHelper.getApplicationContext().getBean(BillRateService.class);
		this.agentRateMap = billRateService.getSipuserRateMap();
		this.groupRateMap = billRateService.getSkillGroupRateMap();
	}
	
	public BillRate getAgentRate(String agentName) {
		BillRate rate = this.agentRateMap.get(agentName);
		if (rate == null)
			rate = this.agentRateMap.get(null);
		return rate;
	}
	
	public BillRate getGroupRate(String queueId) {
		BillRate rate = this.groupRateMap.get(queueId);
		if (rate == null)
			rate = this.groupRateMap.get(null);
		return rate;
	}
	
}
