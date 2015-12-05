package com.ruishengtech.rscc.crm.billing.model;

import org.json.JSONObject;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.Index;
import com.ruishengtech.framework.core.db.IndexDefinition;
import com.ruishengtech.framework.core.db.Key;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

@Table(name = "billing_bill")
public class Billing {
	
	//坐席呼叫
	public static final Integer TYPE_AGENT_CALL = 0;

	//群呼
	public static final Integer TYPE_GROUP_CALL = 1;
	
	@Key
	@Column(meta = "SERIAL")
	private Long id;
	
	@NColumn(meta = "INT")
	private Integer type;	//类型，0为坐席单呼，0为队列群呼

	@Index
	@NColumn(meta = "VARCHAR(64)")
	private String caller;	//呼出者，0时为坐席name，1时为队列id
	
	@NColumn(meta = "VARCHAR(64)")
	private String exten;	//分机
	
	@Index(type = IndexDefinition.TYPE_NORMAL, method = IndexDefinition.METHOD_HASH)
	@NColumn(meta = "VARCHAR(64)", column="call_session_uuid")
	private String callSessionUuid;	//呼叫id
	
	@NColumn(meta = "VARCHAR(64)", column="access_number")
	private String accessNumber;	//接入号
	
	@NColumn(meta = "VARCHAR(64)", column="dest_number")
	private String destNumber;	//呼叫号
	
	@Index
	@NColumn(meta = "DATETIME", column="start_stamp")
	private String startStamp; //开始时间
	
	@Index
	@NColumn(meta = "DATETIME", column="end_stamp")
	private String endStamp;	//结束时间
	
	@Index
	@NColumn(meta = "VARCHAR(64)", column = "account_code")
	private String accountCode;	//费用
	
	@NColumn(meta = "INT")
	private Integer duration;	//持续时长ms
	
	@NColumn(meta = "VARCHAR(64)")
	private String rate;	//费率
	
	@NColumn(meta = "FLOAT")
	private Float cost;	//费用
	
	//时长总和
	private Integer sumD;
	//费用总和
	private Float sumC;
	

	public Integer getSumD() {
		return sumD;
	}

	public void setSumD(Integer sumD) {
		this.sumD = sumD;
	}

	public Float getSumC() {
		return sumC;
	}

