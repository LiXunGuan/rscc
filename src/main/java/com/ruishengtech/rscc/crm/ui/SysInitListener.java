package com.ruishengtech.rscc.crm.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.db.DbContext;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.framework.core.db.service.TableService;
import com.ruishengtech.framework.core.licence.ChannelManager;
import com.ruishengtech.framework.core.licence.ClientHandler;
import com.ruishengtech.framework.core.licence.util.LicenseDataUtil;
import com.ruishengtech.framework.core.licence.util.LicenseUtils;
import com.ruishengtech.framework.core.licence.util.MD5Util;
import com.ruishengtech.framework.core.licence.util.MacAddressUtil;
import com.ruishengtech.framework.core.util.ClassPathScanHandler;
import com.ruishengtech.rscc.crm.datamanager.manager.DataManagers;
import com.ruishengtech.rscc.crm.datamanager.manager.TransferRegister;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.AbandonToBatchTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.BatchToDepTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.BatchToUserTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.BlackListToBatchTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.CrossDepToUserTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.CrossUserToDepTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.CrossUserToUserTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.DepToBatchTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.DepToDepTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.DepToUserTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.FrozenToBatchTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.IntentToCustomerTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.IntentToShareTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.IntentToUserTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.ShareToFrozenTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.ShareToUserTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.UserToAbandonTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.UserToBatchTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.UserToBlacklistTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.UserToCustomerTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.UserToDepTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.UserToIntentTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.UserToShareTransfer;
import com.ruishengtech.rscc.crm.datamanager.manager.transfer.UserToUserTransfer;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.NewUserDataService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DepartmentTableServiceImp;
import com.ruishengtech.rscc.crm.report.service.ReportBillService;
import com.ruishengtech.rscc.crm.report.service.imp.ReportDataBatchServiceImp;
import com.ruishengtech.rscc.crm.ui.report.LinuxInfoUtil;
import com.ruishengtech.rscc.crm.user.model.Datarange;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.UserService;

public class SysInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		try {
			// 自动扫描自己的bean
			doScan();
			DbContext.getInstance().init();
			TableService tableService = ApplicationHelper.getApplicationContext().getBean(TableService.class);
			tableService.createTable();

			DiyTableService diyTableManager = ApplicationHelper.getApplicationContext().getBean(DiyTableService.class);
			diyTableManager.load();
			
			// 获取指纹
			ChannelManager.getInstance().setFingerprint(MD5Util.MD5("a"+MacAddressUtil.getMacAddress()+"b"));
			
			 //获取NETTY服务器端IP和端口
//			openClient(LicenseUtils.serverIP, LicenseUtils.serverPORT);
			
			// 存放请求路径
			IndexService indexService = ApplicationHelper.getApplicationContext().getBean(IndexService.class);
			indexService.initUrl();
			
	        LinuxInfoUtil.getLinuxInfo();
	        
	        doInitDataManager();
	        
	        doInitNewData();
	        
	        clearReportData();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}


    private void doInitDataManager() {
        DataManagers dataManagers = ApplicationHelper.getApplicationContext().getBean(DataManagers.class);
        
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(AbandonToBatchTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(BatchToDepTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(BatchToUserTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(BlackListToBatchTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(CrossUserToUserTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(CrossDepToUserTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(DepToBatchTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(DepToDepTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(DepToUserTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(FrozenToBatchTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(IntentToCustomerTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(IntentToUserTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(IntentToShareTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(UserToBatchTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(UserToAbandonTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(UserToBlacklistTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(UserToCustomerTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(UserToDepTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(UserToIntentTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(UserToUserTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(UserToShareTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(ShareToFrozenTransfer.class)));
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(ShareToUserTransfer.class)));
        
        
        dataManagers.add(new TransferRegister(ApplicationHelper.getApplicationContext().getBean(CrossUserToDepTransfer.class)));
	}
    
    //这里要把之前的部门表和数据创建出来
    //步骤1、扫描系统中所有部门，创建出他们的部门与用户数据表，并把部门插入到new_data_department表中
    //步骤2、扫描系统所有用户，把他们加入new_data_department_user表
    private void doInitNewData() {
    	
    	DataBatchService dataBatchService = ApplicationHelper.getApplicationContext().getBean(DataBatchService.class);
    	DatarangeService datarangeService = ApplicationHelper.getApplicationContext().getBean(DatarangeService.class);
    	UserService userService = ApplicationHelper.getApplicationContext().getBean(UserService.class);
    	DepartmentTableServiceImp departmentTableService = ApplicationHelper.getApplicationContext().getBean(DepartmentTableServiceImp.class);
    	NewUserDataService newUserDataService = ApplicationHelper.getApplicationContext().getBean(NewUserDataService.class);
    	
    	//创建默认批次
    	if (dataBatchService.getByUuid(UUID.UUIDFromString("")) == null) {
    		dataBatchService.createDefaultBatch();
    	}
    	
    	List<Datarange> datarangeList = datarangeService.getAllDatarange();
    	for (Datarange datarange : datarangeList) {
    		if (departmentTableService.getByUuid(datarange.getUuid()) == null) {
//    			departmentTableService.save(datarange.getUid(), datarange.getDatarangeName(), 10000);
    			// chengxin 2015.11.20 部门上限为100000
    			departmentTableService.save(datarange.getUid(), datarange.getDatarangeName(), 100000);
    		}
		}
    	
    	List<User> userList = userService.getAllUser();
    	for (User user : userList) {
    		if (newUserDataService.getByUuid(user.getUuid()) == null) {
    			newUserDataService.save(user.getUid(), user.getDepartment(), 500, 1000, 1000, 500);
    		}
		}
    
    	System.out.println("------------------结束初始化new相关所有表------------");
    }


	private void doScan() {

		ClassPathScanHandler classPathScanHandler = new ClassPathScanHandler(
				true, true, null);
		Set<Class<?>> ret = classPathScanHandler.getPackageAllClasses(
				"com.ruishengtech", true);
		for (Class<?> aClass : ret) {
			if (aClass.isAnnotationPresent(Table.class)) {
				DbContext.getInstance().addTable(aClass);
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
	
	/**
	 * 开启客户端并判断本地License
	 * @param serverip
	 * @param serverport
	 */
	public void openClient(final String serverip, final int serverport){
		Thread clientThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ClientHandler ka = new ClientHandler(serverip, serverport);
					ka.connServer();
					if(LicenseUtils.getInstance().readLicense() == null){
						System.out.println("===============================本地没有license。。。。。。。");
						LicenseDataUtil.requestLicense();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		clientThread.start();
	}
	
	/**
	 * 启动的时候判断清除报表前一天数据，重新生成前一天数据
	 */
	public void clearReportData(){
		ReportDataBatchServiceImp databatch = ApplicationHelper.getApplicationContext().getBean(ReportDataBatchServiceImp.class);
		databatch.deleteAgoData();
		
		System.out.println(new Date()+"昨天数据删除完毕，开始插入数据日报表===============");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		final String yesterday = sdf.format(cal.getTime());
		databatch.insertReportData(yesterday+" 00:00:00",sdf.format(new Date())+" 00:00:00","day");
		System.out.println(new Date()+"结束插入数据日报表===============");
		
		
		ReportBillService bill = ApplicationHelper.getApplicationContext().getBean(ReportBillService.class);
		bill.deleteReportBillByDate();
		System.out.println(new Date()+"昨天计费报表删除完毕，开始插入计费日报表===============");
		bill.insertRBillByBill();
		System.out.println(new Date()+"结束插入计费日报表===============");
		
	}
}
