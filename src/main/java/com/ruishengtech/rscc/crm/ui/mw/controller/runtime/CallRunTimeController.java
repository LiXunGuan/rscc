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
import com.ruishengtech.rscc.crm.ui.mw.condition.runtime.CallRunTimeCondition;
import com.ruishengtech.rscc.crm.ui.mw.model.MWFsHost;
import com.ruishengtech.rscc.crm.ui.mw.model.runtime.CallRunTime;
import com.ruishengtech.rscc.crm.ui.mw.service.runtime.CallRunTimeService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("runtime/callruntime")
public class CallRunTimeController {

	 @Autowired
	 private CallRunTimeService callRunTimeService;


    @RequestMapping
    public String Batch(HttpServletRequest request, HttpServletResponse response,Model model,String str) {
    	model.addAttribute("iframecontent","runtime/callruntime");
		return "iframe";
//        return "runtime/callruntime";
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
                       HttpServletResponse response, CallRunTimeCondition callRunTimeCondition) {
    	callRunTimeCondition.setRequest(request);
        PageResult<CallRunTime> pageResult = callRunTimeService.page(callRunTimeCondition);	//获取符合条件的数据
        List<CallRunTime> list = (List<CallRunTime>) pageResult.getRet();

        JSONArray jsonArray = new JSONArray();	//将数据放入json
        JSONObject jsonObject = new JSONObject();
        
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsob = new JSONObject(list.get(i));
            jsob.put("id", list.get(i).getId());

            if(StringUtils.isNotBlank(list.get(i).getGateway1())){
                jsob.put("gateway",list.get(i).getGateway1());
            }else if(StringUtils.isNotBlank(list.get(i).getGateway2())){
                jsob.put("gateway",list.get(i).getGateway2());
            }else {
                jsob.put("gateway","");
            }

            if (list.get(i).getAnswertime()!=null) {
                Date date = new Date();
                jsob.put("answertime", StringUtils.trimToEmpty(String.valueOf(((date.getTime()-list.get(i).getAnswertime().getTime())/1000))));
            }else if(list.get(i).getAnswertime1()!=null){
                Date date = new Date();
                jsob.put("answertime", StringUtils.trimToEmpty(String.valueOf(((date.getTime()-list.get(i).getAnswertime1().getTime())/1000))));
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
        	jsob.put("titleshow", "<i class='fa fa-lg fa-fw fa-wrench'></i>运行状态&nbsp;&nbsp;>&nbsp;<span>通话状态</span>");
        return jsob.toString();
    }
    
    
}
