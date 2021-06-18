package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    } // save 끝

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    } // findOne 끝

//    public List<Order> findAll(OrderSearch orderSearch) {
//
//    }
} // Class 끝
