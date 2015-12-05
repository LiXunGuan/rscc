package com.ruishengtech.rscc.crm.ui.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.user.condition.UserCondition;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("user")
public class UserIndexController {
	@Autowired
	private UserService userService;

	@RequestMapping()
	public String index(Model model) {

		
		model.addAttribute("iframecontent","user/user-index");
		return "iframe";
		
//		return "user/user-index";
	}
	
	@RequestMapping("redirect")
	@ResponseBody
	public String redirect(HttpServletRequest request, String adminName, String adminPassword){
		
		User oldUser = SessionUtil.getCurrentUser(request);
		User loginedUser = userService.login(adminName,adminPassword);
		
		if (null != loginedUser) {
			loginedUser.setRealUserName(oldUser.getLoginName());
			loginedUser.setRealUserUuid(oldUser.getUid());
			
			if(!loginedUser.getAdminFlag().equals("1")){
				
				return "doLogin";
			}
			
			//设置现在的session
			SessionUtil.setSession(request, SessionUtil.CURRENTUSER, loginedUser);
			
			return "user";
		}
		
		return "doLogin";
		
		
	}

	
	/*如果登录成功，跳转到中间件管理界面，否则不做任何操作*/
	@RequestMapping("dologin")
	public String dologin(HttpServletRequest request, String adminName, String adminPassword) {
		
		User loginedUser = userService.login(adminName,adminPassword);
		SessionUtil.setSession(request, SessionUtil.CURRENTUSER, loginedUser);
		
		if (null != loginedUser) {
			
			//如果是管理员，跳转到中间件管理界面
			if("1".equals(loginedUser.getAdminFlag())){

				return "redirect:"+SpringPropertiesUtil.getProperty("sys.data.call.admin")+"";
			}
		}
		return "tologin";
	}
	
	@RequestMapping("save")
	public String addUser(HttpServletRequest request,
			HttpServletResponse response, User user,
			Model model) {
		user.setDate(new Date());
//		userService.save(user);
		return "user/data";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response, UserCondition userCondition,
			Model model) {
		userCondition.setRequest(request);
		PageResult<User> pageResult = userService.queryPage(userCondition);

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<User> list = pageResult.getRet();
		for (User i:list) {
			JSONObject jsonObject = new JSONObject(i);
//			jsonObject.put("loginName", i.getLoginName());
//			jsonObject.put("password", i.getPassword());
//			jsonObject.put("phone", i.getPhone());
//			jsonObject.put("userDescribe", i.getUserDescribe());
//			jsonObject.put("date", i.getDate());
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
}
