package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.IOException;
import java.net.SocketException;
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
import com.ruishengtech.rscc.crm.ui.mw.condition.GateWayCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.Domains;
import com.ruishengtech.rscc.crm.ui.mw.model.FSSipProfile;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.service.FSSipProfileService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("config/sipprofile")
public class SipProfileController {

	@Autowired
	private FSSipProfileService fsSipProfileService;
	 
	@RequestMapping
	public String Batch(HttpServletRequest request,
			HttpServletResponse response, Model model, String str) {

		model.addAttribute("intext", FSSipProfile.INTEXT);
		model.addAttribute("codec", Domains.CODECSTRING);
		
		model.addAttribute("iframecontent","config/sipprofile");
		return "iframe";
		
//		return "config/sipprofile";
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
			HttpServletResponse response, GateWayCondition gateWayCondition) {

		gateWayCondition.setRequest(request);

		PageResult<FSSipProfile> pageResult = fsSipProfileService.page(gateWayCondition);	//获取符合查询条件的数据
		List<FSSipProfile> list = (List<FSSipProfile>) pageResult.getRet();

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < list.size(); i++) {	//将数据放入json

			JSONObject jsob = new JSONObject(list.get(i));
			jsob.put("id", list.get(i).getId());

			jsob.put("name", StringUtils.trimToEmpty(list.get(i).getName()));
			jsob.put("sipIp",StringUtils.trimToEmpty(list.get(i).getSipIp()));
			jsob.put("sipPort",StringUtils.trimToEmpty(list.get(i).getSipPort()));
			jsob.put("type",StringUtils.trimToEmpty(FSSipProfile.INTEXT.get(list.get(i).getType())));
			jsob.put("servername", StringUtils.trimToEmpty(list.get(i).getServername()));
			jsob.put("codecString",StringUtils.trimToEmpty(list.get(i).getCodecString()));

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
        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>呼叫中心配置&nbsp;&nbsp;>&nbsp;<span>SIP Profile</span>");
        return jsob.toString();
    }

	@RequestMapping("get")
	public String getScopExt(HttpServletRequest request,
			HttpServletResponse response, Long id, Model model)
			throws SocketException {
		model.addAttribute("intext", FSSipProfile.INTEXT);
		model.addAttribute("codec", Domains.CODECSTRING);
		if (id != null) {
			FSSipProfile item = fsSipProfileService.get(id);
			String[] codecs= item.getCodecString().split(",");
			List<String> codecls=new ArrayList<String>();
			for (String str : codecs) {
				codecls.add(str);
			}
			
			model.addAttribute("codecls", codecls);
			model.addAttribute("item", item);
		}
		List<MWFsHost> fshostls= fsSipProfileService.getAll(MWFsHost.class);
		model.addAttribute("fshostls", fshostls);
		return "config/sipprofile_save";
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
		FSSipProfile fsgw = fsSipProfileService.getSipProfile(name);

		if (fsgw==null) {
			response.getWriter().print(true); // 直接打印true 通过
		} else {
			if (id==null) { // 添加
				response.getWriter().print(false);
			} else { // 修改
				if (id==fsgw.getId()) { // 如果个数为一，并且是修改的同一条数据（编号一致）
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
	public String save(HttpServletRequest request,Integer types, FSSipProfile fsSipProfile) {

		fsSipProfileService.saveOrUpdate(fsSipProfile);
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
		
		fsSipProfileService.remove(id);
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
	public String deploy(HttpServletRequest request, FSSipProfile fsSipProfile) {
		fsSipProfileService.deploy();
		return new JSONObject().put("success", true).toString();
	}

}
