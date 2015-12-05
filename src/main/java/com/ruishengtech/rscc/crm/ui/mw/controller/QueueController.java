package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.IOException;
import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;





import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.rscc.crm.billing.manager.RateManager;
import com.ruishengtech.rscc.crm.billing.model.BillRate;
import com.ruishengtech.rscc.crm.billing.model.BillRateSkillGroupLink;
import com.ruishengtech.rscc.crm.billing.service.BillRateService;
import com.ruishengtech.rscc.crm.ui.mw.condition.QueueCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.FSQueue;
import com.ruishengtech.rscc.crm.ui.mw.model.MWAccessnumberRoute;
import com.ruishengtech.rscc.crm.ui.mw.model.MWAgent;
import com.ruishengtech.rscc.crm.ui.mw.model.MWAgentQueue;
import com.ruishengtech.rscc.crm.ui.mw.model.MWExtenRoute;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.model.UploadingVoice;
import com.ruishengtech.rscc.crm.ui.mw.service.FSQueueService;
import com.ruishengtech.rscc.crm.ui.mw.service.MWAccessnumberRputeService;
import com.ruishengtech.rscc.crm.ui.mw.service.MWExtenRouteService;
import com.ruishengtech.rscc.crm.ui.mw.service.UploadingVoiceService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("config/queue")
public class QueueController {
	
	public static  Boolean isApply=false;

	@Autowired
	private FSQueueService fsQueueService;
	
	@Autowired
	private MWExtenRouteService extenRouteService;
	
	@Autowired
	private UploadingVoiceService uploadingVoiceService;
	
	@Autowired
	private MWAccessnumberRputeService mwAccessnumberRputeService;
	
	@Autowired
	private BillRateService brService;

	@RequestMapping
	public String Batch(HttpServletRequest request,
			HttpServletResponse response, Model model, String str) {
		
		//{ring-all=ring-all, longest-idle-agent=longest-idle-agent,
		model.addAttribute("strategySele", FSQueue.STRATEGYSELE);
		model.addAttribute("isApply", isApply);
		
		model.addAttribute("iframecontent","config/queue");
		return "iframe";
		
//		return "config/queue";
	}

