package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest     // Spring Boot 상에서 Test할 때 필요
@Transactional  // RollBack을 위한 선언
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        // given → 테스트를 준비하는 과정 (~가 주어지고)
        Member member = new Member();
        member.setName("JunyHarang");

        // when → 실제로 테스트를 실행하는 과정(~을 했을 때)
        Long saveId = memberService.join(member);


        // then → 테스트를 검증하는 과정(~한 값이 나와야한다)
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));

    } // 회원가입 끝

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given → 테스트를 준비하는 과정 (~가 주어지고)
        Member member1 = new Member();
        member1.setName("JunyHarang");

        Member member2 = new Member();
        member2.setName("JunyHarang");

        // when → 실제로 테스트를 실행하는 과정( ~을 했을 때)
        memberService.join(member1);
        memberService.join(member2);        // 예외가 발생해야 한다!
        
        /* 상단에 @Test(expected = IllegalStateException)인하여 주석
        try {
            memberService.join(member2);    // 예외가 발생해야 한다! 똑같은 이름을 두명 넣었기 때문
        } catch (IllegalStateException e) {
            return;
        }
        
         */


        // then → 테스트를 검증하는 과정(~한 값이 나와야한다)
        fail("회원 중복으로 인한 예외가 발생하였습니다!");


    } // 중복_회원_예외 끝
    
} // Class 끝