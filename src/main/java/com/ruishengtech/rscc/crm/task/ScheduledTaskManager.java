package com.ruishengtech.rscc.crm.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.rscc.crm.datamanager.manager.DataManagers;
import com.ruishengtech.rscc.crm.datamanager.manager.TransferResult;
import com.ruishengtech.rscc.crm.datamanager.manager.node.FrozenNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.ShareNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.ShareToFrozenData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchDepartmentLink;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.DeptDataService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataBatchDepartmentLinkServiceImp;
import com.ruishengtech.rscc.crm.report.service.ReportBillService;
import com.ruishengtech.rscc.crm.report.service.imp.ReportDataBatchServiceImp;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;

@Component
@Transactional
public class ScheduledTaskManager {
	
	@Autowired
	private ReportBillService rbService;
	
	@Autowired
	private ReportDataBatchServiceImp reportDataBatchServiceImp;
	
	@Autowired
	private DataBatchService dataBatchService;

	@Autowired
	private DeptDataService deptDataService;
	
	@Autowired
	private SysConfigService configService;
	
	@Autowired
	private DataBatchDepartmentLinkServiceImp dataBatchDepartmentLinkService;
	
	@Autowired
	private DataManagers dataManager;
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Scheduled(cron="* * * * * ?")
	private void dataStatistics(){
		
	}
	
	/**
	 * 计费报表定时插入任务
	 */
	@Scheduled(cron="0 0 02 * * ?")
//	@Scheduled(cron="*10 * * * * ?")
	public void historyReportPlan(){
		System.out.println(new Date()+"开始插入计费日报表==============="+new Date());
		rbService.insertRBillByBill();
		System.out.println(new Date()+"结束插入计费日报表==============="+new Date());
	}
	
	/**
	 * 数据报表定时插入任务
	 */
	@Scheduled(cron="0 10 02 * * ?")
	public void dataReportPlan(){
		System.out.println(new Date()+"开始插入数据日报表===============");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		final String yesterday = sdf.format(cal.getTime());
		reportDataBatchServiceImp.insertReportData(yesterday+" 00:00:00",sdf.format(new Date())+" 00:00:00","day");
		System.out.println(new Date()+"结束插入数据日报表===============");
	}
	
	/**
	 * 数据报表每月定时插入任务
	 */
	@Scheduled(cron="0 0 01 01 * ?")
	public void dataReportForMonthPlan(){
		System.out.println(new Date()+"  开始插入数据月报表===============");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, 01);
		final String  monthstart = sdf.format(cal.getTime());
		
		Calendar ecal = Calendar.getInstance();
		ecal.set(Calendar.DAY_OF_MONTH, 1);
		final String  monthend = sdf.format(ecal.getTime());
		
