package com.ruishengtech.rscc.crm.ui.mw.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/10/21.
 */
@Table(name = "mw.uploading_voice")
public class UploadingVoice extends CommonDbBean {
	
    /**
     * 语音名
     */
    @Column(meta = "VARCHAR(30)")
   	private String name;
    
    /**
     * 语音
     */
    @NColumn(meta = "VARCHAR(300)")
    private String voice;
    

    /**
     * 语音描述
     */
    @NColumn(meta = "VARCHAR(1000)")
    private String info;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getVoice() {
		return voice;
	}


	public void setVoice(String voice) {
		this.voice = voice;
	}


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}
    
    
    
}
