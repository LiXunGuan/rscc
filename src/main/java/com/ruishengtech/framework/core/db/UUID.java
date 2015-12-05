package com.ruishengtech.framework.core.db;

public class UUID {
	private String uuid;
	
	private UUID(){
		
	}
	
	private UUID(String uuid) {
		this.uuid = uuid.replace("-", "");
	}
	
	public UUID(java.util.UUID uuid) {
		this.uuid = uuid.toString().replace("-", "");
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String toString() {
		return this.uuid.replace("-", ""); //确保没有横杠
	}
	
	public static UUID randomUUID() {
		return new UUID(java.util.UUID.randomUUID());
	}
	
	public static UUID UUIDFromString(String uuid) {
		return new UUID(uuid);
	}
}
