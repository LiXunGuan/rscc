package com.ruishengtech.rscc.crm.ui.knowledge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.rscc.crm.knowledge.condition.KnowledgeCondition;
import com.ruishengtech.rscc.crm.knowledge.model.Knowledge;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeClickNumbers;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeDirectory;
import com.ruishengtech.rscc.crm.knowledge.model.KnowledgeSearchNumbers;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeClickNumbersService;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeDirectoryService;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeLabelLinkService;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeLabelService;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeSearchNumbersService;
import com.ruishengtech.rscc.crm.knowledge.service.KnowledgeService;

@Controller
@RequestMapping("knowledge/knowledge")
public class KnowledgeController {

	@Autowired
	private KnowledgeService knowledgeService;
	
	@Autowired
	private KnowledgeLabelService knowledgeLabelService;
	
	@Autowired
	private KnowledgeLabelLinkService knowledgeLabelLinkService;
	
	@Autowired
	private KnowledgeDirectoryService knowledgeDirectoryService;
	
	@Autowired
	private KnowledgeClickNumbersService knowledgeClickNumbersService;
	
	@Autowired
	private KnowledgeSearchNumbersService knowledgeSearchNumbersService;
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping
	public String index(Model model){
		//得到所有目录用于tree
		model.addAttribute("directoryTree", knowledgeDirectoryService.getDirectoryTree());
		//得到所有知识用于查询
		model.addAttribute("alldirectorys", knowledgeDirectoryService.getAllDirectorys());
		//得到所有知识用于查询
		model.addAttribute("allknowledgs", knowledgeService.getAllKnowledges());
		
		model.addAttribute("iframecontent","knowledge/knowledge_index");
		return "iframe";
		
//		return "knowledge/knowledge_index";
	}
	
	@RequestMapping("manager")
	public String manager(Model model){
		//得到所有目录用于tree
		model.addAttribute("directoryTree", knowledgeDirectoryService.getDirectoryTree());
		
		model.addAttribute("iframecontent","knowledge/knowledge_info");
		return "iframe";
		
//		return "knowledge/knowledge_info";
	}
	
	@RequestMapping("data")
	@ResponseBody
	public String getData(HttpServletRequest request, HttpServletResponse response, KnowledgeCondition knowledgeCondition) throws ParseException{
		
		knowledgeCondition.setRequest(request);
		
		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		
		PageResult<Knowledge> pageResult =  knowledgeService.queryPage(knowledgeCondition);
		List<Knowledge> knowledges = pageResult.getRet();
		
		for (int i = 0; i < knowledges.size(); i++) {
			JSONObject json = new JSONObject(knowledges.get(i));
			//得到父目录对象
			json.put("directoryName", StringUtils.trimToEmpty(knowledges.get(i).getDireName()));
			//查询知识对应的标签
			json.put("knowledgeTages", StringUtils.trimToEmpty(knowledges.get(i).getKnowledgeTags()));
			
			array.put(json);
		}
		
		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
		
		return jsonObject.toString();
	}
	
	//得到最新的10条搜索标签
	@RequestMapping("getLbaels")
	@ResponseBody
	public String getLabels(HttpServletRequest request, HttpServletResponse response){
		List<KnowledgeSearchNumbers> kcn = knowledgeSearchNumbersService.selectTopTenData();
		return new JSONObject().put("success", true).put("labels", kcn).toString();
	}
	
