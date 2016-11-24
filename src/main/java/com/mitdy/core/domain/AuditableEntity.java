package com.mitdy.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class AuditableEntity extends AbstractEntity implements Auditable {

    private static final long serialVersionUID = 4630184875280455364L;

    @Column(name = "CREATE_USER", length = 20, nullable = false)
    private String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME", nullable = false)
    private Date createTime;

    @Column(name = "LAST_UPDATE_USER", length = 20, nullable = false)
    private String lastUpdateUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATE_TIME", nullable = false)
    private Date lastUpdateTime;

    public void onCreate(String createUser) {
        this.createUser = createUser;
        this.createTime = new Date();
    }

    public void onUpdate(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
        this.lastUpdateTime = new Date();
    }

    public AuditableEntity() {
    }

    public AuditableEntity(String createUser) {
        onCreate(createUser);
        onUpdate(createUser);
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

}
