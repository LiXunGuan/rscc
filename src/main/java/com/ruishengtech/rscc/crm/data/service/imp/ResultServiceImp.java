package com.ruishengtech.rscc.crm.data.service.imp;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.diy.ColumnDesign;
import com.ruishengtech.framework.core.db.diy.DiyTableService;
import com.ruishengtech.rscc.crm.data.service.ResultDesignService;
import com.ruishengtech.rscc.crm.data.service.ResultService;
import com.ruishengtech.rscc.crm.data.service.TaskDesignService;
import com.ruishengtech.rscc.crm.data.solution.ResultSolution;

@Service
@Transactional
public class ResultServiceImp extends DiyTableService implements ResultService {

	@Autowired
	private TaskDesignService taskDesignService;
	
	@Autowired
	private ResultDesignService resultDesignService;
	
	public void deleteByUuid(String tableName, String uuid) {
		super.deleteById(tableName, null, UUID.UUIDFromString(uuid));
	}
	
	public void save(String tableName, Map<String, String[]> str) {
		resultDesignService.save(tableName, str);
	}
	
	public void update(String tableName, Map<String, String[]> str) {
		resultDesignService.update(tableName, str);
	}
	
	public int importToResult(String sourceTable, String targetTable) {
		String sql = "insert into data_result_" + targetTable + "(uuid,item_name,item_phone,item_describe,item_address,item_stat) (select * from data_item_" + sourceTable + ")";
		return jdbcTemplate.update(sql);
	}

	@Override
	public void deleteById(String tableName, String uuid) {
		super.deleteById("data_result_" + tableName, null, UUID.UUIDFromString(uuid));
	}

	@Override
	public PageResult<Map<String, Object>> queryPage(
			Map<String, ColumnDesign> str, HttpServletRequest request) {
		return queryPage(new ResultSolution(), str,request);
	}

	@Override
	public JSONObject getJsonObject(Map<String, Object> str) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject jsonObject2 = new JSONObject();
		Map<String,ColumnDesign> ret = resultDesignService.getTableDef("result");
		
		for (ColumnDesign columnDesign : ret.values()) {
			if(ColumnDesign.ALLOWSHOW.equals(columnDesign.getAllowShow())){//如果是要显示的
				
				if(columnDesign.getColumnType().equals(ColumnDesign.DATETYPE)){//如果是时间类型 格式化时间
					try {
						jsonObject2.put(columnDesign.getColumnNameDB(),fmt.format(str.get(columnDesign.getColumnNameDB())));
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}else{
					jsonObject2.put(columnDesign.getColumnNameDB(),str.get(columnDesign.getColumnNameDB()));
				}
			}
		}
		jsonObject2.put("uid",str.get("uuid"));
        return jsonObject2;
	}

	@Override
	public Map<String, Object> getResultById(String tableName, UUID uuid) {
		return jdbcTemplate.queryForMap(" SELECT * FROM data_result_" + tableName + " WHERE uuid = ? ",uuid.toString());
	}

	@Override
	public List<String> getAllResult(String tableName) {
		return jdbcTemplate.queryForList(" SELECT uuid FROM data_result_" + tableName, String.class);
	}

	@Override
	public int getResultCount(String tableName) {
		return jdbcTemplate.queryForObject(" select count(*) from (SELECT uuid FROM data_result_" + tableName + " group by uuid) as b", Integer.class);
	}
	
	public int distinct(String uuid) {
		int count = 0;
		try {
			StringBuilder distinctSql = new StringBuilder();
			distinctSql.append("delete b from ").append("data_result_" + uuid).
				append(" b ,(select *,max(item_owner) as id from ").append("data_result_" + uuid).
				append(" group by item_phone having count(item_phone) > 1) as d where b.uuid>d.uuid and b.item_phone = d.item_phone and b.call_times = 0");
			count = getTemplate().update(distinctSql.toString());
//			d.setDistinctFlag("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
}
