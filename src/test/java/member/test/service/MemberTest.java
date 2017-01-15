package member.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mitdy.shopping.member.service.MemberService;
import com.mitdy.shopping.test.base.BaseTest;

public class MemberTest extends BaseTest {
    
    
    @Autowired
    private MemberService memberService;
    
    @Test
    public void getMemberCountByIdTest() {
        for (int i = 0; i < 10; i++) {
            long count = memberService.getCountById(1L);
            System.out.println("count: " + count);
        }
        
    }

}
