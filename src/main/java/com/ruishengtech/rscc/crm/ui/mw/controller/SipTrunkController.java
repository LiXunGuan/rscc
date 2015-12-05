package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.IOException;
import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruishengtech.rscc.crm.ui.mw.send.MwMangaer;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.rscc.crm.ui.mw.condition.GateWayCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumber;
import com.ruishengtech.rscc.crm.ui.mw.model.AccessNumberGateway;
import com.ruishengtech.rscc.crm.ui.mw.model.FSSipProfile;
import com.ruishengtech.rscc.crm.ui.mw.model.FSSipTrunk;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.service.AccessNumberService;
import com.ruishengtech.rscc.crm.ui.mw.service.FSGateWayNumberService;
import com.ruishengtech.rscc.crm.ui.mw.service.FSGateWayService;
import com.ruishengtech.rscc.crm.ui.mw.service.FSSipProfileService;
import com.ruishengtech.rscc.crm.ui.mw.service.MWExtenRouteService;
import com.ruishengtech.rscc.crm.ui.mw.service.MWFsHostService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("config/gateway")
public class SipTrunkController {
	
	public static  Boolean isApply=false;

	 @Autowired
	 private FSGateWayService fsGateWayService;
	 
	 @Autowired
	 private MWExtenRouteService mwExtenRoute;

	 @Autowired
	 private MWFsHostService fsServerConfig;
	 
	 @Autowired
	 private FSSipProfileService fsSipProfileService;

	 @Autowired
	 private FSGateWayNumberService fsGateWayNumberService;
	 
	 @Autowired
	 private AccessNumberService accessnumberService;
	 
