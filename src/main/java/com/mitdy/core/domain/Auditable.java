package com.mitdy.core.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mitdy.core.json.CustomDateSerializer;

public interface Auditable {
	
	public Long getId();

    public void setCreateUser(String user);

    public void setCreateTime(Date createTime);

    public void setLastUpdateUser(String lastUpdUser);

    public void setLastUpdateTime(Date lastUpdTime);

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getCreateTime();

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getLastUpdateTime();
    
    public String getCreateUser();
    
    public String getLastUpdateUser();

}
