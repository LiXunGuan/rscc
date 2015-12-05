package com.ruishengtech.rscc.crm.datamanager.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Wangyao
 *
 */
@Table(name = "new_data_group_call")
public class NewGroupCall extends CommonDbBean { 
	
	public static final String stat_not_get = "0";
	
	public static final String stat_geted = "1";
	
	public static final String stat_called = "2";
	
	//单方接通
	public static final String stat_single_answered = "3";
	
	//双方接通
	public static final String stat_answered = "4";
	
	public static final String stat_errored = "5";
	
	//群呼任务ID
    @Column(meta = "VARCHAR(64)", column = "groupcall_id")
    private String groupcall_id;

    //任务描述
    @NColumn(meta = "VARCHAR(64)", column = "description")
    private String description;

    //群呼策略
    @NColumn(meta = "VARCHAR(64)", column = "strategy")
    private String strategy;
    
    //并发数
    @NColumn(meta = "VARCHAR(64)", column = "concurrency")
    private String concurrency;
    
    //动态群呼的比率（并发数/空闲坐席数）
    @NColumn(meta = "VARCHAR(64)", column = "ratio")
    private String ratio;

    //外呼所使用的gateway
    @NColumn(meta = "VARCHAR(64)", column = "gateway")
    private String gateway;
    
    //外呼所使用的主叫号
    @NColumn(meta = "VARCHAR(64)", column = "caller_id_num")
    private String caller_id_num;
    
    //呼叫接通后，转到这个exten
    @NColumn(meta = "VARCHAR(64)", column = "dst_exten")
    private String dst_exten;
    
    //中间件向此URL获取群呼所要呼叫的电话数据
    @NColumn(meta = "VARCHAR(80)", column = "data_src_url")
    private String data_src_url;
    
    //中间件向此URL返回呼叫结果
    @NColumn(meta = "VARCHAR(80)", column = "data_dst_url")
    private String data_dst_url;
    
    //任务状态
    @NColumn(meta = "VARCHAR(64)", column = "stat")
    private String stat;
    
    //任务数量
    @NColumn(meta = "INT DEFAULT 0", column = "data_count")
    private Integer dataCount;
    
    //当前数据来源，默认为0，通常数据。2为未接通，3为单方接通
    @NColumn(meta = "INT DEFAULT 0", column = "current_source")
    private Integer currentSource;

	public Integer getCurrentSource() {
		return currentSource;
	}

	public void setCurrentSource(Integer currentSource) {
		this.currentSource = currentSource;
	}

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
//		this.strategy = strategyMap.get(strategy);
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

	public Integer getDataCount() {
		return dataCount;
	}

	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}
 
}
