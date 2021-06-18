package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// JPA가 조회하는 것에서 성능 최적화를 위해 옵션 선언 읽기에만 넣는다.
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    
    private final MemberRepository memberRepository;

    // 위의 RequiredArgsConstructor Annotaion으로 대체
//    // 생성자 Injection을 위한 생성자 선언
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    } // 생성자 끝

    /*
    // Setter Injection을 위한 Setter 선언
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } // setMemberRepository 끝
    */
    
    /**
     * 회원 가입
     */

    // 읽기 전용이 아니기 때문에 readOnly를 false로 설정
    @Transactional(readOnly = false)
    public Long join(Member member) {
        validateDuplicateMember(member);    // 중복 회원 검증
        memberRepository.save(member);

        return member.getId();
    } // join 끝

    private void validateDuplicateMember(Member member) {
        //중복 회원이 있을 경우 예외를 발생
        List<Member> findMembers = memberRepository.findByName(member.getName());  // DB에 같은 이름이 있는지 확인

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원 입니다!");
        } // if 끝

    } // validateDuplidateMember 끝


    public List<Member> findMembers() { /* 회원 전체 조회 */
        return memberRepository.findAll();
    } // findMembers 끝

    
    public Member findOne(Long memberId) { /* 회원 한명 조회 */
        return memberRepository.findOne(memberId);
    } // findOne 끝

} // Class 끝
