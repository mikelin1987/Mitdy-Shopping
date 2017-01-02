package com.mitdy.core.domain;

import java.util.Date;

public class Auditer {

	public static final String SYSTEM_RESERVED_USER_ID = "SYTEM";

	public static void audit(AuditableEntity auditable, Principal principal) {
		if (auditable.getId() == null) {
			onCreate(auditable, principal);
		} else {
			onUpdate(auditable, principal);
		}
	}

	public static void onCreate(AuditableEntity auditable, Principal principal) {
		if (auditable.getId() == null) {
			if (auditable.getCreateUser() == null) {
				auditable.setCreateUser(principal != null ? principal.getUsername() : SYSTEM_RESERVED_USER_ID);
			}
			auditable.setCreateTime(new Date());
		}

		onUpdate(auditable, auditable.getCreateUser(), auditable.getCreateTime());
	}

	public static void onCreate(AuditableEntity auditable, String creator) {
		auditable.setCreateUser(creator);
		auditable.setCreateTime(new Date());

		onUpdate(auditable, auditable.getCreateUser(), auditable.getCreateTime());
	}

	public static void onUpdate(AuditableEntity auditable, Principal principal) {
		auditable.setLastUpdateUser(principal != null ? principal.getUsername() : SYSTEM_RESERVED_USER_ID);
		auditable.setLastUpdateTime(new Date());
	}

	public static void onUpdate(AuditableEntity auditable, String lastUpdateUser, Date lastUpdateTime) {
		auditable.setLastUpdateUser(lastUpdateUser);
		auditable.setLastUpdateTime(lastUpdateTime);
	}

	public static Principal getSystemUser() {
		return new Principal() {


			@Override
			public String getUsername() {
				return SYSTEM_RESERVED_USER_ID;
			}

			@Override
			public String getPassword() {
				return null;
			}

			@Override
			public boolean isAdminUser() {
				return true;
			}
		};
	}

}
