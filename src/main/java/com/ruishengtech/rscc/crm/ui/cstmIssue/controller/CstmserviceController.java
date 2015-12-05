package com.ruishengtech.rscc.crm.ui.cstmIssue.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.App;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.rscc.crm.cstm.model.CstmPopLog;
import com.ruishengtech.rscc.crm.cstm.service.CustomerDesignService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerPoolService;
import com.ruishengtech.rscc.crm.cstm.service.imp.CstmPopLogService;
import com.ruishengtech.rscc.crm.issue.condition.CstmIssueCondition;
import com.ruishengtech.rscc.crm.issue.model.CstmIssue;
import com.ruishengtech.rscc.crm.issue.model.CstmIssueComments;
import com.ruishengtech.rscc.crm.issue.service.CstmIssueCommonetsService;
import com.ruishengtech.rscc.crm.issue.service.CstmIssueService;
import com.ruishengtech.rscc.crm.issue.solution.CstmIssueSolution;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.cstm.service.CustomerSerialize;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Frank
 *
 */
@Controller
@RequestMapping("cstmservice")
public class CstmserviceController {

	@Autowired
	private CstmIssueService cstmserviceService;
	
	@Autowired
	private CstmIssueCommonetsService commonetsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerDesignService designService;

	@Autowired
	private CustomerSerialize serialize;

	@Autowired
	private CustomerPoolService poolService;
	
	@Autowired
	private CstmPopLogService logService;
	/**
	 * 
	 * @return
	 */
	@RequestMapping
	public String index(Model model,HttpServletRequest request){
		
		model.addAttribute("STATUSMAP", CstmIssue.STATUSMAP);
		if(StringUtils.isNotBlank(App.getDateRange(request))){
			model.addAttribute("levels", App.getDateRange(request));
		}
//		else{
//			model.addAttribute("level", "*****");
//		}
		
		
        model.addAttribute("iframecontent","cstmservice/cstmservice-index");
		return "iframe";
		
//		return "cstmservice/cstmservice-index";
	}
	
