package com.ruishengtech.rscc.crm.ui.record.controller;

import java.io.IOException;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.util.PhoneUtil;
import com.ruishengtech.rscc.crm.neworderinfo.model.NewOrderInfo;
import com.ruishengtech.rscc.crm.neworderinfo.service.NewOrderInfoService;
import com.ruishengtech.rscc.crm.qc.model.QualityCheck;
import com.ruishengtech.rscc.crm.qc.service.QualityCheckService;
import com.ruishengtech.rscc.crm.record.condition.RecordCondition;
import com.ruishengtech.rscc.crm.record.model.AccessNumber;
import com.ruishengtech.rscc.crm.record.model.Record;
import com.ruishengtech.rscc.crm.record.service.RecordService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.mw.event.handler.MissCallEvent;
import com.ruishengtech.rscc.crm.ui.mw.model.MWExtenRoute;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Frank
 *
 */
@Controller
@RequestMapping("record")
public class RecordController {

	@Autowired
	private RecordService recordService;

	@Autowired
	private QualityCheckService checkService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DatarangeService datarangeService;
	
	@Autowired
	private SysConfigService sysService;
	
	@Autowired
	private SysConfigService configService;
	
	@Autowired
	private QualityCheckService qualityCheckService;
	
	@Autowired
	private NewOrderInfoService orderInfoService;

	SimpleDateFormat format=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 首页
	 * 
	 * @return
	 * @throws ParseException 
	 * @throws DataAccessException 
	 */
	@RequestMapping()
	public String recordIndex(HttpServletRequest request,Model model) throws DataAccessException, ParseException {
		
		List<AccessNumber> list = recordService.getAllAccessNumbers();
		model.addAttribute("allAccessNumbers", list);
    	model.addAttribute("callDirections", Record.CALL_DIRECTIONS);
    	model.addAttribute("bridgesecs", Record.PUT_THROUGH);
    	
    	if("agent".equals(request.getParameter("level"))){
			model.addAttribute("agent", "我的录音");
			request.getSession().setAttribute("level", "agent");
		}else{
			model.addAttribute("agent", "录音管理");
			request.getSession().removeAttribute("level");
		};
		
		Date d=new Date();
		model.addAttribute("startTime", d);
		model.addAttribute("endTime", d);
		
		User us = (User) SessionUtil.getCurrentUser(request);
		List<String> roles = userService.getPermissionRoles(us.getUid());
		
		for (String str : roles) {
			if("000001".equals(str)){
				model.addAttribute("manage", true);
			}
		}
		
		model.addAttribute("iframecontent","record/record-index");
		return "iframe";
		
//		return "record/record-index";
	}