	/**
	 * 数据请求
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("data")
	@ResponseBody
	public String data(HttpServletRequest request,
			HttpServletResponse response, QueueCondition queueCondition) {

		queueCondition.setRequest(request);

		PageResult<FSQueue> pageResult = fsQueueService.page(queueCondition);		//获取符合查询条件的数据
		List<FSQueue> list = (List<FSQueue>) pageResult.getRet();
		
		JSONArray jsonArray = new JSONArray();
		
		List<UploadingVoice> voices = uploadingVoiceService.getAllVoices();
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < list.size(); i++) {	
			JSONObject jsob = new JSONObject(list.get(i).getConfig());
			jsob.put("id", list.get(i).getId());
			jsob.put("name", StringUtils.trimToEmpty(list.get(i).getName()));
			jsob.put("isStatic", "0".equals(StringUtils.trimToEmpty(list.get(i).getIs_static())) ? "" : "是" );
			jsob.put("staticqueue", StringUtils.trimToEmpty(list.get(i).getIs_static()));

			jsob.put("strategy",StringUtils.trimToEmpty(FSQueue.STRATEGYSELE.get(jsob.get("strategy"))) );
			//如果语音资源不为空
	         if(voices != null){
	        	 for (UploadingVoice uv : voices) {
	        		 //如果语音资源中的voice和fsqueue中的config中的moh-sound的值一致的话，使用语音资源中的资源名称，否则的话使用电脑上保存的资源文件名
	        		 if(uv.getVoice().equals(StringUtils.trimToEmpty(jsob.get("moh-sound").toString()))){
	        			 jsob.put("mohSound", uv.getName());
	        			 break;
	        		 }else{
	        			 jsob.put("mohSound",StringUtils.trimToEmpty(jsob.get("moh-sound").toString()).substring(jsob.get("moh-sound").toString().lastIndexOf("/")+1, jsob.get("moh-sound").toString().length()));
	    	         }
	        	 }
	         }else{
    			 jsob.put("mohSound",StringUtils.trimToEmpty(jsob.get("moh-sound").toString()).substring(jsob.get("moh-sound").toString().lastIndexOf("/")+1, jsob.get("moh-sound").toString().length()));
	         }
			
			jsob.put("timeBaseScore",StringUtils.trimToEmpty(jsob.get("time-base-score").toString()));
			jsob.put("maxWaitTime",StringUtils.trimToEmpty(jsob.get("max-wait-time").toString()));
			jsob.put("maxWaitTimeWithNoAgent",StringUtils.trimToEmpty(jsob.get("max-wait-time-with-no-agent").toString()));
			jsob.put("maxWaitTimeWithNoAgentTimeReached",StringUtils.trimToEmpty(jsob.get("max-wait-time-with-no-agent-time-reached").toString()));
			jsob.put("tierRulesApply",StringUtils.trimToEmpty(jsob.get("tier-rules-apply").toString()));
			jsob.put("tierRuleWaitSecond",StringUtils.trimToEmpty(jsob.get("tier-rule-wait-second").toString()));
			jsob.put("tierRuleWaitMultiplyLevel",StringUtils.trimToEmpty(jsob.get("tier-rule-wait-multiply-level").toString()));
			jsob.put("tierRuleNoAgentNoWait",StringUtils.trimToEmpty(jsob.get("tier-rule-no-agent-no-wait").toString()));
			jsob.put("discardAbandonedAfter",StringUtils.trimToEmpty(jsob.get("discard-abandoned-after").toString()));
			jsob.put("abandonedResumeAllowed",StringUtils.trimToEmpty(jsob.get("abandoned-resume-allowed").toString()));
			
			jsob.put("extension", StringUtils.trimToEmpty(list.get(i).getExtension()));
			
			jsonArray.put(jsob);
		}

		jsonObject.put("aaData", jsonArray);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords",pageResult.getiTotalDisplayRecords());
		return jsonObject.toString();

	}
	
	 /**
     * 加载标题
     */
    @RequestMapping("menushow")
    @ResponseBody
    public String menushow(HttpServletRequest request, HttpServletResponse response, MWFsHost mwFsHost)
            throws IOException {
        JSONObject jsob = new JSONObject();
        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>呼叫中心配置&nbsp;&nbsp;>&nbsp;<span>技能组管理</span>");
        return jsob.toString();
    }

