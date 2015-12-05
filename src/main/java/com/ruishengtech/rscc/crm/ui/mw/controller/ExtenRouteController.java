package com.ruishengtech.rscc.crm.ui.mw.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.rscc.crm.ui.mw.model.MWExtenRoute;
import com.ruishengtech.rscc.crm.ui.mw.service.MWExtenRouteService;

/**
 * Created by yaoliceng on 2014/11/3.
 */
@Controller
@RequestMapping("config/extenroute")
public class ExtenRouteController {
	
	public static  Boolean isApply = false;
	
	@Autowired
	private MWExtenRouteService extenRouteService;
	
	@RequestMapping("isRepeatExtension")
	@ResponseBody
	public void isRepeatExtension(HttpServletRequest request,
			HttpServletResponse response, String extension, Long id,
			String types) throws IOException {
		MWExtenRoute extrou = extenRouteService.getExten(extension);
		MWExtenRoute est = extenRouteService.get(id);
		if (id != null) {
			if(est.getExtension().equals(extension) || extrou==null) {
				response.getWriter().print(true);
			}else{
				response.getWriter().print(false);
			}
		}else{
			if (extrou != null) {
				response.getWriter().print(false); // 存在
			} else {
				response.getWriter().print(true); // 不存在
			}
		}
	}

}
