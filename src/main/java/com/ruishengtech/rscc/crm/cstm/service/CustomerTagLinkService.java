package com.ruishengtech.rscc.crm.cstm.service;

import java.util.List;

public interface CustomerTagLinkService {

	/**
	 * 保存中间数据表
	 * @param tagId 标签编号
	 * @param customerId 客户编号
	 * @return 成功或者失败
	 */
	public abstract boolean saveCustomerTagLink(String tagId, String customerId);

	/**
	 * 绑定客户标签
	 * 
	 * @param cstmId
	 *            客户编号
	 * @param tags
	 *            标签集合
	 * @return
	 */
	public abstract void bindTagsToCustomer(String cstmId, List<String> tags);

	/**
	 * 去除客户标签
	 * 
	 * @param cstmId
	 */
	public abstract void unBindTagsFromCustomer(String cstmId);

	/**添加客户标签
	 * @param cstmId 客户编号
	 * @param tags 客户标签
	 */
	public abstract void bindTagsForCustomer(String cstmId, List<String> tags);

}