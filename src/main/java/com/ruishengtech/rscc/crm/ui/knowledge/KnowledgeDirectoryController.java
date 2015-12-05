package com.ruishengtech.rscc.crm.ui.knowledge;

import java.io.IOException;
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
import com.ruishengtech.rscc.crm.knowledge.condition.KnowledgeDirectoryCondition;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeDirectory;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeDirectoryService;

@Controller
@RequestMapping("knowledge/directory")
public class KnowledgeDirectoryController {
	
	@Autowired
	private KnowledgeDirectoryService knowledgeDirectoryService;
	
	@RequestMapping
	public String index(Model model){
		
		model.addAttribute("iframecontent","knowledge/knowledgeDirectory_index");
		return "iframe";
		
//		return "knowledge/knowledgeDirectory_index";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request, HttpServletResponse response, KnowledgeDirectoryCondition knowledgeDirectoryCondition){
		
		knowledgeDirectoryCondition.setRequest(request);
		PageResult<KnowledgeDirectory> pageResult =  knowledgeDirectoryService.queryPage(knowledgeDirectoryCondition);
		
		List<KnowledgeDirectory> knowledges = pageResult.getRet();
		
		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		
		for (int i = 0; i < knowledges.size(); i++) {
			JSONObject json = new JSONObject(knowledges.get(i));
			//得到父目录对象
			KnowledgeDirectory knowledgeDirectory = knowledgeDirectoryService.getKnowledgesByDirectoryUUid(knowledges.get(i).getDirectoryParentUUid());
			json.put("parentDirectory", knowledgeDirectory == null ? knowledges.get(i).getDirectoryParentUUid() : StringUtils.trimToEmpty(knowledgeDirectory.getDirectoryName()));
			
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
			KnowledgeDirectory knowledgeDirectory = knowledgeDirectoryService.getKnowledgesByDirectoryUUid(uuid);
			model.addAttribute("item", knowledgeDirectory);
		}
		model.addAttribute("parentDirectorys", knowledgeDirectoryService.getDirectorys());
        return "knowledge/knowledgeDirectory_save";
    }
	
	@RequestMapping("save")
    @ResponseBody
    public String save(HttpServletRequest request, KnowledgeDirectory knowledgeDirectory) {
		knowledgeDirectoryService.saveOrUpdate(knowledgeDirectory);
        return new JSONObject().put("success", true).toString();
    }
	
	@RequestMapping("remove")
    @ResponseBody
    public String remove(HttpServletRequest request,String uuid) {
		knowledgeDirectoryService.delete(uuid);
        return new JSONObject().put("success", true).toString();
    }
	
	
	@RequestMapping("checkDirectory")
	@ResponseBody
	public void checkDirectory(HttpServletRequest request, HttpServletResponse response, String directoryName, String uid)
			throws IOException {
		KnowledgeDirectory kd = knowledgeDirectoryService.getKnowledgesByDirectoryName(directoryName);
		if (kd == null) {
			response.getWriter().print(true); // 直接打印true 通过
		}else{
			if(uid == null) {
				response.getWriter().print(false);
			}else{
				if(uid.equals(kd.getUid())) {
					response.getWriter().print(true);
				}else{
					response.getWriter().print(false);
				}
			}
		}
	}
	
}
