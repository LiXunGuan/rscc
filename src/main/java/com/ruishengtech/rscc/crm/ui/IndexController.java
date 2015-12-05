package com.ruishengtech.rscc.crm.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.ruishengtech.framework.core.AESTools;
import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.framework.core.licence.util.LicenseUtils;
import com.ruishengtech.framework.core.websocket.WebSocketUser;
import com.ruishengtech.framework.core.websocket.service.BrokerService;
import com.ruishengtech.rscc.crm.cstm.model.Customer;
import com.ruishengtech.rscc.crm.cstm.service.CustomerDesignService;
import com.ruishengtech.rscc.crm.cstm.service.CustomerService;
import com.ruishengtech.rscc.crm.datamanager.model.UserData;
import com.ruishengtech.rscc.crm.datamanager.service.NewUserDataService;
import com.ruishengtech.rscc.crm.knowledge.model.AgentNotice;
import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminder;
import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminderUser;
import com.ruishengtech.rscc.crm.knowledge.service.AgentNoticeService;
import com.ruishengtech.rscc.crm.knowledge.service.AgentNoticeUserLinkService;
import com.ruishengtech.rscc.crm.knowledge.service.ScheduleReminderService;
import com.ruishengtech.rscc.crm.knowledge.service.ScheduleReminderUserService;
import com.ruishengtech.rscc.crm.ui.cstm.controller.CustomerController;
import com.ruishengtech.rscc.crm.ui.mw.event.handler.MissCallEvent;
import com.ruishengtech.rscc.crm.ui.mw.event.handler.PopEventHandler;
import com.ruishengtech.rscc.crm.ui.mw.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.mw.send.AgentManager;
import com.ruishengtech.rscc.crm.ui.mw.send.AgentStatus;
import com.ruishengtech.rscc.crm.ui.mw.send.CallManager;
import com.ruishengtech.rscc.crm.ui.mw.send.QueueManager;
import com.ruishengtech.rscc.crm.ui.mw.service.CallLogService;
import com.ruishengtech.rscc.crm.ui.mw.service.SYSConfigService;
import com.ruishengtech.rscc.crm.ui.schedulereminder.ScheduleReminderController;
import com.ruishengtech.rscc.crm.ui.sysConfig.SysConfigManager;
import com.ruishengtech.rscc.crm.ui.sysConfig.model.Config;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.SysConfigService;
import com.ruishengtech.rscc.crm.ui.sysConfig.service.UserConfigService;
import com.ruishengtech.rscc.crm.user.model.Action;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.ActionService;
import com.ruishengtech.rscc.crm.user.service.PermissionRoleService;
import com.ruishengtech.rscc.crm.user.service.PermissionService;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Frank
 */
@Controller
@RequestMapping
public class IndexController {

	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	SimpleDateFormat fmts = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private CustomerDesignService designService;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CallLogService callLogService;
	
	@Autowired
	@Qualifier(value="diyTableService")
	private DiyTableService divTableService;
	
	@Autowired
	private IndexService indexService;

	@Autowired
	private UserService userService;

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private PermissionRoleService permissionRoleService;
	
	@Autowired
	private ActionService actionService;
	
	@Autowired
	private AgentNoticeService agentNoticeService;
	
	@Autowired
	private ScheduleReminderService scheduleReminderService;
	
	@Autowired
	private AgentNoticeUserLinkService agentNoticeUserService;
	
	@Autowired
	private ScheduleReminderUserService scheduleReminderUserService;
	
	@Autowired
	private BrokerService brokerService;
	
	@Autowired
	private SYSConfigService sysConfigService;
	
	@Autowired
	private SysConfigService configService;
	
	@Autowired
	private CustomerDesignService customerDesignService;
	
	@Autowired
	private NewUserDataService newUserDataService;
	
	@Autowired
	private UserConfigService userConfigService;
	
	private static  Boolean apply = false;
	
	/**
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public String getLogin(HttpServletRequest request,Model model) throws Exception{
		
		String remote = SpringPropertiesUtil.getProperty("remoteVerification");
		//如果开启了远程访问 校验是否已经
		if(!"false".equals(remote)){
			
			String licenseStatus = LicenseUtils.getInstance().getStatus();
			if("locked".equals(licenseStatus)){
				
				model.addAttribute("errormsg", "您的系统已经过期，请联系管理员。");
				return "toLogin";
			}
		}
		
		
		//如果查询为第一次运行，跳转到配置页面
		String isFirst = configService.getSysConfigByKey("sys.first").getSysVal();
		if(StringUtils.isNotBlank(isFirst) && "0".equals(isFirst)){
			return "sys-guide";
		}
		
		//如果是0，不默认置闲 如果是1 默认置闲
		//如果查询为第一次运行，跳转到配置页面
		//弹屏方式
		String popType = configService.getSysConfigByKey("sys.pop.save.pop.type").getSysVal();
		if(StringUtils.isNotBlank(popType) ){

			model.addAttribute("popType", popType);
		}
		//接通后不保存直接关闭pop的设置
		String closePop = configService.getSysConfigByKey("sys.call.datasave").getSysVal();
		if(StringUtils.isNotBlank(closePop)){
			model.addAttribute("closepop", closePop);
		}
		
		model.addAttribute("verError", "");
		if(apply == false){
			apply = true;
		}
		
		//读取配置，显示配置信息
		indexService.showCompanyInfo(model);
		
		User sessionUser = SessionUtil.getCurrentUser(request);
		if(sessionUser != null){
//			return "redirect:/doLogin";
//			AgentManager.extenUbind(sessionUser);
			//还要删除WebSocket用户
//			brokerService.remove("/user", sessionUser.getLoginName());
			request.getSession().invalidate();
		}
		
		//获取分机号和用户名存放的cookie信息
		Cookie unamecookie = CookieHelper.getCookieByName(request, "username");
		Cookie extencookie = CookieHelper.getCookieByName(request, "extension");
		if(unamecookie != null){
			User user = new User();
			user.setLoginName(URLDecoder.decode(unamecookie.getValue(), "UTF-8"));
			user.setPassword("");
			model.addAttribute("loginuser", user);
		}
		if(extencookie != null){
			model.addAttribute("loginSipId", URLDecoder.decode(extencookie.getValue(), "UTF-8"));
		}
		
		return "toLogin";
	}

	/**
	 * 保存系统配置
	 * @param arg1
	 * @return
	 */
	@RequestMapping("config")
	@ResponseBody
	public String setSystemConfig(Config config){
		
		indexService.setSystemConfigInfo(config);
        indexService.deploy(config);
		return new JSONObject().put("success", true).toString();
	}