		reportDataBatchServiceImp.insertReportData(monthstart+" 00:00:00",monthend+" 00:00:00","month");
		System.out.println(new Date()+"  结束插入数据月报表===============");
	}
	
	/**
	 * 数据模块的同步，每天的两点五分
	 */
	@Scheduled(cron="0 5 02 * * ?")
	public void syncronizeData(){
		System.out.println(new Date() + "=================开始同步数据模块内容===============");
		//sync databatch
		ArrayList<Object[]> updatelist = new ArrayList<Object[]>();
		String updateSql = "update new_data_batch set data_count=?,own_count=?,intent_count=?,"
				+ "customer_count=?,frozen_count=?,abandon_count=?,share_count=?,blacklist_count=? where uuid=?";
		List<String> list = dataBatchService.getUuids();
		for (String dataBatch : list) {
			Map<String, Object> map = dataBatchService.getBatchStat(dataBatch);
			updatelist.add(new Object[]{map.get("dataCount"), map.get("ownCount"), map.get("intentCount"), map.get("customerCount"),
					map.get("frozenCount"), map.get("abandonCount"), map.get("shareCount"), map.get("blacklistCount"), dataBatch});
		}
		jdbcTemplate.batchUpdate(updateSql, updatelist);
		
		//sync dept
		ArrayList<Object[]> synclist = new ArrayList<Object[]>();
		String syncSql = "update new_data_batch_department_link set data_count=?,own_count=?,intent_count=?,customer_count=?,abandon_count=?,blacklist_count=? "
				+ " where data_batch_uuid=? and department_uuid=?";
		List<DataBatchDepartmentLink> linklist = dataBatchDepartmentLinkService.getAllLink();
		for (DataBatchDepartmentLink dataBatchDepartmentLink : linklist) {
			Map<String, Object> map = deptDataService.getDataDeptStat(dataBatchDepartmentLink.getDataBatchUuid(), dataBatchDepartmentLink.getDepartmentUuid());
			synclist.add(new Object[]{(int)map.get("dataCount") + (int)map.get("ownCount"), map.get("ownCount"), map.get("intentCount"), map.get("customerCount"), 
					map.get("abandonCount"), map.get("blacklistCount"), 
					dataBatchDepartmentLink.getDataBatchUuid(), dataBatchDepartmentLink.getDepartmentUuid()});	
		}
		jdbcTemplate.batchUpdate(syncSql, synclist);
		System.out.println(new Date() + "=================结束同步数据模块内容===============");
	}
	
	/**
	 * 每天的一点十分
	 * 同步冷冻的数据
	 */
	@Scheduled(cron="0 10 01 * * ?")
	public void syncronizeFrozenData() {
		
		
		System.out.println(new Date() + "=================开始自动同步冷冻数据===============");
		String beAutoExecute = configService.getSysConfigByKey("autoFrozen").getSysVal();
		
		//开启自动冷冻
		if(StringUtils.isNotBlank(beAutoExecute) && "true".equals(beAutoExecute.split("-")[0])){

			TransferResult tr;
			
			//查询X天被Y次获取过的数据
			List<String> list = jdbcTemplate.queryForList("SELECT d.phone_number FROM `new_data_global_share_record` d "
					+ "WHERE d.mark_save = 1 AND DATE_ADD(d.own_user_timestamp,INTERVAL ? DAY) > NOW() "
					+ "GROUP BY d.phone_number HAVING COUNT(d.phone_number) > ? ; " ,
					String.class,beAutoExecute.split("-")[1],beAutoExecute.split("-")[2]);

			String batchId = "";
			
			for (int i = 0; i < list.size(); i++) {
				try {
					
					batchId = jdbcTemplate.queryForObject(" SELECT batch_uuid FROM `new_data_phone_resource` WHERE phone_number = ? ", String.class,list.get(i));
				} catch (Exception e) {
					
				}
				ShareNode fromNode = new  ShareNode(batchId);
				FrozenNode toNode = new FrozenNode(); 
				ShareToFrozenData data = new ShareToFrozenData();
				data.setTransferPhone(list.get(i));
				
				tr = dataManager.transfer(fromNode, toNode, data);
			}
		}
		System.out.println(new Date() + "=================结束自动同步冷冻数据===============");
	}
	
	@Scheduled(cron="0 20 01 * * ?")
	public void syncronizeShareMarkData() {
		
		System.out.println(new Date() + "=================开始解除共享池中被标记数据===============");
		String deleteFlag = configService.getSysConfigByKey("sys.globalshare.deleteFlag").getSysVal();
		//开启自动冷冻
		String updateSql = "update new_data_global_share set own_user=null,own_user_timestamp=null where DATE_ADD(own_user_timestamp,INTERVAL ? DAY) > NOW() and own_user='system'";
		int updateCount = jdbcTemplate.update(updateSql, Integer.parseInt(deleteFlag));
		
		System.out.println("共解除" + updateCount + "条标记数据");
		System.out.println(new Date() + "=================结束解除共享池中被标记数据===============");
	}
	
}