	public void setSumC(Float sumC) {
		this.sumC = sumC;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDestNumber() {
		return destNumber;
	}

	public void setDestNumber(String destNumber) {
		this.destNumber = destNumber;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getExten() {
		return exten;
	}

	public void setExten(String exten) {
		this.exten = exten;
	}

	public String getCallSessionUuid() {
		return callSessionUuid;
	}

	public void setCallSessionUuid(String callSessionUuid) {
		this.callSessionUuid = callSessionUuid;
	}

	public String getAccessNumber() {
		return accessNumber;
	}

	public void setAccessNumber(String accessNumber) {
		this.accessNumber = accessNumber;
	}

	public String getStartStamp() {
		return startStamp;
	}

	public void setStartStamp(String startStamp) {
		this.startStamp = startStamp;
	}

	public String getEndStamp() {
		return endStamp;
	}

	public void setEndStamp(String endStamp) {
		this.endStamp = endStamp;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}
	
	public Billing(){
		super();
	}
	
	public Billing(JSONObject jsonObject) {
		if (jsonObject.optString("user_field").startsWith("gcall")) { //群呼
			this.setType(TYPE_GROUP_CALL);
//			this.setCaller(QueueManager.getQueueExtenMap().get(jsonObject.optString("dest_number")).toString());
			String name = jsonObject.optString("dest_name");
			name = name.startsWith("技能组")?name.substring(3):name.startsWith("队列")?name.substring(2):name;
			this.setCaller(name);
			this.setExten(jsonObject.optString("dest_number"));
			this.setCallSessionUuid(jsonObject.optString("call_session_uuid"));
			this.setAccessNumber(jsonObject.optString("access_number"));
			this.setDestNumber(jsonObject.optString("caller_id_number"));
			this.setStartStamp(jsonObject.optString("answer_stamp"));
			this.setEndStamp(jsonObject.optString("end_stamp"));
			this.setDuration((int)Math.ceil(jsonObject.optInt("billsec")*1.0/1000));
		} else if ("in_out".equals(jsonObject.optString("call_direction"))) { //单呼
			this.setType(TYPE_AGENT_CALL);
			this.setCaller(jsonObject.optString("caller_agent_id"));
			this.setExten(jsonObject.optString("caller_id_number"));
			this.setCallSessionUuid(jsonObject.optString("call_session_uuid"));
			this.setAccessNumber(jsonObject.optString("access_number"));
			this.setDestNumber(jsonObject.optString("dest_number"));
			this.setStartStamp(jsonObject.optString("answer_stamp"));
			this.setEndStamp(jsonObject.optString("bridge_stamp"));
			this.setDuration((int)Math.ceil(jsonObject.optInt("bridgesec")*1.0/1000));
		}
	}
	
	public float calcCost(Float money, Integer second) {
		if (second == 0) {
			this.setCost(0f);
		} else {
			this.setCost((float)Math.ceil(this.duration * 1.0 / second) * money);
		}
		return this.getCost();
	}

}

/*
 * 一次外呼
{
    "record_file": "http://192.168.1.139:18080/home/record/2015/07/31/01c2289c-5a83-49fa-9005-92e1ccb311a8.mp3",
    "end_stamp": "2015-07-31 11:27:24",
    "dest_number": "18930796307",
    "hangup_cause": "NORMAL_CLEARING",
    "type": "master",
    "timestamp": "2015-07-31 11:27:30",
    "caller_agent_interface_exten": "1001",
    "caller_type": "SIPUSER",
    "answer_stamp": "2015-07-31 11:27:07",
    "is_upload": "0",
    "bridgesec": 5180,
    "event": "cdr_event",
    "dest_name": "18930796307",
    "caller_agent_interface_type": "SIPUSER",
    "gateway_name": "192.168.1.140",
    "aleguuid": "0764173a-3734-11e5-85e2-dfd01e3c05f0",
    "caller_name": "分机1001",
    "call_direction": "in_out",
    "caller_agent_info": "110",
    "dest_type": "OUTBOUNDNUMBER",
    "start_stamp": "2015-07-31 11:27:05",
    "billsec": 17661,
    "access_number": "60172062",
    "call_session_uuid": "01c2289c-5a83-49fa-9005-92e1ccb311a8",
    "caller_agent_interface_name": "1001",
    "caller_id_number": "1001",
    "caller_agent_id": "wy",
    "bridge_stamp": "2015-07-31 11:27:19",
    "sip_hangup_disposition": "send_bye",
    "duration": 19480,
    "context": "default",
    "bleguuid": "0882abae-3734-11e5-8603-dfd01e3c05f0",
    "dest_agent_interface_type": "OUTBOUNDNUMBER",
    "fshost_name": "192.168.1.139",
    "dest_agent_interface_name": "18930796307",
    "dest_agent_interface_exten": "18930796307"
}
 *一次群呼
{
	"record_file": "http://192.168.1.139:18080/home/record/2015/07/31/d9a9f933-4c30-4f4a-8914-3d01afcfef40.mp3",
	"end_stamp": "2015-07-31 11:48:59",
	"dest_number": "99999",
	"hangup_cause": "NORMAL_CLEARING",
	"type": "master",
	"dest_agent_info": "110",
	"timestamp": "2015-07-31 11:49:04",
	"caller_agent_interface_exten": "18930796307",
	"user_field": "gcall39&e7795dc8e0864ab7a3853cd2cb11ebc7",
	"caller_type": "OUTBOUNDNUMBER",
	"answer_stamp": "2015-07-31 11:48:45",
	"is_upload": "0",
	"bridgesec": 11300,
	"event": "cdr_event",
	"dest_name": "99",
	"caller_agent_interface_type": "OUTBOUNDNUMBER",
	"gateway_name": "122.193.90.91",
	"aleguuid": "08433804-3737-11e5-8613-dfd01e3c05f0",
	"caller_name": "18930796307",
	"call_direction": "out_in",
	"dest_type": "CALLCENTER",
	"start_stamp": "2015-07-31 11:48:35",
	"billsec": 13820,
	"call_session_uuid": "d9a9f933-4c30-4f4a-8914-3d01afcfef40",
	"access_number": "18930796307",
	"caller_agent_interface_name": "18930796307",
	"dest_agent_id": "wy",
	"caller_id_number": "18930796307",
	"bridge_stamp": "2015-07-31 11:48:48",
	"sip_hangup_disposition": "recv_bye",
	"duration": 24160,
	"context": "default",
	"bleguuid": "0e7f1486-3737-11e5-8637-dfd01e3c05f0",
	"dest_agent_interface_type": "SIPUSER",
	"fshost_name": "192.168.1.139",
	"dest_agent_interface_name": "1001",
	"dest_agent_interface_exten": "1001"
  }
 */