	/**
	 * 登录方法
	 * 
	 * @return
	 * @throws SchedulerException 
	 * @throws java.text.ParseException 
	 * @throws InterruptedException 
	 */
	@RequestMapping("doLogin")
	public String doLogin(HttpServletRequest request, HttpServletResponse response, User user, Model model) throws Exception {
		
		String remote = SpringPropertiesUtil.getProperty("remoteVerification");
		//如果开启了远程访问 校验是否已经
		if(!"false".equals(remote)){
			
			String licenseStatus = LicenseUtils.getInstance().getStatus();
			if("locked".equals(licenseStatus)){
				
				model.addAttribute("errormsg", "您的系统已经过期，请联系管理员。");
				return "toLogin";
			}
		}
		
		//如果查询为第一次运行，跳转到配置页面
		String isFirst = configService.getSysConfigByKey("sys.first").getSysVal();
		if(StringUtils.isNotBlank(isFirst) && "0".equals(isFirst)){
			return "sys-guide";
		}
		
//		String licenseStatus = LicenseUtils.getInstance().getStatus();
//		if("locked".equals(licenseStatus)){
//			model.addAttribute("verError", "您的系统已经过期，请联系管理员。");
//			return "toLogin";
//		}
		
		//弹屏方式
		String popType = configService.getSysConfigByKey("sys.pop.save.pop.type").getSysVal();

//		String verify = SessionUtil.getSession(request, "validateCode").toString();
//		String verinput = request.getParameter("veryCode").toUpperCase().trim();
//		if(!verify.equals(verinput)){
//			model.addAttribute("verError", "验证码输入错误");
//			return "toLogin";
//		}
		User loginedUser = null;
		String exten = "";
		User sessionUser = SessionUtil.getCurrentUser(request);
		Object sessionExten = SessionUtil.getSession(request, SessionUtil.LOGINEXTEN);
		//如果之前登录过，那么将登录信息存放到session中
		if(sessionUser != null){
			//把session放一个map
			SysSessionListener.sessionMap.put(sessionUser.getLoginName(), request.getSession());
			loginedUser = sessionUser;
			if(sessionExten != null){
				exten = sessionExten.toString();
				// 存放登录分机
				if(!"".equals(exten)){
					request.getSession().setAttribute(SessionUtil.LOGINEXTEN, exten);
					AgentManager.agentMap.put(exten, loginedUser.getLoginName());
					String[] queues = QueueManager.getAgentQueueArray(loginedUser.getLoginName());
					request.getSession().setAttribute("queues", queues);
				}
			}
		}else{
			loginedUser = userService.login(user.getLoginName(),user.getPassword());
			exten = request.getParameter("loginExtension");
			
			if(null == loginedUser){
				User u = new User();
				u.setLoginName(user.getLoginName());
				u.setPassword(user.getPassword());
				model.addAttribute("loginuser", u);
				model.addAttribute("errorAgentFont", "用户名不存在或密码错误,请检查！");
				return "toLogin";
			}
			//把session放一个map
			SysSessionListener.sessionMap.put(loginedUser.getLoginName(), request.getSession());
			//踢掉自己在别的地方登录的用户
			brokerService.sendToUser("/user", loginedUser.getLoginName(), new JSONObject().put("type", "kickout").toString());
			
            //自己强行提掉自己
            AgentManager.extenUbind(loginedUser);

			//绑定分机 {"exit_code":10100,"err_msg":"分机已经被注册,是否踢掉?"}
			if (StringUtils.isNotBlank(exten)) {
				JSONObject code =  AgentManager.extenBind(loginedUser,exten);
				
				if("参数[exten] 为空".equals(code.optString("err_msg"))){
					request.getSession().setAttribute("unexten","未输入分机");
				}else if(!("0").equals(code.optString("exit_code"))){
					model.addAttribute("loginuser", user);
					model.addAttribute("loginSipId", exten);
					model.addAttribute("exists", "1");
					if("分机已经被注册,是否踢掉?".equals(code.optString("err_msg"))){
						model.addAttribute("errorExten",code.optString("err_msg"));
					}else{
						model.addAttribute("errormsg", code.optString("err_msg"));
					}
					return "toLogin";
				}else if(("0").equals(code.optString("exit_code"))){
					if("false".equals(code.opt("exten_register_status"))){
						//分机未注册
						model.addAttribute("loginSipId", exten);
						model.addAttribute("errormsg","分机未注册!");
						return "toLogin";
					}
				}
			} else {
				request.getSession().setAttribute("unexten","未输入分机");
			}
			// 存放登录用户信息
			SessionUtil.setSession(request, SessionUtil.CURRENTUSER, loginedUser);
			// 存放登录分机
			if(!"".equals(exten)){
				request.getSession().setAttribute(SessionUtil.LOGINEXTEN, exten);
				AgentManager.agentMap.put(exten, loginedUser.getLoginName());
				String[] queues = QueueManager.getAgentQueueArray(loginedUser.getLoginName());
				request.getSession().setAttribute("queues", queues);
			}
			
			// 开启日程提醒
			getScheduleReminders(loginedUser);
			
		}
		//还原坐席状态
		indexService.rebackAgentStatus(request, loginedUser);
		
		JSONObject statJson = new JSONObject(AgentStatus.getAgentStatus(loginedUser.getLoginName()).getString("ret"));
		if(statJson.optBoolean("result", true)) {
			if(statJson.opt("number")==null)
				statJson.put("number", "");
			if(statJson.opt("busy_reason")==null)
				statJson.put("busy_reason", "");
				model.addAttribute("agentStat", statJson);
		}
		
		if (null != loginedUser) {

			//存放websocket用户
			WebSocketUser wsuser = WebSocketUser.getUserFromName(loginedUser.getLoginName());
			SessionUtil.setSession(request, WebSocketUser.WEBSOCKETUSER, wsuser);
			
			// 查询权限--菜单显示
			List<String> permissions;
			permissions = userService.getActions(loginedUser);
			if("0".equals(loginedUser.getUid())){
				List<String> adminPermission = permissionService.getAdminPermissions();
				for (String string : adminPermission) {
					if(!permissions.contains(string)) {
						permissions.add(string);
					}
				}
			}else {
				
			}
			Action action = actionService.getMenuTree(permissions);
			
 			model.addAttribute("menus", action);
			model.addAttribute("loginUser", loginedUser);
			
			//查询所有权限
			Map<String, Action> actionMaps = userService.getActionMaps(loginedUser);
			request.getSession().setAttribute("actionMaps", actionMaps);
			
			//漏话的数量
			model.addAttribute("missedcalls", MissCallEvent.getMissCallMap().containsKey(loginedUser.getLoginName()) ? MissCallEvent.getMissCallMap().get(loginedUser.getLoginName()) : "");
			
			
			//查询当前可用置忙原因并存放置忙原因，加上一些队列状态
			Map<String, String> agentpause = addbusyReason();
			model.addAttribute("agentpause", agentpause);
//			model.addAttribute("queueNumber", getQueueNumber((String[])request.getSession().getAttribute("queues")));
			//下面可以再改进一下
			model.addAttribute("queueMap", QueueManager.queueMap);
			Set<String> set = new HashSet<>(userService.getDataranges(loginedUser.getUid(), "queue"));
			String[] queues = QueueManager.getAgentQueueArray(loginedUser.getLoginName());
			request.getSession().setAttribute("queues", queues);
			if(request.getSession().getAttribute("queues") != null) {
				queues = (String[])request.getSession().getAttribute("queues");
				set.addAll(Arrays.asList(queues));
			}
			model.addAttribute("queueNameMap", QueueManager.getQueueNameMap());
			model.addAttribute("allQueue", set);
			
			request.getSession().setAttribute("allQueues", set);
			
			//存放用户名和分机号的cookie信息
			CookieHelper.addCookie(response, "username", loginedUser.getLoginName(), 7*24*60*60);
			CookieHelper.addCookie(response, "extension", exten, 7*24*60*60);
			
			String closePop = configService.getSysConfigByKey("sys.call.datasave").getSysVal();
			
			indexService.judgeIsHidePhone(model, SessionUtil.getCurrentUser(request).getUid());
			if(StringUtils.isNotBlank(popType)){
				model.addAttribute("popType", popType);
			}
			//如果是0，不默认置闲 如果是1 默认置闲
			//如果查询为第一次运行，跳转到配置页面
			//通话后直接关闭弹屏处理
			if(StringUtils.isNotBlank(closePop)){
				model.addAttribute("closepop", closePop);
			}
			
			return "index";

		} else {

			model.addAttribute("loginuser", user);
			return "toLogin";
		}
	}

	
	private void getScheduleReminders(User loginedUser) throws SchedulerException, ParseException {
		
		//获取当前时间
		Date ctime = new Date();
		//查询登陆用户的日程提醒
		List<ScheduleReminder> srs = scheduleReminderService.getScheduleRemindersByUserUUid(loginedUser.getUid());
//		List<ScheduleReminder> srs = new ArrayList<ScheduleReminder>();
//		if(srss != null){
//			for(ScheduleReminder sr : srss){
//				ScheduleReminderUser srr = scheduleReminderUserService.getScheduleByScheduleReminderUUID(sr.getUid());
//				if(srr == null){
//					srs.add(sr);
//				}
//			}
//		}
		ScheduleReminderController src = new ScheduleReminderController();
		if(srs != null){
			for(ScheduleReminder sr : srs) {
				Date stime = fmt.parse(fmt.format(sr.getSchStartTime()));
				//判断是否是不提醒
				if(!"-1".equalsIgnoreCase(sr.getScheduleRemind())){
					ScheduleReminderUser sru = new ScheduleReminderUser();
					//创建中间表信息
					sru.setScheduleUUID(sr.getUid());
					sru.setCreateUserUUID(sr.getScheduleUser());
					sru.setCreateTime(fmt.parse(fmt.format(sr.getScheduleCreateTime())));
					
					// 计算时间
					stime = new Date(stime.getTime() - Long.parseLong(sr.getScheduleRemind()));
					
					sr.setSchStartTime(stime);
					
					//判断一次性活动
					if("disposable".equals(sr.getScheduleRepeat())){
						if(ctime.getTime() <= stime.getTime() ){
							//调用定时任务
							scheduleReminderService.doTimeTask(sr, sru, loginedUser, "login");
						}
					}else{
						//如果当前时间大于日程的开始时间
						if(ctime.getTime() > stime.getTime()){
							//计算时间的间隔数
							Long days = getDaysBetween(stime,ctime);
							//计算出新的时间数
							Long newstime = stime.getTime() + days*24*60*60*1000;
							//判断新的时间数与当前时间的大小
							if(ctime.getTime() <= newstime){
								sr.setSchStartTime(fmt.parse(fmt.format(newstime)));
							}else{
								sr.setSchStartTime(fmt.parse(fmt.format(newstime + 24*60*60*1000)));
							}
						}
						//调用定时任务
						scheduleReminderService.doTimeTask(sr, sru, loginedUser, "login");
					}
				}
			}
		}
	}

