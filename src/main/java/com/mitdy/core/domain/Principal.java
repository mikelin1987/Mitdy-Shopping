package com.mitdy.core.domain;

public interface Principal {

	public String getUsername();
	
	public String getPassword();
	
	public boolean isAdminUser();
	
}
