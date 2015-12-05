package com.ruishengtech.rscc.crm.ui.cstm.controller;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.rscc.crm.cstm.condition.CustomerDesignCondition;
import com.ruishengtech.rscc.crm.cstm.model.CstmTable;
import com.ruishengtech.rscc.crm.cstm.service.CustomerDesignService;

/**
 * 用户自己design
 * @author Frank
 *
 */
@Controller
@RequestMapping("design")
public class CustomerDesignController {

	@Autowired
	private CustomerDesignService designService;
	
	@Autowired
	@Qualifier(value="diyTableService")
	private DiyTableService diyTableService;
	
	@RequestMapping
	public String index(Model model){
		
		//查询所有自定义表
		List<CstmTable> diyTables = designService.getDesignTables();

		for (int i = 0; i < diyTables.size(); i++) {
			if(diyTables.get(i).getTableNameDB().equals("product") ||diyTables.get(i).getTableNameDB().equals("order_info")){
				diyTables.remove(i--);
			}
		}
		
		model.addAttribute("allTables", diyTables);
		
		model.addAttribute("iframecontent","cstm/cstm-des-index");
		return "iframe";
		
//		return "cstm/cstm-des-index";
	}
	
	/**
	 * 
	 * 获取客户表描述信息
	 * @return
	 */
	@RequestMapping("data")
	@ResponseBody
	public String getDataDes(HttpServletRequest request , HttpServletResponse response,CustomerDesignCondition condition){

		condition.setRequest(request);

		PageResult<ColumnDesign> pageResult = designService.queryPage(condition);

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		List<ColumnDesign> customers = pageResult.getRet();

		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < customers.size(); i++) {
			
			JSONObject jsonObject2 = new JSONObject(customers.get(i));
			
			jsonObject2.put("columnType", ColumnDesign.COLUMNTYPE.get(customers.get(i).getColumnType()));
			jsonObject2.put("allowSelect", ColumnDesign.BOOLEANTYPE.get(customers.get(i).getAllowSelect()));
			jsonObject2.put("allowIndex", ColumnDesign.BOOLEANTYPE.get(customers.get(i).getAllowIndex()));
			jsonObject2.put("allowShow", ColumnDesign.BOOLEANTYPE.get(customers.get(i).getAllowShow()));
			
			jsonObject2.put("allowEmpty", ColumnDesign.BOOLEANEMPTY.get(customers.get(i).getAllowEmpty()));
			
			array.put(jsonObject2);
		}

		jsonObject.put("data", array);
		jsonObject.put("iTotalRecords", pageResult.getiTotalRecords());
		jsonObject.put("iTotalDisplayRecords", pageResult.getiTotalDisplayRecords());
		
