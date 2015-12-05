package com.ruishengtech.rscc.crm.ui.mw.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Table;
import com.ruishengtech.rscc.crm.ui.mw.db.CommonDbBean;

/**
 * Created by yaoliceng on 2014/11/14.
 */
@Table(name = "mw.fs_sipprofile")
public class FSSipProfile extends CommonDbBean{

    @Column(meta = "VARCHAR(64)")
    private String name;

    @Column(meta = "VARCHAR(64)")
    private String sipIp;

    @Column(meta = "VARCHAR(5)")
    private String sipPort;

	public static final String INTERNAL = "i";

    public static final String EXTERNAL = "e";
	
	public static Map<String, String> INTEXT = new LinkedHashMap<String, String>() {
		{
            put(EXTERNAL, "external");
            put(INTERNAL, "internal");
		}
		
	};
    
    @Column(meta = "CHAR(1)")
    private String type;
    
    @Column(meta = "INT",column = "fshost_id")
    private Long fshostid;
    
    private String servername;
    
    @Column(meta = "VARCHAR(100)")
    private String codecString;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSipIp() {
        return sipIp;
    }

    public void setSipIp(String sipIp) {
        this.sipIp = sipIp;
    }

    public String getSipPort() {
        return sipPort;
    }

    public void setSipPort(String sipPort) {
        this.sipPort = sipPort;
    }

	public String getCodecString() {
		return codecString;
	}

	public void setCodecString(String codecString) {
		this.codecString = codecString;
	}


