package com.ruishengtech.rscc.crm.ui.sysConfig.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.datamanager.service.imp.DataIntentServiceImp;

@Controller
@RequestMapping("dictionary")
public class DictionaryController {
	
	@Autowired
	private DataIntentServiceImp dataIntentService;
	
	@RequestMapping
	public String index(HttpServletRequest request,HttpServletResponse responst,Model model){
		List<DataIntent> dis = dataIntentService.getAll();
		model.addAttribute("dis", dis);
		
		model.addAttribute("iframecontent","sysconfig/dictionary_index");
		return "iframe";
		
//		return "sysconfig/dictionary_index";
	}
	
}
