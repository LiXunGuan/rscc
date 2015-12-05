package com.ruishengtech.rscc.crm.data.condition;

import com.ruishengtech.framework.core.db.condition.Page;

public class GroupCallCondition extends Page {
	
	//群呼任务ID
    private String groupcall_id;

    //任务描述
    private String description;

    //群呼策略
    private String strategy;
    
    //并发数
    private String concurrency;
    
    //动态群呼的比率（并发数/空闲坐席数）
    private String ratio;

    //外呼所使用的gateway
    private String gateway;
    
    //外呼所使用的主叫号
    private String caller_id_num;
    
    //呼叫接通后，转到这个exten
    private String dst_exten;
    
    //中间件向此URL获取群呼所要呼叫的电话数据
    private String data_src_url;
    
    //中间件向此URL返回呼叫结果
    private String data_dst_url;
    
    //任务状态
    private String stat;
    
    public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getGroupcall_id() {
		return groupcall_id;
	}

	public void setGroupcall_id(String groupcall_id) {
		this.groupcall_id = groupcall_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getConcurrency() {
		return concurrency;
	}

	public void setConcurrency(String concurrency) {
		this.concurrency = concurrency;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getCaller_id_num() {
		return caller_id_num;
	}

	public void setCaller_id_num(String caller_id_num) {
		this.caller_id_num = caller_id_num;
	}

	public String getDst_exten() {
		return dst_exten;
	}

	public void setDst_exten(String dst_exten) {
		this.dst_exten = dst_exten;
	}

	public String getData_src_url() {
		return data_src_url;
	}

	public void setData_src_url(String data_src_url) {
		this.data_src_url = data_src_url;
	}

	public String getData_dst_url() {
		return data_dst_url;
	}

	public void setData_dst_url(String data_dst_url) {
		this.data_dst_url = data_dst_url;
	}
	
}
