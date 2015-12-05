package com.ruishengtech.rscc.crm.datamanager.service;

import java.util.Date;

import com.ruishengtech.rscc.crm.datamanager.model.DeptToUserTransgerLog;

/**
 * @author Frank
 *
 */
public interface DeptToUserTransgerLogService {

	/**
	 * 保存一条记录
	 */
	void save(DeptToUserTransgerLog toUserTransgerLog);
	
	/**
	 * 根据日期获取日志
	 * @param date
	 */
	void getLogsByDate(Date date);
	
	/**
	 * 根据日期和人的编号获取已经获取的记录数
	 * @param date
	 * @param userUuid
	 */
	Integer getLogCount(Date date ,String userUuid);
}
