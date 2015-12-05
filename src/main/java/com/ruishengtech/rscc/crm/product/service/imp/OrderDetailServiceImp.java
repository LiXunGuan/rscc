package com.ruishengtech.rscc.crm.product.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.product.model.OrderDetail;
import com.ruishengtech.rscc.crm.product.service.OrderDetailService;

@Service
@Transactional
public class OrderDetailServiceImp extends BaseService implements OrderDetailService{

	@Override
	public void saveOrUpdate(OrderDetail orderDetail) {
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
	public List<OrderDetail> getOrderDetailsByOrderId(final String orderid) {
		
		List<OrderDetail> od = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append("SELECT * FROM order_detail WHERE order_id = ?");
				params.add(orderid);
			}
			
		}, OrderDetail.class);
		
		if(od.size() > 0){
			return od;
		}
		return null;
	}

	@Override
	public void deleteOrderDetailsByOrderId(String orderId) {
		jdbcTemplate.update(" DELETE FROM order_detail WHERE order_id = ? ", orderId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderDetail getOrderDetailByOidAndPid(final String oId, final String pId) {
		List<OrderDetail> od = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append("SELECT * FROM order_detail WHERE order_id = ? and product_id = ? ");
				params.add(oId);
				params.add(pId);
			}
		}, OrderDetail.class);
		if(od.size() > 0){
			return od.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDetail> getOrderDetailsByPid(final String Pid) {
		List<OrderDetail> od = queryBean(new BeanHandler() {
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT * FROM order_detail WHERE product_id = ? ");
				params.add(Pid);
			}
			
		}, OrderDetail.class);
		
		if(od.size() > 0){
			return od;
		}
		return null;
	}

}
