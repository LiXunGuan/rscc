package com.ruishengtech.rscc.crm.ui.mw.condition;

import com.ruishengtech.framework.core.db.condition.Page;

/**
 * Created by yaoliceng on 2014/11/3.
 */
public class SipUserCondition extends Page {
	
	private Integer sipStrat;
	private Integer sipEnd;

    private String uidname;

    public String getUidname() {
        return uidname;
    }

    public void setUidname(String uidname) {
        this.uidname = uidname;
    }

	public Integer getSipStrat() {
		return sipStrat;
	}
	public void setSipStrat(Integer sipStrat) {
		this.sipStrat = sipStrat;
	}
	public Integer getSipEnd() {
		return sipEnd;
	}
	public void setSipEnd(Integer sipEnd) {
		this.sipEnd = sipEnd;
	}
}
