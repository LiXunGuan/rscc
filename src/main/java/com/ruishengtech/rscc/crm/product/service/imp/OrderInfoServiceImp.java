package com.ruishengtech.rscc.crm.product.service.imp;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyBean;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.product.model.OrderInfo;
import com.ruishengtech.rscc.crm.product.service.OrderInfoService;
import com.ruishengtech.rscc.crm.product.solution.OrderInfoSolution;

@Service
@Transactional
public class OrderInfoServiceImp extends DiyTableService implements OrderInfoService{

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public void getTitles(Model model) {
		
		JSONObject jsonObject = getTitleAndData();
		
		StringBuilder builder = new StringBuilder();
		Iterator it = jsonObject.keys();
		TreeMap<Integer, ColumnDesign> e = new TreeMap<Integer, ColumnDesign>();
		
		while (it.hasNext()) {
			String key = (String) it.next();
			ColumnDesign value = (ColumnDesign) jsonObject.get(key);
			e.put(value.getOrders(), value);
		}
		
		Map<String, String> data = new LinkedHashMap<String, String>();
		
		Iterator titer=e.entrySet().iterator();  
		while(titer.hasNext()){
            Map.Entry ent=(Map.Entry )titer.next();  
            String keyt=ent.getKey().toString();  
            ColumnDesign valuet=(ColumnDesign) ent.getValue();  
            data.put(valuet.getColumnNameDB(),valuet.getColumnName());
			model.addAttribute("title", builder);
        }
		
		model.addAttribute("dataRows", data);
	}

	@SuppressWarnings("static-access")
	@Override
	public JSONObject getTitleAndData() {
		
		JSONObject jsonObject = new JSONObject();
		
		DiyBean ret = getTableDescByName("order_info");
		
		for (com.ruishengtech.framework.core.db.diy.ColumnDesign customerDesign : ret.getClomns().values()) {
			
			if(customerDesign.getAllowShow().equals(customerDesign.ALLOWSHOW)){ //查询允许显示的
				
				jsonObject.put(customerDesign.getColumnNameDB(),customerDesign);
			}
		}

		return jsonObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ColumnDesign> getNotDefaultColumn() {
		Map<String, ColumnDesign> map = new LinkedHashMap<String, ColumnDesign>();
		
		List<ColumnDesign> customerDesigns = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				
				sql.append("SELECT d.* FROM design_column d WHERE 1=1 AND tableName = 'order_info' AND is_default = 0 ORDER BY d.orders ASC");
			}
			
		}, ColumnDesign.class);
		
		if(customerDesigns.size() > 0){
			for (int i = 0; i < customerDesigns.size(); i++) {
				if(customerDesigns.get(i).getAllowShow().equals(ColumnDesign.ALLOWSHOW)){
					map.put(customerDesigns.get(i).getColumnNameDB(), customerDesigns.get(i));
				}
			}
		}
		
		return map;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageResult<Map> queryPage(Map<String, ColumnDesign> str,
			HttpServletRequest request) {
		
		return queryPage(new OrderInfoSolution(), str,request);
	
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	@Override
	public JSONObject getJsonObject(Map str) {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject jsonObject2 = new JSONObject();
		DiyBean ret = getTableDescByName("order_info");
		
		for (com.ruishengtech.framework.core.db.diy.ColumnDesign customerDesign : ret.getClomns().values()) {

			if(customerDesign.ALLOWSHOW.equals(customerDesign.getAllowShow())){//如果是要显示的
				
				if(customerDesign.getColumnType().equals(ColumnDesign.DATETYPE)){//如果是时间类型 格式化时间
					try {
						jsonObject2.put(customerDesign.getColumnNameDB(),null == str.get(customerDesign.getColumnNameDB())?"":fmt.format(str.get(customerDesign.getColumnNameDB())));
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}else{
					jsonObject2.put(customerDesign.getColumnNameDB(),null == str.get(customerDesign.getColumnNameDB())?"":str.get(customerDesign.getColumnNameDB()));
				}
			}
		}
		jsonObject2.put("uuid",str.get("uuid"));
        
        return jsonObject2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ColumnDesign> getAllColumns() {
		Map<String, ColumnDesign> map = new LinkedHashMap<String, ColumnDesign>();
		
		List<ColumnDesign> customerDesigns = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				
				sql.append("SELECT d.* FROM design_column d WHERE 1=1 AND tableName = 'order_info' ORDER BY d.orders ASC");
			}
			
		}, ColumnDesign.class);
		
		if(customerDesigns.size() > 0){
			for (int i = 0; i < customerDesigns.size(); i++) {
				map.put(customerDesigns.get(i).getColumnNameDB(), customerDesigns.get(i));
			}
		}
		
		return map;
	}

	@Override
	public OrderInfo getOrderByUUID(String uuid) {
		return super.getByUuid(OrderInfo.class, UUID.UUIDFromString(uuid));
	}

	@Override
	public Map<String, Object> getOrderInfoByUUId(String uuid) {
		return jdbcTemplate.queryForMap(" SELECT * FROM `order_info` WHERE uuid = ? ", uuid);
	}

	@Override
	public OrderInfo getOrderByOrderid(String oid) {
		return super.getByUuid(OrderInfo.class, UUID.UUIDFromString(oid));
	}

	@Override
	public void deleteOrderByUUid(String uuid) {
		super.deleteById(OrderInfo.class, UUID.UUIDFromString(uuid));
	}

	@Override
	public void saveOrUpdateInfo(OrderInfo orderInfo) {
		if(orderInfo == null)
			return;
		if(StringUtils.isBlank(orderInfo.getUid())){
			super.save(orderInfo);
		}else{
			orderInfo.setUuid(UUID.UUIDFromString(orderInfo.getUid()));
			super.update(orderInfo);
		}
	}
}
