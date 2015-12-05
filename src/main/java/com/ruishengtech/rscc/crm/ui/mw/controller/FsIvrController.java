package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.IOException;
import java.net.SocketException;
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
import com.ruishengtech.rscc.crm.ui.mw.condition.FSIvrCondition;
import com.ruishengtech.rscc.crm.ui.mw.manager.CallRouteManager;
import com.ruishengtech.rscc.crm.ui.mw.model.FSXml;
import com.ruishengtech.rscc.crm.ui.mw.model.MWExtenRoute;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.model.MWIVR;
import com.ruishengtech.rscc.crm.ui.mw.model.UploadingVoice;
import com.ruishengtech.rscc.crm.ui.mw.service.FSIvrService;
import com.ruishengtech.rscc.crm.ui.mw.service.MWExtenRouteService;
import com.ruishengtech.rscc.crm.ui.mw.service.UploadingVoiceService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("config/ivr")
public class FsIvrController {

	public static  Boolean isApply=false;

	 @Autowired
	 private FSIvrService fsIvrService;

	 @Autowired
	 private MWExtenRouteService extenRouteService;

	 @Autowired
	 private UploadingVoiceService uploadingVoiceService;


    @Autowired
    private CallRouteManager callRouteManager;


	@RequestMapping
	public String Batch(HttpServletRequest request,
			HttpServletResponse response, Model model, String str) {
		
		model.addAttribute("isApply", isApply);
		
		model.addAttribute("iframecontent","config/ivr");
		return "iframe";
		
//		return "config/ivr";
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
			HttpServletResponse response, FSIvrCondition fsIvrCondition) {
		
		fsIvrCondition.setRequest(request);
		
		PageResult<MWIVR> pageResult = fsIvrService.page(fsIvrCondition);	//获取符合查询条件的数据
		List<MWIVR> list = (List<MWIVR>) pageResult.getRet();

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < list.size(); i++) {	//将数据放入json
			JSONObject jsob = new JSONObject(list.get(i));
			jsob.put("id", list.get(i).getId());
			jsob.put("name", list.get(i).getName());
			jsob.put("content", list.get(i).getContent());
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
        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>呼叫中心配置&nbsp;&nbsp;>&nbsp;<span>IVR配置</span>");
        return jsob.toString();
    }

    @RequestMapping("ivr")
    public String ivr(HttpServletRequest request,
                      HttpServletResponse response, Long id, String add,Model model){

        model.addAttribute("extRou", MWExtenRoute.ROUTER);
        //音乐
        List<UploadingVoice> voices = uploadingVoiceService.getAllVoices();
        if(voices != null){
        	for (UploadingVoice uv : voices) {
        		uv.setVoice(uv.getVoice());
        	}
        	model.addAttribute("voices", voices);
        }
        model.addAttribute("add", add);
        model.addAttribute("xmlCata", FSXml.CATA);
        return "config/ivr_pop";
    }

	@RequestMapping("get")
	public String getScopExt(HttpServletRequest request,
			HttpServletResponse response, Long id, Model model)
			throws SocketException {

        //音乐
        List<UploadingVoice> voices = uploadingVoiceService.getAllVoices();
        if(voices != null){
        	for (UploadingVoice uv : voices) {
        		uv.setVoice(uv.getVoice());
        	}
        	model.addAttribute("voices", voices);
        }

		if (id!=null) {
			MWIVR item = fsIvrService.get(id);
			model.addAttribute("item", item);

			if(callRouteManager.getRouterMap() != null){
				MWExtenRoute exten = callRouteManager.getRouterMap().get(item.getExten());
				model.addAttribute("extenid",exten.getId());
			}

		}
		return "config/ivr_save";
	}

	/**
	 * 验证
	 * @param request
	 * @param response
	 * @param name
	 * @param id
	 * @throws java.io.IOException
	 */
	@RequestMapping("isRepeat")
	@ResponseBody
	public void isRepeat(HttpServletRequest request,
			HttpServletResponse response, String name, Long id)
			throws IOException {

	}

	/**
	 * 保存
	 * @param request
	 * @param fsGateWay
	 * @return 
	 */
	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request,String id,String ivrcontent, String name, String exten,FSXml fsXml) {


        MWIVR mwivr = new MWIVR();
        if(StringUtils.isNotBlank(id)) {
            mwivr.setId(Long.valueOf(id));
        }
        mwivr.setContent(ivrcontent);
        mwivr.setName(name);
        mwivr.setExten(exten);
        fsIvrService.saveOrUpdate(mwivr);

        fsIvrService.deleteExtenByDestId(mwivr.getId());

        MWExtenRoute mwExtenRoute =new MWExtenRoute();
        mwExtenRoute.setDestString(mwivr.getName());
        mwExtenRoute.setCan_del(1);
        mwExtenRoute.setName(mwivr.getName());
        mwExtenRoute.setExtension(exten);
        mwExtenRoute.setType(MWExtenRoute.ROUTER_TYPE_IVR_EXT);
        mwExtenRoute.setDestId(mwivr.getId());
        fsIvrService.save(mwExtenRoute);

        callRouteManager.reloadRoute();
        isApply=true;
        
        // 调用应用
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
	public String remove(HttpServletRequest request, Long id) {
		
		fsIvrService.remove(id);
		isApply=true;
		
		// 调用应用
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

        fsIvrService.toFSXMl();
		fsIvrService.deploy();
		
		isApply=false;
		return new JSONObject().put("success", true).toString();
	}


}
