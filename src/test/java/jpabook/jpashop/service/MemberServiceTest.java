package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
/*    @Rollback(false) //이렇게 하면 db에 쿼리를 날리는것을 볼수 있다.*/
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("Kim");
        //when
        Long saveId = memberService.join(member);
        //then
        em.flush(); // 2번 롤백안쓰고 하는법 한번쓰고
        assertEquals(member, memberRepository.findOne(saveId));
     }//여기서 롤백됨

     @Test(expected = IllegalStateException.class) // 이렇게 하면트라이캐치문 안써도됨
     public void 중복_회원_예외() throws Exception{
         //given
         Member member1 = new Member();
         member1.setName("kim");

         Member member2 = new Member();
         member2.setName("kim");
         //when
         memberService.join(member1);
         memberService.join(member2);

         //then
         fail("예외가 발생해야한다");


      }


}