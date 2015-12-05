package com.ruishengtech.rscc.crm.neworderinfo.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.neworderinfo.model.NewOrderDetail;
import com.ruishengtech.rscc.crm.neworderinfo.service.NewOrderDetailService;

@Service
@Transactional
public class NewOrderDetailServiceImp extends BaseService implements NewOrderDetailService{

	@Override
	public void saveOrUpdate(NewOrderDetail orderDetail) {
		if(orderDetail == null)
			return;
		if(StringUtils.isBlank(orderDetail.getUid())){
			super.save(orderDetail);
		}else{
			orderDetail.setUuid(UUID.UUIDFromString(orderDetail.getUid()));
			super.update(orderDetail);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewOrderDetail> getOrderDetailsByOrderId(final String orderid) {
		
		List<NewOrderDetail> od = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append("SELECT * FROM new_order_detail WHERE order_id = ?");
				params.add(orderid);
			}
			
		}, NewOrderDetail.class);
		
		if(od.size() > 0){
			return od;
		}
		return null;
	}

	@Override
	public void deleteOrderDetailsByOrderId(String orderId) {
		jdbcTemplate.update(" DELETE FROM new_order_detail WHERE order_id = ? ", orderId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public NewOrderDetail getOrderDetailByOidAndPid(final String oId, final String pId) {
		List<NewOrderDetail> od = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append("SELECT * FROM new_order_detail WHERE order_id = ? and product_id = ? ");
				params.add(oId);
				params.add(pId);
			}
		}, NewOrderDetail.class);
		if(od.size() > 0){
			return od.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewOrderDetail> getOrderDetailsByPid(final String Pid) {
		List<NewOrderDetail> od = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT * FROM new_order_detail WHERE product_id = ? ");
				params.add(Pid);
			}
			
		}, NewOrderDetail.class);
		
		if(od.size() > 0){
			return od;
		}
		return null;
	}

}
