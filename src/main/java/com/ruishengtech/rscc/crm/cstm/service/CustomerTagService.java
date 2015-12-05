package com.ruishengtech.rscc.crm.cstm.service;

import java.util.List;

import com.ruishengtech.rscc.crm.cstm.model.Customer;

public interface CustomerTagService {

	/**
	 * 查询指定类型的tag
	 * 
	 * @param tag
	 *            客户标签
	 * @return
	 */
	public abstract List<Customer> getCustomersByTags(String tag);

	/**
	 * 查询指定类型的tag
	 * 
	 * @param tag
	 *            客户标签集合
	 * @return
	 */
	public abstract List<Customer> getCustomersByTags(List<String> tags);

	
	/**
	 * 根据客户编号获取客户标签
	 * @param cstm
	 * @return
	 */
	public abstract List<String> getCustomerTags(String uuid);
	
}