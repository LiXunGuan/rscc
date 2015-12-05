package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

import com.ruishengtech.rscc.crm.datamanager.model.UserData;

public class UserToShareData extends TransferData {
	
	//当前移动到共享池的是哪个批次的数据，用于更新批次表
	private UserData userData;

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}
	

}
