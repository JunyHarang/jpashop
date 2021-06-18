package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository     // Spring Bean으로 만들어 주기 위한 Annotation
@RequiredArgsConstructor
public class MemberRepository {

    // Spring이 EntityManger를 만들어서 주입 시켜주게 하기 위해 선언
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    } //save 끝

    public Member findOne(Long id) { /* 한건 조회용 */
        return em.find(Member.class, id);
    } // findOne 끝
    
    public List<Member> findAll() { /* 다건 조회용 */

       return em.createQuery("select m from Member m", Member.class).getResultList();
    } // findAll 끝

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();
    }
} // Class 끝
