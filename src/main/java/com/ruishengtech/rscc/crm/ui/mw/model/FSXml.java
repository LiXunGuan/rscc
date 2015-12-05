package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/10/21.
 */
@Table(name = "mw.fs_xml")
public class FSXml extends CommonDbBean {

    public static final String MULTIPLE = "multiple";
    public static final String SINGLE = "single";


    public static final String CATALOG_GATEWAY = "CATA_GW";
    public static final String CATALOG_SIPUSER = "CATA_SIPUSER";
    public static final String CATALOG_SIPPROFILE = "CATA_SIPPROFILE";
    public static final String CATALOG_QUEUE = "CATA_QUEUE";
    public static final String CATA_XML_IVR = "CATA_XML-IVR";
    public static final String CATA_XML_DIALPLAN = "CATA_XML-DIALPLAN";

    public static final String CATALOG_IVR = "CATA_IVR";




    public static Map<String, String> CATA = new LinkedHashMap<String, String>() {
		{
			put(CATA_XML_IVR, "IVR");
			put(CATA_XML_DIALPLAN, "DIALPLAN");
		}
	};

    @Column(meta = "VARCHAR(64)")
    private String name;

    /**
     * 需要刷新的条目
     */
    @Column(meta = "VARCHAR(64)")
    private String cata;

    /**
     * d 开始表示是目录下一起要刷的文件目录
     * f 开始表示是单文件
     */
    @Column(meta = "VARCHAR(10)")
    private String type;

    @Column(meta = "LONGTEXT")
    private String content;

    @Column(meta = "VARCHAR(200)")
    private String dest;

    /**
     * 状态
     * <p/>
     * 0 需要刷新
     * <p/>
     * 1 不需要刷新
     */
    @Column(meta = "CHAR(1)")
    private String status;

    @Column(meta = "VARCHAR(100)")
    private String hostname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCata() {
        return cata;
    }

    public void setCata(String cata) {
        this.cata = cata;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
