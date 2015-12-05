package com.ruishengtech.rscc.crm.cstm.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.cstm.model.CstmPhone;
import com.ruishengtech.rscc.crm.cstm.service.CstmPhoneService;

@Service
@Transactional
public class CstmPhoneServiceImp extends BaseService implements
		CstmPhoneService {

	/**
	 * 
	 * 得到所有主号码的字号码
	 * 
	 * @param phone
	 * @return
	 */
	@Override
	public String getAllCstmPhones(String phone) {

		if (StringUtils.isNotBlank(phone)) {

			List<String> phoneList = jdbcTemplate.queryForList(" SELECT minor_number FROM cstm_phone WHERE main_number = ?",
					String.class, phone);

			if(phoneList.size()>0){
				return phoneList.get(0);
			}
		}

		return null;
	}
	/**
	 * 
	 * 得到所有主号码的字号码
	 * 
	 * @param phone
	 * @return
	 */
	@Override
	public String getCstmPhoneId(String phone, String uuid) {
		
		if (StringUtils.isNotBlank(phone)) {
			
			List<String> phoneId = jdbcTemplate.queryForList(" SELECT id FROM cstm_phone WHERE main_number = ? AND uuid = ? ",
					String.class, phone, uuid);
			
			if(phoneId.size() > 0){
				return phoneId.get(0);
			}
		}
		
		return null;
	}

	/**
	 * 添加主号码和从号码
	 * 
	 * @param phone
	 * @return
	 */
	@Override
	public void save(CstmPhone cstmPhone) {

		super.save(cstmPhone);
	}

	/**
	 * 修改
	 * @param cstmPhone
	 */
	public void update(CstmPhone cstmPhone){
		
		super.update(cstmPhone);
	}
	
	
	/**
	 * 根据主号码查询
	 * @param mainPhone
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CstmPhone getCstmPhone(final String mainPhone){
		
		List<CstmPhone> clazz = queryBean(new BeanHandler() {
			 
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {

				sql.append(" select * from cstm_phone where main_number = ? ");
				params.add(mainPhone);
			}
		}, CstmPhone.class);
		
		
		if(clazz.size()>0){
			
			return clazz.get(0);
		}
		
		return null;
	}
	
    /**
	 * 删除号码前面所有的0
	 * 
	 * @param targetNumber
	 *            目标号码
	 * @return 返回的号码
	 */
	@Override
    public String deletZero(String targetNumber){
    	
    	if(StringUtils.isNotBlank(targetNumber) && targetNumber.startsWith("0")){
    		
    		return deletZero(targetNumber.substring(1));
    	}
    	return targetNumber;
    }
	
	
}
