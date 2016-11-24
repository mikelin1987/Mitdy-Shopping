package com.mitdy.shopping.member.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mitdy.core.domain.AuditableEntity;

@Entity
@Table(name = "MEMBER_MEMBER")
public class Member extends AuditableEntity {
    
    private static final long serialVersionUID = 5260310427069765425L;

}
