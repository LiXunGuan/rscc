package com.ruishengtech.rscc.crm.ui.mw.send.exception;

import org.json.JSONObject;

public class HttpException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2598969595084162850L;

	private int errorCode;
	
	private String errorMsg;
	
	public HttpException() {
		super();
		this.errorCode = 0;
		this.errorMsg = "无消息";
	}

	public HttpException(JSONObject response) {
		super();
		this.errorCode = response.optInt("exit_code");
		this.errorMsg = response.optString("err_msg");
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
	
	
}
