package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // 생성자 final 필드를 가지고 만들어줭 이게 최고양
public class MemberService {


    private final MemberRepository memberRepository;


    /**
     * 회원가입
     */
    @Transactional // 여기선 쓰기라 false 기본값으로 두게 만든다.
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        List<Member> findMembers =memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw  new IllegalStateException("이미 존재하는 회원입니당");
        }
    }

    //전체 회원 조회
    // 조회영역에선 true를 붙여주자 속도가 빨라진다 한다
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberID){
        return memberRepository.findOne(memberID);
    }


}
