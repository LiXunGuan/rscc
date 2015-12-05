package com.ruishengtech.rscc.crm.datamanager.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.datamanager.model.UserDataLog;

@Service
@Transactional
public class DataIntentServiceImp extends BaseService{

	public DataIntent getByUuid(String intentUuid) {
		return super.getByUuid(DataIntent.class, UUID.UUIDFromString(intentUuid));
	}
	
	public void save(DataIntent d) {
		super.save(d);
	}
	
	public void update(DataIntent d) {
		super.update(d);
	}

	public List<DataIntent> getAll(){
		return getBeanList(DataIntent.class, " AND 1=? ORDER BY seq ASC ", "1");
	}
	
	public boolean delete(String uid){
		//根据uid在new_data_department_user_log中的type_des字段中是否存在 判断意向是否被占用
		int intentnum = jdbcTemplate.queryForObject("select count(*) from new_data_department_user_log where type_des=?",Integer.class,uid);
		List<String> optype = new ArrayList<>();
		if(intentnum >0){
			return false;
		} else {
			deleteById(DataIntent.class, UUID.UUIDFromString(uid));
			return true;
		}
	}

	public String batSave(String intentName, String intentInfo) {
		DataIntent dataIntent = new DataIntent();
		dataIntent.setIntentName(intentName);
		dataIntent.setIntentInfo(intentInfo);
		this.save(dataIntent);
		return dataIntent.getUid();
	}
	
//	public void insert() {
//		StringBuilder batchSql = new StringBuilder();
//		batchSql.append("INSERT INTO new_data_batch_5962886399714488bfa8ae0f85693dfe(uuid,batch_uuid,phone_number,json) VALUES (?,?,?,?)");
//		List<String[]> lists = new ArrayList<>(151);
//		for (int i = 0; i < 150; i++) {
//			lists.add(new String[]{"5962886399714488bfa8ae0f8569" + i * 20, "5962886399" + i,"{'abc':'321'}sfafsssafsfsff"});
//		}
//		final List<String[]> list = lists;
//		for(int j = 0; j < 100 ; j++) {
//			long start = System.currentTimeMillis();
//			int[] ret = jdbcTemplate.batchUpdate(batchSql.toString(), new BatchPreparedStatementSetter() {
//				public int getBatchSize() {
//	                return list.size();
//	            }
//	            public void setValues(PreparedStatement ps, int i)throws SQLException {
//	            	String[] l = list.get(i);
//	                ps.setString(1, UUID.randomUUID().toString());
//	                ps.setString(2, l[0]);
//	                ps.setString(3, l[1]);
//	                ps.setString(4, l[2]);
//	            }
//			});
//			System.out.println(System.currentTimeMillis() - start);
//		}
//		
//	}
	
}
