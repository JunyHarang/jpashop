package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        
        if (item.getId() == null) {
            // 아이템을 신규로 등록
            em.persist(item);

        } else {
            // 기존에 있는 아이템을 가져온다.
            em.merge(item);
        } // if-else문 끝

    } // save 끝
    
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    } // findOne 끝

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    } // findAll 끝
} // Class 끝
