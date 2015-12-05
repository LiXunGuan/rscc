package com.ruishengtech.rscc.crm.record.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.rscc.crm.record.condition.RecordCondition;
import com.ruishengtech.rscc.crm.record.model.AccessNumber;
import com.ruishengtech.rscc.crm.record.model.Record;

/**
 * RecordServce
 * @author Frank
 *
 */
public interface RecordService {
	
	
	
	public abstract void insertData();
	
	public abstract void saveOrUpdate(Record record);
	
	/**
	 * 分页查询所有符合条件的数据
	 * @param condition
	 * @return
	 */
	public abstract PageResult<Record> queryPage(RecordCondition condition);
	
	/**
	 * 查询所有符合条件的数据 不分页
	 * @param condition
	 * @return
	 */
	public abstract List<Record> getRecordsList(RecordCondition condition);
	
	/**
	 * 查询单挑信息
	 * @param uuid
	 * @return
	 */
	public abstract Record getRecordByCallsessionuuid(String uuid);
	
	/**
	 * 查询所有分机号
	 * @return
	 */
	public abstract List<AccessNumber> getAllAccessNumbers();
	
	/**
	 * 导出搜索
	 * @param re
	 * @param rq
	 * @param rc
	 */
	public abstract void getExcelCreated(HttpServletRequest re, HttpServletResponse rq, RecordCondition rc);
	
	/**
	 * 根据callsessionuuid查询录音信息
	 * @param callsessionuuid
	 * @return
	 */
	public abstract List<Record> getRecord(String callsessionuuid);
	
	public abstract String getSeconds(long str);
	
	public void deleteRecord(String uuid);
	
}
