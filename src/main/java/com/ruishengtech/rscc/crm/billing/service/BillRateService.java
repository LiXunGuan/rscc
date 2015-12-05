package com.ruishengtech.rscc.crm.billing.service;

import java.util.List;
import java.util.Map;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.rscc.crm.billing.condition.BillRateCondition;
import com.ruishengtech.rscc.crm.billing.model.BillRate;
import com.ruishengtech.rscc.crm.billing.model.BillRateSipuserLink;
import com.ruishengtech.rscc.crm.billing.model.BillRateSkillGroupLink;
import com.ruishengtech.rscc.crm.cstm.model.Customer;
import com.ruishengtech.rscc.crm.data.model.DataContainer;


public interface BillRateService {
	/**
	 * 获取所有费率信息
	 * @return
	 */
	public List<BillRate> getAllBillRate();
	
	public List<BillRate> getBillRateForparam(String billRateType,String billRateName);
	
	public abstract List<BillRate> query(final String billratetype);
	/**
	 * 根据uuid获取费率信息
	 * @param uuid
	 * @return
	 */
	public BillRate getBillRateByUUID(UUID uuid);
	
	/**
	 * 获取费率和分机中间表数据
	 * @param uuid
	 * @return
	 */
	public List<BillRateSipuserLink> getBillSipuser();
	
	/**
	 * 获取费率和技能组中间表数据
	 * @param uuid
	 * @return
	 */
	public List<BillRateSkillGroupLink> getBillSkillGroup();
	
	/**
	 * 添加费率信息
	 * @return
	 */
	public void save(BillRate br);
	
	/**
	 * 更新费率信息
	 */
	public boolean update(BillRate br,String[] exculdeFieldName);
	
	public boolean update(BillRate br);
	/**
	 * 删除费率信息
	 */
	public boolean deleteByID(UUID uuid);
	
	/**
	 * 给费率和分机关联表插入数据
	 * @return
	 */
	public boolean insertRateSipuser(String billRateUUID,String userUUID);
	
	/**
	 * 分配坐席后更新费率和分机关联表数据
	 */
	public boolean updateRateSipuser(String billRateUUID,List<String> userid);
	
	/**
	 * 给费率和技能组关联表插入数据
	 * @return
	 */
	public boolean insertRateSkill(String billRateUUID,String skillid);
	
	/**
	 * 分配技能组后更新费率和技能关联表数据
	 */
	public boolean updateRateSkill(String billRateUUID,List<String> skillid);
	
	/**
	 * 保存费率uuid到关联表
	 * @param br
	 * @param billRateUUID
	 */
	public void save(BillRate br, String billRateUUID);
	
	/**
	 * 根据登录用户名获取费率
	 */
//	public BillRate getBillRateByLoginName(String loginname);
	
	public Map<String, BillRate> getSipuserRateMap();
	
	public Map<String, BillRate> getSkillGroupRateMap();
	
	public void deleteRateSipuserById(String rateuuid,String sipid);
	
	public void deleteRateSkillGroupById(String rateuuid,String skillid);
}
