package com.ruishengtech.rscc.crm.ui.mw.model;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/10/21.
 */

/**
 * 坐席
 */
@Table(name = "mw.fs_user")
public class FSUser extends CommonDbBean {

	@Column(meta = "VARCHAR(64)", column = "sip_id")
	private String sipId;

	@Column(meta = "VARCHAR(30)", column = "sip_password")
	private String sipPassword;

	@NColumn(meta = "VARCHAR(100)")
    private String caller_id_number;

	@NColumn(meta = "VARCHAR(100)")
    private String caller_id_name;

	@NColumn(meta = "VARCHAR(100)")
    private String area_code;

    public String getCaller_id_number() {
        return caller_id_number;
    }

    public void setCaller_id_number(String caller_id_number) {
        this.caller_id_number = caller_id_number;
    }

    public String getCaller_id_name() {
        return caller_id_name;
    }

    public void setCaller_id_name(String caller_id_name) {
        this.caller_id_name = caller_id_name;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

	public String getSipId() {
		return sipId;
	}

	public void setSipId(String sipId) {
		this.sipId = sipId;
	}

	public String getSipPassword() {
		return sipPassword;
	}

	public void setSipPassword(String sipPassword) {
		this.sipPassword = sipPassword;
	}

	public String toXml() {

        StringBuilder s = new StringBuilder("" +
                "<include>\n" +
                "  <user id=\"" + getSipId() + "\">\n" +
                "    <params>\n" +
                "      <param name=\"password\" value=\"" + getSipPassword() + "\"/>\n" +
                "      <param name=\"vm-password\" value=\"" + getSipPassword() + "\"/>\n" +
                "    </params>\n" +
                "    <variables>\n" +
                "      <variable name=\"toll_allow\" value=\"domestic,international,local\"/>\n" +
                "      <variable name=\"accountcode\" value=\"" + getSipId() + "\"/>\n" +
//                "      <variable name=\"sip-force-expires\" value=\"10\"/>\n" +
                "      <variable name=\"user_context\" value=\"default\"/>\n" +
                "      <variable name=\"effective_caller_id_name\" value=\"Extension " + getSipId() + "\"/>\n" +
                "      <variable name=\"effective_caller_id_number\" value=\"" + getSipId() + "\"/>\n" +
                "      <variable name=\"outbound_caller_id_name\" value=\"$${outbound_caller_name}\"/>\n" +
                "      <variable name=\"outbound_caller_id_number\" value=\"$${outbound_caller_id}\"/>\n" +
                "    </variables>\n" +
                "  </user>\n" +
                "</include>\n");

        return s.toString();

    }






}
