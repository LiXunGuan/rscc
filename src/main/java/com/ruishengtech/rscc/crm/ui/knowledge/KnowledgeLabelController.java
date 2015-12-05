package com.ruishengtech.rscc.crm.ui.knowledge;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.rscc.crm.knowledge.condition.KnowledgeLabelCondition;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeLabel;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeLabelService;

@Controller
@RequestMapping("knowledge/label")
public class KnowledgeLabelController {
	
	@Autowired
	private KnowledgeLabelService knowledgeLabelService;
	
	@RequestMapping
	public String index(Model model){
		
		model.addAttribute("iframecontent","knowledge/knowledgeLabel_index");
		return "iframe";
		
//		return "knowledge/knowledgeLabel_index";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request, HttpServletResponse response, KnowledgeLabelCondition knowledgeLabelCondition){
		
		knowledgeLabelCondition.setRequest(request);
		PageResult<KnowledgeLabel> pageResult =  knowledgeLabelService.queryPage(knowledgeLabelCondition);
		
		List<KnowledgeLabel> knowledges = pageResult.getRet();
		
		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		
		for (int i = 0; i < knowledges.size(); i++) {
			JSONObject json = new JSONObject(knowledges.get(i));
			array.put(json);
		}
		
		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
		
		return jsonObject.toString();
	}
	
	@RequestMapping("get")
    public String getData(HttpServletRequest request, HttpServletResponse response, String uuid, Model model){
		if(uuid != null){
			KnowledgeLabel knowledge = knowledgeLabelService.getKnowledgesByLabelUUid(uuid);
			model.addAttribute("item", knowledge);
		}
        return "knowledge/knowledgeLabel_save";
    }
	
	@RequestMapping("save")
    @ResponseBody
    public String save(HttpServletRequest request, KnowledgeLabel knowledgelabel,BindingResult bindingResult) {
		knowledgeLabelService.saveOrUpdate(knowledgelabel);
        return new JSONObject().put("success", true).toString();
    }
}
