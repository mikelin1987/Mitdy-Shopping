package com.mitdy.shopping.member.test.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mitdy.core.domain.Auditer;
import com.mitdy.shopping.member.domain.Member;
import com.mitdy.shopping.member.domain.enumeration.Gender;
import com.mitdy.shopping.member.service.MemberService;
import com.mitdy.shopping.test.base.BaseTest;

public class MemberTest extends BaseTest {

	@Autowired
	private MemberService memberService;
	
	@Test
	public void addMemberTest() {
		Member member = new Member();
		member.setGender(Gender.MALE);
		member.setLoginName("member1");
		member.setLoginPassword("member1");
		member.setNickname("Member01");
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(1980, 11, 31);
		Date birthday = calendar.getTime();;
		member.setBirthday(birthday );
		
		Auditer.audit(member, null);
		
		memberService.addMember(member );
	}
	
}
