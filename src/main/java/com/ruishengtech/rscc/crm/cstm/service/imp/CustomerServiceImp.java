package com.ruishengtech.rscc.crm.cstm.service.imp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyBean;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.rscc.crm.cstm.model.CstmLog;
import com.ruishengtech.rscc.crm.cstm.model.CstmPhone;
import com.ruishengtech.rscc.crm.cstm.model.CstmReport;
import com.ruishengtech.rscc.crm.cstm.model.Customer;
import com.ruishengtech.rscc.crm.cstm.model.CustomerDesign;
import com.ruishengtech.rscc.crm.cstm.model.CustomerPool;
import com.ruishengtech.rscc.crm.cstm.service.CstmPhoneService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerTagLinkService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerTagService;
import com.ruishengtech.rscc.crm.cstm.solution.CustomerSolution;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchDataService;
import com.ruishengtech.rscc.crm.datamanager.service.PhoneResourceService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.cstm.service.CustomerSerialize;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Frank
 *
 */
@Service
@Transactional
@SuppressWarnings("unchecked")
public class CustomerServiceImp extends DiyTableService implements
		CustomerService {

	@Autowired
	private DiyTableService diyTableService;

	@Autowired
	private CustomerPoolService customerPoolService;
	
	@Autowired
	private CstmPhoneService phoneService;

	@Autowired
	private PhoneResourceService phoneResourceService;

	@Autowired
	private DataBatchDataService batchDataService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerSerialize customerSerialize;
	
	@Autowired
	private CustomerTagService customerTagService;
	
	@Autowired
	private CustomerTagLinkService customerTagLinkService;

	/**
	 * 根据编号去到客户信息
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public Customer getCustomerByName(final String csmtName) {

		if (StringUtils.isNotBlank(csmtName)) {

			List<Customer> customers = queryBean(new BeanHandler() {

				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM cstm_customer csmt WHERE csmt.name like ?  ");
					params.add(csmtName);
				}
			}, Customer.class);

			if (customers.size() > 0) {
				return customers.get(0);
			}
		}
		return null;
	}

	/**
	 * 根据客户池编号查询客户
	 * @param poolId
	 * 客户池编号
	 * @return
	 */
	@Override
	public List<Customer> getCustomersByPoolId(final String poolId) {

		if (StringUtils.isNotBlank(poolId)) {

			List<Customer> customers = queryBean(new BeanHandler() {

				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM cstm_customer csmt WHERE csmt.pool_id in (?) ");
					params.add(poolId);
				}
			}, Customer.class);

			if (customers.size() > 0) {
				return customers;
			}
		}

		return null;
	}

	/**
	 * 根据客户池编号查询客户
	 * 
	 * @param str
	 *            [] 客户池编号数组
	 * @return
	 */
	@Override
	public List<Customer> getCustomersByPoolId(final String[] poolId) {

		if (null != poolId && poolId.length > 0) {

			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < poolId.length; i++) {
				builder.append(poolId[i] + ",");
			}

			final String builders = builder.substring(0, builder.length() - 1);
			List<Customer> customers = queryBean(new BeanHandler() {

				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append("  SELECT * FROM cstm_customer csmt WHERE csmt.pool_id in (?)  ");
					params.add(builders);
				}
			}, Customer.class);

			if (customers.size() > 0) {

//				System.out.println(customers.size());
				return customers;
			}
		}

		return null;
	}

	/**
	 * 根据客户池编号查询客户
	 * 
	 * @param poolId
	 *            客户池编号list
	 * @return
	 */
	@Override
	public List<Customer> getCustomersByPoolId(final List<String> poolId) {

		if (null != poolId && poolId.size() > 0) {

			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < poolId.size(); i++) {
				builder.append(poolId.get(i) + ",");
			}

			final String builders = builder.substring(0, builder.length() - 1);
			List<Customer> customers = queryBean(new BeanHandler() {

				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append("  SELECT * FROM cstm_customer csmt WHERE csmt.pool_id in (?) ");
					params.add(builders);

				}
			}, Customer.class);

			if (customers.size() > 0) {

				return customers;
			}
		}

		return null;
	}

	/**
	 * 查符合条件的所有客户
	 * 
	 * @param condition
	 * @return
	 */
	@Override
	public PageResult<Map> queryPage(Map<String, ColumnDesign> str,
			HttpServletRequest request) {

		return queryPage(new CustomerSolution(), str, request);
	}

	/**
	 * 根据UUID删除客户对象
	 * 
	 * @param uuid
	 */
	@Override
	public void deleteCustomerByUUid(UUID uuid,Customer customer) {

		emptySourceOwner(customer);
		
		super.deleteById(Customer.class, uuid);
	}

	
	/**
	 * 清空资源表中所属客户信息
	 * 
	 * 清空对应批次表中数据信息
	 * @param customer
	 * @return
	 */
	private String emptySourceOwner(Customer customer) {
		
		//查询如果存在第二号码
		String secondNumber = null;
		
		try {
			
			secondNumber = jdbcTemplate.queryForObject(" SELECT minor_number FROM `cstm_phone` WHERE main_number = ? ;" , String.class,customer.getPhoneNumber());
			
		} catch (Exception e) {}
		
		if(StringUtils.isNotBlank(secondNumber)){
			// 第二号码的批次编号
			String batchUuid = null ;
			try {
				batchUuid = jdbcTemplate.queryForObject(" SELECT batch_uuid FROM `new_data_phone_resource` WHERE phone_number =  ? ;", String.class,secondNumber);
			} catch (Exception e) {

			}
			if(StringUtils.isNotBlank(batchUuid)){
				backBatchData(batchUuid);
			}
			updatePhoneSource(customer);
		}
		
		//主号码存在的批次编号
		
		String batch_uuid = null;
		try {
			
			batch_uuid = jdbcTemplate.queryForObject(" SELECT batch_uuid FROM `new_data_phone_resource` WHERE phone_number =  ? ;", String.class,customer.getPhoneNumber());
			
		} catch (Exception e) {}
		
		if(StringUtils.isNotBlank(batch_uuid)){
			backBatchData(batch_uuid);
		}
		
		updatePhoneSource(customer);
		
		return null;
	}

	/**
	 * 根据号码更新资源信息表中的数据
	 * @param customer
	 */
	private Integer updatePhoneSource(Customer customer) {
		
		return jdbcTemplate.update(" UPDATE new_data_phone_resource SET customer_uuid = NULL WHERE phone_number = ? ;",customer.getPhoneNumber());
	}

	/**
	 * 根据批次编号还原对应号码的批次信息
	 * @param batchUuid
	 */
	private Integer backBatchData(String batchUuid) {
		//还原批次表的数据
		return jdbcTemplate.update("UPDATE `new_data_batch_"+batchUuid+"`\n" +
				"SET `own_department` = NULL,\n" +
				" `own_department_timestamp` = NULL,\n" +
				" `own_user` = NULL,\n" +
				" `own_user_timestamp` = NULL,\n" +
				" `call_count` = '0',\n" +
				" `last_call_department` = NULL,\n" +
				" `last_call_user` = NULL,\n" +
				" `last_call_result` = NULL,\n" +
				" `last_call_time` = NULL,\n" +
				" `intent_type` = NULL,\n" +
				" `intent_timestamp` = NULL,\n" +
				" `is_lock` = '0',\n" +
				" `lock_timestamp` = NULL,\n" +
				" `is_abandon` = '0',\n" +
				" `abandon_timestamp` = NULL,\n" +
				" `is_blacklist` = '0',\n" +
				" `blacklist_timestamp` = NULL,\n" +
				" `is_frozen` = '0',\n" +
				" `frozen_timestamp` = NULL,\n" +
				" `customer_uuid` = NULL\n" +
				"WHERE\n" +
				"	(\n" +
				"		`batch_uuid` = ? \n" +
				"	);",batchUuid);
	}
	
	/**
	 * 保存或者是修改一个客户
	 * 
	 * @param customer
	 */
	@Override
	public void saveOrUpdate(Customer customer) {

		if (null == customer)
			return;
		if (StringUtils.isNotBlank(customer.getUid())) {
			UUID uuid = UUID.UUIDFromString(customer.getUid());
			customer.setUuid(uuid);
			super.update(customer);
		} else {
			super.save(customer);
		}
	}

	/**
	 * 保存或者是修改一个客户
	 * 
	 * @param customer
	 */
	@Override
	public void saveOrUpdate(Customer customer, String[] str) {
		if (null == customer || StringUtils.isBlank(String.valueOf(str)))
			return;
		if (StringUtils.isBlank(customer.getUuid().getUuid())) {

			super.update(customer, str);

		} else {
			super.save(customer, str);
		}
	}

	/**
	 * 根据编号去到客户信息
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public Map<String, Object> getCustomerById(UUID uuid) {

		return jdbcTemplate.queryForMap(
				" SELECT * FROM `cstm_customer` WHERE uuid = ? ",
				uuid.toString());
	}

	/**
	 * 查询客户表的所有字段
	 * 
	 * @return
	 */
	@Override
	public List<String> getCustomerColumns() {

		String sql = "SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE table_name = 'cstm_customer' AND table_schema = 'crm'";

		List<String> strs = jdbcTemplate.queryForList(sql, String.class);

		return strs;
	}

	/**
	 * 得到表头和对应数据字段
	 * 
	 * @return
	 */
	@Override
	public JSONObject getTitleAndData(DiyTableService diyTableManager) {

		JSONObject jsonObject = new JSONObject();

		DiyBean ret = diyTableManager.getTableDescByName("cstm_customer");

		for (ColumnDesign customerDesign : ret
				.getClomns().values()) {

			if (customerDesign.getAllowShow().equals(CustomerDesign.ALLOWSHOW)) { // 查询允许显示的
				jsonObject
						.put(customerDesign.getColumnNameDB(), customerDesign);
			}
		}

		return jsonObject;
	}

	/**
	 * 取值
	 * 
	 * @param str
	 *            数据库查处的map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public JSONObject getJsonObject(Map str) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject jsonObject2 = new JSONObject();
		DiyBean ret = diyTableService.getTableDescByName("cstm_customer");

		for (ColumnDesign customerDesign : ret.getClomns().values()) {

			if (CustomerDesign.ALLOWSHOW.equals(customerDesign.getAllowShow())) {// 如果是要显示的

				if (customerDesign.getColumnType().equals(ColumnDesign.DATETYPE)) {// 如果是时间类型 格式化时间
					try {
						jsonObject2.put(customerDesign.getColumnNameDB(),null == str.get(customerDesign.getColumnNameDB()) ? "" : fmt.format(str.get(customerDesign.getColumnNameDB())));
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					jsonObject2.put(customerDesign.getColumnNameDB(), null == str.get(customerDesign.getColumnNameDB()) ? "": str.get(customerDesign.getColumnNameDB()));
				}
			}
		}
		
		if(Customer.CUSTOMER_DEAL.equals(str.get("status"))){
			
			jsonObject2.put("status",Customer.CUSTOMER_TYPE.get(Customer.CUSTOMER_DEAL));
		}else if(Customer.CUSTOMER_INTENT.equals(str.get("status"))){
			
			jsonObject2.put("status",Customer.CUSTOMER_TYPE.get(Customer.CUSTOMER_INTENT));
		}else{
			
			jsonObject2.put("status","无");
		}
		
		jsonObject2.put("pool_id",customerPoolService.attendCustomerTypeByUUID(str.get("pool_id")));
		jsonObject2.put("uuid", str.get("uuid"));
		jsonObject2.put("customerTags", str.get("customerTags"));
		StringBuffer aa = new StringBuffer();
		aa.append(str.get("phone_number"));
		aa.append(null==str.get("minorNUmber") ?"":(","+str.get("minorNUmber")));
		jsonObject2.put("phone_number", aa.toString());

		return jsonObject2;
	}
	@Override
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getMapObject(Map str) {
		
		Map<String, Object> newMap = new HashMap<String, Object>();
		newMap = str;
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject jsonObject2 = new JSONObject();
		DiyBean ret = diyTableService.getTableDescByName("cstm_customer");
		
		for (ColumnDesign customerDesign : ret.getClomns().values()) {
			
			if (CustomerDesign.ALLOWSHOW.equals(customerDesign.getAllowShow())) {// 如果是要显示的
				
				if (customerDesign.getColumnType().equals(ColumnDesign.DATETYPE)) {// 如果是时间类型 格式化时间
					try {
						str.put(customerDesign.getColumnNameDB(),null == str.get(customerDesign.getColumnNameDB()) ? "" : fmt.format(str.get(customerDesign.getColumnNameDB())));
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					str.put(customerDesign.getColumnNameDB(), null == str.get(customerDesign.getColumnNameDB()) ? "": str.get(customerDesign.getColumnNameDB()));
				}
			}
		}
		
		if(Customer.CUSTOMER_DEAL.equals(str.get("status"))){
			
			str.put("status",Customer.CUSTOMER_TYPE.get(Customer.CUSTOMER_DEAL));
		}else if(Customer.CUSTOMER_INTENT.equals(str.get("status"))){
			
			str.put("status",Customer.CUSTOMER_TYPE.get(Customer.CUSTOMER_INTENT));
		}else{
			
			str.put("status","无");
		}
		
		if (null == str.get("own_id")|| ("-1").equals(String.valueOf(str.get("own_id")))) {
			str.put("own_id", "暂无");
		} else {
			
			str.put("own_id", userService.getByUuid(UUID.UUIDFromString(String.valueOf(str.get("own_id")))).getUserDescribe());
		}
		
		str.put("pool_id",customerPoolService.attendCustomerTypeByUUID(str.get("pool_id")));
		str.put("uuid", str.get("uuid"));
		str.put("customerTags", str.get("customerTags"));
		StringBuffer aa = new StringBuffer();
		aa.append(str.get("phone_number"));
		str.put("phone_number", aa.toString());
		
		return str;
	}

	/**
	 * 取到所有title
	 * 
	 * @param model
	 */
	@Override
	public void getTitles(Model model) {

		JSONObject jsonObject = getTitleAndData(diyTableService);

		StringBuilder builder = new StringBuilder();
		Iterator it = jsonObject.keys();
		TreeMap<Integer, ColumnDesign> e = new TreeMap<Integer, ColumnDesign>();

		while (it.hasNext()) {

			String key = (String) it.next();
			ColumnDesign value = (ColumnDesign) jsonObject.get(key);
			e.put(value.getOrders(), value);
		}

		Map<String, String> data = new LinkedHashMap<String, String>();

		Iterator titer = e.entrySet().iterator();
		while (titer.hasNext()) {
			Map.Entry ent = (Map.Entry) titer.next();
			String keyt = ent.getKey().toString();
			ColumnDesign valuet = (ColumnDesign) ent.getValue();
			data.put(valuet.getColumnNameDB(), valuet.getColumnName());
			model.addAttribute("title", builder);
		}

		model.addAttribute("dataRows", data);

	}

	/**
	 * 获取所有titles
	 */
	public List<String> getTitles() {

		List<String> ti = jdbcTemplate
				.queryForList(
						" SELECT column_name_db FROM `design_column` WHERE tableName = 'cstm_customer' ORDER BY orders ",
						String.class);
		return ti;
	}

	/**
	 * 校验顺序重复
	 * 
	 * @param request
	 * @param response
	 * @param columnName
	 * @throws java.io.IOException
	 */
	public void checkCstmIdRepeat(HttpServletRequest request,
			HttpServletResponse response, String cstm_id) throws IOException {

		Customer cstm = getCstmserviceByColumn(cstm_id);

		String uid = request.getParameter("uid");

		// 添加的时候没有该用户信息
		if (null == cstm) {
			// 直接打印true
			response.getWriter().print(true);
		} else {

			if ("".equals(uid)) {

				response.getWriter().print(false);
			} else {
				Customer customer = getByUuid(Customer.class,
						UUID.UUIDFromString(uid));

				if (customer != null
						&& uid.equals(String.valueOf(cstm.getUid()))) {
					response.getWriter().print(true);
				} else {

					response.getWriter().print(false);
				}
			}
		}
	}

	
	
	/**
	 * 检查号码是否重复【客户信息表】
	 * 
	 * @param request
	 * @param response
	 * @param phoneNumber
	 * @throws IOException
	 */
	public void checkPhoneRepeat(HttpServletRequest request,
			HttpServletResponse response, String phoneNumber)
			throws IOException {

		//客户表信息判断
		boolean bool = false ;
		
		Customer customer = getCustomerByPhoneOrNumber(phoneNumber);
		if (StringUtils.isNotBlank(phoneNumber)) {
			
			String uid = request.getParameter("uid");
			// 根据号码查询客户信息
			if (null == customer) {

				bool = true;
			} else {

				if ("".equals(uid)) {

					response.getWriter().print(false);
					bool = false;
				} else {
					Customer s = getByUuid(Customer.class, UUID.UUIDFromString(uid));

					if (s != null && uid.equals(String.valueOf(customer.getUid()))) {
//						response.getWriter().print(true);
						bool = true;
					} else {

						response.getWriter().print(false);
						bool = false;
					}
				}
			}

		} else {
			
//			response.getWriter().print(true);
			bool = true;
		}
		
		//如果通过了校验，
		if(bool){
			
			getPhoneValidate(request, response, phoneNumber);
		}
		
		
	}
	
		
	/**
	 * 校验电话号码
	 * 1、检查该资源号码是否是其他坐席的意向客户------不能添加；
	 * 2、检查该资源号码是否存在批次中，尚未分配------直接归为该坐席；
	 * 3、检查该资源号码是否存在部门中，尚未分配------直接归为该坐席；
	 * 4、检查该资源号码是否已经分配给某坐席，但不是意向客户------直接归为该坐席；
	 * 5、检查该资源号码是否在系统中不存在------直接归为该坐席；
	 * @throws IOException 
	 */
	public void getPhoneValidate(HttpServletRequest request , HttpServletResponse response , String phone) throws IOException{
		
		//1、查询是否系统中存在该号码 默认存在
		boolean flag = false;

		if(StringUtils.isNotBlank(phone)){
			
			DataBatchData b = batchDataService.getPhoneData(phone);
			if(null == b){ //在资源库中不存在。
				System.out.println("系统中不存在");
				flag = true;
			}else{
				
				if(StringUtils.isNotBlank(b.getIntentType())){ //如果有意向 不允许添加

					System.out.println("已经有意向，不允许添加-");
					
					flag = false;
					response.getWriter().print(false);
				}else{ //意向不明确
					
					if("0".equals(b.getIsFrozen()) && "0".equals(b.getIsBlacklist()) && "0".equals(b.getIsAbandon()) && "0".equals(b.getIsLock())){
						
						//如果分配给了部门 坐席
						if(StringUtils.isNoneBlank(b.getOwnDepartment(),b.getOwnUser())){
							
							System.out.println("没有意向 分配给了部门 部门分配给了坐席 允许添加该客户");
							flag = true;
						}
						
						//分配给了部门						
						if(StringUtils.isNotBlank(b.getOwnDepartment()) && null==b.getOwnUser()){
							
							System.out.println("没有意向 仅仅分配给了部门 允许添加该客户");
							flag = true;
						}
						
					}else{
						
						System.out.println("已经冻结");
						response.getWriter().print(false);
						flag = false;
					}
				}
			}
			if(flag){
				
				response.getWriter().print(true);
			}
		}
	}
	
	

	/**
	 * 
	 * 根据编号查询是否存在
	 * 
	 * @param cstm_id
	 * @return
	 */
	private Customer getCstmserviceByColumn(final String cstm_id) {

		List<Customer> customer = queryBean(new BeanHandler() {

			@Override
			public void doSql(StringBuilder sql, List<Object> params) {
				sql.append(" SELECT * FROM `cstm_customer` WHERE customer_id = ? ");
				params.add(cstm_id);
			}
		}, Customer.class);
		if (customer.size() > 0) {
			return customer.get(0);
		}

		return null;
	}

	/**
	 * 查询指定客户编号的uuid
	 * 
	 * @param serilizeId
	 * @return
	 */
	public String getCstmUidByCstmSerilizeId(String serilizeId) {

		if (StringUtils.isNotBlank(serilizeId)) {

			return jdbcTemplate.queryForObject(
					"SELECT uuid FROM cstm_customer WHERE customer_id = ? ",
					String.class, serilizeId);
		}
		return null;
	}

	/**
	 * 根据UUID查找客户固定信息
	 * 
	 * @param uuid
	 * @return
	 */
	@Override
	public Customer getCustomerByUUID(String uuid) {

		if (StringUtils.isNotBlank(uuid)) {

			return super.getByUuid(Customer.class, UUID.UUIDFromString(uuid));
		}

		return null;

	}

	/**
	 * 修改客户表主号码
	 * 
	 * @param uuid
	 */
	public void getChangeNumber(String uuid, String phone) {

		jdbcTemplate.update(
				" UPDATE cstm_customer SET phone_number = ? WHERE uuid = ? ",
				phone, uuid);

	}

	/**
	 * 查询所有
	 * 
	 * @param condition
	 * @return
	 */
	@Override
	public List<String> getAll(HttpServletRequest request) {

		Map<String, ColumnDesign> map = getTableDescByName("cstm_customer")
				.getClomns();

		StringBuilder querySql = new StringBuilder("SELECT c.uuid ");
		List<Object> params = new ArrayList<Object>();

		CustomerSolution solution = new CustomerSolution();

		solution.getWhere(map, querySql, request, params);

		return jdbcTemplate.queryForList(querySql.toString(), params.toArray(),
				String.class);

	}

	/**
	 * 批量删除客户信息
	 * 
	 * @param customerIds
	 * @return
	 */
	public Integer batchDelete(String[] customerIds) {

		Integer num = 0;
		if (customerIds.length > 0) {

			for (int i = 0; i < customerIds.length; i++) {

				num += jdbcTemplate.update(
						"DELETE FROM cstm_customer WHERE uuid = ? ;",
						customerIds[i]);
			}

			return num;
		}

		return 0;
	}

	/**
	 * 
	 * 批量移动客户到指定客户池
	 * 
	 * @param customerId
	 * @param uid
	 * @return
	 */
	@Override
	public Integer batchMove(HttpServletRequest request ,String[] customerId, String uid) {

		if (customerId.length > 0 && StringUtils.isNotBlank(uid)) {

			Integer num = 0;
			for (int i = 0; i < customerId.length; i++) {
				
				
				CstmLog cstmLog = new CstmLog();
				cstmLog.setOptUser(SessionUtil.getCurrentUser(request).getLoginName());
				cstmLog.setOptUserUUID(SessionUtil.getCurrentUser(request).getUid());
				cstmLog.setOptDate(new Date());
				cstmLog.setOptObjUUID(customerId[i]);
				cstmLog.setOptObj(customerId[i]);

//				cstmLog.setOptAction("移进了包含"+customer.getCustomerName()+"在内的"+num+"个客户到"+poolName+"中");
//				cstmLog.setObjPool(customerPool.getUid());

				num += jdbcTemplate.update(" UPDATE cstm_customer SET pool_id = ? WHERE uuid = ? ",uid, customerId[i]);
			}

			return num;
		}
		return 0;
	}

	/**
	 * 根据phone查询客户信息
	 * 
	 * @param phone
	 * @return
	 */
	@Override
	public Customer getCustomerInfoByPhone(final String phone) {

		if (StringUtils.isNotBlank(phone)) {

			List<Customer> cstmInfo = queryBean(new BeanHandler() {

				@Override
				public void doSql(StringBuilder sql, List<Object> params) {

					sql.append("SELECT * FROM cstm_customer WHERE customer_id = ? LIMIT 1 ");
					params.add(phone);
				}
			}, Customer.class);
			
			if (cstmInfo.size() > 0) {

				return cstmInfo.get(0);
			} else {
				
				List<Customer> customers = queryBean(new BeanHandler() {

					@Override
					public void doSql(StringBuilder sql, List<Object> params) {

						sql.append(" SELECT c.*,p.minor_number FROM cstm_customer c LEFT JOIN cstm_phone p ON c.uuid = p.uuid "
								+ "WHERE phone_number = ? OR minor_number = ? LIMIT 1 OFFSET 0");
						
						params.add(phone);
						params.add(phone);
					}
				}, Customer.class);
				if (customers.size() > 0) {

					return customers.get(0);
				
				}else{
					
					return null;
				}
			}
		}

		return null;
	}

	/**
	 * 根据【电话号码/编号】查询客户信息
	 * 
	 * @param phone
	 * @return
	 */
	@Override
	public Map<String, Object> getCustomerMapByPhone(String phone) {
		return jdbcTemplate.queryForMap("SELECT c.*,p.minor_number FROM cstm_customer c LEFT JOIN cstm_phone p ON c.uuid = p.uuid WHERE"
				+ " phone_number = ? OR p.minor_number = ?  OR customer_id = ? LIMIT 1 OFFSET 0 ",phone,phone,phone);

	}

	/**
	 * 获取所有号码 主号码 子号码
	 * 
	 * @return
	 */

	@Override
	public List<String> getAllPhones() {

		return jdbcTemplate.queryForList(
				"SELECT phone_number as phoneNumber FROM cstm_customer ",
				String.class);

	}

	/**
	 * 根据编号或者号码查询
	 * 
	 * @param param
	 * @return
	 * 
	 * 可以按照从号码来查询，但是没有显示从号码
	 * 
	 */
	@Override
	public Customer getCustomerByPhoneOrNumber(final String param) {

		if (StringUtils.isNotBlank(param)) {

			List<Customer> customers = queryBean(new BeanHandler() {

				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					
					sql.append(" SELECT c.*,p.minor_number FROM cstm_customer c LEFT JOIN cstm_phone p ON c.uuid = p.uuid "
							+ " WHERE phone_number = ? OR minor_number = ? OR customer_id = ? LIMIT 1 OFFSET 0;");
					params.add(param);
					params.add(param);
					params.add(param);
				}
			}, Customer.class);

			if (customers.size() > 0) {

				return customers.get(0);
			}else{
				
				List<Customer> customerPhone = queryBean(new BeanHandler() {

					@Override
					public void doSql(StringBuilder sql, List<Object> params) {
						sql.append("SELECT * FROM cstm_customer WHERE uuid = (SELECT uuid FROM cstm_phone WHERE 1 = 1 ");
						QueryUtils.like(sql, params, param, " AND  minor_number LIKE ? LIMIT 1) ");
					}
				}, Customer.class);
				
				if(customerPhone.size() > 0){
					
					return customerPhone.get(0);
				}
			}
			
			return null;
		}

		return null;
	}

	/**
	 * 得到使用频率次数前几的人数
	 * @param num
	 * @return
	 */
	@Override
	public List<CstmReport> getTopNumberTags(final String poolId,final String num) {

		List<CstmReport> cstmReports = queryBean(new BeanHandler() {

			@Override
			public void doSql(StringBuilder sql, List<Object> params) {

				sql.append(" SELECT l.customer_uuid,g.tag_name as name, COUNT(*) as count FROM cstm_tag_link l,cstm_customer_tag g"
						+ " WHERE g.uuid = l.tag_id  AND g.tag_name <> ''"
						+ " AND customer_uuid in (SELECT uuid FROM cstm_customer c WHERE c.pool_id = ? ) "
						+ " GROUP BY tag_id  ORDER BY count DESC LIMIT "+ num +" ");
				params.add(poolId);
				
			}
		}, CstmReport.class);
		
		return cstmReports;

	}

	/**
	 *获取所有标签
	 * @return
	 */
	@Override
	public List<String> getAllTags() {
		
		return jdbcTemplate.queryForList(" SELECT tag_name FROM cstm_customer_tag; ", String.class);
	}

	/**
	 * 根据坐席编号查询该坐席的所有客户
	 * @param agnetNum
	 * @return
	 */
	@Override
	public List<Customer> getOwnerByAgent(final String agnetNum) {

		List<Customer> customers = queryBean(new BeanHandler() {
			
			@Override
			public void doSql(StringBuilder sql, List<Object> params) {

				sql.append(" SELECT * FROM `cstm_customer` WHERE own_id = ? ");
				params.add(agnetNum);
			}
		}, Customer.class);
		
		return customers;
	}

	/**
	 * 保存第二个号码
	 * @param request
	 * @param cstm_uuid
	 * @param cstmUuid
	 */
	@Override
	public void saveSecondNumber(HttpServletRequest request, String cstm_uuid, String cstmUuid) {

		// 取到第二个号码
		String secPhone = phoneService.deletZero(request.getParameter("phone_number_a"));

		if (StringUtils.isNotBlank(secPhone)) {

			// 查询开始设置的号码 删除原来关联的号码 添加新的关联
			CstmPhone cstmPhone = phoneService.getCstmPhone(request.getParameter("phone_number"));
			// 存在
			if (null != cstmPhone) {

				cstmPhone.setMinorBumber(secPhone);
				phoneService.update(cstmPhone);

			} else {

				CstmPhone addCstmPhone = new CstmPhone();
				addCstmPhone.setUuid(StringUtils.isBlank(cstm_uuid)?cstmUuid:cstm_uuid);
				addCstmPhone.setMinorBumber(phoneService.deletZero(secPhone));
				addCstmPhone.setMainNumber(request.getParameter("phone_number"));

				phoneService.save(addCstmPhone);
			}
		}
	}
	
	
	/**
	 * 根据号码或者是编号查询多个数据
	 * @param agrs
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getCustomersInfoByIdOrPhone(String agrs){
		
		return jdbcTemplate.queryForList(" SELECT c.*,p.minor_number FROM cstm_customer c LEFT JOIN cstm_phone p ON c.uuid = p.uuid "
							+ " WHERE phone_number = ? OR minor_number = ? OR customer_id = ? ",agrs,agrs,agrs);
		
//		return jdbcTemplate.queryForList("SELECT * FROM cstm_customer WHERE customer_id = ? OR phone_number = ?",agrs,agrs);
	}

	/**
	 * 清空归属人 电话意向 电话状态
	 * @param phone 电话号码
	 * @return 受影响的行数
	 */
	@Override
	public Integer emptyOwnerByPhone(String phone) {

		if(StringUtils.isNotBlank(phone)){
			
			return jdbcTemplate.update(" UPDATE cstm_customer SET own_id = NULL, pool_id = '', `status` = ''  WHERE phone_number = ? ",phone);
		}
		
		return -1;
	}

	/**
	 * 修改意向客户为成交客户
	 * 传递号码 拥有者
	 * @param phone
	 * @param ownerId
	 * @return
	 */
	@Override
	public JSONObject updateIntendToCustomer(String phone, String ownerId) {
		
		String msg = "";
		String uuid = null ;
		Integer updateCount = null ;
			
		uuid = jdbcTemplate.queryForObject(" SELECT uuid FROM cstm_customer WHERE phone_number = ? ", String.class,phone);
		if(StringUtils.isNotBlank(uuid)){
			
			updateCount = jdbcTemplate.update(" UPDATE cstm_customer SET pool_id = 'de001', own_id = ?, `status` = '1',`start_date` = NOW() WHERE phone_number = ? ",ownerId, phone);
		}
		
		if(updateCount < 1){
			
			msg = "修改失败";
		}
		
		return new JSONObject().put("uuid", uuid).put("msg", msg);
	}
	
	/**
	 * 批量修改拥有者
	 * @param toUpdate
	 * @param beUpdate
	 * @return
	 */
	@Override
	public Integer batchUpdateCustomerOwner(String[] toUpdate , String beUpdate) {
		
		
		return 0;
	}

	/**
	 * 根据部分信息保存客户信息
	 * @param cstmName
	 * @param cstmType
	 * @param owner
	 * @param phone
	 * @param phone1
	 * @param status
	 * @return
	 */
	@Override
	public JSONObject saveCustomerInfo(String cstmName,	String owner, String phone, String phone1 , String status , String type) {
		
		if(StringUtils.isBlank(cstmName)){
			
			return new JSONObject().put("success", true).put("message", "不是本人的成交客户！");
		}
		
		String uuid = null;
		String msg = "保存成功";
		Integer affectCount = 0;
		List<String> list = jdbcTemplate.queryForList("SELECT uuid FROM cstm_customer WHERE phone_number = ? OR phone_number = ? limit 1", String.class,phone,phone1);
		if (list.size() > 0) {
			uuid = list.get(0);
			if(StringUtils.isNoneBlank(uuid)){
				msg = "修改成功";
				String sql = "";
				if(StringUtils.isBlank(type)){
					sql = "UPDATE cstm_customer SET cstm_name = ? , own_id = ?  WHERE uuid = ? ";
					affectCount = jdbcTemplate.update(sql,cstmName,owner,uuid);
				}else{
					sql = "UPDATE cstm_customer SET cstm_name = ? , own_id = ?, status = ? ,pool_id = ? WHERE uuid = ? ";
					affectCount = jdbcTemplate.update(sql,cstmName,owner,status,type,uuid);
				}
				if(!(affectCount ==1) ){
					msg = "修改失败";
				}
				
				if(StringUtils.isNotBlank(phone1)){
					//添加一个客户关联信息
					CstmPhone cstmPhone = new CstmPhone();
					cstmPhone.setMinorBumber(phone1);
					cstmPhone.setMainNumber(phone);
					cstmPhone.setUuid(UUID.randomUUID().toString());
					phoneService.save(cstmPhone);	
				}
			}
		} else {
			uuid = UUID.randomUUID().toString();
			affectCount = jdbcTemplate.update("INSERT INTO `cstm_customer` "
					+ "(`uuid`, `cstm_name`, `customer_id`, `phone_number`, `pool_id`, `own_id`,`start_date`, `status`) VALUES "
					+ "( ?, ?, ?, ?, ?, ?,  NOW(),?) ",
					uuid,cstmName,customerSerialize.getCstmSerializeId(),phone,type,owner,status);
			if(!(affectCount ==1) ){
				msg = "保存失败";
			}
			
			
			if(StringUtils.isNotBlank(phone1)){
				
				//添加一个客户关联信息
				CstmPhone cstmPhone = new CstmPhone();
				cstmPhone.setMinorBumber(phone1);
				cstmPhone.setMainNumber(phone);
				cstmPhone.setUuid(UUID.randomUUID().toString());
				phoneService.save(cstmPhone);
			}
		}
		return new JSONObject().put("success", true).put("uuid", uuid).put("message", msg);
	}

	/**
	 * 批量移动到指定客户下
	 * @param customerId
	 * @param ownerId
	 * @return 
	 */
	@Override
	public Integer batchUpdateToUser(String[] customerId, String ownerId) {

		if(customerId.length <= 0){
			
			return 0;
		}else{
			
			String[] param = new String[customerId.length];
			Arrays.fill(param, "?");
			String inSql = Arrays.toString(param);
		
			List<String> array = new ArrayList<String>();
			array.add(ownerId);
			array.addAll(Arrays.asList(customerId));
			
			return jdbcTemplate.update(" UPDATE cstm_customer SET own_id = ? WHERE uuid in ("+ inSql.substring(1, inSql.length()-1) +") " ,array.toArray());
			
		}
		
	}

	/**
	 * 获取该权限下的所有客户池
	 * @param request
	 * @param customerPools
	 * @param user
	 * @return
	 */
	@Override
	public List<CustomerPool> getPermissionPools(HttpServletRequest request,
			List<CustomerPool> customerPools, User user) {
			if (!"1".equals(user.getAdminFlag())) {
				
				//所属角色可管辖部门
				List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());//管辖部门
				//可管辖的所有客户池
				Set<String> set = customerPoolService.getPoolsByDepartments(departments);
				
				set.addAll(customerPoolService.getDepartmentDataContainers(user.getDepartment()));
				
				// 从所有池中去除掉不能看到的池
				for (int i = 0; i < customerPools.size(); i++) {
					if (!set.contains(customerPools.get(i).getUid())) {
						customerPools.remove(i--);
					}
				}

				Set<CustomerPool> setCustomerPools = new HashSet<CustomerPool>(customerPools);
				// 查询该坐席创建的客户池 添加到可以看的客户池中
				List<CustomerPool> list2 = customerPoolService.getPoolsByAgentId(user.getUid());
				if (null != list2) {
					setCustomerPools.addAll(list2);
				}
				customerPools = new ArrayList<CustomerPool>(setCustomerPools);
			}
			return customerPools;
	}
	
	@Override
	public void getShowTitle(Model model) {
		DiyBean ret = getTableDescByName("cstm_customer");
		// 查询的字段
		List<String> list = new ArrayList<String>();
		// 取到可以查询的字段
		for (ColumnDesign re : ret.getClomns().values()) {
			// 筛选语序查询的字段
			if (re.getAllowSelect().equals(ColumnDesign.ALLOWSELECT)
					&& re.getIsDefault().equals(ColumnDesign.NOTDEFAULT)
					&& re.getAllowShow().equals(ColumnDesign.ALLOWSHOW)) {
				list.add(re.getColumnNameDB());
			}
		}
		// 存放允许查询并且不是默认的所有字段
		model.addAttribute("tit", list);
	}

	/**
	 * 取到客户信息，将客户信息值保存 放置页面
	 * @param uuid
	 * @param phone
	 * @param model
	 * @param customer
	 */
	@Override
	public void getCustomerInfoAndSetPageValue(UUID uuid, String phone,
			Model model, Map<String, Object> customer) {
		if (StringUtils.isNotBlank(uuid.getUuid())) {
		
			customer = getCustomerById(uuid);
		} else if (StringUtils.isNotBlank(phone)){
			
			List<String> phones = getAllPhones();
			
			if(phones.contains(phone)){
			
				customer = getCustomerMapByPhone(phone);
			} else {
				
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("phone_number", phone);
				model.addAttribute("Acstm", temp);
			}
		}
		if (customer != null) {
			model.addAttribute("pool_id", customer.get("pool_id").toString());
			model.addAttribute("own_id", StringUtils.trimToEmpty(customer.get("own_id")+""));
			// 存放tag
			List<String> tagsStr = customerTagService.getCustomerTags(customer.get("uuid").toString());
			model.addAttribute("tagsStr",tagsStr.size() > 0 ? tagsStr.toString() : "");
			
			// 存放客户信息
			model.addAttribute("Acstm", customer);
			model.addAttribute("uid", customer.get("uuid").toString());
			
			//查询第二号码
			String cstmPhone = phoneService.getAllCstmPhones(customer.get("phone_number")+"");
			if(null != cstmPhone){

				model.addAttribute("secPhone", cstmPhone);
			}
		}
	}

	/** 
	 * 获取普通用户可查询的池
	 * @param request
	 * @param customerPools
	 * @param user
	 * @return
	 */
	@Override
	public List<CustomerPool> getNormalUserPools(HttpServletRequest request,
			List<CustomerPool> customerPools, User user) {
		if (!"1".equals(user.getAdminFlag())) {
			
			//所属角色可管辖部门
			List<String> departments = userService.getDepartments(SessionUtil.getCurrentUser(request).getUid());//管辖部门
			
			//可管辖的所有客户池
			Set<String> set = customerPoolService.getPoolsByDepartments(departments);
			// 查询他自己创建的所有客户池
			for (int i = 0; i < customerPools.size(); i++) {
				if (!set.contains(customerPools.get(i).getUid())) {
					customerPools.remove(i--);
				}
			}
			
			// 查询该坐席创建的客户池 添加到可以看的客户池中
			Set<CustomerPool> setCustomerPools = new HashSet<CustomerPool>(customerPools);
			//查询该坐席创建的客户池 添加到可以看的客户池中
			List<CustomerPool> list2 = customerPoolService.getPoolsByAgentId(SessionUtil.getCurrentUser(request).getUid());
			if (null != list2)
				setCustomerPools.addAll(list2);

			customerPools = new ArrayList<CustomerPool>(setCustomerPools);

		}
		return customerPools;
	}

	/**
	 * 改变主号码
	 * @param phone
	 * @param uid
	 * @param number
	 * @param pId
	 */
	@Override
	public void changeMainNumber(String phone, String uid, String[] number,
			String pId) {
		String num = Arrays.toString(number);
		CstmPhone cstmPhone = new CstmPhone();
		cstmPhone.setMainNumber(phone);
		if (StringUtils.isNotBlank(num)) {
			num = num.replace("[", "").replace("]", "");
		}
		cstmPhone.setMinorBumber(num);
		cstmPhone.setUuid(uid);

		if (StringUtils.isNotBlank(pId)) {// 保存

			cstmPhone.setId(Integer.valueOf(pId));
			phoneService.update(cstmPhone);
		} else {

			phoneService.save(cstmPhone);
		}
	}

	/**
	 * 根据主号码查询第二号码
	 * @param arg0
	 * @return 
	 */
	@Override
	public String selectSecondNumber(String arg0) {

		try {
			
			return jdbcTemplate.queryForObject(" SELECT minor_number from cstm_phone WHERE main_number = ? LIMIT 1 OFFSET 0 ", String.class,arg0);
			
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		return null;
		
	}

	/**
	 * 根据编号和号码验证是否是成交客户
	 * @param phone
	 * @param customerId
	 */
	@Override
	public String beCustomer(String phone, String customerId) {
		
		try {

			String sql = "SELECT customer_id FROM cstm_customer WHERE customer_id = ? AND phone_number = ?  AND `status` = 1 ";
			return jdbcTemplate.queryForObject(sql, String.class,customerId,phone);
			
		} catch (Exception e) {
		}
		
		return null;
	}

	
}
