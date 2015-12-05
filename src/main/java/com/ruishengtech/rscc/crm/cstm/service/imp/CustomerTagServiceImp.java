package com.ruishengtech.rscc.crm.cstm.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.cstm.model.Customer;
import com.ruishengtech.rscc.crm.cstm.model.CustomerTag;
import com.ruishengtech.rscc.crm.cstm.service.CustomerTagService;

/**
 * @author Frank 客户标签Service
 */
@Service
@Transactional
public class CustomerTagServiceImp extends BaseService implements CustomerTagService {

	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstm.service.imp.CustomerTagService#getCustomersByTags(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> getCustomersByTags(final String tag) {

		if (StringUtils.isNotBlank(tag)) {

			List<Customer> customers = queryBean(new BeanHandler() {

				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					QueryUtils.like(sql, params, tag,
							" SELECT * FROM cstm_customer_tag tag WHERE tag.tag_name like ? ");
				}
			}, Customer.class);

			if (customers.size() > 0) {
				return customers;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstm.service.imp.CustomerTagService#getCustomersByTags(java.util.List)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> getCustomersByTags(final List<String> tags) {

		if (tags.size() > 0) {
			final StringBuilder SqlBuilder = new StringBuilder();
			for (int i = 0; i < tags.size(); i++) {
				SqlBuilder.append(" AND tag.tag_name like ? ");
			}

			List<Customer> customers = queryBean(new BeanHandler() {

				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(
							" SELECT * FROM cstm_customer_tag tag WHERE 1=1 ")
							.append(SqlBuilder)
							.append(" GROUP BY tag.customer_uuid ");
				}
			}, Customer.class);

			if (customers.size() > 0) {
				return customers;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ruishengtech.rscc.crm.cstm.service.CustomerTagService#getCustomerTags(com.ruishengtech.rscc.crm.cstm.model.Customer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCustomerTags(final String uuid) {

		if(StringUtils.isNotBlank(uuid)){

			return jdbcTemplate.queryForList(" SELECT tag_name FROM cstm_customer_tag WHERE uuid in( SELECT tag_id FROM cstm_tag_link l WHERE l.customer_uuid = ? ) ", String.class,uuid);
		}
		return null;
	}
	
}
