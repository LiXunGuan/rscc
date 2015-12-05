package com.ruishengtech.rscc.crm.neworderinfo.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyBean;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.cstm.model.CustomerDesign;
import com.ruishengtech.rscc.crm.neworderinfo.model.NewOrderInfo;
import com.ruishengtech.rscc.crm.neworderinfo.service.NewOrderInfoService;
import com.ruishengtech.rscc.crm.neworderinfo.solution.NewOrderInfoSolution;
import com.ruishengtech.rscc.crm.product.model.OrderDetail;
import com.ruishengtech.rscc.crm.product.model.OrderInfo;
import com.ruishengtech.rscc.crm.product.model.Product;
import com.ruishengtech.rscc.crm.product.service.OrderDetailService;
import com.ruishengtech.rscc.crm.product.service.ProductService;
import com.ruishengtech.rscc.crm.record.model.Record;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Service
@Transactional
public class NewOrderInfoServiceImp extends DiyTableService implements NewOrderInfoService{

	@Autowired
	@Qualifier(value = "diyTableService")
	private DiyTableService diyTableService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
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
		
		DiyBean ret = getTableDescByName("new_order_info");
		
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
				
				sql.append("SELECT d.* FROM design_column d WHERE 1=1 AND tableName = 'new_order_info' AND is_default = 0 ORDER BY d.orders ASC");
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
		
