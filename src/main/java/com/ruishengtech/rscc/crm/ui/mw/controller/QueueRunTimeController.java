package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruishengtech.rscc.crm.ui.mw.service.QueueStatusManagerService;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.mw.condition.QueueRunTimeCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.Domains;
import com.ruishengtech.rscc.crm.ui.mw.model.Members;
import com.ruishengtech.rscc.crm.ui.mw.model.RtAgentStatus;
import com.ruishengtech.rscc.crm.ui.mw.model.RtQueueStatus;
import com.ruishengtech.rscc.crm.ui.mw.send.QueueManager;
import com.ruishengtech.rscc.crm.ui.mw.service.GetMembersService;
import com.ruishengtech.rscc.crm.ui.mw.service.GetQueueRunTimeService;
import com.ruishengtech.rscc.crm.ui.mw.service.QueueRunTimeService;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * 
 * @author chengxin
 *
 */
@Controller
@RequestMapping("runtime/queueruntime")
public class QueueRunTimeController {
	
	@Autowired
	private QueueRunTimeService queueRunTimeService;
	
	@Autowired
	private GetMembersService getMembersService;
	
	@Autowired
	private GetQueueRunTimeService getQueueRunTimeService;
  
	@Autowired
	private UserService userService;


    @Autowired
    private QueueStatusManagerService queueStatusManagerService;

    @RequestMapping
    public String Batch(HttpServletRequest request, HttpServletResponse response,Model model,String str) {
    	model.addAttribute("iframecontent","runtime/queueruntime");
		return "iframe";
//        return "runtime/queueruntime";
    }
    @RequestMapping("get")
    public String get(HttpServletRequest request, HttpServletResponse response,Model model,String str) {
    	User user = (User) SessionUtil.getCurrentUser(request);
    	JSONArray ja = QueueManager.getAllQueue();
    	HashMap<String, String> queues = new HashMap<String, String>();
    	for (int i = 0 ; i < ja.length() ; i++ ) {
    		if(ja.getJSONObject(i).opt("queue_id") == null){
    			continue;
    		}
    		if(userService.hasDatarange(user.getUid(), "queue", ja.getJSONObject(i).getInt("queue_id")+"") || "1".equals(user.getAdminFlag())){
    			queues.put(ja.getJSONObject(i).getInt("queue_id")+"", ja.getJSONObject(i).getString("queue_name"));
    		}
		}
		model.addAttribute("queues", queues);
		
		model.addAttribute("iframecontent","runtime/queueruntime_get");
		return "iframe";
		
		
//        return "runtime/queueruntime_get";
    }
    
