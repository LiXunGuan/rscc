package com.ruishengtech.rscc.crm.qc.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.rscc.crm.qc.model.QualityCheck;
import com.ruishengtech.rscc.crm.qc.service.QualityCheckService;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;

@Service
@Transactional
public class QualityCheckServiceImp extends BaseService implements QualityCheckService{
	
	public void save(QualityCheck check) {
		super.save(check);
	}

	public void save(QualityCheck check, String str[]) {
		super.save(check, str);
	}

	public void update(QualityCheck check, String str[]) {
		super.update(check, str);
	}

	public void update(QualityCheck check) {
		super.update(check);
	}

	/**
	 * 保存
	 * @param check
	 * @param str
	 */
	public void saveOrUpdate(QualityCheck check, String str[]) {

		if (null != check) {
			if (null != str && str.length > 0) {
				if (check.getUid() != null) {
					super.update(check, str);
				} else {
					super.save(check, str);
				}
			} else {
				if (check.getUid()!= null) {
					super.update(check);
				} else {
					super.save(check);
				}
			}
		}
	}

	/**
	 * 根据一个uuid查询质检对象
	 * @param uuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public QualityCheck getQualityByObjUUID(final String uuid) {
		
		List<QualityCheck> checks  = super.queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append("SELECT * FROM  qualitycheck WHERE 1 =1 AND uuid_obj = ?");
				params.add(uuid);
			}
		}, QualityCheck.class);
		
		if(checks.size() > 0 ){
			return checks.get(0);
		}
		
		return null;
	}

	@Override
	public void saveQualityCheck(QualityCheck check) {
		super.save(check);
	}

	@Override
	public void deleteQualityCheckByUuidObj(String uuidObj) {
		jdbcTemplate.update(" DELETE FROM qualitycheck WHERE uuid_obj = ? ", uuidObj);
	}

}
