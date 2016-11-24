package com.mitdy.shopping.security.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.mitdy.core.domain.AuditableEntity;
import com.mitdy.shopping.security.domain.enumuration.UserStatus;

@Entity
@Table(name = "SECURITY_USER")
public class User extends AuditableEntity {

    private static final long serialVersionUID = 9083601521746820850L;

    @Column(name = "USERNAME", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "NICKNAME", length = 20, nullable = true)
    private String nickname;

    @Column(name = "PASSWORD", length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    private UserStatus status;

    public User() {
    }

    public User(String username, String nickname, String password, UserStatus status, Date createTime,
            String createUser) {
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

}