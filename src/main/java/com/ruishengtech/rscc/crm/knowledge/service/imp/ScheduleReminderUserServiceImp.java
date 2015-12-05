package com.ruishengtech.rscc.crm.knowledge.service.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruishengtech.framework.core.db.handler.BeanHandler;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.rscc.crm.knowledge.model.ScheduleReminderUser;
import com.ruishengtech.rscc.crm.knowledge.service.ScheduleReminderUserService;

@Service
@Transactional
public class ScheduleReminderUserServiceImp  extends BaseService implements ScheduleReminderUserService {

	@Override
	public void saveOrUpdate(ScheduleReminderUser reminderUser) {
		if(reminderUser == null){
			return;
		}
		super.save(reminderUser);
	}

	@Override
	public void deleteByScheduleUUID(String scheduleuuid) {
		jdbcTemplate.update(" delete from schedule_reminder_user WHERE schedule_uuid  = ? ", scheduleuuid);
	}

	@Override
	public void deleteByUSID(String usreUUID, String scheduleuuid) {
		jdbcTemplate.update(" delete from schedule_reminder_user WHERE createuser_uuid  = ? and schedule_uuid = ? ", usreUUID, scheduleuuid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScheduleReminderUser> getScheduleReminderUserByUserUUid(final String uuid) {
		if (StringUtils.isNotBlank(uuid)) {
			
			List<ScheduleReminderUser> ru = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT sru.*,sr.schedule_content as schedulecontent FROM schedule_reminder_user sru LEFT JOIN schedule_reminder sr on sru.schedule_uuid = sr.uuid WHERE sru.createuser_uuid = ? ");
					params.add(uuid);
				}
			}, ScheduleReminderUser.class);
			if (ru.size() > 0) {
				return ru;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ScheduleReminderUser getScheduleByUUID(final String uuid) {
		if (StringUtils.isNotBlank(uuid)) {
			
			List<ScheduleReminderUser> ru = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM schedule_reminder_user WHERE schedule_uuid = ? ");
					params.add(uuid);
				}
			}, ScheduleReminderUser.class);
			if (ru.size() > 0) {
				return ru.get(0);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ScheduleReminderUser getScheduleByScheduleReminderUUID(final String uuid) {
		if (StringUtils.isNotBlank(uuid)) {
			
			List<ScheduleReminderUser> ru = queryBean(new BeanHandler() {
				@Override
				public void doSql(StringBuilder sql, List<Object> params) {
					sql.append(" SELECT * FROM schedule_reminder_user WHERE schedule_uuid = ? ");
					params.add(uuid);
				}
			}, ScheduleReminderUser.class);
			if (ru.size() > 0) {
				return ru.get(0);
			}
		}
		return null;
	}

}
