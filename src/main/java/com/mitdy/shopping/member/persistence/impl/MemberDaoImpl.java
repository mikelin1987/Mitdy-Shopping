package com.mitdy.shopping.member.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mitdy.core.persistence.JpaAbstractEntityDao;
import com.mitdy.shopping.member.domain.Member;
import com.mitdy.shopping.member.persistence.MemberDao;

@Repository("memberDao")
public class MemberDaoImpl extends JpaAbstractEntityDao<Member> implements MemberDao {

}
