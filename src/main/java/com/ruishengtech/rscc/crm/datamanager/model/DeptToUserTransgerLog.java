package com.ruishengtech.rscc.crm.datamanager.model;

import java.util.Date;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.Table;

/**
 * @author Frank
 *
 */
@Table(name = "transfer_log")
public class DeptToUserTransgerLog extends CommonDbBean {

	@Column(column = "user_uuid", meta = "VARCHAR(64)")
	@Index
	private String userUuid;

	@Column(column = "user_total_count", meta = "VARCHAR(64)")
	private String userTotalCount;

	@Column(column = "user_day_count", meta = "VARCHAR(64)")
	private String userDayCount;

	@Column(column = "user_single_count", meta = "VARCHAR(64)")
	private String userSingleCount;

	@Column(column = "user_real_count", meta = "VARCHAR(64)")
	private String userRealCount;

	@Column(column = "gotdate", meta = "DATETIME")
	@Index(name = "gotdate", type = IndexDefinition.TYPE_NORMAL,method = IndexDefinition.METHOD_BTREE)
	private Date gotDate;

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getUserTotalCount() {
		return userTotalCount;
	}

	public void setUserTotalCount(String userTotalCount) {
		this.userTotalCount = userTotalCount;
	}

	public String getUserDayCount() {
		return userDayCount;
	}

	public void setUserDayCount(String userDayCount) {
		this.userDayCount = userDayCount;
	}

	public String getUserSingleCount() {
		return userSingleCount;
	}

	public void setUserSingleCount(String userSingleCount) {
		this.userSingleCount = userSingleCount;
	}

	public String getUserRealCount() {
		return userRealCount;
	}

	public void setUserRealCount(String userRealCount) {
		this.userRealCount = userRealCount;
	}

	public Date getGotDate() {
		return gotDate;
	}

	public void setGotDate(Date gotDate) {
		this.gotDate = gotDate;
	}

}
