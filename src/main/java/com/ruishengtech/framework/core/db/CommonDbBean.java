package com.ruishengtech.framework.core.db;

public class CommonDbBean {

	@Key
    @Column(meta = "VARCHAR(64)")
    private UUID uuid;

    public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uid = uuid.toString();
		this.uuid = uuid;
	}


	private String uid;
	
    public String getUid() {
		return uid;
	}

    public void setUid(String uid) {
		this.uid = uid;
		this.uuid = UUID.UUIDFromString(uid);
	}
}

