package com.ruishengtech.rscc.crm.cstm.service;

import org.apache.commons.lang3.StringUtils;

import com.ruishengtech.rscc.crm.cstm.model.CstmPhone;

public interface CstmPhoneService {
	
	/**
	 * 得到所有主号码的字号码
	 * @param phone
	 * @return
	 */
	public abstract String getAllCstmPhones(String phone);
	
	/**
	 * 添加主号码和从号码
	 * @param phone
	 * @return
	 */
	public abstract void save(CstmPhone cstmPhone);
	
	
	/**
	 * 修改
	 * @param cstmPhone
	 */
	public abstract void update(CstmPhone cstmPhone);
	
	/**
	 * 查询编号
	 * @param phone
	 * @param uuid
	 * @return
	 */
	public abstract String getCstmPhoneId(String phone, String uuid);
	
	/**
	 * 根据主号码查询
	 * @param mainPhone
	 * @return
	 */
	public CstmPhone getCstmPhone(final String mainPhone);
	
    /**
	 * 删除号码前面所有的0
	 * 
	 * @param targetNumber
	 *            目标号码
	 * @return 返回的号码
	 */
    public abstract String deletZero(String targetNumber);

}
