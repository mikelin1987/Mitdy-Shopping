package com.mitdy.shopping.member.service;

import org.springframework.cache.annotation.Cacheable;

import com.mitdy.shopping.member.domain.Member;

public interface MemberService {

    public Member addMember(Member member);

    @Cacheable(value = "memberCountById", key = "'id_'+#id")
    public long getCountById(Long id);
    
}