    /**
     * 技能组管理--点击右上角新增
     * @param request
     * @param response
     * @param id
     * @param model
     * @return
     * @throws SocketException
     */
	@RequestMapping("get")
	public String getScopExt(HttpServletRequest request,
			HttpServletResponse response, Long id, Model model)
			throws SocketException {
         if (id != null) {
        	FSQueue item = fsQueueService.get(id);
        	JSONObject js = new JSONObject(item.getConfig());
        	model.addAttribute("id", item.getId());
        	model.addAttribute("is_static", item.getIs_static());
 	        model.addAttribute("name", StringUtils.trimToEmpty(item.getName()));
 	        model.addAttribute("strategy", js.optString("strategy", ""));
 	        model.addAttribute("welcomeMusic", js.optString("welcome-music", ""));
 	        model.addAttribute("mohSound", js.optString("moh-sound", ""));
 	        model.addAttribute("timeBaseScore", js.optString("time-base-score", ""));
 	        model.addAttribute("maxWaitTime", js.optString("max-wait-time", ""));
 	        model.addAttribute("maxWaitTimeWithNoAgent", js.optString("max-wait-time-with-no-agent", ""));
 	        model.addAttribute("maxWaitTimeWithNoAgentTimeReached", js.optString("max-wait-time-with-no-agent-time-reached", ""));
 	        model.addAttribute("tierRulesApply", js.optString("tier-rules-apply", ""));
 	        model.addAttribute("tierRuleWaitSecond", js.optString("tier-rule-wait-second", ""));
 	        model.addAttribute("tierRuleWaitMultiplyLevel", js.optString("tier-rule-wait-multiply-level", ""));
 	        model.addAttribute("tierRuleNoAgentNoWait", js.optString("tier-rule-no-agent-no-wait", ""));
 	        model.addAttribute("discardAbandonedAfter", js.optString("discard-abandoned-after", ""));
 	        model.addAttribute("abandonedResumeAllowed", js.optString("abandoned-resume-allowed", ""));
 	        model.addAttribute("agentWaitTime", js.optString("agentWaitTime", ""));
 	        // 连表查询此技能组关联的分机号
 	       List<MWExtenRoute> ext = extenRouteService.getExtenRoute(MWExtenRoute.ROUTER_TYPE_CALLCENTER, id);
 	       if(ext != null){
 	    	   for (MWExtenRoute er : ext) {
 	    		   model.addAttribute("extensionId", er.getId());
 	    		   model.addAttribute("extension", er.getExtension());
 	    	   }
 	       }
        }
         model.addAttribute("strategySele", FSQueue.STRATEGYSELE);
         //查询全部费率用于新增
         List<BillRate> brs = brService.getAllBillRate();
         model.addAttribute("allrate", brs);
         List<BillRateSkillGroupLink> bs = brService.getBillSkillGroup();
         BillRate billr = new BillRate();
         if(id != null){
	         for(BillRateSkillGroupLink bsg : bs){
	        	 String[] names = bsg.getSkillGroupUUID().split("#");
	        	 if(names.length > 0 ){
	        		 if(names[0].equals(id.toString())){
	 	        		billr = brService.getBillRateByUUID(UUID.UUIDFromString(bsg.getBillrateSkillUUID().split("#")[0]));
	 	        		
	 	        	 }
	        	 }
	        	
	         }
         }
         //保存此技能组对应的费率对象
         model.addAttribute("thisrate", billr);
         //修改等待音乐
         List<UploadingVoice> voices = uploadingVoiceService.getAllVoices();
         if(voices != null){
        	 for (UploadingVoice uv : voices) {
        		 uv.setVoice(uv.getVoice());
        	 }
        	 model.addAttribute("voices", voices);
         }
        return "config/queue_save";
	}

	@RequestMapping("addItem")
	public String addItem(HttpServletRequest request,
			HttpServletResponse response, Long id, Model model)
			throws SocketException {
         List<MWAgent> agentls=fsQueueService.getAll(MWAgent.class);	//查询出所有人员
         List<MWAgentQueue> agentQueuels=fsQueueService.getmwAgentQueue(id);	//查询出当前技能组所拥有的人员人员
         List<MWAgent> agentCheckls =new ArrayList<MWAgent>();		//用来保存当前技能组人员
         if (agentls!=null && agentQueuels!=null) {
        	 for (int i = 0; i < agentls.size(); i++) {
            	 
            	 for (int j = 0; j < agentQueuels.size(); j++) {
            		 
            		 if (agentls.get(i).getUid().equals(agentQueuels.get(j).getAgentUid())) {	
            			agentCheckls.add(agentls.get(i));
              			agentls.remove(i);	//在所有人员中删除当前技能组人员
              			i=i-1;
              			break;
              		}
    			}
    		}
		}
        
        if(agentls != null){
        	for (MWAgent mw : agentls) {
        		mw.setInfo(mw.getUid() + "_" + mw.getInfo());
        	}
        }
        if(agentCheckls != null){
        	for(MWAgent mw : agentCheckls) {
        		mw.setInfo(mw.getUid() + "_" + mw.getInfo());
        	}
        }
         
         model.addAttribute("id", id);
         model.addAttribute("agentls", agentls);
         model.addAttribute("agentCheckls", agentCheckls);
         
        return "config/agent_queue";
	}

	
	/**
	 * 验证
	 * @param request
	 * @param response
	 * @param name
	 * @param id
	 * @throws IOException
	 */
	@RequestMapping("isRepeat")
	@ResponseBody
	public void isRepeat(HttpServletRequest request,
			HttpServletResponse response, String name, Long id)
			throws IOException {


		FSQueue queue=fsQueueService.getFSQueue(StringUtils.trimToEmpty(name));
		
		if (queue==null) {
			response.getWriter().print(true); // 直接打印true 通过
		}else{
			if (id==null) {
				response.getWriter().print(false);
			}else{
				if (id.equals(queue.getId())) {
					response.getWriter().print(true);
				}else{
					response.getWriter().print(false);
				}
			}
		}

	}
	
