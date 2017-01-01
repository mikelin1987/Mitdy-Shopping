package com.mitdy.shopping.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitdy.shopping.member.domain.Member;
import com.mitdy.shopping.member.persistence.MemberDao;
import com.mitdy.shopping.member.service.MemberService;

@Transactional
@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;

	@Override
	public Member addMember(Member member) {
		return memberDao.save(member);
	}

}
