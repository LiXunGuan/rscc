package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.IOException;
import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.rscc.crm.ui.mw.condition.AccessNumberCondition;
import com.ruishengtech.rscc.crm.ui.mw.manager.CallRouteManager;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumber;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumberGateway;
import com.ruishengtech.rscc.crm.ui.mw.model.FSQueue;
import com.ruishengtech.rscc.crm.ui.mw.model.FSSipTrunk;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.mw.service.AccessNumberService;
import com.ruishengtech.rscc.crm.ui.mw.service.MWSYSConfigService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("config/accessnumber")
public class AccessNumberController {

	@Autowired
	private AccessNumberService accessnumberService;
	
	@Autowired
    private MWSYSConfigService sysConfigService;
	
	@Autowired
	private CallRouteManager callRouteManager;
	
	@RequestMapping
	public String Batch(HttpServletRequest request,
			HttpServletResponse response, Model model, String str) {
		
		model.addAttribute("iframecontent","config/accessnumber");
		return "iframe";
		
//		return "config/accessnumber";
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
			HttpServletResponse response, AccessNumberCondition accessNumberCondition) {

		accessNumberCondition.setRequest(request);

		PageResult<AccessNumber> pageResult = accessnumberService.page(accessNumberCondition);		//获取符合查询条件的数据
		List<AccessNumber> list = (List<AccessNumber>) pageResult.getRet();
		
		JSONArray jsonArray = new JSONArray();
		
		JSONObject jsonObject = new JSONObject();
		
		SysConfig config = sysConfigService.getSysConfig("defaultaccessnumber");
		
		for (int i = 0; i < list.size(); i++) {	
			JSONObject jsob = new JSONObject(list.get(i));
			jsob.put("id", list.get(i).getId());
			jsob.put("accessnumber", StringUtils.trimToEmpty(list.get(i).getAccessnumber()));
			jsob.put("canout",list.get(i).getCanout().equals("1")?true:false);
			jsob.put("canin", list.get(i).getCanin().equals("1")?true:false);

			jsob.put("defaultaccessnumber", config != null ? (new JSONObject(config.getVal()).get("accessnumber").equals(StringUtils.trimToEmpty(list.get(i).getAccessnumber())) ? "是" : "否") : "否");
			
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
        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>呼叫中心配置&nbsp;&nbsp;>&nbsp;<span>接入号管理</span>");
        return jsob.toString();
    }

	@RequestMapping("get")
	public String getScopExt(HttpServletRequest request,
			HttpServletResponse response, Long id, Model model)
			throws SocketException {
			if (id != null) {
				AccessNumber item =accessnumberService.get(id);
				model.addAttribute("item",item);
				
				SysConfig config = sysConfigService.getSysConfig("defaultaccessnumber");
				if (config!=null) {
					JSONObject jsob = new JSONObject(config.getVal());
					if(jsob.optString("accessnumber").equals(item.getAccessnumber())){
						model.addAttribute("defaultaccessnumber", true);
					}
				}
			}
        return "config/accessnumber_save";
	}

	@RequestMapping("addItem")
	public String addItem(HttpServletRequest request,
			HttpServletResponse response, Long id, Model model)
			throws SocketException {
		List<FSSipTrunk> gatewayls=accessnumberService.getAll(FSSipTrunk.class);
		List<AccessNumberGateway> angwls=accessnumberService.getAccessNumberGateway(id);
		List<FSSipTrunk> gatewayCheckls=new ArrayList<FSSipTrunk>();
		if (gatewayls!=null&&angwls!=null) {
			for (int i = 0; i < gatewayls.size(); i++) {
				for (int j = 0; j < angwls.size(); j++) {
					if (gatewayls.get(i).getId().equals(angwls.get(j).getGateway_id()) ) {
						gatewayCheckls.add(gatewayls.get(i));
						gatewayls.remove(i);
						i=i-1;
						break;
					}
				}
			}
		}
		
		model.addAttribute("id", id);
		model.addAttribute("gatewayls", gatewayls);
		model.addAttribute("gatewayCheckls", gatewayCheckls);
		
		model.addAttribute("iframecontent","config/acceaanumber_gateway");
		return "iframe";
		
//        return "config/acceaanumber_gateway";
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
		AccessNumber accessnumber= accessnumberService.getAccessNumber(name);
		if (accessnumber==null) {
			response.getWriter().print(true); // 直接打印true 通过
		}else{
			if (id==null) {
				response.getWriter().print(false);
			}else{
				if (id.equals(accessnumber.getId())) {
					response.getWriter().print(true);
				}else{
					response.getWriter().print(false);
				}
			}
		}
	}

	/**
	 * 保存
	 * @param request
	 * @param fsGateWay
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request, AccessNumber accessNumber, String defaultaccessnumber) throws ParseException {
		if (accessNumber.getCanin() == null) {
			accessNumber.setCanin("0");
		}else{
			accessNumber.setCanin("1");
		}
		if (accessNumber.getCanout() == null) {
			accessNumber.setCanout("0");
		}else{
			accessNumber.setCanout("1");
		}
		
		// 判断默认接入号配置
		if(defaultaccessnumber != null) {// 默认接入号
			
			SysConfig config = sysConfigService.getSysConfig("defaultaccessnumber");
			
			SysConfig sc = new SysConfig();
			sc.setId(config.getId());
			
			JSONObject jsob = new JSONObject();
			jsob.put("accessnumber", StringUtils.trimToEmpty(accessNumber.getAccessnumber()));
			jsob.put("content", StringUtils.trimToEmpty(""));
			sc.setVal(jsob.toString());
			sc.setName("defaultaccessnumber");
			sysConfigService.saveOrUpdate(sc);

	        callRouteManager.loadDefaultGateway();
	        
			ExtenRouteController.isApply=true;
			
		}
		
		accessnumberService.saveOrUpdate(accessNumber);
		return new JSONObject().put("success", true).toString();
	}
	

	/**
	 * 保存
	 * @param request
	 * @param fsGateWay
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("saveag")
	@ResponseBody
	public String saveAQ(HttpServletRequest request, Long id) throws ParseException {
		String[] ls=request.getParameterValues("gatewayId");
		accessnumberService.removAccessNumberGateway(id);
		if (ls!=null) {
			for (int i = 0; i < ls.length; i++) {
				AccessNumberGateway angw =new AccessNumberGateway();
					angw.setAccessnumber_id(id);
					angw.setGateway_id(Long.valueOf(ls[i]));
					accessnumberService.save(angw);
			}
		}
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
	public String deploy(HttpServletRequest request, FSQueue fsQueue) {
//		fsQueueService.toFSXML();
//		fsQueueService.deploy();
//		isApply=false;
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
		accessnumberService.removAccessNumberGateway(id);
		accessnumberService.remove(id);
		return new JSONObject().put("success", true).toString();
	}
	
	 /**
     * 弹框  批量增加
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("getAll")
    public String getDataAll(HttpServletRequest request,
                          HttpServletResponse response,Model model){
        return "config/acceaanumber_addAll";
    }

    @RequestMapping("saveAll")
    @ResponseBody
    public String saveAll(HttpServletRequest request,AccessNumber accessNumber,Integer num) throws ParseException {
    	//得到添加的起始接入号
    	Integer number = Integer.valueOf(accessNumber.getAccessnumber());
    	//查询要添加范围内的所有接入号
    	List<AccessNumber> numbers = accessnumberService.getNumbersByNum(accessNumber.getAccessnumber(), String.valueOf(number + num));
    	//用来保存所有接入号
    	List<String> strls = new ArrayList<String>();	
    	if(numbers != null){
    		for(AccessNumber an : numbers){
				strls.add(an.getAccessnumber());
			}
    	}
    	
    	accessNumber.setAccessnumber(String.valueOf(number - 1));
    	number = number - 1;
    	
    	for (int i = 0; i < num; i++) {//循环添加接入号
			number = number + 1; //得到当前接入号
			accessNumber.setAccessnumber(String.valueOf(number));//赋值接入号
			if(numbers != null){//判断已存在的接入号
				if(strls.contains(accessNumber.getAccessnumber())){
					AccessNumber an = accessnumberService.getAccessNumber(accessNumber.getAccessnumber());
					an.setCanin(accessNumber.getCanin());
					an.setCanout(accessNumber.getCanout());
					an.setConcurrency(accessNumber.getConcurrency());
					save(request,an,null);
					continue;
				}
			}
			//新增接入号
			AccessNumber anu = new AccessNumber();
			anu.setCanin(accessNumber.getCanin());
			anu.setCanout(accessNumber.getCanout());
			anu.setAccessnumber(accessNumber.getAccessnumber());
			anu.setConcurrency(accessNumber.getConcurrency());
			save(request,anu, null);
		}
    	return new JSONObject().put("success", true).toString();
    }
    
    @RequestMapping("dataall")
    @ResponseBody
    public String dataAll(HttpServletRequest request,AccessNumber accessNumber) {
    	 List<AccessNumber> list = accessnumberService.getAll(AccessNumber.class);
    	 StringBuilder sb=new StringBuilder();
    	 if (list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i).getId()+",");
			}
		}
        return sb.toString();
    }
    
    /**
     * 批量删除
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("batchremove")
    @ResponseBody
    public String BatchRemove(HttpServletRequest request,String sb) {
    	String[] str=null;
    	if (sb!=null) {
			str=sb.split(",");
			for (int i = 0; i < str.length; i++) {
				if(!"false".equalsIgnoreCase(str[i])){
					remove(request,Long.valueOf(str[i]));
				}
			}
		}
        return new JSONObject().put("success", true).toString();
    }
}
