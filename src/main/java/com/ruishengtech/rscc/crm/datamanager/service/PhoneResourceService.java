package com.ruishengtech.rscc.crm.datamanager.service;

/**
 * @author Frank
 *
 */
public interface PhoneResourceService {

	/**
	 * 根据号码查询资源表中是否存在该对应数据[仅仅作是否存在判断处理]
	 * @param phone
	 * @return true 存在 false不存在
	 */
	abstract boolean getPhoneResourceByPhone(String phone);
}