	@RequestMapping("get")
    public String getData(HttpServletRequest request, HttpServletResponse response, String uuid, String operation, Model model) throws ParseException{
		if(uuid != null){
			Knowledge knowledge = knowledgeService.getKnowledgesByUUid(uuid);
			model.addAttribute("item", knowledge);
			//所属目录
			model.addAttribute("directory", knowledgeDirectoryService.getKnowledgesByDirectoryUUid(knowledge.getDirectoryUUid()));
			//标签信息
			List<String> tagsStr = knowledgeLabelService.getTagsByKnowledge(knowledge);
			model.addAttribute("tagsStr", tagsStr.size() > 0 ? tagsStr.toString() : "");
			//查看信息
			if("get".equals(operation)){
				//增加点击量
				KnowledgeClickNumbers kcn = new KnowledgeClickNumbers();
				kcn.setKnowledgeUUid(knowledge.getUid());
				kcn.setUserUUid("admin");
				kcn.setClickTime(fmt.parse(fmt.format((new Date()))));
				knowledgeClickNumbersService.saveOrUpdate(kcn);
				//查询点击量
				Long clickNumbers = knowledgeClickNumbersService.getCountByKnowledge(knowledge);
				model.addAttribute("clickNumbers", clickNumbers);
				return "knowledge/knowledge_get";
			}
		}
		//所有目录
		model.addAttribute("directoryTree", knowledgeDirectoryService.getDirectoryTree());
        return "knowledge/knowledge_save";
    }
	
	@RequestMapping("save")
    @ResponseBody
    public String save(HttpServletRequest request, Knowledge knowledge,BindingResult bindingResult,String knowledgeTags) {
		if(knowledge != null){
			if(StringUtils.isNotBlank(knowledge.getUid())){
				//修改
				if(StringUtils.isNotBlank(knowledgeTags)){
					List<String> tagList = Arrays.asList(knowledgeTags.split(","));
					//绑定标签
					knowledgeLabelLinkService.bindTagsToKnowledge(knowledge.getUid(), tagList);
					knowledgeService.saveOrUpdate(knowledge);
				}else{
					//修改信息
					knowledgeService.saveOrUpdate(knowledge);
					//删除这条知识的所有标签
					knowledgeLabelLinkService.unBindTagsToKnowledge(knowledge.getUid());
				}
			}else{
				//保存
				knowledgeService.saveOrUpdate(knowledge);
				if(StringUtils.isNotBlank(knowledgeTags)){
					List<String> tagList = Arrays.asList(knowledgeTags.split(","));
					//绑定标签
					knowledgeLabelLinkService.bindTagsToKnowledge(knowledge.getUid(), tagList);
				}
			}
			return new JSONObject().put("success", true).put("dts", knowledgeDirectoryService.getDirectoryTree()).toString();
		}
		return new JSONObject().put("success", false).toString();
    }
	
	@RequestMapping("getSearchNumbers")
    @ResponseBody
    public String getSearchNumbers(String tag) throws ParseException{
		//判断是否是关键字查询,并保存信息
		if(StringUtils.isBlank(tag) == false){
			KnowledgeSearchNumbers ksn = new KnowledgeSearchNumbers();
			ksn.setKeyword(tag);
			ksn.setUserUUid("admin");
			ksn.setSearchTime(fmt.parse(fmt.format((new Date()))));
			knowledgeSearchNumbersService.saveOrUpdate(ksn);
			
			//查询关键字的搜索量
			Long searchNumbers = knowledgeSearchNumbersService.getCountByKeyword(tag);
			return new JSONObject().put("success", true).put("tag", tag).put("searchNumbers", searchNumbers).toString();
		}
		return new JSONObject().put("success", false).toString();
	}
	
	@RequestMapping("remove")
    @ResponseBody
    public String remove(HttpServletRequest request,String uuid) {
		knowledgeService.delete(uuid);
        return new JSONObject().put("success", true).toString();
    }
	