		return queryPage(new NewOrderInfoSolution(), str,request);
	
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	@Override
	public JSONObject getJsonObject(Map str) {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject jsonObject2 = new JSONObject();
		DiyBean ret = getTableDescByName("new_order_info");
		
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
				
				sql.append("SELECT d.* FROM design_column d WHERE 1=1 AND tableName = 'new_order_info' ORDER BY d.orders ASC");
			}
			
		}, ColumnDesign.class);
		
		if(customerDesigns.size() > 0){
			for (int i = 0; i < customerDesigns.size(); i++) {
				if (CustomerDesign.ALLOWSHOW.equals(customerDesigns.get(i).getAllowShow()))
					map.put(customerDesigns.get(i).getColumnNameDB(), customerDesigns.get(i));
			}
		}
		
		return map;
	}

	@Override
	public NewOrderInfo getOrderByUUID(String uuid) {
		return super.getByUuid(NewOrderInfo.class, UUID.UUIDFromString(uuid));
	}

	@Override
	public Map<String, Object> getOrderInfoByUUId(String uuid) {
		return jdbcTemplate.queryForMap(" SELECT * FROM `new_order_info` WHERE uuid = ? ", uuid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public NewOrderInfo getOrderByOrderid(final String oid) {
		List<NewOrderInfo> rs = queryBean(new BeanHandler() {
            @Override
            public void doSql(StringBuilder sql, List<Object> params) {
                sql.append(" select * from new_order_info where order_id = ? ");
                params.add(oid);
            }
        }, NewOrderInfo.class);
        if (rs.size() > 0) {
            return rs.get(0);
        }
        return null;
	}

	@Override
	public void deleteOrderByUUid(String uuid) {
		super.deleteById(NewOrderInfo.class, UUID.UUIDFromString(uuid));
	}

	@Override
	public void saveOrUpdateInfo(NewOrderInfo orderInfo) {
		if(orderInfo == null)
			return;
		if(StringUtils.isBlank(orderInfo.getUid())){
			super.save(orderInfo);
		}else{
			orderInfo.setUuid(UUID.UUIDFromString(orderInfo.getUid()));
			super.update(orderInfo);
		}
	}

	@Override
	public void saveOrUpdateNewOrderInfo(HttpServletRequest request, User user, NewOrderInfo order, String[] pName, String[] spinner) throws Exception {
		
		// 所有的参数和参数的值 
		Map<String, String[]> map = request.getParameterMap();
		
		// 得到订单的UUID
		String uuid = request.getParameter("uid");
		
		// 订单的编号
		String order_id = request.getParameter("order_id");
		
		diyTableService.releaseLock(map);
		
		if(StringUtils.isNotBlank(uuid)) { 
			
			diyTableService.update(map, NewOrderInfo.class);
			
		}else{ 
			
			map.put("order_user_uuid", new String[]{user.getUid().toString()});
			
			map.put("order_status", new String[]{OrderInfo.OrderStatus_WaitConfirm});
			
			map.put("order_create_time", new String[]{fmt.format(new Date())});
			
			diyTableService.save(map, NewOrderInfo.class);
		}
		
		// 更新商品信息
		if(pName != null && spinner != null){
			
			if(StringUtils.isNotBlank(uuid)) {
				
				updateProductCount(order_id, pName, spinner);
			
			}else{
				
				addProductCount(pName, spinner, order_id);
			
			}
		}
	}

	private void addProductCount(String[] pName, String[] spinner,
			String order_id) {
		if(pName != null && spinner != null){
			for (int i = 0; i < spinner.length; i++) {
				String pid = pName[i]; // 商品编号
				Integer pnumber = Integer.parseInt(spinner[i]); // 商品数量
				// 通过订单编号和商品编号查询商品详情是否已存在
				OrderDetail ode = orderDetailService.getOrderDetailByOidAndPid(order_id, pid);
				if(ode == null){
					// 存储新的订单详情信息
					OrderDetail od = new OrderDetail();
					od.setProductId(pid);
					od.setProductNumber(pnumber);
					od.setOrderid(order_id);
					orderDetailService.saveOrUpdate(od);
				}else{ // 用户页面选择了多条相同的商品信息
					ode.setProductNumber(pnumber + ode.getProductNumber());
					orderDetailService.saveOrUpdate(ode);
				}
			}
			
			// 去除重复的商品编号
			List<String> list = new LinkedList<String>();
			for(String str : pName){
				if(!list.contains(str)) {
		            list.add(str);
		        }
			}
			
			// 更新商品数量
			for (int i = 0; i < list.size(); i++) {
				String pid = list.get(i); // 商品编号
				OrderDetail ode = orderDetailService.getOrderDetailByOidAndPid(order_id, pid);
				if(ode != null){
					// 更新商品数量
					Product pd = productService.getProByPid(pid);
					pd.setProductNumber(pd.getProductNumber() - ode.getProductNumber());
					productService.saveOrUpdateInfo(pd);
				}
			}
		}
	}

	private void updateProductCount(String order_id, String[] pName, String[] spinner) {
		///删除订单原有的订单商品详情之前还原商品数量
		List<OrderDetail> ods = orderDetailService.getOrderDetailsByOrderId(order_id);
		if(ods != null){
			for (OrderDetail od : ods) {
				//更新商品数量
				Product pd = productService.getProByPid(od.getProductId());
				pd.setProductNumber(pd.getProductNumber() + od.getProductNumber());
				productService.saveOrUpdateInfo(pd);
			}
		}
		
		//删除订单原有的订单商品详情
		List<OrderDetail> details = orderDetailService.getOrderDetailsByOrderId(order_id);
		if(details != null){
			for (OrderDetail od : details) {
				orderDetailService.deleteOrderDetailsByOrderId(od.getOrderid());
			}
		}
		List<OrderDetail> oldinfo = new ArrayList<OrderDetail>();
		if(pName != null && spinner != null){
			List<String> listPid = new LinkedList<String>();
			for (String str : pName) {
				listPid.add(str);
			}
			List<String> listPnum = new LinkedList<String>();
			for (String str : spinner) {
				listPnum.add(str);
			}
			for (int i = 0,j = 0; i < listPid.size(); i++,j++) {
				if(listPid.size() == 0){
					break;
				}
				String pid = pName[j]; // 商品编号
				Integer pnumber = Integer.parseInt(spinner[j]); // 商品数量
				Product pd = productService.getProByPid(pid); // 得到商品信息
				if(details != null){
					for (OrderDetail od : details) {
						if(od.getProductId().equals(pid)){// 该商品还存在该订单
							OrderDetail odee = null;
							OrderDetail ode = orderDetailService.getOrderDetailByOidAndPid(od.getOrderid(), od.getProductId());
							if(ode != null){
								odee = ode;
								odee.setProductNumber(pnumber + odee.getProductNumber());
							}else{
								odee = new OrderDetail();
								odee.setProductNumber(pnumber);
							}
							odee.setProductId(od.getProductId());
							odee.setOrderid(od.getOrderid());
							orderDetailService.saveOrUpdate(odee);
							// 更新商品数量
//							pd.setProductNumber(pd.getProductNumber() + (od.getProductNumber() - odee.getProductNumber()));
							pd.setProductNumber(pd.getProductNumber() - pnumber);
							productService.saveOrUpdateInfo(pd);
							if(listPid.get(i) != null && listPnum.get(i) != null){
								listPid.remove(i);
								listPnum.remove(i);
								i--;
							}
						}else{
							oldinfo.add(od);
						}
					}
				}
			}
			if(listPid.size() > 0 && listPnum.size() > 0){
				for (int i = 0; i < listPid.size(); i++){
					OrderDetail odd = new OrderDetail();
					odd.setProductId(listPid.get(i));
					odd.setProductNumber(Integer.parseInt(listPnum.get(i)));
					odd.setOrderid(order_id);
					orderDetailService.saveOrUpdate(odd);
					Product pd = productService.getProByPid(listPid.get(i)); // 得到商品信息
					// 更新商品数量
					pd.setProductNumber(pd.getProductNumber() - odd.getProductNumber());
					productService.saveOrUpdateInfo(pd);
				}
			}
			if(oldinfo != null){
				for (OrderDetail od : oldinfo) {
					//更新商品数量
					Product pd = productService.getProByPid(od.getProductId());
					pd.setProductNumber(pd.getProductNumber() + od.getProductNumber());
					productService.saveOrUpdateInfo(pd);
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> getMapObject(Map str) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		newMap = str;
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject jsonObject2 = new JSONObject();
		DiyBean ret = diyTableService.getTableDescByName("new_order_info");
		
		for (ColumnDesign cd : ret.getClomns().values()) {
			//自定义表
			if (CustomerDesign.ALLOWSHOW.equals(cd.getAllowShow())) {// 如果是要显示的
				
				if (cd.getColumnType().equals(ColumnDesign.DATETYPE)) {// 如果是时间类型 格式化时间
					try {
						str.put(cd.getColumnNameDB(),null == str.get(cd.getColumnNameDB()) ? "" : fmt.format(str.get(cd.getColumnNameDB())));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					str.put(cd.getColumnNameDB(), null == str.get(cd.getColumnNameDB()) ? "": str.get(cd.getColumnNameDB()));
				}
			} 
		}
		
		str.put("order_status", NewOrderInfo.OrderStatus.get(str.get("order_status")));
		
		str.put("order_user_uuid", userService.getByUuid(UUID.UUIDFromString(str.get("order_user_uuid").toString())).getLoginName());
		
		// 是否接通
        str.put("put_through", str.get("bridgesec") == null ? "无" : Record.PUT_THROUGH.get(Long.parseLong(str.get("bridgesec").toString())>0?"y":"n"));
        
        // 接通时长
        str.put("bridgesec", str.get("bridgesec") == null ? "无" : Long.parseLong(str.get("bridgesec").toString())%1000!=0?Long.parseLong(str.get("bridgesec").toString())/1000+1:Long.parseLong(str.get("bridgesec").toString())/1000);
		
        // 商品信息
        List<OrderDetail> ods = orderDetailService.getOrderDetailsByOrderId(str.get("order_id").toString());
        String pros = "";
        if(ods != null){
			for (OrderDetail od : ods) {
				Product pro = productService.getProByPid(od.getProductId());
				od.setProductName(pro.getProductName());
				pros += pro.getProductName() + ":" + od.getProductNumber() + ";"; 
			}
		}
        str.put("products", pros);
        
		return str;
	}
}
