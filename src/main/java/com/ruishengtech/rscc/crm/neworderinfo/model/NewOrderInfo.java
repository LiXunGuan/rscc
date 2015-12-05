package com.ruishengtech.rscc.crm.neworderinfo.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ruishengtech.framework.core.db.Column;
import com.ruishengtech.framework.core.db.CommonDbBean;
import com.ruishengtech.framework.core.db.NColumn;
import com.ruishengtech.framework.core.db.Table;

/**
 * 订单模块
 * @author chengxin
 *
 */
@Table(name="new_order_info")
public class NewOrderInfo extends CommonDbBean{
	
	/**
	 * 订单编号
	 */
	@Column(meta = "VARCHAR(64)",column = "order_id")
	private String orderid;
	
	/**
	 * 收货人姓名
	 */
	@Column(meta = "VARCHAR(64)",column = "receive_user_name")
	private String receiveUserName;
	
	/**
	 * 收货人地址
	 */
	@Column(meta = "VARCHAR(64)",column = "receive_user_address")
	private String receiveUserAddress;
	
	/**
	 * 收货人电话
	 */
	@Column(meta = "VARCHAR(64)",column = "receive_user_mobile")
	private String receiveUserMobile;
	
	/**
	 * 订单状态
	 */
	@Column(meta = "VARCHAR(64)", column = "order_status")
	private String orderStatus;
	
	/**
	 * 订单的创建时间
	 */
	@Column(meta = "DATETIME", column = "order_create_time")
	private Date orderCreateTime;
	
	/**
	 * 订单创建用户的编号
	 */
	@Column(meta = "VARCHAR(64)", column = "order_user_uuid")
	private String orderUserUUID;
	
	/**
	 * 关联录音
	 */
	@NColumn(meta = "VARCHAR(64)", column = "call_session_uuid")
    private String call_session_uuid;
	
	/**
	 * 支付状态
	 */
	@NColumn(meta = "VARCHAR(64)",column = "pay_status")
	private String payStatus;
	
	/**
	 * 运费
	 */
	@NColumn(meta = "VARCHAR(64)",column = "freight")
	private String freight;
	
	/**
	 * 发票类型
	 */
	@NColumn(meta = "VARCHAR(64)",column = "invoice_type")
	private String invoiceType;
	
	/**
	 * 发票具体信息
	 */
	@NColumn(meta = "VARCHAR(64)",column = "invoice_info")
	private String invoiceInfo;
	
	/**
	 * 质检分数
	 */
	private String score;
	
	/**
	 * 接通时长
	 */
	private long bridgesec;
	
	public static final String OrderStatus_WaitConfirm = "waitconfirm";
	public static final String OrderStatus_Confirm = "confirm";
	public static final String OrderStatus_Completed = "complete";
	public static final String OrderStatus_Cancel = "cancel";
	public static final String OrderStatus_Cancelled = "cancelled";
	public static final String OrderStatus_Refund = "refund";
	
	public static Map<String,String> OrderStatus =new LinkedHashMap<String,String>(){
		{
			put(OrderStatus_WaitConfirm, "录入完毕等待确认");
			put(OrderStatus_Confirm, "已确认");
			put(OrderStatus_Completed, "已完成");
			put(OrderStatus_Cancel, "取消中");
			put(OrderStatus_Cancelled, "已取消");
			put(OrderStatus_Refund, "已退款");
		}
	};

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getReceiveUserName() {
		return receiveUserName;
	}

	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}

	public String getReceiveUserAddress() {
		return receiveUserAddress;
	}

	public void setReceiveUserAddress(String receiveUserAddress) {
		this.receiveUserAddress = receiveUserAddress;
	}

	public String getReceiveUserMobile() {
		return receiveUserMobile;
	}

	public void setReceiveUserMobile(String receiveUserMobile) {
		this.receiveUserMobile = receiveUserMobile;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getOrderUserUUID() {
		return orderUserUUID;
	}

	public void setOrderUserUUID(String orderUserUUID) {
		this.orderUserUUID = orderUserUUID;
	}

	public String getCall_session_uuid() {
		return call_session_uuid;
	}

	public void setCall_session_uuid(String call_session_uuid) {
		this.call_session_uuid = call_session_uuid;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public long getBridgesec() {
		return bridgesec;
	}

	public void setBridgesec(long bridgesec) {
		this.bridgesec = bridgesec;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}
}
