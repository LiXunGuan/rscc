package com.ruishengtech.rscc.crm.ui.mw.event.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.rscc.crm.billing.manager.RateManager;
import com.ruishengtech.rscc.crm.billing.model.BillRate;
import com.ruishengtech.rscc.crm.billing.model.Billing;
import com.ruishengtech.rscc.crm.billing.service.imp.BillServiceImp;
import com.ruishengtech.rscc.crm.data.model.DataTask;
import com.ruishengtech.rscc.crm.data.service.DataTaskService;
import com.ruishengtech.rscc.crm.ui.mw.event.IEventHandler;
import com.ruishengtech.rscc.crm.ui.mw.manager.CallHistoryManager;
import com.ruishengtech.rscc.crm.ui.mw.model.CallLog;
import com.ruishengtech.rscc.crm.ui.mw.service.CallLogService;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;

/**
 * Created by yaoliceng on 2015/6/16.
 */
public class CdrHandler implements IEventHandler {

	//日志记录器
	private static final Logger logger = Logger.getLogger(CdrHandler.class);
	
    @Override
    public void handler(JSONObject jsonObject) {
    	
    	SysConfigService sysConfigService = ApplicationHelper.getApplicationContext().getBean(SysConfigService.class);
    	
    	//CDR计费  是否计费
    	if("true".equals(sysConfigService.getSysConfigByKey("sys.billing").getSysVal()) && jsonObject.optInt("bridgesec") > 0) {	//通话时长大于0时再计费,群呼的计费不在这里
    		Billing bill = new Billing(jsonObject);
    		if (!StringUtils.isBlank(bill.getCallSessionUuid())) {
    			BillServiceImp billService = ApplicationHelper.getApplicationContext().getBean(BillServiceImp.class);
    			BillRate rate;
	    		if (jsonObject.optString("user_field").startsWith("gcall"))
	    			rate = RateManager.getInstance().getGroupRate(bill.getCaller());
	    		else
	    			rate = RateManager.getInstance().getAgentRate(bill.getExten());
	    		bill.setRate(rate.getRateSdfMoney()+"元/"+rate.getRateSdfTime()+"秒");
	    		bill.calcCost(rate.getRateSdfMoney(), rate.getRateSdfTime());
	    		billService.save(bill);
    		}
    	}
    	
    	if(jsonObject.optString("user_field").startsWith("dataCall")){
    		logger.info("dataCall");
    		CallLogService callLogService = ApplicationHelper.getApplicationContext().getBean(CallLogService.class);
	    	DataTaskService dataTaskService = ApplicationHelper.getApplicationContext().getBean(DataTaskService.class);
	    	CallLog temp = callLogService.getCallLogBySession(jsonObject.getString("call_session_uuid"));
	    	temp.setTalk_time(jsonObject.getInt("bridgesec"));
	    	temp.setRecord_path(jsonObject.getString("record_file"));
	    	callLogService.updateCDR(temp);
	    	//先取数据库，没有再取缓存，因为要加1
	    	DataTask dataTask = CallHistoryManager.get(temp.getAgent_name(), temp.getData_id());
			if (dataTask==null){
				dataTask = dataTaskService.getByUuid(temp.getAgent_id(), UUID.UUIDFromString(temp.getData_id()));
			}
			if (dataTask !=null && StringUtils.isBlank(dataTask.getMoveTo()))
				dataTaskService.addCallTimes(temp.getAgent_id(), temp.getData_id());
			//加到呼叫列表，可能需要修改一下位置s
			CallHistoryManager.put(temp.getAgent_name(), dataTask);
    	}else if("single_call".equals(jsonObject.optString("user_field"))){
    		logger.info("single_call");
    		CallLogService callLogService = ApplicationHelper.getApplicationContext().getBean(CallLogService.class);
    		CallLog temp = callLogService.getCallLogBySession(jsonObject.getString("call_session_uuid"));
    		if(null != temp){
	    		temp.setTalk_time(jsonObject.getInt("bridgesec"));
	    		temp.setRecord_path(jsonObject.getString("record_file"));
	    		callLogService.updateCDR(temp);
    		}
    	}else{
    		
    		CallLogService callLogService = ApplicationHelper.getApplicationContext().getBean(CallLogService.class);
    		CallLog temp = callLogService.getCallLogBySession(jsonObject.getString("call_session_uuid"));
    		if(null != temp){
    			temp.setTalk_time(jsonObject.getInt("bridgesec"));
    			temp.setRecord_path(jsonObject.getString("record_file"));
    			callLogService.updateCDR(temp);
    		}
    	}
    }

}
