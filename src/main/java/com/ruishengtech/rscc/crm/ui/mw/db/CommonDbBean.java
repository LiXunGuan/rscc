package com.ruishengtech.rscc.crm.ui.mw.db;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Key;

public class CommonDbBean {

    @Key
    @Column(meta = "SERIAL")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

