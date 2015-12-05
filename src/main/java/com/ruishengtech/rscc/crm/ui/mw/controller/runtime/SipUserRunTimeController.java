package com.ruishengtech.rscc.crm.ui.mw.controller.runtime;

import java.io.IOException;
import java.util.Date;
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
import com.ruishengtech.rscc.crm.ui.mw.condition.runtime.SipUserRunTimeCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.Domains;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.model.runtime.SipUserRunTime;
import com.ruishengtech.rscc.crm.ui.mw.service.MWExtenRouteService;
import com.ruishengtech.rscc.crm.ui.mw.service.runtime.SipUserRunTimeService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("runtime/sipuserruntime")
public class SipUserRunTimeController {

	 @Autowired
	 private SipUserRunTimeService sipUserRunTimeService;

	 @Autowired
	 private MWExtenRouteService extenRouteService;

    @RequestMapping
    public String Batch(HttpServletRequest request, HttpServletResponse response,Model model,String str) {
    	
    	model.addAttribute("iframecontent","runtime/sipuserruntime");
		return "iframe";
//        return "runtime/sipuserruntime";
    }
    
    /**
     * 数据请求
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("data")
    @ResponseBody
    public String data(HttpServletRequest request,
                       HttpServletResponse response, SipUserRunTimeCondition sipUserRunTimeCondition) {
    	sipUserRunTimeCondition.setRequest(request);
        PageResult<SipUserRunTime> pageResult = sipUserRunTimeService.page(sipUserRunTimeCondition);	//获取符合条件的数据
        List<SipUserRunTime> list = (List<SipUserRunTime>) pageResult.getRet();

        JSONArray jsonArray = new JSONArray();	//将数据放入json
        JSONObject jsonObject = new JSONObject();
        
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsob = new JSONObject(list.get(i));
            jsob.put("id", list.get(i).getId());
			jsob.put("sipid", StringUtils.trimToEmpty(list.get(i).getSipid()));
			jsob.put("agentuid", StringUtils.trimToEmpty(list.get(i).getAgentuid()));
			jsob.put("agentinfo", StringUtils.trimToEmpty(list.get(i).getAgentinfo()));
			jsob.put("blindingstatus",StringUtils.trimToEmpty(Domains.AGENTSTATUS.get(list.get(i).getBlindingstatus())));
			if (list.get(i).getRegstatus()!=null) {
				jsob.put("regstatus", Domains.REGISTRATIONS_ON);
			}else{
				jsob.put("regstatus", Domains.REGISTRATIONS_OFF);
			}
			
			if (list.get(i).getAnswertime()!=null) {
				Date date = new Date();
				jsob.put("answerTime", StringUtils.trimToEmpty(String.valueOf(((date.getTime()-list.get(i).getAnswertime().getTime())/1000))));
			}else if(list.get(i).getAnswertime1()!=null){
                Date date = new Date();
                jsob.put("answerTime", StringUtils.trimToEmpty(String.valueOf(((date.getTime()-list.get(i).getAnswertime1().getTime())/1000))));
            }

            if(StringUtils.isNotBlank(list.get(i).getCallstate())) {
                jsob.put("callstate", StringUtils.trimToEmpty(Domains.EXTENSTASTUS.get(list.get(i).getCallstate())));
            }

            if(StringUtils.isNotBlank(list.get(i).getDirection())) {
                jsob.put("direction", StringUtils.trimToEmpty(Domains.DIRECTIONS.get(list.get(i).getDirection())));
            }
            jsonArray.put(jsob);
        }

        jsonObject.put("aaData", jsonArray);
        jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
        jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
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
        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>运行状态&nbsp;&nbsp;>&nbsp;<span>分机状态</span>");
        return jsob.toString();
    }
}
