package com.ruishengtech.rscc.crm.qc.service;

import com.ruishengtech.rscc.crm.qc.model.QualityCheck;

/**
 * @author Frank
 *
 */
public interface QualityCheckService {

	/**
	 * 保存一个质检对象
	 * @param check
	 */
	public abstract void saveOrUpdate(QualityCheck check, String str[]);
	
	public abstract void saveQualityCheck(QualityCheck check);
	

	/**
	 * 根据一个uuid查询质检对象
	 * @param uuid
	 * @return
	 */
	public abstract QualityCheck getQualityByObjUUID(String uuid);
	
	public void deleteQualityCheckByUuidObj(String uuidObj);
	
}
