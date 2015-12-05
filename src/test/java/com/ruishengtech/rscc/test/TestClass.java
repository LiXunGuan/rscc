//package com.ruishengtech.rscc.test;
//
//import java.util.List;
//import java.util.Set;
//
//import org.json.JSONObject;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.ruishengtech.framework.core.db.DbContext;
//import com.ruishengtech.framework.core.db.Table;
//import com.ruishengtech.framework.core.db.service.BaseService;
//import com.ruishengtech.framework.core.util.ClassPathScanHandler;
//import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
//import com.ruishengtech.rscc.crm.task.ScheduledTaskManager;
//import com.ruishengtech.rscc.crm.ui.IndexService;
//import com.ruishengtech.rscc.crm.user.model.Datarange;
//import com.ruishengtech.rscc.crm.user.service.DatarangeService;
//import com.ruishengtech.rscc.crm.user.service.UserService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:config/applicationContext.xml" })
//public class TestClass extends BaseService {
//
//	@Autowired
//	private IndexService imp;
//
//	@Autowired
//	private CustomerService customerService;
//
//	@Autowired
//	private ScheduledTaskManager manager;
//	
//	@Autowired
//	private DatarangeService datarangeService;
//	
//	@Autowired
//	private UserService userService;
//
//	private void doScan() {
//
//		ClassPathScanHandler classPathScanHandler = new ClassPathScanHandler(
//				true, true, null);
//		Set<Class<?>> ret = classPathScanHandler.getPackageAllClasses(
//				"com.ruishengtech", true);
//		for (Class<?> aClass : ret) {
//			if (aClass.isAnnotationPresent(Table.class)) {
//				DbContext.getInstance().addTable(aClass);
//			}
//		}
//	}
//
//	public TestClass(){
//		doScan();
//		DbContext.getInstance().init();
//	}
//	
//	
//	/**
//	 * 测试当前部门下的子部门
//	 * @throws Exception
//	 */
//	@Test
//	public void getChildrenDept() throws Exception {
//
//		System.out.println(jdbcTemplate.queryForObject("select count(*) from cstm_customer where 1=0", Integer.class));;
//		
//	}
//
//
//	
//	
//	public static void main(String[] args) {
//
//		JSONObject jsonObject = new JSONObject().put("ss", "12").put("qq", "23").put("ee", "323");
////		toHashMap(jsonObject);
//	}
//
//}