    /**
     * 数据请求
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("getmembers")
    @ResponseBody
    public String getMembers(HttpServletRequest request, HttpServletResponse response, QueueRunTimeCondition queueRunTimeCondition) {
    	queueRunTimeCondition.setRequest(request);
        PageResult<Members> pageResult = getMembersService.queryPage(queueRunTimeCondition);	
        List<Members> list = (List<Members>) pageResult.getRet();

        JSONArray jsonArray = new JSONArray();	
        JSONObject jsonObject = new JSONObject();
        
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsob = new JSONObject(list.get(i));
            Date now=new Date();
            jsob.put("joined_epoch", (now.getTime()/1000-list.get(i).getJoined_epoch()));
            jsob.put("number", (i+1));
            
            jsonArray.put(jsob);
        }

        jsonObject.put("aaData", jsonArray);
        jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
        jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
        return jsonObject.toString();

    }
    
    /**
     * 数据请求
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("getqueue")
    @ResponseBody
    public String getqueue(HttpServletRequest request,
                       HttpServletResponse response, QueueRunTimeCondition queueRunTimeCondition) {

        queueStatusManagerService.updateQueueStatus(queueRunTimeCondition);

        queueRunTimeCondition.setRequest(request);
        PageResult<RtAgentStatus> pageResult = getQueueRunTimeService.queryPage(queueRunTimeCondition);	//获取符合条件的数据
        
        List<RtAgentStatus> list = (List<RtAgentStatus>) pageResult.getRet();
       

        JSONArray jsonArray = new JSONArray();	//将数据放入json
        JSONObject jsonObject = new JSONObject();
        
        if (list.size()>0) {
        	for (int i = 0; i < list.size(); i++) {
                JSONObject jsob = new JSONObject(list.get(i));
                if (list.get(i).getUp_time()!=null) {
                	 Date now=new Date();
                     jsob.put("up_time",StringUtils.trimToEmpty(String.valueOf(now.getTime()/1000-list.get(i).getUp_time().getTime()/1000)));
				}else{
					jsob.put("up_time","");
				}
                jsob.put("status", StringUtils.trimToEmpty(Domains.AGENTSTATUS.get(list.get(i).getStatus())));
                jsob.put("number", StringUtils.trimToEmpty(list.get(i).getNumber()));
                jsob.put("state", StringUtils.trimToEmpty(Domains.EXTENSTASTUS.get(list.get(i).getState())));
                jsob.put("in_count", StringUtils.trimToEmpty(String.valueOf(list.get(i).getIn_count().equals("0")?list.get(i).getIn_count():"")));
                jsob.put("miss_count", StringUtils.trimToEmpty(String.valueOf(list.get(i).getMiss_count().equals("0")?list.get(i).getMiss_count():"")));
                jsonArray.put(jsob);
            }
		}
        

        jsonObject.put("aaData", jsonArray);
        jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
        jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
        
        /**
         * 放入额外的信息
         */
        PageResult<RtQueueStatus> result = queueRunTimeService.queryPage(queueRunTimeCondition);	//获取符合条件的数据
        List<RtQueueStatus> ls = (List<RtQueueStatus>) result.getRet();
        JSONObject jsonQueue = new JSONObject();
        if (ls.size()>0) {
        	RtQueueStatus queueStatus=ls.get(0);
        	jsonQueue.put("in_answer_count", queueStatus.getIn_answer_count());
        	jsonQueue.put("in_count", queueStatus.getIn_count());
        	jsonQueue.put("pick_up_rate",  Math.round(Double.valueOf(queueStatus.getIn_answer_count())/Double.valueOf(queueStatus.getIn_count())*100)+"%");
        	jsonQueue.put("misscount", Math.round(Double.valueOf(queueStatus.getRingring_time())/Double.valueOf(queueStatus.getIn_answer_count())/1000));
        	jsonQueue.put("member_count",StringUtils.trimToEmpty(String.valueOf(queueStatus.getMember_count())));
        	jsonQueue.put("busy_ready_count",StringUtils.trimToEmpty(String.valueOf(queueStatus.getBusy_ready_count())));
        	jsonQueue.put("idle_ready_count",StringUtils.trimToEmpty(String.valueOf(queueStatus.getIdle_ready_count())));
        	jsonQueue.put("not_ready_count",StringUtils.trimToEmpty(String.valueOf(queueStatus.getNot_ready_count())));
        	jsonQueue.put("offline_count",StringUtils.trimToEmpty(String.valueOf(queueStatus.getOffline_count())));
        	jsonObject.put("jsonQueue", jsonQueue);
		}else{
			jsonQueue.put("pick_up_rate", "0%");
			jsonQueue.put("misscount", 0);
			jsonQueue.put("member_count", 0);
			jsonQueue.put("busy_ready_count",0);
			jsonQueue.put("idle_ready_count",0);
			jsonQueue.put("not_ready_count",0);
			jsonQueue.put("offline_count",0);
			jsonObject.put("jsonQueue", jsonQueue);
		}
        return jsonObject.toString();
    }

//    @RequestMapping("tospy")
//    public String tospy(HttpServletRequest request, HttpServletResponse response, String uuid,Model model)
//            throws IOException {
//        String adminsip = (String) request.getSession().getAttribute("adminsip");
//        if(StringUtils.isNotBlank(adminsip)){
//            eavesdropforadmin(request,response,uuid,adminsip);
//            return "";
//        }
//        model.addAttribute("uuid",uuid);
//        return "spy";
//    }
//    
//    
//    @RequestMapping("tokill")
//    @ResponseBody
//    public String tokill(HttpServletRequest request, HttpServletResponse response, String uuid,Model model){
//        agentService.killByChannal(uuid);
//        return new JSONObject().put("success", true).toString();
//    }
//    
//    
//    @RequestMapping("eavesdropforadmin")
//    @ResponseBody
//    public String eavesdropforadmin(HttpServletRequest request, HttpServletResponse response, String uuid,String sip) {
//        if(StringUtils.isBlank(sip)){
//            sip = (String) request.getSession().getAttribute("adminsip");
//        }else{
//            request.getSession().setAttribute("adminsip",sip);
//        }
//        try {
//            agentService.eavesdrop(sip, uuid);
//            return new JSONObject().put("success", true).toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new JSONObject().put("success", false).toString();
//        }
//    }
}
