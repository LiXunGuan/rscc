package com.ruishengtech.rscc.crm.datamanager.manager.transferdata;

import com.ruishengtech.rscc.crm.datamanager.model.UserData;

public class UserToUserData extends TransferData {
	
	//把userData存进来，可以直接取到transferUser和transferPhone
	private UserData userData;
	
	//是变更还是添加
//	private boolean isChange;
	
	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

}
