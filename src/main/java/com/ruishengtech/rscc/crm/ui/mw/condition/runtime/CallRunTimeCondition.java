package com.ruishengtech.rscc.crm.ui.mw.condition.runtime;

import com.ruishengtech.framework.core.db.condition.Page;

/**
 * Created by yaoliceng on 2014/11/3.
 */
public class CallRunTimeCondition extends Page {
	
	private String calling;
	
	private String called;

	public String getCalling() {
		return calling;
	}

	public void setCalling(String calling) {
		this.calling = calling;
	}

	public String getCalled() {
		return called;
	}

	public void setCalled(String called) {
		this.called = called;
	}
}
