package com.ruishengtech.rscc.crm.ui.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ruishengtech.framework.core.QueryUtils;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.rscc.crm.data.manager.insertData;
import com.ruishengtech.rscc.crm.datamanager.condition.UserDataLogCondition;
import com.ruishengtech.rscc.crm.datamanager.model.DataBatch;
import com.ruishengtech.rscc.crm.datamanager.model.UserDataLog;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.service.imp.UserDataLogServiceImp;
import com.ruishengtech.rscc.crm.report.condition.ReportDataBatchCondition;
import com.ruishengtech.rscc.crm.report.model.ReportDataBatch;
import com.ruishengtech.rscc.crm.report.service.imp.ReportDataBatchServiceImp;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.mw.model.Domains;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Controller
@RequestMapping("report/databatch")
public class ReportDataBatchController {
	
	@Autowired
	private UserDataLogServiceImp userDataLogService;
	
	@Autowired
	private DataBatchService dbService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReportDataBatchServiceImp reportDataBatchServiceImp;
	
	SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
	@RequestMapping
	public String index(HttpServletRequest request, HttpServletResponse response,Model model){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		model.addAttribute("yesterdaystart", sdf.format(cal.getTime())+"  00:00:00");
		model.addAttribute("yesterdayend", sdf.format(new Date())+"  00:00:00");
		
		Calendar calm = Calendar.getInstance();
		calm.add(Calendar.MONTH, -1);
		calm.set(Calendar.DAY_OF_MONTH, 01);
		model.addAttribute("agomonthstart", sdf.format(calm.getTime())+"  00:00:00");
		Calendar calml = Calendar.getInstance();
		calml.setTime(QueryUtils.getFirstDayOfThisMonth());
		calml.set(Calendar.DAY_OF_MONTH, 1);
		model.addAttribute("agomonthend", sdf.format(calml.getTime())+"  00:00:00");
		
//		model.addAttribute("titles", new String[]{"时间","坐席","通话数量","成交客户量","客户新增","客户减少","意向客户","意向新增","意向减少","转共享量","无人应答数量","拉黑数量","转废号量"});
//		model.addAttribute("columns", new String[]{"optime","agent","callcount","cstmcount","cstmaddcount","cstmdelcount","intentcount","intentaddcount","intentdelcount","globalsharecount","noanswercount","blacklistcount","abandoncount"});
		model.addAttribute("titles", new String[]{"时间","坐席","成交客户净增量","成交客户新增量","成交客户减少量","意向客户净增量","意向客户新增量","意向客户减少量"});
		model.addAttribute("columns", new String[]{"optime","agent","cstmcount","cstmaddcount","cstmdelcount","intentcount","intentaddcount","intentdelcount"});
		
		model.addAttribute("iframecontent","report/reportdatabatch");
		return "iframe";
		
		
//		return "report/reportdatabatch";
		
		
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request,HttpServletResponse response,Model model,ReportDataBatchCondition dataBatchCondition){
		dataBatchCondition.setRequest(request);
		//获取当前登录用户，进行权限控制
		dataBatchCondition.setLoginuser(SessionUtil.getCurrentUser(request).getUid());
		
		//获取当前用户管理下的所有未被删除的坐席id
		Collection<String> loginnames = userService.getManagerUsernames(SessionUtil.getCurrentUser(request).getUid());
		Collection<String> loginids =  new ArrayList<>();
		for(String loginname : loginnames){
			//获取到没有删除的所有用户id
			loginids.add(userService.getUserByLoginName(loginname, false).getUid());
		}
		dataBatchCondition.setShowagents(loginids);
		
		PageResult<ReportDataBatch> pageResult = reportDataBatchServiceImp.querypage(dataBatchCondition);
		
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List<ReportDataBatch> list = pageResult.getRet();
		for(ReportDataBatch d : list){
			JSONObject jsonObject2 = new JSONObject(d);
			jsonObject2.put("cstmdelcount", d.getCstmdelcount()<0?String.valueOf(d.getCstmdelcount()).substring(1,String.valueOf(d.getCstmdelcount()).length()):d.getCstmdelcount());
			jsonObject2.put("intentdelcount", d.getIntentdelcount()<0?String.valueOf(d.getIntentdelcount()).substring(1,String.valueOf(d.getIntentdelcount()).length()):d.getIntentdelcount());
			if(dataBatchCondition.getSelectionReport().equals("day")){
				jsonObject2.put("optime", d.getOptime().substring(0, 10));
			}else if(dataBatchCondition.getSelectionReport().equals("month")){
				jsonObject2.put("optime", d.getOptime());
			}
			jsonObject2.put("agent", userService.getByUuid(UUID.UUIDFromString(d.getAgent())).getUserDescribe());
			jsonArray.put(jsonObject2);
		}
		Map<String,Object> map = reportDataBatchServiceImp.getAllReportDataBatch(dataBatchCondition);
		//{sumcstmadd=2880, sumcstmdel=-4320, sumcstm=-1440, sumintentadd=2880, sumintentdel=-4320, sumintent=-1440}
		jsonObject.put("statistics", new JSONObject(map));
		
		jsonObject.put("data", jsonArray);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());	
		
		return jsonObject.toString();
	}
}