    public String toXml() {
        StringBuilder ret = new StringBuilder();
        if (INTERNAL.equals(type)) {

            ret.append("<profile name=\""+getName()+"\">\n" +
                    " \n" +
                    "  <aliases>\n" +
                    "  </aliases>\n" +
                    "    <domain name=\"all\" alias=\"true\" parse=\"false\"/>\n" +
                    "  </domains>\n" +
                    "  <settings>\n" +
                    "\n" +
                    "\n" +
                    "    <param name=\"debug\" value=\"0\"/>\n" +
                    "    <param name=\"sip-trace\" value=\"no\"/>\n" +
                    "    <param name=\"sip-capture\" value=\"no\"/>\n" +
                    "    <param name=\"watchdog-enabled\" value=\"no\"/>\n" +
                    "    <param name=\"watchdog-step-timeout\" value=\"30000\"/>\n" +
                    "    <param name=\"watchdog-event-timeout\" value=\"30000\"/>\n" +
                    "    <param name=\"log-auth-failures\" value=\"false\"/>\n" +
                    "    <param name=\"forward-unsolicited-mwi-notify\" value=\"false\"/>\n" +
                    "    <param name=\"context\" value=\"public\"/>\n" +
                    "    <param name=\"rfc2833-pt\" value=\"101\"/>\n" +
                    "   \n" +
                    "    <param name=\"sip-port\" value=\""+getSipPort()+"/>\n" +
                    "    <param name=\"dialplan\" value=\"XML\"/>\n" +
                    "    <param name=\"dtmf-duration\" value=\"2000\"/>\n" +
                    "    <param name=\"inbound-codec-prefs\" value=\"$${global_codec_prefs}\"/>\n" +
                    "    <param name=\"outbound-codec-prefs\" value=\"$${global_codec_prefs}\"/>\n" +
                    "    <param name=\"rtp-timer-name\" value=\"soft\"/>\n" +
                    "    <param name=\"rtp-ip\" value=\""+getSipIp()+"\"/>\n" +
                    "    <param name=\"sip-ip\" value=\""+getSipIp()+"\"/>\n" +
                    "    <param name=\"hold-music\" value=\"$${hold_music}\"/>\n" +
                    "    <param name=\"apply-nat-acl\" value=\"nat.auto\"/>\n" +
                    "    <param name=\"apply-inbound-acl\" value=\"domains\"/>\n" +
                    "    <param name=\"local-network-acl\" value=\"localnet.auto\"/>\n" +
                    "    <param name=\"record-path\" value=\"$${recordings_dir}\"/>\n" +
                    "    <param name=\"record-template\" value=\"${caller_id_number}.${target_domain}.${strftime(%Y-%m-%d-%H-%M-%S)}.wav\"/>\n" +
                    "    <param name=\"manage-presence\" value=\"true\"/>\n" +
                    "    <param name=\"presence-hosts\" value=\"$${domain},$${local_ip_v4}\"/>\n" +
                    "    <param name=\"presence-privacy\" value=\"$${presence_privacy}\"/>\n" +
                    "    <param name=\"inbound-codec-negotiation\" value=\"generous\"/>\n" +
                    "    <param name=\"tls\" value=\"$${internal_ssl_enable}\"/>\n" +
                    "    <param name=\"tls-only\" value=\"false\"/>\n" +
                    "    <param name=\"tls-bind-params\" value=\"transport=tls\"/>\n" +
                    "    <param name=\"tls-sip-port\" value=\"$${internal_tls_port}\"/>\n" +
                    "    <param name=\"tls-passphrase\" value=\"\"/>\n" +
                    "    <param name=\"tls-verify-date\" value=\"true\"/>\n" +
                    "    <param name=\"tls-verify-policy\" value=\"none\"/>\n" +
                    "    <param name=\"tls-verify-depth\" value=\"2\"/>\n" +
                    "    <param name=\"tls-verify-in-subjects\" value=\"\"/>\n" +
                    "    <param name=\"tls-version\" value=\"$${sip_tls_version}\"/>\n" +
                    "    <param name=\"tls-ciphers\" value=\"$${sip_tls_ciphers}\"/>\n" +
                    "    <param name=\"inbound-late-negotiation\" value=\"true\"/>\n" +
                    "    <param name=\"inbound-zrtp-passthru\" value=\"true\"/>\n" +
                    "    <param name=\"nonce-ttl\" value=\"60\"/>\n" +
                    "    <param name=\"auth-calls\" value=\"$${internal_auth_calls}\"/>\n" +
                    "    <param name=\"inbound-reg-force-matching-username\" value=\"true\"/>\n" +
                    "    <param name=\"auth-all-packets\" value=\"false\"/>\n" +
                    "    <param name=\"ext-rtp-ip\" value=\"auto-nat\"/>\n" +
                    "    <param name=\"ext-sip-ip\" value=\"auto-nat\"/>\n" +
                    "    <param name=\"rtp-timeout-sec\" value=\"300\"/>\n" +
                    "    <param name=\"rtp-hold-timeout-sec\" value=\"1800\"/>\n" +
                    "    <param name=\"force-register-domain\" value=\"$${domain}\"/> \n" +
                    "    <param name=\"force-subscription-domain\" value=\"$${domain}\"/>\n" +
                    "    <param name=\"force-register-db-domain\" value=\"$${domain}\"/>\n" +
                    "    <param name=\"challenge-realm\" value=\"auto_from\"/>\n" +
                    "    <param name=\"liberal-dtmf\" value=\"true\"/>\n" +
                    "  </settings>\n" +
                    "</profile>\n");

        } else if (EXTERNAL.equals(type)) {

            ret.append("<profile name=\""+getName()+"\">\n" +
                    "  <gateways>\n" +
                    "    <X-PRE-PROCESS cmd=\"include\" data=\""+getName()+"/*.xml\"/>\n" +
                    "  </gateways>\n" +
                    "  <aliases>\n" +
                    "  </aliases>\n" +
                    "  <domains>\n" +
                    "    <domain name=\"all\" alias=\"false\" parse=\"true\"/>\n" +
                    "  </domains>\n" +
                    "  <settings>\n" +
                    "    <param name=\"debug\" value=\"0\"/>\n" +
                    "    <param name=\"sip-trace\" value=\"no\"/>\n" +
                    "    <param name=\"sip-capture\" value=\"no\"/>\n" +
                    "    <param name=\"rfc2833-pt\" value=\"101\"/>\n" +
                    "    <param name=\"sip-port\" value=\"$${external_sip_port}\"/>\n" +
                    "    <param name=\"dialplan\" value=\"XML\"/>\n" +
                    "    <param name=\"context\" value=\"public\"/>\n" +
                    "    <param name=\"dtmf-duration\" value=\"2000\"/>\n" +
                    "    <param name=\"inbound-codec-prefs\" value=\"$${global_codec_prefs}\"/>\n" +
                    "    <param name=\"outbound-codec-prefs\" value=\"$${outbound_codec_prefs}\"/>\n" +
                    "    <param name=\"hold-music\" value=\"$${hold_music}\"/>\n" +
                    "    <param name=\"rtp-timer-name\" value=\"soft\"/>\n" +
                    "    <param name=\"local-network-acl\" value=\"localnet.auto\"/>\n" +
                    "    <param name=\"manage-presence\" value=\"false\"/>\n" +
                    "    <param name=\"inbound-codec-negotiation\" value=\"generous\"/>\n" +
                    "    <param name=\"nonce-ttl\" value=\"60\"/>\n" +
                    "    <param name=\"auth-calls\" value=\"false\"/>\n" +
                    "    <param name=\"inbound-late-negotiation\" value=\"true\"/>\n" +
                    "    <param name=\"inbound-zrtp-passthru\" value=\"true\"/> \n" +
                    "    <param name=\"rtp-ip\" value=\"$${local_ip_v4}\"/>\n" +
                    "    <param name=\"sip-ip\" value=\"$${local_ip_v4}\"/>\n" +
                    "    <param name=\"ext-rtp-ip\" value=\"auto-nat\"/>\n" +
                    "    <param name=\"ext-sip-ip\" value=\"auto-nat\"/>\n" +
                    "    <param name=\"rtp-timeout-sec\" value=\"300\"/>\n" +
                    "    <param name=\"rtp-hold-timeout-sec\" value=\"1800\"/>\n" +
                    "    <param name=\"tls\" value=\"$${external_ssl_enable}\"/>\n" +
                    "    <param name=\"tls-only\" value=\"false\"/>\n" +
                    "    <param name=\"tls-bind-params\" value=\"transport=tls\"/>\n" +
                    "    <param name=\"tls-sip-port\" value=\"$${external_tls_port}\"/>\n" +
                    "    <param name=\"tls-passphrase\" value=\"\"/>\n" +
                    "    <param name=\"tls-verify-date\" value=\"true\"/>\n" +
                    "    <param name=\"tls-verify-policy\" value=\"none\"/>\n" +
                    "    <param name=\"tls-verify-depth\" value=\"2\"/>\n" +
                    "    <param name=\"tls-verify-in-subjects\" value=\"\"/>\n" +
                    "    <param name=\"tls-version\" value=\"$${sip_tls_version}\"/>\n" +
                    "    <param name=\"liberal-dtmf\" value=\"true\"/>\n" +
                    "  </settings>\n" +
                    "</profile>\n");

        }
        return ret.toString();
    }

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public Long getFshostid() {
		return fshostid;
	}

	public void setFshostid(Long fshostid) {
		this.fshostid = fshostid;
	}

}
