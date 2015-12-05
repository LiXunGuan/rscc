package com.ruishengtech.rscc.crm.ui.mw.condition;

import com.ruishengtech.framework.core.db.condition.Page;

/**
 * Created by yaoliceng on 2014/11/3.
 */
public class FSXmlCondition extends Page {
    private String uidname;
    private String type;

    public String getUidname() {
        return uidname;
    }

    public void setUidname(String uidname) {
        this.uidname = uidname;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