	private Map<String, String> addbusyReason() {
		SysConfig sysConfig = sysConfigService.getSysConfig("agentbusy");
		Map<String,String> agentpause = new LinkedHashMap<String,String>();
		if (sysConfig!=null) {
            JSONObject jsob= new JSONObject(sysConfig.getVal());

            if (jsob.get("state_1").toString().equals("1")) {
                agentpause.put("name_1",jsob.get("name_1").toString());
            }
            if (jsob.get("state_2").toString().equals("1")) {
                agentpause.put("name_2",jsob.get("name_2").toString());
            }
            if (jsob.get("state_3").toString().equals("1")) {
                agentpause.put("name_3",jsob.get("name_3").toString());
            }
            if (jsob.get("state_4").toString().equals("1")) {
                agentpause.put("name_4",jsob.get("name_4").toString());
            }
            if (jsob.get("state_5").toString().equals("1")) {
                agentpause.put("name_5",jsob.get("name_5").toString());
            }
            if (jsob.get("state_6").toString().equals("1")) {
                agentpause.put("name_6",jsob.get("name_6").toString());
            }
//            if (jsob.get("state_7").toString().equals("1")) {
//                agentpause.put("name_7",jsob.get("name_7").toString());
//            }
//            if (jsob.get("state_8").toString().equals("1")) {
//                agentpause.put("name_8",jsob.get("name_8").toString());
//            }
//            if (jsob.get("state_9").toString().equals("1")) {
//                agentpause.put("name_9",jsob.get("name_9").toString());
//            }
//            if (jsob.get("state_10").toString().equals("1")) {
//                agentpause.put("name_10",jsob.get("name_10").toString());
//            }

        }
		return agentpause;
	}
	
