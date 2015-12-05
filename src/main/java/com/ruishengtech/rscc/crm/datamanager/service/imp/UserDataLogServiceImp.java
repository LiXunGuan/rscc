package com.ruishengtech.rscc.crm.datamanager.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.util.DateUtils;
import com.ruishengtech.rscc.crm.datamanager.manager.node.AbandonNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.BlacklistNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.CustomerNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.IntentNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.ShareNode;
import com.ruishengtech.rscc.crm.datamanager.manager.node.UserNode;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToAbandonData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToBlacklistData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToCustomerData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToIntentData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToShareData;
import com.ruishengtech.rscc.crm.datamanager.manager.transferdata.UserToUserData;
import com.ruishengtech.rscc.crm.datamanager.model.DataIntent;
import com.ruishengtech.rscc.crm.datamanager.model.UserDataLog;
import com.ruishengtech.rscc.crm.datamanager.service.DataBatchService;
import com.ruishengtech.rscc.crm.datamanager.solution.UserDataLogSolution;
import com.ruishengtech.rscc.crm.user.model.User;
import com.ruishengtech.rscc.crm.user.service.UserService;

@Service
@Transactional
public class UserDataLogServiceImp extends BaseService{
	
	@Autowired
	private DataIntentServiceImp dataIntentService;
	
	public PageResult<UserDataLog> queryPage(ICondition condition) {
		
		return super.queryPage(new UserDataLogSolution(), condition, UserDataLog.class);
	}

	public UserDataLog getByUuid(String intentUuid) {
		return super.getByUuid(UserDataLog.class, UUID.UUIDFromString(intentUuid));
	}
	
	public void save(UserDataLog d) {
		super.save(d);
	}
	
	public void update(UserDataLog d) {
		super.update(d);
	}

	public List<UserDataLog> getAll(){
		return getBeanList(UserDataLog.class, "");
	}

	//获取前一天所有的数据
	public List<UserDataLog> getYesterdayData(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = sdf.format(cal.getTime());
		
		return jdbcTemplate.queryForList("SELECT * "
		+"	FROM "
		+"		new_data_department_user_log "
		+"	WHERE"
		+"		op_time >= ? AND op_time < ? ", UserDataLog.class, yesterday+" 00:00:00",yesterday+" 23:59:59");
//		return super.getBeanList(UserDataLog.class, " AND op_time >= ? AND op_time < ? ", sdf.format(stime),sdf.format(etime));
	}

	//获取各类型的添加数量
	public int getPhoneTypeCount(String phonetype){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = sdf.format(cal.getTime());
		
		int count = jdbcTemplate.queryForObject("SELECT count(*) FROM "
					+"	new_data_department_user_log"
					+"	WHERE"
					+"	op_time >= ?"
					+"  AND op_time < ? "
					+"	AND op_type=? ",Integer.class,yesterday+" 00:00:00",yesterday+" 23:59:59",phonetype);
		return count;
	}
	//获取减少数量
	public int getDelCount(String type){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = sdf.format(cal.getTime());
		
		int count = jdbcTemplate.queryForObject("SELECT count(*) FROM "
				+"	new_data_department_user_log"
				+"	WHERE"
				+"	op_time >= ?"
				+"  AND op_time < ? "
				+"	AND old_stat= ? ",Integer.class,yesterday+" 00:00:00",yesterday+" 23:59:59",type);
	return count;
	}
}
