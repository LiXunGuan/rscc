package com.ruishengtech.rscc.crm.cstm.service.imp;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.cstm.model.CustomerTag;
import com.ruishengtech.rscc.crm.cstm.service.CustomerTagLinkService;

/**
 * @author Frank
 *
 */
@Service
@Transactional
public class CustomerTagLinkServiceImp extends BaseService implements CustomerTagLinkService {
	
	
	public static Map<String, String> allCustomerTags = new LinkedHashMap<String, String>();
	
	@SuppressWarnings({ "unchecked" })
	public  Map<String, String> getCustomerTag(){
		
		List<CustomerTag> customerTags = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT * FROM cstm_customer_tag WHERE 1 = 1 ");
			}
		}, CustomerTag.class);
		
		if(customerTags.size() > 0){
			for (int i = 0; i < customerTags.size(); i	++) {
				allCustomerTags.put(customerTags.get(i).getTagName(), customerTags.get(i).getUid());
			}
			
			return allCustomerTags;
		}
		
		return null;
	}
	/* (non-Javadoc)	
	 * @see com.ruishengtech.rscc.crm.cstm.service.imp.CustomerTagLinkService#saveCustomerTagLink(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean saveCustomerTagLink(String tagId,String customerId){
		
		Integer affectedRow =  jdbcTemplate.update(" INSERT INTO `cstm_tag_link` (`tag_id`, `customer_uuid`) VALUES (?, ?)",tagId,customerId);
		if(affectedRow > 0){
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstm.service.imp.CustomerTagLinkService#bindTagsToCustomer(java.lang.String, java.util.List)
	 */
	@Override
	public void bindTagsToCustomer(String cstmId, List<String> tags) {

		//去除客户标签
		unBindTagsFromCustomer(cstmId);
		
		//添加客户标签
		bindTagsForCustomer(cstmId, tags);
	}

	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstm.service.imp.CustomerTagLinkService#unBindTagsFromCustomer(java.lang.String)
	 */
	@Override
	public void unBindTagsFromCustomer(String cstmId) {
		jdbcTemplate.update(" DELETE FROM cstm_tag_link WHERE customer_uuid  = ? ", cstmId);
	}
	
	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstm.service.imp.CustomerTagLinkService#bindTagsForCustomer(java.lang.String, java.util.List)
	 */
	@Override
	public void bindTagsForCustomer(String cstmId,List<String> tags) {
		
		Map<String, String> allCustomerTags = getCustomerTag();
		if (null != cstmId && tags.size() > 0) {

			for (String str : tags) {
				if( null != allCustomerTags && allCustomerTags.containsKey(str.trim())){
					jdbcTemplate.update(" INSERT INTO `cstm_tag_link` (`tag_id`, `customer_uuid`) VALUES (?, ?) ",getCustomerTag().get(str.trim()),cstmId);
				}else{
					CustomerTag o = new CustomerTag();
					o.setTagName(str.trim());
					super.save(o);
					jdbcTemplate.update(" INSERT INTO `cstm_tag_link` (`tag_id`, `customer_uuid`) VALUES (?, ?);",o.getUid(),cstmId);
				}
			}
		}
	}
}
