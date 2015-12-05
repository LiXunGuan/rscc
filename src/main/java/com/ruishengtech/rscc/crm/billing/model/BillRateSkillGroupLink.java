package com.ruishengtech.rscc.crm.billing.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.Table;

@Table(name="billrate_skillgroup")
public class BillRateSkillGroupLink {
	
	/**
	 * 费率UUID
	 */
	@Key
	@Column(meta="VARCHAR(64)",column="billrateS_uuid")
	private String billrateSkillUUID;
	
	/**.
	 * 技能组UUID
	 */
	@Key
	@Column(meta="VARCHAR(64)",column="skillGroup_uuid")
	private String skillGroupUUID;

	public String getBillrateSkillUUID() {
		return billrateSkillUUID;
	}

	public void setBillrateSkillUUID(String billrateSkillUUID) {
		this.billrateSkillUUID = billrateSkillUUID;
	}

	public String getSkillGroupUUID() {
		return skillGroupUUID;
	}

	public void setSkillGroupUUID(String skillGroupUUID) {
		this.skillGroupUUID = skillGroupUUID;
	}

}
