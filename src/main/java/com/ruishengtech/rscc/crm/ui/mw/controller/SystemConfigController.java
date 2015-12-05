package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.IOException;
import java.net.SocketException;
import java.text.ParseException;
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

import com.ruishengtech.rscc.crm.ui.mw.manager.CallRouteManager;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumber;
import com.ruishengtech.rscc.crm.ui.mw.model.FSSipTrunk;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.model.SysConfig;
import com.ruishengtech.rscc.crm.ui.mw.service.AccessNumberService;
import com.ruishengtech.rscc.crm.ui.mw.service.FSGateWayService;
import com.ruishengtech.rscc.crm.ui.mw.service.MWSYSConfigService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("config/sysconfig")
public class SystemConfigController {

	@Autowired
	private CallRouteManager callRouteManager;


    @Autowired
    private MWSYSConfigService sysConfigService;

	@Autowired 
	private FSGateWayService fsGateWayService;
	
	@Autowired
	private AccessNumberService accessNumberService;
	
	@RequestMapping
	public String Batch(Model model) {
				
			List<AccessNumber> accessNumberls=sysConfigService.getAccessNumberls();
			model.addAttribute("accessNumberls", accessNumberls);
			
			SysConfig config=sysConfigService.getSysConfig("defaultaccessnumber");
			
			if (config!=null) {
				JSONObject jsob = new JSONObject(config.getVal());
				model.addAttribute("id", config.getId());
//					model.addAttribute("gatewayName", jsob.optString("gatewayName", ""));
				model.addAttribute("accessnumber", jsob.optString("accessnumber", ""));
				model.addAttribute("content", jsob.optString("content", ""));
//				
//					List<FSGateway> fsGatewayls=fsGateWayService.getGateWayls(jsob.opt("accessnumber").toString());
//					model.addAttribute("fsGatewayls", fsGatewayls);
			}
				
			
			model.addAttribute("iframecontent","config/sysconfig");
			return "iframe";
				
		
//		return "config/sysconfig";
	}
	
    /**
     * 加载标题
     */
    @RequestMapping("menushow")
    @ResponseBody
    public String menushow(HttpServletRequest request, HttpServletResponse response, MWFsHost mwFsHost)
            throws IOException {
        JSONObject jsob = new JSONObject();
        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>呼叫中心配置&nbsp;&nbsp;>&nbsp;<span>默认路由</span>");
        return jsob.toString();
    }


	
	@RequestMapping("get")
	@ResponseBody
	public String getScopExt(HttpServletRequest request,
			HttpServletResponse response, String accessnumber, Model model)
			throws SocketException {
		JSONArray json = new JSONArray();
		JSONObject jsob=null;
		if (StringUtils.isNotEmpty(accessnumber)) {
			List<FSSipTrunk> fsGatewayls=fsGateWayService.getGateWayls(accessnumber);
			for (int i = 0; i < fsGatewayls.size(); i++) {	//将数据放入json
				jsob = new JSONObject(fsGatewayls.get(i));
				json.put(jsob);
			}
			return json.toString(); 
		}else{
			jsob = new JSONObject();
			 jsob.put("result", "false");
			 return jsob.toString();
		}
		
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
			HttpServletResponse response, String name, Integer id)
			throws IOException {
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
	public String save(HttpServletRequest request, SysConfig sysConfig) throws ParseException {
		JSONObject jsob = new JSONObject();
//		jsob.put("gatewayName", StringUtils.trimToEmpty(sysConfig.getGatewayName()));
		jsob.put("accessnumber", StringUtils.trimToEmpty(sysConfig.getAccessnumber()));
//		jsob.put("content", "".equals(StringUtils.trimToEmpty(sysConfig.getContent())) ? "{}" : StringUtils.trimToEmpty(sysConfig.getContent()));
		jsob.put("content", StringUtils.trimToEmpty(sysConfig.getContent()));
		sysConfig.setVal(jsob.toString());
		sysConfig.setName("defaultaccessnumber");
		sysConfigService.saveOrUpdate(sysConfig);

        callRouteManager.loadDefaultGateway();
        
		ExtenRouteController.isApply=true;

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
	public String deploy(HttpServletRequest request,SysConfig sysConfig) {
		sysConfigService.deploy();
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
		sysConfigService.remove(id);
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("checkContent")
	@ResponseBody
	public String checkContent(String content) {
		
//		if("".equals(StringUtils.trimToEmpty(content))){
//			content = "{}";
//		}
//		try {
//			JSONObject r = new JSONObject(content);
//			return new JSONObject().put("success", true).toString();
//		}catch(Exception e){
//			return new JSONObject().put("success", false).toString();
//		}
		
		return new JSONObject().put("success", true).toString();
	}

}
