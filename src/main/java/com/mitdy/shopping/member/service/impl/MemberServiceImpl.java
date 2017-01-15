package com.mitdy.shopping.member.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitdy.shopping.member.domain.Member;
import com.mitdy.shopping.member.mapper.MemberMapper;
import com.mitdy.shopping.member.persistence.MemberDao;
import com.mitdy.shopping.member.service.MemberService;

@Transactional
@Service("memberService")
public class MemberServiceImpl implements MemberService {
    
    public static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private MemberMapper memberMapper;

	@Override
	public Member addMember(Member member) {
		return memberDao.save(member);
	}

    @Override
    public long getCountById(Long id) {
        logger.info("getCountById: {}", id);
        
        return memberMapper.getCountById(id);
    }

}