		return jsonObject.toString();
	}
	
	/**
	 * 编辑表 页面跳转
	 * @return
	 */
	@RequestMapping("edit")
	public String toEditTable(Model model ,String id, String tablename,String tableNameStr){
	
		if(StringUtils.isNotBlank(id)){
			
			/*查询是否特殊*/
			ColumnDesign columnDesign = designService.getCustomerDesign(id);
			//如果是默认
			model.addAttribute("default", columnDesign);
		}
		model.addAttribute("tablename", tablename);
		model.addAttribute("tablenamestr", tableNameStr);
		model.addAttribute("COLUMNTYPE", ColumnDesign.COLUMNTYPE);
		
		return "cstm/cstm-edit-table-dialog";
	}

	
	/**
	 * 
	 * 保存编辑表
	 * @param request
	 * @param response
	 * @param columndesign
	 * @param allowSelect
	 * @param allowIndex
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("editSave")
	@ResponseBody
	public String editSave(HttpServletRequest request, HttpServletResponse response, ColumnDesign columndesign,
			String allowSelect, String allowIndex, String uid, String tablename) {
		
		
		String callType = request.getParameter("callType");
		
		if(StringUtils.isNotBlank(uid)){ //修改
			/*查询是否特殊*/
			columndesign = designService.getCustomerDesign(uid);
		}
		
		if(StringUtils.isBlank(uid)){
			
			/*保存表结构信息*/
			columndesign.setIsDefault(ColumnDesign.NOTDEFAULT);
			columndesign.setTableName(tablename);
			
			String colNameDB = designService.getColumnName();
			
			if( ("sys_call_log").equals(tablename) && StringUtils.isNotBlank(callType)){
				
				if("callOut".equals(callType)){
					colNameDB = "out_"+colNameDB;
				}

				if("callIn".equals(callType)){
					colNameDB = "in_"+colNameDB;
				}
			}
			
			columndesign.setColumnNameDB(colNameDB);
			
			columndesign.setAllowIndex(StringUtils.isNotBlank(columndesign.getAllowIndex())?columndesign.ALLOWINDEX:columndesign.NOTALLOWINDEX);
			columndesign.setAllowSelect(StringUtils.isNotBlank(columndesign.getAllowSelect())?columndesign.ALLOWSELECT:columndesign.NOTALLOWSELECT);
			columndesign.setAllowShow(StringUtils.isNotBlank(columndesign.getAllowShow())?columndesign.ALLOWSHOW:columndesign.NOTALLOWSHOW);
			columndesign.setAllowEmpty(StringUtils.isNotBlank(columndesign.getAllowEmpty())?columndesign.NOTALLOWEMPTY:columndesign.ALLOWEMPTY);
			
			//设置顺序
			columndesign.setOrders(diyTableService.getMaxTableOrder(tablename)+1);
			if(columndesign.getColumnType().equals(ColumnDesign.ENUMTYPE)){
				
				columndesign.setCharacterProperty(columndesign.getCharacterProperty().substring(0,columndesign.getCharacterProperty().lastIndexOf(",")));
			}
			diyTableService.addColumnDesin(columndesign);
			
			try {
				
				/*修改表结构 customer表结构*/
				diyTableService.alterAdd(columndesign, tablename,callType);
				
			} catch (Exception e) {
				//执行异常，报错
				StringWriter sw=new StringWriter();  
				PrintWriter pw=new PrintWriter(sw);  
				e.printStackTrace(pw);
				
				return new JSONObject().put("success", false).put("error", sw.toString()).toString();
			}
		}else{
			//修改
			if(columndesign.getIsDefault().equals(ColumnDesign.DEFAULT)){ //默认字段，修改表名
				columndesign.setColumnName(request.getParameter("orders"));
				designService.update(columndesign, new String[] { "id","columnName",
						"column_name_db", "tableName", "column_type",
						"is_default", "column_value", "character_property",
						"allow_select", "allow_index", "allow_show", "orders" });
			}else{
				
				columndesign.setColumnName(request.getParameter("columnName"));
				
				designService.setValue(columndesign, request);
				
				designService.update(columndesign, new String[]{ "id",
						"column_name_db", "tableName", "column_type",
						"is_default", "column_value", "character_property","orders"});
			}
		}
		
		
		diyTableService.load();
		
		return new JSONObject().put("success", true).put("error", "").put("tablename", tablename).toString();
	}
	
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public String delete(String uuid,String tableName){
		
		if(StringUtils.isNotBlank(uuid)){
			
			//删除数据库字段  删除客户表字段
			ColumnDesign columnDesign = diyTableService.delete(uuid, tableName);
			diyTableService.dropColumn(columnDesign, tableName);
			
			diyTableService.load();
			return new JSONObject().put("success", true).toString();
		}
		
		return new JSONObject().put("success", false).toString();
	}
	
	
	/**
	 * 校验列明不能重复
	 * @param request
	 * @param response 
	 * @param columnName 列明 
	 * @return
	 */
	@RequestMapping("columnRepeat")
	@ResponseBody
	public void columnNameValidate(HttpServletRequest request,HttpServletResponse response,String columnName) throws Exception{
		
		designService.checkNameRepeat(request, response, columnName);
		
	}
	
	
	/**
	 * 校验顺序不能重复
	 * @param request
	 * @param response 
	 * @param columnName 列明 
	 * @return
	 */
	@RequestMapping("orderRepeat")
	@ResponseBody
	public void columnOrderValidate(HttpServletRequest request,HttpServletResponse response,String orders) throws Exception{
		
		designService.checkOrdersRepeat(request, response, orders);
	}
	/**
	 * 校验列明不能重复
	 * @param request
	 * @param response 
	 * @param  length 长度
	 * @return
	 */
	@RequestMapping("lengthCompare")
	@ResponseBody
	public void lengthCompare(HttpServletRequest request,HttpServletResponse response,String characterProperty) throws Exception{
		
		designService.comparelength(request, response, characterProperty);
	}

	
	/**
	 * 修改字段顺序
	 * @param request
	 * @param response
	 * @param uid 编号uuid
	 * @param orders 顺序
	 * @param tag 先前还是向后 1向前 0向后
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("alterOrders")
	@ResponseBody
	public String alterOrders(HttpServletRequest request,HttpServletResponse response,String uid,String orders,String tag,String tableName) throws Exception{
		
		if(StringUtils.isNotBlank(tag) &&StringUtils.isNotBlank(uid) &&StringUtils.isNotBlank(orders) ){

			String res = designService.alterOrders(uid, orders, tag);
			if(res.equals("0")){
				
				return new JSONObject().put("success", true).put("tableName", tableName).toString();
			}else{
				
				return new JSONObject().put("success", false).put("errorMsg", "修改异常！").put("tableName", tableName).toString();
			}
		}
		
		return new JSONObject().put("success", false).put("errorMsg", "参数不足，修改失败！").put("tableName", tableName).toString();
		
	}
}
