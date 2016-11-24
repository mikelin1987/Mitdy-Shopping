package com.mitdy.shopping.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mitdy.domain.AuditableEntity;

@Entity
@Table(name = "DEMO_USER")
public class User extends AuditableEntity {

    private static final long serialVersionUID = 7725254424862008109L;

    @Column(name = "USERNAME", length = 20, unique = true, nullable = false)
    private String username;

    @Column(name = "NICKNAME", length = 20, nullable = true)
    private String nickname;

    @Column(name = "PASSWORD", length = 20, nullable = false)
    private String password;

    @Column(name = "STATUS", length = 20, nullable = false)
    private String status;

    public User() {
    }

    public User(String username, String nickname, String password, Date createTime, String createUser, String status) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.status = status;
        setCreateTime(createTime);
        setCreateUser(createUser);
        setLastUpdateTime(createTime);
        setLastUpdateUser(createUser);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}