	@RequestMapping("checkExten")
	@ResponseBody
	public void checkExten(HttpServletRequest request, HttpServletResponse response,String extension, Long id)
			throws IOException {
		
		MWExtenRoute extrou = extenRouteService.getExten(extension);
		
		if(extrou == null){
			response.getWriter().print(true);
		}else {
			if(id == null) { // 添加
				response.getWriter().print(false);
			}else { // 修改
				List<MWExtenRoute> ext = extenRouteService.getExtenRoute(MWExtenRoute.ROUTER_TYPE_CALLCENTER, extrou.getDestId());
				if(ext != null) { 
					response.getWriter().print(true);
				}else{
					response.getWriter().print(false);
				}
			}
		}
	}


    @Resource(name = "propertyConfigurer")
    private SpringPropertiesUtil propertiesUtil;

	/**
	 * 保存
	 * @param request
	 * @param fsGateWay
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request, FSQueue fsQueue, Long extensionId) throws ParseException {
		fsQueue.setTimeBaseScore(request.getParameter("timeBaseScore"));
		JSONObject jsob = new JSONObject();
		jsob.put("strategy", StringUtils.trimToEmpty(fsQueue.getStrategy()));
		jsob.put("moh-sound",StringUtils.isNotBlank(fsQueue.getMohSound()) ? StringUtils.trimToEmpty(fsQueue.getMohSound()) : "/usr/local/freeswitch/sounds/moh/aw.wav");
		jsob.put("welcome-music",StringUtils.trimToEmpty(fsQueue.getWelcomeMusic()));
		jsob.put("time-base-score",StringUtils.trimToEmpty(fsQueue.getTimeBaseScore()));
		jsob.put("max-wait-time",StringUtils.trimToEmpty(fsQueue.getMaxWaitTime()) );
		jsob.put("max-wait-time-with-no-agent",StringUtils.trimToEmpty(fsQueue.getMaxWaitTimeWithNoAgent()));
		jsob.put("max-wait-time-with-no-agent-time-reached",StringUtils.trimToEmpty(fsQueue.getMaxWaitTimeWithNoAgentTimeReached()));
		jsob.put("tier-rules-apply",StringUtils.trimToEmpty(fsQueue.getTierRulesApply()));
		jsob.put("tier-rule-wait-second",StringUtils.trimToEmpty(fsQueue.getTierRuleWaitSecond()));
		jsob.put("tier-rule-wait-multiply-level",StringUtils.trimToEmpty(fsQueue.getTierRuleWaitMultiplyLevel()));
		jsob.put("tier-rule-no-agent-no-wait",StringUtils.trimToEmpty(fsQueue.getTierRuleNoAgentNoWait()));
		jsob.put("discard-abandoned-after",StringUtils.trimToEmpty(fsQueue.getDiscardAbandonedAfter()));
		jsob.put("abandoned-resume-allowed",StringUtils.trimToEmpty(fsQueue.getAbandonedResumeAllowed()));
		jsob.put("agentWaitTime",StringUtils.trimToEmpty(fsQueue.getAgentWaitTime()));

		fsQueue.setConfig(jsob.toString());
		
		if (fsQueue.getIs_static() == null) {
			fsQueue.setIs_static("0");
		}else{
			fsQueue.setIs_static("1");
		}
		
		fsQueueService.saveOrUpdate(fsQueue);
		
		MWExtenRoute ext = null;
		if(extensionId != null){
			ext = extenRouteService.get(extensionId);
			ext.setExtension(fsQueue.getExtension());
			extenRouteService.saveOrUpdate(ext);
		}else{
			
			//查询MWExtenRoute表 是否已经存在该技能组关联的分机
//			ext = extenRouteService.getExten(fsQueue.getExtension().toString());
//			if (ext == null) {
				
				// 保存一个技能组关联分机号
				MWExtenRoute extrenRoute = new MWExtenRoute();
				extrenRoute.setName("技能组" + fsQueue.getName());
				extrenRoute.setExtension(fsQueue.getExtension());
				extrenRoute.setType(MWExtenRoute.ROUTER_TYPE_CALLCENTER);
				extrenRoute.setDestId(fsQueue.getId());
				extrenRoute.setDestString(fsQueue.getName());
				extrenRoute.setCan_del(1);
				extenRouteService.saveOrUpdate(extrenRoute);

//			}
		}
		
    	ExtenRouteController.isApply = true;
		
		isApply=true;
		
		//如果设置了费率，那么id要保存到费率和技能组关联表里面去
		if(fsQueue.getId() != null){
			if(StringUtils.isNotBlank(request.getParameter("rate"))){
				brService.deleteRateSkillGroupById(null, (fsQueue.getId()+"#"+fsQueue.getName()).toString());
				brService.insertRateSkill(request.getParameter("rate"), (fsQueue.getId()+"#"+fsQueue.getName()).toString());
				RateManager.getInstance().refresh();
			}else if("".equals(request.getParameter("rate"))){
				brService.deleteRateSkillGroupById(null, (fsQueue.getId()+"#"+fsQueue.getName()).toString());
			}
		}
		
		// 调用应用
		deploy(request);
		
		return new JSONObject().put("success", true).toString();
	}
	

	/**
	 * 保存
	 * @param request
	 * @param fsGateWay
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("saveaq")
	@ResponseBody
	public String saveAQ(HttpServletRequest request, Long id) throws ParseException {
		String[] ls=request.getParameterValues("agentUid");

        FSQueue fsqueue = fsQueueService.getById(FSQueue.class, id);

        fsQueueService.removeBind(fsqueue);
        fsQueueService.removMWAgentQueues(id);

		if (ls!=null) {
			for (int i = 0; i < ls.length; i++) {
				MWAgentQueue mwAgentQueue =new MWAgentQueue();
					mwAgentQueue.setAgentUid(ls[i]);
					mwAgentQueue.setQueueId(id);
					mwAgentQueue.setLevel("1");
					mwAgentQueue.setPosition("1");
					fsQueueService.save(mwAgentQueue);
					fsQueueService.bind(fsqueue, mwAgentQueue);
			}
		}
//		isApply=true;
		return new JSONObject().put("success", true).toString();
	}







	/**
	 * 应用
	 * @param request
	 * @param mwGateWay
	 * @return
	 */
	@RequestMapping("deploy")
	@ResponseBody
	public String deploy(HttpServletRequest request) {
		fsQueueService.toFSXML();
		fsQueueService.deploy();
		isApply=false;
		// 调用分机号管理应用
    	extenRouteService.deploy();
    	ExtenRouteController.isApply = false;
		return new JSONObject().put("success", true).toString();
	}

	/**
	 * 删除
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("remove")
	@ResponseBody
	public String remove(HttpServletRequest request, Long id) {
		fsQueueService.removMWAgentQueues(id);
		fsQueueService.remove(id);
		fsQueueService.removeMWRT(id);
		brService.deleteRateSkillGroupById(null, String.valueOf(id));
		//(新加的逻辑)----查询此技能组相关的分级号并删除分机号
		List<MWExtenRoute> extens = extenRouteService.getExtenRoute(MWExtenRoute.ROUTER_TYPE_CALLCENTER, id);
		if(extens != null){
			for (MWExtenRoute exten : extens) {
				//查询此分机号对应的相关的接入号路由信息
				List<MWAccessnumberRoute> anumers = mwAccessnumberRputeService.getAccessNumberRouteByExtenId(exten.getId());
				if(anumers != null){
					for (MWAccessnumberRoute mwar : anumers) {
						mwAccessnumberRputeService.remove(mwar.getId());
					}
				}
				extenRouteService.remove(exten.getId());
			}
		}
		
		isApply=true;
		
		// 调用应用
		deploy(request);
		
		return new JSONObject().put("success", true).toString();
	}




}