	@RequestMapping("saveTree")
    @ResponseBody
    public String saveTree(HttpServletRequest request,String name,String type,String pid,String uid) {
		KnowledgeDirectory kdr = null;
		Knowledge kn = null;
		List<String> tagsStr = null;
		String content = null;
		if("directory".equalsIgnoreCase(type)){
        	if(uid.length() < 4){//新增
        		//添加目录
        		kdr = new KnowledgeDirectory();
        		kdr.setDirectoryName(name);
        		kdr.setDirectoryParentUUid(pid);
        		kdr.setDirectoryRemark("");
        	}else{
        		kdr = knowledgeDirectoryService.getKnowledgesByDirectoryUUid(uid);
        		if(kdr != null){
        			kdr.setDirectoryName(name);
        		}
        	}
        	
        	knowledgeDirectoryService.saveOrUpdate(kdr);
        	
        	return new JSONObject().put("success", true).put("type", "directory").put("uid", kdr.getUid()).toString();
        
		}else if("knowledge".equalsIgnoreCase(type)){
        	if(uid.length() < 4){//新增
        		//添加新知识
        		kn = new Knowledge();
        		kn.setDirectoryUUid(pid);
        		kn.setKnowledgeTitle(name);
        		kn.setKnowledgeContent("");
        		kn.setKnowledgeTags("");
        	}else{
        		kn = knowledgeService.getKnowledgesByUUid(uid);
        		if(kn != null){
        			kn.setKnowledgeTitle(name);
        		}
        		//标签信息
    			tagsStr = knowledgeLabelService.getTagsByKnowledge(kn);
    			content = kn.getKnowledgeContent();
        	}
        	
        	knowledgeService.saveOrUpdate(kn);
        	
        	return new JSONObject().put("success", true).put("type", "knowledge").put("tagsStr", tagsStr != null ? tagsStr.toString() : "").put("content", content != null ? content : "" ).put("uid", kn.getUid()).toString();
        }
        return new JSONObject().put("success", false).toString();
    }
	
	@RequestMapping("deleteTree")
    @ResponseBody
    public String deleteTree(HttpServletRequest request,String type,String uid) {
		if("directory".equalsIgnoreCase(type)){
			knowledgeDirectoryService.delete(uid);
		}else if("knowledge".equalsIgnoreCase(type)){
			knowledgeService.delete(uid);
		}else{
			return new JSONObject().put("success", false).toString();
		}
		return new JSONObject().put("success", true).toString();
    }
	
	@RequestMapping("getKnowledge")
    @ResponseBody
    public String getKnowledge(HttpServletRequest request,String uid) {
		Knowledge kn = knowledgeService.getKnowledgesByUUid(uid);
		List<String> tagsStr = null;
		String content = null;
		if(kn != null){
			//标签信息
			tagsStr = knowledgeLabelService.getTagsByKnowledge(kn);
			content = kn.getKnowledgeContent();
			return new JSONObject().put("success", true).put("cont", content).put("tagsStr", tagsStr != null ? tagsStr.toString() : "").toString();
		}
		return new JSONObject().put("success", false).toString();
    }
	
	
	@RequestMapping("search")
    @ResponseBody
    public String searchKnowledge(HttpServletRequest request, String title, String checktype, Integer pageNum, Integer pageSize){
		//判断是目录或知识查询
		if("dirs".equals(checktype)){
			List<KnowledgeDirectory> dirs = null;
			List<Knowledge> knos = null;
			if(StringUtils.isNotBlank(title)){
				//得到匹配的目录信息
				dirs = knowledgeDirectoryService.getKnowledgesByDirNames(title);
				//得到匹配的知识信息
				knos = knowledgeService.getKnowledgesByParams(title);
			}else{
				//得到全部的目录信息
				dirs = knowledgeDirectoryService.getAllDirectorys();
				//得到全部的知识信息
				knos = knowledgeService.getAllKnowledges();
			}
			if(dirs == null && knos == null){
				return new JSONObject().put("success", false).toString();
			}
			//将得到的两种信息拼成JSON数据
			JSONObject json = new JSONObject();
			json.put("dirs", dirs);
			json.put("knos", knos);
			return json.put("success", true).put("type", "dir").toString();
		}
		//对于标签信息的查询
		Integer countRecord = knowledgeService.getKnowledgesCountBytitle(title, checktype);
		pageSize = pageSize == null ? 10 : pageSize;
		pageNum = pageNum == null ? 0 : (pageNum - 1)*pageSize;
		List<Knowledge> kns = knowledgeService.getKnowledgesByTitle(title, checktype, pageSize, pageNum);
		if(kns == null){
			return new JSONObject().put("success", false).toString();
		}
		return new JSONObject().put("success", true).put("kns", kns).put("countRecord", countRecord).put("type", "tag").toString();
	}
}
