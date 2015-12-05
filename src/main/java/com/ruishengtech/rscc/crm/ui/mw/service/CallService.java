package com.ruishengtech.rscc.crm.ui.mw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.UUID;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.ui.mw.model.Call;

@Service
@Transactional
public class CallService extends BaseService {
	
	public List<Call> getCalls() {
		List<Call> list = getBeanList(Call.class,"");
		return list;
	}

	public Call getByUuid(UUID uuid) {
		return super.getByUuid(Call.class, uuid);
	}
	
	public Call getCallBySession(String sessionUuid) {
		List<Call> list = getBeanList(Call.class, "and call_session_uuid = ?", sessionUuid);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
	}
	
	public boolean save(Call c) {
		try {
			super.save(c);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void save(Call c, String[] excludeFieldName) {
		super.save(c, excludeFieldName);
	}
	
	public boolean update(Call c) {
		try {
	        super.update(c,new String[]{"agent_id","call_phone","exten","call_session_uuid"});
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void update(Call c, String[] excludeFieldName) {
		 super.update(c, excludeFieldName);
	}
	
	public boolean deleteById(UUID uuid) {
		try {
			super.deleteById(Call.class, uuid);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean deleteBySession(String sessionUuid) {
		try {
			jdbcTemplate.update("delete from agent_call where call_session_uuid=?", sessionUuid);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

//	public PageResult<Project> queryPage(ICondition condition) {
//		return super.queryPage(new ProjectSolution(), condition, Project.class);
//	}
	
}