	/**
	 * 返回所有数据
	 * 
	 * @return
	 */
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, RecordCondition recordCondition) {

		recordCondition.setRequest(request);
		
		String startTime=request.getParameter("startTime");	
		String endTime=request.getParameter("endTime");
		
		recordCondition.setStime(startTime);
		recordCondition.setEtime(endTime);
		
		if(StringUtils.isNotBlank(recordCondition.getSduration())){
			recordCondition.setSduration(Integer.parseInt(recordCondition.getSduration())*1000 + "");
		}
		if(StringUtils.isNotBlank(recordCondition.getEduration())){
			recordCondition.setEduration(Integer.parseInt(recordCondition.getEduration())*1000 + "");
		}
		if(StringUtils.isNotBlank(recordCondition.getSbillsec())){
			recordCondition.setSbillsec(Integer.parseInt(recordCondition.getSbillsec())*1000 + "");
		}
		if(StringUtils.isNotBlank(recordCondition.getEbillsec())){
			recordCondition.setEbillsec(Integer.parseInt(recordCondition.getEbillsec())*1000 + "");
		}
		
		//查询当前登陆用户
		User user = (User) SessionUtil.getCurrentUser(request);
		recordCondition.setUsername(user.getLoginName());
		recordCondition.setAdminflag(user.getAdminFlag());
		
		if(request.getSession().getAttribute("level") != null){
			recordCondition.setLevel(request.getSession().getAttribute("level").toString());
		}else{
//			JSONArray ja = QueueManager.getAllQueue();
//			List<String> queues = new ArrayList<String>();
//			for (int i = 0 ; i < ja.length() ; i++ ) {
//				if(ja.getJSONObject(i).opt("queue_id") == null){
//					continue;
//				}
//				if(userService.hasDatarange(user.getUid(), "queue", ja.getJSONObject(i).getInt("queue_id")+"")){
//					queues.add(ja.getJSONObject(i).getString("queue_name"));
//				}
//			}
//			queues.add(user.getLoginName());
			recordCondition.setQueuesList(userService.getManagerUsernames(SessionUtil.getCurrentUser(request).getUid()));
		}
		
		PageResult<Record> pageResult = recordService.queryPage(recordCondition);

//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<Record> recordList = pageResult.getRet();

		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		
		
		//是否隐藏号码
		String hiddenPhoneNumber = configService.getSysConfigByKey("hiddenPhoneNumber").getSysVal();
		boolean hasPermission = userService.hasPermission(user.getUid(), "90");

		for (int i = 0; i < recordList.size(); i++) {
			JSONObject json = new JSONObject(recordList.get(i));
			

			//主叫
			if(MWExtenRoute.ROUTER_TYPE_OUTBOUNDNUMBER.equals(recordList.get(i).getCaller_type())){
				
				if("true".equals(hiddenPhoneNumber) && !hasPermission){
					
					json.put("caller_id_number", PhoneUtil.hideNumber(StringUtils.trimToEmpty(recordList.get(i).getCaller_id_number())));
				}else{
					json.put("caller_id_number", StringUtils.trimToEmpty(recordList.get(i).getCaller_id_number()));
				}
				
			}else{
				json.put("caller_id_number", StringUtils.trimToEmpty(recordList.get(i).getCaller_id_number()));
			}
           	
			if(MWExtenRoute.ROUTER_TYPE_OUTBOUNDNUMBER.equals(recordList.get(i).getDest_agent_interface_type())){
				
				if("true".equals(hiddenPhoneNumber) && !hasPermission){
					
					json.put("dest_agent_interface_exten", PhoneUtil.hideNumber(StringUtils.trimToEmpty(recordList.get(i).getDest_number())));
				}else{
					json.put("dest_agent_interface_exten", StringUtils.trimToEmpty(recordList.get(i).getDest_number()));
				}
				
			}else{
           	//被叫
           	json.put("dest_agent_interface_exten", StringUtils.trimToEmpty(recordList.get(i).getDest_number()));
			}

           	//主叫agent_id
           	json.put("caller_agent_id", StringUtils.trimToEmpty(recordList.get(i).getCaller_agent_id()));
           	
           	//被叫agent_id
           	json.put("dest_agent_id", StringUtils.trimToEmpty(recordList.get(i).getDest_agent_id()));
           	
           	//被叫Agent Info
           	json.put("dest_agent_info", StringUtils.trimToEmpty(recordList.get(i).getDest_agent_info()));
           	
           	//开始时间
           	json.put("start_stamp", StringUtils.trimToEmpty(format.format(recordList.get(i).getStart_stamp())));
           	
          	//呼叫方向
          	json.put("call_direction", StringUtils.trimToEmpty(Record.CALL_DIRECTIONS.get(recordList.get(i).getCall_direction())));

            //是否接通
            json.put("put_through", Record.PUT_THROUGH.get(recordList.get(i).getBridgesec()>0?"y":"n"));

            // 通话时长
            json.put("duration", recordList.get(i).getBillsec()%1000!=0?recordList.get(i).getBillsec()/1000+1:recordList.get(i).getBillsec()/1000);
            
            // 接通时长
//          json.put("billsec", recordList.get(i).getBridgesec()%1000!=0?recordList.get(i).getBridgesec()/1000+1:recordList.get(i).getBridgesec()/1000);
            json.put("billsec", recordList.get(i).getBridgesec()%1000!=0?recordList.get(i).getBridgesec()/1000+1:recordList.get(i).getBridgesec()/1000);

            //接入号
            json.put("access_number", StringUtils.trimToEmpty(recordList.get(i).getAccess_number()));
            
            //主叫Agent Info
            json.put("caller_agent_info", StringUtils.trimToEmpty(recordList.get(i).getCaller_agent_info()));
            
//			json.put("end_stamp", dateFormat.format(recordList.get(i).getEnd_stamp()));
			
			json.put("score",StringUtils.isBlank(recordList.get(i).getScore())?"0":recordList.get(i).getScore());
			
			if(request.getSession().getAttribute("level") != null){
				json.put("level", "agent");
			}
			
			array.put(json);
		}

		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());

		return jsonObject.toString();
	}

	/**
	 * 查询单个录音质检对象
	 * @param uid
	 * @param model
	 * @return
	 */
	@RequestMapping("get")
	public String get(String uid, Model model) {
		
		if (StringUtils.isNotBlank(uid)) {
			
			Record record = recordService.getRecordByCallsessionuuid(uid);
//			record.setRecord_file(record.getPlayUrl(SpringPropertiesUtil.getProperty("ngnix")));
			record.setRecord_file(record.getPlayUrl(SysConfigManager.getInstance().getDataMap().get("nginx").getSysVal()));
			model.addAttribute("record", record);
			
			/*查询是否存在打分*/
			QualityCheck check = checkService.getQualityByObjUUID(record.getCall_session_uuid());
			if(null != check){
				model.addAttribute("check", check);
				model.addAttribute("sScore", check.getScore());
			}
		}
		return "record/record-dialog";
	}

	/**
	 * 保存质检对象
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request , QualityCheck check, String uid, String cuid, String oStatus) {

		if (null != check) {
			if("".equals(check.getScore())){
				check.setScore("1");
			}
			//修改质检分数
			if(StringUtils.isNotBlank(cuid)){
				check.setUuid(UUID.UUIDFromString(cuid));
				checkService.saveOrUpdate(check, new String[]{"uuidObj","date","reporter"});
			}else{ //质检新添加分数
				check.setDate(new Date());
				check.setReporter(SessionUtil.getCurrentUser(request).getLoginName());
				checkService.saveOrUpdate(check,null);
			}
			
			if(StringUtils.isNotBlank(oStatus)){
				NewOrderInfo orderinfo = orderInfoService.getOrderByOrderid(check.getUuidObj());
				if(orderinfo != null){
					orderinfo.setOrderStatus(oStatus);
					orderInfoService.saveOrUpdateInfo(orderinfo);
				}
			}
			
			return new JSONObject().put("success", true).toString();
		}
		return new JSONObject().put("success", false).toString();
	}
	
	/**
	 * 导出搜索
	 */
	@RequestMapping("exportSearch")
	@ResponseBody
	public void exportSearch(HttpServletRequest request,HttpServletResponse response, RecordCondition recordCondition)  throws Exception {
		//查询当前登陆用户
		User user = (User) SessionUtil.getCurrentUser(request);
		recordCondition.setUsername(user.getLoginName());
		recordCondition.setAdminflag(user.getAdminFlag());
		
		if(request.getSession().getAttribute("level") != null){
			recordCondition.setLevel(request.getSession().getAttribute("level").toString());
		}else{
//			JSONArray ja = QueueManager.getAllQueue();
//			List<String> queues = new ArrayList<String>();
//			for (int i = 0 ; i < ja.length() ; i++ ) {
//				if(ja.getJSONObject(i).opt("queue_id") == null){
//					continue;
//				}
//				if(userService.hasDatarange(user.getUid(), "queue", ja.getJSONObject(i).getInt("queue_id")+"")){
//					queues.add(ja.getJSONObject(i).getString("queue_name"));
//				}
//			}
//			queues.add(user.getLoginName());
//			recordCondition.setQueuesList(queues);
			recordCondition.setQueuesList(userService.getManagerUsernames(SessionUtil.getCurrentUser(request).getUid()));
		}
		
		response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename="+new String( "通话录音单".getBytes("utf-8"), "ISO8859-1" )+".xls");
		recordService.getExcelCreated(request,response,recordCondition);
	}
	
	/**
	 * 详情
	 * @param request
	 * @param response
	 * @param callsessionuuid
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getdestnumber")
	@ResponseBody
	public String getdestnumber(HttpServletRequest request,
			HttpServletResponse response, String callsessionuuid, Model model)
			throws IOException {
		JSONObject jsob = new JSONObject();
		if (callsessionuuid != null) {
			StringBuilder sb = new StringBuilder();
			List<Record> cdrls = recordService.getRecord(callsessionuuid);
			if (cdrls != null) {
				for (int i = 0; i < cdrls.size(); i++) {//将数据放入json
					Integer billsec = cdrls.get(i).getBillsec()%1000 != 0 ? cdrls.get(i).getBillsec()/1000 + 1 : cdrls.get(i).getBillsec()/1000;
					boolean b = userService.hasPermission(SessionUtil.getCurrentUser(request).getUid(), "90");
					if("true".equals(SysConfigManager.getInstance().getDataMap().get("hiddenPhoneNumber").getSysVal()) && !b){
						sb.append("被叫号码："+PhoneUtil.hideNumber(cdrls.get(i).getCaller_id_number())+"\t\t接通时长："+billsec);
					}else{
						sb.append("被叫号码："+cdrls.get(i).getCaller_id_number()+"\t\t接通时长："+billsec);
					}
				}
			}
			jsob.put("ext", sb.toString()); 
			return jsob.toString();
		}else{
			jsob.put("result", "false");
			return jsob.toString();
		}
	}
	
	/**
	 * 播放
	 * @param request
	 * @param response
	 * @param id
	 * @param recording
	 * @param model
	 * @return
	 * @throws SocketException
	 */
	@RequestMapping("player")
	public String getScopExt(HttpServletRequest request,
			HttpServletResponse response, String callsessionuuid, Model model)
			throws SocketException {
    	try {
			if(callsessionuuid != null) {
				Record record = recordService.getRecordByCallsessionuuid(callsessionuuid);
				SysConfig sys = sysService.getSysConfigByKey("sys.recording.play.config");
				model.addAttribute("playconfig", sys.getSysVal());
				//获得录音文件路径
//				model.addAttribute("urls", record.getPlayUrl(SpringPropertiesUtil.getProperty("ngnix")));
				model.addAttribute("urls", record.getPlayUrl(SysConfigManager.getInstance().getDataMap().get("nginx").getSysVal()));
				
				//存放录音信息播放页面显示
				record.setRecord_file(record.getRecord_file().substring(1));
				model.addAttribute("r", record);
				return "record/record_player";
			}else{
				model.addAttribute("urls", "false");
				return "record/record_player";
			}
		} catch (Exception e) {
			model.addAttribute("urls", "false");
			return "record/record_player";
		}
	}
	
	@RequestMapping("clearMissCalls")
	public void clearMissCalls(HttpServletRequest request, HttpServletResponse response){
		
		//得到当前登陆用户
		User user = (User) SessionUtil.getCurrentUser(request);
		
		//清除漏话数据
		if(MissCallEvent.getMissCallMap() != null){
			if(MissCallEvent.getMissCallMap().containsKey(user.getLoginName())){
				MissCallEvent.getMissCallMap().remove(user.getLoginName());
			}
		}
	}
	
	@RequestMapping("delete")
    @ResponseBody
    public String delete(HttpServletRequest request, String uuid) {
    	
		QualityCheck qc = qualityCheckService.getQualityByObjUUID(uuid);
		
		if(qc != null){
			qualityCheckService.deleteQualityCheckByUuidObj(uuid);
		}
		
		recordService.deleteRecord(uuid);
        
		return new JSONObject().put("success", true).toString();
    }
}