	@RequestMapping("forgetpwd")
	public String forgetPwd(Model model){
		model.addAttribute("model", "forgetpwd");
		return "user/userpwd_forget";
	}
	@RequestMapping("forgetpwd/sendMail")
	@ResponseBody
	public String forgetPwd(HttpServletRequest request,HttpServletResponse response,Model model){
		String loginname = request.getParameter("loginname");
		String mail = request.getParameter("mail");
		List<User> users = userService.getAllUser();
		List<String> loginnames = new ArrayList<>();
		for(User user : users){
			loginnames.add(user.getLoginName());
		}
		if(StringUtils.isBlank(loginname) || !loginnames.contains(loginname)){
			return new JSONObject().put("success", false).toString();
		}else{
			User user = userService.getUserByLoginName(loginname, true);
			try {
				SendMailUtil sm = new SendMailUtil();
				String url = sm.sendMail(request,response, loginname, mail);
				
				user.setCheckUrl(url);
				userService.update(user);
				return new JSONObject().put("success", true).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new JSONObject().put("success", false).toString();
	}
	
	@RequestMapping("user/reset_password")
	public String resetpwd(HttpServletRequest request,HttpServletResponse response,Model model){
		//获取到链接中的验证信息
		String validateInfo = request.getParameter("sid");
		model.addAttribute("checkinfo", validateInfo);
		User user = userService.getUserByCheckurl(validateInfo);
		//获取当前时间的毫秒数 和数据库中保存的校验信息的时间进行比对，超过则判定链接失效
		Timestamp time = new Timestamp(System.currentTimeMillis());
		long date = time.getTime()/1000*1000;

		long outTime = Long.parseLong(validateInfo.split("-")[0]);
		if(user == null || date>outTime){
			return "error";
		}
		return "user/userpwd-reset";
	}
	
	@RequestMapping("forgetpwd/checkMail")
	@ResponseBody
	public String checkMail(HttpServletRequest request,String loginname,String mail) {
		User user = userService.getUserByLoginName(loginname, true);
		if(StringUtils.isNotBlank(mail) && user !=null && StringUtils.isNotBlank(user.getMail())){
			if(user!=null && user.getMail().equals(mail)){
				return "true";
			}
		}
		return "false";
	}
	
	@RequestMapping("forgetpwd/savePwd")
	@ResponseBody
	public String savePwd(HttpServletRequest request,Model model,String sid){
		
		User user = userService.getUserByCheckurl(sid);
		String newPwd = request.getParameter("password");
		if(user != null){
		user.setPassword(newPwd);
		user.setCheckUrl("0");
		userService.update(user);
		}
		return new JSONObject().put("success", true).toString();
	}
	
	/**
	 * 得到两个时间的间隔天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Long getDaysBetween(Date startDate, Date endDate) {  
        Calendar fromCalendar = Calendar.getInstance();  
        fromCalendar.setTime(startDate);  
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);  
        fromCalendar.set(Calendar.MINUTE, 0);  
        fromCalendar.set(Calendar.SECOND, 0);  
        fromCalendar.set(Calendar.MILLISECOND, 0);  
  
        Calendar toCalendar = Calendar.getInstance();  
        toCalendar.setTime(endDate);  
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);  
        toCalendar.set(Calendar.MINUTE, 0);  
        toCalendar.set(Calendar.SECOND, 0);  
        toCalendar.set(Calendar.MILLISECOND, 0);  
  
        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);  
    }

	@RequestMapping("ui")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		return "index";
	}

	/**
	 * 添加新的tab
	 * 
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("addTab")
	public String addTab(HttpServletRequest request, HttpServletResponse response,String name,String labelname, String detailq , String detail) throws IOException {
		
		// 查看是否存在已经打开的tab
 		LinkedHashMap<String, TableBeans> linkedHashMap = (LinkedHashMap<String, TableBeans>) request.getSession().getAttribute(TableBeans.TAB);
		
		if (linkedHashMap == null) {
			linkedHashMap = new LinkedHashMap<>();
		}
		linkedHashMap.put(name, new TableBeans(name, labelname));
		request.getSession().setAttribute(TableBeans.TAB, linkedHashMap);
		request.getSession().setAttribute(TableBeans.TABSELECTED, name);
		
		/**
		 * 基本菜单、点击外呼、来电弹屏
		 */
		if (StringUtils.isNotBlank(name) && indexService.getUrlMap().containsKey(name)) {
			
			detail = replaceCharacter(name, detail);

			//是否已经传递参数
			if(indexService.getUrlMap().get(name).getActionURL().contains("?")){
				
				return "redirect:/" + indexService.getUrlMap().get(name).getActionURL()+"&arg0" + labelname +"&arg1=" + detail;
			}
			return "redirect:/" + indexService.getUrlMap().get(name).getActionURL()+"?arg0" + labelname +"&arg1=" + detail;
			
		} else {
			/**
			 * 电话号码 编号查询、点击编号、点击电话号码
			 */
			if(StringUtils.isNotBlank(detail)){
				
				detail = detail.replace("{", "(").replace("}", ")");
				
				return "redirect:/newTab?arg0=" + detail ;
			}

			System.err.println("=========弹屏出错===========");
			return null;
		}
	}
	/**
	 * 新弹窗
	 * @param request
	 * @param arg0 电话号码或者是编号
	 * @param arg1 详细JSON信息
	 * @param model 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("newTab")
	public String newTab(HttpServletRequest request , String arg0 , String arg1 , Model model) throws UnsupportedEncodingException{
		
		String callSession_uuid = "";
		JSONObject jsonObject ;
		String jsonInfo = arg0;
		if(arg0.contains("(")){
			arg0 = arg0.replace("(", "{").replace(")", "}");
			arg1 = arg0;
			jsonObject = new JSONObject(arg0);
			callSession_uuid = jsonObject.optString("call_session_uuid");
			arg0 = jsonObject.optString("caller_id");
			
			if(arg1.contains("controller")){
				
				String name = jsonObject.optString("controller");
				String phone = jsonObject.optString("caller_id");
				if(StringUtils.isBlank(phone)) {
					phone = jsonObject.optString("number");
				}
				//是否已经传递参数
				if(indexService.getUrlMap().get(name).getActionURL().contains("?")){
					
					return "redirect:/" + indexService.getUrlMap().get(name).getActionURL()+"&arg1=" + jsonInfo +"&arg0" + phone;
				}
				return "redirect:/" + indexService.getUrlMap().get(name).getActionURL()+"?arg1=" + jsonInfo +"&arg0=" + phone;
			}
		}
		CustomerController customerController =  (CustomerController) ApplicationHelper.getApplicationContext().getBean("customerController");

		//根据编号或者电话查询客户信息
		customerController.getDataByIdOrPhone(arg0, model, request);
		
		setPhoneDetailfInfo(model,arg0);
		
		//根据号码查询匹配的客户数
		List<Map<String, Object>> map = customerService.getCustomersInfoByIdOrPhone(arg0);
		if(null != map && map.size()>=0 ){
			model.addAttribute("sameCount", map.size());
			List<Customer> list = new ArrayList<Customer>();
			for (int i = 0; i < map.size(); i++) {
				Customer customer = new Customer();
				customer.setCustomerId((String) map.get(i).get("customer_id"));
				customer.setCustomerName((String) map.get(i).get("cstm_name"));
				list.add(customer);
			}
			
			model.addAttribute("sameList", list);
		}
		
		getBussinessInfo(model);
		
		model.addAttribute("location", CallManager.getPhoneLocation(arg0));
		//呼叫日志信息
		getCallLogInfo(model);
		
		//存放电话详细信息
		saveCallInfoToModel(model,arg1);
		
		if(StringUtils.isNotBlank(callSession_uuid)){
			
			model.addAttribute("callsessionuuid", callSession_uuid);
		}
		
		model.addAttribute("currentUser", SessionUtil.getCurrentUser(request));
		
		UserData entry = newUserDataService.getUserDataByPhone(SessionUtil.getCurrentUser(request).getDepartment(), arg0);
		model.addAttribute("entry", entry);
		
		//在个人配置中获取到默认选择的值,如果没有则给默认值
		User user = SessionUtil.getCurrentUser(request);
		String  defUserCfg = "{\"time\":\"5\",\"timing\":\"1\",\"nocall\":\"noanswer\",\"intent\":\"global_share\"}";
		JSONObject uc = new JSONObject((userConfigService.getByUuid(user.getUid())==null?defUserCfg:userConfigService.getByUuid(user.getUid()).getConfig()));
		model.addAttribute("userconfig", uc.optString("intent","global_share"));
		
		
		model.addAttribute("iframecontent","screenpop/finalTab");
		return "iframe";
		
//		return "screenpop/newTab";
		
	}

	/**
	 * 传递JSON的时候，如果存在大括号，替换。
	 * @param name
	 * @param detail
	 * @return
	 */
	private String replaceCharacter(String name, String detail) {
		if(name.equals("deptGetDataCount") || name.equals("newdatacall") || "deptGetOwnCount".equals(name) || "getAbandonCount".equals(name) || "getBlackListCount".equals(name) || "getIntentCount".equals(name)){
			detail = detail.replace("{", "(").replace("}", ")");
		}
		return detail;
	}
	
	/**
	 * //存放电话详细信息 热线号码 来电描述 
	 * @param model 
	 * @param arg1
	 */
	private void saveCallInfoToModel(Model model, String arg1) {

		//如果arg1不为空
		if(StringUtils.isNotBlank(arg1)){
			try {
				JSONObject jsonObject = new JSONObject(arg1);
				getPhoneDetails(model, jsonObject);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void getPhoneDetails(Model model, JSONObject jsonObject) {
		//呼叫时间
		model.addAttribute("timestamp", jsonObject.optString("timestamp",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
		//通话类型
		model.addAttribute("call_direction", jsonObject.optString("call_direction","无"));
		//来电描述
		model.addAttribute("ivr_desc", jsonObject.optString("ivr_desc","无"));
		//热线号码
		model.addAttribute("access_number", jsonObject.optString("access_number","无"));
	}

	/**
	 * 弹出新的窗体
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param detailq
	 * @return
	 */
	@RequestMapping("addWindow")
	public String addWindow(HttpServletRequest request, HttpServletResponse response,RedirectAttributesModelMap modelMap,String detailq) {
		
		String name = null ,urls ;
		
		//如果是查询
		if(StringUtils.isNotBlank(detailq)){
			
			/*要展示的tab*/
			JSONObject detail = new JSONObject();
			detail.put("info",new JSONObject().put("poplog", "record").put("order", "order"));
			
			modelMap.addFlashAttribute("param", detailq);
			modelMap.addFlashAttribute("call_session_uuid", request.getParameter("call_session_uuid"));
			
			name = getShowTabs(modelMap, detail);
			urls = detail.toString();
			modelMap.addFlashAttribute("params", detailq);
			
			//弹屏发送的时候加密 返回密文
			String sendStr = indexService.encrypt(detailq);
			return "redirect:/" + indexService.getUrlMap().get(name).getActionURL()+"?requestParams="+sendStr+"&call_session_uuid="+request.getParameter("call_session_uuid");
			
		}else{
			
			String paraString = null;
			/*
			 * 拨打电话
			 */
			//先取值
			String infos = request.getParameter("detail");
			
			if(StringUtils.isNotBlank(infos) && !"{}".equals(infos) ){
				
				JSONObject detail = new JSONObject(infos);
				
//				二次转发
				if(detail != null && StringUtils.isBlank(detailq)){
					
					if(detail.optString("type").equals("pop_event")){
						
						paraString = detail.optString("caller_id","");
						modelMap.addFlashAttribute("params", detail.optString("caller_id",""));
						modelMap.addFlashAttribute("param", paraString);
						
						modelMap.addFlashAttribute("caller_id", detail.optString("caller_id",""));
						modelMap.addFlashAttribute("access_number", detail.optString("access_number",""));
						modelMap.addFlashAttribute("timestamp", detail.optString("timestamp",""));
						modelMap.addFlashAttribute("ivr_desc", detail.optString("ivr_desc",""));
						modelMap.addFlashAttribute("location", CallManager.getPhoneLocation(detail.optString("caller_id","")));
						
						modelMap.addFlashAttribute("call_session_uuid", detail.optString("call_session_uuid",""));
						
						name = getShowTabs(modelMap, detail);
						
						//查询所有列
						JSONObject jsonObject1 =  callLogService.getTitleAndData(divTableService);
						divTableService.getTitles(modelMap, jsonObject1);
						
					}
					if(detail.optString("type").equals("user_call")){
						
					}
					if(detail.optString("type").equals("startCall")){
						
						modelMap.addFlashAttribute("call_session_uuid", detail.optString("call_session_uuid",""));
						
						JSONObject jsonObject =detail.getJSONObject("info");
						
						Map<String, String> showTab = new LinkedHashMap<String, String>();
						
						for (Object str : jsonObject.keySet()) {
							
							showTab.put(String.valueOf(str), PopEventHandler.allBusiness.get(jsonObject.get(str.toString())));
						}
						name="startCall";
						modelMap.addFlashAttribute("showTabs", showTab);
					}
					
					//加密发送的号码
					String callSessionUUid =request.getParameter("call_session_uuid");
					if(StringUtils.isNotBlank(callSessionUUid)){
						
						paraString = detail.optString("caller_id","");
						modelMap.addFlashAttribute("params", detail.optString("caller_id",""));
						modelMap.addFlashAttribute("param", paraString);
						
						callSessionUUid = detail.optString("call_session_uuid","");
						name="addInfo";
					}
					
					String sendStr = null;
					if(StringUtils.isNotBlank(paraString)){
						
						
//						byte[] encryptResult = AESTools.encrypt(StringUtils.isBlank(detailq)?detail.getString("caller_id"):detailq, "shrsrjjsyxgs");
//						sendStr = AESTools.parseByte2HexStr(encryptResult);

						//加密
						sendStr = indexService.encrypt(StringUtils.isBlank(detailq)?detail.getString("caller_id"):detailq);
						
//						System.out.println("jimihou----------||"+sendStr);
					}
					
					return "redirect:/" + indexService.getUrlMap().get(name).getActionURL()+"?requestParams="+sendStr+"&call_session_uuid="+callSessionUUid;
				}
			}
		}
		
		return null;
		
	}

	
	/**
	 * 
	 * 传递客户电话号码【针对刷新页面，取到页面传递过来的参数】
	 * @param agentId
	 * @param accessNumber
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("pop_event")
	public String addBlankTab(HttpServletRequest request,Model model,String requestParams,RedirectAttributesModelMap modelMap) throws UnsupportedEncodingException{
		
		String strString = request.getParameter("requestParams");
		//解密
		byte[] decryptFrom = AESTools.parseHexStr2Byte(strString);
		byte[] decryptResult = AESTools.decrypt(decryptFrom, "shrsrjjsyxgs");
		String strString1 = new String(decryptResult,"UTF-8");
 		model.addAttribute("params", strString1);
 		
 		System.out.println("弹屏：客户编号----------||"+strString1);
		
		getBussinessInfo(model);
		
		//查询所有列
		JSONObject jsonObject =  callLogService.getTitleAndData(divTableService);
		divTableService.getTitles(model, jsonObject); 
		
		
		return "screenpop/customermainpop";
	}
	
	/**
	 * 要显示的所有业务逻辑
	 * @param modelMap
	 * @param detail
	 * @return
	 */
	public String getShowTabs(Model modelMap, JSONObject detail) {
		
		JSONObject jsonArray =detail.getJSONObject("info");
		Map<String, String> showTab = new LinkedHashMap<String, String>();
		for (Object str : jsonArray.keySet()) {
			
			showTab.put(String.valueOf(str), PopEventHandler.allBusiness.get(jsonArray.get(str.toString())));
		}
		
		if(modelMap instanceof RedirectAttributesModelMap){
			((RedirectAttributesModelMap) modelMap).addFlashAttribute("showTabs", showTab);
		}
		
		//查询所有列
		JSONObject jsonObject =  callLogService.getTitleAndData(divTableService);
		divTableService.getTitles(modelMap, jsonObject);
		
		return "addInfo";
	}



	@RequestMapping("dailysummary")
	public String daily(){
		return "cstm/dailysummary";
	}
	
	@RequestMapping("homepage")
	public String homepage(HttpServletRequest request, Model model){

		Map<String, com.ruishengtech.rscc.crm.ui.sysConfig.model.SysConfig> map = SysConfigManager.getInstance().getDataMap();
		model.addAttribute("companyname", map.get("companyname").getSysVal());
		model.addAttribute("companyphone", map.get("companyphone").getSysVal());
		model.addAttribute("companyadd", map.get("companyadd").getSysVal());

        model.addAttribute("iframecontent","index/homepage");
		return "iframe";
		
//		return "index/homepage";
	}
	
	@RequestMapping("agentnotices")
	@ResponseBody
	public String agentnotices(HttpServletRequest request) throws ParseException{
		List<AgentNotice> ans = agentNoticeService.getAgentNoticesLimitFive(SessionUtil.getCurrentUser(request).getUid());
		if (ans != null) {
			for (AgentNotice an : ans) {
				an.setPubTime((fmts.format(an.getPublishTime())));
	//			an.setPublishTime(fmts.parse(fmt.format(an.getPublishTime())));
			}
		}
		return new JSONObject().put("ans", ans).toString();
	}
	
	/* 每日小结 */
	@RequestMapping("daily")
	@ResponseBody
	public String dailySummary(HttpServletRequest request) {

		return AgentStatus.getAgentStatus(SessionUtil.getCurrentUser(request).getLoginName()).getString("ret").toString();
	}

	/**
	 * 删除tab
	 * @param request
	 * @param response
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("deletetab")
	@ResponseBody
	public String deletetab(HttpServletRequest request,HttpServletResponse response, String name,String phone) {
		
		LinkedHashMap<String, TableBeans> linkedHashMap = (LinkedHashMap<String, TableBeans>) request.getSession().getAttribute(TableBeans.TAB);
		
		if (linkedHashMap != null) {
			linkedHashMap.remove(name);
		}
		request.getSession().setAttribute(TableBeans.TAB, linkedHashMap);
		return new JSONObject().put("success", true).toString();
	}

	@RequestMapping("logout")
	public String loginout(HttpServletRequest request, String type) {
		User user = (User) request.getSession().getAttribute(
				SessionUtil.CURRENTUSER);
		if (user != null) { // 登出日志
			JSONObject num = AgentManager.extenUbind(user);
			AgentManager.agentMap.removeValue(user.getLoginName());
			if (("0").equals(num.opt("exit_code"))) {
				
				
			}

			// //记录日志
		}
		//关闭网页时
		if("close".equals(type))
			return "";
		request.getSession().invalidate();
		return "redirect:/";
	}
	
	/**
	 * @param phone
	 * @return
	 */
	@RequestMapping("getAllBusniess")
	public String getCustomerBuniessByUuid(String cstmId,Model model){
		
		return "screenpop/customermainpop";
	}
	
	/**
	 * 分机解绑
	 * @param user
	 */
	@RequestMapping("ubind")
	@ResponseBody
	public String extenUbind(HttpServletRequest request, String exten, String uname, String upwd, Model model,String operation){
		//根据分机号查询坐席号
		JSONObject json = AgentManager.getBindAgent(exten);
		String agentid = json.getString("agent_id");
		//解绑坐席
		AgentManager.kickOffExten(agentid);
		//绑定新的坐席和分机号
		User user = new User();
		user.setLoginName(uname);
		user.setPassword(upwd);
		AgentManager.extenBind(user,exten);
		if("bindexten".equals(operation)){
			// 存放登录分机
			request.getSession().setAttribute(SessionUtil.LOGINEXTEN, exten);
			AgentManager.agentMap.put(exten, uname);
			// 销毁session
			request.getSession().removeAttribute("unexten");
		}
		
		//推送消息至前台告诉已被解绑
		JSONObject js = new JSONObject();
		js.put("type", "extenubind");
		try {
			brokerService.sendToUser("/user", agentid, js.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new JSONObject().put("success", true).toString();
	}
	
	
	@RequestMapping("unbind")
	@ResponseBody
	public String extenUnbind(HttpServletRequest request, String ext, String uname, String upwd){
		User user = new User();
		user.setLoginName(uname);
		user.setPassword(upwd);
		JSONObject result = AgentManager.extenUbind(user);
		if(result != null){
			request.getSession().removeAttribute(SessionUtil.LOGINEXTEN);
			AgentManager.agentMap.remove(ext);
			request.getSession().setAttribute("unexten","未输入分机");
			return new JSONObject().put("success", true).toString();
		}
		return new JSONObject().put("success", false).toString();
	}
	
	/**
	 * 弹出绑定分机的页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getBindExten")
    public String getData(String uname, String upwd, String ext, Model model){
		model.addAttribute("uname", uname);
		model.addAttribute("upwd", upwd);
		model.addAttribute("ext", ext);
        return "index/bindexten_save";
    }
	

	/**
	 * 获取业务逻辑信息
	 * @param model
	 */
	public void getBussinessInfo(Model model) {
		
		JSONObject detail = new JSONObject();
		detail.put("info",new JSONObject().put("poplog", "record"));

		JSONObject jsonArray =detail.getJSONObject("info");
		Map<String, String> showTab = new LinkedHashMap<String, String>();
		for (Object str : jsonArray.keySet()) {
			showTab.put(String.valueOf(str), PopEventHandler.allBusiness.get(jsonArray.get(str.toString())));
		}
		
		model.addAttribute("showTabs", showTab);
	}

	/**
	 * 呼叫日志信息
	 * @param model
	 */
	public void getCallLogInfo(Model model) {
		//查询所有列
		JSONObject jsonObject =  callLogService.getTitleAndData(divTableService);
		divTableService.getTitles(model, jsonObject); 
		
		/* 得到所有列 */
		Map<String, ColumnDesign> mapss = customerDesignService.getAddedColumn("sys_call_log");
		model.addAttribute("mapss", mapss);
	}
	

	
	@RequestMapping("checkExten")
	@ResponseBody
	public String checkExten(HttpServletRequest request, String exten, String uname, String upwd, Model model){
		User user = new User();
		user.setLoginName(uname);
		user.setPassword(upwd);
		
		JSONObject code =  AgentManager.extenBind(user,exten);
		if(!("0").equals(code.optString("exit_code"))){
			if("分机已经被注册,是否踢掉?".equals(code.optString("err_msg"))){
				return new JSONObject().put("success", true).put("registered", "该分机已被注册,是否踢掉?").toString();
			}else{
				return new JSONObject().put("success", true).put("checkresult", code.optString("err_msg")).toString();
			}
		}else if(("0").equals(code.optString("exit_code"))){
			if("false".equals(code.opt("exten_register_status"))){
				return new JSONObject().put("success", true).put("checkresult", "该分机未注册").toString();
			}
		}
		// 存放登录分机
		request.getSession().setAttribute(SessionUtil.LOGINEXTEN, exten);
		AgentManager.agentMap.put(exten, uname);
		String[] queues = QueueManager.getAgentQueueArray(uname);
		request.getSession().setAttribute("queues", queues);
		// 销毁session
		request.getSession().removeAttribute("unexten");
		return new JSONObject().put("success", false).toString();
	}

	@RequestMapping("bindExtenSave")
	@ResponseBody
	public String bindExtenSave(String extension, User user, Model model){
		return new JSONObject().put("success", true).toString();
	}
	
	
	@RequestMapping("dailySum")
	public String dailySum(){
		return "dailysummary/dailysummary";
	}
	
	@RequestMapping("queStates")
	public String queueState(Model model,HttpServletRequest request){
		model.addAttribute("queueNameMap", QueueManager.getQueueNameMap());
		model.addAttribute("allQueue", request.getSession().getAttribute("allQueues"));
		return "dailysummary/questate";
	}

	@RequestMapping("queueState/{queueId}")
	public String queueState(@PathVariable String queueId, Model model){
		model.addAttribute("queueId", queueId);
		return "dailysummary/queuestate";
	}
	
	@RequestMapping("queue")
	@ResponseBody
	public String queue(String queueId, Model model){
		JSONArray jsonArray = QueueManager.getQueueRuntime(queueId);
		return jsonArray.length() > 0 ? jsonArray.getJSONObject(0).toString() : new JSONObject().toString();
	}
	
	/**
	 * 刷新动态表缓存
	 */
	@RequestMapping("reload")
	public void reloadCatch(){
		
		divTableService.load();
	}
	
	/**
	 * 刷新配置缓存
	 */
	@RequestMapping("reloadConfig")
	public void reloadConfig(){
		
		configService.reload();
	}
	
	/**
	 * 刷新请求路径
	 */
	@RequestMapping("reloadUrl")
	public void reloadUrl(){
		
		indexService.initUrl();
	}
	
	/**设置电话详细信息
	 * @param model
	 * @param arg0
	 */
	private void setPhoneDetailfInfo(Model model, String arg0) {}
	
	/**
	 * 发送请求 获取当前状态 保存当前状态
	 * @param request
	 */
	@RequestMapping("saveStatus")
	public void saveCurrentStatus(HttpServletRequest request) {
		
		JSONObject jsonObject = AgentStatus.getAgentStatus(SessionUtil.getCurrentUser(request).getLoginName());
		if("0".equals(jsonObject.optString("exit_code"))){
			
		}
		
		SessionUtil.setSession(request, "cStatus", "");
	}

	/**
	 * 还原ag开始的状态
	 * @return
	 */
	@RequestMapping("reBackAgentStatus")
	@ResponseBody
	public String reBackAgStatus(HttpServletRequest request) {
		
		User loginedUser = SessionUtil.getCurrentUser(request);
		
		String msg = "";
		
		//还原上次闲忙状态
		Object reason = request.getSession().getAttribute("pause");
		if (reason == null) {
			msg = "nobusy";
			CallManager.unpauseCall(loginedUser.getLoginName());
		} else {
			CallManager.pauseCall(loginedUser.getLoginName(), reason.toString());
		}
		
		return new JSONObject().put("success", true).put("msg", msg).toString();
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("setNoBusy")
	@ResponseBody
	public String setNoBusy(HttpServletRequest request){

		request.getSession().removeAttribute("pause");
		
		return new JSONObject().toString();
	}
	
	
	/**
	 * 关于系统
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("about")
	public String about(Model model) throws Exception {
		
		indexService.getSystemInfo(model);
		
		model.addAttribute("iframecontent","about");
		return "iframe";
		
//		return null;
	}
	
	/**
	 * 关于系统
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("version")
	@ResponseBody
	public String version(Model model) throws Exception {
		
		String version = null;
		
		try {
			version = SpringPropertiesUtil.getProperty("sys.tag.info");
		} catch (Exception e) {
			return StringUtils.trimToNull(version) == null?"暂未记录":version;
		}
		return "系统版本信息为："+version;
	}
	
	
	public static void main(String[] args) {
		
		JSONArray array = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("s", 1).put("imsDjwgIp", "1.1.1.1");
		jsonObject.put("q", 1).put("imsDjwgIp", "1.1.1.2");
		array.put(jsonObject);
		
		String imsDjwgIp = "1.1.1.2";
		
		if(array.toString().contains(imsDjwgIp)){
			
		}
		
	}

	
	
	
}