	@RequestMapping
	public String Batch(HttpServletRequest request,
			HttpServletResponse response, Model model, String str) {
		model.addAttribute("isApply", isApply);
		
		model.addAttribute("iframecontent","config/gateway");
		return "iframe";
		
//		return "config/gateway";
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
			HttpServletResponse response, GateWayCondition gateWayCondition,Model model) {

		gateWayCondition.setRequest(request);

		PageResult<FSSipTrunk> pageResult = fsGateWayService.page(gateWayCondition);	//获取符合查询条件的数据
		List<FSSipTrunk> list = (List<FSSipTrunk>) pageResult.getRet();

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();

        Map<String, Boolean> map = MwMangaer.getGateWayStatus();

		for (int i = 0; i < list.size(); i++) {	//将数据放入json

			JSONObject jsob = new JSONObject(list.get(i));
			jsob.put("id", list.get(i).getId());
			
			Integer n=list.get(i).getIp().indexOf(":");
			if (n!=-1) {
				jsob.put("ip", StringUtils.trimToEmpty(list.get(i).getIp().substring(0,n)));
				jsob.put("port", StringUtils.trimToEmpty(list.get(i).getIp().substring(n)));
			}

			jsob.put("name", StringUtils.trimToEmpty(list.get(i).getName()));
			jsob.put("registration",StringUtils.trimToEmpty(FSSipTrunk.EXTERNAL.get(list.get(i).getRegistration())));
			jsob.put("username",StringUtils.trimToEmpty(list.get(i).getUsername()));
			jsob.put("password",StringUtils.trimToEmpty(list.get(i).getPassword()));
			jsob.put("servername",StringUtils.trimToEmpty(list.get(i).getServername()));
			jsob.put("sipProfilename",StringUtils.trimToEmpty(list.get(i).getSipprofilename()));

            Boolean status = map.get(StringUtils.trimToEmpty(list.get(i).getName()));
            if(status==null){
                status=false;
            }
			jsob.put("status", status);
			jsonArray.put(jsob);
		}
		jsonObject.put("aaData", jsonArray);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords",
				pageResult.getiTotalDisplayRecords());
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
        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>呼叫中心配置&nbsp;&nbsp;>&nbsp;<span>SIP Trunk</span>");
        return jsob.toString();
    }

	@RequestMapping("get")
	public String getScopExt(HttpServletRequest request,
			HttpServletResponse response, Long id, Model model)
			throws SocketException {
		FSSipTrunk item=null;
		if (id != null) {	//如果外线id不为空 表示修改  查询外线信息
			item= fsGateWayService.get(id);
			if(item.getIp().lastIndexOf(":") > 0){
				String port = item.getIp().substring(item.getIp().lastIndexOf(":")+1, item.getIp().length());
				item.setIp(item.getIp().substring(0, item.getIp().lastIndexOf(":")));
				item.setPort(port);
			}
			model.addAttribute("item", item);
		}
		List<MWFsHost> serverls= fsServerConfig.getAllById(MWFsHost.class);	//查询出所有主机
		model.addAttribute("serverls", serverls);
		
		List<FSSipProfile> sipls=null;	//查询出主机对应SipProfile 如果是新增 主机id为空 查询第一台主机对应SipProfile
		if (item!=null) {
			sipls=fsSipProfileService.getSipPro(item.getFshost_id());
		}else{
			if(serverls.size() != 0){
				sipls=fsSipProfileService.getSipPro(serverls.get(0).getId());
			}
		}
		model.addAttribute("sipls", sipls);
		return "config/gateway_save";
	}
	
	/**
	 * 页面跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getnumber")
	public String getnumber(HttpServletRequest request,
			HttpServletResponse response, Long id, Model model)
			throws SocketException {
		model.addAttribute("gwId", id);
		return "config/gateway_savenumber";
	}
	
	
	@RequestMapping("addAccessNumber")
	public String addAccessNumber(HttpServletRequest request, HttpServletResponse response, Long id, Model model)
			throws SocketException {
		List<AccessNumber> numbers = accessnumberService.getAll(AccessNumber.class);
		List<AccessNumberGateway> angwls = fsGateWayService.getGateWayAccessNumber(id);
		List<AccessNumber> numbersCheckls = new ArrayList<AccessNumber>();
		if(numbers != null && angwls != null){
			for (int i = 0; i < numbers.size(); i++) {
				for (int j = 0; j < angwls.size(); j++) {
					if (numbers.get(i).getId().equals(angwls.get(j).getAccessnumber_id()) ) {
						numbersCheckls.add(numbers.get(i));
						numbers.remove(i);
						i=i-1;
						break;
					}
				}
			}
		}
		
//		for (int i = 0; i < numbers.size(); i++) {
//			numbers.get(i).setAccessnumber("接入号-" + numbers.get(i).getAccessnumber());
//		}
		
		model.addAttribute("id", id);
		model.addAttribute("numbers", numbers);
		model.addAttribute("numbersCheckls", numbersCheckls);
		
		return "config/gateway_accessnumber";
	}
	
	
	/**
	 * 根据主机ID找到对应SipProfile
	 * @param request
	 * @param response
	 * @param name
	 * @param id
	 * @throws IOException
	 */
	@RequestMapping("getSipProfile")
	@ResponseBody
	public String getSipProfile(HttpServletRequest request,
			HttpServletResponse response, Long fshost_id,Model model)
			throws IOException {
		JSONArray json = new JSONArray();
		JSONObject jsob=null;
		if (fshost_id!=null) {
			List<FSSipProfile> sipls=fsSipProfileService.getSipPro(fshost_id);
			
			for (int i = 0; i < sipls.size(); i++) {	//将数据放入json
				jsob = new JSONObject(sipls.get(i));
				System.out.println(jsob.toString());
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
			HttpServletResponse response, String name, Long id)
			throws IOException {
		// 根据该外线名查询外线信息
		FSSipTrunk fsgw = fsGateWayService.getGateWay(name);

		if (fsgw==null) {
			response.getWriter().print(true); // 直接打印true 通过
		} else {
			if (id==null) { // 添加
				response.getWriter().print(false);
			} else { // 修改
				if (id.equals(fsgw.getId())) { // 如果个数为一，并且是修改的同一条数据（编号一致）
					response.getWriter().print(true); // 如果有 ： 返回 true 通过
				} else {
					response.getWriter().print(false); // 没有 返回false 不通过
				}
			}

		}

	}

	/**
	 * 保存
	 * @param request
	 * @param fsGateWay
	 * @return 
	 */
	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request,HttpServletResponse response,String registrations, FSSipTrunk fsGateWay) {
		
		String pwd = request.getParameter("pwd");
		
		fsGateWay.setPassword(pwd);
		
		if (fsGateWay.getId()!=null) {	
			FSSipTrunk gateWay=fsGateWayService.get(fsGateWay.getId());
			
			if (registrations==null) {	//如果registrations等于1 表示注册
				gateWay.setRegistration(FSSipTrunk.EXTERNAL_N);
			}else{
				gateWay.setRegistration(FSSipTrunk.EXTERNAL_Y);
			}
			
			if("".equals(fsGateWay.getPort())){
				fsGateWay.setPort("5060");
			}
			gateWay.setName(fsGateWay.getName());
			gateWay.setIp(fsGateWay.getIp()+":"+fsGateWay.getPort());
			gateWay.setPort(fsGateWay.getPort());
			gateWay.setUsername(fsGateWay.getUsername());
			gateWay.setPassword(fsGateWay.getPassword());
			
			fsGateWayService.saveOrUpdate(gateWay);
		}else{
			
			if("".equals(fsGateWay.getPort())){
				fsGateWay.setPort("5060");
			}
			fsGateWay.setIp(fsGateWay.getIp()+":"+fsGateWay.getPort());
			
			if (registrations==null) {	
				fsGateWay.setRegistration(FSSipTrunk.EXTERNAL_N);
			}else{
				fsGateWay.setRegistration(registrations);
			}
			
			fsGateWayService.saveOrUpdate(fsGateWay);
		}
		isApply=true;
		
		// 调用应用方法
		deploy(request);
		
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
	public String remove(HttpServletRequest request, Long id,Model model) {
		fsGateWayNumberService.removeGateWayNumberls(id);
		fsGateWayService.remove(id);
		isApply=true;
		
		// 调用应用方法
		deploy(request);
		
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
		fsGateWayService.toFSXMl();
		fsGateWayService.deploy();
		isApply=false;
		return new JSONObject().put("success", true).toString();
	}
	
	@RequestMapping("savega")
	@ResponseBody
	public String savega(HttpServletRequest request, Long id) throws ParseException {
		String[] ls = request.getParameterValues("numberId");
		fsGateWayService.removeGatewayAccessNumber(id);
		if(ls != null) {
			for (int i = 0; i < ls.length; i++) {
				AccessNumberGateway angw = new AccessNumberGateway();
				angw.setGateway_id(id);
				angw.setAccessnumber_id(Long.valueOf(ls[i]));
				accessnumberService.save(angw);
			}
		}
		isApply=false;
		return new JSONObject().put("success", true).put("isApply", false).toString();
	}

}
