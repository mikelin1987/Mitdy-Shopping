package com.mitdy.core.domain;

import java.util.Date;

public interface Principal {

	public String getUserId();
	
	public String getUserName();
	
	public Date getLastLoginTime();

	public String getPassword();
	
	public boolean isAdmin();
	
}
