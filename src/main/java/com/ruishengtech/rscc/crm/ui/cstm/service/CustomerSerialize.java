package com.ruishengtech.rscc.crm.ui.cstm.service;
/**
 * 客户、客服公共的标准编号序列化接口类
 * 其他客户实现自己的序列化类，实现里面的方法即可
 * 
 * @author Frank
 */

public interface CustomerSerialize {
	
	/**
	 * 得到客户序列编号
	 * @return
	 */
	public abstract String getCstmSerializeId();
	
	/**
	 * 得到客户服务序列编号
	 * @return
	 */
	public abstract String getCstmserviceSerializeId();
	
}