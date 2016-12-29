package com.mitdy.shopping.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mitdy.core.domain.AuditableEntity;
import com.mitdy.shopping.member.domain.enumeration.Gender;

@Entity
@Table(name = "MEMBER_MEMBER")
public class Member extends AuditableEntity {

    private static final long serialVersionUID = 5260310427069765425L;

    @Column(name = "LOGIN_NAME", length = 50, nullable = false)
    private String loginName;

    @Column(name = "LOGIN_PASSWORD", length = 50, nullable = false)
    private String loginPassword;

    @Column(name = "NICKNAME", length = 20, nullable = false)
    private String nickname;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDAY", length = 20, nullable = true)
    private Date birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", length = 10, nullable = false)
    private Gender gender;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

}
