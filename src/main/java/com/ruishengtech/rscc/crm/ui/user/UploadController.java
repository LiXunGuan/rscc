package com.ruishengtech.rscc.crm.ui.user;

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
import org.springframework.web.multipart.MultipartFile;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.rscc.crm.user.condition.UserCondition;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

/**
 * @author Wangyao
 *
 */
@Controller
@RequestMapping("user/upload")
public class UploadController {
	@Autowired
	private UserService userService;

	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("model", "user");
		model.addAttribute("items", new String[]{"用户名","手机号","用户描述"});
		model.addAttribute("names", new String[]{"loginName","phone","userDescribe"});
		model.addAttribute("titles", new String[]{"用户名","密码","手机号","用户描述","添加日期"});
		model.addAttribute("columns", new String[]{"loginName","password","phone","userDescribe","date"});
		
		model.addAttribute("iframecontent","user/upload-index");
		return "iframe";
		
//		return "user/upload-index";
	}
	
	@RequestMapping("test")
	@ResponseBody
    public String setMainPhoto(HttpServletRequest request,MultipartFile multipartFile) throws Exception{
    	
    	if(multipartFile != null){
    		
			// 上传图片
    		QueryUtils.uploadFile(multipartFile, ApplicationHelper.getContextPath() + "/upload/test");
			 
			return new JSONObject().put("success", true).toString();
    	}
    	return new JSONObject().put("success", false).toString();
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
			jsonArray.put(jsonObject);
		}

		jsonObject2.put("data", jsonArray);
		jsonObject2.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject2.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());		
		return jsonObject2.toString();
	}
}
