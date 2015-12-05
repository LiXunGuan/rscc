package com.ruishengtech.rscc.crm.ui.mw.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.Page;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyBean;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.cstm.model.CustomerDesign;
import com.ruishengtech.rscc.crm.ui.SessionUtil;
import com.ruishengtech.rscc.crm.ui.mw.model.CallLog;
import com.ruishengtech.rscc.crm.ui.mw.solution.CallLogSolution;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Service
@Transactional
public class CallLogService extends BaseService {

	@Autowired
	@Qualifier(value = "diyTableService")
	private DiyTableService diyTableService;
	
	@Autowired
	private UserService userService;
	
	public List<CallLog> getCallLogs() {
		List<CallLog> list = getBeanList(CallLog.class,"");
		return list;
	}

	public CallLog getByUuid(UUID uuid) {
		return super.getByUuid(CallLog.class, uuid);
	}
	
	public CallLog getCallLogBySession(String sessionUuid) {
		List<CallLog> list = getBeanList(CallLog.class, "and call_session_uuid = ?", sessionUuid);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public boolean save(CallLog c) {
		try {
			super.save(c);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void save(CallLog c, String[] excludeFieldName) {
		super.save(c, excludeFieldName);
	}
	
	public boolean update(CallLog c) {
		try {
	        super.update(c,new String[]{"agent_id","agent_name","call_phone","call_time","in_out_flag","data_source","data_id","call_session_uuid","record_path","talk_time"});
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean updateCDR(CallLog c) {
		try {//只更新cdr相关的三个记录
	        super.update(c,new String[]{"agent_id","agent_name","call_phone","call_time","text_log","in_out_flag","data_source","data_id","call_session_uuid"});
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void update(CallLog c, String[] excludeFieldName) {
		 super.update(c, excludeFieldName);
	}
	
	public boolean deleteById(UUID uuid) {
		try {
			super.deleteById(CallLog.class, uuid);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean deleteBySession(String sessionUuid) {
		try {
			jdbcTemplate.update("delete from sys_call_log where call_session_uuid=?", sessionUuid);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public PageResult<Map<String, Object>> queryPage(
			Map<String, ColumnDesign> str, HttpServletRequest request) {
		//如果点击的是部门客服记录，取出当前登录用户所管辖的所有人的uid
		if(null == request.getAttribute("level")){
			request.setAttribute("users", userService.getManagerUsers(SessionUtil.getCurrentUser(request).getUid()));
		}
		return diyTableService.queryPage(new CallLogSolution(), str,request);
	}
	
	//获取sys_call_log表中和自定义表中字段对应的可以显示的列
	public Map<String, String> getColumns() {
		HashMap<String,String> map = new LinkedHashMap<String,String>();
		Map<String,ColumnDesign> ret = diyTableService.getDiyColumns("sys_call_log");
		ret = sortMapByValue(ret);
		for (ColumnDesign columnDesign : ret.values()) {
			if(columnDesign.getAllowShow().equals(ColumnDesign.ALLOWSHOW)){ //查询允许显示的
				map.put(columnDesign.getColumnNameDB(),columnDesign.getColumnName());
			}
		}
		
		return map;
	}
	
	
    /** 
     * 使用 Map按value进行排序 
     * @param map 
     * @return 
     */  
    public static Map<String, ColumnDesign> sortMapByValue(Map<String, ColumnDesign> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, ColumnDesign> sortedMap = new LinkedHashMap<String, ColumnDesign>();  
        List<Map.Entry<String, ColumnDesign>> entryList = new ArrayList<Map.Entry<String, ColumnDesign>>(map.entrySet());  
        Collections.sort(entryList, new Comparator<Map.Entry<String, ColumnDesign>>() {
        	 public int compare(Entry<String, ColumnDesign> me1, Entry<String, ColumnDesign> me2) {  
                 return me1.getValue().getOrders().compareTo(me2.getValue().getOrders());  
             }  
		});  
        Iterator<Map.Entry<String, ColumnDesign>> iter = entryList.iterator();  
        Map.Entry<String, ColumnDesign> tmpEntry = null;  
        while (iter.hasNext()) {  
            tmpEntry = iter.next();  
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());  
        }  
        return sortedMap;  
    }  
    
  //比较器类  
    public class MapValueComparator implements Comparator<Map.Entry<String, String>> {  
        public int compare(Entry<String, String> me1, Entry<String, String> me2) {  
            return me1.getValue().compareTo(me2.getValue());  
        }  
    }  

	
	public Map<String, String> getSelects() {
		HashMap<String,String> map = new HashMap<String,String>();
		Map<String,ColumnDesign> ret = diyTableService.getDiyColumns("sys_call_log");
		for (ColumnDesign columnDesign : ret.values()) {
			if(columnDesign.getAllowShow().equals(ColumnDesign.ALLOWSELECT)){ //查询允许显示的
				map.put(columnDesign.getColumnNameDB(),columnDesign.getColumnName());
			}
		}
		return map;
	}
	
	public Map<String, ColumnDesign> getSelectData() {
		HashMap<String, ColumnDesign> map = new HashMap<String, ColumnDesign>();
		Map<String,ColumnDesign> ret = diyTableService.getDiyColumns("sys_call_log");
		for (ColumnDesign columnDesign : ret.values()) {
			if(columnDesign.getAllowShow().equals(ColumnDesign.ALLOWSELECT)){ //查询允许显示的
				map.put(columnDesign.getColumnNameDB(),columnDesign);
			}
		}
		return map;
	}
	
	public Map<String, ColumnDesign> getTableDef() {
		return diyTableService.getDiyColumns("sys_call_log");
	}
	
	public JSONObject getJsonObject(Map<String, Object> str) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject jsonObject2 = new JSONObject();
		Map<String,ColumnDesign> ret = diyTableService.getDiyColumns("sys_call_log");
		
		for (ColumnDesign columnDesign : ret.values()) {
			if(ColumnDesign.ALLOWSHOW.equals(columnDesign.getAllowShow())){//如果是要显示的
				
				if (columnDesign.getColumnType().equals(ColumnDesign.DATETYPE)){//如果是时间类型 格式化时间
					try {
						jsonObject2.put(columnDesign.getColumnNameDB(),fmt.format(str.get(columnDesign.getColumnNameDB())));
					} catch (Exception e) {
						e.printStackTrace();
					} 
				} else if ("talk_time".equals(columnDesign.getColumnNameDB()) && str.get(columnDesign.getColumnNameDB()) != null ) {
					jsonObject2.put(columnDesign.getColumnNameDB(),(int)Math.ceil((int)str.get(columnDesign.getColumnNameDB()) * 1.0 / 1000 ));
				} else {
					jsonObject2.put(columnDesign.getColumnNameDB(),str.get(columnDesign.getColumnNameDB()));
				}
			}
		}
		jsonObject2.put("uid",str.get("uuid"));
        return jsonObject2;
	}
	
	public void update(Map<String, String[]> map){
		diyTableService.update(map, CallLog.class);
	}

	public void updateByCallSessionUuid(String callSessionUuid, String textLog){
		jdbcTemplate.update("update sys_call_log set text_log=? where call_session_uuid=?", textLog, callSessionUuid);
	}
	
	public List<String[]> getCallLogByPhone(String phone, Page page) {
		List<String[]> resultList = new ArrayList<String[]>();
		List<Map<String, Object>> list = jdbcTemplate
				.queryForList(
						"SELECT call_time, text_log from sys_call_log where call_phone=? order by call_time desc limit ? offset ? ",
						phone, page.getLength(), page.getStart());
		for (Map<String, Object> map : list) {
			resultList.add(new String[] { String.valueOf(map.get("call_time")),
					map.get("text_log")==null?"":map.get("text_log")+""});
		}
		return resultList;
	}
	
	/**
	 * 
	 * 根据编号查询记录信息
	 * @param uuid
	 * @return
	 */
	public Map<String, Object> getCallLogById(UUID uuid) {
		
		return jdbcTemplate.queryForMap(" SELECT * FROM `sys_call_log` WHERE uuid = ? ",uuid.toString());
		
	}
	
	
	public JSONObject getTitleAndData(DiyTableService diyTableManager) {

		JSONObject jsonObject = new JSONObject();

		DiyBean ret = diyTableManager.getTableDescByName("sys_call_log");
		
		for (com.ruishengtech.framework.core.db.diy.ColumnDesign customerDesign : ret.getClomns().values()) {
			
			if(customerDesign.getAllowShow().equals(CustomerDesign.ALLOWSHOW)){ //查询允许显示的
				jsonObject.put(customerDesign.getColumnNameDB(),customerDesign);
			}
		}

		return jsonObject;
	}
		
}
