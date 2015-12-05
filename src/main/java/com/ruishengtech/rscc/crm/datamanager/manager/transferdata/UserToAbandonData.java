package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

import com.ruishengtech.rscc.crm.datamanager.model.UserData;

public class UserToAbandonData extends TransferData {
	
	private UserData userData;

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}
	
}
