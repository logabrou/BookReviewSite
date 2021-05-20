package com.lb.books.configuration;

import java.util.HashSet;
import java.util.Set;
import com.google.common.collect.Sets;

public enum UserRole {
	
	
	USER(Sets.newHashSet()),	
	ADMIN(Sets.newHashSet(UserPermission.USER_READALL));
	
	Set<UserPermission> setty = new HashSet<>();
	Set<UserPermission> sets = new HashSet<>();
	
	private final Set<UserPermission> permissions;
	
	UserRole(Set<UserPermission> permissions) {
		this.permissions = permissions;
	}
	
	public Set<UserPermission> getPermissions() {
		return permissions;
	}
}
