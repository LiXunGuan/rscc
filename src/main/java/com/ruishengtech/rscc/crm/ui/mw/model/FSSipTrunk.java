package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ruishengtech.framework.core.db.*;

/**
 * Created by yaoliceng on 2014/10/21.
 */
@Table(name = "mw.fs_gateway")
public class FSSipTrunk {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FSSipTrunk)) return false;

        FSSipTrunk fsGateway = (FSSipTrunk) o;

        if (!id.equals(fsGateway.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Key
    @Column(meta = "SERIAL")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


	
	/**
	 * 不注册
	 */
	public static final String EXTERNAL_N = "0";

	/**
	 * 注册
	 */
	public static final String EXTERNAL_Y = "1";
	
	
	public static Map<String, String> EXTERNAL = new LinkedHashMap<String, String>() {
		{
			put(EXTERNAL_N, "不注册");
			put(EXTERNAL_Y, "注册");
			
		}
		
	};

    /**
     * 名称
     */
    @Column(meta = "VARCHAR(64)")
    private String name;

    /**
     * 类型
     */
    @Column(meta = "CHAR(1)")
    private String registration;


    /**
     * 外线的ip地址
     */
    @Column(meta = "VARCHAR(64)")
    private String ip;
    
    /**
     * 外线的端口
     */
    private String port;


    /**
     * 注册的登录用户名
     */
    @NColumn(meta = "VARCHAR(64)")
    private String username;

    /**
     * 注册的密码
     */
    @NColumn(meta = "VARCHAR(64)")
    private String password;



    /**
     * 主机号码
     */
    @Column(meta = "INT")
    private Long fshost_id;



    private String servername;
    private String number;

    /**
     * SipProfile
     */
    @Column(meta = "INT")
    private Long sipProfileId;

    private String sipprofilename;
    private boolean status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public String getSipprofilename() {
		return sipprofilename;
	}

	public void setSipprofilename(String sipprofilename) {
		this.sipprofilename = sipprofilename;
	}

	public Long getSipProfileId() {
		return sipProfileId;
	}

	public void setSipProfileId(Long sipProfileId) {
		this.sipProfileId = sipProfileId;
	}

 
    public String toXml() {

        StringBuilder stringBuilder = new StringBuilder();

        if (getRegistration().equals(EXTERNAL_Y)) {

            stringBuilder.append("<include>").append("\n");
            stringBuilder.append("<gateway name=\"" + getName() + "\"> ").append("\n");
            stringBuilder.append("<param name=\"username\" value=\"" + getUsername() + "\"/>").append("\n");
            stringBuilder.append("<param name=\"realm\" value=\"" + getIp() + "\"/>").append("\n");
            stringBuilder.append("<param name=\"password\" value=\"" +getPassword() + "\"/>").append("\n");
            stringBuilder.append("<param name=\"expire-seconds\" value=\"60\"/>").append("\n");
            stringBuilder.append("<param name=\"register\" value=\"true\"/>").append("\n");
            stringBuilder.append("<param name=\"retry-seconds\" value=\"30\"/>").append("\n");
            stringBuilder.append("</gateway>").append("\n");
            stringBuilder.append("</include>").append("\n");


            return stringBuilder.toString();
        } else if (getRegistration().equals(EXTERNAL_N)) {

            stringBuilder.append("<include>").append("\n");
            stringBuilder.append("<gateway name=\"" +getName() + "\"> ").append("\n");
            stringBuilder.append("<param name=\"proxy\" value=\"" + getIp() + "\"/>").append("\n");
            stringBuilder.append("<param name=\"register\" value=\"false\"/>").append("\n");
            stringBuilder.append("<param name=\"rtp-autofix-timing\" value=\"false\"/>").append("\n");
            stringBuilder.append("<param name=\"caller-id-in-from\" value=\"true" +
                    "\"/>").append("\n");
            stringBuilder.append("</gateway>").append("\n");
            stringBuilder.append("</include>").append("\n");
            return stringBuilder.toString();

        }

        return stringBuilder.toString();
    }

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}


    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public Long getFshost_id() {
		return fshost_id;
	}

	public void setFshost_id(Long fshost_id) {
		this.fshost_id = fshost_id;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
