package com.ruishengtech.rscc.crm.ui.mw.event.handler;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.datamanager.manager.DataManagers;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BatchNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.DepNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.BatchToUserData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.CrossDepToUserData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.DepToUserData;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatchData;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.ui.mw.event.IEventHandler;
import com.ruishengtech.rscc.crm.ui.mw.model.CallLog;
import com.ruishengtech.rscc.crm.ui.mw.service.CallLogService;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * Created by yaoliceng on 2015/6/16.
 */
public class PopEventHandler implements IEventHandler {
    
	private static final Logger logger = Logger.getLogger(PopEventHandler.class);
	
	@SuppressWarnings("serial")
	public static Map<String, String> allBusiness = new LinkedHashMap<String, String>(){
		{
			put("record", "沟通记录");
			put("order", "订单管理");
			put("appointment", "预约提醒");
		}
	};
	
	BrokerService brokerService;
	CallLogService callLogService;
	DataBatchService dataBatchService;
	UserService userService;
	DataManagers dataManagers;
	SysConfigService configService;
	/*
	 * {"timestamp":"2015-09-10 19:29:08","call_direction":"out_in","caller_id":"52018900099908","time":"answer","queue_name":"99","event":"pop_event",
	 * "record_file_url":"http://192.168.1.139:18080/home/record/2015/09/10/6a8ef3f0-f05f-45ca-b380-48a20efa101f.mp3","exten":"1001",
	 * "call_session_uuid":"6a8ef3f0-f05f-45ca-b380-48a20efa101f","agent_id":"ndata22","agent_info":"ndata22"}
	 */
	
	public PopEventHandler() {}
	
	@Override
    public void handler(JSONObject jsonObject) {
		brokerService = brokerService==null?ApplicationHelper.getApplicationContext().getBean(BrokerService.class):brokerService;
		callLogService = ApplicationHelper.getApplicationContext().getBean(CallLogService.class);
		dataBatchService = ApplicationHelper.getApplicationContext().getBean(DataBatchService.class);
		userService = ApplicationHelper.getApplicationContext().getBean(UserService.class);
		dataManagers = ApplicationHelper.getApplicationContext().getBean(DataManagers.class);
		configService = ApplicationHelper.getApplicationContext().getBean(SysConfigService.class);
		
		String autoBusy = configService.getSysConfigByKey("autoBusy").getSysVal();
		logger.info("弹屏消息收到！");
		
    	try {
    		
			JSONObject json = new JSONObject();
			json.put("type", "pop_event");
			
			json.put("agent_id", jsonObject.optString("agent_id"));
//			json.put("access_number", jsonObject.optString("caller_id"));
			json.put("access_number", jsonObject.optString("access_number"));
			json.put("caller_id", jsonObject.optString("caller_id"));
			json.put("ivr_desc", jsonObject.optString("ivr_desc"));
			json.put("timestamp", jsonObject.optString("timestamp"));
			json.put("autoBusy", autoBusy);
			
			json.put("call_session_uuid", jsonObject.optString("call_session_uuid"));
			
			json.put("info", new JSONObject().put("poplog", "record").put("order", "order").put("appointment", "appointment"));
			
			if(("out_in").equals(jsonObject.optString("call_direction"))){
				CallLog callLog = new CallLog();
				callLog.setAgent_name(jsonObject.optString("agent_id"));
				callLog.setCall_phone(jsonObject.optString("caller_id"));
				callLog.setIn_out_flag("呼入");
				callLog.setCall_time(new Date());
				callLog.setCall_session_uuid(jsonObject.optString("call_session_uuid"));
				callLog.setRecord_path(jsonObject.optString("record_file_url"));
				callLogService.save(callLog);
				

	    		String phone = jsonObject.optString("caller_id");
	    		String userName = jsonObject.optString("agent_id");
	    		String batchUuid = dataBatchService.getPhoneBatch(phone);
	    		
	    		if (StringUtils.isNotBlank(batchUuid)) { //某个批次中有这条数据，直接帮他从批次获取
	    			User user = userService.getUserByLoginName(userName, false);
	    			DataBatchData data = dataBatchService.getDataByPhone(batchUuid, phone);
	    			if(isGetableFromBatch(data)) {
	    				BatchNode from = new BatchNode(batchUuid);
	    				UserNode to = new UserNode(user.getDepartment());
	    				
	    				BatchToUserData transferData = new BatchToUserData();
	    				
	    				transferData.setTransferUser(user.getUid());
	    				
	    				transferData.setDatas(new String[]{data.getUid()});
	    				
	    				dataManagers.transfer(from, to, transferData);
	    			} else if(isGetableFromDept(data)) { //如果是可获取的，帮他获取
	    				if(data.getOwnDepartment().equals(user.getDepartment())) {//如果是他部门的，则直接分配给他
		    				DepNode from = new DepNode(user.getDepartment());
		    				UserNode to = new UserNode(user.getUid());
		    				
		    				DepToUserData transferData = new DepToUserData();
		    				
		    				transferData.setBatchUuid(batchUuid);
		    				
		    				transferData.setDatas(new String[]{data.getUid()});
		    				
		    				dataManagers.transfer(from, to, transferData);
	    				} else {//如果不是，则跨部门分配
	    					DepNode from = new DepNode(data.getOwnDepartment());
		    				UserNode to = new UserNode(user.getUid());
		    				
		    				CrossDepToUserData transferData = new CrossDepToUserData();
		    				
		    				transferData.setBatchUuid(batchUuid);
		    				transferData.setUserDept(user.getDepartment());
		    				transferData.setDatas(new String[]{data.getUid()});
		    				
		    				dataManagers.transfer(from, to, transferData);
	    				}
	    				
	    			} else { //如果是不可获取的，禁止他做除了填小计外的其他操作
	    				json.put("disableEdit", true);
	    			}
	    			
	    			json.put("isFromData", true);

	    			boolean f = brokerService.sendToUser("/user",jsonObject.optString("agent_id"),json.toString());
	    			logger.info("1.弹屏消息为："+json.toString()+ " 发送给 "+jsonObject.optString("agent_id")+" 结果为："+ f +"");
	    		} else {	//理论上讲这里应该是直接添加到某一个默认批次中，这里还没有实现，还有一个地方也需要，就是在主动呼出时
	    			
	    			//CallManager.pauseCall(jsonObject.optString("agent_id"), "呼入弹屏");
	    			boolean f = brokerService.sendToUser("/user",jsonObject.optString("agent_id"),json.toString());
	    			logger.info("2.弹屏消息为："+json.toString()+ " 发送给 "+jsonObject.optString("agent_id")+" 结果为："+ f +"");
	    		}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	private boolean isGetableFromDept(DataBatchData data) {
		if (StringUtils.isNotBlank(data.getOwnDepartment()) && !"global_share".equals(data.getOwnDepartment()) && 
				StringUtils.isBlank(data.getOwnUser()) && StringUtils.isBlank(data.getIntentType()) && "0".equals(data.getIsLock()) && 
				"0".equals(data.getIsAbandon()) && "0".equals(data.getIsBlacklist()) && "0".equals(data.getIsFrozen())) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isGetableFromBatch(DataBatchData data) {
		if (StringUtils.isBlank(data.getOwnDepartment()) && StringUtils.isBlank(data.getOwnUser()) && 
				StringUtils.isBlank(data.getIntentType()) && "0".equals(data.getIsLock()) && 
				"0".equals(data.getIsAbandon()) && "0".equals(data.getIsBlacklist()) && "0".equals(data.getIsFrozen())) {
			return true;
		} else {
			return false;
		}
	}


}
