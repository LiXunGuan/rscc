package com.ruishengtech.rscc.crm.ui.knowledge;

import java.io.IOException;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.knowledge.condition.AgentNoticeCondition;
import com.ruishengtech.rscc.crm.knowledge.model.AgentNotice;
import com.ruishengtech.rscc.crm.knowledge.model.AgentNoticeUserLink;
import com.ruishengtech.rscc.crm.knowledge.service.AgentNoticeService;
import com.ruishengtech.rscc.crm.knowledge.service.AgentNoticeUserLinkService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.schedulereminder.MyListener;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.DatarangeService;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Controller
@RequestMapping("agentnotice")
public class AgentNoticeController {

	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DatarangeService datarangeService;
	
	@Autowired
	private AgentNoticeService agentNoticeService;
	
	@Autowired
	private BrokerService brokerService;
	
	@Autowired
	private AgentNoticeUserLinkService agentNoticeUserService;
	
	@RequestMapping
	public String index(Model model,HttpServletRequest request){
		model.addAttribute("publishs", AgentNotice.ISPUBLISH);
		if("agent".equals(request.getParameter("level"))){
			request.getSession().setAttribute("level", "agent");
		}
		
		
		model.addAttribute("iframecontent","agentnotice/agentnotice_info");
		return "iframe";
		
//		return "agentnotice/agentnotice_info";
	}
	
	@RequestMapping("manager")
	public String manager(Model model,HttpServletRequest request){
		model.addAttribute("publishs", AgentNotice.ISPUBLISH);
		request.getSession().removeAttribute("level");
		
		model.addAttribute("iframecontent","agentnotice/agentnotice_index");
		return "iframe";
		
//		return "agentnotice/agentnotice_index";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request, HttpServletResponse response, AgentNoticeCondition agentNoticeCondition){
		//获取当前登录用户
		User user = (User) SessionUtil.getCurrentUser(request);
		
		agentNoticeCondition.setRequest(request);
		agentNoticeCondition.setUserUUid(user.getUid());
		
		agentNoticeCondition.setLevel(request.getSession().getAttribute("level") == null ? "" : request.getSession().getAttribute("level").toString());
		
		PageResult<AgentNotice> pageResult =  agentNoticeService.queryPage(agentNoticeCondition);
		List<AgentNotice> angentNotices = pageResult.getRet();
		
		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < angentNotices.size(); i++) {
			JSONObject json = new JSONObject(angentNotices.get(i));
			if(angentNotices.get(i).getNoticeTitle().length() > 15){
				angentNotices.get(i).setNoticeTitle(angentNotices.get(i).getNoticeTitle().substring(0, 10)+"....");
			}
			if(angentNotices.get(i).getNoticeContent().length() > 25){
				angentNotices.get(i).setNoticeContent(angentNotices.get(i).getNoticeContent().substring(0, 15)+"....");
			}
			json.put("createTime", StringUtils.trimToEmpty(fmt.format(angentNotices.get(i).getCreateTime())));
			json.put("publishStatus", "0".equals(StringUtils.trimToEmpty(angentNotices.get(i).getPublishStatus())) ? "否" : "是");
			json.put("publishState", "0".equals(StringUtils.trimToEmpty(angentNotices.get(i).getPublishStatus())) ? "0" : "1");
			User us = userService.getByUuid(UUID.UUIDFromString(angentNotices.get(i).getCreateUserUUID().toString()));
			json.put("createUser", us.getLoginName());
			
			array.put(json);
		}
		
		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
		