	/**
	 * 数据
	 * @param request
	 * @param response
	 * @param cstmserviceCondition
	 * @return
	 */
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request , HttpServletResponse response , CstmIssueCondition cstmserviceCondition){
		
		//获取一个人可管辖的所有用户uuid
		request.setAttribute("manageList", userService.getManagerUsers(SessionUtil.getCurrentUser(request).getUid()));
		cstmserviceCondition.setRequest(request);
		cstmserviceCondition.setCstmserviceAssignee(cstmserviceCondition.getUserName());
		cstmserviceCondition.setUserName(SessionUtil.getCurrentUser(request).getLoginName());
		cstmserviceCondition.setAdminFlag(SessionUtil.getCurrentUser(request).getAdminFlag());
		cstmserviceCondition.setLevels(request.getParameter("levels"));
		PageResult<CstmIssue> pageResult =  cstmserviceService.queryPage(cstmserviceCondition);

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<CstmIssue> cstmservices = pageResult.getRet();

		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		
		for (int i = 0; i < cstmservices.size(); i++) {
			
			JSONObject jsonObject2 = new JSONObject(cstmservices.get(i));
			jsonObject2.put("cstmserviceStartTime", fmt.format(cstmservices.get(i).getCstmserviceStartTime()));
			jsonObject2.put("cstmserviceStatus", CstmIssue.STATUSMAP.get(cstmservices.get(i).getCstmserviceStatus()));
			
			array.put(jsonObject2);
		}
		
		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
		
		return jsonObject.toString();
	}

	
	
	/**
	 * 查询单个caseservice信息
	 * @return
	 */
	@RequestMapping("getCstmserviceInfo")
	public String getCstmserviceInfo(UUID uuid , Model model){
		
		CstmIssue cstmservice = cstmserviceService.getCstmserviceByUUID(uuid);
		
		model.addAttribute("cstmservice", cstmservice);
		
		//查询所有评论
		List<CstmIssueComments> cstmserviceComments = cstmserviceService.getCstmserviceComments(uuid);
		
		if(cstmserviceComments.size() > 0){
			model.addAttribute("comments", cstmserviceComments);
		}
		
		return "cstmservice/cstmservice-detail-info";
	}
	
	
	/**
	 * 
	 * 查询单个信息
	 * @param uid
	 * @param model
	 * @return
	 */
	@RequestMapping("get")
	public String getInfo(String uid , Model model){
		
		model.addAttribute("statusMap", CstmIssue.STATUSMAP);
		
		List<User> userList =  userService.getAllUser();
		model.addAttribute("userList", userList);
		
		if(StringUtils.isNoneBlank(uid)){
			
			CstmIssue cstmservice = cstmserviceService.getCstmserviceByUUID(UUID.UUIDFromString(uid));
			model.addAttribute("cs", cstmservice);
		}
		
		return "cstmservice/cstmservice-dialog";
	}
	
	
	/**
	 * 保存或者修改客服信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request, HttpServletResponse response,CstmIssue cstmservice,String ssName ,BindingResult bindingResult) throws Exception{
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		User user = SessionUtil.getCurrentUser(request);
		
		cstmservice.setCstmserviceReporterUuid(user.getUid());
		cstmservice.setCstmserviceReporter(user.getLoginName());
		cstmservice.setCstmservicePlanEndTime(fmt.parse(request.getParameter("cstmservicePlanEndTimes")));
		cstmservice.setCstmserviceStartTime(new Date());
		cstmservice.setCstmserviceStatus(CstmIssue.STATUS0);
		
		
		//设置 客服事件编号
		cstmservice.setCstmserviceId(serialize.getCstmserviceSerializeId());
		/*设置冗余字段的值*/
		cstmservice.setUserName(ssName);
		
		if(null != cstmservice ){
			cstmserviceService.saveOrUpdate(cstmservice);
		}
		
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 添加评论
	 * @param uid
	 * @return
	 */
	@RequestMapping("comments")
	public String comment(String uid , Model model){
		
		/*如果编号不为空*/
		if(StringUtils.isNotBlank(uid)){
			
			CstmIssue cstmservice = cstmserviceService.getCstmserviceByUUID(UUID.UUIDFromString(uid));
			
			model.addAttribute("cstmser", cstmservice);
			
			//查询这条记录的所有评论
			List<CstmIssueComments> comments =  commonetsService.getCommentsByUid(uid);
			
			model.addAttribute("comments", comments);
			
			return "cstmservice/cstmservice-comment-dialog";
		}
		
		return null;
	}
	
	/**
	 * 跳转到客户服务详细页面
	 * @param request
	 * @param uid
	 * @param model
	 * @return
	 */
	@RequestMapping("detailPage")
	public String detailPage(HttpServletRequest request,String uid,Model model){
		
		if(StringUtils.isNotBlank(uid)){
			
			//查询客户服务详细信息
			CstmIssue cstmservice = cstmserviceService.getCstmserviceByUUID(UUID.UUIDFromString(uid));
			model.addAttribute("cstmservice", cstmservice);
			//查询这条记录的所有评论
			List<CstmIssueComments> comments =  commonetsService.getCommentsByUid(uid);
			model.addAttribute("comments", comments);
			
			/*问题的所有状态*/ 
			model.addAttribute("csStstus", CstmIssue.STATUSMAP);
			
			List<User> users = userService.getAllUser();
//			JSONArray array = new JSONArray(users);
			
			StringBuilder u = new StringBuilder();
			for (User user : users) {
				u.append("'"+user.getUid()+"':'"+user.getLoginName()+"',");
			}
			
//			model.addAttribute("tempuser", cstmservice.getCstmserviceAssignee());
			model.addAttribute("tempuser", cstmservice.getCstmserviceAssignee());
			model.addAttribute("arrays", u);
			
			//存放所有标签
			model.addAttribute("alltags", cstmservice.getCstmserviceAssigneeNote());
			
			model.addAttribute("currentUser", SessionUtil.getCurrentUser(request).getLoginName());
			
			model.addAttribute("planEndTime", cstmservice.getCstmservicePlanEndTime());
			
			return "cstmservice/cstmservice-comment-dialog";
		}
		
		return null;
	}
	
	/**
	 * 改变状态
	 * @param status
	 * @param uid
	 * @return
	 */
	@RequestMapping("changeStatus")
	@ResponseBody
	public String changeStatus(String status,String uid){
		
		CstmIssue cstmservice = cstmserviceService.getCstmserviceByUUID(UUID.UUIDFromString(uid));
		cstmservice.setCstmserviceStatus(status);
		
		/*更新状态*/
		cstmserviceService.saveOrUpdate(cstmservice, new String[] {
				"cstmservice_name", "cstmservice_description","cstmservice_id",
				"cstmservice_assignee", "cstmservice_assignee_note",
				"cstmservice_reporter", "cstmservice_voice_record",
				"cstmservice_starttime", "cstmservice_planendtime",
				"cstmservice_realendtime"});
		
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 添加评论
	 * @param comment
	 * @param uid
	 * @return
	 */
	@RequestMapping("addComments")
	@ResponseBody
	public String addComments(HttpServletRequest request,String comment,String uid){
		
//		CstmIssue cstmservice = cstmserviceService.getCstmserviceByUUID(UUID.UUIDFromString(uid));

		CstmIssueComments comments = new CstmIssueComments();
		comments.setCommentTime(new Date());
		comments.setCommonts(comment);
		comments.setCstmName(SessionUtil.getCurrentUser(request).getLoginName());
		comments.setCstmserviceUUID(uid);
		
		commonetsService.save(comments);
		
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 
	 * 修改客服详细信息方法
	 * @param name 修改的对象名
	 * @param param 修改的值
	 * @return 
	 */
	@RequestMapping("updateDetails")
	@ResponseBody 
	public String updateDetails(String name,String param,String uid){
		
		
		CstmIssue cstmservice = cstmserviceService.getCstmserviceByUUID(UUID.UUIDFromString(uid));
		
		if(StringUtils.isNoneBlank(name) && StringUtils.isNotBlank(param)){
			
			switch (param) {
			case "cstmserviceName": //修改服务名称
				cstmservice.setCstmserviceName(name);
				cstmserviceService.saveOrUpdate(cstmservice, new String[]{"cstmservice_description","cstmservice_id","cstmservice_assignee","cstmservice_assignee_note","cstmservice_reporter","cstmservice_voice_record","cstmservice_starttime","cstmservice_planendtime","cstmservice_realendtime","cstmservice_status"});
				break;
			case "questionDes": //问题描述
				
				cstmservice.setCstmserviceDescription(name);
				cstmserviceService.saveOrUpdate(cstmservice, new String[]{"cstmservice_name","cstmservice_id","cstmservice_assignee","cstmservice_assignee_note","cstmservice_reporter","cstmservice_voice_record","cstmservice_starttime","cstmservice_planendtime","cstmservice_realendtime","cstmservice_status"});
				break;
			case "environment": //环境
				cstmservice.setCstmserviceAssigneeNote(name);
				cstmserviceService.saveOrUpdate(cstmservice, new String[]{"cstmservice_name","cstmservice_id","cstmservice_assignee","cstmservice_description","cstmservice_reporter","cstmservice_voice_record","cstmservice_starttime","cstmservice_planendtime","cstmservice_realendtime","cstmservice_status"});
				break;

			default:
				break;
			}
		}
		
		return new JSONObject().put("success", true).toString();
		
	}
	
	/**
	 * 
	 * 修改指派人
	 * @param request
	 * @param uid
	 * @return
	 */
	@RequestMapping("updateAssignee")
	@ResponseBody
	public String updateAssignee(HttpServletRequest request,String uid){
		
		if(StringUtils.isNotBlank(uid)){
			
			CstmIssue cstmservice = cstmserviceService.getCstmserviceByUUID(UUID.UUIDFromString(uid));
			cstmservice.setCstmserviceAssignee(request.getParameter("value"));
			
			cstmservice.setUserName(userService.getByUuid(UUID.UUIDFromString(cstmservice.getCstmserviceAssignee())).getLoginName());
			
			cstmserviceService.saveOrUpdate(cstmservice, new String[]{"cstmservice_name","cstmservice_description","cstmservice_assignee_note","cstmservice_reporter","cstmservice_voice_record","cstmservice_starttime","cstmservice_planendtime","cstmservice_realendtime","cstmservice_status"});
			
			return new JSONObject().put("success", true).toString();
		}
		
		return new JSONObject().put("success", false).toString();
	}
	
	/**
	 * 
	 * 添加标签
	 * @param request
	 * @param uid
	 * @return
	 */
	@RequestMapping("tagsinput")
	@ResponseBody
	public String tagsinput(String tagsinput,String cstmserviceId,String status){
		
		if(StringUtils.isNotBlank(cstmserviceId)){
			
			CstmIssue cstmservice = cstmserviceService.getCstmserviceByUUID(UUID.UUIDFromString(cstmserviceId));
			
			if(status.equals("0")){
				//删除
				cstmservice.setCstmserviceAssigneeNote(cstmservice.getCstmserviceAssigneeNote().replace(","+tagsinput, ""));
			}else{
				cstmservice.setCstmserviceAssigneeNote((StringUtils.isBlank(cstmservice.getCstmserviceAssigneeNote())?"":cstmservice.getCstmserviceAssigneeNote())+tagsinput+",");
			}
			
			cstmserviceService.saveOrUpdate(cstmservice, new String[] {
					"cstmservice_name", "cstmservice_id",
					"cstmservice_description", "cstmservice_assignee",
					"cstmservice_reporter", "cstmservice_voice_record",
					"user_name", "cstmservice_starttime",
					"cstmservice_planendtime", "cstmservice_realendtime",
					"cstmservice_status" });
			
			return new JSONObject().put("success", true).toString();
		}
		
		return new JSONObject().put("success", false).toString();
	}
	
	
	/**
	 * 
	 * 保存沟通日志
	 * @param cstmPopLog
	 * @return
	 */
	@RequestMapping("savepoplog")
	@ResponseBody
	public String savepoplog(HttpServletRequest request, CstmPopLog cstmPopLog,String customer_id){

		if(null != cstmPopLog){
			
			cstmPopLog.setDate(new Date());
			cstmPopLog.setAgentId(SessionUtil.getCurrentUser(request).getUid());
			
			logService.save(cstmPopLog);
			
			return new JSONObject().put("success", true).toString();
		}
		
		return new JSONObject().put("success", false).toString();
		
	}
	
	/**
	 * 导出信息
	 * @param request
	 * @param response
	 * @param solution
	 * @throws Exception
	 */
	@RequestMapping("export")
	@ResponseBody
	public void exportData(HttpServletRequest request,HttpServletResponse response,CstmIssueCondition condition,CstmIssueSolution solution) throws Exception{
		
		condition.setRequest(request);
		request.setAttribute("manageList", userService.getManagerUsers(SessionUtil.getCurrentUser(request).getUid()));
		cstmserviceService.getExcelCreated(request, response, condition,solution);
	}
	
}


