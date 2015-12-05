package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.IOException;
import java.net.SocketException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.rscc.crm.ui.IndexService;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.mw.send.CallManager;

/**
 * 
 * @author chengxin
 *
 */
@Controller
@RequestMapping("agentpause")
public class AgentPauseController {
	
	@Autowired
	private IndexService indexService;
	
    /**
     * logger
     */
    private static final Logger logger = Logger.getLogger(AgentPauseController.class);

	/**
     * 置忙
     *
     * @param id
     * @throws IOException
     */
    @RequestMapping("pause")
    @ResponseBody
    public String pause(HttpServletRequest request, HttpServletResponse response, String id, String pause, Model model)
            throws SocketException {
    	CallManager.pauseCall(id, pause);
    	request.getSession().setAttribute("pause", pause);
    	logger.info("AgentPauseController 置忙的坐席为："+id+"置忙的原因为："+pause);
        return new JSONObject().put("success", true).put("pause", pause).toString();
    }

    /**
     * 置闲
     *
     * @param id
     * @throws IOException
     */
    @RequestMapping("nopause")
    @ResponseBody
    public String nopause(HttpServletRequest request,  HttpServletResponse response, String id, Model model)
            throws SocketException {
    	
    	JSONObject jsonObject ;
    	if(StringUtils.isNotBlank(id)){
    		
    		jsonObject =  CallManager.unpauseCall(id);
    	}else{
    		
    		jsonObject =  CallManager.unpauseCall(SessionUtil.getCurrentUser(request).getLoginName());
    		System.out.println("置闲用户："+ SessionUtil.getCurrentUser(request).getLoginName() );
    	}
    	
    	if(jsonObject.optString("exit_code").equals("0")){
			request.getSession().removeAttribute("pause");
			return new JSONObject().put("success", true).toString();
		}
    	
        return new JSONObject().put("success", false).toString();
    }
	
}