		return jsonObject.toString();
	}
	
	@RequestMapping("get")
    public String getData(HttpServletRequest request, HttpServletResponse response, String uuid, String operation,String number, Model model){
		if(uuid != null){
			AgentNotice agentNotice = agentNoticeService.getAgentNoticeByUUid(uuid);
			model.addAttribute("item", agentNotice);
			if("look".equalsIgnoreCase(operation)){
				model.addAttribute("operation", "look");
				model.addAttribute("number", number);
			}
			return "agentnotice/agentnotice_save";
		}
		
		User us = (User) SessionUtil.getCurrentUser(request);
		
		//查询出所有用户
	    List<User> users = userService.getAllUser();
	    
	    for (int i = 0; i < users.size(); i++) {
	    	if(!"1".equals(us.getAdminFlag()) && !userService.hasDatarange(us.getUid(), "datarange", users.get(i).getDepartment())){
	    		users.remove(users.get(i--));
	    	}else{
	    		users.get(i).setLoginName(users.get(i).getDepartmentName() + "-" + users.get(i).getLoginName());
	    	};
		}
	    
	    model.addAttribute("users", users);
        return "agentnotice/agentnotice_user_save";
    }
	
	@RequestMapping("save")
    @ResponseBody
    public String save(HttpServletRequest request, AgentNotice agentNotice, String uid) throws ParseException, IOException{
		//获取当前登录用户
		User user = (User) SessionUtil.getCurrentUser(request);
		//得到当前时间
		String currenttime = fmt.format((new Date()));
		
		if(!StringUtils.isNotBlank(uid)){//保存
			agentNotice.setPublishStatus("0");
		}
		//保存坐席公告信息
		agentNotice.setCreateUserUUID(user.getUid());
		agentNotice.setCreateTime(fmt.parse(currenttime));
		agentNoticeService.saveOrUpdate(agentNotice);
		
		//得到接受人员的信息并在中间表进行保存
		String[] users = null;
		//接收公告的人员姓名
		String[] usernames = null;
		//得到添加的人员的UUID
		if(StringUtils.isNotBlank(request.getParameter("userUUid")) == true){
			users = request.getParameter("userUUid").split(",");
		}
		//得到添加人员的姓名
		if(StringUtils.isNotBlank(request.getParameter("usernames")) == true){
			usernames = request.getParameter("usernames").split(",");
		}
		if(users != null) {
			//保存添加的人员
			for (int i = 0; i < users.length; i++) {
				AgentNoticeUserLink agentNoticeUser = new AgentNoticeUserLink();
				agentNoticeUser.setUserUUID(users[i]);
				agentNoticeUser.setPublishUserUUID(user.getUid());
				agentNoticeUser.setAgentNoticeUUID(agentNotice.getUid());
				agentNoticeUserService.saveOrUpdate(agentNoticeUser);
			}
		}
		//判断公告状态是否是已发布
		if("1".equals(agentNotice.getPublishStatus())){
			//更新页面公告信息
			if(usernames != null){//保存页面传过来的人员姓名
				noticeDisplay(agentNotice,usernames);
			}else{
				//查询出当前公告已拥有的所有人员
			    List<AgentNoticeUserLink> agentNoticeUsers = agentNoticeUserService.getAgentNoticeUserByNotice(agentNotice);
			    if(agentNoticeUsers != null){
			    	String[] unames = new String[agentNoticeUsers.size()];
			    	int i = 0 ;
			    	for(AgentNoticeUserLink anu : agentNoticeUsers) {
			    		unames[i++] = userService.getByUuid(UUID.UUIDFromString(anu.getUserUUID())).getLoginName();
			    	}
			    	noticeDisplay(agentNotice,unames);
			    }
			}
		}
		
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 之前的保存方法
	 * @param request
	 * @param agentNotice
	 * @param bindingResult
	 * @param uid
	 * @param usernames
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws SchedulerException
	 */
	@RequestMapping("beforesave")
    @ResponseBody
    public String beforesave(HttpServletRequest request, AgentNotice agentNotice, BindingResult bindingResult, String uid, String usernames) throws ParseException, IOException, SchedulerException {
		//获取当前登录用户
		User user = (User) SessionUtil.getCurrentUser(request);
		//获取发布的开始和结束时间
		String stime = request.getParameter("publishStartTime");
//		String etime = request.getParameter("publishEndTime");
		//得到当前时间
		String currenttime = fmt.format((new Date()));
		if(fmt.parse(stime).getTime() <= fmt.parse(currenttime).getTime()){ //立即发布
			agentNotice.setPublishStatus("1");
		}else{
			agentNotice.setPublishStatus("0");
		}
//		agentNotice.setPublishUserUUID(user.getUid());
//		agentNotice.setPublishStartTime(fmt.parse(stime));
//		agentNotice.setPublishEndTime(fmt.parse(etime));
		agentNotice.setCreateUserUUID(user.getUid());
		agentNotice.setCreateTime(fmt.parse(currenttime));
		agentNoticeService.saveOrUpdate(agentNotice);
		
		//声明变量用于存储添加人员的姓名
		String[] unames = null;
		
		if("".equals(uid)){//添加
			String[] users = null;
			//得到添加的人员的UUID
			if(StringUtils.isBlank(request.getParameter("userUUid")) == false){
				users = request.getParameter("userUUid").split(",");
			}
			if (users != null) {
				//保存添加的人员
				for (int i = 0; i < users.length; i++) {
					AgentNoticeUserLink agentNoticeUser = new AgentNoticeUserLink();
					agentNoticeUser.setUserUUID(users[i]);
					agentNoticeUser.setAgentNoticeUUID(agentNotice.getUid());
					agentNoticeUserService.saveOrUpdate(agentNoticeUser);
				}
				if(usernames != null){
					unames = request.getParameter("usernames").split(",");
					if(fmt.parse(stime).getTime() <= fmt.parse(currenttime).getTime()){
						//如果开始时间小于当前时间,则以当前时间为开始时间
//						agentNotice.setPublishStartTime(fmt.parse(currenttime));
						//触发公告的定时任务
						doAgentNoticeTimerTask(agentNotice,unames);
						
						//立即发布公告
						//noticeDisplay(agentNotice, unames);
					}else{
						//触发公告的定时任务
						doAgentNoticeTimerTask(agentNotice,unames);
					}
				}
			}
		}else{//修改
		    //查询出当前公告已拥有的所有人员
		    List<AgentNoticeUserLink> agentNoticeUsers = agentNoticeUserService.getAgentNoticeUserByNotice(agentNoticeService.getAgentNoticeByUUid(uid));
			if(agentNoticeUsers != null){
				String uname = "";
				for (int i = 0; i < agentNoticeUsers.size(); i++) {
					String username = userService.getByUuid(UUID.UUIDFromString(agentNoticeUsers.get(i).getUserUUID())).getLoginName();
					uname = username + ","; 
				}
				unames = uname.split(",");
				if(fmt.parse(stime).getTime() <= fmt.parse(currenttime).getTime()){
					//如果开始时间小于当前时间,则以当前时间为开始时间
//					agentNotice.setPublishStartTime(fmt.parse(currenttime));
					//触发公告的定时任务
					doAgentNoticeTimerTask(agentNotice,unames);
					
					//立即发布公告
					//noticeDisplay(agentNotice, unames);
				}else{
					//更新公告的定时任务
					doAgentNoticeTimerTask(agentNotice,unames);
				}
			}
		}
        return new JSONObject().put("success", true).toString();
    }
	
	/**
	 * 坐席公告定时任务
	 * @param agentNotice
	 * @throws SchedulerException
	 */
	public void doAgentNoticeTimerTask(AgentNotice agentNotice,String[] users) throws SchedulerException{
		//获取变量
		String jobName = "jobname" + agentNotice.getUid();
		String jobGroup = "jobgroup" + agentNotice.getUid();
//		Date stime = agentNotice.getPublishStartTime();
//		Date etime = agentNotice.getPublishEndTime();
//		Integer repeatCount = agentNotice.getRepeatCount() == null ? 0 : agentNotice.getRepeatCount() - 1;
//		Long repeatInterval = agentNotice.getRepeatInterval() == null ? 0 : agentNotice.getRepeatInterval()*1000;
		
		//创建调度器实例
		StdSchedulerFactory sf = new StdSchedulerFactory();
		Properties props = new Properties();//监听完成时会用到
		props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		props.put("org.quartz.scheduler.instanceName", agentNotice.getUid());
		props.put("org.quartz.threadPool.threadCount", "10");
		sf.initialize(props);
		Scheduler scheduler = sf.getScheduler();
		
		//添加监听
		MyListener my = new MyListener("listener" + agentNotice.getUid());
		scheduler.addSchedulerListener(my);
		scheduler.start();
		scheduler.deleteJob(jobName, jobGroup);
		
		//创建一个JobDetail,该Job负责定义需要执行任务,指明在scheduler中的任务名,任务组,任务执行类.
		JobDetail job = new JobDetail(jobName, jobGroup, AgentNoticeTimerTask.class);
		//存放参数
		job.getJobDataMap().put("agentnotice", agentNotice);
		job.getJobDataMap().put("users", users);
		
		//创建触发器,负责设置调度策略
//		Trigger trigger = new SimpleTrigger(jobName, jobGroup, stime, etime, repeatCount, repeatInterval);
//		trigger.getJobDataMap().put("type","agentNotice");
//		trigger.getJobDataMap().put("agentnotice", agentNotice);
		
		//注册并进行调度  
//		scheduler.scheduleJob(job, trigger);
	}
	
	/**
	 * 页面公告提醒
	 * @param agentNotice
	 * @throws IOException 
	 */
	public void noticeDisplay(AgentNotice agentNotice, String[] users) throws IOException{
		JSONObject  json = new JSONObject();
		json.put("type", "notice");
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		brokerService.sendToUsers("/user", json.toString(), users);
	} 
	
	/**
	 * 删除坐席公告
	 * @param request
	 * @param uuid
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("remove")
    @ResponseBody
    public String remove(HttpServletRequest request,String uuid) throws IOException {
		//得到公告信息
		AgentNotice an = agentNoticeService.getAgentNoticeByUUid(uuid);
		//查询出当前公告已拥有的所有人员
	    List<AgentNoticeUserLink> agentNoticeUsers = agentNoticeUserService.getAgentNoticeUserByNotice(an);
	    //删除公告信息
	    agentNoticeService.delete(agentNoticeService.getAgentNoticeByUUid(uuid));;
	    //判断公告的发布状态
		if("1".equals(an.getPublishStatus())){
			//更新页面公告信息
			if(agentNoticeUsers != null){
				String[] usernames = new String[agentNoticeUsers.size()];
	    		int i = 0 ;
	    		for(AgentNoticeUserLink anu : agentNoticeUsers) {
	    			usernames[i++] = userService.getByUuid(UUID.UUIDFromString(anu.getUserUUID())).getLoginName();
	    		}
	    		noticeDisplay(an,usernames);
			}
		}
        return new JSONObject().put("success", true).toString();
    }
	
	/**
	 * 弹出添加人员页面
	 * @param request
	 * @param response
	 * @param uuid
	 * @param model
	 * @return
	 * @throws SocketException
	 */
	@RequestMapping("addItem")
	public String addItem(HttpServletRequest request, HttpServletResponse response, String uuid, Model model)
			throws SocketException {
	    
		//查询出所有用户
	    List<User> users = userService.getAllUser(); 
	    
	    //查询出当前公告已拥有的所有人员
	    List<AgentNoticeUserLink> agentNoticeUsers = agentNoticeUserService.getAgentNoticeUserByNotice(agentNoticeService.getAgentNoticeByUUid(uuid));
	    
	    //用来保存当前公告的人员
	    List<User> userCheckls =new ArrayList<User>();
	     
	    if(users != null && agentNoticeUsers != null) {
	    	 for(int i = 0; i < users.size(); i++) {
	        	 for(int j = 0; j < agentNoticeUsers.size(); j++) {
	        		 if(users.get(i).getUid().equals(agentNoticeUsers.get(j).getUserUUID())) {	
	        			 userCheckls.add(users.get(i));
	        			 users.remove(i);	//在所有人员中删除当前技能组人员
	          			 i=i-1;
	          			 break;
	          		}
				}
			}
		}
	    
	    //获取当前登录用户
	    User us = (User) SessionUtil.getCurrentUser(request);
	    for (int i = 0; i < users.size(); i++) {
	    	if(!"1".equals(us.getAdminFlag()) && !userService.hasDatarange(us.getUid(), "datarange", users.get(i).getDepartment())){
	    		users.remove(users.get(i--));
	    	}else{
	    		users.get(i).setLoginName(users.get(i).getDepartmentName() + "-" + users.get(i).getLoginName());
	    	};
		}
	    
//	    if(users != null){
//	    	//为用户添加部门名字
//	    	for (User user : users) {
//	    		Datarange datarange = datarangeService.getByUuid(UUID.UUIDFromString(user.getDepartment()));
//	    		user.setLoginName(datarange.getDatarangeName() + "-" + user.getLoginName());
//	    	}
//	    }
	    
	    if(userCheckls != null){
	    	//选中用户添加部门名字
	    	for (User user : userCheckls) {
//	    		Datarange datarange = datarangeService.getByUuid(UUID.UUIDFromString(user.getDepartment()));
	    		user.setLoginName(user.getDepartmentName() + "-" + user.getLoginName());
	    	}
	    }
	     
	    model.addAttribute("agentNoticeUUid", uuid);
	    model.addAttribute("users", users);
	    model.addAttribute("userCheckls", userCheckls);
	     
	    return "agentnotice/agentnotice_user";
	}
	
	/**
	 * 保存添加的人员
	 * @param request
	 * @param agentNoticeUUid
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 * @throws SchedulerException 
	 */
	@RequestMapping("saveAgentnoticeUser")
    @ResponseBody
	public String saveAgentNoticeUser(HttpServletRequest request, String agentNoticeUUid, String usernames) throws ParseException, IOException, SchedulerException {
		//获取当前登录用户
		User user = (User) SessionUtil.getCurrentUser(request);
		//得到新的所有人员的姓名
		String[] newunames = null;
		String[] oldunames = null;
		//得到添加的人员的UUID
		String[] ls = request.getParameterValues("userUUid");
		//得到坐席公告
		AgentNotice agentNotice = agentNoticeService.getAgentNoticeByUUid(agentNoticeUUid);
		//查询出当前公告上一次已拥有的所有人员
	    List<AgentNoticeUserLink> agentNoticeUsers = agentNoticeUserService.getAgentNoticeUserByNotice(agentNotice);
		//先删除中间表所有相关公告的信息
		agentNoticeUserService.deleteByAgentNotice(agentNotice);
		if (ls != null) {
			for (int i = 0; i < ls.length; i++) {
				AgentNoticeUserLink agentNoticeUser = new AgentNoticeUserLink();
				agentNoticeUser.setUserUUID(ls[i]);
				agentNoticeUser.setPublishUserUUID(user.getUid());
				agentNoticeUser.setAgentNoticeUUID(agentNotice.getUid());
				agentNoticeUserService.saveOrUpdate(agentNoticeUser);
			}
			//判断当前公告的发布状态是否正在发布,如果正在发布，则重新推送到前台提醒
			if("1".equals(agentNotice.getPublishStatus())){
				if(agentNoticeUsers != null){
				    oldunames = new String[agentNoticeUsers.size()];
					int i = 0 ;
					for(AgentNoticeUserLink anu : agentNoticeUsers) {
						oldunames[i++] = userService.getByUuid(UUID.UUIDFromString(anu.getUserUUID())).getLoginName();
					}
				}
				//获取新的所有接受公告人员的姓名
				if(usernames != null){
					newunames = usernames.split(",");
					if(oldunames != null){
						String[] names = mergedArray(newunames,oldunames);
						//给新的接收人员发送消息
						noticeDisplay(agentNotice, names);
					}else{
						noticeDisplay(agentNotice,newunames);
					}
				}
			}
			return new JSONObject().put("success", true).toString();
//			return new JSONObject().put("success", true).put("publishStatus",agentNotice.getPublishStatus()).put("agentNoticeUUid", agentNotice.getUid()).put("userNames", unames).put("userUUid", ls).toString();
		}
		return new JSONObject().put("success", false).put("message", "未选择任何人员！").toString();
	}
	
	/**
	 * 合并数组
	 * @param s1
	 * @param s2
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String[] mergedArray(String[] s1,String[] s2){
		Set s = new HashSet<String>();
		s.addAll(Arrays.asList(s1));
		s.addAll(Arrays.asList(s2));
		String[] newArray = new String[s.size()];
		return (String[]) s.toArray(newArray);
	}
	
	/**
	 * 发布公告
	 * @param request
	 * @param agentNoticeUUid
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("publishAgentNotice")
    @ResponseBody
	public String publishAgentNotice(HttpServletRequest request, String agentNoticeUUid) throws IOException, ParseException{
		//获取当前登录用户
		User user = (User) SessionUtil.getCurrentUser(request);
		//得到公告信息
		AgentNotice agentNotice = agentNoticeService.getAgentNoticeByUUid(agentNoticeUUid);
		//查询出当前公告已拥有的所有人员
	    List<AgentNoticeUserLink> agentNoticeUsers = agentNoticeUserService.getAgentNoticeUserByNotice(agentNotice);
	    if(agentNoticeUsers != null){
	    	//更新坐席公告的状态
	    	agentNotice.setPublishStatus("1");
	    	agentNoticeService.saveOrUpdate(agentNotice);
	    	
	    	//获取所有人员的姓名
	    	String[] usernames = new String[agentNoticeUsers.size()];
	    	int i = 0 ;
	    	for(AgentNoticeUserLink anu : agentNoticeUsers) {
	    		//获取人员姓名
	    		usernames[i++] = userService.getByUuid(UUID.UUIDFromString(anu.getUserUUID())).getLoginName();;
	    		//更新公告用户中间表的发布用户的信息
	    		anu.setPublishUserUUID(user.getUid());
	    		anu.setPublishTime(fmt.parse(fmt.format(new Date())));
	    		agentNoticeUserService.saveOrUpdate(anu);
	    	}
	    	
	    	//将消息发布到页面
	    	noticeDisplay(agentNotice,usernames);
	    	return new JSONObject().put("success", true).toString();
	    }
	    return new JSONObject().put("success", false).put("errorMessage", "此条公告暂未选择任何人员！").toString();
	}
	
	/**
	 * 取消发布公告
	 * @param request
	 * @param agentNoticeUUid
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("unpublishAgentNotice")
    @ResponseBody
	public String unpublishAgentNotice(HttpServletRequest request, String agentNoticeUUid) throws IOException{
		//得到公告信息并修改状态
		AgentNotice agentNotice = agentNoticeService.getAgentNoticeByUUid(agentNoticeUUid);
		agentNotice.setPublishStatus("0");
		agentNoticeService.saveOrUpdate(agentNotice);
		//查询出当前公告已拥有的所有人员
	    List<AgentNoticeUserLink> agentNoticeUsers = agentNoticeUserService.getAgentNoticeUserByNotice(agentNotice);
//		//删除关联表的数据
//		agentNoticeUserService.deleteByAgentNotice(agentNotice);
		//更新页面公告信息
		if(agentNoticeUsers != null){
			String[] usernames = new String[agentNoticeUsers.size()];
    		int i = 0 ;
    		for(AgentNoticeUserLink anu : agentNoticeUsers) {
    			usernames[i++] = userService.getByUuid(UUID.UUIDFromString(anu.getUserUUID())).getLoginName();
    		}
    		noticeDisplay(agentNotice,usernames);
		}
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 验证公告标题
	 * @param request
	 * @param response
	 * @param title
	 * @param uid
	 * @throws IOException
	 */
	@RequestMapping("checkTitle")
	@ResponseBody
	public void isRepeat(HttpServletRequest request, HttpServletResponse response, String title, String uid)
			throws IOException {
		AgentNotice notice = agentNoticeService.getAgentNoticeByTitle(title);
		if (notice == null) {
			response.getWriter().print(true); // 直接打印true 通过
		}else{
			if(uid == null) {
				response.getWriter().print(false);
			}else{
				if(uid.equals(notice.getUid())) {
					response.getWriter().print(true);
				}else{
					response.getWriter().print(false);
				}
			}
		}
	}
	
	/**
	 * 查询当前登陆用户的公告信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getUserAgentNotices")
    @ResponseBody
	public String getUserAgentNotice(HttpServletRequest request, HttpServletResponse response){
		//获取当前登录用户
		User user = (User) SessionUtil.getCurrentUser(request);
		//查询当前登陆用户的公告信息
		List<AgentNotice> agentNotices = agentNoticeService.getAgentNoticeUser(user.getUid());
		//遍历当前用户的公告,得到发送公告的人的姓名
		if(agentNotices != null){
			for (AgentNotice an : agentNotices) {
				an.setPublishUserName(userService.getByUuid(UUID.UUIDFromString(an.getPublishUserUuid())).getLoginName());
			}
		}
		return new JSONObject().put("success", true).put("agentNotices", agentNotices).toString();
	}
	
}
