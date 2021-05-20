package com.lb.books.configuration;

public enum UserPermission {

	USER_READALL("user:readAll");
	
	private final String permission;

	UserPermission(String permission) {
		this.permission = permission;
	}
	
	public String getPermssion() {
		return this.permission;
	}
	
}



