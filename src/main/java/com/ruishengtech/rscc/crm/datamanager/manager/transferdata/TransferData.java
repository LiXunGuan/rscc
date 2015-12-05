package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by yaoliceng on 2015/8/18.
 */
public class TransferData {

	private Collection<String> datas;

	public Collection<String> getDatas() {
		return datas;
	}

	public void setDatas(Collection<String> datas) {
		this.datas = datas;
	}

	public void setDatas(String[] datas) {
		this.datas = Arrays.asList(datas);
	}

}
