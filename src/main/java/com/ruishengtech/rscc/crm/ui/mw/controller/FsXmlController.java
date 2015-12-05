package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.rscc.crm.ui.mw.model.FSXml;
import com.ruishengtech.rscc.crm.ui.mw.service.FSXmlService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("config/fsxml")
public class FsXmlController {
	
	@Autowired
	private FSXmlService fsXmlService;

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
		FSXml fxxml = fsXmlService.getXml(name);
		if (fxxml == null) {
			response.getWriter().print(true); // 直接打印true 通过
		}else{
			if (id==null) {
				response.getWriter().print(false);
			}else{
				if (id.equals(fxxml.getId())) {
					response.getWriter().print(true);
				}else{
					response.getWriter().print(false);
				}
			}
		}


	}

}
