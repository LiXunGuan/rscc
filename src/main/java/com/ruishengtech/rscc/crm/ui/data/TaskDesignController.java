package com.ruishengtech.rscc.crm.ui.data;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
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
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.rscc.crm.data.condition.TaskDesignCondition;
import com.ruishengtech.rscc.crm.data.service.TaskDesignService;

/**
 * 用户自己design
 * @author Frank
 *
 */
@Controller
@RequestMapping("data/taskDesign")
public class TaskDesignController {

	@Autowired
	private TaskDesignService taskDesignService;
	
	@RequestMapping
	public String index(Model model){
		
		
		model.addAttribute("iframecontent","data/task-des-index");
		return "iframe";
		
//		return "data/task-des-index";
	}
	
	/**
	 * 编辑表 页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String toEditTable(Model model ,String uuid){
	
		model.addAttribute("COLUMNTYPE", ColumnDesign.COLUMNTYPE);
		
		return "data/task-edit-table-dialog";
	}
	
	/**
	 * 
	 * 获取客户表描述信息
	 * @return
	 */
	@RequestMapping("data")
	@ResponseBody
	public String getDataDes(HttpServletRequest request , HttpServletResponse response, TaskDesignCondition condition){

		condition.setRequest(request);

		PageResult<ColumnDesign> pageResult = taskDesignService.queryPage(condition);

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		List<ColumnDesign> tasks = pageResult.getRet();

		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < tasks.size(); i++) {
			
			JSONObject jsonObject2 = new JSONObject(tasks.get(i));
			
			jsonObject2.put("columnType", ColumnDesign.COLUMNTYPE.get(tasks.get(i).getColumnType()));
			jsonObject2.put("allowSelect", ColumnDesign.BOOLEANTYPE.get(tasks.get(i).getAllowSelect()));
			jsonObject2.put("allowIndex", ColumnDesign.BOOLEANTYPE.get(tasks.get(i).getAllowIndex()));
			jsonObject2.put("allowShow", ColumnDesign.BOOLEANTYPE.get(tasks.get(i).getAllowShow()));
			
			array.put(jsonObject2);
		}

		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
		
		return jsonObject.toString();
	}
	
	
	/**
	 * 
	 * 保存编辑表
	 * @param request
	 * @param response
	 * @param taskDesign
	 * @param allowSelect
	 * @param allowIndex
	 * @return
	 */
	@RequestMapping("editSave")
	@ResponseBody
	public String editSave(HttpServletRequest request,
			HttpServletResponse response, ColumnDesign taskDesign,
			String allowSelect, String allowIndex) {
		
		/*保存表结构信息*/
		taskDesign.setIsDefault(ColumnDesign.NOTDEFAULT);
		taskDesign.setTableName("task");
		taskDesign.setColumnNameDB(taskDesignService.getColumnName());
		
		if (StringUtils.isNotBlank(taskDesign.getAllowIndex())) {
			taskDesign.setAllowIndex(ColumnDesign.ALLOWINDEX);
		} else {
			taskDesign.setAllowIndex(ColumnDesign.NOTALLOWINDEX);
		}

		if (StringUtils.isNotBlank(taskDesign.getAllowSelect())) {
			taskDesign.setAllowSelect(ColumnDesign.ALLOWSELECT);
		} else {
			taskDesign.setAllowSelect(ColumnDesign.NOTALLOWSELECT);
		}

		if (StringUtils.isNotBlank(taskDesign.getAllowShow())) {
			taskDesign.setAllowShow(ColumnDesign.ALLOWSHOW);
		} else {
			taskDesign.setAllowShow(ColumnDesign.NOTALLOWSHOW);
		}
		try {
			/*修改表结构 customer表结构*/
			taskDesignService.save(taskDesign);
		} catch (Exception e) {
			//执行异常，报错
			StringWriter sw=new StringWriter();  
            PrintWriter pw=new PrintWriter(sw);  
            e.printStackTrace(pw);
			return new JSONObject().put("success", false).put("error", sw.toString()).toString();
		}
		/*重新加载数据*/
		return new JSONObject().put("success", true).put("error", "").toString();
	}
	
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public String delete(String uuid){
		
		if(StringUtils.isNotBlank(uuid)){
			//删除数据库字段  删除客户表字段
			taskDesignService.deleteColumn(uuid, "task");
			return new JSONObject().put("success", true).toString();
		}
		return new JSONObject().put("success", false).toString();
	}
	
	
	
}
