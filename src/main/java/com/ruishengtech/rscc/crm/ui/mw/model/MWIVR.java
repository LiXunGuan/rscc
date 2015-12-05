package com.ruishengtech.rscc.crm.ui.mw.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2015/6/9.
 */
@Table(name = "mw.mw_ivr")
public class MWIVR extends CommonDbBean {

    @Column(meta = "VARCHAR(64)")
    private String name;

    @Column(meta = "VARCHAR(64)")
    private String exten;

    @Column(meta = "LONGTEXT")
    private String content;

    public String getExten() {
        return exten;
    }

    public void setExten(String exten) {
        this.exten = exten;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
